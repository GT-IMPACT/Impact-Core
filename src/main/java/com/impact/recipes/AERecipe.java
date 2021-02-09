package com.impact.recipes;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import com.impact.mods.GregTech.GT_ItemList;
import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

public class AERecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED
            | GT_ModHandler.RecipeBits.NOT_REMOVABLE/* | GT_ModHandler.RecipeBits.REVERSIBLE*/;

    public void delRecipe() {
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockFluix", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzChiseled", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGrowthAccelerator", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCharger", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCellWorkbench", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockIOPort", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCondenser", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSecurity", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSkyCompass", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, GT_Values.W));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, GT_Values.W));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzLamp", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzTorch", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockLightDetector", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockTinyTNT", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ToolMassCannon", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ToolMemoryCard", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ToolChargedStaff", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ToolEntropyManipulator", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ToolColorApplicator", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ToolBiometricCard", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemCrystalSeed", 1L, GT_Values.W));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ToolPortableCell", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInscriber", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockGrinder", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCrank", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.ChiseledQuartzSlabBlock", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.QuartzPillarSlabBlock", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.QuartzSlabBlock", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("minecraft", "quartz_block", 1L, 0));
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockController", 1L, 0), true, false,
                false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1L, 0),
                true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDrive", 1L, 0),
                true, false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockMolecularAssembler", 1L, 0),
                true, false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumRing", 1L, 0), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 1L, 0),
                true, false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 1L, 0), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialIOPort", 1L, 0), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1L, 0), true, false,
                false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyAcceptor", 1L, 0), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.1k", 1L), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.4k", 1L), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.16k", 1L), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.64k", 1L), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.2Cubed", 1L),
                true, false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.16Cubed", 1L),
                true, false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.128Cubed", 1L),
                true, false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemViewCell", 1L),
                true, false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyCell", 1L, 0), true, false,
                false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDenseEnergyCell", 1L, 0), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 1), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 0), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 1), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 2), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 3), true,
                false, false);
        removeRecipeByOutput(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockWireless", 1L, 0), true, false,
                false);
    }

    public void handRecipe() {
        // --- Charger
        GT_ModHandler
                .addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCharger", 1L), tBitMask,
                        new Object[]{"PEP", "Cw ", "PEP", 'P', OrePrefixes.plateDouble.get(Materials.Vanadium), 'E', ItemList.Emitter_EV,
                                'C', GT_ModHandler.getModItem("OpenComputers", "charger", 1L, 0)});
        // --- ME Storage Housing
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), tBitMask,
                new Object[]{"hQS", "TGT", "SVd", 'Q', OrePrefixes.plate.get(Materials.CertusQuartz),
                        'T', OrePrefixes.plate.get(Materials.StainlessSteel), 'V', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'S', OrePrefixes.screw.get(Materials.Quartzite), 'G', "paneGlass"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), tBitMask,
                new Object[]{"hQS", "TGT", "SVd", 'Q', OrePrefixes.plate.get(Materials.CertusQuartz),
                        'T', OrePrefixes.plate.get(Materials.StainlessSteel), 'V', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'S', OrePrefixes.screw.get(Materials.Quartzite), 'G', GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L)});
        // --- ME Interface
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1L, 0), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 440)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 440), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1L, 0)});
        // --- ME Conversion Monitor
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 420), tBitMask,
                new Object[]{"dAh", "SDS", "PEP", 'P', OrePrefixes.plate.get(Materials.NetherQuartz), 'S', OrePrefixes.screw.get(Materials.CertusQuartz),
                    'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 400),
                    'D', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 43),
                    'E', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 44)});
        // --- Cell Workbench
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCellWorkbench", 1L, 0), tBitMask,
                new Object[]{"CMC", "PEP", 'P', OrePrefixes.plate.get(Materials.Vanadium), 'C', OrePrefixes.circuit.get(Materials.Advanced),
                    'E', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1L, 0),
                    'M', ItemList.Cover_Screen});
        // --- ME IO Port
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockIOPort", 1L, 0), tBitMask,
                new Object[]{"CMC", "ADA", "PEP", 'P', OrePrefixes.plate.get(Materials.Vanadium),
                    'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                    'M', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 480),
                    'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37),
                    'D', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDrive", 1L, 0),
                    'E', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 76)});
        // --- Matter Condenser
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCondenser", 1L, 0), tBitMask,
                new Object[]{"FCF", "CMC", "FCF", 'F', OrePrefixes.foil.get(Materials.VanadiumSteel),
                    'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 44),
                    'M', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockMolecularAssembler", 1L, 0)});
        // --- ME Security Terminal
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSecurity", 1L, 0), tBitMask,
                new Object[]{"PMP", "ACA", "POP", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1L, 0),
                        'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        'O', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37),
                        'M', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 480)});
        // --- Meteorite Compass
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSkyCompass", 1L, 0), tBitMask,
                new Object[]{"dGS", "PRP", "SPh", 'P', OrePrefixes.plate.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.CertusQuartz),
                        'R', OrePrefixes.stick.get(Materials.SteelMagnetic), 'G', OrePrefixes.plate.get(Materials.Glass)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSkyCompass", 1L, 0), tBitMask,
                new Object[]{"hGS", "PRP", "SPd", 'P', OrePrefixes.plate.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.CertusQuartz),
                        'R', OrePrefixes.stick.get(Materials.SteelMagnetic), 'G', OrePrefixes.plate.get(Materials.Glass)});
        // --- Network Tool
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolNetworkTool", 1L, 0), tBitMask,
                new Object[]{"WPC", "O  ", 'W', GT_ModHandler.getModItem("EnderIO", "itemYetaWrench", 1L, 0),
                        'P', "itemIlluminatedPanel", 'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        'O', GT_ModHandler.getModItem("minecraft", "chest", 1L, 0)});
        // --- Fluix Pearl
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 9), tBitMask,
                new Object[]{"QPQ", "POP", "QPQ", 'P', OrePrefixes.plate.get(Materials.Grisium),
                        'Q', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        'O', OrePrefixes.lens.get(Materials.EnderPearl)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 9), tBitMask,
                new Object[]{"QPQ", "POP", "QPQ", 'P', OrePrefixes.plate.get(Materials.Grisium),
                        'Q', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 7),
                        'O', OrePrefixes.lens.get(Materials.EnderPearl)});
        // --- Terminal
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 380), tBitMask,
                new Object[]{"RSR", "ICP", "RdR", 'R', OrePrefixes.stick.get(Materials.NetherQuartz), 'S', OrePrefixes.screw.get(Materials.Quartzite),
                        'I', "itemIlluminatedPanel", 'C', OrePrefixes.circuit.get(Materials.Good), 'P', OrePrefixes.plate.get(Materials.CertusQuartz)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 380), tBitMask,
                new Object[]{"RdR", "ICP", "RSR", 'R', OrePrefixes.stick.get(Materials.NetherQuartz), 'S', OrePrefixes.screw.get(Materials.Quartzite),
                        'I', "itemIlluminatedPanel", 'C', OrePrefixes.circuit.get(Materials.Good), 'P', OrePrefixes.plate.get(Materials.CertusQuartz)});
        // --- Crafting Terminal
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 360), tBitMask,
                new Object[]{"dTh", "SOS", "PCP", 'S', OrePrefixes.screw.get(Materials.CertusQuartz), 'P', OrePrefixes.plate.get(Materials.NetherQuartz),
                        'T', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 380),
                        'O', ItemList.Cover_Crafting, 'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24)});
        // --- Interface Terminal
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 480), tBitMask,
                new Object[]{"dTh", "SOS", "PCP", 'S', OrePrefixes.screw.get(Materials.CertusQuartz), 'P', OrePrefixes.plate.get(Materials.NetherQuartz),
                        'T', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 380),
                        'O', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 440),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24)});
        // --- Pattern Terminal
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 340), tBitMask,
                new Object[]{"dTh", "SOS", "PCP", 'S', OrePrefixes.screw.get(Materials.CertusQuartz), 'P', OrePrefixes.plate.get(Materials.NetherQuartz),
                        'T', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 380),
                        'O', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 52),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24)});
        // --- Storage Monitor
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 400), tBitMask,
                new Object[]{"dTh", "SOS", "PPP", 'S', OrePrefixes.screw.get(Materials.CertusQuartz), 'P', OrePrefixes.plate.get(Materials.NetherQuartz),
                        'T', "itemIlluminatedPanel", 'O', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 280)});
        // --- Wireless Terminal
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolWirelessTerminal", 1L, 0), tBitMask,
                new Object[]{"WTW", "PCP", "PEP", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'T', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 380),
                        'W', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 41),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24),
                        'E', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDenseEnergyCell", 1L, 0)});
        // --- Vibrant Quartz Glass
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzLamp", 1L, 0), tBitMask,
                new Object[]{"DPD", "PGP", "DPD", 'D', OrePrefixes.dust.get(Materials.Glowstone), 'P', OrePrefixes.plate.get(Materials.Glowstone),
                        'G', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 1L, 0)});
        // --- Charged Quartz Fixture
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzTorch", 1L, 0), tBitMask,
                new Object[]{OrePrefixes.gem.get(Materials.CertusQuartz), OrePrefixes.stick.get(Materials.Aluminium)});
        // --- Light Detecting Fixture
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockLightDetector", 1L, 0), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1),
                        OrePrefixes.stick.get(Materials.Iron)});
        // --- Advanced Card
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 28), tBitMask,
                new Object[]{"PVS", "RCV", "PVS", 'P', OrePrefixes.plate.get(Materials.Platinum), 'V', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'R', OrePrefixes.plate.get(Materials.RedAlloy), 'S', OrePrefixes.screw.get(Materials.Titanium),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23)});
        // --- Basic Card
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 25), tBitMask,
                new Object[]{"PVS", "RCV", "PVS", 'P', OrePrefixes.plate.get(Materials.Gold), 'V', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'R', OrePrefixes.plate.get(Materials.RedAlloy), 'S', OrePrefixes.screw.get(Materials.StainlessSteel),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23)});
        // --- Capacity Card
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 27), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 25),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1)});
        // --- Card Upgrade
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 54), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 28),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1)});
        // --- Crafting Card
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 53), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 25),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        "craftingWorkBench"});
        // --- Redstone Card
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 26), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 25),
                        "craftingRedstoneTorch", "craftingRedstoneTorch",
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23)});
        // --- Fuzzy Card
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 29), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 28),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23)});
        // --- Inverter Card
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 31), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 28),
                        GT_ModHandler.getModItem("IC2", "upgradeModule", 1L, 5),
                        GT_ModHandler.getModItem("IC2", "upgradeModule", 1L, 5),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23)});
        // --- Acceleration Card
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 30), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 28),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 7)});
        // --- Wireless Booster
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 42), tBitMask,
                new Object[]{"DGE", "PPP", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel), 'E', OrePrefixes.plate.get(Materials.EnderPearl),
                        'G', OrePrefixes.gem.get(Materials.CertusQuartz),
                        'D', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 8)});
        // --- ME Annihilation Plane
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 300), tBitMask,
                new Object[]{"DDD", "PAP", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'D', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 8),
                        'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 44)});
        // --- ME Formation Plane
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 320), tBitMask,
                new Object[]{"DDD", "PAP", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'D', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 8),
                        'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 43)});
        // --- ME Toggle Bus
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 80), tBitMask,
                new Object[]{" P ", "CLC", " P ", 'P', OrePrefixes.plate.get(Materials.RedAlloy),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        'L', GT_ModHandler.getModItem("minecraft", "lever", 1L, 0)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 80), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 100)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 100), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 80)});
        // --- Cable Anchor
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Iron)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Copper)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 3L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Bronze)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Tin)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Steel)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Aluminium)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Lead)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Nickel)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Silver)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 3L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Brass)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 3L, 120), tBitMask,
                new Object[]{ToolDictNames.craftingToolKnife, OrePrefixes.ingot.get(Materials.Invar)});
        // --- Matter Cannon
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolMassCannon", 1L, 0), tBitMask,
                new Object[]{"PPA", "BE ", "P  ", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'B', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38),
                        'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 43),
                        'E', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDenseEnergyCell", 1L, 0)});
        // --- Memory Card
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolMemoryCard", 1L, 0), tBitMask,
                new Object[]{"CPP", "GAG", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel), 'G', OrePrefixes.plate.get(Materials.Gold),
                        'A', OrePrefixes.plate.get(Materials.RedAlloy),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23)});
        // --- Color Applicator
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolColorApplicator", 1L, 0), tBitMask,
                new Object[]{"WAW", "TES", " I ", 'W', OrePrefixes.wireGt01.get(Materials.Aluminium), 'I', OrePrefixes.stick.get(Materials.Aluminium),
                        'S', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        'T', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35),
                        'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 43),
                        'E', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyCell", 1L, 0)});
        // --- Biometric Card
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolBiometricCard", 1L, 0), tBitMask,
                new Object[]{"CPP", "GAG", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel), 'G', OrePrefixes.plate.get(Materials.Gold),
                        'A', OrePrefixes.plate.get(Materials.RedAlloy),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 24)});
        // --- Portable Cell
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolPortableCell", 1L, 0), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1L, 0),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.1k", 1L, 0),
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDenseEnergyCell", 1L, 0)});
        // --- Slabs
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.ChiseledQuartzSlabBlock", 2L, 0), tBitMask,
                new Object[]{"sB", 'B', GT_ModHandler.getModItem("appliedenergistics2", "tile.ChiseledQuartzSlabBlock.double", 1L, 0)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.QuartzPillarSlabBlock", 2L, 0), tBitMask,
                new Object[]{"sB", 'B', GT_ModHandler.getModItem("appliedenergistics2", "tile.QuartzPillarSlabBlock.double", 1L, 0)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.QuartzSlabBlock", 2L, 0), tBitMask,
                new Object[]{"sB", 'B', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1L, 0)});
        // --- Spatial storage fix
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.2Cubed", 1L, 0), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.2Cubed", 1L, 0)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.16Cubed", 1L, 0), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.16Cubed", 1L, 0)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.128Cubed", 1L, 0), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.128Cubed", 1L, 0)});
        // --- Illuminated Panel
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 180), tBitMask,
                new Object[]{"hVd", "SGS", "DPD", 'P', OrePrefixes.plate.get(Materials.Glowstone),
                        'V', OrePrefixes.plate.get(Materials.VanadiumSteel), 'S', OrePrefixes.screw.get(Materials.CertusQuartz),
                        'G', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 1L), 'D', "dustFluix"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 180), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 160)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 180), tBitMask,
                new Object[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 200)});
        // --- Blank Pattern
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 52), tBitMask,
                new Object[]{"WWW", "PCP", "PQP", 'P', OrePrefixes.plate.get(Materials.Plastic),
                        'W', OrePrefixes.wireFine.get(Materials.Tin), 'Q', OrePrefixes.gem.get(Materials.CertusQuartz),
                        'C', OrePrefixes.circuit.get(Materials.Basic)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 52), tBitMask,
                new Object[]{"WWW", "PCP", "PQP", 'P', OrePrefixes.plate.get(Materials.Plastic),
                        'W', OrePrefixes.wireFine.get(Materials.Electrum), 'Q', OrePrefixes.gem.get(Materials.CertusQuartz),
                        'C', OrePrefixes.circuit.get(Materials.Good)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16L, 52), tBitMask,
                new Object[]{"WWW", "PCP", "PQP", 'P', OrePrefixes.plate.get(Materials.Plastic),
                        'W', OrePrefixes.wireFine.get(Materials.Platinum), 'Q', OrePrefixes.gem.get(Materials.CertusQuartz),
                        'C', OrePrefixes.circuit.get(Materials.Advanced)});
        // --- Energy Cell
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyCell", 1L, 0), tBitMask,
                new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel),
                        'B', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockFluix", 1L, 0),
                        'L', ItemList.Circuit_Parts_Crystal_Chip_Master});
        // --- Dense Energy Cell
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDenseEnergyCell", 1L, 0), tBitMask,
                new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefixes.plate.get(Materials.VanadiumGallium),
                        'B', GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyCell", 1L, 0),
                        'L', OrePrefixes.circuit.get(Materials.Elite)});
        // --- Annihilation Core
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 44), tBitMask,
                new Object[]{"SFh", "PCG", "SFd", 'P', OrePrefixes.plate.get(Materials.NetherQuartz),
                        'F', OrePrefixes.foil.get(Materials.VanadiumSteel), 'S', OrePrefixes.screw.get(Materials.Quartzite),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 7),
                        'G', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22)});
        // --- Formation Core
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 43), tBitMask,
                new Object[]{"SFh", "PCG", "SFd", 'P', OrePrefixes.plate.get(Materials.CertusQuartz),
                        'F', OrePrefixes.foil.get(Materials.VanadiumSteel), 'S', OrePrefixes.screw.get(Materials.Quartzite),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 7),
                        'G', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22)});
        // --- Wireless Access Point
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockWireless", 1L, 0), tBitMask,
                new Object[]{"SIS", "PCP", "dAw", 'P', OrePrefixes.plate.get(Materials.Vanadium), 'S', OrePrefixes.screw.get(Materials.Titanium),
                        'I', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 41),
                        'C', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                        'A', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16)});
        // --- Wireless Receiver
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 41), tBitMask,
                new Object[]{"EOE", "PIP", "PCP", 'P', OrePrefixes.plate.get(Materials.VanadiumSteel), 'I', OrePrefixes.stick.get(Materials.Grisium),
                        'C', OrePrefixes.circuit.get(Materials.Advanced),
                        'E', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 140),
                        'O', GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 9)});

    }

    public void alloySmelterRecipe() {
        // --- Quartz Glass
        GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("minecraft", "glass", 1L, 0),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 1L, 0), 100, 16);
        GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("minecraft", "glass", 1L, 0),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.NetherQuartz, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 1L, 0), 100, 16);
    }

    public void assemblerRecipe() {
        // --- Dark Illuminated Panel
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 180),
                GT_Utility.getIntegratedCircuit(2),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 200), 100, 4);
        // --- Level Emitter
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 23),
                GT_ModHandler.getModItem("minecraft", "redstone_torch", 1L, 0),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 280), 200, 30);
        // --- Dark Illuminated Panel
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 180),
                GT_Utility.getIntegratedCircuit(3),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 160), 100, 4);
        // --- Fluix Glass Cable
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2L, 140),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 8),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2L, 16), 200, 96);
        // --- Storage Cell Component - 1K
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{ItemList.Circuit_Chip_Ram.get(4), CoreItems.getRecipe(38, 4),
                        GT_ItemList.LogicProcessorItemGoldCore.get(1L), GT_Utility.getIntegratedCircuit(1)},
                null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35),
                120, 480);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{ItemList.Circuit_Chip_NAND.get(3), CoreItems.getRecipe(38, 4),
                        GT_ItemList.GoldCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35), 160,
                1920);
        // --- Storage Cell Component - 4K
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 35),
                        ItemList.Circuit_Chip_Ram.get(4), GT_ItemList.LogicProcessorItemGoldCore.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36), 160,
                480);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 35),
                        ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.GoldCoreChip.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36), 240,
                1920);
        // --- Storage Cell Component - 16K
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 36),
                        ItemList.Circuit_Chip_Ram.get(4),
                        GT_ItemList.EngineeringProcessorItemDiamondCore.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), 180,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 36),
                        ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.DiamondCoreChip.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), 260,
                7680);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_Ram.get(32),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 32L, 8),
                        ItemList.Circuit_Chip_SoC.get(1), GT_ItemList.DiamondCoreChip.get(1L),
                        GT_Utility.getIntegratedCircuit(2)}, Materials.Europium.getMolten(36),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), 30,
                30720);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Chip_NAND.get(16),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16L, 8),
                        ItemList.Circuit_Chip_SoC2.get(1), GT_ItemList.EngravedDiamondCrystalChip.get(1L),
                        GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), 30,
                122880);
        // --- Storage Cell Component - 64K
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 37),
                        ItemList.Circuit_Chip_Ram.get(4),
                        GT_ItemList.EngineeringProcessorItemDiamondCore.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), 200,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 37),
                        ItemList.Circuit_Chip_NAND.get(3), GT_ItemList.DiamondCoreChip.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), 280,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 37),
                        ItemList.Circuit_Chip_SoC.get(1), GT_ItemList.DiamondCoreChip.get(1L),
                        GT_Utility.getIntegratedCircuit(2)}, Materials.Europium.getMolten(36),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), 30,
                30720);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2L, 37),
                        ItemList.Circuit_Chip_SoC2.get(1), GT_ItemList.EngravedDiamondCrystalChip.get(1L),
                        GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), 30,
                122880);
        // --- 2 Spatial Component
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Niobium, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 9),
                        GT_ItemList.EngineeringProcessorSpatialPulsatingCore.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 32), 160,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Niobium, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 9),
                        GT_ItemList.PulsatingSpatialCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 32), 200,
                7680);
        // --- 16 Spatial Component
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 32),
                        GT_ItemList.EngineeringProcessorSpatialPulsatingCore.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33), 160,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 32),
                        GT_ItemList.PulsatingSpatialCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33), 200,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 32),
                        ItemList.Circuit_Chip_SoC.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L),
                        GT_Utility.getIntegratedCircuit(2)}, Materials.Europium.getMolten(36),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33), 30,
                30720);
        // --- 128 Spatial Component
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 33),
                        GT_ItemList.EngineeringProcessorSpatialPulsatingCore.get(1L),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34), 200,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 33),
                        GT_ItemList.PulsatingSpatialCoreChip.get(1L), GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34), 240,
                30720);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 33),
                        ItemList.Circuit_Chip_SoC.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L),
                        GT_Utility.getIntegratedCircuit(2)}, Materials.Americium.getMolten(36),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34), 30,
                122880);
        // --- Storage Cells
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39),
                GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemViewCell", 1L), 100, 120);

        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.1k", 1L), 100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.4k", 1L), 100,
                256);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.16k", 1L), 100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.64k", 1L), 100,
                1024);

        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 32),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.2Cubed", 1L),
                200, 4096);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.16Cubed", 1L),
                300, 7680);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.128Cubed", 1L),
                400, 16384);
        // --- CoCraftingUnit
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
                Materials.SolderingAlloy.getMolten(2304),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 1), 800,
                1024);

        // --- Crafting Monitor
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 400),
                Materials.SolderingAlloy.getMolten(576),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingMonitor", 1L, 0), 200,
                480);
        // --- CraftingStorages
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35),
                Materials.SolderingAlloy.getMolten(144),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 0), 1200,
                64);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36),
                Materials.SolderingAlloy.getMolten(576),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 1), 1200,
                256);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37),
                Materials.SolderingAlloy.getMolten(2304),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 2), 1200,
                1024);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38),
                Materials.SolderingAlloy.getMolten(9216),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 3), 1200,
                4096);
        // --- Annihilation Core
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.VanadiumSteel, 2),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 44), 200,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NetherQuartz, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Iridium, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_Utility.getIntegratedCircuit(2)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2L, 44), 100,
                30720);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.NetherQuartz, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Duranium, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_Utility.getIntegratedCircuit(3)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 44), 50,
                122880);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.NetherQuartz, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_Utility.getIntegratedCircuit(4)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16L, 44), 20,
                500000);
        // --- Formation Core
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.VanadiumSteel, 2),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_Utility.getIntegratedCircuit(1)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 43), 200,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.CertusQuartz, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Iridium, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_Utility.getIntegratedCircuit(2)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2L, 43), 100,
                30720);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.CertusQuartz, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Duranium, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_Utility.getIntegratedCircuit(3)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 43), 50,
                122880);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.CertusQuartz, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 22),
                        GT_Utility.getIntegratedCircuit(4)}, null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16L, 43), 20,
                500000);
    }

    public void autoclaveRecipe() {
        GT_Values.RA.addAutoclaveRecipe(CoreItems.getRecipe(38, 1), Materials.Water.getFluid(200L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), 7000,
                2000, 24);
        GT_Values.RA
                .addAutoclaveRecipe(CoreItems.getRecipe(38, 1), GT_ModHandler.getDistilledWater(1000),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), 9000,
                        1500, 24);
    }

    public void centrifugeRecipe() {
        // --- Tiny TNT
        GT_Values.RA.addCentrifugeRecipe(GT_ModHandler.getModItem("IC2", "blockITNT", 1L),
                GT_Values.NI, GT_Values.NF, GT_Values.NF,
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockTinyTNT", 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockTinyTNT", 1L),
                GT_Values.NI, GT_Values.NI, GT_Values.NI, GT_Values.NI,
                new int[]{10000, 10000}, 100, 16);
    }

    public void chemicalBathRecipe() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < Dyes.VALUES[i].getSizeOfFluidList(); j++) {
                GT_Values.RA.addChemicalBathRecipe(
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        Dyes.VALUES[i].getFluidDye(j, 36L),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 15 - i),
                        GT_Values.NI, GT_Values.NI, null, 20, 2);
                GT_Values.RA.addChemicalBathRecipe(
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                        Dyes.VALUES[i].getFluidDye(j, 36L),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 35 - i),
                        GT_Values.NI, GT_Values.NI, null, 20, 2);
                GT_Values.RA.addChemicalBathRecipe(
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 56),
                        Dyes.VALUES[i].getFluidDye(j, 36L),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 55 - i),
                        GT_Values.NI, GT_Values.NI, null, 20, 2);
                GT_Values.RA.addChemicalBathRecipe(
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 76),
                        Dyes.VALUES[i].getFluidDye(j, 36L),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 75 - i),
                        GT_Values.NI, GT_Values.NI, null, 20, 2);
                GT_Values.RA.addChemicalBathRecipe(
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 6),
                        Dyes.VALUES[i].getFluidDye(j, 36L),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemPaintBall", 1L, 15 - i),
                        GT_Values.NI, GT_Values.NI, null, 20, 2);
            }
            GT_Values.RA.addChemicalBathRecipe(
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 15 - i),
                    Materials.Chlorine.getGas(50L),
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                    GT_Values.NI, GT_Values.NI, null, 20, 2);
            GT_Values.RA.addChemicalBathRecipe(
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 35 - i),
                    Materials.Chlorine.getGas(50L),
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                    GT_Values.NI, GT_Values.NI, null, 20, 2);
            GT_Values.RA.addChemicalBathRecipe(
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 55 - i),
                    Materials.Chlorine.getGas(50L),
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 56),
                    GT_Values.NI, GT_Values.NI, null, 20, 2);
            GT_Values.RA.addChemicalBathRecipe(
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 75 - i),
                    Materials.Chlorine.getGas(50L),
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 76),
                    GT_Values.NI, GT_Values.NI, null, 20, 2);
            GT_Values.RA.addChemicalBathRecipe(
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemPaintBall", 1L, 15 - i),
                    Materials.Chlorine.getGas(50L),
                    GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 6),
                    GT_Values.NI, GT_Values.NI, null, 20, 2);
        }
    }

    public void chemicalReactorRecipe() {
        // --- Seeds
        GT_Values.RA
                .addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 1L),
                        GT_ModHandler.getModItem("minecraft", "sand", 1L, 0), null, null,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemCrystalSeed", 2L, 0), 100);
        GT_Values.RA
                .addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 1L),
                        GT_ModHandler.getModItem("minecraft", "sand", 1L, 1), null, null,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemCrystalSeed", 2L, 0), 100);
        GT_Values.RA
                .addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.NetherQuartz, 1L),
                        GT_ModHandler.getModItem("minecraft", "sand", 1L, 0), null, null,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemCrystalSeed", 2L, 600), 100);
        GT_Values.RA
                .addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.NetherQuartz, 1L),
                        GT_ModHandler.getModItem("minecraft", "sand", 1L, 1), null, null,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemCrystalSeed", 2L, 600), 100);
        GT_Values.RA
                .addChemicalRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 8),
                        GT_ModHandler.getModItem("minecraft", "sand", 1L, 0), null, null,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemCrystalSeed", 2L, 1200), 100);
        GT_Values.RA
                .addChemicalRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 8),
                        GT_ModHandler.getModItem("minecraft", "sand", 1L, 1), null, null,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemCrystalSeed", 2L, 1200), 100);
        GT_Values.RA.addChemicalRecipe(CoreItems.getRecipe(38, 3), Materials.Sodium.getDust(1),
                Materials.Water.getFluid(1000L), null,
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 1), 500);

    }

    public void compressorRecipe() {
        GT_Values.RA
                .addCompressorRecipe(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 7),
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockFluix", 1L), 200, 8);
    }

    public void cuttingSawRecipe() {
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1L, 0),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 4L), GT_Values.NI, 800, 30);
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("minecraft", "quartz_block", 1L, 0),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 4L), GT_Values.NI, 800, 30);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 6L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Aluminium, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Lead, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Nickel, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Brass, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 6L, 120), GT_Values.NI, 80, 8);
        GT_Values.RA.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Invar, 1L),
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 6L, 120), GT_Values.NI, 80, 8);
    }

    public void printerRecipe() {
        //ME Controller
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyAcceptor", 1, 0),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 24)/*Diamond*/,
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 76),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockController", 1, 0), null, 30 * 20,
                1920);

        //ME Chest
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 380),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 16),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 2),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 2),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 22)/*Gold*/,
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Vanadium, 12)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1, 0), null, 20 * 20,
                480);

        //ME Drive
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1, 0),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.VibrantAlloy, 2),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24)/*Diamond*/,
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 3),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Vanadium, 9)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDrive", 1, 0), null, 30 * 20,
                1920);

        //CraftingUnit
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 1),
                        /*GT_ModHandler.getModItem("OpenComputers", "item", 1, 29), */
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 23)
/*Processor*/, GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 56),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 12)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1, 0), null,
                30 * 20, 1920);

        //Molecular Assembler
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Machine_MV_Assembler.get(1L),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44)/*Ani*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 23)
/*Processor*/, GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Vanadium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.VanadiumSteel, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 12, 0)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockMolecularAssembler", 1, 0), null,
                30 * 20, 1920);

        //ME Quantum Ring
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyCell", 1, 0),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24)/*Diamond*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 76),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.MaragingSteel300, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Vanadium, 8)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumRing", 1, 0), null,
                20 * 20, 7680);

        //ME Quantum Link Chamber
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                        ItemList.Field_Generator_EV.get(1L),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 7),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 9),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.MaragingSteel300, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzGlass", 12, 0)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 1, 0), null,
                40 * 20, 7680);

        //Spatial Pylon
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumRing", 1, 0),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 7),
                        GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Platinum, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 16),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Palladium, 16)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 2, 0), null,
                30 * 20, 7680);

        //Spatial IO Port
        GT_Values.RA.addBasicLineRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockIOPort", 1, 0),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24)/*Diamond*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 4, 0),
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 4, 0),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 7),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.NiobiumTitanium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 8),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Vanadium, 16)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialIOPort", 1, 0), null,
                40 * 20, 7680);

        //ME Interface
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Robot_Arm_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 44)/*Ani*/,
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1, 0), null, 100,
                120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Robot_Arm_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 2),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44)/*Ani*/,
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 2, 0), null, 200,
                480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Robot_Arm_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 3),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 44)/*Ani*/,
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 4, 0), null, 300,
                1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Robot_Arm_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44)/*Ani*/,
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 8, 0), null, 400,
                7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Robot_Arm_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 5),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 44)/*Ani*/,
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 12, 0), null, 500,
                30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Robot_Arm_LuV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 6),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44)/*Ani*/,
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 16, 0), null, 600,
                122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Robot_Arm_ZPM.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 7),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 44)/*Ani*/,
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 24, 0), null, 700,
                500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Robot_Arm_UV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43)/*Form*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44)/*Ani*/,
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 32, 0), null, 800,
                2000000);

        //Enegery Acceptor
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyCell", 1, 0),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 2),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 7)/*crystal*/,
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 8)
                }, GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyAcceptor", 1, 0), null, 200,
                480);

        //New Pattern Terminal
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 52),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 340),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 24)/*Diamond*/
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 500), null, 400,
                1920);

        //ME Export Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 260), null, 200,
                120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2, 260), null, 300,
                480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 260), null, 400,
                1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 260), null, 500,
                7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 10, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 12, 260), null, 600,
                30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_LuV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 12, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 16, 260), null, 700,
                122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_ZPM.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 14, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 24, 260), null, 800,
                500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_UV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 32, 260), null, 900,
                2000000);

        //ME Import Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 240), null, 200,
                120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2, 240), null, 300,
                480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 240), null, 400,
                1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 240), null, 500,
                7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 10, 44),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 12, 240), null, 600,
                30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_LuV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 12, 44),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 16, 240), null, 700,
                122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_ZPM.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 14, 44),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 24, 240), null, 800,
                500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Conveyor_Module_UV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 16, 44),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 32, 240), null, 900,
                2000000);

        //Storage Bus
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Hatch_Input_Bus_LV.get(1L), ItemList.Hatch_Output_Bus_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 220), null, 200,
                120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Hatch_Input_Bus_MV.get(1L), ItemList.Hatch_Output_Bus_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2, 220), null, 300,
                480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Hatch_Input_Bus_HV.get(1L), ItemList.Hatch_Output_Bus_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 220), null, 400,
                1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Hatch_Input_Bus_EV.get(1L), ItemList.Hatch_Output_Bus_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 220), null, 500,
                7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Hatch_Input_Bus_IV.get(1L), ItemList.Hatch_Output_Bus_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 12, 220), null, 600,
                30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Hatch_Input_Bus_LuV.get(1L), ItemList.Hatch_Output_Bus_LuV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 16, 220), null, 700,
                122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Hatch_Input_Bus_ZPM.get(1L), ItemList.Hatch_Output_Bus_ZPM.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 24, 220), null, 800,
                500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Hatch_Input_Bus_UV.get(1L), ItemList.Hatch_Output_Bus_UV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 4),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 32, 220), null, 900,
                2000000);

        //P2P Tunnel
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Emitter_LV.get(1L), ItemList.Sensor_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 2)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 2, 460), null, 200,
                120);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Emitter_MV.get(1L), ItemList.Sensor_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 4)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 4, 460), null, 300,
                480);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Emitter_HV.get(1L), ItemList.Sensor_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSLA, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 6)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 8, 460), null, 400,
                1920);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Emitter_EV.get(1L), ItemList.Sensor_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSG, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 8)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 16, 460), null, 500,
                7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Emitter_IV.get(1L), ItemList.Sensor_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyC276, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 5, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 12)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 24, 460), null, 600,
                30720);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Emitter_LuV.get(1L), ItemList.Sensor_LuV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HastelloyN, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 6, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 16)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 32, 460), null, 700,
                122880);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Emitter_ZPM.get(1L), ItemList.Sensor_ZPM.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lafium, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 7, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 20)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 48, 460), null, 800,
                500000);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[]{
                        ItemList.Emitter_UV.get(1L), ItemList.Sensor_UV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CinobiteA243, 8),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 44),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 8, 43),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Vanadium, 24)
                }, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 64, 460), null, 900,
                2000000);
    }

    public void pulveriserRecipe() {
        GT_Values.RA.addPulveriserRecipe(
                GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockFluix", 1L),
                new ItemStack[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 4L, 7)},
                new int[]{10000}, 300, 2);
    }

    public void wireAssemblerRecipe() {
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        GT_Utility.getIntegratedCircuit(24)},
                Materials.Silicone.getMolten(72), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                100, 8, false);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        GT_Utility.getIntegratedCircuit(24)},
                Materials.StyreneButadieneRubber.getMolten(108), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                100, 8, false);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        GT_Utility.getIntegratedCircuit(24)},
                Materials.Rubber.getMolten(144), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                100, 8, false);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Polydimethylsiloxane, 1L)},
                Materials.StyreneButadieneRubber.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                100, 8, false);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.PolyvinylChloride, 1L)},
                Materials.StyreneButadieneRubber.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                100, 8, false);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Polydimethylsiloxane, 1L)},
                Materials.Silicone.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                100, 8, false);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.PolyvinylChloride, 1L)},
                Materials.Silicone.getMolten(36), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36),
                100, 8, false);
        // --- Dense Cable
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 56),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Vanadium, 2L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 2L)},
                null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 76),
                200, 480, false);
        // --- Smart Cable
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicon, 2L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 1L),
                        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 36)},
                null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 56),
                100, 120, false);
    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
        alloySmelterRecipe();
        assemblerRecipe();
        autoclaveRecipe();
        centrifugeRecipe();
        chemicalBathRecipe();
        chemicalReactorRecipe();
        compressorRecipe();
        cuttingSawRecipe();
        printerRecipe();
        pulveriserRecipe();
        wireAssemblerRecipe();
    }
}
