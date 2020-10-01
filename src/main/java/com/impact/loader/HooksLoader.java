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
        registerHookContainer("com.impact.System.Hooks");
    }
}
