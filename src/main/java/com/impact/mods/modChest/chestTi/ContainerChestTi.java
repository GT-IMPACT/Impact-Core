package com.impact.mods.modChest.chestTi;

import com.impact.mods.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestTi extends Container_BaseChest {

	public ContainerChestTi(TEChestTi TEChestTi, InventoryPlayer inventoryPlayer) {
		super(TEChestTi, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 117;
	}// Общее количество слотов
	public int getX(){
		return 13;
	} // Количество слотов в ряду
	public int getY(){
		return 9;
	}// Количество слотов в столбце
	public int getPosXInv(){
		return 44;
	}// Начала координаты инвентаря игрока по X
	public int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}