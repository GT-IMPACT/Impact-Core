package com.impact.mods.modChest.Steel_Chest;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiSteelChest extends Gui_BaseChest
{
	public TESteelChest TESteelChest;

	public GuiSteelChest(TESteelChest TESteelChest, InventoryPlayer inventoryPlayer)
	{
		super(new ContainerSteelChest(TESteelChest, inventoryPlayer));
		this.TESteelChest = TESteelChest;
	}

	@Nonnull
	@Override
	public TE_BaseChest getTileEntity()
	{
		return TESteelChest;
	}
	public ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/SteelChest.png");
	}
	public int getXSize(){
		return 175;
	}
	public int getYSize(){
		return 239;
	}
	public int getScale(){
		return 500;
	}
	public int getNamedPos(){
		return 8;
	}

}