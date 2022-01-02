package com.impact.recipe.gui;

import codechicken.nei.recipe.GuiCraftingRecipe;
import com.impact.client.gui.GuiIntegerBox;
import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine;
import com.impact.network.IPacketString;
import com.impact.network.ToServer_Integer;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class RecipeGuiContainer extends GT_GUIContainerMT_Machine implements IPacketString {
	
	GuiIntegerBox vol, time, special;
	List<GuiTextField> boxes = new ArrayList<>();
	String name, nameMap;
	
	public RecipeGuiContainer(InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity, String gui) {
		super(new RecipeContainer(aPlayerInventory, aBaseMetaTileEntity), RES_PATH_GUI + "RecipeEditor.png");
		this.ySize = 251;
		this.xSize = 200;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();
		boxes.add(vol = new GuiIntegerBox(fontRendererObj, guiLeft + 55, guiTop + 120, 115, 10));
		boxes.add(time = new GuiIntegerBox(fontRendererObj, guiLeft + 55, guiTop + 135, 115, 10));
		boxes.add(special = new GuiIntegerBox(fontRendererObj, guiLeft + 55, guiTop + 150, 115, 10));
		vol.setText("0");
		time.setText("0");
		special.setText("0");
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
		fontRendererObj.drawString(nameMap, 0, -10, Color.WHITE.hashCode(), false);
		fontRendererObj.drawString("ZBS Recipe Editor 3000", 46, -25, Color.WHITE.hashCode(), false);
		fontRendererObj.drawString("Voltage:", 10, 120, Color.BLACK.hashCode(), false);
		fontRendererObj.drawString("Time:", 10, 135, Color.BLACK.hashCode(), false);
		fontRendererObj.drawString("Special:", 10, 150, Color.BLACK.hashCode(), false);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p1, int p2, int p3) {
		super.drawGuiContainerBackgroundLayer(p1, p2, p3);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		drawTexturedModalRect(x + 92-1, y + 44 -1, 200, 18, 18, 18);
		
		drawTexturedModalRect(x + 92-1, y + 80 -1, 218, 18, 18, 18);
		
		int indexSlot = 0;
		for (int i = 0; i < 48; i++) {
			int xx = ((Slot) mContainer.inventorySlots.get(i)).xDisplayPosition - 1;
			int yy = ((Slot) mContainer.inventorySlots.get(i)).yDisplayPosition - 1;
			if (indexSlot >= 24 && indexSlot <= 47) {
				drawTexturedModalRect(x + xx, y + yy, 218, 0, 18, 18);
			} else {
				drawTexturedModalRect(x + xx, y + yy, 200, 0, 18, 18);
			}
			indexSlot++;
		}
		for (GuiTextField f : boxes) {
			f.drawTextBox();
		}

	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		if (mContainer.inventorySlots.size() >= 50) {
			int x = ((Slot) mContainer.inventorySlots.get(49)).xDisplayPosition - 1;
			int y = ((Slot) mContainer.inventorySlots.get(49)).yDisplayPosition - 1;
			getTooltip(mouseX, mouseY, x, y, 16, 16, new String[]{
					"Open NEI: ", nameMap,
			});
			x = ((Slot) mContainer.inventorySlots.get(48)).xDisplayPosition - 1;
			y = ((Slot) mContainer.inventorySlots.get(48)).yDisplayPosition - 1;
			getTooltip(mouseX, mouseY, x, y, 16, 16, new String[]{
					"Save Recipe",
					"LClick: Add to NEI",
					"Shift + LClick: Add to JSON",
			});
			x = ((Slot) mContainer.inventorySlots.get(50)).xDisplayPosition - 1;
			y = ((Slot) mContainer.inventorySlots.get(50)).yDisplayPosition - 1;
			getTooltip(mouseX, mouseY, x, y, 16, 16, new String[]{
					"Refresh",
					"LClick: Clear all Slots",
					"Shift + LClick: Reload all maps from JSON",
			});
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int par3) {
		super.mouseClicked(mouseX, mouseY, par3);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		if (mContainer.inventorySlots.size() >= 49) {
			int xx = x + ((Slot) mContainer.inventorySlots.get(49)).xDisplayPosition - 1;
			int yy = y + ((Slot) mContainer.inventorySlots.get(49)).yDisplayPosition - 1;
			if (xx <= mouseX && xx + 18 >= mouseX && yy <= mouseY && yy + 18 >= mouseY) {
				if (nameMap != null && !nameMap.isEmpty())
					GuiCraftingRecipe.openRecipeGui(nameMap);
			}
		}
		
		for (GuiTextField f : boxes) {
			f.mouseClicked(mouseX, mouseY, par3);
		}
	}
	
	@Override
	protected void keyTyped(char ch, int id) {
		super.keyTyped(ch, id);
		for (GuiTextField f : boxes) {
			f.textboxKeyTyped(ch, id);
		}
		
		if (vol.getText().isEmpty() || time.getText().isEmpty()) {
			return;
		}
		
		try {
			int[] ints;
			int v = Integer.parseInt(vol.getText());
			int t = Integer.parseInt(time.getText());
			if (!special.getText().isEmpty()) {
				int s = Integer.parseInt(special.getText());
				ints = new int[] {v, t, s};
			} else {
				ints = new int[] {v, t};
			}
			new ToServer_Integer(ints).sendToServer();
		} catch (Exception ignored) {
		
		}
		
	}
	
	@Override
	public void update(String... str) {
		name = str[0];
		nameMap = str[1];
		vol.setText(str[2]);
		time.setText(str[3]);
		special.setText(str[4]);
	}
}
