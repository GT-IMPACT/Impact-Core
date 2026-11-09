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

public class GTMTE_Extradifier extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_Extradifier> {

    private static final ICasing CASING = ImpactCasings.EXTRADIFICATION_CASING;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 31)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.PowerInput).build();
    private static IStructureDefinition<GTMTE_Extradifier> definition;

    public GTMTE_Extradifier(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.extradifier", aNameRegional);
    }

    public GTMTE_Extradifier(String aName) {
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
        return new GTMTE_Extradifier(this.mName);
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
    public IStructureDefinition<GTMTE_Extradifier> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_Extradifier>builder()
                    .addShape("main", new String[][] {
                            { " CCC ", "C   C", "C   C", "C   C", " C~C " },
                            { "CCGCC", " CGC ", " CGC ", " CGC ", "CCCCC" },
                            { "CGCGC", " G G ", " G G ", " G G ", "CCCCC" },
                            { "CCGCC", " CGC ", " CGC ", " CGC ", "CCCCC" },
                            { " CCC ", "C   C", "C   C", "C   C", " CCC " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', GTStructureUtility.buildHatchAdder(GTMTE_Extradifier.class)
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
                .addMachineType("Fluid Extractor, Fluid Solidifier, Fluid Heater")
                .addParallelInfo()
                .beginStructureBlock(5, 5, 5, false)
                .addController("front center of the bottom layer")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart("I-Glass", "exactly 16; upper four layers; any color")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Energy Hatches", "1-4 total; casing positions")
                .addHatchInfo("Input Buses", "optional, up to 6; casing positions")
                .addHatchInfo("Input Hatches", "optional, up to 6; casing positions")
                .addHatchInfo("Output Buses", "optional, up to 6; casing positions")
                .addHatchInfo("Output Hatches", "optional, up to 6; casing positions")
                .addHatchInfo("Parallel Receiver", "optional, up to 1; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }


    @NotNull
    @Override
    public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
        return Lists.newArrayList(
                GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes,
                GT_Recipe.GT_Recipe_Map.sFluidSolidficationRecipes,
                GT_Recipe.GT_Recipe_Map.sFluidHeaterRecipes);
    }

    @Override
    public boolean hasSwitchMap() {
        return true;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(2, 4, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 6) {
            formationChecklist = false;
        }
        if (HatchCounts.inputHatches(this) > 6) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 6) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 6) {
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
