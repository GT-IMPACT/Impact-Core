package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;

public class GTMTE_ParallelHatch_Input extends GT_MetaTileEntity_Hatch {
	
	public int mMaxParallel = 1;
	public int mCurrentParallelIn = 1;
	public int mTargetX = 0;
	public int mTargetY = 0;
	public int mTargetZ = 0;
	public boolean isDebug;
	public String machineName;
	public String address;
	
	public IGregTechTileEntity tTile = null;
	public boolean mTrueRecipe;
	
	public PositionObject mThisPosition;
	public PositionObject mOutputPosition;
	
	public GTMTE_ParallelHatch_Input(int aID, String aName, String aNameRegional, int aTier,
									 int aMaxParallel) {
		super(aID, aName, aNameRegional, aTier, 0, new String[]{
				Utilits.impactTag(),
				"Parallel points receiver",
				"Used in multi-block machines"
		});
		mMaxParallel = aMaxParallel;
		isDebug = false;
	}
	
	public GTMTE_ParallelHatch_Input(String aName, int aTier, String[] aDescription,
									 ITexture[][][] aTextures, int aMaxParallel) {
		super(aName, aTier, 0, aDescription, aTextures);
		mMaxParallel = aMaxParallel;
		isDebug = false;
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				new GT_RenderedTexture(PRL_HATCH_YELLOW,
						Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
				/*new GT_RenderedTexture(EM_D_CONN)*/};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				new GT_RenderedTexture(PRL_HATCH_RED,
						Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
				/*new GT_RenderedTexture(EM_D_CONN)*/};
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		isDebug = false;
		return new GTMTE_ParallelHatch_Input(mName, mTier, mDescriptionArray, mTextures, mMaxParallel);
	}
	
	@Override
	public boolean isOutputFacing(byte aSide) {
		return false;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		if (aBaseMetaTileEntity.isServerSide()) {
			mThisPosition = new PositionObject(aBaseMetaTileEntity);
			mOutputPosition = new PositionObject(mTargetX, mTargetY, mTargetZ);
			machineName = "Not target machine";
			address = UUID.randomUUID().toString().substring(0, 8);
		}
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
			if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
				GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
				NBTTagCompound nbt = tCurrentItem.getTagCompound();
				nbt.setInteger("mXCoordIn", mThisPosition.xPos);
				nbt.setInteger("mYCoordIn", mThisPosition.yPos);
				nbt.setInteger("mZCoordIn", mThisPosition.zPos);
				nbt.setInteger("mDCoordIn", mThisPosition.dPos);
				GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.YELLOW + "Connection start");
			}
		}
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			if (aPlayer.capabilities.isCreativeMode) {
				GT_Utility.sendChatToPlayer(aPlayer, "Debug recipe: " + getTrueRecipe());
			}
		}
	}
	
	@Override
	public void inValidate() {
		if (getBaseMetaTileEntity().isServerSide()) {
			if (tTile != null) {
				IMetaTileEntity outputPar = tTile.getMetaTileEntity();
				if (outputPar instanceof GTMTE_ParallelHatch_Output) {
					((GTMTE_ParallelHatch_Output) outputPar).mInputPosition = null;
					((GTMTE_ParallelHatch_Output) outputPar).mInputPosition = null;
					((GTMTE_ParallelHatch_Output) outputPar).address = "No target address";
					((GTMTE_ParallelHatch_Output) outputPar).machineName = "no target machine";
				}
			}
		}
		super.inValidate();
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide() && aTick % 20 == 0) {
			if (mOutputPosition == null) {
				iAm.setActive(false);
				return;
			}
			tTile = PositionObject.getIGregTechTileEntity(iAm, mOutputPosition);
			if (tTile != null) {
				IMetaTileEntity outputPar = tTile.getMetaTileEntity();
				if (outputPar instanceof GTMTE_ParallelHatch_Output) {
					connectOut((GTMTE_ParallelHatch_Output) outputPar);
				}
			}
		}
	}
	
	public void connectOut(GTMTE_ParallelHatch_Output output) {
		boolean isActive = false;
		boolean isActiveRecipe = false;
		if (output.getMaxParallel() == getMaxParallel() && output.getBaseMetaTileEntity().isActive()) {
			if (PositionObject.checkComparePosition(mThisPosition, output.mInputPosition)) {
				isActive = true;
				isActiveRecipe = output.mIsTrueRecipe;
				mTargetX = mOutputPosition.xPos;
				mTargetY = mOutputPosition.yPos;
				mTargetZ = mOutputPosition.zPos;
			}
		}
		setTrueRecipe(isActiveRecipe);
		getBaseMetaTileEntity().setActive(isActive);
	}
	
	public void setCoord(PositionObject outputPosition) {
		mOutputPosition = outputPosition;
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mCurrentParallelIn", this.mCurrentParallelIn);
		aNBT.setInteger("mTargetX", mTargetX);
		aNBT.setInteger("mTargetY", mTargetY);
		aNBT.setInteger("mTargetZ", mTargetZ);
	}
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mCurrentParallelIn = aNBT.getInteger("mCurrentParallelIn");
		mTargetX = aNBT.getInteger("mTargetX");
		mTargetY = aNBT.getInteger("mTargetY");
		mTargetZ = aNBT.getInteger("mTargetZ");
	}
	
	public int getMaxParallel() {
		return mMaxParallel;
	}
	
	public boolean getTrueRecipe() {
		return mTrueRecipe;
	}
	
	public void setTrueRecipe(boolean isTrue) {
		mTrueRecipe = isTrue;
	}
}