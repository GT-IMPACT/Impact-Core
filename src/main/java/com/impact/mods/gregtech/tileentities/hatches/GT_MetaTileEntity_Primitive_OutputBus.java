package com.impact.mods.gregtech.tileentities.hatches;

import com.impact.util.string.Language;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_1by1;
import gregtech.api.gui.GT_GUIContainer_1by1;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GT_MetaTileEntity_Primitive_OutputBus extends GT_MetaTileEntity_Hatch {

  public byte mMachineBlock = 0;
  private byte mTexturePage = 0;
  private byte actualTexture = 0;

  public GT_MetaTileEntity_Primitive_OutputBus(int aID, String aName, String aNameRegional,
      int aTier) {
    super(aID, aName, aNameRegional, aTier, getSlots(aTier),
            new String[]{
                    Language.transDesc("basic.hatch.primitive.item.out.0",  "Primitive Item Output for Coke Oven"),
                    Language.transDesc("basic.hatch.primitive.item.out.1", "Capacity: 1 stack")
            });
  }

  public GT_MetaTileEntity_Primitive_OutputBus(String aName, int aTier, String aDescription,
      ITexture[][][] aTextures) {
    super(aName, 0, 1, aDescription, aTextures);
  }

  public GT_MetaTileEntity_Primitive_OutputBus(String aName, int aTier, String[] aDescription,
      ITexture[][][] aTextures) {
    super(aName, 0, 1, aDescription, aTextures);
  }


  @Override
  public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
      byte aColorIndex, boolean aActive, boolean aRedstone) {
    if (aSide == aFacing) {
      return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][53], TextureFactory.of(
              Textures.BlockIcons.OVERLAY_PIPE_OUT)};
    } else if (aSide != aFacing) {
      return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][53]};
    }
    int textureIndex = this.actualTexture | this.mTexturePage << 7;
    int texturePointer = (byte) (this.actualTexture & 127);
    return aSide != aFacing ? (textureIndex > 0 ? new ITexture[]{
        Textures.BlockIcons.casingTexturePages[this.mTexturePage][texturePointer]}
        : new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColorIndex + 1]})
        : (textureIndex > 0 ? (aActive ? this.getTexturesActive(
            Textures.BlockIcons.casingTexturePages[this.mTexturePage][texturePointer]) : this
            .getTexturesInactive(
                Textures.BlockIcons.casingTexturePages[this.mTexturePage][texturePointer]))
            : (aActive ? this
                .getTexturesActive(Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColorIndex + 1])
                : this.getTexturesInactive(
                    Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColorIndex + 1])));

  }

  @Override
  public ITexture[] getTexturesActive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture,
        TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
  }

  @Override
  public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture,
        TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
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
  public boolean isValidSlot(int aIndex) {
    return true;
  }

  @Override
  public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GT_MetaTileEntity_Primitive_OutputBus(mName, mTier, mDescriptionArray, mTextures);
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
  public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity, "Primitive Output Bus");
  }

  @Override
  public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
      ItemStack aStack) {
    return aSide == aBaseMetaTileEntity.getFrontFacing();
  }

  @Override
  public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
      ItemStack aStack) {
    return false;
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isAllowedToWork()
        && (aTick & 0x7) == 0) {
      IInventory tTileEntity = aBaseMetaTileEntity
          .getIInventoryAtSide(aBaseMetaTileEntity.getFrontFacing());
      if (tTileEntity != null) {
        for (ItemStack aMInventory : mInventory) {
          GT_Utility.moveOneItemStack(aBaseMetaTileEntity, tTileEntity,
              aBaseMetaTileEntity.getFrontFacing(), aBaseMetaTileEntity.getBackFacing(),
              null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
        }
      }
    }
  }
}
