package com.gwppcore.modChest.chestAL;

import com.gwppcore.modChest.BASE.Gui_BaseChest;
import com.gwppcore.modChest.BASE.TE_BaseChest;
import com.gwppcore.modChest.Steel_Chest.ContainerSteelChest;
import com.gwppcore.modChest.Steel_Chest.TESteelChest;
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
		return new ResourceLocation("gwppcore", "textures/gui/ChestAl.png");
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