package com.impact.mods.gregtech.blocks.other;

import static com.impact.mods.gregtech.GT_ItemList.Long_Distance_Pipeline_Fluid_Pipe;
import static com.impact.mods.gregtech.GT_ItemList.Long_Distance_Pipeline_Item_Pipe;
import static com.impact.mods.gregtech.enums.Texture.Icons.LONG_DISTANCE_PIPE_FLUID;
import static com.impact.mods.gregtech.enums.Texture.Icons.LONG_DISTANCE_PIPE_ITEM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Material_Machines;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GT_Block_LongDistancePipe extends GT_Generic_Block {

  public IIconContainer[] mIcons;

  public GT_Block_LongDistancePipe() {
    super(GT_Item_LongDistancePipe.class, "gt.block.longdistancepipe", new GT_Material_Machines());
    setStepSound(soundTypeMetal);
    setCreativeTab(GregTech_API.TAB_GREGTECH);
    setHarvestLevel("wrench", 2);
    setHardness(5.0f);
    setResistance(6.0f);
    GregTech_API.registerMachineBlock(this, -1);

    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name",
        "Long Distance Fluid Pipeline Pipe");
    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name",
        "Long Distance Item Pipeline Pipe");
    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + 32767 + ".name",
        "Any Sub Block of this");

    Long_Distance_Pipeline_Fluid_Pipe.set(new ItemStack(this, 1, 0));
    Long_Distance_Pipeline_Item_Pipe.set(new ItemStack(this, 1, 1));
    mIcons = new IIconContainer[]{LONG_DISTANCE_PIPE_FLUID, LONG_DISTANCE_PIPE_ITEM};
  }

  public void onBlockAdded(World aWorld, int aX, int aY, int aZ) {
    super.onBlockAdded(aWorld, aX, aY, aZ);
    if (GregTech_API.isMachineBlock(this, aWorld.getBlockMetadata(aX, aY, aZ))) {
      GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
    }
  }

  public void breakBlock(World aWorld, int aX, int aY, int aZ, Block par5, int par6) {
    GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
    super.breakBlock(aWorld, aX, aY, aZ, par5, par6);
  }

  public String getUnlocalizedName() {
    return this.mUnlocalizedName;
  }

  public String getLocalizedName() {
    return StatCollector.translateToLocal(this.mUnlocalizedName + ".name");
  }

  public IIcon getIcon(int aSide, int aMeta) {
    return mIcons[aMeta % mIcons.length].getIcon();
  }

  public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
    return false;
  }

  @Override
  public int damageDropped(int meta) {
    return meta;
  }

  @Override
  public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
    return false;
  }

  @Override
  public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
    return false;
  }

  @SideOnly(Side.CLIENT)
  public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List aList) {
    for (int i = 0; i < 3; i++) {
      ItemStack aStack = new ItemStack(aItem, 1, i);
      if (!aStack.getDisplayName().contains(".name")) {
        aList.add(aStack);
      }
    }
  }
}