package com.impact.modChest.chestTi;

import com.impact.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestTi extends Container_BaseChest
{

	public ContainerChestTi(@Nonnull final TEChestTi TEChestTi, final InventoryPlayer inventoryPlayer)
	{
		super(TEChestTi, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 117;
	}// Общее количество слотов
	protected int getX(){
		return 13;
	} // Количество слотов в ряду
	protected int getY(){
		return 9;
	}// Количество слотов в столбце
	protected int getPosXInv(){
		return 44;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}