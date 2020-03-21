package com.impact.modChest.WroughtIron_Chest;

import com.impact.modChest.BASE.Gui_BaseChest;
import com.impact.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;


import javax.annotation.Nonnull;

public final class GuiWroughtIronChest extends Gui_BaseChest
{
	private final TEWroughtIronChest TEWroughtIronChest;

	public GuiWroughtIronChest(@Nonnull final TEWroughtIronChest TEWroughtIronChest, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerWroughtIronChest(TEWroughtIronChest, inventoryPlayer));
		this.TEWroughtIronChest = TEWroughtIronChest;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TEWroughtIronChest;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation("gwppcore", "textures/gui/WroughtIronChest.png");
	}
	protected int getXSize(){
		return 175;
	}
	protected int getYSize(){
		return 203;
	}
	protected int getScale(){
		return 500;
	}
	protected int getNamedPos(){
		return 8;
	}

}