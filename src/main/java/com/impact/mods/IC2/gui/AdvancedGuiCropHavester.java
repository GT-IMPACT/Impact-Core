package com.impact.mods.IC2.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class AdvancedGuiCropHavester extends GuiIC2 {
    public AdvancedContainerCropHavester container;

    public AdvancedGuiCropHavester(AdvancedContainerCropHavester container1) {
        super(container1, 191);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        if (this.container.base.energy > 0.0D) {
            int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
            this.drawTexturedModalRect(this.xoffset + 153, this.yoffset + 56 - chargeLevel, 176, 14 - chargeLevel, 14, chargeLevel);
        }

    }

    public String getName() {
        return "Advanced Crop Harvester";
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(IC2.textureDomain, "textures/gui/GUICropHavester.png");
    }
}