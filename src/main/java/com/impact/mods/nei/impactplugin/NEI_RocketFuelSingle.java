package com.impact.mods.nei.impactplugin;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Utility;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class NEI_RocketFuelSingle extends NEI_Impact_SingleTemplate {

  public NEI_RocketFuelSingle() {
    super(
        new int[]{0, 0, 0, 0},
        new ItemStack[]{
            new ItemStack(GCItems.rocketTier1),
            GT_Utility.getFluidDisplayStack(new FluidStack(ItemList.sRocketFuel, 1000), true),
        },
        new int[]{25, 125}, new int[]{22, 22},
        new ItemStack[]{
            new ItemStack(GCItems.fuelCanister)
        },
        new int[]{75}, new int[]{22},
        false
    );
    if (!NEI_Impact_Config.sIsAdded) {
      FMLInterModComms.sendRuntimeMessage(GT_Values.GT, "NEIPlugins", "register-crafting-handler",
          "impact@" + getRecipeName() + "@" + getOverlayIdentifier());
      GuiCraftingRecipe.craftinghandlers.add(this);
      GuiUsageRecipe.usagehandlers.add(this);
    }
  }

  public void drawExtras(int aRecipeIndex) {
    drawText(4, 85, "For any Rockets", -16777216);
    drawText(4, 95, "Need to make the rocket fuel", -16777216);
    drawText(4, 105, "Need to fill the canister", -16777216);
    drawText(4, 115, "Need to fuel the rocket", -16777216);
  }

  public String getRecipeName() {
    return "Rocket Fuel Production";
  }

  public String getGuiTexture() {
    return "gregtech:textures/gui/basic/RocketFuel.png";
  }

  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals(getOverlayIdentifier())) {
      this.arecipes.add(new CachedDefaultRecipe());
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  public TemplateRecipeHandler newInstance() {
    return new NEI_RocketFuelSingle();
  }
}
