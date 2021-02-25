package com.impact.mods.nei.oreplugin;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import com.impact.mods.nei.oreplugin.helper.GT5OreLayerHelper;
import gregtech.api.GregTech_API;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class PluginGT5VeinStat extends PluginGT5Base {

  public static String coustomVeinRenamer(GT5OreLayerHelper.OreLayerWrapper oreLayer) {
    Set<String> s = new HashSet<String>();
    for (int i = 0; i < 4; i++) {
      s.add(getGTOreLocalizedName(oreLayer.Meta[i]).replaceAll(" ", ""));
    }
    return s.toString()
        .replace("[".charAt(0), ",".charAt(0))
        .replace("]".charAt(0), ",".charAt(0))
        .replaceAll(" Ore", ",")
        .replaceAll("Ore", ",")
        .replaceAll(" Sand", ",")
        .replaceAll("Sand", ",")
        .replaceAll("Stone", ",")
        .replaceAll(" Stone", ",")
        .replaceAll("Earth", ",")
        .replaceAll(" Earth", ",")
        .replaceAll("Infused", ",")
        .replaceAll(" Infused", ",")
        .replaceAll(",", "")
        .trim();
  }

  public static String[] get_Cnames(GT5OreLayerHelper.OreLayerWrapper oreLayer) {

    String[] splt = coustomVeinRenamer(oreLayer).split("\\s");
    String[] ret = {oreLayer.veinName.replace("ore.mix.custom.", "") + " ", " ", " "};
    for (int i = 0; i < ret.length; i++) {
      ret[i] = ret[i].trim();
    }
    for (int i = 0; i < splt.length; i++) {
      if (ret[0].length() + splt[i].length() <= 20) {
        ret[0] = ret[0] + splt[i] + " ";
      }
      if ((ret[0].length() + splt[i].length() > 20) && ret[1].length() + splt[i].length() <= 70
          && !ret[0].contains(splt[i])) {
        ret[1] = ret[1] + splt[i] + " ";
      }
      if ((ret[0].length() + splt[i].length() > 20) && (ret[1].length() + splt[i].length() > 70)
          && ret[2].length() + splt[i].length() <= 70 && !ret[1].contains(splt[i])) {
        ret[2] = ret[2] + splt[i] + " ";
      }
    }
    for (int i = 0; i < ret.length; i++) {
      ret[i] = ret[i].trim();
    }

    if (ret[2].isEmpty() && !ret[1].isEmpty()) {
      if (ret[1].length() <= 65) {
        ret[1] = ret[1] + " Vein";
      } else {
        ret[2] = ret[2] + "Vein";
      }
    } else if (ret[1].isEmpty() && ret[2].isEmpty() && !ret[0].isEmpty()) {
      if (ret[0].length() <= 15) {
        ret[0] = ret[0] + " Vein";
      } else {
        ret[1] = ret[1] + "Vein";
      }
    } else if (!(ret[1].isEmpty() && ret[2].isEmpty())) {
      ret[2] = ret[2] + "Vein";
    }
    String[] ret2 = new String[2];
    if (ret[2].isEmpty() && !ret[1].isEmpty()) {
      ret2[0] = ret[0];
      ret2[1] = ret[1];
      return ret2;
    }
    String[] ret1 = new String[1];
    if (ret[1].isEmpty() && ret[2].isEmpty() && !ret[0].isEmpty()) {
      ret1[0] = ret[0];
      return ret1;
    } else {
      return ret;
    }
  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals(getOutputId())) {
      GT5OreLayerHelper.OreLayerWrapper oreLayerWrapper;
      for (String veinName : GT5OreLayerHelper.mapOreLayerWrapper.keySet()) {
        oreLayerWrapper = GT5OreLayerHelper.mapOreLayerWrapper.get(veinName);
        List<ItemStack> stackListPrimary = new ArrayList<ItemStack>();
        List<ItemStack> stackListSecondary = new ArrayList<ItemStack>();
        List<ItemStack> stackListBetween = new ArrayList<ItemStack>();
        List<ItemStack> stackListSporadic = new ArrayList<ItemStack>();
        for (int i = 0; i < 7; i++) {
          stackListPrimary
              .add(new ItemStack(GregTech_API.sBlockOres1, 1,
                  oreLayerWrapper.Meta[0] + i * 1000));
          stackListSecondary
              .add(new ItemStack(GregTech_API.sBlockOres1, 1,
                  oreLayerWrapper.Meta[1] + i * 1000));
          stackListBetween
              .add(new ItemStack(GregTech_API.sBlockOres1, 1,
                  oreLayerWrapper.Meta[2] + i * 1000));
          stackListSporadic
              .add(new ItemStack(GregTech_API.sBlockOres1, 1,
                  oreLayerWrapper.Meta[3] + i * 1000));
        }
        this.arecipes
            .add(new PluginGT5VeinStat.CachedVeinStatRecipe(veinName, stackListPrimary,
                stackListSecondary, stackListBetween, stackListSporadic));
      }
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  @Override
  public void loadCraftingRecipes(ItemStack stack) {
    if (stack.getUnlocalizedName().startsWith("gt.blockores")) {
      if (stack.getItemDamage() > 16000) {
        super.loadCraftingRecipes(stack);
        return;
      }
      short baseMeta = (short) (stack.getItemDamage() % 1000);
      for (GT5OreLayerHelper.OreLayerWrapper worldGen : GT5OreLayerHelper.mapOreLayerWrapper
          .values()) {
        if (worldGen.Meta[0] == baseMeta || worldGen.Meta[1] == baseMeta
            || worldGen.Meta[2] == baseMeta || worldGen.Meta[3] == baseMeta) {
          List<ItemStack> stackListPrimary = new ArrayList<ItemStack>();
          List<ItemStack> stackListSecondary = new ArrayList<ItemStack>();
          List<ItemStack> stackListBetween = new ArrayList<ItemStack>();
          List<ItemStack> stackListSporadic = new ArrayList<ItemStack>();
          for (int i = 0; i < getMaximumMaterialIndex(baseMeta, false); i++) {
            stackListPrimary
                .add(new ItemStack(GregTech_API.sBlockOres1, 1,
                    worldGen.Meta[0] + i * 1000));
            stackListSecondary
                .add(new ItemStack(GregTech_API.sBlockOres1, 1,
                    worldGen.Meta[1] + i * 1000));
            stackListBetween
                .add(new ItemStack(GregTech_API.sBlockOres1, 1,
                    worldGen.Meta[2] + i * 1000));
            stackListSporadic
                .add(new ItemStack(GregTech_API.sBlockOres1, 1,
                    worldGen.Meta[3] + i * 1000));
          }
          this.arecipes.add(
              new CachedVeinStatRecipe(worldGen.veinName, stackListPrimary,
                  stackListSecondary,
                  stackListBetween, stackListSporadic));
        }
      }
    } else {
      super.loadCraftingRecipes(stack);
    }
  }

  @Override
  public void drawExtras(int recipe) {
    PluginGT5VeinStat.CachedVeinStatRecipe crecipe = (PluginGT5VeinStat.CachedVeinStatRecipe) this.arecipes
        .get(recipe);
    GT5OreLayerHelper.OreLayerWrapper oreLayer = GT5OreLayerHelper.mapOreLayerWrapper
        .get(crecipe.veinName);
    String sDimNames = GT5OreLayerHelper.bufferedDims.get(oreLayer);
    if (getGTOreLocalizedName(oreLayer.Meta[0]).contains("Ore")) {
      GuiDraw.drawString(
          I18n.format("gtnop.gui.nei.veinName") + ": " + getGTOreLocalizedName(oreLayer.Meta[0])
              .split("Ore")[0] + "" + I18n.format("gtnop.gui.nei.vein"), 2, 20, 0x404040,
          false);
    } else if (getGTOreLocalizedName(oreLayer.Meta[0]).contains("Sand")) {
      GuiDraw.drawString(
          I18n.format("gtnop.gui.nei.veinName") + ": " + getGTOreLocalizedName(oreLayer.Meta[0])
              .split("Sand")[0] + "" + I18n.format("gtnop.gui.nei.vein"), 2, 20, 0x404040,
          false);
    } else {
      GuiDraw.drawString(
          I18n.format("gtnop.gui.nei.veinName") + ": " + getGTOreLocalizedName(oreLayer.Meta[0])
              + " " + I18n.format("gtnop.gui.nei.vein"), 2, 20, 0x404040, false);
    }
    drawToolTip(sDimNames);
    if (!ttDisplayed) {
      GuiDraw.drawString(
          I18n.format("gtnop.gui.nei.primaryOre") + ": " + getGTOreLocalizedName(oreLayer.Meta[0]),
          2, 35, 0x404040, false);
      GuiDraw.drawString(I18n.format("gtnop.gui.nei.secondaryOre") + ": " + getGTOreLocalizedName(
          oreLayer.Meta[1]), 2, 45, 0x404040, false);
      GuiDraw.drawString(
          I18n.format("gtnop.gui.nei.betweenOre") + ": " + getGTOreLocalizedName(oreLayer.Meta[2]),
          2, 55, 0x404040, false);
      GuiDraw.drawString(
          I18n.format("gtnop.gui.nei.sporadicOre") + ": " + getGTOreLocalizedName(oreLayer.Meta[3]),
          2, 65, 0x404040, false);
      GuiDraw
          .drawString(I18n.format("gtnop.gui.nei.genHeight") + ": " + oreLayer.worldGenHeightRange,
              2, 80, 0x404040, false);
      GuiDraw.drawString(I18n.format("gtnop.gui.nei.weightedChance") + ": " + Integer
          .toString(oreLayer.randomWeight), 100, 80, 0x404040, false);
      GuiDraw.drawString(I18n.format("gtnop.gui.nei.worldNames") + ": ", 2, 100, 0x404040, false);
      if (sDimNames.length() > 36) {
        GuiDraw
            .drawString(I18n.format("") + sDimNames.substring(0, 36), 2, 110, 0x404040, false);
        if (sDimNames.length() > 70) {
          GuiDraw
              .drawString(I18n.format("") + sDimNames.substring(36, 70), 2, 120, 0x404040,
                  false);
          GuiDraw
              .drawString(I18n.format("") + sDimNames.substring(70, sDimNames.length() - 1),
                  2,
                  130,
                  0x404040, false);
        } else {
          GuiDraw
              .drawString(I18n.format("") + sDimNames.substring(36, sDimNames.length() - 1),
                  2,
                  120,
                  0x404040, false);
        }
      } else {
        GuiDraw.drawString(I18n.format("") + sDimNames.substring(0, sDimNames.length() - 1), 2,
            110,
            0x404040, false);
      }
    }
    GuiDraw.drawStringR("Show All", getGuiWidth() - 3, 5, 0x404040, false);
  }

  @Override
  public String getOutputId() {
    return "GTOrePluginVein";
  }

  @Override
  public String getRecipeName() {
    return I18n.format("gtnop.gui.veinStat.name");
  }

  public class CachedVeinStatRecipe extends CachedRecipe {

    public String veinName;
    public PositionedStack positionedStackPrimary;
    public PositionedStack positionedStackSecondary;
    public PositionedStack positionedStackBetween;
    public PositionedStack positionedStackSporadic;

    public CachedVeinStatRecipe(String veinName, List<ItemStack> stackListPrimary,
        List<ItemStack> stackListSecondary,
        List<ItemStack> stackListBetween, List<ItemStack> stackListSporadic) {
      this.veinName = veinName;
      positionedStackPrimary = new PositionedStack(stackListPrimary, 2, 0);
      positionedStackSecondary = new PositionedStack(stackListSecondary, 22, 0);
      positionedStackBetween = new PositionedStack(stackListBetween, 42, 0);
      positionedStackSporadic = new PositionedStack(stackListSporadic, 62, 0);
    }

    @Override
    public List<PositionedStack> getIngredients() {
      List<PositionedStack> ingredientsList = new ArrayList<PositionedStack>();
      positionedStackPrimary
          .setPermutationToRender((cycleticks / 20) % positionedStackPrimary.items.length);
      positionedStackSecondary
          .setPermutationToRender((cycleticks / 20) % positionedStackPrimary.items.length);
      positionedStackBetween
          .setPermutationToRender((cycleticks / 20) % positionedStackPrimary.items.length);
      positionedStackSporadic
          .setPermutationToRender((cycleticks / 20) % positionedStackPrimary.items.length);
      ingredientsList.add(positionedStackPrimary);
      ingredientsList.add(positionedStackSecondary);
      ingredientsList.add(positionedStackBetween);
      ingredientsList.add(positionedStackSporadic);
      return ingredientsList;
    }

    @Override
    public PositionedStack getResult() {
      return null;
    }

  }
}
