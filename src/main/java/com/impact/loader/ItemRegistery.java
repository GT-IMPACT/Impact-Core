package com.impact.loader;

import com.impact.common.armor.MaskOfVision;
import com.impact.common.block.blocks.*;
import com.impact.common.block.itemblock.FakeBlocksItem;
import com.impact.common.item.Core_List_Items;
import com.impact.core.Refstrings;
import com.impact.mods.gregtech.items.tools.ConstructionLaser;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static codechicken.nei.api.API.hideItem;
import static com.impact.core.impactLog.INFO;
import static com.impact.mods.gregtech.GT_ItemList.ConstructLaser;
import static com.impact.util.Utilits.BlockstackMeta;
import static gregtech.api.enums.GT_Values.W;


public class ItemRegistery {
	
	public static final Block[] decorateBlock = {
			null, null,
			new Core_FakeBlock("FakeBlock", new String[]{
					Refstrings.MODID + ":glass/blockGB0", //white
					Refstrings.MODID + ":glass/blockGB14", //red
					Refstrings.MODID + ":glass/blockGB13", //green
					Refstrings.MODID + ":glass/blockGB11", //blue
					Refstrings.MODID + ":glass/frameGt",
			}, 2)
	};
	
	public static Block lscLapotronicEnergyUnit, SawMillBlock, IGlassBlock, CoalBlock, UtilBlock,
			NqTetherBlock, InsideBlock, FluidTankBlock, CeramicBlock, CollisionBlock,
			SpaceElevatorBlock, placedItem, MPSystem, MPTransducer, Wind_rotor, TheWind, DryingRack;
	public static Item maskVision;
	
	public static void run() {
		NEIHide();
		registerOreDict();
		registerHazmat();
	}
	
	private static void registerOreDict() {
		OreDictionary.registerOre("concrete", BlockstackMeta(UtilBlock, 0));
		
		OreDictionary.registerOre("blockCokeCoal", BlockstackMeta(CoalBlock, 0));
		OreDictionary.registerOre("CompressedCoalCoke", BlockstackMeta(CoalBlock, 1));
		OreDictionary.registerOre("DoubleCompressedCoalCoke", BlockstackMeta(CoalBlock, 2));
		OreDictionary.registerOre("TripleCompressedCoalCoke", BlockstackMeta(CoalBlock, 3));
		OreDictionary.registerOre("QuadrupleCompressedCoalCoke", BlockstackMeta(CoalBlock, 4));
		
		OreDictionary.registerOre("CompressedCharcoal", BlockstackMeta(CoalBlock, 5));
		OreDictionary.registerOre("DoubleCompressedCharcoal", BlockstackMeta(CoalBlock, 6));
		OreDictionary.registerOre("TripleCompressedCharcoal", BlockstackMeta(CoalBlock, 7));
		OreDictionary.registerOre("QuadrupleCompressedCharcoal", BlockstackMeta(CoalBlock, 8));
		
		OreDictionary.registerOre("CompressedCoal", BlockstackMeta(CoalBlock, 9));
		OreDictionary.registerOre("DoubleCompressedCoal", BlockstackMeta(CoalBlock, 10));
		OreDictionary.registerOre("TripleCompressedCoal", BlockstackMeta(CoalBlock, 11));
		OreDictionary.registerOre("QuadrupleCompressedCoal", BlockstackMeta(CoalBlock, 12));
		INFO("[Init] Item Registery Blocks OreDict - Loaded");
	}
	
	private static void registerHazmat() {
		GregTech_API.sFrostHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
		GregTech_API.sHeatHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
		GregTech_API.sBioHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
		GregTech_API.sGasHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
		GregTech_API.sRadioHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
		GregTech_API.sElectroHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
		
		GregTech_API.sFrostHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
		GregTech_API.sHeatHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
		GregTech_API.sBioHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
		GregTech_API.sGasHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
		GregTech_API.sRadioHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
		GregTech_API.sElectroHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
		
		GregTech_API.sFrostHazmatList.add(GT_ModHandler.getIC2Item("nanoHelmet", 1, W));
		GregTech_API.sHeatHazmatList.add(GT_ModHandler.getIC2Item("nanoHelmet", 1, W));
		GregTech_API.sBioHazmatList.add(GT_ModHandler.getIC2Item("nanoHelmet", 1, W));
		GregTech_API.sGasHazmatList.add(GT_ModHandler.getIC2Item("nanoHelmet", 1, W));
		GregTech_API.sRadioHazmatList.add(GT_ModHandler.getIC2Item("nanoHelmet", 1, W));
		GregTech_API.sElectroHazmatList.add(GT_ModHandler.getIC2Item("nanoHelmet", 1, W));
		
		GregTech_API.sFrostHazmatList.add(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, W));
		GregTech_API.sHeatHazmatList.add(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, W));
		GregTech_API.sBioHazmatList.add(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, W));
		GregTech_API.sGasHazmatList.add(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, W));
		GregTech_API.sRadioHazmatList.add(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, W));
		GregTech_API.sElectroHazmatList.add(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, W));
		
		GregTech_API.sFrostHazmatList.add(GT_ModHandler.getIC2Item("nanoLeggings", 1, W));
		GregTech_API.sHeatHazmatList.add(GT_ModHandler.getIC2Item("nanoLeggings", 1, W));
		GregTech_API.sBioHazmatList.add(GT_ModHandler.getIC2Item("nanoLeggings", 1, W));
		GregTech_API.sGasHazmatList.add(GT_ModHandler.getIC2Item("nanoLeggings", 1, W));
		GregTech_API.sRadioHazmatList.add(GT_ModHandler.getIC2Item("nanoLeggings", 1, W));
		GregTech_API.sElectroHazmatList.add(GT_ModHandler.getIC2Item("nanoLeggings", 1, W));
		
		GregTech_API.sFrostHazmatList.add(GT_ModHandler.getIC2Item("nanoBoots", 1, W));
		GregTech_API.sHeatHazmatList.add(GT_ModHandler.getIC2Item("nanoBoots", 1, W));
		GregTech_API.sBioHazmatList.add(GT_ModHandler.getIC2Item("nanoBoots", 1, W));
		GregTech_API.sGasHazmatList.add(GT_ModHandler.getIC2Item("nanoBoots", 1, W));
		GregTech_API.sRadioHazmatList.add(GT_ModHandler.getIC2Item("nanoBoots", 1, W));
		GregTech_API.sElectroHazmatList.add(GT_ModHandler.getIC2Item("nanoBoots", 1, W));
		INFO("[Init] Hazmat Additions - Loaded");
	}
	
	public static void registerBlocks() {
		GameRegistry.registerBlock(decorateBlock[2], FakeBlocksItem.class, "FakeBlock");
		lscLapotronicEnergyUnit = Block_LapotronicEnergyUnit.registerBlock();
		IGlassBlock             = Block_IGlass.registerBlock();
		CoalBlock               = Block_Coal.registerBlock();
		UtilBlock               = Block_UtilBlock.registerBlock();
		SawMillBlock            = Block_Sawmill.registerBlock();
		NqTetherBlock           = Block_NqTether.registerBlock();
		InsideBlock             = Block_InsideBlocks.registerBlock();
		CollisionBlock          = Block_CollisionBlocks.registerBlock();
		FluidTankBlock          = Block_FluidTank.registerBlock();
		CeramicBlock            = Block_Ceramic.registerBlock();
		SpaceElevatorBlock      = Block_SpaceElevatorTether.registerBlock();
		placedItem              = new PlacedItem();
		MPSystem                = Block_MatrixSystem.registerBlock();
		MPTransducer            = Block_MatrixTransducer.registerBlock();
		Wind_rotor              = Block_Wind.registerBlock();
		TheWind                 = Block_TheMill.registerBlock();
		DryingRack              = Block_DryingRack.registerBlock();
		maskVision              = MaskOfVision.registerItem();
		INFO("[Init] Item Registery Blocks - Loaded");
	}
	
	public static void NEIHide() {
		hideItem(new ItemStack(placedItem));
		
		for (int i = 0; i < 5; i++) {
			hideItem(new ItemStack(decorateBlock[2], 1, i));
		}
		for (Core_List_Items value : Core_List_Items.values()) {
			if (value.hideNEI) {
				hideItem(value.get(1));
			}
		}
		for (int i = 0; i <= 11; i++) {
			hideItem(GT_ModHandler.getModItem("Railcraft", "cube", 1, i));
		}
		for (int i = 0; i <= 5; i++) {
			hideItem(GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsChips", 1L, i));
		}
		for (int i = 0; i <= 15; i++) {
			hideItem(GT_ModHandler.getModItem("LogisticsPipes", "item.pipeComponents", 1L, i));
		}
		
	}
}