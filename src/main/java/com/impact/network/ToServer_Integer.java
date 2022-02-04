package com.impact.network;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@ElegantPacket
public class ToServer_Integer implements ClientToServerPacket {
	
	public final int[] integer;
	
	public ToServer_Integer(int... integer) {
		this.integer = integer;
	}
	
	@Override
	public void onReceive(EntityPlayerMP entityPlayerMP) {
		if (entityPlayerMP.openContainer instanceof IPacketInteger) {
			IPacketInteger cont = (IPacketInteger) entityPlayerMP.openContainer;
			cont.update(integer);
		} else {
			World world = entityPlayerMP.worldObj;
			if (world != null) {
				int x = integer[0];
				int y = integer[1];
				int z = integer[2];
				int[] newInt = new int[integer.length - 3];
				System.arraycopy(integer, 3, newInt, 0, 3);
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile instanceof IGregTechTileEntity) {
					IGregTechTileEntity igt = (IGregTechTileEntity) tile;
					IMetaTileEntity mte = igt.getMetaTileEntity();
					if (mte instanceof IPacketInteger) {
						((IPacketInteger) mte).update(newInt);
					}
				}
			}
		}
	}
}