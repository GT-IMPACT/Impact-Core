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


public class GTMTE_ParallelHatch_Input extends GT_MetaTileEntity_Hatch {

  public int mMaxParallel = 1;
  public int mCurrentParallelIn = 1;
  public boolean isDebug;

  public int mTargetX = 0;
  public int mTargetY = 0;
  public int mTargetZ = 0;
  public TileEntity tTile = null;
  public boolean mTrueRecipe;

  public GTMTE_ParallelHatch_Input(int aID, String aName, String aNameRegional, int aTier,
      int aMaxParallel) {
    super(aID, aName, aNameRegional, aTier, 0, new String[]{
        Utilits.impactTag(),
        "Parallel points receiver",
        "Used in multi-block machines"
    });
    mMaxParallel = aMaxParallel;
    isDebug = false;
  }

  public GTMTE_ParallelHatch_Input(String aName, int aTier, String[] aDescription,
      ITexture[][][] aTextures, int aMaxParallel) {
    super(aName, aTier, 0, aDescription, aTextures);
    mMaxParallel = aMaxParallel;
    isDebug = false;
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
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
    isDebug = false;
    return new GTMTE_ParallelHatch_Input(mName, mTier, mDescriptionArray, mTextures, mMaxParallel);
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
            EnumChatFormatting.BLUE + "Lost connection to Output Parallel Hatch");
        GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.BLUE + "Connection restored!");
      } else {
        if (aPlayer.capabilities.isCreativeMode) {
          GT_Utility.sendChatToPlayer(aPlayer,
              "Debug recipe: " + getTrueRecipe());
        }
      }
    }
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {
      tTile = aBaseMetaTileEntity.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
      if (tTile != null) {
        if (tTile instanceof IGregTechTileEntity) {
          IMetaTileEntity outputPar = ((IGregTechTileEntity) tTile).getMetaTileEntity();
          if (outputPar instanceof GTMTE_ParallelHatch_Output) {
            connectOut((GTMTE_ParallelHatch_Output) outputPar);
          }
        }
      }
    }
  }

  public void connectOut(GTMTE_ParallelHatch_Output output) {
    boolean isActive = false;
    boolean isActiveRecipe = false;
    if (output.getMaxParallel() == getMaxParallel()
        && output.getBaseMetaTileEntity().isActive()) {
      if (getBaseMetaTileEntity().getXCoord()
          == output.mTargetX
          && getBaseMetaTileEntity().getYCoord()
          == output.mTargetY
          && getBaseMetaTileEntity().getZCoord()
          == output.mTargetZ) {
        isActive = true;
        isActiveRecipe = output.mIsTrueRecipe;
      }
    }
    setTrueRecipe(isActiveRecipe);
    getBaseMetaTileEntity().setActive(isActive);
  }


  public void setCoord(int x, int y, int z) {
    this.mTargetX = x;
    this.mTargetY = y;
    this.mTargetZ = z;
  }

  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setInteger("mTargetX", this.mTargetX);
    aNBT.setInteger("mTargetY", this.mTargetY);
    aNBT.setInteger("mTargetZ", this.mTargetZ);
    aNBT.setInteger("mCurrentParallelIn", this.mCurrentParallelIn);
  }

  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    this.mTargetX = aNBT.getInteger("mTargetX");
    this.mTargetY = aNBT.getInteger("mTargetY");
    this.mTargetZ = aNBT.getInteger("mTargetZ");
    this.mCurrentParallelIn = aNBT.getInteger("mCurrentParallelIn");
  }

  public int getMaxParallel() {
    return mMaxParallel;
  }

  public boolean getTrueRecipe() {
    return mTrueRecipe;
  }

  public void setTrueRecipe(boolean isTrue) {
    mTrueRecipe = isTrue;
  }

}