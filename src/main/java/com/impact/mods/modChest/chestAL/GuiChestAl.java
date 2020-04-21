package com.impact.mods.modChest.chestAL;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiChestAl extends Gui_BaseChest {
	private TEChestAl TEChestAl;

	public GuiChestAl(TEChestAl TEChestAl, InventoryPlayer inventoryPlayer) {
		super(new ContainerChestAl(TEChestAl, inventoryPlayer));
		this.TEChestAl = TEChestAl;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity() {
		return TEChestAl;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestAl.png");
	}
	public int getXSize(){
		return 175;
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