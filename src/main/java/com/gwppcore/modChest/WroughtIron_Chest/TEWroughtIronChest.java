package com.gwppcore.modChest.WroughtIron_Chest;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import com.gwppcore.modChest.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TEWroughtIronChest extends TE_BaseChest
{
	public static ResourceLocation COMPRESSED_CHEST_TEXTURE = new ResourceLocation("gwppcore", "textures/entity/compressedChest.png");

	public TEWroughtIronChest() {
		super(64, 36);
	}

	@Override
	public final String getInventoryName()
	{
		return "Wrought Iron Chest";
	}

	@Override
	public int invSize(){
		return 36;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return COMPRESSED_CHEST_TEXTURE;
	}
}