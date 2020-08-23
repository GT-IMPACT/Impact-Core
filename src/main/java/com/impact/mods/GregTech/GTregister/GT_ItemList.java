package com.impact.mods.GregTech.GTregister;

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
    RedstoneEmeraldChipset, RedstoneDiamondChipset,
    EngineeringProcessorFluidDiamondCore, EngineeringProcessorFluidEmeraldCore, EngineeringProcessorFluidGoldCore,
    EngineeringProcessorItemAdvEmeraldCore, EngineeringProcessorItemDiamondCore, EngineeringProcessorItemEmeraldCore,
    EngineeringProcessorSpatialPulsatingCore, LogicProcessorItemGoldCore, EngravedDiamondCrystalChip, EngravedEnergyChip,
    EngravedGoldChip, EngravedQuantumChip, GeneticCircuit, EnvironmentalCircuit, PulsatingSpatialCoreChip,
    GoldFluidCoreChip, GoldCoreChip, DiamondCoreChip, DiamondFluidCoreChip, EmeraldAdvancedCoreChip, EmeraldAdvancedFluidCoreChip,
    EmeraldHighAdvancedCoreChip,

    packEuropa, packBarnardaE, packBarnardaF, packCallisto, packCentauriA, packCeres, packDeimos, packEarth,
    packEris, packGanymed, packHaumea, packIapetus, packIo, packMakeMake, packMercury, packMoon,
    packOberon, packPhobos, packPluto, packTCetiE, packTitan, packTriton, packVegaB, packVenus, packMars,
    packProteus, packAsteroids, spacebox1, spacebox2, spacebox3, spacebox4, spacebox5, spacebox6, spacebox7,
    spacebox8,

    Machine_FreezerSolidifier,

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
    Generator_Semi_Turbine_MV, Generator_Semi_Turbine_HV,

    Water_Tank,

    //HATCHES
    Hatch_Output_Prim, Bus_Input_Prim, Bus_Output_Prim, Hatch_Output_Pump,

    Hatch_Input_UEV, Hatch_Input_UIV, Hatch_Input_UMV, Hatch_Input_UXV, Hatch_Input_OPV, Hatch_Input_MAX,
    Hatch_Output_UEV, Hatch_Output_UIV, Hatch_Output_UMV, Hatch_Output_UXV, Hatch_Output_OPV, Hatch_Output_MAX,

    Bus_Input_UEV, Bus_Input_UIV, Bus_Input_UMV, Bus_Input_UXV, Bus_Input_OPV, Bus_Input_MAX,
    Bus_Output_UEV, Bus_Output_UIV, Bus_Output_UMV, Bus_Output_UXV, Bus_Output_OPV, Bus_Output_MAX,

    Energy_4A_IV, Energy_4A_LuV, Energy_4A_ZPM, Energy_4A_UV, Energy_16A_LuV, Energy_16A_ZPM, Energy_16A_UV, Energy_64A_ZPM,
    Energy_64A_UV, Energy_256A_UV,

    Dynamo_2A_EV, Dynamo_2A_IV, Dynamo_2A_LuV, Dynamo_2A_ZPM, Dynamo_2A_UV, Dynamo_4A_IV, Dynamo_4A_LuV, Dynamo_4A_ZPM,
    Dynamo_4A_UV, Dynamo_16A_LuV, Dynamo_16A_ZPM, Dynamo_16A_UV, Dynamo_64A_ZPM, Dynamo_64A_UV, Dynamo_256A_UV,

    Diode_2A_ULV, Diode_2A_LV, Diode_2A_MV, Diode_2A_HV, Diode_2A_EV, Diode_2A_IV, Diode_2A_LuV, Diode_2A_ZPM, Diode_2A_UV,
    Diode_4A_ULV, Diode_4A_LV, Diode_4A_MV, Diode_4A_HV, Diode_4A_EV, Diode_4A_IV, Diode_4A_LuV, Diode_4A_ZPM, Diode_4A_UV,
    Diode_16A_ULV, Diode_16A_LV, Diode_16A_MV, Diode_16A_HV, Diode_16A_EV, Diode_16A_IV, Diode_16A_LuV, Diode_16A_ZPM, Diode_16A_UV,

    Tank_Hatch,

    Nuclear_Resistant_OH,

    //MULTI
    Machine_CokeOven, Machine_Multi_Farm, Multi_Tank, Single_Tank, SOFC_Low, SOFC_Medium, SOFC_Huge,

    Machine_PBE, Machine_LaserEngraver, Machine_Assembler, Machine_Centrifuge, Machine_Electrolyzer, Machine_Wire, Machine_Supply,
    Machine_Utility, Machine_Brewmenter, Machine_ArcFurnace, Machine_Cutting, Machine_Extradifier, Machine_Macerator, Machine_Mixer,
    Machine_Siftarator, Machine_DDDPrinter,
    PowerStation, PowerReactor, PowerTurbine, Machine_FreezSolidifier, Machine_BlastSmelter, WaterDrill, BasicWaterPump,
    AdvVacuumFreezer, LapPowerStation, SawMill, Pyrolyse, AdvPyrolyse,

    Naquadah_multi, Naquadah_Liquid_multi, Heavy_Metal_Cyclone, Naquadah_Liquid_Enriched,

    //Cases
    //Page3 1-15
    UpgradeCasingT1, UpgradeCasingT2, UpgradeCasingT3, UpgradeCasingT4, PBECasing, EngraverCasing, AssemblerCasing,
    CentrifugeCasing, ElectrolyzerCasing, WireFactoryCasing, SupplyProductionCasing, UtilityMachineCasing, BrewmenterCasing, ArcCasing,
    CuttingCasing, MixingCasing,
    //Page3 16-31
    NukeTurbineCasing, ElectromagneticCasing, ExtradificationCasing, MacerationCasing, DDDPrinterCasing, DDDPrinterCasing3x3,
    DDDPrinterCasing4x4, PrimitiveWaterPumpCase, LSCC, SawCase, NqCasing, CycloneCasing,
    //Page3 16-31
    Casing_Farm,


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
        mStack = GT_Utility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public Item getItem() {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return null;
        }
        return mStack.getItem();
    }

    @Override
    public Block getBlock() {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
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
        return !GT_Utility.isStackInvalid(aStack) && GT_Utility.areUnificationsEqual((ItemStack) aStack, aWildcard ? getWildcard(1) : get(1), aIgnoreNBT);
    }

    @Override
    public ItemStack get(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmount(aAmount, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getWildcard(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmountAndMetaData(aAmount, W, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getUndamaged(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmountAndMetaData(aAmount, 0, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getAlmostBroken(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmountAndMetaData(aAmount, mStack.getMaxDamage() - 1, GT_OreDictUnificator.get(mStack));
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
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmountAndMetaData(aAmount, aMetaValue, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public IItemContainer registerOre(Object... aOreNames) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        for (Object tOreName : aOreNames) {
            GT_OreDictUnificator.registerOre(tOreName, get(1));
        }
        return this;
    }

    @Override
    public IItemContainer registerWildcardAsOre(Object... aOreNames) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        for (Object tOreName : aOreNames) {
            GT_OreDictUnificator.registerOre(tOreName, getWildcard(1));
        }
        return this;
    }
}
