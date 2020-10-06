package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_InsideBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class Block_InsideBlocks extends gtUpdateBlockAPI {

	private static final Block_InsideBlocks instance = new Block_InsideBlocks();

	private IIcon Chamber;
	private IIcon CycloneSide, CycloneTop;


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
	}
	
	@Override
	@SuppressWarnings({"unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List l) {
		for(byte i = 0; i < 2; i++)
			l.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		switch(meta) {
		case 0: return Chamber;
		case 1: return (side < 2) ? CycloneTop: CycloneSide;
		default: return null;
		}
	}
	
}
