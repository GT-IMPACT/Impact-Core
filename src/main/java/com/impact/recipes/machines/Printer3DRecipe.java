package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
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
        //Controlers
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.GumMetal, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.GumMetal, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Titaniolum, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titaniolum, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_PBE.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Zamak, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), ItemList.Robot_Arm_IV.get(4L), ItemList.Emitter_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Zamak, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Duraluminium, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Duraluminium, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_LaserEngraver.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel690, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), ItemList.Robot_Arm_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Inconel690, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Assembler.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Inconel792, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Centrifuge.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Zamak, 4),
                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Zamak, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Electrolyzer.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titaniolum, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Conveyor_Module_IV.get(4L), ItemList.Electric_Piston_IV.get(4L), ItemList.Robot_Arm_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titaniolum, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Inconel690, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Inconel690, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Wire.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.MaragingSteel250, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel250, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Inconel792, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Inconel792, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Supply.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), ItemList.Electric_Pump_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Utility.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Talonite, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), ItemList.Electric_Pump_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Talonite, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Grisium, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Grisium, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Brewmenter.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Inconel690, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Inconel690, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Siftarator.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol60, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Nitinol60, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Kovar, 4),
                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Kovar, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_ArcFurnace.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TiBetaC, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), ItemList.Electric_Pump_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Talonite, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Talonite, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Extradifier.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titaniolum, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titaniolum, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.MaragingSteel250, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.MaragingSteel250, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Mixer.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Mangalloy, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Mangalloy, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Macerator.get(1L), null, 40 * 20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.MaragingSteel300, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Stellite, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Stellite, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Cutting.get(1L), null, 40 * 20, 7680);

        //UpgradeCasingT1
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1, 3), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.GumMetal, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Nitinol, 16),
                ItemList.Electric_Motor_IV.get(4L), ItemList.Electric_Piston_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), ItemList.Robot_Arm_IV.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Graphene, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16)
        }, GT_ItemList.UpgradeCasingT1.get(1L), null, 80 * 20, 7680);

        //UpgradeCasingT2
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_LuV.get(1L), GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 0), GT_ItemList.UpgradeCasingT1.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyN, 16),
                ItemList.Electric_Motor_LuV.get(4L), ItemList.Electric_Piston_LuV.get(4L), ItemList.Conveyor_Module_LuV.get(4L), ItemList.Robot_Arm_LuV.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.YttriumBariumCuprate, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Chrome, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 32)
        }, GT_ItemList.UpgradeCasingT2.get(1L), null, 120 * 20, 30720);

        //UpgradeCasingT3
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_ZPM.get(1L), GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 1), GT_ItemList.UpgradeCasingT2.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lafium, 16),
                ItemList.Electric_Motor_ZPM.get(4L), ItemList.Electric_Piston_ZPM.get(4L), ItemList.Conveyor_Module_ZPM.get(4L), ItemList.Robot_Arm_ZPM.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Naquadah, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Iridium, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 48)
        }, GT_ItemList.UpgradeCasingT3.get(1L), null, 160 * 20, 122880);

        //UpgradeCasingT4
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_UV.get(1L), GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 2), GT_ItemList.UpgradeCasingT3.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CinobiteA243, 16),
                ItemList.Electric_Motor_UV.get(4L), ItemList.Electric_Piston_UV.get(4L), ItemList.Conveyor_Module_UV.get(4L), ItemList.Robot_Arm_UV.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.NaquadahAlloy, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Osmium, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64)
        }, GT_ItemList.UpgradeCasingT4.get(1L), null, 200 * 20, 500000);

        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 500), ItemList.Cover_ItemDetector.get(8L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol, 8),
                ItemList.Robot_Arm_IV.get(8L), GT_ItemList.DDDPrinterCasing3x3.get(9), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 16),
                GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 16), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.GumMetal, 32), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64)
        }, GT_ItemList.DDDPrinterCasing4x4.get(16L), null, 200 * 20, 1920);

        //Machine_BlastSmelter
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Machine_Multi_BlastFurnace.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HSLA, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Kanthal, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSLA, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 16)
        }, GT_ItemList.Machine_BlastSmelter.get(1L), null, 40 * 20, 1920);

        //Machine_FreezSolidifier
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Machine_Multi_VacuumFreezer.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.BlueSteel, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.StainlessSteel, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Aluminium, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 16)
        }, GT_ItemList.Machine_FreezSolidifier.get(1L), null, 40 * 20, 1920);

        //AdvancedFreezer
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lafium, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 6), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iridium, 8),
                ItemList.Electric_Pump_ZPM.get(4L), ItemList.Robot_Arm_ZPM.get(4L), ItemList.Reactor_Coolant_Le_3.get(1L), ItemList.Reactor_Coolant_Le_3.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Enderium, 8), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Osmiridium, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64)
        }, GT_ItemList.AdvVacuumFreezer.get(1L), null, 200 * 20, 122880);


        /* ================================= end IMPACT MOD =================================*/

/** ================================= start EnderIO =================================*/
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Enderium, 1), GT_ModHandler.getModItem("EnderIO", "itemBasicCapacitor", 1L, 2), ItemList.Emitter_EV.get(1L),
                ItemList.Sensor_EV.get(1L), GT_ModHandler.getModItem("EnderIO", "itemMaterial", 4L, 5), GT_ModHandler.getModItem("EnderIO", "blockFusedQuartz", 4L, 0),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.DarkSteel, 8)
        }, GT_ModHandler.getModItem("EnderIO", "blockTelePad", 9L, 0), null, 20 * 20, 480);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Enderium, 1), ItemList.Field_Generator_LuV.get(1L), GT_ModHandler.getModItem("EnderIO", "itemBasicCapacitor", 4L, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4), GT_ModHandler.getModItem("EnderIO", "blockFusedQuartz", 6L, 0), GT_ModHandler.getModItem("EnderIO", "itemMaterial", 8L, 5), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.DarkSteel, 12)
        }, GT_ModHandler.getModItem("EnderIO", "blockTransceiver", 1L, 0), null, 40 * 20, 30720);
        /* ================================= end  EnderIO =================================*/

/** ================================= start AE2 =================================*/
        //ME Controller
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyAcceptor", 1, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 24)/*Diamond*/, GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 76), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 4),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockController", 1, 0), null, 30 * 20, 1920);

        //ME Chest
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 380), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 16),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 2),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 22)/*Gold*/, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Vanadium, 12)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1, 0), null, 20 * 20, 480);

        //ME Drive
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1, 0), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.VibrantAlloy, 2),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24)/*Diamond*/, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 3), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 4),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Vanadium, 9)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDrive", 1, 0), null, 30 * 20, 1920);

        //CraftingUnit
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 1), /*GT_ModHandler.getModItem("OpenComputers", "item", 1, 29), */GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 23)/*Processor*/, GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 56),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 12)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1, 0), null, 30 * 20, 1920);

        //Molecular Assembler
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Machine_MV_Assembler.get(1L), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43)/*Form*/, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44)/*Ani*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 23)/*Processor*/, GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Vanadium, 2), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.VanadiumSteel, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 12, 0)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockMolecularAssembler", 1, 0), null, 30 * 20, 1920);

        //ME Quantum Ring
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyCell", 1, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24)/*Diamond*/, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 76), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 4),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.MaragingSteel300, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Vanadium, 8)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumRing", 1, 0), null, 20 * 20, 7680);

        //ME Quantum Link Chamber
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_EV.get(1L), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 7), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 9), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 12, 0)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 1, 0), null, 40 * 20, 7680);

        //Spatial Pylon
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumRing", 1, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 8), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 7), GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Platinum, 4),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 8), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 16), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Palladium, 16)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 2, 0), null, 30 * 20, 7680);

        //Spatial IO Port
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockIOPort", 1, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24)/*Diamond*/, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 4, 0), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 4, 0),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 7), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.NiobiumTitanium, 4), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 8),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Vanadium, 16)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialIOPort", 1, 0), null, 40 * 20, 7680);

        //ME Interface
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Robot_Arm_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 43)/*Form*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 44)/*Ani*/, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1, 0), null, 100, 120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Robot_Arm_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 2), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43)/*Form*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44)/*Ani*/, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 2, 0), null, 200, 480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Robot_Arm_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 43)/*Form*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 44)/*Ani*/, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 4, 0), null, 300, 1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Robot_Arm_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43)/*Form*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44)/*Ani*/, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 8, 0), null, 400, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Robot_Arm_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 5), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 43)/*Form*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 44)/*Ani*/, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 12, 0), null, 500, 30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Robot_Arm_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 6), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43)/*Form*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44)/*Ani*/, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 16, 0), null, 600, 122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Robot_Arm_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 7), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 43)/*Form*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 44)/*Ani*/, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 24, 0), null, 700, 500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Robot_Arm_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 8), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43)/*Form*/,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44)/*Ani*/, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 32, 0), null, 800, 2000000);

        //Enegery Acceptor
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyCell", 1, 0), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 7)/*crystal*/, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 8)
        }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyAcceptor", 1, 0), null, 200, 480);

        //New Pattern Terminal
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 52), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 340), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24)/*Diamond*/
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 500), null, 400, 1920);

        //ME Export Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 260), null, 200, 120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2, 260), null, 300, 480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 260), null, 400, 1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 260), null, 500, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 10, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 12, 260), null, 600, 30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 12, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 16, 260), null, 700, 122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 14, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 24, 260), null, 800, 500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 32, 260), null, 900, 2000000);

        //ME Import Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 240), null, 200, 120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2, 240), null, 300, 480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 240), null, 400, 1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 240), null, 500, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 10, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 12, 240), null, 600, 30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 12, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 16, 240), null, 700, 122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 14, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 24, 240), null, 800, 500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Conveyor_Module_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 32, 240), null, 900, 2000000);

        //Storage Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_Bus_LV.get(1L), ItemList.Hatch_Output_Bus_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 220), null, 200, 120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_Bus_MV.get(1L), ItemList.Hatch_Output_Bus_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2, 220), null, 300, 480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_Bus_HV.get(1L), ItemList.Hatch_Output_Bus_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 220), null, 400, 1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_Bus_EV.get(1L), ItemList.Hatch_Output_Bus_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 220), null, 500, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_Bus_IV.get(1L), ItemList.Hatch_Output_Bus_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 12, 220), null, 600, 30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_Bus_LuV.get(1L), ItemList.Hatch_Output_Bus_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 16, 220), null, 700, 122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_Bus_ZPM.get(1L), ItemList.Hatch_Output_Bus_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 24, 220), null, 800, 500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_Bus_UV.get(1L), ItemList.Hatch_Output_Bus_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 32, 220), null, 900, 2000000);

        //P2P Tunnel
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Emitter_LV.get(1L), ItemList.Sensor_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2, 460), null, 200, 120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Emitter_MV.get(1L), ItemList.Sensor_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 460), null, 300, 480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Emitter_HV.get(1L), ItemList.Sensor_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 460), null, 400, 1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Emitter_EV.get(1L), ItemList.Sensor_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 16, 460), null, 500, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Emitter_IV.get(1L), ItemList.Sensor_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 24, 460), null, 600, 30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Emitter_LuV.get(1L), ItemList.Sensor_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 32, 460), null, 700, 122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Emitter_ZPM.get(1L), ItemList.Sensor_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 48, 460), null, 800, 500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Emitter_UV.get(1L), ItemList.Sensor_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
        }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 64, 460), null, 900, 2000000);
        /* ================================= end AE2 =================================*/


/** ================================= start EC2 =================================*/
//ME Fluid Export Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
        }, GT_ModHandler.getModItem("extracells", "part.base", 1, 0), null, 200, 120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
        }, GT_ModHandler.getModItem("extracells", "part.base", 2, 0), null, 300, 480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
        }, GT_ModHandler.getModItem("extracells", "part.base", 4, 0), null, 400, 1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
        }, GT_ModHandler.getModItem("extracells", "part.base", 8, 0), null, 500, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 10, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
        }, GT_ModHandler.getModItem("extracells", "part.base", 12, 0), null, 600, 30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 12, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
        }, GT_ModHandler.getModItem("extracells", "part.base", 16, 0), null, 700, 122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 14, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
        }, GT_ModHandler.getModItem("extracells", "part.base", 24, 0), null, 800, 500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16, 43),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
        }, GT_ModHandler.getModItem("extracells", "part.base", 32, 0), null, 900, 2000000);

        //ME Fluid Import Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
        }, GT_ModHandler.getModItem("extracells", "part.base", 1, 1), null, 200, 120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
        }, GT_ModHandler.getModItem("extracells", "part.base", 2, 1), null, 300, 480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
        }, GT_ModHandler.getModItem("extracells", "part.base", 4, 1), null, 400, 1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
        }, GT_ModHandler.getModItem("extracells", "part.base", 8, 1), null, 500, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 10, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
        }, GT_ModHandler.getModItem("extracells", "part.base", 12, 1), null, 600, 30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 12, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
        }, GT_ModHandler.getModItem("extracells", "part.base", 16, 1), null, 700, 122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 14, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
        }, GT_ModHandler.getModItem("extracells", "part.base", 24, 1), null, 800, 500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Electric_Pump_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16, 44),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
        }, GT_ModHandler.getModItem("extracells", "part.base", 32, 1), null, 900, 2000000);

        //ME Fluid Storage Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_LV.get(1L), ItemList.Hatch_Output_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
        }, GT_ModHandler.getModItem("extracells", "part.base", 1, 2), null, 200, 120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_MV.get(1L), ItemList.Hatch_Output_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
        }, GT_ModHandler.getModItem("extracells", "part.base", 2, 2), null, 300, 480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_HV.get(1L), ItemList.Hatch_Output_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
        }, GT_ModHandler.getModItem("extracells", "part.base", 4, 2), null, 400, 1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_EV.get(1L), ItemList.Hatch_Output_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
        }, GT_ModHandler.getModItem("extracells", "part.base", 8, 2), null, 500, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_IV.get(1L), ItemList.Hatch_Output_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
        }, GT_ModHandler.getModItem("extracells", "part.base", 12, 2), null, 600, 30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_LuV.get(1L), ItemList.Hatch_Output_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
        }, GT_ModHandler.getModItem("extracells", "part.base", 16, 2), null, 700, 122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_ZPM.get(1L), ItemList.Hatch_Output_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
        }, GT_ModHandler.getModItem("extracells", "part.base", 24, 2), null, 800, 500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Input_UV.get(1L), ItemList.Hatch_Output_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
        }, GT_ModHandler.getModItem("extracells", "part.base", 32, 2), null, 900, 2000000);
        /* ================================= end EC2 =================================*/

/** ================================= start ExtraUtilities =================================*/
        //Ender Quarry
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Machine_IV_Miner.get(1L), ItemList.Field_Generator_LuV.get(1L), ItemList.Electric_Motor_LuV.get(3L), GT_ModHandler.getModItem("ExtraUtilities", "enderThermicPump", 1, 0),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HastelloyN, 8), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.HastelloyC276, 8), GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 8, 0)
        }, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarry", 1, 0), null, 60 * 20, 30720);

        //Marker
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Emitter_LuV.get(1L), GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 4, 0), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HastelloyN, 4),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Enderium, 8), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyC276, 8)
        }, GT_ModHandler.getModItem("ExtraUtilities", "endMarker", 1, 0), null, 50 * 20, 7680);

        //Last Millennium
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialIOPort", 1, 0), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 4, 0), GT_ModHandler.getModItem("EnderIO", "blockTelePad", 9, 0),
                GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Vanadium, 10)
        }, GT_ModHandler.getModItem("ExtraUtilities", "dark_portal", 1, 2), null, 40 * 20, 1920);
        /* ================================= end  ExtraUtilities =================================*/

/** ================================= start Gravisuite =================================*/
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                GT_ModHandler.getIC2Item("quantumBodyarmor", 1L, 1), ItemList.Transformer_ZPM_LuV.get(1L), GT_ModHandler.getModItem("GraviSuite", "ultimateLappack", 1, 1), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
                GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 2, 2), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 2, 3), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Duranium, 2),
                ItemList.Energy_LapotronicOrb2.get(1L), ItemList.Field_Generator_IV.get(2L), ItemList.Electric_Motor_ZPM.get(2L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Duranium, 4)
        }, GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1, 26), null, 50 * 20, 30720);

        //K2P4
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1, 1), ItemList.Transformer_UV_ZPM.get(1L), GT_ModHandler.getModItem("GraviSuite", "epicLappack", 1, 1), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 8, 1),
                CoreItems2.getRecipe(98,4), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 2, 7), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Tritanium, 2),
                ItemList.Energy_Module.get(1L), ItemList.Field_Generator_LuV.get(2L), ItemList.Electric_Motor_UV.get(2L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tritanium, 8)
        }, GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1, 26), null, 60 * 20, 122880);
        /* ================================= end  Gravisuite =================================*/

/** ================================= start IC2 =================================*/
        //Nuclear Reactor
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), ItemList.Robot_Arm_IV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Titanium, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                GT_ModHandler.getModItem("IC2", "blockReactorChamber", 4L, 0), CoreItems2.getRecipe(141,8), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Lead, 12)
        }, GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 5), null, 40 * 20, 1920);

        //Kinetic Generator
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Dynamo_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2), ItemList.Electric_Motor_HV.get(2L), ItemList.Casing_Motor.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 6)
        }, GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 9), null, 10 * 20, 480);

        //Kinetic Wind Generator
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Casing_Gearbox_Steel.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2), ItemList.Electric_Motor_HV.get(2L), GT_ModHandler.getModItem("IC2", "itemRecipePart", 4, 12),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 6)
        }, GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 0), null, 10 * 20, 480);

        //Kinetic Water Generator
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Casing_Gearbox_Steel.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2), ItemList.Electric_Pump_HV.get(2L), GT_ModHandler.getModItem("IC2", "itemRecipePart", 4, 12),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 6)
        }, GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 4), null, 10 * 20, 480);

        /* ================================= end  IC2 =================================*/

/** ================================= start Compact-Kinetic =================================*/
        //Wind Generator
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Desh, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2), ItemList.Electric_Motor_EV.get(2L), GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 2, 0),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 6)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 0), null, 20 * 20, 1024);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.HSSG, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Trinium, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                ItemList.Electric_Motor_IV.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 0), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyN, 8)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 1), null, 30 * 20, 4096);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.VanadiumGallium, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Draconium, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2),
                ItemList.Electric_Motor_LuV.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lafium, 10)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 2), null, 40 * 20, 16384);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt12, Materials.Duranium, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Oriharukon, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 2),
                ItemList.Electric_Motor_ZPM.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 2), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CinobiteA243, 12)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 3), null, 50 * 20, 65536);

        //Water Generator
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Desh, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2), ItemList.Electric_Pump_EV.get(2L), GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 2, 4),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 6)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 4), null, 20 * 20, 1024);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.HSSG, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Trinium, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                ItemList.Electric_Pump_IV.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyN, 8)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 5), null, 30 * 20, 4096);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.VanadiumGallium, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Draconium, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2),
                ItemList.Electric_Pump_LuV.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 5), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lafium, 10)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 6), null, 40 * 20, 16384);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt12, Materials.Duranium, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Oriharukon, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 2),
                ItemList.Electric_Pump_ZPM.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 2L, 6), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CinobiteA243, 12)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 7), null, 50 * 20, 65536);

        //Kinetic Generator
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hatch_Dynamo_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Desh, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2), ItemList.Electric_Motor_EV.get(2L), GT_ModHandler.getModItem("IC2", "blockGenerator", 1, 9),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 6)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 8), null, 20 * 20, 1024);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hatch_Dynamo_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.HSSG, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Trinium, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                ItemList.Electric_Motor_IV.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 8), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyN, 8)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 9), null, 30 * 20, 4096);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hatch_Dynamo_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.VanadiumGallium, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Draconium, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2),
                ItemList.Electric_Motor_LuV.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 9), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lafium, 10)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 10), null, 40 * 20, 16384);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hatch_Dynamo_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt12, Materials.Duranium, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Oriharukon, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 2),
                ItemList.Electric_Motor_ZPM.get(2L), GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 10), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CinobiteA243, 12)
        }, GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 11), null, 50 * 20, 65536);
        /* ================================= end  Compact-Kinetic =================================*/

/** ================================= start GT =================================*/

        //Teleporter
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("EnderIO", "blockTransceiver", 1, 0), ItemList.Energy_LapotronicOrb2.get(1L), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialIOPort", 1, 0), ItemList.Field_Generator_LuV.get(3L),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 1, 0), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumRing", 8, 0), ItemList.Tool_DataOrb.get(4L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 12, 0), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Europium, 12)
        }, ItemList.Teleporter.get(1L), null, 50 * 20, 30720);

        //Fusion Casing MK1
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 4), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyN, 4), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 4, 1),
                CoreItems2.getRecipe(142,4), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.TungstenSteel, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8), ItemList.Casing_LuV.get(8L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Europium, 12), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)
        }, ItemList.Casing_Fusion.get(8L), null, 50 * 20, 30720);
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 6), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lafium, 6), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
                CoreItems2.getRecipe(142,6), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.HSSS, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8), ItemList.Casing_LuV.get(20L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Americium, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 32)
        }, ItemList.Casing_Fusion.get(20L), null, 100 * 20, 122880);
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 8), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CinobiteA243, 8), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 8, 1),
                CoreItems2.getRecipe(142,8), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Osmiridium, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8), ItemList.Casing_LuV.get(60L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 20), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 64)
        }, ItemList.Casing_Fusion.get(60L), null, 200 * 20, 500000);

        //Fusion Casing MK2
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 4), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lafium, 4), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 4, 1),
                ItemList.Neutron_Reflector.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Osmiridium, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8), ItemList.Casing_Fusion.get(8L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Americium, 12), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSS, 16)
        }, ItemList.Casing_Fusion2.get(8L), null, 50 * 20, 122880);
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 6), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CinobiteA243, 6), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
                ItemList.Neutron_Reflector.get(6L), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Duranium, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8), ItemList.Casing_Fusion.get(20L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSS, 32)
        }, ItemList.Casing_Fusion2.get(20L), null, 100 * 20, 500000);
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_UHV.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 8), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Pikyonium64B, 8), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 8, 1),
                ItemList.Neutron_Reflector.get(8L), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.ElectrumFlux, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 8), ItemList.Casing_Fusion.get(60L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Phoenixite, 20), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSS, 64)
        }, ItemList.Casing_Fusion2.get(60L), null, 200 * 20, 2000000);

        //Fusion Casing MK3
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 4), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CinobiteA243, 4), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 4, 1),
                ItemList.Neutron_Reflector.get(8L), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Duranium, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8), ItemList.Casing_Fusion2.get(8L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 12), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Adamantium, 16)
        }, ItemList.Casing_Fusion3.get(8L), null, 50 * 20, 500000);
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_UHV.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 6), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Pikyonium64B, 6), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 6, 1),
                ItemList.Neutron_Reflector.get(10L), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.ElectrumFlux, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 8), ItemList.Casing_Fusion2.get(20L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Phoenixite, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Adamantium, 32)
        }, ItemList.Casing_Fusion3.get(20L), null, 100 * 20, 2000000);
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Field_Generator_UEV.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Superconductor, 8), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Quantum, 8), GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 8, 1),
                ItemList.neutroniumHeatCapacitor.get(8L), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.BlackPlutonium, 8), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 8), ItemList.Casing_Fusion2.get(60L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CosmicNeutronium, 20), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Adamantium, 64)
        }, ItemList.Casing_Fusion3.get(60L), null, 200 * 20, 8000000);

        //Steam Turbine
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2), GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Steel, 2),
                GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 4), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 12)
        }, ItemList.LargeSteamTurbine.get(1L), null, 20 * 20, 120);

        //Gas Turbine
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2), GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.StainlessSteel, 2),
                GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.StainlessSteel, 4), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StainlessSteel, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 12)
        }, ItemList.LargeGasTurbine.get(1L), null, 40 * 20, 480);

        //HP Turbine
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 4), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Titanium, 4),
                GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Titanium, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Inconel792, 8), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 16)
        }, ItemList.LargeHPSteamTurbine.get(1L), null, 60 * 20, 1920);

        //Plasma Turbine
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.NaquadahAlloy, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Oriharukon, 8),
                GT_OreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Naquadah, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.NaquadahAlloy, 8)/*TriniumNaquadahAlloy*/, GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Draconium, 8), GT_OreDictUnificator.get(OrePrefixes.round, Materials.NaquadahAlloy, 16),
                GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Osmiridium, 16)
        }, ItemList.LargePlasmaTurbine.get(1L), null, 100 * 20, 122880);

        //Heat Exchanger
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), ItemList.Casing_Firebox_Titanium.get(1L), ItemList.Electric_Pump_EV.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 2), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 4), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Titanium, 4),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 8)
        }, ItemList.Machine_Multi_HeatExchanger.get(1L), null, 40 * 20, 480);

        //OilDrill1
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_MV.get(1L), ItemList.Electric_Motor_MV.get(2L), ItemList.Electric_Pump_MV.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 2), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 4),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 8)
        }, ItemList.OilDrill1.get(1L), null, 20 * 20, 120);

        //OilDrill2
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_HV.get(1L), ItemList.Electric_Motor_HV.get(2L), ItemList.Electric_Pump_HV.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 2), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 10)
        }, ItemList.OilDrill2.get(1L), null, 40 * 20, 480);

        //OilDrill3
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), ItemList.Electric_Motor_EV.get(2L), ItemList.Electric_Pump_EV.get(2L), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 4),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 8), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 12), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 16)
        }, ItemList.OilDrill3.get(1L), null, 60 * 20, 1920);

        //OreDrill1
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), ItemList.Electric_Motor_EV.get(2L), ItemList.Conveyor_Module_EV.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 2), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 12)
        }, ItemList.OreDrill1.get(1L), null, 40 * 20, 480);

        //OreDrill2
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), ItemList.Electric_Motor_IV.get(2L), ItemList.Conveyor_Module_IV.get(2L), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Desh, 4),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 12), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 16)
        }, ItemList.OreDrill2.get(1L), null, 60 * 20, 1920);

        //OreDrill3
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_LuV.get(1L), ItemList.Electric_Motor_LuV.get(2L), ItemList.Conveyor_Module_LuV.get(2L), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Trinium, 8),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Osmiridium, 12), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 20)
        }, ItemList.OreDrill3.get(1L), null, 80 * 20, 7680);

        //OreDrill4
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_ZPM.get(1L), ItemList.Electric_Motor_ZPM.get(4L), ItemList.Conveyor_Module_ZPM.get(4L), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Oriharukon, 12),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 12), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 16), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Osmiridium, 24)
        }, ItemList.OreDrill4.get(1L), null, 100 * 20, 30720);

        //OreDrill4
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Hull_UV.get(1L), ItemList.Electric_Motor_UV.get(6L), ItemList.Conveyor_Module_UV.get(6L), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Adamantium, 16),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 16), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 20), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Tritanium, 26)
        }, ItemList.OreDrill5.get(1L), null, 120 * 20, 122880);

        //Oil Cracker
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4), ItemList.Casing_Coil_Cupronickel.get(4L),
                ItemList.Electric_Pump_HV.get(4L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 8)
        }, ItemList.OilCracker.get(1L), null, 20 * 20, 120);

        //Assembly Line
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Machine_IV_Assembler.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Casing_Gearbox_TungstenSteel.get(4L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 4),
                ItemList.Conveyor_Module_IV.get(4L), ItemList.Robot_Arm_IV.get(4L), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Inconel792, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, ItemList.Machine_Multi_Assemblyline.get(1L), null, 120 * 20, 7680);

        //DieselGen1
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 2),
                ItemList.Electric_Piston_EV.get(2L), ItemList.Electric_Motor_EV.get(2L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8)
        }, ItemList.Machine_Multi_DieselEngine.get(1L), null, 40 * 20, 1920);

        //DieselGen2
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 2),
                ItemList.Electric_Piston_IV.get(2L), ItemList.Electric_Motor_IV.get(2L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 8)
        }, ItemList.Machine_Multi_DieselEngine2.get(1L), null, 80 * 20, 7680);

        //LCR
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_HV.get(1L), ItemList.Electric_Pump_HV.get(2L), ItemList.Electric_Motor_HV.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 2), GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Polytetrafluoroethylene, 2),
                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.StainlessSteel, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 6)
        }, ItemList.Machine_Multi_LargeChemicalReactor.get(1L), null, 20 * 20, 120);

        //Implosion Compressor
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Machine_HV_Compressor.get(1L), ItemList.Electric_Piston_HV.get(2L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Steel, 8), ItemList.Casing_ExplosionHazard.get(1L)
        }, ItemList.Machine_Multi_ImplosionCompressor.get(1L), null, 30 * 20, 256);

        //Vacuum Freezer
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Machine_HV_Extractor.get(1L), ItemList.Electric_Pump_HV.get(2L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Aluminium, 8), ItemList.Casing_FrostHazard.get(1L)
        }, ItemList.Machine_Multi_VacuumFreezer.get(1L), null, 30 * 20, 256);

        //Titanium
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), ItemList.Casing_Firebox_Titanium.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Titanium, 3),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 4), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Titanium, 12)
        }, ItemList.Machine_Multi_LargeBoiler_Titanium.get(1L), null, 40 * 20, 480);

        //TungstenSteel
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_IV.get(1L), ItemList.Casing_Firebox_TungstenSteel.get(1L), GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.TungstenSteel, 3),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 4), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 12)
        }, ItemList.Machine_Multi_LargeBoiler_TungstenSteel.get(1L), null, 60 * 20, 1920);

        //Distillation_Tower
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), ItemList.Electric_Pump_EV.get(4L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 8), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 12)
        }, ItemList.Distillation_Tower.get(1L), null, 40 * 20, 480);

        //Centrifuge
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Machine_EV_Centrifuge.get(1L), ItemList.Electric_Motor_EV.get(4L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8)
        }, ItemList.Machine_MultiblockCentrifuge.get(1L), null, 40 * 20, 480);

        //Electrolyzer
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Machine_EV_Electrolyzer.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Aluminium, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Tungsten, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8)
        }, ItemList.Machine_MultiblockElectrolyzer.get(1L), null, 40 * 20, 480);

        //Flotation Unit
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Hull_EV.get(1L), ItemList.Electric_Pump_EV.get(4L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 6),
                GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 8), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 12)
        }, ItemList.Machine_FlotationUnit.get(1L), null, 40 * 20, 480);

        //Solar Panel LV 32 EU
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Cover_SolarPanel_8V.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polytetrafluoroethylene, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4),
                ItemList.Circuit_Silicon_Wafer2.get(4L), CoreItems2.getRecipe(117,4), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorMV, 4)
        }, ItemList.Cover_SolarPanel_LV.get(1L), null, 20 * 20, 120);

        //Solar Panel MV 128 EU
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                ItemList.Cover_SolarPanel_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Epoxid, 2), ItemList.Circuit_Chip_ULPIC.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4), ItemList.Circuit_Silicon_Wafer2.get(4L),
                CoreItems2.getRecipe(118,4), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorHV, 4)
        }, ItemList.Cover_SolarPanel_MV.get(1L), null, 40 * 20, 480);

        //Solar Panel HV 512 EU
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Cover_SolarPanel_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.IndiumGalliumPhosphide, 2), ItemList.Circuit_Chip_LPIC.get(2L), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 4),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 6), ItemList.Circuit_Silicon_Wafer2.get(4L), CoreItems2.getRecipe(119,4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicon, 4),
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorEV, 6)
        }, ItemList.Cover_SolarPanel_HV.get(1L), null, 60 * 20, 1920);

        //Solar Panel EV 2048 EU
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Cover_SolarPanel_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polybenzimidazole, 4), ItemList.Circuit_Chip_PIC.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 6),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 6), ItemList.Circuit_Silicon_Wafer4.get(4L), CoreItems2.getRecipe(120,4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Silicon, 4),
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorIV, 6)
        }, ItemList.Cover_SolarPanel_EV.get(1L), null, 80 * 20, 7680);

        //Solar Panel IV 8192 EU
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Cover_SolarPanel_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polybenzimidazole, 6), ItemList.Circuit_Chip_HPIC.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 8),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8), ItemList.Circuit_Silicon_Wafer3.get(6L), CoreItems2.getRecipe(121,4), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Silicon, 6),
                GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorLuV, 8)
        }, ItemList.Cover_SolarPanel_IV.get(1L), null, 100 * 20, 30720);

        //Solar Panel LuV 32768 EU
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Cover_SolarPanel_IV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Polybenzimidazole, 8), ItemList.Circuit_Chip_UHPIC.get(6L), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 10),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8), ItemList.Circuit_Silicon_Wafer7.get(8L), CoreItems2.getRecipe(122,6), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Silicon, 6),
                GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorZPM, 8)
        }, ItemList.Cover_SolarPanel_LuV.get(1L), null, 120 * 20, 122880);

        //Solar Panel ZPM 131072 EU
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Cover_SolarPanel_LuV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Polybenzimidazole, 10), ItemList.Circuit_Chip_QPIC.get(8L), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 12),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 10), ItemList.Circuit_Silicon_Wafer8.get(10L), CoreItems2.getRecipe(123,6), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, Materials.Silicon, 8),
                GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.SuperconductorUV, 10)
        }, ItemList.Cover_SolarPanel_ZPM.get(1L), null, 140 * 20, 500000);

        //Solar Panel UV 524288 EU
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                ItemList.Cover_SolarPanel_ZPM.get(2L), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Polybenzimidazole, 12), ItemList.Circuit_Chip_FPIC.get(10L), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 14),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 10), ItemList.Circuit_Silicon_Wafer10.get(12L), CoreItems2.getRecipe(124,8), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Silicon, 8),
                GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.Superconductor, 12)
        }, ItemList.Cover_SolarPanel_UV.get(1L), null, 160 * 20, 2000000);
        /* ================================= end  GT =================================*/
        /** ================================= START GALACTICRAFT =================================*/
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                CoreItems2.getRecipe(0, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.noseCone", 1L, 0), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedSDHD120", 3L, 0),
                GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 8L, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.rocketFins", 4L, 0), GT_ModHandler.getModItem("GalaxySpace", "item.ModuleSmallFuelCanister", 1L, 0),
                GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L, 0)
        }, GT_ModHandler.getModItem("GalacticraftCore", "item.spaceship", 1L, 0), null, 100 * 20, 480);

        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                CoreItems2.getRecipe(1, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.noseCone", 1L, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 3L, 0),
                GT_ModHandler.getModItem("GalacticraftMars", "item.null", 10L, 3), GT_ModHandler.getModItem("GalacticraftCore", "item.rocketFins", 4L, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 2L, 1),
                GT_ModHandler.getModItem("GalaxySpace", "item.ModuleSmallFuelCanister", 2L, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 2L, 0)
        }, GT_ModHandler.getModItem("GalacticraftMars", "item.spaceshipTier2", 1L, 0), null, 120 * 20, 1920);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                CoreItems2.getRecipe(2, 0), GT_ModHandler.getModItem("GalacticraftMars", "item.heavyNoseCone", 1L, 0), GT_ModHandler.getModItem("GalacticraftMars", "item.null", 4L, 3), GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 12L, 0),
                GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 4L, 2), GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 4L, 1), CoreItems2.getRecipe(133, 2),
                GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 1)
        }, GT_ModHandler.getModItem("GalacticraftMars", "item.itemTier3Rocket", 1L, 0), null, 140 * 20, 7680);

        /* ================================= END GALACTICRAFT =================================*/

    }
}