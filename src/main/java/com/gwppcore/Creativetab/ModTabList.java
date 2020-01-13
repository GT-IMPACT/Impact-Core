package com.gwppcore.creativetab;

import com.gwppcore.item.ItemList;

import eu.usrv.yamcore.creativetabs.CreativeTabsManager;
import eu.usrv.yamcore.creativetabs.ModCreativeTab;
import eu.usrv.yamcore.items.ModItemManager;

public class ModTabList {

    public static String GWPPCoreTab = "tabGWPPCore";

    private ModTabList() {}

    public static void InitModTabs(CreativeTabsManager pTabManager, ModItemManager pItemManager)
    {
        pTabManager.AddCreativeTab(new ModCreativeTab(GWPPCoreTab, ItemList.GWPPCOIN.Item.getConstructedItem()));


    }


}
