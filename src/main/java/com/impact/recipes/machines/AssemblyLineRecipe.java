package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import static com.impact.item.Core_List_Items.*;

public class AssemblyLineRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        //Quantum Armor
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumHelmet", 1, GT_Values.W));

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

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBodyarmor", 1, GT_Values.W));

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(QuantumPartHelmet.getMetaID(), 1),
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

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumLeggings", 1, GT_Values.W));

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

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBoots", 1, GT_Values.W));

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

        //Integration without TT
        if (Loader.isModLoaded("tectech")) {

        } else {
            GT_Values.RA.addAssemblylineRecipe(ItemList.Casing_Core_Chamber.get(1L), 1152000, new Object[]{
                    ItemList.Teleporter.get(1L),
                    ItemList.Casing_Dyson_Ring.get(16L),
                    ItemList.Casing_Fusion_Coil.get(16L),
                    ItemList.Field_Generator_UHV.get(4L),
                    ItemList.Sensor_UHV.get(4L),
                    ItemList.Emitter_UHV.get(4L),
                    new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4},
                    GT_OreDictUnificator.get(OrePrefixes.lens, Materials.ReinforcedGlass, 32),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Superconductor, 16)
            }, new FluidStack[]{
                    Materials.UUMatter.getFluid(2000),
                    Materials.Neutronium.getMolten(2592),
                    new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000),
                    Materials.Osmiridium.getMolten(1296)
            }, ItemList.Machine_MultiblockTesseract.get(1L), 8000, 2000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Casing_Fusion_Coil3.get(1L), 1152000, new Object[]{
                    ItemList.Casing_Fusion_Coil3.get(1L),
                    ItemList.Circuit_Nano.get(1L),
                    ItemList.Circuit_Nano.get(1L),
                    ItemList.Circuit_Nano.get(1L),
                    ItemList.Circuit_Nano.get(1L),
                    GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Neutronium, 4L),
                    ItemList.Field_Generator_UHV.get(2L),
                    ItemList.Circuit_Wafer_QPIC.get(64L),
                    ItemList.Circuit_Wafer_QPIC.get(64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorUEV, 32),
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(5760),
                    Materials.Phoenixite.getMolten(2304L)
            }, ItemList.FusionComputer_UEV.get(1L), 1600, 1000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Cover_SolarPanel_UV.get(1, new Object() {
                    }), 1152000, new Object[]{
                            ItemList.Cover_SolarPanel_UV.get(2L),
                            new Object[]{OrePrefixes.circuit.get(Materials.Nano), 8},
                            GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, Materials.Sunnarium, 8),
                            ItemList.Circuit_Silicon_Wafer10.get(4L),
                            ItemList.Circuit_Silicon_Wafer6.get(4L),
                            ItemList.Circuit_Chip_RPico.get(4L),
                            GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 12),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Neutronium, 3),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.PerroxPolymer, 2),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Silicon, 8),
                            GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.ElectrumFlux, 8),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Superconductor, 16),
                            ItemList.Circuit_Chip_QPIC.get(8L)
                    },
                    new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(1890)},
                    ItemList.Cover_SolarPanel_UHV.get(1), 840, 8000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Cover_SolarPanel_UHV.get(1, new Object() {
                    }), 2304000, new Object[]{
                            ItemList.Cover_SolarPanel_UHV.get(1L),
                            new Object[]{OrePrefixes.circuit.get(Materials.Piko), 8},
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Sunnarium, 8),
                            ItemList.Circuit_Chip_Pico.get(8L),
                            GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 16),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Phoenixite, 4),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.PerroxPolymer, 4),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Silicon, 8),
                            GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.ElectrumFlux, 8),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.SuperconductorUEV, 18),
                            ItemList.Circuit_Chip_FPIC.get(12L)
                    },
                    new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(3780)},
                    ItemList.Cover_SolarPanel_UEV.get(1), 960, 32000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Machine_DigitalTransformer_UV.get(1, new Object() {
                    }), 576000, new Object[]{
                            GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1L, 12159),
                            ItemList.Cover_Screen.get(16L),
                            GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Osmium, 2L),
                            ItemList.UHV_Coil.get(1L),
                            ItemList.Circuit_HighEnergyFlow.get(1L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Infinite), 2),
                    },
                    new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(11520)},
                    ItemList.Machine_DigitalTransformer_UHV.get(1L), 1000, 2000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Machine_DigitalTransformer_UHV.get(1, new Object() {
                    }), 1152000, new Object[]{
                            GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1L, 12160),
                            ItemList.Cover_Screen.get(32L),
                            GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Osmium, 2L),
                            ItemList.UEV_Coil.get(4L),
                            ItemList.Circuit_HighEnergyFlow.get(2L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Bio), 2),
                    },
                    new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(23040)},
                    ItemList.Machine_DigitalTransformer_UEV.get(1L), 2000, 8000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Circuit_Biowarecomputer.get(1L), 576000, new ItemStack[]{
                            ItemList.Circuit_Board_Bio_Ultra.get(2L),
                            ItemList.Circuit_Biowarecomputer.get(2L),
                            ItemList.Circuit_Parts_DiodeSMD.get(48L),
                            ItemList.Circuit_Parts_ResistorSMD.get(16L),
                            ItemList.Circuit_Parts_TransistorSMD.get(16L),
                            ItemList.Circuit_Parts_DiodeSMD.get(16L),
                            ItemList.Circuit_Chip_NOR.get(32L),
                            ItemList.Circuit_Chip_Ram.get(64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 32L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 16L),
                    }, new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(1440L),
                            Materials.BioMediumSterilized.getFluid(1440L),
                            new FluidStack(FluidRegistry.getFluid("ic2coolant"), 10000)
                    },
                    ItemList.Circuit_Biowaresupercomputer.get(1L), 4000, 500000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Circuit_Biowaresupercomputer.get(1L), 576000, new ItemStack[]{
                    GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 4L),
                    ItemList.Circuit_Biowaresupercomputer.get(2L),
                    ItemList.UV_Coil.get(16L),
                    ItemList.Circuit_Parts_CapacitorSMD.get(64L),
                    ItemList.Circuit_Parts_ResistorSMD.get(64L),
                    ItemList.Circuit_Parts_TransistorSMD.get(64L),
                    ItemList.Circuit_Parts_DiodeSMD.get(64L),
                    ItemList.Circuit_Chip_Ram.get(64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 32),
                    GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 64),
                    GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64)
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(2880L),
                    Materials.BioMediumSterilized.getFluid(2880L),
                    new FluidStack(FluidRegistry.getFluid("ic2coolant"), 20000)
            }, ItemList.Circuit_Biomainframe.get(1L), 6000, 2000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Hatch_Energy_UV.get(1, new Object() {
                    }), 576000, new Object[]{
                            ItemList.Hull_MAX.get(1L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Superconductor, 2L),
                            ItemList.Circuit_Chip_QPIC.get(2L, new Object() {
                            }),
                            ItemList.Circuit_HighEnergyFlow.get(1L, new Object() {
                            }),
                            new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 2},
                            ItemList.UHV_Coil.get(2L, new Object() {
                            }),
                            new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_NaK_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_Le_2.get(1, new Object() {
                            })},
                            new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_NaK_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_Le_2.get(1, new Object() {
                            })},
                            new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_NaK_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_Le_2.get(1, new Object() {
                            })},
                            ItemList.Electric_Pump_UHV.get(1L, new Object() {
                            })},
                    new FluidStack[]{
                            new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000),
                            Materials.SolderingAlloy.getMolten(5760)},
                    ItemList.Hatch_Energy_MAX.get(1), 1000, 2000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Hatch_Energy_MAX.get(1, new Object() {
                    }), 576000, new Object[]{
                            ItemList.Hull_UEV.get(1L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorUEV, 6L),
                            ItemList.Circuit_Chip_QPIC.get(4L, new Object() {
                            }),
                            ItemList.Circuit_HighEnergyFlow.get(2L, new Object() {
                            }),
                            new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                            ItemList.UEV_Coil.get(2L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_2.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_2.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_2.get(1L, new Object() {
                            }),
                            ItemList.Electric_Pump_UEV.get(1L, new Object() {
                            })},
                    new FluidStack[]{
                            new FluidStack(FluidRegistry.getFluid("ic2coolant"), 32000),
                            Materials.SolderingAlloy.getMolten(11520)},
                    ItemList.Hatch_Energy_UEV.get(1), 1200, 8000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Hatch_Energy_UEV.get(1, new Object() {
                    }), 576000, new Object[]{
                            ItemList.Hull_UIV.get(1L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Neutronium, 8L),
                            ItemList.Circuit_Chip_QPIC.get(8L, new Object() {
                            }),
                            ItemList.Circuit_HighEnergyFlow.get(4L, new Object() {
                            }),
                            new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                            ItemList.UIV_Coil.get(2L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_2.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_2.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_2.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_2.get(1L, new Object() {
                            }),
                            ItemList.Electric_Pump_UEV.get(1L, new Object() {
                            })},
                    new FluidStack[]{
                            new FluidStack(FluidRegistry.getFluid("ic2coolant"), 64000),
                            Materials.SolderingAlloy.getMolten(23040)},
                    ItemList.Hatch_Energy_UIV.get(1), 1400, 32000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Energy_Cluster.get(1), 288000, new ItemStack[]{
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tritanium, 64L),
                    ItemList.Circuit_HighEnergyFlow.get(4L, new Object() {
                    }),
                    GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Infinite), 1L),
                    GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Infinite), 1L),
                    GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Infinite), 1L),
                    GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Infinite), 1L),
                    ItemList.Energy_Cluster.get(8L),
                    ItemList.Field_Generator_UV.get(2),
                    ItemList.Circuit_Wafer_HPIC.get(64),
                    ItemList.Circuit_Wafer_HPIC.get(64),
                    ItemList.Circuit_Parts_DiodeSMD.get(64),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 32),
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(2880),
                    GregTech_API.mIC2Classic ? Materials.Water.getFluid(16000) : new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000)
            }, ItemList.ZPM2.get(1), 3000, 400000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.ZPM2.get(1), 576000, new ItemStack[]{
                    GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 64L),
                    ItemList.Circuit_HighEnergyFlow.get(16L, new Object() {
                    }),
                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 1L),
                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 1L),
                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 1L),
                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 1L),
                    ItemList.ZPM2.get(8),
                    ItemList.Field_Generator_UHV.get(4),
                    ItemList.Circuit_Wafer_UHPIC.get(64),
                    ItemList.Circuit_Wafer_UHPIC.get(64),
                    ItemList.Circuit_Wafer_SoC2.get(32),
                    ItemList.Circuit_Parts_DiodeSMD.get(64),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Neutronium, 64),
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(3760),
                    Materials.Naquadria.getMolten(9000),
                    new FluidStack(FluidRegistry.getFluid("ic2coolant"), 32000)
            }, ItemList.ZPM3.get(1), 4000, 1600000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Hatch_Dynamo_UV.get(1, new Object() {
                    }), 576000, new Object[]{
                            ItemList.Hull_MAX.get(1L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Europium, 2L),//TODO Need new recipe for UHV superconductor
                            ItemList.Circuit_Chip_QPIC.get(2L, new Object() {
                            }),
                            ItemList.Circuit_HighEnergyFlow.get(1L, new Object() {
                            }),
                            new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 2},
                            ItemList.UHV_Coil.get(2L, new Object() {
                            }),
                            new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_NaK_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_Le_2.get(1, new Object() {
                            })},
                            new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_NaK_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_Le_2.get(1, new Object() {
                            })},
                            new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_NaK_6.get(1, new Object() {
                            }), ItemList.Reactor_Coolant_Le_2.get(1, new Object() {
                            })},
                            ItemList.Electric_Pump_UHV.get(1L, new Object() {
                            })},
                    new FluidStack[]{
                            new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000),
                            Materials.SolderingAlloy.getMolten(5760)},
                    ItemList.Hatch_Dynamo_MAX.get(1), 1000, 2000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Hatch_Dynamo_MAX.get(1, new Object() {
                    }), 576000, new Object[]{
                            ItemList.Hull_UEV.get(1L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Diamericiumtitanium, 2L),
                            ItemList.Circuit_Chip_QPIC.get(4L, new Object() {
                            }),
                            ItemList.Circuit_HighEnergyFlow.get(2L, new Object() {
                            }),
                            new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                            ItemList.UEV_Coil.get(2L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_3.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_3.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_3.get(1L, new Object() {
                            }),
                            ItemList.Electric_Pump_UHV.get(1L, new Object() {
                            })},
                    new FluidStack[]{
                            new FluidStack(FluidRegistry.getFluid("ic2coolant"), 32000),
                            Materials.SolderingAlloy.getMolten(11520)},
                    ItemList.Hatch_Dynamo_UEV.get(1), 1200, 8000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Hatch_Dynamo_UEV.get(1, new Object() {
                    }), 576000, new Object[]{
                            ItemList.Hull_UIV.get(1L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Neutronium, 8L),
                            ItemList.Circuit_Chip_QPIC.get(8L, new Object() {
                            }),
                            ItemList.Circuit_HighEnergyFlow.get(4L, new Object() {
                            }),
                            new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                            ItemList.UIV_Coil.get(2L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_3.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_3.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_3.get(1L, new Object() {
                            }),
                            ItemList.Reactor_Coolant_Le_3.get(1L, new Object() {
                            }),
                            ItemList.Electric_Pump_UEV.get(1L, new Object() {
                            })},
                    new FluidStack[]{
                            new FluidStack(FluidRegistry.getFluid("ic2coolant"), 64000),
                            Materials.SolderingAlloy.getMolten(23040)},
                    ItemList.Hatch_Dynamo_UIV.get(1), 1400, 32000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.MysteriousCrystal.get(1, new Object() {
                    }), 576000, new Object[]{
                            ItemList.Circuit_Board_Bio_Ultra.get(1L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Neutronium, 32L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Infinite), 4L),
                            ItemList.Circuit_Parts_MECrystal_Chip_Elite.get(36L, new Object() {
                            }),
                            ItemList.Circuit_Parts_MECrystal_Chip_Elite.get(36L, new Object() {
                            }),
                            ItemList.Circuit_Chip_PPIC.get(64L, new Object() {
                            }),
                            ItemList.Circuit_Parts_DiodeSMD.get(64L, new Object() {
                            }),
                            ItemList.Circuit_Parts_CapacitorSMD.get(64L, new Object() {
                            }),
                            ItemList.Circuit_Parts_ResistorSMD.get(64L, new Object() {
                            }),
                            ItemList.Circuit_Parts_TransistorSMD.get(64L, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine.get(Materials.Neutronium), 64L)},
                    new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(1440)},
                    ItemList.MysteriousCrystalOrb.get(1), 1000, 2000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.MysteriousCrystalOrb.get(1, new Object() {
                    }), 1440000, new ItemStack[]{
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Phoenixite, 16L),
                            ItemList.Circuit_Biomainframe.get(1, new Object() {
                            }),
                            ItemList.Circuit_Biomainframe.get(1, new Object() {
                            }),
                            ItemList.Circuit_Biomainframe.get(1, new Object() {
                            }),
                            ItemList.Circuit_Biomainframe.get(1, new Object() {
                            }),
                            ItemList.MysteriousCrystalOrb.get(8L),
                            ItemList.Field_Generator_UEV.get(2, new Object() {
                            }),
                            ItemList.Circuit_Wafer_SoC3.get(64, new Object() {
                            }),
                            ItemList.Circuit_Wafer_SoC3.get(64, new Object() {
                            }),
                            ItemList.Circuit_Parts_DiodeSMD.get(64, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUEV, 16),},
                    new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(2880), GregTech_API.mIC2Classic ? Materials.Water.getFluid(8000) : new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000)},
                    ItemList.MysteriousCrystalModule.get(1, new Object() {
                    }), 2000, 8000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Circuit_Wetwaresupercomputer.get(1L), 576000, new ItemStack[]{
                    GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 2),
                    ItemList.Circuit_Wetwaresupercomputer.get(2L),
                    ItemList.ZPM_Coil.get(16L),
                    ItemList.Circuit_Parts_CapacitorSMD.get(64L),
                    ItemList.Circuit_Parts_ResistorSMD.get(64L),
                    ItemList.Circuit_Parts_TransistorSMD.get(64L),
                    ItemList.Circuit_Parts_DiodeSMD.get(64L),
                    ItemList.Circuit_Chip_Ram.get(48L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorZPM, 16),
                    GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 64)
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(2880),
                    new FluidStack(FluidRegistry.getFluid("ic2coolant"), 10000),
                    Materials.Radon.getGas(2500),
            }, ItemList.Circuit_Wetwaremainframe.get(1L), 2000, 300000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Circuit_Biomainframe.get(1L), 1152000, new ItemStack[]{
                    GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Infuscolium, 8),
                    ItemList.Circuit_Biomainframe.get(2L),
                    ItemList.Circuit_Parts_CapacitorSMD.get(64L),
                    ItemList.Circuit_Parts_ResistorSMD.get(64L),
                    ItemList.Circuit_Parts_TransistorSMD.get(64L),
                    ItemList.Circuit_Parts_DiodeSMD.get(64L),
                    ItemList.Circuit_Chip_Ram.get(64L),
                    ItemList.Circuit_Chip_NPIC.get(64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Diamericiumtitanium, 16),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Superconductor, 16),
                    GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 64),
                    GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64)
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(3760L),
                    Materials.Naquadria.getMolten(4032L),
                    new FluidStack(FluidRegistry.getFluid("ic2coolant"), 20000)
            }, ItemList.Circuit_Nano.get(1L), 8000, 8000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Circuit_Chip_Pico.get(1L), 2304000, new ItemStack[]{
                    ItemList.Circuit_Board_Crystal_Extreme.get(1L),
                    GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 12),
                    ItemList.Circuit_Chip_Pico.get(4L),
                    ItemList.Circuit_Nano.get(2L),
                    ItemList.Circuit_Parts_TransistorSMD.get(64L),
                    ItemList.Circuit_Parts_ResistorSMD.get(64L),
                    ItemList.Circuit_Parts_CapacitorSMD.get(64L),
                    ItemList.Circuit_Parts_DiodeSMD.get(64L),
                    ItemList.Circuit_Chip_PPIC.get(64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Neutronium, 24),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUEV, 24),
                    GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 16),
                    GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Osmium, 32),
                    GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Neutronium, 16),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Lanthanum, 64)
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(3760L),
                    Materials.UUMatter.getFluid(8000L),
                    Materials.Osmium.getMolten(1152L)
            }, ItemList.Circuit_Piko.get(1L), 10000, 8000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Circuit_Piko.get(1L), 4608000, new ItemStack[]{
                    GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Phoenixite, 16),
                    ItemList.Circuit_Piko.get(8L),
                    ItemList.Circuit_Parts_CapacitorSMD.get(64L),
                    ItemList.Circuit_Parts_DiodeSMD.get(64L),
                    ItemList.Circuit_Parts_TransistorSMD.get(64L),
                    ItemList.Circuit_Parts_ResistorSMD.get(64L),
                    ItemList.Circuit_Chip_QPIC.get(64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Quantium, 32),
                    GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 64),
                    GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Indium, 64),
                    GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Phoenixite, 16),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Lanthanum, 64)
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(3760L),
                    Materials.UUMatter.getFluid(24000L),
                    Materials.Osmium.getMolten(2304L)
            }, ItemList.Circuit_Quantum.get(1L), 20000, 32000000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Motor_UV.get(1, new Object() {
            }), 576000, new ItemStack[]{
                    GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.EuropiumoxideMagnetic, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Infuscolium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Infuscolium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.round, Materials.Infuscolium, 32L),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 2L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(2592),
                    Materials.Lubricant.getFluid(4000),
                    Materials.Naquadria.getMolten(2592)}, ItemList.Electric_Motor_UHV.get(1), 800, 200000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Motor_UHV.get(1, new Object() {
            }), 1152000, new ItemStack[]{
                    GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.EuropiumoxideMagnetic, 4L),
                    GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.round, Materials.Neutronium, 32L),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 4L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(5184),
                    Materials.Lubricant.getFluid(8000),
                    Materials.Quantium.getMolten(2592)}, ItemList.Electric_Motor_UEV.get(1), 1000, 800000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Pump_UV.get(1, new Object() {
            }), 576000, new ItemStack[]{
                    ItemList.Electric_Motor_UHV.get(1, new Object() {
                    }),
                    GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Naquadah, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Infuscolium, 4L),
                    GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Infuscolium, 16L),
                    GT_OreDictUnificator.get(OrePrefixes.ring, Materials.AnySyntheticRubber, 32L),
                    GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.NaquadahAlloy, 4L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 2L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(2592),
                    Materials.Lubricant.getFluid(4000),
                    Materials.Naquadria.getMolten(2592)}, ItemList.Electric_Pump_UHV.get(1), 800, 200000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Pump_UHV.get(1, new Object() {
            }), 1152000, new ItemStack[]{
                    ItemList.Electric_Motor_UEV.get(1, new Object() {
                    }),
                    GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Neutronium, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 4L),
                    GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Neutronium, 16L),
                    GT_OreDictUnificator.get(OrePrefixes.ring, Materials.AnySyntheticRubber, 48L),
                    GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Neutronium, 4L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 4L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(5184),
                    Materials.Lubricant.getFluid(8000),
                    Materials.Quantium.getMolten(2592)}, ItemList.Electric_Pump_UEV.get(1), 1000, 800000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Conveyor_Module_UV.get(1, new Object() {
            }), 576000, new ItemStack[]{
                    ItemList.Electric_Motor_UHV.get(2, new Object() {
                    }),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Infuscolium, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Infuscolium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.round, Materials.Infuscolium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 2L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(2592),
                    Materials.Lubricant.getFluid(4000),
                    Materials.StyreneButadieneRubber.getMolten(11520),
                    Materials.Naquadria.getMolten(2592)}, ItemList.Conveyor_Module_UHV.get(1), 800, 200000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Conveyor_Module_UHV.get(1, new Object() {
            }), 1152000, new ItemStack[]{
                    ItemList.Electric_Motor_UEV.get(2, new Object() {
                    }),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.round, Materials.Neutronium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 4L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(5184),
                    Materials.Lubricant.getFluid(8000),
                    Materials.StyreneButadieneRubber.getMolten(23040),
                    Materials.Quantium.getMolten(2592)}, ItemList.Conveyor_Module_UEV.get(1), 1000, 800000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Piston_UV.get(1, new Object() {
            }), 576000, new ItemStack[]{
                    ItemList.Electric_Motor_UHV.get(1, new Object() {
                    }),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Infuscolium, 6L),
                    GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Infuscolium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.round, Materials.Infuscolium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Infuscolium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.gear, Materials.NaquadahAlloy, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.NaquadahAlloy, 4L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 4L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(2592),
                    Materials.Lubricant.getFluid(4000),
                    Materials.Naquadria.getMolten(2592)}, ItemList.Electric_Piston_UHV.get(1), 800, 200000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Piston_UHV.get(1, new Object() {
            }), 1152000, new ItemStack[]{
                    ItemList.Electric_Motor_UEV.get(1, new Object() {
                    }),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 6L),
                    GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.round, Materials.Neutronium, 64L),
                    GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Neutronium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Neutronium, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Neutronium, 4L),
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 8L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(5184),
                    Materials.Lubricant.getFluid(8000),
                    Materials.Quantium.getMolten(2592)}, ItemList.Electric_Piston_UEV.get(1), 1000, 800000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Robot_Arm_UV.get(1, new Object() {
            }), 576000, new Object[]{
                    GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Infuscolium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.gear, Materials.NaquadahAlloy, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.NaquadahAlloy, 6L),
                    ItemList.Electric_Motor_UHV.get(2, new Object() {
                    }),
                    ItemList.Electric_Piston_UHV.get(1, new Object() {
                    }),
                    new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 2},
                    new Object[]{OrePrefixes.circuit.get(Materials.Superconductor), 4},
                    new Object[]{OrePrefixes.circuit.get(Materials.Ultimate), 8},
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 6L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(4608),
                    Materials.Lubricant.getFluid(4000),
                    Materials.Naquadria.getMolten(2592)}, ItemList.Robot_Arm_UHV.get(1), 800, 200000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Robot_Arm_UHV.get(1, new Object() {
            }), 1152000, new Object[]{
                    GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 8L),
                    GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Neutronium, 2L),
                    GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Neutronium, 6L),
                    ItemList.Electric_Motor_UEV.get(2, new Object() {
                    }),
                    ItemList.Electric_Piston_UEV.get(1, new Object() {
                    }),
                    new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                    new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4},
                    new Object[]{OrePrefixes.circuit.get(Materials.Superconductor), 8},
                    GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 12L)}, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(9216),
                    Materials.Lubricant.getFluid(8000),
                    Materials.Quantium.getMolten(2592)}, ItemList.Robot_Arm_UEV.get(1), 1000, 800000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Emitter_UV.get(1, new Object() {
                    }), 576000, new Object[]{
                            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Americium, 1L),
                            ItemList.Electric_Motor_UHV.get(1, new Object() {
                            }),
                            ItemList.Emitter_UV.get(1, new Object() {
                            }),
                            ItemList.Emitter_ZPM.get(2, new Object() {
                            }),
                            ItemList.Emitter_LuV.get(4, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Infuscolium, 8L),
                            new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4},
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 7L)}, new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(4608),
                            Materials.Naquadria.getMolten(2592)},
                    ItemList.Emitter_UHV.get(1), 800, 200000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Emitter_UHV.get(1, new Object() {
                    }), 1152000, new Object[]{
                            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                            ItemList.Electric_Motor_UEV.get(1, new Object() {
                            }),
                            ItemList.Emitter_UHV.get(1, new Object() {
                            }),
                            ItemList.Emitter_UV.get(2, new Object() {
                            }),
                            ItemList.Emitter_ZPM.get(4, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 8L),
                            new Object[]{OrePrefixes.circuit.get(Materials.Bio), 4},
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Americium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Americium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 14L)}, new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(9216),
                            Materials.Quantium.getMolten(2592)},
                    ItemList.Emitter_UEV.get(1), 1000, 800000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Sensor_UV.get(1, new Object() {
                    }), 576000, new Object[]{
                            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Americium, 1L),
                            ItemList.Electric_Motor_UHV.get(1, new Object() {
                            }),
                            ItemList.Sensor_UV.get(1, new Object() {
                            }),
                            ItemList.Sensor_ZPM.get(2, new Object() {
                            }),
                            ItemList.Sensor_LuV.get(4, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Infuscolium, 8L),
                            new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4},
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 7L)}, new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(4608),
                            Materials.Naquadria.getMolten(2592)},
                    ItemList.Sensor_UHV.get(1), 800, 200000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Sensor_UHV.get(1, new Object() {
                    }), 1152000, new Object[]{
                            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                            ItemList.Electric_Motor_UEV.get(1, new Object() {
                            }),
                            ItemList.Sensor_UHV.get(1, new Object() {
                            }),
                            ItemList.Sensor_UV.get(2, new Object() {
                            }),
                            ItemList.Sensor_ZPM.get(4, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 8L),
                            new Object[]{OrePrefixes.circuit.get(Materials.Bio), 4},
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Americium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Americium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 14L)}, new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(9216),
                            Materials.Quantium.getMolten(2592)},
                    ItemList.Sensor_UEV.get(1), 1000, 800000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Field_Generator_UV.get(1, new Object() {
                    }), 576000, new Object[]{
                            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Americium, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Infuscolium, 6L),
                            ItemList.Emitter_UHV.get(2, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Infinite, 4L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 8L)},
                    new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(4608),
                            Materials.Naquadria.getMolten(2592)},
                    ItemList.Field_Generator_UHV.get(1), 800, 200000);

            GT_Values.RA.addAssemblylineRecipe(ItemList.Field_Generator_UHV.get(1, new Object() {
                    }), 1152000, new Object[]{
                            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 6L),
                            ItemList.Emitter_UEV.get(2, new Object() {
                            }),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Bio, 4L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 16L)},
                    new FluidStack[]{
                            Materials.SolderingAlloy.getMolten(9216),
                            Materials.Quantium.getMolten(2592)},
                    ItemList.Field_Generator_UEV.get(1), 1000, 800000);


        }
    }
}
