package com.gwppcore.modChest.chestHSLA;

import com.gwppcore.modChest.BASE.Renderer_BaseChest;
import com.gwppcore.modChest.chestAL.TEChestAl;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public final class ItemRendererChestHSLA implements IItemRenderer
{
	private static final TEChestHSLA TE_HSLA_CHEST = new TEChestHSLA();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if (type == ItemRenderType.ENTITY)
			Renderer_BaseChest.instance.renderTileEntityAt(TE_HSLA_CHEST, -0.5F, -0.5F, -0.5F, 0);
		else
			Renderer_BaseChest.instance.renderTileEntityAt(TE_HSLA_CHEST, 0, 0, 0, 0);
	}
}