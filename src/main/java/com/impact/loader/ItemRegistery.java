package com.impact.loader;

import com.impact.System.Refstrings;
import com.impact.block.blocks.*;
import com.impact.block.itemblock.BufferItem;
import com.impact.block.itemblock.DecorateBlocksItem;
import com.impact.block.itemblock.FakeBlocksItem;
import com.impact.block.itemblock.GlassBlocksItem;
import com.impact.item.GT_Pump.GregtechPump;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static codechicken.nei.api.API.hideItem;
import static com.impact.System.impactLog.INFO;
import static com.impact.util.Utilits.BlockstackMeta;


public class ItemRegistery {

    public static final Block[] decorateBlock = {
            new Core_Blocks("DecorateBlock", new String[]{
                    null,
            }, 0),
            new Core_Blocks("BufferCasing", new String[]{
                    null
            }, 1),
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

        OreDictionary.registerOre("blockCokeCoal",              BlockstackMeta(CoalBlock, 0));
        OreDictionary.registerOre("CompressedCoalCoke",         BlockstackMeta(CoalBlock, 1));
        OreDictionary.registerOre("DoubleCompressedCoalCoke",   BlockstackMeta(CoalBlock, 2));
        OreDictionary.registerOre("TripleCompressedCoalCoke",   BlockstackMeta(CoalBlock, 3));
        OreDictionary.registerOre("QuadrupleCompressedCoalCoke",BlockstackMeta(CoalBlock, 4));

        OreDictionary.registerOre("CompressedCharcoal",         BlockstackMeta(CoalBlock, 5));
        OreDictionary.registerOre("DoubleCompressedCharcoal",   BlockstackMeta(CoalBlock, 6));
        OreDictionary.registerOre("TripleCompressedCharcoal",   BlockstackMeta(CoalBlock, 7));
        OreDictionary.registerOre("QuadrupleCompressedCharcoal",BlockstackMeta(CoalBlock, 8));

        OreDictionary.registerOre("CompressedCoal",             BlockstackMeta(CoalBlock, 9));
        OreDictionary.registerOre("DoubleCompressedCoal",       BlockstackMeta(CoalBlock, 10));
        OreDictionary.registerOre("TripleCompressedCoal",       BlockstackMeta(CoalBlock, 11));
        OreDictionary.registerOre("QuadrupleCompressedCoal",    BlockstackMeta(CoalBlock, 12));
        INFO("[Init] Item Registery Blocks OreDict - Loaded");
    }

    public static void GregtechPump() {
        GTPump = new GregtechPump();
        GTPump.registerPumpType(0, "LV Hand Pump", 0, 0);
        GTPump.registerPumpType(1, "MV Hand Pump", 100000, 1);
        GTPump.registerPumpType(2, "HV Hand Pump", 400000, 2);
        GTPump.registerPumpType(3, "EV Hand Pump", 1600000, 3);
    }

    public static void registerBlocks_LSC() {
        lscLapotronicEnergyUnit = Block_LapotronicEnergyUnit.registerBlock();
    }
    public static void registerBlock_IGlass() {
        IGlassBlock = Block_IGlass.registerBlock();
    }
    public static void registerBlock_Coal() {
        CoalBlock = Block_Coal.registerBlock();
    }
    public static void registerBlock_UtilBlock() {
        UtilBlock = Block_UtilBlock.registerBlock();
    }
    public static void registerBlocks_Sawmill() {
        SawMillBlock = Block_Sawmill.registerBlock("SawMill", new String[]{
                Refstrings.MODID + ":sawmill/conveyor",
        });
    }
}