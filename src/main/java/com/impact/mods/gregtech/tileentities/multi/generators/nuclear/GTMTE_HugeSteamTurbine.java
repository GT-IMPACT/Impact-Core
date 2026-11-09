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
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.text.NumberFormat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static space.impact.structures.structure.StructureUtility.onElementPass;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofChain;
import static space.impact.structures.structure.StructureUtility.isAir;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_HugeSteamTurbine extends GTMTE_Impact_BlockBase<GTMTE_HugeSteamTurbine> {

    private static final ICasing CASING = ImpactCasings.HUGE_CASING_TURBINE;
    private static final ICasing GEARBOX = Casings.STEEL_GEARBOX;
    private int casingCount;

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
                            .allow(HatchElement.InputHatch)
                            .autoPlace(HatchElement.InputHatch).buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.BLUE))
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
                .addInfo("At full efficiency: 2 L of steam or 1 L of superheated steam per EU.")
                .addInfo("Output is halved below full efficiency.")
                .addInfo("Steam above the dynamo output limit is consumed.")
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
                .addStructureHint("Main body: casings or input/maintenance hatches", HintColor.BLUE)
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
    protected ProcessingLogic createProcessingLogic() {
        return new WorldProcessingLogic(request -> {
            ProcessingInputs inputs = request.inputs.get(0).copy();
            long flow = 0;
            int divisor = 0;
            for (FluidStack fluid : inputs.fluids) {
                String name = fluid.getUnlocalizedName();
                if (name.equals("fluid.steam") || name.equals("ic2.fluidSteam")) {
                    divisor = 2;
                } else if (name.equals("ic2.fluidSuperheatedSteam")) {
                    divisor = 1;
                } else {
                    continue;
                }
                flow = Math.addExact(flow, fluid.amount);
                fluid.amount = 0;
            }
            if (flow == 0) {
                return WorldProcessingLogic.Operation.failure(CheckRecipeResult.NO_RECIPE);
            }
            long generated = flow / divisor;
            if (request.efficiency < 10000) {
                generated /= 2;
            }
            long output = Math.min(generated / 20, getMaxOutputVoltage());
            int stored = Math.toIntExact(flow / 20);
            return new WorldProcessingLogic.Operation(
                    0,
                    20,
                    new ItemStack[0],
                    new FluidStack[0],
                    () -> {
                        if (!inputs.commit()) {
                            return false;
                        }
                        mStoredFluids = stored;
                        mOutputSalary = output;
                        return true;
                    }
            );
        });
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        super.addEnergyOutputMultipleDynamos(mOutputSalary, true);
        return super.onRunningTick(aStack);
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
                "Void Steam: " + EnumChatFormatting.RED + (int) Math.abs(mStoredFluids - mOutputSalary)
                        + EnumChatFormatting.RESET + " L/t",
                "Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F
                        + EnumChatFormatting.YELLOW + " %",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
        };
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }
}
