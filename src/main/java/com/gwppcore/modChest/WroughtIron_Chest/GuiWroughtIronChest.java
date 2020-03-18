package com.gwppcore.modChest.WroughtIron_Chest;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import com.gwppcore.modChest.Gui_BaseChest;
import com.gwppcore.modChest.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;


import javax.annotation.Nonnull;

public final class GuiWroughtIronChest extends Gui_BaseChest
{
	private final TEWroughtIronChest TEWroughtIronChest;

	public GuiWroughtIronChest(@Nonnull final TEWroughtIronChest TEWroughtIronChest, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerWroughtIronChest(TEWroughtIronChest, inventoryPlayer));
		this.TEWroughtIronChest = TEWroughtIronChest;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEWroughtIronChest;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation("gwppcore", "textures/gui/WroughtIronChest.png");
	}
	protected int getXSize(){
		return 175;
	}
	protected int getYSize(){
		return 185;
	}
	protected int getNamedPos(){
		return 8;
	}

}