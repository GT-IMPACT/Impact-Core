package com.impact.mods.nei.impactplugin.util;

import codechicken.nei.PositionedStack;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FixedPositionedStack extends PositionedStack {
	
	public final int mChance;
	public boolean permutated = false;
	
	public FixedPositionedStack(Object object, int x, int y) {
		this(object, x, y, 0);
	}
	
	public FixedPositionedStack(Object object, int x, int y, int aChance) {
		super(object, x, y, true);
		this.mChance = aChance;
	}
	
	public void generatePermutations() {
		if (this.permutated) {
			return;
		}
		ArrayList<ItemStack> tDisplayStacks = new ArrayList<>();
		for (ItemStack tStack : this.items) {
			if (GT_Utility.isStackValid(tStack)) {
				if (tStack.getItemDamage() == 32767) {
					List<ItemStack> permutations = codechicken.nei.ItemList.itemMap.get(tStack.getItem());
					if (!permutations.isEmpty()) {
						ItemStack stack;
						for (Iterator<ItemStack> i$ = permutations.iterator(); i$.hasNext();
							 tDisplayStacks.add(GT_Utility.copyAmount(tStack.stackSize, stack))) {
							stack = i$.next();
						}
					} else {
						ItemStack base = new ItemStack(tStack.getItem(), tStack.stackSize);
						base.stackTagCompound = tStack.stackTagCompound;
						tDisplayStacks.add(base);
					}
				} else {
					tDisplayStacks.add(GT_Utility.copy(tStack));
				}
			}
		}
		this.items = (tDisplayStacks.toArray(new ItemStack[0]));
		if (this.items.length == 0) {
			this.items = new ItemStack[]{new ItemStack(Blocks.fire)};
		}
		this.permutated = true;
		setPermutationToRender(0);
	}
}