package com.gwppcore.gthandler;


import com.gwppcore.gthandler.tileentities.multi.*;

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

		CustomItemList.Machine_Bender.set(new GT_TileEntity_Bender(14049, "multimachine.bender", "Multi Bending Machine").getStackForm(1L));
		CustomItemList.Machine_Extruder.set(new GT_TileEntity_Extruder(14050, "multimachine.extruder", "Multi Extruding Machine").getStackForm(1L));
		CustomItemList.Machine_LaserEngraver.set(new GT_TileEntity_LaserEngraver(14051, "multimachine.laserengraver", "Multi Laser Engraver").getStackForm(1L));
		CustomItemList.Machine_Presser.set(new GT_TileEntity_Presser(14052, "multimachine.presser", "Multi Pressing Machine").getStackForm(1L));
		CustomItemList.Machine_Assembler.set(new GT_TileEntity_Assembler(14053, "multimachine.assembler", "Multi Assembling Machine").getStackForm(1L));
		CustomItemList.Machine_CircuitAssembler.set(new GT_TileEntity_CircuitAssembler(14054, "multimachine.circuitassembler", "Multi Circuit Assembler").getStackForm(1L));
	}
}
