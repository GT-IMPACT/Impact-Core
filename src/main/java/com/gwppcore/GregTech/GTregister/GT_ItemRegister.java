package com.gwppcore.GregTech.GTregister;


import gregtech.common.items.GT_MetaGenerated_Item_04;

public class GT_ItemRegister
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

		GT_ItemList.Coin.set(GT.addItem(						   					401,                 "GWPP Coin",        					"I`m is GOD",new Object[] {}));
		/* === STEAM AGE === */
		GT_ItemList.WoodenBrickForm.set(GT.addItem( 			   					402,         "Wooden Brick Form",        					"Reusable",new Object[]{}));
		GT_ItemList.UnfiredSearedBrick.set(GT.addItem( 		   					403,      "Unfired Seared Brick",        					"",new Object[]{}));
		GT_ItemList.UnfiredCokeOvenBrick.set(GT.addItem(	 	   					404,   "Unfired Coke Oven Brick",        					"",new Object[]{}));
		GT_ItemList.UnfiredClayBrick.set(GT.addItem( 		   					405,        "Unfired Clay Brick",        					"",new Object[]{}));
		GT_ItemList.CokeOvenBrick.set(GT.addItem( 		   	   					406,           "Coke Oven Brick",        					"",new Object[]{}));

		/* === CHIPSETS === */
		GT_ItemList.EngineeringProcessorFluidDiamondCore.set(GT.addItem( 		471,"Engineering Processor Fluid Diamond Core",			"",new Object[]{}));
		GT_ItemList.EngineeringProcessorFluidEmeraldCore.set(GT.addItem( 		472,"Engineering Processor Fluid Emerald Core",			"",new Object[]{}));
		GT_ItemList.EngineeringProcessorFluidGoldCore.set(GT.addItem( 			473,"Engineering Processor Fluid Gold Core",				"",new Object[]{}));
		GT_ItemList.EngineeringProcessorItemAdvEmeraldCore.set(GT.addItem( 		474,"Engineering Processor Item Adv Emerald Core",		"",new Object[]{}));
		GT_ItemList.EngineeringProcessorItemDiamondCore.set(GT.addItem( 			475,"Engineering Processor Item Diamond Core",			"",new Object[]{}));
		GT_ItemList.EngineeringProcessorItemEmeraldCore.set(GT.addItem( 			476,"Engineering Processor Item Emerald Core",			"",new Object[]{}));
		GT_ItemList.EngineeringProcessorSpatialPulsatingCore.set(GT.addItem( 	477,"Engineering Processor Spatial Pulsating Core",		"",new Object[]{}));
		GT_ItemList.LogicProcessorItemGoldCore.set(GT.addItem( 					478,"Logic Processor Item Gold Core",						"",new Object[]{}));
		GT_ItemList.EngravedDiamondCrystalChip.set(GT.addItem( 					479,"Engraved Diamond Crystal Chip",						"",new Object[]{}));
		GT_ItemList.EngravedEnergyChip.set(GT.addItem( 							480,"Engraved Energy Chip",								"",new Object[]{}));
		GT_ItemList.EngravedGoldChip.set(GT.addItem( 							481,"Engraved Gold Chip",									"",new Object[]{}));
		GT_ItemList.EngravedQuantumChip.set(GT.addItem( 							482,"Engraved Quantum Chip",								"",new Object[]{}));
		GT_ItemList.GeneticCircuit.set(GT.addItem( 								483,"Genetic Circuit",									"",new Object[]{}));
		GT_ItemList.EnvironmentalCircuit.set(GT.addItem( 						484,"Environmental Circuit",								"",new Object[]{}));
		GT_ItemList.PulsatingSpatialCoreChip.set(GT.addItem( 					485,"Pulsating Spatial Core Chip",						"",new Object[]{}));
		GT_ItemList.GoldFluidCoreChip.set(GT.addItem( 							486,"Gold Fluid Core Chip",								"",new Object[]{}));
		GT_ItemList.GoldCoreChip.set(GT.addItem( 								487,"Gold Core Chip",										"",new Object[]{}));
		GT_ItemList.DiamondCoreChip.set(GT.addItem( 								488,"Diamond Core Chip",									"",new Object[]{}));
		GT_ItemList.DiamondFluidCoreChip.set(GT.addItem( 						489,"Diamond Fluid Core Chip",							"",new Object[]{}));
		GT_ItemList.EmeraldAdvancedCoreChip.set(GT.addItem(						490,"Emerald Advanced Core Chip",							"",new Object[]{}));
		GT_ItemList.EmeraldAdvancedFluidCoreChip.set(GT.addItem( 				491,"Emerald Advanced Fluid Core Chip",					"",new Object[]{}));
		GT_ItemList.EmeraldHighAdvancedCoreChip.set(GT.addItem( 					492,"Emerald High Advanced Core Chip",					"",new Object[]{}));
		GT_ItemList.RedstoneRedChipset.set(GT.addItem(          					493,      "Redstone Red Chipset",        					"",new Object[]{}));
		GT_ItemList.RedstoneQuartzChipset.set(GT.addItem(       					494,   "Redstone Quartz Chipset",        					"",new Object[]{}));
		GT_ItemList.RedstonePulsatingChipset.set(GT.addItem(    					495,"Redstone Pulsating Chipset",        					"",new Object[]{}));
		GT_ItemList.RedstoneIronChipset.set(GT.addItem(         					496,     "Redstone Iron Chipset",        					"",new Object[]{}));
		GT_ItemList.RedstoneGoldChipset.set(GT.addItem(         					497,     "Redstone Gold Chipset",        					"",new Object[]{}));
		GT_ItemList.RedstoneEmeraldChipset.set(GT.addItem(      					498,  "Redstone Emerald Chipset",        					"",new Object[]{}));
		GT_ItemList.RedstoneDiamondChipset.set(GT.addItem( 	   					499,  "Redstone Diamond Chipset",        					"",new Object[]{}));
		/* === SPACE === */
		GT_ItemList.packEuropa.set(GT.addItem( 	               					500,    "Pack Europa Stone Dust",							"",new Object[]{}));
		GT_ItemList.packBarnardaE.set(GT.addItem(               					501, "Pack BarnardaE Stone Dust",							"",new Object[]{}));
		GT_ItemList.packBarnardaF.set(GT.addItem(               					502, "Pack BarnardaF Stone Dust",							"",new Object[]{}));
		GT_ItemList.packCallisto.set(GT.addItem(	               					503,  "Pack Callisto Stone Dust",							"",new Object[]{}));
		GT_ItemList.packCentauriA.set(GT.addItem(               					504, "Pack CentauriA Stone Dust",							"",new Object[]{}));
		GT_ItemList.packCeres.set(GT.addItem( 	               					505,     "Pack Ceres Stone Dust",							"",new Object[]{}));
		GT_ItemList.packDeimos.set(GT.addItem( 	               					506,    "Pack Deimos Stone Dust",							"",new Object[]{}));
		GT_ItemList.packEarth.set(GT.addItem( 	               					507,     "Pack Earth Stone Dust",							"",new Object[]{}));
		GT_ItemList.packEris.set(GT.addItem( 	               					508,   "Pack Miranda Stone Dust",							"",new Object[]{}));
		GT_ItemList.packGanymed.set(GT.addItem( 	               					509,   "Pack Ganymed Stone Dust",							"",new Object[]{}));
		GT_ItemList.packHaumea.set(GT.addItem( 	               					510,    "Pack Haumea Stone Dust",							"",new Object[]{}));
		GT_ItemList.packIapetus.set(GT.addItem( 	               					511, "Pack Enceladus Stone Dust",							"",new Object[]{}));
		GT_ItemList.packIo.set(GT.addItem(                      					512,        "Pack Io Stone Dust",							"",new Object[]{}));
		GT_ItemList.packMakeMake.set(GT.addItem(                					513, " Pack MakeMake Stone Dust",							"",new Object[]{}));
		GT_ItemList.packMercury.set(GT.addItem(                 					514,   "Pack Mercury Stone Dust",							"",new Object[]{}));
		GT_ItemList.packProteus.set(GT.addItem(                 					515,   "Pack Proteus Stone Dust",							"",new Object[]{}));
		GT_ItemList.packOberon.set(GT.addItem(                  					516,    "Pack Oberon Stone Dust",							"",new Object[]{}));
		GT_ItemList.packPhobos.set(GT.addItem(                  					517,    "Pack Phobos Stone Dust",							"",new Object[]{}));
		GT_ItemList.packPluto.set(GT.addItem(                   					518,     "Pack Pluto Stone Dust",							"",new Object[]{}));
		GT_ItemList.packTCetiE.set(GT.addItem(                  					519,    "Pack TCetiE Stone Dust",							"",new Object[]{}));
		GT_ItemList.packTitan.set(GT.addItem(                   					520,     "Pack Titan Stone Dust",							"",new Object[]{}));
		GT_ItemList.packTriton.set(GT.addItem(                  					521,    "Pack Triton Stone Dust",							"",new Object[]{}));
		GT_ItemList.packVegaB.set(GT.addItem(                   					522,     "Pack VegaB Stone Dust",							"",new Object[]{}));
		GT_ItemList.packVenus.set(GT.addItem(                   					523,     "Pack Venus Stone Dust",							"",new Object[]{}));
		GT_ItemList.packMoon.set(GT.addItem(                    					524,      "Pack Moon Stone Dust",							"",new Object[]{}));
		GT_ItemList.packMars.set(GT.addItem(                    					525,      "Pack Mars Stone Dust",							"",new Object[]{}));
		GT_ItemList.packAsteroids.set(GT.addItem(               					526, "Pack Asteroids Stone Dust",							"",new Object[]{}));
		GT_ItemList.spacebox1.set(GT.addItem( 	               					527, 		  "Space Box Tier 1",         					"Use for crafting Rocket Tier-1",new Object[]{}));
		GT_ItemList.spacebox2.set(GT.addItem( 	               					528, 		  "Space Box Tier 2",         					"Use for crafting Rocket Tier-2",new Object[]{}));
		GT_ItemList.spacebox3.set(GT.addItem( 	               					529, 		  "Space Box Tier 3",         					"Use for crafting Rocket Tier-3",new Object[]{}));
		GT_ItemList.spacebox4.set(GT.addItem( 	               					530, 		  "Space Box Tier 4",         					"Use for crafting Rocket Tier-4",new Object[]{}));
		GT_ItemList.spacebox5.set(GT.addItem( 	               					531, 		  "Space Box Tier 5",         					"Use for crafting Rocket Tier-5",new Object[]{}));
		GT_ItemList.spacebox6.set(GT.addItem( 	               					532, 		  "Space Box Tier 6",         					"Use for crafting Rocket Tier-6",new Object[]{}));
		GT_ItemList.spacebox7.set(GT.addItem( 	               					533, 		  "Space Box Tier 7",         					"Use for crafting Rocket Tier-7",new Object[]{}));
		GT_ItemList.spacebox8.set(GT.addItem( 	               					534, 		  "Space Box Tier 8",         					"Use for crafting Rocket Tier-8",new Object[]{}));
	}
}
