package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.google.common.collect.Lists;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.HatchElement;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.List;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.SawMillBlock;
import static com.impact.mods.gregtech.enums.Texture.Icons.SAW;
import static com.impact.mods.gregtech.enums.Texture.Icons.SAW_ACTIVE;
import static com.impact.mods.gregtech.enums.Texture.Icons.SAW_FRONT;
import static com.impact.mods.gregtech.enums.Texture.Icons.SAW_FRONT_ACTIVE;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_SawMill extends GTMTE_Impact_BlockBase<GTMTE_SawMill> {

    private static final ICasing CASING = ImpactCasings.SAW_CASING;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 10)
            .atLeast(ImpactHatchElement.RegularEnergy).build();
    private static IStructureDefinition<GTMTE_SawMill> definition;

    public GTMTE_SawMill(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.sawmill", aNameRegional);
        enabledMaintenance = false;
    }

    public GTMTE_SawMill(String aName) {
        super(aName);
        enabledMaintenance = false;
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aBaseMetaTileEntity.getBackFacing()
                ? new ITexture[] { CASING.getTexture(), TextureFactory.of(aActive ? SAW_ACTIVE : SAW) }
                : aSide == aFacing
                        ? new ITexture[] { CASING.getTexture(),
                                TextureFactory.of(aActive ? SAW_FRONT_ACTIVE : SAW_FRONT) }
                        : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_SawMill(this.mName);
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 0, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 0, 1, 0, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_SawMill> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_SawMill>builder()
                    .addShape("main", new String[][] {
                            { "D D", "~ D", "DCD" },
                            { "DDD", "   ", " C " },
                            { "D D", "   ", " C " },
                            { "DDD", "   ", " C " },
                            { "D D", "D D", "DCD" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', ofBlockAnyMeta(SawMillBlock))
                    .addElement('D', GTStructureUtility.buildHatchAdder(GTMTE_SawMill.class)
                            .allow(HatchElement.Input, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                    HatchElement.Output)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Saw Mill")
                .addInfo("After overclocking: 1/4 EU/t, 2x recipe time.")
                .beginStructureBlock(3, 3, 5, false)
                .addController("front left edge, middle layer")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(new ItemStack(SawMillBlock, 1, 0), "exactly 5; center row of the bottom layer")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Energy Hatch", "exactly 1; casing positions; standard hatch only")
                .addHatchInfo("Input Bus", "optional, up to 1; casing positions")
                .addHatchInfo("Input Hatch", "optional, up to 1; casing positions")
                .addHatchInfo("Output Buses", "optional, up to 2; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }


    @Override
    protected ProcessingLogic createProcessingLogic() {
        return super.createProcessingLogic()
                .setEUtAfterOverclock(eut -> eut / 4)
                .setDurationAfterOverclock(duration -> Math.multiplyExact(duration, 2));
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;
        boolean formationChecklist = checkPiece(0, 1, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 1) {
            formationChecklist = false;
        }
        if (HatchCounts.inputHatches(this) > 1) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 2) {
            formationChecklist = false;
        }
        if (HatchCounts.energyHatches(this) != 1) {
            formationChecklist = false;
        }
        return formationChecklist;
    }

    @NotNull
    @Override
    public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
        return Lists.newArrayList(
                GT_Recipe.GT_Recipe_Map.sSawMill0,
                GT_Recipe.GT_Recipe_Map.sSawMill1,
                GT_Recipe.GT_Recipe_Map.sSawMill2);
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMapForNEI() {
        return getRecipeMap() == null ? null : GT_Recipe.GT_Recipe_Map.sSawMillVisual;
    }

    @Override
    public boolean hasSwitchMap() {
        return true;
    }

    @Override
    public int getSupportedProcessingModes() {
        return super.getSupportedProcessingModes() & ~ProcessingLogic.SEPARATE_INPUTS;
    }
}
