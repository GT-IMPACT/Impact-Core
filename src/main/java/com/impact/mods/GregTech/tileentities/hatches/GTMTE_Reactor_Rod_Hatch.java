package com.impact.mods.GregTech.tileentities.hatches;

import com.impact.mods.GregTech.gui.GT_Container_Reactor_Rod;
import com.impact.mods.GregTech.gui.GT_GUIContainer_Reactor_Rod;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GTMTE_Reactor_Rod_Hatch extends GT_MetaTileEntity_Hatch {

  public int mDownRod = 0;
  public int mCountCells = 0;
  public float mTemp = 0;
  public boolean mStartReactor = false;
  private final List<ItemStack> sDepleted = new ArrayList<ItemStack>() {{
      add(GT_ModHandler.getIC2Item("reactorDepletedUraniumSimple", 1));
      add(GT_ModHandler.getIC2Item("reactorDepletedUraniumDual", 1));
      add(GT_ModHandler.getIC2Item("reactorDepletedUraniumQuad", 1));
      add(GT_ModHandler.getIC2Item("reactorDepletedMOXSimple", 1));
      add(GT_ModHandler.getIC2Item("reactorDepletedMOXDual", 1));
      add(GT_ModHandler.getIC2Item("reactorDepletedMOXQuad", 1));
    }};

  public GTMTE_Reactor_Rod_Hatch(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional, 5, 1, new String[]{
        "Hatch is used for feeding fuel rods into the reactor",
        "Adjusted the state of the fuel rods by raising or lowering them,",
        "this dependence affects the final operating time",
        "and the output amount of supercritical steam"});
  }

  public GTMTE_Reactor_Rod_Hatch(String aName, String aDescription, ITexture[][][] aTextures) {
    super(aName, 5, 1, aDescription, aTextures);
  }

  public GTMTE_Reactor_Rod_Hatch(String aName, String[] aDescription, ITexture[][][] aTextures) {
    super(aName, 5, 1, aDescription, aTextures);
  }

  @Override
  public ITexture[] getTexturesActive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture,
        new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_MUFFLER)};
  }

  @Override
  public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture,
        new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_MUFFLER)};
  }

  @Override
  public boolean isSimpleMachine() {
    return true;
  }

  @Override
  public boolean isFacingValid(byte aFacing) {
    return aFacing == 1;
  }

  @Override
  public boolean isAccessAllowed(EntityPlayer aPlayer) {
    return true;
  }

  @Override
  public boolean isLiquidInput(byte aSide) {
    return false;
  }

  @Override
  public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_Reactor_Rod_Hatch(mName, mDescriptionArray, mTextures);
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
    return new GT_Container_Reactor_Rod(aPlayerInventory, aBaseMetaTileEntity);
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_GUIContainer_Reactor_Rod(aPlayerInventory, aBaseMetaTileEntity, "Reactor Rod");
  }

  @Override
  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setInteger("mDownRod", mDownRod);
    aNBT.setInteger("mCountCells", mCountCells);
    aNBT.setFloat("mTemp", mTemp);
  }

  @Override
  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    mDownRod = aNBT.getInteger("mDownRod");
    mCountCells = aNBT.getInteger("mCountCells");
    mTemp = aNBT.getFloat("mTemp");
  }

  @Override
  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {
    GT_Utility.sendChatToPlayer(aPlayer, "mDownRod: " + mDownRod);
    GT_Utility.sendChatToPlayer(aPlayer, "mTemp: " + mTemp);
    GT_Utility.sendChatToPlayer(aPlayer, "mCountCells: " + mCountCells);
    super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
  }

  @Override
  public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
      ItemStack aStack) {
    return checkItemStack(aStack);
  }

  private boolean checkItemStack(ItemStack aStack) {
    if (aStack != null) {
      if (aStack.getItem() instanceof GT_RadioactiveCellIC_Item) {
        return ((GT_RadioactiveCellIC_Item) aStack.getItem()).sHeat == 0;
      }
      for (ItemStack is : sDepleted) {
        return aStack.getItem() == is.getItem();
      }
    }
    return false;
  }

  @Override
  public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
      ItemStack aStack) {
    if (aStack.getItem() instanceof GT_RadioactiveCellIC_Item) {
      return ((GT_RadioactiveCellIC_Item) aStack.getItem()).sHeat > 0;
    }
    return false;
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
    if (aTimer < 10) {
      getBaseMetaTileEntity().setFrontFacing((byte) 1);
    }
    ItemStack is = mInventory[0];
    if (aBaseMetaTileEntity.isServerSide() && aTimer % 20 == 0) {
      if (mStartReactor && mDownRod > 0) {
        if (is != null && is.getItem() instanceof GT_RadioactiveCellIC_Item) {
          GT_RadioactiveCellIC_Item radioactiveCellIC_item = (GT_RadioactiveCellIC_Item) is
              .getItem();
          NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(is);
          if (radioactiveCellIC_item.sHeat > 0) {
            mTemp = radioactiveCellIC_item.sHeat * 4 * (radioactiveCellIC_item.sMox ? 4 : 1);
            mCountCells = radioactiveCellIC_item.numberOfCells;
            radioactiveCellIC_item
                .setDamageForStack(is, nbtData.getInteger("advDmg") + mDownRod);
            if (is.getItemDamage() >= is.getMaxDamage() - 1) {
              mInventory[0] = radioactiveCellIC_item.sDepleted.copy();
            }
          }
        }
        mStartReactor = false;
      }
    }
  }

  public void setRodUp(int up) {
    mDownRod -= up;
    if (mDownRod < 0) {
      mDownRod = 0;
    }
  }

  public void setRodDown(int down) {
    mDownRod += down;
    if (mDownRod > 10) {
      mDownRod = 10;
    }
  }

  public void setStartReactor(boolean start) {
    this.mStartReactor = start;
  }

  public void updateSlots() {
    for (int i = 0; i < mInventory.length; i++) {
      if (mInventory[i] != null && mInventory[i].stackSize <= 0) {
        mInventory[i] = null;
      }
    }
    fillStacksIntoFirstSlots();
  }

  protected void fillStacksIntoFirstSlots() {
    for (int i = 0; i < mInventory.length; i++) {
      for (int j = i + 1; j < mInventory.length; j++) {
        if (mInventory[j] != null && (mInventory[i] == null || GT_Utility
            .areStacksEqual(mInventory[i], mInventory[j]))) {
          GT_Utility
              .moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), j, i,
                  (byte) 64, (byte) 1, (byte) 64, (byte) 1);
        }
      }
    }
  }

  @Override
  public int getInventoryStackLimit() {
    return 1;
  }

  @Override
  public boolean isValidSlot(int aIndex) {
    return true;
  }
}
