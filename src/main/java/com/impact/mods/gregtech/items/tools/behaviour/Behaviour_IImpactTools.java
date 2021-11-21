package com.impact.mods.gregtech.items.tools.behaviour;

import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.impact.util.ItemNBTHelper.getBoolean;
import static com.impact.util.ItemNBTHelper.setBoolean;

public class Behaviour_IImpactTools extends Behaviour_None {
	
	public final boolean adDrop;
	
	public Behaviour_IImpactTools(boolean adDrop) {
		this.adDrop = adDrop;
	}
	
	@Override
	public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld,
								  int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
		if (!aWorld.isRemote) {
			if (aPlayer.isSneaking()) {
				if (adDrop) {
					boolean statusAdDrop = getBoolean(aStack, "adDrop", false);
					statusAdDrop = !statusAdDrop;
					setBoolean(aStack, "adDrop", statusAdDrop);
					GT_Utility.sendChatToPlayer(aPlayer, "Advanced Drop Harvester: " + (statusAdDrop ? "Enabled" : "Disabled"));
				}
			}
		}
		return false;
	}
	
	@Override
	public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
		if (adDrop) {
			boolean mode = getBoolean(aStack, "adDrop", false);
			aList.add("Shift + Right click on the block to change harvester mode");
			aList.add(EnumChatFormatting.WHITE + "Advanced Harvester Mode: " + EnumChatFormatting.GREEN + (mode ? "Enable" : "Disable"));
		}
		return aList;
	}
}
