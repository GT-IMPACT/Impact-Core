package com.impact.recipes;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import com.impact.common.item.WoodBrickFormTool;
import com.impact.mods.GregTech.GT_ItemList;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.loaders.postload.GT_CraftingRecipeLoader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import static com.impact.common.item.Core_List_Items.*;
import static com.impact.loader.ItemRegistery.*;
import static com.impact.util.Utilits.Blockstack;
import static gregtech.api.GregTech_API.getStackofAmountFromOreDict;
import static gregtech.api.util.GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES;

public class HandRecipe extends GT_CraftingRecipeLoader implements Runnable {

  private static final String aTextWire1 = "wire.";
  private static final String aTextCable1 = "cable.";
  private static final String aTextWire2 = " Wire";
  private static final String aTextCable2 = " Cable";
  private final static String aTextPlate = "PPP";
  private final static String aTextPlateWrench = "PwP";
  private final static String aTextPlateMotor = "PMP";
  private final static String aTextCableHull = "CMC";
  private final static String aTextWireHull = "WMW";
  private final static String aTextWireChest = "WTW";
  private final static String aTextWireCoil = "WCW";
  private final static String aTextMotorWire = "EWE";
  private final static String aTextWirePump = "WPW";
  private static final String aTextRailcraft = "Railcraft";
  private static final String aTextMachineBeta = "machine.beta";
  private static final String aTextMachineAlpha = "machine.alpha";
  private static final String aTextIron1 = "X X";
  private static final String aTextIron2 = "XXX";
  private final static String aTextForestry = "Forestry";
  private static final long bits = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.BUFFERED;
  private static final long bits2 = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.BUFFERED | DELETE_ALL_OTHER_RECIPES;
  private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE/* | GT_ModHandler.RecipeBits.REVERSIBLE*/;
  private final static long bitsd = GT_ModHandler.RecipeBits.DISMANTLEABLE | bits;

  final Core_Items CoreItems = Core_Items.getInstance(); //пыльки
  final Core_Items2 CoreItems2 = Core_Items2.getInstance(); //компоненты
  final WoodBrickFormTool WoodForm = WoodBrickFormTool.getInstance(); //вуд форма

  public void run1() {
    final Object[] mk1_recipe = {
        "CCC", "PHP", "FBL",
        'C', OrePrefixes.circuit.get(Materials.Data),
        'P', ItemList.Electric_Pump_HV.get(1L),
        'H', ItemList.Hull_HV.get(1L),
        'F', GT_OreDictUnificator.get(OrePrefixes.pipeSmall, Materials.Titanium, 1),
        'B', GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Gold, 1),
        'L', GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Titanium, 1)};
    GT_ModHandler.addCraftingRecipe(GT_ItemList.SOFC_Low.get(1), mk1_recipe);

    final Object[] mk2_recipe = {
        "CCC", "PHP", "FBL",
        'C', OrePrefixes.circuit.get(Materials.Elite),
        'P', ItemList.Electric_Pump_EV.get(1L),
        'H', ItemList.Hull_EV.get(1L),
        'F', GT_OreDictUnificator.get(OrePrefixes.pipeSmall, Materials.TungstenSteel, 1),
        'B', GregTech_API.getStackofAmountFromOreDict("wireGt04SuperconductorEV", 1),
        'L', GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1)};
    GT_ModHandler.addCraftingRecipe(GT_ItemList.SOFC_Medium.get(1), mk2_recipe);

    final Object[] mk3_recipe = {
        "CCC", "PHP", "FBL",
        'C', OrePrefixes.circuit.get(Materials.Master),
        'P', ItemList.Electric_Pump_IV.get(1L),
        'H', ItemList.Hull_IV.get(1L),
        'F', GT_OreDictUnificator.get(OrePrefixes.pipeSmall, Materials.Ultimate, 1),
        'B', GregTech_API.getStackofAmountFromOreDict("wireGt04SuperconductorIV", 1),
        'L', GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Ultimate, 1)};
    GT_ModHandler.addCraftingRecipe(GT_ItemList.SOFC_Huge.get(1), mk3_recipe);

    final Object[] tfft_recipe = {
        "HFH", "PVP", "CFC",
        'H', OrePrefixes.pipeMedium.get(Materials.Titanium),
        'F', ItemList.Field_Generator_MV.get(1L),
        'P', ItemList.Electric_Pump_HV.get(1L),
        'V', ItemList.Hull_HV.get(1L),
        'C', OrePrefixes.circuit.get(Materials.Data)};
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Multi_Tank.get(1), tfft_recipe);
    //SingleTank
    final Object[] singletank_recipe = {
        "HFH", "PVP", "CFC",
        'H', OrePrefixes.pipeMedium.get(Materials.StainlessSteel),
        'F', ItemList.Field_Generator_LV.get(1L),
        'P', ItemList.Electric_Pump_MV.get(1L),
        'V', ItemList.Hull_MV.get(1L),
        'C', OrePrefixes.circuit.get(Materials.Data)};
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Single_Tank.get(1), singletank_recipe);

    final ItemStack[] yszUnit = {
        GT_Utility.getIntegratedCircuit(6),
        GregTech_API.getStackofAmountFromOreDict("plateYSZ", 6),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1),
        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
        ItemList.Electric_Motor_EV.get(1L)};
    GT_Values.RA.addAssemblerRecipe(
        yszUnit,
        Materials.Hydrogen.getGas(4000),
        Blockstack(CeramicBlock, 1, 0),
        20*60, 480);

    //GDC Unit
    final ItemStack[] gdcUnit = {
        GT_Utility.getIntegratedCircuit(6),
        GregTech_API.getStackofAmountFromOreDict("plateGDC", 6),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium,1),
        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium,1),
        ItemList.Electric_Motor_IV.get(1L)};
    GT_Values.RA.addAssemblerRecipe(
        gdcUnit,
        Materials.Hydrogen.getGas(8000),
        Blockstack(CeramicBlock, 1, 1),
        20*60, 1920);
    //LSCF Unit
    final ItemStack[] lscfUnit = {
        GT_Utility.getIntegratedCircuit(6),
        GregTech_API.getStackofAmountFromOreDict("plateLSCF", 6),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1),
        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1),
        ItemList.Electric_Motor_LuV.get(1L)};
    GT_Values.RA.addAssemblerRecipe(
        lscfUnit, Materials.Hydrogen.getGas(16000),
        Blockstack(CeramicBlock, 1, 2),
        20*60, 7680);

    //Field Tier 1
    final ItemStack[] tfftstoragefield1 = {
        GT_Utility.getIntegratedCircuit(6),
        ItemList.Electric_Pump_MV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4)};
    GT_Values.RA.addAssemblerRecipe(
        tfftstoragefield1, FluidRegistry.getFluidStack("molten.polytetrafluoroethylene", 36),
        Blockstack(FluidTankBlock, 1, 0),
        200, 480);
    //Field Tier 2
    final ItemStack[] tfftstoragefield2 = {
        GT_Utility.getIntegratedCircuit(6),
        ItemList.Field_Generator_LV.get(1L),
        ItemList.Electric_Pump_HV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4)};
    GT_Values.RA.addAssemblerRecipe(
        tfftstoragefield2, FluidRegistry.getFluidStack("molten.polytetrafluoroethylene", 144),
        Blockstack(FluidTankBlock, 1, 1),
        200, 1920);
    //Field Tier 3
    final ItemStack[] tfftstoragefield3 = {
        GT_Utility.getIntegratedCircuit(6),
        ItemList.Field_Generator_MV.get(1L),
        ItemList.Electric_Pump_EV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4)};
    GT_Values.RA.addAssemblerRecipe(
        tfftstoragefield3, FluidRegistry.getFluidStack("molten.epoxid", 144),
        Blockstack(FluidTankBlock, 1, 2),
        200, 7680);
    //Field Tier 4
    final ItemStack[] tfftstoragefield4 = {
        GT_Utility.getIntegratedCircuit(6),
        ItemList.Field_Generator_HV.get(1L),
        ItemList.Electric_Pump_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 4)};
    GT_Values.RA.addAssemblerRecipe(
        tfftstoragefield4, FluidRegistry.getFluidStack("molten.epoxid", 144),
        Blockstack(FluidTankBlock, 1, 3),
        200, 30720);
    //Field Tier 5
    final ItemStack[] tfftstoragefield5 = {
        GT_Utility.getIntegratedCircuit(6),
        ItemList.Field_Generator_EV.get(1L),
        ItemList.Electric_Pump_LuV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 4)};
    GT_Values.RA.addAssemblerRecipe(
        tfftstoragefield5,  FluidRegistry.getFluidStack("molten.epoxid", 144),
        Blockstack(FluidTankBlock, 1, 4),
        200, 122880);

    //Field Tier 6
    final ItemStack[] tfftstoragefield6 = {
        GT_Utility.getIntegratedCircuit(6),
        ItemList.Field_Generator_IV.get(1L),
        ItemList.Electric_Pump_ZPM.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 4)};
    GT_Values.RA.addAssemblerRecipe(
        tfftstoragefield6, FluidRegistry.getFluidStack("molten.epoxid", 144),
        Blockstack(FluidTankBlock, 1, 5),
        200, 491520);
    //Field Tier 7
    final ItemStack[] tfftstoragefield7 = {
        GT_Utility.getIntegratedCircuit(6),
        ItemList.Field_Generator_LuV.get(1L),
        ItemList.Electric_Pump_UV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tritanium, 4)};
    GT_Values.RA.addAssemblerRecipe(
        tfftstoragefield7, FluidRegistry.getFluidStack("molten.epoxid", 144),
        Blockstack(FluidTankBlock, 1, 6),
        200, 1966080);
    //Field Tier 8
    final ItemStack[] tfftstoragefield8 = {
        GT_Utility.getIntegratedCircuit(6),
        ItemList.Field_Generator_ZPM.get(1L),
        ItemList.Electric_Pump_UHV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 4)};
    GT_Values.RA.addAssemblerRecipe(
        tfftstoragefield8, FluidRegistry.getFluidStack("molten.epoxid", 144),
        Blockstack(FluidTankBlock, 1, 7),
        200, 7864320);

    final Object[] tank_hatch = {
        "PRP", "UFU", "PRP",
        'P', GT_OreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Titanium, 1),
        'R', ItemList.Hatch_Output_EV.get(1L),
        'U', ItemList.Hatch_Input_EV.get(1L),
        'F', ItemList.Electric_Pump_HV.get(1L)};
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Tank_Hatch.get(1), tank_hatch);

    GT_ModHandler.addCraftingRecipe(GT_ItemList.ULVConveyorModule.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "MWM", "PPP", 'P', GT_ModHandler.getModItem("minecraft", "leather", 1L, 0), 'M', GT_ItemList.ULVMotor, 'W', OrePrefixes.cableGt01.get(Materials.Lead)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.ULVConveyorModule.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "MWM", "PPP", 'P', OrePrefixes.plate.get(Materials.Rubber), 'M', GT_ItemList.ULVMotor, 'W', OrePrefixes.cableGt01.get(Materials.Lead)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.ULVPump.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMS", 'M', GT_ItemList.ULVMotor, 'O', OrePrefixes.ring.get(Materials.Paper), 'X', OrePrefixes.rotor.get(Materials.Lead), 'S', OrePrefixes.screw.get(Materials.Lead), 'P', OrePrefixes.pipeMedium.get(Materials.Copper)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.ULVPump.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMS", 'M', GT_ItemList.ULVMotor, 'O', OrePrefixes.ring.get(Materials.Rubber), 'X', OrePrefixes.rotor.get(Materials.Lead), 'S', OrePrefixes.screw.get(Materials.Lead), 'P', OrePrefixes.pipeMedium.get(Materials.Copper)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.ULVPump.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMS", 'M', GT_ItemList.ULVMotor, 'O', OrePrefixes.ring.get(Materials.StyreneButadieneRubber), 'X', OrePrefixes.rotor.get(Materials.Lead), 'S', OrePrefixes.screw.get(Materials.Lead), 'P', OrePrefixes.pipeMedium.get(Materials.Copper)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.ULVMotor.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefixes.bolt.get(Materials.IronMagnetic), 'R', OrePrefixes.stick.get(Materials.Iron), 'W', OrePrefixes.wireFine.get(Materials.Tin), 'C', OrePrefixes.cableGt01.get(Materials.Lead)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.ULVRobotArm.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefixes.stick.get(Materials.WroughtIron), 'M', GT_ItemList.ULVMotor, 'P', GT_ItemList.ULVPiston, 'E', OrePrefixes.circuit.get(Materials.Primitive), 'C', OrePrefixes.cableGt01.get(Materials.Lead)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.ULVPiston.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CPP", "dSS", "CMG", 'P', OrePrefixes.plate.get(Materials.WroughtIron), 'S', OrePrefixes.stick.get(Materials.WroughtIron), 'G', OrePrefixes.gearGtSmall.get(Materials.WroughtIron), 'M', GT_ItemList.ULVMotor, 'C', OrePrefixes.screw.get(Materials.WroughtIron)});

    if (GT_Mod.gregtechproxy.mComponentAssembler) {
      GT_ModHandler.addCraftingRecipe(GT_ItemList.Machine_LV_ComponentAssembler.get(1L), bitsd, new Object[]{"PCP", "RHR", "WCW", 'P', GT_ItemList.ULVPiston, 'R', GT_ItemList.ULVRobotArm, 'H', ItemList.Hull_LV, 'C', OrePrefixes.circuit.get(Materials.Basic), 'W', OrePrefixes.cableGt01.get(Materials.Tin)});
      GT_ModHandler.addCraftingRecipe(GT_ItemList.Machine_MV_ComponentAssembler.get(1L), bitsd, new Object[]{"PCP", "RHR", "WCW", 'P', ItemList.Electric_Piston_LV, 'R', ItemList.Robot_Arm_LV, 'H', ItemList.Hull_MV, 'C', OrePrefixes.circuit.get(Materials.Good), 'W', OrePrefixes.cableGt01.get(Materials.Copper)});
      GT_ModHandler.addCraftingRecipe(GT_ItemList.Machine_HV_ComponentAssembler.get(1L), bitsd, new Object[]{"PCP", "RHR", "WCW", 'P', ItemList.Electric_Piston_MV, 'R', ItemList.Robot_Arm_MV, 'H', ItemList.Hull_HV, 'C', OrePrefixes.circuit.get(Materials.Advanced), 'W', OrePrefixes.cableGt02.get(Materials.Gold)});
      GT_ModHandler.addCraftingRecipe(GT_ItemList.Machine_EV_ComponentAssembler.get(1L), bitsd, new Object[]{"PCP", "RHR", "WCW", 'P', ItemList.Electric_Piston_HV, 'R', ItemList.Robot_Arm_HV, 'H', ItemList.Hull_EV, 'C', OrePrefixes.circuit.get(Materials.Data), 'W', OrePrefixes.cableGt02.get(Materials.Aluminium)});
      GT_ModHandler.addCraftingRecipe(GT_ItemList.Machine_IV_ComponentAssembler.get(1L), bitsd, new Object[]{"PCP", "RHR", "WCW", 'P', ItemList.Electric_Piston_EV, 'R', ItemList.Robot_Arm_EV, 'H', ItemList.Hull_IV, 'C', OrePrefixes.circuit.get(Materials.Elite), 'W', OrePrefixes.cableGt04.get(Materials.TungstenSteel)});
    } else {
      //===Motors===\\
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefixes.stick.get(Materials.IronMagnetic), 'R', OrePrefixes.stick.get(Materials.Iron), 'W', OrePrefixes.wireGt01.get(Materials.Copper), 'C', OrePrefixes.cableGt01.get(Materials.Tin)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefixes.stick.get(Materials.SteelMagnetic), 'R', OrePrefixes.stick.get(Materials.Steel), 'W', OrePrefixes.wireGt01.get(Materials.Copper), 'C', OrePrefixes.cableGt01.get(Materials.Tin)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefixes.stick.get(Materials.SteelMagnetic), 'R', OrePrefixes.stick.get(Materials.Aluminium), 'W', OrePrefixes.wireGt02.get(Materials.Cupronickel), 'C', OrePrefixes.cableGt01.get(Materials.Copper)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefixes.stick.get(Materials.SteelMagnetic), 'R', OrePrefixes.stick.get(Materials.StainlessSteel), 'W', OrePrefixes.wireGt02.get(Materials.Electrum), 'C', OrePrefixes.cableGt01.get(Materials.Silver)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefixes.stick.get(Materials.NeodymiumMagnetic), 'R', OrePrefixes.stick.get(Materials.Titanium), 'W', OrePrefixes.wireGt02.get(Materials.AnnealedCopper), 'C', OrePrefixes.cableGt01.get(Materials.Aluminium)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefixes.stick.get(Materials.NeodymiumMagnetic), 'R', OrePrefixes.stick.get(Materials.TungstenSteel), 'W', OrePrefixes.wireGt02.get(Materials.Graphene), 'C', OrePrefixes.cableGt01.get(Materials.Tungsten)});

      //===Pumps===\\
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_LV, 'O', OrePrefixes.ring.get(Materials.Paper), 'X', OrePrefixes.rotor.get(Materials.Tin), 'S', OrePrefixes.screw.get(Materials.Tin), 'W', OrePrefixes.cableGt01.get(Materials.Tin), 'P', OrePrefixes.pipeMedium.get(Materials.Bronze)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_LV, 'O', OrePrefixes.ring.get(Materials.Rubber), 'X', OrePrefixes.rotor.get(Materials.Tin), 'S', OrePrefixes.screw.get(Materials.Tin), 'W', OrePrefixes.cableGt01.get(Materials.Tin), 'P', OrePrefixes.pipeMedium.get(Materials.Bronze)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_LV, 'O', OrePrefixes.ring.get(Materials.StyreneButadieneRubber), 'X', OrePrefixes.rotor.get(Materials.Tin), 'S', OrePrefixes.screw.get(Materials.Tin), 'W', OrePrefixes.cableGt01.get(Materials.Tin), 'P', OrePrefixes.pipeMedium.get(Materials.Bronze)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_LV, 'O', OrePrefixes.ring.get(Materials.Silicone), 'X', OrePrefixes.rotor.get(Materials.Tin), 'S', OrePrefixes.screw.get(Materials.Tin), 'W', OrePrefixes.cableGt01.get(Materials.Tin), 'P', OrePrefixes.pipeMedium.get(Materials.Bronze)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_MV, 'O', OrePrefixes.ring.get(Materials.Rubber), 'X', OrePrefixes.rotor.get(Materials.Bronze), 'S', OrePrefixes.screw.get(Materials.Bronze), 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'P', OrePrefixes.pipeMedium.get(Materials.Steel)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_MV, 'O', OrePrefixes.ring.get(Materials.StyreneButadieneRubber), 'X', OrePrefixes.rotor.get(Materials.Bronze), 'S', OrePrefixes.screw.get(Materials.Bronze), 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'P', OrePrefixes.pipeMedium.get(Materials.Steel)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_MV, 'O', OrePrefixes.ring.get(Materials.Silicone), 'X', OrePrefixes.rotor.get(Materials.Bronze), 'S', OrePrefixes.screw.get(Materials.Bronze), 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'P', OrePrefixes.pipeMedium.get(Materials.Steel)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_HV, 'O', OrePrefixes.ring.get(Materials.Rubber), 'X', OrePrefixes.rotor.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.Steel), 'W', OrePrefixes.cableGt01.get(Materials.Gold), 'P', OrePrefixes.pipeMedium.get(Materials.StainlessSteel)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_HV, 'O', OrePrefixes.ring.get(Materials.StyreneButadieneRubber), 'X', OrePrefixes.rotor.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.Steel), 'W', OrePrefixes.cableGt01.get(Materials.Gold), 'P', OrePrefixes.pipeMedium.get(Materials.StainlessSteel)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_HV, 'O', OrePrefixes.ring.get(Materials.Silicone), 'X', OrePrefixes.rotor.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.Steel), 'W', OrePrefixes.cableGt01.get(Materials.Gold), 'P', OrePrefixes.pipeMedium.get(Materials.StainlessSteel)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_EV, 'O', OrePrefixes.ring.get(Materials.Rubber), 'X', OrePrefixes.rotor.get(Materials.StainlessSteel), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'W', OrePrefixes.cableGt01.get(Materials.Aluminium), 'P', OrePrefixes.pipeMedium.get(Materials.Titanium)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_EV, 'O', OrePrefixes.ring.get(Materials.StyreneButadieneRubber), 'X', OrePrefixes.rotor.get(Materials.StainlessSteel), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'W', OrePrefixes.cableGt01.get(Materials.Aluminium), 'P', OrePrefixes.pipeMedium.get(Materials.Titanium)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_EV, 'O', OrePrefixes.ring.get(Materials.Silicone), 'X', OrePrefixes.rotor.get(Materials.StainlessSteel), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'W', OrePrefixes.cableGt01.get(Materials.Aluminium), 'P', OrePrefixes.pipeMedium.get(Materials.Titanium)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_IV, 'O', OrePrefixes.ring.get(Materials.StyreneButadieneRubber), 'X', OrePrefixes.rotor.get(Materials.TungstenSteel), 'S', OrePrefixes.screw.get(Materials.TungstenSteel), 'W', OrePrefixes.cableGt01.get(Materials.Tungsten), 'P', OrePrefixes.pipeMedium.get(Materials.TungstenSteel)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_IV, 'O', OrePrefixes.ring.get(Materials.Silicone), 'X', OrePrefixes.rotor.get(Materials.TungstenSteel), 'S', OrePrefixes.screw.get(Materials.TungstenSteel), 'W', OrePrefixes.cableGt01.get(Materials.Tungsten), 'P', OrePrefixes.pipeMedium.get(Materials.TungstenSteel)});

      //===Conveyors===\\
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_LV, 'C', OrePrefixes.cableGt01.get(Materials.Tin), 'R', OrePrefixes.plate.get(Materials.Rubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_LV, 'C', OrePrefixes.cableGt01.get(Materials.Tin), 'R', OrePrefixes.plate.get(Materials.StyreneButadieneRubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_LV, 'C', OrePrefixes.cableGt01.get(Materials.Tin), 'R', OrePrefixes.plate.get(Materials.Silicone)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_MV, 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'R', OrePrefixes.plate.get(Materials.Rubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_MV, 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'R', OrePrefixes.plate.get(Materials.StyreneButadieneRubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_MV, 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'R', OrePrefixes.plate.get(Materials.Silicone)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_HV, 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'R', OrePrefixes.plate.get(Materials.Rubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_HV, 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'R', OrePrefixes.plate.get(Materials.StyreneButadieneRubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_HV, 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'R', OrePrefixes.plate.get(Materials.Silicone)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_EV, 'C', OrePrefixes.cableGt01.get(Materials.Aluminium), 'R', OrePrefixes.plate.get(Materials.Rubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_EV, 'C', OrePrefixes.cableGt01.get(Materials.Aluminium), 'R', OrePrefixes.plate.get(Materials.StyreneButadieneRubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_EV, 'C', OrePrefixes.cableGt01.get(Materials.Aluminium), 'R', OrePrefixes.plate.get(Materials.Silicone)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_IV, 'C', OrePrefixes.cableGt01.get(Materials.Tungsten), 'R', OrePrefixes.plate.get(Materials.StyreneButadieneRubber)});
      GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_IV, 'C', OrePrefixes.cableGt01.get(Materials.Tungsten), 'R', OrePrefixes.plate.get(Materials.Silicone)});

      //===Pistons===\\
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefixes.plate.get(Materials.Steel), 'S', OrePrefixes.stick.get(Materials.Steel), 'G', OrePrefixes.gearGtSmall.get(Materials.Steel), 'M', ItemList.Electric_Motor_LV, 'C', OrePrefixes.cableGt01.get(Materials.Tin)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'S', OrePrefixes.stick.get(Materials.Aluminium), 'G', OrePrefixes.gearGtSmall.get(Materials.Aluminium), 'M', ItemList.Electric_Motor_MV, 'C', OrePrefixes.cableGt01.get(Materials.Copper)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'S', OrePrefixes.stick.get(Materials.StainlessSteel), 'G', OrePrefixes.gearGtSmall.get(Materials.StainlessSteel), 'M', ItemList.Electric_Motor_HV, 'C', OrePrefixes.cableGt01.get(Materials.Gold)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefixes.plate.get(Materials.Titanium), 'S', OrePrefixes.stick.get(Materials.Titanium), 'G', OrePrefixes.gearGtSmall.get(Materials.Titanium), 'M', ItemList.Electric_Motor_EV, 'C', OrePrefixes.cableGt01.get(Materials.Aluminium)});
      GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefixes.plate.get(Materials.TungstenSteel), 'S', OrePrefixes.stick.get(Materials.TungstenSteel), 'G', OrePrefixes.gearGtSmall.get(Materials.TungstenSteel), 'M', ItemList.Electric_Motor_IV, 'C', OrePrefixes.cableGt01.get(Materials.Tungsten)});

      //===RobotArms===\\
      GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefixes.stick.get(Materials.Steel), 'M', ItemList.Electric_Motor_LV, 'P', ItemList.Electric_Piston_LV, 'E', OrePrefixes.circuit.get(Materials.Basic), 'C', OrePrefixes.cableGt01.get(Materials.Tin)});
      GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefixes.stick.get(Materials.Aluminium), 'M', ItemList.Electric_Motor_MV, 'P', ItemList.Electric_Piston_MV, 'E', OrePrefixes.circuit.get(Materials.Good), 'C', OrePrefixes.cableGt01.get(Materials.Copper)});
      GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefixes.stick.get(Materials.StainlessSteel), 'M', ItemList.Electric_Motor_HV, 'P', ItemList.Electric_Piston_HV, 'E', OrePrefixes.circuit.get(Materials.Advanced), 'C', OrePrefixes.cableGt01.get(Materials.Gold)});
      GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefixes.stick.get(Materials.Titanium), 'M', ItemList.Electric_Motor_EV, 'P', ItemList.Electric_Piston_EV, 'E', OrePrefixes.circuit.get(Materials.Data), 'C', OrePrefixes.cableGt01.get(Materials.Aluminium)});
      GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefixes.stick.get(Materials.TungstenSteel), 'M', ItemList.Electric_Motor_IV, 'P', ItemList.Electric_Piston_IV, 'E', OrePrefixes.circuit.get(Materials.Elite), 'C', OrePrefixes.cableGt01.get(Materials.Tungsten)});

      //===Emitters===\\
      GT_ModHandler.addCraftingRecipe(ItemList.Emitter_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefixes.gem.get(Materials.CertusQuartz), 'S', OrePrefixes.stick.get(Materials.Brass), 'C', OrePrefixes.circuit.get(Materials.Basic), 'W', OrePrefixes.cableGt01.get(Materials.Tin)});
      GT_ModHandler.addCraftingRecipe(ItemList.Emitter_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefixes.gem.get(Materials.Quartzite), 'S', OrePrefixes.stick.get(Materials.Brass), 'C', OrePrefixes.circuit.get(Materials.Basic), 'W', OrePrefixes.cableGt01.get(Materials.Tin)});
      GT_ModHandler.addCraftingRecipe(ItemList.Emitter_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefixes.gem.get(Materials.NetherQuartz), 'S', OrePrefixes.stick.get(Materials.Electrum), 'C', OrePrefixes.circuit.get(Materials.Good), 'W', OrePrefixes.cableGt01.get(Materials.Copper)});
      GT_ModHandler.addCraftingRecipe(ItemList.Emitter_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefixes.gemFlawed.get(Materials.Emerald), 'S', OrePrefixes.stick.get(Materials.Chrome), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'W', OrePrefixes.cableGt01.get(Materials.Gold)});
      GT_ModHandler.addCraftingRecipe(ItemList.Emitter_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', CoreItems2.getRecipe(ChargedGlassLense.getMetaID(), 1), 'S', OrePrefixes.stick.get(Materials.Platinum), 'C', OrePrefixes.circuit.get(Materials.Data), 'W', OrePrefixes.cableGt01.get(Materials.Aluminium)});
      GT_ModHandler.addCraftingRecipe(ItemList.Emitter_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', CoreItems2.getRecipe(HugeChargedGlassLense.getMetaID(), 1), 'S', OrePrefixes.stick.get(Materials.Iridium), 'C', OrePrefixes.circuit.get(Materials.Elite), 'W', OrePrefixes.cableGt01.get(Materials.Tungsten)});

      //===Sensors===\\
      GT_ModHandler.addCraftingRecipe(ItemList.Sensor_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefixes.gem.get(Materials.CertusQuartz), 'S', OrePrefixes.stick.get(Materials.Brass), 'P', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.circuit.get(Materials.Basic)});
      GT_ModHandler.addCraftingRecipe(ItemList.Sensor_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefixes.gem.get(Materials.Quartzite), 'S', OrePrefixes.stick.get(Materials.Brass), 'P', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.circuit.get(Materials.Basic)});
      GT_ModHandler.addCraftingRecipe(ItemList.Sensor_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefixes.gem.get(Materials.NetherQuartz), 'S', OrePrefixes.stick.get(Materials.Electrum), 'P', OrePrefixes.plate.get(Materials.Aluminium), 'C', OrePrefixes.circuit.get(Materials.Good)});
      GT_ModHandler.addCraftingRecipe(ItemList.Sensor_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefixes.gemFlawed.get(Materials.Emerald), 'S', OrePrefixes.stick.get(Materials.Chrome), 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'C', OrePrefixes.circuit.get(Materials.Advanced)});
      GT_ModHandler.addCraftingRecipe(ItemList.Sensor_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', CoreItems2.getRecipe(ChargedGlassLense.getMetaID(), 1), 'S', OrePrefixes.stick.get(Materials.Platinum), 'P', OrePrefixes.plate.get(Materials.Titanium), 'C', OrePrefixes.circuit.get(Materials.Data)});
      GT_ModHandler.addCraftingRecipe(ItemList.Sensor_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', CoreItems2.getRecipe(HugeChargedGlassLense.getMetaID(), 1), 'S', OrePrefixes.stick.get(Materials.Iridium), 'P', OrePrefixes.plate.get(Materials.TungstenSteel), 'C', OrePrefixes.circuit.get(Materials.Elite)});

      //===FieldGenerators===\\
      GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', ItemList.Emitter_LV, 'C', OrePrefixes.circuit.get(Materials.Good), 'W', OrePrefixes.plate.get(Materials.RedSteel)});
      GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', ItemList.Emitter_MV, 'C', OrePrefixes.circuit.get(Materials.Advanced), 'W', OrePrefixes.plate.get(Materials.BT6)});
      GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', ItemList.Emitter_HV, 'C', OrePrefixes.circuit.get(Materials.Data), 'W', OrePrefixes.plateDouble.get(Materials.HSSG)});
      GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', ItemList.Emitter_EV, 'C', OrePrefixes.circuit.get(Materials.Elite), 'W', OrePrefixes.plateDouble.get(Materials.NiobiumTitanium)});
      GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', ItemList.Emitter_IV, 'C', OrePrefixes.circuit.get(Materials.Master), 'W', OrePrefixes.plateTriple.get(Materials.HSSS)});
    }

    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Semi_Turbine_ULV.get(1L), bitsd, new Object[]{"GCG", "EME", "PWP", 'M', ItemList.Hull_ULV, 'P', GT_ItemList.ULVPiston, 'E', GT_ItemList.ULVMotor, 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OrePrefixes.cableGt01.get(Materials.Lead), 'G', OrePrefixes.gearGt.get(Materials.WroughtIron)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Steam_Turbine_ULV.get(1L), bitsd, new Object[]{"PCP", "RHR", "EWE", 'H', ItemList.Hull_ULV, 'E', GT_ItemList.ULVMotor, 'R', OrePrefixes.rotor.get(Materials.Lead), 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OrePrefixes.cableGt01.get(Materials.Lead), 'P', OrePrefixes.pipeSmall.get(Materials.Lead)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Diesel_ULV.get(1L), bitsd, new Object[]{"PCP", "EME", "GWG", 'M', ItemList.Hull_ULV, 'P', GT_ItemList.ULVPiston, 'E', GT_ItemList.ULVMotor, 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OrePrefixes.cableGt01.get(Materials.Lead), 'G', OrePrefixes.gearGt.get(Materials.WroughtIron)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Gas_Turbine_ULV.get(1L), bitsd, new Object[]{"CRC", "RHR", "EWE", 'H', ItemList.Hull_ULV, 'E', GT_ItemList.ULVMotor, 'R', OrePrefixes.rotor.get(Materials.Lead), 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OrePrefixes.cableGt01.get(Materials.Lead)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Machine_ULV_Assembler.get(1L), bitsd, new Object[]{"RCR", "OMO", "WCW", 'M', ItemList.Hull_ULV, 'R', GT_ItemList.ULVRobotArm, 'O', GT_ItemList.ULVConveyorModule, 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OrePrefixes.cableGt01.get(Materials.Lead)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_CokeOvenBrick.get(1L), bits, new Object[]{"BB", "BB", 'B', CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 1)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Hatch_Output_Prim.get(1L), bitsd, new Object[]{"BBB", "B B", "BEB", 'B', CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 1), 'E', new ItemStack(Items.bucket, 1, 0)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Bus_Input_Prim.get(1L), bitsd, new Object[]{"BEB", "B B", "BBB", 'B', CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 1), 'E', new ItemStack(Blocks.hopper, 1, 32767)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Bus_Output_Prim.get(1L), bitsd, new Object[]{"BBB", "B B", "BEB", 'B', CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 1), 'E', new ItemStack(Blocks.hopper, 1, 32767)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Machine_CokeOven.get(1L), bitsd, new Object[]{"BBB", "BFB", "BBB", 'B', CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 1), 'F', OreDictNames.craftingFurnace});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Semi_Turbine_LV.get(1L), bitsd, new Object[]{"GCG", "EME", "PWP", 'M', ItemList.Hull_LV, 'P', ItemList.Electric_Piston_LV, 'E', ItemList.Electric_Motor_LV, 'C', OrePrefixes.circuit.get(Materials.Basic), 'W', OrePrefixes.cableGt01.get(Materials.Tin), 'G', OrePrefixes.gearGt.get(Materials.Steel)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Semi_Turbine_MV.get(1L), bitsd, new Object[]{"GCG", "EME", "PWP", 'M', ItemList.Hull_MV, 'P', ItemList.Electric_Piston_MV, 'E', ItemList.Electric_Motor_MV, 'C', OrePrefixes.circuit.get(Materials.Good), 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'G', OrePrefixes.gearGt.get(Materials.Aluminium)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Semi_Turbine_HV.get(1L), bitsd, new Object[]{"GCG", "EME", "PWP", 'M', ItemList.Hull_HV, 'P', ItemList.Electric_Piston_HV, 'E', ItemList.Electric_Motor_HV, 'C', OrePrefixes.circuit.get(Materials.Advanced), 'W', OrePrefixes.cableGt01.get(Materials.Gold), 'G', OrePrefixes.gearGt.get(Materials.StainlessSteel)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Machine_Multi_Farm.get(1L), bitsd, new Object[]{"ROR", "CHC", "PWP", 'H', ItemList.Hull_MV, 'W', OrePrefixes.cableGt02.get(Materials.Copper), 'C', OrePrefixes.circuit.get(Materials.Good), 'P', ItemList.Electric_Pump_MV, 'R', ItemList.Robot_Arm_MV, 'O', OreDictNames.craftingDiamondBlade});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Semi_Turbine_EV.get(1L), bitsd, new Object[]{"GCG", "EME", "PWP", 'M', ItemList.Hull_EV, 'P', ItemList.Electric_Piston_EV, 'E', ItemList.Electric_Motor_EV, 'C', OrePrefixes.circuit.get(Materials.Data), 'W', OrePrefixes.cableGt01.get(Materials.Aluminium), 'G', OrePrefixes.gearGt.get(Materials.Titanium)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Steam_Turbine_EV.get(1L), bitsd, new Object[]{"PCP", "RHR", "EWE", 'H', ItemList.Hull_EV, 'E', ItemList.Electric_Motor_EV, 'R', OrePrefixes.rotor.get(Materials.StainlessSteel), 'C', OrePrefixes.circuit.get(Materials.Data), 'W', OrePrefixes.cableGt01.get(Materials.Aluminium), 'P', OrePrefixes.pipeLarge.get(Materials.Titanium)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Generator_Diesel_EV.get(1L), bitsd, new Object[]{"PCP", "EME", "GWG", 'M', ItemList.Hull_EV, 'P', ItemList.Electric_Piston_EV, 'E', ItemList.Electric_Motor_EV, 'C', OrePrefixes.circuit.get(Materials.Data), 'W', OrePrefixes.cableGt01.get(Materials.Aluminium), 'G', OrePrefixes.gearGt.get(Materials.Titanium)});

    //=== Super / Quant tanks ===//
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Tank_ULV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', GT_ItemList.ULVPump, 'M', ItemList.Casing_Tank_0, 'G', OrePrefixes.pipeMedium.get(Materials.Bronze), 'D', OrePrefixes.circuit.get(Materials.Primitive), 'P', OrePrefixes.plate.get(Materials.Steel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Tank_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_LV, 'M', ItemList.Casing_Tank_1, 'G', OrePrefixes.pipeLarge.get(Materials.Bronze), 'D', OrePrefixes.circuit.get(Materials.Basic), 'P', OrePrefixes.plate.get(Materials.Aluminium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Tank_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_MV, 'M', ItemList.Casing_Tank_2, 'G', OrePrefixes.pipeLarge.get(Materials.Steel), 'D', OrePrefixes.circuit.get(Materials.Good), 'P', OrePrefixes.plate.get(Materials.StainlessSteel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Tank_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_HV, 'M', ItemList.Casing_Tank_3, 'G', ItemList.Field_Generator_LV, 'D', OrePrefixes.circuit.get(Materials.Advanced), 'P', OrePrefixes.plate.get(Materials.StainlessSteel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Tank_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_EV, 'M', ItemList.Casing_Tank_4, 'G', ItemList.Field_Generator_MV, 'D', OrePrefixes.circuit.get(Materials.Data), 'P', OrePrefixes.plate.get(Materials.Titanium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Tank_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_IV, 'M', ItemList.Casing_Tank_5, 'G', ItemList.Field_Generator_HV, 'D', OrePrefixes.circuit.get(Materials.Elite), 'P', OrePrefixes.plate.get(Materials.Titanium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Tank_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_IV, 'M', ItemList.Casing_Tank_6, 'G', ItemList.Field_Generator_EV, 'D', OrePrefixes.circuit.get(Materials.Master), 'P', OrePrefixes.plate.get(Materials.TungstenSteel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Tank_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_IV, 'M', ItemList.Casing_Tank_7, 'G', ItemList.Field_Generator_IV, 'D', OrePrefixes.circuit.get(Materials.Ultimate), 'P', OrePrefixes.plate.get(Materials.HSSG)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Tank_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_LuV, 'M', ItemList.Casing_Tank_8, 'G', ItemList.Field_Generator_LuV, 'D', OrePrefixes.circuit.get(Materials.Superconductor), 'P', OrePrefixes.plate.get(Materials.HSSS)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Tank_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_ZPM, 'M', ItemList.Casing_Tank_9, 'G', ItemList.Field_Generator_ZPM, 'D', OrePrefixes.circuit.get(Materials.Infinite), 'P', OrePrefixes.plate.get(Materials.Europium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Tank_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_UV, 'M', ItemList.Casing_Tank_10, 'G', ItemList.Field_Generator_UV, 'D', OrePrefixes.circuit.get(Materials.Bio), 'P', OrePrefixes.plate.get(Materials.Americium)});

    //=== Super / Quant tanks casing ===//
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_0.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.WroughtIron), 'I', OrePrefixes.pipeLarge.get(Materials.Bronze)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_1.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Steel), 'I', OrePrefixes.pipeLarge.get(Materials.Steel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_2.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'I', OrePrefixes.pipeLarge.get(Materials.Plastic)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_3.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'I', OrePrefixes.pipeLarge.get(Materials.Polytetrafluoroethylene)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_4.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Titanium), 'I', OrePrefixes.pipeLarge.get(Materials.StainlessSteel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_5.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.TungstenSteel), 'I', OrePrefixes.pipeLarge.get(Materials.Titanium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_6.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Chrome), 'I', OrePrefixes.pipeLarge.get(Materials.TungstenSteel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_7.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Iridium), 'I', OrePrefixes.pipeLarge.get(Materials.NiobiumTitanium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_8.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Osmium), 'I', OrePrefixes.pipeLarge.get(Materials.Enderium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_9.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Tritanium), 'I', OrePrefixes.pipeLarge.get(Materials.Naquadah)});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Tank_10.get(1L), bitsd, new Object[]{"PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Neutronium), 'I', OrePrefixes.pipeLarge.get(Materials.NetherStar)});

    //=== Super / Quant chests ===//
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Chest_LV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_LV, 'M', ItemList.Conveyor_Module_MV, 'G', OrePrefixes.pipeLarge.get(Materials.Brass), 'D', OrePrefixes.circuit.get(Materials.Basic), 'P', OrePrefixes.plateDense.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Chest_MV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_MV, 'M', ItemList.Conveyor_Module_HV, 'G', OrePrefixes.pipeLarge.get(Materials.Aluminium), 'D', OrePrefixes.circuit.get(Materials.Good), 'P', OrePrefixes.plateDense.get(Materials.Steel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Chest_HV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_HV, 'M', ItemList.Conveyor_Module_HV, 'G', ItemList.Field_Generator_LV, 'D', OrePrefixes.circuit.get(Materials.Advanced), 'P', OrePrefixes.plateQuintuple.get(Materials.Aluminium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Chest_EV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_EV, 'M', ItemList.Conveyor_Module_EV, 'G', ItemList.Field_Generator_MV, 'D', OrePrefixes.circuit.get(Materials.Data), 'P', OrePrefixes.plateQuintuple.get(Materials.StainlessSteel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Super_Chest_IV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_IV, 'M', ItemList.Conveyor_Module_EV, 'G', ItemList.Field_Generator_HV, 'D', OrePrefixes.circuit.get(Materials.Elite), 'P', OrePrefixes.plateQuadruple.get(Materials.Titanium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Chest_LV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_LuV, 'M', ItemList.Conveyor_Module_IV, 'G', ItemList.Field_Generator_EV, 'D', OrePrefixes.circuit.get(Materials.Master), 'P', OrePrefixes.plateQuadruple.get(Materials.TungstenSteel)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Chest_MV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_ZPM, 'M', ItemList.Conveyor_Module_IV, 'G', ItemList.Field_Generator_IV, 'D', OrePrefixes.circuit.get(Materials.Ultimate), 'P', OrePrefixes.plateTriple.get(Materials.HSSG)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Chest_HV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_UV, 'M', ItemList.Conveyor_Module_LuV, 'G', ItemList.Field_Generator_LuV, 'D', OrePrefixes.circuit.get(Materials.Superconductor), 'P', OrePrefixes.plateTriple.get(Materials.HSSS)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Chest_EV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_MAX, 'M', ItemList.Conveyor_Module_ZPM, 'G', ItemList.Field_Generator_ZPM, 'D', OrePrefixes.circuit.get(Materials.Infinite), 'P', OrePrefixes.plateDouble.get(Materials.Europium)});
    GT_ModHandler.addCraftingRecipe(ItemList.Quantum_Chest_IV.get(1L), bitsd, new Object[]{"DMD", "PCP", "DGD", 'C', ItemList.Hull_UEV, 'M', ItemList.Conveyor_Module_UV, 'G', ItemList.Field_Generator_UV, 'D', OrePrefixes.circuit.get(Materials.Bio), 'P', OrePrefixes.plate.get(Materials.Americium)});

    //        //Chests - отключены из-за бага
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "WroughtIronChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.WroughtIron), 'R', OrePrefixes.ring.get(Materials.WroughtIron), 'C', ItemList.Casing_Tank_0});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "SteelChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.Steel), 'R', OrePrefixes.ring.get(Materials.Steel), 'C', ItemList.Casing_Tank_1});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "AlChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'R', OrePrefixes.ring.get(Materials.Aluminium), 'C', ItemList.Casing_Tank_2});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "HSLAChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.HSLA), 'R', OrePrefixes.ring.get(Materials.HSLA), 'C', ItemList.Casing_Tank_3});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "TitaniumChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.Titanium), 'R', OrePrefixes.ring.get(Materials.Titanium), 'C', ItemList.Casing_Tank_4});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "TungstenSteelChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.TungstenSteel), 'R', OrePrefixes.ring.get(Materials.TungstenSteel), 'C', ItemList.Casing_Tank_5});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "ChromeChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.Chrome), 'R', OrePrefixes.ring.get(Materials.Chrome), 'C', ItemList.Casing_Tank_6});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "IridiumChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.Iridium), 'R', OrePrefixes.ring.get(Materials.Iridium), 'C', ItemList.Casing_Tank_7});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "OsmiumChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.Osmium), 'R', OrePrefixes.ring.get(Materials.Osmium), 'C', ItemList.Casing_Tank_8});
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "NeutroniumChest", 1L, 0), bits, new Object[]{"PPP", "RCR", "PPP", 'P', OrePrefixes.plate.get(Materials.Neutronium), 'R', OrePrefixes.ring.get(Materials.Neutronium), 'C', ItemList.Casing_Tank_9});

    //Portable Tank
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Portable_Tank_ULV.get(1L), bitsd, new Object[]{"SPS", "PTP", "hPC", 'T', ItemList.Large_Fluid_Cell_Steel.get(1L), 'P', OrePrefixes.plateDouble.get(Materials.Lead), 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'C', ToolDictNames.craftingToolScrewdriver});

    //Potin Alloy
    GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Potin, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Tin), OrePrefixes.dust.get(Materials.Tin), OrePrefixes.dust.get(Materials.Lead)});

    //Wooden Brick Form
    GT_ModHandler.addShapelessCraftingRecipe(WoodForm.getRecipe(1), tBitMask, new Object[]{ToolDictNames.craftingToolKnife, GT_ModHandler.getModItem("TConstruct", "blankPattern", 1L, 0)});

    //Bricks
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(UnfiredClayBrick.getMetaID(), 8), tBitMask, new Object[]{"CCC", "CFC", "CCC", 'C', new ItemStack(Items.clay_ball, 1, 0), 'F', WoodForm.get()});
    GT_ModHandler.addShapelessCraftingRecipe(CoreItems2.getRecipe(UnfiredClayBrick.getMetaID(), 1), tBitMask, new Object[]{new ItemStack(Items.clay_ball, 1, 0), WoodForm.get()});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(UnfiredSearedBrick.getMetaID(), 8), tBitMask, new Object[]{"GGG", "GFG", "GGG", 'G', GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1), 'F', WoodForm.get()});
    GT_ModHandler.addShapelessCraftingRecipe(CoreItems2.getRecipe(UnfiredSearedBrick.getMetaID(), 1), tBitMask, new Object[]{GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1), WoodForm.get()});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(UnfiredCokeOvenBrick.getMetaID(), 3), tBitMask, new Object[]{"CCC", "SFS", "SSS", 'C', new ItemStack(Items.clay_ball, 1, 0), 'S', new ItemStack(Blocks.sand, 1, 0), 'F', WoodForm.get()});
    GT_ModHandler.addShapelessCraftingRecipe(CoreItems2.getRecipe(UnfiredCokeOvenBrick.getMetaID(), 1), tBitMask, new Object[]{new ItemStack(Items.clay_ball, 1, 0), new ItemStack(Blocks.sand, 1, 0), OrePrefixes.block.get(Materials.Sand), WoodForm.get()});

    GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("minecraft", "clay_ball", 1L, 0));
    GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1));

    GT_ModHandler.addSmeltingRecipe(CoreItems2.getRecipe(UnfiredClayBrick.getMetaID(), 1), GT_ModHandler.getModItem("minecraft", "brick", 1L, 0));
    GT_ModHandler.addSmeltingRecipe(CoreItems2.getRecipe(UnfiredSearedBrick.getMetaID(), 1), GT_ModHandler.getModItem("TConstruct", "materials", 1L, 2));
    GT_ModHandler.addSmeltingRecipe(CoreItems2.getRecipe(UnfiredCokeOvenBrick.getMetaID(), 1), CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 1));

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(SteelBars.getMetaID(), 3), tBitMask, new Object[]{" h ", "SSS", "SSS", 'S', OrePrefixes.stick.get(Materials.Steel)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Casing_Farm.get(1L), tBitMask, new Object[]{"ThT", "TFT", "TwT", 'T', CoreItems2.getRecipe(SteelBars.getMetaID(), 1), 'F', OrePrefixes.frameGt.get(Materials.Steel)});
    GT_ModHandler.addCraftingRecipe(CoreItems.getRecipe(DustSmallFertilizer.getMetaID(), 4), tBitMask, new Object[]{" D", "  ", 'D', GT_ModHandler.getModItem("IC2", "itemFertilizer", 1L, 0)});
    GT_ModHandler.addCraftingRecipe(CoreItems.getRecipe(DustTinyFertilizer.getMetaID(), 9), tBitMask, new Object[]{"D ", "  ", 'D', GT_ModHandler.getModItem("IC2", "itemFertilizer", 1L, 0)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemFertilizer", 1L, 0), tBitMask, new Object[]{"DD", "DD", 'D', CoreItems.getRecipe(DustSmallFertilizer.getMetaID(), 4)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemFertilizer", 1L, 0), tBitMask, new Object[]{"DDD", "DDD", "DDD", 'D', CoreItems.getRecipe(DustTinyFertilizer.getMetaID(), 9)});

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(UnfiredClayBrick.getMetaID(), 8), tBitMask, new Object[]{"CCC", "CFC", "CCC", ('C'), new ItemStack(Items.clay_ball, 1, 0), ('F'), new ItemStack(WoodBrickFormTool.WoodBrickFormTool, 1, OreDictionary.WILDCARD_VALUE)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(UnfiredSearedBrick.getMetaID(), 8), tBitMask, new Object[]{"CCC", "CFC", "CCC", ('C'), GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1), ('F'), new ItemStack(WoodBrickFormTool.WoodBrickFormTool, 1, OreDictionary.WILDCARD_VALUE)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(UnfiredCokeOvenBrick.getMetaID(), 3), tBitMask, new Object[]{"CCC", "SFS", "SSS", ('S'), GT_ModHandler.getModItem("minecraft", "sand", 1L, 0), ('C'), new ItemStack(Items.clay_ball, 1, 0), ('F'), new ItemStack(WoodBrickFormTool.WoodBrickFormTool, 1, OreDictionary.WILDCARD_VALUE)});
    GT_ModHandler.addCraftingRecipe(ItemList.CompressedFireclay.get(8), tBitMask, new Object[]{"CCC", "TFT", "CCC", 'C', OrePrefixes.dust.get(Materials.Clay), 'T', CoreItems.getRecipe(ConcreteDust.getMetaID(), 1), ('F'), new ItemStack(WoodBrickFormTool.WoodBrickFormTool, 1, OreDictionary.WILDCARD_VALUE)});

    GT_ModHandler.addShapelessCraftingRecipe(CoreItems.getRecipe(37, 4), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Stone), OrePrefixes.dust.get(Materials.Stone), OrePrefixes.dust.get(Materials.Gypsum), OrePrefixes.dust.get(Materials.Calcite)});
    GT_ModHandler.addShapelessCraftingRecipe(CoreItems.getRecipe(37, 4), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Stone), OrePrefixes.dust.get(Materials.Stone), OrePrefixes.dust.get(Materials.Gypsum), OrePrefixes.dust.get(Materials.Marble)});
    GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Fireclay, 4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Clay), OrePrefixes.dust.get(Materials.Clay), OrePrefixes.dust.get(Materials.Clay), CoreItems.getRecipe(37, 1)});

    //FurnaceRecipes
    GT_ModHandler.addSmeltingRecipe(CoreItems2.getRecipe(UnfiredClayBrick.getMetaID(), 1), new ItemStack(Items.brick, 1, 0));
    GT_ModHandler.addSmeltingRecipe(CoreItems2.getRecipe(UnfiredSearedBrick.getMetaID(), 1), GT_ModHandler.getModItem("TConstruct", "materials", 1L, 2));
    GT_ModHandler.addSmeltingRecipe(CoreItems2.getRecipe(UnfiredCokeOvenBrick.getMetaID(), 1), CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 1));

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(98, 1), tBitMask, new Object[]{"PRP", "ICI", "PMP", 'P', CoreItems2.getRecipe(84, 1), 'R', OrePrefixes.rotor.get(Materials.Iridium), 'M', ItemList.Electric_Motor_ZPM, 'I', ItemList.Neutron_Reflector, 'C', ItemList.Reactor_Coolant_Le_3});

    //IC2 Batteries
    GT_ModHandler.addShapelessCraftingRecipe(ItemList.EnergyCrystal.get(1), tBitMask, new Object[]{ItemList.IC2_EnergyCrystal.get(1, GT_Values.W)});
    GT_ModHandler.addShapelessCraftingRecipe(ItemList.LapotronCrystal.get(1), tBitMask, new Object[]{ItemList.IC2_LapotronCrystal.get(1, GT_Values.W)});

    //Rods
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(177,1), tBitMask, new Object[]{"f ", " S", 'S', new ItemStack(Blocks.cobblestone)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(178,1), tBitMask, new Object[]{"f ", " S", 'S', new ItemStack(Blocks.sandstone)});

    //GT Wood Ring
    GT_ModHandler.addCraftingRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Wood, 4L),
        new Object[]{
            "k ",
            " S",
            'S', GT_OreDictUnificator.get(OrePrefixes.plank, Materials.Wood, 1L)
        });
    //Stone Dust
    GT_ModHandler.addShapelessCraftingRecipe(
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L),
            new Object[]{"stone", ToolDictNames.craftingToolMortar
            });
    //Filter Casing
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Vent.get(6L), bits, new Object[]{"PPP", "SFS", "MIV", 'P', CoreItems2.getRecipe(SteelBars.getMetaID(), 1), 'I', OrePrefixes.stick.get(Materials.StainlessSteel), 'F', OrePrefixes.frameGt.get(Materials.VanadiumSteel), 'M', ItemList.Electric_Motor_HV, 'V', OrePrefixes.rotor.get(Materials.BlackSteel), 'S', ItemList.Component_Filter});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Vent.get(24L), bits, new Object[]{"PSP", "GFR", "MIV", 'P', CoreItems2.getRecipe(SteelBars.getMetaID(), 1), 'G', OrePrefixes.gearGtSmall.get(Materials.Titanium), 'R', OrePrefixes.ring.get(Materials.Titanium), 'I', OrePrefixes.stickLong.get(Materials.Titanium), 'F', OrePrefixes.frameGt.get(Materials.Titanium), 'M', ItemList.Electric_Motor_EV, 'V', OrePrefixes.rotor.get(Materials.HSLA), 'S', ItemList.Component_Filter});
    GT_ModHandler.addCraftingRecipe(ItemList.Casing_Vent.get(64L), bits, new Object[]{"PSP", "GFR", "MIV", 'P', GT_ModHandler.getModItem("EnderIO", "blockDarkIronBars", 1L), 'G', OrePrefixes.gearGtSmall.get(Materials.TungstenSteel), 'R', OrePrefixes.ring.get(Materials.TungstenSteel), 'I', OrePrefixes.stickLong.get(Materials.TungstenSteel), 'F', OrePrefixes.frameGt.get(Materials.MaragingSteel250), 'M', ItemList.Electric_Motor_IV, 'V', OrePrefixes.rotor.get(Materials.HastelloyC276), 'S', ItemList.Component_Filter});


    /**====START SOLAR COVER====*/
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(109, 1), tBitMask, new Object[]{"PAP", "ACA", "PAP", 'C', CoreItems2.getRecipe(101, 1), 'A', OrePrefixes.plateAlloy.get(Materials.Carbon), 'P', OrePrefixes.plateAlloy.get(Materials.Advanced)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(110, 1), tBitMask, new Object[]{"PAP", "ACA", "PAP", 'C', CoreItems2.getRecipe(102, 1), 'A', OrePrefixes.plate.get(Materials.Silicon), 'P', OrePrefixes.plateAlloy.get(Materials.Advanced)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(111, 1), tBitMask, new Object[]{"PAP", "ACA", "PAP", 'C', CoreItems2.getRecipe(103, 1), 'A', OrePrefixes.plate.get(Materials.Tungsten), 'P', OrePrefixes.plateAlloy.get(Materials.Advanced)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(112, 1), tBitMask, new Object[]{"PAP", "ACA", "PAP", 'C', CoreItems2.getRecipe(104, 1), 'A', OrePrefixes.plate.get(Materials.TungstenSteel), 'P', OrePrefixes.plateAlloy.get(Materials.Advanced)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(113, 1), tBitMask, new Object[]{"PAP", "ACA", "PAP", 'C', CoreItems2.getRecipe(105, 1), 'A', OrePrefixes.plate.get(Materials.Diamond), 'P', OrePrefixes.plateAlloy.get(Materials.Advanced)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(114, 1), tBitMask, new Object[]{"PAP", "ACA", "PAP", 'C', CoreItems2.getRecipe(106, 1), 'A', OrePrefixes.plateAlloy.get(Materials.Iridium), 'P', OrePrefixes.plateAlloy.get(Materials.Advanced)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(115, 1), tBitMask, new Object[]{"PAP", "ACA", "PAP", 'C', CoreItems2.getRecipe(107, 1), 'A', OrePrefixes.plate.get(Materials.MysteriousCrystal), 'P', OrePrefixes.plateAlloy.get(Materials.Advanced)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(116, 1), tBitMask, new Object[]{"PAP", "ACA", "PAP", 'C', CoreItems2.getRecipe(108, 1), 'A', OrePrefixes.plate.get(Materials.BlackPlutonium), 'P', OrePrefixes.plateAlloy.get(Materials.Advanced)});

    GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel.get(1L), tBitMask, new Object[]{"SGS", "CPC", "TRT", 'C', OrePrefixes.circuit.get(Materials.Basic), 'G', GT_ModHandler.getIC2Item("reinforcedGlass", 1L), 'P', OrePrefixes.plateAlloy.get(Materials.Carbon), 'S', ItemList.Circuit_Silicon_Wafer, 'T', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'R', CoreItems2.getRecipe(101, 1)});
    GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_8V.get(1L), tBitMask, new Object[]{"GRG", "CAC", "GRG", 'C', OrePrefixes.circuit.get(Materials.Good), 'G', ItemList.Cover_SolarPanel, 'R', OrePrefixes.plate.get(Materials.GalliumArsenide), 'A', CoreItems2.getRecipe(109, 1)});
    GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_LV.get(1L), tBitMask, new Object[]{"GRG", "CAC", "GRG", 'C', OrePrefixes.circuit.get(Materials.Good), 'G', ItemList.Cover_SolarPanel_8V, 'R', OrePrefixes.plate.get(Materials.Plastic), 'A', OrePrefixes.plateDouble.get(Materials.HSLA)});
    GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_MV.get(1L), tBitMask, new Object[]{"GRG", "CAC", "GRG", 'C', OrePrefixes.circuit.get(Materials.Advanced), 'G', ItemList.Cover_SolarPanel_LV, 'R', OrePrefixes.plate.get(Materials.Polytetrafluoroethylene), 'A', CoreItems2.getRecipe(110, 1)});
    GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_HV.get(1L), tBitMask, new Object[]{"GRG", "CAC", "GRG", 'C', OrePrefixes.circuit.get(Materials.Data), 'G', ItemList.Cover_SolarPanel_MV, 'R', OrePrefixes.plate.get(Materials.EpoxidFiberReinforced), 'A', CoreItems2.getRecipe(111, 1)});

    /*====END SOLAR COVER====*/
    /**====START SPACE ADDITION====*/
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(97, 1), tBitMask, new Object[]{"SdS", "BTA", "ShS", 'T', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'B', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedDualBronze", 1L, 0), 'A', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedDualAluminium", 1L, 0)});

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyNoseConeTier4.getMetaID(), 1), tBitMask, new Object[]{"dNh", "SPS", "PAP", 'N', GT_ModHandler.getModItem("GalacticraftMars", "item.heavyNoseCone", 1L), 'S', OrePrefixes.screw.get(Materials.Osmiridium), 'P', CoreItems2.getRecipe(80, 1), 'A', CoreItems2.getRecipe(11, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyNoseConeTier5.getMetaID(), 1), tBitMask, new Object[]{"dNh", "SPS", "PAP", 'N', CoreItems2.getRecipe(HeavyDutyNoseConeTier4.getMetaID(), 1), 'S', OrePrefixes.screw.get(Materials.Duranium), 'P', OrePrefixes.plateTriple.get(Materials.NaquadahAlloy), 'A', CoreItems2.getRecipe(12, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyNoseConeTier6.getMetaID(), 1), tBitMask, new Object[]{"dNh", "SPS", "PAP", 'N', CoreItems2.getRecipe(HeavyDutyNoseConeTier5.getMetaID(), 1), 'S', OrePrefixes.screw.get(Materials.Tritanium), 'P', CoreItems2.getRecipe(83, 1), 'A', CoreItems2.getRecipe(13, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyNoseConeTier7.getMetaID(), 1), tBitMask, new Object[]{"dNh", "SPS", "PAP", 'N', CoreItems2.getRecipe(HeavyDutyNoseConeTier6.getMetaID(), 1), 'S', OrePrefixes.screw.get(Materials.Neutronium), 'P', CoreItems2.getRecipe(85, 1), 'A', CoreItems2.getRecipe(14, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyNoseConeTier8.getMetaID(), 1), tBitMask, new Object[]{"dNh", "SPS", "PAP", 'N', CoreItems2.getRecipe(HeavyDutyNoseConeTier7.getMetaID(), 1), 'S', OrePrefixes.screw.get(Materials.Phoenixite), 'P', CoreItems2.getRecipe(77, 1), 'A', CoreItems2.getRecipe(15, 1)});

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketEngineTier4.getMetaID(), 1), tBitMask, new Object[]{"PIP", "PSP", "BEB", 'I', OrePrefixes.pipeTiny.get(Materials.NiobiumTitanium), 'S', ItemList.Electric_Piston_LuV, 'B', CoreItems2.getRecipe(36, 1), 'P', CoreItems2.getRecipe(11, 1), 'E', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketEngineTier5.getMetaID(), 1), tBitMask, new Object[]{"PIP", "PSP", "BEB", 'I', OrePrefixes.pipeTiny.get(Materials.Enderium), 'S', ItemList.Electric_Piston_ZPM, 'B', CoreItems2.getRecipe(37, 1), 'P', CoreItems2.getRecipe(12, 1), 'E', CoreItems2.getRecipe(26, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketEngineTier6.getMetaID(), 1), tBitMask, new Object[]{"PIP", "PSP", "BEB", 'I', OrePrefixes.pipeTiny.get(Materials.Naquadah), 'S', ItemList.Electric_Piston_UV, 'B', CoreItems2.getRecipe(38, 1), 'P', CoreItems2.getRecipe(13, 1), 'E', CoreItems2.getRecipe(27, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketEngineTier7.getMetaID(), 1), tBitMask, new Object[]{"PIP", "PSP", "BEB", 'I', OrePrefixes.pipeTiny.get(Materials.Duranium), 'S', ItemList.Electric_Piston_UHV, 'B', CoreItems2.getRecipe(39, 1), 'P', CoreItems2.getRecipe(14, 1), 'E', CoreItems2.getRecipe(28, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketEngineTier8.getMetaID(), 1), tBitMask, new Object[]{"PIP", "PSP", "BEB", 'I', OrePrefixes.pipeTiny.get(Materials.Neutronium), 'S', ItemList.Electric_Piston_UEV, 'B', CoreItems2.getRecipe(40, 1), 'P', CoreItems2.getRecipe(15, 1), 'E', CoreItems2.getRecipe(29, 1)});

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketFinsTier4.getMetaID(), 1), tBitMask, new Object[]{"fPh", "APA", "ACA", 'P', CoreItems2.getRecipe(11, 1), 'C', CoreItems2.getRecipe(92, 1), 'A', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketFinsTier5.getMetaID(), 1), tBitMask, new Object[]{"fPh", "APA", "ACA", 'P', CoreItems2.getRecipe(12, 1), 'C', CoreItems2.getRecipe(91, 1), 'A', CoreItems2.getRecipe(11, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketFinsTier6.getMetaID(), 1), tBitMask, new Object[]{"fPh", "APA", "ACA", 'P', CoreItems2.getRecipe(13, 1), 'C', CoreItems2.getRecipe(94, 1), 'A', CoreItems2.getRecipe(12, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketFinsTier7.getMetaID(), 1), tBitMask, new Object[]{"fPh", "APA", "ACA", 'P', CoreItems2.getRecipe(14, 1), 'C', CoreItems2.getRecipe(95, 1), 'A', CoreItems2.getRecipe(13, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(HeavyDutyRocketFinsTier8.getMetaID(), 1), tBitMask, new Object[]{"fPh", "APA", "ACA", 'P', CoreItems2.getRecipe(15, 1), 'C', GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Adamantium, 1), 'A', CoreItems2.getRecipe(14, 1)});

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(Tier4Booster.getMetaID(), 1), tBitMask, new Object[]{"PPP", "ABA", "ACA", 'P', OrePrefixes.plate.get(Materials.HastelloyN), 'A', CoreItems2.getRecipe(11, 1), 'C', CoreItems2.getRecipe(86, 1), 'B', GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(Tier5Booster.getMetaID(), 1), tBitMask, new Object[]{"PPP", "ABA", "ACA", 'P', OrePrefixes.plate.get(Materials.Europium), 'A', CoreItems2.getRecipe(12, 1), 'C', CoreItems2.getRecipe(88, 1), 'B', CoreItems2.getRecipe(36, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(Tier6Booster.getMetaID(), 1), tBitMask, new Object[]{"PPP", "ABA", "ACA", 'P', OrePrefixes.plate.get(Materials.Americium), 'A', CoreItems2.getRecipe(13, 1), 'C', CoreItems2.getRecipe(79, 1), 'B', CoreItems2.getRecipe(37, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(Tier7Booster.getMetaID(), 1), tBitMask, new Object[]{"PPP", "ABA", "ACA", 'P', OrePrefixes.plate.get(Materials.Neutronium), 'A', CoreItems2.getRecipe(14, 1), 'C', CoreItems2.getRecipe(85, 1), 'B', CoreItems2.getRecipe(38, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(Tier8Booster.getMetaID(), 1), tBitMask, new Object[]{"PPP", "ABA", "ACA", 'P', OrePrefixes.plate.get(Materials.Phoenixite), 'A', CoreItems2.getRecipe(15, 1), 'C', CoreItems2.getRecipe(77, 1), 'B', CoreItems2.getRecipe(39, 1)});

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(MediumFuelCanister.getMetaID(), 1), tBitMask, new Object[]{"SdS", "PCA", "ShS", 'S', OrePrefixes.screw.get(Materials.HastelloyC276), 'P', CoreItems2.getRecipe(96, 1), 'A', CoreItems2.getRecipe(90, 1), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.Modules", 1L, 3)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(LargeFuelCanister.getMetaID(), 1), tBitMask, new Object[]{"SdS", "PCA", "ShS", 'S', OrePrefixes.screw.get(Materials.Lafium), 'P', CoreItems2.getRecipe(91, 1), 'A', CoreItems2.getRecipe(92, 1), 'C', CoreItems2.getRecipe(133, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(ExtraLargeFuelCanister.getMetaID(), 1), tBitMask, new Object[]{"SdS", "PCA", "ShS", 'S', OrePrefixes.screw.get(Materials.Neutronium), 'P', CoreItems2.getRecipe(94, 1), 'A', CoreItems2.getRecipe(95, 1), 'C', CoreItems2.getRecipe(134, 1)});

    /*====END SPACE ADDITION====*/

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(NeutronReflectorSmallParts.getMetaID(), 1), tBitMask, new Object[]{"PPP", "PwP", 'P', GT_ModHandler.getModItem("IC2", "reactorReflector", 1L, 1)});
    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(NeutronReflectorParts.getMetaID(), 1), tBitMask, new Object[]{"PPP", "PwP", "PPP", 'P', GT_ModHandler.getModItem("IC2", "reactorReflectorThick", 1L, 1)});

    GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(IndustryFrame.getMetaID(), 1), tBitMask, new Object[]{"PPP", "SBS", "SSS", 'P', OrePrefixes.plate.get(Materials.Iridium), 'S', OrePrefixes.stick.get(Materials.Osmium), 'B', GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Europium, 1)});

    GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(WoodBrickFormTool.WoodBrickFormTool, 1), tBitMask, new Object[]{GT_ModHandler.getModItem("TConstruct", "blankPattern", 1L), ToolDictNames.craftingToolKnife});

    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "string", 2L), tBitMask, new Object[]{"Wk", 'W', "blockWool"});

    //Sawmill
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("impact", "impact_sawmill_block", 5L), tBitMask, new Object[]{"SCS", "BLB", "BdB", 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'B', OrePrefixes.frameGt.get(Materials.Wood), 'L', OrePrefixes.slab.get(Materials.Wood), 'C', GT_ItemList.ULVConveyorModule});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.SawCase.get(2L), tBitMask, new Object[]{"SrS", "SBS", "SsS", 'S', OrePrefixes.slab.get(Materials.Wood), 'B', OrePrefixes.frameGt.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.SawMill.get(1L), bitsd, new Object[]{"SBd", "MCM", "LWL", 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'B', OrePrefixes.toolHeadBuzzSaw.get(Materials.Iron), 'L', OrePrefixes.circuit.get(Materials.Primitive), 'W', OrePrefixes.wireGt01.get(Materials.Lead), 'M', GT_ItemList.ULVMotor, 'C', GT_ItemList.SawCase});

    //Pyrolyse Oven
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Pyrolyse.get(1L), bitsd, new Object[]{"PQW", "CHC", "ICW", 'Q', OrePrefixes.pipeNonuple.get(Materials.Bronze), 'C', OrePrefixes.circuit.get(Materials.Basic), 'W', OrePrefixes.wireGt08.get(Materials.Cupronickel), 'P', ItemList.Electric_Pump_LV, 'I', ItemList.Electric_Piston_LV, 'H', ItemList.Hull_LV});

    //WaterPump
    GT_ModHandler.addCraftingRecipe(GT_ItemList.PrimitiveWaterPumpCase.get(1L), tBitMask, new Object[]{"SWS", "dCr", 'S', OrePrefixes.screw.get(Materials.Iron), 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.slab.get(Materials.Cobblestone)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.BasicWaterPump.get(1L), bitsd, new Object[]{"RPS", "OWd", "CHC", 'O', OrePrefixes.rotor.get(Materials.Iron), 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'P', OrePrefixes.pipeMedium.get(Materials.Wood), 'H', GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Wood, 1), 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.slab.get(Materials.Cobblestone)});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Hatch_Output_Pump.get(1L), bitsd, new Object[]{"SRd", "WHW", "CRC", 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'H', GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Wood, 1), 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.slab.get(Materials.Cobblestone)});
    //WaterDrill
    GT_ModHandler.addCraftingRecipe(GT_ItemList.WaterDrill.get(1L), bitsd, new Object[]{"BDB", "GPG", "CHC", 'B', GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1), 'D', GT_OreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Steel, 1), 'G', GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 1), 'P', OrePrefixes.pipeHuge.get(Materials.Steel), 'C', OrePrefixes.circuit.get(Materials.Good), 'H', ItemList.Hull_MV});

    //Diodes 2A
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_ULV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.WroughtIron), 'W', OrePrefixes.cableGt02.get(Materials.Lead), 'S', OrePrefixes.wireFine.get(Materials.Lead), 'C', ItemList.Casing_ULV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_LV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Steel), 'W', OrePrefixes.cableGt02.get(Materials.Tin), 'S', OrePrefixes.wireFine.get(Materials.Tin), 'C', ItemList.Casing_LV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_MV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Birmabright), 'W', OrePrefixes.cableGt02.get(Materials.Copper), 'S', OrePrefixes.wireFine.get(Materials.Copper), 'C', ItemList.Casing_MV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_HV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'W', OrePrefixes.cableGt02.get(Materials.Electrum), 'S', OrePrefixes.wireFine.get(Materials.Electrum), 'C', ItemList.Casing_HV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_EV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.BT6), 'W', OrePrefixes.cableGt02.get(Materials.Aluminium), 'S', OrePrefixes.wireFine.get(Materials.Aluminium), 'C', ItemList.Casing_EV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_IV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.HastelloyC276), 'W', OrePrefixes.cableGt02.get(Materials.TungstenSteel), 'S', OrePrefixes.wireFine.get(Materials.TungstenSteel), 'C', ItemList.Casing_IV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_LuV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.HastelloyN), 'W', OrePrefixes.cableGt02.get(Materials.YttriumBariumCuprate), 'S', OrePrefixes.wireFine.get(Materials.YttriumBariumCuprate), 'C', ItemList.Casing_LuV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_ZPM.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Lafium), 'W', OrePrefixes.cableGt02.get(Materials.Naquadah), 'S', OrePrefixes.wireFine.get(Materials.Naquadah), 'C', ItemList.Casing_ZPM});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_2A_UV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.CinobiteA243), 'W', OrePrefixes.cableGt02.get(Materials.ElectrumFlux), 'S', OrePrefixes.wireFine.get(Materials.ElectrumFlux), 'C', ItemList.Casing_UV});

    //Diodes 4A
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_ULV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.WroughtIron), 'W', OrePrefixes.cableGt04.get(Materials.Lead), 'S', OrePrefixes.springSmall.get(Materials.Lead), 'C', ItemList.Casing_ULV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_LV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Steel), 'W', OrePrefixes.cableGt04.get(Materials.Tin), 'S', OrePrefixes.springSmall.get(Materials.Tin), 'C', ItemList.Casing_LV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_MV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Birmabright), 'W', OrePrefixes.cableGt04.get(Materials.Copper), 'S', OrePrefixes.springSmall.get(Materials.Copper), 'C', ItemList.Casing_MV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_HV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'W', OrePrefixes.cableGt04.get(Materials.Electrum), 'S', OrePrefixes.springSmall.get(Materials.Electrum), 'C', ItemList.Casing_HV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_EV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.BT6), 'W', OrePrefixes.cableGt04.get(Materials.Aluminium), 'S', OrePrefixes.springSmall.get(Materials.Aluminium), 'C', ItemList.Casing_EV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_IV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.HastelloyC276), 'W', OrePrefixes.cableGt04.get(Materials.TungstenSteel), 'S', OrePrefixes.springSmall.get(Materials.TungstenSteel), 'C', ItemList.Casing_IV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_LuV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.HastelloyN), 'W', OrePrefixes.cableGt04.get(Materials.YttriumBariumCuprate), 'S', OrePrefixes.springSmall.get(Materials.YttriumBariumCuprate), 'C', ItemList.Casing_LuV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_ZPM.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Lafium), 'W', OrePrefixes.cableGt04.get(Materials.Naquadah), 'S', OrePrefixes.springSmall.get(Materials.Naquadah), 'C', ItemList.Casing_ZPM});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_4A_UV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.CinobiteA243), 'W', OrePrefixes.cableGt04.get(Materials.ElectrumFlux), 'S', OrePrefixes.springSmall.get(Materials.ElectrumFlux), 'C', ItemList.Casing_UV});

    //Diodes 16A
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_ULV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.WroughtIron), 'W', OrePrefixes.cableGt16.get(Materials.Lead), 'S', OrePrefixes.spring.get(Materials.Lead), 'C', ItemList.Casing_ULV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_LV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Steel), 'W', OrePrefixes.cableGt16.get(Materials.Tin), 'S', OrePrefixes.spring.get(Materials.Tin), 'C', ItemList.Casing_LV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_MV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Birmabright), 'W', OrePrefixes.cableGt16.get(Materials.Copper), 'S', OrePrefixes.spring.get(Materials.Copper), 'C', ItemList.Casing_MV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_HV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'W', OrePrefixes.cableGt16.get(Materials.Electrum), 'S', OrePrefixes.spring.get(Materials.Electrum), 'C', ItemList.Casing_HV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_EV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.BT6), 'W', OrePrefixes.cableGt16.get(Materials.Aluminium), 'S', OrePrefixes.spring.get(Materials.Aluminium), 'C', ItemList.Casing_EV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_IV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.HastelloyC276), 'W', OrePrefixes.cableGt16.get(Materials.TungstenSteel), 'S', OrePrefixes.spring.get(Materials.TungstenSteel), 'C', ItemList.Casing_IV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_LuV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.HastelloyN), 'W', OrePrefixes.cableGt16.get(Materials.YttriumBariumCuprate), 'S', OrePrefixes.spring.get(Materials.YttriumBariumCuprate), 'C', ItemList.Casing_LuV});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_ZPM.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.Lafium), 'W', OrePrefixes.cableGt16.get(Materials.Naquadah), 'S', OrePrefixes.spring.get(Materials.Naquadah), 'C', ItemList.Casing_ZPM});
    GT_ModHandler.addCraftingRecipe(GT_ItemList.Diode_16A_UV.get(1L), 0, new Object[]{"PSP", "WCW", 'P', OrePrefixes.plate.get(Materials.CinobiteA243), 'W', OrePrefixes.cableGt16.get(Materials.ElectrumFlux), 'S', OrePrefixes.spring.get(Materials.ElectrumFlux), 'C', ItemList.Casing_UV});

    //White Concrete
    GT_ModHandler.addCraftingRecipe(Blockstack(UtilBlock, 8, 2), bitsd, new Object[]{"CCC", "CDC", "CCC", 'C', Blockstack(UtilBlock, 1, 0), 'D', Dyes.dyeWhite});

  }
  public void run2() {
    /** ==== START CHEST ==== */
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'P', OrePrefixes.plateDouble.get(Materials.WroughtIron), 'C', GT_ModHandler.getModItem("minecraft", "chest", 1L, 0)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 1), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Steel), 'P', OrePrefixes.plateDouble.get(Materials.Steel), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 0)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Aluminium), 'P', OrePrefixes.plateDouble.get(Materials.Aluminium), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 1)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 3), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.HSLA), 'P', OrePrefixes.plateDouble.get(Materials.HSLA), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 4), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Titanium), 'P', OrePrefixes.plateDouble.get(Materials.Titanium), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 3)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 5), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.TungstenSteel), 'P', OrePrefixes.plateDouble.get(Materials.TungstenSteel), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 4)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 6), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Chrome), 'P', OrePrefixes.plateDouble.get(Materials.Chrome), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 5)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 7), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Iridium), 'P', OrePrefixes.plateDouble.get(Materials.Iridium), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 6)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 8), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Osmium), 'P', OrePrefixes.plateDouble.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 7)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 9), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Neutronium), 'P', OrePrefixes.plateDouble.get(Materials.Neutronium), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 8)});

    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "WOODWRIRONUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'P', OrePrefixes.plateDouble.get(Materials.WroughtIron), 'C', OrePrefixes.plate.get(Materials.Wood)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "WRIRONSTEELUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Steel), 'P', OrePrefixes.plateDouble.get(Materials.Steel), 'C', OrePrefixes.plate.get(Materials.WroughtIron)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "STEELALUMINIUMUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Aluminium), 'P', OrePrefixes.plateDouble.get(Materials.Aluminium), 'C', OrePrefixes.plate.get(Materials.Steel)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "ALUMINIUMHSLAUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.HSLA), 'P', OrePrefixes.plateDouble.get(Materials.HSLA), 'C', OrePrefixes.plate.get(Materials.Aluminium)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "HSLATITANIUMUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Titanium), 'P', OrePrefixes.plateDouble.get(Materials.Titanium), 'C', OrePrefixes.plate.get(Materials.HSLA)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "TITANIUMWOLFRAMUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.TungstenSteel), 'P', OrePrefixes.plateDouble.get(Materials.TungstenSteel), 'C', OrePrefixes.plate.get(Materials.Titanium)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "WOLFRAMCHROMEUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Chrome), 'P', OrePrefixes.plateDouble.get(Materials.Chrome), 'C', OrePrefixes.plate.get(Materials.TungstenSteel)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "CHROMEIRIDIUMUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Iridium), 'P', OrePrefixes.plateDouble.get(Materials.Iridium), 'C', OrePrefixes.plate.get(Materials.Chrome)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "IRIDIUMOSMIUMUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Osmium), 'P', OrePrefixes.plateDouble.get(Materials.Osmium), 'C', OrePrefixes.plate.get(Materials.Iridium)});
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("chestup", "OSMIUMNEUTRONIUMUpgrade", 1L, 0), tBitMask, new Object[]{"SPS", "PCP", "dPh", 'S', OrePrefixes.screw.get(Materials.Neutronium), 'P', OrePrefixes.plateDouble.get(Materials.Neutronium), 'C', OrePrefixes.plate.get(Materials.Osmium)});

    /* ==== END CHEST ==== */
    /** ==== START LOGISTIC PIPES ==== */
    // --- Logistics Power Junction
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 1), tBitMask, new Object[]{"GMG", "ECE", "IWI", 'G', OrePrefixes.gear.get(Materials.Steel), 'I', OrePrefixes.circuit.get(Materials.Good), 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'E', ItemList.Electric_Motor_MV, 'M', ItemList.Cover_Screen, 'C', ItemList.MACHINE_HULLS[2]});
    // --- Logistics Secгrity Junction
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 2), tBitMask, new Object[]{"RMR", "ICI", "SGS", 'G', OrePrefixes.gear.get(Materials.Steel), 'I', OrePrefixes.circuit.get(Materials.Good), 'R', OrePrefixes.plate.get(Materials.RedAlloy), 'S', OrePrefixes.plate.get(Materials.Steel), 'M', ItemList.Cover_Screen, 'C', ItemList.MACHINE_HULLS[2]});
    // --- Logistics Crafting Table
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 3), tBitMask, new Object[]{"RMR", "OCB", "IGI", 'G', OrePrefixes.gear.get(Materials.Steel), 'I', OrePrefixes.circuit.get(Materials.Good), 'R', OrePrefixes.plate.get(Materials.Steel), 'M', ItemList.Cover_Crafting, 'O', ItemList.Conveyor_Module_MV, 'B', ItemList.Robot_Arm_MV, 'C', ItemList.MACHINE_HULLS[2]});
    // --- Logistics Fuzzy Crafting Table
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 4), tBitMask, new Object[]{"RMR", "OCB", "IGI", 'G', OrePrefixes.gear.get(Materials.Steel), 'I', OrePrefixes.circuit.get(Materials.Good), 'R', OrePrefixes.plate.get(Materials.Lapis), 'M', ItemList.Cover_Crafting, 'O', ItemList.Conveyor_Module_MV, 'B', ItemList.Robot_Arm_MV, 'C', ItemList.MACHINE_HULLS[2]});
    // --- Logistics Statistics Table
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 5), tBitMask, new Object[]{"RMR", "SCE", "IGI", 'G', OrePrefixes.gear.get(Materials.Steel), 'I', OrePrefixes.circuit.get(Materials.Good), 'R', OrePrefixes.plate.get(Materials.Steel), 'M', ItemList.Cover_Screen, 'S', ItemList.Sensor_MV, 'E', ItemList.Emitter_MV, 'C', ItemList.MACHINE_HULLS[2]});
    // --- Logistics EU Power Provider
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 12), tBitMask, new Object[]{"IMI", "LCL", "IBI", 'I', OrePrefixes.circuit.get(Materials.Good), 'M', ItemList.Cover_Screen, 'L', ItemList.MV_Coil, 'B', ItemList.Battery_RE_MV_Lithium, 'C', ItemList.MACHINE_HULLS[2]});
    // --- Unrouted Transport Pipe
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicTransport", 8L, 0), tBitMask, new Object[]{"SfS", "SGS", "SsS", 'S', OrePrefixes.stick.get(Materials.Birmabright), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicTransport", 8L, 0), tBitMask, new Object[]{"SfS", "SGS", "SsS", 'S', OrePrefixes.stick.get(Materials.Birmabright), 'G', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L)});
    // --- Logistics Request Table
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeBlockRequestTable", 1L, 0), tBitMask, new Object[]{"IMI", "RCR", "IOI", 'I', OrePrefixes.circuit.get(Materials.Good), 'M', GT_ModHandler.getModItem("EnderIO", "blockInventoryPanel", 1L), 'R', ItemList.Robot_Arm_MV, 'O', ItemList.Conveyor_Module_MV, 'C', GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 3)});
    // --- Logistics HUD Glasses
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsHUDGlasses", 1L, 0), tBitMask, new Object[]{"ISI", "RTR", "GdG", 'S', OrePrefixes.screw.get(Materials.Aluminium), 'R', OrePrefixes.ring.get(Materials.Aluminium), 'I', GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsParts", 1L), 'T', GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsParts", 1L, 2), 'G', GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsParts", 1L, 1)});
    // --- Logistics HUD Glass
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsParts", 1L, 1), tBitMask, new Object[]{"SRS", "dLh", 'S', OrePrefixes.screw.get(Materials.Aluminium), 'R', OrePrefixes.ring.get(Materials.Aluminium), 'L', GT_OreDictUnificator.get(OrePrefixes.lens, Materials.ReinforcedGlass, 1)});
    // --- Logistics HUD Bow
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsParts", 2L, 0), tBitMask, new Object[]{"SSB", "  f", 'S', OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.bolt.get(Materials.Aluminium)});
    // --- Logistics HUD Nose Bridge
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsParts", 1L, 2), tBitMask, new Object[]{" P ", "BhB", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'B', OrePrefixes.bolt.get(Materials.Aluminium)});
    // --- Logistics Pipe Controller
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.pipeController", 1L, 0), tBitMask, new Object[]{"  S", "CMC", "PWP", 'P', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.circuit.get(Materials.Basic), 'W', OrePrefixes.cableGt01.get(Materials.Tin), 'S', ItemList.Sensor_LV, 'M', ItemList.Cover_Screen});
    // --- Remote Orderer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.remoteOrdererItem", 1L, 0), tBitMask, new Object[]{"  S", "CMC", "PWP", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'C', OrePrefixes.circuit.get(Materials.Good), 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'S', ItemList.Sensor_MV, 'M', GT_ModHandler.getModItem("EnderIO", "blockInventoryPanel", 1L)});
    // --- ItemSink Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 1), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneIronChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Copper), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});
    // --- Passive Supplier Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 2), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneIronChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Gold), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});
    // --- Extractor Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 3), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneIronChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Electrum), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});
    // --- Polymorphic ItemSink Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 4), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneRedChipset, 'B', OrePrefixes.bolt.get(Materials.Gold), 'F', OrePrefixes.foil.get(Materials.Copper), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 1)});
    // --- QuickSort Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 5), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneGoldChipset, 'B', OrePrefixes.bolt.get(Materials.Silver), 'F', OrePrefixes.foil.get(Materials.Gold), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});
    // --- Terminus Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 6), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneRedChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Iron), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});
    // --- Extractor Module MK2
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 103), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneGoldChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Electrum), 'I', OrePrefixes.circuit.get(Materials.Good), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 3)});
    // --- Extractor Module MK3
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 203), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneDiamondChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Birmabright), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 103)});
    // --- Provider Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 500), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneGoldChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Silver), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});
    // --- Provider Module MK2
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 501), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneGoldChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Silver), 'I', OrePrefixes.circuit.get(Materials.Good), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 500)});
    // --- Electric Manager Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 300), tBitMask, new Object[]{"WCA", "BMB", "FIF", 'C', GT_ItemList.RedstoneIronChipset, 'B', OrePrefixes.bolt.get(Materials.Electrum), 'F', OrePrefixes.foil.get(Materials.Silver), 'I', OrePrefixes.circuit.get(Materials.Good), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L), 'W', OrePrefixes.wireGt02.get(Materials.Copper), 'A', ItemList.Battery_RE_LV_Lithium});
    // --- Electric Buffer Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 301), tBitMask, new Object[]{"ACA", "BMB", "FIF", 'C', GT_ItemList.RedstoneIronChipset, 'B', OrePrefixes.bolt.get(Materials.Electrum), 'F', OrePrefixes.foil.get(Materials.Silver), 'I', OrePrefixes.circuit.get(Materials.Good), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L), 'A', ItemList.Battery_RE_MV_Lithium});
    // --- Mod Based ItemSink Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 12), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneGoldChipset, 'B', OrePrefixes.bolt.get(Materials.Electrum), 'F', OrePrefixes.foil.get(Materials.Steel), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 1)});
    // --- OreDict ItemSink Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 13), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneGoldChipset, 'B', OrePrefixes.bolt.get(Materials.Gold), 'F', OrePrefixes.foil.get(Materials.AnnealedCopper), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 12)});
    // --- Crafting Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 600), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneIronChipset, 'B', OrePrefixes.bolt.get(Materials.Iron), 'F', OrePrefixes.foil.get(Materials.Gold), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});
    // --- Crafting Module MK2
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 601), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneGoldChipset, 'B', OrePrefixes.bolt.get(Materials.Steel), 'F', OrePrefixes.foil.get(Materials.Gold), 'I', OrePrefixes.circuit.get(Materials.Good), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 600)});
    // --- Crafting Module MK3
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 602), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneDiamondChipset, 'B', OrePrefixes.bolt.get(Materials.Aluminium), 'F', OrePrefixes.foil.get(Materials.Gold), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 601)});
    // --- Active Supplier Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 502), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ItemList.RedstoneRedChipset, 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Gold), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});
    // --- Logistics Item Card
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.logisticsItemCard", 1L), tBitMask, new Object[]{" C ", "BMB", "FIF", 'C', GT_ModHandler.getModItem("ExtraUtilities", "chestMini", 1L), 'B', OrePrefixes.bolt.get(Materials.RedAlloy), 'F', OrePrefixes.foil.get(Materials.Gold), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L)});

    /* ==== END LOGISTIC PIPES ==== */
    /** ==== START AE2/EC2 ==== */

    // --- Advanced Storage Housing
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L), tBitMask, new Object[]{"hGS", "TOT", "SVd", 'O', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), 'T', OrePrefixes.plate.get(Materials.Iridium), 'V', OrePrefixes.plate.get(Materials.HastelloyC276), 'S', OrePrefixes.screw.get(Materials.CertusQuartz), 'G', OrePrefixes.plate.get(Materials.ReinforcedGlass)});

    // --- Fluid Storage Housing
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), tBitMask, new Object[]{"hQS", "TGT", "SVd", 'Q', OrePrefixes.plate.get(Materials.CertusQuartz), 'T', OrePrefixes.plate.get(Materials.Birmabright), 'V', OrePrefixes.plate.get(Materials.StainlessSteel), 'S', OrePrefixes.screw.get(Materials.NetherQuartz), 'G', "paneGlass"});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), tBitMask, new Object[]{"hQS", "TGT", "SVd", 'Q', OrePrefixes.plate.get(Materials.CertusQuartz), 'T', OrePrefixes.plate.get(Materials.Birmabright), 'V', OrePrefixes.plate.get(Materials.StainlessSteel), 'S', OrePrefixes.screw.get(Materials.NetherQuartz), 'G', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L)});

    /* ==== END AE2/EC2 ==== */
    /** ==== START VANILLA ==== */
    // --- Crafting Table
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "crafting_table", 1L), tBitMask, new Object[]{"WW", "WW", 'W', "logWood"});
    // --- Chest
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "chest", 1L), tBitMask, new Object[]{"WWW", "W W", "WWW", 'W', "plankWood"});
    // --- Trapper Chest
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "trapped_chest", 1L), tBitMask, new Object[]{" T ", "SCS", " d ", 'C', GT_ModHandler.getModItem("minecraft", "chest", 1L), 'S', OrePrefixes.screw.get(Materials.Iron), 'T', GT_ModHandler.getModItem("minecraft", "tripwire_hook", 1L)});
    // --- Dispenser
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "dispenser", 1L), tBitMask, new Object[]{"CRC", "SMS", "GWG", 'C', "cobblestone", 'S', OrePrefixes.spring.get(Materials.Iron), 'G', OrePrefixes.gearGtSmall.get(Materials.Iron), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'R', OrePrefixes.ring.get(Materials.Iron), 'M', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Dropper
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "dropper", 1L), tBitMask, new Object[]{"CRC", "SMS", "GWG", 'C', "cobblestone", 'S', OrePrefixes.springSmall.get(Materials.Iron), 'G', OrePrefixes.gearGtSmall.get(Materials.Iron), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'R', OrePrefixes.ring.get(Materials.Iron), 'M', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Sticky Piston
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "sticky_piston", 1L), tBitMask, new Object[]{"r", "S", "P", 'S', "slimeball", 'P', GT_ModHandler.getModItem("minecraft", "piston", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "sticky_piston", 1L), tBitMask, new Object[]{"r", "S", "P", 'S', GT_ModHandler.getModItem("IC2", "itemHarz", 1L), 'P', GT_ModHandler.getModItem("minecraft", "piston", 1L)});
    // --- Noteblock
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "noteblock", 1L), tBitMask, new Object[]{"PPP", "BGB", "PWP", 'P', "plankWood", 'B', GT_ModHandler.getModItem("minecraft", "iron_bars", 1L), 'G', OrePrefixes.gear.get(Materials.Wood), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy)});
    // --- Jukebox
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "jukebox", 1L), tBitMask, new Object[]{"LBL", "NRN", "LGL", 'L', "plankWood", 'N', GT_ModHandler.getModItem("minecraft", "noteblock", 1L), 'G', OrePrefixes.gear.get(Materials.Iron), 'B', OrePrefixes.bolt.get(Materials.Diamond), 'R', "record"});
    // --- Jack O' Lantern
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("minecraft", "lit_pumpkin", 1L), tBitMask, new Object[]{GT_ModHandler.getModItem("minecraft", "pumpkin", 1L), GT_ModHandler.getModItem("minecraft", "torch", 1L), ToolDictNames.craftingToolKnife});
    // --- Trapdoor Oak
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "trapdoor", 1L), tBitMask, new Object[]{"PSP", "SSS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "trapdoor", 2L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "trapdoor", 3L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Steel), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L)});
    // --- Pressure Plate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "stone_pressure_plate", 2L), tBitMask, new Object[]{"ShS", "BPB", "SdS", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.spring.get(Materials.Iron), 'B', GT_ModHandler.getModItem("minecraft", "stone_slab", 1L)});
    // --- Wooden Pressure Plate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "wooden_pressure_plate", 2L), tBitMask, new Object[]{"ShS", "BPB", "SdS", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.spring.get(Materials.Iron), 'B', "slabWood"});
    // --- Glass Pane
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("minecraft", "glass_pane", 2L), tBitMask, new Object[]{ToolDictNames.craftingToolSaw, GT_ModHandler.getModItem("minecraft", "glass", 1L)});
    // --- Torch
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "torch", 3L), tBitMask, new Object[]{"C", "S", 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.gem.get(Materials.Coal)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "torch", 2L), tBitMask, new Object[]{"C", "S", 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.gem.get(Materials.Charcoal)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "torch", 1L), tBitMask, new Object[]{"C", "S", 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.gem.get(Materials.Lignite)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "torch", 5L), tBitMask, new Object[]{"C", "S", 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.gem.get(Materials.CokeCoal)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "torch", 3L), tBitMask, new Object[]{"C", "S", 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.dust.get(Materials.Sulfur)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "torch", 6L), tBitMask, new Object[]{"C", "S", 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.dust.get(Materials.Phosphorus)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "torch", 2L), tBitMask, new Object[]{"C", "S", 'S', OrePrefixes.stick.get(Materials.Wood), 'C', GT_ModHandler.getModItem("IC2", "itemHarz", 1L)});
    // --- Ladder
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "ladder", 1L), tBitMask, new Object[]{"SIS", "SSS", "SrS", 'S', OrePrefixes.stick.get(Materials.Wood), 'I', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "ladder", 2L), tBitMask, new Object[]{"SdS", "SWS", "SrS", 'S', OrePrefixes.stick.get(Materials.Wood), 'W', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "ladder", 4L), tBitMask, new Object[]{"SdS", "SWS", "SrS", 'S', OrePrefixes.stick.get(Materials.Wood), 'W', OrePrefixes.screw.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "ladder", 6L), tBitMask, new Object[]{"SdS", "SWS", "SrS", 'S', OrePrefixes.stick.get(Materials.Wood), 'W', OrePrefixes.screw.get(Materials.Steel)});
    // --- Fence
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fence", 1L), tBitMask, new Object[]{"SPS", "SPS", "SPS", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', "plankWood"});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fence", 1L), tBitMask, new Object[]{"WdW", "SPS", "SPS", 'W', OrePrefixes.screw.get(Materials.Wood), 'S', OrePrefixes.stick.get(Materials.Wood), 'P', "plankWood"});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fence", 2L), tBitMask, new Object[]{"WdW", "SPS", "SPS", 'W', OrePrefixes.screw.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Wood), 'P', "plankWood"});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fence", 4L), tBitMask, new Object[]{"WdW", "SPS", "SPS", 'W', OrePrefixes.screw.get(Materials.Steel), 'S', OrePrefixes.stick.get(Materials.Wood), 'P', "plankWood"});
    // --- Oak Fence Gate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fence_gate", 1L), tBitMask, new Object[]{"PSP", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L), 'S', OrePrefixes.stick.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fence_gate", 1L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fence_gate", 2L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fence_gate", 4L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Steel)});
    // --- Redstone Lamp
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "redstone_lamp", 1L), tBitMask, new Object[]{"GGG", "GBG", "GRG", 'R', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'B', GT_ModHandler.getModItem("minecraft", "glowstone", 1L), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L)});
    // --- Tripwire Hook
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "tripwire_hook", 1L), tBitMask, new Object[]{"RSR", "ISI", " I ", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.ring.get(Materials.Iron), 'I', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Anvil
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "anvil", 1L), tBitMask, new Object[]{"BBB", "SBS", "PBP", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'B', OrePrefixes.block.get(Materials.Iron)});
    // --- Daylight Sensor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "daylight_detector", 1L), tBitMask, new Object[]{"GGG", "QQQ", "SRS", 'R', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'Q', OrePrefixes.plate.get(Materials.NetherQuartz), 'S', "slabWood", 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L)});
    // --- Oak Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "wooden_door", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "wooden_door", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Copper), 'R', OrePrefixes.ring.get(Materials.Copper), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L)});
    // --- Iron Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "iron_door", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Steel), 'R', OrePrefixes.ring.get(Materials.Steel), 'P', OrePrefixes.plate.get(Materials.Iron)});
    // --- Diamond Suit
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "diamond_helmet", 1L), tBitMask, new Object[]{"DDD", "DfD", 'D', OrePrefixes.gem.get(Materials.Diamond)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "diamond_chestplate", 1L), tBitMask, new Object[]{"DfD", "DDD", "DDD", 'D', OrePrefixes.gem.get(Materials.Diamond)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "diamond_leggings", 1L), tBitMask, new Object[]{"DDD", "DfD", "D D", 'D', OrePrefixes.gem.get(Materials.Diamond)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "diamond_boots", 1L), tBitMask, new Object[]{"D D", "DfD", 'D', OrePrefixes.gem.get(Materials.Diamond)});
    // --- Iron Horse Armor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "iron_horse_armor", 1L), tBitMask, new Object[]{"hdH", "PCP", "LSL", 'P', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.screw.get(Materials.Iron), 'H', GT_ModHandler.getModItem("minecraft", "iron_helmet", 1L), 'C', GT_ModHandler.getModItem("minecraft", "iron_chestplate", 1L), 'L', GT_ModHandler.getModItem("minecraft", "iron_leggings", 1L)});
    // --- Gold Horse Armor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "golden_horse_armor", 1L), tBitMask, new Object[]{"hdH", "PCP", "LSL", 'P', OrePrefixes.plate.get(Materials.Gold), 'S', OrePrefixes.screw.get(Materials.Gold), 'H', GT_ModHandler.getModItem("minecraft", "golden_helmet", 1L), 'C', GT_ModHandler.getModItem("minecraft", "golden_chestplate", 1L), 'L', GT_ModHandler.getModItem("minecraft", "golden_leggings", 1L)});
    // --- Diamond Horse Armor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "diamond_horse_armor", 1L), tBitMask, new Object[]{"hdH", "PCP", "LSL", 'P', OrePrefixes.plate.get(Materials.Diamond), 'S', OrePrefixes.screw.get(Materials.Diamond), 'H', GT_ModHandler.getModItem("minecraft", "diamond_helmet", 1L), 'C', GT_ModHandler.getModItem("minecraft", "diamond_chestplate", 1L), 'L', GT_ModHandler.getModItem("minecraft", "diamond_leggings", 1L)});
    // --- Saddle
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "saddle", 1L), tBitMask, new Object[]{"LBL", "LCL", "RSR", 'C', "blockWool", 'L', GT_ModHandler.getModItem("minecraft", "leather", 1L), 'B', GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L), 'R', OrePrefixes.ring.get(Materials.Iron)});
    // --- Item Frame
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "item_frame", 1L), tBitMask, new Object[]{"IRI", "SLS", "SSS", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.ring.get(Materials.Iron), 'I', GT_ModHandler.getModItem("minecraft", "string", 1L), 'L', GT_ModHandler.getModItem("minecraft", "leather", 1L)});
    // --- Comparator
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "comparator", 1L), tBitMask, new Object[]{"STS", "TPT", "LdL", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.NetherQuartz), 'T', GT_ModHandler.getModItem("minecraft", "redstone_torch", 1L), 'L', GT_ModHandler.getModItem("minecraft", "stone_slab", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "comparator", 1L), tBitMask, new Object[]{"STS", "TPT", "LdL", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Quartzite), 'T', GT_ModHandler.getModItem("minecraft", "redstone_torch", 1L), 'L', GT_ModHandler.getModItem("minecraft", "stone_slab", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "comparator", 1L), tBitMask, new Object[]{"STS", "TPT", "LdL", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.CertusQuartz), 'T', GT_ModHandler.getModItem("minecraft", "redstone_torch", 1L), 'L', GT_ModHandler.getModItem("minecraft", "stone_slab", 1L)});
    // --- Redstone Repeater
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "repeater", 1L), tBitMask, new Object[]{"S S", "TdT", "LIL", 'S', OrePrefixes.screw.get(Materials.Iron), 'I', OrePrefixes.stick.get(Materials.RedAlloy), 'T', GT_ModHandler.getModItem("minecraft", "redstone_torch", 1L), 'L', GT_ModHandler.getModItem("minecraft", "stone_slab", 1L)});
    // --- Bed
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "bed", 1L), tBitMask, new Object[]{"WWW", "FPF", 'P', OrePrefixes.plank.get(Materials.Wood), 'F', GT_ModHandler.getModItem("minecraft", "fence", 1L), 'W', "blockWool"});
    // --- Fishing Rod
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("minecraft", "fishing_rod", 1L), tBitMask, new Object[]{"  S", " SI", "SxR", 'S', OrePrefixes.stickLong.get(Materials.Wood), 'R', OrePrefixes.ring.get(Materials.Iron), 'I', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    for (int i = 0; i < 16; i++) {
      // --- Carpet
      GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.carpet, 1, i), tBitMask, new Object[]{"WW", 'W', new ItemStack(Blocks.wool, 1, i)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.carpet, 3, i), tBitMask, new Object[]{"WWs", 'W', new ItemStack(Blocks.wool, 1, i)});
      GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.carpet, 1, 15 - i), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.carpet, 1, 0), Dyes.VALUES[i]});
      // --- Glass Pane
      GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.stained_glass_pane, 2, i), tBitMask, new Object[]{"sW", 'W', new ItemStack(Blocks.stained_glass, 1, i)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.stained_glass_pane, 8, 15 - i), tBitMask, new Object[]{"WWW", "WDW", "WWW", 'W', new ItemStack(Blocks.glass_pane, 1, 0), 'D', Dyes.VALUES[i]});
      GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.stained_glass, 1, 15 - i), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.glass_pane, 1, 0), Dyes.VALUES[i]});
      // --- GT's Dye
      GT_ModHandler.addShapelessCraftingRecipe(ItemList.DYE_ONLY_ITEMS[i].get(1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{Dyes.VALUES[i]});
    }

    /* ==== END VANILLA ==== */
    /** ==== START MALISIS DOORS ==== */
    // --- Iron Trap Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), tBitMask, new Object[]{"SPS", "PTP", "sPd", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'T', GT_ModHandler.getModItem("minecraft", "trapdoor", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), tBitMask, new Object[]{"SPS", "PTP", "sPd", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'T', GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), tBitMask, new Object[]{"SPS", "PTP", "sPd", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'T', GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), tBitMask, new Object[]{"SPS", "PTP", "sPd", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'T', GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), tBitMask, new Object[]{"SPS", "PTP", "sPd", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'T', GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), tBitMask, new Object[]{"SPS", "PTP", "sPd", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'T', GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 1L)});
    // --- Sliding Trap Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "sliding_trapdoor", 1L), tBitMask, new Object[]{"S S", "PTP", "s d", 'S', OrePrefixes.screw.get(Materials.Steel), 'P', OrePrefixes.plate.get(Materials.Steel), 'T', GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L)});
    // --- Player Sensor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "player_sensor", 1L), tBitMask, new Object[]{"BRB", "hPs", 'R', OrePrefixes.plate.get(Materials.RedAlloy), 'B', GT_ModHandler.getModItem("minecraft", "stone_button", 1L), 'P', GT_ModHandler.getModItem("minecraft", "heavy_weighted_pressure_plate", 1L)});
    // --- Wooden Vanishing Frame
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L), tBitMask, new Object[]{"SIS", "IPI", "sId", 'S', OrePrefixes.screw.get(Materials.RedAlloy), 'I', OrePrefixes.stick.get(Materials.Wood), 'P', OrePrefixes.plate.get(Materials.EnderPearl)});
    // --- Iron Vanishing Frame
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 1), tBitMask, new Object[]{"SIS", "IPI", "sId", 'S', OrePrefixes.screw.get(Materials.RedAlloy), 'I', OrePrefixes.stick.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.EnderPearl)});
    // --- Gold Vanishing Frame
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 2), tBitMask, new Object[]{"SIS", "IPI", "sId", 'S', OrePrefixes.screw.get(Materials.RedAlloy), 'I', OrePrefixes.stick.get(Materials.Gold), 'P', OrePrefixes.plate.get(Materials.EnderPearl)});
    // --- Diamond Vanishing Frame
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 3), tBitMask, new Object[]{"SIS", "IPI", "sId", 'S', OrePrefixes.screw.get(Materials.RedAlloy), 'I', OrePrefixes.stick.get(Materials.Diamond), 'P', OrePrefixes.plate.get(Materials.EnderPearl)});
    // --- Block Mixer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "block_mixer", 1L), tBitMask, new Object[]{"CMC", "SOS", "GPG", 'S', OrePrefixes.springSmall.get(Materials.Iron), 'G', OrePrefixes.gearGtSmall.get(Materials.Iron), 'O', ItemList.Casing_SolidSteel, 'C', ItemList.Conveyor_Module_LV, 'M', ItemList.Electric_Motor_LV, 'P', GT_ModHandler.getModItem("minecraft", "sticky_piston", 1L)});
    // --- Door Factory
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "door_factory", 1L), tBitMask, new Object[]{"CMC", "SOS", "GPG", 'S', OrePrefixes.springSmall.get(Materials.Steel), 'G', OrePrefixes.gearGtSmall.get(Materials.Steel), 'O', ItemList.Casing_SolidSteel, 'C', ItemList.Conveyor_Module_LV, 'M', ItemList.Electric_Motor_LV, 'P', GT_ModHandler.getModItem("minecraft", "sticky_piston", 1L)});
    // --- Rusty Hatch
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "rustyHatch", 1L), tBitMask, new Object[]{"PPP", "hHw", "PPP", 'P', OrePrefixes.plate.get(Materials.Iron), 'H', GT_ModHandler.getModItem("malisisdoors", "item.rustyHandle", 1L)});
    // --- Garage Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "garage_door", 5L), tBitMask, new Object[]{"PRP", "RPR", "PRP", 'P', OrePrefixes.plate.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Steel)});
    // --- Rusty Handle
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.rustyHandle", 1L), tBitMask, new Object[]{"SRS", "RwR", "SRS", 'S', OrePrefixes.stick.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron)});
    // --- Rusty Ladder
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "rustyLadder", 1L), tBitMask, new Object[]{"RRR", "SwS", "RRR", 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.stick.get(Materials.Iron)});
    // --- Acacia Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 4)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Copper), 'R', OrePrefixes.ring.get(Materials.Copper), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 4)});
    // --- Birch Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 2)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Copper), 'R', OrePrefixes.ring.get(Materials.Copper), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 2)});
    // --- Dark Oak Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 5)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Copper), 'R', OrePrefixes.ring.get(Materials.Copper), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 5)});
    // --- Jungle Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 3)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Copper), 'R', OrePrefixes.ring.get(Materials.Copper), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 3)});
    // --- Spruce Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 1)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L), tBitMask, new Object[]{"SPP", "RPP", "dPP", 'S', OrePrefixes.screw.get(Materials.Copper), 'R', OrePrefixes.ring.get(Materials.Copper), 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 1)});
    // --- Trapdoor Acacia
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 1L), tBitMask, new Object[]{"PSP", "SSS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 4)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 2L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 4)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 3L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Steel), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 4)});
    // --- Trapdoor Spruce
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 1L), tBitMask, new Object[]{"PSP", "SSS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 1)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 2L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 1)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 3L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Steel), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 1)});
    // --- Trapdoor Birch
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 1L), tBitMask, new Object[]{"PSP", "SSS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 2)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 2L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 2)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 3L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Steel), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 2)});
    // --- Trapdoor Jungle
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 1L), tBitMask, new Object[]{"PSP", "SSS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 3)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 2L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 3)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 3L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Steel), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 3)});
    // --- Trapdoor Dark Oak
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 1L), tBitMask, new Object[]{"PSP", "SSS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 5)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 2L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Iron), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 5)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 3L), tBitMask, new Object[]{"PSP", "SRS", "PSP", 'S', OrePrefixes.stick.get(Materials.Wood), 'R', OrePrefixes.screw.get(Materials.Steel), 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 5)});
    // --- Wooden Glass Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', "paneGlassColorless", 'D', GT_ModHandler.getModItem("minecraft", "wooden_door", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), 'D', GT_ModHandler.getModItem("minecraft", "wooden_door", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', "paneGlassColorless", 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', "paneGlassColorless", 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', "paneGlassColorless", 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', "paneGlassColorless", 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', "paneGlassColorless", 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), 'D', GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L)});
    // --- Iron Glass Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.iron_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', "paneGlassColorless", 'D', GT_ModHandler.getModItem("minecraft", "iron_door", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.iron_sliding_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), 'D', GT_ModHandler.getModItem("minecraft", "iron_door", 1L)});
    // --- Jail Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.jail_door", 1L), tBitMask, new Object[]{" P ", "PDP", " s ", 'P', CoreItems2.getRecipe(SteelBars.getMetaID(), 1), 'D', GT_ModHandler.getModItem("minecraft", "iron_door", 1L)});
    // --- Laboratory Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.laboratory_door", 1L), tBitMask, new Object[]{" P ", "SDS", " h ", 'P', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.plate.get(Materials.Steel), 'D', GT_ModHandler.getModItem("minecraft", "iron_door", 1L)});
    // --- Factory Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.factory_door", 1L), tBitMask, new Object[]{" P ", "SDS", " h ", 'P', OrePrefixes.plate.get(Materials.Steel), 'S', OrePrefixes.plate.get(Materials.Iron), 'D', GT_ModHandler.getModItem("minecraft", "iron_door", 1L)});
    // --- Shoji Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.shoji_door", 1L), tBitMask, new Object[]{"PTS", "PTR", "PTd", 'P', GT_ModHandler.getModItem("minecraft", "paper", 1L), 'T', OrePrefixes.stick.get(Materials.Wood), 'S', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.shoji_door", 1L), tBitMask, new Object[]{"PTS", "PTR", "PTd", 'P', GT_ModHandler.getModItem("minecraft", "paper", 1L), 'T', OrePrefixes.stick.get(Materials.Wood), 'S', OrePrefixes.screw.get(Materials.Copper), 'R', OrePrefixes.ring.get(Materials.Copper)});
    // --- Curtain Purple
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_purple", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 10), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Yellow
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_yellow", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 4), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Magenta
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_magenta", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 2), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Pink
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_pink", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 6), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain White
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_white", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Blue
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_blue", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 11), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Gray
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_gray", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 7), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Cyan
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_cyan", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 9), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Red
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_red", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 14), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Brown
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_brown", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 12), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Lime
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_lime", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 5), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Orange
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_orange", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 1), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Light Gray
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_silver", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 8), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Green
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_green", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 13), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Light Blue
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_light_blue", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 3), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Curtain Black
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.curtain_black", 1L), tBitMask, new Object[]{"C C", "CSC", "C C", 'C', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 15), 'S', GT_ModHandler.getModItem("minecraft", "string", 1L)});
    // --- Saloon Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "item.saloon", 1L), tBitMask, new Object[]{" SW", "hPW", " SW", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', OrePrefixes.springSmall.get(Materials.Iron), 'W', OrePrefixes.plank.get(Materials.Wood)});
    // --- Arcania Fence Gate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 1L), tBitMask, new Object[]{"PSP", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 4), 'S', OrePrefixes.stick.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 1L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 4), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 2L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 4), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 4L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 4), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Steel)});
    // --- Birch Fence Gate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 1L), tBitMask, new Object[]{"PSP", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 2), 'S', OrePrefixes.stick.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 1L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 2), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 2L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 2), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 4L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 2), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Steel)});
    // --- Dark Oak Fence Gate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 1L), tBitMask, new Object[]{"PSP", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 5), 'S', OrePrefixes.stick.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 1L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 5), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 2L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 5), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 4L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 5), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Steel)});
    // --- Jungle Fence Gate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 1L), tBitMask, new Object[]{"PSP", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 3), 'S', OrePrefixes.stick.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 1L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 3), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 2L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 3), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 4L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 3), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Steel)});
    // --- Spruce Fence Gate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 1L), tBitMask, new Object[]{"PSP", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 1), 'S', OrePrefixes.stick.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 1L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 1), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 2L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 1), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 4L), tBitMask, new Object[]{"CdC", "PSP", "PSP", 'P', GT_ModHandler.getModItem("minecraft", "planks", 1L, 1), 'S', OrePrefixes.stick.get(Materials.Wood), 'C', OrePrefixes.screw.get(Materials.Steel)});

    /* ==== END MALISIS DOORS ==== */
    /** ==== START GRAVISUITE ==== */
    // --- Super Conductor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 1), tBitMask, new Object[]{"GGG", "SPS", "GGG", 'G', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L), 'S', OrePrefixes.wireGt02.get(Materials.SuperconductorLuV), 'P', OrePrefixes.plateTriple.get(Materials.Platinum)});
    // --- Super Conductor Cover
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 2L), tBitMask, new Object[]{"PPP", "III", "PPP", 'I', GT_ModHandler.getModItem("IC2", "itemPartIridium", 1L), 'P', OrePrefixes.plate.get(Materials.NiobiumTitanium)});
    // --- Engine Booster
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 6), tBitMask, new Object[]{"PCP", "PMP", "SVS", 'V', GT_ModHandler.getModItem("IC2", "reactorVentDiamond", 1L, 1), 'M', GT_ModHandler.getModItem("IC2", "upgradeModule", 1L), 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'C', OrePrefixes.circuit.get(Materials.Advanced)});
    // --- Gravitation Engine
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 3), tBitMask, new Object[]{"ESE", "COC", "ESE", 'S', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 1), 'C', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 2), 'E', ItemList.Emitter_IV, 'O', ItemList.Energy_LapotronicOrb});
    // --- Magnetron
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 4), tBitMask, new Object[]{"MCM", "PSP", "MCM", 'M', OrePrefixes.plateDense.get(Materials.NeodymiumMagnetic), 'P', OrePrefixes.plateDense.get(Materials.Copper), 'S', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 1), 'C', ItemList.IV_Coil});
    // --- Vajra Core
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 5), tBitMask, new Object[]{"SwS", "IMI", "STS", 'I', GT_ModHandler.getModItem("IC2", "itemPartIridium", 1L), 'M', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 4), 'S', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 1), 'T', ItemList.Transformer_LuV_IV});
    // --- Gravitational Engine 2
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 7), tBitMask, new Object[]{"ESE", "COC", "ESE", 'S', OrePrefixes.wireGt04.get(Materials.SuperconductorLuV), 'C', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 3), 'E', ItemList.Emitter_LuV, 'O', ItemList.Energy_LapotronicOrb2});
    // --- Cooling Core
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 2), tBitMask, new Object[]{"IHI", "RCR", "PHP", 'P', OrePrefixes.plateDouble.get(Materials.Inconel792), 'I', GT_ModHandler.getModItem("IC2", "itemPartIridium", 1L), 'H', GT_ModHandler.getModItem("IC2", "reactorHeatSwitchDiamond", 1L, 1), 'R', GT_ModHandler.getModItem("IC2", "reactorPlatingHeat", 1L), 'C', ItemList.Reactor_Coolant_He_6});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 2), tBitMask, new Object[]{"PHP", "RCR", "IHI", 'P', OrePrefixes.plateDouble.get(Materials.Inconel792), 'I', GT_ModHandler.getModItem("IC2", "itemPartIridium", 1L), 'H', GT_ModHandler.getModItem("IC2", "reactorHeatSwitchDiamond", 1L, 1), 'R', GT_ModHandler.getModItem("IC2", "reactorPlatingHeat", 1L), 'C', ItemList.Reactor_Coolant_He_6});
    // --- Epic Lappack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "epicLappack", 1L, 27), tBitMask, new Object[]{"PCP", "PUP", "SOS", 'P', OrePrefixes.itemCasing.get(Materials.HSSG), 'C', OrePrefixes.circuit.get(Materials.Superconductor), 'S', OrePrefixes.wireGt12.get(Materials.SuperconductorZPM), 'U', GT_ModHandler.getModItem("GraviSuite", "ultimateLappack", 1L, GT_Values.W), 'O', ItemList.Energy_LapotronicOrb2});
    // --- Vajra
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "vajra", 1L, 27), tBitMask, new Object[]{"HVL", "CIC", "SOS", 'S', OrePrefixes.stick.get(Materials.Trinium), 'C', OrePrefixes.circuit.get(Materials.Elite), 'L', OrePrefixes.lens.get(Materials.ReinforcedGlass), 'V', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 4), 'I', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 5), 'H', GT_ItemList.EngravedEnergyChip, 'O', CoreItems2.getRecipe(46, 1)});
    // --- GraviTool
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "graviTool", 1L), tBitMask, new Object[]{"AHA", "RBR", "WCT", 'A', "plateAlloyCarbon", 'B', "batteryElite", 'R', "plateAlloyAdvanced", 'C', OrePrefixes.circuit.get(Materials.Advanced), 'H', GT_ModHandler.getModItem("IC2", "itemToolHoe", 1L, GT_Values.W), 'W', GT_ModHandler.getModItem("IC2", "itemToolWrenchElectric", 1L, GT_Values.W), 'T', GT_ModHandler.getModItem("IC2", "itemTreetapElectric", 1L, GT_Values.W)});
    // --- Advanced Chainsaw
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advChainsaw", 1L), tBitMask, new Object[]{"SBd", "GMG", "PIP", 'P', OrePrefixes.plate.get(Materials.HSSG), 'G', OrePrefixes.gearGtSmall.get(Materials.HSSG), 'D', OrePrefixes.toolHeadChainsaw.get(Materials.TungstenSteel), 'S', OrePrefixes.screw.get(Materials.HSSG), 'I', GT_ModHandler.getModItem("IC2", "itemBatChargeLamaCrystal", 1L, GT_Values.W), 'M', ItemList.Electric_Motor_EV});
    // --- Advanced Nanochestplate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1L, 27), tBitMask, new Object[]{"AJA", "ANA", "WCW", 'A', "plateAlloyAdvanced", 'W', OrePrefixes.wireGt04.get(Materials.Platinum), 'C', OrePrefixes.circuit.get(Materials.Elite), 'J', GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1L, GT_Values.W), 'N', GT_ModHandler.getModItem("IC2", "itemArmorNanoChestplate", 1L, GT_Values.W)});
    // --- Advanced Jetpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1L, 27), tBitMask, new Object[]{"AJA", "ELE", "WCW", 'A', "plateAlloyAdvanced", 'W', OrePrefixes.wireGt04.get(Materials.Platinum), 'C', OrePrefixes.circuit.get(Materials.Data), 'J', GT_ModHandler.getModItem("IC2", "itemArmorJetpackElectric", 1L, GT_Values.W), 'L', GT_ModHandler.getModItem("GraviSuite", "advLappack", 1L, GT_Values.W), 'E', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 6)});
    // --- Ultimate Lappack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "ultimateLappack", 1L, 27), tBitMask, new Object[]{"PIP", "LAL", "CSC", 'P', OrePrefixes.itemCasing.get(Materials.TungstenSteel), 'C', OrePrefixes.circuit.get(Materials.Elite), 'I', GT_ModHandler.getModItem("IC2", "itemPartIridium", 1L), 'L', ItemList.LapotronCrystal.get(1), 'A', GT_ModHandler.getModItem("GraviSuite", "advLappack", 1L, GT_Values.W), 'S', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 1)});
    // --- Advanced Lappack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advLappack", 1, 27), new Object[]{"CEC", "EJE", "WPW", 'C', OrePrefixes.plateAlloy.get(Materials.Carbon), 'J', GT_ModHandler.getModItem("IC2","itemArmorEnergypack", 1L, GT_Values.W), 'E', ItemList.EnergyCrystal.get(1),  'W', OrePrefixes.wireGt04.get(Materials.Platinum), 'P', OrePrefixes.circuit.get(Materials.Data)});

    /* ==== END GRAVISUITE ==== */
    /** ==== START CARPENTERS BLOCKS ==== */
    // --- Carpenter's Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 2L), tBitMask, new Object[]{"FSF", "SdS", "FSF", 'F', OrePrefixes.frameGt.get(Materials.Wood), 'S', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 8L), tBitMask, new Object[]{"FSF", "FdF", "FSF", 'F', OrePrefixes.frameGt.get(Materials.Wood), 'S', OrePrefixes.screw.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 12L), tBitMask, new Object[]{"FSF", "FdF", "FSF", 'F', OrePrefixes.frameGt.get(Materials.Wood), 'S', OrePrefixes.screw.get(Materials.Steel)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 16L), tBitMask, new Object[]{"FSF", "FdF", "FSF", 'F', OrePrefixes.frameGt.get(Materials.Wood), 'S', OrePrefixes.screw.get(Materials.Aluminium)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 20L), tBitMask, new Object[]{"FSF", "FdF", "FSF", 'F', OrePrefixes.frameGt.get(Materials.Wood), 'S', OrePrefixes.screw.get(Materials.StainlessSteel)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 24L), tBitMask, new Object[]{"FSF", "FdF", "FSF", 'F', OrePrefixes.frameGt.get(Materials.Wood), 'S', OrePrefixes.screw.get(Materials.Titanium)});
    // --- Carpenter's Collapsible Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L), tBitMask, new Object[]{"SdS", "WWW", "SBS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'W', ItemList.Plank_Oak, 'S', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L), tBitMask, new Object[]{"SdS", "WWW", "SBS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'W', ItemList.Plank_Spruce, 'S', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L), tBitMask, new Object[]{"SdS", "WWW", "SBS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'W', ItemList.Plank_Birch, 'S', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L), tBitMask, new Object[]{"SdS", "WWW", "SBS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'W', ItemList.Plank_Jungle, 'S', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L), tBitMask, new Object[]{"SdS", "WWW", "SBS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'W', ItemList.Plank_Acacia, 'S', OrePrefixes.screw.get(Materials.Wood)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L), tBitMask, new Object[]{"SdS", "WWW", "SBS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'W', ItemList.Plank_DarkOak, 'S', OrePrefixes.screw.get(Materials.Wood)});
    // --- Carpenter's Chisel
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersChisel", 1L, 0), tBitMask, new Object[]{"dTs", "SIS", "BIB", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'T', GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 2), 'S', OrePrefixes.screw.get(Materials.Iron), 'I', OrePrefixes.stick.get(Materials.Iron)});
    // --- Carpenter's Hammer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersHammer", 1L, 0), tBitMask, new Object[]{"dTs", "SIS", "BIB", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'T', OrePrefixes.toolHeadHammer.get(Materials.Iron), 'S', OrePrefixes.screw.get(Materials.Iron), 'I', OrePrefixes.stick.get(Materials.Iron)});
    // --- Carpenter's Safe
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersSafe", 1L, 0), tBitMask, new Object[]{"SBS", "BIB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Iron), 'I', GT_ModHandler.getModItem("IC2", "blockPersonal", 1L, 0)});
    // --- Carpenter's Daylight Sensor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersDaylightSensor", 1L, 0), tBitMask, new Object[]{"SdS", "BMB", "BRB", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Iron), 'M', GT_ModHandler.getModItem("minecraft", "daylight_detector", 1L, 0), 'R', OrePrefixes.dust.get(Materials.Redstone)});
    // --- Carpenter's Bed
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersBed", 1L, 0), tBitMask, new Object[]{"MMM", "BBB", "BrB", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'M', GT_ModHandler.getModItem("minecraft", "carpet", 1L, 0)});
    // --- Carpenter's Torch
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersTorch", 2L, 0), tBitMask, new Object[]{"TBT", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'T', GT_ModHandler.getModItem("minecraft", "torch", 1L, 0)});
    // --- Carpenter's Garage Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGarageDoor", 5L, 0), tBitMask, new Object[]{"BRB", "RBR", "BRB", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'R', OrePrefixes.ring.get(Materials.Iron)});
    // --- Carpenter's Ladder
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersLadder", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("minecraft", "ladder", 1L, 0)});
    // --- Carpenter's Barrier
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBarrier", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("minecraft", "fence", 1L, 0)});
    // --- Carpenter's Pressure Plate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersPressurePlate", 2L, 0), tBitMask, new Object[]{"SrS", "BRB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'R', OrePrefixes.spring.get(Materials.Iron)});
    // --- Carpenter's Flower Pot
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersFlowerPot", 1L, 0), tBitMask, new Object[]{"BMB", " B ", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'M', GT_ModHandler.getModItem("minecraft", "flower_pot", 1L, 0)});
    // --- Carpenter's Gate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("minecraft", "fence_gate", 1L, 0)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 1L)});
    // --- Carpenter's Hatch
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("minecraft", "trapdoor", 1L, 0)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 1L)});
    // --- Carpenter's Lever
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersLever", 1L, 0), tBitMask, new Object[]{"BLS", " d ", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("minecraft", "lever", 1L, 0)});
    // --- Carpenter's Door
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("minecraft", "wooden_door", 1L, 0)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), tBitMask, new Object[]{"BLB", "SdS", 'B', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'L', GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L)});
    // --- Carpenter's Button
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersButton", 1L, 0), tBitMask, new Object[]{"sC", 'C', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersPressurePlate", 1L, 0)});
    // --- Carpenter's Slope
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersSlope", 2L, 0), tBitMask, new Object[]{"Cs", 'C', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0)});
    // --- Carpenter's Stairs
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersStairs", 1L, 0), tBitMask, new Object[]{"sC", 'C', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersSlope", 1L, 0)});
    // --- Carpenter's Tile
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersTile", 1L, 0), tBitMask, new Object[]{GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersPressurePlate", 1L, 0), ToolDictNames.craftingToolRollingPin, "itemClay"});

    /* ==== END CARPENTERS BLOCKS ==== */
    /** ==== START EXTRA UTILITIES ==== */
    // --- Mini Chest
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "chestMini", 1L), tBitMask, new Object[]{"hPs", "P P", " P ", 'P', OrePrefixes.plate.get(Materials.Wood)});
    // --- Slightly larger Chest
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 1L), tBitMask, new Object[]{"hPs", "PСP", " P ", 'P', OrePrefixes.plate.get(Materials.Wood), 'C', GT_ModHandler.getModItem("minecraft", "chest", 1L)});
    // --- Filing Cabinet
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "filing", 1L), tBitMask, new Object[]{"PUP", "SCS", "PUP", 'P', OrePrefixes.plate.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.Steel), 'U', GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 1L, 0), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 1)});
    // --- Advanced Filing Cabinet
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "filing", 1L, 1), tBitMask, new Object[]{"PUP", "SUS", "PUP", 'P', OrePrefixes.plateDouble.get(Materials.Gold), 'S', OrePrefixes.screw.get(Materials.Steel), 'U', GT_ModHandler.getModItem("ExtraUtilities", "filing", 1L, 0)});
    // --- Trash Can
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L), tBitMask, new Object[]{"PhP", "PBP", "PSP", 'S', "slimeball", 'P', OrePrefixes.plate.get(Materials.Iron), 'B', GT_ModHandler.getModItem("minecraft", "bucket", 1L, 0)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L), tBitMask, new Object[]{"PhP", "PBP", "PSP", 'S', GT_ModHandler.getModItem("IC2", "itemHarz", 1L), 'P', OrePrefixes.plate.get(Materials.Iron), 'B', GT_ModHandler.getModItem("minecraft", "bucket", 1L, 0)});
    // --- Watering Can
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "watering_can", 1L, 1), tBitMask, new Object[]{"hRd", "PPI", "PPS", 'I', OrePrefixes.stick.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.Steel), 'P', OrePrefixes.plate.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron)});
    // --- Redstone Clock
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "timer", 1L), tBitMask, new Object[]{"PGP", "GCG", "PGP", 'G', OrePrefixes.gear.get(Materials.Wood), 'C', GT_ModHandler.getModItem("minecraft", "clock", 1L), 'P', OrePrefixes.plate.get(Materials.RedAlloy)});
    // --- Angel Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "angelBlock", 1L), tBitMask, new Object[]{"FPF", "FOF", "FPF", 'O', OrePrefixes.stone.get(Materials.Obsidian), 'F', GT_ModHandler.getModItem("minecraft", "feather", 1L), 'P', OrePrefixes.plate.get(Materials.Gold)});
    // --- Golden Bag
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "golden_bag", 1L), tBitMask, new Object[]{"FDF", "PBP", "hPf", 'F', OrePrefixes.foil.get(Materials.Gold), 'D', OrePrefixes.gemExquisite.get(Materials.Diamond), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L), 'P', OrePrefixes.plate.get(Materials.Gold)});
    // --- Thickened Glass
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 0), tBitMask, new Object[]{GT_ModHandler.getModItem("TConstruct", "GlassBlock", 1L, 0)});

    /* ==== END EXTRA UTILITIES ==== */
    /** ==== START ARCHITECTURE CRAFT ==== */
    // --- Architecs Saw Bench
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ArchitectureCraft", "sawbench", 1L), tBitMask, new Object[]{"SPS", "BAB", "sLh", 'S', OrePrefixes.screw.get(Materials.Steel), 'P', GT_ModHandler.getModItem("minecraft", "heavy_weighted_pressure_plate", 1L), 'B', GT_ModHandler.getModItem("TConstruct", "trap.barricade.oak", 1L), 'A', GT_ModHandler.getModItem("ArchitectureCraft", "sawblade", 1L), 'L', GT_ModHandler.getModItem("ArchitectureCraft", "largePulley", 1L)});
    // --- Large Pulley
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ArchitectureCraft", "largePulley", 1L), tBitMask, new Object[]{"SdS", "GWG", "ShS", 'S', OrePrefixes.screw.get(Materials.Steel), 'G', OrePrefixes.gearGtSmall.get(Materials.Iron), 'W', OrePrefixes.gear.get(Materials.Wood)});
    // --- Architecs Hammer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ArchitectureCraft", "hammer", 1L), tBitMask, new Object[]{"PPI", "fSI", " Sh", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', OrePrefixes.plate.get(Materials.Steel), 'I', OrePrefixes.ingot.get(Materials.Iron)});
    // --- Architecs Chisel
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ArchitectureCraft", "chisel", 1L), tBitMask, new Object[]{"hIP", "SKI", "WSd", 'S', OrePrefixes.screw.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Steel), 'I', OrePrefixes.plate.get(Materials.Iron), 'K', OrePrefixes.stick.get(Materials.Iron), 'W', OrePrefixes.stick.get(Materials.Wood)});
    // --- Circular Saw
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("ArchitectureCraft", "sawblade", 1L, 0), tBitMask, new Object[]{OrePrefixes.toolHeadBuzzSaw.get(Materials.Iron)});

    /* ==== END ARCHITECTURE CRAFT ==== */
    /** ==== START BIBLIOCRAFT ==== */
    // --- Print Press Case
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioChase", 1L, 0), tBitMask, new Object[]{"SSS", "S S", "SSS", 'S', OrePrefixes.stick.get(Materials.WoodSealed)});
    // --- Typesetting Table
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "Typesetting Machine", 1L, 0), tBitMask, new Object[]{"PCP", "WFW", "WWW", 'W', OrePrefixes.plate.get(Materials.WoodSealed), 'F', OrePrefixes.frameGt.get(Materials.WoodSealed), 'P', OrePrefixes.plate.get(Materials.Aluminium), 'C', GT_ModHandler.getModItem("BiblioCraft", "item.BiblioChase", 1L, 0)});
    // --- Framed Chest
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "tile.BiblioframeGtdChest", 1L, 0), tBitMask, new Object[]{"FFF", "SLS", "FFF", 'F', GT_ModHandler.getModItem("BiblioCraft", "item.FramingSheet", 1L, 0), 'S', OrePrefixes.screw.get(Materials.Iron), 'L', GT_ModHandler.getModItem("BiblioCraft", "BiblioLabel", 1L, 6)});
    // --- Oak Furniture Paneler
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioPaneler", 1L, 0), tBitMask, new Object[]{"PsP", "WGW", "LLL", 'G', GT_ModHandler.getModItem("gregtech", "gt.metaitem.02", 1L, 32470), 'P', OrePrefixes.plate.get(Materials.Iron), 'W', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 0),  'L', GT_ModHandler.getModItem("minecraft", "planks", 1L, 0)});
    // --- Spruce Furniture Paneler
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioPaneler", 1L, 1), tBitMask, new Object[]{"PsP", "WGW", "LLL", 'G', GT_ModHandler.getModItem("gregtech", "gt.metaitem.02", 1L, 32471), 'P', OrePrefixes.plate.get(Materials.Iron), 'W', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 1),  'L', GT_ModHandler.getModItem("minecraft", "planks", 1L, 1)});
    // --- Birce Furniture Paneler
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioPaneler", 1L, 2), tBitMask, new Object[]{"PsP", "WGW", "LLL", 'G', GT_ModHandler.getModItem("gregtech", "gt.metaitem.02", 1L, 32472), 'P', OrePrefixes.plate.get(Materials.Iron), 'W', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 2),  'L', GT_ModHandler.getModItem("minecraft", "planks", 1L, 2)});
    // --- Jungle Furniture Paneler
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioPaneler", 1L, 3), tBitMask, new Object[]{"PsP", "WGW", "LLL", 'G', GT_ModHandler.getModItem("gregtech", "gt.metaitem.02", 1L, 32473), 'P', OrePrefixes.plate.get(Materials.Iron), 'W', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 3),  'L', GT_ModHandler.getModItem("minecraft", "planks", 1L, 3)});
    // --- Arcacia Furniture Paneler
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioPaneler", 1L, 4), tBitMask, new Object[]{"PsP", "WGW", "LLL", 'G', GT_ModHandler.getModItem("gregtech", "gt.metaitem.02", 1L, 32474), 'P', OrePrefixes.plate.get(Materials.Iron), 'W', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 4),  'L', GT_ModHandler.getModItem("minecraft", "planks", 1L, 4)});
    // --- Dark Oak Furniture Paneler
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioPaneler", 1L, 5), tBitMask, new Object[]{"PsP", "WGW", "LLL", 'G', GT_ModHandler.getModItem("gregtech", "gt.metaitem.02", 1L, 32475), 'P', OrePrefixes.plate.get(Materials.Iron), 'W', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, 5),  'L', GT_ModHandler.getModItem("minecraft", "planks", 1L, 5)});
    // --- Framed Furniture Paneler
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioPaneler", 1L, 6), tBitMask, new Object[]{"PsP", "WLW", "LLL", 'P', OrePrefixes.plate.get(Materials.Iron), 'W', GT_ModHandler.getModItem("BiblioCraft", "item.FramingBoard", 1L, 0),  'L', GT_ModHandler.getModItem("BiblioCraft", "FramingSheet", 1L, 0)});
    // --- Atlas
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.AtlasBook", 1L, 0), tBitMask, new Object[]{"PTP", "PBP", "PMP", 'P', OrePrefixes.plate.get(Materials.Paper), 'B', OreDictNames.craftingBook, 'M', "paperMap", 'T', GT_ModHandler.getModItem("BiblioCraft", "item.BiblioMapTool", 1L, 0)});
    // --- Painting Press
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioPaintPress", 1L, 0), tBitMask, new Object[]{"PPB", "R  ", "BIB", 'P', OrePrefixes.plate.get(Materials.Iron), 'B', OrePrefixes.bolt.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'I', OrePrefixes.block.get(Materials.Iron)});
    // --- Fancy Gold Latern
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioLantern", 1L, 0), tBitMask, new Object[]{"PDP", "GTG", "PPP", 'P', OrePrefixes.plate.get(Materials.Gold), 'D', OrePrefixes.dust.get(Materials.Glowstone), 'G', new ItemStack(Blocks.glass_pane), 'T', GT_ModHandler.getModItem("minecraft", "torch", 1L, 0)});
    // --- Fancy Iron Latern
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioIronLantern", 1L, 0), tBitMask, new Object[]{"PDP", "GTG", "PPP", 'P', OrePrefixes.plate.get(Materials.Iron), 'D', OrePrefixes.dust.get(Materials.Glowstone), 'G', new ItemStack(Blocks.glass_pane), 'T', GT_ModHandler.getModItem("minecraft", "torch", 1L, 0)});
    // --- Fancy Gold Lamp
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioLamp", 1L, 0), tBitMask, new Object[]{"PBP", " S ", "PPP", 'P', OrePrefixes.plate.get(Materials.Gold), 'S', OrePrefixes.stick.get(Materials.Gold), 'B', GT_ModHandler.getModItem("GregsLighting", "glowstoneBulb", 1L, 0)});
    // --- Fancy Iron Lamp
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioIronLamp", 1L, 0), tBitMask, new Object[]{"PBP", " S ", "PPP", 'P', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Iron), 'B', GT_ModHandler.getModItem("GregsLighting", "glowstoneBulb", 1L, 0)});
    // --- Armor Stand
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "Armor Stand", 1L, 0), tBitMask, new Object[]{"BSB", "hSs", "PMP", 'P', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Iron), 'B', OrePrefixes.bolt.get(Materials.Iron), 'M', GT_ModHandler.getModItem("minecraft", "stone_pressure_plate", 1L, 0)});
    // --- Desk Bell
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioBell", 1L, 0), tBitMask, new Object[]{" B ", " P ", "PhP", 'P', OrePrefixes.plate.get(Materials.Iron), 'B', GT_ModHandler.getModItem("minecraft", "stone_button", 1L, 0)});
    // --- Cookie Jar
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioStuffs", 1L, 0), tBitMask, new Object[]{" P ", "G G", "GGG", 'P', OrePrefixes.plate.get(Materials.Rubber), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L, 0)});
    // --- Diner Plate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioStuffs", 1L, 2), tBitMask, new Object[]{"P P", " P ", 'P', OrePrefixes.plate.get(Materials.NetherQuartz)});
    // --- Clock
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioClock", 1L, 6), tBitMask, new Object[]{"SCS", "SIS","SPS", 'S', GT_ModHandler.getModItem("BiblioCraft", "item.FramingSheet", 1L, 0), 'C', new ItemStack(Items.clock), 'I', new ItemStack(Items.stick), 'P', OrePrefixes.plate.get(Materials.Gold)});
    for (int i = 0; i < 6; i++) {
      // --- Frame Sheet
      GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.FramingSheet", 2L, 0), tBitMask, new Object[]{"Gs", 'G', new ItemStack(Blocks.planks, 1, i)});
      // --- Clock
      GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "BiblioClock", 1L, i), tBitMask, new Object[]{"SCS", "SIS","SPS", 'S', new ItemStack(Blocks.wooden_slab, 1, i), 'C', new ItemStack(Items.clock), 'I', new ItemStack(Items.stick), 'P', OrePrefixes.plate.get(Materials.Gold)});
    }
    // --- Frame Board
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.FramingBoard", 2L, 0), tBitMask, new Object[]{"Gs", 'G', GT_ModHandler.getModItem("BiblioCraft", "item.FramingSheet", 1L, 0)});
    // --- Drafting Compass
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioMapTool", 1L, 0), tBitMask, new Object[]{"IWI", "IIf", "IWd", 'I', OrePrefixes.stick.get(Materials.Iron), 'W', OrePrefixes.screw.get(Materials.Iron)});
    // --- Waypoint Compass
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioWayPointCompass", 1L, 0), tBitMask, new Object[]{"IRf", "WCW", "dRI", 'I', OrePrefixes.stick.get(Materials.Gold), 'W', OrePrefixes.screw.get(Materials.Gold), 'R', OrePrefixes.ring.get(Materials.Gold), 'C', GT_ModHandler.getModItem("minecraft", "compass", 1L, 0)});
    // --- Monocle
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioGlasses", 1L, 0), tBitMask, new Object[]{"IWI", "RBR", "LdL", 'I', OrePrefixes.stick.get(Materials.Iron), 'W', OrePrefixes.screw.get(Materials.Iron), 'B', OrePrefixes.bolt.get(Materials.Iron), 'R', OrePrefixes.ring.get(Materials.Iron), 'L', OrePrefixes.lens.get(Materials.Glass)});
    // --- Reading Glasses
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioGlasses", 1L, 2), tBitMask, new Object[]{"RWW", "LrW", " W ", 'W', OrePrefixes.wireFine.get(Materials.Gold), 'R', OrePrefixes.ring.get(Materials.Gold), 'L', OrePrefixes.lens.get(Materials.Glass)});
    // --- Plum Line
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.PlumbLine", 1L, 0), tBitMask, new Object[]{"WWW", "PrW", "R W", 'W', OrePrefixes.wireFine.get(Materials.Steel), 'R', OrePrefixes.round.get(Materials.Lead), 'P', OrePrefixes.plate.get(Materials.Lead)});
    // --- Screw Gun
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioDrill", 1L, 0), tBitMask, new Object[]{"IBd", "GRG", "PEP", 'I', OrePrefixes.screw.get(Materials.Iron), 'B', OrePrefixes.bolt.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'G', OrePrefixes.gearGtSmall.get(Materials.Iron), 'R', GT_ModHandler.getModItem("IC2", "itemRecipePart", 1L, 3), 'E', GT_ModHandler.getModItem("IC2", "itemBatREDischarged", 1L, 0)});
    // --- Tape Measure Real
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.tape", 1L, 0), tBitMask, new Object[]{"WWW", "WYW", "WWW", 'W', OrePrefixes.wireFine.get(Materials.Iron), 'B', "dyeYellow"});
    // --- Tape Maesure
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.tapeMeasure", 1L, 0), tBitMask, new Object[]{"TTT", "SSS", 'T', GT_ModHandler.getModItem("BiblioCraft", "item.tape", 1L, 0), 'S', OrePrefixes.stick.get(Materials.Iron)});
    // --- Clipboard
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioClipboard", 1L, 0), tBitMask, new Object[]{"ERE", "dGs", "PPP", 'E', OrePrefixes.screw.get(Materials.Iron), 'R', OrePrefixes.springSmall.get(Materials.Iron), 'G', GT_ModHandler.getModItem("gregtech", "gt.metaitem.01", 1L, 17809), 'P', GT_ModHandler.getModItem("minecraft", "paper", 1L, 0)});

    /* ==== END BIBLIOCRAFT ==== */
    /** ==== START AE2STUFF ==== */
    // --- Wireless Setup Kit
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ae2stuff", "WirelessKit", 1L, 0), tBitMask, new Object[]{"SWS", "PAP", "dIw", 'S', OrePrefixes.screw.get(Materials.Titanium), 'P', OrePrefixes.plate.get(Materials.Birmabright), 'I', OrePrefixes.stick.get(Materials.Birmabright), 'W', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 41), 'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ToolNetworkTool", 1L, 0)});
    // --- Network Visualisation Tool
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ae2stuff", "Visualiser", 1L, 0), tBitMask, new Object[]{"E S", "PMP", "PCP", 'P', OrePrefixes.plate.get(Materials.Birmabright), 'C', OrePrefixes.circuit.get(Materials.Data), 'E', ItemList.Emitter_EV, 'S', ItemList.Sensor_EV, 'M', GT_ModHandler.getModItem("OpenComputers", "hologram2", 1L)});
    // --- Pattern Encoder
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ae2stuff", "Encoder", 1L, 0), tBitMask,
            new Object[]{"POP", "ACA", "PEP", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel),
                    'O', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 340),
                    'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                    'C', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0),
                    'E', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16)});

    /* ==== END AE2STUFF ==== */
    /** ==== START Compact Generators ==== */
    // --- Kinetic Gearbox Rotor (Iridium)
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("compactkineticgenerators", "IridiumRotor", 1L, 0), tBitMask, new Object[]{"dBS", "BAB", "SBw", 'S', OrePrefixes.screw.get(Materials.Lafium), 'B', GT_ModHandler.getModItem("compactkineticgenerators", "IridiumBlade", 1L, 0), 'A', GT_ModHandler.getModItem("IC2", "itemRecipePart", 1L, 12)});
    // --- Iridium Rotor Blade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("compactkineticgenerators", "IridiumBlade", 1L, 0), tBitMask, new Object[]{"PPP", "PRP", "PPP", 'R', OrePrefixes.ring.get(Materials.Lafium), 'P', GT_ModHandler.getModItem("IC2", "itemPartIridium", 1L, 0)});

    /* ==== END Compact Generators ==== */
    /** ==== START Forestry ==== */
    // --- Apiarist's Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "apiaristBag", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Iron), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 0), 'C', GT_ModHandler.getModItem("Forestry", "apicultureChest", 1L, 0), 'W', "blockWool"});
    // --- Lepidopterist's Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "lepidopteristBag", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Iron), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 0), 'C', GT_ModHandler.getModItem("Forestry", "lepidopterology", 1L, 0), 'W', "blockWool"});
    // --- Miner's Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "minerBag", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Iron), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 0), 'C', OrePrefixes.plate.get(Materials.Iron), 'W', "blockWool"});
    // --- Digger's Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "diggerBag", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Iron), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 0), 'C', "stone", 'W', "blockWool"});
    // --- Forester's Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "foresterBag", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Iron), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 0), 'C', "logWood", 'W', "blockWool"});
    // --- Hunter's Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "hunterBag", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Iron), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 0), 'C', GT_ModHandler.getModItem("minecraft", "feather", 1L, 0), 'W', "blockWool"});
    // --- Builder's Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "builderBag", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Iron), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 0), 'C', GT_ModHandler.getModItem("minecraft", "fence", 1L, 0), 'W', "blockWool"});

    // --- Miner's Woven Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "minerBagT2", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Steel), 'B', GT_ModHandler.getModItem("Forestry", "minerBag", 1L, 0), 'C', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100), 'W', GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L, 0)});
    // --- Digger's Woven Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "diggerBagT2", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Steel), 'B', GT_ModHandler.getModItem("Forestry", "diggerBag", 1L, 0), 'C', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100), 'W', GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L, 0)});
    // --- Forester's Woven Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "foresterBagT2", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Steel), 'B', GT_ModHandler.getModItem("Forestry", "foresterBag", 1L, 0), 'C', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100), 'W', GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L, 0)});
    // --- Hunter's Woven Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "hunterBagT2", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Steel), 'B', GT_ModHandler.getModItem("Forestry", "hunterBag", 1L, 0), 'C', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100), 'W', GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L, 0)});
    // --- Builder's Woven Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Forestry", "builderBagT2", 1L, 0), tBitMask, new Object[]{"WRW", "WBW", "WCW", 'R', OrePrefixes.ring.get(Materials.Steel), 'B', GT_ModHandler.getModItem("Forestry", "builderBag", 1L, 0), 'C', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100), 'W', GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L, 0)});

    /* ==== END Forestry ==== */
    /** ==== START WIRELESS TERMINAL ==== */
    // --- Terminal
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ae2wct", "wirelessCraftingTerminal", 1L, 0), tBitMask, new Object[]{"SAE", "PTP", "PBP", 'P', OrePrefixes.plate.get(Materials.HastelloyN), 'E', ItemList.Emitter_IV, 'B', ItemList.BatteryHull_IV_Full, 'S', ItemList.Sensor_IV, 'T', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 360), 'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 41)});

    /* ==== END WIRELESS TERMINAL ==== */
    /** ==== START WR-CBE ==== */
    // --- Stone Bowl
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("WR-CBE|Core", "stoneBowl", 1L, 0), tBitMask, new Object[]{"P P", "PhP", "PPP", 'P', "stone"});
    // --- Remote
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("WR-CBE|Addons", "remote", 1L, 0), tBitMask, new Object[]{"dTf", "CMC", "PSP", 'P', OrePrefixes.itemCasing.get(Materials.TungstenSteel), 'S', OrePrefixes.screw.get(Materials.TungstenSteel), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'T', GT_ModHandler.getModItem("WR-CBE|Core", "wirelessTransceiver", 1L, 0), 'M', ItemList.Cover_Screen});
    // --- Wireless Sniffer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("WR-CBE|Addons", "sniffer", 1L, 0), tBitMask, new Object[]{"DTD", "PSP", "RRR", 'P', OrePrefixes.itemCasing.get(Materials.TungstenSteel), 'S', OrePrefixes.stick.get(Materials.TungstenSteel), 'D', GT_ModHandler.getModItem("WR-CBE|Core", "recieverDish", 1L, 0), 'T', GT_ModHandler.getModItem("WR-CBE|Core", "wirelessTransceiver", 1L, 0), 'R', GT_ModHandler.getModItem("ProjRed|Core", "projectred.core.part", 1L, 0)});
    // --- Private Sniffer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("WR-CBE|Addons", "psniffer", 1L, 0), tBitMask, new Object[]{"DTD", "PSP", "RRR", 'P', OrePrefixes.itemCasing.get(Materials.TungstenSteel), 'S', OrePrefixes.stick.get(Materials.TungstenSteel), 'D', GT_ModHandler.getModItem("WR-CBE|Core", "blazeRecieverDish", 1L, 0), 'T', GT_ModHandler.getModItem("WR-CBE|Core", "blazeTransceiver", 1L, 0), 'R', GT_ModHandler.getModItem("ProjRed|Core", "projectred.core.part", 1L, 0)});
    // --- Wireless Transmitter
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("WR-CBE|Logic", "wirelessLogic", 1L, 0), tBitMask, new Object[]{"dTS", "WWI", "RRR", 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'I', OrePrefixes.stick.get(Materials.Birmabright), 'T', GT_ModHandler.getModItem("WR-CBE|Core", "wirelessTransceiver", 1L, 0), 'R', GT_ModHandler.getModItem("ProjRed|Core", "projectred.core.part", 1L, 0)});
    // --- Wireless Receiver
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("WR-CBE|Logic", "wirelessLogic", 1L, 1), tBitMask, new Object[]{"dTS", "WWI", "RRR", 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'I', OrePrefixes.stick.get(Materials.Birmabright), 'T', GT_ModHandler.getModItem("WR-CBE|Core", "recieverDish", 1L, 0), 'R', GT_ModHandler.getModItem("ProjRed|Core", "projectred.core.part", 1L, 0)});
    // --- Wireless Jammer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("WR-CBE|Logic", "wirelessLogic", 1L, 2), tBitMask, new Object[]{"dTS", "WWI", "RRR", 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'I', OrePrefixes.stick.get(Materials.Birmabright), 'T', GT_ModHandler.getModItem("WR-CBE|Core", "blazeTransceiver", 1L, 0), 'R', GT_ModHandler.getModItem("ProjRed|Core", "projectred.core.part", 1L, 0)});

    /* ==== END WR-CBE ==== */
    /** ==== START ZTONES ==== */
    // --- Ztones Tile
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("minecraft", "stone_slab", 1L, 0), 'S', GT_ModHandler.getModItem("minecraft", "stone", 1L, 0)});
    // --- Mine Charcoal
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "minicharcoal", 7L, 0), tBitMask, new Object[]{"h", "C", 'C', "gemCharcoal"});
    // --- Mine Coal
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "minicoal", 7L, 0), tBitMask, new Object[]{"h", "C", 'C', "gemCoal"});
    // --- Aurora Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "auroraBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', "blockGlass", 'S', "dye"});
    // --- Korp Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "tile.korpBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), 'S', OrePrefixes.plate.get(Materials.Obsidian)});
    // --- Tank Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "tile.tankBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), 'S', OrePrefixes.plate.get(Materials.Iron)});
    // --- Sols Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "tile.solsBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), 'S', OrePrefixes.plate.get(Materials.Brass)});
    // --- Ztyl Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), 'S', OrePrefixes.plate.get(Materials.Steel)});
    // --- Vect Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "tile.vectBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), 'S', OrePrefixes.plate.get(Materials.Carbon)});
    // --- Jelt Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "tile.jeltBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), 'S', OrePrefixes.plate.get(Materials.Gold)});
    // --- Reed Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "tile.reedBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), 'S', OrePrefixes.plate.get(Materials.Wood)});
    // --- Vena Block
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "tile.venaBlock", 8L, 0), tBitMask, new Object[]{"LLL", "LSL", "LLL", 'L', GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), 'S', OrePrefixes.plate.get(Materials.Lapis)});
    // --- Ofanix
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Ztones", "ofanix", 1L, 0), tBitMask,
            new Object[]{"hSC", "SGS", "fSs", 'C', GT_ModHandler.getModItem("TConstruct", "CraftingSlab", 1L, 0),
                    'S', OrePrefixes.stick.get(Materials.Iron), 'G', OrePrefixes.gem.get(Materials.Diamond)});

    /* ==== END ZTONES ==== */
    /** ==== START CHICKENCHUNKS ==== */
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ChickenChunks", "chickenChunkLoader", 1L, 0), tBitMask, new Object[]{" h ", "BPB", "PFP", 'B', OrePrefixes.bolt.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Gold), 'F', OrePrefixes.frameGt.get(Materials.Iron)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ChickenChunks", "chickenChunkLoader", 9L, 1), tBitMask, new Object[]{"sC", 'C', GT_ModHandler.getModItem("ChickenChunks", "chickenChunkLoader", 1L, 0)});

    /* ==== END CHICKENCHUNKS ==== */
    /** ==== START IC2 ==== */
    // --- Frequency Transmitter
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemFreq", 1L, 0), tBitMask, new Object[]{" S ", "PBP", " C ", 'C', OrePrefixes.circuit.get(Materials.Basic), 'P', OrePrefixes.itemCasing.get(Materials.Iron), 'B', GT_ModHandler.getModItem("minecraft", "stone_button", 1L, 0), 'S', ItemList.Sensor_LV});
    // --- Heat Vent
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorVent", 1L, 1), tBitMask, new Object[]{"PBP", "BwB", "PBP", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'B', new ItemStack(Blocks.iron_bars)});
    // --- Reactor Heat Vent
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorVentCore", 1L, 1), tBitMask, new Object[]{"CSC", "SVS", "CSC", 'C', OrePrefixes.plateDouble.get(Materials.Copper), 'S', OrePrefixes.plate.get(Materials.Silver), 'V', GT_ModHandler.getModItem("IC2", "reactorVent", 1L, 1)});
    // --- Advanced Heat Vent
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorVentDiamond", 1L, 1), tBitMask, new Object[]{"BVB", "BDB", "BVB", 'D', OrePrefixes.gem.get(Materials.Diamond), 'V', GT_ModHandler.getModItem("IC2", "reactorVentCore", 1L, 1), 'B', CoreItems2.getRecipe(51, 1)});
    // --- Overclocked Heat Vent
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorVentGold", 1L, 1), tBitMask, new Object[]{"CSC", "SVS", "CSC", 'C', OrePrefixes.screw.get(Materials.StainlessSteel), 'S', OrePrefixes.plate.get(Materials.Gold), 'V', GT_ModHandler.getModItem("IC2", "reactorVentDiamond", 1L, 1)});
    // --- Component Heat Vent
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorVentSpread", 1L, 0), tBitMask, new Object[]{"CSC", "SVS", "CSC", 'C', CoreItems2.getRecipe(51, 1), 'S', OrePrefixes.plateDense.get(Materials.Tin), 'V', GT_ModHandler.getModItem("IC2", "reactorVent", 1L, 1)});
    // --- Heat Exchanger
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorHeatSwitch", 1L, 1), tBitMask, new Object[]{"COC", "SVS", "CSC", 'C', OrePrefixes.plate.get(Materials.Silver), 'S', OrePrefixes.plate.get(Materials.Aluminium), 'V', OrePrefixes.plate.get(Materials.Copper), 'O', OrePrefixes.circuit.get(Materials.Basic)});
    // --- Reactor Heat Exchanger
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorHeatSwitchCore", 1L, 1), tBitMask, new Object[]{"CSC", "SVS", "CSC", 'C', OrePrefixes.plateDouble.get(Materials.Copper), 'S', OrePrefixes.plate.get(Materials.Silver), 'V', GT_ModHandler.getModItem("IC2", "reactorHeatSwitch", 1L, 1)});
    // --- Component Heat Exchanger
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorHeatSwitchSpread", 1L, 1), tBitMask, new Object[]{"CSC", "SVS", "CSC", 'C', OrePrefixes.screw.get(Materials.StainlessSteel), 'S', OrePrefixes.plate.get(Materials.Gold), 'V', GT_ModHandler.getModItem("IC2", "reactorHeatSwitchCore", 1L, 1)});
    // --- Advanced Heat Exchanger
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "reactorHeatSwitchDiamond", 1L, 1), tBitMask, new Object[]{"LCL", "VDV", "LCL", 'L', OrePrefixes.plate.get(Materials.Lapis), 'D', OrePrefixes.plate.get(Materials.Diamond), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'V', GT_ModHandler.getModItem("IC2", "reactorHeatSwitchSpread", 1L, 1)});
    // --- Recrafting Iron Scaffold
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "blockIronScaffold", 1L), new Object[]{"L", 'L', OrePrefixes.frameGt.get(Materials.Steel)});
    // --- Recrafting Iron Scaffold
    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L), new Object[]{"L", 'L', GT_ModHandler.getModItem("IC2", "blockIronScaffold", 1L)});
    // --- Electric Treetap
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2","itemTreetapElectric",1, 26),bits,
            new Object[] {"dRD","RPB","ECS",'R', OrePrefixes.stickLong.get(Materials.Steel),'D', OrePrefixes.toolHeadDrill.get(Materials.Steel),
                    'P',ItemList.Electric_Pump_LV,'B',GT_ModHandler.getModItem("IC2","itemBatRE",1,GT_Values.W),
                    'E',GT_ModHandler.getModItem("IC2","itemRecipePart",1,3),'C', OrePrefixes.cableGt01.get(Materials.Tin),
                    'S', OrePrefixes.screw.get(Materials.Steel)});
    // --- Electric Hoe
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2","itemToolHoe",1, 26),bits,
            new Object[] {"dPH","PGB","ECS", 'S', OrePrefixes.screw.get(Materials.Steel),'H', OrePrefixes.toolHeadHoe.get(Materials.Steel),
                    'G', OrePrefixes.gearGtSmall.get(Materials.Steel),'B',GT_ModHandler.getModItem("IC2","itemBatRE",1,GT_Values.W),
                    'E',GT_ModHandler.getModItem("IC2","itemRecipePart",1,3), 'P', OrePrefixes.plate.get(Materials.Steel),
                    'C', OrePrefixes.cableGt01.get(Materials.Tin)});
    // --- Small Power Unit
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2","itemRecipePart",1, 3),bits,
            new Object[] {"WPW","PdP","CMC",'W', OrePrefixes.cableGt01.get(Materials.Copper),'P', OrePrefixes.itemCasing.get(Materials.Steel),
                    'M',ItemList.Electric_Motor_LV, 'C', OrePrefixes.circuit.get(Materials.Basic)});

    /* ==== END IC2 ==== */
    /** ==== START PROJECTRED ==== */
    // --- Workbench
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ProjRed|Fabrication", "projectred.integration.icblock", 1L), tBitMask, new Object[]{"PCP", "WFW", "WWW", 'P', OrePrefixes.plate.get(Materials.Iron), 'W', OrePrefixes.plate.get(Materials.Wood), 'F', GT_ModHandler.getModItem("Forestry", "factory2", 1L, 2), 'C', ItemList.Cover_Crafting});
    // --- Printer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ProjRed|Fabrication", "projectred.integration.icblock", 1L, 1), tBitMask, new Object[]{"EME", "LAL", "PFP", 'P', OrePrefixes.plate.get(Materials.Steel), 'L', "craftingLensRed", 'F', GT_ModHandler.getModItem("Forestry", "factory2", 1L, 2), 'M', ItemList.Cover_Screen, 'E', ItemList.Emitter_LV, 'A', ItemList.Machine_LV_Assembler});
    // --- Screwdriver
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ProjRed|Core", "projectred.core.screwdriver", 1L, 0), tBitMask, new Object[]{" fI", "BIh", "RB ", 'I', OrePrefixes.stick.get(Materials.Iron), 'R', OrePrefixes.stick.get(Materials.Rubber), 'B', "dyeBlue"});
    // --- Multimeter
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("ProjRed|Core", "projectred.core.wiredebugger", 1L, 0), tBitMask, new Object[]{"WSW", "PGP", "RdR", 'S', OrePrefixes.stick.get(Materials.SteelMagnetic), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'P', OrePrefixes.itemCasing.get(Materials.Steel), 'G', OrePrefixes.plate.get(Materials.Glowstone), 'R', OrePrefixes.screw.get(Materials.Steel)});
    // --- Insulated Red Alloy Wires
      for (int i = 0; i < 16; i++) {
          GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1L, 16 - i), tBitMask,
                  new Object[]{GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1L, 0),
                          GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1L, 0),
                          OrePrefixes.plate.get(Materials.Rubber), Dyes.VALUES[i]});
      }
    // --- Circuit Plate
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("ProjRed|Core", "projectred.core.part", 1L, 0),
            tBitMask,new Object[]{OrePrefixes.plate.get(Materials.Concrete)});

    /* ==== END PROJECTRED ==== */
    /** ==== START PRACTICAL LOGISTICS ==== */
    // --- Energy Reader
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "EnergyReader", 1L, 0), tBitMask, new Object[]{"SES", "TBT", "hRd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Cover_EnergyDetector, 'E', OrePrefixes.circuit.get(Materials.Basic), 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Fluid Reader
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "FluidReader", 1L, 0), tBitMask, new Object[]{"SES", "TBT", "hRd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Cover_FluidDetector, 'E', OrePrefixes.circuit.get(Materials.Basic), 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Item Reader
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "InventoryReader", 1L, 0), tBitMask, new Object[]{"SES", "TBT", "hRd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Cover_ItemDetector, 'E', OrePrefixes.circuit.get(Materials.Basic), 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Info Reader
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "InfoReader", 1L, 0), tBitMask, new Object[]{"SES", "TBT", "hRd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Cover_Screen, 'E', OrePrefixes.circuit.get(Materials.Basic), 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Data Modifier
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "DataModifier", 1L, 0), tBitMask, new Object[]{"SRS", "hBd", "STS", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Cover_Screen, 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Info Creator
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "InfoCreator", 1L, 0), tBitMask, new Object[]{"SRS", " T ", "hBd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Cover_Screen, 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Channel Selector
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "ChannelSelector", 1L, 0), tBitMask, new Object[]{"SRS", "hBd", "STS", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Cover_Screen, 'T', GT_ModHandler.getModItem("PracticalLogistics", "MultiCable", 1L)});
    // --- Holographic Display
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "HolographicDisplay", 1L, 0), tBitMask, new Object[]{" G ", "WCW", " T ", 'W', OrePrefixes.wireFine.get(Materials.Cobalt), 'C', OrePrefixes.circuit.get(Materials.Basic), 'G', "paneGlass", 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "HolographicDisplay", 1L, 0), tBitMask, new Object[]{" G ", "WCW", " T ", 'W', OrePrefixes.wireFine.get(Materials.Cobalt), 'C', OrePrefixes.circuit.get(Materials.Basic), 'G', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Item Router
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "ItemRouter", 1L, 0), tBitMask, new Object[]{"SES", "TBT", "hRd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', "craftingFilter", 'E', OrePrefixes.circuit.get(Materials.Basic), 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Entity Node
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "EntityNode", 1L, 0), tBitMask, new Object[]{"SRS", " T ", "hBd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Sensor_LV, 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Node
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "Node", 1L, 0), tBitMask, new Object[]{"SRS", " T ", "hBd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Conveyor_Module_LV, 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});
    // --- Transceiver Array
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("PracticalLogistics", "TransceiverArray", 1L, 0), tBitMask, new Object[]{"SRS", " T ", "hBd", 'S', OrePrefixes.screw.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'R', ItemList.Robot_Arm_LV, 'T', GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L)});

    /* ==== END PRACTICAL LOGISTICS ==== */
    /** ==== START GALACTICRAFT & GALAXYSPACE ==== */
    // --- Arc Lamp
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.arclamp", 1L, 0), tBitMask, new Object[]{"PRP", "CEL", "PRP", 'P', OrePrefixes.plate.get(Materials.BlackSteel), 'R', OrePrefixes.ring.get(Materials.BlackSteel), 'C', OrePrefixes.circuit.get(Materials.Good), 'L', OrePrefixes.lens.get(Materials.Glass), 'E', ItemList.Emitter_MV});
    // --- Light Oxygen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankLightFull", 1L, 1800), tBitMask, new Object[]{"PTP", "PCP", "PPP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'C', ItemList.Large_Fluid_Cell_Steel.get(1), 'T', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L)});
    // --- Medium Oxygen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankMedFull", 1L, 3600), tBitMask, new Object[]{"PTP", "PCP", "PPP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.meteoricIronIngot", 1L, 1), 'C', ItemList.Large_Fluid_Cell_Aluminium.get(1), 'T', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L)});
    // --- Heavy Oxygen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankHeavyFull", 1L, 5400), tBitMask, new Object[]{"PTP", "PCP", "PPP", 'P', GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 5), 'C', ItemList.Large_Fluid_Cell_StainlessSteel.get(1), 'T', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L)});
    // --- T4 Oxygen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankt4", 1L, 7200), tBitMask, new Object[]{"PTP", "PCP", "PPP", 'P', CoreItems2.getRecipe(84, 1), 'C', ItemList.Large_Fluid_Cell_Titanium.get(1), 'T', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L)});
    // --- T5 Oxygen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankt5", 1L, 9000), tBitMask, new Object[]{"PTP", "PCP", "PPP", 'P', OrePrefixes.plateTriple.get(Materials.Trinium), 'C', ItemList.Large_Fluid_Cell_TungstenSteel.get(1), 'T', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L)});
    // --- Fins 1
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.rocketFins", 1L), tBitMask, new Object[]{"SPS", "HPH", "HCH", 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'H', GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1L), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 4)});
    // --- Fins 2
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 2), tBitMask, new Object[]{"SPS", "HPH", "HPH", 'S', OrePrefixes.screw.get(Materials.TungstenSteel), 'P', GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 3), 'H', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L)});
    // --- Rocket Launch Pad
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.landingPad", 3L), tBitMask, new Object[]{"III", "PPP", "BBB", 'B', OrePrefixes.frameGt.get(Materials.StainlessSteel), 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'I', GT_ModHandler.getModItem("IC2", "itemPartAlloy", 1L)});
    // --- Buggy Fueling Pad
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.landingPad", 3L, 1), tBitMask, new Object[]{"III", "PPP", "BBB", 'B', OrePrefixes.block.get(Materials.Steel), 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'I', GT_ModHandler.getModItem("IC2", "itemPartAlloy", 1L)});
    // --- Oxygen Collector
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenCollector", 1L), tBitMask, new Object[]{"ACA", "VFM", "SWS", 'W', OrePrefixes.cableGt02.get(Materials.Aluminium), 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenConcentrator", 1L), 'V', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L), 'F', GT_ModHandler.getModItem("GalacticraftCore", "item.airFan", 1L), 'M', ItemList.Electric_Motor_HV});
    // --- Oxygen Compressor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenCompressor", 1L), tBitMask, new Object[]{"ACA", "PEM", "SBS", 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'B', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 10), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenConcentrator", 1L), 'E', GT_ModHandler.getModItem("GalacticraftCore", "item.oilCanisterPartial", 1L, GT_Values.W), 'M', ItemList.Electric_Motor_HV, 'P', ItemList.Electric_Piston_HV});
    // --- Oxygen Decompressor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenCompressor", 1L, 4), tBitMask, new Object[]{"ACA", "MEF", "SBS", 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'B', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 10), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenConcentrator", 1L), 'E', GT_ModHandler.getModItem("GalacticraftCore", "item.oilCanisterPartial", 1L, GT_Values.W), 'M', ItemList.Electric_Motor_HV, 'F', GT_ModHandler.getModItem("GalacticraftCore", "item.airFan", 1L)});
    // --- Oxygen Storage Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.machine2", 1L, 8), tBitMask, new Object[]{"STS", "TCT", "STS", 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'T', GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankHeavyFull", 1L, GT_Values.W), 'C', ItemList.Casing_SolidSteel});
    // --- Oxygen Bubble Distributor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.distributor", 1L), tBitMask, new Object[]{"AFA", "VMV", "SFS", 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'V', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L), 'F', GT_ModHandler.getModItem("GalacticraftCore", "item.airFan", 1L), 'M', ItemList.Electric_Motor_HV});
    // --- Oxygen Sealer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.sealer", 1L), tBitMask, new Object[]{"AVA", "VBV", "SDS", 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'V', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L), 'B', GT_ModHandler.getModItem("GalacticraftCore", "tile.distributor", 1L), 'D', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenDetector", 1L)});
    // --- Oxygen Detector
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenDetector", 1L), tBitMask, new Object[]{"SSS", "VEV", "AWA", 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'V', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L), 'E', ItemList.Sensor_HV});
    // --- Fuel Loader
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.fuelLoader", 1L), tBitMask, new Object[]{"SWS", "PTM", "AIA", 'I', OrePrefixes.pipeMedium.get(Materials.Steel), 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'W', OrePrefixes.circuit.get(Materials.Advanced), 'M', ItemList.Electric_Motor_HV, 'P', ItemList.Electric_Pump_HV, 'T', ItemList.Large_Fluid_Cell_Steel});
    // --- Cargo Loader
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.cargo", 1L), tBitMask, new Object[]{"AHA", "CBC", "SPS", 'P', OrePrefixes.pipeMedium.get(Materials.Brass), 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'H', GT_ModHandler.getModItem("minecraft", "hopper", 1L), 'C', ItemList.Conveyor_Module_HV, 'B', ItemList.Automation_ChestBuffer_HV});
    // --- Cargo Unloader
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.cargo", 1L, 4), tBitMask, new Object[]{"APA", "CBC", "SHS", 'P', OrePrefixes.pipeMedium.get(Materials.Brass), 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'H', GT_ModHandler.getModItem("minecraft", "hopper", 1L), 'C', ItemList.Conveyor_Module_HV, 'B', ItemList.Automation_ChestBuffer_HV});
    // --- Decorate Tin
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 5L, 4), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 7), 'S', "stone"});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 5L, 3), tBitMask, new Object[]{" Ph", "PSP", "wP ", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 7), 'S', "stone"});
    // --- Air Lock Frame
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.airLockFrame", 2L), tBitMask, new Object[]{"DSD", "VdV", "ASA", 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'D', GT_ModHandler.getModItem("GalacticraftCore", "item.meteoricIronIngot", 1L, 1), 'V', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L)});
    // --- Air Lock Controller
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.airLockFrame", 1L, 1), tBitMask, new Object[]{"PPP", "VAV", "WRW", 'P', OrePrefixes.plate.get(Materials.MeteoricSteel), 'R', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'W', OrePrefixes.circuit.get(Materials.Advanced), 'A', GT_ModHandler.getModItem("GalacticraftCore", "tile.airLockFrame", 1L), 'V', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L)});
    // --- Aluminium Wire
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L), tBitMask, new Object[]{OrePrefixes.cableGt01.get(Materials.Aluminium)});
    // --- Heavy Aluminium Wire
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 3L, 1), tBitMask, new Object[]{"AAA", "WWW", "AAA", 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'W', GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L)});
    // --- Spin Truster
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.spinThruster", 1L), tBitMask, new Object[]{"TTT", "CWC", "EPE", 'T', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 6), 'W', OrePrefixes.circuit.get(Materials.Advanced), 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1L), 'E', GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L), 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.fuelCanisterPartial", 1L, 1)});
    // --- Telemetry Unit
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.telemetry", 1L), tBitMask, new Object[]{"MPE", "WPW", "PCP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 7), 'W', OrePrefixes.circuit.get(Materials.Advanced), 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 6), 'M', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 19), 'E', ItemList.Emitter_HV});
    // --- Canvas
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.canvas", 1L), tBitMask, new Object[]{" WS", "WSW", "SW ", 'S', OrePrefixes.stick.get(Materials.Plastic), 'W', "blockWool"});
    // --- Oxygen Gear
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenGear", 1L), tBitMask, new Object[]{"PPP", "ECE", "PIP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L), 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenConcentrator", 1L), 'E', ItemList.Electric_Pump_HV, 'I', ItemList.Electric_Piston_HV});
    // --- Sensor Glasses
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.sensorGlasses", 1L), tBitMask, new Object[]{"CSC", "RBR", "LdL", 'L', GT_ModHandler.getModItem("GalacticraftCore", "item.sensorLens", 1L), 'C', OrePrefixes.circuit.get(Materials.Data), 'S', OrePrefixes.screw.get(Materials.MeteoricIron), 'R', OrePrefixes.ring.get(Materials.Desh), 'B', OrePrefixes.bolt.get(Materials.Desh)});
    // --- Oxygen Vent
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L), tBitMask, new Object[]{"BPB", "PSP", "BPB", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 7), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'B', CoreItems2.getRecipe(51, 1)});
    // --- Oxygen Fan
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.airFan", 1L), tBitMask, new Object[]{"SdS", "RIR", "SwS", 'S', OrePrefixes.screw.get(Materials.Steel), 'R', OrePrefixes.rotor.get(Materials.Steel), 'I', OrePrefixes.stickLong.get(Materials.StainlessSteel)});
    // --- Oxygen Concentrator
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenConcentrator", 1L), tBitMask, new Object[]{"SVS", "SFS", "CPC", 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'V', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L), 'F', GT_ModHandler.getModItem("GalacticraftCore", "item.airFan", 1L), 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.canister", 1L), 'P', ItemList.Electric_Pump_HV});
    // --- Tier 1 Rocket Engine
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L), tBitMask, new Object[]{"PEI", "SFS", "SRS", 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1L), 'F', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L), 'F', ItemList.Casing_Firebox_Steel, 'E', ItemList.Electric_Piston_HV, 'I', OrePrefixes.pipeTiny.get(Materials.StainlessSteel), 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'R', OrePrefixes.ring.get(Materials.StainlessSteel)});
    // --- Tier 1 Booster
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L, 1), tBitMask, new Object[]{"PPP", "HCH", "HVH", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.meteoricIronIngot", 1L, 1), 'H', GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1L), 'V', GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L), 'C', ItemList.Large_Fluid_Cell_Steel});
    // --- Nose Cone
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.noseCone", 1L), tBitMask, new Object[]{"dPh", "SES", "PCP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1L), 'E', ItemList.Sensor_HV, 'C', OrePrefixes.circuit.get(Materials.Advanced), 'S', OrePrefixes.screw.get(Materials.StainlessSteel)});
    // --- Oil Extractor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oilExtractor", 1L), tBitMask, new Object[]{"IdS", "SCP", "BPP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 10), 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.oilCanisterPartial", 1L, 1001), 'B', GT_ModHandler.getModItem("minecraft", "stone_button", 1L), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'I', OrePrefixes.pipeTiny.get(Materials.Steel)});
    // --- Buggy Wheel
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.buggymat", 1L), tBitMask, new Object[]{"PRP", "RTR", "PRP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'T', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 6), 'R', OrePrefixes.plate.get(Materials.Rubber)});
    // --- Buggy Seat
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.buggymat", 1L, 1), tBitMask, new Object[]{" RP", "RRP", "PPP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'R', OrePrefixes.plate.get(Materials.Rubber)});
    // --- Buggy Storage Box
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.buggymat", 1L, 2), tBitMask, new Object[]{"PRP", "PCP", "PPP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'R', GT_ModHandler.getModItem("IC2", "blockRubber", 1L), 'C', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 1)});
    // --- Frequency Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 19), tBitMask, new Object[]{"AEA", "WMW", "TBT", 'A', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 8), 'T', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 7), 'W', OrePrefixes.circuit.get(Materials.Advanced), 'M', GT_ModHandler.getModItem("PracticalLogistics", "DisplayScreenItem", 1L), 'E', ItemList.Sensor_HV, 'B', ItemList.Battery_RE_HV_Lithium});
    // --- Walk away
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.walkway", 1L), tBitMask, new Object[]{"PhP", " B ", "PwP", 'P', OrePrefixes.plate.get(Materials.Desh), 'B', OrePrefixes.frameGt.get(Materials.Desh)});
    // --- Walk away Aluminum
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.walkwayWire", 1L), tBitMask, new Object[]{"PhP", "WBW", "PwP", 'W', GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L, 1), 'P', OrePrefixes.plate.get(Materials.Desh), 'B', OrePrefixes.frameGt.get(Materials.Desh)});
    // --- Walk away Oxygen
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.walkwayOxygenPipe", 1L), tBitMask, new Object[]{"PhP", "WBW", "PwP", 'W', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L), 'P', OrePrefixes.plate.get(Materials.Desh), 'B', OrePrefixes.frameGt.get(Materials.Desh)});
    // --- Standard Wrench
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.standardWrench", 1L), tBitMask, new Object[]{"PsP", "SIS", "dIf", 'P', OrePrefixes.plate.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.Steel), 'I', OrePrefixes.stick.get(Materials.Steel)});
    // --- Heavy Rocket Engine (Tier 2)
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 1), tBitMask, new Object[]{"PIP", "PEP", "BOB", 'P', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L), 'B', GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L, 1), 'O', GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L), 'E', ItemList.Electric_Piston_IV, 'I', OrePrefixes.pipeTiny.get(Materials.TungstenSteel)});
    // --- Heavy Nose Cone
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.heavyNoseCone", 1L), tBitMask, new Object[]{"dNh", "SDS", "DPD", 'D', GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 5), 'P', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L), 'N', GT_ModHandler.getModItem("GalacticraftCore", "item.noseCone", 1L), 'S', OrePrefixes.screw.get(Materials.Titanium)});
    // --- Beam Core
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 8), tBitMask, new Object[]{"RPR", "PLP", "RPR", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 11), 'R', OrePrefixes.ring.get(Materials.RedAlloy), 'L', OrePrefixes.lens.get(Materials.Diamond)});
    // --- Energy Beam Reflector
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.beamReflector", 1L), tBitMask, new Object[]{"RBR", " S ", "PLP", 'B', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 8), 'R', OrePrefixes.ring.get(Materials.Desh), 'S', OrePrefixes.stick.get(Materials.Desh), 'P', OrePrefixes.plate.get(Materials.Desh), 'L', OrePrefixes.block.get(Materials.Desh)});
    // --- Energy Beam Receiver
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.beamReceiver", 1L), tBitMask, new Object[]{"RPR", "PBP", "RPR", 'R', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 7), 'P', OrePrefixes.ring.get(Materials.Desh), 'B', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 8)});
    // --- Cryogenic Chamber
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.marsMachine", 1L, 4), tBitMask, new Object[]{"RCR", "PBP", "ROR", 'P', GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 3), 'R', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L), 'C', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 2), 'B', GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersBed", 1L), 'O', GT_ModHandler.getModItem("minecraft", "clock", 1L)});
    // --- Terraformer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.marsMachine", 1L), tBitMask, new Object[]{"COC", "PGP", "MTI", 'C', GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 6), 'O', GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenConcentrator", 1L), 'T', GT_ItemList.Portable_Tank_HV.get(1L), 'G', ItemList.Casing_Gearbox_Titanium, 'M', ItemList.Electric_Motor_HV, 'I', ItemList.Electric_Pump_HV, 'P', OrePrefixes.plate.get(Materials.Desh)});
    // --- Launch Controller
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.marsMachine", 1L, 8), tBitMask, new Object[]{"SME", "PHP", "CWC", 'S', ItemList.Sensor_HV, 'E', ItemList.Emitter_HV, 'W', OrePrefixes.circuit.get(Materials.Advanced), 'M', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 19), 'H', ItemList.Hull_HV, 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'C', OrePrefixes.cableGt02.get(Materials.Aluminium)});
    // --- Grapple
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.grapple", 1L), tBitMask, new Object[]{"  B", "WWW", "R  ", 'R', OrePrefixes.ring.get(Materials.MeteoricSteel), 'W', OrePrefixes.wireFine.get(Materials.MeteoricIron), 'B', OrePrefixes.bolt.get(Materials.MeteoricSteel)});
    // --- Parachute
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.parachute", 1L), tBitMask, new Object[]{"CCC", "W W", "WWW", 'C', GT_ModHandler.getModItem("GalacticraftCore", "item.canvas", 1L), 'W', OrePrefixes.wireFine.get(Materials.Steel)});
    // --- Slabs
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 2L), tBitMask, new Object[]{"sB", 'B', GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 4)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 2L), tBitMask, new Object[]{"sB", 'B', GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 3)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 2L), tBitMask, new Object[]{"sB", 'B', GT_ModHandler.getModItem("GalacticraftCore", "tile.moonBlock", 1L, 4)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 2L), tBitMask, new Object[]{"sB", 'B', GT_ModHandler.getModItem("GalacticraftCore", "tile.moonBlock", 1L, 14)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 2L), tBitMask, new Object[]{"sB", 'B', GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, 4)});
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 2L), tBitMask, new Object[]{"sB", 'B', GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, 7)});
    // --- Battery
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.battery", 1L, 100), tBitMask, new Object[]{ItemList.Battery_Hull_LV});

    // --- Cobalt Boots
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_boots", 1L), tBitMask, new Object[]{"P P", "ChC", "C C", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0)});
    // --- Cobalt Chestplate
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_plate", 1L), tBitMask, new Object[]{"ChC", "CPC", "C C", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0)});
    // --- Cobalt Helmet
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_helmet", 1L), tBitMask, new Object[]{"CCC", "ChC", "P P", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0)});
    // --- Cobalt Leggings
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_leg", 1L), tBitMask, new Object[]{"CCC", "CPC", "ChC", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0)});
    // --- Plasma Sword
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.plasmasword", 1L), tBitMask, new Object[]{"PCP", "fCh", "PBW", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0), 'B', GT_ModHandler.getModItem("GalaxySpace", "item.LeadBattery", 1L, 100), 'W', GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L, 1)});
    // --- Plasma Pickaxe
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.plasmapickaxe", 1L), tBitMask, new Object[]{"CCC", "fSh", "PBW", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0), 'B', GT_ModHandler.getModItem("GalaxySpace", "item.LeadBattery", 1L, 100), 'W', GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L, 1), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.steelPole", 1L)});
    // --- Plasma Axe
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.plasmaaxe", 1L), tBitMask, new Object[]{"CCh", "CSP", "fBW", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0), 'B', GT_ModHandler.getModItem("GalaxySpace", "item.LeadBattery", 1L, 100), 'W', GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L, 1), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.steelPole", 1L)});
    // --- Plasma Hoe
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.plasmahoe", 1L), tBitMask, new Object[]{"CCh", "fSP", "WBP", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0), 'B', GT_ModHandler.getModItem("GalaxySpace", "item.LeadBattery", 1L, 100), 'W', GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L, 1), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.steelPole", 1L)});
    // --- Plasma Shovel
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.plasmashovel", 1L), tBitMask, new Object[]{"fCh", "PSP", "WBW", 'P', OrePrefixes.plate.get(Materials.Desh), 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 0), 'B', GT_ModHandler.getModItem("GalaxySpace", "item.LeadBattery", 1L, 100), 'W', GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L, 1), 'S', GT_ModHandler.getModItem("GalacticraftCore", "item.steelPole", 1L)});
    // --- Small Fuel Canister
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Modules", 1L, 3), tBitMask, new Object[]{"SdS", "CMC", "ShS", 'C', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 4), 'S', OrePrefixes.screw.get(Materials.HSLA), 'M', GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L)});
    // --- Blank Fuel Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L), tBitMask, new Object[]{"PRP", "PwP", "PRP", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 9), 'R', OrePrefixes.ring.get(Materials.Steel)});
    // --- Decorate Lead
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 3), 'S', "stone"});
    // --- Decorate Adamantite
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 1), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L), 'S', "stone"});
    // --- Decorate Cobaltum
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 2), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 1), 'S', "stone"});
    // --- Decorate Magnesium
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 3), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 4), 'S', "stone"});
    // --- Decorate Mithril
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 4), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 5), 'S', "stone"});
    // --- Decorate Nickel
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 5), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 6), 'S', "stone"});
    // --- Decorate Oriharukon
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 6), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 7), 'S', "stone"});
    // --- Decorate Platinum
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 7), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 8), 'S', "stone"});
    // --- Decorate Tungsten
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 8), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 9), 'S', "stone"});
    // --- Decorate Copper
    //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 5L, 9), tBitMask, new Object[]{"hP ", "PSP", " Pw", 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 6), 'S', "stone"});
    // --- Advanced Fuel Loader
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "advFuelLoader", 1L), tBitMask, new Object[]{"SWS", "PTM", "AIA", 'I', OrePrefixes.pipeMedium.get(Materials.TungstenSteel), 'S', GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 5), 'A', CoreItems2.getRecipe(IceCompressedPlate.getMetaID(), 1), 'W', OrePrefixes.circuit.get(Materials.Elite), 'M', ItemList.Electric_Motor_IV, 'P', ItemList.Electric_Pump_IV, 'T', ItemList.Large_Fluid_Cell_TungstenSteel});
    // --- Advanced Landing Pad
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "advlandingpad", 5L), tBitMask, new Object[]{"III", "PPP", "BBB", 'B', OrePrefixes.frameGt.get(Materials.TungstenSteel), 'P', GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 5), 'I', OrePrefixes.plate.get(Materials.MaragingSteel300)});
    // --- Gravity Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "gravitationmoduleon", 1L), tBitMask, new Object[]{"PLP", "CFC", "IHI", 'L', OrePrefixes.lens.get(Materials.Diamond), 'C', OrePrefixes.cableGt02.get(Materials.Aluminium), 'I', OrePrefixes.circuit.get(Materials.Data), 'F', ItemList.Field_Generator_HV, 'H', ItemList.Hull_EV, 'P', GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 4)});
    // --- Magnetic Field Generator
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "radiationstabiliser", 1L), tBitMask, new Object[]{"ACA", "WHF", "SCS", 'A', OrePrefixes.compressed.get(Materials.Aluminium), 'S', OrePrefixes.compressed.get(Materials.Steel), 'C', OrePrefixes.circuit.get(Materials.Data), 'W', OrePrefixes.cableGt02.get(Materials.Aluminium), 'F', ItemList.EV_Coil, 'H', ItemList.Hull_EV});
    // --- Very Heavy Oxygen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t4", 1L, 10800), tBitMask, new Object[]{"PTP", "PCP", "PPP", 'P', CoreItems2.getRecipe(79, 1), 'C', ItemList.Large_Fluid_Cell_Chrome.get(1), 'T', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L)});
    // --- Ultra Oxygen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t5", 1L, 12600), tBitMask, new Object[]{"PTP", "PCP", "PPP", 'P', CoreItems2.getRecipe(83, 1), 'C', ItemList.Large_Fluid_Cell_Iridium.get(1), 'T', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L)});
    // --- Very Ultra Oxygen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t6", 1L, 14400), tBitMask, new Object[]{"PTP", "PCP", "PPP", 'P', CoreItems2.getRecipe(85, 1), 'C', ItemList.Large_Fluid_Cell_Osmium.get(1), 'T', GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L)});
    // --- Module "Expander"
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L, 14), tBitMask, new Object[]{"SCS", "PIP", "RFR", 'S', OrePrefixes.stick.get(Materials.ElectricalSteel), 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'R', OrePrefixes.ring.get(Materials.ElectricalSteel), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'F', ItemList.Field_Generator_MV, 'C', ItemList.HV_Coil});
    // --- Module "Stabilization"
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L, 15), tBitMask, new Object[]{"SCS", "PIP", "RFR", 'S', OrePrefixes.stick.get(Materials.VanadiumSteel), 'P', OrePrefixes.plate.get(Materials.VanadiumSteel), 'R', OrePrefixes.ring.get(Materials.VanadiumSteel), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'F', ItemList.Field_Generator_MV, 'C', ItemList.Electric_Pump_HV});


    /* ==== END GALACTICRAFT & GALAXYSPACE ==== */
    /** ==== START OPENBLOCKS ==== */
    // --- Elevator
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("OpenBlocks", "elevator", 1L), tBitMask, new Object[]{"w h", "PHP", "GBG", 'P', OrePrefixes.plate.get(Materials.Steel), 'G', OrePrefixes.gearGtSmall.get(Materials.Steel), 'B', OrePrefixes.frameGt.get(Materials.Steel), 'H', new ItemStack(Blocks.heavy_weighted_pressure_plate)});
    // --- DevNull
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("OpenBlocks", "devnull", 1L), tBitMask, new Object[]{"dCh", "SBS", 'S', OrePrefixes.screw.get(Materials.Iron), 'B', OrePrefixes.frameGt.get(Materials.Iron), 'C', GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L)});

    /* ==== END OPENBLOCKS ==== */
    /** ==== START NUCLEAR CONTROL ==== */
    // --- Remote Sensor Kit
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("IC2NuclearControl", "ItemRemoteSensorKit", 1L, 0), tBitMask, new Object[]{GT_ModHandler.getModItem("IC2NuclearControl", "ItemSensorLocationCard", 1L, 0)});
    // --- Energy Sensor Kit
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("IC2NuclearControl", "ItemEnergySensorKit", 1L, 0), tBitMask, new Object[]{GT_ModHandler.getModItem("IC2NuclearControl", "ItemEnergySensorLocationCard", 1L, 0)});
    // --- Counter Sensor Kit
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("IC2NuclearControl", "ItemMultipleSensorKit", 1L, 0), tBitMask, new Object[]{GT_ModHandler.getModItem("IC2NuclearControl", "ItemMultipleSensorLocationCard", 1L, 0)});
    // --- Liquid Sensor Kit
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("IC2NuclearControl", "ItemMultipleSensorKit", 1L, 1), tBitMask, new Object[]{GT_ModHandler.getModItem("IC2NuclearControl", "ItemMultipleSensorLocationCard", 1L, 1)});
    // --- Generator Sensor Kit
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("IC2NuclearControl", "ItemMultipleSensorKit", 1L, 2), tBitMask, new Object[]{GT_ModHandler.getModItem("IC2NuclearControl", "ItemMultipleSensorLocationCard", 1L, 2)});
    // --- GregTech Sensor Kit
    GT_ModHandler.addShapelessCraftingRecipe(ItemList.NC_SensorKit.get(1), tBitMask, new Object[]{ItemList.NC_SensorCard});
    // --- AE Sensor Kit
    GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("IC2NuclearControl", "KitAppeng", 1L, 0), tBitMask, new Object[]{GT_ModHandler.getModItem("IC2NuclearControl", "CardAppeng", 1L, 0)});

    /* ==== END NUCLEAR CONTROL ==== */
    /** ==== START BACKPACK ==== */
    // --- Bound Leather
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Backpack", "boundLeather", 1L), tBitMask, new Object[]{"SLS", "LSL", "SLS", 'S', new ItemStack(Items.string), 'L', new ItemStack(Items.leather)});
    // --- Small Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Backpack", "backpack", 1L), tBitMask, new Object[]{"WRW", "LWL", "LLL", 'R', OrePrefixes.ring.get(Materials.Iron), 'L', GT_ModHandler.getModItem("Backpack", "boundLeather", 1L), 'W', new ItemStack(Blocks.wool)});
    // --- Medium Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100), tBitMask, new Object[]{"RLR", "LBL", "RLR", 'R', OrePrefixes.ring.get(Materials.WroughtIron), 'L', GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L)});
    // --- Big Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Backpack", "backpack", 1L, 200), tBitMask, new Object[]{"RLR", "LBL", "RLR", 'R', OrePrefixes.ring.get(Materials.Steel), 'L', new ItemStack(Blocks.wool), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100)});
    // --- Workbench Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Backpack", "workbenchbackpack", 1L, 17), tBitMask, new Object[]{"WRW", "LWL", "LBL", 'R', OrePrefixes.ring.get(Materials.Iron), 'L', GT_ModHandler.getModItem("Backpack", "boundLeather", 1L), 'W', new ItemStack(Blocks.wool), 'B', GT_ModHandler.getModItem("TConstruct", "CraftingSlab", 1L)});
    // --- Workbench Big Backpack
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Backpack", "workbenchbackpack", 1L, 217), tBitMask, new Object[]{"RLR", "LBL", "RCR", 'R', OrePrefixes.ring.get(Materials.Steel), 'L', new ItemStack(Blocks.wool), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100), 'C', GT_ModHandler.getModItem("TConstruct", "CraftingSlab", 1L)});

    for (int i = 0; i < 16; i++) {
      // --- Small Backpack*
      GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("Backpack", "backpack", 1L, i + 1), tBitMask, new Object[]{Dyes.VALUES[i], GT_ModHandler.getModItem("Backpack", "backpack", 1L)});
      // --- Medium Backpack*
      GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("Backpack", "backpack", 1L, i + 101), tBitMask, new Object[]{Dyes.VALUES[i], GT_ModHandler.getModItem("Backpack", "backpack", 1L, 100)});
      GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Backpack", "backpack", 1L, i + 101), tBitMask, new Object[]{"RLR", "LBL", "RLR", 'R', OrePrefixes.ring.get(Materials.WroughtIron), 'L', GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, i + 1)});
      // --- Big Backpack*
      GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("Backpack", "backpack", 1L, i + 201), tBitMask, new Object[]{Dyes.VALUES[i], GT_ModHandler.getModItem("Backpack", "backpack", 1L, 200)});
      GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Backpack", "backpack", 1L, i + 201), tBitMask, new Object[]{"RLR", "LBL", "RLR", 'R', OrePrefixes.ring.get(Materials.Steel), 'L', new ItemStack(Blocks.wool), 'B', GT_ModHandler.getModItem("Backpack", "backpack", 1L, i + 101)});
    }
    /* ==== END BACKPACK ==== */
    /** ==== START GENDUSTRY ==== */
    // --- Genetic Imprinter
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "Imprinter", 1L), tBitMask, new Object[]{"PGP", "BCB", "GMG", 'G', OrePrefixes.gear.get(Materials.Osmium), 'C', ItemList.Hull_LuV, 'B', GT_ModHandler.getModItem("gendustry", "BeeReceptacle", 1L), 'P', GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 1L), 'M', GT_ModHandler.getModItem("gendustry", "PowerModule", 1L)});
    // --- Genetic Sampler
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "Sampler", 1L), tBitMask, new Object[]{"PBP", "ICI", "GMG", 'G', OrePrefixes.gear.get(Materials.Osmium), 'C', ItemList.Hull_LuV, 'B', GT_ModHandler.getModItem("gendustry", "BeeReceptacle", 1L), 'P', GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 1L), 'M', GT_ModHandler.getModItem("gendustry", "PowerModule", 1L), 'I', GT_ItemList.EngravedDiamondCrystalChip});
    // --- Protein Liquifier
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "Liquifier", 1L), tBitMask, new Object[]{"GIG", "PCP", "GMG", 'G', OrePrefixes.gear.get(Materials.Osmium), 'C', ItemList.Hull_LuV, 'M', GT_ModHandler.getModItem("gendustry", "PowerModule", 1L), 'I', ItemList.Conveyor_Module_LuV, 'P', ItemList.Electric_Piston_LuV});
    // --- DNA Extractor
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "Extractor", 1L), tBitMask, new Object[]{"IBI", "PCP", "GMG", 'G', OrePrefixes.gear.get(Materials.Osmium), 'C', ItemList.Hull_LuV, 'B', GT_ModHandler.getModItem("gendustry", "BeeReceptacle", 1L), 'P', GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 1L), 'M', GT_ModHandler.getModItem("gendustry", "PowerModule", 1L), 'I', ItemList.Electric_Piston_LuV});
    // --- Genetic Transposer
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "Transposer", 1L), tBitMask, new Object[]{"IGI", "PCP", "GMG", 'G', OrePrefixes.gear.get(Materials.Osmium), 'C', ItemList.Hull_LuV, 'P', GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 1L), 'M', GT_ModHandler.getModItem("gendustry", "PowerModule", 1L), 'I', ItemList.Robot_Arm_LuV});
    // --- Genetic Replicator
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "Replicator", 1L), tBitMask, new Object[]{"IPI", "MCM", "GPG", 'G', OrePrefixes.gear.get(Materials.Osmium), 'C', ItemList.Hull_LuV, 'P', GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 1L), 'M', GT_ModHandler.getModItem("gendustry", "PowerModule", 1L), 'I', ItemList.Robot_Arm_LuV});
    // --- Bee Receptacle
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "BeeReceptacle", 1L), tBitMask, new Object[]{"SPS", "CIC", "SdS", 'S', OrePrefixes.screw.get(Materials.HSSS), 'P', OrePrefixes.plate.get(Materials.HSSG), 'C', CoreItems2.getRecipe(52, 1), 'I', CoreItems2.getRecipe(146, 1)});
    // --- Power Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "PowerModule", 1L), tBitMask, new Object[]{"WPW", "CIC", "GMG", 'W', OrePrefixes.wireGt02.get(Materials.VanadiumGallium), 'P', OrePrefixes.plate.get(Materials.HSSG), 'C', CoreItems2.getRecipe(52, 1), 'I', OrePrefixes.circuit.get(Materials.Master), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'M', ItemList.Electric_Motor_LuV});
    // --- Plains Emulation Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 17), tBitMask, new Object[]{"HCH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'P', GT_ModHandler.getModItem("gendustry", "EnvProcessor", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.grass)});
    // --- Light Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 11), tBitMask, new Object[]{"HSH", "SUS", "GSG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'S', GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 7), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L)});
    // --- Flowering Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 2), tBitMask, new Object[]{"HRH", "YUY", "GRG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'R', new ItemStack(Blocks.red_flower), 'Y', new ItemStack(Blocks.yellow_flower), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L)});
    // --- Winter Emulation Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 20), tBitMask, new Object[]{"HCH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'P', GT_ModHandler.getModItem("gendustry", "EnvProcessor", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.ice)});
    // --- Dryer Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 5), tBitMask, new Object[]{"HCH", "OUO", "GOG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.sand)});
    // --- Humidifier Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 4), tBitMask, new Object[]{"HCH", "OUO", "GOG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.cactus)});
    // --- Hell Emulation Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 13), tBitMask, new Object[]{"HCH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'P', GT_ModHandler.getModItem("gendustry", "EnvProcessor", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.netherrack)});
    // --- Desert Emulation Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 16), tBitMask, new Object[]{"HCH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'P', GT_ModHandler.getModItem("gendustry", "EnvProcessor", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.sandstone)});
    // --- Jungle Emulation Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 18), tBitMask, new Object[]{"HCH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'P', GT_ModHandler.getModItem("gendustry", "EnvProcessor", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.vine)});
    // --- Ocean Emulation Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 21), tBitMask, new Object[]{"HCH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'P', GT_ModHandler.getModItem("gendustry", "EnvProcessor", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Items.water_bucket)});
    // --- Cooler Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 7), tBitMask, new Object[]{"HCH", "OUO", "GOG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Items.snowball)});
    // --- Automation Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 14), tBitMask, new Object[]{"HCH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'C', OrePrefixes.gear.get(Materials.BT6), 'P', ItemList.Electric_Piston_EV, 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.redstone_torch)});
    // --- Pollen Scrubber Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 22), tBitMask, new Object[]{"HPH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', CoreItems2.getRecipe(51, 1), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', OrePrefixes.rotor.get(Materials.Titanium)});
    // --- Live Spawn Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 1), tBitMask, new Object[]{"HPH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', new ItemStack(Items.flower_pot), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Items.clock)});
    // --- Seal Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 10), tBitMask, new Object[]{"HPH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', OrePrefixes.plate.get(Materials.Rubber), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', "itemBeeswax"});
    // --- Genetics Stabilizer Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 19), tBitMask, new Object[]{"HPH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', OrePrefixes.plate.get(Materials.RedAlloy)});
    // --- Territory Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 3), tBitMask, new Object[]{"HPH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', OrePrefixes.plate.get(Materials.Iron), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', OrePrefixes.plate.get(Materials.Diamond)});
    // --- Open Sky Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 12), tBitMask, new Object[]{"HPH", "OUO", "GOG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', GT_ModHandler.getModItem("ProjRed|Illumination", "projectred.illumination.lamp", 1L, 27), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L)});
    // --- Heater Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 6), tBitMask, new Object[]{"HPH", "OUO", "GOG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', new ItemStack(Blocks.netherrack)});
    // --- Sieve Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, 15), tBitMask, new Object[]{"HPH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', GT_ModHandler.getModItem("Forestry", "craftingMaterial", 1L, 3), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', CoreItems2.getRecipe(51, 1)});
    // --- Production Upgrade
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L), tBitMask, new Object[]{"HPH", "OUO", "GPG", 'H', OrePrefixes.gearGtSmall.get(Materials.HastelloyC276), 'G', OrePrefixes.gearGtSmall.get(Materials.Osmium), 'P', new ItemStack(Items.sugar), 'U', GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L), 'O', GT_ModHandler.getModItem("Forestry", "royalJelly", 1L)});
    // --- Industrial Grafter
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "IndustrialGrafter", 1L, 100), tBitMask, new Object[]{"PdI", "GAS", "MBP", 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'A', OrePrefixes.stickLong.get(Materials.StainlessSteel), 'G', OrePrefixes.gearGtSmall.get(Materials.StainlessSteel), 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'M', ItemList.Electric_Motor_LV, 'B', ItemList.Battery_RE_LV_Lithium, 'I', GT_ModHandler.getModItem("Forestry", "grafter", 1L)});
    // --- Industrial Scoop
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "IndustrialScoop", 1L, 100), tBitMask, new Object[]{"PdI", "GAS", "MBP", 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'A', OrePrefixes.stickLong.get(Materials.StainlessSteel), 'G', OrePrefixes.gearGtSmall.get(Materials.StainlessSteel), 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'M', ItemList.Electric_Motor_LV, 'B', ItemList.Battery_RE_LV_Lithium, 'I', new ItemStack(Blocks.wool)});
    // --- Climate Control Module
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L), tBitMask, new Object[]{"SIS", "GBG", "PMP", 'S', OrePrefixes.plate.get(Materials.HSSG), 'G', OrePrefixes.stick.get(Materials.Palladium), 'P', CoreItems2.getRecipe(52, 1), 'M', ItemList.Electric_Motor_LuV, 'B', OrePrefixes.rotor.get(Materials.Osmium), 'I', ItemList.LuV_Coil});
    // --- Mutagen Tank
    GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("gendustry", "MutagenTank", 1L), tBitMask, new Object[]{"SPS", "CIC", "SdS", 'S', OrePrefixes.screw.get(Materials.HSSS), 'P', OrePrefixes.plate.get(Materials.HSSG), 'C', CoreItems2.getRecipe(52, 1), 'I', ItemList.Large_Fluid_Cell_TungstenSteel});

    /* ==== END GENDUSTRY ==== */

  }


  @Override
  public void run() {
    run1();
    run2();
  }
}