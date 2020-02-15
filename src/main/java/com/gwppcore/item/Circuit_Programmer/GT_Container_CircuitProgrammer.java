package com.gwppcore.item.Circuit_Programmer;

import com.gwppcore.gwppcore;
import com.gwppcore.item.Circuit_Programmer.CircuitProgrammer;
import com.gwppcore.item.Circuit_Programmer.CircuitProgrammerPacket;
import com.gwppcore.util.SendUtils;
import com.gwppcore.util.Utilits;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.gui.GT_Slot_Render;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeHooks;

import static com.gwppcore.gwppcore.SendUtils_instance;

public class GT_Container_CircuitProgrammer extends Container {

    EntityPlayer player;

    public GT_Container_CircuitProgrammer(InventoryPlayer inventory) {

        this.player = inventory.player;

        IInventory inv = new pinv(this.player);

        this.addSlotToContainer(new Slot(inv, 0, 81, 79));//-45, 84));

        for (int i = 1; i < 9; i++) {
            this.addSlotToContainer(new GT_Slot_Holo(inv, i, 0 + i * 18, 22, false, false, 1));
        }
        for (int i = 1; i < 9; i++) {
            this.addSlotToContainer(new GT_Slot_Holo(inv, i, 0 + i * 18, 40, false, false, 1));
        }
        for (int i = 1; i < 9; i++) {
            this.addSlotToContainer(new GT_Slot_Holo(inv, i, 0 + i * 18, 58, false, false, 1));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 9 + j * 18, 102 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            if (GT_Utility.isStackValid(inventory.getStackInSlot(i)) && inventory.getStackInSlot(i).getItem() instanceof CircuitProgrammer)
                this.addSlotToContainer(new GT_Slot_Render(inventory, i, 9 + i * 18, 160));
            else
                this.addSlotToContainer(new Slot(inventory, i, 9 + i * 18, 160));
        }
    }

    @Override
    public ItemStack slotClick(int slot, int button, int aShifthold, EntityPlayer entityPlayer) {
        if (slot > 0 && slot < 25 && ((Slot) this.inventorySlots.get(0)).getStack() != null) {
            ItemStack iCircuit = GT_Utility.getIntegratedCircuit(slot);
            iCircuit.stackSize = 64;
            ((Slot) this.inventorySlots.get(0)).putStack(iCircuit);
            this.detectAndSendChanges();
            return ((Slot) this.inventorySlots.get(0)).getStack();
        }
        this.detectAndSendChanges();
        return super.slotClick(slot, button, aShifthold, entityPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int SlotNR) {
        Slot chipslot = (Slot) this.inventorySlots.get(0);
        if (SlotNR > 24) {
            Slot slot = (Slot) this.inventorySlots.get(SlotNR);
            if (slot != null && slot.getStack() != null && slot.getStack().getItem().equals(GT_Utility.getIntegratedCircuit(0).getItem())) {
                if (chipslot.getStack() == null) {
                    chipslot.putStack(slot.getStack().copy());
                    slot.decrStackSize(64);
                }
            }
        } else if (SlotNR == 0 && chipslot.getStack() != null) {
            for (int i = 25; i < this.inventorySlots.size(); i++) {
                if (((Slot) this.inventorySlots.get(i)).getStack() == null) {
                    Slot empty = ((Slot) this.inventorySlots.get(i));
                    empty.putStack(chipslot.getStack().copy());
                    chipslot.decrStackSize(64);
                    break;
                }
            }
        }
        this.detectAndSendChanges();
        return null;
    }

    class pinv implements IInventory {

        ItemStack toBind;
        EntityPlayer Player;
        ItemStack Slot;
        NBTTagCompound tag;

        public pinv(EntityPlayer Player) {
            this.Player = Player;
            this.toBind = Player.inventory.getCurrentItem();
            this.tag = this.toBind.getTagCompound();
            if (this.tag.getBoolean("HasChip")) {
                this.Slot = GT_Utility.getIntegratedCircuit(this.tag.getByte("ChipConfig"));
                this.Slot.stackSize = 64;
            }
        }

        @Override
        public int getSizeInventory() {
            return 64;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return slot == 0 ? this.Slot : null;
        }

        @Override
        public ItemStack decrStackSize(int slotNR, int count) {
            ItemStack ret = this.Slot.copy();
            this.Slot = null;
            this.tag = this.toBind.getTagCompound();
            this.tag.setBoolean("HasChip", false);
            this.toBind.setTagCompound(this.tag);
            this.Player.inventory.setInventorySlotContents(this.Player.inventory.currentItem, this.toBind);
            return ret;
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
            return this.Slot;
        }

        @Override
        public void setInventorySlotContents(int slotNR, ItemStack itemStack) {
            if (itemStack != null && itemStack.getItem() != null && itemStack.getItem().equals(GT_Utility.getIntegratedCircuit(0).getItem())) {
                this.Slot = itemStack.copy().splitStack(64);
                itemStack.stackSize--;
                this.tag = this.toBind.getTagCompound();
                this.tag.setBoolean("HasChip", true);
                this.tag.setByte("ChipConfig", (byte) itemStack.getItemDamage());
                this.toBind.setTagCompound(this.tag);
                this.Player.inventory.setInventorySlotContents(this.Player.inventory.currentItem, this.toBind);
                if (!this.Player.isClientWorld())
                    SendUtils_instance.sendToServer(new CircuitProgrammerPacket(this.Player.worldObj.provider.dimensionId, this.Player.getEntityId(), true, (byte) itemStack.getItemDamage()));
            } else if (Utilits.checkStackAndPrefix(itemStack) && GT_OreDictUnificator.getAssociation(itemStack).mPrefix.equals(OrePrefixes.circuit) && GT_OreDictUnificator.getAssociation(itemStack).mMaterial.mMaterial.equals(Materials.Basic)) {
                this.Slot = GT_Utility.getIntegratedCircuit(0);
                this.Slot.stackSize = 64;
                itemStack.stackSize--;
                this.tag = this.toBind.getTagCompound();
                this.tag.setBoolean("HasChip", true);
                this.tag.setByte("ChipConfig", (byte) 0);
                this.toBind.setTagCompound(this.tag);
                this.Player.inventory.setInventorySlotContents(this.Player.inventory.currentItem, this.toBind);
                if (!this.Player.isClientWorld())
                    SendUtils_instance.sendToServer(new CircuitProgrammerPacket(this.Player.worldObj.provider.dimensionId, this.Player.getEntityId(), true, (byte) 0));
            }/* else if (GT_Utility.isStackValid(itemStack) && itemStack.getItem() instanceof Circuit_Programmer) {
                ForgeHooks.onPlayerTossEvent(Player, itemStack, false);
                this.closeInventory();
                Player.closeScreen();
            }*/ else {
                ForgeHooks.onPlayerTossEvent(this.Player, itemStack, false);
                this.tag = this.toBind.getTagCompound();
                this.tag.setBoolean("HasChip", false);
                this.toBind.setTagCompound(this.tag);
                this.Player.inventory.setInventorySlotContents(this.Player.inventory.currentItem, this.toBind);
                if (!this.Player.isClientWorld())
                    SendUtils_instance.sendToServer(new CircuitProgrammerPacket(this.Player.worldObj.provider.dimensionId, this.Player.getEntityId(), false, (byte) 0));
            }
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
        public int getInventoryStackLimit() {
            return 64;
        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
            return true;
        }

        @Override
        public void openInventory() {

        }

        @Override
        public void closeInventory() {

        }

        @Override
        public boolean isItemValidForSlot(int p_94041_1_, ItemStack itemStack) {
            return itemStack != null && itemStack.getItem().equals(GT_Utility.getIntegratedCircuit(0).getItem());
        }
    }

}