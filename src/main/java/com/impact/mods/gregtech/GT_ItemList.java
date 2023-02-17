package com.impact.mods.gregtech;

import gregtech.api.interfaces.IItemContainer;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static gregtech.api.enums.GT_Values.W;

public enum GT_ItemList implements IItemContainer {
	
	//items
	Coin, RedstoneRedChipset, RedstoneQuartzChipset, RedstonePulsatingChipset, RedstoneIronChipset, RedstoneGoldChipset,
	RedstoneEmeraldChipset, RedstoneDiamondChipset, EngineeringProcessorFluidDiamondCore, EngineeringProcessorFluidEmeraldCore,
	EngineeringProcessorFluidGoldCore, EngineeringProcessorItemAdvEmeraldCore, EngineeringProcessorItemDiamondCore,
	EngineeringProcessorItemEmeraldCore, EngineeringProcessorSpatialPulsatingCore, LogicProcessorItemGoldCore,
	EngravedDiamondCrystalChip, EngravedEnergyChip, EngravedGoldChip, EngravedQuantumChip, GeneticCircuit, EnvironmentalCircuit,
	PulsatingSpatialCoreChip, GoldFluidCoreChip, GoldCoreChip, DiamondCoreChip, DiamondFluidCoreChip, EmeraldAdvancedCoreChip,
	EmeraldAdvancedFluidCoreChip, EmeraldHighAdvancedCoreChip,
	
	packEuropa, packBarnardaE, packBarnardaF, packCallisto, packCentauriA, packCeres, packDeimos, packEarth,
	packEris, packGanymed, packHaumea, packIapetus, packIo, packMakeMake, packMercury, packMoon,
	packOberon, packPhobos, packPluto, packTCetiE, packTitan, packTriton, packVegaB, packVenus, packMars,
	packProteus, packAsteroids, spacebox1, spacebox2, spacebox3, spacebox4, spacebox5, spacebox6, spacebox7,
	spacebox8, Aerostat, ConstructLaser, DebugOreGen, OreProbe,
	
	ULVMotor, ULVPiston, ULVRobotArm, ULVConveyorModule, ULVPump,
	
	//BASIC
	Portable_Tank_ULV, Portable_Tank_LV, Portable_Tank_MV, Portable_Tank_HV, Portable_Tank_EV, Portable_Tank_IV,
	Portable_Tank_LuV, Portable_Tank_ZPM, Portable_Tank_UV,
	
	DustWasherULV, DustWasherLV, DustWasherMV, DustWasherHV, DustWasherEV, DustWasherIV, DustWasherLuV, DustWasherZPM,
	DustWasherUV, DustWasherUHV, DustWasherUEV,
	
	Machine_LV_ComponentAssembler, Machine_MV_ComponentAssembler, Machine_HV_ComponentAssembler, Machine_EV_ComponentAssembler,
	Machine_IV_ComponentAssembler, Machine_LuV_ComponentAssembler, Machine_ZPM_ComponentAssembler, Machine_UV_ComponentAssembler,
	Machine_UHV_ComponentAssembler, Machine_UEV_ComponentAssembler,
	
	Machine_ULV_Assembler, CircuitProgrammer,
	
	Generator_Steam_Turbine_ULV, Generator_Diesel_ULV, Generator_Gas_Turbine_ULV, Generator_Semi_Turbine_ULV, Generator_Semi_Turbine_LV,
	Generator_Semi_Turbine_MV, Generator_Semi_Turbine_HV, Generator_Steam_Turbine_EV, Generator_Diesel_EV, Generator_Gas_Turbine_EV,
	Generator_Semi_Turbine_EV, Generator_Steam_Turbine_IV, Generator_Diesel_IV, Generator_Gas_Turbine_IV, Generator_Semi_Turbine_IV,
	
	Water_Tank, Creative_Tank, Regulate_Digital_Chest, Regulate_One_Stack_Chest,
	
	Drying_Oven_LV, Drying_Oven_MV, Drying_Oven_HV, Drying_Oven_EV, Drying_Oven_IV,
	
	//HATCHES
	Hatch_Output_Prim, Bus_Input_Prim, Bus_Output_Prim, Hatch_Output_Pump,
	
	Hatch_Input_UEV, Hatch_Input_UIV, Hatch_Input_UMV, Hatch_Input_UXV, Hatch_Input_OPV, Hatch_Input_MAX,
	Hatch_Output_UEV, Hatch_Output_UIV, Hatch_Output_UMV, Hatch_Output_UXV, Hatch_Output_OPV, Hatch_Output_MAX,
	
	Bus_Input_UEV, Bus_Input_UIV, Bus_Input_UMV, Bus_Input_UXV, Bus_Input_OPV, Bus_Input_MAX,
	Bus_Output_UEV, Bus_Output_UIV, Bus_Output_UMV, Bus_Output_UXV, Bus_Output_OPV, Bus_Output_MAX,
	
	Diode_2A_ULV, Diode_2A_LV, Diode_2A_MV, Diode_2A_HV, Diode_2A_EV, Diode_2A_IV, Diode_2A_LuV, Diode_2A_ZPM, Diode_2A_UV,
	Diode_4A_ULV, Diode_4A_LV, Diode_4A_MV, Diode_4A_HV, Diode_4A_EV, Diode_4A_IV, Diode_4A_LuV, Diode_4A_ZPM, Diode_4A_UV,
	Diode_16A_ULV, Diode_16A_LV, Diode_16A_MV, Diode_16A_HV, Diode_16A_EV, Diode_16A_IV, Diode_16A_LuV, Diode_16A_ZPM, Diode_16A_UV,
	
	Tank_Hatch, Quadruple_Input_Hatch, Nonuple_Input_Hatch,
	
	Parallel_Hatch_IN4, Parallel_Hatch_OUT4, Parallel_Hatch_IN16, Parallel_Hatch_OUT16, Parallel_Hatch_IN64, Parallel_Hatch_OUT64,
	Parallel_Hatch_IN256, Parallel_Hatch_OUT256, Parallel_Hatch_Debug,
	
	Communication_Hatch_Transmitter, Communication_Hatch_Receiver, Parallel_Hatch_Rack,
	
	Nuclear_Hatch_Rod, Boxinator_Hatch_Input, BusHatch_Input,
	
	AE_Hatch_Connector, MP_Hatch_Chamber,
	
	Solar_ULV, Solar_LV, Solar_MV, Solar_HV, Solar_EV, Solar_IV, Solar_LuV, Solar_ZPM, Solar_UV, Solar_UHV, Solar_UEV, Solar_UIV,
	
	Volumetric_Configurator, Level_Emitter,
	
	Steam_In_Hatch, Steam_Out_Hatch,
	
	EnergyMulti4_IV, EnergyMulti16_IV, EnergyMulti64_IV,
	EnergyMulti4_LuV, EnergyMulti16_LuV, EnergyMulti64_LuV,
	EnergyMulti4_ZPM, EnergyMulti16_ZPM, EnergyMulti64_ZPM,
	EnergyMulti4_UV, EnergyMulti16_UV, EnergyMulti64_UV,
	EnergyMulti4_UHV, EnergyMulti16_UHV, EnergyMulti64_UHV,
	EnergyMulti4_UEV, EnergyMulti16_UEV, EnergyMulti64_UEV,
	EnergyMulti4_UIV, EnergyMulti16_UIV, EnergyMulti64_UIV,
	EnergyMulti4_UMV, EnergyMulti16_UMV, EnergyMulti64_UMV,
	EnergyMulti4_UXV, EnergyMulti16_UXV, EnergyMulti64_UXV,
	
	
	DynamoMulti2_EV,
	DynamoMulti2_IV, DynamoMulti4_IV, DynamoMulti16_IV, DynamoMulti64_IV,
	DynamoMulti2_LuV, DynamoMulti4_LuV, DynamoMulti16_LuV, DynamoMulti64_LuV,
	DynamoMulti2_ZPM, DynamoMulti4_ZPM, DynamoMulti16_ZPM, DynamoMulti64_ZPM,
	DynamoMulti2_UV, DynamoMulti4_UV, DynamoMulti16_UV, DynamoMulti64_UV,
	DynamoMulti2_UHV, DynamoMulti4_UHV, DynamoMulti16_UHV, DynamoMulti64_UHV,
	DynamoMulti2_UEV, DynamoMulti4_UEV, DynamoMulti16_UEV, DynamoMulti64_UEV,
	DynamoMulti4_UIV, DynamoMulti16_UIV, DynamoMulti64_UIV,
	DynamoMulti4_UMV, DynamoMulti16_UMV, DynamoMulti64_UMV,
	DynamoMulti4_UXV, DynamoMulti16_UXV, DynamoMulti64_UXV,
	
	//MULTI
	Machine_CokeOven, Machine_Multi_Farm, Multi_Tank, Single_Tank, SOFC_Low, SOFC_Medium, SOFC_Huge,
	
	Machine_PBE, Machine_LaserEngraver, Machine_Assembler, Machine_Centrifuge, Machine_Electrolyzer, Machine_Wire, Machine_Supply,
	Machine_Utility, Machine_Brewmenter, Machine_ArcFurnace, Machine_Cutting, Machine_Extradifier, Machine_Macerator, Machine_Mixer,
	Machine_Siftarator, Machine_DDDPrinter, Machine_AdvDDDPrinter,
	PowerStation, PowerReactor, PowerTurbine, Machine_FreezSolidifier, Machine_BlastSmelter, WaterDrill, BasicWaterPump,
	AdvVacuumFreezer, AdvCrackUnit, LapPowerStation, SawMill, Pyrolyse, AdvPyrolyse, Machine_FreezerSolidifier,
	Machine_EIF, Machine_ChemicalReactor, Machine_DistTower, Machine_ElectricImplosion, Aerostate,
	
	Naquadah_multi, Naquadah_Liquid_multi, Heavy_Metal_Cyclone, Naquadah_Liquid_Enriched, Moon_Miner, Rail_Assembler, Space_Elevator,
	Nuclear_Reactor_I, Nuclear_Reactor_II, Nuclear_Reactor_III, Huge_Steam_Turbine,
	
	Parallel_Computer, Communication_Tower, Space_Satellite,
	
	//Cases
	UpgradeCasingT1, UpgradeCasingT2, UpgradeCasingT3, UpgradeCasingT4, PBECasing, EngraverCasing, AssemblerCasing,
	CentrifugeCasing, ElectrolyzerCasing, WireFactoryCasing, SupplyProductionCasing, UtilityMachineCasing, BrewmenterCasing, ArcCasing,
	CuttingCasing, MixingCasing,
	
	NukeTurbineCasing, ElectromagneticCasing, ExtradificationCasing, MacerationCasing, DDDPrinterCasing, DDDPrinterCasing3x3,
	DDDPrinterCasing4x4, PrimitiveWaterPumpCase, LSCC, SawCase, NqCasing, CycloneCasing, MoonMinerCasing, RailAssemblerCasing,
	SpaceElevatorCasing, LabSafeLGCasing, MECasing, AerostateCasing, MillCasing,
	
	Casing_Farm, Huge_Casing_Turbine, Space_Satellite_Casing, Tower_Casing, Computer_Casing,
	
	Long_Distance_Pipeline_Fluid, Long_Distance_Pipeline_Item,
	Long_Distance_Pipeline_Fluid_Pipe, Long_Distance_Pipeline_Item_Pipe,
	
	
	LDPE_LV, LDPE_MV, LDPE_HV, LDPE_EV, LDPE_IV,
	LDPFE_LV, LDPFE_MV, LDPFE_HV, LDPFE_EV, LDPFE_IV,
	
	EnergyTunnel9001, DynamoTunnel9001, Machine_DataReader,
	
	Laser_reflector_IV, Laser_reflector_LuV, Laser_reflector_ZPM, Laser_reflector_UV, Laser_reflector_UHV,
	Laser_reflector_UEV, Laser_reflector_UIV, Laser_reflector_UMV, Laser_reflector_UXV,
	
	EnergyTunnel1_IV, EnergyTunnel1_LuV, EnergyTunnel1_ZPM, EnergyTunnel1_UV, EnergyTunnel1_UHV, EnergyTunnel1_UEV, EnergyTunnel1_UIV, EnergyTunnel1_UMV, EnergyTunnel1_UXV,
	EnergyTunnel2_IV, EnergyTunnel2_LuV, EnergyTunnel2_ZPM, EnergyTunnel2_UV, EnergyTunnel2_UHV, EnergyTunnel2_UEV, EnergyTunnel2_UIV, EnergyTunnel2_UMV, EnergyTunnel2_UXV,
	EnergyTunnel3_IV, EnergyTunnel3_LuV, EnergyTunnel3_ZPM, EnergyTunnel3_UV, EnergyTunnel3_UHV, EnergyTunnel3_UEV, EnergyTunnel3_UIV, EnergyTunnel3_UMV, EnergyTunnel3_UXV,
	EnergyTunnel4_IV, EnergyTunnel4_LuV, EnergyTunnel4_ZPM, EnergyTunnel4_UV, EnergyTunnel4_UHV, EnergyTunnel4_UEV, EnergyTunnel4_UIV, EnergyTunnel4_UMV, EnergyTunnel4_UXV,
	EnergyTunnel5_IV, EnergyTunnel5_LuV, EnergyTunnel5_ZPM, EnergyTunnel5_UV, EnergyTunnel5_UHV, EnergyTunnel5_UEV, EnergyTunnel5_UIV, EnergyTunnel5_UMV, EnergyTunnel5_UXV,
	EnergyTunnel6_IV, EnergyTunnel6_LuV, EnergyTunnel6_ZPM, EnergyTunnel6_UV, EnergyTunnel6_UHV, EnergyTunnel6_UEV, EnergyTunnel6_UIV, EnergyTunnel6_UMV, EnergyTunnel6_UXV,
	EnergyTunnel7_IV, EnergyTunnel7_LuV, EnergyTunnel7_ZPM, EnergyTunnel7_UV, EnergyTunnel7_UHV, EnergyTunnel7_UEV, EnergyTunnel7_UIV, EnergyTunnel7_UMV, EnergyTunnel7_UXV,
	
	DynamoTunnel1_IV, DynamoTunnel1_LuV, DynamoTunnel1_ZPM, DynamoTunnel1_UV, DynamoTunnel1_UHV, DynamoTunnel1_UEV, DynamoTunnel1_UIV, DynamoTunnel1_UMV, DynamoTunnel1_UXV,
	DynamoTunnel2_IV, DynamoTunnel2_LuV, DynamoTunnel2_ZPM, DynamoTunnel2_UV, DynamoTunnel2_UHV, DynamoTunnel2_UEV, DynamoTunnel2_UIV, DynamoTunnel2_UMV, DynamoTunnel2_UXV,
	DynamoTunnel3_IV, DynamoTunnel3_LuV, DynamoTunnel3_ZPM, DynamoTunnel3_UV, DynamoTunnel3_UHV, DynamoTunnel3_UEV, DynamoTunnel3_UIV, DynamoTunnel3_UMV, DynamoTunnel3_UXV,
	DynamoTunnel4_IV, DynamoTunnel4_LuV, DynamoTunnel4_ZPM, DynamoTunnel4_UV, DynamoTunnel4_UHV, DynamoTunnel4_UEV, DynamoTunnel4_UIV, DynamoTunnel4_UMV, DynamoTunnel4_UXV,
	DynamoTunnel5_IV, DynamoTunnel5_LuV, DynamoTunnel5_ZPM, DynamoTunnel5_UV, DynamoTunnel5_UHV, DynamoTunnel5_UEV, DynamoTunnel5_UIV, DynamoTunnel5_UMV, DynamoTunnel5_UXV,
	DynamoTunnel6_IV, DynamoTunnel6_LuV, DynamoTunnel6_ZPM, DynamoTunnel6_UV, DynamoTunnel6_UHV, DynamoTunnel6_UEV, DynamoTunnel6_UIV, DynamoTunnel6_UMV, DynamoTunnel6_UXV,
	DynamoTunnel7_IV, DynamoTunnel7_LuV, DynamoTunnel7_ZPM, DynamoTunnel7_UV, DynamoTunnel7_UHV, DynamoTunnel7_UEV, DynamoTunnel7_UIV, DynamoTunnel7_UMV, DynamoTunnel7_UXV,
	
	Filler,
	
	Parametric_Diffuser, Matrix_Stabilizer, Matrix_Containment, ME_System_Provider,
	
	Mining_Hatch_ULV,
	Mining_Enrich_HV,
	RecipeEditor, RecipeEditorCrafting,
	CoalMiner, BasicMiner, AdvancedMiner, OreSamplingMachine,
	ProspectorLV, ProspectorMV, ProspectorHV, ProspectorEV, ProspectorIV,
	
	Maintenance,
	
	Wind_Generator, The_Mill,
	
	end;
	private ItemStack mStack;
	private boolean mHasNotBeenSet = true;
	
	//public static Fluid sOilExtraHeavy, sOilHeavy, sOilMedium, sOilLight, sNaturalGas;
	
	@Override
	public IItemContainer set(Item aItem) {
		mHasNotBeenSet = false;
		if (aItem == null) {
			return this;
		}
		ItemStack aStack = new ItemStack(aItem, 1, 0);
		mStack = GT_Utility.copyAmount(1, aStack);
		return this;
	}
	
	@Override
	public IItemContainer set(ItemStack aStack) {
		mHasNotBeenSet = false;
		mStack         = GT_Utility.copyAmount(1, aStack);
		return this;
	}
	
	@Override
	public Item getItem() {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		if (GT_Utility.isStackInvalid(mStack)) {
			return null;
		}
		return mStack.getItem();
	}
	
	@Override
	public Block getBlock() {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		return GT_Utility.getBlockFromItem(getItem());
	}
	
	@Override
	public final boolean hasBeenSet() {
		return !mHasNotBeenSet;
	}
	
	@Override
	public boolean isStackEqual(Object aStack) {
		return isStackEqual(aStack, false, false);
	}
	
	@Override
	public boolean isStackEqual(Object aStack, boolean aWildcard, boolean aIgnoreNBT) {
		return !GT_Utility.isStackInvalid(aStack) && GT_Utility
				.areUnificationsEqual((ItemStack) aStack, aWildcard ? getWildcard(1) : get(1), aIgnoreNBT);
	}
	
	@Override
	public ItemStack get(long aAmount, Object... aReplacements) {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		if (GT_Utility.isStackInvalid(mStack)) {
			return GT_Utility.copyAmount(aAmount, aReplacements);
		}
		return GT_Utility.copyAmount(aAmount, GT_OreDictUnificator.get(mStack));
	}
	
	@Override
	public ItemStack getWildcard(long aAmount, Object... aReplacements) {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		if (GT_Utility.isStackInvalid(mStack)) {
			return GT_Utility.copyAmount(aAmount, aReplacements);
		}
		return GT_Utility.copyAmountAndMetaData(aAmount, W, GT_OreDictUnificator.get(mStack));
	}
	
	@Override
	public ItemStack getUndamaged(long aAmount, Object... aReplacements) {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		if (GT_Utility.isStackInvalid(mStack)) {
			return GT_Utility.copyAmount(aAmount, aReplacements);
		}
		return GT_Utility.copyAmountAndMetaData(aAmount, 0, GT_OreDictUnificator.get(mStack));
	}
	
	@Override
	public ItemStack getAlmostBroken(long aAmount, Object... aReplacements) {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		if (GT_Utility.isStackInvalid(mStack)) {
			return GT_Utility.copyAmount(aAmount, aReplacements);
		}
		return GT_Utility.copyAmountAndMetaData(aAmount, mStack.getMaxDamage() - 1,
				GT_OreDictUnificator.get(mStack)
		);
	}
	
	@Override
	public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements) {
		ItemStack rStack = get(1, aReplacements);
		if (GT_Utility.isStackInvalid(rStack)) {
			return null;
		}
		rStack.setStackDisplayName(aDisplayName);
		return GT_Utility.copyAmount(aAmount, rStack);
	}
	
	@Override
	public ItemStack getWithCharge(long aAmount, int aEnergy, Object... aReplacements) {
		ItemStack rStack = get(1, aReplacements);
		if (GT_Utility.isStackInvalid(rStack)) {
			return null;
		}
		GT_ModHandler.chargeElectricItem(rStack, aEnergy, Integer.MAX_VALUE, true, false);
		return GT_Utility.copyAmount(aAmount, rStack);
	}
	
	@Override
	public ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements) {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		if (GT_Utility.isStackInvalid(mStack)) {
			return GT_Utility.copyAmount(aAmount, aReplacements);
		}
		return GT_Utility.copyAmountAndMetaData(aAmount, aMetaValue, GT_OreDictUnificator.get(mStack));
	}
	
	@Override
	public IItemContainer registerOre(Object... aOreNames) {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		for (Object tOreName : aOreNames) {
			GT_OreDictUnificator.registerOre(tOreName, get(1));
		}
		return this;
	}
	
	@Override
	public IItemContainer registerWildcardAsOre(Object... aOreNames) {
		if (mHasNotBeenSet) {
			throw new IllegalAccessError(
					"The Enum '" + name() + "' has not been set to an Item at this time!");
		}
		for (Object tOreName : aOreNames) {
			GT_OreDictUnificator.registerOre(tOreName, getWildcard(1));
		}
		return this;
	}
}
