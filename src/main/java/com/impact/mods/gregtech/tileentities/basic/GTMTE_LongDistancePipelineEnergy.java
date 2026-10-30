package com.impact.mods.gregtech.tileentities.basic;

import com.impact.mods.gregtech.enums.Texture;
import com.impact.util.string.Language;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.util.GT_Utility.getOppositeSide;

public class GTMTE_LongDistancePipelineEnergy extends GTMTE_LongDistancePipelineBase {

    private static final long CONNECTION_CHECK_INTERVAL = 20L;

    public GTMTE_LongDistancePipelineEnergy(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier,
                Language.transDesc("pipeline.energy.0", "Sends Energy over long distances") + ", " + Language.transDesc(
                        "pipeline.energy.1", "Max Amperage: 16, No Loss by Distance, Min Distance: 32 blocks"));
    }

    public GTMTE_LongDistancePipelineEnergy(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public boolean isSameClass(GTMTE_LongDistancePipelineBase other) {
        return other instanceof GTMTE_LongDistancePipelineEnergy && other.mTier == mTier;
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        return tile != null && tile.isAllowedToWork() && isSender() && aSide == tile.getFrontFacing();
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        return tile != null && tile.isAllowedToWork() && !isSender() && aSide == tile.getBackFacing();
    }

    @Override
    public boolean isSender() {
        return !isLoadedEndpoint(mSender) || !isSameClass(mSender) || mSender.mTarget != this
                || !matchesPosition(mSender.mTargetPos, getBaseMetaTileEntity());
    }

    @Override
    public int getMinDistance() {
        return 32;
    }

    @Override
    public void onPostTick(IGregTechTileEntity tile, long tick) {
        super.onPostTick(tile, tick);
        if (!tile.isServerSide() || !tile.isAllowedToWork() || !isSender()) {
            return;
        }

        if (tick % CONNECTION_CHECK_INTERVAL == 1L) {
            checkTarget();
        }

        if (!hasValidTarget()) {
            return;
        }

        GTMTE_LongDistancePipelineEnergy target = (GTMTE_LongDistancePipelineEnergy) mTarget;
        long voltage = maxEUOutput();
        long available = Math.max(0L, getEUVar() - getMinimumStoredEU());
        long space = Math.max(0L, target.maxEUStore() - target.getEUVar());
        long amperage = Math.min(maxAmperesOut(), Math.min(available / voltage, space / voltage));
        if (amperage <= 0L) {
            return;
        }

        // The local GT energy network handles external sides, covers and overvoltage.
        // Only the two validated endpoint buffers participate in the long-distance link.
        long energy = voltage * amperage;
        setEUVar(getEUVar() - energy);
        target.setEUVar(target.getEUVar() + energy);
        markStorageDirty();
        target.markStorageDirty();
    }

    @Override
    public boolean checkTarget() {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        if (!isLoadedEndpoint(this) || !tile.isServerSide() || !tile.isAllowedToWork() || !isSender()) {
            return false;
        }

        if (!isLoadedEndpoint(mTarget) || !isSameClass(mTarget)
                || !matchesPosition(mTargetPos, mTarget.getBaseMetaTileEntity())) {
            detachTarget();
        }

        if (mTargetPos == null || matchesPosition(mTargetPos, tile)) {
            scanPipes();
        } else if (mTarget == null) {
            World world = tile.getWorld();
            if (!world.blockExists(mTargetPos.posX, mTargetPos.posY, mTargetPos.posZ)) {
                return false;
            }
            TileEntity targetTile = world.getTileEntity(mTargetPos.posX, mTargetPos.posY, mTargetPos.posZ);
            if (targetTile instanceof IGregTechTileEntity) {
                IMetaTileEntity meta = ((IGregTechTileEntity) targetTile).getMetaTileEntity();
                if (meta instanceof GTMTE_LongDistancePipelineEnergy
                        && isSameClass((GTMTE_LongDistancePipelineEnergy) meta)) {
                    mTarget = (GTMTE_LongDistancePipelineEnergy) meta;
                }
            }
            if (mTarget == null) {
                mTargetPos = null;
                mDistance = 0L;
                return false;
            }
        }

        if (mTarget == this || !isLoadedEndpoint(mTarget) || !isSameClass(mTarget) || mDistance < getMinDistance()) {
            return false;
        }

        GTMTE_LongDistancePipelineEnergy target = (GTMTE_LongDistancePipelineEnergy) mTarget;
        if ((!target.isSender() && target.mSender != this) || (target.mTarget != null && target.mTarget != target
                && target.mTarget.mSender == target && target.isLoadedEndpoint(target.mTarget))) {
            return false;
        }

        target.mSender = this;
        target.mDistance = mDistance;
        return hasValidTarget();
    }

    private boolean hasValidTarget() {
        return isLoadedEndpoint(this) && isSender() && mTarget != this && isLoadedEndpoint(mTarget)
                && isSameClass(mTarget) && matchesPosition(mTargetPos, mTarget.getBaseMetaTileEntity())
                && mDistance >= getMinDistance() && mTarget.mSender == this && getBaseMetaTileEntity().isAllowedToWork()
                && mTarget.getBaseMetaTileEntity().isAllowedToWork();
    }

    private boolean isLoadedEndpoint(GTMTE_LongDistancePipelineBase endpoint) {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        IGregTechTileEntity other = endpoint == null ? null : endpoint.getBaseMetaTileEntity();
        if (tile == null || other == null || other.isDead() || other.getMetaTileEntity() != endpoint
                || tile.getWorld() == null || other.getWorld() != tile.getWorld()) {
            return false;
        }
        World world = tile.getWorld();
        return world.blockExists(other.getXCoord(), other.getYCoord(), other.getZCoord())
                && world.getTileEntity(other.getXCoord(), other.getYCoord(), other.getZCoord()) == other;
    }

    private static boolean matchesPosition(ChunkCoordinates position, IGregTechTileEntity tile) {
        return position != null && tile != null && position.posX == tile.getXCoord()
                && position.posY == tile.getYCoord() && position.posZ == tile.getZCoord();
    }

    private void detachTarget() {
        if (mTarget != null && mTarget != this && mTarget.mSender == this) {
            mTarget.mSender = null;
        }
        mTarget = null;
    }

    private void disconnect() {
        detachTarget();
        if (mSender != null && mSender.mTarget == this) {
            mSender.mTarget = null;
        }
        mSender = null;
    }

    @Override
    protected void scanPipes() {
        if (!isSender()) {
            return;
        }
        ChunkCoordinates previousPosition = mTargetPos;
        long previousDistance = mDistance;
        detachTarget();
        mSender = null;
        super.scanPipes();
        if (mTarget != null && mTarget != this && isLoadedEndpoint(this)
                && (!mTargetPos.equals(previousPosition) || mDistance != previousDistance)) {
            markStorageDirty();
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        disconnect();
        mTargetPos = null;
        mDistance = 0L;
        super.loadNBTData(tag);
    }

    @Override
    public void onMachineBlockUpdate() {
        GTMTE_LongDistancePipelineBase sender = mSender != null && mSender.mTarget == this ? mSender : null;
        disconnect();
        if (sender != null) {
            sender.mTargetPos = null;
            sender.mDistance = 0L;
        }
        mDistance = 0L;
        super.onMachineBlockUpdate();
    }

    @Override
    public void onFacingChange() {
        onMachineBlockUpdate();
        super.onFacingChange();
    }

    @Override
    public void onRemoval() {
        onMachineBlockUpdate();
        super.onRemoval();
    }

    @Override
    public void inValidate() {
        onMachineBlockUpdate();
        super.inValidate();
    }

    @Override
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
        // GT debits its output overhead per packet. Keep room for 16 outputs and a packet remainder.
        long outputPacketCost = maxEUOutput() + (1L << mTier);
        return getMinimumStoredEU() + outputPacketCost * (maxAmperesOut() + 1L);
    }

    @Override
    public boolean isElectric() {
        return true;
    }

    @Override
    public boolean isEnetInput() {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        return tile != null && tile.isAllowedToWork() && isSender();
    }

    @Override
    public boolean isEnetOutput() {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        return tile != null && tile.isAllowedToWork() && !isSender();
    }

    @Override
    public int getPipeMeta() {
        return mTier + 1;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_LongDistancePipelineEnergy(mName, mTier, mDescription, mTextures);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity te, byte aSide, byte aFacing, byte aColorIndex, boolean aActive,
            boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
                    Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI[mTier] };
        } else if (aSide == getOppositeSide(aFacing)) {
            return new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
                    Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier] };
        } else {
            return new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
                    TextureFactory.of(Texture.Icons.OVERLAY_PIPELINE_ENERGY_SIDE) };
        }
    }
}
