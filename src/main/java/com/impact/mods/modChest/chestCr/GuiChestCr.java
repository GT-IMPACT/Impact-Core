package com.impact.mods.modChest.chestCr;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiChestCr extends Gui_BaseChest {
	private TEChestCr TEChestCr;

	public GuiChestCr(TEChestCr TEChestCr, InventoryPlayer inventoryPlayer) {
		super(new ContainerChestCr(TEChestCr, inventoryPlayer));
		this.TEChestCr = TEChestCr;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity()
	{
		return TEChestCr;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestCr.png");
	}
	public int getXSize(){
		return 320;
	}
	public int getYSize(){
		return 275;
	}
	public int getScale(){
		return 500;
	}
	public int getNamedPos(){
		return 8;
	}
}