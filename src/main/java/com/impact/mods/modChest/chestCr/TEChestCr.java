package com.impact.mods.modChest.chestCr;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TEChestCr extends TE_BaseChest {
	public static ResourceLocation MODEL_CHEST = new ResourceLocation(Refstrings.MODID, "textures/entity/chestCr.png");

	public TEChestCr() {
		super(64, 153);
	}

	@Override
	public String getInventoryName() {
		return "Chrome Chest";
	}

	@Override
	public int invSize(){
		return 153;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	public ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}