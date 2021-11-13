package com.impact.util;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStack_NoNBT implements Comparable<ItemStack_NoNBT> {
	public final Item mItem;
	public final int mStackSize;
	public final int mMetaData;
	
	public ItemStack_NoNBT(Item aItem, long aStackSize, long aMetaData) {
		this.mItem      = aItem;
		this.mStackSize = (byte) ((int) aStackSize);
		this.mMetaData  = (short) ((int) aMetaData);
	}
	
	public ItemStack_NoNBT(ItemStack aStack) {
		if (aStack == null) {
			mItem      = null;
			mStackSize = mMetaData = 0;
		} else {
			mItem      = aStack.getItem();
			mStackSize = aStack.stackSize;
			mMetaData  = Items.feather.getDamage(aStack);
		}
	}
	
	@Override
	public int compareTo(ItemStack_NoNBT o) {
		if (mMetaData > o.mMetaData) {
			return 1;
		}
		if (mMetaData < o.mMetaData) {
			return -1;
		}
		if (mStackSize > o.mStackSize) {
			return 1;
		}
		if (mStackSize < o.mStackSize) {
			return -1;
		}
		if (mItem != null && o.mItem != null) {
			return mItem.getUnlocalizedName().compareTo(o.mItem.getUnlocalizedName());
		}
		if (mItem == null && o.mItem == null) {
			return 0;
		}
		if (mItem != null) {
			return 1;
		}
		return -1;
	}
	
	@Override
	public boolean equals(Object aStack) {
		return aStack == this || (aStack instanceof ItemStack_NoNBT &&
				((mItem == ((ItemStack_NoNBT) aStack).mItem) ||
						((ItemStack_NoNBT) aStack).mItem.getUnlocalizedName().equals(this.mItem.getUnlocalizedName())) &&
				((ItemStack_NoNBT) aStack).mStackSize == this.mStackSize &&
				((ItemStack_NoNBT) aStack).mMetaData == this.mMetaData);
	}
	
	@Override
	public int hashCode() {
		return (mItem != null ? mItem.getUnlocalizedName().hashCode() : 0) ^ (mMetaData << 16) ^ (mStackSize << 24);
	}
	
	@Override
	public String toString() {
		return Integer.toString(hashCode()) + ' ' + (mItem == null ? "null" : mItem.getUnlocalizedName()) + ' ' + mMetaData + ' ' + mStackSize;
	}
}