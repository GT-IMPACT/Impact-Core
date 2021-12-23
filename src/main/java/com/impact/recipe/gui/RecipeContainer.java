package com.impact.recipe.gui;

import com.impact.impact;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_RecipeEditor;
import com.impact.network.IPacketInteger;
import com.impact.network.ToClient_String;
import com.impact.recipe.maps.RecipesJson;
import gregtech.api.enums.ItemList;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

import static com.impact.recipe.maps.RecipesJson.load;

public class RecipeContainer extends GT_ContainerMetaTile_Machine implements IPacketInteger {
	
	
	public RecipeContainer(InventoryPlayer aPlayerInventory, IGregTechTileEntity aTileEntity) {
		super(aPlayerInventory, aTileEntity, 12, 86, true);
	}
	
	@Override
	public void addSlots(InventoryPlayer aPlayerInventory) {
		int indexSlot = 0;
		int y, x;
		for (y = 0; y < 4; y++) {
			for (x = 0; x < 4; x++) {
				addSlotToContainer(new Slot(this.mTileEntity, indexSlot++, 8 + x * 18, 8 + y * 18));
			}
		}
		for (y = 0; y < 2; y++) {
			for (x = 0; x < 4; x++) {
				addSlotToContainer(new Slot(this.mTileEntity, indexSlot++, 122 + x * 18, 8 + y * 18));
			}
		}
		for (y = 0; y < 2; y++) {
			for (x = 0; x < 4; x++) {
				addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, indexSlot++, 8 + x * 18, 80 + y * 18, false, true, 1));
			}
		}
		for (y = 0; y < 4; y++) {
			for (x = 0; x < 4; x++) {
				addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, indexSlot++, 122 + x * 18, 44 + y * 18, false, true, 1));
			}
		}
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, indexSlot++, 92, 8, false, true, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, indexSlot++, 92, 8 + 36, false, true, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, indexSlot, 92, 8 + 36 + 36, false, true, 1));
	}
	
	public int getSlotCount() {
		return 24;
	}
	
	public int getShiftClickSlotCount() {
		return 24;
	}
	
	@Override
	public ItemStack slotClick(int index, int mouse, int hotkeys, EntityPlayer player) {
		try {
			GTMTE_RecipeEditor recipeEditor = (GTMTE_RecipeEditor) mTileEntity.getMetaTileEntity();
			if (index < 16) {
				if (hotkeys == 1) {
					if (mTileEntity.isClientSide()) return null;
					if (recipeEditor.mInventory[index] != null) {
						recipeEditor.mInventory[index].stackSize += mouse == 1 ? 1 : recipeEditor.mInventory[index].stackSize <= 0 ? 0 :  -1;
						impact.proxy.addClientSideChatMessages(recipeEditor.mInventory[index].toString());
						return recipeEditor.mInventory[index];
					}
				}
			}
			if (index >= 16 && index <= 23) {
				recipeEditor.chanceEnabled = false;
				int chanceIndex = index - 16;
				if (hotkeys == 1) {
					recipeEditor.chanceEnabled = true;
					if (mTileEntity.isClientSide()) return null;
					if (recipeEditor.mInventory[index] != null) {
						int chance = recipeEditor.chance[chanceIndex] / 100;
						chance += mouse == 1 ? 1 : chance <= 0 ? 100 : -1;
						if (chance > 100) {
							chance = 0;
						}
						recipeEditor.chance[chanceIndex] = chance*100;
						impact.proxy.addClientSideChatMessages("Chance product set: " + recipeEditor.chance[chanceIndex]/100 + "% (" + recipeEditor.chance[chanceIndex] + ")");
						return null;
					}
				}
			}
			if (index <= 23) {
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
			if (index == 48) {
				if (hotkeys == 1) {
					RecipesJson.save();
				} else {
					recipeEditor.save();
				}
			}
			if (index == 49) {
			}
			if (index == 50) {
				if (hotkeys == 1) {
					load();
				} else {
					recipeEditor.chanceEnabled = false;
					Arrays.fill(recipeEditor.mInventory, null);
				}
			}
			if (index <= 47 && index >= 24) {
				ItemStack tStackHeld = player.inventory.getItemStack();
				if (tStackHeld != null) {
					FluidStack tFluidHeld = GT_Utility.getFluidForFilledItem(tStackHeld, true);
					if (tFluidHeld != null) {
						recipeEditor.mInventory[index] = GT_Utility.getFluidDisplayStack(tFluidHeld, true);
					}
				} else {
					if (ItemList.Display_Fluid.isStackEqual(recipeEditor.mInventory[index], true, true)) {
						recipeEditor.mInventory[index] = null;
					}
				}
			}
		} catch (Exception e) {
		}
		return super.slotClick(index, mouse, hotkeys, player);
	}
	
	int ticker;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (mTileEntity.isClientSide() && mTileEntity.getMetaTileEntity() == null) return;
		if (mTileEntity.isServerSide()) {
			GTMTE_RecipeEditor recipeEditor = (GTMTE_RecipeEditor) mTileEntity.getMetaTileEntity();
			if (ticker % 20 == 0) {
				if (recipeEditor.map != null) {
					new ToClient_String(recipeEditor.nameGui, recipeEditor.map.mNEIName,
							recipeEditor.voltage + "", recipeEditor.time + "", recipeEditor.special + ""
					).sendToClients();
				}
			}
			ticker++;
		}
	}
	
	@Override
	public void update(int... integer) {
		GTMTE_RecipeEditor recipeEditor = (GTMTE_RecipeEditor) mTileEntity.getMetaTileEntity();
		switch (integer.length) {
			case 2:
				recipeEditor.voltage = integer[0];
				recipeEditor.time = integer[1];
				recipeEditor.special = 0;
				break;
			case 3:
				recipeEditor.voltage = integer[0];
				recipeEditor.time = integer[1];
				recipeEditor.special = integer[2];
				break;
		}
	}
}