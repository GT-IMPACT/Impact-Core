package com.impact.mods.modChest.BASE;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class Item_BaseChest extends ItemBlock {

	public Item_BaseChest( Block block)
	{
		super(block);
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean bool) {
		list.add(EnumChatFormatting.YELLOW +"Warning! When break chest, item not dropped");
	}


	@Override
	public boolean hasCustomEntity(ItemStack itemStack) {
		return itemStack != null && itemStack.getItem() instanceof Item_BaseChest;
	}

	@Override
	public Entity createEntity(World world, Entity entity,  ItemStack itemStack) {
		return null;
	}
}