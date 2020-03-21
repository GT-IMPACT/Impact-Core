package com.gwppcore.modChest.WroughtIron_Chest;

import com.gwppcore.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TEWroughtIronChest extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation("gwppcore", "textures/entity/chestWroughtIron.png");

	public TEWroughtIronChest() {
		super(64, 45);
	}

	@Override
	public final String getInventoryName()
	{
		return "Wrought Iron Chest";
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