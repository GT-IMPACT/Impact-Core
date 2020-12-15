package com.impact.recipes.machines;

import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_ModHandler;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import static com.impact.common.item.Core_List_Items.*;

public class FluidCannerRecipe implements Runnable{
    @Override
    public void run(){

        final Core_Items2 CoreItems2 = Core_Items2.getInstance();

/** ================================= start GT =================================*/

        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(Empty180SpCell.getMetaID(), 1), ItemList.Reactor_Coolant_Le_1.get(1L), Materials.SuperCoolant.getFluid(1000L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(Empty360SpCell.getMetaID(), 1), ItemList.Reactor_Coolant_Le_2.get(1L), Materials.SuperCoolant.getFluid(2000L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(Empty540SpCell.getMetaID(), 1), ItemList.Reactor_Coolant_Le_3.get(1L), Materials.SuperCoolant.getFluid(3000L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(Empty1080SpCell.getMetaID(), 1), ItemList.Reactor_Coolant_Le_6.get(1L), Materials.SuperCoolant.getFluid(6000L), GT_Values.NF);

        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(143, 1), ItemList.Reactor_Coolant_He_1.get(1L), Materials.Helium.getGas(1000L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(144, 1), ItemList.Reactor_Coolant_He_3.get(1L), Materials.Helium.getGas(3000L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(145, 1), ItemList.Reactor_Coolant_He_6.get(1L), Materials.Helium.getGas(6000L), GT_Values.NF);

        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(143, 1), ItemList.Reactor_Coolant_NaK_1.get(1L), Materials.SodiumPotassium.getFluid(1000L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(144, 1), ItemList.Reactor_Coolant_NaK_3.get(1L), Materials.SodiumPotassium.getFluid(3000L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(145, 1), ItemList.Reactor_Coolant_NaK_6.get(1L), Materials.SodiumPotassium.getFluid(6000L), GT_Values.NF);

        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(143, 1), GT_ModHandler.getModItem("IC2", "reactorCoolantSimple", 1L, 1), new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(144, 1), GT_ModHandler.getModItem("IC2", "reactorCoolantTriple", 1L, 1), new FluidStack(FluidRegistry.getFluid("ic2coolant"), 3000), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(CoreItems2.getRecipe(145, 1), GT_ModHandler.getModItem("IC2", "reactorCoolantSix", 1L, 1), new FluidStack(FluidRegistry.getFluid("ic2coolant"), 6000), GT_Values.NF);

        /* ================================= end GT =================================*/

        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankLightFull", 1L, 1800), GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankLightFull", 1L, 0), Materials.Oxygen.getGas(1800L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankMedFull", 1L, 3600), GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankMedFull", 1L, 0), Materials.Oxygen.getGas(3600L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankHeavyFull", 1L, 5400), GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankHeavyFull", 1L, 0), Materials.Oxygen.getGas(5400L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankt4", 1L, 7200), GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankt4", 1L, 0), Materials.Oxygen.getGas(7200L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankt5", 1L, 9000), GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankt5", 1L, 0), Materials.Oxygen.getGas(9000L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t4", 1L, 10800), GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t4", 1L, 0), Materials.Oxygen.getGas(10800L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t5", 1L, 12600), GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t5", 1L, 0), Materials.Oxygen.getGas(12600L), GT_Values.NF);
        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t6", 1L, 14400), GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t6", 1L, 0), Materials.Oxygen.getGas(14400L), GT_Values.NF);

        GT_Values.RA.addFluidCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.fuelCanisterPartial", 1L, 1001), GT_ModHandler.getModItem("GalacticraftCore", "item.fuelCanisterPartial", 1L, 1), new FluidStack(ItemList.sRocketFuel,1000), GT_Values.NF);


    }
}
