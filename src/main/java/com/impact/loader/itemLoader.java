package com.impact.loader;

import com.impact.GregTech.casings.glass1.glassed.GlassBlocks;
import com.impact.block.Core_GlassBlocks;

public class itemLoader implements Runnable {

    @Override
    public void run() {
        GlassBlocks.run();
        Core_GlassBlocks.run();
    }
}
