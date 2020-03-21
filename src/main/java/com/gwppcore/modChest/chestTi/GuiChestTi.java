package com.gwppcore.modChest.chestTi;

import com.gwppcore.modChest.BASE.Gui_BaseChest;
import com.gwppcore.modChest.BASE.TE_BaseChest;
import com.gwppcore.modChest.chestHSLA.ContainerChestHSLA;
import com.gwppcore.modChest.chestHSLA.TEChestHSLA;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiChestTi extends Gui_BaseChest
{
	private final TEChestTi TEChestTi;

	public GuiChestTi(@Nonnull final TEChestTi TEChestTi, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestTi(TEChestTi, inventoryPlayer));
		this.TEChestTi = TEChestTi;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEChestTi;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation("gwppcore", "textures/gui/ChestTi.png");
	}
	protected int getXSize(){
		return 247;
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