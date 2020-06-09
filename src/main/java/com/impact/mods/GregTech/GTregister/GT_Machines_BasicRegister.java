package com.impact.mods.GregTech.GTregister;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;

import gregtech.api.metatileentity.implementations.*;
import gregtech.common.tileentities.generators.*;
import gregtech.common.tileentities.storage.*;
import com.impact.mods.GregTech.tileentities.basic.*;
import com.impact.mods.GregTech.tileentities.hatches.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import static com.impact.mods.GregTech.GTregister.GT_ItemList.*;

public class GT_Machines_BasicRegister {
	public void run()
	{
		registerMachines();
	}

	private void registerMachines() {
		int ID = 14500;
		int ID2 = 14534;

		long bitsd = GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE
				| GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED;

		/* BASIC */

		Portable_Tank_ULV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.00", "ULV Portable Tank",     0).getStackForm(1L));
		Portable_Tank_LV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.01", "LV Portable Tank",      1).getStackForm(1L));
		Portable_Tank_MV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.02", "MV Portable Tank",      2).getStackForm(1L));
		Portable_Tank_HV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.03", "HV Portable Tank",      3).getStackForm(1L));
		Portable_Tank_EV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.04", "EV Portable Tank",      4).getStackForm(1L));
		Portable_Tank_IV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.05", "IV Portable Tank",      5).getStackForm(1L));
		Portable_Tank_LuV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.06", "LuV Portable Tank",     6).getStackForm(1L));
		Portable_Tank_ZPM	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.07", "ZPM Portable Tank",     7).getStackForm(1L));
		Portable_Tank_UV	.set(new GT_MetaTileEntity_PortableTank(ID++,  "portabletank.tier.08", "UV Portable Tank",      8).getStackForm(1L));

		/*Energy_4A_IV		.set(new GT_MetaTileEntity_Hatch_Energy(9++,  "energy4A.tier.05",  "IV Energy Hatch 4A",    5, 4).getStackForm(1L));
		Energy_4A_LuV		.set(new GT_MetaTileEntity_Hatch_Energy(10++,  "energy4A.tier.06",  "LuV Energy Hatch 4A",   6, 4).getStackForm(1L));
		Energy_4A_ZPM		.set(new GT_MetaTileEntity_Hatch_Energy(11++,  "energy4A.tier.07",  "ZPM Energy Hatch 4A",   7, 4).getStackForm(1L));
		Energy_4A_UV		.set(new GT_MetaTileEntity_Hatch_Energy(12++,  "energy4A.tier.08",  "UV Energy Hatch 4A",    8, 4).getStackForm(1L));
		Energy_16A_LuV		.set(new GT_MetaTileEntity_Hatch_Energy(13++,  "energy16A.tier.06", "LuV Energy Hatch 16A",  6, 16).getStackForm(1L));
		Energy_16A_ZPM		.set(new GT_MetaTileEntity_Hatch_Energy(14++,  "energy16A.tier.07", "ZPM Energy Hatch 16A",  7, 16).getStackForm(1L));
		Energy_16A_UV		.set(new GT_MetaTileEntity_Hatch_Energy(15++,  "energy16A.tier.08", "UV Energy Hatch 16A",   8, 16).getStackForm(1L));
		Energy_64A_ZPM		.set(new GT_MetaTileEntity_Hatch_Energy(16++,  "energy64A.tier.07", "ZPM Energy Hatch 64A",  7, 64).getStackForm(1L));
		Energy_64A_UV		.set(new GT_MetaTileEntity_Hatch_Energy(17++,  "energy64A.tier.08", "UV Energy Hatch 64A",   8, 64).getStackForm(1L));
		Energy_256A_UV		.set(new GT_MetaTileEntity_Hatch_Energy(18++,  "energy256A.tier.08","UV Energy Hatch 256A",  8, 256).getStackForm(1L));

		Dynamo_2A_EV		.set(new GT_MetaTileEntity_Hatch_Dynamo(19,  "dynamo2A.tier.04",  "EV Dynamo Hatch 2A",    4, 2).getStackForm(1L));
		Dynamo_2A_IV		.set(new GT_MetaTileEntity_Hatch_Dynamo(20++,  "dynamo2A.tier.05",  "IV Dynamo Hatch 2A",    5, 2).getStackForm(1L));
		Dynamo_2A_LuV		.set(new GT_MetaTileEntity_Hatch_Dynamo(21++,  "dynamo2A.tier.06",  "LuV Dynamo Hatch 2A",   6, 2).getStackForm(1L));
		Dynamo_2A_ZPM		.set(new GT_MetaTileEntity_Hatch_Dynamo(22++,  "dynamo2A.tier.07",  "ZPM Dynamo Hatch 2A",   7, 2).getStackForm(1L));
		Dynamo_2A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(23++,  "dynamo2A.tier.08",  "UV Dynamo Hatch 2A",    8, 2).getStackForm(1L));
		Dynamo_4A_IV		.set(new GT_MetaTileEntity_Hatch_Dynamo(24++,  "dynamo4A.tier.05",  "IV Dynamo Hatch 4A",    5, 4).getStackForm(1L));
		Dynamo_4A_LuV		.set(new GT_MetaTileEntity_Hatch_Dynamo(25++,  "dynamo4A.tier.06",  "LuV Dynamo Hatch 4A",   6, 4).getStackForm(1L));
		Dynamo_4A_ZPM		.set(new GT_MetaTileEntity_Hatch_Dynamo(26++,  "dynamo4A.tier.07",  "ZPM Dynamo Hatch 4A",   7, 4).getStackForm(1L));
		Dynamo_4A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(27++,  "dynamo4A.tier.08",  "UV Dynamo Hatch 4A",    8, 4).getStackForm(1L));
		Dynamo_16A_LuV		.set(new GT_MetaTileEntity_Hatch_Dynamo(28++,  "dynamo16A.tier.06", "LuV Dynamo Hatch 16A",  6, 16).getStackForm(1L));
		Dynamo_16A_ZPM		.set(new GT_MetaTileEntity_Hatch_Dynamo(29++,  "dynamo16A.tier.07", "ZPM Dynamo Hatch 16A",  7, 16).getStackForm(1L));
		Dynamo_16A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(30++,  "dynamo16A.tier.08", "UV Dynamo Hatch 16A",   8, 16).getStackForm(1L));
		Dynamo_64A_ZPM		.set(new GT_MetaTileEntity_Hatch_Dynamo(31++,  "dynamo64A.tier.07", "ZPM Dynamo Hatch 64A",  7, 64).getStackForm(1L));
		Dynamo_64A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(32++,  "dynamo64A.tier.08", "UV Dynamo Hatch 64A",   8, 64).getStackForm(1L));
		Dynamo_256A_UV		.set(new GT_MetaTileEntity_Hatch_Dynamo(33++,  "dynamo256A.tier.08","UV Dynamo Hatch 256A",  8, 256).getStackForm(1L));
*/
		Diode_2A_ULV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.00", "Diode Cable 2A ULV", 0, 2).getStackForm(1L));
		Diode_2A_LV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.01", "Diode Cable 2A LV",  1, 2).getStackForm(1L));
		Diode_2A_MV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.02", "Diode Cable 2A MV",  2, 2).getStackForm(1L));
		Diode_2A_HV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.03", "Diode Cable 2A HV",  3, 2).getStackForm(1L));
		Diode_2A_EV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.04", "Diode Cable 2A EV",  4, 2).getStackForm(1L));
		Diode_2A_IV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.05", "Diode Cable 2A IV",  5, 2).getStackForm(1L));
		Diode_2A_LuV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.06", "Diode Cable 2A LuV", 6, 2).getStackForm(1L));
		Diode_2A_ZPM		.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.07", "Diode Cable 2A ZPM", 7, 2).getStackForm(1L));
		Diode_2A_UV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode2A.tier.08", "Diode Cable 2A UV",  8, 2).getStackForm(1L));

		Diode_4A_ULV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.00", "Diode Cable 4A ULV", 0, 4).getStackForm(1L));
		Diode_4A_LV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.01", "Diode Cable 4A LV",  1, 4).getStackForm(1L));
		Diode_4A_MV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.02", "Diode Cable 4A MV",  2, 4).getStackForm(1L));
		Diode_4A_HV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.03", "Diode Cable 4A HV",  3, 4).getStackForm(1L));
		Diode_4A_EV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.04", "Diode Cable 4A EV",  4, 4).getStackForm(1L));
		Diode_4A_IV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.05", "Diode Cable 4A IV",  5, 4).getStackForm(1L));
		Diode_4A_LuV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.06", "Diode Cable 4A LuV", 6, 4).getStackForm(1L));
		Diode_4A_ZPM		.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.07", "Diode Cable 4A ZPM", 7, 4).getStackForm(1L));
		Diode_4A_UV			.set(new GT_MetaTileEntity_Diode(ID2++, "diode4A.tier.08", "Diode Cable 4A UV",  8, 4).getStackForm(1L));

		Diode_16A_ULV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.00", "Diode Cable 16A ULV", 0, 16).getStackForm(1L));
		Diode_16A_LV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.01", "Diode Cable 16A LV",  1, 16).getStackForm(1L));
		Diode_16A_MV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.02", "Diode Cable 16A MV",  2, 16).getStackForm(1L));
		Diode_16A_HV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.03", "Diode Cable 16A HV",  3, 16).getStackForm(1L));
		Diode_16A_EV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.04", "Diode Cable 16A EV",  4, 16).getStackForm(1L));
		Diode_16A_IV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.05", "Diode Cable 16A IV",  5, 16).getStackForm(1L));
		Diode_16A_LuV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.06", "Diode Cable 16A LuV", 6, 16).getStackForm(1L));
		Diode_16A_ZPM		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.07", "Diode Cable 16A ZPM", 7, 16).getStackForm(1L));
		Diode_16A_UV		.set(new GT_MetaTileEntity_Diode(ID2++, "diode16A.tier.08", "Diode Cable 16A UV",  8, 16).getStackForm(1L));


		DustWasherULV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13035, "basicmachine.dustwasher.tier.00", "ULV Dust Washer", 0, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000,   0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', OrePrefixes.plate.get(Materials.WroughtIron), 'P', GT_ItemList.ULVPump, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherLV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13036, "basicmachine.dustwasher.tier.01", "LV Dust Washer",  1, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*2, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherMV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13037, "basicmachine.dustwasher.tier.02", "MV Dust Washer",  2, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*3, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherHV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13038, "basicmachine.dustwasher.tier.03", "HV Dust Washer",  3, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*4, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherEV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13039, "basicmachine.dustwasher.tier.04", "EV Dust Washer",  4, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*5, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherIV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13040, "basicmachine.dustwasher.tier.05", "IV Dust Washer",  5, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*6, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherLuV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13041, "basicmachine.dustwasher.tier.06", "LuV Dust Washer", 6, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*7, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherZPM		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13042, "basicmachine.dustwasher.tier.07", "ZPM Dust Washer", 7, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*8, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherUV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13043, "basicmachine.dustwasher.tier.08", "UV Dust Washer",  8, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*8, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherUHV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13044, "basicmachine.dustwasher.tier.09", "UHV Dust Washer", 9, "Washed your Dusts",  GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*8, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));
		DustWasherUEV		.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13045, "basicmachine.dustwasher.tier.10", "UEV Dust Washer", 10, "Washed your Dusts", GT_Recipe.GT_Recipe_Map.sDustWashRecipes, 1, 1, 8000*8, 0, 1, "Autoclave.png", "", false, false, 0, "DUSTWASHER", new Object[]{"IPI", "IMI", "ICI", 'I', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE, 'P', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP, 'M', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE, 'C', GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL}).getStackForm(1L));

		if(GT_Mod.gregtechproxy.mComponentAssembler){
			Machine_LV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13010, "basicmachine.componentassembler.tier.01", "Basic Component Assembling Machine", 1, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 16000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_MV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13011, "basicmachine.componentassembler.tier.02", "Advanced Component Assembling Machine", 2, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 24000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_HV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13012, "basicmachine.componentassembler.tier.03", "Advanced Component Assembling Machine II", 3, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 32000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_EV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13013, "basicmachine.componentassembler.tier.04", "Advanced Component Assembling Machine III", 4, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 48000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_IV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13014, "basicmachine.componentassembler.tier.05", "Advanced Component Assembling Machine IV", 5, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 64000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_LuV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13015, "basicmachine.componentassembler.tier.06", "Extreme Component Assembling Machine", 6, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 64000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_ZPM_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13016, "basicmachine.componentassembler.tier.07", "Extreme Component Assembling Machine II", 7, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 64000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_UV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13017, "basicmachine.componentassembler.tier.08", "Extreme Component Assembling Machine III", 8, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 64000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_UHV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13018, "basicmachine.componentassembler.tier.09", "Epic Component Assembling Machine", 9, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 64000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
			Machine_UEV_ComponentAssembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13019, "basicmachine.componentassembler.tier.10", "Epic Component Assembling Machine II", 10, "Components, Assemble!", GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes,6, 1, 64000, 0, 1, "Assembler.png", GregTech_API.sSoundList.get(204), false, false, 0, "COMPONENTASSEMBLER", null).getStackForm(1L));
		}

		Machine_ULV_Assembler.set(new GT_MetaTileEntity_BasicMachine_GT_Recipe(13032, "basicmachine.assembler.tier.00", "Primitive Assembler", 0, "Avengers, Assemble!", GT_Recipe.GT_Recipe_Map.sAssemblerRecipes, 3, 1, 8000, 0, 1, "Assembler0.png", GregTech_API.sSoundList.get(204), false, false, 0, "ASSEMBLER", null).getStackForm(1L));
		Generator_Steam_Turbine_ULV.set(new GT_MetaTileEntity_SteamTurbine(13025, "basicgenerator.steamturbine.tier.00", "First Steam Turbine", 0).getStackForm(1L));
		Generator_Diesel_ULV.set(new GT_MetaTileEntity_DieselGenerator(13026, "basicgenerator.diesel.tier.00", "First Combustion Generator", 0).getStackForm(1L));
		Generator_Gas_Turbine_ULV.set(new GT_MetaTileEntity_GasTurbine(13027, "basicgenerator.gasturbine.tier.00", "First Gas Turbine", 0).getStackForm(1L));
		Generator_Semi_Turbine_ULV.set(new GTMTE_SemifluidGenerator(13028, "basicgenerator.semifluid.tier.00", "First Semifluid Generator", 0).getStackForm(1L));

		Hatch_Output_Prim.set(new GT_MetaTileEntity_Primitive_Hatch_Output(14013, "primitive.hatchoutput", "Primitive Output Hatch",0, 0).getStackForm(1L));
		Hatch_Output_Pump.set(new GT_MetaTileEntity_Primitive_Hatch_Output(14014, "primitive.hatchoutput1", "Pump Output Hatch",0, 1).getStackForm(1L));
		Bus_Input_Prim.set(new GT_MetaTileEntity_Primitive_InputBus(14012, "primitive.input", "Primitive Input Bus",0).getStackForm(1L));
		Bus_Output_Prim.set(new GT_MetaTileEntity_Primitive_OutputBus(14011, "primitive.output", "Primitive Output Bus",0).getStackForm(1L));

		Generator_Semi_Turbine_LV.set(new GTMTE_SemifluidGenerator(13029, "basicgenerator.semifluid.tier.01", "Basic Semifluid Generator", 1).getStackForm(1L));
		Generator_Semi_Turbine_MV.set(new GTMTE_SemifluidGenerator(13030, "basicgenerator.semifluid.tier.02", "Advanced Semifluid Generator", 2).getStackForm(1L));
		Generator_Semi_Turbine_HV.set(new GTMTE_SemifluidGenerator(13031, "basicgenerator.semifluid.tier.03", "Turbo Semifluid Generator", 3).getStackForm(1L));
		Water_Tank.set(new GTMTE_WaterTank(13034, "basicmachine.watertank", "Water Tank",0).getStackForm(1L));

		Hatch_Input_UEV.set(new GT_MetaTileEntity_Hatch_Input(12250, "hatch.input.tier.10", "Input Hatch (UEV)",10).getStackForm(1L));
		Hatch_Input_UIV.set(new GT_MetaTileEntity_Hatch_Input(12251, "hatch.input.tier.11", "Input Hatch (UIV)",11).getStackForm(1L));
		Hatch_Input_UMV.set(new GT_MetaTileEntity_Hatch_Input(12252, "hatch.input.tier.12", "Input Hatch (UMV)",12).getStackForm(1L));
		Hatch_Input_UXV.set(new GT_MetaTileEntity_Hatch_Input(12253, "hatch.input.tier.13", "Input Hatch (UXV)",13).getStackForm(1L));
		Hatch_Input_OPV.set(new GT_MetaTileEntity_Hatch_Input(12254, "hatch.input.tier.14", "Input Hatch (OpV)",14).getStackForm(1L));
		Hatch_Input_MAX.set(new GT_MetaTileEntity_Hatch_Input(12255, "hatch.input.tier.15", "Input Hatch (MAX)",15).getStackForm(1L));

		Hatch_Output_UEV.set(new GT_MetaTileEntity_Hatch_Output(12256, "hatch.output.tier.10", "Output Hatch (UEV)",10).getStackForm(1L));
		Hatch_Output_UIV.set(new GT_MetaTileEntity_Hatch_Output(12257, "hatch.output.tier.11", "Output Hatch (UIV)",11).getStackForm(1L));
		Hatch_Output_UMV.set(new GT_MetaTileEntity_Hatch_Output(12258, "hatch.output.tier.12", "Output Hatch (UMV)",12).getStackForm(1L));
		Hatch_Output_UXV.set(new GT_MetaTileEntity_Hatch_Output(12259, "hatch.output.tier.13", "Output Hatch (UXV)",13).getStackForm(1L));
		Hatch_Output_OPV.set(new GT_MetaTileEntity_Hatch_Output(12260, "hatch.output.tier.14", "Output Hatch (OpV)",14).getStackForm(1L));
		Hatch_Output_MAX.set(new GT_MetaTileEntity_Hatch_Output(12261, "hatch.output.tier.15", "Output Hatch (MAX)",15).getStackForm(1L));

		Bus_Input_UEV.set(new GT_MetaTileEntity_Hatch_InputBus(12262, "bus.input.tier.10", "Input Bus (UEV)",10).getStackForm(1L));
		Bus_Input_UIV.set(new GT_MetaTileEntity_Hatch_InputBus(12263, "bus.input.tier.11", "Input Bus (UIV)",11).getStackForm(1L));
		Bus_Input_UMV.set(new GT_MetaTileEntity_Hatch_InputBus(12264, "bus.input.tier.12", "Input Bus (UMV)",12).getStackForm(1L));
		Bus_Input_UXV.set(new GT_MetaTileEntity_Hatch_InputBus(12265, "bus.input.tier.13", "Input Bus (UXV)",13).getStackForm(1L));
		Bus_Input_OPV.set(new GT_MetaTileEntity_Hatch_InputBus(12266, "bus.input.tier.14", "Input Bus (OpV)",14).getStackForm(1L));
		Bus_Input_MAX.set(new GT_MetaTileEntity_Hatch_InputBus(12267, "bus.input.tier.15", "Input Bus (MAX)",15).getStackForm(1L));

		Bus_Output_UEV.set(new GT_MetaTileEntity_Hatch_OutputBus(12268, "bus.output.tier.10", "Output Bus (UEV)",10).getStackForm(1L));
		Bus_Output_UIV.set(new GT_MetaTileEntity_Hatch_OutputBus(12269, "bus.output.tier.11", "Output Bus (UIV)",11).getStackForm(1L));
		Bus_Output_UMV.set(new GT_MetaTileEntity_Hatch_OutputBus(12270, "bus.output.tier.12", "Output Bus (UMV)",12).getStackForm(1L));
		Bus_Output_UXV.set(new GT_MetaTileEntity_Hatch_OutputBus(12271, "bus.output.tier.13", "Output Bus (UXV)",13).getStackForm(1L));
		Bus_Output_OPV.set(new GT_MetaTileEntity_Hatch_OutputBus(12272, "bus.output.tier.14", "Output Bus (OpV)",14).getStackForm(1L));
		Bus_Output_MAX.set(new GT_MetaTileEntity_Hatch_OutputBus(12273, "bus.output.tier.15", "Output Bus (MAX)",15).getStackForm(1L));


	}
}
