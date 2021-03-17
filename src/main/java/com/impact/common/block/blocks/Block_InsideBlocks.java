package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_InsideBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Textures;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class Block_InsideBlocks extends gtUpdateBlockAPI {

  private static final Block_InsideBlocks instance = new Block_InsideBlocks();

  private IIcon Chamber;
  private IIcon CycloneSide, CycloneTop;
  private IIcon EmptyRackSides, EmptyRackFront;
  private IIcon ReactorTop;


  private Block_InsideBlocks() {
    super(Material.iron);
  }

  public static Block registerBlock() {
    final String blockName = "impact_inside_block";
    instance.setBlockName(blockName);
    instance.setHardness(5.0f);
    instance.setResistance(6.0f);
    GameRegistry.registerBlock(instance, IB_InsideBlocks.class, blockName);
    return instance;
  }

  @Override
  public void registerBlockIcons(IIconRegister ir) {
    Chamber = ir.registerIcon("impact:ChamberInside");

    CycloneTop = ir.registerIcon("impact:CycloneTop");
    CycloneSide = ir.registerIcon("impact:CycloneActive");

    EmptyRackSides = ir.registerIcon("impact:EmptyRackSides");
    EmptyRackFront = ir.registerIcon("impact:EmptyRackFront");
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public void getSubBlocks(Item item, CreativeTabs tab, List l) {
    for (byte i = 0; i < 5; i++) {
      l.add(new ItemStack(item, 1, i));
    }
  }

  @Override
  protected void dropBlockAsItem(World w, int x, int y, int z, ItemStack is) {
    super.dropBlockAsItem(w, x, y, z, is);
  }

  @Override
  public IIcon getIcon(int side, int meta) {
    switch (meta) {
      case 0:
        return Chamber;
      case 1:
        return (side < 2) ? CycloneTop : CycloneSide;
      case 2:
        return (side < 2) ? EmptyRackSides : EmptyRackFront;
      case 3:
      case 4:
        return Textures.BlockIcons.MACHINE_CASING_GEARBOX_STEEL.getIcon();
      default:
        return null;
    }
  }
}