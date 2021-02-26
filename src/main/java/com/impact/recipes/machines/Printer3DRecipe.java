package com.impact.recipes.machines;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static com.impact.loader.ItemRegistery.lscLapotronicEnergyUnit;

import com.impact.common.item.Core_Items2;
import com.impact.mods.GregTech.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class Printer3DRecipe implements Runnable {

  final Core_Items2 CoreItems2 = Core_Items2.getInstance();

  @Override
  public void run() {
/** ================================= start IMPACT MOD =================================*/
    //Controllers
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.GumMetal, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.GumMetal, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Titaniolum, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titaniolum, 16),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_PBE.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Zamak, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L), ItemList.Robot_Arm_IV.get(4L),
        ItemList.Emitter_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Zamak, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Duraluminium, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Duraluminium, 16),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_LaserEngraver.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel690, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L), ItemList.Robot_Arm_IV.get(4L),
        ItemList.Emitter_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Inconel690, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Nitinol, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Nitinol, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Assembler.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Motor_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Inconel792, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.TiBetaC, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TiBetaC, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Centrifuge.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyC276, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyC276, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Zamak, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Zamak, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Electrolyzer.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titaniolum, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Conveyor_Module_IV.get(4L), ItemList.Electric_Piston_IV.get(4L),
        ItemList.Robot_Arm_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titaniolum, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Inconel690, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Inconel690, 16),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Wire.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.MaragingSteel250, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Motor_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel250, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Inconel792, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Inconel792, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Supply.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L), ItemList.Electric_Pump_IV.get(4L),
        ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Nitinol, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.HastelloyC276, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HastelloyC276, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Utility.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Talonite, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L), ItemList.Electric_Pump_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Talonite, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Grisium, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Grisium, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Brewmenter.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Nitinol, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Inconel690, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Inconel690, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Siftarator.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol60, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Nitinol60, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Kovar, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Kovar, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_ArcFurnace.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TiBetaC, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L), ItemList.Electric_Pump_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TiBetaC, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Talonite, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Talonite, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Extradifier.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titaniolum, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Motor_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titaniolum, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.MaragingSteel250, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.MaragingSteel250, 16),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Mixer.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyC276, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Piston_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyC276, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Mangalloy, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Mangalloy, 16),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Macerator.get(1L), null, 40 * 20, 7680);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.MaragingSteel300, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
        ItemList.Electric_Motor_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Stellite, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Stellite, 16),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_Cutting.get(1L), null, 40 * 20, 7680);

    //UpgradeCasingT1
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L),
        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1, 3),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.GumMetal, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Nitinol, 16),
        ItemList.Electric_Motor_IV.get(4L), ItemList.Electric_Piston_IV.get(4L),
        ItemList.Conveyor_Module_IV.get(4L), ItemList.Robot_Arm_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Graphene, 16),
        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16)
    }, GT_ItemList.UpgradeCasingT1.get(1L), null, 80 * 20, 7680);

    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 500),
        ItemList.Cover_ItemDetector.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol, 8),
        ItemList.Robot_Arm_IV.get(8L), GT_ItemList.DDDPrinterCasing3x3.get(9),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 16),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 16),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.GumMetal, 32),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64)
    }, GT_ItemList.DDDPrinterCasing4x4.get(16L), null, 200 * 20, 1920);

    //Machine_BlastSmelter
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Machine_Multi_BlastFurnace.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HSLA, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Kanthal, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSLA, 6),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 16)
    }, GT_ItemList.Machine_BlastSmelter.get(1L), null, 40 * 20, 1920);

    //Machine_FreezSolidifier
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Machine_Multi_VacuumFreezer.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.BlueSteel, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.StainlessSteel, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Aluminium, 6),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 16)
    }, GT_ItemList.Machine_FreezSolidifier.get(1L), null, 40 * 20, 1920);

    //AdvancedFreezer
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_ZPM.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lafium, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 6),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iridium, 8),
        ItemList.Electric_Pump_ZPM.get(4L), ItemList.Robot_Arm_ZPM.get(4L),
        ItemList.Reactor_Coolant_Le_3.get(1L), ItemList.Reactor_Coolant_Le_3.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Enderium, 8),
        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Osmiridium, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64)
    }, GT_ItemList.AdvVacuumFreezer.get(1L), null, 200 * 20, 122880);

    //LapatronicSupercapacitor
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L), ItemList.Battery_TurboCharger_4by4_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.HastelloyC276, 6),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        ItemList.Circuit_Chip_HPIC.get(16L),
        GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Platinum, 16),
        ItemList.Reactor_Coolant_He_6.get(1L), ItemList.Reactor_Coolant_He_6.get(1L),
        ItemList.Electric_Pump_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.pipeSmall, Materials.Inconel690, 8),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Kovar, 16),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 32),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 64)
    }, GT_ItemList.LapPowerStation.get(1L), null, 200 * 20, 4096);

    //LapotronicCapacitorIV
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.Energy_LapotronicOrb.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Platinum, 8),
        ItemList.Circuit_Chip_PIC.get(12L),
        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.TungstenSteel, 32),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HastelloyC276, 64),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 64)
    }, new ItemStack(lscLapotronicEnergyUnit, 1, 1), null, 100 * 20, 7680);

    //Energium Orb Capacitor
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.EnergyCrystal_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Platinum, 8),
        ItemList.Circuit_Chip_PIC.get(12L),
        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.TungstenSteel, 32),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HastelloyC276, 64),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 64)
    }, new ItemStack(lscLapotronicEnergyUnit, 1, 6), null, 100 * 20, 7680);

    //LapotronicCapacitorLuV
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.Energy_LapotronicOrb2.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 8),
        ItemList.Circuit_Chip_HPIC.get(12L),
        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 32),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HastelloyN, 64),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 64)
    }, new ItemStack(lscLapotronicEnergyUnit, 1, 2), null, 120 * 20, 30720);

    //LapotronicCapacitorZPM
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.Energy_Module.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Osmiridium, 8),
        ItemList.Circuit_Chip_UHPIC.get(12L),
        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Naquadah, 32),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Lafium, 64),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 64)
    }, new ItemStack(lscLapotronicEnergyUnit, 1, 3), null, 140 * 20, 122880);

    //LapotronicCapacitorUV
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.Energy_Cluster.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NaquadahAlloy, 8),
        ItemList.Circuit_Chip_NPIC.get(12L),
        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Duranium, 32),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.CinobiteA243, 64),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tritanium, 64)
    }, new ItemStack(lscLapotronicEnergyUnit, 1, 4), null, 160 * 20, 500000);

    //LapotronicCapacitorUHV
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.ZPM2.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Americium, 8),
        ItemList.Circuit_Chip_PPIC.get(12L),
        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 32),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Pikyonium64B, 64),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Neutronium, 64)
    }, new ItemStack(lscLapotronicEnergyUnit, 1, 5), null, 180 * 20, 2000000);

    //AdvancedPyrolyseOven
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        GT_ItemList.Pyrolyse.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HSLA, 2),
        ItemList.Electric_Pump_EV.get(2L),
        ItemList.Electric_Piston_EV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_OreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.StainlessSteel, 4),
        GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Kanthal, 6),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSLA, 6),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 16)
    }, GT_ItemList.AdvPyrolyse.get(1L), null, 60 * 20, 1920);

    //Naquadah Chamber Part
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.NaquadahAlloy, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.GumMetal, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Desh, 8),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.TungstenCarbide, 16),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Talonite, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 32)
    }, CoreItems2.getRecipe(163, 1), null, 30 * 20, 7680);

    //Tachyon Tube
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        CoreItems2.getRecipe(163, 3), CoreItems2.getRecipe(161, 4),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 32),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 32),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Trinium, 32),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Iridium, 32),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.NaquadahAlloy, 32)
    }, CoreItems2.getRecipe(162, 1), null, 40 * 20, 7680);

    //Cyclone Chamber Casing
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_ItemList.CycloneCasing.get(1), ItemList.Electric_Motor_LuV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.MaragingSteel250, 2),
        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.MaragingSteel300, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Inconel792, 4),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TiBetaC, 6),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Inconel690, 12),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 16)
    }, new ItemStack(InsideBlock, 1, 1), null, 60 * 20, 30720);

    //3DPrinter 4x4
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Machine_IV_Printer.get(1L),
        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 500),
        ItemList.Conveyor_Module_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Inconel690, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 6),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HastelloyC276, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, GT_ItemList.Machine_AdvDDDPrinter.get(1L), null, 50 * 20, 1920);

    //Communication Tower
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
            ItemList.Hull_HV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4),
            GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 4),
            ItemList.Tool_DataStick.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSLA, 4),
            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedSteel, 8),
            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.BlueSteel, 8)
    }, GT_ItemList.Communication_Tower.get(1L), null, 40 * 20, 480);
    //Space Satelitte
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
            ItemList.Hull_HV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4),
            GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 4),
            GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 4L, 1),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.VanadiumSteel, 4),
            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedSteel, 8),
            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.BlueSteel, 8)
    }, GT_ItemList.Space_Satellite.get(1L), null, 50 * 20, 480);

    //Space Elevator
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
            ItemList.Hull_EV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
            GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HSLA, 4),
            GT_ModHandler.getModItem("EnderIO", "blockTravelAnchor", 2L, 0),
            ItemList.Conveyor_Module_EV.get(8L),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16),
            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 32),
            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Titanium, 32)
    }, GT_ItemList.Space_Elevator.get(1L), null, 60 * 20, 1920);

    /* ================================= end IMPACT MOD =================================*/

/** ================================= start ExtraUtilities =================================*/
    //Ender Quarry
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Machine_IV_Miner.get(1L), ItemList.Field_Generator_LuV.get(1L),
        ItemList.Electric_Motor_LuV.get(3L),
        GT_ModHandler.getModItem("ExtraUtilities", "enderThermicPump", 1, 0),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HastelloyN, 8),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.HastelloyC276, 8),
        GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 8, 0)
    }, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarry", 1, 0), null, 60 * 20, 30720);

    //Marker
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Emitter_LuV.get(1L),
        GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 4, 0),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HastelloyN, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Enderium, 8),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyC276, 8)
    }, GT_ModHandler.getModItem("ExtraUtilities", "endMarker", 1, 0), null, 50 * 20, 7680);

    //Last Millennium
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialIOPort", 1, 0),
        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 4, 0),
        GT_ModHandler.getModItem("EnderIO", "blockTelePad", 9, 0),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Vanadium, 10)
    }, GT_ModHandler.getModItem("ExtraUtilities", "dark_portal", 1, 2), null, 40 * 20, 1920);
    /* ================================= end  ExtraUtilities =================================*/

/** ================================= start Gravisuite =================================*/
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            GT_ModHandler.getIC2Item("quantumBodyarmor", 1L, 1), ItemList.Transformer_ZPM_LuV.get(1L),
            GT_ModHandler.getModItem("GraviSuite", "ultimateLappack", 1, 1),
            GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
            GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 2, 2),
            GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 2, 3),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Duranium, 2),
            ItemList.Energy_LapotronicOrb2.get(1L), ItemList.Field_Generator_IV.get(2L),
            ItemList.Electric_Motor_ZPM.get(2L),
            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Duranium, 4)
        }, GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1, GT_Values.W), null, 50 * 20,
        30720);

    //K2P4
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1, 1),
            ItemList.Transformer_UV_ZPM.get(1L),
            GT_ModHandler.getModItem("GraviSuite", "epicLappack", 1, 1),
            GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 8, 1),
            CoreItems2.getRecipe(98, 4), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 2, 7),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2),
            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Tritanium, 2),
            ItemList.Energy_Module.get(1L), ItemList.Field_Generator_LuV.get(2L),
            ItemList.Electric_Motor_UV.get(2L),
            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tritanium, 8)
        }, GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, GT_Values.W), null, 60 * 20,
        122880);
    /* ================================= end  Gravisuite =================================*/

/** ================================= start IC2 =================================*/
    //Nuclear Reactor
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L), ItemList.Robot_Arm_IV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_ModHandler.getModItem("IC2", "blockReactorChamber", 4L, 0), CoreItems2.getRecipe(141, 8),
        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Lead, 12)
    }, GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 5), null, 40 * 20, 1920);

    //Kinetic Generator
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hatch_Dynamo_HV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2),
        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
        ItemList.Electric_Motor_HV.get(2L), ItemList.Casing_Motor.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSLA, 6)
    }, GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 9), null, 10 * 20, 480);

    //Kinetic Wind Generator
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Casing_Gearbox_Steel.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
        ItemList.Electric_Motor_HV.get(2L),
        GT_ModHandler.getModItem("IC2", "itemRecipePart", 4, 12),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSLA, 6)
    }, GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 0), null, 10 * 20, 480);

    //Kinetic Water Generator
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Casing_Gearbox_Steel.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2),
        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
        ItemList.Electric_Pump_HV.get(2L), GT_ModHandler.getModItem("IC2", "itemRecipePart", 4, 12),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSLA, 6)
    }, GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 4), null, 10 * 20, 480);

    /* ================================= end  IC2 =================================*/

/** ================================= start Compact-Kinetic =================================*/
    //Wind Generator
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
            ItemList.Hull_EV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2),
            GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Desh, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2),
            ItemList.Electric_Motor_EV.get(2L),
            GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 2, 0),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 6)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 0), null, 20 * 20,
        1024);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hull_IV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.HSSG, 2),
            GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Trinium, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
            ItemList.Electric_Motor_IV.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 0),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyN, 8)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 1), null, 30 * 20,
        4096);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hull_LuV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.VanadiumGallium, 2),
            GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Draconium, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2),
            ItemList.Electric_Motor_LuV.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 1),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lafium, 10)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 2), null, 40 * 20,
        16384);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hull_ZPM.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt12, Materials.Duranium, 2),
            GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Oriharukon, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 2),
            ItemList.Electric_Motor_ZPM.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 2),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CinobiteA243, 12)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 3), null, 50 * 20,
        65536);

    //Water Generator
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
            ItemList.Hull_EV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2),
            GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Desh, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2),
            ItemList.Electric_Pump_EV.get(2L),
            GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 2, 4),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 6)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 4), null, 20 * 20,
        1024);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hull_IV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.HSSG, 2),
            GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Trinium, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
            ItemList.Electric_Pump_IV.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 4),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyN, 8)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 5), null, 30 * 20,
        4096);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hull_LuV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.VanadiumGallium, 2),
            GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Draconium, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2),
            ItemList.Electric_Pump_LuV.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 5),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lafium, 10)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 6), null, 40 * 20,
        16384);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hull_ZPM.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt12, Materials.Duranium, 2),
            GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Oriharukon, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 2),
            ItemList.Electric_Pump_ZPM.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 6),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CinobiteA243, 12)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 7), null, 50 * 20,
        65536);

    //Kinetic Generator
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
            ItemList.Hatch_Dynamo_EV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2),
            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Desh, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2),
            ItemList.Electric_Motor_EV.get(2L), GT_ModHandler.getModItem("IC2", "blockGenerator", 1, 9),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 6)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 8), null, 20 * 20,
        1024);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hatch_Dynamo_IV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.HSSG, 2),
            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Trinium, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
            ItemList.Electric_Motor_IV.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 8),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyN, 8)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 9), null, 30 * 20,
        4096);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hatch_Dynamo_LuV.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.VanadiumGallium, 2),
            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Draconium, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2),
            ItemList.Electric_Motor_LuV.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 9),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lafium, 10)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 10), null, 40 * 20,
        16384);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            ItemList.Hatch_Dynamo_ZPM.get(1L),
            GT_OreDictUnificator.get(OrePrefixes.cableGt12, Materials.Duranium, 2),
            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Oriharukon, 2),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 2),
            ItemList.Electric_Motor_ZPM.get(2L),
            GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 10),
            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CinobiteA243, 12)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 11), null, 50 * 20,
        65536);
    /* ================================= end  Compact-Kinetic =================================*/

/** ================================= start GT =================================*/

    //Fusion Casing MK1
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_LuV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 4),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyN, 4),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 4, 1),
        CoreItems2.getRecipe(142, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.TungstenSteel, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8),
        ItemList.Casing_LuV.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Europium, 12),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)
    }, ItemList.Casing_Fusion.get(8L), null, 50 * 20, 30720);
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_ZPM.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 6),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lafium, 6),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
        CoreItems2.getRecipe(142, 6),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.HSSS, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8),
        ItemList.Casing_LuV.get(20L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Americium, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 32)
    }, ItemList.Casing_Fusion.get(20L), null, 100 * 20, 122880);
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_UV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 8),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CinobiteA243, 8),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 8, 1),
        CoreItems2.getRecipe(142, 8),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Osmiridium, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8),
        ItemList.Casing_LuV.get(60L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 20),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 64)
    }, ItemList.Casing_Fusion.get(60L), null, 200 * 20, 500000);

    //Fusion Casing MK2
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_ZPM.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 4),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lafium, 4),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 4, 1),
        ItemList.Neutron_Reflector.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Osmiridium, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8),
        ItemList.Casing_Fusion.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Americium, 12),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSS, 16)
    }, ItemList.Casing_Fusion2.get(8L), null, 50 * 20, 122880);
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_UV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 6),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CinobiteA243, 6),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
        ItemList.Neutron_Reflector.get(6L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Duranium, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8),
        ItemList.Casing_Fusion.get(20L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSS, 32)
    }, ItemList.Casing_Fusion2.get(20L), null, 100 * 20, 500000);
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_UHV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 8),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Pikyonium64B, 8),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 8, 1),
        ItemList.Neutron_Reflector.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.ElectrumFlux, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 8),
        ItemList.Casing_Fusion.get(60L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Phoenixite, 20),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSS, 64)
    }, ItemList.Casing_Fusion2.get(60L), null, 200 * 20, 2000000);

    //Fusion Casing MK3
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_UV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 4),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CinobiteA243, 4),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 4, 1),
        ItemList.Neutron_Reflector.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Duranium, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8),
        ItemList.Casing_Fusion2.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 12),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Adamantium, 16)
    }, ItemList.Casing_Fusion3.get(8L), null, 50 * 20, 500000);
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_UHV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 6),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Pikyonium64B, 6),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
        ItemList.Neutron_Reflector.get(10L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.ElectrumFlux, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 8),
        ItemList.Casing_Fusion2.get(20L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Phoenixite, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Adamantium, 32)
    }, ItemList.Casing_Fusion3.get(20L), null, 100 * 20, 2000000);
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_UEV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 8),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Quantum, 8),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 8, 1),
        ItemList.neutroniumHeatCapacitor.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.BlackPlutonium, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 8),
        ItemList.Casing_Fusion2.get(60L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CosmicNeutronium, 20),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Adamantium, 64)
    }, ItemList.Casing_Fusion3.get(60L), null, 200 * 20, 8000000);

    //Fusion Casing MK4
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_UHV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 4),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Pikyonium64B, 4),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 4, 1),
        ItemList.neutroniumHeatCapacitor.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Quantium, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 8),
        ItemList.Casing_Fusion3.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Phoenixite, 12),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.DraconiumAwakened, 16)
    }, ItemList.Casing_Fusion4.get(8L), null, 50 * 20, 2000000);
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Field_Generator_UEV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 6),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Quantum, 6),
        GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
        ItemList.neutroniumHeatCapacitor.get(10L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.BlackPlutonium, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 8),
        ItemList.Casing_Fusion3.get(20L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CosmicNeutronium, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.DraconiumAwakened, 32)
    }, ItemList.Casing_Fusion4.get(20L), null, 100 * 20, 8000000);

    //Steam Turbine
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_HV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2),
        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Steel, 2),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 4),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 12)
    }, ItemList.LargeSteamTurbine.get(1L), null, 20 * 20, 120);

    //Gas Turbine
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.StainlessSteel, 2),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.StainlessSteel, 4),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StainlessSteel, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 12)
    }, ItemList.LargeGasTurbine.get(1L), null, 40 * 20, 480);

    //HP Turbine
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Titanium, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Inconel792, 8),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 16)
    }, ItemList.LargeHPSteamTurbine.get(1L), null, 60 * 20, 1920);

    //Plasma Turbine
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_UV.get(1L),
        ItemList.Casing_Coil_Superconductor.get(4L),
        ItemList.Electric_Pump_UV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.NaquadahAlloy, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Draconium, 8),
        GT_OreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Naquadah, 8),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.NaquadahAlloy, 8),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Oriharukon, 8),
        GT_OreDictUnificator.get(OrePrefixes.round, Materials.NaquadahAlloy, 16),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Americium, 16),
        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Pikyonium64B, 16)
    }, ItemList.LargePlasmaTurbine.get(1L), null, 100 * 20, 500000);

    //Heat Exchanger
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_EV.get(1L), ItemList.Casing_Firebox_Titanium.get(1L),
        ItemList.Electric_Pump_EV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 2),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 8)
    }, ItemList.Machine_Multi_HeatExchanger.get(1L), null, 40 * 20, 480);

    //OilDrill2
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_HV.get(1L), ItemList.Electric_Motor_HV.get(2L),
        ItemList.Electric_Pump_HV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 2),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 10)
    }, ItemList.OilDrill2.get(1L), null, 40 * 20, 480);

    //OilDrill3
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_EV.get(1L), ItemList.Electric_Motor_EV.get(2L),
        ItemList.Electric_Pump_EV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 8),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 12),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 16)
    }, ItemList.OilDrill3.get(1L), null, 60 * 20, 1920);

    //OreDrill1
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_EV.get(1L), ItemList.Electric_Motor_EV.get(2L),
        ItemList.Conveyor_Module_EV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 2),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 12)
    }, ItemList.OreDrill1.get(1L), null, 40 * 20, 480);

    //OreDrill2
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L), ItemList.Electric_Motor_IV.get(2L),
        ItemList.Conveyor_Module_IV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Desh, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 12),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 16)
    }, ItemList.OreDrill2.get(1L), null, 60 * 20, 1920);

    //OreDrill3
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_LuV.get(1L), ItemList.Electric_Motor_LuV.get(2L),
        ItemList.Conveyor_Module_LuV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Trinium, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Osmiridium, 12),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 20)
    }, ItemList.OreDrill3.get(1L), null, 80 * 20, 7680);

    //OreDrill4
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_ZPM.get(1L), ItemList.Electric_Motor_ZPM.get(4L),
        ItemList.Conveyor_Module_ZPM.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Oriharukon, 12),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 12),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 16),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Osmiridium, 24)
    }, ItemList.OreDrill4.get(1L), null, 100 * 20, 30720);

    //OreDrill5
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_UV.get(1L), ItemList.Electric_Motor_UV.get(6L),
        ItemList.Conveyor_Module_UV.get(6L),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Adamantium, 16),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 16),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 20),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Tritanium, 26)
    }, ItemList.OreDrill5.get(1L), null, 120 * 20, 122880);

    //Oil Cracker
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_HV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4),
        ItemList.Casing_Coil_Cupronickel.get(4L),
        ItemList.Electric_Pump_HV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 8)
    }, ItemList.OilCracker.get(1L), null, 20 * 20, 120);

    //Assembly Line
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Machine_IV_Assembler.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        ItemList.Casing_Gearbox_TungstenSteel.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 4),
        ItemList.Conveyor_Module_IV.get(4L), ItemList.Robot_Arm_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Inconel792, 8),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
    }, ItemList.Machine_Multi_Assemblyline.get(1L), null, 120 * 20, 7680);

    //DieselGen1
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 2),
        ItemList.Electric_Piston_EV.get(2L), ItemList.Electric_Motor_EV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8)
    }, ItemList.Machine_Multi_DieselEngine.get(1L), null, 40 * 20, 1920);

    //DieselGen2
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 2),
        ItemList.Electric_Piston_IV.get(2L), ItemList.Electric_Motor_IV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 8)
    }, ItemList.Machine_Multi_DieselEngine2.get(1L), null, 80 * 20, 7680);

    //LCR
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_HV.get(1L), ItemList.Electric_Pump_HV.get(2L),
        ItemList.Electric_Motor_HV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2),
        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 2),
        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Polytetrafluoroethylene, 2),
        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.StainlessSteel, 4),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 6)
    }, ItemList.Machine_Multi_LargeChemicalReactor.get(1L), null, 20 * 20, 120);

    //Implosion Compressor
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_HV.get(1L), ItemList.Electric_Piston_HV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Steel, 8),
        ItemList.Casing_ExplosionHazard.get(1L)
    }, ItemList.Machine_Multi_ImplosionCompressor.get(1L), null, 30 * 20, 256);

    //Vacuum Freezer
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_HV.get(1L), ItemList.Electric_Pump_HV.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Aluminium, 8),
        ItemList.Casing_FrostHazard.get(1L)
    }, ItemList.Machine_Multi_VacuumFreezer.get(1L), null, 30 * 20, 256);

    //Titanium
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_EV.get(1L), ItemList.Casing_Firebox_Titanium.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Titanium, 3),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Titanium, 12)
    }, ItemList.Machine_Multi_LargeBoiler_Titanium.get(1L), null, 40 * 20, 480);

    //TungstenSteel
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_IV.get(1L), ItemList.Casing_Firebox_TungstenSteel.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.TungstenSteel, 3),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 4),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 12)
    }, ItemList.Machine_Multi_LargeBoiler_TungstenSteel.get(1L), null, 60 * 20, 1920);

    //Distillation_Tower
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_EV.get(1L), ItemList.Electric_Pump_EV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 8),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 12)
    }, ItemList.Distillation_Tower.get(1L), null, 40 * 20, 480);

    //Centrifuge
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Machine_EV_Centrifuge.get(1L), ItemList.Electric_Motor_EV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 4),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8)
    }, ItemList.Machine_MultiblockCentrifuge.get(1L), null, 40 * 20, 480);

    //Electrolyzer
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Machine_EV_Electrolyzer.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Aluminium, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Tungsten, 4),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8)
    }, ItemList.Machine_MultiblockElectrolyzer.get(1L), null, 40 * 20, 480);

    //Flotation Unit
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Hull_EV.get(1L), ItemList.Electric_Pump_EV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 6),
        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 8),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 12)
    }, ItemList.Machine_FlotationUnit.get(1L), null, 40 * 20, 480);

    //Solar Panel LV 32 EU
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 1),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polytetrafluoroethylene, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4),
        ItemList.Circuit_Silicon_Wafer2.get(4L), CoreItems2.getRecipe(117, 4),
        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorMV, 4)
    }, ItemList.Cover_SolarPanel_LV.get(1L), null, 20 * 20, 120);

    //Solar Panel MV 128 EU
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_LV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Epoxid, 2),
        ItemList.Circuit_Chip_ULPIC.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 2),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4),
        ItemList.Circuit_Silicon_Wafer2.get(4L),
        CoreItems2.getRecipe(118, 4),
        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorHV, 4)
    }, ItemList.Cover_SolarPanel_MV.get(1L), null, 40 * 20, 480);

    //Solar Panel HV 512 EU
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_MV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.IndiumGalliumPhosphide, 2),
        ItemList.Circuit_Chip_LPIC.get(2L),
        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 6),
        ItemList.Circuit_Silicon_Wafer2.get(4L),
        CoreItems2.getRecipe(119, 4),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicon, 4),
        GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorEV, 6)
    }, ItemList.Cover_SolarPanel_HV.get(1L), null, 60 * 20, 1920);

    //Solar Panel EV 2048 EU
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_HV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polybenzimidazole, 4),
        ItemList.Circuit_Chip_PIC.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 6),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 6),
        ItemList.Circuit_Silicon_Wafer4.get(4L), CoreItems2.getRecipe(120, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Silicon, 4),
        GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorIV, 6)
    }, ItemList.Cover_SolarPanel_EV.get(1L), null, 80 * 20, 7680);
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_HV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.IndiumGalliumPhosphide, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
        CoreItems2.getRecipe(112, 2),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicon, 16)
    }, ItemList.Cover_SolarPanel_EV.get(1L), null, 10 * 20, 480);

    //Solar Panel IV 8192 EU
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_EV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polybenzimidazole, 6),
        ItemList.Circuit_Chip_HPIC.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 8),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8),
        ItemList.Circuit_Silicon_Wafer3.get(6L), CoreItems2.getRecipe(121, 4),
        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Silicon, 6),
        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorLuV, 8)
    }, ItemList.Cover_SolarPanel_IV.get(1L), null, 100 * 20, 30720);
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_EV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Polybenzimidazole, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
        CoreItems2.getRecipe(113, 2),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicon, 24)
    }, ItemList.Cover_SolarPanel_IV.get(1L), null, 20 * 20, 1920);

    //Solar Panel LuV 32768 EU
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_IV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Polybenzimidazole, 8),
        ItemList.Circuit_Chip_UHPIC.get(6L),
        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 10),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8),
        ItemList.Circuit_Silicon_Wafer7.get(8L), CoreItems2.getRecipe(122, 6),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Silicon, 6),
        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorZPM, 8)
    }, ItemList.Cover_SolarPanel_LuV.get(1L), null, 120 * 20, 122880);
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_IV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.VanadiumGallium, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 6),
        CoreItems2.getRecipe(114, 4),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicon, 32)
    }, ItemList.Cover_SolarPanel_LuV.get(1L), null, 30 * 20, 7680);

    //Solar Panel ZPM 131072 EU
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_LuV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Polybenzimidazole, 10),
        ItemList.Circuit_Chip_QPIC.get(8L),
        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 12),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 10),
        ItemList.Circuit_Silicon_Wafer8.get(10L), CoreItems2.getRecipe(123, 6),
        GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, Materials.Silicon, 8),
        GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.SuperconductorUV, 10)
    }, ItemList.Cover_SolarPanel_ZPM.get(1L), null, 140 * 20, 500000);
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_LuV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.NaquadahAlloy, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 6),
        CoreItems2.getRecipe(115, 4),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicon, 48)
    }, ItemList.Cover_SolarPanel_ZPM.get(1L), null, 40 * 20, 30720);

    //Solar Panel UV 524288 EU
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_ZPM.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Polybenzimidazole, 12),
        ItemList.Circuit_Chip_FPIC.get(10L),
        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 14),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 10),
        ItemList.Circuit_Silicon_Wafer10.get(12L), CoreItems2.getRecipe(124, 8),
        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Silicon, 8),
        GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.Superconductor, 12)
    }, ItemList.Cover_SolarPanel_UV.get(1L), null, 160 * 20, 2000000);
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        ItemList.Cover_SolarPanel_ZPM.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.ElectrumFlux, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 8),
        CoreItems2.getRecipe(116, 4),
        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicon, 64)
    }, ItemList.Cover_SolarPanel_UV.get(1L), null, 50 * 20, 122880);

    /* ================================= end  GT =================================*/
    /** ================================= START GALACTICRAFT =================================*/
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        CoreItems2.getRecipe(0, 0),
        GT_ModHandler.getModItem("GalacticraftCore", "item.noseCone", 1L, 0),
        GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 3L, 4),
        GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 8L, 0),
        GT_ModHandler.getModItem("GalacticraftCore", "item.rocketFins", 4L, 0),
        GT_ModHandler.getModItem("GalaxySpace", "item.Modules", 1L, 3),
        GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L, 0)
    }, GT_ModHandler.getModItem("GalacticraftCore", "item.spaceship", 1L, 0), null, 100 * 20, 480);

    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
            CoreItems2.getRecipe(1, 0),
            GT_ModHandler.getModItem("GalacticraftCore", "item.noseCone", 1L, 0),
            GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 3L, 0),
            GT_ModHandler.getModItem("GalacticraftMars", "item.null", 10L, 3),
            GT_ModHandler.getModItem("GalacticraftCore", "item.rocketFins", 4L, 0),
            GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 2L, 1),
            GT_ModHandler.getModItem("GalaxySpace", "item.Modules", 2L, 3),
            GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 2L, 0)
        }, GT_ModHandler.getModItem("GalacticraftMars", "item.spaceshipTier2", 1L, 0), null, 120 * 20,
        1920);

    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
            CoreItems2.getRecipe(2, 0),
            GT_ModHandler.getModItem("GalacticraftMars", "item.heavyNoseCone", 1L, 0),
            GT_ModHandler.getModItem("GalacticraftMars", "item.null", 4L, 3),
            GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 12L, 0),
            GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 4L, 2),
            GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 4L, 1),
            CoreItems2.getRecipe(133, 2),
            GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 1)
        }, GT_ModHandler.getModItem("GalacticraftMars", "item.itemTier3Rocket", 1L, 0), null, 140 * 20,
        7680);

    /* ================================= END GALACTICRAFT =================================*/

    /** ================================= START GREG'S SG CRAFT =================================*/
    //Stargate Base
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 6),
        GT_ModHandler.getModItem("SGCraft", "stargateRing", 4L, 0),
        GT_ModHandler.getModItem("SGCraft", "stargateRing", 1L, 1), ItemList.Emitter_UV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Piko, 1),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Quantum, 32)
    }, GT_ModHandler.getModItem("SGCraft", "stargateBase", 1L, 0), null, 100 * 20, 500000);

    //Stargate Ring Block
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 8),
        CoreItems2.getRecipe(147, 8), CoreItems2.getRecipe(148, 6), CoreItems2.getRecipe(149, 3),
        ItemList.Field_Generator_UV.get(1L),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Quantum, 16)
    }, GT_ModHandler.getModItem("SGCraft", "stargateRing", 1L, 0), null, 50 * 20, 500000);

    //Stargate Chevron Block
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 8),
        ItemList.Sensor_UV.get(4L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Nano, 4),
        GT_ModHandler.getModItem("SGCraft", "sgChevronUpgrade", 1L),
        GT_ModHandler.getModItem("SGCraft", "stargateRing", 1L),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Quantum, 16)
    }, GT_ModHandler.getModItem("SGCraft", "stargateRing", 1L, 1), null, 50 * 20, 500000);

    //IC2 Stargate Power Unit
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 12),
        ItemList.Casing_Coil_Superconductor.get(10L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Nano, 4),
        GT_ModHandler.getModItem("SGCraft", "ic2Capacitor", 4L),
        GT_ModHandler.getModItem("SGCraft", "stargateRing", 1L, 0),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Quantum, 32)
    }, GT_ModHandler.getModItem("SGCraft", "ic2PowerUnit", 1L, 0), null, 100 * 20, 500000);

    //OC Stargate Interface
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 12),
        ItemList.Casing_Coil_Superconductor.get(10L),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Nano, 4),
        GT_ModHandler.getModItem("OpenComputers", "item", 16L, 102),
        GT_ModHandler.getModItem("OpenComputers", "case3", 1L),
        GT_ModHandler.getModItem("SGCraft", "stargateRing", 1L, 0),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Quantum, 32)
    }, GT_ModHandler.getModItem("SGCraft", "ocInterface", 1L, 0), null, 100 * 20, 500000);

    /* ================================= END GREG'S SG CRAFT =================================*/
    /** ================================= START GENDUSTRY =================================*/
    // --- Mutagen Producer
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_LuV.get(1), GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 2L, 0),
        GT_ModHandler.getModItem("gendustry", "BeeReceptacle", 2L, 0),
        GT_ModHandler.getModItem("gendustry", "PowerModule", 2L, 0),
        ItemList.Robot_Arm_LuV.get(2), ItemList.Electric_Pump_LuV.get(4),
        GT_ModHandler.getModItem("gendustry", "MutagenTank", 4L, 0),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Trinium, 4),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 32),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 32)
    }, GT_ModHandler.getModItem("gendustry", "MutagenProducer", 1L, 0), null, 100 * 20, 65576);

    // --- Mutatron
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_LuV.get(1), GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 2L, 0),
        GT_ModHandler.getModItem("gendustry", "BeeReceptacle", 2L, 0),
        GT_ModHandler.getModItem("gendustry", "PowerModule", 2L, 0),
        ItemList.Robot_Arm_LuV.get(2), GT_ModHandler.getModItem("gendustry", "MutagenTank", 4L, 0),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Osmium, 4),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 8),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 32),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 32)
    }, GT_ModHandler.getModItem("gendustry", "Mutatron", 1L, 0), null, 100 * 20, 65576);

    // --- Advanced Mutatron
    GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
        ItemList.Hull_ZPM.get(1), GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 4L, 0),
        GT_ModHandler.getModItem("gendustry", "BeeReceptacle", 4L, 0),
        GT_ModHandler.getModItem("gendustry", "PowerModule", 4L, 0),
        ItemList.Robot_Arm_ZPM.get(4), GT_ModHandler.getModItem("gendustry", "MutagenTank", 8L, 0),
        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.NaquadahAlloy, 8),
        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.MysteriousCrystal, 16),
        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 64)
    }, GT_ModHandler.getModItem("gendustry", "MutatronAdv", 1L, 0), null, 200 * 20, 500000);

    // --- Industrial Apiary
    GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
        GT_ModHandler.getModItem("Forestry", "apiculture", 1L, 0),
        GT_ModHandler.getModItem("Forestry", "alveary", 2L, 2),
        GT_ModHandler.getModItem("Forestry", "alveary", 2L, 3),
        GT_ModHandler.getModItem("Forestry", "alveary", 2L, 4),
        GT_ModHandler.getModItem("Forestry", "alveary", 2L, 5),
        GT_ModHandler.getModItem("Forestry", "alveary", 2L, 6),
        GT_ModHandler.getModItem("Forestry", "alveary", 2L, 7),
        GT_ModHandler.getModItem("Forestry", "alveary", 4L, 0), ItemList.Field_Generator_HV.get(4)
    }, GT_ModHandler.getModItem("gendustry", "IndustrialApiary", 1L, 0), null, 60 * 20, 1024);

    /* ================================= END GENDUSTRY =================================*/

  }
}