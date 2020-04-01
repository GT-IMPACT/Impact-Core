package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import com.impact.mods.GregTech.GTregister.GT_Materials;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class Printer3DRecipe implements Runnable{
    @Override
    public void run(){
/** ================================= start IMPACT MOD =================================*/
//Controlers
        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.GumMetal, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.GumMetal, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Titaniolum, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Titaniolum, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_PBE.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Zamak, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Robot_Arm_IV.get(4L, new Object[0]), ItemList.Emitter_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Zamak, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Duraluminium, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Duraluminium, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_LaserEngraver.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Inconel690, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Robot_Arm_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Inconel690, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Assembler.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Inconel792, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Inconel792, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Centrifuge.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Zamak, 4),
                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Zamak, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Electrolyzer.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Titaniolum, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Conveyor_Module_IV.get(4L, new Object[0]), ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Robot_Arm_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Titaniolum, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Inconel690, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Inconel690, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Wire.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.MaragingSteel250, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.MaragingSteel250, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Inconel792, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Inconel792, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Supply.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Electric_Pump_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Utility.get(1L, new Object[0]), null, 40*20,7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Talonite, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Electric_Pump_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Talonite, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Grisium, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Grisium, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Brewmenter.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Inconel690, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Inconel690, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Siftarator.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol60, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Nitinol60, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Kovar, 4),
                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Kovar, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_ArcFurnace.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.TiBetaC, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Electric_Pump_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Talonite, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Talonite, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Extradifier.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Titaniolum, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Titaniolum, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.MaragingSteel250, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.MaragingSteel250, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Mixer.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Mangalloy, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Mangalloy, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Macerator.get(1L, new Object[0]), null, 40*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.MaragingSteel300, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.MaragingSteel300, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Stellite, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Stellite, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Cutting.get(1L, new Object[0]), null, 40*20, 7680);

//Upgrade Casing
        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1, 3), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.GumMetal, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Nitinol, 16),
                ItemList.Electric_Motor_IV.get(4L), ItemList.Electric_Piston_IV.get(4L), ItemList.Conveyor_Module_IV.get(4L), ItemList.Robot_Arm_IV.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Graphene, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 16), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.SteelMagnetic, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 16)
        }, GT_ItemList.UpgradeCasingT1.get(1L, new Object[0]), null, 80*20, 7680);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_LuV.get(1L), GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 0), GT_ItemList.UpgradeCasingT1.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 16),//HastelloyN
                ItemList.Electric_Motor_LuV.get(4L), ItemList.Electric_Piston_LuV.get(4L), ItemList.Conveyor_Module_LuV.get(4L), ItemList.Robot_Arm_LuV.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.YttriumBariumCuprate, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Chrome, 16), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.NeodymiumMagnetic, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 32), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 32)
        }, GT_ItemList.UpgradeCasingT2.get(1L, new Object[0]), null, 120*20, 30720);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_ZPM.get(1L), GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 1), GT_ItemList.UpgradeCasingT2.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.WroughtIron, 16),//Lafium
                ItemList.Electric_Motor_ZPM.get(4L), ItemList.Electric_Piston_ZPM.get(4L), ItemList.Conveyor_Module_ZPM.get(4L), ItemList.Robot_Arm_ZPM.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Naquadah, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Iridium, 16), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.EuropiumoxideMagnetic, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 48), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 48)
        }, GT_ItemList.UpgradeCasingT3.get(1L, new Object[0]), null, 160*20, 122880);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_UV.get(1L), GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 2), GT_ItemList.UpgradeCasingT3.get(4L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 16),//CinobiteA243
                ItemList.Electric_Motor_UV.get(4L), ItemList.Electric_Piston_UV.get(4L), ItemList.Conveyor_Module_UV.get(4L), ItemList.Robot_Arm_UV.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.NaquadahAlloy, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Osmium, 16), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.EuropiumoxideMagnetic, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 64)
        }, GT_ItemList.UpgradeCasingT4.get(1L, new Object[0]), null, 200*20, 500000);

        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[] {
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 500), ItemList.Cover_ItemDetector.get(8L), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol, 8),
                ItemList.Robot_Arm_IV.get(8L), GT_ItemList.DDDPrinterCasing3x3.get(9), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 16),
                GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 16), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.GumMetal, 32), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64)
        }, GT_ItemList.DDDPrinterCasing4x4.get(16L), null, 200*20, 1920);
/* ================================= end IMPACT MOD =================================*/
    }
}