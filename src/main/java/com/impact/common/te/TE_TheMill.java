package com.impact.common.te;

import com.google.common.collect.Lists;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_TheMill;
import com.impact.util.vector.Structure;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.NetworkHelper;
import ic2.api.tile.IWrenchable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class TE_TheMill extends TileEntity implements IWrenchable, INetworkDataProvider {
	
	public int facing = 0;
	public long tick = 0;
	public float prevRotation = 0.0F;
	public float rotation = 0.0F;
	public float turnSpeed = 0.0F;
	public boolean canTurn = false;
	@SideOnly(Side.CLIENT)
	private AxisAlignedBB renderAABB;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		tick++;
		if (tick >= Long.MAX_VALUE) {
			tick = 0;
		}
		
		if (worldObj.getTotalWorldTime() % 128 == ((xCoord ^ zCoord) & 127)) {
			canTurn = checkArea();
		}
		
		if (!canTurn) return;
		double mod = .00005;
		if (!worldObj.isRaining()) mod *= .75;
		if (!worldObj.isThundering()) mod *= .66;
		if (yCoord > 200) mod *= 2;
		else if (yCoord > 150) mod *= 1.5;
		else if (yCoord > 100) mod *= 1.25;
		else if (yCoord < 70) mod *= .33;
		mod *= getSpeedModifier();
		
		prevRotation = (float) (turnSpeed * mod);
		rotation += turnSpeed * mod;
	}
	
	protected float getSpeedModifier() {
		return .5F;
	}
	
	public boolean checkArea() {
		turnSpeed = 0;
		for (int hh = -6; hh <= 6; hh++) {
			int r = Math.abs(hh) == 6 ? 1 : Math.abs(hh) == 5 ? 3 : Math.abs(hh) == 4 ? 4 : Math.abs(hh) > 1 ? 5 : 6;
			for (int ww = -r; ww <= r; ww++)
				if ((hh != 0 || ww != 0) && !worldObj.isAirBlock(xCoord + (facing <= 3 ? ww : 0), yCoord + hh, zCoord + (facing <= 3 ? 0 : ww)))
					return false;
		}
		
		ForgeDirection fd = ForgeDirection.getOrientation(this.facing);
		GTMTE_TheMill mill = Structure.getIMTE(GTMTE_TheMill.class, worldObj,
				this.xCoord - fd.offsetX,
				this.yCoord - fd.offsetY,
				this.zCoord - fd.offsetZ
		);
		if (mill == null) {
			return false;
		}
		
		int blocked = 0;
		for (int hh = -6; hh <= 6; hh++) {
			int r = Math.abs(hh) == 6 ? 1 : Math.abs(hh) == 5 ? 3 : Math.abs(hh) == 4 ? 4 : Math.abs(hh) > 1 ? 5 : 6;
			for (int ww = -r; ww <= r; ww++) {
				for (int dd = 1; dd < 8; dd++) {
					int xx = xCoord + (facing <= 3 ? ww : 0) + (facing == 4 ? -dd : facing == 5 ? dd : 0);
					int yy = yCoord + hh;
					int zz = zCoord + (facing <= 3 ? 0 : ww) + (facing == 2 ? -dd : facing == 3 ? dd : 0);
					if (worldObj.isAirBlock(xx, yy, zz))
						turnSpeed++;
					else if (worldObj.getTileEntity(xx, yy, zz) instanceof TE_TheMill) {
						blocked += 20;
						turnSpeed -= 179;
					} else {
						blocked++;
						turnSpeed -= 2;
					}
				}
			}
			if (blocked > 100)
				return false;
		}
		return !(turnSpeed <= 0);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		try {
			super.readFromNBT(nbt);
		} catch (Exception ignored) {}
		this.facing    = nbt.getInteger("facing");
		this.rotation  = nbt.getFloat("rotation");
		this.turnSpeed = nbt.getFloat("turnSpeed");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		try {
			super.writeToNBT(nbt);
		} catch (Exception ignored) {}
		nbt.setInteger("facing", this.facing);
		nbt.setFloat("rotation", this.rotation);
		nbt.setFloat("turnSpeed", this.turnSpeed);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if (renderAABB == null)
			renderAABB = AxisAlignedBB.getBoundingBox(xCoord - (facing <= 3 ? 6 : 0), yCoord - 6, zCoord - (facing <= 3 ? 0 : 6), xCoord + (facing <= 3 ? 7 : 0), yCoord + 7, zCoord + (facing <= 3 ? 0 : 7));
		return renderAABB;
	}
	
	public double getMaxRenderDistanceSquared() {
		return super.getMaxRenderDistanceSquared();
	}
	
	@Override
	public List<String> getNetworkedFields() {
		return Lists.newArrayList("facing", "rotation", "turnSpeed");
	}
	
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int i) {
		return false;
	}
	
	@Override
	public short getFacing() {
		return (short) facing;
	}
	
	@Override
	public void setFacing(short side) {
		facing = side;
		NetworkHelper.updateTileEntityField(this, "facing");
	}
	
	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		return false;
	}
	
	@Override
	public float getWrenchDropRate() {
		return 0;
	}
	
	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return null;
	}
}