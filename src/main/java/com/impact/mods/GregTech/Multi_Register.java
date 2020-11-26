package com.impact.mods.GregTech;


import com.impact.mods.GregTech.tileentities.multi.*;
import com.impact.mods.GregTech.tileentities.multi.generators.*;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_LapPowerStation;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_MultiTank;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_SingleTank;

import static com.impact.mods.GregTech.GT_ItemList.*;

public class Multi_Register {
    public void run() {
        registerMachines();
    }

    private void registerMachines() {
        int ID = 14020;

        //MULTIBLOCKS
        Machine_PBE.set(new GTMTE_PressBendExtrud(ID++, "impact.multimachine.pbe", "Multi PBE Machine").getStackForm(1L));
        Machine_LaserEngraver.set(new GTMTE_LaserEng(ID++, "impact.multimachine.laserengraver", "Multi Laser Engraver").getStackForm(1L));
        Machine_Assembler.set(new GTMTE_Assembler(ID++, "impact.multimachine.assembler", "Multi Assembling Machine").getStackForm(1L));
        Machine_Centrifuge.set(new GTMTE_Centrifuge(ID++, "impact.multimachine.centrifuge", "Multi Centrifuge").getStackForm(1L));
        Machine_Electrolyzer.set(new GTMTE_Electrolyzer(ID++, "impact.multimachine.electrolyzer", "Multi Electrolyzer").getStackForm(1L));
        Machine_Wire.set(new GTMTE_Wire(ID++, "impact.multimachine.wire", "Multi Wire Factory").getStackForm(1L));
        Machine_Supply.set(new GTMTE_Supply(ID++, "impact.multimachine.supply", "Multi Supply Production").getStackForm(1L));
        Machine_Utility.set(new GTMTE_Utility(ID++, "impact.multimachine.utility", "Multi Utility Machine").getStackForm(1L));
        Machine_Brewmenter.set(new GTMTE_Brewmenter(ID++, "impact.multimachine.brewmenter", "Multi Brewmenter").getStackForm(1L));
        Machine_ArcFurnace.set(new GTMTE_ArcFurnace(ID++, "impact.multimachine.arcfurnace", "Multi Arc Furnace").getStackForm(1L));
        Machine_Cutting.set(new GTMTE_Cutting(ID++, "impact.multimachine.cutting", "Multi Cutting Machine").getStackForm(1L));
        Machine_Extradifier.set(new GTMTE_Extradifier(ID++, "impact.multimachine.extradifier", "Multi Extradification").getStackForm(1L));
        Machine_Macerator.set(new GTMTE_Macerator(ID++, "impact.multimachine.macerator", "Multi Maceration Stack").getStackForm(1L));
        Machine_Mixer.set(new GTMTE_Mixer(ID++, "impact.multimachine.mixer", "Multi Mixing Machine").getStackForm(1L));
        Machine_Siftarator.set(new GTMTE_Siftarator(ID++, "impact.multimachine.siftarator", "Multi Siftaration Unit").getStackForm(1L));
        Machine_DDDPrinter.set(new GTMTE_DDDPrinter(ID++, "impact.multimachine.dddprinter", "Basic 3D Printer").getStackForm(1L));
        Machine_FreezSolidifier.set(new GTMTE_FreezerSolidifier(ID++, "impact.multimachine.freezsolidifier", "Freezer Solidifier").getStackForm(1L));
        Machine_BlastSmelter.set(new GTMTE_BlastSmelter(ID++, "impact.multimachine.blastsmelter", "Blast Smelter").getStackForm(1L));
        WaterDrill.set(new GTMTE_DrillerWater(ID++, "impact.multimachine.waterdrill", "Electric Water Drilling Rig").getStackForm(1L));
        BasicWaterPump.set(new GTMTE_BasicWaterPump(ID++, "impact.multimachine.basicwaterpump", "Primitive Water Pump").getStackForm(1L));
        AdvVacuumFreezer.set(new GTMTE_AdvancedVacuumFreezer(ID++, "impact.multimachine.advvf", "Advanced Vacuum Freezer").getStackForm(1L));
        LapPowerStation.set(new GTMTE_LapPowerStation(ID++, "impact.multimachine.supercapacitor", "Lapotronic Supercapacitor").getStackForm(1L));
        SawMill.set(new GTMTE_SawMill(ID++, "impact.multimachine.sawmill", "Saw Mill").getStackForm(1L));
        Pyrolyse.set(new GTMTE_Pyrolyse(ID++, "impact.multimachine.pyrolyse", "Pyrolyse Oven").getStackForm(1L));
        AdvPyrolyse.set(new GTMTE_AdvancedPyrolyse(ID++, "impact.multimachine.advpyrolyse", "Advanced Pyrolyse Oven").getStackForm(1L));
        Naquadah_multi.set(new GTMTE_HyperNaquadahGenerator(ID++, "impact.multimachine.naqgen", "Hyper Generator").getStackForm(1L));
        Naquadah_Liquid_multi.set(new GTMTE_LiquidNqGenerator(ID++, "impact.multimachine.liquidnaqgen", "Liquid Naquadah Generator").getStackForm(1L));
        Heavy_Metal_Cyclone.set(new GTMTE_HeavyMetalCyclone(ID++, "impact.multimachine.heavymetalcyclone", "Heavy Metal Cyclone").getStackForm(1L));
        Naquadah_Liquid_Enriched.set(new GTMTE_LiquidEnrichedNqGenerator(ID++, "impact.multimachine.liquidenrichednqgenerator", "Liquid Enriched Naquadah Generator").getStackForm(1L));

        Moon_Miner.set(new GTMTE_MoonMiner(ID++, "impact.multimachine.blockminer", "Moon Miner").getStackForm(1L));
        Machine_AdvDDDPrinter.set(new GTMTE_AdvDDDPrinter(ID++, "impact.multimachine.advdddprinter", "Advanced 3D Printer").getStackForm(1L));

        Rail_Assembler.set(new GTMTE_RailAssembler(ID++, "impact.multimachine.railassembler", "Rail Assembler").getStackForm(1L));


        Machine_CokeOven.set(new GTMTE_CokeOven(13002, "impact.multimachine.cokeoven", "Coke Oven").getStackForm(1L));
        Machine_Multi_Farm.set(new GTMTE_Farm(13001, "impact.multimachine.farm", "Multiblock Farm").getStackForm(1L));

        SOFC_Low.set(new GTMTE_SOFC_I(13101, "impact.multimachine.fuelcellmk1", "Solid-Oxide Generator Tier 1", 0).getStackForm(1L));
        SOFC_Medium.set(new GTMTE_SOFC_II(13102, "impact.multimachine.fuelcellmk2", "Solid-Oxide Generator Tier 2", 1).getStackForm(1L));
        SOFC_Huge.set(new GTMTE_SOFC_III(13103, "impact.multimachine.fuelcellmk3", "Solid-Oxide Generator Tier 3", 2).getStackForm(1L));
        Multi_Tank.set(new GTMTE_MultiTank(13104, "impact.multimachine.multifluidtank", "Multi-Tank").getStackForm(1L));
        Single_Tank.set(new GTMTE_SingleTank(13105, "impact.multimachine.singlefluidtank", "Single-Tank").getStackForm(1L));
    }
}
