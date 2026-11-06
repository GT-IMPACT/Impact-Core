package com.impact.mods.gregtech.gui.aerostat;


import com.impact.network.NetworkPackets;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static space.impact.packet_network.network.NetworkHandler.sendToServer;

public class GUI_FirstAerostat extends GT_GUIContainerMetaTile_Machine {

    private GuiTextField gui;
    private GuiButton apply;

    public GUI_FirstAerostat(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new Container_FirstAerostat(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "Aerostat.png");
    }

    @Override
    public void initGui() {
        super.initGui();

        int center = guiLeft + xSize / 2;
        apply = new GuiButton(0, center - 21, guiTop + 51, 38, 20, "Accept");
        buttonList.add(apply);

        gui = new GuiTextField(fontRendererObj, center - 60, guiTop + 20, 120, fontRendererObj.FONT_HEIGHT);
        gui.setEnableBackgroundDrawing(false);
        gui.setMaxStringLength(16);
        gui.setTextColor(16777215);
        gui.setVisible(true);
        gui.setFocused(true);
        gui.setText("");
    }

    @Override
    protected final void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(new ResourceLocation("gregtech:textures/gui/Aerostat.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        this.fontRendererObj.drawString("Set name station:", guiLeft + 28, guiTop + 8, 0);
        apply.enabled = !gui.getText().trim().isEmpty();

        gui.drawTextBox();
    }

    @Override
    protected void keyTyped(char character, int key) {
        if (!checkHotbarKeys(key)) {
            if (key == 28) {
                actionPerformed(apply);
            }
            if (character == ' ' && this.gui.getText().isEmpty()) {
                return;
            }

            if (!gui.textboxKeyTyped(character, key)) {
                super.keyTyped(character, key);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton btn) {
        if (btn == apply && !gui.getText().trim().isEmpty()) {
            sendToServer(
                    Minecraft.getMinecraft().thePlayer,
                    NetworkPackets.StreamPacket.transaction(gui.getText())
            );
            mc.thePlayer.closeScreen();
        }
    }
}
