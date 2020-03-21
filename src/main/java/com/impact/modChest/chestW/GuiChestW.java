package com.impact.modChest.chestW;

import com.impact.lib.Refstrings;
import com.impact.modChest.BASE.Gui_BaseChest;
import com.impact.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiChestW extends Gui_BaseChest
{
	private final TEChestW TEChestW;

	public GuiChestW(@Nonnull final TEChestW TEChestW, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerChestW(TEChestW, inventoryPlayer));
		this.TEChestW = TEChestW;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEChestW;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/ChestW.png");
	}
	protected int getXSize(){
		return 283;
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