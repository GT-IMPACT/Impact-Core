package com.impact.mods.modChest.chestOs;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TEChestOs extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation(Refstrings.MODID, "textures/entity/chestOs.png");

	public TEChestOs() {
		super(64, 189);
	}

	@Override
	public final String getInventoryName()
	{
		return "Osmium Chest";
	}

	@Override
	public int invSize(){
		return 189;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	public ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}