package com.impact.mods.modChest.chestOs;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiChestOs extends Gui_BaseChest {
	private TEChestOs TEChestOs;

	public GuiChestOs(TEChestOs TEChestOs, InventoryPlayer inventoryPlayer) {
		super(new ContainerChestOs(TEChestOs, inventoryPlayer));
		this.TEChestOs = TEChestOs;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity()
	{
		return TEChestOs;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestOs.png");
	}
	public int getXSize(){
		return 392;
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