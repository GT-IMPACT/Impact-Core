package com.gwppcore.modChest.Steel_Chest;

import com.gwppcore.modChest.BASE.Container_BaseChest;
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
		return 45;
	}
	protected int getX(){
		return 9;
	}
	protected int getY(){
		return 5;
	}
	protected int getPosXInv(){
		return 8;
	}
	protected int getPosYInv(){
		return 122;
	}

}