package com.impact.mods.IC2.tileentities;

import com.impact.mods.IC2.gui.AdvancedContainerCropHavester;
import com.impact.mods.IC2.gui.AdvancedGuiCropHavester;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.Direction;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.Ic2Items;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.crop.TileEntityCrop;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import ic2.core.util.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.EnumSet;
import java.util.Set;

public class IC_AdvancedHarvester extends TileEntityElectricMachine implements IHasGui, IUpgradableBlock {

    public final InvSlot contentSlot = new InvSlot(this, "content", 1, InvSlot.Access.IO, 15);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 16, 1);
    public final InvSlotConsumableId cropnalyzerSlot = new InvSlotConsumableId(this, "cropnalyzer", 8, 7, Ic2Items.cropnalyzer.getItem());
    public int scanX = -5;
    public int scanY = -3;
    public int scanZ = -5;

    public IC_AdvancedHarvester() {
        super(100000, 2, 0, false);
    }

    protected void updateEntityServer() {
        super.updateEntityServer();
        ItemStack upgrade = this.upgradeSlot.get(0);
        if (upgrade != null && ((IUpgradeItem) upgrade.getItem()).onTick(upgrade, this)) super.markDirty();
        if (this.energy >= 201.0D) this.scan();
    }

    public void scan() {
        ItemStack cropnalyzer = this.cropnalyzerSlot.get(0);
        ++this.scanX;
        if (this.scanX > 5) {
            this.scanX = -5;
            ++this.scanZ;
            if (this.scanZ > 5) {
                this.scanZ = -5;
                ++this.scanY;
                if (this.scanY > 3) {
                    this.scanY = -3;
                }
            }
        }

        --this.energy;
        TileEntity te = this.worldObj.getTileEntity(this.xCoord + this.scanX, this.yCoord + this.scanY, this.zCoord + this.scanZ);
        if (te instanceof TileEntityCrop && !this.isInvFull()) {
            TileEntityCrop crop = (TileEntityCrop) te;
            ItemStack[] drops = crop.harvest_automated(cropnalyzer != null);
            if (drops != null) {
                for (ItemStack drop : drops) {
                    if (StackUtil.putInInventory(this, Direction.XN, drop, true) == 0) {
                        StackUtil.dropAsEntity(this.worldObj, this.xCoord, this.yCoord, this.zCoord, drop);
                    } else {
                        StackUtil.putInInventory(this, Direction.XN, drop, false);
                    }

                    this.energy -= 100.0D;
                    if (cropnalyzer != null) {
                        this.energy -= 100.0D;
                    }
                }
            }
        }
    }

    public ContainerBase<IC_AdvancedHarvester> getGuiContainer(EntityPlayer entityPlayer) {
        return new AdvancedContainerCropHavester(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new AdvancedGuiCropHavester(new AdvancedContainerCropHavester(entityPlayer, this));
    }

    public String getInventoryName() {
        return "AdvancedCropHavester";
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemProducing);
    }

    public double getEnergy() {
        return this.energy;
    }

    public boolean useEnergy(double amount) {
        if (this.energy >= amount) {
            this.energy -= amount;
            return true;
        } else {
            return false;
        }
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    private boolean isInvFull() {
        for (int i = 0; i < this.contentSlot.size(); ++i) {
            ItemStack stack = this.contentSlot.get(i);
            if (stack == null || stack.stackSize < 64) {
                return false;
            }
        }

        return true;
    }
}
