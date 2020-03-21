package com.gwppcore.modChest.chestCr;

import com.gwppcore.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TEChestCr extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation("gwppcore", "textures/entity/chestCr.png");

	public TEChestCr() {
		super(64, 153);
	}

	@Override
	public final String getInventoryName()
	{
		return "Chrome Chest";
	}

	@Override
	public int invSize(){
		return 153;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}