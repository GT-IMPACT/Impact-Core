package com.impact.network.special;

import baubles.api.BaublesApi;
import com.impact.common.armor.MaskOfVision;
import com.impact.impact;
import com.impact.util.vector.Vector3i;
import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@ElegantPacket
public class ToClient_LaserPush implements ServerToClientPacket {
	
	public final int dim;
	public final Vector3i vec1, vec2;
	public final int color, mode, lifeTime, type;
	public final float endMode;
	
	public ToClient_LaserPush(int dim, Vector3i vec1, Vector3i vec2, int color, int mode) {
		this.dim = dim;
		this.vec1 = vec1;
		this.vec2 = vec2;
		this.color = color;
		this.mode = mode;
		this.lifeTime = 20;
		this.type = 1;
		this.endMode = 1;
	}
	
	public ToClient_LaserPush(int dim, Vector3i vec1, Vector3i vec2, int color, int mode, int lifeTime, int type, float endMode) {
		this.dim = dim;
		this.vec1 = vec1;
		this.vec2 = vec2;
		this.color = color;
		this.mode = mode;
		this.lifeTime = lifeTime;
		this.type = type;
		this.endMode = endMode;
	}
	
	@Override
	public void onReceive(Minecraft minecraft) {
		EntityClientPlayerMP thePlayer = minecraft.thePlayer;
		World w = thePlayer.worldObj;
		if (dim != w.provider.dimensionId) {
			return;
		}
		switch (mode) {
			case 0:
				boolean mask = false;
				ItemStack is = thePlayer.getCurrentArmor(3);
				if (is == null || !(is.getItem() instanceof MaskOfVision)) {
					IInventory handler = BaublesApi.getBaubles(thePlayer);
					if (handler != null) {
						for (int i = 0; i < handler.getSizeInventory(); ++i) {
							is = handler.getStackInSlot(i);
							if (is != null && is.getItem() instanceof MaskOfVision) {
								mask = true;
								break;
							}
						}
					}
				} else {
					mask = true;
				}
				if (mask) {
					impact.proxy.beam(w, vec1, vec2, type, color, false, endMode, lifeTime);
				}
				break;
			
			case 1:
				double translate = 0.5d;
				impact.proxy.beam(w, vec1.x() + translate, vec1.y() + translate, vec1.z() + translate,
						vec2.x() + translate, vec2.y() + translate, vec2.z() + translate, type, 0x770ED0, false, endMode, lifeTime);
				break;
		}
	}
}
