package com.impact.mods.modChest.chestCr;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiChestCr extends Gui_BaseChest
{
	private final TEChestCr TEChestCr;

	public GuiChestCr(@Nonnull final TEChestCr TEChestCr, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestCr(TEChestCr, inventoryPlayer));
		this.TEChestCr = TEChestCr;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEChestCr;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestCr.png");
	}
	protected int getXSize(){
		return 320;
	}
	protected int getYSize(){
		return 275;
	}
	protected int getScale(){
		return 500;
	}
	protected int getNamedPos(){
		return 8;
	}

}