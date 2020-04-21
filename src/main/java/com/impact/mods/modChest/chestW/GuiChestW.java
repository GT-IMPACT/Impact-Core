package com.impact.mods.modChest.chestW;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiChestW extends Gui_BaseChest {
	private TEChestW TEChestW;

	public GuiChestW( TEChestW TEChestW, InventoryPlayer inventoryPlayer) {
		super(new ContainerChestW(TEChestW, inventoryPlayer));
		this.TEChestW = TEChestW;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity() {
		return TEChestW;
	}
	public ResourceLocation getPathTexture() {
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestW.png");
	}
	public int getXSize(){
		return 283;
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