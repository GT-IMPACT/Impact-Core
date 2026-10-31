package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.google.common.collect.Lists;
import com.impact.addon.gt.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.HatchElement;
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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.SawMillBlock;
import static com.impact.mods.gregtech.enums.Texture.Icons.*;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_SawMill extends GTMTE_Impact_BlockBase<GTMTE_SawMill> {

    private static final ICasing CASING = ImpactCasings.SAW_CASING;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
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
                    .addElement('D', withHint(GTStructureUtility.buildHatchAdder(GTMTE_SawMill.class)
                            .allow(HatchElement.Input, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                    HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Nooo! Do not saw me..")
                .addMachineType("Saw Mill")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 1")
                .addCasing("", CASING, false)
                .addOtherStructurePart("Saw Mill Conveyor", "Bottom middle")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
    }

    @Override
    public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
        boolean checkRecipe = recipeBuilder
                .checkSizeHatches(true, true, indexBus)
                .checkVoltage()
                .checkRecipeMap(indexBus)
                .checkInputEquals(indexBus, false)
                .checkEfficiency()
                .checkConsumption()
                .checkOutputs(true)
                .build();

        if (!checkRecipe) {
            return false;
        }
        mEUt /= 4;
        mMaxProgresstime *= 2;
        return true;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean formationChecklist = checkPiece(0, 1, 0) && SHELL.isSatisfied(this, 0);

        if (this.mInputBusses.size() > 1) {
            formationChecklist = false;
        }
        if (this.mInputHatches.size() > 1) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 2) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() != 1) {
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
    public boolean hasSwitchMap() {
        return true;
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }
}
