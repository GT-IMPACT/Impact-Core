package com.impact.common.te;

import com.impact.network.IPacketInteger;
import com.impact.network.ToClient_Integer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TE_TheMill extends TileEntity implements IPacketInteger {
	
	public int facing = 0;
	public int tick = 0;
	public float prevRotation = 0.0F;
	public float rotation = 0.0F;
	public float turnSpeed = 0.0F;
	public boolean canTurn = false;
	@SideOnly(Side.CLIENT)
	private AxisAlignedBB renderAABB;
	
	public TE_TheMill() {
	}
	
	public void updateEntity() {
		tick++;
		
		if (!worldObj.isRemote && (tick < 5 || tick % 3 == 0)) {
			new ToClient_Integer(xCoord, yCoord, zCoord, facing).sendPacketToAllAround(worldObj, xCoord, yCoord, zCoord, 16*2);
		}
		
		if (this.worldObj.getTotalWorldTime() % 128L == (long)((this.xCoord ^ this.zCoord) & 127)) {
			this.canTurn = this.checkArea();
		}
		
		
		if (this.canTurn) {
			double mod = 5.0E-5D;
			if (!this.worldObj.isRaining()) {
				mod *= 0.75D;
			}
			
			if (!this.worldObj.isThundering()) {
				mod *= 0.66D;
			}
			
			if (this.yCoord > 200) {
				mod *= 2.0D;
			} else if (this.yCoord > 150) {
				mod *= 1.5D;
			} else if (this.yCoord > 100) {
				mod *= 1.25D;
			} else if (this.yCoord < 70) {
				mod *= 0.33D;
			}
			
			mod *= this.getSpeedModifier();
			this.prevRotation = (float)((double)this.turnSpeed * mod);
			this.rotation = (float)((double)this.rotation + (double)this.turnSpeed * mod);
			this.rotation %= 1.0F;
			if (!this.worldObj.isRemote) {
				ForgeDirection fd = ForgeDirection.getOrientation(this.facing);
				TileEntity tileEntity = this.worldObj.getTileEntity(
						this.xCoord - fd.offsetX,
						this.yCoord - fd.offsetY,
						this.zCoord - fd.offsetZ
				);
			}
		}
	}
	
	protected float getSpeedModifier() {
		return 0.5F;
	}
	
	public boolean checkArea() {
		this.turnSpeed = 0.0F;
		int blocked;
		int hh;
		int r;
		for(blocked = -6; blocked <= 6; ++blocked) {
			hh = Math.abs(blocked) == 6 ? 1 : (Math.abs(blocked) == 5 ? 3 : (Math.abs(blocked) == 4 ? 4 : (Math.abs(blocked) > 1 ? 5 : 6)));
			
			for(r = -hh; r <= hh; ++r) {
				if ((blocked != 0 || r != 0) && !this.worldObj.isAirBlock(this.xCoord + (this.facing <= 3 ? r : 0), this.yCoord + blocked, this.zCoord + (this.facing <= 3 ? 0 : r))) {
					return false;
				}
			}
		}
		
		blocked = 0;
		
		for(hh = -6; hh <= 6; ++hh) {
			r = Math.abs(hh) == 6 ? 1 : (Math.abs(hh) == 5 ? 3 : (Math.abs(hh) == 4 ? 4 : (Math.abs(hh) > 1 ? 5 : 6)));
			
			for(int ww = -r; ww <= r; ++ww) {
				for(int dd = 1; dd < 8; ++dd) {
					int xx = this.xCoord + (this.facing <= 3 ? ww : 0) + (this.facing == 4 ? -dd : (this.facing == 5 ? dd : 0));
					int yy = this.yCoord + hh;
					int zz = this.zCoord + (this.facing <= 3 ? 0 : ww) + (this.facing == 2 ? -dd : (this.facing == 3 ? dd : 0));
					if (this.worldObj.isAirBlock(xx, yy, zz)) {
						++this.turnSpeed;
					} else if (this.worldObj.getTileEntity(xx, yy, zz) instanceof TE_TheMill) {
						blocked += 20;
						this.turnSpeed -= 179.0F;
					} else {
						++blocked;
						this.turnSpeed -= 2.0F;
					}
				}
			}
			if (blocked > 100) {
				return false;
			}
		}
		
		if (this.turnSpeed <= 0.0F) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.facing = nbt.getInteger("facing");
		this.rotation = nbt.getFloat("rotation");
		this.turnSpeed = nbt.getFloat("turnSpeed");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("facing", this.facing);
		nbt.setFloat("rotation", this.rotation);
		nbt.setFloat("turnSpeed", this.turnSpeed);
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		if (this.renderAABB == null) {
			this.renderAABB = AxisAlignedBB.getBoundingBox((this.xCoord - (this.facing <= 3 ? 6 : 0)), (this.yCoord - 6), (this.zCoord - (this.facing <= 3 ? 0 : 6)), (this.xCoord + (this.facing <= 3 ? 7 : 0)), (this.yCoord + 7), (this.zCoord + (this.facing <= 3 ? 0 : 7)));
		}
		return this.renderAABB;
	}
	
	public double getMaxRenderDistanceSquared() {
		return super.getMaxRenderDistanceSquared();
	}
	
	@Override
	public void update(int... integer) {
		this.facing = integer[0];
	}
}