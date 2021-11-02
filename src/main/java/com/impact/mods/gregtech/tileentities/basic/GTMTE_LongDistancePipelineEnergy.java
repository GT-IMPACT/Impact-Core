package com.impact.mods.gregtech.tileentities.basic;

import com.impact.mods.gregtech.enums.Texture;
import com.impact.util.Language;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;

import static gregtech.api.enums.GT_Values.V;

public class GTMTE_LongDistancePipelineEnergy extends GTMTE_LongDistancePipelineBase {
	
	public GTMTE_LongDistancePipelineEnergy(int aID, String aName, String aNameRegional, int aTier) {
		super(aID, aName, aNameRegional, aTier, Language.transDesc("pipeline.energy", "Sends Energy over long distances"));
	}
	
	public GTMTE_LongDistancePipelineEnergy(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, aDescription, aTextures);
	}
	
	@Override
	public boolean isSameClass(GTMTE_LongDistancePipelineBase other) {
		return other instanceof GTMTE_LongDistancePipelineEnergy;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return checkTarget() && aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isOutputFacing(byte aSide) {
		return checkTarget() && aSide == mTarget.getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		
		if (te.isServerSide() && checkTarget()) {
			final IGregTechTileEntity tTile = mTarget.getBaseMetaTileEntity();
			
			IGregTechTileEntity teDest = te.getIGregTechTileEntityAtSide(te.getFrontFacing());
			IGregTechTileEntity teTarget = tTile.getIGregTechTileEntityAtSide(tTile.getBackFacing());
			if (teDest != null && teTarget != null) {
				long tEU = V[mTier];
				long tAmp = 1;
				if (teDest.inputEnergyFrom(te.getFrontFacing()) && teTarget.outputsEnergyTo(tTile.getBackFacing())) {
					if (teTarget.getStoredEU() < teTarget.getEUCapacity()) {
						tAmp = teTarget.injectEnergyUnits((byte) 6, tEU, teDest.getOutputAmperage());
						teDest.drainEnergyUnits((byte) 6, tEU, tAmp);
					}
				} else if (teDest.outputsEnergyTo(tTile.getFrontFacing()) && teTarget.inputEnergyFrom(te.getBackFacing())) {
					if (teDest.getStoredEU() < teDest.getEUCapacity()) {
						tAmp = teDest.injectEnergyUnits((byte) 6, tEU, teTarget.getOutputAmperage());
						teTarget.drainEnergyUnits((byte) 6, tEU, tAmp);
					}
				}
			}
		}
	}
	
	public int getPipeMeta() {
		return mTier + 1;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_LongDistancePipelineEnergy(mName, mTier, mDescription, mTextures);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity te, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI[mTier]};
		} else if (aSide == GT_Utility.getOppositeSide(aFacing)) {
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier]};
		} else {
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], new GT_RenderedTexture(Texture.Icons.OVERLAY_PIPELINE_ENERGY_SIDE)};
		}
	}
}
