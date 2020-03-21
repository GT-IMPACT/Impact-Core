package com.impact.modChest.chestNt;

import com.impact.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestNt extends Container_BaseChest
{

	public ContainerChestNt(@Nonnull final TEChestNt TEChestNt, final InventoryPlayer inventoryPlayer)
	{
		super(TEChestNt, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 207;
	}// Общее количество слотов
	protected int getX(){
		return 23;
	} // Количество слотов в ряду
	protected int getY(){
		return 9;
	}// Количество слотов в столбце
	protected int getPosXInv(){
		return 134;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}