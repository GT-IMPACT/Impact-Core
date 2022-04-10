package com.impact.mods.gregtech.tileentities.hatches;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.util.GT_Utility;
import net.minecraftforge.fluids.FluidStack;

public class GT_MegaHatch_Output extends GT_MetaTileEntity_Hatch_Output {
	
	private final static int VALUE = 2_000_000_000;
	
	public GT_MegaHatch_Output(int aID, String aName) {
		super(aID, "impact.basic.mega_hatch.output", aName, 6);
	}
	
	public GT_MegaHatch_Output(String aName, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, 6, aDescription, aTextures);
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MegaHatch_Output(mName, mDescriptionArray, mTextures);
	}
	
	@Override
	public int getCapacity() {
		return VALUE;
	}
	
	@Override
	public boolean isFluidInputAllowed(FluidStack aFluid) {
		String fluidName = aFluid.getUnlocalizedName();
		return fluidName.equals("fluid.steam") || fluidName.equals("ic2.fluidSteam") || fluidName.equals("ic2.fluidSuperheatedSteam");
	}
	
	@Override
	public String[] getDescription() {
		return new String[]{"Steam output for Nuclear Reactor", "Capacity: " + GT_Utility.formatNumbers(VALUE) + "L"};
	}
}
