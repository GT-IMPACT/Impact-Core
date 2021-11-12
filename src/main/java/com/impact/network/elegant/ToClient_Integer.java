package com.impact.network.elegant;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@ElegantPacket
public class ToClient_Integer implements ServerToClientPacket {
	
	public final int x, y, z;
	public final int[] integer;
	
	public ToClient_Integer(int x, int y, int z, int... integer) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.integer = integer;
	}
	public ToClient_Integer(IGregTechTileEntity te, int... integer) {
		this.x = te.getXCoord();
		this.y = te.getYCoord();
		this.z = te.getZCoord();
		this.integer = integer;
	}
	
	@Override
	public void onReceive(Minecraft minecraft) {
		World w = minecraft.theWorld;
		TileEntity tileEntity = w.getTileEntity(x, y, z);
		if (tileEntity instanceof IGregTechTileEntity) {
			IGregTechTileEntity igt = (IGregTechTileEntity) tileEntity;
			MetaTileEntity mte = (MetaTileEntity) igt.getMetaTileEntity();
			if (mte instanceof IPacketInteger) {
				((IPacketInteger) mte).update(integer);
				igt.issueTextureUpdate();
			}
		}
	}
}