package com.impact.mods.NEI.ImpactPlugin;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Utility;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class NEI_MoonMiner extends NEI_Impact_SingleTemplate {

    public NEI_MoonMiner() {
        super(
                new int[]{0, 0, 0, 0},
                new ItemStack[]{
                        GT_Utility.getFluidDisplayStack(new FluidStack(ItemList.sDrillingFluid, 200), true),
                },
                new int[]{39}, new int[]{35},
                new ItemStack[]{
                        new ItemStack(GCBlocks.blockMoon, 1, 3),
                        new ItemStack(GCBlocks.blockMoon, 1, 4),
                        new ItemStack(GCBlocks.blockMoon, 1, 5),
                },
                new int[]{75, 93, 111}, new int[]{35, 35, 35},
                false
        );
        if (!NEI_Impact_Config.sIsAdded) {
            FMLInterModComms.sendRuntimeMessage(GT_Values.GT, "NEIPlugins", "register-crafting-handler", "impact@" + getRecipeName() + "@" + getOverlayIdentifier());
            GuiCraftingRecipe.craftinghandlers.add(this);
            GuiUsageRecipe.usagehandlers.add(this);
        }
    }

    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip, int aRecipeIndex) {
        TemplateRecipeHandler.CachedRecipe tObject = this.arecipes.get(aRecipeIndex);
        if ((tObject instanceof CachedDefaultRecipe)) {
            CachedDefaultRecipe tRecipe = (CachedDefaultRecipe) tObject;
            for (PositionedStack tStack : tRecipe.mInputs) {
                if (aStack == tStack.item) {
                    if (!(tStack instanceof FixedPositionedStack)) {
                        break;
                    }
                    currenttip.add("For 1 second");
                    break;
                }
            }
        }
        if ((tObject instanceof CachedDefaultRecipe)) {
            CachedDefaultRecipe tRecipe = (CachedDefaultRecipe) tObject;
            for (PositionedStack tStack : tRecipe.mOutputs) {
                if (aStack == tStack.item) {
                    if (!(tStack instanceof FixedPositionedStack)) {
                        break;
                    }
                    currenttip.add("Chance: " + (100 / 3) + "%");
                    break;
                }
            }
        }
        return super.handleItemTooltip(gui, aStack, currenttip, aRecipeIndex);
    }

    public void drawExtras(int aRecipeIndex) {
        drawText(4, 85, "Usage: 64 EU/t (MV)", -16777216);
        drawText(4, 95, "Amperage: 1", -16777216);
        drawText(4, 105, "Time: 1s", -16777216);
        drawText(4, 115, "Dimension: Moon", 0x602487);
    }

    public String getRecipeName() {
        return "Moon Miner";
    }

    public String getGuiTexture() {
        return "gregtech:textures/gui/basic/MoonMiner.png";
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getOverlayIdentifier())) {
            this.arecipes.add(new CachedDefaultRecipe());
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public TemplateRecipeHandler newInstance() {
        return new NEI_MoonMiner();
    }
}
