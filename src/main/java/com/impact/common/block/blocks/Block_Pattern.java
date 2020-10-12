package com.impact.common.block.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class Block_Pattern extends Block {
    private static boolean isopaque;
    private static Material aMaterial;

    private static final Block_Pattern instance = new Block_Pattern();

    public Block_Pattern() {
        super(Material.rock);

    }

    public static Block registerBlock(Material material, String name, float hardness, String harvTool, int harvLvl, float resistance, Block.SoundType sound, boolean isOpaque) {
        instance.setBlockName(name);
        instance.setBlockTextureName("impact:" + name);
        instance.setHardness(hardness);
        instance.setHarvestLevel(harvTool, harvLvl);
        instance.setResistance(resistance);
        instance.setStepSound(sound);
        isopaque = isOpaque;
        aMaterial = material;
        GameRegistry.registerBlock(instance, name);
        return instance;
    }

    public boolean isOpaqueCube() {
        return isopaque;
    }
}
