package com.impact.GregTech.GTregister;


import com.impact.GregTech.tileentities.multi.*;
import com.impact.GregTech.tileentities.multi.NuclearReactor.GT_TileEntity_NuclearTurbine;
import com.impact.GregTech.tileentities.multi.NuclearReactor.GT_TileEntity_REACTOR;
import com.impact.GregTech.tileentities.storage.GT_MetaTileEntity_PowerStation;
import gregtech.api.util.GT_ModHandler;

public class GT_Machines_MultiRegister {
	public void run()
	{
		registerMachines();
	}

	private void registerMachines()
	{
		int ID = 14020;
		long bitsd = GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE
				| GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED;

		/** MULTIBLOCKS */

			GT_ItemList.Machine_PBE.set(new GT_TileEntity_PressBendExtrud(			ID++, "multimachine.pbe", 			"Multi PBE Machine").getStackForm(1L));
			GT_ItemList.Machine_LaserEngraver.set(new GT_TileEntity_LaserEng(		ID++, "multimachine.laserengraver", "Multi Laser Engraver").getStackForm(1L));
			GT_ItemList.Machine_Assembler.set(new GT_TileEntity_Assembler(			ID++, "multimachine.assembler", 	"Multi Assembling Machine").getStackForm(1L));
			GT_ItemList.Machine_Centrifuge.set(new GT_TileEntity_Centrifuge(		ID++, "multimachine.centrifuge", 	"Multi Centrifuge").getStackForm(1L));
			GT_ItemList.Machine_Electrolyzer.set(new GT_TileEntity_Electrolyzer(	ID++, "multimachine.electrolyzer",  "Multi Electrolyzer").getStackForm(1L));
			GT_ItemList.Machine_Wire.set(new GT_TileEntity_Wire(					ID++, "multimachine.wire",  		"Multi Wire Factory").getStackForm(1L));
			GT_ItemList.Machine_Supply.set(new GT_TileEntity_Supply(				ID++, "multimachine.supply",  		"Multi Supply Production").getStackForm(1L));
			GT_ItemList.Machine_Utility.set(new GT_TileEntity_Utility(				ID++, "multimachine.utility",  		"Multi Utility Machine").getStackForm(1L));
			GT_ItemList.Machine_Brewmenter.set(new GT_TileEntity_Brewmenter(		ID++, "multimachine.brewmenter",  	"Multi Brewmenter").getStackForm(1L));



			GT_ItemList.PowerStation.set(new GT_MetaTileEntity_PowerStation(		ID++, "multimachine.powerstation",  "Power Station").getStackForm(1L));
			GT_ItemList.PowerReactor.set(new GT_TileEntity_REACTOR(					ID++, "multimachine.powerreactor",  "Power Reactor").getStackForm(1L));
			GT_ItemList.PowerTurbine.set(new GT_TileEntity_NuclearTurbine(			ID++, "multimachine.powerturb", 	"Power Turbine").getStackForm(1L));



			//GT_ItemList.Machine_FreezerSolidifier.set(new GT_MetaTileEntity_FreezerSolidifier(ID++, "multimachine.freezersolidifier", "Freezer Solidifier").getStackForm(1L));
		/** CRAFTING MANAGER */

			//ЗДЕСЬ ПИШИ КРАФТ

	}
}
