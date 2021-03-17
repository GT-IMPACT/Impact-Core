package com.impact.recipes.machines;

import com.impact.mods.gregtech.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class UnboxingRecipe implements Runnable {

  @Override
  public void run() {
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 0),
        ItemList.Cover_SolarPanel_8V.get(1L), GT_ItemList.ULVRobotArm.get(1L), 800, 1);
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 1),
        ItemList.Cover_SolarPanel_LV.get(1L), ItemList.Robot_Arm_LV.get(1L), 1000, 2);
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 2),
        ItemList.Cover_SolarPanel_MV.get(1L), ItemList.Robot_Arm_MV.get(1L), 1200, 4);
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 3),
        ItemList.Cover_SolarPanel_HV.get(1L), ItemList.Robot_Arm_HV.get(1L), 1400, 16);
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 4),
        ItemList.Cover_SolarPanel_EV.get(1L), ItemList.Robot_Arm_EV.get(1L), 1600, 64);
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 5),
        ItemList.Cover_SolarPanel_IV.get(1L), ItemList.Robot_Arm_IV.get(1L), 1800, 256);
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 6),
        ItemList.Cover_SolarPanel_LuV.get(1L), ItemList.Robot_Arm_LuV.get(1L), 2000, 1024);
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 7),
        ItemList.Cover_SolarPanel_ZPM.get(1L), ItemList.Robot_Arm_ZPM.get(1L), 2200, 4096);
    GT_Values.RA.addUnboxingRecipe(GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 8),
        ItemList.Cover_SolarPanel_UV.get(1L), ItemList.Robot_Arm_UV.get(1L), 2400, 7680);


    GT_Values.RA.addUnboxingRecipe(
        GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 1),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.Energy_LapotronicOrb.get(1L), 1600, 64);
    GT_Values.RA.addUnboxingRecipe(
        GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 6),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.EnergyCrystal_IV.get(1L), 1600, 64);
    GT_Values.RA.addUnboxingRecipe(
        GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 2),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.Energy_LapotronicOrb2.get(1L), 1800, 256);
    GT_Values.RA.addUnboxingRecipe(
        GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 3),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.Energy_Module.get(1L), 2000, 1024);
    GT_Values.RA.addUnboxingRecipe(
        GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 4),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.Energy_Cluster.get(1L), 2200, 4096);
    GT_Values.RA.addUnboxingRecipe(
        GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 5),
        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1),
        ItemList.ZPM2.get(1L), 2400, 7680);
  }
}
