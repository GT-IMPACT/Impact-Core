package com.gwppcore.gthandler;

import com.gwppcore.item.ItemList;
import gregtech.common.items.GT_MetaGenerated_Item_01;

public class GT_Loader_Items
{
	public void run()
	{
		GT = GT_MetaGenerated_Item_01.INSTANCE;
		registerItems();
	}

	private GT_MetaGenerated_Item_01 GT;
	
	private void registerItems()
	{
		CustomItemList.Coin.set(ItemList.Coin.getIS());

		
	}
}
