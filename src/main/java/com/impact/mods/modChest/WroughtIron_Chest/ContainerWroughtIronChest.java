package com.impact.mods.modChest.WroughtIron_Chest;

import com.impact.mods.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

@ChestContainer
public class ContainerWroughtIronChest extends Container_BaseChest
{

	public ContainerWroughtIronChest(@Nonnull final TEWroughtIronChest TEWroughtIronChest, final InventoryPlayer inventoryPlayer)
	{
		super(TEWroughtIronChest, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 45;
	} // Общее количество слотов
	protected int getX(){
		return 9;
	}  // Количество слотов в ряду
	protected int getY(){
		return 5;
	}  // Количество слотов в столбце
	protected int getPosXInv(){
		return 8;
	}// Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 122;
	} // Начала координаты инвентаря игрока по Y

}