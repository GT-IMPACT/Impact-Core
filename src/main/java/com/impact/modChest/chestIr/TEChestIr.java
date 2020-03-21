package com.impact.modChest.chestIr;

import com.impact.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TEChestIr extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation("gwppcore", "textures/entity/chestIr.png");

	public TEChestIr() {
		super(64, 171);
	}

	@Override
	public final String getInventoryName()
	{
		return "Iridium Chest";
	}

	@Override
	public int invSize(){
		return 171;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}