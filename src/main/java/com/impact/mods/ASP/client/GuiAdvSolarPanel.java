package com.impact.mods.asp.client;

import com.impact.mods.asp.common.ContainerAdvSolarPanel;
import com.impact.mods.asp.common.te.TileEntitySolarPanel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiAdvSolarPanel extends GuiContainer {

  private static ResourceLocation tex = new ResourceLocation("impactsolarpanel",
      "textures/gui/GUIAdvancedSolarPanel.png");
  public TileEntitySolarPanel tileentity;

  public GuiAdvSolarPanel(TileEntitySolarPanel tileentitysolarpanel,
      InventoryPlayer inventoryplayer) {
    super(new ContainerAdvSolarPanel(inventoryplayer, tileentitysolarpanel));
    this.tileentity = tileentitysolarpanel;
    this.allowUserInput = false;
    this.xSize = 194;
    this.ySize = 168;
  }

  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    String formatPanelName = I18n.format(this.tileentity.panelName);
    this.fontRendererObj.drawString(EnumChatFormatting.WHITE + formatPanelName, 20, 11, 0);
    String generatingString = I18n.format("Gen") + ": ";
    String energyPerTickString = I18n.format("gui.AdvancedSolarPanel.energyPerTick");
    this.fontRendererObj.drawString(
        EnumChatFormatting.GREEN + "" + this.tileentity.storage + EnumChatFormatting.WHITE + " / "
            + EnumChatFormatting.YELLOW + this.tileentity.maxStorage + EnumChatFormatting.WHITE
            + " EU", 20, 25, 13487565);
    this.fontRendererObj.drawString(
        EnumChatFormatting.WHITE + generatingString + EnumChatFormatting.GREEN
            + this.tileentity.generating + EnumChatFormatting.WHITE + " " + energyPerTickString, 20,
        36, 13487565);
  }

  protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(tex);
    int h = (this.width - this.xSize) / 2;
    int k = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(h, k, 0, 0, this.xSize, this.ySize);
    if (this.tileentity.storage >= 0) {
      double l = this.tileentity.gaugeEnergyScaled(48);
      this.drawTexturedModalRect(h + 19, k + 61, 195, 0, Math.min(48, (int) l), 12);
    }

    if (this.tileentity.skyIsVisible) {
      if (this.tileentity.sunIsUp) {
        this.drawTexturedModalRect(h + 84, k + 63, 198, 18, 8, 8);
      } else {
        this.drawTexturedModalRect(h + 84, k + 63, 213, 18, 8, 8);
      }
    }

  }
}
