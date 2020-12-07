package com.impact.mods.NEI.ImpactPlugin;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.impact.recipes.RegisterOreVein;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.nei.GT_NEI_DefaultHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class NEI_Impact_OreVeinHelper extends NEI_Impact_Default {

    int xPos = 4 + 1;
    int yPos = 8 + 3;

    public NEI_Impact_OreVeinHelper(GT_Recipe.GT_Recipe_Map aRecipeMap) {
        super(aRecipeMap);
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(81 - xPos, 40 - yPos, 42, 8), getOverlayIdentifier()));
    }

    public TemplateRecipeHandler newInstance() {
        return new NEI_Impact_OreVeinHelper(this.mRecipeMap);
    }

    public String getGuiTexture() {
        return "gregtech:textures/gui/basic/Default.png";
    }//todo

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getOverlayIdentifier())) {
            for (GT_Recipe tRecipe : getSortedRecipes()) {
                if (!tRecipe.mHidden) {
                    this.arecipes.add(new CachedDefaultRecipe(tRecipe));
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack aResult) {
        ItemData tPrefixMaterial = GT_OreDictUnificator.getAssociation(aResult);

        ArrayList<ItemStack> tResults = new ArrayList();
        tResults.add(aResult);
        tResults.add(GT_OreDictUnificator.get(true, aResult));
        if ((tPrefixMaterial != null) && (!tPrefixMaterial.mBlackListed) && (!tPrefixMaterial.mPrefix.mFamiliarPrefixes.isEmpty())) {
            for (OrePrefixes tPrefix : tPrefixMaterial.mPrefix.mFamiliarPrefixes) {
                tResults.add(GT_OreDictUnificator.get(tPrefix, tPrefixMaterial.mMaterial.mMaterial, 1L));
            }
        }
        FluidStack tFluid = GT_Utility.getFluidForFilledItem(aResult, true);
        if (tFluid != null) {
            tResults.add(GT_Utility.getFluidDisplayStack(tFluid, false));
            for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                if (tData.fluid.isFluidEqual(tFluid)) {
                    tResults.add(GT_Utility.copy(tData.filledContainer));
                }
            }
        }
        for (GT_Recipe tRecipe : getSortedRecipes()) {
            if (!tRecipe.mHidden) {
                CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(tRecipe);
                for (ItemStack tStack : tResults) {
                    if (tNEIRecipe.contains(tNEIRecipe.mOutputs, tStack)) {
                        this.arecipes.add(tNEIRecipe);
                        break;
                    }
                }
            }
        }
    }

    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip, int aRecipeIndex) {
        CachedRecipe tObject = this.arecipes.get(aRecipeIndex);
        if ((tObject instanceof CachedDefaultRecipe)) {
            CachedDefaultRecipe tRecipe = (CachedDefaultRecipe) tObject;
            for (PositionedStack tStack : tRecipe.mOutputs) {
                if (aStack == tStack.item) {
                    if ((!(tStack instanceof FixedPositionedStack)) || (((FixedPositionedStack) tStack).mChance <= 0) || (((FixedPositionedStack) tStack).mChance == 10000)) {
                        break;
                    }
                    currenttip.add(trans("150", "Chance: ") + ((FixedPositionedStack) tStack).mChance / 100 + "." + (((FixedPositionedStack) tStack).mChance % 100 < 10 ? "0" + ((FixedPositionedStack) tStack).mChance % 100 : Integer.valueOf(((FixedPositionedStack) tStack).mChance % 100)) + "%");
                    break;
                }
            }
            for (PositionedStack tStack : tRecipe.mInputs) {
                if (aStack == tStack.item) {
                    if ((gregtech.api.enums.ItemList.Display_Fluid.isStackEqual(tStack.item, true, true)) ||
                            (tStack.item.stackSize != 0)) {
                        break;
                    }
                    currenttip.add(trans("151", "Does not get consumed in the process"));
                    break;
                }
            }
        }
        return currenttip;
    }

    public void drawExtras(int aRecipeIndex) {
        String[] recipeDesc = ((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe.getNeiDesc();
        String biomeName = RegisterOreVein.mAllBiomes[((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe.mSpecialValue].biomeName;
        if (recipeDesc == null) {
            drawText(10, 83,  "Biome Name: " + biomeName, -16777216);
        } else {
            int i = 0;
            for (String descLine : recipeDesc) {
                drawText(10, 73 + 10 * i, descLine, -16777216);
                i++;
            }
        }
    }

    public void loadUsageRecipes(ItemStack aInput) {
        ItemData tPrefixMaterial = GT_OreDictUnificator.getAssociation(aInput);

        ArrayList<ItemStack> tInputs = new ArrayList();
        tInputs.add(aInput);
        tInputs.add(GT_OreDictUnificator.get(false, aInput));
        if ((tPrefixMaterial != null) && (!tPrefixMaterial.mPrefix.mFamiliarPrefixes.isEmpty())) {
            for (OrePrefixes tPrefix : tPrefixMaterial.mPrefix.mFamiliarPrefixes) {
                tInputs.add(GT_OreDictUnificator.get(tPrefix, tPrefixMaterial.mMaterial.mMaterial, 1L));
            }
        }
        FluidStack tFluid = GT_Utility.getFluidForFilledItem(aInput, true);
        if (tFluid != null) {
            tInputs.add(GT_Utility.getFluidDisplayStack(tFluid, false));
            for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                if (tData.fluid.isFluidEqual(tFluid)) {
                    tInputs.add(GT_Utility.copy(tData.filledContainer));
                }
            }
        }
        for (GT_Recipe tRecipe : getSortedRecipes()) {
            if (!tRecipe.mHidden) {
                CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(tRecipe);
                for (ItemStack tStack : tInputs) {
                    if (tNEIRecipe.contains(tNEIRecipe.mInputs, tStack)) {
                        this.arecipes.add(tNEIRecipe);
                        break;
                    }
                }
            }
        }
    }

    public class CachedDefaultRecipe extends CachedRecipe {

        public final GT_Recipe mRecipe;
        public final List<PositionedStack> mOutputs;
        public final List<PositionedStack> mInputs;

        public CachedDefaultRecipe(GT_Recipe aRecipe) {
            super();
            this.mRecipe = aRecipe;

            if (aRecipe.getInputPositionedStacks() != null && aRecipe.getOutputPositionedStacks() != null) {
                this.mInputs = aRecipe.getInputPositionedStacks();
                this.mOutputs = aRecipe.getOutputPositionedStacks();
                return;
            }
            this.mOutputs = new ArrayList<>();
            this.mInputs = new ArrayList<>();

            int tStartIndex = 0;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 12, -4));
            }
            tStartIndex++;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 30, -4));
            }
            tStartIndex++;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 48, -4));
            }
            tStartIndex++;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 12, 14));
            }
            tStartIndex++;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 30, 14));
            }
            tStartIndex++;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 48, 14));
            }
            tStartIndex++;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 12, 32));
            }
            tStartIndex++;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 30, 32));
            }
            tStartIndex++;
            if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
                this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 48, 32));
            }

            tStartIndex = 0;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 102, -4, aRecipe.getOutputChance(tStartIndex)));
            }
            tStartIndex++;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 120, -4, aRecipe.getOutputChance(tStartIndex)));
            }
            tStartIndex++;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 138, -4, aRecipe.getOutputChance(tStartIndex)));
            }
            tStartIndex++;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 102, 14, aRecipe.getOutputChance(tStartIndex)));
            }
            tStartIndex++;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 120, 14, aRecipe.getOutputChance(tStartIndex)));
            }
            tStartIndex++;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 138, 14, aRecipe.getOutputChance(tStartIndex)));
            }
            tStartIndex++;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 102, 32, aRecipe.getOutputChance(tStartIndex)));
            }
            tStartIndex++;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 120, 32, aRecipe.getOutputChance(tStartIndex)));
            }
            tStartIndex++;
            if (aRecipe.getOutput(tStartIndex) != null) {
                this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(tStartIndex), 138, 32, aRecipe.getOutputChance(tStartIndex)));
            }

        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 10, this.mInputs);
        }

        public PositionedStack getResult() {
            return null;
        }

        public List<PositionedStack> getOtherStacks() {
            return this.mOutputs;
        }
    }

}
