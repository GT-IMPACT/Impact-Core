package com.impact.loader;

import com.impact.block.BufferItem;
import com.impact.block.Core_Blocks;
import com.impact.block.Core_GlassBlocks;
import com.impact.block.DecorateBlocksItem;
import com.impact.mods.GregTech.casings.glass1.glassed.GlassBlocksItem;
import com.impact.item.Circuit_Programmer.CircuitProgrammer;
import com.impact.item.GT_Pump.GregtechPump;
import com.impact.System.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


public class ItemRegistery {

    public static final Block[] decorateBlock = {
            new Core_Blocks("DecorateBlock", new String[] { Refstrings.MODID + ":blockConcrete", }, 0),
            new Core_Blocks("BufferCasing", new String[] { Refstrings.MODID + ":bufferULV", Refstrings.MODID + ":bufferLV", Refstrings.MODID + ":bufferMV", Refstrings.MODID + ":bufferHV", Refstrings.MODID + ":bufferEV", Refstrings.MODID + ":bufferIV", Refstrings.MODID + ":bufferLuV", Refstrings.MODID + ":bufferZPM", Refstrings.MODID + ":bufferUV", Refstrings.MODID + ":bufferUHV"}, 1),
    };

    public static void run() {

        for (int i=0; i<=15; i++) {
            GameRegistry.registerBlock(new Core_GlassBlocks("GlassBlock"+i, new String[]{ Refstrings.MODID + ":glass/blockGB"+i },null, true, true), GlassBlocksItem.class, "GlassBlock"+i);
        }
        GameRegistry.registerBlock(ItemRegistery.decorateBlock[0], DecorateBlocksItem.class, "DecorateBlock");
        OreDictionary.registerOre("concrete", new ItemStack(decorateBlock[0], 1, 0));
        GameRegistry.registerBlock(ItemRegistery.decorateBlock[1], BufferItem.class, "BufferCasing");
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
