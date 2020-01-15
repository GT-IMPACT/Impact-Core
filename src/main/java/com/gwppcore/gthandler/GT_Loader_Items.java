package com.gwppcore.gthandler;

import com.gwppcore.item.ItemList;
import gregtech.common.items.GT_MetaGenerated_Item_04;

public class GT_Loader_Items
{
	public void run()
	{
		GT = GT_MetaGenerated_Item_04.INSTANCE;
		registerItems();
	}

	private GT_MetaGenerated_Item_04 GT;
	
	private void registerItems()
	{
		/*=======================

		 		ID 401++

		=======================*/

		CustomItemList.Coin.set(GT.addItem(						   401,                 "GWPP Coin",        "I`m is GOD",new Object[] {}));

		CustomItemList.WoodenBrickForm.set(GT.addItem( 			   402,         "Wooden Brick Form",        "Reusable",new Object[]{}));
		CustomItemList.UnfiredSearedBrick.set(GT.addItem( 		   403,      "Unfired Seared Brick",        "",new Object[]{}));
		CustomItemList.UnfiredCokeOvenBrick.set(GT.addItem(	 	   404,   "Unfired Coke Oven Brick",        "",new Object[]{}));
		CustomItemList.UnfiredClayBrick.set(GT.addItem( 		   405,        "Unfired Clay Brick",        "",new Object[]{}));
		CustomItemList.RedstoneRedChipset.set(GT.addItem(          406,      "Redstone Red Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneQuartzChipset.set(GT.addItem(       407,   "Redstone Quartz Chipset",        "",new Object[]{}));
		CustomItemList.RedstonePulsatingChipset.set(GT.addItem(    408,"Redstone Pulsating Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneIronChipset.set(GT.addItem(         409,     "Redstone Iron Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneGoldChipset.set(GT.addItem(         410,     "Redstone Gold Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneEmeraldChipset.set(GT.addItem(      411,  "Redstone Emerald Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneDiamondChipset.set(GT.addItem( 	   412,  "Redstone Diamond Chipset",        "",new Object[]{}));

		CustomItemList.packEuropa.set(GT.addItem( 	               413,    "Pack Europa Stone Dust",        "",new Object[]{}));
		CustomItemList.packBarnardaE.set(GT.addItem(               414, "Pack BarnardaE Stone Dust",        "",new Object[]{}));
		CustomItemList.packBarnardaF.set(GT.addItem(               415, "Pack BarnardaF Stone Dust",        "",new Object[]{}));
		CustomItemList.packCallisto.set(GT.addItem(	               416,  "Pack Callisto Stone Dust",        "",new Object[]{}));
		CustomItemList.packCentauriA.set(GT.addItem(               417, "Pack CentauriA Stone Dust",        "",new Object[]{}));
		CustomItemList.packCeres.set(GT.addItem( 	               418,     "Pack Ceres Stone Dust",        "",new Object[]{}));
		CustomItemList.packDeimos.set(GT.addItem( 	               419,    "Pack Deimos Stone Dust",        "",new Object[]{}));
		CustomItemList.packEarth.set(GT.addItem( 	               420,     "Pack Earth Stone Dust",        "",new Object[]{}));
		CustomItemList.packEris.set(GT.addItem( 	               421,   "Pack Miranda Stone Dust",        "",new Object[]{}));
		CustomItemList.packGanymed.set(GT.addItem( 	               422,   "Pack Ganymed Stone Dust",        "",new Object[]{}));
		CustomItemList.packHaumea.set(GT.addItem( 	               423,    "Pack Haumea Stone Dust",        "",new Object[]{}));
		CustomItemList.packIapetus.set(GT.addItem( 	               424, "Pack Enceladus Stone Dust",        "",new Object[]{}));
		CustomItemList.packIo.set(GT.addItem(                      425,        "Pack Io Stone Dust",        "",new Object[]{}));
		CustomItemList.packMakeMake.set(GT.addItem(                426, " Pack MakeMake Stone Dust",        "",new Object[]{}));
		CustomItemList.packMercury.set(GT.addItem(                 427,   "Pack Mercury Stone Dust",        "",new Object[]{}));
		CustomItemList.packProteus.set(GT.addItem(                 428,   "Pack Proteus Stone Dust",        "",new Object[]{}));
		CustomItemList.packOberon.set(GT.addItem(                  429,    "Pack Oberon Stone Dust",        "",new Object[]{}));
		CustomItemList.packPhobos.set(GT.addItem(                  430,    "Pack Phobos Stone Dust",        "",new Object[]{}));
		CustomItemList.packPluto.set(GT.addItem(                   431,     "Pack Pluto Stone Dust",        "",new Object[]{}));
		CustomItemList.packTCetiE.set(GT.addItem(                  432,    "Pack TCetiE Stone Dust",        "",new Object[]{}));
		CustomItemList.packTitan.set(GT.addItem(                   433,     "Pack Titan Stone Dust",        "",new Object[]{}));
		CustomItemList.packTriton.set(GT.addItem(                  434,    "Pack Triton Stone Dust",        "",new Object[]{}));
		CustomItemList.packVegaB.set(GT.addItem(                   435,     "Pack VegaB Stone Dust",        "",new Object[]{}));
		CustomItemList.packVenus.set(GT.addItem(                   436,     "Pack Venus Stone Dust",        "",new Object[]{}));
		CustomItemList.packMoon.set(GT.addItem(                    437,      "Pack Moon Stone Dust",        "",new Object[]{}));
		CustomItemList.packMars.set(GT.addItem(                    438,      "Pack Mars Stone Dust",        "",new Object[]{}));
		CustomItemList.packAsteroids.set(GT.addItem(               439, "Pack Asteroids Stone Dust",        "",new Object[]{}));
		CustomItemList.spacebox1.set(GT.addItem( 	               440, 		  "Space Box Tier 1",        "Use for crafting Rocket Tier-1",new Object[]{}));
		CustomItemList.spacebox2.set(GT.addItem( 	               441, 		  "Space Box Tier 2",        "Use for crafting Rocket Tier-2",new Object[]{}));
		CustomItemList.spacebox3.set(GT.addItem( 	               442, 		  "Space Box Tier 3",        "Use for crafting Rocket Tier-3",new Object[]{}));
		CustomItemList.spacebox4.set(GT.addItem( 	               443, 		  "Space Box Tier 4",        "Use for crafting Rocket Tier-4",new Object[]{}));
		CustomItemList.spacebox5.set(GT.addItem( 	               444, 		  "Space Box Tier 5",        "Use for crafting Rocket Tier-5",new Object[]{}));
		CustomItemList.spacebox6.set(GT.addItem( 	               445, 		  "Space Box Tier 6",        "Use for crafting Rocket Tier-6",new Object[]{}));
		CustomItemList.spacebox7.set(GT.addItem( 	               446, 		  "Space Box Tier 7",        "Use for crafting Rocket Tier-7",new Object[]{}));
		CustomItemList.spacebox8.set(GT.addItem( 	               447, 		  "Space Box Tier 8",        "Use for crafting Rocket Tier-8",new Object[]{}));


	}
}
