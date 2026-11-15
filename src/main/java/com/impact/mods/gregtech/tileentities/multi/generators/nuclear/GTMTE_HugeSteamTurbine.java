package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.logic.ProcessingInputs;
import gregtech.api.logic.WorldProcessingLogic;
import gregtech.api.logic.ProcessingLogic;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static space.impact.structures.structure.StructureUtility.onElementPass;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofChain;
import static space.impact.structures.structure.StructureUtility.isAir;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_HugeSteamTurbine extends GTMTE_Impact_BlockBase<GTMTE_HugeSteamTurbine> {

    private static final ICasing CASING = ImpactCasings.HUGE_CASING_TURBINE;
    private static final ICasing GEARBOX = Casings.STEEL_GEARBOX;
    private static final int CYCLE_TICKS = 20;
    private static final int STEAM_PER_EU = 2;
    private static final double EU_PER_SUPERHEATED_STEAM = 1.5;
    private static final int STEAM_PER_WATER = 400;
    private int casingCount;
    private int steamRemainder;
    private long steamUsedThisCycle;
    private long superheatedSteamUsedThisCycle;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 56)
            .atLeast(HatchElement.Maintenance).build();
    private static final StructureRequirements DYNAMOS = StructureRequirements.builder(CASING, 0).build();

    int mStoredFluids = 0;
    long mOutputSalary = 0;
    private static IStructureDefinition<GTMTE_HugeSteamTurbine> definition;

    public GTMTE_HugeSteamTurbine(int aID, String aNameRegional) {
        super(aID, "impact.multis.hugesteamturbine", aNameRegional);
    }

    public GTMTE_HugeSteamTurbine(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(), TextureFactory.of(
                        aActive ? Textures.BlockIcons.LARGETURBINE_ST_ACTIVE5 : Textures.BlockIcons.LARGETURBINE_ST5) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_HugeSteamTurbine(this.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 2, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 2, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_HugeSteamTurbine> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition
                    .<GTMTE_HugeSteamTurbine>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "     ", " BBB ", " B~B ", " BBB " },
                            { "     ", "CCCCC", "C A C", "CCCCC" },
                            { " BBB ", "CCCCC", "C A C", "CCCCC" },
                            { "BBBBB", "CCCCC", "C A C", "CCCCC" },
                            { "BBBBB", "CCACC", "C R C", "CCCCC" },
                            { "BBBBB", "CCCCC", "C   C", "CCCCC" },
                            { " BBB ", "CCCCC", "CCCCC", "CCCCC" },
                            { "     ", " DDD ", " DDD ", " DDD " }
                    })
                    .addElement('A', GEARBOX.asElement())
                    .addElement('R', ofChain(isAir(), ofBlock(InsideBlock, 3), ofBlock(InsideBlock, 4)))
                    .addElement('B', onElementPass(machine -> machine.casingCount++, CASING.asElement()))
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_HugeSteamTurbine.class)
                            .autoPlace(HatchElement.InputHatch, HatchElement.OutputHatch)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.BLUE))
                    .addElement('D', withHint(GTStructureUtility.buildHatchAdder(GTMTE_HugeSteamTurbine.class)
                            .allow(ImpactHatchElement.Dynamo, ImpactHatchElement.LaserOutput)
                            .autoPlace(ImpactHatchElement.PowerOutput).buildAndChain(DYNAMOS, machine -> machine.casingCount++),
                            HintColor.YELLOW))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Generator")
                .addInfo("At full efficiency: %s EU per L of steam or %s EU per L of superheated steam.", 1.0 / STEAM_PER_EU, EU_PER_SUPERHEATED_STEAM)
                .addInfo("Maintenance problems double the steam needed per EU.")
                .addInfo("Both steam types can run at once. Excess steam stays in the input hatches.")
                .addInfo("Returns 1 L of distilled water per %s L of either steam type consumed.", STEAM_PER_WATER)
                .beginStructureBlock(5, 4, 8, false)
                .addController("front center of layer 2")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+ total; front and upper cover are fixed", SHELL.getMinimumCasings())
                .addOtherStructurePart(GEARBOX, "exactly 4; central shaft and above the rotor")
                .addStructureInfo("The rotor appears automatically 4 blocks behind the controller; keep this position clear.")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; main body casings")
                .addHatchInfo("Input Hatches", "optional, up to 20; main body casings")
                .addHatchInfo("Dynamo Hatches", "optional, up to 9 total; rear wall")
                .addHatchInfo("Output Hatches", "1+ in the main body for distilled water output")
                .addStructureHint("Main body: casings or input, output and maintenance hatches", HintColor.BLUE)
                .addStructureHint("Rear wall: casings or dynamo hatches", HintColor.YELLOW)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }


    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(2, 2, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputHatches(this) > 20) {
            formationChecklist = false;
        }

        if (mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        if (HatchCounts.dynamoHatches(this) > 9) {
            formationChecklist = false;
        }

        if (formationChecklist) {
            rotorTopTrigger(false);
        }
        return formationChecklist;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (mMachine && aTick % 40 == 0) {
            rotorTopTrigger(aBaseMetaTileEntity.isActive());
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound tag) {
        super.saveNBTData(tag);
        tag.setInteger("steamRemainder", steamRemainder);
        tag.setInteger("steamInputPerTick", mStoredFluids);
        tag.setLong("outputEUt", mOutputSalary);
        tag.setLong("steamUsedThisCycle", steamUsedThisCycle);
        tag.setLong("superheatedSteamUsedThisCycle", superheatedSteamUsedThisCycle);
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        super.loadNBTData(tag);
        steamRemainder = Math.floorMod(tag.getInteger("steamRemainder"), STEAM_PER_WATER);
        mStoredFluids = tag.getInteger("steamInputPerTick");
        mOutputSalary = tag.getLong("outputEUt");
        steamUsedThisCycle = tag.getLong("steamUsedThisCycle");
        superheatedSteamUsedThisCycle = tag.getLong("superheatedSteamUsedThisCycle");
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new WorldProcessingLogic(request -> {
            ProcessingInputs inputs = request.inputs.get(0).copy();
            List<FluidStack> steamInputs = new ArrayList<>();
            List<FluidStack> superheatedSteamInputs = new ArrayList<>();
            long availableSteam = 0;
            long availableSuperheatedSteam = 0;
            for (FluidStack fluid : inputs.fluids) {
                if (fluid == null || fluid.amount <= 0) {
                    continue;
                }
                String name = fluid.getUnlocalizedName();
                if (name.equals("fluid.steam") || name.equals("ic2.fluidSteam")
                        || name.equals("fluid.mfr.steam.still.name")) {
                    availableSteam = Math.addExact(availableSteam, fluid.amount);
                    steamInputs.add(fluid);
                } else if (name.equals("ic2.fluidSuperheatedSteam")) {
                    availableSuperheatedSteam = Math.addExact(availableSuperheatedSteam, fluid.amount);
                    superheatedSteamInputs.add(fluid);
                }
            }

            long steamEnergy = availableSteam / STEAM_PER_EU;
            long superheatedEnergy = (long) (availableSuperheatedSteam * EU_PER_SUPERHEATED_STEAM);
            int cycleCost = request.efficiency < 10000 ? CYCLE_TICKS * 2 : CYCLE_TICKS;
            long availableEnergy = Math.addExact(steamEnergy, superheatedEnergy);
            long output = Math.min(availableEnergy / cycleCost, getMaxOutputVoltage());
            if (output <= 0) {
                return WorldProcessingLogic.Operation.failure(CheckRecipeResult.NO_RECIPE);
            }

            long cycleEnergy = output * cycleCost;
            // Share the output between both fuels when available, and let either cover the other's shortfall.
            long steamShare = Math.min(steamEnergy, Math.max(cycleEnergy / 2, cycleEnergy - superheatedEnergy));
            long steamUsed = steamShare * STEAM_PER_EU;
            // Whole liters must cover the requested energy, including fractional fuel costs.
            long superheatedSteamUsed = (long) Math.ceil((cycleEnergy - steamShare) / EU_PER_SUPERHEATED_STEAM);
            consumeSteam(steamInputs, steamUsed);
            consumeSteam(superheatedSteamInputs, superheatedSteamUsed);

            long totalSteamUsed = Math.addExact(steamUsed, superheatedSteamUsed);
            int stored = Math.toIntExact(totalSteamUsed / CYCLE_TICKS);
            long steamToCondense = Math.addExact(totalSteamUsed, steamRemainder);
            int waterAmount = Math.toIntExact(steamToCondense / STEAM_PER_WATER);
            int nextRemainder = (int) (steamToCondense % STEAM_PER_WATER);
            FluidStack[] fluidOutputs = waterAmount > 0 && !mOutputHatches.isEmpty()
                    ? new FluidStack[] { GT_ModHandler.getDistilledWater(waterAmount) }
                    : new FluidStack[0];
            return new WorldProcessingLogic.Operation(
                    0,
                    CYCLE_TICKS,
                    new ItemStack[0],
                    fluidOutputs,
                    () -> {
                        if (!inputs.commit()) {
                            return false;
                        }
                        mStoredFluids = stored;
                        mOutputSalary = output;
                        steamRemainder = nextRemainder;
                        steamUsedThisCycle = steamUsed;
                        superheatedSteamUsedThisCycle = superheatedSteamUsed;
                        return true;
                    }
            );
        });
    }

    private static void consumeSteam(List<FluidStack> stacks, long remaining) {
        for (FluidStack fluid : stacks) {
            if (remaining == 0) {
                break;
            }
            int used = (int) Math.min(remaining, fluid.amount);
            fluid.amount -= used;
            remaining -= used;
        }
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (!isWaitingForOutput()) {
            super.addEnergyOutputMultipleDynamos(Math.min(mOutputSalary, getMaxOutputVoltage()), true);
        }
        return super.onRunningTick(aStack);
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(
            EntityPlayerMP player,
            TileEntity tile,
            NBTTagCompound tag,
            World world,
            int x,
            int y,
            int z
    ) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        double duration = mMaxProgresstime;
        boolean running = tag.getBoolean("isActive") && duration > 0 && !isWaitingForOutput();
        tag.setDouble("steamPerTick", running ? steamUsedThisCycle / duration : 0);
        tag.setDouble("superheatedSteamPerTick", running ? superheatedSteamUsedThisCycle / duration : 0);
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        if (!config.getConfig("gregtech.multiblock", true)) {
            return;
        }
        NBTTagCompound tag = accessor.getNBTData();
        double steamPerTick = tag.getDouble("steamPerTick");
        double superheatedSteamPerTick = tag.getDouble("superheatedSteamPerTick");
        if (steamPerTick <= 0 && superheatedSteamPerTick <= 0) {
            return;
        }
        NumberFormat format = NumberFormat.getNumberInstance();
        if (steamPerTick > 0) {
            tooltip.add(translateToLocalFormatted("impact.waila.turbine.steam", format.format(steamPerTick)));
        }
        if (superheatedSteamPerTick > 0) {
            tooltip.add(translateToLocalFormatted(
                    "impact.waila.turbine.superheated_steam",
                    format.format(superheatedSteamPerTick)
            ));
        }
    }

    private void rotorTopTrigger(boolean active) {
        IGregTechTileEntity base = getBaseMetaTileEntity();

        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(base.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(base.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(base.getBackFacing()).offsetZ);

        final Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, -4);

        net.minecraft.block.Block current = Structure.getBlock(base, offset);
        int oldMetadata = base.getMetaIDOffset(offset.x(), offset.y(), offset.z());
        if (current != net.minecraft.init.Blocks.air
                && (current != InsideBlock || (oldMetadata != 3 && oldMetadata != 4))) {
            return;
        }
        int metadata = active ? 3 : 4;
        if (current != InsideBlock || oldMetadata != metadata) {
            Structure.setBlock(base, offset, InsideBlock, metadata);
        }
    }

    @Override
    public String[] getInfoData() {
        return new String[] {
                "Steam Input: " + EnumChatFormatting.RED + "" + NumberFormat.getNumberInstance().format(mStoredFluids)
                        + EnumChatFormatting.RESET + " L/t",
                "Output Energy: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(mOutputSalary)
                        + EnumChatFormatting.RESET + " EU/t",
                "Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F
                        + EnumChatFormatting.YELLOW + " %",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
        };
    }

    @Override
    public int getSupportedProcessingModes() {
        return ProcessingLogic.PROTECT_FLUIDS;
    }
}
