package com.impact.mods.railcraft.carts.item.entities;

import com.impact.client.gui.GUIHandler;
import com.impact.impact;
import com.impact.mods.railcraft.carts.base.CartTransferBase;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import mods.railcraft.api.carts.IItemTransfer;
import mods.railcraft.api.carts.IMinecart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;

@Optional.InterfaceList({
		@Optional.Interface(iface = "mods.railcraft.api.carts.IMinecart", modid = "RailcraftAPI|carts"),
		@Optional.Interface(iface = "mods.railcraft.api.carts.IItemTransfer", modid = "RailcraftAPI|carts")
})
abstract public class EntityExtraCartChestMinecart extends CartTransferBase implements IInventory, IMinecart, IItemTransfer {
	
	protected boolean passThrough = false;
	private ItemStack[] minecartContainerItems = new ItemStack[108];
	private boolean dropContentsWhenDead = true;
	
	public EntityExtraCartChestMinecart(World world) {
		super(world);
	}
	
	public EntityExtraCartChestMinecart(World p_i1717_1_, double p_i1717_2_, double p_i1717_4_, double p_i1717_6_) {
		super(p_i1717_1_, p_i1717_2_, p_i1717_4_, p_i1717_6_);
	}
	
	public void killMinecartNoDrop(DamageSource damageSource) {
		super.killMinecart(damageSource);
	}
	
	public void killMinecart(DamageSource damageSource, ItemStack drop) {
		super.killMinecart(damageSource);
		float f = this.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
		float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
		EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double) f, this.posY + (double) f1, this.posZ + (double) f2, drop);
		float f3 = 0.05F;
		entityitem.motionX = (float) this.rand.nextGaussian() * f3;
		entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
		entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
		if (!this.worldObj.isRemote) {
			this.worldObj.spawnEntityInWorld(entityitem);
		}
	}
	
	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return this.getMinecartContainerItems()[p_70301_1_];
	}
	
	@Override
	public ItemStack decrStackSize(int pos, int stackSize) {
		if (this.getMinecartContainerItems()[pos] != null) {
			ItemStack itemstack;
			if (this.getMinecartContainerItems()[pos].stackSize <= stackSize) {
				itemstack = this.getMinecartContainerItems()[pos];
				this.getMinecartContainerItems()[pos] = null;
            } else {
				itemstack = this.getMinecartContainerItems()[pos].splitStack(stackSize);
				if (this.getMinecartContainerItems()[pos].stackSize == 0) {
					this.getMinecartContainerItems()[pos] = null;
				}
            }
          return itemstack;
        } else {
			return null;
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int pos) {
		if (this.getMinecartContainerItems()[pos] != null) {
			ItemStack itemstack = this.getMinecartContainerItems()[pos];
			this.getMinecartContainerItems()[pos] = null;
			return itemstack;
		} else {
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int pos, ItemStack stack) {
		this.getMinecartContainerItems()[pos] = stack;
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return !this.isDead && player.getDistanceSqToEntity(this) <= 64.0D;
	}
	
	@Override
	public void openInventory() {
	}
	
	@Override
	public void closeInventory() {
	}
	
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
	
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.func_95999_t() : "container.minecart";
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void travelToDimension(int dim) {
		this.setDropContentsWhenDead(false);
		super.travelToDimension(dim);
	}
	
	@Override
	public void setDead() {
		if (this.isDropContentsWhenDead()) {
			for (int i = 0; i < this.getSizeInventory(); ++i) {
				ItemStack itemstack = this.getStackInSlot(i);
				if (itemstack != null) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
					while (itemstack.stackSize > 0) {
						int j = this.rand.nextInt(21) + 10;
						if (j > itemstack.stackSize) {
							j = itemstack.stackSize;
						}
						itemstack.stackSize -= j;
						EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double) f, this.posY + (double) f1, this.posZ + (double) f2,
								new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
						
						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
						float f3 = 0.05F;
						entityitem.motionX = (float) this.rand.nextGaussian() * f3;
						entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
						if (!this.worldObj.isRemote) {
							this.worldObj.spawnEntityInWorld(entityitem);
						}
					}
				}
			}
		}
		super.setDead();
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.getMinecartContainerItems().length; ++i) {
			if (this.getMinecartContainerItems()[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.getMinecartContainerItems()[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
		NBTTagList nbttaglist = p_70037_1_.getTagList("Items", 10);
		this.setMinecartContainerItems(new ItemStack[this.getSizeInventory()]);
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;
			if (j >= 0 && j < this.getMinecartContainerItems().length) {
				this.getMinecartContainerItems()[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player) {
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player))) {
			return true;
		}
		if (!this.worldObj.isRemote && !player.isSneaking()) {
			FMLNetworkHandler.openGui(player, impact.instance, GUIHandler.GUI_ID_Carts, player.worldObj, this.getEntityId(), 0, 0);
		}
		return true;
	}
	
	@Override
	public int getSizeInventory() {
		return 0;
	}
	
	@Override
	public void markDirty() {
	}
	
	@Override
	public int getMinecartType() {
		return 0;
	}
 
	public ItemStack[] getMinecartContainerItems() {
		return minecartContainerItems;
	}
	
	public void setMinecartContainerItems(ItemStack[] minecartContainerItems) {
		this.minecartContainerItems = minecartContainerItems;
	}
	
	public boolean isDropContentsWhenDead() {
		return dropContentsWhenDead;
	}
	
	public void setDropContentsWhenDead(boolean dropContentsWhenDead) {
		this.dropContentsWhenDead = dropContentsWhenDead;
	}
	
	@Optional.Method(modid = "RailcraftAPI|carts")
	public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
		return false;
	}
}