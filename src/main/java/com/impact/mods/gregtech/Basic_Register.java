package com.impact.mods.gregtech;

import com.impact.mods.gregtech.tileentities.basic.*;
import com.impact.mods.gregtech.tileentities.basic.ae.craftsup.GTMTE_AELevelEmitter;
import com.impact.mods.gregtech.tileentities.hatches.*;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Reflector;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_AE_Connector;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_Hatch_MESystemMPChamber;
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_EnrichmentUnit;
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_OreHatch;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.*;
import com.impact.mods.gregtech.tileentities.multi.storage.hatch.GTMTE_TankHatch;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.GT_Recipe;
import gregtech.common.tileentities.generators.GT_MetaTileEntity_DieselGenerator;
import gregtech.common.tileentities.generators.GT_MetaTileEntity_GasTurbine;
import gregtech.common.tileentities.generators.GT_MetaTileEntity_SteamTurbine;
import gregtech.common.tileentities.storage.GT_MetaTileEntity_PortableTank;

import static com.impact.core.impactLog.INFO;
import static com.impact.mods.gregtech.GT_ItemList.*;
import static gregtech.api.enums.GT_Values.V;

public class Basic_Register {
	
	private final static boolean aBoolConst_0 = false;
	
	public void run() {
		registerMachines();
		GTMTE_ComputerRack.run();
	}
	
	private void registerMachines() {
		int ID = 14500;
		int ID2 = 14534;
		
		/* BASIC */
		Portable_Tank_ULV.set(new GT_MetaTileEntity_PortableTank(ID++, "impact.portabletank.tier.00", "ULV Portable Tank", 0).getStackForm(1L));
		Portable_Tank_LV.set(new GT_MetaTileEntity_PortableTank(ID++, "impact.portabletank.tier.01", "LV Portable Tank", 1).getStackForm(1L));
		Portable_Tank_MV.set(new GT_MetaTileEntity_PortableTank(ID++, "impact.portabletank.tier.02", "MV Portable Tank", 2).getStackForm(1L));
		Portable_Tank_HV.set(new GT_MetaTileEntity_PortableTank(ID++, "impact.portabletank.tier.03", "HV Portable Tank", 3).getStackForm(1L));
		Portable_Tank_EV.set(new GT_MetaTileEntity_PortableTank(ID++, "impact.portabletank.tier.04", "EV Portable Tank", 4).getStackForm(1L));
		Portable_Tank_IV.set(new GT_MetaTileEntity_PortableTank(ID++, "impact.portabletank.tier.05", "IV Portable Tank", 5).getStackForm(1L));
		Portable_Tank_LuV.set(new GT_MetaTileEntity_PortableTank(ID++, "impact.portabletank.tier.06", "LuV Portable Tank", 6).getStackForm(1L));
		Portable_Tank_ZPM.set(new GT_MetaTileEntity_PortableTank(ID++, "impact.portabletank.tier.07", "ZPM Portable Tank", 7).getStackForm(1L));
		Portable_Tank_UV.set(new GT_MetaTileEntity_PortableTank(ID, "impact.portabletank.tier.08", "UV Portable Tank", 8).getStackForm(1L));

		/*
		Energy_4A_IV		.set(new GT_MetaTileEntity_Hatch_Energy(9++,  "energy4A.tier.05",  "IV Energy Hatch 4A",    5, 4).getStackForm(1L));
		Dynamo_256A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(33++,  "dynamo256A.tier.08","UV Dynamo Hatch 256A",  8, 256).getStackForm(1L));
		*/
		
		Diode_2A_ULV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.00", "Diode Cable 2A ULV", 0, 2).getStackForm(1L));
		Diode_2A_LV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.01", "Diode Cable 2A LV", 1, 2).getStackForm(1L));
		Diode_2A_MV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.02", "Diode Cable 2A MV", 2, 2).getStackForm(1L));
		Diode_2A_HV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.03", "Diode Cable 2A HV", 3, 2).getStackForm(1L));
		Diode_2A_EV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.04", "Diode Cable 2A EV", 4, 2).getStackForm(1L));
		Diode_2A_IV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.05", "Diode Cable 2A IV", 5, 2).getStackForm(1L));
		Diode_2A_LuV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.06", "Diode Cable 2A LuV", 6, 2).getStackForm(1L));
		Diode_2A_ZPM.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.07", "Diode Cable 2A ZPM", 7, 2).getStackForm(1L));
		Diode_2A_UV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode2A.tier.08", "Diode Cable 2A UV", 8, 2).getStackForm(1L));
		
		Diode_4A_ULV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.00", "Diode Cable 4A ULV", 0, 4).getStackForm(1L));
		Diode_4A_LV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.01", "Diode Cable 4A LV", 1, 4).getStackForm(1L));
		Diode_4A_MV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.02", "Diode Cable 4A MV", 2, 4).getStackForm(1L));
		Diode_4A_HV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.03", "Diode Cable 4A HV", 3, 4).getStackForm(1L));
		Diode_4A_EV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.04", "Diode Cable 4A EV", 4, 4).getStackForm(1L));
		Diode_4A_IV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.05", "Diode Cable 4A IV", 5, 4).getStackForm(1L));
		Diode_4A_LuV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.06", "Diode Cable 4A LuV", 6, 4).getStackForm(1L));
		Diode_4A_ZPM.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.07", "Diode Cable 4A ZPM", 7, 4).getStackForm(1L));
		Diode_4A_UV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode4A.tier.08", "Diode Cable 4A UV", 8, 4).getStackForm(1L));
		
		Diode_16A_ULV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.00", "Diode Cable 16A ULV", 0, 16).getStackForm(1L));
		Diode_16A_LV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.01", "Diode Cable 16A LV", 1, 16).getStackForm(1L));
		Diode_16A_MV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.02", "Diode Cable 16A MV", 2, 16).getStackForm(1L));
		Diode_16A_HV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.03", "Diode Cable 16A HV", 3, 16).getStackForm(1L));
		Diode_16A_EV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.04", "Diode Cable 16A EV", 4, 16).getStackForm(1L));
		Diode_16A_IV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.05", "Diode Cable 16A IV", 5, 16).getStackForm(1L));
		Diode_16A_LuV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.06", "Diode Cable 16A LuV", 6, 16).getStackForm(1L));
		Diode_16A_ZPM.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.07", "Diode Cable 16A ZPM", 7, 16).getStackForm(1L));
		Diode_16A_UV.set(new GT_MetaTileEntity_Diode(ID2++, "impact.diode16A.tier.08", "Diode Cable 16A UV", 8, 16).getStackForm(1L));
		
		Tank_Hatch.set(new GTMTE_TankHatch(ID2++, "impact.machine.tankhatchio", "I/O Tank Hatch", 5).getStackForm(1L));
		
		Generator_Steam_Turbine_EV.set(new GT_MetaTileEntity_SteamTurbine(ID2++, "impact.basicgenerator.steamturbine.tier.04", "EV Steam Turbine", 4).getStackForm(1L));
		Generator_Diesel_EV.set(new GT_MetaTileEntity_DieselGenerator(ID2++, "impact.basicgenerator.diesel.tier.04", "EV Combustion Generator", 4).getStackForm(1L));
		
		ID2++; // ID:14564 boxinator hatch - removed
		
		Generator_Semi_Turbine_EV.set(new GTMTE_SemifluidGenerator(ID2++, "impact.basicgenerator.semifluid.tier.04", "EV Semifluid Generator", 4).getStackForm(1L));
		
		Long_Distance_Pipeline_Fluid.set(new GTMTE_LongDistancePipelineFluid(ID2++, "long.distance.pipeline.fluid", "Long Distance Fluid Pipeline", 1).getStackForm(1L));
		Long_Distance_Pipeline_Item.set(new GTMTE_LongDistancePipelineItem(ID2++, "long.distance.pipeline.item", "Long Distance Item Pipeline", 1).getStackForm(1L));
		
		Parallel_Hatch_Debug.set(new GTMTE_ParallelHatch_Debug(ID2++, "impact.machine.parallelhatch.debug", "Debug Parallel Receiver", 8, 256).getStackForm(1L));
		
		ID2++; // ID:14569 GT_MetaTileEntity_Hatch_OutputBus_ME
		
		Creative_Tank.set(new GTMTE_DebugTank(ID2++, "impact.machine.creativetank", "Creative Tank", 5).getStackForm(1L));
		
		Quadruple_Input_Hatch.set(new GTMTE_Multi_Hatch_Input(ID2++, "impact.hatch.quadruple", "Quadruple Input Hatch", 4).getStackForm(1L));
		Nonuple_Input_Hatch.set(new GTMTE_Multi_Hatch_Input(ID2++, "impact.hatch.nonuple", "Nonuple Input Hatch", 5).getStackForm(1L));
		
		Nuclear_Hatch_Rod.set(new GTMTE_Reactor_Rod_Hatch(ID2++, "impact.hatch.nuclear.rod", "Nuclear Rod Hatch").getStackForm(1L));
		
		Parallel_Hatch_IN4.set(new GTMTE_ParallelHatch_Input(ID2++, "impact.machine.parallelhatch.in.4", "Parallel Receiver 4P", 5, 4).getStackForm(1L));
		Parallel_Hatch_OUT4.set(new GTMTE_ParallelHatch_Output(ID2++, "impact.machine.parallelhatch.out.4", "Parallel Transmitter 4P", 5, 4).getStackForm(1L));
		
		Parallel_Hatch_IN16.set(new GTMTE_ParallelHatch_Input(ID2++, "impact.machine.parallelhatch.in.16", "Parallel Receiver 16P", 6, 16).getStackForm(1L));
		Parallel_Hatch_OUT16.set(new GTMTE_ParallelHatch_Output(ID2++, "impact.machine.parallelhatch.out.16", "Parallel Transmitter 16P", 6, 16).getStackForm(1L));
		
		Parallel_Hatch_IN64.set(new GTMTE_ParallelHatch_Input(ID2++, "impact.machine.parallelhatch.in.64", "Parallel Receiver 64P", 7, 64).getStackForm(1L));
		Parallel_Hatch_OUT64.set(new GTMTE_ParallelHatch_Output(ID2++, "impact.machine.parallelhatch.out.64", "Parallel Transmitter 64P", 7, 64).getStackForm(1L));
		
		Parallel_Hatch_IN256.set(new GTMTE_ParallelHatch_Input(ID2++, "impact.machine.parallelhatch.in.256", "Parallel Receiver 256P", 8, 256).getStackForm(1L));
		Parallel_Hatch_OUT256.set(new GTMTE_ParallelHatch_Output(ID2++, "impact.machine.parallelhatch.out.256", "Parallel Transmitter 256P", 8, 256).getStackForm(1L));
		
		Communication_Hatch_Transmitter.set(new GTMTE_SpaceSatellite_Transmitter(ID2++, "impact.machine.communicationhatch.transmitter", "Communication Transmitter").getStackForm(1L));
		Communication_Hatch_Receiver.set(new GTMTE_CommunicationTower_Receiver(ID2++, "impact.machine.communicationhatch.receiver", "Communication Receiver").getStackForm(1L));
		
		Parallel_Hatch_Rack.set(new GTMTE_ComputerRack(ID2++, "impact.machine.rack", "Parallel Computer Rack").getStackForm(1L));
		
		Regulate_Digital_Chest.set(new GTMTE_RegulateDigitalChest(ID2++, "impact.machine.regulatechest", "Regulate Digital Chest", 1).getStackForm(1L));
		
		BusHatch_Input.set(new GTMTE_BusHatch_Input(ID2++, "impact.hatch.bushatch.input", "Input Item/Fluid Bus", 3).getStackForm(1L));
		
		Regulate_One_Stack_Chest.set(new GTMTE_OneStackRegulateChest(ID2++, "impact.machine.onestackregulatechest", "One Stack Regulate Chest", 1).getStackForm(1L));
		
		Drying_Oven_LV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(ID2++, "basicmachine.dryingoven.tier.01", "LV Drying Oven", 1,
				"Sterilisation and drying of all kinds of materials", GT_RecipeMaps.sDryingOven, 1, 2, 0,
				0, 1, "DryingOven.png", GregTech_API.sSoundList.get(207), false, false, 0, "ELECTRIC_FURNACE",
				new Object[]{"WCW", "BMB", "WCW", 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL, 'B', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.STICK_DISTILLATION, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.CIRCUIT, 'W', OrePrefixes.cableGt01.get(Materials.Tin)}
		).getStackForm(1L));
		Drying_Oven_MV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(ID2++, "basicmachine.dryingoven.tier.02", "MV Drying Oven", 2,
				"Sterilisation and drying of all kinds of materials", GT_RecipeMaps.sDryingOven, 1, 2, 0,
				0, 1, "DryingOven.png", GregTech_API.sSoundList.get(207), false, false, 0, "ELECTRIC_FURNACE",
				new Object[]{"WCW", "BMB", "WCW", 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL, 'B', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.STICK_DISTILLATION, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.CIRCUIT, 'W', OrePrefixes.cableGt01.get(Materials.Copper)}
		).getStackForm(1L));
		Drying_Oven_HV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(ID2++, "basicmachine.dryingoven.tier.03", "HV Drying Oven", 3,
				"Sterilisation and drying of all kinds of materials", GT_RecipeMaps.sDryingOven, 1, 2, 0,
				0, 1, "DryingOven.png", GregTech_API.sSoundList.get(207), false, false, 0, "ELECTRIC_FURNACE",
				new Object[]{"WCW", "BMB", "WCW", 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL, 'B', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.STICK_DISTILLATION, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.CIRCUIT, 'W', OrePrefixes.cableGt01.get(Materials.Gold)}
		).getStackForm(1L));
		Drying_Oven_EV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(ID2++, "basicmachine.dryingoven.tier.04", "EV Drying Oven", 4,
				"Sterilisation and drying of all kinds of materials", GT_RecipeMaps.sDryingOven, 1, 2, 0,
				0, 1, "DryingOven.png", GregTech_API.sSoundList.get(207), false, false, 0, "ELECTRIC_FURNACE",
				new Object[]{"WCW", "BMB", "WCW", 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL, 'B', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.STICK_DISTILLATION, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.CIRCUIT, 'W', OrePrefixes.cableGt01.get(Materials.Aluminium)}
		).getStackForm(1L));
		Drying_Oven_IV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(ID2++, "basicmachine.dryingoven.tier.05", "IV Drying Oven", 5,
				"Sterilisation and drying of all kinds of materials", GT_RecipeMaps.sDryingOven, 1, 2, 0,
				0, 1, "DryingOven.png", GregTech_API.sSoundList.get(207), false, false, 0, "ELECTRIC_FURNACE",
				new Object[]{"WCW", "BMB", "WCW", 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL, 'B', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.STICK_DISTILLATION, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.CIRCUIT, 'W', OrePrefixes.cableGt01.get(Materials.Platinum)}
		).getStackForm(1L));
		
		AE_Hatch_Connector.set(new GTMTE_AE_Connector(ID2++, "impact.basic.meconnector", "ME Connector").getStackForm(1L));
		MP_Hatch_Chamber.set(new GTMTE_Hatch_MESystemMPChamber(ID2++).getStackForm(1L));
		
		Solar_ULV.set(new GTMTE_Solar(ID2++, "ULV Solar Panel", 0).getStackForm(1L));
		Solar_LV.set(new GTMTE_Solar(ID2++, "LV Solar Panel", 1).getStackForm(1L));
		Solar_MV.set(new GTMTE_Solar(ID2++, "MV Solar Panel", 2).getStackForm(1L));
		Solar_HV.set(new GTMTE_Solar(ID2++, "HV Solar Panel", 3).getStackForm(1L));
		Solar_EV.set(new GTMTE_Solar(ID2++, "EV Solar Panel", 4).getStackForm(1L));
		Solar_IV.set(new GTMTE_Solar(ID2++, "IV Solar Panel", 5).getStackForm(1L));
		Solar_LuV.set(new GTMTE_Solar(ID2++, "LuV Solar Panel", 6).getStackForm(1L));
		Solar_ZPM.set(new GTMTE_Solar(ID2++, "ZPM Solar Panel", 7).getStackForm(1L));
		Solar_UV.set(new GTMTE_Solar(ID2++, "UV Solar Panel", 8).getStackForm(1L));
		
		Volumetric_Configurator.set(new GTMTE_VolumetricConfigurator(ID2++, "Volumetric Configurator").getStackForm(1L));
		
		LDPE_LV.set(new GTMTE_LongDistancePipelineEnergy(ID2++, "long.distance.pipeline.energy.0", "Long Distance Energy Pipeline LV", 1).getStackForm(1L));
		LDPE_MV.set(new GTMTE_LongDistancePipelineEnergy(ID2++, "long.distance.pipeline.energy.1", "Long Distance Energy Pipeline MV", 2).getStackForm(1L));
		LDPE_HV.set(new GTMTE_LongDistancePipelineEnergy(ID2++, "long.distance.pipeline.energy.2", "Long Distance Energy Pipeline HV", 3).getStackForm(1L));
		LDPE_EV.set(new GTMTE_LongDistancePipelineEnergy(ID2++, "long.distance.pipeline.energy.3", "Long Distance Energy Pipeline EV", 4).getStackForm(1L));
		LDPE_IV.set(new GTMTE_LongDistancePipelineEnergy(ID2++, "long.distance.pipeline.energy.4", "Long Distance Energy Pipeline IV", 5).getStackForm(1L));
		
		Laser_reflector_IV.set(new GTMTE_LaserEnergy_Reflector(ID2++, "IV Laser Reflector", 5, 256).getStackForm(1));
		Laser_reflector_LuV.set(new GTMTE_LaserEnergy_Reflector(ID2++, "LuV Laser Reflector", 6, 256).getStackForm(1));
		Laser_reflector_ZPM.set(new GTMTE_LaserEnergy_Reflector(ID2++, "ZPM Laser Reflector", 7, 256).getStackForm(1));
		Laser_reflector_UV.set(new GTMTE_LaserEnergy_Reflector(ID2++, "UV Laser Reflector", 8, 256).getStackForm(1));
		Laser_reflector_UHV.set(new GTMTE_LaserEnergy_Reflector(ID2++, "UHV Laser Reflector", 9, 256).getStackForm(1));
		Laser_reflector_UEV.set(new GTMTE_LaserEnergy_Reflector(ID2++, "UEV Laser Reflector", 10, 256).getStackForm(1));
		Laser_reflector_UIV.set(new GTMTE_LaserEnergy_Reflector(ID2++, "UIV Laser Reflector", 11, 256).getStackForm(1));
		Laser_reflector_UMV.set(new GTMTE_LaserEnergy_Reflector(ID2++, "UMV Laser Reflector", 12, 256).getStackForm(1));
		Laser_reflector_UXV.set(new GTMTE_LaserEnergy_Reflector(ID2++, "UXV Laser Reflector", 13, 256).getStackForm(1));
		
		EnergyTunnel1_IV.set(new GTMTE_LaserEnergy_In(ID2++, "IV 256 A/t Laser Target (Input) Hatch", 5, 256).getStackForm(1L));
		EnergyTunnel2_IV.set(new GTMTE_LaserEnergy_In(ID2++, "IV 1024 A/t Laser Target (Input) Hatch", 5, 1024).getStackForm(1L));
		EnergyTunnel3_IV.set(new GTMTE_LaserEnergy_In(ID2++, "IV 4096 A/t Laser Target (Input) Hatch", 5, 4096).getStackForm(1L));
		EnergyTunnel4_IV.set(new GTMTE_LaserEnergy_In(ID2++, "IV 16384 A/t Laser Target (Input) Hatch", 5, 16384).getStackForm(1L));
		EnergyTunnel5_IV.set(new GTMTE_LaserEnergy_In(ID2++, "IV 65536 A/t Laser Target (Input) Hatch", 5, 65536).getStackForm(1L));
		EnergyTunnel6_IV.set(new GTMTE_LaserEnergy_In(ID2++, "IV 262144 A/t Laser Target (Input) Hatch", 5, 262144).getStackForm(1L));
		EnergyTunnel7_IV.set(new GTMTE_LaserEnergy_In(ID2++, "IV 1048576 A/t Laser Target (Input) Hatch", 5, 1048576).getStackForm(1L));
		
		EnergyTunnel1_LuV.set(new GTMTE_LaserEnergy_In(ID2++, "LuV 256 A/t Laser Target (Input) Hatch", 6, 256).getStackForm(1L));
		EnergyTunnel2_LuV.set(new GTMTE_LaserEnergy_In(ID2++, "LuV 1024 A/t Laser Target (Input) Hatch", 6, 1024).getStackForm(1L));
		EnergyTunnel3_LuV.set(new GTMTE_LaserEnergy_In(ID2++, "LuV 4096 A/t Laser Target (Input) Hatch", 6, 4096).getStackForm(1L));
		EnergyTunnel4_LuV.set(new GTMTE_LaserEnergy_In(ID2++, "LuV 16384 A/t Laser Target (Input) Hatch", 6, 16384).getStackForm(1L));
		EnergyTunnel5_LuV.set(new GTMTE_LaserEnergy_In(ID2++, "LuV 65536 A/t Laser Target (Input) Hatch", 6, 65536).getStackForm(1L));
		EnergyTunnel6_LuV.set(new GTMTE_LaserEnergy_In(ID2++, "LuV 262144 A/t Laser Target (Input) Hatch", 6, 262144).getStackForm(1L));
		EnergyTunnel7_LuV.set(new GTMTE_LaserEnergy_In(ID2++, "LuV 1048576 A/t Laser Target (Input) Hatch", 6, 1048576).getStackForm(1L));
		
		EnergyTunnel1_ZPM.set(new GTMTE_LaserEnergy_In(ID2++, "ZPM 256 A/t Laser Target (Input) Hatch", 7, 256).getStackForm(1L));
		EnergyTunnel2_ZPM.set(new GTMTE_LaserEnergy_In(ID2++, "ZPM 1024 A/t Laser Target (Input) Hatch", 7, 1024).getStackForm(1L));
		EnergyTunnel3_ZPM.set(new GTMTE_LaserEnergy_In(ID2++, "ZPM 4096 A/t Laser Target (Input) Hatch", 7, 4096).getStackForm(1L));
		EnergyTunnel4_ZPM.set(new GTMTE_LaserEnergy_In(ID2++, "ZPM 16384 A/t Laser Target (Input) Hatch", 7, 16384).getStackForm(1L));
		EnergyTunnel5_ZPM.set(new GTMTE_LaserEnergy_In(ID2++, "ZPM 65536 A/t Laser Target (Input) Hatch", 7, 65536).getStackForm(1L));
		EnergyTunnel6_ZPM.set(new GTMTE_LaserEnergy_In(ID2++, "ZPM 262144 A/t Laser Target (Input) Hatch", 7, 262144).getStackForm(1L));
		EnergyTunnel7_ZPM.set(new GTMTE_LaserEnergy_In(ID2++, "ZPM 1048576 A/t Laser Target (Input) Hatch", 7, 1048576).getStackForm(1L));
		
		EnergyTunnel1_UV .set(new GTMTE_LaserEnergy_In(ID2++, "UV 256 A/t Laser Target (Input) Hatch", 8, 256).getStackForm(1L));
		EnergyTunnel2_UV .set(new GTMTE_LaserEnergy_In(ID2++, "UV 1024 A/t Laser Target (Input) Hatch", 8, 1024).getStackForm(1L));
		EnergyTunnel3_UV .set(new GTMTE_LaserEnergy_In(ID2++, "UV 4096 A/t Laser Target (Input) Hatch", 8, 4096).getStackForm(1L));
		EnergyTunnel4_UV .set(new GTMTE_LaserEnergy_In(ID2++, "UV 16384 A/t Laser Target (Input) Hatch", 8, 16384).getStackForm(1L));
		EnergyTunnel5_UV .set(new GTMTE_LaserEnergy_In(ID2++, "UV 65536 A/t Laser Target (Input) Hatch", 8, 65536).getStackForm(1L));
		EnergyTunnel6_UV .set(new GTMTE_LaserEnergy_In(ID2++, "UV 262144 A/t Laser Target (Input) Hatch", 8, 262144).getStackForm(1L));
		EnergyTunnel7_UV .set(new GTMTE_LaserEnergy_In(ID2++, "UV 1048576 A/t Laser Target (Input) Hatch", 8, 1048576).getStackForm(1L));
		
		EnergyTunnel1_UHV.set(new GTMTE_LaserEnergy_In(ID2++, "UHV 256 A/t Laser Target (Input) Hatch", 9, 256).getStackForm(1L));
		EnergyTunnel2_UHV.set(new GTMTE_LaserEnergy_In(ID2++, "UHV 1024 A/t Laser Target (Input) Hatch", 9, 1024).getStackForm(1L));
		EnergyTunnel3_UHV.set(new GTMTE_LaserEnergy_In(ID2++, "UHV 4096 A/t Laser Target (Input) Hatch", 9, 4096).getStackForm(1L));
		EnergyTunnel4_UHV.set(new GTMTE_LaserEnergy_In(ID2++, "UHV 16384 A/t Laser Target (Input) Hatch", 9, 16384).getStackForm(1L));
		EnergyTunnel5_UHV.set(new GTMTE_LaserEnergy_In(ID2++, "UHV 65536 A/t Laser Target (Input) Hatch", 9, 65536).getStackForm(1L));
		EnergyTunnel6_UHV.set(new GTMTE_LaserEnergy_In(ID2++, "UHV 262144 A/t Laser Target (Input) Hatch", 9, 262144).getStackForm(1L));
		EnergyTunnel7_UHV.set(new GTMTE_LaserEnergy_In(ID2++, "UHV 1048576 A/t Laser Target (Input) Hatch", 9, 1048576).getStackForm(1L));
		
		EnergyTunnel1_UEV.set(new GTMTE_LaserEnergy_In(ID2++, "UEV 256 A/t Laser Target (Input) Hatch", 10, 256).getStackForm(1L));
		EnergyTunnel2_UEV.set(new GTMTE_LaserEnergy_In(ID2++, "UEV 1024 A/t Laser Target (Input) Hatch", 10, 1024).getStackForm(1L));
		EnergyTunnel3_UEV.set(new GTMTE_LaserEnergy_In(ID2++, "UEV 4096 A/t Laser Target (Input) Hatch", 10, 4096).getStackForm(1L));
		EnergyTunnel4_UEV.set(new GTMTE_LaserEnergy_In(ID2++, "UEV 16384 A/t Laser Target (Input) Hatch", 10, 16384).getStackForm(1L));
		EnergyTunnel5_UEV.set(new GTMTE_LaserEnergy_In(ID2++, "UEV 65536 A/t Laser Target (Input) Hatch", 10, 65536).getStackForm(1L));
		EnergyTunnel6_UEV.set(new GTMTE_LaserEnergy_In(ID2++, "UEV 262144 A/t Laser Target (Input) Hatch", 10, 262144).getStackForm(1L));
		EnergyTunnel7_UEV.set(new GTMTE_LaserEnergy_In(ID2++, "UEV 1048576 A/t Laser Target (Input) Hatch", 10, 1048576).getStackForm(1L));
		
		EnergyTunnel1_UIV.set(new GTMTE_LaserEnergy_In(ID2++, "UIV 256 A/t Laser Target (Input) Hatch", 11, 256).getStackForm(1L));
		EnergyTunnel2_UIV.set(new GTMTE_LaserEnergy_In(ID2++, "UIV 1024 A/t Laser Target (Input) Hatch", 11, 1024).getStackForm(1L));
		EnergyTunnel3_UIV.set(new GTMTE_LaserEnergy_In(ID2++, "UIV 4096 A/t Laser Target (Input) Hatch", 11, 4096).getStackForm(1L));
		EnergyTunnel4_UIV.set(new GTMTE_LaserEnergy_In(ID2++, "UIV 16384 A/t Laser Target (Input) Hatch", 11, 16384).getStackForm(1L));
		EnergyTunnel5_UIV.set(new GTMTE_LaserEnergy_In(ID2++, "UIV 65536 A/t Laser Target (Input) Hatch", 11, 65536).getStackForm(1L));
		EnergyTunnel6_UIV.set(new GTMTE_LaserEnergy_In(ID2++, "UIV 262144 A/t Laser Target (Input) Hatch", 11, 262144).getStackForm(1L));
		EnergyTunnel7_UIV.set(new GTMTE_LaserEnergy_In(ID2++, "UIV 1048576 A/t Laser Target (Input) Hatch", 11, 1048576).getStackForm(1L));
		
		EnergyTunnel1_UMV.set(new GTMTE_LaserEnergy_In(ID2++, "UMV 256 A/t Laser Target (Input) Hatch", 12, 256).getStackForm(1L));
		EnergyTunnel2_UMV.set(new GTMTE_LaserEnergy_In(ID2++, "UMV 1024 A/t Laser Target (Input) Hatch", 12, 1024).getStackForm(1L));
		EnergyTunnel3_UMV.set(new GTMTE_LaserEnergy_In(ID2++, "UMV 4096 A/t Laser Target (Input) Hatch", 12, 4096).getStackForm(1L));
		EnergyTunnel4_UMV.set(new GTMTE_LaserEnergy_In(ID2++, "UMV 16384 A/t Laser Target (Input) Hatch", 12, 16384).getStackForm(1L));
		EnergyTunnel5_UMV.set(new GTMTE_LaserEnergy_In(ID2++, "UMV 65536 A/t Laser Target (Input) Hatch", 12, 65536).getStackForm(1L));
		EnergyTunnel6_UMV.set(new GTMTE_LaserEnergy_In(ID2++, "UMV 262144 A/t Laser Target (Input) Hatch", 12, 262144).getStackForm(1L));
		EnergyTunnel7_UMV.set(new GTMTE_LaserEnergy_In(ID2++, "UMV 1048576 A/t Laser Target (Input) Hatch", 12, 1048576).getStackForm(1L));
		
		EnergyTunnel1_UXV.set(new GTMTE_LaserEnergy_In(ID2++, "UXV 256 A/t Laser Target (Input) Hatch", 13, 256).getStackForm(1L));
		EnergyTunnel2_UXV.set(new GTMTE_LaserEnergy_In(ID2++, "UXV 1024 A/t Laser Target (Input) Hatch", 13, 1024).getStackForm(1L));
		EnergyTunnel3_UXV.set(new GTMTE_LaserEnergy_In(ID2++, "UXV 4096 A/t Laser Target (Input) Hatch", 13, 4096).getStackForm(1L));
		EnergyTunnel4_UXV.set(new GTMTE_LaserEnergy_In(ID2++, "UXV 16384 A/t Laser Target (Input) Hatch", 13, 16384).getStackForm(1L));
		EnergyTunnel5_UXV.set(new GTMTE_LaserEnergy_In(ID2++, "UXV 65536 A/t Laser Target (Input) Hatch", 13, 65536).getStackForm(1L));
		EnergyTunnel6_UXV.set(new GTMTE_LaserEnergy_In(ID2++, "UXV 262144 A/t Laser Target (Input) Hatch", 13, 262144).getStackForm(1L));
		EnergyTunnel7_UXV.set(new GTMTE_LaserEnergy_In(ID2++, "UXV 1048576 A/t Laser Target (Input) Hatch", 13, 1048576).getStackForm(1L));
		
		EnergyTunnel9001 .set(new GTMTE_LaserEnergy_In(ID2++, "Legendary Laser Target (Input) Hatch", 13, (int) V[13]).getStackForm(1L));
		
		DynamoTunnel1_IV .set(new GTMTE_LaserEnergy_Out(ID2++,  "IV 256 A/t Laser Source (Output) Hatch", 5, 256).getStackForm(1L));
		DynamoTunnel2_IV .set(new GTMTE_LaserEnergy_Out(ID2++,  "IV 1024 A/t Laser Source (Output) Hatch", 5, 1024).getStackForm(1L));
		DynamoTunnel3_IV .set(new GTMTE_LaserEnergy_Out(ID2++,  "IV 4096 A/t Laser Source (Output) Hatch", 5, 4096).getStackForm(1L));
		DynamoTunnel4_IV .set(new GTMTE_LaserEnergy_Out(ID2++,  "IV 16384 A/t Laser Source (Output) Hatch", 5, 16384).getStackForm(1L));
		DynamoTunnel5_IV .set(new GTMTE_LaserEnergy_Out(ID2++,  "IV 65536 A/t Laser Source (Output) Hatch", 5, 65536).getStackForm(1L));
		DynamoTunnel6_IV .set(new GTMTE_LaserEnergy_Out(ID2++,  "IV 262144 A/t Laser Source (Output) Hatch", 5, 262144).getStackForm(1L));
		DynamoTunnel7_IV .set(new GTMTE_LaserEnergy_Out(ID2++,  "IV 1048576 A/t Laser Source (Output) Hatch", 5, 1048576).getStackForm(1L));
		
		DynamoTunnel1_LuV.set(new GTMTE_LaserEnergy_Out(ID2++,  "LuV 256 A/t Laser Source (Output) Hatch", 6, 256).getStackForm(1L));
		DynamoTunnel2_LuV.set(new GTMTE_LaserEnergy_Out(ID2++,  "LuV 1024 A/t Laser Source (Output) Hatch", 6, 1024).getStackForm(1L));
		DynamoTunnel3_LuV.set(new GTMTE_LaserEnergy_Out(ID2++,  "LuV 4096 A/t Laser Source (Output) Hatch", 6, 4096).getStackForm(1L));
		DynamoTunnel4_LuV.set(new GTMTE_LaserEnergy_Out(ID2++,  "LuV 16384 A/t Laser Source (Output) Hatch", 6, 16384).getStackForm(1L));
		DynamoTunnel5_LuV.set(new GTMTE_LaserEnergy_Out(ID2++,  "LuV 65536 A/t Laser Source (Output) Hatch", 6, 65536).getStackForm(1L));
		DynamoTunnel6_LuV.set(new GTMTE_LaserEnergy_Out(ID2++,  "LuV 262144 A/t Laser Source (Output) Hatch", 6, 262144).getStackForm(1L));
		DynamoTunnel7_LuV.set(new GTMTE_LaserEnergy_Out(ID2++,  "LuV 1048576 A/t Laser Source (Output) Hatch", 6, 1048576).getStackForm(1L));
		
		DynamoTunnel1_ZPM.set(new GTMTE_LaserEnergy_Out(ID2++,  "ZPM 256 A/t Laser Source (Output) Hatch", 7, 256).getStackForm(1L));
		DynamoTunnel2_ZPM.set(new GTMTE_LaserEnergy_Out(ID2++,  "ZPM 1024 A/t Laser Source (Output) Hatch", 7, 1024).getStackForm(1L));
		DynamoTunnel3_ZPM.set(new GTMTE_LaserEnergy_Out(ID2++,  "ZPM 4096 A/t Laser Source (Output) Hatch", 7, 4096).getStackForm(1L));
		DynamoTunnel4_ZPM.set(new GTMTE_LaserEnergy_Out(ID2++,  "ZPM 16384 A/t Laser Source (Output) Hatch", 7, 16384).getStackForm(1L));
		DynamoTunnel5_ZPM.set(new GTMTE_LaserEnergy_Out(ID2++,  "ZPM 65536 A/t Laser Source (Output) Hatch", 7, 65536).getStackForm(1L));
		DynamoTunnel6_ZPM.set(new GTMTE_LaserEnergy_Out(ID2++,  "ZPM 262144 A/t Laser Source (Output) Hatch", 7, 262144).getStackForm(1L));
		DynamoTunnel7_ZPM.set(new GTMTE_LaserEnergy_Out(ID2++,  "ZPM 1048576 A/t Laser Source (Output) Hatch", 7, 1048576).getStackForm(1L));
		
		DynamoTunnel1_UV .set(new GTMTE_LaserEnergy_Out(ID2++,  "UV 256 A/t Laser Source (Output) Hatch", 8, 256).getStackForm(1L));
		DynamoTunnel2_UV .set(new GTMTE_LaserEnergy_Out(ID2++,  "UV 1024 A/t Laser Source (Output) Hatch", 8, 1024).getStackForm(1L));
		DynamoTunnel3_UV .set(new GTMTE_LaserEnergy_Out(ID2++,  "UV 4096 A/t Laser Source (Output) Hatch", 8, 4096).getStackForm(1L));
		DynamoTunnel4_UV .set(new GTMTE_LaserEnergy_Out(ID2++,  "UV 16384 A/t Laser Source (Output) Hatch", 8, 16384).getStackForm(1L));
		DynamoTunnel5_UV .set(new GTMTE_LaserEnergy_Out(ID2++,  "UV 65536 A/t Laser Source (Output) Hatch", 8, 65536).getStackForm(1L));
		DynamoTunnel6_UV .set(new GTMTE_LaserEnergy_Out(ID2++,  "UV 262144 A/t Laser Source (Output) Hatch", 8, 262144).getStackForm(1L));
		DynamoTunnel7_UV .set(new GTMTE_LaserEnergy_Out(ID2++,  "UV 1048576 A/t Laser Source (Output) Hatch", 8, 1048576).getStackForm(1L));
		
		DynamoTunnel1_UHV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UHV 256 A/t Laser Source (Output) Hatch", 9, 256).getStackForm(1L));
		DynamoTunnel2_UHV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UHV 1024 A/t Laser Source (Output) Hatch", 9, 1024).getStackForm(1L));
		DynamoTunnel3_UHV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UHV 4096 A/t Laser Source (Output) Hatch", 9, 4096).getStackForm(1L));
		DynamoTunnel4_UHV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UHV 16384 A/t Laser Source (Output) Hatch", 9, 16384).getStackForm(1L));
		DynamoTunnel5_UHV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UHV 65536 A/t Laser Source (Output) Hatch", 9, 65536).getStackForm(1L));
		DynamoTunnel6_UHV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UHV 262144 A/t Laser Source (Output) Hatch", 9, 262144).getStackForm(1L));
		DynamoTunnel7_UHV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UHV 1048576 A/t Laser Source (Output) Hatch", 9, 1048576).getStackForm(1L));
		
		DynamoTunnel1_UEV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UEV 256 A/t Laser Source (Output) Hatch", 10, 256).getStackForm(1L));
		DynamoTunnel2_UEV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UEV 1024 A/t Laser Source (Output) Hatch", 10, 1024).getStackForm(1L));
		DynamoTunnel3_UEV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UEV 4096 A/t Laser Source (Output) Hatch", 10, 4096).getStackForm(1L));
		DynamoTunnel4_UEV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UEV 16384 A/t Laser Source (Output) Hatch", 10, 16384).getStackForm(1L));
		DynamoTunnel5_UEV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UEV 65536 A/t Laser Source (Output) Hatch", 10, 65536).getStackForm(1L));
		DynamoTunnel6_UEV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UEV 262144 A/t Laser Source (Output) Hatch", 10, 262144).getStackForm(1L));
		DynamoTunnel7_UEV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UEV 1048576 A/t Laser Source (Output) Hatch", 10, 1048576).getStackForm(1L));
		
		DynamoTunnel1_UIV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UIV 256 A/t Laser Source (Output) Hatch", 11, 256).getStackForm(1L));
		DynamoTunnel2_UIV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UIV 1024 A/t Laser Source (Output) Hatch", 11, 1024).getStackForm(1L));
		DynamoTunnel3_UIV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UIV 4096 A/t Laser Source (Output) Hatch", 11, 4096).getStackForm(1L));
		DynamoTunnel4_UIV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UIV 16384 A/t Laser Source (Output) Hatch", 11, 16384).getStackForm(1L));
		DynamoTunnel5_UIV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UIV 65536 A/t Laser Source (Output) Hatch", 11, 65536).getStackForm(1L));
		DynamoTunnel6_UIV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UIV 262144 A/t Laser Source (Output) Hatch", 11, 262144).getStackForm(1L));
		DynamoTunnel7_UIV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UIV 1048576 A/t Laser Source (Output) Hatch", 11, 1048576).getStackForm(1L));
		
		DynamoTunnel1_UMV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UMV 256 A/t Laser Source (Output) Hatch", 12, 256).getStackForm(1L));
		DynamoTunnel2_UMV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UMV 1024 A/t Laser Source (Output) Hatch", 12, 1024).getStackForm(1L));
		DynamoTunnel3_UMV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UMV 4096 A/t Laser Source (Output) Hatch", 12, 4096).getStackForm(1L));
		DynamoTunnel4_UMV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UMV 16384 A/t Laser Source (Output) Hatch", 12, 16384).getStackForm(1L));
		DynamoTunnel5_UMV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UMV 65536 A/t Laser Source (Output) Hatch", 12, 65536).getStackForm(1L));
		DynamoTunnel6_UMV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UMV 262144 A/t Laser Source (Output) Hatch", 12, 262144).getStackForm(1L));
		DynamoTunnel7_UMV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UMV 1048576 A/t Laser Source (Output) Hatch", 12, 1048576).getStackForm(1L));
		
		DynamoTunnel1_UXV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UXV 256 A/t Laser Source (Output) Hatch", 13, 256).getStackForm(1L));
		DynamoTunnel2_UXV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UXV 1024 A/t Laser Source (Output) Hatch", 13, 1024).getStackForm(1L));
		DynamoTunnel3_UXV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UXV 4096 A/t Laser Source (Output) Hatch", 13, 4096).getStackForm(1L));
		DynamoTunnel4_UXV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UXV 16384 A/t Laser Source (Output) Hatch", 13, 16384).getStackForm(1L));
		DynamoTunnel5_UXV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UXV 65536 A/t Laser Source (Output) Hatch", 13, 65536).getStackForm(1L));
		DynamoTunnel6_UXV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UXV 262144 A/t Laser Source (Output) Hatch", 13, 262144).getStackForm(1L));
		DynamoTunnel7_UXV.set(new GTMTE_LaserEnergy_Out(ID2++,  "UXV 1048576 A/t Laser Source (Output) Hatch", 13, 1048576).getStackForm(1L));
		
		DynamoTunnel9001 .set(new GTMTE_LaserEnergy_Out(ID2++,  "Legendary Laser Source (Output) Hatch", 13, (int) V[13]).getStackForm(1L));
		
		Mining_Hatch_ULV.set(new GTMTE_OreHatch(ID2++, "Miner Drill Hatch", 0).getStackForm(1));
		Mining_Enrich_HV.set(new GTMTE_EnrichmentUnit(ID2++, "Miner Enrichment Unit", 3).getStackForm(1));
		
		RecipeEditor.set(new GTMTE_RecipeEditor(ID2++, "Recipe Editor Machines", 3).getStackForm(1));
		RecipeEditorCrafting.set(new GTMTE_RecipeEditorCrafting(ID2++, "Recipe Editor WorkTable", 3).getStackForm(1));
		
		ProspectorLV.set(new GTMTE_Prospector(ID2++, "Ore and Oil Prospector", 1).getStackForm(1));
		ProspectorMV.set(new GTMTE_Prospector(ID2++, "Ore and Oil Prospector", 2).getStackForm(1));
		ProspectorHV.set(new GTMTE_Prospector(ID2++, "Ore and Oil Prospector", 3).getStackForm(1));
		ProspectorEV.set(new GTMTE_Prospector(ID2++, "Ore and Oil Prospector", 4).getStackForm(1));
		ProspectorIV.set(new GTMTE_Prospector(ID2++, "Ore and Oil Prospector", 5).getStackForm(1));
		
		Maintenance.set(new GTMTE_Maintenance(ID2++, "Non-serviceable Maintenance Hatch", 8).getStackForm(1));
		
		Solar_UHV.set(new GTMTE_Solar(ID2++, "UHV Solar Panel", 9).getStackForm(1L));
		Solar_UEV.set(new GTMTE_Solar(ID2++, "UEV Solar Panel", 10).getStackForm(1L));
		Solar_UIV.set(new GTMTE_Solar(ID2++, "UIV Solar Panel", 11).getStackForm(1L));
		
		Level_Emitter.set(new GTMTE_AELevelEmitter(ID2++, "Level Emitter").getStackForm(1));
		
		Steam_Out_Hatch.set(new GT_MegaHatch_Output(ID2++, "Steam Output Hatch").getStackForm(1));
		Steam_In_Hatch.set(new GT_MegaHatch_Input(ID2++, "Steam Input Hatch").getStackForm(1));
		
		INFO("Last ID Basic_Register.java: " + ID2);
		
		DustWasherULV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13035,
				"impact.basicmachine.dustwasher.tier.00", "ULV Dust Washer", 0, "Washed your Dusts",
				GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000, 0, 1, "Autoclave.png", "", false,
				false, 0, "DUSTWASHER",
				new Object[]{"IPI", "IMI", "ICI", 'I', OrePrefixes.plate.get(Materials.WroughtIron), 'P', GT_ItemList.ULVPump, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}
		).getStackForm(1L));
		DustWasherLV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13036,
				"impact.basicmachine.dustwasher.tier.01", "LV Dust Washer", 1, "Washed your Dusts",
				GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000 * 2, 0, 1, "Autoclave.png", "", false,
				false, 0, "DUSTWASHER",
				new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}
		).getStackForm(1L));
		DustWasherMV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13037,
				"impact.basicmachine.dustwasher.tier.02", "MV Dust Washer", 2, "Washed your Dusts",
				GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000 * 3, 0, 1, "Autoclave.png", "", false,
				false, 0, "DUSTWASHER",
				new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}
		).getStackForm(1L));
		DustWasherHV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13038,
				"impact.basicmachine.dustwasher.tier.03", "HV Dust Washer", 3, "Washed your Dusts",
				GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000 * 4, 0, 1, "Autoclave.png", "", false,
				false, 0, "DUSTWASHER",
				new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE,
						'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M',
						GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C',
						GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}
		).getStackForm(1L));
		DustWasherEV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13039,
				"impact.basicmachine.dustwasher.tier.04", "EV Dust Washer", 4, "Washed your Dusts",
				GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000 * 5, 0, 1, "Autoclave.png", "", false,
				false, 0, "DUSTWASHER",
				new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}
		).getStackForm(1L));
		DustWasherIV.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13040,
				"impact.basicmachine.dustwasher.tier.05", "IV Dust Washer", 5, "Washed your Dusts",
				GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000 * 6, 0, 1, "Autoclave.png", "", false,
				false, 0, "DUSTWASHER",
				new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}
		).getStackForm(1L));
		
		
		Machine_LV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13010,
				"impact.basicmachine.componentassembler.tier.01", "LV Component Assembler", 1,
				"Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes, 6, 1, 16000,
				0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0,
				"COMPONENTASSEMBLER", null
		).getStackForm(1L));
		Machine_MV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13011,
				"impact.basicmachine.componentassembler.tier.02", "MV Component Assembler",
				2, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes, 6, 1,
				24000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0,
				"COMPONENTASSEMBLER", null
		).getStackForm(1L));
		Machine_HV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13012,
				"impact.basicmachine.componentassembler.tier.03",
				"HV Component Assembler", 3, "Components, Assemble!",
				GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes, 6, 1, 32000, 0, 1, "Assembler.png",
				GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null
		).getStackForm(1L));
		Machine_EV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13013,
				"impact.basicmachine.componentassembler.tier.04",
				"EV Component Assembler", 4, "Components, Assemble!",
				GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes, 6, 1, 48000, 0, 1, "Assembler.png",
				GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null
		).getStackForm(1L));
		Machine_IV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13014,
				"impact.basicmachine.componentassembler.tier.05",
				"IV Component Assembler", 5, "Components, Assemble!",
				GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes, 6, 1, 64000, 0, 1, "Assembler.png",
				GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null
		).getStackForm(1L));
		
		
		Machine_ULV_Assembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13032, "impact.basicmachine.assembler.tier.00",
				"ULV Assembler", 0, "Avengers, Assemble!",
				GT_Recipe.GT_Recipe_Map.sAssemblerRecipes, 3, 1, 8000, 0, 1, "Assembler0.png",
				GregTech_API.sSoundList.get(204), false, false, 0, "ASSEMBLER", null
		).getStackForm(1L));
		Generator_Steam_Turbine_ULV.set(new GT_MetaTileEntity_SteamTurbine(13025, "impact.basicgenerator.steamturbine.tier.00", "ULV Steam Turbine", 0).getStackForm(1L));
		Generator_Diesel_ULV.set(new GT_MetaTileEntity_DieselGenerator(13026, "impact.basicgenerator.diesel.tier.00", "ULV Combustion Generator", 0).getStackForm(1L));
		Generator_Gas_Turbine_ULV.set(new GT_MetaTileEntity_GasTurbine(13027, "impact.basicgenerator.gasturbine.tier.00", "ULV Gas Turbine", 0).getStackForm(1L));
		Generator_Semi_Turbine_ULV.set(new GTMTE_SemifluidGenerator(13028, "impact.basicgenerator.semifluid.tier.00", "ULV Semifluid Generator", 0).getStackForm(1L));
		
		Hatch_Output_Prim.set(new GT_MetaTileEntity_Primitive_Hatch_Output(14013, "impact.primitive.hatchoutput", "Primitive Output Hatch", 0, 0).getStackForm(1L));
		Hatch_Output_Pump.set(new GT_MetaTileEntity_Primitive_Hatch_Output(14014, "impact.primitive.hatchoutput1", "Pump Output Hatch", 0, 1).getStackForm(1L));
		Bus_Input_Prim.set(new GT_MetaTileEntity_Primitive_InputBus(14012, "impact.primitive.input", "Primitive Input Bus", 0).getStackForm(1L));
		Bus_Output_Prim.set(new GT_MetaTileEntity_Primitive_OutputBus(14011, "impact.primitive.output", "Primitive Output Bus", 0).getStackForm(1L));
		
		Generator_Semi_Turbine_LV.set(new GTMTE_SemifluidGenerator(13029, "impact.basicgenerator.semifluid.tier.01", "LV Semifluid Generator", 1).getStackForm(1L));
		Generator_Semi_Turbine_MV.set(new GTMTE_SemifluidGenerator(13030, "impact.basicgenerator.semifluid.tier.02", "MV Semifluid Generator", 2).getStackForm(1L));
		Generator_Semi_Turbine_HV.set(new GTMTE_SemifluidGenerator(13031, "impact.basicgenerator.semifluid.tier.03", "HV Semifluid Generator", 3).getStackForm(1L));
		
		Hatch_Input_UEV.set(new GT_MetaTileEntity_Hatch_Input(12250, "impact.hatch.input.tier.10", "UEV Input Hatch", 10).getStackForm(1L));
		Hatch_Input_UIV.set(new GT_MetaTileEntity_Hatch_Input(12251, "impact.hatch.input.tier.11", "UIV Input Hatch", 11).getStackForm(1L));
		Hatch_Input_UMV.set(new GT_MetaTileEntity_Hatch_Input(12252, "impact.hatch.input.tier.12", "UMV Input Hatch", 12).getStackForm(1L));
		Hatch_Input_UXV.set(new GT_MetaTileEntity_Hatch_Input(12253, "impact.hatch.input.tier.13", "UXV Input Hatch", 13).getStackForm(1L));
		Hatch_Input_OPV.set(new GT_MetaTileEntity_Hatch_Input(12254, "impact.hatch.input.tier.14", "OpV Input Hatch", 14).getStackForm(1L));
		Hatch_Input_MAX.set(new GT_MetaTileEntity_Hatch_Input(12255, "impact.hatch.input.tier.15", "MAX Input Hatch", 15).getStackForm(1L));
		
		Hatch_Output_UEV.set(new GT_MetaTileEntity_Hatch_Output(12256, "impact.hatch.output.tier.10", "UEV Output Hatch", 10).getStackForm(1L));
		Hatch_Output_UIV.set(new GT_MetaTileEntity_Hatch_Output(12257, "impact.hatch.output.tier.11", "UIV Output Hatch", 11).getStackForm(1L));
		Hatch_Output_UMV.set(new GT_MetaTileEntity_Hatch_Output(12258, "impact.hatch.output.tier.12", "UMV Output Hatch", 12).getStackForm(1L));
		Hatch_Output_UXV.set(new GT_MetaTileEntity_Hatch_Output(12259, "impact.hatch.output.tier.13", "UXV Output Hatch", 13).getStackForm(1L));
		Hatch_Output_OPV.set(new GT_MetaTileEntity_Hatch_Output(12260, "impact.hatch.output.tier.14", "OpV Output Hatch", 14).getStackForm(1L));
		Hatch_Output_MAX.set(new GT_MetaTileEntity_Hatch_Output(12261, "impact.hatch.output.tier.15", "MAX Output Hatch", 15).getStackForm(1L));
		
		Bus_Input_UEV.set(new GT_MetaTileEntity_Hatch_InputBus(12262, "impact.bus.input.tier.10", "UEV Input Bus", 10).getStackForm(1L));
		Bus_Input_UIV.set(new GT_MetaTileEntity_Hatch_InputBus(12263, "impact.bus.input.tier.11", "UIV Input Bus", 11).getStackForm(1L));
		Bus_Input_UMV.set(new GT_MetaTileEntity_Hatch_InputBus(12264, "impact.bus.input.tier.12", "UMV Input Bus", 12).getStackForm(1L));
		Bus_Input_UXV.set(new GT_MetaTileEntity_Hatch_InputBus(12265, "impact.bus.input.tier.13", "UXV Input Bus", 13).getStackForm(1L));
		Bus_Input_OPV.set(new GT_MetaTileEntity_Hatch_InputBus(12266, "impact.bus.input.tier.14", "OpV Input Bus", 14).getStackForm(1L));
		Bus_Input_MAX.set(new GT_MetaTileEntity_Hatch_InputBus(12267, "impact.bus.input.tier.15", "MAX Input Bus", 15).getStackForm(1L));
		
		Bus_Output_UEV.set(new GT_MetaTileEntity_Hatch_OutputBus(12268, "impact.bus.output.tier.10", "UEV Output Bus", 10).getStackForm(1L));
		Bus_Output_UIV.set(new GT_MetaTileEntity_Hatch_OutputBus(12269, "impact.bus.output.tier.11", "UIV Output Bus", 11).getStackForm(1L));
		Bus_Output_UMV.set(new GT_MetaTileEntity_Hatch_OutputBus(12270, "impact.bus.output.tier.12", "UMV Output Bus", 12).getStackForm(1L));
		Bus_Output_UXV.set(new GT_MetaTileEntity_Hatch_OutputBus(12271, "impact.bus.output.tier.13", "UXV Output Bus", 13).getStackForm(1L));
		Bus_Output_OPV.set(new GT_MetaTileEntity_Hatch_OutputBus(12272, "impact.bus.output.tier.14", "OpV Output Bus", 14).getStackForm(1L));
		Bus_Output_MAX.set(new GT_MetaTileEntity_Hatch_OutputBus(12273, "impact.bus.output.tier.15", "MAX Output Bus", 15).getStackForm(1L));
		
		EnergyMulti4_IV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14509, "IV 4A Energy Hatch", 5, 4).getStackForm(1L));
		
		EnergyMulti4_LuV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14510, "LuV 4A Energy Hatch", 6, 4).getStackForm(1L));
		EnergyMulti16_LuV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14513, "LuV 16A Energy Hatch", 6, 16).getStackForm(1L));
		
		EnergyMulti4_ZPM.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14511, "ZPM 4A Energy Hatch", 7, 4).getStackForm(1L));
		EnergyMulti16_ZPM.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14514, "ZPM 16A Energy Hatch", 7, 16).getStackForm(1L));
		EnergyMulti64_ZPM.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14516, "ZPM 64A Energy Hatch", 7, 64).getStackForm(1L));
		
		EnergyMulti4_UV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14512, "UV 4A Energy Hatch", 8, 4).getStackForm(1L));
		EnergyMulti16_UV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14515, "UV 16A Energy Hatch", 8, 16).getStackForm(1L));
		EnergyMulti64_UV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(14517, "UV 64A Energy Hatch", 8, 64).getStackForm(1L));
		
		EnergyMulti4_UHV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15104, "UHV 4A Energy Hatch", 9, 4).getStackForm(1L));
		EnergyMulti16_UHV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15114, "UHV 16A Energy Hatch", 9, 16).getStackForm(1L));
		EnergyMulti64_UHV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15124, "UHV 64A Energy Hatch", 9, 64).getStackForm(1L));
		
		EnergyMulti4_UEV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15105, "UEV 4A Energy Hatch", 10, 4).getStackForm(1L));
		EnergyMulti16_UEV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15115, "UEV 16A Energy Hatch", 10, 16).getStackForm(1L));
		EnergyMulti64_UEV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15125, "UEV 64A Energy Hatch", 10, 64).getStackForm(1L));
		
		EnergyMulti4_UIV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15106, "UIV 4A Energy Hatch", 11, 4).getStackForm(1L));
		EnergyMulti16_UIV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15116, "UIV 16A Energy Hatch", 11, 16).getStackForm(1L));
		EnergyMulti64_UIV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15126, "UIV 64A Energy Hatch", 11, 64).getStackForm(1L));
		
		EnergyMulti4_UMV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15107, "UMV 4A Energy Hatch", 12, 4).getStackForm(1L));
		EnergyMulti16_UMV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15117, "UMV 16A Energy Hatch", 12, 16).getStackForm(1L));
		EnergyMulti64_UMV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15127, "UMV 64A Energy Hatch", 12, 64).getStackForm(1L));
		
		EnergyMulti4_UXV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15108, "UXV 4A Energy Hatch", 13, 4).getStackForm(1L));
		EnergyMulti16_UXV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15118, "UXV 16A Energy Hatch", 13, 16).getStackForm(1L));
		EnergyMulti64_UXV.set(new GT_MetaTileEntity_Hatch_EnergyMulti(15128, "UXV 64A Energy Hatch", 13, 64).getStackForm(1L));
		
		DynamoMulti2_EV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14519,  "EV 2A Dynamo Hatch", 4, 2).getStackForm(1L));
		
		DynamoMulti2_IV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14520,  "IV 2A Dynamo Hatch", 5, 2).getStackForm(1L));
		DynamoMulti4_IV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14524,  "IV 4A Dynamo Hatch", 5, 4).getStackForm(1L));
		
		DynamoMulti2_LuV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14521,  "LuV 2A Dynamo Hatch", 6, 2).getStackForm(1L));
		DynamoMulti4_LuV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14525,  "LuV 4A Dynamo Hatch", 6, 4).getStackForm(1L));
		DynamoMulti16_LuV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14528, "LuV 16A Dynamo Hatch", 6, 16).getStackForm(1L));
		
		DynamoMulti2_ZPM.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14522,"ZPM 2A Dynamo Hatch", 7, 2).getStackForm(1L));
		DynamoMulti4_ZPM.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14526,"ZPM 4A Dynamo Hatch", 7, 4).getStackForm(1L));
		DynamoMulti16_ZPM.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14529, "ZPM 16A Dynamo Hatch", 7, 16).getStackForm(1L));
		DynamoMulti64_ZPM.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14531, "ZPM 64A Dynamo Hatch", 7, 64).getStackForm(1L));
		
		DynamoMulti2_UV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14523, "UV 2A Dynamo Hatch", 8, 2).getStackForm(1L));
		DynamoMulti4_UV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14527, "UV 4A Dynamo Hatch", 8, 4).getStackForm(1L));
		DynamoMulti16_UV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14530, "UV 16A Dynamo Hatch", 8, 16).getStackForm(1L));
		DynamoMulti64_UV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(14532, "UV 64A Dynamo Hatch", 8, 64).getStackForm(1L));
		
		DynamoMulti2_UHV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15095, "UHV 2A Dynamo Hatch", 9, 2).getStackForm(1L));
		DynamoMulti4_UHV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15204, "UHV 4A Dynamo Hatch", 9, 4).getStackForm(1L));
		DynamoMulti16_UHV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15214, "UHV 16A Dynamo Hatch", 9, 16).getStackForm(1L));
		DynamoMulti64_UHV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15224, "UHV 64A Dynamo Hatch", 9, 64).getStackForm(1L));

		DynamoMulti2_UEV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15096, "UEV 2A Dynamo Hatch", 10, 2).getStackForm(1L));
		DynamoMulti4_UEV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15205, "UEV 4A Dynamo Hatch", 10, 4).getStackForm(1L));
		DynamoMulti16_UEV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15215, "UEV 16A Dynamo Hatch", 10, 16).getStackForm(1L));
		DynamoMulti64_UEV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15225, "UEV 64A Dynamo Hatch", 10, 64).getStackForm(1L));
		
		DynamoMulti4_UIV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15206, "UIV 4A Dynamo Hatch", 11, 4).getStackForm(1L));
		DynamoMulti16_UIV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15216, "UIV 16A Dynamo Hatch", 11, 16).getStackForm(1L));
		DynamoMulti64_UIV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15226, "UIV 64A Dynamo Hatch", 11, 64).getStackForm(1L));
		
		DynamoMulti4_UMV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15207, "UMV 4A Dynamo Hatch", 12, 4).getStackForm(1L));
		DynamoMulti16_UMV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15217, "UMV 16A Dynamo Hatch", 12, 16).getStackForm(1L));
		DynamoMulti64_UMV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15227, "UMV 64A Dynamo Hatch", 12, 64).getStackForm(1L));
		
		DynamoMulti4_UXV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15208, "UXV 4A Dynamo Hatch", 13, 4).getStackForm(1L));
		DynamoMulti16_UXV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15218, "UXV 16A Dynamo Hatch", 13, 16).getStackForm(1L));
		DynamoMulti64_UXV.set(new GT_MetaTileEntity_Hatch_DynamoMulti(15228, "UXV 64A Dynamo Hatch", 13, 64).getStackForm(1L));
		
		Machine_DataReader.set(new GTMTE_DataReader(15481, "Data Reader").getStackForm(1));
		
	}
}