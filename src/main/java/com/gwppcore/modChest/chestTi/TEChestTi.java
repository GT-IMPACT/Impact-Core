package com.gwppcore.modChest.chestTi;

import com.gwppcore.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TEChestTi extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation("gwppcore", "textures/entity/chestTi.png");

	public TEChestTi() {
		super(64, 117);
	}

	@Override
	public final String getInventoryName()
	{
		return "Titanium Chest";
	}

	@Override
	public int invSize(){
		return 117;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}