package com.impact.mods.gregtech.gui.aerostat;


import com.impact.network.ToServer_String;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_FirstAerostat extends GT_GUIContainerMetaTile_Machine {
	
	private GuiTextField gui;
	private GuiButton apply;
	
	public GUI_FirstAerostat(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
		super(new Container_FirstAerostat(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "Aerostat.png");
	}
	
	public void initGui() {
		super.initGui();
		
		buttonList.add(apply = new GuiButton(0, guiLeft + xSize / 2 - 21, guiTop + 51, 38, 20, "Accept"));
		
		gui = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 120 / 2, guiTop + 20, 120, fontRendererObj.FONT_HEIGHT);
		gui.setEnableBackgroundDrawing(false);
		gui.setMaxStringLength(16);
		gui.setTextColor(16777215);
		gui.setVisible(true);
		gui.setFocused(true);
		gui.setText("");
	}
	
	protected final void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(new ResourceLocation("gregtech:textures/gui/Aerostat.png"));
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		this.fontRendererObj.drawString("Set name station:", guiLeft + 28, guiTop + 8, 0);
		try {
			apply.enabled = (gui.getText().length() > 0);
		} catch (NumberFormatException e) {
			apply.enabled = false;
		}
		
		gui.drawTextBox();
	}
	
	protected void keyTyped(char character, int key) {
		if (!checkHotbarKeys(key)) {
			if (key == 28) {
				actionPerformed(apply);
			}
			if (character == ' ' && this.gui.getText().isEmpty()) {
				return;
			}
			
			if (this.gui.textboxKeyTyped(character, key)) {
				String out = gui.getText();
				gui.setText(out);
			} else {
				super.keyTyped(character, key);
			}
		}
	}
	
	protected void actionPerformed(GuiButton btn) {
		if (btn == apply) {
			new ToServer_String(gui.getText()).sendToServer();
			mc.thePlayer.closeScreen();
		}
	}
}
