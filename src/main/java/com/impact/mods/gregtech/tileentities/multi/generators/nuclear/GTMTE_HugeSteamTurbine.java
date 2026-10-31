package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
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
import java.util.ArrayList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_HugeSteamTurbine extends GTMTE_Impact_BlockBase<GTMTE_HugeSteamTurbine> {

    private static final ICasing CASING = ImpactCasings.HUGE_CASING_TURBINE;
    private static final ICasing GEARBOX = Casings.STEEL_GEARBOX;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
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
                            { "BBBBB", "CCACC", "C   C", "CCCCC" },
                            { "BBBBB", "CCCCC", "C   C", "CCCCC" },
                            { " BBB ", "CCCCC", "CCCCC", "CCCCC" },
                            { "     ", " DDD ", " DDD ", " DDD " }
                    })
                    .addElement('A', GEARBOX.asElement())
                    .addElement('B', CASING.asElement())
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_HugeSteamTurbine.class)
                            .allow(HatchElement.Input).buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('D', withHint(GTStructureUtility.buildHatchAdder(GTMTE_HugeSteamTurbine.class)
                            .allow(ImpactHatchElement.Dynamo, ImpactHatchElement.LaserOutput).buildAndChain(DYNAMOS),
                            HintColor.BLUE))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Mega steam turbine!")
                .addMachineType("Generator")
                .addInfo("The turbine operates at 100% efficiency")
                .addInfo("Accepts both steam (2L = 1 EU/t)")
                .addInfo("and superheated steam (1L = 1 EU/t)")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Dynamo Hatch", "Any casing back side; maximum 9")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 20")
                .addRequirements(SHELL, "Side casings")
                .addOtherStructurePart("Steel GearBox Casing", "inside structure")
                .addStructureHint("Casing or input hatch/bus / maintenance hatch", HintColor.GREEN)
                .addStructureHint("Casing or dynamo / laser output", HintColor.BLUE)
                .toolTipFinisher();
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Huge Steam Turbine", "MultiParallelBlockGUI.png");
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(2, 2, 0) && SHELL.isSatisfied(this, 0);

        if (mInputHatches.size() > 20) {
            formationChecklist = false;
        }

        if (mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        if (mDynamoHatches.size() > 9 || mDynamoHatchesMulti.size() > 9) {
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
    public boolean checkRecipe(ItemStack itemStack) {
        ArrayList<FluidStack> tFluids = getStoredFluids();
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        int totalFlow = 0;
        int countEU = 0;
        long outEU = 0;
        this.mStoredFluids = 0;
        int countFluid = 0;
        for (FluidStack fluidStack : tFluids) {
            String fluidName = fluidStack.getUnlocalizedName();
            if (fluidName.equals("fluid.steam") || fluidName.equals("ic2.fluidSteam")) {
                countFluid = fluidStack.amount;
                depleteInput(new FluidStack(fluidStack, countFluid));
                this.mStoredFluids += countFluid;
                totalFlow += countFluid;
                countEU = 2;
            } else if (fluidName.equals("ic2.fluidSuperheatedSteam")) {
                countFluid = fluidStack.amount;
                depleteInput(new FluidStack(fluidStack, countFluid));
                this.mStoredFluids += countFluid;
                totalFlow += countFluid;
                countEU = 1;
            }
            outEU = totalFlow / countEU;
        }

        if (tFluids.isEmpty() || totalFlow == 0) {
            return false;
        }

        outEU = mEfficiency < 10000 ? outEU / 2 : outEU;
        outEU /= 20;
        mStoredFluids /= 20;
        mOutputSalary = Math.min(outEU, getMaxOutputVoltage());

        this.mMaxProgresstime = 20;
        this.mEfficiencyIncrease = 10000;
        return true;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        super.addEnergyOutputMultipleDynamos(mOutputSalary, true);
        return super.onRunningTick(aStack);
    }

    private void rotorTopTrigger(boolean shouldExist) {
        IGregTechTileEntity base = getBaseMetaTileEntity();

        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(base.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(base.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(base.getBackFacing()).offsetZ);

        final Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, -4);

        if (base.isActive()) {
            Structure.setBlock(base, offset, InsideBlock, 3);
        } else {
            Structure.setBlock(base, offset, InsideBlock, 4);
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
    public boolean hasSeparate() {
        return false;
    }
}
