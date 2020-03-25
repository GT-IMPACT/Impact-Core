package com.impact.mods.modChest.Steel_Chest;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TESteelChest extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation(Refstrings.MODID, "textures/entity/chestSteel.png");

	public TESteelChest() {
		super(64, 63);
	}

	@Override
	public final String getInventoryName()
	{
		return "Steel Chest";
	}

	@Override
	public int invSize(){
		return 63;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}