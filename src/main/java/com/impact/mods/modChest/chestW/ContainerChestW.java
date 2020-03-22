package com.impact.mods.modChest.chestW;

import com.impact.mods.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerChestW extends Container_BaseChest
{

	public ContainerChestW(@Nonnull final TEChestW TEChestW, final InventoryPlayer inventoryPlayer)
	{
		super(TEChestW, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 135;
	}// Общее количество слотов
	protected int getX(){
		return 15;
	} // Количество слотов в ряду
	protected int getY(){
		return 9;
	}// Количество слотов в столбце
	protected int getPosXInv(){
		return 62;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 194;
	}// Начала координаты инвентаря игрока по Y

}