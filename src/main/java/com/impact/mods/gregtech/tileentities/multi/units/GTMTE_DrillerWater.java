package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.BiomeDictionary;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.*;
import static gregtech.api.GregTech_API.*;
import static gregtech.api.enums.GT_Values.VN;
import static net.minecraftforge.common.BiomeDictionary.Type.OCEAN;
import static space.impact.structures.structure.StructureUtility.*;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_DrillerWater extends GTMTE_DrillerBase<GTMTE_DrillerWater> {
    private static final ICasing CASING = Casings.SOLID_STEEL;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.RegularEnergy, ImpactHatchElement.FluidOutput)
            .build();
    private static IStructureDefinition<GTMTE_DrillerWater> STRUCTURE;

    @Override
    public IStructureDefinition<GTMTE_DrillerWater> getStructureDefinition() {
        if (STRUCTURE == null) {
            int frameId = 4096 + Materials.Steel.mMetaItemSubID;
            int frameMeta = METATILEENTITIES[frameId] == null ? 32767
                    : METATILEENTITIES[frameId].getTileEntityBaseType();
            STRUCTURE = StructureDefinition.<GTMTE_DrillerWater>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "     ", "     ", "     ", "     ", "     ", "     ", "CC~CC", "F   F", "F   F" },
                            { "     ", "     ", "     ", " CCC ", " F F ", " F F ", "CCCCC", "     ", "     " },
                            { "  F  ", "  F  ", "  F  ", " CCC ", "     ", "     ", "CC CC", "     ", "     " },
                            { "     ", "     ", "     ", " CCC ", " F F ", " F F ", "CCCCC", "     ", "     " },
                            { "     ", "     ", "     ", "     ", "     ", "     ", "CCCCC", "F   F", "F   F" }
                    })
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_DrillerWater.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Energy,
                                    ImpactHatchElement.Muffler)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('F',
                            ofChain(GTStructureUtility.ofFrame(Materials.Steel), ofBlock(sBlockMachines, frameMeta)))
                    .build();
        }
        return STRUCTURE;
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 2, 6, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 6, 0, budget, environment);
    }

    public GTMTE_DrillerWater(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.waterdrill", aNameRegional);
    }

    public GTMTE_DrillerWater(String aName) {
        super(aName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Drilling water from Bedrock")
                .addMachineType("Water Pump")
                .addInfo("Energy Hatch and Output Hatch must be of the same Tier")
                .addInfo("When drilling it is necessary to consider the Biome Coefficient and tier Hatches")
                .addInfo("Drilling Salt Water in Ocean")
                .addSeparator()
                .addControlInfo("Biome Coefficient:")
                .addControlInfo("   River - 1000 L/s")
                .addControlInfo("   Taiga - 175 L/s")
                .addControlInfo("   Jungle - 350 L/s")
                .addControlInfo("   Swampland - 800 L/s")
                .addControlInfo("   Snow, Iceland - 300 L/s")
                .addControlInfo("   Beach - 170 L/s")
                .addControlInfo("   Plans, Forest - 250 L/s")
                .addControlInfo("   Hills, Mountains, Savana, Desert, Mesa - 100 L/s")
                .addControlInfo("   Ocean (Salt Water) - 20 L/s")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 4")
                .addRequirements(SHELL, "Any casing position")
                .addOtherStructurePart("Steel Frame Box", "Any casing")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_DrillerWater(mName);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "DrillingRig.png");
    }

    @Override
    protected boolean checkHatches() {
        return !mMaintenanceHatches.isEmpty() && !mOutputHatches.isEmpty() && !mEnergyHatches.isEmpty();
    }

    @Override
    protected void setElectricityStats() {
        this.mEfficiencyIncrease = 10000;
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        int tier = Math.max(0, getTierEnergyHatch());
        this.mEUt = -7 << (tier << 1);// (1/4) A of current tier when at bottom (7/8) A of current tier while mining
        this.mMaxProgresstime = 20;
    }

    @Override
    protected boolean workingAtBottom(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe,
            int yHead, int oldYHead) {
        switch (tryLowerPipeState(true)) {
        case 0:
            workState = STATE_DOWNWARD;
            if (this.mEfficiency > 0) {
                setElectricityStats();
            }
            return true;
        case 3:
            workState = STATE_UPWARD;
            return true;
        }

        if (reachingVoidOrBedrock()) {
            if (BiomeDictionary.isBiomeOfType(getBaseMetaTileEntity().getBiome(), OCEAN)) {
                addOutput(Materials.SaltWater.getFluid(getWaterOutputPerTick() * 20));
            } else {
                addOutput(GT_ModHandler.getWater(getWaterOutputPerTick() * 20));
            }
        }
        if (mEfficiency == 10000) {
            workState = STATE_AT_BOTTOM;
        } else {
            workState = STATE_UPWARD;
        }
        return true;
    }

    @Override
    protected ICasing getCasing() {
        return CASING;
    }

    @Override
    protected Materials getFrameMaterial() {
        return Materials.Steel;
    }

    @Override
    protected int getMinTier() {
        return 1;
    }

    @Override
    protected int getWaterInBiomes() {
        return BiomeDictionary.isBiomeOfType(getBaseMetaTileEntity().getBiome(), OCEAN) ? 20 : super.getWaterInBiomes();
    }

    private long getWaterOutputPerTick() {
        return (1L << Math.max(0, getTierEnergyHatch())) * 2L * getWaterInBiomes() * 2;
    }

    @Override
    public String[] getInfoData() {
        EnumChatFormatting GREEN = EnumChatFormatting.GREEN;
        EnumChatFormatting RESET = EnumChatFormatting.RESET;
        EnumChatFormatting YELLOW = EnumChatFormatting.YELLOW;
        EnumChatFormatting RED = EnumChatFormatting.RED;
        return new String[] {
                "Water Drilling: " + YELLOW + getWaterOutputPerTick() + RESET + " L/t",
                "Biome Coefficient: " + GREEN + getWaterInBiomes() + RESET + " L/t",
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t " + YELLOW
                        + VN[GT_Utility.getTier(getMaxInputVoltage())] + RESET,
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
        };
    }
}
