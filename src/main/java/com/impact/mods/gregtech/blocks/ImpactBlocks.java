package com.impact.mods.gregtech.blocks;

import com.impact.mods.gregtech.blocks.casings.Casing_1;
import com.impact.mods.gregtech.blocks.casings.Casing_2;
import com.impact.mods.gregtech.blocks.casings.Casing_3;
import com.impact.mods.gregtech.blocks.casings.Casing_9;
import com.impact.mods.gregtech.blocks.other.GT_Block_LongDistancePipe;
import net.minecraft.block.Block;

public class ImpactBlocks implements Runnable {

    public static Block sCaseCore1;
    public static Block sCaseCore2;
    public static Block sCaseCore3;
    public static Block sCasePage8_3;
    public static Block sBlockLongDistancePipes;

    public ImpactBlocks() {
    }

    @Override
    public void run() {
        ImpactBlocks.sCaseCore1 = new Casing_1();
        ImpactBlocks.sCaseCore2 = new Casing_2();
        ImpactBlocks.sCaseCore3 = new Casing_3();
        ImpactBlocks.sCasePage8_3 = new Casing_9();
        ImpactBlocks.sBlockLongDistancePipes = new GT_Block_LongDistancePipe();
    }
}
