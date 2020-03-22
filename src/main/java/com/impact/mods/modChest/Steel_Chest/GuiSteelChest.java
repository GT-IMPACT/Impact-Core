package com.impact.mods.modChest.Steel_Chest;

import com.impact.System.Refstrings;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class GuiSteelChest extends Gui_BaseChest
{
	private final TESteelChest TESteelChest;

	public GuiSteelChest(@Nonnull final TESteelChest TESteelChest, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerSteelChest(TESteelChest, inventoryPlayer));
		this.TESteelChest = TESteelChest;
	}

	@Nonnull
	@Override
	protected TE_BaseChest getTileEntity()
	{
		return TESteelChest;
	}
	protected ResourceLocation getPathTexture(){
		return new ResourceLocation(Refstrings.MODID, "textures/gui/SteelChest.png");
	}
	protected int getXSize(){
		return 175;
	}
	protected int getYSize(){
		return 239;
	}
	protected int getScale(){
		return 500;
	}
	protected int getNamedPos(){
		return 8;
	}

}