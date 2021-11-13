package com.impact.mods.gregtech.gui.regulatechest;


import com.impact.client.gui.GuiIntegerBox;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_RegulateDigitalChest;
import com.impact.network.ToServer_Integer;
import com.impact.network.special.ToServer_RegulateDigitalChest;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.text.ParseException;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_ValueRegulateChest extends GT_GUIContainerMetaTile_Machine {
	
	private GuiIntegerBox amount;
	private GuiButton apply;
	private GuiButton plus1;
	private GuiButton plus10;
	private GuiButton plus100;
	private GuiButton plus1000;
	private GuiButton minus1;
	private GuiButton minus10;
	private GuiButton minus100;
	private GuiButton minus1000;
	
	public GUI_ValueRegulateChest(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
		super(new Container_ValueRegulateChest(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "VolumetricFlask.png");
	}
	
	public void initGui() {
		super.initGui();
		
		buttonList.add(plus1 = new GuiButton(0, guiLeft + 20, guiTop + 26, 22, 20, "+1"));
		buttonList.add(plus10 = new GuiButton(0, guiLeft + 48, guiTop + 26, 28, 20, "+10"));
		buttonList.add(plus100 = new GuiButton(0, guiLeft + 82, guiTop + 26, 32, 20, "+100"));
		buttonList.add(plus1000 = new GuiButton(0, guiLeft + 120, guiTop + 26, 38, 20, "+1000"));
		
		buttonList.add(minus1 = new GuiButton(0, guiLeft + 20, guiTop + 75, 22, 20, "-1"));
		buttonList.add(minus10 = new GuiButton(0, guiLeft + 48, guiTop + 75, 28, 20, "-10"));
		buttonList.add(minus100 = new GuiButton(0, guiLeft + 82, guiTop + 75, 32, 20, "-100"));
		buttonList.add(minus1000 = new GuiButton(0, guiLeft + 120, guiTop + 75, 38, 20, "-1000"));
		
		buttonList.add(apply = new GuiButton(0, guiLeft + 128, guiTop + 51, 38, 20, "Accept"));
		
		amount = new GuiIntegerBox(fontRendererObj, guiLeft + 62, guiTop + 57, 59, fontRendererObj.FONT_HEIGHT, GTMTE_RegulateDigitalChest.MAX_COUNT);
		amount.setEnableBackgroundDrawing(false);
		amount.setMaxStringLength(16);
		amount.setTextColor(16777215);
		amount.setVisible(true);
		amount.setFocused(true);
		amount.setText("64");
	}
	
	protected final void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(mGUIbackground);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		try {
			Long.parseLong(amount.getText());
			apply.enabled = (amount.getText().length() > 0);
		} catch (NumberFormatException e) {
			apply.enabled = false;
		}
		
		amount.drawTextBox();
	}
	
	protected void keyTyped(char character, int key) {
		if (!checkHotbarKeys(key)) {
			if (key == 28)
				actionPerformed(apply);
			if (((key == 211) || (key == 205) || (key == 203) || (key == 14) || (character == '-') || (Character.isDigit(character))) && (amount.textboxKeyTyped(character, key))) {
				try {
					String out = amount.getText();
					boolean fixed = false;
					while ((out.startsWith("0")) && (out.length() > 1)) {
						out   = out.substring(1);
						fixed = true;
					}
					if (fixed) {
						amount.setText(out);
					}
					if (out.isEmpty()) {
						out = "0";
					}
					long result = Long.parseLong(out);
					if (result < 0L) {
						amount.setText("1");
					}
					
				} catch (NumberFormatException ignored) {
				}
			} else {
				super.keyTyped(character, key);
			}
		}
	}
	
	protected void actionPerformed(GuiButton btn) {
		try {
			if (btn == apply) {
				new ToServer_RegulateDigitalChest(Integer.parseInt(amount.getText()), mContainer.mTileEntity.getXCoord(),
						mContainer.mTileEntity.getYCoord(), mContainer.mTileEntity.getZCoord()).sendToServer();
				mc.thePlayer.closeScreen();
			}
			
		} catch (NumberFormatException e) {
			amount.setText("1");
		}
		
		boolean isPlus = (btn == plus1) || (btn == plus10) || (btn == plus100) || (btn == plus1000);
		boolean isMinus = (btn == minus1) || (btn == minus10) || (btn == minus100) || (btn == minus1000);
		
		if ((isPlus) || (isMinus)) {
			addQty(getQty(btn));
		}
	}
	
	private void addQty(int i) {
		try {
			String out = amount.getText();
			
			boolean fixed = false;
			while ((out.startsWith("0")) && (out.length() > 1)) {
				out   = out.substring(1);
				fixed = true;
			}
			
			if (fixed) {
				amount.setText(out);
			}
			if (out.isEmpty()) {
				out = "0";
			}
			long result = Integer.parseInt(out);
			
			if ((result == 1L) && (i > 1)) {
				result = 0L;
			}
			result += i;
			if (result < 1L) {
				result = 1L;
			}
			out = Long.toString(result);
			Integer.parseInt(out);
			amount.setText(out);
		} catch (NumberFormatException ignored) {
		}
	}
	
	
	protected int getQty(GuiButton btn) {
		try {
			DecimalFormat df = new DecimalFormat("+#;-#");
			return df.parse(btn.displayString).intValue();
		} catch (ParseException ignored) {
		}
		
		return 0;
	}
}
