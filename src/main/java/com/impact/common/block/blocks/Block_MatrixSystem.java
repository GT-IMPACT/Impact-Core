package com.impact.common.block.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class Block_MatrixSystem extends gtUpdateBlockAPI {
	
	private static final Block_MatrixSystem instance = new Block_MatrixSystem();
	
	private IIcon iconMatrixReflector;
	
	private Block_MatrixSystem() {
		super(Material.iron);
	}
	
	public static Block registerBlock() {
		final String blockName = "impact_matrix_system";
		instance.setBlockName(blockName);
		instance.setHardness(5.0f);
		instance.setResistance(6.0f);
		GameRegistry.registerBlock(instance, blockName);
		return instance;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		iconMatrixReflector = ir.registerIcon("impact:matrixsystem/MatrixReflector");
	}
	
	@Override
	@SuppressWarnings({"unchecked"})
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list) {
		for (int i = 0; i <= 0; i++) {
			list.add(new ItemStack(par1, 1, i));
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		switch (meta) {
			case 0:
				return iconMatrixReflector;
			default:
				return null;
		}
	}
}