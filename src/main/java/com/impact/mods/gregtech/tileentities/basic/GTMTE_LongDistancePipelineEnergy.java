package com.impact.mods.gregtech.tileentities.basic;

import com.impact.mods.gregtech.enums.Texture;
import com.impact.util.energy.EnergyUtil;
import com.impact.util.string.Language;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;

import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.util.GT_Utility.getOppositeSide;

public class GTMTE_LongDistancePipelineEnergy extends GTMTE_LongDistancePipelineBase {

    public GTMTE_LongDistancePipelineEnergy(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier,
                Language.transDesc("pipeline.energy.0", "Sends Energy over long distances") + ", " +
                        Language.transDesc("pipeline.energy.1", "Max Amperage: 16, No Loss by Distance, Min Distance: 32 blocks")
        );
    }

    public GTMTE_LongDistancePipelineEnergy(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public boolean isSameClass(GTMTE_LongDistancePipelineBase other) {
        return other instanceof GTMTE_LongDistancePipelineEnergy;
    }

    public boolean isInputFacing(byte aSide) {
        return isSender() && aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    public boolean isOutputFacing(byte aSide) {
        return !isSender() && aSide == getBaseMetaTileEntity().getBackFacing();
    }

    @Override
    public int getMinDistance() {
        return 32;
    }

    @Override
    public void onPostTick(IGregTechTileEntity inTE, long aTick) {
        super.onPostTick(inTE, aTick);

        if (inTE.isServerSide() && mDistance >= getMinDistance()) {

            if (aTick % 20L == 0) {
                if (!checkTarget()) return;
            }

            final IGregTechTileEntity outTE = mTarget.getBaseMetaTileEntity();

            IGregTechTileEntity in = inTE.getIGregTechTileEntityAtSide(inTE.getFrontFacing());
            if (in == null) return;

            IGregTechTileEntity out = outTE.getIGregTechTileEntityAtSide(outTE.getBackFacing());
            if (out == null) return;

            long tEU = in.getOutputVoltage();
            long amperes = Math.min(in.getOutputAmperage(), 16L);

            EnergyUtil.transferEnergy(in, out, amperes, tEU);
        }
    }

    public long getMinimumStoredEU() {
        return 512L;
    }

    @Override
    public long maxEUInput() {
        return V[mTier];
    }

    @Override
    public long maxEUOutput() {
        return V[mTier];
    }

    @Override
    public long maxAmperesOut() {
        return 16;
    }

    @Override
    public long maxAmperesIn() {
        return 16;
    }

    @Override
    public long maxEUStore() {
        return 512L + V[mTier] * 16L;
    }

    @Override
    public boolean isElectric() {
        return true;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isEnetOutput() {
        return true;
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
