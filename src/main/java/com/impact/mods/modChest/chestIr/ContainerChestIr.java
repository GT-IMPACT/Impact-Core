package com.impact.mods.modChest.chestIr;

import com.impact.mods.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestIr extends Container_BaseChest
{

	public ContainerChestIr(@Nonnull final TEChestIr TEChestIr, final InventoryPlayer inventoryPlayer)
	{
		super(TEChestIr, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 171;
	}// Общее количество слотов
	protected int getX(){
		return 19;
	} // Количество слотов в ряду
	protected int getY(){
		return 9;
	}// Количество слотов в столбце
	protected int getPosXInv(){
		return 98;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}