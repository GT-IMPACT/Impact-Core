package com.impact.loader;

import static codechicken.nei.api.API.hideItem;
import static com.impact.core.impactLog.INFO;
import static com.impact.util.Utilits.BlockstackMeta;
import static gregtech.api.enums.GT_Values.W;

import com.impact.common.block.blocks.Block_Ceramic;
import com.impact.common.block.blocks.Block_Coal;
import com.impact.common.block.blocks.Block_CollisionBlocks;
import com.impact.common.block.blocks.Block_FluidTank;
import com.impact.common.block.blocks.Block_HCloud;
import com.impact.common.block.blocks.Block_IGlass;
import com.impact.common.block.blocks.Block_InsideBlocks;
import com.impact.common.block.blocks.Block_LapotronicEnergyUnit;
import com.impact.common.block.blocks.Block_Liquid;
import com.impact.common.block.blocks.Block_NqTether;
import com.impact.common.block.blocks.Block_Pattern_Space;
import com.impact.common.block.blocks.Block_Sawmill;
import com.impact.common.block.blocks.Block_SpaceElevatorTether;
import com.impact.common.block.blocks.Block_UtilBlock;
import com.impact.common.block.blocks.Core_FakeBlock;
import com.impact.common.block.itemblock.FakeBlocksItem;
import com.impact.common.item.GT_Pump.GregtechPump;
import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


public class ItemRegistery {

  public static final Block[] decorateBlock = {
      null,
      null,
      new Core_FakeBlock("FakeBlock", new String[]{
          Refstrings.MODID + ":glass/blockGB0", //white
          Refstrings.MODID + ":glass/blockGB14", //red
          Refstrings.MODID + ":glass/blockGB13", //green
          Refstrings.MODID + ":glass/blockGB11", //blue
          Refstrings.MODID + ":glass/frameGt",
      }, 2)
  };

  public static GregtechPump GTPump;
  public static Block lscLapotronicEnergyUnit;
  public static Block SawMillBlock;
  public static Block IGlassBlock;
  public static Block CoalBlock;
  public static Block UtilBlock;
  public static Block NqTetherBlock;
  public static Block InsideBlock;
  public static Block FluidTankBlock;
  public static Block CeramicBlock;
  public static Block CollisionBlock;
  public static Block SpaceElevatorBlock;
  public static Block HCloud, HMetal;
  public static Block HFluid;
  public static Block MarsStone;

  public static void run() {
    //Blocks
    GameRegistry.registerBlock(decorateBlock[2], FakeBlocksItem.class, "FakeBlock");
    INFO("[Init] Item Registery Blocks - Loaded");
    NEIHide();

    for (int i = 0; i <= 5; i++) {
      hideItem(GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsChips", 1L, i));
    }

    for (int i = 0; i <= 15; i++) {
      hideItem(GT_ModHandler.getModItem("LogisticsPipes", "item.pipeComponents", 1L, i));
    }

    //OreDictionary
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

    GregTech_API.sFrostHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
    GregTech_API.sHeatHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
    GregTech_API.sBioHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
    GregTech_API.sGasHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
    GregTech_API.sRadioHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
    GregTech_API.sElectroHazmatList
        .add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));

    GregTech_API.sFrostHazmatList
        .add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
    GregTech_API.sHeatHazmatList
        .add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
    GregTech_API.sBioHazmatList
        .add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
    GregTech_API.sGasHazmatList
        .add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
    GregTech_API.sRadioHazmatList
        .add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));
    GregTech_API.sElectroHazmatList
        .add(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W));

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

  public static void GregtechPump() {
    GTPump = new GregtechPump();
    GTPump.registerPumpType(0, "LV Hand Pump", 0, 0);
    GTPump.registerPumpType(1, "MV Hand Pump", 100000, 1);
    GTPump.registerPumpType(2, "HV Hand Pump", 400000, 2);
    GTPump.registerPumpType(3, "EV Hand Pump", 1600000, 3);
  }

  public static void registerBlocks() {
    lscLapotronicEnergyUnit = Block_LapotronicEnergyUnit.registerBlock();
    IGlassBlock = Block_IGlass.registerBlock();
    CoalBlock = Block_Coal.registerBlock();
    UtilBlock = Block_UtilBlock.registerBlock();
    SawMillBlock = Block_Sawmill.registerBlock();
    NqTetherBlock = Block_NqTether.registerBlock();
    InsideBlock = Block_InsideBlocks.registerBlock();
    CollisionBlock = Block_CollisionBlocks.registerBlock();
    FluidTankBlock = Block_FluidTank.registerBlock();
    CeramicBlock = Block_Ceramic.registerBlock();
    SpaceElevatorBlock = Block_SpaceElevatorTether.registerBlock();
    HCloud = Block_HCloud.registerBlock();
    HMetal = new Block_Pattern_Space(Material.rock, "HMetal");
    MarsStone = new Block_Pattern_Space(Material.rock, "MarsStone");
  }

  public static void NEIHide() {
    hideItem(new ItemStack(decorateBlock[2], 1, 0));
    hideItem(new ItemStack(decorateBlock[2], 1, 1));
    hideItem(new ItemStack(decorateBlock[2], 1, 2));
    hideItem(new ItemStack(decorateBlock[2], 1, 3));
    hideItem(new ItemStack(decorateBlock[2], 1, 4));
    for (int i = 0; i <= 11; i++) {
      hideItem(GT_ModHandler.getModItem("Railcraft", "cube", 1, i));
    }

  }
}