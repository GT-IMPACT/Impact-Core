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

        //SPACE RESEARCH
    packEuropa(new ModSimpleBaseItem("packEuropa",ModTabList.GWppTab)),
    packBarnardaE(new ModSimpleBaseItem("packBarnardaE",ModTabList.GWppTab)),
    packBarnardaF(new ModSimpleBaseItem("packBarnardaF",ModTabList.GWppTab)),
    packCallisto(new ModSimpleBaseItem("packCallisto",ModTabList.GWppTab)),
    packCentauriA(new ModSimpleBaseItem("packCentauriA",ModTabList.GWppTab)),
    packCeres(new ModSimpleBaseItem("packCeres",ModTabList.GWppTab)),
    packDeimos(new ModSimpleBaseItem("packDeimos",ModTabList.GWppTab)),
    packEarth(new ModSimpleBaseItem("packEarth",ModTabList.GWppTab)),
    packEris(new ModSimpleBaseItem("packEris",ModTabList.GWppTab)),
    packGanymed(new ModSimpleBaseItem("packGanymed",ModTabList.GWppTab)),
    packHaumea(new ModSimpleBaseItem("packHaumea",ModTabList.GWppTab)),
    packIapetus(new ModSimpleBaseItem("packIapetus",ModTabList.GWppTab)),
    packIo(new ModSimpleBaseItem("packIo",ModTabList.GWppTab)),
    packMakeMake(new ModSimpleBaseItem("packMakeMake",ModTabList.GWppTab)),
    packMercury(new ModSimpleBaseItem("packMercury",ModTabList.GWppTab)),
    packMoon(new ModSimpleBaseItem("packMoon",ModTabList.GWppTab)),
    packOberon(new ModSimpleBaseItem("packOberon",ModTabList.GWppTab)),
    packPhobos(new ModSimpleBaseItem("packPhobos",ModTabList.GWppTab)),
    packPluto(new ModSimpleBaseItem("packPluto",ModTabList.GWppTab)),
    packTCetiE(new ModSimpleBaseItem("packTCetiE",ModTabList.GWppTab)),
    packTitan(new ModSimpleBaseItem("packTitan",ModTabList.GWppTab)),
    packTriton(new ModSimpleBaseItem("packTriton",ModTabList.GWppTab)),
    packVegaB(new ModSimpleBaseItem("packVegaB",ModTabList.GWppTab)),
    packVenus(new ModSimpleBaseItem("packVenus",ModTabList.GWppTab)),
    packMars(new ModSimpleBaseItem("packMars",ModTabList.GWppTab)),
    packProteus(new ModSimpleBaseItem("packProteus",ModTabList.GWppTab)),
    packAsteroids(new ModSimpleBaseItem("packAsteroids",ModTabList.GWppTab)),
    spacebox1(new ModSimpleBaseItem("spacebox1",ModTabList.GWppTab)),
    spacebox2(new ModSimpleBaseItem("spacebox2",ModTabList.GWppTab)),
    spacebox3(new ModSimpleBaseItem("spacebox3",ModTabList.GWppTab)),
    spacebox4(new ModSimpleBaseItem("spacebox4",ModTabList.GWppTab)),
    spacebox5(new ModSimpleBaseItem("spacebox5",ModTabList.GWppTab)),
    spacebox6(new ModSimpleBaseItem("spacebox6",ModTabList.GWppTab)),
    spacebox7(new ModSimpleBaseItem("spacebox7",ModTabList.GWppTab)),
    spacebox8(new ModSimpleBaseItem("spacebox8",ModTabList.GWppTab)),

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

