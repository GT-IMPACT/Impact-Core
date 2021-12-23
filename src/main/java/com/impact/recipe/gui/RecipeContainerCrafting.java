package com.impact.recipe.gui;

import com.impact.impact;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_RecipeEditor;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_RecipeEditorCrafting;
import com.impact.network.IPacketInteger;
import com.impact.network.ToClient_String;
import com.impact.recipe.maps.RecipesJson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

import static com.impact.recipe.maps.RecipesJson.loadCrafting;

public class RecipeContainerCrafting extends GT_ContainerMetaTile_Machine {
	
	public RecipeContainerCrafting(InventoryPlayer aPlayerInventory, IGregTechTileEntity aTileEntity) {
		super(aPlayerInventory, aTileEntity, 12, 86, true);
	}
	
	@Override
	public void addSlots(InventoryPlayer aPlayerInventory) {
		int indexSlot = 0;
		int y, x;
		for (y = 0; y < 3; y++) {
			for (x = 0; x < 3; x++) {
				addSlotToContainer(new Slot(this.mTileEntity, indexSlot++, 8 + x * 18, 8 + y * 18));
			}
		}
		addSlotToContainer(new Slot(this.mTileEntity, indexSlot++, 122, 8));
		
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, indexSlot++, 92, 8, false, true, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, indexSlot++, 92, 8 + 36, false, true, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, indexSlot, 92, 8 + 36 + 36, false, true, 1));
	}
	
	public int getSlotCount() {
		return 10;
	}
	
	public int getShiftClickSlotCount() {
		return 10;
	}
	
	@Override
	public ItemStack slotClick(int index, int mouse, int hotkeys, EntityPlayer player) {
		try {
			GTMTE_RecipeEditorCrafting recipeEditor = (GTMTE_RecipeEditorCrafting) mTileEntity.getMetaTileEntity();
			if (index <= 9) {
				if (recipeEditor.mInventory[index] != null) {
					if (mouse == 2) {
							if (recipeEditor.mInventory[index].getTagCompound() == null) {
								recipeEditor.mInventory[index].setTagCompound(new NBTTagCompound());
						    }
							if (!recipeEditor.mInventory[index].getTagCompound().hasKey("oredict")) {
								recipeEditor.mInventory[index].stackTagCompound.setString("oredict", "oredict");
								if (!mTileEntity.isServerSide()) impact.proxy.addClientSideChatMessages("#" + index + " OreDict Enabled");
							} else {
								if (recipeEditor.mInventory[index].getTagCompound().hasKey("oredict")) {
									recipeEditor.mInventory[index].getTagCompound().removeTag("oredict");
									if (!mTileEntity.isServerSide()) impact.proxy.addClientSideChatMessages("#" + index + " OreDict Disabled");
								}
							}
						return null;
					}
				}
			}
			if (!mTileEntity.isServerSide()) {
				if (index == 10) {
					if (hotkeys == 1) {
						RecipesJson.saveCrafting();
					} else {
						recipeEditor.save();
					}
				}
				if (index == 12) {
					if (hotkeys == 1) {
						loadCrafting();
					} else {
						Arrays.fill(recipeEditor.mInventory, null);
					}
				}
			}
		} catch (Exception ignored) {
		}
		return super.slotClick(index, mouse, hotkeys, player);
	}
}