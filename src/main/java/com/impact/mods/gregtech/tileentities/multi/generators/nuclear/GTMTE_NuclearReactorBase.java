package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import com.impact.mods.gregtech.gui.GT_Container_NuclearReactor;
import com.impact.mods.gregtech.gui.GUI_NuclearReactor;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

import static com.impact.mods.gregtech.enums.Texture.Icons.REACTOR_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.REACTOR_OVERLAY_ACTIVE;
import static com.impact.util.Utilits.getFluidStack;

public abstract class GTMTE_NuclearReactorBase extends GT_MetaTileEntity_MultiParallelBlockBase {
 
	private static final int SPEED_DECAY = 5;
	public ArrayList<GTMTE_Reactor_Rod_Hatch> mRodHatches = new ArrayList<>();
	public boolean mFirstStart = false;
	public long mCurrentTemp = 1;
	public long mMaxTemp = 10000;
	public long mCurrentOutput = 1;
	public long mCurrentInput = 1;
	public boolean isMoxFuel = false;
	public boolean isFastDecay = false;
	ITexture INDEX_CASE = Textures.BlockIcons.CASING_BLOCKS[12 + 32];
	
	public GTMTE_NuclearReactorBase(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
	}
	
	public GTMTE_NuclearReactorBase(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
								 byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{INDEX_CASE,
					new GT_RenderedTexture(aActive ? REACTOR_OVERLAY_ACTIVE : REACTOR_OVERLAY)};
		}
		return new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setLong("mCurrentTemp", mCurrentTemp);
		aNBT.setLong("mMaxTemp", mMaxTemp);
		aNBT.setLong("mCurrentOutput", mCurrentOutput);
		aNBT.setLong("mCurrentInput", mCurrentInput);
		aNBT.setBoolean("mFirstStart", mFirstStart);
		aNBT.setBoolean("isFastDecay", isFastDecay);
		aNBT.setBoolean("isMoxFuel", isMoxFuel);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.mCurrentTemp = aNBT.getLong("mCurrentTemp");
		this.mMaxTemp = aNBT.getLong("mMaxTemp");
		this.mCurrentOutput = aNBT.getLong("mCurrentOutput");
		this.mCurrentInput = aNBT.getLong("mCurrentInput");
		this.mFirstStart = aNBT.getBoolean("mFirstStart");
		this.isFastDecay = aNBT.getBoolean("isFastDecay");
		this.isMoxFuel = aNBT.getBoolean("isMoxFuel");
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_NuclearReactor(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
				"NuclearReactorGUI.png");
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_NuclearReactor(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		if (mFirstStart) {
			this.mMaxProgresstime = 20;
			this.mEUt = 0;
			this.mEfficiencyIncrease = 1000;
			return true;
		}
		return false;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		long aTemp = 0;
		long aCountCells = 0;
		int countTime = 20; // 1 sec
		if (!aBaseMetaTileEntity.isActive()) {
			mCurrentTemp -= 10;
			if (mCurrentTemp <= 0) {
				mCurrentTemp = 0;
			}
		}
		int checkRod = 0;
		float checkHeat = 0;
		int checkCells = 0;
		int checkReallyRod = 0;
		ArrayList<Boolean> arrayMoxRod = new ArrayList<>();
		if (mRodHatches.size() > 0) {
			for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {
				if (rod_hatch.mInventory[0] != null && rod_hatch.mInventory[0]
						.getItem() instanceof GT_RadioactiveCellIC_Item) {
					if (((GT_RadioactiveCellIC_Item) rod_hatch.mInventory[0]
							.getItem()).sHeat > 0) {
						if (mEfficiency >= getMaxEfficiency(null)) {
							rod_hatch.setStartReactor(aBaseMetaTileEntity.isActive());
						}
						checkReallyRod++;
						checkRod = Math.max(rod_hatch.mDownRod, 0);
						checkHeat = rod_hatch.mDownRod > 0 ? rod_hatch.mTemp : 0;
						checkCells = rod_hatch.mDownRod > 0 ? rod_hatch.mCountCells : 0;
						aTemp += (checkHeat * checkRod + checkCells);
						arrayMoxRod.add(rod_hatch.mIsMox);
						if (isFastDecay) {
							rod_hatch.setFastDecay(SPEED_DECAY);
						}
					}
				}
			}
		}
		if (aTemp == 0) {
			if (aTick % countTime == 0) {
				mCurrentTemp -= 10;
				if (mCurrentTemp <= 0) {
					mCurrentTemp = 0;
				}
			}
		}
		
		if (aTick % countTime == 0 && aBaseMetaTileEntity.isActive()) {
			if (mCurrentTemp <= mMaxTemp) {
				mCurrentTemp += aTemp;
			}
			if (mCurrentTemp > mMaxTemp) {
				mCurrentTemp = mMaxTemp;
			}
		}
		
		int countMox = (int) arrayMoxRod.stream().filter(b -> b).count();
		isMoxFuel = countMox != 0 && countMox >= checkReallyRod;
		
		double progress = (double) mCurrentTemp / (double) mMaxTemp;
		long abs = (int) ((aTemp * checkCells) * (progress + 1)) / 2;
		mCurrentInput = abs;
		mCurrentOutput = isFastDecay ? abs : abs * 160;
		
		if (abs < 1) {
			long a = isFastDecay ? 0 : 1;
			mCurrentOutput = a;
			mCurrentInput = a;
		}
		
		if (aTick % 8 == 0 && super.mEfficiency > (getMaxEfficiency(null) / (isFastDecay ? 2 : 8))) {
			if (!depleteInput(getInputFluid())) {
				for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {
					rod_hatch.getBaseMetaTileEntity().doExplosion(Long.MAX_VALUE);
				}
			}
		}
		
		if (aTick % 8 == 0 && super.mEfficiency == getMaxEfficiency(null)) {
			addOutput(getOutputFluid());
		}
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		isFastDecay = !isFastDecay;
		GT_Utility.sendChatToPlayer(aPlayer, "Fast Decay Mode " + (isFastDecay ? "Enabled" : "Disabled"));
	}
	
	public void setFastDecayMode(boolean isFastDecay) {
		this.isFastDecay = isFastDecay;
	}
	
	public int[] getRodPosition() {
		int[] rodsStatus = new int[mRodHatches.size()];
		for (int i = 0; i < mRodHatches.size(); i++) {
			rodsStatus[i] = mRodHatches.get(i).mDownRod;
		}
		return rodsStatus;
	}
	
	public void setRodPosition(int rodPosition) {
		for (GTMTE_Reactor_Rod_Hatch rod : mRodHatches) {
			rod.setRodPosition(rodPosition);
		}
	}
	
	public void setSelectRodPosition(int i, int rodPosition) {
		if (mRodHatches.size() > i) {
			mRodHatches.get(i).setRodPosition(rodPosition);
		}
	}
	
	public FluidStack getInputFluid() {
		return isFastDecay ? getFluidStack("ic2coolant", (int) mCurrentInput) :
				Materials.Water.getFluid(mCurrentInput);
	}
	
	public FluidStack getOutputFluid() {
		return isFastDecay ? getFluidStack("ic2hotcoolant", (int) mCurrentOutput) : isMoxFuel ?
				getFluidStack("ic2superheatedsteam", (int) mCurrentOutput) : Materials.Water.getGas(mCurrentOutput);
	}
	
	public void setMaxTemp(int maxTemp) {
		this.mMaxTemp = maxTemp;
	}
	
	public abstract boolean checkMachineFunction(IGregTechTileEntity thisController);
	
	public boolean checkRodHatches(IGregTechTileEntity aTileEntity, int aBaseCasingIndex, int ID) {
		if (aTileEntity == null) {
			return false;
		}
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) {
			return false;
		}
		if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch) {
			((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
		}
		if (aMetaTileEntity instanceof GTMTE_Reactor_Rod_Hatch) {
			((GTMTE_Reactor_Rod_Hatch) aMetaTileEntity).setID(ID);
			return mRodHatches.add((GTMTE_Reactor_Rod_Hatch) aMetaTileEntity);
		}
		return false;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		mRodHatches.clear();
		return this.checkMachineFunction(thisController);
	}
}