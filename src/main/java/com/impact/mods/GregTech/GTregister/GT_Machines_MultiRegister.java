package com.impact.mods.GregTech.GTregister;


import com.impact.mods.GregTech.tileentities.multi.*;
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

			GT_ItemList.Machine_PBE				.set(new GTMTE_PressBendExtrud(ID++, "multimachine.pbe", 				"Multi PBE Machine").getStackForm(1L));
			GT_ItemList.Machine_LaserEngraver	.set(new GTMTE_LaserEng(ID++, "multimachine.laserengraver", 			"Multi Laser Engraver").getStackForm(1L));
			GT_ItemList.Machine_Assembler		.set(new GTMTE_Assembler(ID++, "multimachine.assembler", 				"Multi Assembling Machine").getStackForm(1L));
			GT_ItemList.Machine_Centrifuge		.set(new GTMTE_Centrifuge(ID++, "multimachine.centrifuge", 				"Multi Centrifuge").getStackForm(1L));
			GT_ItemList.Machine_Electrolyzer	.set(new GTMTE_Electrolyzer(ID++, "multimachine.electrolyzer",  		"Multi Electrolyzer").getStackForm(1L));
			GT_ItemList.Machine_Wire			.set(new GTMTE_Wire(ID++, "multimachine.wire",  						"Multi Wire Factory").getStackForm(1L));
			GT_ItemList.Machine_Supply			.set(new GTMTE_Supply(ID++, "multimachine.supply",  					"Multi Supply Production").getStackForm(1L));
			GT_ItemList.Machine_Utility			.set(new GTMTE_Utility(ID++, "multimachine.utility",  					"Multi Utility Machine").getStackForm(1L));
			GT_ItemList.Machine_Brewmenter		.set(new GTMTE_Brewmenter(ID++, "multimachine.brewmenter",  			"Multi Brewmenter").getStackForm(1L));
			GT_ItemList.Machine_ArcFurnace		.set(new GTMTE_ArcFurnace(ID++, "multimachine.arcfurnace",  			"Multi Arc Furnace").getStackForm(1L));
			GT_ItemList.Machine_Cutting			.set(new GTMTE_Cutting(ID++, "multimachine.cutting",  					"Multi Cutting Machine").getStackForm(1L));
			GT_ItemList.Machine_Extradifier		.set(new GTMTE_Extradifier(ID++, "multimachine.extradifier",  			"Multi Extradification").getStackForm(1L));
			GT_ItemList.Machine_Macerator		.set(new GTMTE_Macerator(ID++, "multimachine.macerator",  				"Multi Maceration Stack").getStackForm(1L));
			GT_ItemList.Machine_Mixer			.set(new GTMTE_Mixer(ID++, "multimachine.mixer",  						"Multi Mixing Machine").getStackForm(1L));
			GT_ItemList.Machine_Siftarator		.set(new GTMTE_Siftarator(ID++, "multimachine.siftarator",  			"Multi Siftaration Unit").getStackForm(1L));
			GT_ItemList.Machine_DDDPrinter		.set(new GTMTE_3DPrinter(ID++, "multimachine.dddprinter",  				"3D Printer").getStackForm(1L));
			GT_ItemList.Machine_FreezSolidifier	.set(new GTMTE_FreezerSolidifier(ID++, "multimachine.freezsolidifier", 	"Freezer Solidifier").getStackForm(1L));
			GT_ItemList.Machine_BlastSmelter	.set(new GTMTE_BlastSmelter(ID++, "multimachine.blastsmelter", 			"Blast Smelter").getStackForm(1L));

			GT_ItemList.Machine_CokeOven.set(new GTMTE_CokeOven(13002, "multimachine.cokeoven", "Coke Oven").getStackForm(1L));
			GT_ItemList.Machine_Multi_Farm.set(new GTMTE_Farm(13001, "multimachine.farm", "Multiblock Farm").getStackForm(1L));

			//GT_ItemList.PowerStation			.set(new GTMTE_PowerStation(ID++, "multimachine.powerstation",  		"Power Station").getStackForm(1L));
			//GT_ItemList.PowerReactor			.set(new GTMTE_REACTOR(ID++, "multimachine.powerreactor",  				"Power Reactor").getStackForm(1L));
			//GT_ItemList.PowerTurbine			.set(new GTMTE_NuclearTurbine(ID++, "multimachine.powerturb", 			"Power Turbine").getStackForm(1L));

	}
}
