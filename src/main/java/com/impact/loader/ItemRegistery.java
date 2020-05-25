package com.impact.loader;

import com.impact.System.Refstrings;
import com.impact.block.*;
import com.impact.item.GT_Pump.GregtechPump;
import com.impact.util.SendUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static codechicken.nei.api.API.hideItem;
import static com.impact.System.impactLog.INFO;


public class ItemRegistery {

    public static final Block[] decorateBlock = {
            new Core_Blocks("DecorateBlock", new String[]{
                    Refstrings.MODID + ":Concrete",
                    Refstrings.MODID + ":CokeCoal",
                    Refstrings.MODID + ":PistonBlock",
            }, 0),
            new Core_Blocks("BufferCasing", new String[]{
                    Refstrings.MODID + ":bufferULV",
                    Refstrings.MODID + ":bufferLV",
                    Refstrings.MODID + ":bufferMV",
                    Refstrings.MODID + ":bufferHV",
                    Refstrings.MODID + ":bufferEV",
                    Refstrings.MODID + ":bufferIV",
                    Refstrings.MODID + ":bufferLuV",
                    Refstrings.MODID + ":bufferZPM",
                    Refstrings.MODID + ":bufferUV",
                    Refstrings.MODID + ":bufferUHV"
            }, 1),
            new Core_FakeBlock("FakeBlock", new String[]{
                    Refstrings.MODID + ":glass/blockGB0",
                    Refstrings.MODID + ":glass/blockGB14",
                    Refstrings.MODID + ":glass/blockGB13",
                    Refstrings.MODID + ":glass/blockGB11",
                    Refstrings.MODID + ":glass/frameGt",
            }, 2),
            new Core_GlassBlocks("GlassBlock", new String[]{
                    Refstrings.MODID + ":glass/blockGB0", // white
                    Refstrings.MODID + ":glass/blockGB1", // orange
                    Refstrings.MODID + ":glass/blockGB2", // magenta
                    Refstrings.MODID + ":glass/blockGB3", // light blue
                    Refstrings.MODID + ":glass/blockGB4", // yellow
                    Refstrings.MODID + ":glass/blockGB5", // lime
                    Refstrings.MODID + ":glass/blockGB6", // pink
                    Refstrings.MODID + ":glass/blockGB7", // gray
                    Refstrings.MODID + ":glass/blockGB8", // light gray
                    Refstrings.MODID + ":glass/blockGB9", // cyan
                    Refstrings.MODID + ":glass/blockGB10", // purple
                    Refstrings.MODID + ":glass/blockGB11", // blue
                    Refstrings.MODID + ":glass/blockGB12", // brown
                    Refstrings.MODID + ":glass/blockGB13", // green
                    Refstrings.MODID + ":glass/blockGB14", // red
                    Refstrings.MODID + ":glass/blockGB15", // black
            }, false, true)
    };


    public static void run() {
        //Blocks
        GameRegistry.registerBlock(decorateBlock[0], DecorateBlocksItem.class, "DecorateBlock");
        GameRegistry.registerBlock(decorateBlock[1], BufferItem.class, "BufferCasing");
        GameRegistry.registerBlock(decorateBlock[2], FakeBlocksItem.class, "FakeBlock");
        GameRegistry.registerBlock(decorateBlock[3], GlassBlocksItem.class, "GlassBlock");
        INFO("[Init] Item Registery Blocks - Loaded");

        //NEI hide
        hideItem(new ItemStack(decorateBlock[2], 1, 0));
        hideItem(new ItemStack(decorateBlock[2], 1, 1));
        hideItem(new ItemStack(decorateBlock[2], 1, 2));
        hideItem(new ItemStack(decorateBlock[2], 1, 3));
        hideItem(new ItemStack(decorateBlock[2], 1, 4));
        INFO("[Init] Item Registery Hide Fake BLocks - Loaded");

        //OreDictionary
        OreDictionary.registerOre("concrete", new ItemStack(decorateBlock[0], 1, 0));
        OreDictionary.registerOre("blockCokeCoal", new ItemStack(decorateBlock[0], 1, 1));
        INFO("[Init] Item Registery Blocks OreDict - Loaded");
    }
    public static GregtechPump GTPump;
    public static void GregtechPump() {
        GTPump = new GregtechPump();
        GTPump.registerPumpType(0, "LV Hand Pump", 0, 0);
        GTPump.registerPumpType(1, "MV Hand Pump", 32000, 1);
        GTPump.registerPumpType(2, "HV Hand Pump", 128000, 2);
        GTPump.registerPumpType(3, "EV Hand Pump", 512000, 3);
    }
}
