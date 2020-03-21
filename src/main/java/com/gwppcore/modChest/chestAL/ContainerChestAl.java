package com.gwppcore.modChest.chestAL;

import com.gwppcore.modChest.BASE.Container_BaseChest;
import com.gwppcore.modChest.Steel_Chest.TESteelChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestAl extends Container_BaseChest
{

	public ContainerChestAl(@Nonnull final TEChestAl TEChestAl, final InventoryPlayer inventoryPlayer)
	{
		super(TEChestAl, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 81;
	}// Общее количество слотов
	protected int getX(){
		return 9;
	} // Количество слотов в ряду
	protected int getY(){
		return 9;
	}// Количество слотов в столбце
	protected int getPosXInv(){
		return 8;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}