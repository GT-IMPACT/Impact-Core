package com.impact.recipes.machines;

import static com.impact.common.item.Core_List_Items.AcaciaScheme;
import static com.impact.common.item.Core_List_Items.BarnardaCScheme;
import static com.impact.common.item.Core_List_Items.BirchScheme;
import static com.impact.common.item.Core_List_Items.DarkOakScheme;
import static com.impact.common.item.Core_List_Items.JungleScheme;
import static com.impact.common.item.Core_List_Items.OakScheme;
import static com.impact.common.item.Core_List_Items.RubberScheme;
import static com.impact.common.item.Core_List_Items.SpruceScheme;

import com.impact.common.item.Core_Items2;
import com.impact.mods.gregtech.GT_ItemList;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class CircuitAssemblerRecipe implements Runnable {

  final Core_Items2 CoreItems2 = Core_Items2.getInstance();

  @Override
  public void run() {

/** ================================= start GT =================================*/

    for (Materials tMat : Materials.values()) {
      if (tMat.mStandardMoltenFluid != null && tMat.contains(SubTag.SOLDERING_MATERIAL) && !(
          GregTech_API.mUseOnlyGoodSolderingMaterials && !tMat
              .contains(SubTag.SOLDERING_MATERIAL_GOOD))) {
        int tMultiplier = tMat.contains(SubTag.SOLDERING_MATERIAL_GOOD) ? 1
            : tMat.contains(SubTag.SOLDERING_MATERIAL_BAD) ? 4 : 2;

        //Rocket Circuits
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1L, 0),
                GT_ItemList.spacebox1.get(1)}, tMat.getMolten(288L * tMultiplier / 2L),
            CoreItems2.getRecipe(0, 1), 1000, 256);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L),
                GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 3),
                GT_ItemList.spacebox2.get(1)}, tMat.getMolten(576L * tMultiplier / 2L),
            CoreItems2.getRecipe(1, 1), 1600, 480);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1L),
                GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 0),
                GT_ItemList.spacebox3.get(2)}, tMat.getMolten(1152L * tMultiplier / 2L),
            CoreItems2.getRecipe(2, 1), 2200, 1920);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L),
                CoreItems2.getRecipe(11, 1), GT_ItemList.spacebox4.get(2)},
            tMat.getMolten(2304L * tMultiplier / 2L), CoreItems2.getRecipe(3, 1), 2800, 4096);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 1L),
                CoreItems2.getRecipe(12, 1), GT_ItemList.spacebox5.get(4)},
            tMat.getMolten(4608L * tMultiplier / 2L), CoreItems2.getRecipe(4, 1), 3400, 16384);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 1L),
                CoreItems2.getRecipe(13, 1), GT_ItemList.spacebox6.get(4)},
            tMat.getMolten(9216L * tMultiplier / 2L), CoreItems2.getRecipe(5, 1), 4000, 65536);
        GT_Values.RA.addCircuitAssemblerRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 1L),
                CoreItems2.getRecipe(14, 1), GT_ItemList.spacebox7.get(8)},
            tMat.getMolten(18432L * tMultiplier / 2L), CoreItems2.getRecipe(6, 1), 4600, 262144);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 1L),
                CoreItems2.getRecipe(15, 1), GT_ItemList.spacebox8.get(8)},
            tMat.getMolten(36864L * tMultiplier / 2L), CoreItems2.getRecipe(7, 1), 5200, 1048576);

        //Farm Circuits
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                GT_ModHandler.getModItem("minecraft", "sapling", 64L, 0)},
            tMat.getMolten(144L * tMultiplier / 2L), CoreItems2.getRecipe(OakScheme.getMetaID(), 1),
            200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                GT_ModHandler.getModItem("minecraft", "sapling", 64L, 1)},
            tMat.getMolten(144L * tMultiplier / 2L),
            CoreItems2.getRecipe(SpruceScheme.getMetaID(), 1), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                GT_ModHandler.getModItem("minecraft", "sapling", 64L, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            CoreItems2.getRecipe(BirchScheme.getMetaID(), 1), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                GT_ModHandler.getModItem("minecraft", "sapling", 64L, 3)},
            tMat.getMolten(144L * tMultiplier / 2L),
            CoreItems2.getRecipe(JungleScheme.getMetaID(), 1), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                GT_ModHandler.getModItem("minecraft", "sapling", 64L, 4)},
            tMat.getMolten(144L * tMultiplier / 2L),
            CoreItems2.getRecipe(AcaciaScheme.getMetaID(), 1), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                GT_ModHandler.getModItem("minecraft", "sapling", 64L, 5)},
            tMat.getMolten(144L * tMultiplier / 2L),
            CoreItems2.getRecipe(DarkOakScheme.getMetaID(), 1), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                GT_ModHandler.getModItem("IC2", "blockRubSapling", 64L, 0)},
            tMat.getMolten(144L * tMultiplier / 2L),
            CoreItems2.getRecipe(RubberScheme.getMetaID(), 1), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 1L),
                GT_ModHandler.getModItem("GalaxySpace", "barnardaCsapling", 64L, 1)},
            tMat.getMolten(1152L * tMultiplier / 2L),
            CoreItems2.getRecipe(BarnardaCScheme.getMetaID(), 1), 1000, 8000000);

        //LP Upgrades
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Tin, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 6), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Iron, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 7), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 20), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 16), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 21), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.RedAlloy, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 23), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 24), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Lapis, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 25), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 30), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Bronze, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 41), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silver, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 42), 120, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.RedAlloy, 1),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 2)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, 26), 120, 30);

        //ALL Circuits
        //Forestry Chipsets
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 2),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Tin, 2),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tin, 1),
                GT_Utility.getIntegratedCircuit(1)}, tMat.getMolten(1152L * tMultiplier / 2L),
            GT_ModHandler.getModItem("Forestry", "chipsets", 1L, 0), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Bronze, 2),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Bronze, 1),
                GT_Utility.getIntegratedCircuit(1)}, tMat.getMolten(1152L * tMultiplier / 2L),
            GT_ModHandler.getModItem("Forestry", "chipsets", 1L, 1), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 2),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Steel, 1),
                GT_Utility.getIntegratedCircuit(1)}, tMat.getMolten(1152L * tMultiplier / 2L),
            GT_ModHandler.getModItem("Forestry", "chipsets", 1L, 2), 200, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1L),
                ItemList.Circuit_Integrated_Good.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 2),
                GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Electrum, 4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 1),
                GT_Utility.getIntegratedCircuit(1)}, tMat.getMolten(1152L * tMultiplier / 2L),
            GT_ModHandler.getModItem("Forestry", "chipsets", 1L, 3), 200, 30);

        // --- GT Circuits
        // --- ULV
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1L),
                ItemList.Circuit_Chip_Simple_SoC.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tin, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.NandChip.get(12L), 450, 30);

        // --- LV
        //1
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1),
                ItemList.Circuit_Parts_Resistor.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2),
                ItemList.Circuit_Parts_Vacuum_Tube.get(2)}, tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("IC2", "itemPartCircuit", 2L, 0), 200, 16);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1),
                ItemList.Circuit_Parts_ResistorSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2),
                ItemList.Circuit_Parts_Vacuum_Tube.get(2)}, tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("IC2", "itemPartCircuit", 2L, 0), 200, 16);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1),
                ItemList.Circuit_Parts_Resistor.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2),
                ItemList.NandChip.get(2)}, tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("IC2", "itemPartCircuit", 2L, 0), 200, 16);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1),
                ItemList.Circuit_Parts_ResistorSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2),
                ItemList.NandChip.get(2)}, tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getModItem("IC2", "itemPartCircuit", 2L, 0), 200, 16);
        //2
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1),
                ItemList.Circuit_Chip_ILC.get(1), ItemList.Circuit_Parts_Resistor.get(2),
                ItemList.Circuit_Parts_Diode.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Tin, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Basic.get(3), 200, 16);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1),
                ItemList.Circuit_Chip_ILC.get(1), ItemList.Circuit_Parts_ResistorSMD.get(2),
                ItemList.Circuit_Parts_Diode.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Tin, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Basic.get(3), 200, 16);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1),
                ItemList.Circuit_Chip_ILC.get(1), ItemList.Circuit_Parts_ResistorSMD.get(2),
                ItemList.Circuit_Parts_DiodeSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Tin, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Basic.get(3), 200, 16);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Coated_Basic.get(1),
                ItemList.Circuit_Chip_ILC.get(1), ItemList.Circuit_Parts_Resistor.get(2),
                ItemList.Circuit_Parts_DiodeSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Tin, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Basic.get(3), 200, 16);
        //3
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_Resistor.get(2),
                ItemList.Circuit_Parts_Capacitor.get(2), ItemList.Circuit_Parts_Transistor.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(4), 200,
            60, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(2),
                ItemList.Circuit_Parts_Capacitor.get(2), ItemList.Circuit_Parts_Transistor.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(4), 200,
            60, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_Resistor.get(2),
                ItemList.Circuit_Parts_CapacitorSMD.get(2),
                ItemList.Circuit_Parts_Transistor.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(4), 200,
            60, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_Resistor.get(2),
                ItemList.Circuit_Parts_Capacitor.get(2),
                ItemList.Circuit_Parts_TransistorSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(4), 200,
            60, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(2),
                ItemList.Circuit_Parts_CapacitorSMD.get(2),
                ItemList.Circuit_Parts_Transistor.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(4), 200,
            60, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(2),
                ItemList.Circuit_Parts_Capacitor.get(2),
                ItemList.Circuit_Parts_TransistorSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(4), 200,
            60, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_Resistor.get(2),
                ItemList.Circuit_Parts_CapacitorSMD.get(2),
                ItemList.Circuit_Parts_TransistorSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(4), 200,
            60, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(2),
                ItemList.Circuit_Parts_CapacitorSMD.get(2),
                ItemList.Circuit_Parts_TransistorSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(4), 200,
            60, true);

        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_SoC.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Tin, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Microprocessor.get(6), 50,
            600, true);

        // --- MV
        //1
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1),
                GT_ModHandler.getModItem("IC2", "itemPartCircuit", 2L, 0),
                ItemList.Circuit_Parts_Diode.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Good.get(2), 300, 30);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1),
                GT_ModHandler.getModItem("IC2", "itemPartCircuit", 2L, 0),
                ItemList.Circuit_Parts_DiodeSMD.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 2)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Good.get(2), 300, 30);
        //2
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1),
                ItemList.Circuit_Basic.get(3), ItemList.Circuit_Parts_Resistor.get(4),
                ItemList.Circuit_Parts_Diode.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Integrated_Good.get(3), 400,
            24);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1),
                ItemList.Circuit_Basic.get(3), ItemList.Circuit_Parts_ResistorSMD.get(4),
                ItemList.Circuit_Parts_Diode.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Integrated_Good.get(3), 400,
            24);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1),
                ItemList.Circuit_Basic.get(3), ItemList.Circuit_Parts_ResistorSMD.get(4),
                ItemList.Circuit_Parts_DiodeSMD.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Integrated_Good.get(3), 400,
            24);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1),
                ItemList.Circuit_Basic.get(3), ItemList.Circuit_Parts_Resistor.get(4),
                ItemList.Circuit_Parts_DiodeSMD.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Integrated_Good.get(3), 400,
            24);
        //3
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_Resistor.get(4),
                ItemList.Circuit_Parts_Capacitor.get(4), ItemList.Circuit_Parts_Transistor.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4), 200, 60,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(4),
                ItemList.Circuit_Parts_Capacitor.get(4), ItemList.Circuit_Parts_Transistor.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4), 200, 60,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_Resistor.get(4),
                ItemList.Circuit_Parts_CapacitorSMD.get(4),
                ItemList.Circuit_Parts_Transistor.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4), 200, 60,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_Resistor.get(4),
                ItemList.Circuit_Parts_Capacitor.get(4),
                ItemList.Circuit_Parts_TransistorSMD.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4), 200, 60,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(4),
                ItemList.Circuit_Parts_CapacitorSMD.get(4),
                ItemList.Circuit_Parts_Transistor.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4), 200, 60,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(4),
                ItemList.Circuit_Parts_Capacitor.get(4),
                ItemList.Circuit_Parts_TransistorSMD.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4), 200, 60,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_Resistor.get(4),
                ItemList.Circuit_Parts_CapacitorSMD.get(4),
                ItemList.Circuit_Parts_TransistorSMD.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4), 200, 60,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(4),
                ItemList.Circuit_Parts_CapacitorSMD.get(4),
                ItemList.Circuit_Parts_TransistorSMD.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4), 200, 60,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                ItemList.Circuit_Chip_CPU.get(1L), ItemList.Circuit_Parts_ResistorASMD.get(1L),
                ItemList.Circuit_Parts_CapacitorASMD.get(1L),
                ItemList.Circuit_Parts_TransistorASMD.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(4L), 100, 60,
            true);

        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_SoC.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 4)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Processor.get(6), 50, 2400,
            true);

        // --- HV
        //1
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Integrated_Good.get(1),
                ItemList.Circuit_Chip_ILC.get(2), ItemList.Circuit_Chip_Ram.get(2),
                ItemList.Circuit_Parts_Transistor.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 8)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getIC2Item("advancedCircuit", 1L), 800, 30, false);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Integrated_Good.get(1),
                ItemList.Circuit_Chip_ILC.get(2), ItemList.Circuit_Chip_Ram.get(2),
                ItemList.Circuit_Parts_TransistorSMD.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 8)},
            tMat.getMolten(144L * tMultiplier / 2L),
            GT_ModHandler.getIC2Item("advancedCircuit", 1L), 800, 30, false);
        //2
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Processor.get(2), ItemList.Circuit_Parts_Coil.get(4),
                ItemList.Circuit_Parts_Capacitor.get(8), ItemList.Circuit_Chip_Ram.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 8)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Computer.get(2), 400, 120, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Processor.get(2), ItemList.Circuit_Parts_Coil.get(4),
                ItemList.Circuit_Parts_CapacitorSMD.get(8), ItemList.Circuit_Chip_Ram.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 8)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Computer.get(2), 400, 96, true);
        //3
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                ItemList.Circuit_Chip_NanoCPU.get(1), ItemList.Circuit_Parts_ResistorSMD.get(8),
                ItemList.Circuit_Parts_CapacitorSMD.get(8),
                ItemList.Circuit_Parts_TransistorSMD.get(8),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Nanoprocessor.get(3), 200,
            600, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                ItemList.Circuit_Chip_NanoCPU.get(1L), ItemList.Circuit_Parts_ResistorASMD.get(2L),
                ItemList.Circuit_Parts_CapacitorASMD.get(2L),
                ItemList.Circuit_Parts_TransistorASMD.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Nanoprocessor.get(3), 100,
            600, true);

        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                ItemList.Circuit_Chip_SoC2.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Nanoprocessor.get(4), 50,
            9600, true);

        // --- EV
        //1
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Advanced.get(2), ItemList.Circuit_Parts_Diode.get(4),
                ItemList.Circuit_Chip_Ram.get(8),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Data.get(1), 400, 120, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Advanced.get(2), ItemList.Circuit_Parts_DiodeSMD.get(4),
                ItemList.Circuit_Chip_Ram.get(8),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Data.get(1), 400, 120, true);
        //2
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                ItemList.Circuit_Nanoprocessor.get(2), ItemList.Circuit_Parts_Coil.get(8),
                ItemList.Circuit_Parts_CapacitorSMD.get(8), ItemList.Circuit_Chip_Ram.get(8),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Nanocomputer.get(1), 400, 600,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                ItemList.Circuit_Nanoprocessor.get(2L), ItemList.Circuit_Parts_Coil.get(8L),
                ItemList.Circuit_Parts_CapacitorASMD.get(2L), ItemList.Circuit_Chip_Ram.get(8L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Nanocomputer.get(1L), 200, 600,
            true);

        //3
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                ItemList.Circuit_Chip_QuantumCPU.get(1), ItemList.Circuit_Chip_NanoCPU.get(1),
                ItemList.Circuit_Parts_CapacitorSMD.get(12),
                ItemList.Circuit_Parts_TransistorSMD.get(12),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 12)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Quantumprocessor.get(2), 200,
            2400, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                ItemList.Circuit_Chip_QuantumCPU.get(1L), ItemList.Circuit_Chip_NanoCPU.get(1L),
                ItemList.Circuit_Parts_CapacitorASMD.get(3L),
                ItemList.Circuit_Parts_TransistorASMD.get(3L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Quantumprocessor.get(2L), 100,
            2400, true);

        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                ItemList.Circuit_Chip_SoC3.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.NiobiumTitanium, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Quantumprocessor.get(3), 50,
            38400, true);

        // --- IV
        //1
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Data.get(2), ItemList.Circuit_Parts_Coil.get(12),
                ItemList.Circuit_Parts_Capacitor.get(24), ItemList.Circuit_Chip_Ram.get(16),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 24)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Elite.get(1), 1600, 480, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Data.get(2), ItemList.Circuit_Parts_Coil.get(12),
                ItemList.Circuit_Parts_CapacitorSMD.get(16), ItemList.Circuit_Chip_Ram.get(16),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 16)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Elite.get(1), 1600, 480, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Data.get(2), ItemList.Circuit_Parts_Coil.get(12L),
                ItemList.Circuit_Parts_CapacitorASMD.get(4L), ItemList.Circuit_Chip_Ram.get(16L),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 16)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Elite.get(1L), 800, 480, true);

        //2
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                ItemList.Circuit_Nanocomputer.get(2), ItemList.Circuit_Parts_DiodeSMD.get(8),
                ItemList.Circuit_Chip_NOR.get(4), ItemList.Circuit_Chip_Ram.get(16),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Elitenanocomputer.get(1), 400, 600,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                ItemList.Circuit_Nanocomputer.get(2L), ItemList.Circuit_Parts_DiodeASMD.get(2L),
                ItemList.Circuit_Chip_NOR.get(4L), ItemList.Circuit_Chip_Ram.get(16L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Elitenanocomputer.get(1L), 200,
            600, true);

        //3
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                ItemList.Circuit_Quantumprocessor.get(2), ItemList.Circuit_Parts_Coil.get(12),
                ItemList.Circuit_Parts_CapacitorSMD.get(16), ItemList.Circuit_Chip_Ram.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Quantumcomputer.get(2), 400, 2400,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                ItemList.Circuit_Quantumprocessor.get(2L), ItemList.Circuit_Parts_Coil.get(12L),
                ItemList.Circuit_Parts_CapacitorASMD.get(4L), ItemList.Circuit_Chip_Ram.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 24)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Quantumcomputer.get(2L), 200, 2400,
            true);

        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                ItemList.Circuit_Chip_SoC4.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.YttriumBariumCuprate, 8)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Quantumcomputer.get(2), 50, 38400,
            true);
        //4
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Multifiberglass_Elite.get(1L),
                ItemList.Circuit_Chip_CrystalCPU.get(1L), ItemList.Circuit_Chip_NanoCPU.get(2L),
                ItemList.Circuit_Parts_CapacitorASMD.get(6),
                ItemList.Circuit_Parts_TransistorASMD.get(6),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Crystalprocessor.get(2L), 100,
            9600, true);

        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Multifiberglass_Elite.get(1),
                ItemList.Circuit_Chip_CrystalSoC.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.YttriumBariumCuprate, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Crystalprocessor.get(3), 50,
            153600, true);

        // --- LuV
        //1
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Elitenanocomputer.get(2), ItemList.Circuit_Parts_Coil.get(16),
                ItemList.Circuit_Parts_CapacitorSMD.get(32), ItemList.Circuit_Chip_Ram.get(16),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 32)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Master.get(1), 1600, 1920,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Elitenanocomputer.get(2L), ItemList.Circuit_Parts_Coil.get(16L),
                ItemList.Circuit_Parts_CapacitorASMD.get(8L), ItemList.Circuit_Chip_Ram.get(16L),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 32)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Master.get(1L), 800, 1920,
            true);

        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Chip_SoC4.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 32),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.YttriumBariumCuprate, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Master.get(1), 50, 500000,
            true);
        //2
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                ItemList.Circuit_Quantumcomputer.get(2), ItemList.Circuit_Parts_DiodeSMD.get(8),
                ItemList.Circuit_Chip_NOR.get(4), ItemList.Circuit_Chip_Ram.get(16),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 32)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Masterquantumcomputer.get(1), 400,
            2400, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                ItemList.Circuit_Quantumcomputer.get(2L), ItemList.Circuit_Parts_DiodeASMD.get(2L),
                ItemList.Circuit_Chip_NOR.get(4L), ItemList.Circuit_Chip_Ram.get(16L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 48)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Masterquantumcomputer.get(1L), 200,
            2400, true);

        //3
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Multifiberglass_Elite.get(1L),
                ItemList.Circuit_Crystalprocessor.get(2L), ItemList.Circuit_Parts_Coil.get(24),
                ItemList.Circuit_Parts_CapacitorASMD.get(8L), ItemList.Circuit_Chip_Ram.get(24),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Crystalcomputer.get(2L), 200, 9600,
            true);

        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Multifiberglass_Elite.get(1),
                ItemList.Circuit_Chip_SoC4.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.YttriumBariumCuprate, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Crystalcomputer.get(2), 50,
            153600, true);
        //4
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Chip_NeuroCPU.get(1L),
                ItemList.Circuit_Chip_CrystalCPU.get(1L), ItemList.Circuit_Chip_NanoCPU.get(1L),
                ItemList.Circuit_Parts_CapacitorASMD.get(8L),
                ItemList.Circuit_Parts_TransistorASMD.get(8L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Neuroprocessor.get(2L), 200,
            38400, true);

        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Chip_NeuroCPU.get(1),
                ItemList.Circuit_Chip_CrystalSoC2.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Naquadah, 8)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Neuroprocessor.get(3), 50,
            614400, true);

        // --- ZPM
        //1
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Masterquantumcomputer.get(2), ItemList.Circuit_Parts_Coil.get(24),
                ItemList.Circuit_Parts_CapacitorSMD.get(48), ItemList.Circuit_Chip_Ram.get(24),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 48)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Quantummainframe.get(1), 1600,
            7680, true);
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Masterquantumcomputer.get(2L), ItemList.Circuit_Parts_Coil.get(24),
                ItemList.Circuit_Parts_CapacitorASMD.get(12L), ItemList.Circuit_Chip_Ram.get(24),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 48)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Quantummainframe.get(1L), 800,
            7680, true);

        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Trinium, 2),
                ItemList.Circuit_Chip_CrystalSoC.get(1),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Platinum, 32),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Europium, 16)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Quantummainframe.get(1), 50,
            2000000, true);
        //2
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Multifiberglass_Elite.get(1),
                ItemList.Circuit_Crystalcomputer.get(2), ItemList.Circuit_Chip_Ram.get(4),
                ItemList.Circuit_Chip_NOR.get(32), ItemList.Circuit_Chip_NAND.get(64),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 32)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Ultimatecrystalcomputer.get(1),
            400, 9600, true);
        //3
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Wetware_Extreme.get(1L),
                ItemList.Circuit_Neuroprocessor.get(2L), ItemList.Circuit_Parts_Coil.get(32L),
                ItemList.Circuit_Parts_CapacitorASMD.get(12L), ItemList.Circuit_Chip_Ram.get(24L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 16)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Wetwarecomputer.get(2L), 300,
            38400, true);

        //4
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Chip_BioCPU.get(1L),
                ItemList.Circuit_Chip_CrystalSoC2.get(1L), ItemList.Circuit_Chip_NanoCPU.get(2L),
                ItemList.Circuit_Parts_CapacitorASMD.get(12L),
                ItemList.Circuit_Parts_TransistorASMD.get(12L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 16)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Bioprocessor.get(2L), 300,
            153600, true);

        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Chip_BioCPU.get(1L),
                ItemList.Circuit_Chip_MCrystalCPU.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.ElectrumFlux, 16)},
            tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Bioprocessor.get(3), 150,
            2457600, true);

        // --- UV
        //1
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                ItemList.Circuit_Ultimatecrystalcomputer.get(2L),
                ItemList.Circuit_Parts_Coil.get(32L), ItemList.Circuit_Parts_CapacitorASMD.get(16L),
                ItemList.Circuit_Chip_Ram.get(32L),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 8)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Crystalmainframe.get(1L), 800,
            30720, true);

        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Oriharukon, 2),
                ItemList.Circuit_Chip_CrystalSoC.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 8),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Americium, 32)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Crystalmainframe.get(1), 50,
            8000000, true);
        //2
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Wetware_Extreme.get(2L),
                ItemList.Circuit_Wetwarecomputer.get(2L), ItemList.Circuit_Parts_DiodeASMD.get(8L),
                ItemList.Circuit_Chip_NOR.get(16L), ItemList.Circuit_Chip_Ram.get(64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 24)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Wetwaresupercomputer.get(1L), 600,
            38400, true);

        //3
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Bio_Ultra.get(1L),
                ItemList.Circuit_Bioprocessor.get(2L), ItemList.Circuit_Parts_Coil.get(48L),
                ItemList.Circuit_Parts_CapacitorASMD.get(16L), ItemList.Circuit_Chip_Ram.get(32L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 24)},
            tMat.getMolten(144L * tMultiplier), ItemList.Circuit_Biowarecomputer.get(2L), 400,
            153600, true);

        // --- UHV
        //1
        GT_Values.RA.addCircuitAssemblerSpaceRecipe(
            new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Adamantium, 2),
                ItemList.Circuit_Chip_MCrystalCPU.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorZPM, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Neutronium, 64)},
            tMat.getMolten(144L * tMultiplier * 2), ItemList.Circuit_Wetwaremainframe.get(1), 50,
            32000000, true);

        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Plastic_Advanced.get(1),
                ItemList.Circuit_Chip_CPU.get(2), ItemList.Circuit_Chip_NAND.get(32),
                ItemList.Circuit_Chip_Ram.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 16),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 4)},
            tMat.getMolten(144L * tMultiplier), ItemList.Tool_DataStick.get(1), 400, 90, true);
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                ItemList.Circuit_Nanoprocessor.get(2), ItemList.Circuit_Chip_Ram.get(4),
                ItemList.Circuit_Chip_NOR.get(32), ItemList.Circuit_Chip_NAND.get(64),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 32)},
            tMat.getMolten(144L * tMultiplier), ItemList.Tool_DataOrb.get(1), 400, 1200, true);

        //Energy Flow Circuit
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Multifiberglass_Elite.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 2),
                ItemList.Circuit_Chip_UHPIC.get(4L), ItemList.Circuit_Chip_QuantumCPU.get(2L),
                ItemList.Circuit_Chip_NanoCPU.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorZPM, 16)},
            tMat.getMolten(576L * tMultiplier / 2L), ItemList.Circuit_HighEnergyFlow.get(1L), 2400,
            7680, true);

        //Lapoorbs
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                ItemList.Circuit_Chip_PIC.get(4),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(24L),
                ItemList.Circuit_Chip_NanoCPU.get(2),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Platinum, 8)},
            tMat.getMolten(144L * tMultiplier), ItemList.Energy_LapotronicOrb.get(1), 512, 1024,
            true);
        //Wetware Board
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Multifiberglass.get(16L),
                ItemList.Circuit_Parts_PetriDish.get(1L), ItemList.Electric_Pump_LuV.get(1L),
                ItemList.Sensor_IV.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 16L)},
            Materials.GrowthMediumSterilized.getFluid(4000L),
            ItemList.Circuit_Board_Wetware.get(16L), 1200, 30720, true);

        //Bio Board
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Wetware.get(32L),
                ItemList.Circuit_Parts_PetriDish.get(32L), ItemList.Electric_Pump_UV.get(1L),
                ItemList.Sensor_LuV.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 1L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Neutronium, 32L)},
            Materials.BioMediumSterilized.getFluid(16000L), ItemList.Circuit_Board_Bio.get(32L),
            1200, 500000, true);

        //Crystal Board
        GT_Values.RA.addCircuitAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Board_Bio.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Polybenzimidazole, 1),
                ItemList.Electric_Pump_IV.get(1L), ItemList.Sensor_IV.get(1L),
                ItemList.Circuit_Elitenanocomputer.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Orichalcum, 8)},
            Materials.Ledox.getMolten(288L), ItemList.Circuit_Board_Crystal.get(1L), 1400, 122880,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Board_Bio.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Polybenzimidazole, 1),
                ItemList.Electric_Pump_IV.get(1L), ItemList.Sensor_IV.get(1L),
                ItemList.Circuit_Quantumcomputer.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Orichalcum, 8)},
            Materials.Ledox.getMolten(288L), ItemList.Circuit_Board_Crystal.get(1L), 1400, 122880,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Board_Bio.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Polybenzimidazole, 1),
                ItemList.Electric_Pump_IV.get(1L), ItemList.Sensor_IV.get(1L),
                ItemList.Circuit_Crystalprocessor.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Orichalcum, 8)},
            Materials.Ledox.getMolten(288L), ItemList.Circuit_Board_Crystal.get(1L), 1400, 122880,
            true);
        GT_Values.RA.addCircuitAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Board_Bio.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Polybenzimidazole, 1),
                ItemList.Electric_Pump_IV.get(1L), ItemList.Sensor_IV.get(1L),
                ItemList.Circuit_Elite.get(2L),
                GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Orichalcum, 8)},
            Materials.Ledox.getMolten(288L), ItemList.Circuit_Board_Crystal.get(1L), 1400, 122880,
            true);
        // --- Blank Gene Sample
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                ItemList.Circuit_Parts_PetriDish.get(1L), ItemList.Circuit_Chip_CPU.get(2),
                ItemList.Circuit_Chip_Ram.get(4),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Europium, 4)},
            tMat.getMolten(144L * tMultiplier),
                CoreItems2.getRecipe(179, 1), 400, 2560, true);
        // --- Genetic Template
        GT_Values.RA.addCircuitAssemblerRecipe(
            new ItemStack[]{ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                ItemList.Circuit_Parts_PetriDish.get(1L), ItemList.Circuit_Chip_Ram.get(4),
                ItemList.Circuit_Chip_NOR.get(32),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 32),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Europium, 8)},
            tMat.getMolten(144L * tMultiplier),
                CoreItems2.getRecipe(180, 1), 500, 7680, true);

      }
    }
    /* ================================= end GT =================================*/
  }
}