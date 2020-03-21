package com.impact.modChest.chestOs;

import com.impact.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestOs extends Container_BaseChest
{

	public ContainerChestOs(@Nonnull final TEChestOs TEChestOs, final InventoryPlayer inventoryPlayer)
	{
		super(TEChestOs, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 189;
	}// Общее количество слотов
	protected int getX(){
		return 21;
	} // Количество слотов в ряду
	protected int getY(){
		return 9;
	}// Количество слотов в столбце
	protected int getPosXInv(){
		return 116;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}