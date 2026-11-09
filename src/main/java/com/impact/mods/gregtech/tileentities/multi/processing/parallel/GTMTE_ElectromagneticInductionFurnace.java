package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static space.impact.structures.structure.StructureUtility.ofBlock;

public class GTMTE_ElectromagneticInductionFurnace
        extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_ElectromagneticInductionFurnace> {

    private static final ICasing CASING = Casings.HEAT_PROOF;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 24)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler, ImpactHatchElement.PowerInput).build();
    private static IStructureDefinition<GTMTE_ElectromagneticInductionFurnace> definition;

    public GTMTE_ElectromagneticInductionFurnace(int aID, String aNameRegional) {
        super(aID, "impact.multis.eif", aNameRegional);
    }

    public GTMTE_ElectromagneticInductionFurnace(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE
                                : Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_ElectromagneticInductionFurnace(this.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 4, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 4, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_ElectromagneticInductionFurnace> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_ElectromagneticInductionFurnace>builder()
                    .addShape("main", new String[][] {
                            { " BBB ", "  B  ", "  B  ", "  B  ", " B~B " },
                            { "BBBBB", " CCC ", " DDD ", " CCC ", "BBBBB" },
                            { "BBBBB", "BCCCB", "BDDDB", "BCCCB", "BBBBB" },
                            { "BBBBB", " CCC ", " DDD ", " CCC ", "BBBBB" },
                            { " BBB ", "  B  ", "  B  ", "  B  ", " BBB " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', Casings.MAGNETIC_COIL.asElement())
                    .addElement('D', ofBlock(InsideBlock, 5))
                    .addElement('B',
                            GTStructureUtility.buildHatchAdder(GTMTE_ElectromagneticInductionFurnace.class)
                                    .allow(HatchElement.Input, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                            ImpactHatchElement.Parallel, HatchElement.Output)
                                    .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, ImpactHatchElement.Parallel, HatchElement.Output)
                                    .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Electric Blast Furnace")
                .addParallelInfo()
                .addInfo("Supports recipes at any required temperature.")
                .addInfo("Pollution: %s per second before muffler reduction.", getPollutionPerTick(null) * 20)
                .beginStructureBlock(5, 5, 5, false)
                .addController("front center of the bottom layer")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(Casings.MAGNETIC_COIL, "exactly 18; center of layers 2 and 4")
                .addOtherStructurePart(new ItemStack(InsideBlock, 1, 5), "exactly 9; center of layer 3")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance and Muffler Hatches", "exactly 1 each; casing positions")
                .addHatchInfo("Energy Hatches", "1-4 total; casing positions")
                .addHatchInfo("Input Buses", "optional, up to 8; casing positions")
                .addHatchInfo("Input Hatches", "optional, up to 8; casing positions")
                .addHatchInfo("Output Buses", "optional, up to 3; casing positions")
                .addHatchInfo("Output Hatches", "optional, up to 3; casing positions")
                .addHatchInfo("Parallel Receiver", "optional, up to 1; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }


    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe_Map.sBlastRecipes;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(2, 4, 0) && SHELL.isSatisfied(this, casingCount);

        if (this.mMufflerHatches.size() != 1) {
            formationChecklist = false;
        }
        if (HatchCounts.inputBuses(this) > 8) {
            formationChecklist = false;
        }
        if (HatchCounts.inputHatches(this) > 8) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 3) {
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

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 20;
    }
}
