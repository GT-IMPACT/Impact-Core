package com.impact.mods.GregTech.GTregister;

import gregtech.api.util.GT_Log;
import gregtech.common.GT_Worldgenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class GT_WorldGenRegister implements Runnable {
    public void run() {

        new GT_Worldgenerator();

//        new GT_Worldgen_GT_Ore_Layer("ore.mix.galliumniobiumgiant", true, 35, 45, 100, 10, 32, false, true, false, GT_CoreModSupport.Trinium, Materials.Draconium, GT_CoreModSupport.Trinium, Materials.Draconium);


        //DO NOT DELETE V THIS V - this is needed so that gregtech generates its Ore Layer's first (the ones up there), which can then be transformed into "GT_Worldgen_GT_Ore_Layer_Space". Also Reflexion is slow.
        try {
            Class clazz = Class.forName("bloodasp.galacticgreg.WorldGenGaGT");
            Constructor constructor=clazz.getConstructor();
            Method method=clazz.getMethod("run");
            method.invoke(constructor.newInstance());
            GT_Log.out.println("Started Galactic Greg ore gen code");
            //this function calls Galactic Greg and enables its generation.
        }catch (Exception e){
            GT_Log.err.println("Unable to start Galactic Greg ore gen code");
            e.printStackTrace(GT_Log.err);
        }

        /*if (GregTech_API.mImmersiveEngineering && GT_Mod.gregtechproxy.mImmersiveEngineeringRecipes) {
            blusunrize.immersiveengineering.api.tool.ExcavatorHandler.recalculateChances(true);
        }*/
    }
}