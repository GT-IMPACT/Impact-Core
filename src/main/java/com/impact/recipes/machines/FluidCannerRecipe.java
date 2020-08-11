package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;

import static com.impact.item.Core_List_Items.*;

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


        /* ================================= end GT =================================*/

    }
}
