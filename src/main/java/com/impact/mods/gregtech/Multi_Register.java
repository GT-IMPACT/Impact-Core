package com.impact.mods.gregtech;

import static com.impact.mods.gregtech.GT_ItemList.*;

import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_HyperNaquadahGenerator;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidEnrichedNqGenerator;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidNqGenerator;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_HugeSteamTurbine;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorI;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorII;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorIII;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_I;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_II;
import com.impact.mods.gregtech.tileentities.multi.generators.sofc.GTMTE_SOFC_III;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_AdvDDDPrinter;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_BlastSmelter;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_CokeOven;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_DDDPrinter;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_Farm;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_HeavyMetalCyclone;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_Pyrolyse;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_SawMill;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_AdvancedPyrolyse;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Supply;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_AdvancedVacuumFreezer;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_ArcFurnace;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Assembler;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Brewmenter;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Centrifuge;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Cutting;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Electrolyzer;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Extradifier;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_FreezerSolidifier;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_LaserEng;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Macerator;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Mixer;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_PressBendExtrud;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Siftarator;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Utility;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Wire;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_BasicWaterPump;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_DrillerWater;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_MoonMiner;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_SpaceElevator;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import com.impact.mods.gregtech.tileentities.multi.storage.*;

public class Multi_Register {

  public void run() {
    registerMachines();
  }

  private void registerMachines() {
    int ID = 14020;

    //MULTIBLOCKS
    Machine_PBE.set(new GTMTE_PressBendExtrud(ID++, "impact.multimachine.pbe", "Multi PBE Machine")
        .getStackForm(1L));
    Machine_LaserEngraver.set(
        new GTMTE_LaserEng(ID++, "impact.multimachine.laserengraver", "Multi Laser Engraver")
            .getStackForm(1L));
    Machine_Assembler.set(
        new GTMTE_Assembler(ID++, "impact.multimachine.assembler", "Multi Assembling Machine")
            .getStackForm(1L));
    Machine_Centrifuge.set(
        new GTMTE_Centrifuge(ID++, "impact.multimachine.centrifuge", "Multi Centrifuge")
            .getStackForm(1L));
    Machine_Electrolyzer.set(
        new GTMTE_Electrolyzer(ID++, "impact.multimachine.electrolyzer", "Multi Electrolyzer")
            .getStackForm(1L));
    Machine_Wire.set(
        new GTMTE_Wire(ID++, "impact.multimachine.wire", "Multi Wire Factory")
            .getStackForm(1L));
    Machine_Supply.set(
        new GTMTE_Supply(ID++, "impact.multimachine.supply", "Multi Supply Production")
            .getStackForm(1L));
    Machine_Utility.set(
        new GTMTE_Utility(ID++, "impact.multimachine.utility", "Multi Utility Machine")
            .getStackForm(1L));
    Machine_Brewmenter.set(
        new GTMTE_Brewmenter(ID++, "impact.multimachine.brewmenter", "Multi Brewmenter")
            .getStackForm(1L));
    Machine_ArcFurnace.set(
        new GTMTE_ArcFurnace(ID++, "impact.multimachine.arcfurnace", "Multi Arc Furnace")
            .getStackForm(1L));
    Machine_Cutting.set(
        new GTMTE_Cutting(ID++, "impact.multimachine.cutting", "Multi Cutting Machine")
            .getStackForm(1L));
    Machine_Extradifier.set(
        new GTMTE_Extradifier(ID++, "impact.multimachine.extradifier", "Multi Extradification")
            .getStackForm(1L));
    Machine_Macerator.set(
        new GTMTE_Macerator(ID++, "impact.multimachine.macerator", "Multi Maceration Stack")
            .getStackForm(1L));
    Machine_Mixer.set(new GTMTE_Mixer(ID++, "impact.multimachine.mixer", "Multi Mixing Machine")
        .getStackForm(1L));
    Machine_Siftarator.set(
        new GTMTE_Siftarator(ID++, "impact.multimachine.siftarator", "Multi Siftaration Unit")
            .getStackForm(1L));
    Machine_DDDPrinter.set(
        new GTMTE_DDDPrinter(ID++, "impact.multimachine.dddprinter", "Basic 3D Printer")
            .getStackForm(1L));
    Machine_FreezSolidifier.set(
        new GTMTE_FreezerSolidifier(ID++, "impact.multimachine.freezsolidifier",
            "Freezer Solidifier").getStackForm(1L));
    Machine_BlastSmelter.set(
        new GTMTE_BlastSmelter(ID++, "impact.multimachine.blastsmelter", "Blast Smelter")
            .getStackForm(1L));
    WaterDrill.set(new GTMTE_DrillerWater(ID++, "impact.multimachine.waterdrill",
        "Electric Water Drilling Rig").getStackForm(1L));
    BasicWaterPump.set(
        new GTMTE_BasicWaterPump(ID++, "impact.multimachine.basicwaterpump", "Primitive Water Pump")
            .getStackForm(1L));
    AdvVacuumFreezer.set(new GTMTE_AdvancedVacuumFreezer(ID++, "impact.multimachine.advvf",
        "Advanced Vacuum Freezer").getStackForm(1L));
    LapPowerStation.set(new GTMTE_LapPowerStation(ID++, "impact.multimachine.supercapacitor",
        "Lapotronic Supercapacitor").getStackForm(1L));
    SawMill
        .set(new GTMTE_SawMill(ID++, "impact.multimachine.sawmill", "Saw Mill").getStackForm(1L));
    Pyrolyse.set(
        new GTMTE_Pyrolyse(ID++, "impact.multimachine.pyrolyse", "Pyrolyse Oven")
            .getStackForm(1L));
    AdvPyrolyse.set(new GTMTE_AdvancedPyrolyse(ID++, "impact.multimachine.advpyrolyse",
        "Advanced Pyrolyse Oven").getStackForm(1L));
    Naquadah_multi.set(
        new GTMTE_HyperNaquadahGenerator(ID++, "impact.multimachine.naqgen", "Hyper Generator")
            .getStackForm(1L));
    Naquadah_Liquid_multi.set(new GTMTE_LiquidNqGenerator(ID++, "impact.multimachine.liquidnaqgen",
        "Liquid Naquadah Generator").getStackForm(1L));
    Heavy_Metal_Cyclone.set(
        new GTMTE_HeavyMetalCyclone(ID++, "impact.multimachine.heavymetalcyclone",
            "Heavy Metal Cyclone").getStackForm(1L));
    Naquadah_Liquid_Enriched.set(
        new GTMTE_LiquidEnrichedNqGenerator(ID++, "impact.multimachine.liquidenrichednqgenerator",
            "Liquid Enriched Naquadah Generator").getStackForm(1L));
    Moon_Miner.set(
        new GTMTE_MoonMiner(ID++, "impact.multimachine.blockminer", "Moon Miner").getStackForm(1L));
    Machine_AdvDDDPrinter.set(
        new GTMTE_AdvDDDPrinter(ID++, "impact.multimachine.advdddprinter", "Advanced 3D Printer")
            .getStackForm(1L));
    Rail_Assembler.set(
        new GTMTE_RailAssembler(ID++, "impact.multimachine.railassembler", "Rail Assembler")
            .getStackForm(1L));
    Space_Elevator.set(
        new GTMTE_SpaceElevator(ID++, "impact.multimachine.spaceelevator", "Space Elevator")
            .getStackForm(1L));
    Nuclear_Reactor_I.set(new GTMTE_NuclearReactorI(ID++, "impact.multis.nuclear1", "Nuclear Reactor I")
        .getStackForm(1));
    Nuclear_Reactor_II.set(new GTMTE_NuclearReactorII(ID++, "impact.multis.nuclear2", "Nuclear Reactor II")
        .getStackForm(1));
    Nuclear_Reactor_III.set(new GTMTE_NuclearReactorIII(ID++, "impact.multis.nuclear3", "Nuclear Reactor III")
        .getStackForm(1));
    Huge_Steam_Turbine.set(new GTMTE_HugeSteamTurbine(ID++, "impact.multis.hugesteamturbine", "Huge Steam Turbine")
        .getStackForm(1));

    Parallel_Computer.set(new GTMTE_ParallelComputer(ID++, "impact.multis.parallelcomputer", "Parallel Supercomputer").getStackForm(1L));
    Communication_Tower.set(new GTMTE_TowerCommunication(ID++, "impact.multis.communicationtower", "Communication Tower").getStackForm(1L));
    Space_Satellite.set(new GTMTE_SpaceSatellite(ID++, "impact.multis.spacesatellite", "Space Satellite").getStackForm(1L));

    System.out.println("Multi_Register.java | Last ID: " + ID);

    Machine_CokeOven.set(
        new GTMTE_CokeOven(13002, "impact.multimachine.cokeoven", "Coke Oven")
            .getStackForm(1L));
    Machine_Multi_Farm
        .set(new GTMTE_Farm(13001, "impact.multimachine.farm", "Multiblock Farm")
            .getStackForm(1L));

    SOFC_Low.set(
        new GTMTE_SOFC_I(13101, "impact.multimachine.fuelcellmk1", "Solid-Oxide Generator Tier 1",
            0).getStackForm(1L));
    SOFC_Medium.set(
        new GTMTE_SOFC_II(13102, "impact.multimachine.fuelcellmk2", "Solid-Oxide Generator Tier 2",
            1).getStackForm(1L));
    SOFC_Huge.set(
        new GTMTE_SOFC_III(13103, "impact.multimachine.fuelcellmk3", "Solid-Oxide Generator Tier 3",
            2).getStackForm(1L));
    Multi_Tank.set(new GTMTE_MultiTank(13104, "impact.multimachine.multifluidtank", "Multi-Tank")
        .getStackForm(1L));
    Single_Tank.set(
        new GTMTE_SingleTank(13105, "impact.multimachine.singlefluidtank", "Single-Tank")
            .getStackForm(1L));
  }
}
