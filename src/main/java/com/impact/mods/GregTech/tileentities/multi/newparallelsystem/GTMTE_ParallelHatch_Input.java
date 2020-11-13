package com.impact.mods.GregTech.tileentities.multi.newparallelsystem;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import static com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DataConnector.*;
import static gregtech.api.enums.Dyes.MACHINE_METAL;
import static gregtech.api.enums.ItemList.Tool_NoteBook;
import static gregtech.api.util.GT_ModHandler.isElectricItem;

public class GTMTE_ParallelHatch_Input extends GT_MetaTileEntity_Hatch {

    public int mMaxParallel = 1;
    public int mCurrentParallelIn = 1;


    public GTMTE_ParallelHatch_Input(int aID, String aName, String aNameRegional, int aTier, int aMaxParallel) {
        super(aID, aName, aNameRegional, aTier, 0, new String[]{});
        mMaxParallel = aMaxParallel;
    }

    public GTMTE_ParallelHatch_Input(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
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

    public void setCurrentParallelIn(int aCurrentParallelIn) {
        mCurrentParallelIn = aCurrentParallelIn;
    }

}
