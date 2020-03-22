package com.impact.mods.modChest.chestAL;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiChestAl extends Gui_BaseChest
{
	private final TEChestAl TEChestAl;

	public GuiChestAl(@Nonnull final TEChestAl TEChestAl, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestAl(TEChestAl, inventoryPlayer));
		this.TEChestAl = TEChestAl;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEChestAl;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestAl.png");
	}
	protected int getXSize(){
		return 175;
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