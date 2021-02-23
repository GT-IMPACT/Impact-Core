package com.impact.loader;

import com.impact.mods.ASP.ASP;
import com.impact.mods.GregTech.TecTech.Holo_Impact;
import com.impact.mods.GregTech.TecTech.Holo_Vanila_GregTech;
import com.impact.recipes.*;
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
