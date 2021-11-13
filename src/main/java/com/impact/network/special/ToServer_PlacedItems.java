package com.impact.network.special;

import com.impact.common.te.TilePlacedItem;
import com.impact.loader.ItemRegistery;
import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@ElegantPacket
public class ToServer_PlacedItems implements ClientToServerPacket {
	
	public final int side;
	public final int blockX;
	public final int blockY;
	public final int blockZ;
	
	public ToServer_PlacedItems(int side, int blockX, int blockY, int blockZ) {
		this.side   = side;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
	}
	
	@Override
	public void onReceive(EntityPlayerMP player) {
		World world = player.worldObj;
		if (world != null) {
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			int x = blockX + dir.offsetX;
			int y = blockY + dir.offsetY;
			int z = blockZ + dir.offsetZ;
			if (!world.isAirBlock(x, y, z) || player.getHeldItem() == null) {
				return;
			}
			ItemStack stack = player.getHeldItem();
			world.setBlock(x, y, z, ItemRegistery.placedItem, side, 2);
			TilePlacedItem tile = (world.getTileEntity(x, y, z) != null &&
					world.getTileEntity(x, y, z) instanceof TilePlacedItem) ? (TilePlacedItem) world.getTileEntity(x, y, z) : null;
			if (tile == null) {
				world.setBlockToAir(x, y, z);
				return;
			}
			tile.setStack(stack.copy());
			player.destroyCurrentEquippedItem();
		}
	}
}