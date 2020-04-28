package com.impact.loader;

import codechicken.nei.api.ItemInfo;
import com.impact.block.*;
import com.impact.mods.GregTech.casings.glass1.glassed.GlassBlocksItem;
import com.impact.item.GT_Pump.GregtechPump;
import com.impact.System.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


public class ItemRegistery {

    public static final Block[] decorateBlock = {
            new Core_Blocks("DecorateBlock", new String[] {
                    Refstrings.MODID + ":blockConcrete",
            }, 0),
            new Core_Blocks("BufferCasing", new String[] {
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
            }, 2),
};

    public static void run() {

       for (int i=0; i<=15; i++) {
           GameRegistry.registerBlock(new Core_GlassBlocks("GlassBlock"+i, new String[]{ Refstrings.MODID + ":glass/blockGB"+i },null, true, true), GlassBlocksItem.class, "GlassBlock"+i);
       }
        GameRegistry.registerBlock(ItemRegistery.decorateBlock[0], DecorateBlocksItem.class, "DecorateBlock");
        OreDictionary.registerOre("concrete", new ItemStack(decorateBlock[0], 1, 0));
        GameRegistry.registerBlock(ItemRegistery.decorateBlock[1], BufferItem.class, "BufferCasing");
        GameRegistry.registerBlock(ItemRegistery.decorateBlock[2], FakeBlocksItem.class, "FakeBlock");
    }



    //public static CircuitProgrammer toolCircuitProgrammer;
    //public static void CircuitProgrammer() {
    //    toolCircuitProgrammer = new CircuitProgrammer();
    //}

    public static GregtechPump toolGregtechPump;
    public static void GregtechPump() {
        toolGregtechPump = new GregtechPump();
        toolGregtechPump.registerPumpType(0, "LV Hand Pump", 0, 0);
        toolGregtechPump.registerPumpType(1, "MV Hand Pump", 32000, 1);
        toolGregtechPump.registerPumpType(2, "HV Hand Pump", 128000, 2);
        toolGregtechPump.registerPumpType(3, "EV Hand Pump", 512000, 3);
    }

}
