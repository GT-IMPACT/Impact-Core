package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.onElementPass;

public class GTMTE_Farm
        extends GT_MetaTileEntity_ProcessingMultiBlockBase<GTMTE_Farm>
        implements ISurvivalConstructable {
    private static final ICasing CASING = Casings.SOLID_STEEL;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 64)
            .atLeast(HatchElement.Energy).build();
    private static IStructureDefinition<GTMTE_Farm> STRUCTURE;

    @Override
    public IStructureDefinition<GTMTE_Farm> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition.<GTMTE_Farm>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "CCCCC", "CCCCC", "CCCCC", "CCCCC", "AA~AA" },
                            { "CGGGC", "C---C", "C---C", "C---C", "AFFFA" },
                            { "CGGGC", "C---C", "C---C", "C---C", "AFFFA" },
                            { "CGGGC", "C---C", "C---C", "C---C", "AFFFA" },
                            { "CCCCC", "CCCCC", "CCCCC", "CCCCC", "AAAAA" }
                    })
                    .addElement('A', GTStructureUtility.buildHatchAdder(GTMTE_Farm.class)
                            .allow(HatchElement.Maintenance, HatchElement.Input, HatchElement.Output,
                                    HatchElement.Energy)
                            .autoPlace(HatchElement.Maintenance, HatchElement.Input, HatchElement.Output, HatchElement.Energy)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .addElement('C', onElementPass(machine -> machine.casingCount++, CASING.asElement()))
                    .addElement('G', ofBlockAnyMeta(StackUtil.getBlock(Ic2Items.reinforcedGlass)))
                    .addElement('F', ImpactCasings.CASING_FARM.asElement())
                    .build();
        }
        return STRUCTURE;
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 2, 4, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 4, 0, budget, environment);
    }

    private GT_Recipe cashedRecipe = null;

    public GTMTE_Farm(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.farm", aNameRegional);

    }

    public GTMTE_Farm(String aName) {
        super(aName);

    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Farm(mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Wood Farm")
                .addInfo("Overclock: 4x EU/t, 1/4 recipe time; minimum 2 ticks.")
                .beginStructureBlock(5, 5, 5, false)
                .addController("front center of the bottom layer")
                .addStructureSection("Components")
                .addOtherStructurePart(SHELL.getCasing(), "64+; roof frame and walls; fill unused bottom-edge positions")
                .addOtherStructurePart(ImpactCasings.CASING_FARM, "exactly 9; center of the bottom layer")
                .addOtherStructurePart("Reinforced Glass", "exactly 9; roof center")
                .addAir(27, "interior, above the farm casings")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "optional, up to 1; bottom edge")
                .addHatchInfo("Energy Hatches", "1-4 total; bottom edge")
                .addHatchInfo("Input Buses", "optional, up to 3; bottom edge")
                .addHatchInfo("Input Hatches", "optional, up to 3; bottom edge")
                .addHatchInfo("Output Buses", "optional, up to 3; bottom edge")
                .addStructureHint("Bottom edge: casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
            byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] { CASING.getTexture(), TextureFactory.of(
                    aActive ? Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE
                            : Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE) };
        }
        return new ITexture[] { CASING.getTexture() };
    }


    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sFarmRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic()
                .setRecipeMapSupplier(this::getRecipeMap)
                .setOverclock(4, 4)
                .setDurationAfterOverclock(duration -> Math.max(2, duration));
    }

    public boolean checkMachine(IGregTechTileEntity controller, ItemStack stack) {
        casingCount = 0;
        return checkPiece("main", 2, 4, 0)
                && SHELL.isSatisfied(this, casingCount)
                && mMaintenanceHatches.size() <= 1
                && HatchCounts.energyHatches(this) <= 4
                && HatchCounts.inputBuses(this) <= 3
                && HatchCounts.inputHatches(this) <= 3
                && mOutputBusses.size() <= 3;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

}
