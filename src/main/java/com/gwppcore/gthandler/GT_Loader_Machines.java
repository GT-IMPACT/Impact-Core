package com.gwppcore.gthandler;


import com.gwppcore.gthandler.tileentities.multi.GT_TileEntity_ParallelBender;
import com.gwppcore.gthandler.tileentities.multi.GT_TileEntity_ParallelVacuumFreezer;
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

		CustomItemList.Machine_FREEZTEST.set(new GT_TileEntity_ParallelVacuumFreezer(14010, "multimachine.freeztest", "Machine_FREEZTEST").getStackForm(1L));
		CustomItemList.Machine_BENDERTEST.set(new GT_TileEntity_ParallelBender(14011, "multimachine.bendertest", "Machine_BENDERTEST").getStackForm(1L));

	}

}
