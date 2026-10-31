package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.addon.gt.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.GregTech_API.*;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sTinyWormHoleRecipes;
import static space.impact.structures.structure.StructureUtility.*;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_TinyWormHole extends GTMTE_Impact_BlockBase<GTMTE_TinyWormHole> {

    private static final StructureRequirements HATCHES_G = StructureRequirements
            .builder(Casings.ROBUST_NAQUADAH_ALLOY, 0).build();
    private static final StructureRequirements HATCHES_J = StructureRequirements.builder(Casings.INTERMIX_CHAMBER, 0)
            .build();

    public GTMTE_TinyWormHole(int aID, String aNameRegional) {
        super(aID, "impact.multis.tinywormhole", aNameRegional);
    }

    public GTMTE_TinyWormHole(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_TinyWormHole(mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        ITexture[] sTexture;
        if (aSide == aFacing) {
            sTexture = new ITexture[] {
                    TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS,
                            Dyes.getModulation(-1, Dyes._NULL.mRGBa)),
                    TextureFactory.of(Textures.BlockIcons.OVERLAY_FUSION1) };
        } else {
            if (!aActive) {
                sTexture = new ITexture[] { TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS,
                        Dyes.getModulation(-1, Dyes._NULL.mRGBa)) };
            } else {
                sTexture = new ITexture[] { TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW,
                        Dyes.getModulation(-1, Dyes._NULL.mRGBa)) };
            }
        }
        return sTexture;
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "TinyWormHole.png");
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
        return sTinyWormHoleRecipes;
    }

    @Override
    public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
        return recipeBuilder
                .checkSizeHatches(true, true, indexBus)
                .checkVoltage()
                .checkRecipeMap(indexBus)
                .checkInputEquals(indexBus, true)
                .checkEfficiency()
                .checkConsumption()
                .checkOutputs(true)
                .build();
    }

    private static IStructureDefinition<GTMTE_TinyWormHole> structureDefinition;

    @Override
    public IStructureDefinition<GTMTE_TinyWormHole> getStructureDefinition() {
        if (structureDefinition == null) {

            structureDefinition = StructureDefinition.<GTMTE_TinyWormHole>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "FFFFF", "FAAAF", "FAAAF", "FAJAF", "FAAAF", "FAAAF", "GG~GG" },
                            { "FFFFF", "ABBBA", "ADDDA", "ADCDA", "ADDDA", "ABBBA", "GGGGG" },
                            { "FFFFF", "ABBBA", "ADCDA", "JCECJ", "ADCDA", "ABBBA", "GGGGG" },
                            { "FFFFF", "ABBBA", "ADDDA", "ADCDA", "ADDDA", "ABBBA", "GGGGG" },
                            { "FFFFF", "FAAAF", "FAAAF", "FAJAF", "FAAAF", "FAAAF", "GGGGG" }
                    })
                    .addElement('A',
                            ofBlockAnyMeta(StackUtil.getBlock(Ic2Items.reinforcedGlass)))
                    .addElement('B', Casings.FUSION_MK_II.asElement())
                    .addElement('C', Casings.INTERMIX_CHAMBER.asElement())
                    .addElement('D', Casings.DYSON_RING.asElement())
                    .addElement('E', Casings.CORE_CHAMBER.asElement())
                    .addElement('F', Casings.ROBUST_NAQUADAH_ALLOY.asElement())
                    .addElement('G', withHint(GTStructureUtility.buildHatchAdder(GTMTE_TinyWormHole.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Maintenance,
                                    HatchElement.Energy,
                                    ImpactHatchElement.Muffler, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserInput,
                                    ImpactHatchElement.LaserOutput)
                            .buildAndChain(HATCHES_G), HintColor.RED))
                    .addElement('J', withHint(GTStructureUtility.buildHatchAdder(GTMTE_TinyWormHole.class)
                            .allow(HatchElement.Input)
                            .buildAndChain(HATCHES_J), HintColor.GREEN))
                    .build();

        }
        return structureDefinition;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean check = checkPiece(2, 6, 0);

        for (GT_MetaTileEntity_Hatch_Energy mEnergyHatch : this.mEnergyHatches) {
            if (mEnergyHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_InputBus mInputBuss : this.mInputBusses) {
            if (mInputBuss.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_OutputBus mOutputBuss : this.mOutputBusses) {
            if (mOutputBuss.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Input mInputHatch : this.mInputHatches) {
            if (mInputHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Output mOutputHatch : this.mOutputHatches) {
            if (mOutputHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        if (mInputHatches.size() > 1)
            check = false;
        if (mOutputHatches.size() > 1)
            check = false;
        if (mInputBusses.size() > 1)
            check = false;
        if (mOutputBusses.size() > 1)
            check = false;
        if (mEnergyHatches.size() > 2)
            check = false;

        return check;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Tiny WORMHOLE on The Earth!!!")
                .addMachineType("Wormhole")
                .addSeparator()
                .addController("See the structure hologram")
                .beginStructureBlock(5, 7, 5, false)
                .addOtherStructurePart("Input Hatch", "(UV or better); maximum 1")
                .addOtherStructurePart("Output Bus", "(UV or better); maximum 1")
                .addOtherStructurePart("Energy Hatch", "(UV or better); maximum 2")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addCasingInfoMin("Robust Naquadah Alloy Machine Casing", 65, false)
                .addCasingInfoMin("Intermix Chamber Casing", 9, false)
                .addCasingInfoMin("Dyson Ring Casing", 20, false)
                .addCasingInfoMin("Fusion Casing MKII", 18, false)
                .addCasingInfoMin("Core Chamber Casing", 1, false)
                .addCasingInfoMin("Reinforced Glass", 56, false)

                .addStructureHint("Output Bus / Energy Hatch / Maintenance or Robust Naquadah Alloy Machine Casing", HintColor.RED)
                .addStructureHint("Input Hatch or Intermix Chamber Casing", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 6, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 6, 0, budget, environment);
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }
}
