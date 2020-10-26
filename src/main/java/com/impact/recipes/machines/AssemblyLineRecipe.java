package com.impact.recipes.machines;

import com.impact.common.item.Core_Items2;
import com.impact.mods.GregTech.GT_ItemList;
import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

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
                GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 2L)
        },
                new FluidStack[]{
                Materials.SolderingAlloy.getMolten(144),
                Materials.Lubricant.getFluid(250)
        },
                ItemList.Electric_Motor_LuV.get(1), 600, 6000);

        GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Motor_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.EuropiumoxideMagnetic, 1L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Osmiridium, 4L),
                GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Desh, 6L),
                GT_OreDictUnificator.get(OrePrefixes.round, Materials.Desh, 24L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 64L),
                GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.Naquadah, 2L)
        },
                new FluidStack[]{
                Materials.SolderingAlloy.getMolten(288),
                Materials.Lubricant.getFluid(750)
        },
                ItemList.Electric_Motor_ZPM.get(1, new Object[]{}), 600, 24000);

        GT_Values.RA.addAssemblylineRecipe(ItemList.Electric_Motor_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.EuropiumoxideMagnetic, 1L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Tritanium, 6L),
                GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Oriharukon, 8L),
                GT_OreDictUnificator.get(OrePrefixes.round, Materials.Oriharukon, 32L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 2L)
        },
                new FluidStack[]{
                Materials.SolderingAlloy.getMolten(1296),
                Materials.Lubricant.getFluid(2000),
                Materials.Naquadria.getMolten(1296)
                },
                ItemList.Electric_Motor_UV.get(1, new Object[]{}), 600, 100000);


        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(3, 1), 144000, new Object[]{
                        CoreItems2.getRecipe(21, 1),
                        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 4L, 0),
                        CoreItems2.getRecipe(11, 14),
                        CoreItems2.getRecipe(31, 6),
                        CoreItems2.getRecipe(36, 4),
                        CoreItems2.getRecipe(133, 4),
                        CoreItems2.getRecipe(26, 2)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576),
                        Materials.Lubricant.getFluid(500),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 500)
                },
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier4Rocket", 1L, 0), 3000, 16384);

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(4, 1), 288000, new Object[]{
                        CoreItems2.getRecipe(22, 1),
                        CoreItems2.getRecipe(11, 6),
                        CoreItems2.getRecipe(12, 16),
                        CoreItems2.getRecipe(32, 6),
                        CoreItems2.getRecipe(37, 4),
                        CoreItems2.getRecipe(134, 2),
                        CoreItems2.getRecipe(27, 3)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1152),
                        Materials.Lubricant.getFluid(1000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000),
                        Materials.Duranium.getMolten(576)
                },
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier5Rocket", 1L, 0), 4000, 65536);

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(5, 1), 432000, new Object[]{
                        CoreItems2.getRecipe(23, 1),
                        CoreItems2.getRecipe(12, 8),
                        CoreItems2.getRecipe(13, 18),
                        CoreItems2.getRecipe(33, 8),
                        CoreItems2.getRecipe(38, 6),
                        CoreItems2.getRecipe(134, 4),
                        CoreItems2.getRecipe(28, 4)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2304),
                        Materials.Lubricant.getFluid(2000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 2000),
                        Materials.Tritanium.getMolten(1152)
                },
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier6Rocket", 1L, 0), 5000, 262144);

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(6, 1), 576000, new Object[]{
                        CoreItems2.getRecipe(24, 1),
                        CoreItems2.getRecipe(13, 10),
                        CoreItems2.getRecipe(14, 20),
                        CoreItems2.getRecipe(34, 10),
                        CoreItems2.getRecipe(39, 8),
                        CoreItems2.getRecipe(135, 2),
                        CoreItems2.getRecipe(29, 5)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(4608),
                        Materials.Lubricant.getFluid(4000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000),
                        Materials.Neutronium.getMolten(2304)
                },
                GT_ModHandler.getModItem("GalaxySpace", "item.Tier7Rocket", 1L, 0), 6000, 1000000);

        GT_Values.RA.addAssemblylineRecipe(CoreItems2.getRecipe(7, 1), 1152000, new Object[]{
                        CoreItems2.getRecipe(25, 1),
                        CoreItems2.getRecipe(14, 12),
                        CoreItems2.getRecipe(15, 24),
                        CoreItems2.getRecipe(35, 12),
                        CoreItems2.getRecipe(40, 10),
                        CoreItems2.getRecipe(135, 4),
                        CoreItems2.getRecipe(30, 6)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(9216),
                        Materials.Lubricant.getFluid(8000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 8000),
                        Materials.Phoenixite.getMolten(4608)
                },
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
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 32)
                },
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
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 48)
                },
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
                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSE, 64)
                },
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
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Desh, 16)
                },
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
                        new Object[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 16)},
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.NaquadahAlloy, 8),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.NaquadahAlloy, 8),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Diamond, 16),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Diamond, 16)
                },
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
                        new Object[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4)},
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
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HSSG, 16)
                },
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
                        new Object[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4)},
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
                        new Object[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 8)},
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
