package com.impact.mods.gregtech.blocks;

import com.impact.mods.gregtech.blocks.casings.Casing_1;
import com.impact.mods.gregtech.blocks.casings.Casing_2;
import com.impact.mods.gregtech.blocks.casings.Casing_3;
import com.impact.mods.gregtech.blocks.casings.Casing_9;
import com.impact.mods.gregtech.blocks.other.GT_Block_LongDistancePipe;
import net.minecraft.block.Block;

public class Casing_Helper implements Runnable {

    public static Block sCaseCore1;
    public static Block sCaseCore2;
    public static Block sCaseCore3;
    public static Block sCasePage8_3;
    public static Block sBlockLongDistancePipes;

    public Casing_Helper() {
    }

    @Override
    public void run() {
        Casing_Helper.sCaseCore1 = new Casing_1();
        Casing_Helper.sCaseCore2 = new Casing_2();
        Casing_Helper.sCaseCore3 = new Casing_3();
        Casing_Helper.sCasePage8_3 = new Casing_9();
        Casing_Helper.sBlockLongDistancePipes = new GT_Block_LongDistancePipe();
    }
}