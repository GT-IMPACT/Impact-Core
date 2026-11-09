package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;


public class GTMTE_FreezerSolidifier extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_FreezerSolidifier> {

    private static final ICasing CASING = Casings.FROST_PROOF;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 33)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.PowerInput).build();
    private static IStructureDefinition<GTMTE_FreezerSolidifier> definition;

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 0, 2);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 0, 2, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_FreezerSolidifier> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_FreezerSolidifier>builder()
                    .addShape("main", new String[][] {
                            { " CCC ", " C C ", " C C ", " CCC " },
                            { "CCCCC", "C C C", "C C C", "CCCCC" },
                            { "CC~CC", " C C ", " C C ", "CCCCC" },
                            { "CCCCC", "C C C", "C C C", "CCCCC" },
                            { " CCC ", " C C ", " C C ", " CCC " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', GTStructureUtility.buildHatchAdder(GTMTE_FreezerSolidifier.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.Parallel, HatchElement.Output)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, ImpactHatchElement.Muffler, ImpactHatchElement.Parallel, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    public GTMTE_FreezerSolidifier(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.freezsolidifier", aNameRegional);
    }

    public GTMTE_FreezerSolidifier(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_FreezerSolidifier(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Freezer Solidifier")
                .addParallelInfo()
                .beginStructureBlock(5, 4, 5, false)
                .addController("roof center")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Energy Hatches", "1-4 total; casing positions")
                .addHatchInfo("Input Buses", "optional, up to 2; casing positions")
                .addHatchInfo("Input Hatches", "optional, up to 6; casing positions")
                .addHatchInfo("Output Bus", "optional, up to 1; casing positions")
                .addHatchInfo("Parallel Receiver", "optional, up to 1; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == 1
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE
                                : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER) }
                : new ITexture[] { CASING.getTexture() };
    }


    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sFreezerSolidficationRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;

        boolean formationChecklist = checkPiece(2, 0, 2) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 2) {
            formationChecklist = false;
        }
        if (HatchCounts.inputHatches(this) > 6) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 1) {
            formationChecklist = false;
        }
        if (HatchCounts.energyHatches(this) > 4) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }
        if (this.sParallHatchesIn.size() > 1) {
            formationChecklist = false;
        }
        if (this.sParallHatchesOut.size() != 0) {
            formationChecklist = false;
        }
        return formationChecklist;
    }
}
