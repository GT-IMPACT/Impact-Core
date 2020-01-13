package com.gwppcore.Creativetab;

import com.gwppcore.GTHandler.CustomItemList;
import com.gwppcore.Item.ItemList;

import eu.usrv.yamcore.creativetabs.CreativeTabsManager;
import eu.usrv.yamcore.creativetabs.ModCreativeTab;
import eu.usrv.yamcore.items.ModItemManager;
import gregtech.api.GregTech_API;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.List;

public class ModTabList {

    public static String GWPPCoreTab = "tabGWPPCore";

    private ModTabList() {}

    public static void InitModTabs(CreativeTabsManager pTabManager, ModItemManager pItemManager)
    {
        pTabManager.AddCreativeTab(new ModCreativeTab(GWPPCoreTab, ItemList.GWPPCOIN.Item.getConstructedItem()));


    }


}
