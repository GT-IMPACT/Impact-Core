package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import com.impact.mods.GregTech.GTregister.GT_Materials;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
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
        }, GT_ItemList.Machine_PBE.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Zamak, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Robot_Arm_IV.get(4L, new Object[0]), ItemList.Emitter_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Zamak, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Duraluminium, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Duraluminium, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_LaserEngraver.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Inconel690, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Robot_Arm_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Inconel690, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Assembler.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Inconel792, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Inconel792, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Centrifuge.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Zamak, 4),
                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Zamak, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Electrolyzer.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Titaniolum, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Conveyor_Module_IV.get(4L, new Object[0]), ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Robot_Arm_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Titaniolum, 8),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Inconel690, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Inconel690, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Wire.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.MaragingSteel250, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.MaragingSteel250, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Inconel792, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Inconel792, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Supply.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Electric_Pump_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Utility.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Talonite, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Electric_Pump_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Talonite, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Grisium, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Grisium, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Brewmenter.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Nitinol, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Inconel690, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Inconel690, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Siftarator.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol60, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Nitinol60, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Kovar, 4),
                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Kovar, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_ArcFurnace.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.TiBetaC, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Electric_Pump_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.TiBetaC, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Talonite, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Talonite, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Extradifier.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Titaniolum, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.Titaniolum, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.MaragingSteel250, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.MaragingSteel250, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Mixer.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.HastelloyC276, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Piston_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.HastelloyC276, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Mangalloy, 8), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Mangalloy, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Macerator.get(1L, new Object[0]), null, 7680, 40*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                ItemList.Hull_IV.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.MaragingSteel300, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 8), ItemList.Cover_Screen.get(1L),
                ItemList.Electric_Motor_IV.get(4L, new Object[0]), ItemList.Conveyor_Module_IV.get(4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, GT_Materials.MaragingSteel300, 8), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSG, 4),
                GT_OreDictUnificator.get(OrePrefixes.gear, GT_Materials.Stellite, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, GT_Materials.Stellite, 16), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 16),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)
        }, GT_ItemList.Machine_Cutting.get(1L, new Object[0]), null, 7680, 40*20);
/* ================================= end IMPACT MOD =================================*/
    }
}