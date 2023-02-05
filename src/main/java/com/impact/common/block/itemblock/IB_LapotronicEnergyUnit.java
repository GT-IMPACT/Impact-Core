package com.impact.common.block.itemblock;

import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

import static com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation.*;

public class IB_LapotronicEnergyUnit extends ItemBlock {
	
	public IB_LapotronicEnergyUnit(Block block) {
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
		lines.add("Part of the Lapotronic Super Capacitor");
		String cap;
		switch (stack.getItemDamage()) {
			case 1:
				cap = GT_Utility.formatNumbers(L_IV);
				lines.add(String.format("Capacity: %s EU", cap));
				break;
			case 2:
				cap = GT_Utility.formatNumbers(L_LuV);
				lines.add(String.format("Capacity: %s EU", cap));
				break;
			case 3:
				cap = GT_Utility.formatNumbers(L_ZPM);
				lines.add(String.format("Capacity: %s EU", cap));
				break;
			case 4:
				cap = GT_Utility.formatNumbers(L_UV);
				lines.add(String.format("Capacity: %s EU", cap));
				break;
			case 6:
				cap = GT_Utility.formatNumbers(E_IV);
				lines.add(String.format("Capacity: %s EU", cap));
				break;
			case 7:
				cap = GT_Utility.formatNumbers(L_UHV);
				lines.add(String.format("Capacity: %s EU", cap));
				break;
			case 8:
				cap = GT_Utility.formatNumbers(L_UEV);
				lines.add(String.format("Capacity: %s EU", cap));
				break;
		}
	}
}