package com.impact.mods.modChest.chestNt;

import com.impact.mods.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestNt extends Container_BaseChest {

	public ContainerChestNt(TEChestNt TEChestNt, InventoryPlayer inventoryPlayer) {
		super(TEChestNt, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 207;
	}// Общее количество слотов
	public int getX(){
		return 23;
	} // Количество слотов в ряду
	public int getY(){
		return 9;
	}// Количество слотов в столбце
	public int getPosXInv(){
		return 134;
	}// Начала координаты инвентаря игрока по X
	public int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}