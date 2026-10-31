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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.GregTech_API.*;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sTesseractRecipes;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_Tesseract extends GTMTE_Impact_BlockBase<GTMTE_Tesseract> {

    private static final StructureRequirements HATCHES_2 = StructureRequirements.builder(Casings.INTERMIX_CHAMBER, 0)
            .build();
    private static final StructureRequirements HATCHES_7 = StructureRequirements
            .builder(Casings.ROBUST_NAQUADAH_ALLOY, 0).build();
    private static final StructureRequirements HATCHES_8 = StructureRequirements
            .builder(Casings.ROBUST_NAQUADAH_ALLOY, 0).build();

    public GTMTE_Tesseract(int aID, String aNameRegional) {
        super(aID, "impact.multis.tesseract", aNameRegional);
    }

    public GTMTE_Tesseract(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Tesseract(mName);
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
                "MultiblockDisplay.png");
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

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return sTesseractRecipes;
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

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Small WORMHOLE on The Earth!!!")
                .addMachineType("Wormhole")
                .addSeparator()
                .addController("See the structure hologram")
                .beginStructureBlock(9, 9, 9, false)
                .addOtherStructurePart("Input Hatch", "(UHV or better); maximum 1")
                .addOtherStructurePart("Output Hatch", "(UHV or better); maximum 1")
                .addOtherStructurePart("Input Bus", "(UHV or better); maximum 1")
                .addOtherStructurePart("Output Bus", "(UHV or better); maximum 1")
                .addOtherStructurePart("Energy Hatch", "(UHV or better); maximum 2")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addCasingInfoMin("Robust Naquadah Alloy Machine Casing", 95, false)
                .addCasingInfoMin("Intermix Chamber Casing", 23, false)
                .addCasingInfoMin("Dyson Ring Casing", 24, false)
                .addCasingInfoMin("Fusion Casing MKII", 4, false)
                .addCasingInfoMin("Fusion Coil Block", 1, false)
                .addCasingInfoMin("Core Chamber Casing", 16, false)

                .addStructureHint("Input Bus / Input Hatch or Intermix Chamber Casing", HintColor.RED)
                .addStructureHint("Output Hatch / Output Bus / Maintenance Hatch or Robust Naquadah Alloy Machine Casing", HintColor.GREEN)
                .addStructureHint("Energy Hatch or Robust Naquadah Alloy Machine Casing", HintColor.YELLOW)
                .toolTipFinisher();
    }

    private static IStructureDefinition<GTMTE_Tesseract> STRUCTURE;

    @Override
    public IStructureDefinition<GTMTE_Tesseract> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition
                    .<GTMTE_Tesseract>builder()
                    .addShape("main", new String[][] {
                            { "000000000", "0       0", "0       0", "0       0", "0       0", "0       0", "0       0", "0       0", "000080000" },
                            { "0       0", " 1     1 ", "         ", "         ", "         ", "         ", "         ", " 1     1 ", "0       0" },
                            { "0       0", "         ", "  1   1  ", "    3    ", "   333   ", "    3    ", "  1   1  ", "         ", "0       0" },
                            { "0       0", "         ", "    2    ", "   141   ", "  34443  ", "   141   ", "    3    ", "    6    ", "0  7~7  0" },
                            { "0       0", "         ", "   232   ", "  34 43  ", "  34 43  ", "  34 43  ", "   333   ", "   656   ", "8  777  8" },
                            { "0       0", "         ", "    2    ", "   141   ", "  34443  ", "   141   ", "    3    ", "    6    ", "0  777  0" },
                            { "0       0", "         ", "  1   1  ", "    3    ", "   333   ", "    3    ", "  1   1  ", "         ", "0       0" },
                            { "0       0", " 1     1 ", "         ", "         ", "         ", "         ", "         ", " 1     1 ", "0       0" },
                            { "000000000", "0       0", "0       0", "0       0", "0       0", "0       0", "0       0", "0       0", "000080000" }
                    })
                    .addElement('0', Casings.ROBUST_NAQUADAH_ALLOY.asElement())
                    .addElement('1', Casings.DYSON_RING.asElement())
                    .addElement('2', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Tesseract.class)
                            .allow(HatchElement.Input)
                            .buildAndChain(HATCHES_2), HintColor.RED))
                    .addElement('3', Casings.INTERMIX_CHAMBER.asElement())
                    .addElement('4', Casings.CORE_CHAMBER.asElement())
                    .addElement('5', Casings.FUSION_COIL.asElement())
                    .addElement('6', Casings.FUSION_MK_II.asElement())
                    .addElement('7', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Tesseract.class)
                            .allow(HatchElement.Output, HatchElement.Maintenance)
                            .buildAndChain(HATCHES_7), HintColor.GREEN))
                    .addElement('8', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Tesseract.class)
                            .allow(HatchElement.Energy, ImpactHatchElement.LaserInput)
                            .buildAndChain(HATCHES_8), HintColor.YELLOW))
                    .build();
        }
        return STRUCTURE;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        boolean check = checkPiece(4, 8, 3);

        for (GT_MetaTileEntity_Hatch_Energy mEnergyHatch : this.mEnergyHatches) {
            if (mEnergyHatch.mTier < 9) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_InputBus mInputBuss : this.mInputBusses) {
            if (mInputBuss.mTier < 9) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_OutputBus mOutputBuss : this.mOutputBusses) {
            if (mOutputBuss.mTier < 9) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Input mInputHatch : this.mInputHatches) {
            if (mInputHatch.mTier < 9) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Output mOutputHatch : this.mOutputHatches) {
            if (mOutputHatch.mTier < 9) {
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
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 4, 8, 3);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 4, 8, 3, budget, environment);
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }
}
