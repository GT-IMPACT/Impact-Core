package com.gwppcore.loader;

import com.gwppcore.gthandler.casings.glass1.glassed.GlassBlocks;
import com.gwppcore.block.Core_GlassBlocks;

public class itemLoader implements Runnable {

    @Override
    public void run() {
        GlassBlocks.run();
        Core_GlassBlocks.run();
    }
}
