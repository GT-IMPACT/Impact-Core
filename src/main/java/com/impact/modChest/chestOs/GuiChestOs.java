package com.impact.modChest.chestOs;

import com.impact.modChest.BASE.Gui_BaseChest;
import com.impact.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiChestOs extends Gui_BaseChest
{
	private final TEChestOs TEChestOs;

	public GuiChestOs(@Nonnull final TEChestOs TEChestOs, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestOs(TEChestOs, inventoryPlayer));
		this.TEChestOs = TEChestOs;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEChestOs;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation("gwppcore", "textures/gui/ChestOs.png");
	}
	protected int getXSize(){
		return 392;
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