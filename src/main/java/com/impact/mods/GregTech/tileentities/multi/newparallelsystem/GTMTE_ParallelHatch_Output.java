package com.impact.mods.GregTech.tileentities.multi.newparallelsystem;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

import static com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DataConnector.*;
import static gregtech.api.enums.Dyes.MACHINE_METAL;
import static gregtech.api.enums.ItemList.Tool_NoteBook;
import static gregtech.api.util.GT_ModHandler.isElectricItem;

public class GTMTE_ParallelHatch_Output extends GT_MetaTileEntity_Hatch {

    public int mMaxParallel = 1;
    public int mCurrentParallelOut = 1;

    public int mTargetX = 0;
    public int mTargetY = 0;
    public int mTargetZ = 0;
    public int mTargetD = 0;

    public int tTargetX = 0;
    public int tTargetY = 0;
    public int tTargetZ = 0;
    public int tTargetD = 0;

    public TileEntity tTile = null;

    public GTMTE_ParallelHatch_Output(int aID, String aName, String aNameRegional, int aTier, int aMaxParallel) {
        super(aID, aName, aNameRegional, aTier, 0, new String[]{});
        mMaxParallel = aMaxParallel;
    }

    public GTMTE_ParallelHatch_Output(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new GTMTE_ParallelHatch_Output(mName, mTier, mDescriptionArray, mTextures);
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        if (mFluid != null) aNBT.setTag("mFluid", mFluid.writeToNBT(new NBTTagCompound()));
        aNBT.setInteger("mTargetX", this.mTargetX);
        aNBT.setInteger("mTargetY", this.mTargetY);
        aNBT.setInteger("mTargetZ", this.mTargetZ);
        aNBT.setInteger("mTargetD", this.mTargetD);
        aNBT.setInteger("mCurrentParallelOut", this.mCurrentParallelOut);
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        mFluid = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mFluid"));
        this.mTargetX = aNBT.getInteger("mTargetX");
        this.mTargetY = aNBT.getInteger("mTargetY");
        this.mTargetZ = aNBT.getInteger("mTargetZ");
        this.mTargetD = aNBT.getInteger("mTargetD");
        this.mCurrentParallelOut = aNBT.getInteger("mCurrentParallelOut");
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(EM_D_ACTIVE, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())), new GT_RenderedTexture(EM_D_CONN)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(EM_D_SIDES, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())), new GT_RenderedTexture(EM_D_CONN)};
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

    public void setCurrentParallelOut(int aCurrentParallelOut) {
        mCurrentParallelOut = aCurrentParallelOut;
    }

    public void getCoordInputPar(int aTargetX, int aTargetY, int aTargetZ, int aTargetD) {
        this.mTargetX = aTargetX;
        this.mTargetY = aTargetY;
        this.mTargetZ = aTargetZ;
        this.mTargetD = aTargetD;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {

        GT_Utility.sendChatToPlayer(aPlayer, "X: " + this.mTargetX + " Y: " + this.mTargetX + " Z: " + this.mTargetX + " D: " + this.mTargetD);

        return super.onRightclick(aBaseMetaTileEntity, aPlayer, aSide, aX, aY, aZ);
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        if (aBaseMetaTileEntity.isServerSide()) {
            if ((this.mTargetX == 0) && (this.mTargetY == 0) && (this.mTargetZ == 0) && (this.mTargetD == 0)) {
                this.mTargetX = aBaseMetaTileEntity.getXCoord();
                this.mTargetY = aBaseMetaTileEntity.getYCoord();
                this.mTargetZ = aBaseMetaTileEntity.getZCoord();
                this.mTargetD = aBaseMetaTileEntity.getWorld().provider.dimensionId;
            }
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);

        if (getBaseMetaTileEntity().isServerSide()) {
            if (getBaseMetaTileEntity().isAllowedToWork()) {
                if (tTargetD != mTargetD || tTargetX != mTargetX || tTargetY != mTargetY || tTargetZ != mTargetZ) {
                    tTargetD = mTargetD;
                    tTargetX = mTargetX;
                    tTargetY = mTargetY;
                    tTargetZ = mTargetZ;
                    if (this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId) {
                        tTile = getBaseMetaTileEntity().getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
                        if (tTile != null) {
                            if (tTile instanceof IGregTechTileEntity) {
                                IMetaTileEntity inputPar = ((IGregTechTileEntity) tTile).getMetaTileEntity();
                                if (inputPar.getBaseMetaTileEntity() instanceof GTMTE_ParallelHatch_Input) {
                                    ((GTMTE_ParallelHatch_Input) inputPar.getBaseMetaTileEntity()).setCurrentParallelIn(mCurrentParallelOut);
                                }
                            }

                        }
                    }
                }
                getBaseMetaTileEntity().setActive(true);
            } else
                getBaseMetaTileEntity().setActive(false);
        }

    }

}
