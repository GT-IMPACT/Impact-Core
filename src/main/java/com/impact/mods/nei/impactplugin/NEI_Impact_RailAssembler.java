package com.impact.mods.nei.impactplugin;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class NEI_Impact_RailAssembler extends NEI_Impact_Default {

  int xPos = 4 + 1;
  int yPos = 8 + 3;

  public NEI_Impact_RailAssembler(GT_Recipe.GT_Recipe_Map aRecipeMap) {
    super(aRecipeMap);
    this.transferRects.add(
        new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(81 - xPos, 40 - yPos, 42, 8),
            getOverlayIdentifier()));
  }

  public TemplateRecipeHandler newInstance() {
    return new NEI_Impact_RailAssembler(this.mRecipeMap);
  }

  public String getGuiTexture() {
    return "gregtech:textures/gui/basic/RailAssembler.png";
  }

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
    if ((tPrefixMaterial != null) && (!tPrefixMaterial.mBlackListed)
        && (!tPrefixMaterial.mPrefix.mFamiliarPrefixes.isEmpty())) {
      for (OrePrefixes tPrefix : tPrefixMaterial.mPrefix.mFamiliarPrefixes) {
        tResults.add(GT_OreDictUnificator.get(tPrefix, tPrefixMaterial.mMaterial.mMaterial, 1L));
      }
    }
    FluidStack tFluid = GT_Utility.getFluidForFilledItem(aResult, true);
    if (tFluid != null) {
      tResults.add(GT_Utility.getFluidDisplayStack(tFluid, false));
      for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry
          .getRegisteredFluidContainerData()) {
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

  public java.util.List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, java.util.List<String> currenttip, int aRecipeIndex) {
    TemplateRecipeHandler.CachedRecipe tObject = this.arecipes.get(aRecipeIndex);
    if ((tObject instanceof CachedDefaultRecipe)) {
      CachedDefaultRecipe tRecipe = (CachedDefaultRecipe) tObject;
      for (PositionedStack tStack : tRecipe.mOutputs) {
        if (aStack == tStack.item) {
          if ((!(tStack instanceof FixedPositionedStack)) || (
              ((FixedPositionedStack) tStack).mChance <= 0) || (
              ((FixedPositionedStack) tStack).mChance == 10000)) {
            break;
          }
          currenttip.add(
              trans("150", "Chance: ") + ((FixedPositionedStack) tStack).mChance / 100 + "." + (
                  ((FixedPositionedStack) tStack).mChance % 100 < 10 ? "0"
                      + ((FixedPositionedStack) tStack).mChance % 100
                      : Integer.valueOf(((FixedPositionedStack) tStack).mChance % 100)) + "%");
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
    int tEUt = ((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe.mEUt;
    int tDuration = ((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe.mDuration;
    String[] recipeDesc = ((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe
        .getNeiDesc();
    if (recipeDesc == null) {
      if (tEUt != 0) {
        drawText(10, 73, trans("152", "Total: ") + NumberFormat.getNumberInstance()
            .format(((long) tDuration * tEUt)) + " EU", -16777216);
        drawText(10, 83,
            trans("153", "Usage: ") + NumberFormat.getNumberInstance().format(tEUt) + " EU/t",
            -16777216);
        drawText(10, 93, trans("154", "Voltage: ") + NumberFormat.getNumberInstance()
            .format(tEUt / this.mRecipeMap.mAmperage) + " EU (" + GT_Values.VN[GT_Utility
            .getTier(tEUt / this.mRecipeMap.mAmperage)] + ")", -16777216);
        drawText(10, 103, trans("155", "Amperage: ") + this.mRecipeMap.mAmperage, -16777216);
      }
      if (tDuration > 0) {
        drawText(10, 113, trans("158", "Time: ") + String
            .format("%.2f " + trans("161", " secs"), 0.05F * tDuration), -16777216);
      }

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
      for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry
          .getRegisteredFluidContainerData()) {
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

  public class CachedDefaultRecipe extends TemplateRecipeHandler.CachedRecipe {

    public final GT_Recipe mRecipe;
    public final java.util.List<PositionedStack> mOutputs;
    public final java.util.List<PositionedStack> mInputs;

    public CachedDefaultRecipe(GT_Recipe aRecipe) {
      super();
      this.mRecipe = aRecipe;

      if (aRecipe.getInputPositionedStacks() != null
          && aRecipe.getOutputPositionedStacks() != null) {
        this.mInputs = aRecipe.getInputPositionedStacks();
        this.mOutputs = aRecipe.getOutputPositionedStacks();
        return;
      }

      this.mOutputs = new ArrayList<>();
      this.mInputs = new ArrayList<>();

      amXPosInputs = new int[]{87, 15, 33, 51, 15, 33, 51, 15, 33, 51};
      amYPosInputs = new int[]{16, 16, 16, 16, 34, 34, 34, 52, 52, 52};

      int step = 0;
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step++] - yPos));
      }
      if (aRecipe.getRepresentativeInput(step) != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(step),
            amXPosInputs[step] - xPos, amYPosInputs[step] - yPos));
      }

      this.mOutputs.add(new FixedPositionedStack(aRecipe.getOutput(0), 125 - xPos, 34 - yPos));

    }

    public java.util.List<PositionedStack> getIngredients() {
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
