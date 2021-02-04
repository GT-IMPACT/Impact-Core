package com.impact.common.block.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Block_Pattern_Space extends Block {

  private static boolean isopaque;

  public Block_Pattern_Space(Material material, String name) {
    super(material);
    setBlockName(name);
    setBlockTextureName("impact:" + name);
    setHardness(1.0f);
    setHarvestLevel("pickaxe", 2);
    setResistance(10f);
    setStepSound(Block.soundTypeStone);
    isopaque = true;
    GameRegistry.registerBlock(this, name);
  }

  public boolean isOpaqueCube() {
    return isopaque;
  }
}
