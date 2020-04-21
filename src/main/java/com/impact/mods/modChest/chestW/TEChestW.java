package com.impact.mods.modChest.chestW;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TEChestW extends TE_BaseChest {
	public static ResourceLocation MODEL_CHEST = new ResourceLocation(Refstrings.MODID, "textures/entity/chestW.png");

	public TEChestW() {
		super(64, 135);
	}

	@Override
	public String getInventoryName()
	{
		return "Tungsten Steel Chest";
	}

	@Override
	public int invSize(){
		return 135;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	public ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}