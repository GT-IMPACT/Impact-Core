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
		CustomItemList.CokeOvenBrick.set(GT.addItem( 		   	   406,           "Coke Oven Brick",        "",new Object[]{}));

		CustomItemList.RedstoneRedChipset.set(GT.addItem(          493,      "Redstone Red Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneQuartzChipset.set(GT.addItem(       494,   "Redstone Quartz Chipset",        "",new Object[]{}));
		CustomItemList.RedstonePulsatingChipset.set(GT.addItem(    495,"Redstone Pulsating Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneIronChipset.set(GT.addItem(         496,     "Redstone Iron Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneGoldChipset.set(GT.addItem(         497,     "Redstone Gold Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneEmeraldChipset.set(GT.addItem(      498,  "Redstone Emerald Chipset",        "",new Object[]{}));
		CustomItemList.RedstoneDiamondChipset.set(GT.addItem( 	   499,  "Redstone Diamond Chipset",        "",new Object[]{}));

		CustomItemList.packEuropa.set(GT.addItem( 	               500,    "Pack Europa Stone Dust",        "",new Object[]{}));
		CustomItemList.packBarnardaE.set(GT.addItem(               501, "Pack BarnardaE Stone Dust",        "",new Object[]{}));
		CustomItemList.packBarnardaF.set(GT.addItem(               502, "Pack BarnardaF Stone Dust",        "",new Object[]{}));
		CustomItemList.packCallisto.set(GT.addItem(	               503,  "Pack Callisto Stone Dust",        "",new Object[]{}));
		CustomItemList.packCentauriA.set(GT.addItem(               504, "Pack CentauriA Stone Dust",        "",new Object[]{}));
		CustomItemList.packCeres.set(GT.addItem( 	               505,     "Pack Ceres Stone Dust",        "",new Object[]{}));
		CustomItemList.packDeimos.set(GT.addItem( 	               506,    "Pack Deimos Stone Dust",        "",new Object[]{}));
		CustomItemList.packEarth.set(GT.addItem( 	               507,     "Pack Earth Stone Dust",        "",new Object[]{}));
		CustomItemList.packEris.set(GT.addItem( 	               508,   "Pack Miranda Stone Dust",        "",new Object[]{}));
		CustomItemList.packGanymed.set(GT.addItem( 	               509,   "Pack Ganymed Stone Dust",        "",new Object[]{}));
		CustomItemList.packHaumea.set(GT.addItem( 	               510,    "Pack Haumea Stone Dust",        "",new Object[]{}));
		CustomItemList.packIapetus.set(GT.addItem( 	               511, "Pack Enceladus Stone Dust",        "",new Object[]{}));
		CustomItemList.packIo.set(GT.addItem(                      512,        "Pack Io Stone Dust",        "",new Object[]{}));
		CustomItemList.packMakeMake.set(GT.addItem(                513, " Pack MakeMake Stone Dust",        "",new Object[]{}));
		CustomItemList.packMercury.set(GT.addItem(                 514,   "Pack Mercury Stone Dust",        "",new Object[]{}));
		CustomItemList.packProteus.set(GT.addItem(                 515,   "Pack Proteus Stone Dust",        "",new Object[]{}));
		CustomItemList.packOberon.set(GT.addItem(                  516,    "Pack Oberon Stone Dust",        "",new Object[]{}));
		CustomItemList.packPhobos.set(GT.addItem(                  517,    "Pack Phobos Stone Dust",        "",new Object[]{}));
		CustomItemList.packPluto.set(GT.addItem(                   518,     "Pack Pluto Stone Dust",        "",new Object[]{}));
		CustomItemList.packTCetiE.set(GT.addItem(                  519,    "Pack TCetiE Stone Dust",        "",new Object[]{}));
		CustomItemList.packTitan.set(GT.addItem(                   520,     "Pack Titan Stone Dust",        "",new Object[]{}));
		CustomItemList.packTriton.set(GT.addItem(                  521,    "Pack Triton Stone Dust",        "",new Object[]{}));
		CustomItemList.packVegaB.set(GT.addItem(                   522,     "Pack VegaB Stone Dust",        "",new Object[]{}));
		CustomItemList.packVenus.set(GT.addItem(                   523,     "Pack Venus Stone Dust",        "",new Object[]{}));
		CustomItemList.packMoon.set(GT.addItem(                    524,      "Pack Moon Stone Dust",        "",new Object[]{}));
		CustomItemList.packMars.set(GT.addItem(                    525,      "Pack Mars Stone Dust",        "",new Object[]{}));
		CustomItemList.packAsteroids.set(GT.addItem(               526, "Pack Asteroids Stone Dust",        "",new Object[]{}));
		CustomItemList.spacebox1.set(GT.addItem( 	               527, 		  "Space Box Tier 1",        "Use for crafting Rocket Tier-1",new Object[]{}));
		CustomItemList.spacebox2.set(GT.addItem( 	               528, 		  "Space Box Tier 2",        "Use for crafting Rocket Tier-2",new Object[]{}));
		CustomItemList.spacebox3.set(GT.addItem( 	               529, 		  "Space Box Tier 3",        "Use for crafting Rocket Tier-3",new Object[]{}));
		CustomItemList.spacebox4.set(GT.addItem( 	               530, 		  "Space Box Tier 4",        "Use for crafting Rocket Tier-4",new Object[]{}));
		CustomItemList.spacebox5.set(GT.addItem( 	               531, 		  "Space Box Tier 5",        "Use for crafting Rocket Tier-5",new Object[]{}));
		CustomItemList.spacebox6.set(GT.addItem( 	               532, 		  "Space Box Tier 6",        "Use for crafting Rocket Tier-6",new Object[]{}));
		CustomItemList.spacebox7.set(GT.addItem( 	               533, 		  "Space Box Tier 7",        "Use for crafting Rocket Tier-7",new Object[]{}));
		CustomItemList.spacebox8.set(GT.addItem( 	               534, 		  "Space Box Tier 8",        "Use for crafting Rocket Tier-8",new Object[]{}));


	}
}
