package com.impact.mods.modChest.WroughtIron_Chest;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;


import javax.annotation.Nonnull;

public class GuiWroughtIronChest extends Gui_BaseChest
{
	private  TEWroughtIronChest TEWroughtIronChest;

	public GuiWroughtIronChest(TEWroughtIronChest TEWroughtIronChest,  InventoryPlayer inventoryPlayer)
	{
		super(new ContainerWroughtIronChest(TEWroughtIronChest, inventoryPlayer));
		this.TEWroughtIronChest = TEWroughtIronChest;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity()
	{
		return TEWroughtIronChest;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/WroughtIronChest.png");
	}
	public int getXSize(){
		return 175;
	}
	public int getYSize(){
		return 203;
	}
	public int getScale(){
		return 500;
	}
	public int getNamedPos(){
		return 8;
	}

}