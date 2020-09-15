package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
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
import static com.impact.loader.ItemRegistery.*;

public class AssemblyLineRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        //Motors
        GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Motor_IV.get(1, new Object(){}),144000,new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.NeodymiumMagnetic, 1L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSS, 2L),
                GT_OreDictUnificator.get(OrePrefixes.ring, Materials.MeteoricSteel, 4L),
                GT_OreDictUnificator.get(OrePrefixes.round, Materials.MeteoricSteel, 16L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 64L),
                GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(144),
                Materials.Lubricant.getFluid(250)}, ItemList.Electric_Motor_LuV.get(1), 600, 6000);

        GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Motor_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.EuropiumoxideMagnetic, 1L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Osmiridium, 4L),
                GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Desh, 6L),
                GT_OreDictUnificator.get(OrePrefixes.round, Materials.Desh, 24L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 64L),
                GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.Naquadah, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(288),
                Materials.Lubricant.getFluid(750)}, ItemList.Electric_Motor_ZPM.get(1, new Object[]{}), 600, 24000);

        GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Motor_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.EuropiumoxideMagnetic, 1L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Tritanium, 6L),
                GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Oriharukon, 8L),
                GT_OreDictUnificator.get(OrePrefixes.round, Materials.Oriharukon, 32L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(1296),
                Materials.Lubricant.getFluid(2000),
                Materials.Naquadria.getMolten(1296)}, ItemList.Electric_Motor_UV.get(1, new Object[]{}), 600, 100000);


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
                            ItemList.Circuit_Parts_DiodeASMD.get(24L),
                            ItemList.Circuit_Parts_ResistorASMD.get(8L),
                            ItemList.Circuit_Parts_TransistorASMD.get(8L),
                            ItemList.Circuit_Parts_DiodeASMD.get(8L),
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
                    ItemList.Circuit_Parts_CapacitorASMD.get(32L),
                    ItemList.Circuit_Parts_ResistorASMD.get(32L),
                    ItemList.Circuit_Parts_TransistorASMD.get(32L),
                    ItemList.Circuit_Parts_DiodeASMD.get(32L),
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
                    ItemList.Circuit_Parts_DiodeASMD.get(48),
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
                    ItemList.Circuit_Parts_DiodeASMD.get(64),
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
                            ItemList.Circuit_Parts_DiodeASMD.get(32L, new Object() {
                            }),
                            ItemList.Circuit_Parts_CapacitorASMD.get(32L, new Object() {
                            }),
                            ItemList.Circuit_Parts_ResistorASMD.get(32L, new Object() {
                            }),
                            ItemList.Circuit_Parts_TransistorASMD.get(32L, new Object() {
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
                            ItemList.Circuit_Parts_DiodeASMD.get(32, new Object() {
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
                    ItemList.Circuit_Parts_CapacitorASMD.get(32L),
                    ItemList.Circuit_Parts_ResistorASMD.get(32L),
                    ItemList.Circuit_Parts_TransistorASMD.get(32L),
                    ItemList.Circuit_Parts_DiodeASMD.get(32L),
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
                    ItemList.Circuit_Parts_CapacitorASMD.get(32L),
                    ItemList.Circuit_Parts_ResistorASMD.get(32L),
                    ItemList.Circuit_Parts_TransistorASMD.get(32L),
                    ItemList.Circuit_Parts_DiodeASMD.get(32L),
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
                    ItemList.Circuit_Parts_TransistorASMD.get(48L),
                    ItemList.Circuit_Parts_ResistorASMD.get(48L),
                    ItemList.Circuit_Parts_CapacitorASMD.get(48L),
                    ItemList.Circuit_Parts_DiodeASMD.get(48L),
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
                    ItemList.Circuit_Parts_CapacitorASMD.get(64L),
                    ItemList.Circuit_Parts_DiodeASMD.get(64L),
                    ItemList.Circuit_Parts_TransistorASMD.get(64L),
                    ItemList.Circuit_Parts_ResistorASMD.get(64L),
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

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(3, 1), 144000, new Object[]{
                        CoreItems2.getRecipe(21, 1),
                        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 4L, 0),
                        CoreItems2.getRecipe(11, 14),
                        CoreItems2.getRecipe(31, 6),
                        CoreItems2.getRecipe(36, 4),
                        CoreItems2.getRecipe(133, 4),
                        CoreItems2.getRecipe(26, 2)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576),
                        Materials.Lubricant.getFluid(500),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 500)},
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier4Rocket", 1L, 0), 3000, 16384);

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(4, 1), 288000, new Object[]{
                        CoreItems2.getRecipe(22, 1),
                        CoreItems2.getRecipe(11, 6),
                        CoreItems2.getRecipe(12, 16),
                        CoreItems2.getRecipe(32, 6),
                        CoreItems2.getRecipe(37, 4),
                        CoreItems2.getRecipe(134, 2),
                        CoreItems2.getRecipe(27, 3)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1152),
                        Materials.Lubricant.getFluid(1000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000),
                        Materials.Duranium.getMolten(576)},
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier5Rocket", 1L, 0), 4000, 65536);

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(5, 1), 432000, new Object[]{
                        CoreItems2.getRecipe(23, 1),
                        CoreItems2.getRecipe(12, 8),
                        CoreItems2.getRecipe(13, 18),
                        CoreItems2.getRecipe(33, 8),
                        CoreItems2.getRecipe(38, 6),
                        CoreItems2.getRecipe(134, 4),
                        CoreItems2.getRecipe(28, 4)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2304),
                        Materials.Lubricant.getFluid(2000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 2000),
                        Materials.Tritanium.getMolten(1152)},
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier6Rocket", 1L, 0), 5000, 262144);

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(6, 1), 576000, new Object[]{
                        CoreItems2.getRecipe(24, 1),
                        CoreItems2.getRecipe(13, 10),
                        CoreItems2.getRecipe(14, 20),
                        CoreItems2.getRecipe(34, 10),
                        CoreItems2.getRecipe(39, 8),
                        CoreItems2.getRecipe(135, 2),
                        CoreItems2.getRecipe(29, 5)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(4608),
                        Materials.Lubricant.getFluid(4000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000),
                        Materials.Neutronium.getMolten(2304)},
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier7Rocket", 1L, 0), 6000, 1000000);

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(7, 1), 1152000, new Object[]{
                        CoreItems2.getRecipe(25, 1),
                        CoreItems2.getRecipe(14, 12),
                        CoreItems2.getRecipe(15, 24),
                        CoreItems2.getRecipe(35, 12),
                        CoreItems2.getRecipe(40, 10),
                        CoreItems2.getRecipe(135, 4),
                        CoreItems2.getRecipe(30, 6)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(9216),
                        Materials.Lubricant.getFluid(8000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 8000),
                        Materials.Phoenixite.getMolten(4608)},
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier8Rocket", 1L, 0), 7000, 4000000);

        //UpgradeCasingT2
        GT_Values.RA.addAssemblylineRecipe(GT_ItemList.UpgradeCasingT1.get(1L), 144000, new Object[]{
                ItemList.Hull_LuV.get(1L),
                GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 0),
                GT_ItemList.UpgradeCasingT1.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HastelloyN, 16),
                ItemList.Electric_Motor_LuV.get(4L),
                ItemList.Electric_Piston_LuV.get(4L),
                ItemList.Conveyor_Module_LuV.get(4L),
                ItemList.Robot_Arm_LuV.get(4L),
                new Object[]{OrePrefixes.circuit.get(Materials.Master), 8},
                GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.YttriumBariumCuprate, 16),
                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Chrome, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 32)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1152),
                        Materials.Lubricant.getFluid(1000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000)
                },
                GT_ItemList.UpgradeCasingT2.get(1L), 120 * 20, 30720);

        //UpgradeCasingT3
        GT_Values.RA.addAssemblylineRecipe(GT_ItemList.UpgradeCasingT2.get(1L), 288000, new Object[]{
                ItemList.Hull_ZPM.get(1L),
                GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 1),
                GT_ItemList.UpgradeCasingT2.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lafium, 16),
                ItemList.Electric_Motor_ZPM.get(4L),
                ItemList.Electric_Piston_ZPM.get(4L),
                ItemList.Conveyor_Module_ZPM.get(4L),
                ItemList.Robot_Arm_ZPM.get(4L),
                new Object[]{OrePrefixes.circuit.get(Materials.Ultimate), 8},
                GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Naquadah, 16),
                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Iridium, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 48)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2304),
                        Materials.Lubricant.getFluid(2000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 2000)
                },
                GT_ItemList.UpgradeCasingT3.get(1L), 160 * 20, 122880);

        //UpgradeCasingT4
        GT_Values.RA.addAssemblylineRecipe(GT_ItemList.UpgradeCasingT3.get(1L), 576000, new Object[]{
                ItemList.Hull_UV.get(1L),
                GT_ModHandler.getModItem("extracells", "craftingstorage", 1, 2),
                GT_ItemList.UpgradeCasingT3.get(4L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.CinobiteA243, 16),
                ItemList.Electric_Motor_UV.get(4L),
                ItemList.Electric_Piston_UV.get(4L),
                ItemList.Conveyor_Module_UV.get(4L),
                ItemList.Robot_Arm_UV.get(4L),
                new Object[]{OrePrefixes.circuit.get(Materials.Superconductor), 8},
                GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.NaquadahAlloy, 16),
                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Osmium, 16),
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(4608),
                        Materials.Lubricant.getFluid(4000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000)
                },
                GT_ItemList.UpgradeCasingT4.get(1L), 200 * 20, 500000);

        //Naquadah Chamber Casing
        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(163, 1), 144000, new Object[]{
                        GT_ItemList.NqCasing.get(1),
                        CoreItems2.getRecipe(163, 6),
                        ItemList.Electric_Pump_LuV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Europium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Plutonium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lead, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 10),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Desh, 16)},
                new FluidStack[]{
                        Materials.Trinium.getMolten(1296),
                        Materials.Osmium.getMolten(1296),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 2000),
                        Materials.Argon.getGas(1000)
                },
                new ItemStack(InsideBlock, 1, 0), 120 * 20, 10000);

        //Tether Core
        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(162, 1), 156000, new Object[]{
                        CoreItems2.getRecipe(162, 6),
                        ItemList.Electric_Piston_LuV.get(8L),
                        ItemList.Emitter_LuV.get(8L),
                        ItemList.Sensor_LuV.get(8L),
                        CoreItems2.getRecipe(50, 10),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 16),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.NaquadahAlloy, 8),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.NaquadahAlloy, 8),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Diamond, 16),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Diamond, 16)},
                new FluidStack[]{
                        Materials.Duranium.getMolten(1296),
                        Materials.Osmiridium.getMolten(1296),
                        Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid.getMolten(1296),
                        Materials.NiobiumTitanium.getMolten(1296)
                },
                new ItemStack(NqTetherBlock, 1, 0), 160 * 20, 14576);

        //Heavy Metal Cyclon
        GT_Values.RA.addAssemblylineRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Inconel792, 1), 156000, new Object[]{
                        ItemList.Hull_LuV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Inconel792, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
                        GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L),
                        ItemList.Electric_Motor_LuV.get(4L),
                        ItemList.Electric_Pump_LuV.get(4L),
                        ItemList.Conveyor_Module_LuV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Inconel792, 8),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSS, 4),
                        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.TiBetaC, 8),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TiBetaC, 8),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSS, 8),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 16)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1152),
                        Materials.Lubricant.getFluid(1000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000),
                        Materials.Osmiridium.getMolten(1152)
                },
                GT_ItemList.Heavy_Metal_Cyclone.get(1L), 160 * 20, 30720);

        //Naquadah Generator
        GT_Values.RA.addAssemblylineRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeHuge, Materials.MaragingSteel300, 1), 172000, new Object[]{
                        GT_ModHandler.getModItem("impact", "impact_inside_block", 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.MaragingSteel300, 4),
                        ItemList.Electric_Pump_LuV.get(4L),
                        ItemList.Field_Generator_LuV.get(4L),
                        ItemList.Sensor_LuV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorLuV, 8),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Ruby, 8)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1152),
                        Materials.Lubricant.getFluid(1000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000),
                        Materials.Trinium.getMolten(1152)
                },
                GT_ItemList.Naquadah_Liquid_multi.get(1L), 240 * 20, 26780);

        //Naquadah Enriched Generator
        GT_Values.RA.addAssemblylineRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Inconel690, 1), 256000, new Object[]{
                        GT_ModHandler.getModItem("impact", "impact_nqtether_block", 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Inconel690, 6),
                        ItemList.Electric_Pump_ZPM.get(6L),
                        ItemList.Field_Generator_ZPM.get(8L),
                        ItemList.Sensor_ZPM.get(8L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorZPM, 16),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Sapphire, 16)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2304),
                        Materials.Lubricant.getFluid(2000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 2000),
                        Materials.Oriharukon.getMolten(2304)
                },
                GT_ItemList.Naquadah_Liquid_Enriched.get(1L), 320 * 20, 118236);

    }
}
