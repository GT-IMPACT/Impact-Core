package com.impact.common.block.blocks;


import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Materials;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class Block_Liquid extends BlockFluidClassic {

  public Block_Liquid(Materials aMaterial, boolean isGas) {
    super(isGas ? aMaterial.mGas : aMaterial.mFluid, Material.water);
    setBlockName(aMaterial.mName);
    setBlockTextureName("impact:" + aMaterial.mName);
    GameRegistry.registerBlock(this, aMaterial.mName);
  }

  public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
    if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
      return false;
    }
    return super.canDisplace(world, x, y, z);
  }


  public boolean displaceIfPossible(World world, int x, int y, int z) {
    if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
      return false;
    }
    return super.displaceIfPossible(world, x, y, z);
  }
}
