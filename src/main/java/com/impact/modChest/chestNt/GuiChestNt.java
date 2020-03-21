package com.impact.modChest.chestNt;

import com.impact.modChest.BASE.Gui_BaseChest;
import com.impact.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiChestNt extends Gui_BaseChest
{
	private final TEChestNt TEChestNt;

	public GuiChestNt(@Nonnull final TEChestNt TEChestNt, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestNt(TEChestNt, inventoryPlayer));
		this.TEChestNt = TEChestNt;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEChestNt;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation("gwppcore", "textures/gui/ChestNt.png");
	}
	protected int getXSize(){
		return 428;
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