package com.gwppcore.modChest.chestCr;

import com.gwppcore.modChest.BASE.Gui_BaseChest;
import com.gwppcore.modChest.BASE.TE_BaseChest;
import com.gwppcore.modChest.chestHSLA.ContainerChestHSLA;
import com.gwppcore.modChest.chestHSLA.TEChestHSLA;
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
		return new ResourceLocation("gwppcore", "textures/gui/ChestCr.png");
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