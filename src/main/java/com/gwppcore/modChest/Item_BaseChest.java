package com.gwppcore.modChest;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class Item_BaseChest extends ItemBlock
{
	public Item_BaseChest(final Block block)
	{
		super(block);
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean bool)
	{
		list.add(I18n.format("tooltip.fillingRange", Integer.toString(itemStack.hasTagCompound() ? itemStack.getTagCompound().getTagList("Contents", 10).tagCount() : 0), 243));
	}

	@Override
	public boolean hasCustomEntity(final ItemStack itemStack)
	{
		return itemStack != null && itemStack.getItem() instanceof Item_BaseChest;
	}

	@Override
	public Entity createEntity(final World world, final Entity entity, final ItemStack itemStack)
	{
		return null;
	}
}