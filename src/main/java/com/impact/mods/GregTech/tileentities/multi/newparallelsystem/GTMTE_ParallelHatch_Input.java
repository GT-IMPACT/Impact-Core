package com.impact.mods.GregTech.tileentities.multi.newparallelsystem;

import com.impact.mods.GregTech.enums.Texture;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

import static com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DataConnector.EM_D_CONN;
import static com.impact.mods.GregTech.enums.Texture.Icons.*;
import static gregtech.api.enums.Dyes.MACHINE_METAL;


public class GTMTE_ParallelHatch_Input extends GT_MetaTileEntity_Hatch {

    public int mMaxParallel = 1;
    public int mCurrentParallelIn = 1;

    public int mTargetX = 0;
    public int mTargetY = 0;
    public int mTargetZ = 0;
    public TileEntity tTile = null;

    public GTMTE_ParallelHatch_Input(int aID, String aName, String aNameRegional, int aTier, int aMaxParallel) {
        super(aID, aName, aNameRegional, aTier, 0, new String[]{});
        mMaxParallel = aMaxParallel;
    }

    public GTMTE_ParallelHatch_Input(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture,
                new GT_RenderedTexture(PRL_HATCH_YELLOW, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
                new GT_RenderedTexture(EM_D_CONN)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture,
                new GT_RenderedTexture(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
                new GT_RenderedTexture(EM_D_CONN)};
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
        return new GTMTE_ParallelHatch_Input(mName, mTier, mDescriptionArray, mTextures);
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
    }

    @Override
    public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);

        if (getBaseMetaTileEntity().isServerSide()) {
            ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
            if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
                GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
                NBTTagCompound nbt = tCurrentItem.getTagCompound();
                nbt.setInteger("mXCoordIn", getBaseMetaTileEntity().getXCoord());
                nbt.setInteger("mYCoordIn", getBaseMetaTileEntity().getYCoord());
                nbt.setInteger("mZCoordIn", getBaseMetaTileEntity().getZCoord());
                nbt.setInteger("mDCoordIn", getBaseMetaTileEntity().getWorld().provider.dimensionId);
                GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.YELLOW + "Connection start");
                GT_Utility.sendChatToPlayer(aPlayer, "X: " + getBaseMetaTileEntity().getXCoord() + " Y: " +
                        getBaseMetaTileEntity().getYCoord() + " Z: " + getBaseMetaTileEntity().getZCoord());

            }
        }
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.GREEN + (getCurrentParallelIn() == getAskPar() ? "True Recipe" : "False Recipe"));
    }

    public void setOutParHatch(int X, int Y, int Z) {
        mTargetX = X;
        mTargetY = Y;
        mTargetZ = Z;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (mTargetX != 0 && mTargetY != 0 && mTargetZ != 0) {
            tTile = getBaseMetaTileEntity().getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
            if (tTile != null) {
                if (tTile instanceof IGregTechTileEntity) {
                    IMetaTileEntity outputPar = ((IGregTechTileEntity) tTile).getMetaTileEntity();
                    if (outputPar instanceof GTMTE_ParallelHatch_Output) {
                        ((GTMTE_ParallelHatch_Output) outputPar).setAskInputHatch(mMBaskPar);
                    }
                }
            }
        }
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mTargetX", this.mTargetX);
        aNBT.setInteger("mTargetY", this.mTargetY);
        aNBT.setInteger("mTargetZ", this.mTargetZ);
        aNBT.setInteger("mCurrentParallelIn", this.mCurrentParallelIn);
        aNBT.setInteger("mMBaskPar", this.mMBaskPar);
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        this.mTargetX = aNBT.getInteger("mTargetX");
        this.mTargetY = aNBT.getInteger("mTargetY");
        this.mTargetZ = aNBT.getInteger("mTargetZ");
        this.mCurrentParallelIn = aNBT.getInteger("mCurrentParallelIn");
        this.mMBaskPar = aNBT.getInteger("mMBaskPar");
    }

    /**
     * @param aCurrentParallelIn Set Current Parallel Point from POH
     */
    public void setCurrentParallelIn(int aCurrentParallelIn) {
        mCurrentParallelIn = aCurrentParallelIn;
    }

    public int getCurrentParallelIn() {
        return mCurrentParallelIn;
    }

    public int mMBaskPar = 2; //todo ask set to 0

    /**
     * @param ask Set MultiBlock Current Recipe Parallel Point
     */
    public void setAskPar(int ask) {
        mMBaskPar = ask;
    }

    public int getAskPar() {
        return mMBaskPar;
    }

    /*
    *todo in MBbase class:
    * if (getCurrentParallelIn() == getAskPar()) {
    *   return true;
    * }
    */

}
