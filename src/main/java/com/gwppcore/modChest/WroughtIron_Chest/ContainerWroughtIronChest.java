package com.gwppcore.modChest.WroughtIron_Chest;

import com.gwppcore.modChest.BASE.Container_BaseChest;
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
		return 36;
	}
	protected int getX(){
		return 9;
	}
	protected int getY(){
		return 4;
	}
	protected int getPosXInv(){
		return 8;
	}
	protected int getPosYInv(){
		return 104;
	}

}