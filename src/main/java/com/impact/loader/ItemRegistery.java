package com.impact.loader;

import com.impact.common.block.blocks.*;
import com.impact.common.block.itemblock.FakeBlocksItem;
import com.impact.common.item.GT_Pump.GregtechPump;
import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static codechicken.nei.api.API.hideItem;
import static com.impact.core.impactLog.INFO;
import static com.impact.util.Utilits.BlockstackMeta;
import static gregtech.api.enums.GT_Values.W;


public class ItemRegistery {

    public static final Block[] decorateBlock = {
            null,
            null,
            new Core_FakeBlock("FakeBlock", new String[]{
                    Refstrings.MODID + ":glass/blockGB0",
                    Refstrings.MODID + ":glass/blockGB14",
                    Refstrings.MODID + ":glass/blockGB13",
                    Refstrings.MODID + ":glass/blockGB11",
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

    public static void run() {
        //Blocks
        GameRegistry.registerBlock(decorateBlock[2], FakeBlocksItem.class, "FakeBlock");
        INFO("[Init] Item Registery Blocks - Loaded");

        //NEI hide
        hideItem(new ItemStack(decorateBlock[2], 1, 0));
        hideItem(new ItemStack(decorateBlock[2], 1, 1));
        hideItem(new ItemStack(decorateBlock[2], 1, 2));
        hideItem(new ItemStack(decorateBlock[2], 1, 3));
        hideItem(new ItemStack(decorateBlock[2], 1, 4));
        INFO("[Init] Item Registery Hide Fake BLocks - Loaded");

        for (int i = 0; i <= 5; i++)
            hideItem(GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsChips", 1L, i));

        for (int i = 0; i <= 15; i++)
            hideItem(GT_ModHandler.getModItem("LogisticsPipes", "item.pipeComponents", 1L, i));

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
        GregTech_API.sElectroHazmatList.add(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, W));
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
    }
}