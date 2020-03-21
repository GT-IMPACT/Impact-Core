package com.impact.modChest.chestW;

import com.impact.modChest.BASE.Renderer_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public final class ItemRendererChestW implements IItemRenderer
{
	private static final TEChestW TE_W_CHEST = new TEChestW();

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
			Renderer_BaseChest.instance.renderTileEntityAt(TE_W_CHEST, -0.5F, -0.5F, -0.5F, 0);
		else
			Renderer_BaseChest.instance.renderTileEntityAt(TE_W_CHEST, 0, 0, 0, 0);
	}
}