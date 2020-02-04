package com.gwppcore.gthandler;


import com.gwppcore.gthandler.tileentities.multi.GT_TileEntity_MegaVacuumFreezer;
import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.tileentities.generators.GT_MetaTileEntity_DieselGenerator;
import gregtech.common.tileentities.generators.GT_MetaTileEntity_GasTurbine;
import gregtech.common.tileentities.generators.GT_MetaTileEntity_SteamTurbine;
import net.minecraft.item.ItemStack;

public class GT_Loader_Machines {
	public void run()
	{
		registerMachines();
	}

	private void registerMachines()
	{
		long bitsd = GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE
				| GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED;

		CustomItemList.Machine_FREEZTEST.set(new GT_TileEntity_MegaVacuumFreezer(14010, "multimachine.freeztest", "Machine_FREEZTEST").getStackForm(1L));
	}

}
