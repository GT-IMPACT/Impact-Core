package com.impact.mods.NEI.OrePugin;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.awt.Rectangle;

public class PluginBase extends TemplateRecipeHandler {

  @Override
  public String getGuiTexture() {
    return "impact:textures/gui/guiBase.png";
  }

  @Override
  public String getRecipeName() {
    return null;
  }

  @Override
  public int recipiesPerPage() {
    return 1;
  }

  public String getOutputId() {
    return null;
  }

  public int getGuiWidth() {
    return 166;
  }

  @Override
  public void loadTransferRects() {
    int stringLength = GuiDraw.getStringWidth("Show all");
    transferRects.add(
        new RecipeTransferRect(new Rectangle(getGuiWidth() - stringLength - 3, 5, stringLength, 9),
            getOutputId()));
  }
}
