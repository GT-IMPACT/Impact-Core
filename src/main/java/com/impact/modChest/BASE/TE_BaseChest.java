package com.impact.modChest.BASE;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class TE_BaseChest extends TileEntity implements IInventory
{


	private int ticksSinceSync;
	private int facingSide = 0;

	float lidAngle;
	float prevLidAngle;
	private int numPlayersUsing;

	int maxStackSize;
	int slot;

	public final ItemStack[] contents = new ItemStack[getSizeInventory()];

	public TE_BaseChest(final int maxStackSize, final int slot) {
		this.maxStackSize = maxStackSize;
		this.slot = slot;

	}

	@Override
	public final boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public final void openInventory()
	{
		if (worldObj == null)
			return;
		if (numPlayersUsing < 0)
			numPlayersUsing = 0;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, ++numPlayersUsing);
	}

	@Override
	public final void closeInventory() {
		if (worldObj != null)
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, --numPlayersUsing);
	}

	@Override
	public final Packet getDescriptionPacket() {
		final NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 3, nbttagcompound);
	}

	@Override
	public final void onDataPacket(final NetworkManager networkManager, final S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public final boolean receiveClientEvent(final int argument, final int value) {
		if (argument == 1) {
			this.numPlayersUsing = value;
			return true;
		} else {
			return super.receiveClientEvent(argument, value);
		}
	}

	@Override
	public final void readFromNBT(final NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		facingSide = nbtTagCompound.getInteger("FacingSide");
		readCustomNBT(nbtTagCompound);
	}

	@Override
	public final void writeToNBT(final NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setInteger("FacingSide", facingSide);
		writeCustomNBT(nbtTagCompound);
	}

	public void readCustomNBT(final NBTTagCompound nbtTagCompound) {
		final NBTTagList nbtTagList = nbtTagCompound.getTagList("Contents", 10);
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			final NBTTagCompound slotCompound = nbtTagList.getCompoundTagAt(i);
			final int slot = slotCompound.getShort("Slot");
			if (slot >= 0 && slot < invSize())
			setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(slotCompound));
		}
	}

	public NBTTagCompound writeCustomNBT(final NBTTagCompound nbtTagCompound) {
		final NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < this.slot; i++) {
			final ItemStack itemStack = getStackInSlot(i);
			if (itemStack != null) {
				final NBTTagCompound slotCompound = new NBTTagCompound();
				slotCompound.setShort("Slot", (short) i);
				nbtTagList.appendTag(itemStack.writeToNBT(slotCompound));
			}
		}
		nbtTagCompound.setTag("Contents", nbtTagList);
		return nbtTagCompound;
	}

	@Override
	public int getSizeInventory() {
		return invSize();
	}

	@Override
	public final void updateEntity() {
		++ticksSinceSync;
		float f;

		if (!worldObj.isRemote && numPlayersUsing != 0 && (ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0) {
			numPlayersUsing = 0;
			f = 5.0F;
			List list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double) ((float) xCoord - f), (double) ((float) yCoord - f), (double) ((float) zCoord - f), (double) ((float) (xCoord + 1) + f), (double) ((float) (yCoord + 1) + f), (double) ((float) (zCoord + 1) + f)));

			for (Object aList : list) {
				EntityPlayer entityplayer = (EntityPlayer) aList;

				if (entityplayer.openContainer instanceof Container_BaseChest) {
					IInventory iinventory = ((Container_BaseChest) entityplayer.openContainer).TEBaseChest;

					if (iinventory == this) {
						++numPlayersUsing;
					}
				}
			}
		}

		prevLidAngle = lidAngle;
		f = 0.1F;

		if (numPlayersUsing > 0 && lidAngle == 0.0F)
			worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);

		if (numPlayersUsing == 0 && lidAngle > 0.0F || numPlayersUsing > 0 && lidAngle < 1.0F) {
			final float f1 = lidAngle;

			if (numPlayersUsing > 0)
				lidAngle += f;
			else
				lidAngle -= f;

			if (lidAngle > 1.0F)
				lidAngle = 1.0F;

			if (lidAngle < 0.5F && f1 >= 0.5F)
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);

			if (lidAngle < 0.0F)
				lidAngle = 0.0F;
		}
	}

	@Override
	public ItemStack getStackInSlot(final int slot) {
		return contents[slot];
	}

	@Override
	public ItemStack decrStackSize(final int slot, final int howMuch) {
		final ItemStack slotStack = contents[slot];
		if (slotStack != null) {
			final int quantity = MathHelper.clamp_int(MathHelper.clamp_int(howMuch, 1, slotStack.getMaxStackSize()), 1, slotStack.stackSize);
			final ItemStack newStack = slotStack.copy();
			newStack.stackSize = quantity;
			if ((slotStack.stackSize -= quantity) == 0)
				contents[slot] = null;
			markDirty();
			return newStack;
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot)
	{
		return contents[slot];
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack itemStack) {
		contents[slot] = itemStack;
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return maxStackSize;
	}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack itemStack) {
		return true;
	}

	@Override
	public final boolean hasCustomInventoryName()
	{
		return false;
	}

	protected final void setFacingSide(final int facingSide)
	{
		this.facingSide = facingSide;
	}

	protected final int getFacingSide()
	{
		return facingSide;
	}

	public abstract int invSize();

	@SideOnly(Side.CLIENT)
	@Nonnull
	protected abstract ResourceLocation getTexture();

	public void removeAdornments() {}
}
