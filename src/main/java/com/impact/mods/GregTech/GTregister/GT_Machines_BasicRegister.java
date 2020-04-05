package com.impact.mods.GregTech.GTregister;


import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.tileentities.storage.GT_MetaTileEntity_PortableTank;

public class GT_Machines_BasicRegister {
	public void run()
	{
		registerMachines();
	}

	private void registerMachines()
	{
		int ID = 14500;
		long bitsd = GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE
				| GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED;

		/** BASIC */

			GT_ItemList.Portable_Tank_ULV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.00", "ULV Portable Tank",     0).getStackForm(1L));
			GT_ItemList.Portable_Tank_LV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.01", "LV Portable Tank",      1).getStackForm(1L));
			GT_ItemList.Portable_Tank_MV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.02", "MV Portable Tank",      2).getStackForm(1L));
			GT_ItemList.Portable_Tank_HV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.03", "HV Portable Tank",      3).getStackForm(1L));
			GT_ItemList.Portable_Tank_EV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.04", "EV Portable Tank",      4).getStackForm(1L));
			GT_ItemList.Portable_Tank_IV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.05", "IV Portable Tank",      5).getStackForm(1L));
			GT_ItemList.Portable_Tank_LuV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.06", "LuV Portable Tank",     6).getStackForm(1L));
			GT_ItemList.Portable_Tank_ZPM	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.07", "ZPM Portable Tank",     7).getStackForm(1L));
			GT_ItemList.Portable_Tank_UV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.08", "UV Portable Tank",      8).getStackForm(1L));

			GT_ItemList.Energy_4A_IV		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy4A.tier.05",  "IV Energy Hatch 4A",    5, 4).getStackForm(1L));
			GT_ItemList.Energy_4A_LuV		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy4A.tier.06",  "LuV Energy Hatch 4A",   6, 4).getStackForm(1L));
			GT_ItemList.Energy_4A_ZPM		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy4A.tier.07",  "ZPM Energy Hatch 4A",   7, 4).getStackForm(1L));
			GT_ItemList.Energy_4A_UV		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy4A.tier.08",  "UV Energy Hatch 4A",    8, 4).getStackForm(1L));
			GT_ItemList.Energy_16A_LuV		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy16A.tier.06", "LuV Energy Hatch 16A",  6, 16).getStackForm(1L));
			GT_ItemList.Energy_16A_ZPM		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy16A.tier.07", "ZPM Energy Hatch 16A",  7, 16).getStackForm(1L));
			GT_ItemList.Energy_16A_UV		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy16A.tier.08", "UV Energy Hatch 16A",   8, 16).getStackForm(1L));
			GT_ItemList.Energy_64A_ZPM		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy64A.tier.07", "ZPM Energy Hatch 64A",  7, 64).getStackForm(1L));
			GT_ItemList.Energy_64A_UV		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy64A.tier.08", "UV Energy Hatch 64A",   8, 64).getStackForm(1L));
			GT_ItemList.Energy_256A_UV		.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energy256A.tier.08","UV Energy Hatch 256A",  8, 256).getStackForm(1L));

			GT_ItemList.Dynamo_2A_EV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo2A.tier.04",  "IV Dynamo Hatch 2A",    4, 2).getStackForm(1L));
			GT_ItemList.Dynamo_2A_IV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo2A.tier.05",  "IV Dynamo Hatch 2A",    5, 2).getStackForm(1L));
			GT_ItemList.Dynamo_2A_LuV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo2A.tier.06",  "LuV Dynamo Hatch 2A",   6, 2).getStackForm(1L));
			GT_ItemList.Dynamo_2A_ZPM		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo2A.tier.07",  "ZPM Dynamo Hatch 2A",   7, 2).getStackForm(1L));
			GT_ItemList.Dynamo_2A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo2A.tier.08",  "UV Dynamo Hatch 2A",    8, 2).getStackForm(1L));
			GT_ItemList.Dynamo_4A_IV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo4A.tier.05",  "IV Dynamo Hatch 4A",    5, 4).getStackForm(1L));
			GT_ItemList.Dynamo_4A_LuV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo4A.tier.06",  "LuV Dynamo Hatch 4A",   6, 4).getStackForm(1L));
			GT_ItemList.Dynamo_4A_ZPM		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo4A.tier.07",  "ZPM Dynamo Hatch 4A",   7, 4).getStackForm(1L));
			GT_ItemList.Dynamo_4A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo4A.tier.08",  "UV Dynamo Hatch 4A",    8, 4).getStackForm(1L));
			GT_ItemList.Dynamo_16A_LuV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo16A.tier.06", "LuV Dynamo Hatch 16A",  6, 16).getStackForm(1L));
			GT_ItemList.Dynamo_16A_ZPM		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo16A.tier.07", "ZPM Dynamo Hatch 16A",  7, 16).getStackForm(1L));
			GT_ItemList.Dynamo_16A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo16A.tier.08", "UV Dynamo Hatch 16A",   8, 16).getStackForm(1L));
			GT_ItemList.Dynamo_64A_ZPM		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo64A.tier.07", "ZPM Dynamo Hatch 64A",  7, 64).getStackForm(1L));
			GT_ItemList.Dynamo_64A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo64A.tier.08", "UV Dynamo Hatch 64A",   8, 64).getStackForm(1L));
			GT_ItemList.Dynamo_256A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo256A.tier.08","UV Dynamo Hatch 256A",  8, 256).getStackForm(1L));

	}
}
