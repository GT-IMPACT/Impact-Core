package com.gwppcore.item;

import com.gwppcore.creativetab.ModTabList;
import com.gwppcore.lib.Refstrings;
import com.gwppcore.gwppcore;
import eu.usrv.yamcore.items.ModItemManager;
import eu.usrv.yamcore.items.ModSimpleBaseItem;
import net.minecraft.item.ItemStack;

public enum ItemList {

	Coin(new ModSimpleBaseItem("Coin",ModTabList.GWppTab)),

        //STEAM AGE
    WoodenBrickForm(new ModSimpleBaseItem("WoodenBrickForm",ModTabList.GWppTab)),
    UnfiredSearedBrick(new ModSimpleBaseItem("UnfiredSearedBrick",ModTabList.GWppTab)),
    UnfiredCokeOvenBrick(new ModSimpleBaseItem("UnfiredCokeOvenBrick",ModTabList.GWppTab)),
    UnfiredClayBrick(new ModSimpleBaseItem("UnfiredClayBrick",ModTabList.GWppTab)),

        //BC CHIPS
    RedstoneRedChipset(new ModSimpleBaseItem("RedstoneRedChipset",ModTabList.GWppTab)),
    RedstoneQuartzChipset(new ModSimpleBaseItem("RedstoneQuartzChipset",ModTabList.GWppTab)),
    RedstonePulsatingChipset(new ModSimpleBaseItem("RedstonePulsatingChipset",ModTabList.GWppTab)),
    RedstoneIronChipset(new ModSimpleBaseItem("RedstoneIronChipset",ModTabList.GWppTab)),
    RedstoneGoldChipset(new ModSimpleBaseItem("RedstoneGoldChipset",ModTabList.GWppTab)),
    RedstoneEmeraldChipset(new ModSimpleBaseItem("RedstoneEmeraldChipset",ModTabList.GWppTab)),
    RedstoneDiamondChipset(new ModSimpleBaseItem("RedstoneDiamondChipset",ModTabList.GWppTab)),



	
	// Do not delete this
    EndOfList(null);

    // ################################################################################
    public ModSimpleBaseItem Item;

    ItemList(ModSimpleBaseItem pItem)
    {
        Item = pItem;
        if (Item != null) {
            Item.setModIDName(Refstrings.MODID);
        }
    }

    public static boolean AddToItemManager(ModItemManager pItemManager)
    {
        boolean tResult = true;
        for (ItemList il : ItemList.values())
        {
            if (il.Item != null) {
                if (!pItemManager.AddItemToManagedRegistry(il.Item)) {
                    gwppcore.Logger.error(String.format("Item [%s] failed to register", il.toString()));
                    tResult = false;
                }
            }
        }

        return tResult;
    }

    public ItemStack getIS()
    {
        return new ItemStack(Item.getConstructedItem(), 1);
    }
    
    public ItemStack getIS(int amount)
    {
        return new ItemStack(Item.getConstructedItem(), amount);
    }

}

