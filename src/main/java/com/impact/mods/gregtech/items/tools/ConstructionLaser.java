package com.impact.mods.gregtech.items.tools;

import com.impact.impact;
import com.impact.util.vector.Box;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.items.GT_Generic_Item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class ConstructionLaser extends GT_Generic_Item {
	
	public ConstructionLaser(String english) {
		super("impact.items.constructionlaser", english, null);
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List itemList) {
		ItemStack is = new ItemStack(this);
		
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setInteger("yMin", 1);
		nbt.setInteger("yMax", 1);
		
		nbt.setInteger("xMin", 0);
		nbt.setInteger("xMax", 0);
		
		nbt.setInteger("zMin", 0);
		nbt.setInteger("zMax", 0);
		
		is.setTagCompound(nbt);
		itemList.add(is);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player instanceof EntityPlayerMP && getMovingObjectPositionFromPlayer(world, player, true) == null) {
			if (!player.isSneaking()) {
				NBTTagCompound nbt = stack.getTagCompound();
				if (nbt != null) {
					if (nbt.hasKey("xMax")) {
						impact.proxy.remove_hint_particle(world, nbt.getInteger("xMax"), nbt.getInteger("yMax"), nbt.getInteger("zMax"));
					}
					if (nbt.hasKey("xMin")) {
						impact.proxy.remove_hint_particle(world, nbt.getInteger("xMin"), nbt.getInteger("yMin"), nbt.getInteger("zMin"));
					}
					Box box = getBox(stack);
					if (!box.render) {
						box.setRenderHint(world);
					}
				}
			}
		}
		return super.onItemRightClick(stack, world, player);
	}
	
	public Box getBox(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			return null;
		}
		int xMax = nbt.getInteger("xMax");
		int yMax = nbt.getInteger("yMax");
		int zMax = nbt.getInteger("zMax");
		int xMin = nbt.getInteger("xMin");
		int yMin = nbt.getInteger("yMin");
		int zMin = nbt.getInteger("zMin");
		return new Box(xMin, yMin, zMin, xMax, yMax, zMax);
	}
	
	@Override
	public boolean onItemUse(ItemStack aStack, EntityPlayer aPlayer, World w, int x, int y, int z, int aSide, float hitX, float hitY, float hitZ) {
		if (aPlayer instanceof EntityPlayerMP && getMovingObjectPositionFromPlayer(w, aPlayer, true) != null) {
			NBTTagCompound nbt = aStack.getTagCompound();
			if (nbt == null) {
				nbt = new NBTTagCompound();
			}
			if (!aPlayer.isSneaking()) {
				if (nbt.hasKey("xMax")) {
					impact.proxy.remove_hint_particle(w, nbt.getInteger("xMax"), nbt.getInteger("yMax"), nbt.getInteger("zMax"));
				}
				impact.proxy.hint_particleMega(w, x, y, z, Color.BLUE, 1200);
				nbt.setInteger("xMax", x);
				nbt.setInteger("yMax", y);
				nbt.setInteger("zMax", z);
			} else {
				if (nbt.hasKey("xMin")) {
					impact.proxy.remove_hint_particle(w, nbt.getInteger("xMin"), nbt.getInteger("yMin"), nbt.getInteger("zMin"));
				}
				impact.proxy.hint_particleMega(w, x, y, z, Color.YELLOW, 1200);
				nbt.setInteger("xMin", x);
				nbt.setInteger("yMin", y);
				nbt.setInteger("zMin", z);
			}
			aStack.setTagCompound(nbt);
		}
		return aPlayer instanceof EntityPlayerMP && getMovingObjectPositionFromPlayer(w, aPlayer, true) != null;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean b) {
		super.addInformation(stack, player, info, b);
		info.add("This is a construction laser that serves to mark out the region");
		info.add("RClick and Shift-RClick on a block set a points");
		info.add("Click on the air shows the size of the selected area");
	}
}