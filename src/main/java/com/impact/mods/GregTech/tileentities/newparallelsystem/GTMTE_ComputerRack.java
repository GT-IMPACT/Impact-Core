package com.impact.mods.GregTech.tileentities.newparallelsystem;

import static com.impact.mods.GregTech.enums.Texture.Icons.OVERLAY_RACK;
import static com.impact.mods.GregTech.enums.Texture.Icons.RACK_OVERLAY;
import static gregtech.api.enums.Dyes.MACHINE_METAL;

import com.impact.mods.GregTech.gui.GT_Container_Rack;
import com.impact.mods.GregTech.gui.GT_GUIContainer_Rack;
import com.impact.util.Utilits;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class GTMTE_ComputerRack extends GT_MetaTileEntity_Hatch {

  public int mCapacityP = 0;
  public int mTexturesUpdate = 0;

  public GTMTE_ComputerRack(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional, 5, 4, new String[]{
        Utilits.impactTag(),
        "Parallel points receiver",
        "Used in multi-block machines"
    });
  }

  public GTMTE_ComputerRack(String aName, String[] aDescription, ITexture[][][] aTextures) {
    super(aName, 5, 4, aDescription, aTextures);
  }

  @Override
  public ITexture[] getTexturesActive(ITexture aBaseTexture) {
    ITexture base = new GT_RenderedTexture(OVERLAY_RACK,
        Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA()));
    int meta = mTexturesUpdate;
    return new ITexture[]{aBaseTexture, base, new GT_RenderedTexture(RACK_OVERLAY[meta])};
  }

  @Override
  public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
    ITexture base = new GT_RenderedTexture(OVERLAY_RACK,
        Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA()));
    int meta = mTexturesUpdate;
    return new ITexture[]{aBaseTexture, base, new GT_RenderedTexture(RACK_OVERLAY[meta])};
  }

  @Override
  public void receiveClientEvent(int aB1, int aB2, int aB3, int aB4) {
    super.receiveClientEvent(aB1, aB2, aB3, aB4);
    mTexturesUpdate = aB1;
    getBaseMetaTileEntity().issueTextureUpdate();
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
    return new GTMTE_ComputerRack(mName, mDescriptionArray, mTextures);
  }

  @Override
  public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_Container_Rack(aPlayerInventory, aBaseMetaTileEntity);
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_GUIContainer_Rack(aPlayerInventory, aBaseMetaTileEntity, "123");
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
  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {
    super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
    if (getBaseMetaTileEntity().isServerSide()) {
      getBaseMetaTileEntity().setActive(true);
    }
  }

  @Override
  public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
    if (aBaseMetaTileEntity.isClientSide()) {
      return true;
    }
    aBaseMetaTileEntity.openGUI(aPlayer);
    return true;
  }

  @Override
  public void onCloseGUI() {
    super.onCloseGUI();
    getBaseMetaTileEntity().issueTextureUpdate();
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aBaseMetaTileEntity.isServerSide()) {
    }
  }

  public int getStackSize(ItemStack aInv) {
    if (aInv == null || aInv.stackSize <= 0) {
      return 0;
    }
    return 1;
  }

  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setInteger("mTexturesUpdate", this.mTexturesUpdate);
  }

  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    this.mTexturesUpdate = aNBT.getInteger("mTexturesUpdate");
  }
}
