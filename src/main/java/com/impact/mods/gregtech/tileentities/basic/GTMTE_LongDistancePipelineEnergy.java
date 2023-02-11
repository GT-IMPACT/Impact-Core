package com.impact.mods.gregtech.tileentities.basic;

import com.impact.mods.gregtech.enums.Texture;
import com.impact.util.string.Language;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;

import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.util.GT_Utility.getOppositeSide;

public class GTMTE_LongDistancePipelineEnergy extends GTMTE_LongDistancePipelineBase {
	
	public GTMTE_LongDistancePipelineEnergy(int aID, String aName, String aNameRegional, int aTier) {
		super(aID, aName, aNameRegional, aTier, Language.transDesc("pipeline.energy.0", "Sends Energy over long distances"));
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
	public void onPostTick(IGregTechTileEntity inTE, long aTick) {
		super.onPostTick(inTE, aTick);
		
		if (inTE.isServerSide() && checkTarget()) {
			final IGregTechTileEntity outTE = mTarget.getBaseMetaTileEntity();
			
			IGregTechTileEntity in = inTE.getIGregTechTileEntityAtSide(inTE.getFrontFacing());
			if (in == null) return;
			
			IGregTechTileEntity out = outTE.getIGregTechTileEntityAtSide(outTE.getBackFacing());
			if (out == null) return;
			
			long tEU = V[mTier] - Math.min(V[mTier], mDistance / 20);
			
			if (in.isUniversalEnergyStored(tEU)) {
				if (out.injectEnergyUnits((byte) 6, tEU, 1) > 0) {
					in.decreaseStoredEnergyUnits(tEU, false);
				}
			}
		}
	}
	
	@Override
	public String[] getDescription() {
		return new String[] {
//			Language.transDesc("pipeline.energy.1", "Amperage: Unlimited"),
//			Language.transDesc("pipeline.energy.2", "Loss: 0.125 EU / block"),
		};
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
		} else if (aSide == getOppositeSide(aFacing)) {
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier]};
		} else {
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], TextureFactory.of(Texture.Icons.OVERLAY_PIPELINE_ENERGY_SIDE)};
		}
	}
}
