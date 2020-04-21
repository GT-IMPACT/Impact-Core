package com.impact.mods.modChest.chestIr;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiChestIr extends Gui_BaseChest {
	private TEChestIr TEChestIr;

	public GuiChestIr( TEChestIr TEChestIr, InventoryPlayer inventoryPlayer) {
		super(new ContainerChestIr(TEChestIr, inventoryPlayer));
		this.TEChestIr = TEChestIr;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity() {
		return TEChestIr;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestIr.png");
	}
	public int getXSize(){
		return 356;
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