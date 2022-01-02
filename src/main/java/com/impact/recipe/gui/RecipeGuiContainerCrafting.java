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

public class RecipeGuiContainerCrafting extends GT_GUIContainerMT_Machine {
	
	public RecipeGuiContainerCrafting(InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity, String gui) {
		super(new RecipeContainerCrafting(aPlayerInventory, aBaseMetaTileEntity), RES_PATH_GUI + "RecipeEditor.png");
		this.ySize = 251;
		this.xSize = 200;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
		fontRendererObj.drawString("ZBS Recipe Editor 3000", 46, -25, Color.WHITE.hashCode(), false);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p1, int p2, int p3) {
		super.drawGuiContainerBackgroundLayer(p1, p2, p3);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		drawTexturedModalRect(x + 92 - 1, y + 44 - 1, 200, 18, 18, 18);
		
		drawTexturedModalRect(x + 92 - 1, y + 80 - 1, 218, 18, 18, 18);
		
		int indexSlot = 0;
		for (int i = 0; i < 10; i++) {
			int xx = ((Slot) mContainer.inventorySlots.get(i)).xDisplayPosition - 1;
			int yy = ((Slot) mContainer.inventorySlots.get(i)).yDisplayPosition - 1;
			if (indexSlot < 10) {
				drawTexturedModalRect(x + xx, y + yy, 200, 0, 18, 18);
			}
			indexSlot++;
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		if (mContainer.inventorySlots.size() >= 12) {
			int x = ((Slot) mContainer.inventorySlots.get(11)).xDisplayPosition - 1;
			int y = ((Slot) mContainer.inventorySlots.get(11)).yDisplayPosition - 1;
			getTooltip(mouseX, mouseY, x, y, 16, 16, new String[]{
					"Open NEI",
			});
			x = ((Slot) mContainer.inventorySlots.get(10)).xDisplayPosition - 1;
			y = ((Slot) mContainer.inventorySlots.get(10)).yDisplayPosition - 1;
			getTooltip(mouseX, mouseY, x, y, 16, 16, new String[]{
					"Save Recipe",
					"LClick: Add to NEI",
					"Shift + LClick: Add to JSON",
			});
			x = ((Slot) mContainer.inventorySlots.get(12)).xDisplayPosition - 1;
			y = ((Slot) mContainer.inventorySlots.get(12)).yDisplayPosition - 1;
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
		if (mContainer.inventorySlots.size() >= 11) {
			int xx = x + ((Slot) mContainer.inventorySlots.get(11)).xDisplayPosition - 1;
			int yy = y + ((Slot) mContainer.inventorySlots.get(11)).yDisplayPosition - 1;
			if (xx <= mouseX && xx + 18 >= mouseX && yy <= mouseY && yy + 18 >= mouseY) {
				GuiCraftingRecipe.openRecipeGui("crafting");
			}
		}
	}
}
