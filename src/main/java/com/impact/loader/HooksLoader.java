package com.impact.loader;

import gloomyfolken.hooklib.minecraft.HookLoader;
import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;

public class HooksLoader extends HookLoader {

  @Override
  public String[] getASMTransformerClass() {
    return new String[]{
        PrimaryClassTransformer.class.getName()
    };
  }

  @Override
  public void registerHooks() {
    registerHookContainer("com.impact.events.Hooks");
    registerHookContainer("com.impact.hooks.FluidInterface_Hook");
    registerHookContainer("com.impact.hooks.AE2Spatial_Hook");
    registerHookContainer("com.impact.hooks.OpisPatch_Hook");
  }
  
  @Override
  public String getSetupClass() {
    return "com.impact.loader.DepLoader";
  }
}
