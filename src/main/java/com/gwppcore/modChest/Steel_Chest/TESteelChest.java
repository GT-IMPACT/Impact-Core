package com.gwppcore.modChest.Steel_Chest;

import com.gwppcore.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TESteelChest extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation("gwppcore", "textures/entity/SteelChest.png");

	public TESteelChest() {
		super(64, 45);
	}

	@Override
	public final String getInventoryName()
	{
		return "Steel Chest";
	}

	@Override
	public int invSize(){
		return 45;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}