package com.impact.mods.gregtech;
import com.impact.mods.gregtech.items.tools.behaviour.Behaviour_Aerostat;
import com.impact.mods.gregtech.items.tools.behaviour.Behaviour_DebugOreGen;
import com.impact.mods.gregtech.items.tools.behaviour.Behaviour_OreProbe;
import gregtech.common.items.GT_MetaGenerated_Item_04;

public class GT_ItemRegister {

  private GT_MetaGenerated_Item_04 GT;

  public void run() {
    GT = GT_MetaGenerated_Item_04.INSTANCE;
    registerItems();
  }

  private void registerItems() {
    GT_ItemList.Aerostat.set(GT.addItem(6, "Aerostat", "", Behaviour_Aerostat.INSTANCE));
    GT_ItemList.DebugOreGen.set(GT.addItem(7, "Debug Impact Ore Generator", "", Behaviour_DebugOreGen.INSTANCE));
    GT_ItemList.OreProbe.set(GT.addItem(8, "Ore Sampling Tool", "Ore sampling tool in current Chunk (Only Layer 0)", Behaviour_OreProbe.INSTANCE));
    
    GT_ItemList.ULVPump.set(GT.addItem(395, "ULV Electric Pump", "640 L/sec"));
    GT_ItemList.ULVMotor.set(GT.addItem(396, "ULV Electric Motor", ""));
    GT_ItemList.ULVPiston.set(GT.addItem(397, "ULV Electric Piston", ""));
    GT_ItemList.ULVRobotArm.set(GT.addItem(398, "ULV Robot Arm", ""));
    GT_ItemList.ULVConveyorModule.set(GT.addItem(399, "ULV Conveyor Module", ""));

    /* === CHIPSETS === */
    GT_ItemList.EngineeringProcessorFluidDiamondCore
        .set(GT.addItem(471, "Engineering Processor Fluid Diamond Core", ""));
    GT_ItemList.EngineeringProcessorFluidEmeraldCore
        .set(GT.addItem(472, "Engineering Processor Fluid Emerald Core", ""));
    GT_ItemList.EngineeringProcessorFluidGoldCore
        .set(GT.addItem(473, "Engineering Processor Fluid Gold Core", ""));
    GT_ItemList.EngineeringProcessorItemAdvEmeraldCore
        .set(GT.addItem(474, "Engineering Processor Item Adv Emerald Core", ""));
    GT_ItemList.EngineeringProcessorItemDiamondCore
        .set(GT.addItem(475, "Engineering Processor Item Diamond Core", ""));
    GT_ItemList.EngineeringProcessorItemEmeraldCore
        .set(GT.addItem(476, "Engineering Processor Item Emerald Core", ""));
    GT_ItemList.EngineeringProcessorSpatialPulsatingCore
        .set(GT.addItem(477, "Engineering Processor Spatial Pulsating Core", ""));
    GT_ItemList.LogicProcessorItemGoldCore
        .set(GT.addItem(478, "Logic Processor Item Gold Core", ""));
    GT_ItemList.EngravedDiamondCrystalChip
        .set(GT.addItem(479, "Engraved Diamond Crystal Chip", ""));
    GT_ItemList.EngravedEnergyChip.set(GT.addItem(480, "Engraved Energy Chip", ""));
    GT_ItemList.EngravedGoldChip.set(GT.addItem(481, "Engraved Gold Chip", ""));
    GT_ItemList.EngravedQuantumChip.set(GT.addItem(482, "Engraved Quantum Chip", ""));
    GT_ItemList.GeneticCircuit.set(GT.addItem(483, "Genetic Circuit", ""));
    GT_ItemList.EnvironmentalCircuit.set(GT.addItem(484, "Environmental Circuit", ""));
    GT_ItemList.PulsatingSpatialCoreChip.set(GT.addItem(485, "Pulsating Spatial Core Chip", ""));
    GT_ItemList.GoldFluidCoreChip.set(GT.addItem(486, "Gold Fluid Core Chip", ""));
    GT_ItemList.GoldCoreChip.set(GT.addItem(487, "Gold Core Chip", ""));
    GT_ItemList.DiamondCoreChip.set(GT.addItem(488, "Diamond Core Chip", ""));
    GT_ItemList.DiamondFluidCoreChip.set(GT.addItem(489, "Diamond Fluid Core Chip", ""));
    GT_ItemList.EmeraldAdvancedCoreChip.set(GT.addItem(490, "Emerald Advanced Core Chip", ""));
    GT_ItemList.EmeraldAdvancedFluidCoreChip
        .set(GT.addItem(491, "Emerald Advanced Fluid Core Chip", ""));
    GT_ItemList.EmeraldHighAdvancedCoreChip
        .set(GT.addItem(492, "Emerald High Advanced Core Chip", ""));
    GT_ItemList.RedstoneRedChipset.set(GT.addItem(493, "Redstone Red Chipset", ""));
    GT_ItemList.RedstoneQuartzChipset.set(GT.addItem(494, "Redstone Quartz Chipset", ""));
    GT_ItemList.RedstonePulsatingChipset.set(GT.addItem(495, "Redstone Pulsating Chipset", ""));
    GT_ItemList.RedstoneIronChipset.set(GT.addItem(496, "Redstone Iron Chipset", ""));
    GT_ItemList.RedstoneGoldChipset.set(GT.addItem(497, "Redstone Gold Chipset", ""));
    GT_ItemList.RedstoneEmeraldChipset.set(GT.addItem(498, "Redstone Emerald Chipset", ""));
    GT_ItemList.RedstoneDiamondChipset.set(GT.addItem(499, "Redstone Diamond Chipset", ""));

    /* === SPACE === */
    GT_ItemList.packEuropa.set(GT.addItem(500, "Pack Europa Stone Dust", ""));
    GT_ItemList.packBarnardaE.set(GT.addItem(501, "Pack BarnardaE Stone Dust", ""));
    GT_ItemList.packBarnardaF.set(GT.addItem(502, "Pack BarnardaF Stone Dust", ""));
    GT_ItemList.packCallisto.set(GT.addItem(503, "Pack Callisto Stone Dust", ""));
    GT_ItemList.packCentauriA.set(GT.addItem(504, "Pack CentauriA Stone Dust", ""));
    GT_ItemList.packCeres.set(GT.addItem(505, "Pack Ceres Stone Dust", ""));
    GT_ItemList.packDeimos.set(GT.addItem(506, "Pack Deimos Stone Dust", ""));
    GT_ItemList.packEarth.set(GT.addItem(507, "Pack Earth Stone Dust", ""));
    GT_ItemList.packEris.set(GT.addItem(508, "Pack Miranda Stone Dust", ""));
    GT_ItemList.packGanymed.set(GT.addItem(509, "Pack Ganymed Stone Dust", ""));
    GT_ItemList.packHaumea.set(GT.addItem(510, "Pack Haumea Stone Dust", ""));
    GT_ItemList.packIapetus.set(GT.addItem(511, "Pack Enceladus Stone Dust", ""));
    GT_ItemList.packIo.set(GT.addItem(512, "Pack Io Stone Dust", ""));
    GT_ItemList.packMakeMake.set(GT.addItem(513, " Pack MakeMake Stone Dust", ""));
    GT_ItemList.packMercury.set(GT.addItem(514, "Pack Mercury Stone Dust", ""));
    GT_ItemList.packProteus.set(GT.addItem(515, "Pack Proteus Stone Dust", ""));
    GT_ItemList.packOberon.set(GT.addItem(516, "Pack Oberon Stone Dust", ""));
    GT_ItemList.packPhobos.set(GT.addItem(517, "Pack Phobos Stone Dust", ""));
    GT_ItemList.packPluto.set(GT.addItem(518, "Pack Pluto Stone Dust", ""));
    GT_ItemList.packTCetiE.set(GT.addItem(519, "Pack TCetiE Stone Dust", ""));
    GT_ItemList.packTitan.set(GT.addItem(520, "Pack Titan Stone Dust", ""));
    GT_ItemList.packTriton.set(GT.addItem(521, "Pack Triton Stone Dust", ""));
    GT_ItemList.packVegaB.set(GT.addItem(522, "Pack VegaB Stone Dust", ""));
    GT_ItemList.packVenus.set(GT.addItem(523, "Pack Venus Stone Dust", ""));
    GT_ItemList.packMoon.set(GT.addItem(524, "Pack Moon Stone Dust", ""));
    GT_ItemList.packMars.set(GT.addItem(525, "Pack Mars Stone Dust", ""));
    GT_ItemList.packAsteroids.set(GT.addItem(526, "Pack Asteroids Stone Dust", ""));
    GT_ItemList.spacebox1.set(GT.addItem(527, "Space Box Tier 1", ""));
    GT_ItemList.spacebox2.set(GT.addItem(528, "Space Box Tier 2", ""));
    GT_ItemList.spacebox3.set(GT.addItem(529, "Space Box Tier 3", ""));
    GT_ItemList.spacebox4.set(GT.addItem(530, "Space Box Tier 4", ""));
    GT_ItemList.spacebox5.set(GT.addItem(531, "Space Box Tier 5", ""));
    GT_ItemList.spacebox6.set(GT.addItem(532, "Space Box Tier 6", ""));
    GT_ItemList.spacebox7.set(GT.addItem(533, "Space Box Tier 7", ""));
    GT_ItemList.spacebox8.set(GT.addItem(534, "Space Box Tier 8", ""));
  }
}
