package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_LapotronicEnergyUnit;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class Block_LapotronicEnergyUnit extends gtUpdateBlockAPI {
	
	private static final Block_LapotronicEnergyUnit instance = new Block_LapotronicEnergyUnit();
	
	private IIcon iconLapoIVSide;
	private IIcon iconLapoIVTop;
	private IIcon iconLapoLuVSide;
	private IIcon iconLapoLuVTop;
	private IIcon iconLapoZPMSide;
	private IIcon iconLapoZPMTop;
	private IIcon iconLapoUVSide;
	private IIcon iconLapoUVTop;
	private IIcon iconUltimateSide;
	private IIcon iconUltimateTop;
	
	private Block_LapotronicEnergyUnit() {
		super(Material.iron);
	}
	
	public static Block registerBlock() {
		final String blockName = "impact_lapotronicenergyunit_block";
		instance.setBlockName(blockName);
		instance.setHardness(5.0f);
		instance.setResistance(6.0f);
		GameRegistry.registerBlock(instance, IB_LapotronicEnergyUnit.class, blockName);
		return instance;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		iconLapoIVSide = ir.registerIcon("impact:LapotronicEnergyUnit1_side");
		iconLapoIVTop = ir.registerIcon("impact:LapotronicEnergyUnit1_top");
		iconLapoLuVSide = ir.registerIcon("impact:LapotronicEnergyUnit2_side");
		iconLapoLuVTop = ir.registerIcon("impact:LapotronicEnergyUnit2_top");
		iconLapoZPMSide = ir.registerIcon("impact:LapotronicEnergyUnit3_side");
		iconLapoZPMTop = ir.registerIcon("impact:LapotronicEnergyUnit3_top");
		iconLapoUVSide = ir.registerIcon("impact:LapotronicEnergyUnit4_side");
		iconLapoUVTop = ir.registerIcon("impact:LapotronicEnergyUnit4_top");
		
		iconUltimateSide = ir.registerIcon("impact:UltimateEnergyUnit_side");
		iconUltimateTop = ir.registerIcon("impact:UltimateEnergyUnit_top");
	}
	
	@Override
	@SuppressWarnings({"unchecked" })
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		// Lapo units IV - UV
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 4));
		// Ultimate battery
		par3List.add(new ItemStack(par1, 1, 5));
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		switch(meta) {
		case 1: return (side < 2) ? iconLapoIVTop : iconLapoIVSide;
		case 2: return (side < 2) ? iconLapoLuVTop : iconLapoLuVSide;
		case 3: return (side < 2) ? iconLapoZPMTop : iconLapoZPMSide;
		case 4: return (side < 2) ? iconLapoUVTop : iconLapoUVSide;
		case 5: return (side < 2) ? iconUltimateTop : iconUltimateSide;
		default: return iconUltimateTop;
		}
	}
	
}
