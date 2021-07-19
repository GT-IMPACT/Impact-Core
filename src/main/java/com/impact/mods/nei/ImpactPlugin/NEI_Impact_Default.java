package com.impact.mods.nei.impactplugin;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerInputHandler;
import codechicken.nei.guihook.IContainerTooltipHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public abstract class NEI_Impact_Default extends TemplateRecipeHandler {

  public static ItemStack[] amInputs;
  public static int[] amXPosInputs;
  public static int[] amYPosInputs;
  public static ItemStack[] amOutputs;
  public static int[] amXPosOutputs;
  public static int[] amYPosOutputs;

  static {
    GuiContainerManager.addInputHandler(new GT_RectHandler());
    GuiContainerManager.addTooltipHandler(new GT_RectHandler());
  }

  public final GT_Recipe.GT_Recipe_Map mRecipeMap;

  public NEI_Impact_Default(GT_Recipe.GT_Recipe_Map aRecipeMap) {
    this.mRecipeMap = aRecipeMap;

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

  public java.util.List<GT_Recipe> getSortedRecipes() {
    java.util.List<GT_Recipe> result = new ArrayList<>(this.mRecipeMap.mRecipeList);
    Collections.sort(result);
    return result;
  }

  public String getOverlayIdentifier() {
    return this.mRecipeMap.mNEIName;
  }

  public void drawBackground(int recipe) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    GuiDraw.changeTexture(getGuiTexture());
    GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 80);
  }

  public int recipiesPerPage() {
    return 1;
  }

  public String getRecipeName() {
    return GT_LanguageManager.getTranslation(this.mRecipeMap.mUnlocalizedName);
  }

  public String trans(String aKey, String aEnglish) {
    return GT_LanguageManager
        .addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
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

  public static class FixedPositionedStack extends PositionedStack {

    public final int mChance;
    public boolean permutated = false;

    public FixedPositionedStack(Object object, int x, int y) {
      this(object, x, y, 0);
    }

    public FixedPositionedStack(Object object, int x, int y, int aChance) {
      super(GT_OreDictUnificator.getNonUnifiedStacks(object), x, y, true);
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
            java.util.List<ItemStack> permutations = codechicken.nei.ItemList.itemMap
                .get(tStack.getItem());
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
}
