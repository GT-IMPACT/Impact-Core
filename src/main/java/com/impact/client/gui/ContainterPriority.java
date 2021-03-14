package com.impact.client.gui;

import appeng.container.guisync.GuiSync;
import appeng.helpers.IPriorityHost;
import appeng.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainterPriority extends Container {

    private final IPriorityHost priHost;
    @GuiSync(2)
    public long PriorityValue = 0;
    @SideOnly(Side.CLIENT)
    private GuiTextField textField;

    public ContainterPriority(final InventoryPlayer ip, final IPriorityHost te) {
        this.priHost = te;
    }

    @SideOnly(Side.CLIENT)
    public void setTextField(final GuiTextField level) {
        this.textField = level;
        this.textField.setText(String.valueOf(this.PriorityValue));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.priHost != null;
    }

    public void setPriority(final int newValue, final EntityPlayer player) {
        this.priHost.setPriority(newValue);
        this.PriorityValue = newValue;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (Platform.isServer()) {
            this.PriorityValue = this.priHost.getPriority();
        }
    }


    public void onUpdate(final String field, final Object oldValue, final Object newValue) {
        if (field.equals("PriorityValue")) {
            if (this.textField != null) {
                this.textField.setText(String.valueOf(this.PriorityValue));
            }
        }
    }
}
