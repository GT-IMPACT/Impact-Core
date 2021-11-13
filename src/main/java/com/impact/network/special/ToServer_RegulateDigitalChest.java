package com.impact.network.special;

import com.impact.mods.gregtech.tileentities.basic.GTMTE_RegulateDigitalChest;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@ElegantPacket
public class ToServer_RegulateDigitalChest implements ClientToServerPacket {
	
	public final int maxCount;
	public final int x;
	public final int y;
	public final int z;
	
	public ToServer_RegulateDigitalChest(int maxCount, int x, int y, int z) {
		this.maxCount = maxCount;
		this.x        = x;
		this.y        = y;
		this.z        = z;
	}
	
	@Override
	public void onReceive(EntityPlayerMP player) {
		World world = player.worldObj;
		if (world != null) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof IGregTechTileEntity) {
				IGregTechTileEntity igt = (IGregTechTileEntity) tile;
				IMetaTileEntity mte = igt.getMetaTileEntity();
				if (mte instanceof GTMTE_RegulateDigitalChest) {
					GTMTE_RegulateDigitalChest chest = (GTMTE_RegulateDigitalChest) mte;
					chest.mMaxItemCount = maxCount;
				}
			}
		}
	}
}