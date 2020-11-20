package com.impact.recipes;

import com.impact.mods.GregTech.GT_ItemList;
import com.jaquadro.minecraft.storagedrawers.core.ModBlocks;
import com.jaquadro.minecraft.storagedrawers.core.ModItems;
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

public class StorageDrawersRecipe implements Runnable{

    private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE/* | GT_ModHandler.RecipeBits.REVERSIBLE*/;

    public void handRecipe() {

        for (int i = 0; i < 6; i++) {
            // --- Drawers 1
            GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.fullDrawers1, 1, i), tBitMask, new Object[]{"PPP", "SCS", "PPP", 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, i), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
            // --- Drawers 1x2
            GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.fullDrawers2, 2, i), tBitMask, new Object[]{"PCP", "SPS", "PCP", 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, i), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
            // --- Drawers 2x2
            GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.fullDrawers4, 4, i), tBitMask, new Object[]{"CPC", "SPS", "CPC", 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, i), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
            // --- Compact Drawers 1x2
            GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.halfDrawers2, 2, i), tBitMask, new Object[]{"PPP", "CSC", "PPP", 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, i), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
            // --- Compact Drawers 2x2
            GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.halfDrawers4, 4, i), tBitMask, new Object[]{"PCP", "CSC", "PCP", 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, i), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
            // --- Drawers Trim
            GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.trim, 1, i), tBitMask, new Object[]{"SPS", "PPP", "SPS", 'P', GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, i), 'S', OrePrefixes.screw.get(Materials.Wood)});
        }
        // --- Controller Slave
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.controllerSlave), tBitMask, new Object[]{"PCP", "ODO", "PAP", 'P', OrePrefixes.plate.get(Materials.Birmabright), 'A', OrePrefixes.plate.get(Materials.Diamond), 'C', OrePrefixes.circuit.get(Materials.Good), 'O', GT_ModHandler.getModItem("ProjRed|Integration", "projectred.integration.gate", 1L, 26), 'D', "drawerBasic"});
        // --- Compacting Drawers
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.compDrawers), tBitMask, new Object[]{"PCP", "ODO", "PAP", 'P', OrePrefixes.plate.get(Materials.Birmabright), 'A', OrePrefixes.plate.get(Materials.Diamond), 'C', OrePrefixes.circuit.get(Materials.Good), 'O', ItemList.Electric_Piston_MV, 'D', "drawerBasic"});
        // --- Controller
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.controller), tBitMask, new Object[]{"PCP", "ODR", "PAP", 'P', OrePrefixes.plate.get(Materials.Birmabright), 'A', OrePrefixes.plate.get(Materials.Diamond), 'C', OrePrefixes.circuit.get(Materials.Good), 'O', ItemList.Conveyor_Module_MV, 'R', ItemList.Robot_Arm_MV, 'D', "drawerBasic"});
        // --- Upgrade Iron
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgrade, 1, 2), tBitMask, new Object[]{"PTP", "TPT", "PSP", 'P', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Iron), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Upgrade Gold
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgrade, 1, 3), tBitMask, new Object[]{"PTP", "TRT", "PSP", 'P', OrePrefixes.plate.get(Materials.Gold), 'R', OrePrefixes.plate.get(Materials.Steel), 'S', OrePrefixes.stick.get(Materials.Gold), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Upgrade Obsidian
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgrade, 1, 4), tBitMask, new Object[]{"PTP", "TRT", "PSP", 'P', OrePrefixes.plate.get(Materials.Obsidian), 'R', OrePrefixes.plate.get(Materials.Aluminium), 'S', OrePrefixes.stick.get(Materials.Obsidian), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Upgrade Diamond
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgrade, 1, 5), tBitMask, new Object[]{"PTP", "TRT", "PSP", 'P', OrePrefixes.plate.get(Materials.Diamond), 'R', OrePrefixes.plate.get(Materials.StainlessSteel), 'S', OrePrefixes.stick.get(Materials.Diamond), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Upgrade Emerald
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgrade, 1, 6), tBitMask, new Object[]{"PTP", "TRT", "PSP", 'P', OrePrefixes.plate.get(Materials.Emerald), 'R', OrePrefixes.plate.get(Materials.Titanium), 'S', OrePrefixes.stick.get(Materials.Emerald), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Status Upgrade 1
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgradeStatus, 1, 1), tBitMask, new Object[]{"IRI", "RTR", "PRP", 'P', OrePrefixes.plate.get(Materials.Redstone), 'R', GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0), 'I', new ItemStack(Blocks.redstone_torch)});
        // --- Status Upgrade 2
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgradeStatus, 1, 2), tBitMask, new Object[]{"IRI", "RTR", "PRP", 'P', OrePrefixes.plate.get(Materials.Redstone), 'R', GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0), 'I', GT_ModHandler.getModItem("ProjRed|Integration", "projectred.integration.gate", 1L, 26)});
        // --- Redstone Upgrades
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgradeRedstone, 1, 0), tBitMask, new Object[]{"PSP", "STS", "PRP", 'P', OrePrefixes.plate.get(Materials.Redstone), 'S', OrePrefixes.stick.get(Materials.Wood), 'R', GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Redstone Upgrades Max
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgradeRedstone, 1, 1), tBitMask, new Object[]{"PPP", "STS", "SRS", 'P', OrePrefixes.plate.get(Materials.Redstone), 'S', OrePrefixes.stick.get(Materials.Wood), 'R', GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Redstone Upgrades Min
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgradeRedstone, 1, 2), tBitMask, new Object[]{"SRS", "STS", "PPP", 'P', OrePrefixes.plate.get(Materials.Redstone), 'S', OrePrefixes.stick.get(Materials.Wood), 'R', GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Void Upgrade
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgradeVoid, 1, 0), tBitMask, new Object[]{"STS", "PVP", "SRS", 'P', OrePrefixes.itemCasing.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.Steel), 'V', GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1), 'R', GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Storage Key
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgradeLock, 1, 0), tBitMask, new Object[]{" BP", "TPS", "Gs ", 'B', OrePrefixes.bolt.get(Materials.Gold), 'G', OrePrefixes.plate.get(Materials.Gold), 'S', OrePrefixes.stick.get(Materials.Gold), 'P', OrePrefixes.plate.get(Materials.Steel), 'T', new ItemStack(ModItems.upgradeTemplate, 1, 0)});
        // --- Framing Table
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.framingTable, 1, 0), tBitMask, new Object[]{"TTT", "FSF", "FdF", 'S', OrePrefixes.screw.get(Materials.Steel), 'F', new ItemStack(Blocks.fence), 'T', new ItemStack(ModBlocks.trim, GT_Values.W)});
        // --- Upgrade Template
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModItems.upgradeTemplate, 1, 0), tBitMask, new Object[]{"SPS", "SDS", " d ", 'S', OrePrefixes.screw.get(Materials.Steel), 'P', new ItemStack(Blocks.piston), 'D', "drawerBasic"});
        // --- Framed Drawers 1
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.fullCustom1, 1, 0), tBitMask, new Object[]{"PPP", "SCS", "PPP", 'P', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
        // --- Framed Drawers 1x2
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.fullCustom2, 2, 0), tBitMask, new Object[]{"PCP", "SPS", "PCP", 'P', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
        // --- Framed Drawers 2x2
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.fullCustom4, 4, 0), tBitMask, new Object[]{"CPC", "SPS", "CPC", 'P', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
        // --- Framed Compact Drawers 1x2
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.halfCustom2, 2, 0), tBitMask, new Object[]{"PPP", "CSC", "PPP", 'P', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
        // --- Framed Compact Drawers 2x2
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.halfCustom4, 4, 0), tBitMask, new Object[]{"PCP", "CSC", "PCP", 'P', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1, 0), 'S', OrePrefixes.screw.get(Materials.Wood), 'C', "chestWood"});
        // --- Framed Drawers Trim
        GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.trimCustom, 1, 0), tBitMask, new Object[]{"SPS", "PPP", "SPS", 'P', GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1, 0), 'S', OrePrefixes.screw.get(Materials.Wood)});
    }

    public void assemblerRecipe() {

        for (int i = 0; i < 6; i++) {
            // --- Drawers 1
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.trim, 1, i), new ItemStack(Blocks.chest, 1), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(ModBlocks.fullDrawers1, 1, i), 100, 8);
            // --- Drawers 1x2
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.trim, 1, i), new ItemStack(Blocks.chest, 2), GT_Utility.getIntegratedCircuit(2)}, null, new ItemStack(ModBlocks.fullDrawers2, 2, i), 100, 8);
            // --- Drawers 2x2
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.trim, 1, i), new ItemStack(Blocks.chest, 4), GT_Utility.getIntegratedCircuit(4)}, null, new ItemStack(ModBlocks.fullDrawers4, 4, i), 100, 8);
            // --- Compact Drawers 1x2
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.trim, 1, i), new ItemStack(Blocks.chest, 2), GT_Utility.getIntegratedCircuit(3)}, null, new ItemStack(ModBlocks.halfDrawers2, 2, i), 100, 8);
            // --- Compact Drawers 2x2
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.trim, 1, i), new ItemStack(Blocks.chest, 4), GT_Utility.getIntegratedCircuit(6)}, null, new ItemStack(ModBlocks.halfDrawers4, 4, i), 100, 8);
            // --- Drawers Trim
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1), GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, i), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(ModBlocks.trim, 1, i), 100, 8);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Iron, 1), GT_ModHandler.getModItem("minecraft", "wooden_slab", 2L, i), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(ModBlocks.trim, 2, i), 100, 10);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Bronze, 1), GT_ModHandler.getModItem("minecraft", "wooden_slab", 2L, i), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(ModBlocks.trim, 2, i), 100, 10);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1), GT_ModHandler.getModItem("minecraft", "wooden_slab", 4L, i), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(ModBlocks.trim, 4, i), 100, 12);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 1), GT_ModHandler.getModItem("minecraft", "wooden_slab", 8L, i), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(ModBlocks.trim, 8, i), 100, 16);
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1), GT_ModHandler.getModItem("minecraft", "wooden_slab", 16L, i), GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(ModBlocks.trim, 16, i), 100, 24);
        }
        // --- Upgrade Template
        GT_Values.RA.addAssemblerRecipe(new ItemStack(ModBlocks.trim, 1, GT_Values.W), new ItemStack(Blocks.piston), new ItemStack(ModItems.upgradeTemplate, 1, 0), 600, 16);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(ModBlocks.trim, 2, GT_Values.W), GT_ItemList.ULVPiston.get(1L), new ItemStack(ModItems.upgradeTemplate, 2, 0), 400, 20);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(ModBlocks.trim, 4, GT_Values.W), ItemList.Electric_Piston_LV.get(1L), new ItemStack(ModItems.upgradeTemplate, 4, 0), 200, 24);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(ModBlocks.trim, 8, GT_Values.W), ItemList.Electric_Piston_MV.get(1L), new ItemStack(ModItems.upgradeTemplate, 8, 0), 100, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(ModBlocks.trim, 16, GT_Values.W), ItemList.Electric_Piston_HV.get(1L), new ItemStack(ModItems.upgradeTemplate, 16, 0), 50, 30);
        // --- Concealment Key
        GT_Values.RA.addAssemblerRecipe(new ItemStack(ModItems.upgradeTemplate, 1, 0), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 1), new ItemStack(ModItems.shroudKey, 1, 0), 100, 30);
        // --- Storage Personal Key
        GT_Values.RA.addAssemblerRecipe(new ItemStack(ModItems.upgradeTemplate, 1, 0), new ItemStack(Items.name_tag), new ItemStack(ModItems.personalKey), 100, 30);
        // --- Storage Personal Key
        GT_Values.RA.addAssemblerRecipe(new ItemStack(Items.paper), GT_Utility.getIntegratedCircuit(1), Materials.Glue.getFluid(144L), new ItemStack(ModItems.tape), 200, 30);
        // --- Void Upgrade
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModItems.upgradeTemplate, 1, 0), GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1)}, Materials.Redstone.getMolten(144), new ItemStack(ModItems.upgradeVoid, 1, 0), 400, 30);
    }

    @Override
    public void run() {
        handRecipe();
        assemblerRecipe();
    }
}
