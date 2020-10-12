package com.impact.common.block.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class Block_HCloud extends Block {

    private static final Block_HCloud instance = new Block_HCloud();

    public Block_HCloud() {
        super(Material.cloth);
    }

    public static Block registerBlock() {
        final String blockName = "HCloud";
        instance.setBlockName(blockName);
        instance.setBlockTextureName("impact:HCloud");
        instance.setLightOpacity(0);
        instance.setHardness(4.0F);
        instance.setStepSound(soundTypeCloth);
        GameRegistry.registerBlock(instance, blockName);
        return instance;
    }


    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int par2, int par3, int par4) {
        return null;
    }
}