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
		CustomItemList.WoodenBrickForm.set(ItemList.WoodenBrickForm.getIS());
		CustomItemList.UnfiredSearedBrick.set(ItemList.UnfiredSearedBrick.getIS());
		CustomItemList.UnfiredCokeOvenBrick.set(ItemList.UnfiredCokeOvenBrick.getIS());
		CustomItemList.UnfiredClayBrick.set(ItemList.UnfiredClayBrick.getIS());
		CustomItemList.RedstoneRedChipset.set(ItemList.RedstoneRedChipset.getIS());
		CustomItemList.RedstoneQuartzChipset.set(ItemList.RedstoneQuartzChipset.getIS());
		CustomItemList.RedstonePulsatingChipset.set(ItemList.RedstonePulsatingChipset.getIS());
		CustomItemList.RedstoneIronChipset.set(ItemList.RedstoneIronChipset.getIS());
		CustomItemList.RedstoneGoldChipset.set(ItemList.RedstoneGoldChipset.getIS());
		CustomItemList.RedstoneEmeraldChipset.set(ItemList.RedstoneEmeraldChipset.getIS());
		CustomItemList.RedstoneDiamondChipset.set(ItemList.RedstoneDiamondChipset.getIS());
	}
}
