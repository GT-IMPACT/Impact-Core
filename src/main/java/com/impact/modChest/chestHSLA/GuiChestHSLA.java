package com.impact.modChest.chestHSLA;

import com.impact.lib.Refstrings;
import com.impact.modChest.BASE.Gui_BaseChest;
import com.impact.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiChestHSLA extends Gui_BaseChest
{
	private final TEChestHSLA TEChestHSLA;

	public GuiChestHSLA(@Nonnull final TEChestHSLA TEChestHSLA, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestHSLA(TEChestHSLA, inventoryPlayer));
		this.TEChestHSLA = TEChestHSLA;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEChestHSLA;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestHSLA.png");
	}
	protected int getXSize(){
		return 211;
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