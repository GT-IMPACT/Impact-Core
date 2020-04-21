package com.impact.mods.modChest.chestAL;

import com.impact.mods.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestAl extends Container_BaseChest {

	public ContainerChestAl(TEChestAl TEChestAl, InventoryPlayer inventoryPlayer) {
		super(TEChestAl, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 81;
	}// Общее количество слотов
	public int getX(){
		return 9;
	} // Количество слотов в ряду
	public int getY(){
		return 9;
	}// Количество слотов в столбце
	public int getPosXInv(){
		return 8;
	}// Начала координаты инвентаря игрока по X
	public int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}