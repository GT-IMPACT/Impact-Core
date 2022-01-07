package com.impact.common.block.itemblock;

import com.impact.common.te.TE_TheMill;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class IB_TheMill extends ItemBlock {
	
	public IB_TheMill(Block block) {
		super(block);
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	public boolean getHasSubtypes() {
		return false;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advancedTooltips) {
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
								int side, float hitX, float hitY, float hitZ, int meta) {
		
		int xRange = 6;
		int yRange = 6;
		int zRange = 0;
		switch (side) {
			case 0:
			case 1:
				zRange = 6;
				break;
			case 2:
			case 3:
				break;
			case 4:
			case 5:
				xRange = 0;
				zRange = 6;
				break;
		}
		
		for (int xPos = x - xRange; xPos <= x + xRange; xPos++) {
			for (int yPos = y - yRange; yPos <= y + yRange; yPos++) {
				for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
					if (world.getBlock(xPos, yPos, zPos) != Blocks.air) {
						return false;
					}
				}
			}
		}
		
		boolean ret = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, meta);
		
		if (!ret) return false;
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TE_TheMill) {
			((TE_TheMill) tileEntity).facing = side;
		}
		
		return true;
	}
}