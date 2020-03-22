package com.impact.mods.modChest.Steel_Chest;

import com.impact.mods.modChest.BASE.Container_BaseChest;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.InventoryPlayer;
import javax.annotation.Nonnull;

@ChestContainer
public class ContainerSteelChest extends Container_BaseChest
{

	public ContainerSteelChest(@Nonnull final TESteelChest TESteelChest, final InventoryPlayer inventoryPlayer)
	{
		super(TESteelChest, inventoryPlayer);
	}

	@Override
	public int getSlot(){
		return 63;
	} // Общее количество слотов
	protected int getX(){
		return 9;
	}  // Количество слотов в ряду
	protected int getY(){
		return 7;
	}  // Количество слотов в столбце
	protected int getPosXInv(){
		return 8;
	} // Начала координаты инвентаря игрока по X
	protected int getPosYInv(){
		return 158;
	} // Начала координаты инвентаря игрока по Y

}