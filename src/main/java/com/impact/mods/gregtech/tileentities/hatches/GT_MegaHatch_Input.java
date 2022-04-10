package com.impact.mods.gregtech.tileentities.hatches;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.util.GT_Utility;
import net.minecraftforge.fluids.FluidStack;

public class GT_MegaHatch_Input extends GT_MetaTileEntity_Hatch_Input {
	
	private final static int VALUE = 2_000_000_000;
	
	public GT_MegaHatch_Input(int aID, String aName) {
		super(aID, "impact.basic.mega_hatch.input", aName, 6);
	}
	
	public GT_MegaHatch_Input(String aName, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, 6, aDescription, aTextures);
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MegaHatch_Input(mName, mDescriptionArray, mTextures);
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
		return new String[]{"Steam input for Nuclear Reactor", "Capacity: " + GT_Utility.formatNumbers(VALUE) + "L"};
	}
}
