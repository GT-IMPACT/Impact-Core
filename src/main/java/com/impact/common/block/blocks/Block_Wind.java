package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_WindMill;
import com.impact.common.te.TE_WindMill;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.IHasGui;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class Block_Wind extends gtUpdateBlockAPI {

    private static final Block_Wind INSTANCE = new Block_Wind();
    @SideOnly(Side.CLIENT)
    private IIcon side;

    private Block_Wind() {
        super(Material.glass);
    }

    public static Block registerBlock() {
        final String blockName = "impact_wind_block";
        INSTANCE.setBlockName(blockName);
        INSTANCE.setCreativeTab(CreativeTabs.tabMisc);
        INSTANCE.setHardness(15.0f);
        INSTANCE.setResistance(15.0f);
        GameRegistry.registerBlock(INSTANCE, IB_WindMill.class, blockName);
        return INSTANCE;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.side;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TE_WindMill();
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativetab, List itemList) {
        for (int i = 0; i < 1; i++) {
            itemList.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        side = ir.registerIcon("impact:SpaceElevator");
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}