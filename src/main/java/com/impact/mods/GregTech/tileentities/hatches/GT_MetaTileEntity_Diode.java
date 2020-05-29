package com.impact.mods.GregTech.tileentities.hatches;

import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class GT_MetaTileEntity_Diode extends GT_MetaTileEntity_BasicHull {
    private long maxAmps;
    private long aAmps;
    private int mAmperSet;


    public GT_MetaTileEntity_Diode(int aID, String aName, String aNameRegional, int aTier, int aAmper) {
        super(aID, aName, aNameRegional, aTier, "A Simple diode that will allow Energy Flow in only one direction");
        this.mAmperSet = aAmper;
        this.maxAmps = aAmper;
        this.aAmps = this.maxAmps;
    }

    public GT_MetaTileEntity_Diode(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
        if (this.maxAmps == 0 && !this.getBaseMetaTileEntity().getWorld().isRemote) {
            this.maxAmps = mAmperSet;
            this.aAmps = this.maxAmps;
        }
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);

        if (this.getBaseMetaTileEntity().getWorld().isRemote)
            return;
        if (!aPlayer.isSneaking()) {
            --this.aAmps;
            if (this.aAmps < 0)
                this.aAmps = this.maxAmps;
            GT_Utility.sendChatToPlayer(aPlayer, "Max Amps: " + this.aAmps);
        } else {
            ++this.aAmps;
            if (this.aAmps > this.maxAmps)
                this.aAmps = 0;
            GT_Utility.sendChatToPlayer(aPlayer, "Max Amps: " + this.aAmps);
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setLong("maxAmp", this.maxAmps);
        aNBT.setLong("Amps", this.aAmps);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        this.maxAmps = aNBT.getLong("maxAmp");
        this.aAmps = aNBT.getLong("Amps");
        super.loadNBTData(aNBT);
    }

    @Override
    public long maxAmperesOut() {
        return this.aAmps;
    }

    @Override
    public long maxAmperesIn() {
        return this.aAmps;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Diode(this.mName, this.mTier, this.mDescriptionArray, this.mTextures);
    }

    @SuppressWarnings("deprecation")
    public String[] getDescription() {
        return new String[]{this.mDescription,
            "Voltage: " + EnumChatFormatting.YELLOW + GT_Values.V[this.mTier],
            "Amperage IN: " + EnumChatFormatting.YELLOW + this.maxAmperesIn(),
            "Amperage OUT: " + EnumChatFormatting.YELLOW + this.maxAmperesOut(),
        };
    }

}
