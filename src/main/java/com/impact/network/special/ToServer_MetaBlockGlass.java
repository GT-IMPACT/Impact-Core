package com.impact.network.special;

import com.impact.common.block.itemblock.IB_IGlass;
import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@ElegantPacket
public class ToServer_MetaBlockGlass implements ClientToServerPacket {
	
	public final boolean aBoolean;
	
	public ToServer_MetaBlockGlass(boolean aBoolean) {
		this.aBoolean = aBoolean;
	}
	
	@Override
	public void onReceive(EntityPlayerMP player) {
		World w = player.worldObj;
		if (w != null) {
			ItemStack stack = player.getHeldItem();
			if (stack != null) {
				if (stack.getItem() instanceof IB_IGlass) {
					int maxDmg = 15;
					int newDamage = stack.getItemDamage();
					newDamage = newDamage + (aBoolean ? 1 : -1);
					newDamage = newDamage > maxDmg ? 0 : newDamage < 0 ? maxDmg : newDamage;
					stack.setItemDamage(newDamage);
				}
			}
			
		}
	}
}
