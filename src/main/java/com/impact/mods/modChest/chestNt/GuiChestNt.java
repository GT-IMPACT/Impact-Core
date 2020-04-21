package com.impact.mods.modChest.chestNt;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiChestNt extends Gui_BaseChest {
	private TEChestNt TEChestNt;

	public GuiChestNt(TEChestNt TEChestNt, InventoryPlayer inventoryPlayer) {
		super(new ContainerChestNt(TEChestNt, inventoryPlayer));
		this.TEChestNt = TEChestNt;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity()
	{
		return TEChestNt;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestNt.png");
	}
	public int getXSize(){
		return 428;
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