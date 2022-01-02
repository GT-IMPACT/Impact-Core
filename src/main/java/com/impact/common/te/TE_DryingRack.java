package com.impact.common.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;

public class TE_DryingRack extends TileEntity implements IInventory {
	
	protected ItemStack[] inventory;
	protected String invName;
	protected int stackSizeLimit;
	public int currentTime;
	public int maxTime;
	
	public TE_DryingRack() {
		inventory      = new ItemStack[1];
		stackSizeLimit = 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot < inventory.length ? inventory[slot] : null;
	}
	
	public boolean isStackInSlot(int slot) {
		return slot < inventory.length && inventory[slot] != null;
	}
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return stackSizeLimit;
	}
	
	public boolean canDropInventorySlot(int slot) {
		return true;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		inventory[slot] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		updateDryingTime();
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int quantity) {
		maxTime = 0;
		currentTime = 0;
		if (inventory[slot] != null) {
			if (inventory[slot].stackSize <= quantity) {
				ItemStack stack = inventory[slot];
				inventory[slot] = null;
				return stack;
			}
			ItemStack split = inventory[slot].splitStack(quantity);
			if (inventory[slot].stackSize == 0) {
				inventory[slot] = null;
			}
			return split;
		} else {
			return null;
		}
		
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
			return false;
		
		else
			return entityplayer.getDistance((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		
	}
	
	@Override
	public void openInventory() {
	}
	
	@Override
	public void closeInventory() {
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && maxTime > 0 && currentTime < maxTime) {
			currentTime++;
			if (currentTime >= maxTime) {
				inventory[0] = DryingRackRecipes.getDryingResult(inventory[0]);
				updateDryingTime();
			}
		}
	}
	
	public void updateDryingTime() {
		currentTime = 0;
		if (inventory[0] != null) maxTime = DryingRackRecipes.getDryingTime(inventory[0]);
		else maxTime = 0;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public String getInventoryName() {
		return null;
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord - 1, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		readInventoryFromNBT(tags);
		currentTime = tags.getInteger("Time");
		maxTime     = tags.getInteger("MaxTime");
	}
	
	public void readInventoryFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		NBTTagList nbttaglist = tags.getTagList("Items", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];
		
		if (tags.hasKey("сName", 8)) {
			this.invName = tags.getString("сName");
		}
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;
			
			if (j >= 0 && j < this.inventory.length) {
				this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		writeInventoryToNBT(tags);
		tags.setInteger("Time", currentTime);
		tags.setInteger("MaxTime", maxTime);
	}
	
	public void writeInventoryToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.inventory.length; ++i) {
			if (this.inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		
		tags.setTag("Items", nbttaglist);
		
		if (this.isInvNameLocalized()) {
			tags.setString("сName", this.invName);
		}
		
	}
	
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}
	
	public boolean isInvNameLocalized() {
		return this.invName != null && this.invName.length() > 0;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if (slot < getSizeInventory()) {
			return inventory[slot] == null || itemstack.stackSize + inventory[slot].stackSize <= getInventoryStackLimit();
		}
		return false;
	}
	
	public static class DryingRackRecipes {
		public static ArrayList<DryingRecipe> recipes = new ArrayList<>();
		
		public static void addDryingRecipe(ItemStack inputItem, int time, ItemStack outputItem) {
			recipes.add(new DryingRecipe(inputItem, time, outputItem));
		}
		
		public static int getDryingTime(ItemStack input) {
			for (DryingRecipe r : recipes) {
				if (r.matches(input))
					return r.time;
			}
			
			return -1;
		}
		
		public static ItemStack getDryingResult(ItemStack input) {
			for (DryingRecipe r : recipes) {
				if (r.matches(input))
					return r.getResult();
			}
			
			return null;
		}
		
		public static class DryingRecipe {
			public final int time;
			public final ItemStack input;
			public final ItemStack result;
			
			DryingRecipe(ItemStack input, int time, ItemStack result) {
				this.time   = time;
				this.input  = input;
				this.result = result;
			}
			
			public boolean matches(ItemStack input) {
				if (input.hasTagCompound()) {
					input = input.copy();
					input.getTagCompound().removeTag("frypanKill");
					if (input.getTagCompound().hasNoTags())
						input.setTagCompound(null);
				}
				return ItemStack.areItemStacksEqual(this.input, input);
			}
			
			public ItemStack getResult() {
				return result.copy();
			}
		}
	}
}