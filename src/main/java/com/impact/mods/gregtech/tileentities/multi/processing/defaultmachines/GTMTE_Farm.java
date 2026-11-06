package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.ImpactCasings;
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
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_Farm
        extends GT_MetaTileEntity_ProcessingMultiBlockBase<GTMTE_Farm>
        implements ISurvivalConstructable {
    private static final ICasing CASING = Casings.SOLID_STEEL;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 64).build();
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
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Farm.class)
                            .allow(HatchElement.Maintenance, HatchElement.Input, HatchElement.Output,
                                    HatchElement.Energy)
                            .autoPlace(HatchElement.Maintenance, HatchElement.Input, HatchElement.Output, HatchElement.Energy)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.GREEN))
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
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Industrial Wood producer")
                .addMachineType("Wood Farm")
                .addInfo("Overclocking without loss of efficiency")
                .addInfo("Select machine type by clicking the controller with a screwdriver")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing")
                .addOtherStructurePart("Input Hatch", "Any casing")
                .addOtherStructurePart("Output Bus", "Any casing")
                .addOtherStructurePart("Output Hatch", "Any casing")
                .addRequirements(SHELL, "Bottom edge")
                .addOtherStructurePart(ImpactCasings.CASING_FARM, "At the center of the bottom layer", 9)
                .addOtherStructurePart("Reinforced Glass", "At the center of the Top layer")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureInfo("Hatches replacing casings: at most 15")
                .addSubChannel(GTStructureUtility.HATCH_CHANNEL, "Installs maintenance, energy, input and output hatches from your inventory in the farm casing.")
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
        return checkPiece("main", 2, 4, 0) && SHELL.isSatisfied(this, casingCount) && mMaintenanceHatches.size() <= 1;
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
