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

public class GTMTE_ParallelHatch_Output extends GT_MetaTileEntity_Hatch {
	
	public int mMaxParallel;
	public int mTargetX = 0;
	public int mTargetY = 0;
	public int mTargetZ = 0;
	public String machineName;
	public String address;
	
	public PositionObject mInputPosition;
	public PositionObject mThisPosition;
	
	public IGregTechTileEntity tTile = null;
	public boolean mIsTrueRecipe = false;
	
	public GTMTE_ParallelHatch_Output(int aID, String aName, String aNameRegional, int aTier,
									  int aMaxParallel) {
		super(aID, aName, aNameRegional, aTier, 0, new String[]{
				Utilits.impactTag(),
				"Parallel points transmitter",
				"Used in multi-block machines"
		});
		mMaxParallel = aMaxParallel;
	}
	
	public GTMTE_ParallelHatch_Output(String aName, int aTier, String[] aDescription,
									  ITexture[][][] aTextures, int aMaxParallel) {
		super(aName, aTier, 0, aDescription, aTextures);
		mMaxParallel = aMaxParallel;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new GTMTE_ParallelHatch_Output(mName, mTier, mDescriptionArray, mTextures, mMaxParallel);
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
	public boolean isOutputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return false;
	}
	
	public int getMaxParallel() {
		return mMaxParallel;
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			if (aPlayer.capabilities.isCreativeMode) {
				GT_Utility.sendChatToPlayer(aPlayer, "Debug recipe: " + mIsTrueRecipe);
			}
		}
	}
	
	@Override
	public void inValidate() {
		if (getBaseMetaTileEntity().isServerSide()) {
			if (tTile != null) {
				IMetaTileEntity inputPar = tTile.getMetaTileEntity();
				if (inputPar instanceof GTMTE_ParallelHatch_Input) {
					((GTMTE_ParallelHatch_Input) inputPar).mOutputPosition = null;
				}
			}
		}
		super.inValidate();
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			getBaseMetaTileEntity().setActive(false);
			ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
			if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
				GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
				NBTTagCompound nbt = tCurrentItem.getTagCompound();
				if (nbt != null) {
					PositionObject offsetPosObj = new PositionObject(
							nbt.getInteger("mXCoordIn"),
							nbt.getInteger("mYCoordIn"),
							nbt.getInteger("mZCoordIn"));
					
					tTile = PositionObject.getIGregTechTileEntity(getBaseMetaTileEntity(), offsetPosObj);
					
					nbt.removeTag("mXCoordIn");
					nbt.removeTag("mYCoordIn");
					nbt.removeTag("mZCoordIn");
					nbt.removeTag("mDCoordIn");
					
					if (tTile != null) {
						IMetaTileEntity input = tTile.getMetaTileEntity();
						if (input instanceof GTMTE_ParallelHatch_Input) {
							if (((GTMTE_ParallelHatch_Input) input).getMaxParallel() == getMaxParallel()) {
								message(aPlayer, 0);
								mInputPosition = new PositionObject(tTile);
							} else {
								message(aPlayer, 1);
								getBaseMetaTileEntity().setActive(false);
							}
							return;
						}
					}
				}
				message(aPlayer, 1);
			}
		}
	}
	
	private void message(EntityPlayer aPlayer, int msg) {
		switch (msg) {
			case 0:
				GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.GREEN + "Successful connection");
				GT_Utility.sendChatToPlayer(aPlayer, "Current Parallel Point: " + mMaxParallel);
				break;
			case 1:
				GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Connection error, try again");
				break;
		}
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		if (aBaseMetaTileEntity.isServerSide()) {
			mThisPosition = new PositionObject(aBaseMetaTileEntity);
			mInputPosition = new PositionObject(mTargetX, mTargetY, mTargetZ);
			machineName = "No target machine";
			address = "no target address";
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide() && aTick % 20 == 0) {
			if (mInputPosition == null) {
				iAm.setActive(false);
				return;
			}
			if (PositionObject.checkComparePositionOnlyDim(mThisPosition, mInputPosition)) {
				tTile = PositionObject.getIGregTechTileEntity(iAm, mInputPosition);
				if (tTile != null) {
					IMetaTileEntity inputPar = tTile.getMetaTileEntity();
					if (inputPar instanceof GTMTE_ParallelHatch_Input) {
						connectIn((GTMTE_ParallelHatch_Input) inputPar);
					}
				}
			}
		}
	}
	
	public void connectIn(GTMTE_ParallelHatch_Input input) {
		boolean isActive = false;
		if (input.getMaxParallel() == getMaxParallel()) {
			if (PositionObject.checkComparePosition(mThisPosition, input.mOutputPosition)) {
				mTargetX = mInputPosition.xPos;
				mTargetY = mInputPosition.yPos;
				mTargetZ = mInputPosition.zPos;
				isActive = true;
			}
			input.setCoord(mThisPosition);
			machineName = input.machineName;
			address = input.address;
		}
		getBaseMetaTileEntity().setActive(isActive);
	}
	
	public void setRecipe(boolean is) {
		this.mIsTrueRecipe = is;
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("mIsTrueRecipe", this.mIsTrueRecipe);
		aNBT.setInteger("mTargetX", mTargetX);
		aNBT.setInteger("mTargetY", mTargetY);
		aNBT.setInteger("mTargetZ", mTargetZ);
		aNBT.setString("machineName", machineName);
		aNBT.setString("machineAddress", address);
	}
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.mIsTrueRecipe = aNBT.getBoolean("mIsTrueRecipe");
		mTargetX = aNBT.getInteger("mTargetX");
		mTargetY = aNBT.getInteger("mTargetY");
		mTargetZ = aNBT.getInteger("mTargetZ");
		machineName = aNBT.getString("machineName");
		address = aNBT.getString("machineAddress");
	}
}