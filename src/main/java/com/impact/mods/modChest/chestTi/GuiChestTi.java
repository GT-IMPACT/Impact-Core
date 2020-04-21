package com.impact.mods.modChest.chestTi;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiChestTi extends Gui_BaseChest
{
	public TEChestTi TEChestTi;

	public GuiChestTi(TEChestTi TEChestTi, InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestTi(TEChestTi, inventoryPlayer));
		this.TEChestTi = TEChestTi;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity()
	{
		return TEChestTi;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestTi.png");
	}
	public int getXSize(){
		return 247;
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