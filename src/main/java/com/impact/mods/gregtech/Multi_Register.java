package com.impact.mods.gregtech;

import com.impact.mods.gregtech.tileentities.multi.ores.GTMTEOreSamplingMachine;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_AdvancedMiner;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_BasicMiner;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_Mining_Coal;
import com.impact.mods.gregtech.tileentities.multi.generators.green.GTMTE_Wind_Generator;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_HyperNaquadahGenerator;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidEnrichedNqGenerator;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidNqGenerator;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_HugeSteamTurbine;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorI;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorII;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorIII;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_I;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_II;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_III;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MESystemProvider;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPContainment;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPStabilizer;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_ParametricDiffuser;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.*;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.*;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.mods.gregtech.tileentities.multi.units.*;
import gregtech.api.enums.ItemList;

import static codechicken.nei.api.API.hideItem;
import static com.impact.core.impactLog.INFO;
import static com.impact.mods.gregtech.GT_ItemList.*;

public class Multi_Register {
	
	public void run() {
		registerMachines();
	}
	
	private void registerMachines() {
		
		Machine_CokeOven.set(new GTMTE_CokeOven(13002, "Coke Oven").getStackForm(1));
		Machine_Multi_Farm.set(new GTMTE_Farm(13001, "Multiblock Farm").getStackForm(1));
		
		SOFC_Low.set(new GTMTE_SOFC_I(13101, "Solid-Oxide Generator Tier 1", 0).getStackForm(1));
		SOFC_Medium.set(new GTMTE_SOFC_II(13102, "Solid-Oxide Generator Tier 2", 1).getStackForm(1));
		SOFC_Huge.set(new GTMTE_SOFC_III(13103, "Solid-Oxide Generator Tier 3", 2).getStackForm(1));
		Multi_Tank.set(new GTMTE_MultiTank(13104, "Multi-Tank").getStackForm(1));
		Single_Tank.set(new GTMTE_SingleTank(13105, "Single-Tank").getStackForm(1));
		
		int ID = 14020;
		
		//MULTIBLOCKS
		Machine_PBE.set(new GTMTE_PressBendExtrud(ID++, "Multi PBE Machine").get());
		Machine_LaserEngraver.set(new GTMTE_LaserEng(ID++, "Multi Laser Engraver").get());
		Machine_Assembler.set(new GTMTE_Assembler(ID++, "Multi Assembling Machine").get());
		Machine_Centrifuge.set(new GTMTE_Centrifuge(ID++, "Multi Centrifuge").get());
		Machine_Electrolyzer.set(new GTMTE_Electrolyzer(ID++, "Multi Electrolyzer").get());
		Machine_Wire.set(new GTMTE_Wire(ID++, "Multi Wire Factory").get());
		Machine_Supply.set(new GTMTE_Supply(ID++, "Multi Supply Production").get());
		Machine_Utility.set(new GTMTE_Utility(ID++, "Multi Utility Machine").get());
		Machine_Brewmenter.set(new GTMTE_Brewmenter(ID++, "Multi Brewmenter").get());
		Machine_ArcFurnace.set(new GTMTE_ArcFurnace(ID++, "Multi Arc Furnace").get());
		Machine_Cutting.set(new GTMTE_Cutting(ID++, "Multi Cutting Machine").get());
		Machine_Extradifier.set(new GTMTE_Extradifier(ID++, "Multi Extradification").get());
		Machine_Macerator.set(new GTMTE_Macerator(ID++, "Multi Maceration Stack").get());
		Machine_Mixer.set(new GTMTE_Mixer(ID++, "Multi Mixing Machine").get());
		Machine_Siftarator.set(new GTMTE_Siftarator(ID++, "Multi Siftaration Unit").get());
		Machine_DDDPrinter.set(new GTMTE_DDDPrinter(ID++, "Basic 3D Printer").get());
		Machine_FreezSolidifier.set(new GTMTE_FreezerSolidifier(ID++, "Freezer Solidifier").get());
		Machine_BlastSmelter.set(new GTMTE_BlastSmelter(ID++, "Blast Smelter").getStackForm(1));
		WaterDrill.set(new GTMTE_DrillerWater(ID++, "Electric Water Drilling Rig").getStackForm(1));
		BasicWaterPump.set(new GTMTE_BasicWaterPump(ID++, "Primitive Water Pump").get());
		AdvVacuumFreezer.set(new GTMTE_AdvancedVacuumFreezer(ID++, "Advanced Vacuum Freezer").get());
		LapPowerStation.set(new GTMTE_LapPowerStation(ID++, "Lapotronic Supercapacitor").getStackForm(1));
		SawMill.set(new GTMTE_SawMill(ID++, "Saw Mill").get());
		Pyrolyse.set(new GTMTE_Pyrolyse(ID++, "Pyrolyse Oven").get());
		AdvPyrolyse.set(new GTMTE_AdvancedPyrolyse(ID++, "Advanced Pyrolyse Oven").get());
		Naquadah_multi.set(new GTMTE_HyperNaquadahGenerator(ID++, "Hyper Generator").getStackForm(1));
		Naquadah_Liquid_multi.set(new GTMTE_LiquidNqGenerator(ID++, "Liquid Naquadah Generator").get());
		Heavy_Metal_Cyclone.set(new GTMTE_HeavyMetalCyclone(ID++, "Heavy Metal Cyclone").get());
		Naquadah_Liquid_Enriched.set(new GTMTE_LiquidEnrichedNqGenerator(ID++, "Liquid Enriched Naquadah Generator").getStackForm(1));
		
		Moon_Miner.set(new GTMTE_MoonMiner(ID++, "Moon Miner").get());
		hideItem(Moon_Miner.get(1));
		
		Machine_AdvDDDPrinter.set(new GTMTE_AdvDDDPrinter(ID++, "Advanced 3D Printer").get());
		Rail_Assembler.set(new GTMTE_RailAssembler(ID++, "Rail Assembler").get());
		Space_Elevator.set(new GTMTE_SpaceElevator(ID++, "Space Elevator").get());
		Nuclear_Reactor_I.set(new GTMTE_NuclearReactorI(ID++, "Nuclear Reactor I").get());
		Nuclear_Reactor_II.set(new GTMTE_NuclearReactorII(ID++, "Nuclear Reactor II").get());
		Nuclear_Reactor_III.set(new GTMTE_NuclearReactorIII(ID++, "Nuclear Reactor III").get());
		Huge_Steam_Turbine.set(new GTMTE_HugeSteamTurbine(ID++, "Huge Steam Turbine").get());
		
		Parallel_Computer.set(new GTMTE_ParallelComputer(ID++, "Parallel Supercomputer").get());
		Communication_Tower.set(new GTMTE_TowerCommunication(ID++, "Communication Tower").getStackForm(1));
		Space_Satellite.set(new GTMTE_SpaceSatellite(ID++, "Space Satellite").get());
		Machine_EIF.set(new GTMTE_ElectromagneticInductionFurnace(ID++, "Electromagnetic Induction Furnace").get());
		Machine_ChemicalReactor.set(new GTMTE_MultiChemicalReactor(ID++, "Multi Chemical Reactor").get());
		Machine_DistTower.set(new GTMTE_MultiDistillationTower(ID++, "Multi Distillation Tower").get());
		Machine_ElectricImplosion.set(new GTMTE_ElectricImplosionCompressor(ID++, "Electric Implosion Compressor").get());
		
		Aerostate.set(new GTMTE_Aerostat(ID++, "Aerostate Platform").get());
		
		Filler.set(new GTMTE_Filler(ID++, "Filler").get());
		hideItem(Filler.get(1));
		
		Parametric_Diffuser.set(new GTMTE_ParametricDiffuser(ID++, "Parametric Diffuser").get());
		Matrix_Stabilizer.set(new GTMTE_MPStabilizer(ID++, "Matrix Particles Stabilizer").get());
		Matrix_Containment.set(new GTMTE_MPContainment(ID++, "Matrix Particles Containment").get());
		ME_System_Provider.set(new GTMTE_MESystemProvider(ID++, "ME System Provider").get());
		
		Wind_Generator.set(new GTMTE_Wind_Generator(ID++, /*"Wind Generator", */"[WIP]").get());
		hideItem(Wind_Generator.get(1));
		
		CoalMiner.set(new GTMTE_Mining_Coal(ID++, "Primitive Miner").get());
		BasicMiner.set(new GTMTE_BasicMiner(ID++, "Basic Electric Miner").get());
		AdvancedMiner.set(new GTMTE_AdvancedMiner(ID++, "Advanced Electric Miner").get());
		The_Mill.set(new GTMTE_TheMill(ID++, "The Mill").get());
		AdvCrackUnit.set(new GTMTE_AdvancedCrackingUnit(ID++, "Advanced Cracking Unit").get());
		OreSamplingMachine.set(new GTMTEOreSamplingMachine(ID++, "Ore Sampling Machine").get());
		GTMTEOreSamplingMachine.Companion.addRecipe();
		
		INFO("[IMPACT-CORE] FINISH ADDED MULTIS | LAST ID: " + ID);
		
		ItemList.Machine_FlotationUnit.set(new GTMTE_FlotationUnit(12192, "Flotation Unit").get());
		ItemList.Machine_MultiblockTesseract.set(new GTMTE_Tesseract(12195, "Tesseract").get());
		ItemList.Machine_MultiblockTinyWormHole.set(new GTMTE_TinyWormHole(12197, "Tiny Wormhole").get());
		ItemList.Antimatter_Reactor.set(new GTMTE_AntimatterReactor(12071, "Antimatter Reactor").get());
	}
}