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
	{	//LOGO GPWW
		CustomItemList.Coin.set(ItemList.Coin.getIS());
		//STEAM AGE
		CustomItemList.WoodenBrickForm.set(ItemList.WoodenBrickForm.getIS());
		CustomItemList.UnfiredSearedBrick.set(ItemList.UnfiredSearedBrick.getIS());
		CustomItemList.UnfiredCokeOvenBrick.set(ItemList.UnfiredCokeOvenBrick.getIS());
		CustomItemList.UnfiredClayBrick.set(ItemList.UnfiredClayBrick.getIS());
		//CHIPSETS
		CustomItemList.RedstoneRedChipset.set(ItemList.RedstoneRedChipset.getIS());
		CustomItemList.RedstoneQuartzChipset.set(ItemList.RedstoneQuartzChipset.getIS());
		CustomItemList.RedstonePulsatingChipset.set(ItemList.RedstonePulsatingChipset.getIS());
		CustomItemList.RedstoneIronChipset.set(ItemList.RedstoneIronChipset.getIS());
		CustomItemList.RedstoneGoldChipset.set(ItemList.RedstoneGoldChipset.getIS());
		CustomItemList.RedstoneEmeraldChipset.set(ItemList.RedstoneEmeraldChipset.getIS());
		CustomItemList.RedstoneDiamondChipset.set(ItemList.RedstoneDiamondChipset.getIS());
		//SPACE RESEARCH
		CustomItemList.packEuropa.set(ItemList.packEuropa.getIS());
		CustomItemList.packBarnardaE.set(ItemList.packBarnardaE.getIS());
		CustomItemList.packBarnardaF.set(ItemList.packBarnardaF.getIS());
		CustomItemList.packCallisto.set(ItemList.packCallisto.getIS());
		CustomItemList.packCentauriA.set(ItemList.packCentauriA.getIS());
		CustomItemList.packCeres.set(ItemList.packCeres.getIS());
		CustomItemList.packDeimos.set(ItemList.packDeimos.getIS());
		CustomItemList.packEarth.set(ItemList.packEarth.getIS());
		CustomItemList.packEris.set(ItemList.packEris.getIS());
		CustomItemList.packGanymed.set(ItemList.packGanymed.getIS());
		CustomItemList.packHaumea.set(ItemList.packHaumea.getIS());
		CustomItemList.packIapetus.set(ItemList.packIapetus.getIS());
		CustomItemList.packIo.set(ItemList.packIo.getIS());
		CustomItemList.packMakeMake.set(ItemList.packMakeMake.getIS());
		CustomItemList.packMercury.set(ItemList.packMercury.getIS());
		CustomItemList.packMoon.set(ItemList.packMoon.getIS());
		CustomItemList.packOberon.set(ItemList.packOberon.getIS());
		CustomItemList.packPhobos.set(ItemList.packPhobos.getIS());
		CustomItemList.packPluto.set(ItemList.packPluto.getIS());
		CustomItemList.packTCetiE.set(ItemList.packTCetiE.getIS());
		CustomItemList.packTitan.set(ItemList.packTitan.getIS());
		CustomItemList.packTriton.set(ItemList.packTriton.getIS());
		CustomItemList.packVegaB.set(ItemList.packVegaB.getIS());
		CustomItemList.packVenus.set(ItemList.packVenus.getIS());
		CustomItemList.packMars.set(ItemList.packMars.getIS());
		CustomItemList.packProteus.set(ItemList.packProteus.getIS());
		CustomItemList.packAsteroids.set(ItemList.packAsteroids.getIS());
		CustomItemList.spacebox1.set(ItemList.spacebox1.getIS());
		CustomItemList.spacebox2.set(ItemList.spacebox2.getIS());
		CustomItemList.spacebox3.set(ItemList.spacebox3.getIS());
		CustomItemList.spacebox4.set(ItemList.spacebox4.getIS());
		CustomItemList.spacebox5.set(ItemList.spacebox5.getIS());
		CustomItemList.spacebox6.set(ItemList.spacebox6.getIS());
		CustomItemList.spacebox7.set(ItemList.spacebox7.getIS());
		CustomItemList.spacebox8.set(ItemList.spacebox8.getIS());

	}
}
