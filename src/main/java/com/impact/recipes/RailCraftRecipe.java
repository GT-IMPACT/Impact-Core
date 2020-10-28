package com.impact.recipes;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import mods.railcraft.common.blocks.tracks.EnumTrack;
import mods.railcraft.common.items.ItemRail;
import mods.railcraft.common.items.ItemRailbed;
import mods.railcraft.common.items.RailcraftItem;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.util.GT_ModHandler.removeRecipe;
import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

public class RailCraftRecipe implements Runnable {

    private static final long bits = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.BUFFERED;


    public void delRecipe() {
        removeRecipeByOutput(EnumTrack.CONTROL.getItem());
    }

    public void handRecipe() {
        // --- Control Track
        final Object[] recipe = {
                "SRS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.CONTROL.getItem(8), recipe);


    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
    }
}
