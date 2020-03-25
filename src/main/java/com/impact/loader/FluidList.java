package com.impact.loader;

import com.impact.impact;
import com.impact.System.Refstrings;
import eu.usrv.yamcore.fluids.ModFluidManager;
import eu.usrv.yamcore.fluids.ModSimpleBaseFluid;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public enum FluidList {
	
	// Do not delete this
		EndOfList(null, null);
	
	public ModSimpleBaseFluid Fluid;
	FluidList(ModSimpleBaseFluid pFluid, String pCreativeTabName)
	{
		Fluid = pFluid;
		if (Fluid != null)
		{
			Fluid.SetModID(Refstrings.MODID);
			Fluid.setCreativeTabName(pCreativeTabName);
		}
	}
	
	public static boolean AddToItemManager(ModFluidManager pFluidManager)
	{
		Fluid t = null;
		boolean tResult = true;
		for (FluidList il : FluidList.values())
		{
			if (il.Fluid != null) {
                if (!pFluidManager.AddItemToManagedRegistry(il.Fluid)) {
                    impact.Logger.error(String.format("Fluid [%s] failed to register", il.toString()));
                    tResult = false;
                }
            }
		}
		
		return tResult;
	}

	public FluidStack getFluidStack(){
		return getFluidStack(1000L);
	}

	public FluidStack getFluidStack(long ammount){
		return new FluidStack(this.Fluid.getFluid(),(int)ammount);
	}
}