package com.gwppcore.modChest.WroughtIron_Chest;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import com.gwppcore.modChest.Container_BaseChest;
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