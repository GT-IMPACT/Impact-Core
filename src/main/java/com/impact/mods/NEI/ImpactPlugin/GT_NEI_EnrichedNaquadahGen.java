package com.impact.mods.nei.impactplugin;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerInputHandler;
import codechicken.nei.guihook.IContainerTooltipHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_WithAlt;
import gregtech.api.util.GT_Utility;
import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class GT_NEI_EnrichedNaquadahGen extends TemplateRecipeHandler {

  public static final int sOffsetX = 5;
  public static final int sOffsetY = 11;

  static {
    GuiContainerManager.addInputHandler(new GT_RectHandler());
    GuiContainerManager.addTooltipHandler(new GT_RectHandler());
  }

  protected final GT_Recipe.GT_Recipe_Map mRecipeMap;

  public GT_NEI_EnrichedNaquadahGen(
      GT_Recipe.GT_Recipe_Map aRecipeMap) {//this is called when recipes should be shown
    this.mRecipeMap = aRecipeMap;
    this.transferRects
        .add(new RecipeTransferRect(new Rectangle(75, 32, 18, 18), getOverlayIdentifier()));
    if (!NEI_Impact_Config.sIsAdded) {
      FMLInterModComms.sendRuntimeMessage(GT_Values.GT, "NEIPlugins", "register-crafting-handler",
          "gregtech@" + getRecipeName() + "@" + getOverlayIdentifier());
      GuiCraftingRecipe.craftinghandlers.add(this);
      GuiUsageRecipe.usagehandlers.add(this);
    }
  }

  public static void drawText(int aX, int aY, String aString, int aColor) {
    Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
  }

  public TemplateRecipeHandler newInstance() {
    return new GT_NEI_EnrichedNaquadahGen(mRecipeMap);
  }

  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals(getOverlayIdentifier())) {
      for (GT_Recipe tRecipe : this.mRecipeMap.mRecipeList) {
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
    for (GT_Recipe tRecipe : this.mRecipeMap.mRecipeList) {
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
    for (GT_Recipe tRecipe : this.mRecipeMap.mRecipeList) {
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
    CachedDefaultRecipe tNEIRecipe;
  }

  public String getOverlayIdentifier() {
    return this.mRecipeMap.mNEIName;
  }

  public void drawBackground(int recipe) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GuiDraw.changeTexture(getGuiTexture());
    GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 79);
  }

  public int recipiesPerPage() {
    return 1;
  }

  public String getRecipeName() {
    return "Liquid Nq+ Generator";
  }

  public String getGuiTexture() {
    return this.mRecipeMap.mNEIGUIPath;
  }

  public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip,
      int aRecipeIndex) {
    return currenttip;
  }

  public void drawExtras(int aRecipeIndex) {
    String[] recipeDesc = ((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe
        .getNeiDesc();
    if (recipeDesc == null) {
      int tSpecial = ((GT_NEI_EnrichedNaquadahGen.CachedDefaultRecipe) this.arecipes
          .get(aRecipeIndex)).mRecipe.mSpecialValue;
      long mEUTotal = (20L * 1000L / (long) tSpecial) * (131072L * 64L);
      int mTime = (20 * 1000 / tSpecial) / 20;
      if ((GT_Utility.isStringValid(this.mRecipeMap.mNEISpecialValuePre)) || (GT_Utility
          .isStringValid(this.mRecipeMap.mNEISpecialValuePost))) {
        drawText(10, 55, "Total EU for 1000L: ", -16777216);
        drawText(10, 65, NumberFormat.getNumberInstance().format(mEUTotal) + " EU", -16777216);
        drawText(10, 80, "Fuel consume: ", -16777216);
        drawText(10, 90, tSpecial + " L/s", -16777216);
        drawText(10, 105, "Generation: ", -16777216);
        drawText(10, 115, "131,072 EU/t * 64 Amp", -16777216);
      }

    } else {
      int i = 0;
      for (String descLine : recipeDesc) {
        drawText(10, 73 + 10 * i, descLine, -16777216);
        i++;
      }
    }
  }

  public static class GT_RectHandler implements IContainerInputHandler, IContainerTooltipHandler {

    public boolean mouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
      return false;
    }

    public boolean lastKeyTyped(GuiContainer gui, char keyChar, int keyCode) {
      return false;
    }

    public List<String> handleTooltip(GuiContainer gui, int mousex, int mousey,
        List<String> currenttip) {

      return currenttip;
    }

    public List<String> handleItemDisplayName(GuiContainer gui, ItemStack itemstack,
        List<String> currenttip) {
      return currenttip;
    }

    public List<String> handleItemTooltip(GuiContainer gui, ItemStack itemstack, int mousex,
        int mousey, List<String> currenttip) {
      return currenttip;
    }

    public boolean keyTyped(GuiContainer gui, char keyChar, int keyCode) {
      return false;
    }

    public void onKeyTyped(GuiContainer gui, char keyChar, int keyID) {
    }

    public void onMouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
    }

    public void onMouseUp(GuiContainer gui, int mousex, int mousey, int button) {
    }

    public boolean mouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
      return false;
    }

    public void onMouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
    }

    public void onMouseDragged(GuiContainer gui, int mousex, int mousey, int button,
        long heldTime) {
    }
  }

  public class FixedPositionedStack extends PositionedStack {

    public final int mChance;
    public boolean permutated = false;

    public FixedPositionedStack(Object object, int x, int y) {
      this(object, x, y, 0);
    }

    public FixedPositionedStack(Object object, int x, int y, int aChance) {
      super(object, x, y, true);
      this.mChance = aChance;
    }

    public void generatePermutations() {
      if (this.permutated) {
        return;
      }
      ArrayList<ItemStack> tDisplayStacks = new ArrayList();
      for (ItemStack tStack : this.items) {
        if (GT_Utility.isStackValid(tStack)) {
          if (tStack.getItemDamage() == 32767) {
            List<ItemStack> permutations = codechicken.nei.ItemList.itemMap.get(tStack.getItem());
            if (!permutations.isEmpty()) {
              ItemStack stack;
              for (Iterator i$ = permutations.iterator(); i$.hasNext();
                  tDisplayStacks.add(GT_Utility.copyAmount(tStack.stackSize, stack))) {
                stack = (ItemStack) i$.next();
              }
            } else {
              ItemStack base = new ItemStack(tStack.getItem(), tStack.stackSize);
              base.stackTagCompound = tStack.stackTagCompound;
              tDisplayStacks.add(base);
            }
          } else {
            tDisplayStacks.add(GT_Utility.copy(tStack));
          }
        }
      }
      this.items = tDisplayStacks.toArray(new ItemStack[0]);
      if (this.items.length == 0) {
        this.items = new ItemStack[]{new ItemStack(Blocks.fire)};
      }
      this.permutated = true;
      setPermutationToRender(0);
    }
  }

  public class CachedDefaultRecipe
      extends CachedRecipe {

    public final GT_Recipe mRecipe;
    public final List<PositionedStack> mOutputs = new ArrayList();
    public final List<PositionedStack> mInputs = new ArrayList();

    public CachedDefaultRecipe(GT_Recipe aRecipe) {
      super();
      this.mRecipe = aRecipe;

      Object obj = aRecipe instanceof GT_Recipe_WithAlt ? ((GT_Recipe_WithAlt) aRecipe)
          .getAltRepresentativeInput(0) : aRecipe.getRepresentativeInput(0);
      if (obj != null) {
        this.mInputs.add(new FixedPositionedStack(obj, 75, 14));
      }

      if (aRecipe.mSpecialItems != null) {
        this.mInputs.add(new FixedPositionedStack(aRecipe.mSpecialItems, 12, 27));
      }

      if (aRecipe.getOutput(0) != null) {
        this.mOutputs.add(
            new FixedPositionedStack(aRecipe.getOutput(0), 84, 27, aRecipe.getOutputChance(0)));
      }

    }

    public List<PositionedStack> getIngredients() {
      return getCycledIngredients(GT_NEI_EnrichedNaquadahGen.this.cycleticks / 10, this.mInputs);
    }

    public PositionedStack getResult() {
      return null;
    }

    public List<PositionedStack> getOtherStacks() {
      return this.mOutputs;
    }
  }
}
