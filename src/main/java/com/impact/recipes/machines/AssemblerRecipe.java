package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import com.impact.loader.ItemRegistery;
import com.impact.mods.GregTech.GT_ItemList;
import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import static com.impact.common.item.Core_List_Items.*;
import static com.impact.loader.ItemRegistery.*;
import static com.impact.mods.GregTech.GT_ItemList.Casing_Farm;
import static com.impact.util.SendUtils.simpleMetaStack;
import static com.impact.util.Utilits.Blockstack;

public class AssemblerRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {

        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 8L), ItemList.Casing_Tank_0.get(1L), GT_ModHandler.getModItem("impact", "WroughtIronChest", 1L), 200, 16);


/** ================================= start IMPACT MOD =================================*/
        //Casings
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.GumMetal, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titaniolum, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.PBECasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Zamak, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Duraluminium, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.EngraverCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel690, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nitinol, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.AssemblerCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TiBetaC, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.CentrifugeCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyC276, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zamak, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.ElectrolyzerCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titaniolum, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Inconel690, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.WireFactoryCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.MaragingSteel250, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Inconel792, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.SupplyProductionCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyC276, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.UtilityMachineCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Talonite, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Grisium, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.BrewmenterCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Inconel690, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.ElectromagneticCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Nitinol60, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Kovar, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.ArcCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TiBetaC, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Talonite, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.ExtradificationCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titaniolum, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.MaragingSteel250, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.MixingCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyC276, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Mangalloy, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.MacerationCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.MaragingSteel300, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Stellite, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.CuttingCasing.get(2), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Machine_HV_Printer.get(1L),ItemList.Cover_Crafting.get(1L), ItemList.Conveyor_Module_HV.get(2L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.BlackSteel, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.StainlessSteel, 16)}, Materials.SolderingAlloy.getMolten(288L), GT_ItemList.Machine_DDDPrinter.get(1), 200, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedSteel, 8)}, null, GT_ItemList.DDDPrinterCasing.get(2), 100, 120);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 340), ItemList.Cover_ItemDetector.get(4L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 4), ItemList.Robot_Arm_HV.get(4L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 8), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.BlackSteel, 16), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 24), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.BlueSteel, 32)}, null, GT_ItemList.DDDPrinterCasing3x3.get(9), 800, 120);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.GumMetal, 1), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Naquadah, 1), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Desh, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 12), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 12), ItemList.Field_Generator_MV.get(1L)}, null, GT_ItemList.NqCasing.get(2), 200, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 6), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlackSteel, 12), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSS, 16)}, Materials.HSSG.getMolten(288L), GT_ItemList.CycloneCasing.get(2), 200, 7680);

        //MultiAmp Hatches
        /*GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_LuV_IV.get(1L), ItemList.Hatch_Energy_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nitinol, 2)}, Materials.Electrum.getMolten(288), GT_ItemList.Energy_4A_IV.get(1), 100, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_ZPM_LuV.get(1L), ItemList.Hatch_Energy_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.VanadiumGallium, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyN, 2)}, Materials.Electrum.getMolten(576), GT_ItemList.Energy_4A_LuV.get(1), 100, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_HA_ZPM_LuV.get(1L), GT_ItemList.Energy_4A_LuV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.VanadiumGallium, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyN, 4)}, Materials.Tungsten.getMolten(576), GT_ItemList.Energy_16A_LuV.get(1), 200, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_UV_ZPM.get(1L), ItemList.Hatch_Energy_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Naquadah, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 2)}, Materials.Electrum.getMolten(1152), GT_ItemList.Energy_4A_ZPM.get(1), 100, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_HA_UV_ZPM.get(1L), GT_ItemList.Energy_4A_ZPM.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Naquadah, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 4)}, Materials.Tungsten.getMolten(1152), GT_ItemList.Energy_16A_ZPM.get(1), 200, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.WetTransformer_UV_ZPM.get(1L), GT_ItemList.Energy_16A_ZPM.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.Naquadah, 6), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 6)}, Materials.NiobiumTitanium.getMolten(1152), GT_ItemList.Energy_64A_ZPM.get(1), 400, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_MAX_UV.get(1L), ItemList.Hatch_Energy_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.ElectrumFlux, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 2)}, Materials.Electrum.getMolten(2304), GT_ItemList.Energy_4A_UV.get(1), 100, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_HA_UHV_UV.get(1L), GT_ItemList.Energy_4A_UV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.ElectrumFlux, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 4)}, Materials.Tungsten.getMolten(2304), GT_ItemList.Energy_16A_UV.get(1), 200, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.WetTransformer_UHV_UV.get(1L), GT_ItemList.Energy_16A_UV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.ElectrumFlux, 6), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 6)}, Materials.NiobiumTitanium.getMolten(2304), GT_ItemList.Energy_64A_UV.get(1), 400, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_Ultra_UHV_UV.get(1L), GT_ItemList.Energy_64A_UV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.ElectrumFlux, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 8)}, Materials.Enderium.getMolten(2304), GT_ItemList.Energy_256A_UV.get(1), 800, 500000);

        //MultiAmp Dynamos
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hatch_Dynamo_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.BlackSteel, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1)}, Materials.Silver.getMolten(144), GT_ItemList.Dynamo_2A_EV.get(1), 50, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hatch_Dynamo_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.TungstenSteel, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nitinol, 1)}, Materials.Silver.getMolten(288), GT_ItemList.Dynamo_2A_IV.get(1), 50, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_LuV_IV.get(1L), GT_ItemList.Dynamo_2A_IV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nitinol, 2)}, Materials.Electrum.getMolten(288), GT_ItemList.Dynamo_4A_IV.get(1), 100, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hatch_Dynamo_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.VanadiumGallium, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyN, 1)}, Materials.Silver.getMolten(576), GT_ItemList.Dynamo_2A_LuV.get(1), 50, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_ZPM_LuV.get(1L), GT_ItemList.Dynamo_2A_LuV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.VanadiumGallium, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyN, 2)}, Materials.Electrum.getMolten(576), GT_ItemList.Dynamo_4A_LuV.get(1), 100, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_HA_ZPM_LuV.get(1L), GT_ItemList.Dynamo_4A_LuV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.VanadiumGallium, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyN, 4)}, Materials.Tungsten.getMolten(576), GT_ItemList.Dynamo_16A_LuV.get(1), 200, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hatch_Dynamo_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Naquadah, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 1)}, Materials.Silver.getMolten(1152), GT_ItemList.Dynamo_2A_ZPM.get(1), 50, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_UV_ZPM.get(1L), GT_ItemList.Dynamo_2A_ZPM.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Naquadah, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 2)}, Materials.Electrum.getMolten(1152), GT_ItemList.Dynamo_4A_ZPM.get(1), 100, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_HA_UV_ZPM.get(1L), GT_ItemList.Dynamo_4A_ZPM.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Naquadah, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 4)}, Materials.Tungsten.getMolten(1152), GT_ItemList.Dynamo_16A_ZPM.get(1), 200, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.WetTransformer_UV_ZPM.get(1L), GT_ItemList.Dynamo_16A_ZPM.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.Naquadah, 6), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 6)}, Materials.NiobiumTitanium.getMolten(1152), GT_ItemList.Dynamo_64A_ZPM.get(1), 400, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hatch_Dynamo_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.ElectrumFlux, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 1)}, Materials.Silver.getMolten(2304), GT_ItemList.Dynamo_2A_UV.get(1), 50, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_MAX_UV.get(1L), GT_ItemList.Dynamo_2A_UV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.ElectrumFlux, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 2)}, Materials.Electrum.getMolten(2304), GT_ItemList.Dynamo_4A_UV.get(1), 100, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_HA_UHV_UV.get(1L), GT_ItemList.Dynamo_4A_UV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.ElectrumFlux, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 4)}, Materials.Tungsten.getMolten(2304), GT_ItemList.Dynamo_16A_UV.get(1), 200, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.WetTransformer_UHV_UV.get(1L), GT_ItemList.Dynamo_16A_UV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.ElectrumFlux, 6), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 6)}, Materials.NiobiumTitanium.getMolten(2304), GT_ItemList.Dynamo_64A_UV.get(1), 400, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Transformer_Ultra_UHV_UV.get(1L), GT_ItemList.Dynamo_64A_UV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.ElectrumFlux, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 8)}, Materials.Enderium.getMolten(2304), GT_ItemList.Dynamo_256A_UV.get(1), 800, 500000);
*/
        //SolarPanel
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_8V.get(1L), GT_ItemList.ULVRobotArm.get(1L), ItemList.Large_Fluid_Cell_Steel.get(1L), ItemList.Sensor_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicon, 6), ItemList.Battery_Buffer_2by2_ULV.get(1L)}, Materials.SolderingAlloy.getMolten(144), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 0), 100, 30);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_LV.get(1L), ItemList.Robot_Arm_LV.get(1L), ItemList.Large_Fluid_Cell_Aluminium.get(1L), ItemList.Sensor_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Silicon, 6), ItemList.Battery_Buffer_2by2_LV.get(1L)}, Materials.SolderingAlloy.getMolten(288), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 1), 200, 120);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_MV.get(1L), ItemList.Robot_Arm_MV.get(1L), ItemList.Large_Fluid_Cell_StainlessSteel.get(1L), ItemList.Sensor_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 6), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Silicon, 6), ItemList.Battery_Buffer_2by2_MV.get(1L)}, Materials.SolderingAlloy.getMolten(576), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 2), 300, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_HV.get(1L), ItemList.Robot_Arm_HV.get(1L), ItemList.Large_Fluid_Cell_Titanium.get(1L), ItemList.Sensor_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.GalliumArsenide, 8), ItemList.Battery_Buffer_2by2_HV.get(1L)}, Materials.SolderingAlloy.getMolten(1152), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 3), 400, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_EV.get(1L), ItemList.Robot_Arm_EV.get(1L), ItemList.Large_Fluid_Cell_TungstenSteel.get(1L), ItemList.Sensor_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 10), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.GalliumArsenide, 8), ItemList.Battery_Buffer_2by2_EV.get(1L)}, Materials.SolderingAlloy.getMolten(2304), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 4), 500, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_IV.get(1L), ItemList.Robot_Arm_IV.get(1L), ItemList.Large_Fluid_Cell_Chrome.get(1L), ItemList.Sensor_LuV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 12), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.GalliumArsenide, 8), ItemList.Battery_Buffer_2by2_IV.get(1L)}, Materials.SolderingAlloy.getMolten(4608), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 5), 600, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_LuV.get(1L), ItemList.Robot_Arm_LuV.get(1L), ItemList.Large_Fluid_Cell_Iridium.get(1L), ItemList.Sensor_ZPM.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 14), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Sunnarium, 10), ItemList.Battery_Buffer_2by2_LuV.get(1L)}, Materials.SolderingAlloy.getMolten(9216), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 6), 700, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_ZPM.get(1L), ItemList.Robot_Arm_ZPM.get(1L), ItemList.Large_Fluid_Cell_Osmium.get(1L), ItemList.Sensor_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 16), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Sunnarium, 10), ItemList.Battery_Buffer_2by2_ZPM.get(1L)}, Materials.SolderingAlloy.getMolten(18432), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 7), 800, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_SolarPanel_UV.get(1L), ItemList.Robot_Arm_UV.get(1L), ItemList.Large_Fluid_Cell_Neutronium.get(1L), ItemList.Sensor_UHV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 18), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Sunnarium, 10), ItemList.Battery_Buffer_2by2_UV.get(1L)}, Materials.SolderingAlloy.getMolten(36864), GT_ModHandler.getModItem("impact", "BlockAdvSolarPanel", 1L, 8), 900, 2000000);

        //Portable Tank
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lead, 8), ItemList.Large_Fluid_Cell_Steel.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_ULV.get(1L), 100, 6);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8), ItemList.Large_Fluid_Cell_Aluminium.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_LV.get(1L), 200, 30);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 8), ItemList.Large_Fluid_Cell_StainlessSteel.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_MV.get(1L), 300, 120);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSLA, 8), ItemList.Large_Fluid_Cell_Titanium.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_HV.get(1L), 400, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 8), ItemList.Large_Fluid_Cell_TungstenSteel.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_EV.get(1L), 500, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyC276, 8), ItemList.Large_Fluid_Cell_Chrome.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_IV.get(1L), 600, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyN, 8), ItemList.Large_Fluid_Cell_Iridium.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_LuV.get(1L), 700, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 8), ItemList.Large_Fluid_Cell_Osmium.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_ZPM.get(1L), 800, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 8), ItemList.Large_Fluid_Cell_Neutronium.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L)}, GT_Values.NF, GT_ItemList.Portable_Tank_UV.get(1L), 900, 500000);

        //Hand Pump
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVPump.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Copper, 1), ItemList.Cell_Empty.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Iron, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1L)}, GT_Values.NF, simpleMetaStack(ItemRegistery.GTPump, 1000, 1), 100, 6);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Pump_LV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1), ItemList.Large_Fluid_Cell_Steel.get(1L), ItemList.Battery_RE_LV_Lithium.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1L)}, GT_Values.NF, simpleMetaStack(ItemRegistery.GTPump, 1001, 1), 120, 20);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Pump_MV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1), ItemList.Large_Fluid_Cell_Aluminium.get(1L), ItemList.Battery_RE_MV_Lithium.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1L)}, GT_Values.NF, simpleMetaStack(ItemRegistery.GTPump, 1002, 1), 140, 48);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Pump_HV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1), ItemList.Large_Fluid_Cell_StainlessSteel.get(1L), ItemList.Battery_RE_HV_Lithium.get(1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Electrum, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1L)}, GT_Values.NF, simpleMetaStack(ItemRegistery.GTPump, 1003, 1), 160, 96);

        //Sawmill
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 6L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1L), GT_Utility.getIntegratedCircuit(2)}, null, GT_ItemList.SawCase.get(2L), 50, 16);

        /* ================================= end IMPACT MOD =================================*/

        /** ================================= start GT MOD =================================*/
        if (!(GT_Mod.gregtechproxy.mComponentAssembler)) {

            //Motors
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.IronMagnetic, 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tin, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 2L)}, GT_Values.NF, GT_ItemList.ULVMotor.get(1L), 100, 8);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L)}, GT_Values.NF, ItemList.Electric_Motor_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L)}, GT_Values.NF, ItemList.Electric_Motor_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L)}, GT_Values.NF, ItemList.Electric_Motor_MV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Electrum, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Silver, 2L)}, GT_Values.NF, ItemList.Electric_Motor_HV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.AnnealedCopper, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2L)}, GT_Values.NF, ItemList.Electric_Motor_EV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2L), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Graphene, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2L)}, GT_Values.NF, ItemList.Electric_Motor_IV.get(1L), 200, 30);

            //Pumps
			GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Lead, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Lead, 2), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Copper, 1)}, GT_Values.NF, GT_ItemList.ULVPump.get(1L), 100, 8);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1)}, GT_Values.NF, ItemList.Electric_Pump_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1)}, GT_Values.NF, ItemList.Electric_Pump_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1)}, GT_Values.NF, ItemList.Electric_Pump_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1)}, GT_Values.NF, ItemList.Electric_Pump_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1)}, GT_Values.NF, ItemList.Electric_Pump_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1)}, GT_Values.NF, ItemList.Electric_Pump_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1)}, GT_Values.NF, ItemList.Electric_Pump_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1)}, GT_Values.NF, ItemList.Electric_Pump_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1)}, GT_Values.NF, ItemList.Electric_Pump_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.HSLA, 1)}, GT_Values.NF, ItemList.Electric_Pump_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.HSLA, 1)}, GT_Values.NF, ItemList.Electric_Pump_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.HSLA, 1)}, GT_Values.NF, ItemList.Electric_Pump_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1)}, GT_Values.NF, ItemList.Electric_Pump_IV.get(1L), 200, 480);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2), GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1)}, GT_Values.NF, ItemList.Electric_Pump_IV.get(1L), 200, 480);

            //Conveyors
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 1)}, GT_Values.NF, GT_ItemList.ULVConveyorModule.get(1L), 40, 4);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1)}, GT_Values.NF, ItemList.Conveyor_Module_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1)}, GT_Values.NF, ItemList.Conveyor_Module_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1)}, GT_Values.NF, ItemList.Conveyor_Module_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1)}, GT_Values.NF, ItemList.Conveyor_Module_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1)}, GT_Values.NF, ItemList.Conveyor_Module_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1)}, GT_Values.NF, ItemList.Conveyor_Module_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1)}, GT_Values.NF, ItemList.Conveyor_Module_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1)}, GT_Values.NF, ItemList.Conveyor_Module_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1)}, GT_Values.NF, ItemList.Conveyor_Module_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1)}, GT_Values.NF, ItemList.Conveyor_Module_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1)}, GT_Values.NF, ItemList.Conveyor_Module_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1)}, GT_Values.NF, ItemList.Conveyor_Module_IV.get(1L), 200, 480);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1)}, GT_Values.NF, ItemList.Conveyor_Module_IV.get(1L), 200, 480);

            //Pistons
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 2), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.WroughtIron, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.WroughtIron, 1)}, GT_Values.NF, GT_ItemList.ULVPiston.get(1L), 100, 8);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1)}, GT_Values.NF, ItemList.Electric_Piston_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 1)}, GT_Values.NF, ItemList.Electric_Piston_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.StainlessSteel, 1)}, GT_Values.NF, ItemList.Electric_Piston_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 1)}, GT_Values.NF, ItemList.Electric_Piston_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TungstenSteel, 1)}, GT_Values.NF, ItemList.Electric_Piston_IV.get(1L), 200, 480);

            //Robot Arms
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(2L), GT_ItemList.ULVPiston.get(1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.WroughtIron, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 3)}, GT_Values.NF, GT_ItemList.ULVRobotArm.get(1L), 100, 8);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(2L), ItemList.Electric_Piston_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 3)}, GT_Values.NF, ItemList.Robot_Arm_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(2L), ItemList.Electric_Piston_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 3)}, GT_Values.NF, ItemList.Robot_Arm_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(2L), ItemList.Electric_Piston_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 3)}, GT_Values.NF, ItemList.Robot_Arm_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(2L), ItemList.Electric_Piston_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 3)}, GT_Values.NF, ItemList.Robot_Arm_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(2L), ItemList.Electric_Piston_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 3)}, GT_Values.NF, ItemList.Robot_Arm_IV.get(1L), 200, 480);

            //Emitter
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2)}, GT_Values.NF, ItemList.Emitter_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Quartzite, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2)}, GT_Values.NF, ItemList.Emitter_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherQuartz, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2)}, GT_Values.NF, ItemList.Emitter_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gemFlawed, Materials.Emerald, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2)}, GT_Values.NF, ItemList.Emitter_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(ChargedGlassLense.getMetaID(), 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2)}, GT_Values.NF, ItemList.Emitter_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(HugeChargedGlassLense.getMetaID(), 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 4), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2)}, GT_Values.NF, ItemList.Emitter_IV.get(1L), 200, 480);

            //Sensor
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),}, GT_Values.NF, ItemList.Sensor_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Quartzite, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),}, GT_Values.NF, ItemList.Sensor_LV.get(1L), 200, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherQuartz, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),}, GT_Values.NF, ItemList.Sensor_MV.get(1L), 200, 60);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gemFlawed, Materials.Emerald, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L),}, GT_Values.NF, ItemList.Sensor_HV.get(1L), 200, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(ChargedGlassLense.getMetaID(), 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1L),}, GT_Values.NF, ItemList.Sensor_EV.get(1L), 200, 240);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(HugeChargedGlassLense.getMetaID(), 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L),}, GT_Values.NF, ItemList.Sensor_IV.get(1L), 200, 480);

            //FieldGenerators
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Emitter_LV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedSteel, 4)}, null, ItemList.Field_Generator_LV.get(1), 600, 30);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Emitter_MV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BT6, 4)}, null, ItemList.Field_Generator_MV.get(1), 600, 120);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Emitter_HV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSSG, 4)}, null, ItemList.Field_Generator_HV.get(1), 600, 480);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Emitter_EV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.NiobiumTitanium, 4)}, null, ItemList.Field_Generator_EV.get(1), 600, 1920);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Emitter_IV.get(1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.HSSS, 4)}, null, ItemList.Field_Generator_IV.get(1L), 600, 7680);

        }

        //Empty Shape Plate
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4), GT_Utility.getIntegratedCircuit(1), GT_Values.NF, ItemList.Shape_Empty.get(1L), 2 * 20, 8);

        //OilDrill1
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MV.get(1L), ItemList.Electric_Motor_MV.get(2L), ItemList.Electric_Pump_MV.get(2L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 2), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 4), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 8)}, GT_Values.NF, ItemList.OilDrill1.get(1L), 20 * 20, 120);

        //Maintenance Hatch
        GT_Values.RA.addAssemblerRecipe(ItemList.Hull_LV.get(1), ItemList.Duct_Tape.get(4), GT_Values.NF, ItemList.Hatch_Maintenance.get(1L), 40 * 20, 30);

        /* ================================= end GT MOD =================================*/

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("IC2", "blockAlloyGlass", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4L), GT_Utility.getIntegratedCircuit(1)}, GT_Values.NF, CoreItems2.getRecipe(54, 1), 100, 120);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(54, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 3L), GT_Utility.getIntegratedCircuit(2)}, GT_Values.NF, CoreItems2.getRecipe(55, 1), 200, 256);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(54, 3), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 6L), GT_Utility.getIntegratedCircuit(3)}, GT_Values.NF, CoreItems2.getRecipe(56, 1), 300, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(54, 1), CoreItems2.getRecipe(55, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 7L), GT_Utility.getIntegratedCircuit(1)}, GT_Values.NF, CoreItems2.getRecipe(56, 1), 100, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(56, 2), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 6L), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.ElectrumFlux, 1L), GT_Utility.getIntegratedCircuit(2)}, GT_Values.NF, CoreItems2.getRecipe(57, 1), 200, 1024);

        //Bars
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 3L), GT_Utility.getIntegratedCircuit(3), CoreItems2.getRecipe(51, 4), 400, 48);

        //Casings
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L), CoreItems2.getRecipe(SteelBars.getMetaID(), 6), Casing_Farm.get(1L), 50, 16);

        //Piston
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.cobblestone, 1L), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L)}, Materials.Redstone.getMolten(72L), Blockstack(UtilBlock, 1, 1), 100, 8, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.cobblestone, 1L), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Bronze, 1L)}, Materials.Redstone.getMolten(72L), Blockstack(UtilBlock, 1, 1), 100, 8, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.cobblestone, 2L), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1L)}, Materials.Redstone.getMolten(144L), Blockstack(UtilBlock, 2, 1), 100, 16, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.cobblestone, 4L), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 1L)}, Materials.Redstone.getMolten(216L), Blockstack(UtilBlock, 4, 1), 200, 30, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.cobblestone, 8L), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.StainlessSteel, 1L)}, Materials.Redstone.getMolten(288L), Blockstack(UtilBlock, 8, 1), 300, 30, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.cobblestone, 16L), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 1L)}, Materials.Redstone.getMolten(576L), Blockstack(UtilBlock, 16, 1), 400, 30, false);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(Blocks.fence), GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 1)}, Materials.Redstone.getMolten(72L), CoreItems2.getRecipe(53, 1), 100, 8, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 1)}, Materials.Redstone.getMolten(72L), CoreItems2.getRecipe(53, 1), 100, 8, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Bronze, 1L), GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 1)}, Materials.Redstone.getMolten(72L), CoreItems2.getRecipe(53, 1), 100, 8, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 1L), GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 2)}, Materials.Redstone.getMolten(144L), CoreItems2.getRecipe(53, 2), 100, 16, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 1L), GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 4)}, Materials.Redstone.getMolten(216L), CoreItems2.getRecipe(53, 4), 200, 30, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 1L), GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 8)}, Materials.Redstone.getMolten(288L), CoreItems2.getRecipe(53, 8), 300, 30, false);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 1L), GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 16)}, Materials.Redstone.getMolten(576L), CoreItems2.getRecipe(53, 16), 400, 30, false);

        if (Loader.isModLoaded("Natura"))
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("Natura", "Natura.fence", 1L), ItemList.Plank_Oak.get(1L)}, Materials.Redstone.getMolten(72L), CoreItems2.getRecipe(53, 1), 100, 30, false);

        if (Loader.isModLoaded("Forestry")) {
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("Forestry", "fences", 1L), ItemList.Plank_Oak.get(1L)}, Materials.Redstone.getMolten(72L), CoreItems2.getRecipe(53, 1), 100, 30, false);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("Forestry", "fencesFireproof", 1L), ItemList.Plank_Oak.get(1L)}, Materials.Redstone.getMolten(72L), CoreItems2.getRecipe(53, 1), 100, 30, false);
        }

        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(53, 1), Blockstack(UtilBlock, 1, 1), null, Blockstack(Blocks.piston, 1), 40, 8);

        //Quantum Armor Parts
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("nanoBoots", 1L, GT_Values.W), CoreItems2.getRecipe(52, 4), CoreItems2.getRecipe(41, 1), 1200, 1920);
        GT_Values.RA.addAssemblerRecipe(Loader.isModLoaded("GraviSuite") ? GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W) : GT_ModHandler.getIC2Item("nanoBodyarmor", 1L, GT_Values.W), CoreItems2.getRecipe(52, 8), CoreItems2.getRecipe(42, 1), 1200, 1920);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("nanoHelmet", 1L, GT_Values.W), CoreItems2.getRecipe(52, 5), CoreItems2.getRecipe(43, 1), 1200, 1920);
        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(43, 1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.ReinforcedGlass, 8L), CoreItems2.getRecipe(44, 1), 1200, 1920);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("nanoLeggings", 1L, GT_Values.W), CoreItems2.getRecipe(52, 7), CoreItems2.getRecipe(45, 1), 1200, 1920);

        //Nano Armor
        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(137, 1), CoreItems2.getRecipe(100, 1), GT_ModHandler.getIC2Item("nanoHelmet", 1L, 27), 400, 120);
        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(138, 1), CoreItems2.getRecipe(100, 1), GT_ModHandler.getIC2Item("nanoBodyarmor", 1L, 27), 400, 120);
        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(139, 1), CoreItems2.getRecipe(100, 1), GT_ModHandler.getIC2Item("nanoLeggings", 1L, 27), 400, 120);
        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(140, 1), CoreItems2.getRecipe(100, 1), GT_ModHandler.getIC2Item("nanoBoots", 1L, 27), 400, 120);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(78, 1), CoreItems2.getRecipe(82, 1)}, new FluidStack(FluidRegistry.getFluid("ic2coolant"), 2000), CoreItems2.getRecipe(80, 1), 1000, 480);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 3), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 6), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 8)}, Materials.Duranium.getMolten(288L), CoreItems2.getRecipe(88, 1), 600, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(88, 1), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 1L, 7), CoreItems2.getRecipe(87, 1)}, Materials.Tritanium.getMolten(288L), CoreItems2.getRecipe(89, 1), 1200, 7680);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2)}, null, CoreItems2.getRecipe(99, 1), 100, 30);

        // --- Charging Lapotron Crystal
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(12), GT_ModHandler.getModItem("IC2", "reactorHeatSwitchDiamond", 1L, 1)}, Materials.SolderingAlloy.getMolten(1440L), GT_ModHandler.getModItem("IC2", "itemBatChargeLamaCrystal", 1L, 0), 200, 1920);

        /** ==== START SOLAR PARTS ==== */
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(109, 1), GT_ModHandler.getModItem("minecraft", "glowstone", 1L), GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Iron, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 2), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 4)}, Materials.SolderingAlloy.getMolten(144), CoreItems2.getRecipe(117, 1), 600, 120);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(110, 1), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 1), GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.MeteoricIron, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 2), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 4)}, Materials.SolderingAlloy.getMolten(288), CoreItems2.getRecipe(118, 1), 600, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(111, 1), GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Ruby, 1), GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 5), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Uranium, 2), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 4)}, Materials.SolderingAlloy.getMolten(576), CoreItems2.getRecipe(119, 1), 600, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(112, 1), CoreItems2.getRecipe(126, 1), GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Naquadah, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plutonium, 2), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 4)}, Materials.SolderingAlloy.getMolten(1152), CoreItems2.getRecipe(120, 1), 600, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(113, 1), CoreItems2.getRecipe(127, 1), GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Draconium, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Europium, 2), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 4)}, Materials.SolderingAlloy.getMolten(2304), CoreItems2.getRecipe(121, 1), 600, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(114, 1), CoreItems2.getRecipe(130, 1), CoreItems2.getRecipe(83, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Americium, 2), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 4)}, Materials.SolderingAlloy.getMolten(4608), CoreItems2.getRecipe(122, 1), 600, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(115, 1), CoreItems2.getRecipe(131, 1), GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Quantium, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 4)}, Materials.SolderingAlloy.getMolten(9216), CoreItems2.getRecipe(123, 1), 600, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(116, 1), CoreItems2.getRecipe(132, 1), GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.BlackPlutonium, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Phoenixite, 2), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 4)}, Materials.SolderingAlloy.getMolten(18432), CoreItems2.getRecipe(124, 1), 600, 2000000);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Sunnarium, 4), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 8)}, null, CoreItems2.getRecipe(127, 1), 800, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Uranium, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 4)}, null, CoreItems2.getRecipe(128, 1), 600, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(126, 1), CoreItems2.getRecipe(128, 8)}, null, CoreItems2.getRecipe(129, 1), 1200, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(127, 1), CoreItems2.getRecipe(129, 8)}, null, CoreItems2.getRecipe(130, 1), 1600, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(130, 1), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Naquadria, 8)}, null, CoreItems2.getRecipe(131, 1), 1800, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(131, 1), GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Neutronium, 8)}, Materials.ElectrumFlux.getMolten(1152), CoreItems2.getRecipe(132, 1), 2000, 2000000);

        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel.get(8L), GT_Utility.getIntegratedCircuit(8), ItemList.Cover_SolarPanel_8V.get(1L), 100, 6);
        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel_8V.get(6L), GT_Utility.getIntegratedCircuit(6), ItemList.Cover_SolarPanel_LV.get(1L), 120, 12);
        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel_LV.get(6L), GT_Utility.getIntegratedCircuit(6), ItemList.Cover_SolarPanel_MV.get(1L), 140, 24);
        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel_MV.get(6L), GT_Utility.getIntegratedCircuit(6), ItemList.Cover_SolarPanel_HV.get(1L), 160, 48);
        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel_HV.get(6L), GT_Utility.getIntegratedCircuit(6), ItemList.Cover_SolarPanel_EV.get(1L), 180, 96);
        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel_EV.get(6L), GT_Utility.getIntegratedCircuit(6), ItemList.Cover_SolarPanel_IV.get(1L), 200, 192);
        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel_IV.get(6L), GT_Utility.getIntegratedCircuit(6), ItemList.Cover_SolarPanel_LuV.get(1L), 220, 384);
        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel_LuV.get(6L), GT_Utility.getIntegratedCircuit(6), ItemList.Cover_SolarPanel_ZPM.get(1L), 240, 768);
        GT_Values.RA.addAssemblerRecipe(ItemList.Cover_SolarPanel_ZPM.get(6L), GT_Utility.getIntegratedCircuit(6), ItemList.Cover_SolarPanel_UV.get(1L), 260, 1536);

        /* ==== END SOLAR PARTS ==== */
        /** ==== START SPACE ADDITION ==== */
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Steel, 1), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedDualBronze", 1L, 0), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedDualAluminium", 1L, 0)}, Materials.StainlessSteel.getMolten(64), CoreItems2.getRecipe(97, 1), 200, 256);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Bronze, 1), GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Aluminium, 1), GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Steel, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 8)}, Materials.StainlessSteel.getMolten(288), ItemList.Ingot_Heavy1.get(1L, new Object[0]), 200, 256);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1L), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedSDHD120", 1L), CoreItems2.getRecipe(93, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 8)}, Materials.Titanium.getMolten(288), ItemList.Ingot_Heavy2.get(1L, new Object[0]), 300, 1024);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 3), CoreItems2.getRecipe(90, 1), CoreItems2.getRecipe(96, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Inconel690, 8)}, Materials.TungstenSteel.getMolten(288), ItemList.Ingot_Heavy3.get(1L, new Object[0]), 400, 4096);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L), CoreItems2.getRecipe(84, 1), CoreItems2.getRecipe(92, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HastelloyN, 8)}, Materials.Chrome.getMolten(288), CoreItems2.getRecipe(16, 1), 500, 16384);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(11, 1), CoreItems2.getRecipe(86, 1), CoreItems2.getRecipe(91, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Lafium, 10)}, Materials.Iridium.getMolten(288), CoreItems2.getRecipe(17, 1), 600, 65536);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(12, 1), CoreItems2.getRecipe(89, 1), CoreItems2.getRecipe(94, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.CinobiteA243, 12)}, Materials.Osmium.getMolten(288), CoreItems2.getRecipe(18, 1), 700, 262144);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(13, 1), CoreItems2.getRecipe(79, 1), CoreItems2.getRecipe(95, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Pikyonium64B, 14)}, Materials.Neutronium.getMolten(288), CoreItems2.getRecipe(19, 1), 800, 1048576);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(14, 1), CoreItems2.getRecipe(77, 1), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Adamantium, 1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Quantum, 16)}, Materials.Phoenixite.getMolten(288), CoreItems2.getRecipe(20, 1), 900, 4194304);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.packEarth.get(64L)}, Materials.HSLA.getMolten(288), GT_ItemList.spacebox1.get(1L), 1200, 64);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.packMoon.get(64L)}, Materials.BT6.getMolten(288), GT_ItemList.spacebox2.get(1L), 1200, 256);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.packMars.get(64L), GT_ItemList.packDeimos.get(64L), GT_ItemList.packPhobos.get(64L)}, Materials.HastelloyC276.getMolten(288), GT_ItemList.spacebox3.get(1L), 1200, 1024);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.packAsteroids.get(64L), GT_ItemList.packCeres.get(64L), GT_ItemList.packEuropa.get(64L), GT_ItemList.packGanymed.get(64L)}, Materials.HastelloyN.getMolten(288), GT_ItemList.spacebox4.get(1L), 1200, 4096);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.packIo.get(64L), GT_ItemList.packMercury.get(64L), GT_ItemList.packVenus.get(64L)}, Materials.Lafium.getMolten(288), GT_ItemList.spacebox5.get(1L), 1200, 16384);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.packIapetus.get(64L), GT_ItemList.packTitan.get(64L), GT_ItemList.packEris.get(64L), GT_ItemList.packOberon.get(64L)}, Materials.CinobiteA243.getMolten(288), GT_ItemList.spacebox6.get(1L), 1200, 65536);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.packProteus.get(64L), GT_ItemList.packTriton.get(64L)}, Materials.Pikyonium64B.getMolten(288), GT_ItemList.spacebox7.get(1L), 1200, 262144);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ItemList.packPluto.get(64L), GT_ItemList.packHaumea.get(64L), GT_ItemList.packMakeMake.get(64L)}, Materials.Quantum.getMolten(288), GT_ItemList.spacebox8.get(1L), 1200, 1048576);

        /* ==== END SPACE ADDITION ==== */
        /** ==== START CASINGS ==== */
        //LuV
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HastelloyN, 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Chrome, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HastelloyN, 6), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 4), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.HastelloyN, 4), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 4), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 4)}, null, ItemList.Casing_LuV.get(1L), 200, 7680);

        //ZPM
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lafium, 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iridium, 6), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lafium, 8), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 8), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Lafium, 4), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 8), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 8)}, null, ItemList.Casing_ZPM.get(1L), 400, 30720);

        //UV
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CinobiteA243, 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Osmium, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 12), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Osmium, 12), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.CinobiteA243, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 12), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 12)}, null, ItemList.Casing_UV.get(1L), 600, 122880);

        //UHV
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Pikyonium64B, 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 10), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Pikyonium64B, 16), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 16), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Pikyonium64B, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)}, null, ItemList.Casing_MAX.get(1L), 800, 500000);

        //UEV
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Quantum, 1), GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Phoenixite, 12), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Quantum, 20), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Phoenixite, 20), GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Quantum, 10), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 24), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 24)}, null, ItemList.Casing_UEV.get(1L), 1000, 2000000);

        //Lapotron Casing
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iridium, 6), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 12), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Palladium, 16), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HastelloyC276, 16), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSE, 16)}, null, GT_ItemList.LSCC.get(1L), 600, 7680);

        /* ====END CASINGS ==== */
        /** ====START QUANTUM SUITE ==== */
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(QuantumPartHelmet.getMetaID(), 1),
                        CoreItems2.getRecipe(QuantumCrystal.getMetaID(), 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 4),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Sensor_IV.get(1L),
                        ItemList.Field_Generator_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(10)},
                Materials.Titanium.getMolten(1728L),
                GT_ModHandler.getIC2Item("quantumHelmet", 1L, 26), 1500, 7680);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(QuantumPartChestplate.getMetaID(), 1),
                        CoreItems2.getRecipe(QuantumCrystal.getMetaID(), 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 6),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(3L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(11)},
                Materials.Titanium.getMolten(2880L),
                GT_ModHandler.getIC2Item("quantumBodyarmor", 1L, 26), 1500, 7680);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(QuantumPartLeggings.getMetaID(), 1),
                        CoreItems2.getRecipe(QuantumCrystal.getMetaID(), 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 6),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(2L),
                        ItemList.Electric_Motor_IV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(12)},
                Materials.Titanium.getMolten(2304L),
                GT_ModHandler.getIC2Item("quantumLeggings", 1L, 26), 1500, 7680);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(QuantumPartBoots.getMetaID(), 1),
                        CoreItems2.getRecipe(QuantumCrystal.getMetaID(), 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 4),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(1L),
                        ItemList.Electric_Piston_IV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(12)},
                Materials.Titanium.getMolten(1440L),
                GT_ModHandler.getIC2Item("quantumBoots", 1L, 26), 1500, 7680);
        /*====END QUANTUM SUITE====*/

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorReflectorThick", 1L, 1), true, false, true);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(NeutronReflectorSmallParts.getMetaID(), 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Beryllium, 2)}, null, GT_ModHandler.getModItem("IC2", "reactorReflectorThick", 1L, 1), 600, 64);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(NeutronReflectorSmallParts.getMetaID(), 1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenCarbide, 1)}, null, GT_ModHandler.getModItem("IC2", "reactorReflectorThick", 1L, 1), 600, 64);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(NeutronReflectorParts.getMetaID(), 1), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 1)}, null, ItemList.Neutron_Reflector.get(1), 1200, 256);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.block, Materials.Glass, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L), GT_Utility.getIntegratedCircuit(1)}, GT_Values.NF, CoreItems2.getRecipe(143, 1), 100, 30);
        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(143, 3), GT_Utility.getIntegratedCircuit(3), CoreItems2.getRecipe(144, 1), 300, 60);
        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(144, 2), GT_Utility.getIntegratedCircuit(2), CoreItems2.getRecipe(145, 1), 600, 90);

        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 5), GT_Utility.getIntegratedCircuit(5), CoreItems2.getRecipe(136, 1), 500, 120);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 8), GT_Utility.getIntegratedCircuit(8), CoreItems2.getRecipe(138, 1), 800, 120);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 7), GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(139, 1), 700, 120);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 4), GT_Utility.getIntegratedCircuit(4), CoreItems2.getRecipe(140, 1), 400, 120);
        GT_Values.RA.addAssemblerRecipe(CoreItems2.getRecipe(136, 1), GT_ModHandler.getModItem("IC2", "itemNightvisionGoggles", 1L, GT_Values.W), CoreItems2.getRecipe(137, 1), 600, 256);

        /** ==== START AE/EC2 ==== */
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_ItemList.GoldCoreChip.get(1L), GT_ItemList.LogicProcessorItemGoldCore.get(1L), 100, 480);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24), GT_ItemList.DiamondCoreChip.get(1L), GT_ItemList.EngineeringProcessorItemDiamondCore.get(1L), 100, 1920);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24), GT_ItemList.EmeraldAdvancedCoreChip.get(1L), GT_ItemList.EngineeringProcessorItemEmeraldCore.get(1L), 100, 4096);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24), GT_ItemList.EmeraldHighAdvancedCoreChip.get(1L), GT_ItemList.EngineeringProcessorItemAdvEmeraldCore.get(1L), 100, 30720);

        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24), GT_ItemList.GoldFluidCoreChip.get(1L), GT_ItemList.EngineeringProcessorFluidGoldCore.get(1L), 100, 480);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24), GT_ItemList.DiamondFluidCoreChip.get(1L), GT_ItemList.EngineeringProcessorFluidDiamondCore.get(1L), 100, 1920);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), GT_ItemList.EngineeringProcessorFluidEmeraldCore.get(1L), 100, 4096);

        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24), GT_ItemList.PulsatingSpatialCoreChip.get(1L), GT_ItemList.EngineeringProcessorSpatialPulsatingCore.get(1L), 100, 4096);

        // --- Storage Cell Component - 1K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_Ram.get(4), CoreItems.getRecipe(38, 4), GT_ItemList.LogicProcessorItemGoldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35), 120, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_NAND.get(3), CoreItems.getRecipe(38, 4), GT_ItemList.GoldCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35), 160, 1920);

        // --- Storage Cell Component - 4K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 35), ItemList.Circuit_Chip_Ram.get(4), GT_ItemList.LogicProcessorItemGoldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36), 160, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 35), ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.GoldCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36), 240, 1920);

        // --- Storage Cell Component - 16K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 36), ItemList.Circuit_Chip_Ram.get(4), GT_ItemList.EngineeringProcessorItemDiamondCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), 180, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 36), ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.DiamondCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), 260, 7680);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_Ram.get(32), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 32L, 8), ItemList.Circuit_Chip_SoC.get(1), GT_ItemList.DiamondCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Europium.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), 30, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_NAND.get(16), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16L, 8), ItemList.Circuit_Chip_SoC2.get(1), GT_ItemList.EngravedDiamondCrystalChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), 30, 122880);

        // --- Storage Cell Component - 64K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 37), ItemList.Circuit_Chip_Ram.get(4), GT_ItemList.EngineeringProcessorItemDiamondCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 37), ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.DiamondCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), 280, 7680);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 37), ItemList.Circuit_Chip_SoC.get(1), GT_ItemList.DiamondCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Europium.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), 30, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2L, 37), ItemList.Circuit_Chip_SoC2.get(1), GT_ItemList.EngravedDiamondCrystalChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), 30, 122880);

        // --- Storage Cell Component - 256K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 38), ItemList.Circuit_Chip_NAND.get(4), GT_ItemList.EngineeringProcessorItemEmeraldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 0), 200, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 38), ItemList.Circuit_Chip_NOR.get(3), GT_ItemList.EmeraldAdvancedCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 0), 280, 30720);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 38), ItemList.Circuit_Chip_SoC2.get(1), GT_ItemList.EmeraldAdvancedCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 0), 30, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2L, 38), ItemList.Circuit_Chip_SoC3.get(1), GT_ItemList.EngravedEnergyChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Neutronium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 0), 30, 500000);

        // --- Storage Cell Component - 1024K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 0), ItemList.Circuit_Chip_NAND.get(4), GT_ItemList.EngineeringProcessorItemEmeraldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 1), 220, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 0), ItemList.Circuit_Chip_NOR.get(3), GT_ItemList.EmeraldAdvancedCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 1), 300, 30720);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 3), GT_ModHandler.getModItem("extracells", "storage.component", 3L, 0), ItemList.Circuit_Chip_SoC2.get(1), GT_ItemList.EmeraldAdvancedCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 1), 30, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 1), GT_ModHandler.getModItem("extracells", "storage.component", 2L, 0), ItemList.Circuit_Chip_SoC3.get(1), GT_ItemList.EngravedEnergyChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Neutronium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 1), 30, 500000);

        // --- Storage Cell Component - 4096K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 1), ItemList.Circuit_Chip_NAND.get(4), GT_ItemList.EngineeringProcessorItemAdvEmeraldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 2), 240, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 1), ItemList.Circuit_Chip_NOR.get(3), GT_ItemList.EmeraldHighAdvancedCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 2), 320, 122880);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 3), GT_ModHandler.getModItem("extracells", "storage.component", 3L, 1), ItemList.Circuit_Chip_SoC3.get(1), GT_ItemList.EmeraldHighAdvancedCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Neutronium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 2), 30, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 1), GT_ModHandler.getModItem("extracells", "storage.component", 2L, 1), ItemList.Circuit_Chip_SoC4.get(1), GT_ItemList.EngravedQuantumChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Phoenixite.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 2), 30, 2000000);

        // --- Storage Cell Component - 16384K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 2), ItemList.Circuit_Chip_NAND.get(4), GT_ItemList.EngineeringProcessorItemAdvEmeraldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 3), 260, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 2), ItemList.Circuit_Chip_NOR.get(3), GT_ItemList.EmeraldHighAdvancedCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 3), 340, 122880);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 3), GT_ModHandler.getModItem("extracells", "storage.component", 3L, 2), ItemList.Circuit_Chip_SoC3.get(1), GT_ItemList.EmeraldHighAdvancedCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Neutronium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 3), 30, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 1), GT_ModHandler.getModItem("extracells", "storage.component", 2L, 2), ItemList.Circuit_Chip_SoC4.get(1), GT_ItemList.EngravedQuantumChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Phoenixite.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 3), 30, 2000000);


        // --- Fluid Storage Cell Component - 1K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_Ram.get(4), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lapis, 4), GT_ItemList.EngineeringProcessorFluidGoldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 4), 120, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_NAND.get(3), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lapis, 4), GT_ItemList.GoldFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 4), 160, 1920);

        // --- Fluid Storage Cell Component - 4K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 4), ItemList.Circuit_Chip_Ram.get(4), GT_ItemList.EngineeringProcessorFluidGoldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 5), 160, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 4), ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.GoldFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 5), 240, 1920);

        // --- Fluid Storage Cell Component - 16K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 5), ItemList.Circuit_Chip_Ram.get(4), GT_ItemList.EngineeringProcessorFluidDiamondCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 6), 180, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 5), ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.DiamondFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 6), 260, 7680);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_Ram.get(32), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lapis, 32), ItemList.Circuit_Chip_SoC.get(1), GT_ItemList.DiamondFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Europium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 6), 30, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_NAND.get(16), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lapis, 16), ItemList.Circuit_Chip_SoC2.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 6), 30, 122880);

        // --- Fluid Storage Cell Component - 64K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 6), ItemList.Circuit_Chip_Ram.get(4), GT_ItemList.EngineeringProcessorFluidDiamondCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 7), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 6), ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.DiamondFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 7), 280, 7680);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 3), GT_ModHandler.getModItem("extracells", "storage.component", 3L, 6), ItemList.Circuit_Chip_SoC.get(1), GT_ItemList.DiamondFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Europium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 7), 30, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1), GT_ModHandler.getModItem("extracells", "storage.component", 2L, 6), ItemList.Circuit_Chip_SoC2.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 7), 30, 122880);

        // --- Fluid Storage Cell Component - 256K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 7), ItemList.Circuit_Chip_NAND.get(4), GT_ItemList.EngineeringProcessorFluidEmeraldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 8), 200, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 7), ItemList.Circuit_Chip_NOR.get(3), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 8), 280, 30720);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 3), GT_ModHandler.getModItem("extracells", "storage.component", 3L, 7), ItemList.Circuit_Chip_SoC2.get(1), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 8), 30, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1), GT_ModHandler.getModItem("extracells", "storage.component", 2L, 7), ItemList.Circuit_Chip_SoC3.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Neutronium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 8), 30, 500000);

        // --- Fluid Storage Cell Component - 1024K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 8), ItemList.Circuit_Chip_NAND.get(4), GT_ItemList.EngineeringProcessorFluidEmeraldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 9), 220, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 8), ItemList.Circuit_Chip_NOR.get(3), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 9), 300, 30720);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 3), GT_ModHandler.getModItem("extracells", "storage.component", 3L, 8), ItemList.Circuit_Chip_SoC2.get(1), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 9), 30, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 1), GT_ModHandler.getModItem("extracells", "storage.component", 2L, 8), ItemList.Circuit_Chip_SoC3.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Neutronium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 9), 30, 500000);

        // --- Fluid Storage Cell Component - 4096K
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 9), ItemList.Circuit_Chip_NAND.get(4), GT_ItemList.EngineeringProcessorFluidEmeraldCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 10), 240, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 3), GT_ModHandler.getModItem("extracells", "storage.component", 4L, 9), ItemList.Circuit_Chip_NOR.get(3), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("extracells", "storage.component", 1L, 10), 320, 122880);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 3), GT_ModHandler.getModItem("extracells", "storage.component", 3L, 9), ItemList.Circuit_Chip_SoC3.get(1), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Neutronium.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 10), 30, 500000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 1), GT_ModHandler.getModItem("extracells", "storage.component", 2L, 9), ItemList.Circuit_Chip_SoC4.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Phoenixite.getMolten(36), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 10), 30, 2000000);


        // --- 2 Spatial Component
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Niobium, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 9), GT_ItemList.EngineeringProcessorSpatialPulsatingCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 32), 160, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Niobium, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 9), GT_ItemList.PulsatingSpatialCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 32), 200, 7680);

        // --- 16 Spatial Component
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 32), GT_ItemList.EngineeringProcessorSpatialPulsatingCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33), 160, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 32), GT_ItemList.PulsatingSpatialCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33), 200, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 32), ItemList.Circuit_Chip_SoC.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Europium.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33), 30, 30720);

        // --- 128 Spatial Component
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 33), GT_ItemList.EngineeringProcessorSpatialPulsatingCore.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34), 200, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 33), GT_ItemList.PulsatingSpatialCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34), 240, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 3), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 33), ItemList.Circuit_Chip_SoC.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L), GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34), 30, 122880);

        // --- Storage Cells
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemViewCell", 1L), 100, 120);

        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.1k", 1L), 100, 120);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.4k", 1L), 100, 256);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.16k", 1L), 100, 480);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.64k", 1L), 100, 1024);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L), GT_ModHandler.getModItem("extracells", "storage.component", 1L), GT_ModHandler.getModItem("extracells", "storage.physical", 1L), 100, 7680);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 1), GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 1), 100, 16384);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 2), GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 2), 100, 30720);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 3), GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 3), 100, 65536);

        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 32), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.2Cubed", 1L), 200, 4096);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.16Cubed", 1L), 300, 7680);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.128Cubed", 1L), 400, 16384);

        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 4), GT_ModHandler.getModItem("extracells", "storage.fluid", 1L), 100, 120);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 5), GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 1), 100, 256);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 6), GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 2), 100, 480);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 7), GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 3), 100, 1024);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 8), GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 4), 100, 7680);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 9), GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 5), 100, 16384);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), GT_ModHandler.getModItem("extracells", "storage.component", 1L, 10), GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 6), 100, 30720);

        // --- CoCraftingUnit
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2), Materials.SolderingAlloy.getMolten(2304), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 1), 800, 1024);

        // --- Crafting Monitor
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 400), Materials.SolderingAlloy.getMolten(576), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingMonitor", 1L, 0), 200, 480);

        // --- CraftingStorages
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35), Materials.SolderingAlloy.getMolten(144), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 0), 1200, 64);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36), Materials.SolderingAlloy.getMolten(576), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 1), 1200, 256);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), Materials.SolderingAlloy.getMolten(2304), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 2), 1200, 1024);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), Materials.SolderingAlloy.getMolten(9216), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 3), 1200, 4096);

        // --- Annihilation Core
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.VanadiumSteel, 2), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 44), 200, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NetherQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Iridium, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_Utility.getIntegratedCircuit(2)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2L, 44), 100, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.NetherQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Duranium, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 44), 50, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.NetherQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_Utility.getIntegratedCircuit(4)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16L, 44), 20, 500000);

        // --- Formation Core
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.VanadiumSteel, 2), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 43), 200, 7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.CertusQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Iridium, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_Utility.getIntegratedCircuit(2)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2L, 43), 100, 30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.CertusQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Duranium, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 43), 50, 122880);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.CertusQuartz, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22), GT_Utility.getIntegratedCircuit(4)}, null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16L, 43), 20, 500000);


        /* ==== END AE/EC2 ==== */
        /** ==== START CHESTUP ==== */
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 8), GT_ModHandler.getModItem("minecraft", "chest", 1L), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 0), 80, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 0), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 1), 90, 16);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), 100, 64);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSLA, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 3), 110, 256);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 3), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 4), 120, 1024);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 4), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 5), 130, 4096);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 5), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 6), 140, 16384);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 6), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 7), 150, 65536);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 7), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 8), 160, 262144);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 8), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 8), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 9), 170, 1048576);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "WOODWRIRONUpgrade", 1L, 0), 80, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "WRIRONSTEELUpgrade", 1L, 0), 90, 16);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "STEELALUMINIUMUpgrade", 1L, 0), 100, 64);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSLA, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "ALUMINIUMHSLAUpgrade", 1L, 0), 110, 256);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSLA, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "HSLATITANIUMUpgrade", 1L, 0), 120, 1024);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "TITANIUMWOLFRAMUpgrade", 1L, 0), 130, 4096);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "WOLFRAMCHROMEUpgrade", 1L, 0), 140, 16384);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "CHROMEIRIDIUMUpgrade", 1L, 0), 150, 65536);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "IRIDIUMOSMIUMUpgrade", 1L, 0), 160, 262144);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 8), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 1), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("chestup", "OSMIUMNEUTRONIUMUpgrade", 1L, 0), 170, 1048576);

        /* ==== END CHESTUP ==== */
        /** ==== START SFM ==== */

        /* ==== END SFM ==== */
        /** ==== START LOGISTIC PIPES ==== */
        // --- Unrouted Transport Pipe
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Birmabright, 6L), null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicTransport", 8L, 0), 100, 30);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Birmabright, 6L), null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicTransport", 8L, 0), 100, 30);
        // --- Basic Logistics Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicTransport", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Aluminium, 1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicLogistics", 1L, 0), 100, 120);
        // --- Logistics Chassis Mk1
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicLogistics", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L), GT_Utility.getIntegratedCircuit(1)}, Materials.Redstone.getMolten(288), GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0), 100, 120);
        // --- Logistics Chassis Mk2
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L), GT_Utility.getIntegratedCircuit(1)}, Materials.RedAlloy.getMolten(288), GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk2", 1L, 0), 120, 120);
        // --- Logistics Chassis Mk3
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk2", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L), GT_Utility.getIntegratedCircuit(1)}, Materials.Electrum.getMolten(288), GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk3", 1L, 0), 140, 120);
        // --- Logistics Chassis Mk4
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk3", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L), GT_Utility.getIntegratedCircuit(1)}, Materials.Electrum.getMolten(576), GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk4", 1L, 0), 160, 120);
        // --- Logistics Chassis Mk5
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk4", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L), GT_Utility.getIntegratedCircuit(1)}, Materials.EnergeticAlloy.getMolten(576), GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk5", 1L, 0), 180, 120);
        // --- Crafting Logistics Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0), ItemList.Cover_Crafting.get(1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsCraftingLogistics", 1L, 0), 100, 120);
        // --- Crafting Logistics Pipe Mk2
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk2", 1L, 0), ItemList.Cover_Crafting.get(1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsCraftingLogisticsMk2", 1L, 0), 120, 120);
        // --- Crafting Logistics Pipe Mk3
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk3", 1L, 0), ItemList.Cover_Crafting.get(1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsCraftingLogisticsMk3", 1L, 0), 140, 120);
        // --- Provider Logistics Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0), ItemList.Conveyor_Module_LV.get(1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsProviderLogistics", 1L, 0), 100, 120);
        // --- Provider Logistics Pipe Mk2
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk2", 1L, 0), ItemList.Conveyor_Module_MV.get(1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsProviderLogisticsMk2", 1L, 0), 120, 120);
        // --- Supplier Logistics Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0), ItemList.Conveyor_Module_MV.get(1), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 2L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSupplierLogistics", 1L, 0), 100, 120);
        // --- Request Logistics Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0), ItemList.Robot_Arm_LV.get(1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsRequestLogistics", 1L, 0), 100, 120);
        // --- Request Logistics Pipe Mk2
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk2", 1L, 0), ItemList.Robot_Arm_MV.get(1), ItemList.Cover_Screen.get(1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsRequestLogisticsMk2", 1L, 0), 100, 120);
        // --- Logistics Inventory System Connector
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0), ItemList.Electric_Motor_LV.get(1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Steel, 2L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsInvSysConnector", 1L, 0), 100, 120);
        // --- Logistics Firewall Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0), ItemList.Electric_Motor_MV.get(1), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 2L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsFirewall", 1L, 0), 120, 120);
        // --- Logistics System Entrance Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk2", 1L, 0), ItemList.Electric_Piston_MV.get(1), GT_Utility.getIntegratedCircuit(1)}, Materials.Electrum.getMolten(288), GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSystemEntranceLogistics", 1L, 0), 120, 120);
        // --- Logistics System Destination Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk2", 1L, 0), ItemList.Electric_Piston_MV.get(1), GT_Utility.getIntegratedCircuit(1)}, Materials.RedAlloy.getMolten(288), GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSystemDestinationLogistics", 1L, 0), 120, 120);
        // --- Satellite Logistics Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsCraftingLogistics", 1L, 0), ItemList.Electric_Piston_MV.get(1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSatelliteLogistics", 1L, 0), 100, 120);
        // --- Remote Ordered Logistics Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSystemEntranceLogistics", 1L, 0), ItemList.Conveyor_Module_MV.get(1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsRemoteOrdererLogistics", 1L, 0), 120, 120);
        // --- Blank Module
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Paper, 1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 2L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 0), 80, 30);
        // --- Advanced Extractor Module
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 3), GT_ItemList.RedstoneGoldChipset.get(1), GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 7), 140, 120);
        // --- Advanced Extractor Module MK2
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 103), GT_ItemList.RedstoneGoldChipset.get(1), GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 107), 160, 120);
        // --- Advanced Extractor Module MK3
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 203), GT_ItemList.RedstoneDiamondChipset.get(1), GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, 207), 180, 256);
        // --- IC2 LV Power Supplier Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 30), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Tin, 2L), GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 33), 100, 120);
        // --- IC2 MV Power Supplier Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 30), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Copper, 2L), GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 34), 120, 120);
        // --- IC2 HV Power Supplier Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 30), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 2L), GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 35), 140, 120);
        // --- IC2 EV Power Supplier Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 30), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Aluminium, 2L), GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 36), 160, 120);

        /* ==== END LOGISTIC PIPES ==== */
        /** ==== START ENDER IO ==== */
        // --- Item Conduit
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Conveyor_Module_LV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Electrum, 4L), GT_ModHandler.getModItem("EnderIO", "itemMaterial", 4L, 1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("EnderIO", "itemItemConduit", 4L, 0), 120, 16);
        // --- Fluid Conduit
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Pump_LV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 4L), GT_ModHandler.getModItem("EnderIO", "itemMaterial", 4L, 1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("EnderIO", "itemLiquidConduit", 4L, 0), 120, 16);
        // --- Big Item Filter
        GT_Values.RA.addAssemblerRecipe(ItemList.Conveyor_Module_HV.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DarkSteel, 1L), GT_ModHandler.getModItem("EnderIO", "itemBigFilterUpgrade", 1L), 200, 256);
        // --- Big Advanced Item Filter
        GT_Values.RA.addAssemblerRecipe(ItemList.Conveyor_Module_EV.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSLA, 1L), GT_ModHandler.getModItem("EnderIO", "itemBigFilterUpgrade", 1L, 1), 300, 480);
        // --- Chargeable Item Filter
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("EnderIO", "itemConduitProbe", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.ElectricalSteel, 1L), GT_ModHandler.getModItem("EnderIO", "itemPowerItemFilter", 1L), 200, 64);
        // --- Network Conduit (OC)
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("OpenComputers", "cable", 1L), GT_ModHandler.getModItem("EnderIO", "itemMaterial", 4L, 1), GT_ModHandler.getModItem("EnderIO", "itemOCConduit", 1L, 0), 120, 16);

        /* ==== END ENDER IO ==== */
        /** ==== START MALISIS DOORS ==== */
        // --- Iron Trap Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "trapdoor", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), 100, 16);
        // --- Sliding Trap Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L), GT_ModHandler.getModItem("malisisdoors", "sliding_trapdoor", 1L), 150, 16);
        // --- Wooden Vanishing Frame
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 1L), Materials.Redstone.getMolten(288), GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L), 300, 30);
        // --- Iron Vanishing Frame
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 4L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 1L), Materials.Redstone.getMolten(288), GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 1), 400, 30);
        // --- Gold Vanishing Frame
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 1L), Materials.Redstone.getMolten(288), GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 2), 500, 30);
        // --- Diamond Vanishing Frame
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Diamond, 4L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 1L), Materials.Redstone.getMolten(288), GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 3), 600, 30);
        // --- Rusty Hatch
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), GT_ModHandler.getModItem("malisisdoors", "item.rustyHandle", 1L), GT_ModHandler.getModItem("malisisdoors", "rustyHatch", 1L), 200, 16);
        // --- Garage Door
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 1L), GT_ModHandler.getModItem("malisisdoors", "garage_door", 3L), 150, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 1L), GT_ModHandler.getModItem("malisisdoors", "garage_door", 4L), 160, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 3L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Aluminium, 2L), GT_ModHandler.getModItem("malisisdoors", "garage_door", 6L), 170, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 3L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StainlessSteel, 2L), GT_ModHandler.getModItem("malisisdoors", "garage_door", 8L), 180, 16);
        // --- Rusty Ladder
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 3L), GT_Utility.getIntegratedCircuit(5), GT_ModHandler.getModItem("malisisdoors", "rustyLadder", 1L), 60, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 3L), GT_Utility.getIntegratedCircuit(5), GT_ModHandler.getModItem("malisisdoors", "rustyLadder", 2L), 70, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 3L), GT_Utility.getIntegratedCircuit(5), GT_ModHandler.getModItem("malisisdoors", "rustyLadder", 4L), 80, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 3L), GT_Utility.getIntegratedCircuit(5), GT_ModHandler.getModItem("malisisdoors", "rustyLadder", 8L), 90, 16);
        // --- Carriage Door
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.NetherQuartz, 4L), GT_ModHandler.getModItem("minecraft", "door", 1L), GT_ModHandler.getModItem("malisisdoors", "carriage_door", 1L), 200, 30);
        // --- Acacia Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 4), GT_Utility.getIntegratedCircuit(6), Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 4), GT_Utility.getIntegratedCircuit(6), Materials.Copper.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L), 400, 4);
        // --- Birch Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 2), GT_Utility.getIntegratedCircuit(6), Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 2), GT_Utility.getIntegratedCircuit(6), Materials.Copper.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L), 400, 4);
        // --- Dark Oak Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 5), GT_Utility.getIntegratedCircuit(6), Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 5), GT_Utility.getIntegratedCircuit(6), Materials.Copper.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L), 400, 4);
        // --- Jungle Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 3), GT_Utility.getIntegratedCircuit(6), Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 3), GT_Utility.getIntegratedCircuit(6), Materials.Copper.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L), 400, 4);
        // --- Spruce Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 1), GT_Utility.getIntegratedCircuit(6), Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L, 1), GT_Utility.getIntegratedCircuit(6), Materials.Copper.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L), 400, 4);
        // --- Wooden Glass Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "wooden_door", 1L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "wooden_door", 1L), GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L), GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L), GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L), GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L), GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L), GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L), 400, 4);
        // --- Iron Glass Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "iron_door", 1L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.iron_sliding_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "iron_door", 1L), GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L), GT_ModHandler.getModItem("malisisdoors", "item.iron_sliding_door", 1L), 400, 4);
        // --- Jail Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "iron_door", 1L), CoreItems2.getRecipe(51, 2), GT_ModHandler.getModItem("malisisdoors", "item.jail_door", 1L), 400, 4);
        // --- Laboratory Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "wooden_door", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L), GT_ModHandler.getModItem("malisisdoors", "item.laboratory_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L), GT_ModHandler.getModItem("malisisdoors", "item.laboratory_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L), GT_ModHandler.getModItem("malisisdoors", "item.laboratory_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L), GT_ModHandler.getModItem("malisisdoors", "item.laboratory_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L), GT_ModHandler.getModItem("malisisdoors", "item.laboratory_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L), GT_ModHandler.getModItem("malisisdoors", "item.laboratory_door", 1L), 400, 4);
        // --- Factory Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "iron_door", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2L), GT_ModHandler.getModItem("malisisdoors", "item.factory_door", 1L), 400, 4);
        // --- Shoji Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "paper", 2L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_ModHandler.getModItem("malisisdoors", "item.shoji_door", 1L), 400, 4);
        // --- Purple Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 10), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_purple", 1L), 400, 4);
        // --- Yellow Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 4), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_yellow", 1L), 400, 4);
        // --- Magenta Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 2), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_magenta", 1L), 400, 4);
        // --- Pink Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 6), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_pink", 1L), 400, 4);
        // --- White Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_white", 1L), 400, 4);
        // --- Blue Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 11), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_blue", 1L), 400, 4);
        // --- Cyan Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 9), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_cyan", 1L), 400, 4);
        // --- Red Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 14), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_red", 1L), 400, 4);
        // --- Gray Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 7), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_gray", 1L), 400, 4);
        // --- Brown Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 12), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_brown", 1L), 400, 4);
        // --- Lime Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 5), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_lime", 1L), 400, 4);
        // --- Orange Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 1), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_orange", 1L), 400, 4);
        // --- Light Gray Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 8), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_silver", 1L), 400, 4);
        // --- Green Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 13), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_green", 1L), 400, 4);
        // --- Light Blue Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 3), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_light_blue", 1L), 400, 4);
        // --- Black Curtain
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "carpet", 3L, 15), GT_ModHandler.getModItem("minecraft", "string", 1L), GT_ModHandler.getModItem("malisisdoors", "item.curtain_black", 1L), 400, 4);
        // --- Saloon Door
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plank, Materials.Wood, 3L), GT_OreDictUnificator.get(OrePrefixes.springSmall, Materials.Iron, 1L), GT_ModHandler.getModItem("malisisdoors", "item.saloon", 1L), 400, 4);
        // --- Fence Gate Acacia
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(2)}, null, GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 1L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 2L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 4L), 300, 8);
        // --- Fence Gate Birch
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 2), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(2)}, null, GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 1L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 2), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 2L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 2), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 4L), 300, 8);
        // --- Fence Gate Dark Oak
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 5), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(2)}, null, GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 1L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 5), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 2L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 5), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 4L), 300, 8);
        // --- Fence Gate Jungle
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(2)}, null, GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 1L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 2L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 4L), 300, 8);
        // --- Fence Gate Spruce
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(2)}, null, GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 1L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 2L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 4L), 300, 8);
        // --- Trapped Door Arcania
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 2L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(2)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 4L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 4), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 6L), 600, 4);
        // --- Trapped Door Birch
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 2), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 2L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 2), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(2)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 4L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 2), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 6L), 600, 4);
        // --- Trapped Door Dark Oak
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 5), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 2L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 5), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(2)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 4L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 5), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 6L), 600, 4);
        // --- Trapped Door Jungle
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 2L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(2)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 4L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 3), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 6L), 600, 4);
        // --- Trapped Door Spruce
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 2L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(2)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 4L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, 1), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 6L), 600, 4);

        /* ==== END MALISIS DOORS ==== */
        /** ==== START VANILLA ==== */
        // --- Oak Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L), GT_Utility.getIntegratedCircuit(6), Materials.Iron.getMolten(16), GT_ModHandler.getModItem("minecraft", "wooden_door", 1L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "planks", 6L), GT_Utility.getIntegratedCircuit(6), Materials.Copper.getMolten(16), GT_ModHandler.getModItem("minecraft", "wooden_door", 1L), 400, 4);
        // --- Iron Door
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 6L), GT_Utility.getIntegratedCircuit(6), Materials.Steel.getMolten(16), GT_ModHandler.getModItem("minecraft", "iron_door", 1L), 400, 8);
        // --- Traped Door Oak
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("minecraft", "trapdoor", 2L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(2)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("minecraft", "trapdoor", 4L), 600, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("minecraft", "trapdoor", 6L), 600, 4);
        // --- Fence Gate Oak
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(2)}, null, GT_ModHandler.getModItem("minecraft", "fence_gate", 1L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("minecraft", "fence_gate", 2L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("minecraft", "planks", 3L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(3)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("minecraft", "fence_gate", 4L), 300, 8);
        // --- Wooden Pressure Plate
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 2L), GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Iron, 1L), GT_ModHandler.getModItem("minecraft", "wooden_pressure_plate", 2L), 100, 8);
        // --- Pressure Plate
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "stone_slab", 2L), GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Iron, 1L), GT_ModHandler.getModItem("minecraft", "stone_pressure_plate", 2L), 100, 8);
        // --- Ladder
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 3L), GT_Utility.getIntegratedCircuit(5)}, null, GT_ModHandler.getModItem("minecraft", "ladder", 2L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 3L), GT_Utility.getIntegratedCircuit(6)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("minecraft", "ladder", 4L), 400, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 3L), GT_Utility.getIntegratedCircuit(7)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("minecraft", "ladder", 6L), 400, 4);
        // --- Fence
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plank, Materials.Wood, 2L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(10)}, null, GT_ModHandler.getModItem("minecraft", "fence", 1L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plank, Materials.Wood, 2L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(11)}, Materials.Iron.getMolten(16), GT_ModHandler.getModItem("minecraft", "fence", 2L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plank, Materials.Wood, 2L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), GT_Utility.getIntegratedCircuit(11)}, Materials.Steel.getMolten(16), GT_ModHandler.getModItem("minecraft", "fence", 4L), 300, 8);

        /* ==== END VANILLA ==== */
        /** ==== START CARPENTERS BLOCKS ==== */
        // --- Carpenter's Block
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 2L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Wood, 2L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 4L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 3L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Iron, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 8L, 0), 100, 30);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 3L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 12L, 0), 100, 48);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 3L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Aluminium, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 16L, 0), 100, 64);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 3L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 20L, 0), 100, 64);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 3L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Titanium, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 24L, 0), 100, 64);
        // --- Carpenter's Barrier
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("minecraft", "fence", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBarrier", 2L, 0), 200, 16);
        // --- Carpenter's Collapsible Block
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), ItemList.Plank_Oak.get(1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), ItemList.Plank_Spruce.get(1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), ItemList.Plank_Birch.get(1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), ItemList.Plank_Jungle.get(1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), ItemList.Plank_Acacia.get(1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), ItemList.Plank_DarkOak.get(1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1L, 0), 100, 16);
        // --- Carpenter's Flower Pot
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("minecraft", "flower_pot", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersFlowerPot", 1L, 0), 100, 16);
        // --- Carpenter's Garage Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Iron, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGarageDoor", 2L, 0), 150, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGarageDoor", 4L, 0), 300, 16);
        // --- Carpenter's Gate
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("minecraft", "fence_gate", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1L, 0), 100, 16);
        // --- Carpenter's Hatch
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("minecraft", "trapdoor", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1L, 0), 100, 16);
        // --- Carpenter's Ladder
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("minecraft", "ladder", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersLadder", 1L, 0), 100, 16);
        // --- Carpenter's Lever
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("minecraft", "lever", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersLever", 1L, 0), 50, 16);
        // --- Carpenter's Safe
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 3L, 0), GT_ModHandler.getModItem("IC2", "blockPersonal", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersSafe", 1L, 0), 300, 30);
        // --- Carpenter's Torch
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("minecraft", "torch", 10L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersTorch", 10L, 0), 100, 16);
        // --- Carpenter's Chisel
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 2), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersChisel", 1L, 0), 200, 30);
        // --- Carpenter's Hammer
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.toolHeadHammer, Materials.Iron, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersHammer", 1L, 0), 200, 30);
        // --- Carpenter's Tile
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersPressurePlate", 1L, 0), GT_ModHandler.getModItem("minecraft", "clay_ball", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersTile", 2L, 0), 50, 16);
        // --- Carpenter's Door
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("minecraft", "wooden_door", 1L, 0), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), 150, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), 150, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), 150, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), 150, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), 150, 16);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1L, 0), 150, 16);
        // --- Carpenter's Pressure Plate
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 2L, 0), GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Iron, 1L), null, GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersPressurePlate", 2L, 0), 100, 8);

        /* ==== END CARPENTERS BLOCKS ==== */
        /** ==== START EXTRA UTILITIES ==== */
        // --- Quarry Upgrade
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Casing_Gearbox_TungstenSteel2.get(1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Talonite, 4L), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Stellite, 4L), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Talonite, 8L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Stellite, 32L)}, null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 0), 600, 1920);
        // --- Hole Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Titanium, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 1), 800, 7680);
        // --- Silk Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Enderium, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 2), 800, 30720);
        // --- Fortune 1 Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Iridium, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 3), 800, 7680);
        // --- Fortune 2 Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 3), GT_OreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Trinium, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 4), 1000, 30720);
        // --- Fortune 3 Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 4), GT_OreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Tritanium, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 5), 1200, 122880);
        // --- Speed 1 Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.HSSG, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 6), 800, 7680);
        // --- Speed 2 Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 6), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.HSSE, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 7), 1000, 30720);
        // --- Speed 3 Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 7), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.HSSS, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 8), 1200, 122880);
        // --- Pump Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 0), ItemList.Electric_Pump_IV.get(1L), null, GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 1L, 9), 1000, 30720);
        // --- Thermionic Pump
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Pump_IV.get(1), GT_ModHandler.getModItem("ExtraUtilities", "enderQuarryUpgrade", 4L, 0), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Talonite, 4L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4L), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.GumMetal, 2L), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Inconel690, 8L), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.BT6, 32L)}, null, GT_ModHandler.getModItem("ExtraUtilities", "enderThermicPump", 1L, 0), 1000, 7680);
        // --- Slightly Larger Chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "chest", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 1L, 0), 100, 30);
        // --- Trash Can
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.round, Materials.Rubber, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 10L), null, GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L, 0), 400, 30);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.round, Materials.StyreneButadieneRubber, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 10L), null, GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L, 0), 400, 30);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.round, Materials.Silicone, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 10L), null, GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L, 0), 400, 30);
        // --- Block Update Detector
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("gregtech", "gt.metaitem.01", 1L, 32731), GT_ModHandler.getModItem("minecraft", "sticky_piston", 1L, 0), null, GT_ModHandler.getModItem("ExtraUtilities", "budoff", 1L, 0), 200, 30);
        // --- Block Update Detector (Advanced)
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "budoff", 1L, 0), GT_ModHandler.getModItem("minecraft", "redstone_block", 4L, 0), null, GT_ModHandler.getModItem("ExtraUtilities", "budoff", 1L, 3), 200, 120);
        // --- Etched Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 0), GT_Utility.getIntegratedCircuit(1), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 1), 100, 8);
        // --- Glass Bricks
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 0), GT_Utility.getIntegratedCircuit(4), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 2), 100, 8);
        // --- Swirling Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 0), GT_Utility.getIntegratedCircuit(5), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 6), 100, 8);
        // --- Squared Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 0), GT_Utility.getIntegratedCircuit(2), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 9), 100, 8);
        // --- Dark Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 10), 100, 8);
        // --- Reinforced Dark Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 10), GT_ModHandler.getModItem("gregtech", "gt.metaitem.01", 4L, 2804), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1L, 11), 400, 30);
        // --- Sandy Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "sand", 1L, 0), GT_ModHandler.getModItem("minecraft", "glass", 1L, 0), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 2L, 9), 40, 4);
        // --- Edged Stone Bricks
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "stone", 4L, 0), GT_ModHandler.getModItem("minecraft", "stonebrick", 5L, 0), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 9L, 0), 180, 4);
        // --- Border Stone
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1L, 0), GT_Utility.getIntegratedCircuit(4), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1L, 4), 20, 4);
        // --- Border Stone (Alternate)
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1L, 4), GT_Utility.getIntegratedCircuit(4), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1L, 7), 20, 4);
        // --- Gravel Bricks
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "gravel", 1L, 0), GT_Utility.getIntegratedCircuit(2), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1L, 6), 20, 4);
        // --- Frosted Stone
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "stone", 1L, 0), GT_ModHandler.getModItem("minecraft", "ice", 4L, 0), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 4L, 3), 80, 4);
        // --- Gravel Road
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 5L, 6), GT_ModHandler.getModItem("minecraft", "stone_slab", 5L, 5), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 5L, 10), 100, 4);
        // --- Ender-Sand Alloy
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "sandstone", 1L, 0), GT_ModHandler.getModItem("minecraft", "end_stone", 1L, 1), null, GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 2L, 13), 40, 4);
        // --- Ineffable Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 0), GT_Utility.getIntegratedCircuit(2), null, GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 1), 100, 8);
        // --- Ethereal Glass (Inverted)
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 0), GT_Utility.getIntegratedCircuit(1), null, GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 3), 100, 8);
        // --- Ineffable Glass (Inverted)
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 1), GT_Utility.getIntegratedCircuit(1), null, GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 4), 100, 8);
        // --- Dark Ethereal Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 2L), null, GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 2), 200, 16);
        // --- Dark Ethereal Glass (Inverted)
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 2), GT_Utility.getIntegratedCircuit(1), null, GT_ModHandler.getModItem("ExtraUtilities", "etherealglass", 1L, 5), 100, 8);

        /* ==== END EXTRA UTILITIES ==== */
        /** ==== START GREG'S SG CRAFT ==== */
        // --- Stargate Controller
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 24L), ItemList.Sensor_UV.get(4L), GT_ModHandler.getModItem("EnderIO", "blockDarkSteelPressurePlate", 12L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Piko, 1), GT_ModHandler.getModItem("SGCraft", "sgControllerCrystal", 1L, 0)}, null, GT_ModHandler.getModItem("SGCraft", "stargateController", 1L, 0), 1000, 500000);
        // --- Stargate Shevron Upgrade
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(147, 8), CoreItems2.getRecipe(149, 3), ItemList.Electric_Piston_UV.get(6L), ItemList.Field_Generator_UV.get(4L), ItemList.Sensor_UV.get(1L)}, null, GT_ModHandler.getModItem("SGCraft", "sgChevronUpgrade", 1L, 0), 1000, 500000);
        // --- Stargate Iris Upgrade
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("SGCraft", "sgIrisBlade", 20L, 0), ItemList.Casing_Coil_Superconductor.get(4L), GT_ModHandler.getModItem("SGCraft", "sgIrisUpgrade", 1L, 0), 1000, 500000);
        // --- Stargate Iris Blade
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.block, Materials.Titanium, 32L), ItemList.Electric_Piston_UV.get(1L), ItemList.Casing_Coil_Superconductor.get(1L)}, null, GT_ModHandler.getModItem("SGCraft", "sgIrisBlade", 1L, 0), 1000, 500000);
        // --- Ridiculously Large Capacitor
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(148, 12), ItemList.Field_Generator_UV.get(5L), ItemList.ZPM2.get(1L)}, null, GT_ModHandler.getModItem("SGCraft", "ic2Capacitor", 1L, 0), 1000, 500000);

        /* ==== END GREG'S SG CRAFT ==== */
        /** ==== START WIRELESS TERMINAL ==== */
        // --- Magnet
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.block, Materials.NeodymiumMagnetic, 2L), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 28), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HastelloyN, 8), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Inconel792, 24)}, null, GT_ModHandler.getModItem("ae2wct", "magnetCard", 1L, 0), 1000, 1920);
        // --- Booster
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 64L, 42), ItemList.Field_Generator_UV.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CinobiteA243, 16), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Quantium, 24), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Osmium, 64)}, null, GT_ModHandler.getModItem("ae2wct", "infinityBoosterCard", 1L, 0), 4000, 122880);

        /* ==== END WIRELESS TERMINAL ==== */
        /** ==== START WR-CBE ==== */
        // --- Wireless Transceiver
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 1L), GT_ModHandler.getModItem("WR-CBE|Core", "retherPearl", 1L, 0), GT_ModHandler.getModItem("WR-CBE|Core", "wirelessTransceiver", 1L, 0), 600, 120);
        // --- Blaze Transceiver
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 1L), GT_ModHandler.getModItem("WR-CBE|Core", "retherPearl", 1L, 0), GT_ModHandler.getModItem("WR-CBE|Core", "blazeTransceiver", 1L, 0), 600, 120);
        // --- Receiver Dish
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("WR-CBE|Core", "stoneBowl", 1L, 0), GT_ModHandler.getModItem("WR-CBE|Core", "wirelessTransceiver", 1L, 0), GT_ModHandler.getModItem("WR-CBE|Core", "recieverDish", 1L, 0), 600, 256);
        // --- Blaze Receiver Dish
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("WR-CBE|Core", "stoneBowl", 1L, 0), GT_ModHandler.getModItem("WR-CBE|Core", "blazeTransceiver", 1L, 0), GT_ModHandler.getModItem("WR-CBE|Core", "blazeRecieverDish", 1L, 0), 600, 256);
        // --- Triangulatior
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "compass", 1L, 0), GT_ModHandler.getModItem("WR-CBE|Core", "wirelessTransceiver", 1L, 0), Materials.Redstone.getMolten(144), GT_ModHandler.getModItem("WR-CBE|Addons", "triangulator", 1L, 0), 600, 120);
        // --- Wireless Map
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("WR-CBE|Addons", "triangulator", 1L, 0), GT_ModHandler.getModItem("minecraft", "paper", 8L, 0), Materials.Redstone.getMolten(144), GT_ModHandler.getModItem("WR-CBE|Addons", "map", 1L, 0), 300, 64);
        // --- Tracker
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 3L), GT_ModHandler.getModItem("WR-CBE|Core", "wirelessTransceiver", 1L, 0), Materials.Redstone.getMolten(144), GT_ModHandler.getModItem("WR-CBE|Addons", "tracker", 1L, 0), 600, 120);
        // --- REP
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("WR-CBE|Core", "blazeTransceiver", 2L, 0), GT_ModHandler.getModItem("WR-CBE|Core", "retherPearl", 2L, 0), Materials.Obsidian.getMolten(1296), GT_ModHandler.getModItem("WR-CBE|Addons", "rep", 1L, 0), 600, 120);

        /* ==== END WR-CBE ==== */
        /** ==== START ZTONES ==== */
        // --- Ztones Tile
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "stone_slab", 8L, 0), GT_ModHandler.getModItem("minecraft", "stone", 1L, 0), GT_ModHandler.getModItem("Ztones", "stoneTile", 8L, 0), 160, 4);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "stone_slab", 8L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Iron, 1L), GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), 140, 6);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "stone_slab", 8L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L), GT_ModHandler.getModItem("Ztones", "stoneTile", 32L, 0), 120, 8);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "stone_slab", 8L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 1L), GT_ModHandler.getModItem("Ztones", "stoneTile", 64L, 0), 100, 12);
        // --- Garden Soil
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "sand", 4L, 0), GT_ModHandler.getModItem("minecraft", "dirt", 4L, 0), Materials.SeedOil.getFluid(5), GT_ModHandler.getModItem("Ztones", "cleanDirt", 8L, 0), 160, 4);
        // --- Booster
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "stone_pressure_plate", 1L, 0), GT_Utility.getIntegratedCircuit(1), Materials.Blaze.getMolten(8), GT_ModHandler.getModItem("Ztones", "booster", 1L, 0), 100, 30);
        // --- Aurora Block
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "glass", 4L, 0), GT_ModHandler.getModItem("minecraft", "dye", 1L, 32767), GT_ModHandler.getModItem("Ztones", "auroraBlock", 8L, 0), 160, 4);
        // --- Korp
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Obsidian, 1L), GT_ModHandler.getModItem("Ztones", "tile.korpBlock", 16L, 0), 100, 8);
        // --- Zech
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 4), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zechBlock", 16L, 0), 100, 8);
        // --- Tank
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Iron, 1L), GT_ModHandler.getModItem("Ztones", "tile.tankBlock", 16L, 0), 100, 8);
        // --- Sync
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L), GT_ModHandler.getModItem("Ztones", "tile.syncBlock", 16L, 0), 100, 8);
        // --- Zkul
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 10), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zkulBlock", 16L, 0), 100, 8);
        // --- Mint
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Rubber, 1L), GT_ModHandler.getModItem("Ztones", "tile.mintBlock", 16L, 0), 100, 8);
        // --- Lair
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_ModHandler.getModItem("minecraft", "netherrack", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.lairBlock", 16L, 0), 100, 8);
        // --- Sols
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Brass, 1L), GT_ModHandler.getModItem("Ztones", "tile.solsBlock", 16L, 0), 100, 8);
        // --- Reds
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), GT_ModHandler.getModItem("Ztones", "tile.redsBlock", 16L, 0), 100, 8);
        // --- Ztyl
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L), GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 0), 100, 8);
        // --- Iszm
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 8), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.iszmBlock", 16L, 0), 100, 8);
        // --- Roen
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_ModHandler.getModItem("minecraft", "sandstone", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.roenBlock", 16L, 0), 100, 8);
        // --- Vect
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Carbon, 1L), GT_ModHandler.getModItem("Ztones", "tile.vectBlock", 16L, 0), 100, 8);
        // --- Lave
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_ModHandler.getModItem("minecraft", "ice", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.laveBlock", 16L, 0), 100, 8);
        // --- Zest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 11), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zestBlock", 16L, 0), 100, 8);
        // --- Zyth
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 15), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zythBlock", 16L, 0), 100, 8);
        // --- Bitt
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_ModHandler.getModItem("minecraft", "wool", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.bittBlock", 16L, 0), 100, 8);
        // --- Tinted Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "glass", 16L, 0), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.glaxx", 16L, 0), 100, 8);
        // --- Agon
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.agonBlock", 16L, 0), 100, 8);
        // --- Zone
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 3), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zoneBlock", 16L, 0), 100, 8);
        // --- Zoea
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 14), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zoeaBlock", 16L, 0), 100, 8);
        // --- Jelt
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Gold, 1L), GT_ModHandler.getModItem("Ztones", "tile.jeltBlock", 16L, 0), 100, 8);
        // --- Zeta
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 13), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zetaBlock", 16L, 0), 100, 8);
        // --- Reed
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1L), GT_ModHandler.getModItem("Ztones", "tile.reedBlock", 16L, 0), 100, 8);
        // --- Cray
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "hardened_clay", 16L, 0), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.crayBlock", 16L, 0), 100, 8);
        // --- Fort
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 9), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.fortBlock", 16L, 0), 100, 8);
        // --- Vena
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Cobalt, 1L), GT_ModHandler.getModItem("Ztones", "tile.venaBlock", 16L, 0), 100, 8);
        // --- Kryp
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_ModHandler.getModItem("chisel", "concrete", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.krypBlock", 16L, 0), 100, 8);
        // --- Zion
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 7), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zionBlock", 16L, 0), 100, 8);
        // --- Zome
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 6), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zomeBlock", 16L, 0), 100, 8);
        // --- Zane
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 12), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zaneBlock", 16L, 0), 100, 8);
        // --- Azur
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "stoneTile", 16L, 0), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Lapis, 1L), GT_ModHandler.getModItem("Ztones", "tile.azurBlock", 16L, 0), 100, 8);
        // --- Zorg
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 16L, 5), GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), GT_ModHandler.getModItem("Ztones", "tile.zorgBlock", 16L, 0), 100, 8);

        /* ==== END ZTONES ==== */
        /** ==== START PRACTICAL LOGISTICS ==== */
        // --- Data Emitter
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("PracticalLogistics", "InfoReader", 1L, 0), ItemList.Emitter_LV.get(1), GT_ModHandler.getModItem("PracticalLogistics", "DataEmitter", 1L, 0), 200, 8);
        // --- Data Receiver
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("PracticalLogistics", "InfoReader", 1L, 0), ItemList.Sensor_LV.get(1), GT_ModHandler.getModItem("PracticalLogistics", "DataReceiver", 1L, 0), 200, 8);
        // --- Transceiver
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("IC2", "itemFreq", 1L, 0), GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L), GT_ModHandler.getModItem("PracticalLogistics", "Transceiver", 1L, 0), 200, 8);
        // --- Redstone Signaller
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "redstone_torch", 1L), GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L), GT_ModHandler.getModItem("PracticalLogistics", "RedstoneSignaller_OFF", 1L), 200, 8);
        // --- Data Cable
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Cobalt, 1L), GT_ModHandler.getModItem("EnderIO", "itemMaterial", 4L, 1), GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L, 0), 100, 8);
        // --- Channelled Cable
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 8L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("PracticalLogistics", "MultiCable", 1L), 200, 8);
        // --- Clock
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("ExtraUtilities", "timer", 1L), GT_ModHandler.getModItem("PracticalLogistics", "DataCable", 1L), GT_ModHandler.getModItem("PracticalLogistics", "Clock", 1L, 0), 200, 8);
        // --- Display Screen
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Cover_Screen.get(1), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Cobalt, 2), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicon, 4)}, null, GT_ModHandler.getModItem("PracticalLogistics", "DisplayScreenItem", 1L, 0), 200, 8);
        // --- Large Display Screen
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("PracticalLogistics", "DisplayScreenItem", 2L, 0), GT_Utility.getIntegratedCircuit(1), GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L, 0), 100, 8);

        /* ==== END PRACTICAL LOGISTICS ==== */
        /** ==== START GALACTICRAFT & GALAXYSPACE ==== */
        // ---  T1 Rocket + chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.spaceship", 1L), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_ModHandler.getModItem("GalacticraftCore", "item.spaceship", 1L, 3), 200, 64);
        // ---  T2 Rocket + chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.spaceshipTier2", 1L), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_ModHandler.getModItem("GalacticraftMars", "item.spaceshipTier2", 1L, 3), 200, 64);
        // ---  T3 Rocket + chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemTier3Rocket", 1L), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_ModHandler.getModItem("GalacticraftMars", "item.itemTier3Rocket", 1L, 3), 200, 64);
        // ---  T4 Rocket + chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier4Rocket", 1L), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_ModHandler.getModItem("GalaxySpace", "item.Tier4Rocket", 1L, 3), 200, 64);
        // ---  T5 Rocket + chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier5Rocket", 1L), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_ModHandler.getModItem("GalaxySpace", "item.Tier5Rocket", 1L, 3), 200, 64);
        // ---  T6 Rocket + chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier6Rocket", 1L), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_ModHandler.getModItem("GalaxySpace", "item.Tier6Rocket", 1L, 3), 200, 64);
        // ---  T7 Rocket + chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier7Rocket", 1L), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_ModHandler.getModItem("GalaxySpace", "item.Tier7Rocket", 1L, 3), 200, 64);
        // ---  T8 Rocket + chest
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier8Rocket", 1L), GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 2), GT_ModHandler.getModItem("GalaxySpace", "item.Tier8Rocket", 1L, 3), 200, 64);

        // --- Oxygen Mask
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("IC2", "itemArmorHazmatHelmet", 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.ReinforcedGlass, 16), Materials.Rubber.getMolten(144), GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenMask", 1L), 400, 120);
        // --- Thermal Cloth
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 4), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polycaprolactam, 1), Materials.Polytetrafluoroethylene.getMolten(576), GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 7), 100, 1920);
        // --- Glowstone Torch
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "redstone_torch", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 1), GT_ModHandler.getModItem("GalacticraftCore", "tile.glowstoneTorch", 1L), 200, 16);
        // --- Canister
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 8L, 9), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 4), GT_ModHandler.getModItem("GalacticraftCore", "item.oilCanisterPartial", 1L, 1001), 200, 64);
        // --- Canister T2
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 8L, 6), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Titanium, 4), GT_ModHandler.getModItem("GalacticraftMars", "item.LOX2", 1L, 8001), 300, 256);
        // --- Canister T3
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.TungstenSteel, 8), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.TungstenSteel, 4), GT_ModHandler.getModItem("GalacticraftMars", "item.LOX3", 1L, 32001), 400, 1024);
        // --- Hydrogen Pipe
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Copper, 4), GT_ModHandler.getModItem("GalacticraftMars", "tile.hydrogenPipe", 1L), 400, 16);
        // --- Thermal Padding Helm
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 5L, 7), GT_Utility.getIntegratedCircuit(5), GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L), 750, 1024);
        // --- Thermal Padding Chestpiece
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 8L, 7), GT_Utility.getIntegratedCircuit(8), GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 1), 1200, 1024);
        // --- Thermal Padding Leggings
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 7L, 7), GT_Utility.getIntegratedCircuit(7), GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 2), 1050, 1024);
        // --- Thermal Padding Boots
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 4L, 7), GT_Utility.getIntegratedCircuit(4), GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 3), 600, 1024);

        // --- Thermal Cloth Tier 2
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.foil, Materials.MeteoricSteel, 4), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Titanium, 4), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tungsten, 4), GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 7)}, Materials.Polycaprolactam.getMolten(576), GT_ModHandler.getModItem("GalaxySpace", "item.ThermalClothT2", 1L), 200, 7680);
        // --- Lead Battery
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.battery", 1L, GT_Values.W), GT_Utility.getIntegratedCircuit(1), Materials.Lead.getMolten(4608), GT_ModHandler.getModItem("GalaxySpace", "item.LeadBattery", 1L, 100), 100, 120);
        // --- Thermal Padding Helmet Tier 2
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalClothT2", 5L), GT_Utility.getIntegratedCircuit(5), GT_ModHandler.getModItem("GalaxySpace", "item.ThermalPaddingT2", 1L), 1500, 7680);
        // --- Thermal Padding Chestpiece Tier 2
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalClothT2", 8L), GT_Utility.getIntegratedCircuit(8), GT_ModHandler.getModItem("GalaxySpace", "item.ThermalPaddingT2", 1L, 1), 2400, 7680);
        // --- Thermal Padding Leggings Tier 2
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalClothT2", 7L), GT_Utility.getIntegratedCircuit(7), GT_ModHandler.getModItem("GalaxySpace", "item.ThermalPaddingT2", 1L, 2), 2100, 7680);
        // --- Thermal Padding Boots Tier 2
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalClothT2", 4L), GT_Utility.getIntegratedCircuit(4), GT_ModHandler.getModItem("GalaxySpace", "item.ThermalPaddingT2", 1L, 3), 1200, 7680);
        // --- Space Suit Jetpack
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("GalaxySpace", "item.spacesuit_plate", 1L), GT_ModHandler.getModItem("IC2", "itemArmorJetpackElectric", 1L, GT_Values.W), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 4L, 2)}, Materials.Platinum.getMolten(1440), GT_ModHandler.getModItem("GalaxySpace", "item.spacesuit_jetplate", 1L), 600, 1920);
        // --- Space Suit Gravity Boots
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("GalaxySpace", "item.spacesuit_boots", 1L), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 2L, 4), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 4L, 2)}, Materials.Platinum.getMolten(1440), GT_ModHandler.getModItem("GalaxySpace", "item.spacesuit_gravityboots", 1L), 600, 1920);
        // --- Space Suit Sensor Glasses
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("GalaxySpace", "item.spacesuit_helmet", 1L), GT_ModHandler.getModItem("GalacticraftCore", "item.sensorGlasses", 1L), GT_ModHandler.getModItem("GalaxySpace", "item.CompressedPlates", 4L, 2)}, Materials.Platinum.getMolten(1440), GT_ModHandler.getModItem("GalaxySpace", "item.spacesuit_helmetglasses", 1L), 600, 1920);
        // --- Future Glass
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("minecraft", "glass", 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Desh, 8), GT_ModHandler.getModItem("GalaxySpace", "futureglass", 1L), 200, 480);


        /* ==== END GALACTICRAFT & GALAXYSPACE ==== */
        /** ==== START BACKPACK ==== */
        // --- Bound Leather
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(Items.leather, 2), new ItemStack(Items.string, 3), GT_Utility.getIntegratedCircuit(1)}, null, GT_ModHandler.getModItem("Backpack", "boundLeather", 1L), 100, 16);

        /* ==== END BACKPACK ==== */


        ItemStack[] inHatches = {GT_ItemList.Hatch_Input_UEV.get(1), GT_ItemList.Hatch_Input_UIV.get(1), GT_ItemList.Hatch_Input_UMV.get(1), GT_ItemList.Hatch_Input_UXV.get(1), GT_ItemList.Hatch_Input_OPV.get(1), GT_ItemList.Hatch_Input_MAX.get(1)};
        ItemStack[] outHatches = {GT_ItemList.Hatch_Output_UEV.get(1), GT_ItemList.Hatch_Output_UIV.get(1), GT_ItemList.Hatch_Output_UMV.get(1), GT_ItemList.Hatch_Output_UXV.get(1), GT_ItemList.Hatch_Output_OPV.get(1), GT_ItemList.Hatch_Output_MAX.get(1)};
        ItemStack[][] flInputs = new ItemStack[6][3];
        ItemStack[][] flInputs2 = new ItemStack[6][3];
        ItemStack[] tanks = {GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 142), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 143), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 144), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 120), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 121), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 122),};
        ItemStack[] hulls = {ItemList.Hull_UEV.get(1), ItemList.Hull_UIV.get(1), ItemList.Hull_UMV.get(1), ItemList.Hull_UXV.get(1), ItemList.Hull_OPV.get(1), ItemList.Hull_MAXV.get(1),};
        for (int i = 0; i < 6; i++) {
            flInputs[i] = new ItemStack[]{hulls[i].copy(), tanks[i].copy(), GT_Utility.getIntegratedCircuit(1)};
            flInputs2[i] = new ItemStack[]{hulls[i].copy(), tanks[i].copy(), GT_Utility.getIntegratedCircuit(2)};
        }

        //10-15 since MAX crashes., change to 16 somwhen the NEI handler is fixed and MAX is optainable
        for (int aTier = 10; aTier < 15; aTier++) {
            if (Loader.isModLoaded("bartworks")) {
                GT_Values.RA.addAssemblerRecipe(flInputs[aTier - 10], Materials.RadoxPolymer.getMolten((long) (2.25 * Math.pow(2, (aTier - 9)))), inHatches[aTier - 10], 480, (int) (30 * Math.pow(4, (aTier - 1))), false);
                GT_Values.RA.addAssemblerRecipe(flInputs2[aTier - 10], Materials.RadoxPolymer.getMolten((long) (2.25 * Math.pow(2, (aTier - 9)))), outHatches[aTier - 10], 480, (int) (30 * Math.pow(4, (aTier - 1))), false);
            } else {
                GT_Values.RA.addAssemblerRecipe(flInputs[aTier - 10], Materials.PerroxPolymer.getMolten((long) (2.25 * Math.pow(2, (aTier - 9)))), inHatches[aTier - 10], 480, (int) (30 * Math.pow(4, (aTier - 1))), false);
                GT_Values.RA.addAssemblerRecipe(flInputs2[aTier - 10], Materials.PerroxPolymer.getMolten((long) (2.25 * Math.pow(2, (aTier - 9)))), outHatches[aTier - 10], 480, (int) (30 * Math.pow(4, (aTier - 1))), false);
            }
        }

        ItemStack[] inBuses = {GT_ItemList.Bus_Input_UEV.get(1), GT_ItemList.Bus_Input_UIV.get(1), GT_ItemList.Bus_Input_UMV.get(1), GT_ItemList.Bus_Input_UXV.get(1), GT_ItemList.Bus_Input_OPV.get(1), GT_ItemList.Bus_Input_MAX.get(1)};
        ItemStack[] outBuses = {GT_ItemList.Bus_Output_UEV.get(1), GT_ItemList.Bus_Output_UIV.get(1), GT_ItemList.Bus_Output_UMV.get(1), GT_ItemList.Bus_Output_UXV.get(1), GT_ItemList.Bus_Output_OPV.get(1), GT_ItemList.Bus_Output_MAX.get(1)};
        ItemStack[][] itInputs = new ItemStack[6][3];
        ItemStack[][] itInputs2 = new ItemStack[6][3];
        ItemStack[] chests = {GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 147), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 148), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 149), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 127), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 128), GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 129),};
        for (int i = 0; i < 6; i++) {
            itInputs[i] = new ItemStack[]{hulls[i].copy(), chests[i].copy(), GT_Utility.getIntegratedCircuit(1)};
            itInputs2[i] = new ItemStack[]{hulls[i].copy(), chests[i].copy(), GT_Utility.getIntegratedCircuit(2)};
        }
        for (int aTier = 10; aTier < 15; aTier++) {
            if (Loader.isModLoaded("bartworks")) {
                GT_Values.RA.addAssemblerRecipe(itInputs[aTier - 10], Materials.RadoxPolymer.getMolten((long) (2.25 * Math.pow(2, (aTier - 9)))), inBuses[aTier - 10], 480, (int) (30 * Math.pow(4, (aTier - 1))), false);
                GT_Values.RA.addAssemblerRecipe(itInputs2[aTier - 10], Materials.RadoxPolymer.getMolten((long) (2.25 * Math.pow(2, (aTier - 9)))), outBuses[aTier - 10], 480, (int) (30 * Math.pow(4, (aTier - 1))), false);
            } else {
                GT_Values.RA.addAssemblerRecipe(itInputs[aTier - 10], Materials.PerroxPolymer.getMolten((long) (2.25 * Math.pow(2, (aTier - 9)))), inBuses[aTier - 10], 480, (int) (30 * Math.pow(4, (aTier - 1))), false);
                GT_Values.RA.addAssemblerRecipe(itInputs2[aTier - 10], Materials.PerroxPolymer.getMolten((long) (2.25 * Math.pow(2, (aTier - 9)))), outBuses[aTier - 10], 480, (int) (30 * Math.pow(4, (aTier - 1))), false);
            }
        }

        /* ================================= end GT MOD =================================*/
    }
}
