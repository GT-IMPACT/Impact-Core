package com.impact.modChest.chestCr;

import com.impact.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestCr extends Container_BaseChest
{

	public ContainerChestCr(@Nonnull final TEChestCr TEChestCr, final InventoryPlayer inventoryPlayer)
	{
		super(TEChestCr, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 153;
	}// Общее количество слотов
	protected int getX(){
		return 17;
	} // Количество слотов в ряду
	protected int getY(){
		return 9;
	}// Количество слотов в столбце
	protected int getPosXInv(){
		return 80;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}