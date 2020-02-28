package com.gwppcore.GregTech.GTregister;


import com.gwppcore.GregTech.tileentities.multi.*;
import com.gwppcore.GregTech.tileentities.multi.NuclearReactor.GT_TileEntity_NuclearTurbine;
import com.gwppcore.GregTech.tileentities.multi.NuclearReactor.GT_TileEntity_REACTOR;
import com.gwppcore.GregTech.tileentities.storage.GT_MetaTileEntity_PowerStation;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.util.EnumChatFormatting;

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

			GT_ItemList.Machine_Bender.set(new GT_TileEntity_Bender(						ID++, "multimachine.bender", "Multi Bending Machine").getStackForm(1L));
			GT_ItemList.Machine_LaserEngraver.set(new GT_TileEntity_LaserEngraver(		ID++, "multimachine.laserengraver", "Multi Laser Engraver").getStackForm(1L));
			GT_ItemList.Machine_Assembler.set(new GT_TileEntity_Assembler(				ID++, "multimachine.assembler", "Multi Assembling Machine").getStackForm(1L));
			GT_ItemList.Machine_CircuitAssembler.set(new GT_TileEntity_CircuitAssembler( ID++, "multimachine.circuitassembler", "Multi Circuit Assembler").getStackForm(1L));
			GT_ItemList.Machine_WireAssembler.set(new GT_TileEntity_WireAssembler(		ID++, "multimachine.wireassembler", "Multi Wire Assembler").getStackForm(1L));
			GT_ItemList.Machine_WireMill.set(new GT_TileEntity_Wiremill(					ID++, "multimachine.wiremill", "Multi Wiremill").getStackForm(1L));
			GT_ItemList.ArcFurnace.set(new GT_TileEntity_ArcFurnace(						ID++, "multimachine.arcfurnace", "Multi Arc Furnace").getStackForm(1L));
			GT_ItemList.PlasmaArcFurnace.set(new GT_TileEntity_PlasmaArcFurnace( 		ID++, "multimachine.plasmaarcfurnace", "Multi Plasma Arc Furnace" + EnumChatFormatting.RED + " [WIP | NOT WORKING]").getStackForm(1L));
			GT_ItemList.Electrolyzer.set(new GT_TileEntity_Electrolyzer(					ID++, "multimachine.electrolyzer", "Multi Electrolyzer").getStackForm(1L));
			GT_ItemList.PowerStation.set(new GT_MetaTileEntity_PowerStation(				ID++, "multimachine.powerstation", "Power Station").getStackForm(1L));
			GT_ItemList.PowerReactor.set(new GT_TileEntity_REACTOR(						ID++, "multimachine.powerreactor", "Power Reactor").getStackForm(1L));
			GT_ItemList.PowerTurbine.set(new GT_TileEntity_NuclearTurbine(				ID++, "multimachine.powerturb", "Power Turbine").getStackForm(1L));
			GT_ItemList.Machine_Mixer.set(new GT_TileEntity_Mixer(						ID++, "multimachine.mixer", "Multi Mixer").getStackForm(1L));
			GT_ItemList.Machine_Centrifuge.set(new GT_TileEntity_Centrifuge(				ID++, "multimachine.centrifuge", "Multi Centrifuge").getStackForm(1L));
			GT_ItemList.Naquadah_multi.set(new GT_TileEntity_NaquadahGenerator(			ID++, "multimachine.nqgen", "Nq Gen").getStackForm(1L));
			GT_ItemList.Machine_FreezerSolidifier.set(new GT_MetaTileEntity_FreezerSolidifier(ID++, "multimachine.freezersolidifier", "Freezer Solidifier").getStackForm(1L));
		/** CRAFTING MANAGER */

			//ЗДЕСЬ ПИШИ КРАФТ

	}
}
