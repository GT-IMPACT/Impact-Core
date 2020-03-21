package com.impact.modChest.chestHSLA;

import com.impact.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestHSLA extends Container_BaseChest
{

	public ContainerChestHSLA(@Nonnull final TEChestHSLA TEChestHSLA, final InventoryPlayer inventoryPlayer)
	{
		super(TEChestHSLA, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 99;
	}// Общее количество слотов
	protected int getX(){
		return 11;
	} // Количество слотов в ряду
	protected int getY(){
		return 9;
	}// Количество слотов в столбце
	protected int getPosXInv(){
		return 26;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}