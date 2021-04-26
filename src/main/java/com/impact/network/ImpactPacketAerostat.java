package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.impact.mods.gregtech.gui.aerostat.Container_FirstAerostat;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ImpactPacketAerostat extends ImpactPacket {
	
	private int dimID, playerID;
	private String name;
	
	public ImpactPacketAerostat() {
	}
	
	public ImpactPacketAerostat(String name, int dimID, int playerID) {
		this.name = name;
		this.dimID = dimID;
		this.playerID = playerID;
	}
	
	public ImpactPacketAerostat(String name, EntityPlayer p) {
		this.name = name;
		this.dimID = p.worldObj.provider.dimensionId;
		this.playerID = p.getEntityId();
	}
	
	@Override
	public int getPacketID() {
		return 4;
	}
	
	@Override
	public byte[] encode() {
		ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
		tOut.writeUTF(name);
		tOut.writeInt(dimID);
		tOut.writeInt(playerID);
		return tOut.toByteArray();
	}
	
	@Override
	public ImpactPacket decode(ByteArrayDataInput aData) {
		return new ImpactPacketAerostat(aData.readUTF(), aData.readInt(), aData.readInt());
	}
	
	@Override
	public void process() {
		World w = DimensionManager.getWorld(dimID);
		if (w != null && w.getEntityByID(playerID) instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) w.getEntityByID(playerID);
			if (player.openContainer instanceof Container_FirstAerostat) {
				Container_FirstAerostat container = (Container_FirstAerostat) player.openContainer;
				if (container.mTileEntity.getMetaTileEntity() instanceof GTMTE_Aerostat) {
					GTMTE_Aerostat te = (GTMTE_Aerostat) container.mTileEntity.getMetaTileEntity();
					te.setLocationName(name);
				}
			}
		}
	}
}