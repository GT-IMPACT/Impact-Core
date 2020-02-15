package com.gwppcore.gthandler;


import com.gwppcore.gthandler.tileentities.multi.*;
import com.gwppcore.gthandler.tileentities.storage.GT_MetaTileEntity_PowerStation;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.util.EnumChatFormatting;

public class GT_Loader_MachinesMulti {
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

			CustomItemList.Machine_Bender.set(new GT_TileEntity_Bender(						ID++, "multimachine.bender", "Multi Bending Machine").getStackForm(1L));
			CustomItemList.Machine_Extruder.set(new GT_TileEntity_Extruder(					ID++, "multimachine.extruder", "Multi Extruding Machine").getStackForm(1L));
			CustomItemList.Machine_LaserEngraver.set(new GT_TileEntity_LaserEngraver(		ID++, "multimachine.laserengraver", "Multi Laser Engraver").getStackForm(1L));
			CustomItemList.Machine_Presser.set(new GT_TileEntity_Presser(					ID++, "multimachine.presser", "Multi Pressing Machine").getStackForm(1L));
			CustomItemList.Machine_Assembler.set(new GT_TileEntity_Assembler(				ID++, "multimachine.assembler", "Multi Assembling Machine").getStackForm(1L));
			CustomItemList.Machine_CircuitAssembler.set(new GT_TileEntity_CircuitAssembler( ID++, "multimachine.circuitassembler", "Multi Circuit Assembler").getStackForm(1L));
			CustomItemList.Machine_WireAssembler.set(new GT_TileEntity_WireAssembler(		ID++, "multimachine.wireassembler", "Multi Wire Assembler").getStackForm(1L));
			CustomItemList.Machine_WireMill.set(new GT_TileEntity_Wiremill(					ID++, "multimachine.wiremill", "Multi Wiremill").getStackForm(1L));
			CustomItemList.ArcFurnace.set(new GT_TileEntity_ArcFurnace(						ID++, "multimachine.arcfurnace", "Multi Arc Furnace").getStackForm(1L));
			CustomItemList.PlasmaArcFurnace.set(new GT_TileEntity_PlasmaArcFurnace( 		ID++, "multimachine.plasmaarcfurnace", "Multi Plasma Arc Furnace" + EnumChatFormatting.RED + " [WIP | NOT WORKING]").getStackForm(1L));
			CustomItemList.Electrolyzer.set(new GT_TileEntity_Electrolyzer(					ID++, "multimachine.electrolyzer", "Multi Electrolyzer").getStackForm(1L));
			CustomItemList.PowerStation.set(new GT_MetaTileEntity_PowerStation(				ID++, "multimachine.powerstation", "Power Station").getStackForm(1L));

		/** CRAFTING MANAGER */

			//ЗДЕСЬ ПИШИ КРАФТ

	}
}
