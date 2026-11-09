package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.google.common.collect.Lists;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
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
import java.util.List;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_Cutting extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_Cutting> {

    private static final ICasing CASING = ImpactCasings.CUTTING_CASING;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 22)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.PowerInput).build();
    private static IStructureDefinition<GTMTE_Cutting> definition;

    public GTMTE_Cutting(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.cutting", aNameRegional);
    }

    public GTMTE_Cutting(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Cutting(this.mName);
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
    public IStructureDefinition<GTMTE_Cutting> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_Cutting>builder()
                    .addShape("main", new String[][] {
                            { "  CGC", "CCCGC", "CC~GC", "CCCCC" },
                            { "  CGC", "CCC C", "CCC C", "CCCCC" },
                            { "  CGC", "CCCGC", "CCCGC", "CCCCC" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', GTStructureUtility.buildHatchAdder(GTMTE_Cutting.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.Parallel, HatchElement.Output)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, ImpactHatchElement.Muffler, ImpactHatchElement.Parallel, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Cutting Machine, Saw Mill")
                .addParallelInfo()
                .beginStructureBlock(5, 4, 3, false)
                .addController("front center, layer 2 from the bottom")
                .addStructureSection("Components")
                .addOtherStructurePart(SHELL.getCasing(), "22+; fill unused casing positions")
                .addOtherStructurePart("I-Glass", "exactly 7; layers 2-4; any color")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Energy Hatches", "1-4 total; casing positions")
                .addHatchInfo("Input Buses", "optional, up to 6; casing positions")
                .addHatchInfo("Input Hatches", "optional, up to 3; casing positions")
                .addHatchInfo("Output Buses", "optional, up to 3; casing positions")
                .addHatchInfo("Parallel Receiver", "optional, up to 1; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }


    @NotNull
    @Override
    public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
        return Lists.newArrayList(
                GT_Recipe.GT_Recipe_Map.sSawMill0,
                GT_Recipe.GT_Recipe_Map.sSawMill1,
                GT_Recipe.GT_Recipe_Map.sSawMill2,
                GT_Recipe.GT_Recipe_Map.sCutterRecipes);
    }

    @Override
    public boolean hasSwitchMap() {
        return true;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMapForNEI() {
        GT_Recipe.GT_Recipe_Map map = getRecipeMap();
        if (map == null || map == GT_Recipe.GT_Recipe_Map.sCutterRecipes) {
            return map;
        }
        return GT_Recipe.GT_Recipe_Map.sSawMillVisual;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(2, 2, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 6) {
            formationChecklist = false;
        }
        if (HatchCounts.inputHatches(this) > 3) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 3) {
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
