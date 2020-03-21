package com.impact.modChest.chestHSLA;

import com.impact.lib.Refstrings;
import com.impact.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TEChestHSLA extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation(Refstrings.MODID, "textures/entity/chestHSLA.png");

	public TEChestHSLA() {
		super(64, 99);
	}

	@Override
	public final String getInventoryName()
	{
		return "HSLA Chest";
	}

	@Override
	public int invSize(){
		return 99;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}