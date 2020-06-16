package com.impact.block.blocks;

import com.impact.block.itemblock.IB_IGlass;
import com.impact.block.itemblock.IB_Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import java.util.List;

import static com.impact.System.Refstrings.MODID;
import static com.impact.util.Utilits.ItemstackMeta;

public class Block_UtilBlock extends gtUpdateBlockAPI {

    public static final Block_UtilBlock UtilBlock = new Block_UtilBlock();

    private IIcon iconConcrete;
    private IIcon iconPiston;


    private Block_UtilBlock() {
        super(Material.iron);
    }

    public static Block registerBlock() {
        final String blockName = "impact_util";
        UtilBlock.setBlockName(blockName);
        UtilBlock.setHardness(5.0f);
        UtilBlock.setResistance(6.0f);
        GameRegistry.registerBlock(UtilBlock, IB_Util.class, blockName);
        return UtilBlock;
    }

    @Override
    public void registerBlockIcons(IIconRegister ir) {
        iconConcrete = ir.registerIcon(MODID + ":" + "Concrete");
        iconPiston = ir.registerIcon(MODID + ":" + "PistonBlock");
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(ItemstackMeta(par1, 0));
        par3List.add(ItemstackMeta(par1, 1));
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch (meta) {
            case 0:
                return iconConcrete;
            case 1:
                return iconPiston;
            default:
                return null;
        }
    }

}
