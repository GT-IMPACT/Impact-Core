package com.impact.mods.modChest.chestOs;

import com.impact.mods.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestOs extends Container_BaseChest {

	public ContainerChestOs(TEChestOs TEChestOs, InventoryPlayer inventoryPlayer) {
		super(TEChestOs, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 189;
	}// Общее количество слотов
	public int getX(){
		return 21;
	} // Количество слотов в ряду
	public int getY(){
		return 9;
	}// Количество слотов в столбце
	public int getPosXInv(){
		return 116;
	}// Начала координаты инвентаря игрока по X
	public int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y
}