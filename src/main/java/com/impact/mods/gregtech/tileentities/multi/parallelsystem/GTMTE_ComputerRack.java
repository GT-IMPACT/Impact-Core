package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import static com.github.technus.tectech.util.Util.getUniqueIdentifier;
import static com.impact.mods.gregtech.GT_ItemList.*;
import static com.impact.mods.gregtech.enums.Texture.Icons.*;

import com.impact.mods.gregtech.gui.parallelcomputer.GT_Container_Rack;
import com.impact.mods.gregtech.gui.parallelcomputer.GT_GUIContainer_Rack;
import com.impact.util.Utilits;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class GTMTE_ComputerRack extends GT_MetaTileEntity_Hatch {

  public static Map<String, Components> mMapItem = new HashMap<>();
  public int mCapacityP = 0;

  public GTMTE_ComputerRack(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional, 5, 4, new String[]{
        "Increase the total amount of parall points",
        "Used in Super Parallel Computer",
        Utilits.impactTag()
    });
  }

  public GTMTE_ComputerRack(String aName, String[] aDescription, ITexture[][][] aTextures) {
    super(aName, 5, 4, aDescription, aTextures);
  }

  public static void run() {
    new Components(UpgradeCasingT1.get(1), 1);
    new Components(UpgradeCasingT2.get(1), 2);
    new Components(UpgradeCasingT3.get(1), 3);
    new Components(UpgradeCasingT4.get(1), 4);
  }

  @Override
  public ITexture[] getTexturesActive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture, TextureFactory.of(RACK_OVERLAY_ACTIVE)};
  }

  @Override
  public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture, TextureFactory.of(RACK_OVERLAY)};
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
    return new GT_GUIContainer_Rack(aPlayerInventory, aBaseMetaTileEntity, "Computer Rack");
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
      getBaseMetaTileEntity().setActive(aPlayer.isSneaking());
      GT_Utility.sendChatToPlayer(aPlayer, "mCapacityP: " + mCapacityP);
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
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {
      int curCap = 0;
      for (ItemStack stack : mInventory) {
        if (stack == null || stack.stackSize != 1) {
          continue;
        }
        Components comp = mMapItem.get(getUniqueIdentifier(stack));
        if (comp == null) {
          continue;
        }
        curCap += 1 << (2 * comp.capacity);
      }
      this.mCapacityP = curCap;
    }
  }

  public int getStackSize(ItemStack aInv) {
    if (aInv == null || aInv.stackSize <= 0) {
      return 0;
    }
    return 1;
  }

  @Override
  public int getInventoryStackLimit() {
    return 1;
  }

  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setInteger("mCapacityP", this.mCapacityP);
  }

  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    this.mCapacityP = aNBT.getInteger("mCapacityP");
  }

  public static class Components implements Comparable<Components> {

    private final String unlocalizedName;
    private final int capacity;

    public Components(ItemStack is, int capacity) {
      this(Utilits.getUniqueIdentifier(is), capacity);
    }

    public Components(String is, int capacity) {
      unlocalizedName = is;
      this.capacity = capacity;
      mMapItem.put(is, this);
    }

    @Override
    public int compareTo(Components o) {
      return unlocalizedName.compareTo(o.unlocalizedName);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Components) {
        return compareTo((Components) obj) == 0;
      }
      return false;
    }
  }
}
