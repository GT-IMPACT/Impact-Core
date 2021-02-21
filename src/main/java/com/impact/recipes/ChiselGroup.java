package com.impact.recipes;

import com.cricketcraft.chisel.api.carving.CarvingUtils;
import net.minecraft.block.Block;
import team.chisel.carving.Carving;

import static com.riciJak.Ztones.init.ModBlocks.*;

public class ChiselGroup {
    public static void run(){

        /*String name = "Molds";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        Block[] mold = new Block[]{
                Shape_Mold_Plate.getBlock(), Shape_Mold_Casing.getBlock(), Shape_Mold_Gear.getBlock(), Shape_Mold_Credit.getBlock(),
                Shape_Mold_Bottle.getBlock(), Shape_Mold_Ingot.getBlock(), Shape_Mold_Ball.getBlock(), Shape_Mold_Block.getBlock(),
                Shape_Mold_Nugget.getBlock(), Shape_Mold_Bun.getBlock(), Shape_Mold_Bread.getBlock(), Shape_Mold_Baguette.getBlock(),
                Shape_Mold_Cylinder.getBlock(), Shape_Mold_Anvil.getBlock(), Shape_Mold_Name.getBlock(), null, Shape_Mold_Gear_Small.getBlock(),
                Shape_Mold_Rod.getBlock(), Shape_Mold_Bolt.getBlock(), Shape_Mold_Round.getBlock(), Shape_Mold_Screw.getBlock(),
                Shape_Mold_Ring.getBlock(), Shape_Mold_Rod_Long.getBlock(), Shape_Mold_Rotor.getBlock(), Shape_Mold_Turbine_Blade.getBlock()
        };
        for (int i = 0; i < 25; i++) {
            if (i != 16) {
                continue;
            }
            Carving.chisel.addVariation(name, mold[i], 32301 + i, 0);
        }*/
        String name = "Agon";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, agonBlock, i, 0);
        }

        name = "Bitt";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, bittBlock, i, 0);
        }

        name = "Cray";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, crayBlock, i, 0);
        }

        name = "Fort";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, fortBlock, i, 0);
        }

        name = "Glaxx";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, glaxx, i, 0);
        }

        name = "Iszm";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, iszmBlock, i, 0);
        }

        name = "Jelt";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, jeltBlock, i, 0);
        }

        name = "Korp";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, korpBlock, i, 0);
        }

        name = "Kryp";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, krypBlock, i, 0);
        }

        name = "Lair";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, lairBlock, i, 0);
        }

        name = "Lave";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, laveBlock, i, 0);
        }

        name = "Mint";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, mintBlock, i, 0);
        }

        name = "Myst";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, mystBlock, i, 0);
        }

        name = "Reds";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, redsBlock, i, 0);
        }

        name = "Roen";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, roenBlock, i, 0);
        }

        name = "Sols";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, solsBlock, i, 0);
        }

        name = "Tank";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, tankBlock, i, 0);
        }

        name = "Vect";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, vectBlock, i, 0);
        }

        name = "Vena";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, venaBlock, i, 0);
        }

        name = "Zech";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zechBlock, i, 0);
        }

        name = "Zion";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zionBlock, i, 0);
        }

        name = "Zome";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zomeBlock, i, 0);
        }

        name = "Zone";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zoneBlock, i, 0);
        }

        name = "Zorg";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zorgBlock, i, 0);
        }

        name = "Azur";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, azurBlock, i, 0);
        }

        name = "Zane";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zaneBlock, i, 0);
        }

        name = "Zest";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zestBlock, i, 0);
        }

        name = "Zeta";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zetaBlock, i, 0);
        }

        name = "Zoea";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zoeaBlock, i, 0);
        }

        name = "Zyth";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zythBlock, i, 0);
        }

        name = "Reed";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, reedBlock, i, 0);
        }

        name = "Sync";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, syncBlock, i, 0);
        }

        name = "Zkul";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, zkulBlock, i, 0);
        }

        name = "Ztyl";
        Carving.chisel.addGroup(new CarvingUtils.SimpleCarvingGroup(name));
        for (int i = 0; i < 16; i++) {
            Carving.chisel.addVariation(name, ztylBlock, i, 0);
        }
    }
}
