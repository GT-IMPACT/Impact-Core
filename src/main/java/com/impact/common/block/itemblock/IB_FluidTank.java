package com.impact.common.block.itemblock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class IB_FluidTank extends ItemBlock {
	
	public IB_FluidTank(Block block) {
		super(block);
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	public boolean getHasSubtypes() {
		return true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + stack.getItemDamage();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lines,
							   boolean advancedTooltips) {
		lines.add("This is not a fluid tank");
		switch (stack.getItemDamage()) {
			case 0:
				lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN + " 16 000 000L for 1 fluid (Total 25 fluid)");
				lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 80 000 000L");
				break;
			case 1:
				lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN + " 32 000 000L for 1 fluid (Total 25 fluid)");
				lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 160 000 000L");
				break;
			case 2:
				lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN + " 64 000 000L for 1 fluid (Total 25 fluid)");
				lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 320 000 000L");
				break;
			case 3:
				lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN + " 128 000 000L for 1 fluid (Total 25 fluid)");
				lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 640 000 000L");
				break;
			case 4:
				lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN + " 256 000 000L for 1 fluid (Total 25 fluid)");
				lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 1 280 000 000L");
				break;
			case 5:
				lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN + " 512 000 000L for 1 fluid (Total 25 fluid)");
				lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 2 000 000 000L");
				break;
			case 6:
				lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN + " 1 024 000 000L for 1 fluid (Total 25 fluid)");
				lines.add(EnumChatFormatting.RED + "Single-Tank not used" + EnumChatFormatting.RESET);
				break;
			case 7:
				lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN + " 2 048 000 000L for 1 fluid (Total 25 fluid)");
				lines.add(EnumChatFormatting.RED + "Single-Tank not used" + EnumChatFormatting.RESET);
				break;
		}
	}
}
