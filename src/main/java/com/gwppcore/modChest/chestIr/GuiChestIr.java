package com.gwppcore.modChest.chestIr;

import com.gwppcore.modChest.BASE.Gui_BaseChest;
import com.gwppcore.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiChestIr extends Gui_BaseChest
{
	private final TEChestIr TEChestIr;

	public GuiChestIr(@Nonnull final TEChestIr TEChestIr, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestIr(TEChestIr, inventoryPlayer));
		this.TEChestIr = TEChestIr;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEChestIr;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation("gwppcore", "textures/gui/ChestIr.png");
	}
	protected int getXSize(){
		return 356;
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