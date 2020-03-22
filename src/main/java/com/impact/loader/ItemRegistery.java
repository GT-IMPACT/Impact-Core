package com.impact.loader;

import com.impact.block.Core_GlassBlocks;
import com.impact.mods.GregTech.casings.glass1.glassed.GlassBlocksItem;
import com.impact.item.Circuit_Programmer.CircuitProgrammer;
import com.impact.item.GT_Pump.GregtechPump;
import com.impact.System.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;


public class ItemRegistery {

    public static void run() {

        for (int i=1; i<=16; i++) {
            GameRegistry.registerBlock(new Core_GlassBlocks("GlassBlock"+i, new String[]{ Refstrings.MODID + ":blockGB"+i },null, true, true), GlassBlocksItem.class, "GlassBlock"+i);
        }

    }

    public static CircuitProgrammer toolCircuitProgrammer;
    public static void CircuitProgrammer() {
        toolCircuitProgrammer = new CircuitProgrammer();
    }

    public static GregtechPump toolGregtechPump;
    public static void GregtechPump() {
        toolGregtechPump = new GregtechPump();
        toolGregtechPump.registerPumpType(0, "LV Hand Pump", 0, 0);
        toolGregtechPump.registerPumpType(1, "MV Hand Pump", 32000, 1);
        toolGregtechPump.registerPumpType(2, "HV Hand Pump", 128000, 2);
        toolGregtechPump.registerPumpType(3, "EV Hand Pump", 512000, 3);
    }

}
