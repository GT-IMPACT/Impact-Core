package com.impact.recipes;

import com.impact.mods.GregTech.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import mcp.mobius.betterbarrels.BetterBarrels;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

public class JabbaRecipe implements Runnable {

    private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE/* | GT_ModHandler.RecipeBits.REVERSIBLE*/;

    public void delRecipe() {
        removeRecipeByOutput(new ItemStack(BetterBarrels.itemUpgradeCore, 1, GT_Values.W));
        removeRecipeByOutput(new ItemStack(BetterBarrels.itemHammer, 1, 0));
        removeRecipeByOutput(new ItemStack(BetterBarrels.itemMover, 1, 0));
        removeRecipeByOutput(new ItemStack(BetterBarrels.itemMoverDiamond, 1, 0));
        removeRecipeByOutput(new ItemStack(BetterBarrels.itemTuningFork, 1, 0));
        removeRecipeByOutput(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, GT_Values.W));
    }

    public void handRecipe() {
        // --- Void Upgrade
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 7), tBitMask, new Object[]{"STS", "PVP", "SRS", 'P', OrePrefixes.itemCasing.get(Materials.Steel), 'S', OrePrefixes.screw.get(Materials.Steel), 'V', GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1), 'R', GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1), 'T', new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0)});
        // --- Barrel Hammer
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemHammer, 1, 0), tBitMask, new Object[]{"IPI", "STS", "hTd", 'I', OrePrefixes.ingot.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.screw.get(Materials.Iron), 'T', OrePrefixes.stick.get(Materials.Iron)});
        // --- Dolly
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemMover, 1, 0), tBitMask, new Object[]{"TTR", "TwR", "OPO", 'R', OrePrefixes.round.get(Materials.Rubber), 'P', OrePrefixes.plate.get(Materials.Steel), 'T', OrePrefixes.stick.get(Materials.Iron), 'O', ItemList.Component_Minecart_Wheels_Iron});
        // --- Storage Upgrade
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0), tBitMask, new Object[]{"SPS", "SDS", " d ", 'S', OrePrefixes.screw.get(Materials.Steel), 'P', new ItemStack(Blocks.piston), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK I
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 0), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Wood), 'P', OrePrefixes.plate.get(Materials.Wood), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK II
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 1), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Bronze), 'P', OrePrefixes.plate.get(Materials.Bronze), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK III
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 2), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.WroughtIron), 'P', OrePrefixes.plate.get(Materials.WroughtIron), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK IV
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 3), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Cobalt), 'P', OrePrefixes.plate.get(Materials.Cobalt), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK V
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 4), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Steel), 'P', OrePrefixes.plate.get(Materials.Steel), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK VI
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 5), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Obsidian), 'P', OrePrefixes.plate.get(Materials.Obsidian), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK VII
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 6), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Diamond), 'P', OrePrefixes.plate.get(Materials.Diamond), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK VIII
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 7), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Aluminium), 'P', OrePrefixes.plate.get(Materials.Aluminium), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK IX
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 8), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Titanium), 'P', OrePrefixes.plate.get(Materials.Titanium), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK X
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 9), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.TungstenSteel), 'P', OrePrefixes.plate.get(Materials.TungstenSteel), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Structural Upgrade MK XI
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeStructural, 1, 10), tBitMask, new Object[]{"SPS", "PDP", "SPS", 'S', OrePrefixes.stick.get(Materials.Iridium), 'P', OrePrefixes.plate.get(Materials.Iridium), 'D', new ItemStack(BetterBarrels.blockBarrel, 1, 0)});
        // --- Redstone Upgrade
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 2), tBitMask, new Object[]{"PTP", "TBT", "PTP", 'P', OrePrefixes.plate.get(Materials.RedAlloy), 'B', OrePrefixes.block.get(Materials.Redstone), 'T', new ItemStack(Blocks.piston)});
        // --- Hopper Upgrade
        GT_ModHandler.addCraftingRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 3), tBitMask, new Object[]{"PTP", "TBT", "PTP", 'P', OrePrefixes.plate.get(Materials.Iron), 'B', new ItemStack(Blocks.hopper), 'T', new ItemStack(Blocks.piston)});
    }

    public void assemblerRecipe() {
        // --- Barrel
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1), GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 1L, 0), new ItemStack(BetterBarrels.blockBarrel, 1, 0), 100, 8);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Iron, 1), GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 2L, 0), new ItemStack(BetterBarrels.blockBarrel, 2, 0), 100, 10);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Bronze, 1), GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 2L, 0), new ItemStack(BetterBarrels.blockBarrel, 2, 0), 100, 10);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1), GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 4L, 0), new ItemStack(BetterBarrels.blockBarrel, 4, 0), 100, 12);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 1), GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 8L, 0), new ItemStack(BetterBarrels.blockBarrel, 8, 0), 100, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1), GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 16L, 0), new ItemStack(BetterBarrels.blockBarrel, 16, 0), 100, 24);
        // --- Storage Upgrade
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.blockBarrel, 1, 0), new ItemStack(Blocks.piston), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0), 600, 16);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.blockBarrel, 2, 0), GT_ItemList.ULVPiston.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 2, 0), 400, 20);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.blockBarrel, 4, 0), ItemList.Electric_Piston_LV.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 4, 0), 200, 24);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.blockBarrel, 8, 0), ItemList.Electric_Piston_MV.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 8, 0), 100, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.blockBarrel, 16, 0), ItemList.Electric_Piston_HV.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 16, 0), 50, 30);
        // --- Storage Upgrade 3x
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 3, 0), GT_Utility.getIntegratedCircuit(3), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 4), 600, 24);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0), GT_ItemList.ULVPiston.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 4), 300, 24);
        // --- Storage Upgrade 9x
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 3, 4), GT_Utility.getIntegratedCircuit(3), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 5), 600, 30);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0), ItemList.Electric_Piston_LV.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 5), 300, 30);
        // --- Storage Upgrade 27x
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 3, 5), GT_Utility.getIntegratedCircuit(3), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 6), 600, 64);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0), ItemList.Electric_Piston_MV.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 6), 300, 64);
        // --- Storage Upgrade 81x
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 3, 6), GT_Utility.getIntegratedCircuit(3), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 8), 600, 96);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0), ItemList.Electric_Piston_HV.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 8), 300, 96);
        // --- Storage Upgrade 243x
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 3, 8), GT_Utility.getIntegratedCircuit(3), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 9), 600, 120);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0), ItemList.Electric_Piston_EV.get(1L), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 9), 300, 120);
        // --- Void Upgrade
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(BetterBarrels.itemUpgradeCore, 1, 0), GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1)}, Materials.Redstone.getMolten(144), new ItemStack(BetterBarrels.itemUpgradeCore, 1, 7), 400, 30);
    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
        assemblerRecipe();
    }
}
