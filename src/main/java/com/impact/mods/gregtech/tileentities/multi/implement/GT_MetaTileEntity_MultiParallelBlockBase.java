package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.api.recipe.MultiBlockRecipeBuilder;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.*;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class GT_MetaTileEntity_MultiParallelBlockBase<MULTIS extends GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>>
		extends GTMTE_Impact_ReceiverBase<MULTIS> {
	
	public final HashSet<GTMTE_ParallelHatch_Input> sParallHatchesIn = new HashSet<>();
	public final HashSet<GTMTE_ParallelHatch_Output> sParallHatchesOut = new HashSet<>();
	
	public boolean isConnectParallel = false;
	public int mParallel = 0;
	public int mCheckParallelCurrent = 0;
	
	public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional) {
		super(aID, aName, aNameRegional);
	}
	
	public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional, int slots) {
		super(aID, aName, aNameRegional, slots);
	}
	
	public GT_MetaTileEntity_MultiParallelBlockBase(final String aName, int slots) {
		super(aName, slots);
	}
	
	public GT_MetaTileEntity_MultiParallelBlockBase(final String aName) {
		super(aName);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		aNBT.setInteger("mParallel", this.mParallel);
		aNBT.setInteger("mCheckParallelCurrent", this.mCheckParallelCurrent);
		aNBT.setBoolean("isConnectParallel", this.isConnectParallel);
		super.saveNBTData(aNBT);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		this.mParallel             = aNBT.getInteger("mParallel");
		this.mCheckParallelCurrent = aNBT.getInteger("mCheckParallelCurrent");
		this.isConnectParallel     = aNBT.getBoolean("isConnectParallel");
		super.loadNBTData(aNBT);
	}
	
	@Override
	public String[] addInfoData() {
		final ArrayList<String> ll = new ArrayList<>();
		ll.add(mParallel > 1 ? "Parallel Point: " + mParallel : "Parallel not found");
		final String[] a = new String[ll.size()];
		return ll.toArray(a);
	}
	
	public boolean addParallHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity == null) {
				return false;
			} else if (aMetaTileEntity instanceof GTMTE_ParallelHatch_Input) {
				((GTMTE_ParallelHatch_Input) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return sParallHatchesIn.add((GTMTE_ParallelHatch_Input) aMetaTileEntity);
			} else {
				return false;
			}
		}
	}
	
	public int getParallelCurrent() {
		return mParallel;
	}
	
	public int getParallel() {
		return mParallel;
	}
	
	public void setParallel(int setParallel) {
		mParallel = setParallel;
	}
	
	@Override
	public boolean hasConnectForce() {
		return false;
	}
	
	@Override
	public void clearHatches() {
		super.clearHatches();
		sParallHatchesOut.clear();
		sParallHatchesIn.clear();
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide() && aTick % 100 == 0) {
			int maxParallel = 1;
			boolean isDebug = false;
			
			for (GTMTE_ParallelHatch_Input ph : sParallHatchesIn) {
				
				isConnectParallel = ph.isConnected;
				ph.machineName    = getLocalName();
				
				maxParallel = ph.getParallel();
				isDebug     = ph.isDebug;
				
			}
			if (isDebug) {
				isConnectParallel = true;
				isConnected       = true;
				setParallel(maxParallel);
				return;
			}
			if (!isConnectParallel || !isConnected) {
				maxParallel = 1;
			}
			setParallel(maxParallel);
		}
	}
	
	@Override
	public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
		if (sParallHatchesIn.size() > 0 && !isConnectParallel) {
			return false;
		}
		return recipeBuilder
				.checkSizeHatches(true, true, indexBus)
				.checkVoltage()
				.checkRecipeMap(indexBus)
				.checkInputEqualsParallel(indexBus, true)
				.checkWorldProperties(true, true)
				.checkEfficiency()
				.checkConsumptionParallel()
				.checkOutputs(false)
				.build();
	}
	
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		return super.checkRecipe(itemStack);
	}
}