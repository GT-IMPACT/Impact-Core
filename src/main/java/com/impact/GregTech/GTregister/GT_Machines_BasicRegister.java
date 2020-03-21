package com.impact.GregTech.GTregister;


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

			GT_ItemList.Portable_Tank_ULV.set(new GT_MetaTileEntity_PortableTank(ID++, "fluidtank.tier.00", "ULV Portable Tank", 0).getStackForm(1L));
			GT_ItemList.Portable_Tank_LV.set(new GT_MetaTileEntity_PortableTank(ID++,  "fluidtank.tier.01", "LV Portable Tank", 1).getStackForm(1L));
			GT_ItemList.Portable_Tank_MV.set(new GT_MetaTileEntity_PortableTank(ID++,  "fluidtank.tier.02", "MV Portable Tank", 2).getStackForm(1L));
			GT_ItemList.Portable_Tank_HV.set(new GT_MetaTileEntity_PortableTank(ID++,  "fluidtank.tier.03", "HV Portable Tank", 3).getStackForm(1L));
			GT_ItemList.Portable_Tank_EV.set(new GT_MetaTileEntity_PortableTank(ID++,  "fluidtank.tier.04", "EV Portable Tank", 4).getStackForm(1L));
			GT_ItemList.Portable_Tank_IV.set(new GT_MetaTileEntity_PortableTank(ID++,  "fluidtank.tier.05", "IV Portable Tank", 5).getStackForm(1L));
			GT_ItemList.Portable_Tank_LuV.set(new GT_MetaTileEntity_PortableTank(ID++, "fluidtank.tier.06", "LuV Portable Tank", 6).getStackForm(1L));
			GT_ItemList.Portable_Tank_ZPM.set(new GT_MetaTileEntity_PortableTank(ID++, "fluidtank.tier.07", "ZPM Portable Tank", 7).getStackForm(1L));
			GT_ItemList.Portable_Tank_UV.set(new GT_MetaTileEntity_PortableTank(ID++,  "fluidtank.tier.08", "UV Portable Tank", 8).getStackForm(1L));
			GT_ItemList.Dynamo_4A_IV.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo4A.tier.05", "IV Dynamo Hatch 4A", 5, 4).getStackForm(1L));
			GT_ItemList.Dynamo_4A_LuV.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo4A.tier.06", "LuV Dynamo Hatch 4A", 6, 4).getStackForm(1L));
			GT_ItemList.Dynamo_4A_ZPM.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo4A.tier.07", "ZPM Dynamo Hatch 4A", 7, 4).getStackForm(1L));
			GT_ItemList.Energy_Capacity_EV.set(new GT_MetaTileEntity_Hatch_Energy(ID++,  "energycapacity.tier.06", "EV Energy Hatch Battery", 4, 512000L).getStackForm(1L));
			GT_ItemList.Dynamo_Capacity_EV.set(new GT_MetaTileEntity_Hatch_Dynamo(ID++,  "dynamo2A.tier.04", "EV Dynamo Hatch Battery", 4, 512000L).getStackForm(1L));

		/** CRAFTING MANAGER */

			//ЗДЕСЬ ПИШИ КРАФТ

	}
}
