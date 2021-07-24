package com.impact.hooks;

import gloomyfolken.hooklib.asm.Hook;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;

public class IC2Rotors_Hook {
    @Hook(injectOnExit = true, isMandatory = true)
    public static void updateEntityServer(TileEntityWindKineticGenerator rotor) {
        if (rotor.getTickRate() % 32 == 0 && !rotor.rotorSlot.isEmpty()) {
            if (rotor.getActive() && rotor.rotorSlot.get().getItemDamage() != 0)
                rotor.rotorSlot.get().setItemDamage(0);
        }
    }

    @Hook(injectOnExit = true, isMandatory = true)
    public static void updateEntityServer(TileEntityWaterKineticGenerator rotor) {
        if (rotor.getWorldObj().getWorldTime() % 32 == 0 && !rotor.rotorSlot.isEmpty()) {
            if (rotor.getActive() && rotor.rotorSlot.get().getItemDamage() != 0)
                rotor.rotorSlot.get().setItemDamage(0);
        }
    }


}