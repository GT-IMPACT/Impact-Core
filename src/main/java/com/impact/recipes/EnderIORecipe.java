package com.impact.recipes;

import com.impact.mods.GregTech.GT_ItemList;
import crazypants.enderio.EnderIO;
import crazypants.enderio.item.darksteel.DarkSteelItems;
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

import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

public class EnderIORecipe implements Runnable {

    private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE/* | GT_ModHandler.RecipeBits.REVERSIBLE*/;

    public void delRecipe() {
        removeRecipeByOutput(new ItemStack(EnderIO.blockInventoryPanel, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemFunctionUpgrade, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemItemConduit, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemLiquidConduit, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemYetaWench, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemBigFilterUpgrade, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemBigFilterUpgrade, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemPowerItemFilter, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemOCConduit, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockTravelPlatform, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockTelePad, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockTransceiver, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(DarkSteelItems.itemGliderWing, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(DarkSteelItems.itemGliderWing, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemTravelStaff, 1, 16), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockKillerJoe, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockEnderRail, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockEnderIo, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockAlloySmelter, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockCrusher, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockPowerMonitor, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMaterial, 1, 2), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockVat, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockWirelessCharger, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockZombieGenerator, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockSolarPanel, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockSolarPanel, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockSolarPanel, 1, 2), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemEnderFood, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockSpawnGuard, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockWeatherObelisk, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemCoordSelector, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockBuffer, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockBuffer, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockBuffer, 1, 2), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockBuffer, 1, 3), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockReinforcedObsidian, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockEnchanter, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockDarkIronBars, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockDarkSteelPressurePlate, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockVacuumChest, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemBasicCapacitor, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemBasicCapacitor, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemBasicCapacitor, 1, 2), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockCapBank, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockCapBank, 1, 2), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockCapBank, 1, 3), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemConduitProbe, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockPainter, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockCrafter, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockFarmStation, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockCombustionGenerator, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockStirlingGenerator, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockReservoir, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(DarkSteelItems.itemMagnet, 1, 16), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockElectricLight, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockAttractor, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockExperianceOblisk, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemXpTransfer, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemSoulVessel, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemBasicFilterUpgrade, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemBasicFilterUpgrade, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemModItemFilter, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemRedstoneConduit, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemRedstoneConduit, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemRedstoneConduit, 1, 2), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemPowerConduit, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemPowerConduit, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemPowerConduit, 1, 2), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemLiquidConduit, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemLiquidConduit, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemLiquidConduit, 1, 2), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMEConduit, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMEConduit, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockSliceAndSplice, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockSoulFuser, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockPoweredSpawner, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockKillerJoe, 1, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "darkSteel_helmet", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "darkSteel_chestplate", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "darkSteel_leggings", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "darkSteel_boots", 1L, 0), true, false, false);
        removeRecipeByOutput(DarkSteelItems.itemDarkSteelSword.createItemStack(), true, false, false);
        removeRecipeByOutput(DarkSteelItems.itemDarkSteelPickaxe.createItemStack(), true, false, false);
        removeRecipeByOutput(DarkSteelItems.itemDarkSteelAxe.createItemStack(), true, false, false);
        removeRecipeByOutput(DarkSteelItems.itemDarkSteelShears.createItemStack(), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMachinePart, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockTank, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockTank, 1, 1), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMaterial, 1, 7), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMachinePart, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMaterial, 1, 6), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMaterial, 1, 5), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemPowderIngot, 1, GT_Values.W), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMaterial, 1, 3), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemMaterial, 1, 4), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockDarkSteelAnvil, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockDarkSteelAnvil, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockFrankenZombieGenerator, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.blockEnderGenerator, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemExtractSpeedUpgrade, 1, 0), true, false, false);
        removeRecipeByOutput(new ItemStack(EnderIO.itemExtractSpeedUpgrade, 1, 1), true, false, false);
    }

    public void handRecipe() {
        // --- Inventory Panel
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockInventoryPanel, 1, 0), tBitMask, new Object[]{"PGP", "COR", "PIP", 'O', GT_ModHandler.getModItem("TConstruct", "CraftingSlab", 1L), 'P', OrePrefixes.plate.get(Materials.Steel), 'I', OrePrefixes.circuit.get(Materials.Basic), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), 'C', ItemList.Conveyor_Module_LV, 'R', ItemList.Robot_Arm_LV});
        // --- Remote Awareness Upgrade
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemFunctionUpgrade, 1, 0), tBitMask, new Object[]{"SES", "PDP", "hGd", 'P', OrePrefixes.plate.get(Materials.Silicon), 'S', OrePrefixes.screw.get(Materials.Steel), 'G', OrePrefixes.gear.get(Materials.Steel), 'E', OrePrefixes.gearGtSmall.get(Materials.Steel), 'D', OrePrefixes.gem.get(Materials.Diamond)});
        // --- YetaWrench
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemYetaWench, 1, 0), tBitMask, new Object[]{"IhI", "IGI", " I ", 'I', OrePrefixes.ingot.get(Materials.Steel), 'G', OrePrefixes.gear.get(Materials.Steel)});
        // --- Travel Anchor
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockTravelPlatform, 1, 0), tBitMask, new Object[]{"PGP", "COC", "PFP", 'O', new ItemStack(EnderIO.itemMachinePart, 1, 0), 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'C', OrePrefixes.circuit.get(Materials.Basic), 'G', GT_ModHandler.getModItem("OpenBlocks", "elevator", 1L), 'F', ItemList.Field_Generator_LV});
        // --- Painter
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockPainter, 1, 0), new Object[]{"PRP", "CMC", "LRL", 'R', OrePrefixes.rotor.get(Materials.Steel), 'P',  OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.circuit.get(Materials.Basic), 'L', ItemList.Electric_Motor_LV.get(1L),  'M', new ItemStack(EnderIO.itemMachinePart, 1, 0)});
        // --- Casing
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemMachinePart, 1, 0), new Object[]{"PBP", "BCB", "PBP", 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'C', new ItemStack(EnderIO.itemBasicCapacitor, 1, 0), 'B', new ItemStack(Blocks.iron_bars, 1, 0)});
        // --- Basic Capacitor
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockCapBank, 1, 1), new Object[]{"CIC", "PMP", "CBC", 'P', OrePrefixes.plate.get(Materials.ConductiveIron), 'C', new ItemStack(EnderIO.itemBasicCapacitor, 1, 0), 'I', OrePrefixes.circuit.get(Materials.Basic), 'B', ItemList.Battery_RE_LV_Lithium, 'M', new ItemStack(EnderIO.itemMachinePart, 1, 0)});
        // --- Energetic Capacitor
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockCapBank, 1, 2), new Object[]{"CIC", "PMP", "CBC", 'P', OrePrefixes.plate.get(Materials.EnergeticAlloy), 'C', new ItemStack(EnderIO.itemBasicCapacitor, 1, 1), 'I', OrePrefixes.circuit.get(Materials.Good), 'B', ItemList.Battery_RE_MV_Lithium, 'M', new ItemStack(EnderIO.blockCapBank, 1, 1)});
        // --- Vibrant Capacitor
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockCapBank, 1, 3), new Object[]{"CIC", "PMP", "CBC", 'P', OrePrefixes.plate.get(Materials.VibrantAlloy), 'C', new ItemStack(EnderIO.itemBasicCapacitor, 1, 2), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'B', ItemList.Battery_RE_HV_Lithium, 'M', new ItemStack(EnderIO.blockCapBank, 1, 2)});
        // --- Crafter
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockCrafter, 1, 0), new Object[]{"POP", "IMI", "PCP", 'P', OrePrefixes.itemCasing.get(Materials.Steel), 'C', new ItemStack(EnderIO.itemBasicCapacitor, 1, 0), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', new ItemStack(EnderIO.itemMachinePart, 1, 0), 'O', ItemList.Cover_Crafting});
        // --- Wireless Charger
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockWirelessCharger, 1, 0), new Object[]{"POP", "IMI", "PCP", 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'C', new ItemStack(EnderIO.itemBasicCapacitor, 1, 2), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'M', new ItemStack(EnderIO.itemMachinePart, 1, 0), 'O', ItemList.Field_Generator_MV});
        // --- Staff of Travelling
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemTravelStaff, 1, 16), new Object[]{"dCE", "SIC", "ISw", 'I', OrePrefixes.stick.get(Materials.DarkSteel), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'S', OrePrefixes.screw.get(Materials.VibrantAlloy), 'E', ItemList.Sensor_HV});
        // --- DarkSteel Pressure Plate
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockDarkSteelPressurePlate, 1, 0), new Object[]{"ShS", "PIP", "SdS", 'S', OrePrefixes.screw.get(Materials.Steel), 'P', OrePrefixes.plate.get(Materials.DarkSteel), 'I', OrePrefixes.spring.get(Materials.Steel)});
        // --- Farming Station
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockFarmStation, 1, 0), new Object[]{"RPR", "IMI", "GCG", 'P', OrePrefixes.plateDense.get(Materials.PulsatingIron), 'G', OrePrefixes.gearGt.get(Materials.ElectricalSteel), 'C', new ItemStack(EnderIO.itemBasicCapacitor, 1, 2), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'M', new ItemStack(EnderIO.itemMachinePart, 1, 0), 'R', ItemList.Robot_Arm_HV});
        // --- Conduit Probe
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemConduitProbe, 1, 0), new Object[]{"SIS", "CMC", "ERE", 'S', OrePrefixes.plate.get(Materials.Silicon), 'E', OrePrefixes.plate.get(Materials.ElectricalSteel), 'I', new ItemStack(EnderIO.itemPowerConduit, 1, 0), 'C', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getModItem("ProjRed|Integration", "projectred.integration.gate", 1L, 26), 'R', new ItemStack(EnderIO.itemRedstoneConduit, 1, 2)});
        // --- Buffer
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockBuffer, 1, 0), new Object[]{"ISI", "SCS", "ISI", 'S', OrePrefixes.plate.get(Materials.Steel), 'I', OrePrefixes.plate.get(Materials.Iron), 'C', new ItemStack(Blocks.chest)});
        // --- Magnet
        GT_ModHandler.addCraftingRecipe(new ItemStack(DarkSteelItems.itemMagnet, 1, 16), new Object[]{"ECC", "wST", "ECC", 'E', OrePrefixes.plate.get(Materials.ElectricalSteel), 'C', OrePrefixes.plate.get(Materials.ConductiveIron), 'S', OrePrefixes.screw.get(Materials.SteelMagnetic), 'T', ItemList.Emitter_LV});
        // --- Light
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.blockElectricLight, 1, 0), new Object[]{"QQQ", "PDP", "PCP", 'Q', new ItemStack(EnderIO.blockFusedQuartz, 1, 0), 'P', OrePrefixes.plate.get(Materials.Iron), 'D', OrePrefixes.dust.get(Materials.Glowstone), 'C', new ItemStack(EnderIO.itemPowerConduit, 1, 0)});
        // --- Item Conduit Speed Upgrade
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemExtractSpeedUpgrade, 1, 0), new Object[]{"POP", "GIG", "PWP", 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'O', OrePrefixes.plate.get(Materials.Iron), 'G', OrePrefixes.gearGtSmall.get(Materials.Iron), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'I', new ItemStack(Blocks.piston)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemExtractSpeedUpgrade, 2, 0), new Object[]{"POP", "GIG", "PWP", 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'O', OrePrefixes.plate.get(Materials.WroughtIron), 'G', OrePrefixes.gearGtSmall.get(Materials.WroughtIron), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'I', GT_ItemList.ULVPiston.get(1L)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemExtractSpeedUpgrade, 4, 0), new Object[]{"POP", "GIG", "PWP", 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'O', OrePrefixes.plate.get(Materials.Steel), 'G', OrePrefixes.gearGtSmall.get(Materials.Steel), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'I', ItemList.Electric_Piston_LV.get(1L)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemExtractSpeedUpgrade, 8, 0), new Object[]{"POP", "GIG", "PWP", 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'O', OrePrefixes.plate.get(Materials.Aluminium), 'G', OrePrefixes.gearGtSmall.get(Materials.Aluminium), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'I', ItemList.Electric_Piston_MV.get(1L)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(EnderIO.itemExtractSpeedUpgrade, 16, 0), new Object[]{"POP", "GIG", "PWP", 'P', OrePrefixes.plate.get(Materials.ElectricalSteel), 'O', OrePrefixes.plate.get(Materials.StainlessSteel), 'G', OrePrefixes.gearGtSmall.get(Materials.StainlessSteel), 'W', OrePrefixes.wireGt01.get(Materials.RedAlloy), 'I', ItemList.Electric_Piston_HV.get(1L)});
    }

    public void alloySmelterRecipe() {
        // --- Fused Quartz
        GT_Values.RA.addAlloySmelterRecipe(new ItemStack(Items.quartz, 4), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L), new ItemStack(EnderIO.blockFusedQuartz, 1, 0), 200, 8);
    }

    public void assemblerRecipe() {
        // --- Item Conduit
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Conveyor_Module_LV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Electrum, 4L), new ItemStack(EnderIO.itemMaterial, 4, 1), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(EnderIO.itemItemConduit, 4, 0), 120, 16);
        // --- Fluid Conduit
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Pump_LV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 4L), new ItemStack(EnderIO.itemMaterial, 4, 1), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(EnderIO.itemLiquidConduit, 4, 0), 120, 16);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Pump_MV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 4L), new ItemStack(EnderIO.itemMaterial, 4, 1), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(EnderIO.itemLiquidConduit, 4, 1), 160, 48);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Electric_Pump_HV.get(1), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 4L), new ItemStack(EnderIO.itemMaterial, 4, 1), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(EnderIO.itemLiquidConduit, 4, 2), 200, 160);
        // --- Item Filter
        GT_Values.RA.addAssemblerRecipe(ItemList.Conveyor_Module_LV.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L), new ItemStack(EnderIO.itemBasicFilterUpgrade, 1, 0), 100, 16);
        // --- Advanced Item Filter
        GT_Values.RA.addAssemblerRecipe(ItemList.Conveyor_Module_MV.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.ElectricalSteel, 1L), new ItemStack(EnderIO.itemBasicFilterUpgrade, 1, 1), 100, 64);
        // --- Big Item Filter
        GT_Values.RA.addAssemblerRecipe(ItemList.Conveyor_Module_HV.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DarkSteel, 1L), new ItemStack(EnderIO.itemBigFilterUpgrade, 1, 0), 200, 256);
        // --- Big Advanced Item Filter
        GT_Values.RA.addAssemblerRecipe(ItemList.Conveyor_Module_EV.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSLA, 1L), new ItemStack(EnderIO.itemBigFilterUpgrade, 1, 1), 300, 480);
        // --- Mod Filter
        GT_Values.RA.addAssemblerRecipe(new ItemStack(EnderIO.itemBasicFilterUpgrade, 1, 0), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DarkSteel, 1L), new ItemStack(EnderIO.itemModItemFilter, 1, 0), 200, 120);
        // --- Chargeable Item Filter
        GT_Values.RA.addAssemblerRecipe(new ItemStack(EnderIO.itemConduitProbe, 1, 0), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.ElectricalSteel, 1L), new ItemStack(EnderIO.itemPowerItemFilter, 1, 0), 200, 64);
        // --- Network Conduit (OC)
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("OpenComputers", "cable", 1L), new ItemStack(EnderIO.itemMaterial, 1, 1), new ItemStack(EnderIO.itemOCConduit, 1, 0), 120, 16);
        // --- Capacitor
        GT_Values.RA.addAssemblerRecipe(ItemList.Battery_Hull_LV.get(1), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Plastic, 4L), Materials.Redstone.getMolten(576), new ItemStack(EnderIO.itemBasicCapacitor, 1, 0), 200, 30);
        // --- Capacitor 2
        GT_Values.RA.addAssemblerRecipe(new ItemStack(EnderIO.itemBasicCapacitor, 2, 0), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.EnergeticAlloy, 4L), Materials.Glowstone.getMolten(576), new ItemStack(EnderIO.itemBasicCapacitor, 1, 1), 300, 120);
        // --- Capacitor 3
        GT_Values.RA.addAssemblerRecipe(new ItemStack(EnderIO.itemBasicCapacitor, 2, 1), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.VibrantAlloy, 4L), Materials.ConductiveIron.getMolten(576), new ItemStack(EnderIO.itemBasicCapacitor, 1, 2), 400, 480);
        // --- Redstone Conduit
        GT_Values.RA.addAssemblerRecipe(new ItemStack(EnderIO.itemRedstoneConduit, 1, 2), new ItemStack(Blocks.lever, 1), new ItemStack(EnderIO.itemRedstoneConduit, 1, 1), 50, 60);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(EnderIO.itemRedstoneConduit, 1, 0), new ItemStack(EnderIO.itemMaterial, 1, 1), new ItemStack(EnderIO.itemRedstoneConduit, 1, 2), 50, 30);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 1L), new ItemStack(EnderIO.itemMaterial, 1, 1), new ItemStack(EnderIO.itemRedstoneConduit, 1, 0), 50, 30);
        // --- Power Conduit
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 1L), new ItemStack(EnderIO.itemMaterial, 1, 1), Materials.ConductiveIron.getMolten(144), new ItemStack(EnderIO.itemPowerConduit, 1, 0), 50, 30);
        // --- ME Conduit
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 16), new ItemStack(EnderIO.itemMaterial, 4, 1), new ItemStack(EnderIO.itemMEConduit, 1, 0), 50, 120);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 76), new ItemStack(EnderIO.itemMaterial, 4, 1), new ItemStack(EnderIO.itemMEConduit, 1, 1), 100, 480);
        // --- Speed Upgrade
        GT_Values.RA.addAssemblerRecipe(new ItemStack(EnderIO.itemExtractSpeedUpgrade, 1, 0), GT_Utility.getIntegratedCircuit(1), new ItemStack(EnderIO.itemExtractSpeedUpgrade, 1, 1), 50, 30);
        // --- Dark Pressure Plate
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DarkSteel, 2L), GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Steel, 1L), new ItemStack(EnderIO.blockDarkSteelPressurePlate, 1, 0), 200, 16);

    }

    public void chemicalBathRecipe() {
        GT_Values.RA.addChemicalBathRecipe(new ItemStack(EnderIO.blockFusedQuartz, 1, 0), Materials.Glowstone.getMolten(576L), new ItemStack(EnderIO.blockFusedQuartz, 1, 2), GT_Values.NI, GT_Values.NI, null, 100, 8);
        GT_Values.RA.addChemicalBathRecipe(new ItemStack(EnderIO.blockFusedQuartz, 1, 1), Materials.Glowstone.getMolten(576L), new ItemStack(EnderIO.blockFusedQuartz, 1, 3), GT_Values.NI, GT_Values.NI, null, 100, 4);
        GT_Values.RA.addChemicalBathRecipe(new ItemStack(Blocks.glass, 1), Materials.Chlorine.getGas(50L), new ItemStack(EnderIO.blockFusedQuartz, 1, 1), GT_Values.NI, GT_Values.NI, null, 400, 2);
    }

    public void fluidSolidifierRecipe() {
        GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), Materials.Concrete.getMolten(36L), new ItemStack(EnderIO.itemMaterial, 1, 1), 20, 4);
    }

    public void printerRecipe() {
        // --- Telepad
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1), new ItemStack(EnderIO.itemBasicCapacitor, 1, 2), ItemList.Field_Generator_MV.get(1L),
                ItemList.Sensor_HV.get(2L), new ItemStack(EnderIO.blockFusedQuartz, 4, 0), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.DarkSteel, 8)
        }, new ItemStack(EnderIO.blockTelePad, 9, 0), null, 20 * 20, 480);
        // --- Transceiver
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Enderium, 1), ItemList.Field_Generator_LuV.get(1L), new ItemStack(EnderIO.itemBasicCapacitor, 4, 2),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4), new ItemStack(EnderIO.blockFusedQuartz, 6, 0), new ItemStack(EnderIO.itemMaterial, 8, 5), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.DarkSteel, 12)
        }, new ItemStack(EnderIO.blockTransceiver, 1, 0), null, 40 * 20, 30720);

    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
        alloySmelterRecipe();
        assemblerRecipe();
        chemicalBathRecipe();
        fluidSolidifierRecipe();
        printerRecipe();
    }
}
