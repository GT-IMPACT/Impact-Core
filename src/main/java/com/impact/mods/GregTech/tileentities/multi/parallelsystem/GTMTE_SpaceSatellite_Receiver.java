package com.impact.mods.GregTech.tileentities.multi.parallelsystem;

import com.impact.core.Impact_API;
import com.impact.mods.GregTech.gui.Container_SpaceSatelliteHatches;
import com.impact.mods.GregTech.gui.GUI_SpaceSatelliteHathes;
import com.impact.util.Utilits;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.Arrays;

import static com.impact.mods.GregTech.enums.Texture.Icons.PRL_HATCH_GREEN;
import static com.impact.mods.GregTech.enums.Texture.Icons.PRL_HATCH_RED;
import static gregtech.api.enums.Dyes.MACHINE_METAL;


public class GTMTE_SpaceSatellite_Receiver extends GT_MetaTileEntity_Hatch {

  //Приемник
  public int mTargetX = 0;
  public int mTargetY = 0;
  public int mTargetZ = 0;
  public int mTargetD = 0;
  public TileEntity tTile = null;
  public boolean mIsReceive;
  public int mFrequency;

  public GTMTE_SpaceSatellite_Receiver(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional, 3, 0, new String[]{
        Utilits.impactTag(),
        "Receiving signals from orbit",
        "Used in Communication Tower",
        EnumChatFormatting.RED + "▉" + EnumChatFormatting.GRAY + " - Error",
        EnumChatFormatting.GREEN + "▉" + EnumChatFormatting.GRAY + " - All Right"
    });
  }

  public GTMTE_SpaceSatellite_Receiver(String aName, String[] aDescription, ITexture[][][] aTextures) {
    super(aName, 3, 0, aDescription, aTextures);
  }

  @Override
  public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
    return new Container_SpaceSatelliteHatches(aPlayerInventory, aBaseMetaTileEntity);
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
    return new GUI_SpaceSatelliteHathes(aPlayerInventory, aBaseMetaTileEntity, "Communication Receiver");
  }

  @Override
  public ITexture[] getTexturesActive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture,
        new GT_RenderedTexture(PRL_HATCH_GREEN, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
        /*new GT_RenderedTexture(EM_D_CONN)*/};
  }

  @Override
  public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture,
        new GT_RenderedTexture(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
        /*new GT_RenderedTexture(EM_D_CONN)*/};
  }

  @Override
  public boolean isSimpleMachine() {
    return true;
  }

//    @Override
//    public boolean isElectric() {
//        return false;
//    }
//
//    @Override
//    public void doExplosion(long aExplosionPower) {
//
//    }
//
//    @Override
//    public float getExplosionResistance(byte aSide) {
//        return super.getExplosionResistance(aSide);
//    }

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
    return new GTMTE_SpaceSatellite_Receiver(mName, mDescriptionArray, mTextures);
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
  public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
    if (aBaseMetaTileEntity.isClientSide()) return true;
    if (aSide == aBaseMetaTileEntity.getFrontFacing())
      GT_Utility.sendChatToPlayer(aPlayer, "Connection only with Laptop!");

    return true;
  }

  @Override
  public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
    super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
    if (getBaseMetaTileEntity().isServerSide()) {
      getBaseMetaTileEntity().openGUI(aPlayer);
      //getFrequency(mFrequency, aPlayer);
    }
  }

  public void getFrequency(int freq, EntityPlayer aPlayer) {
    if (Impact_API.sSpaceSatellite.get(Utilits.inToStringUUID(freq, aPlayer)) != null) {
      GT_Utility.sendChatToPlayer(aPlayer, "Coords: " + Arrays.toString(Impact_API.sSpaceSatellite.get(Utilits.inToStringUUID(freq, aPlayer))));
      setCoord(Impact_API.sSpaceSatellite.get(Utilits.inToStringUUID(freq, aPlayer)));
    } else GT_Utility.sendChatToPlayer(aPlayer, "Frequency not found");
  }

  @Override
  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
    super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
    if (getBaseMetaTileEntity().isServerSide()) {
    }
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {

      if (this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId) {
        tTile = getBaseMetaTileEntity().getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
      } else {
        World tWorld = DimensionManager.getWorld(this.mTargetD);
        if (tWorld != null) {
          tTile = tWorld.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
        }
      }
      if (tTile != null && tTile instanceof IGregTechTileEntity) {
        IMetaTileEntity aTransmitter = ((IGregTechTileEntity) tTile).getMetaTileEntity();
        if (aTransmitter instanceof GTMTE_SpaceSatellite_Transmitter) {
          setIsReceive(((GTMTE_SpaceSatellite_Transmitter) aTransmitter).mIsTransmit);
          aTransmitter.getBaseMetaTileEntity().setActive(true);
          aBaseMetaTileEntity.setActive(true);
          if (mFrequency != ((GTMTE_SpaceSatellite_Transmitter) aTransmitter).mFrequency) {
            aBaseMetaTileEntity.setActive(false);
            setIsReceive(false);
          }
        } else aBaseMetaTileEntity.setActive(false);
      } else aBaseMetaTileEntity.setActive(false);
    }
  }

  public void setCoord(int[] coords) {
    this.mTargetX = coords[0];
    this.mTargetY = coords[1];
    this.mTargetZ = coords[2];
    this.mTargetD = coords[3];
  }

  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setInteger("mTargetX", this.mTargetX);
    aNBT.setInteger("mTargetY", this.mTargetY);
    aNBT.setInteger("mTargetZ", this.mTargetZ);
    aNBT.setInteger("mTargetD", this.mTargetD);
    aNBT.setInteger("mFrequency", this.mFrequency);
    aNBT.setBoolean("mIsReceive", this.mIsReceive);
  }

  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    this.mTargetX = aNBT.getInteger("mTargetX");
    this.mTargetY = aNBT.getInteger("mTargetY");
    this.mTargetZ = aNBT.getInteger("mTargetZ");
    this.mTargetD = aNBT.getInteger("mTargetD");
    this.mFrequency = aNBT.getInteger("mFrequency");
    this.mIsReceive = aNBT.getBoolean("mIsReceive");
  }

  public boolean getIsReceive() {
    return mIsReceive;
  }

  public void setIsReceive(boolean IsReceive) {
    mIsReceive = IsReceive;
  }

}