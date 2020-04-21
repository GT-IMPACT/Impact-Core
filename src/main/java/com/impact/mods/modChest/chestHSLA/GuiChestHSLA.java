package com.impact.mods.modChest.chestHSLA;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiChestHSLA extends Gui_BaseChest {
	private TEChestHSLA TEChestHSLA;

	public GuiChestHSLA(TEChestHSLA TEChestHSLA, InventoryPlayer inventoryPlayer) {
		super(new ContainerChestHSLA(TEChestHSLA, inventoryPlayer));
		this.TEChestHSLA = TEChestHSLA;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity()
	{
		return TEChestHSLA;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestHSLA.png");
	}
	public int getXSize(){
		return 211;
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