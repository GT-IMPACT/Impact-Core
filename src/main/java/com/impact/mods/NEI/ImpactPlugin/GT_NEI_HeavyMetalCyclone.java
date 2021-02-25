package com.impact.mods.nei.impactplugin;

import static gregtech.api.util.GT_Utility.trans;

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
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.nei.GT_NEI_DefaultHandler;
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

public class GT_NEI_HeavyMetalCyclone extends TemplateRecipeHandler {

  public static final int sOffsetX = 5;
  public static final int sOffsetY = 11;

  static {
    GuiContainerManager.addInputHandler(new GT_RectHandler());
    GuiContainerManager.addTooltipHandler(new GT_RectHandler());
  }

  protected final GT_Recipe.GT_Recipe_Map mRecipeMap;

  public GT_NEI_HeavyMetalCyclone(
      GT_Recipe.GT_Recipe_Map aRecipeMap) {//this is called when recipes should be shown
    this.mRecipeMap = aRecipeMap;
    this.transferRects
        .add(new RecipeTransferRect(new Rectangle(75, 6, 36, 18), getOverlayIdentifier()));
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
    return new GT_NEI_HeavyMetalCyclone(mRecipeMap);
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
    return "Heavy Metal Cyclone";
  }

  public String getGuiTexture() {
    return "gregtech:textures/gui/basic/HeavyMetalCyclone.png";
  }

  public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip,
      int aRecipeIndex) {
    return currenttip;
  }

  public void drawExtras(int aRecipeIndex) {
    int tEUt = ((GT_NEI_HeavyMetalCyclone.CachedDefaultRecipe) this.arecipes
        .get(aRecipeIndex)).mRecipe.mEUt;
    int tDuration = ((GT_NEI_HeavyMetalCyclone.CachedDefaultRecipe) this.arecipes
        .get(aRecipeIndex)).mRecipe.mDuration;
    String[] recipeDesc = ((GT_NEI_HeavyMetalCyclone.CachedDefaultRecipe) this.arecipes
        .get(aRecipeIndex)).mRecipe.getNeiDesc();
    if (recipeDesc == null) {
      if (tEUt != 0) {
        drawText(10, 73, trans("152", "Total: ") + NumberFormat.getNumberInstance()
            .format(((long) tDuration * tEUt)) + " EU", -16777216);
        drawText(10, 83,
            trans("153", "Usage: ") + NumberFormat.getNumberInstance().format(tEUt) + " EU/t",
            -16777216);
        if (this.mRecipeMap.mShowVoltageAmperageInNEI) {
          drawText(10, 93, trans("154", "Voltage: ") + NumberFormat.getNumberInstance()
              .format(tEUt / this.mRecipeMap.mAmperage) + " EU (" + GT_Values.VN[GT_Utility
              .getTier(tEUt / this.mRecipeMap.mAmperage)] + ")", -16777216);
          drawText(10, 103, trans("155", "Amperage: ") + this.mRecipeMap.mAmperage, -16777216);
        } else {
          drawText(10, 93, trans("156", "Voltage: unspecified"), -16777216);
          drawText(10, 103, trans("157", "Amperage: unspecified"), -16777216);
        }
      }
      if (tDuration > 0) {
        drawText(10, 113, trans("158", "Time: ") + String
            .format("%.2f " + trans("161", " secs"), 0.05F * tDuration), -16777216);
      }
      int tSpecial = ((GT_NEI_HeavyMetalCyclone.CachedDefaultRecipe) this.arecipes
          .get(aRecipeIndex)).mRecipe.mSpecialValue;
      if (tSpecial == -100 && GT_Mod.gregtechproxy.mLowGravProcessing) {
        drawText(10, 123, trans("159", "Needs Low Gravity"), -16777216);
      } else if (tSpecial == -200 && GT_Mod.gregtechproxy.mEnableCleanroom) {
        drawText(10, 123, trans("160", "Needs Cleanroom"), 0x602487);
      } else if (tSpecial == -201) {
        drawText(10, 123, trans("206", "Scan for Assembly Line"), 0x602487);
      } else if (tSpecial == -300 && GT_Mod.gregtechproxy.mEnableCleanroom) {
        drawText(10, 123, trans("160", "Needs Cleanroom & LowGrav"), 0x602487);
      } else if (tSpecial == -400) {
        drawText(10, 123, trans("216", "Deprecated Recipe"), 0x602487);
      } else if (tSpecial == -500 && GT_Mod.gregtechproxy.mPlanetTier1) {
        drawText(10, 123, trans("219", "Needs planets and SRS: Tier 1"), 0x602487);
      } else if (tSpecial == -600 && GT_Mod.gregtechproxy.mPlanetTier2) {
        drawText(10, 123, trans("220", "Needs planets and SRS: Tier 2"), 0x602487);
      } else if (tSpecial == -700 && GT_Mod.gregtechproxy.mPlanetTier3) {
        drawText(10, 123, trans("221", "Needs planets and SRS: Tier 3"), 0x602487);
      } else if (tSpecial == -800 && GT_Mod.gregtechproxy.mPlanetTier4) {
        drawText(10, 123, trans("222", "Needs planets and SRS: Tier 4"), 0x602487);
      } else if (tSpecial == -900 && GT_Mod.gregtechproxy.mPlanetTier5) {
        drawText(10, 123, trans("223", "Needs planets and SRS: Tier 5"), 0x602487);
      } else if (tSpecial == -1000 && GT_Mod.gregtechproxy.mPlanetTier6) {
        drawText(10, 123, trans("224", "Needs planets and SRS: Tier 6"), 0x602487);
      } else if (tSpecial == -1100 && GT_Mod.gregtechproxy.mPlanetTier7) {
        drawText(10, 123, trans("225", "Needs planets and SRS: Tier 7"), 0x602487);
      } else if ((GT_Utility.isStringValid(this.mRecipeMap.mNEISpecialValuePre)) || (GT_Utility
          .isStringValid(this.mRecipeMap.mNEISpecialValuePost))) {
        drawText(10, 123, this.mRecipeMap.mNEISpecialValuePre + NumberFormat.getNumberInstance()
            .format(tSpecial * this.mRecipeMap.mNEISpecialValueMultiplier)
            + this.mRecipeMap.mNEISpecialValuePost, -16777216);
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

      int xPos = 5;
      int yPos = 11;

      for (int i = 0; i < GT_NEI_HeavyMetalCyclone.this.mRecipeMap.mUsualInputCount; i++) {
        if (aRecipe.getRepresentativeInput(i) != null) {
          this.mInputs.add(
              new GT_NEI_DefaultHandler.FixedPositionedStack(
                  aRecipe.getRepresentativeInput(i),
                  (26 + i * 18) - xPos, 17 - yPos));
        }
      }

      int[] x = new int[]{116, 134, 134, 98, 98, 116};
      int[] y = new int[]{17, 17, 35, 35, 53, 53};
      for (int i = 0; i < aRecipe.mOutputs.length; i++) {
        if (aRecipe.getOutput(i) != null) {
          this.mOutputs
              .add(new FixedPositionedStack(aRecipe.getOutput(i), x[i] - xPos, y[i] - yPos,
                  aRecipe.getOutputChance(i)));
        }
      }

      if ((aRecipe.mFluidOutputs.length > 0) && (
          aRecipe.mFluidOutputs[aRecipe.mFluidOutputs.length - 1] != null) && (
          aRecipe.mFluidOutputs[aRecipe.mFluidOutputs.length - 1].getFluid() != null)) {
        for (int i = 0; i < aRecipe.mFluidOutputs.length; i++) {
          this.mOutputs.add(new GT_NEI_HeavyMetalCyclone.FixedPositionedStack(
              GT_Utility.getFluidDisplayStack(aRecipe.mFluidOutputs[i], true), 116 - xPos,
              35 - yPos));
        }
      }

      int[] x2 = new int[]{26, 44, 62, 26, 44, 62};
      if ((aRecipe.mFluidInputs.length > 0) && (
          aRecipe.mFluidInputs[aRecipe.mFluidInputs.length - 1] != null) && (
          aRecipe.mFluidInputs[aRecipe.mFluidInputs.length - 1].getFluid() != null)) {
        for (int i = 0; i < aRecipe.mFluidInputs.length; i++) {
          this.mInputs.add(new GT_NEI_HeavyMetalCyclone.FixedPositionedStack(
              GT_Utility.getFluidDisplayStack(aRecipe.mFluidInputs[i], true), x2[i] - xPos,
              (i > 2 ? 53 : 35) - yPos));
        }
      }
    }

    public List<PositionedStack> getIngredients() {
      return getCycledIngredients(GT_NEI_HeavyMetalCyclone.this.cycleticks / 10, this.mInputs);
    }

    public PositionedStack getResult() {
      return null;
    }

    public List<PositionedStack> getOtherStacks() {
      return this.mOutputs;
    }
  }
}
