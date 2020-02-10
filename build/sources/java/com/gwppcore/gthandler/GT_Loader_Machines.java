package com.gwppcore.gthandler;


import com.gwppcore.gthandler.tileentities.multi.GT_TileEntity_Bender;
import com.gwppcore.gthandler.tileentities.multi.GT_TileEntity_ParallelBilder;
import gregtech.api.util.GT_ModHandler;

public class GT_Loader_Machines {
	public void run()
	{
		registerMachines();
	}

	private void registerMachines()
	{
		long bitsd = GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE
				| GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED;

		CustomItemList.Machine_TESTING_PARALLEL.set(new GT_TileEntity_ParallelBilder(14050, "multimachine.bendertest", "Machine_TESTING_PARALLEL").getStackForm(1L));
		CustomItemList.Machine_TESTING_BENDER.set(new GT_TileEntity_Bender(14049, "multimachine.bender", "Multi Bending Machine").getStackForm(1L));
	}

}
