package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;

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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;

public class GTMTE_ParallelHatch_Output extends GT_MetaTileEntity_Hatch {

  public int mMaxParallel;
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
  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {
    super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
    if (getBaseMetaTileEntity().isServerSide()) {
      if (aPlayer.isSneaking()) {
        this.mTargetX = 0;
        this.mTargetY = 0;
        this.mTargetZ = 0;
        getBaseMetaTileEntity().setActive(false);
        GT_Utility.sendChatToPlayer(aPlayer,
            EnumChatFormatting.BLUE + "Lost connection to Input Parallel Hatch");
      } else {
        if (aPlayer.capabilities.isCreativeMode) {
          GT_Utility.sendChatToPlayer(aPlayer, "Debug recipe: " + mIsTrueRecipe);
        }
      }
    }
  }

  @Override
  public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
    super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);

    if (getBaseMetaTileEntity().isServerSide()) {
      this.mTargetX = 0;
      this.mTargetY = 0;
      this.mTargetZ = 0;
      this.mTargetD = 0;
      getBaseMetaTileEntity().setActive(false);
      GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.BLUE + "Cache is cleared");
      ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
      if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {

        GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
        NBTTagCompound nbt = tCurrentItem.getTagCompound();
        if (nbt != null
            && !(nbt.getInteger("mXCoordIn") == 0
            && nbt.getInteger("mYCoordIn") == 0
            && nbt.getInteger("mZCoordIn") == 0
            && nbt.getInteger("mDCoordIn") == 0)) {
          this.mTargetX = nbt.getInteger("mXCoordIn");
          this.mTargetY = nbt.getInteger("mYCoordIn");
          this.mTargetZ = nbt.getInteger("mZCoordIn");
          this.mTargetD = nbt.getInteger("mDCoordIn");

          nbt.removeTag("mXCoordIn");
          nbt.removeTag("mYCoordIn");
          nbt.removeTag("mZCoordIn");
          nbt.removeTag("mDCoordIn");

          tTile = getBaseMetaTileEntity()
              .getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
          if (tTile != null) {
            if (tTile instanceof IGregTechTileEntity) {
              IMetaTileEntity inputPar = ((IGregTechTileEntity) tTile).getMetaTileEntity();
              if (inputPar instanceof GTMTE_ParallelHatch_Input) {
                if (((GTMTE_ParallelHatch_Input) inputPar).getMaxParallel() == getMaxParallel()) {
                  GT_Utility.sendChatToPlayer(aPlayer,
                      EnumChatFormatting.GREEN + "Successful connection");
                  GT_Utility.sendChatToPlayer(aPlayer,
                      "X: " + this.mTargetX + " Y: " + this.mTargetY + " Z: " + this.mTargetZ);
                  GT_Utility.sendChatToPlayer(aPlayer, "Current Parallel Point: " + 4);
                } else {
                  GT_Utility.sendChatToPlayer(aPlayer,
                      EnumChatFormatting.RED + "Connection error, try again");
                  getBaseMetaTileEntity().setActive(false);
                }
              }
            }
          }
        } else {
          GT_Utility
              .sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Connection error, try again");
        }
      }
    }
  }

  @Override
  public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
    if (aBaseMetaTileEntity.isServerSide()) {
      if ((this.mTargetX == 0) && (this.mTargetY == 0) && (this.mTargetZ == 0) && (this.mTargetD
          == 0)) {
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
    if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {
      if (aBaseMetaTileEntity.isAllowedToWork()) {
        if (this.mTargetD == aBaseMetaTileEntity.getWorld().provider.dimensionId) {
          tTile = aBaseMetaTileEntity.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
          if (tTile != null) {
            if (tTile instanceof IGregTechTileEntity) {
              IMetaTileEntity inputPar = ((IGregTechTileEntity) tTile).getMetaTileEntity();
              if (inputPar instanceof GTMTE_ParallelHatch_Input) {
                connectIn((GTMTE_ParallelHatch_Input) inputPar);
              }
            }
          }
        }
      }
    }
  }

  public void connectIn(GTMTE_ParallelHatch_Input input) {
    boolean isActive = false;
    if (input.getMaxParallel() == getMaxParallel()) {
      if (getBaseMetaTileEntity().getXCoord() == input.mTargetX
          && getBaseMetaTileEntity().getYCoord() == input.mTargetY
          && getBaseMetaTileEntity().getZCoord() == input.mTargetZ) {
        isActive = true;
      }
      input.setCoord(getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(),
          getBaseMetaTileEntity().getZCoord());
    }
    getBaseMetaTileEntity().setActive(isActive);
  }

  public void setRecipe(boolean is) {
    this.mIsTrueRecipe = is;
  }

  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setInteger("mTargetX", this.mTargetX);
    aNBT.setInteger("mTargetY", this.mTargetY);
    aNBT.setInteger("mTargetZ", this.mTargetZ);
    aNBT.setInteger("mTargetD", this.mTargetD);
    aNBT.setBoolean("mIsTrueRecipe", this.mIsTrueRecipe);
  }

  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    this.mTargetX = aNBT.getInteger("mTargetX");
    this.mTargetY = aNBT.getInteger("mTargetY");
    this.mTargetZ = aNBT.getInteger("mTargetZ");
    this.mTargetD = aNBT.getInteger("mTargetD");
    this.mIsTrueRecipe = aNBT.getBoolean("mIsTrueRecipe");
  }
}
