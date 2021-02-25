package com.impact.loader;

import com.impact.mods.GregTech.tectech.Holo_Impact;
import com.impact.mods.GregTech.tectech.Holo_Vanila_GregTech;
import cpw.mods.fml.common.Loader;

public class ModLoader implements Runnable {

  @Override
  public void run() {

    // --- TecTech
    if (Loader.isModLoaded("tectech")) {
      new Holo_Impact().run();
      new Holo_Vanila_GregTech().run();
    }
  }
}
