package com.impact.mods.GregTech.tileentities.multi.generators;

import static com.impact.mods.GregTech.blocks.Casing_Helper.sCaseCore1;
import static com.impact.mods.GregTech.enums.Texture.Icons.REACTOR_OVERLAY;
import static com.impact.mods.GregTech.enums.Texture.Icons.REACTOR_OVERLAY_ACTIVE;

import com.impact.mods.GregTech.gui.GT_Container_NuclearReactor;
import com.impact.mods.GregTech.gui.GUI_NuclearReactor;
import com.impact.mods.GregTech.tileentities.hatches.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import ic2.core.IC2DamageSource;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidRegistry;

public abstract class GTMTE_NuclearReactorBase extends GT_MetaTileEntity_MultiParallelBlockBase {

  final int MAX_TEMP = 999;
  final int RADIATION_DAMAGE = 1;
  final int RADIUS_RADIATION_DAMAGE = 10;
  public ArrayList<GTMTE_Reactor_Rod_Hatch> mRodHatches = new ArrayList<>();
  public boolean mFirstStart = false;
  public long mCurrentTemp = 1;
  public long mMaxTemp = 1;
  public long mCurrentOutput = 1;
  ITexture INDEX_CASE = Textures.BlockIcons.CASING_BLOCKS[12 + 32];


  public GTMTE_NuclearReactorBase(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GTMTE_NuclearReactorBase(String aName) {
    super(aName);
  }

  @Override
  public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
      byte aColorIndex, boolean aActive, boolean aRedstone) {
    if (aSide == aFacing) {
      return new ITexture[]{INDEX_CASE,
          new GT_RenderedTexture(aActive ? REACTOR_OVERLAY_ACTIVE : REACTOR_OVERLAY)};
    }
    return new ITexture[]{INDEX_CASE};
  }

  @Override
  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setLong("mCurrentTemp", mCurrentTemp);
    aNBT.setLong("mMaxTemp", mMaxTemp);
    aNBT.setLong("mCurrentOutput", mCurrentOutput);
    aNBT.setBoolean("mFirstStart", mFirstStart);
  }

  @Override
  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    this.mCurrentTemp = aNBT.getLong("mCurrentTemp");
    this.mMaxTemp = aNBT.getLong("mMaxTemp");
    this.mCurrentOutput = aNBT.getLong("mCurrentOutput");
    this.mFirstStart = aNBT.getBoolean("mFirstStart");
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GUI_NuclearReactor(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
        "NuclearReactorGUI.png");
  }

  @Override
  public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_Container_NuclearReactor(aPlayerInventory, aBaseMetaTileEntity);
  }

  @Override
  public boolean checkRecipe(ItemStack aStack) {
    if (mFirstStart) {
      this.mMaxProgresstime = 20;
      this.mEUt = 0;
      this.mEfficiencyIncrease = 1000;
      if ((100F * mCurrentTemp / mMaxTemp) > 50) {
        List list1 = getBaseMetaTileEntity().getWorld()
            .getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB
                .getBoundingBox((getBaseMetaTileEntity().getXCoord() - RADIUS_RADIATION_DAMAGE),
                    (getBaseMetaTileEntity().getYCoord() - RADIUS_RADIATION_DAMAGE),
                    (getBaseMetaTileEntity().getZCoord() - RADIUS_RADIATION_DAMAGE),
                    (getBaseMetaTileEntity().getXCoord() + RADIUS_RADIATION_DAMAGE),
                    (getBaseMetaTileEntity().getYCoord() + RADIUS_RADIATION_DAMAGE),
                    (getBaseMetaTileEntity().getZCoord() + RADIUS_RADIATION_DAMAGE)));

        for (Object o : list1) {
          Entity ent = (Entity) o;
          ent.attackEntityFrom(IC2DamageSource.radiation,
              (float) ((int) ((float) getBaseMetaTileEntity().getWorld().rand.nextInt(4) * RADIATION_DAMAGE)));
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    long aTemp = 0;
    long aCountCells = 0;
    long aMaxTemp = 0;
    int countTime = 20 * 2;
    if (!aBaseMetaTileEntity.isActive()) {
      mCurrentTemp--;
      if (mCurrentTemp <= 0) {
        mCurrentTemp = 0;
      }
    }
    if (mRodHatches.size() > 0) {
      for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {
        if (rod_hatch.mInventory[0] != null && rod_hatch.mInventory[0]
            .getItem() instanceof GT_RadioactiveCellIC_Item) {
          if (((GT_RadioactiveCellIC_Item) rod_hatch.mInventory[0]
              .getItem()).sHeat > 0) {
            if (mEfficiency >= getMaxEfficiency(null)) {
              rod_hatch.setStartReactor(aBaseMetaTileEntity.isActive());
            }
            int checkRod = Math.max(rod_hatch.mDownRod, 0);
            float checkHeat = rod_hatch.mDownRod > 0 ? rod_hatch.mTemp : 0;
            int checkCells = rod_hatch.mDownRod > 0 ? rod_hatch.mCountCells : 0;
            aCountCells += checkCells;
            aTemp += (checkHeat * checkRod);
            aMaxTemp += checkHeat * 10;
          }
        }
      }
    }
    if (aCountCells == 0 || aTemp == 0 || aMaxTemp == 0) {
      aCountCells = 1;
      aTemp = 1;
      aMaxTemp = mMaxTemp;
      if (aTick % countTime == 0) {
        mCurrentTemp--;
      }
    }

    mMaxTemp = aMaxTemp;
    if (aTick % countTime == 0) {
      if (mCurrentTemp > aTemp) {
        mCurrentTemp--;
      } else if (mCurrentTemp < aTemp) {
        mCurrentTemp++;
      }
    }
    mCurrentOutput = ((mCurrentTemp * aCountCells) / 4) / 2;
    if (super.mEfficiency > (getMaxEfficiency(null) / 8) && !depleteInput(
        Materials.Water.getFluid(mCurrentOutput))) {
      for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {

        rod_hatch.getBaseMetaTileEntity().getWorld().setBlock(
            rod_hatch.getBaseMetaTileEntity().getXCoord(),
            rod_hatch.getBaseMetaTileEntity().getYCoord() - 1,
            rod_hatch.getBaseMetaTileEntity().getZCoord(),
            Blocks.flowing_lava
        );
        if (aTick % 20 * 2 == 0) {
          rod_hatch.getBaseMetaTileEntity().doExplosion(Long.MAX_VALUE);
        }
      }
    }
    if (super.mEfficiency == getMaxEfficiency(null)) {
      double tTemp = (double) mCurrentTemp / (double) mMaxTemp;
      int checkSteam = (int) (100 * tTemp);
      if (checkSteam < 50) {
        addOutput(Materials.Water.getGas(mCurrentOutput * 160));
      } else {
        addOutput(FluidRegistry
            .getFluidStack("ic2superheatedsteam", (int) mCurrentOutput * 160));
      }
    }
  }

  public int[] getRodStatus() {
    int[] rodsStatus = new int[mRodHatches.size()];
    for (int i = 0; i < mRodHatches.size(); i++) {
      rodsStatus[i] = mRodHatches.get(i).mDownRod;
    }
    return rodsStatus;
  }

  public void setRodUp(int up) {
    for (GTMTE_Reactor_Rod_Hatch rod : mRodHatches) {
      rod.setRodUp(up);
    }
  }

  public void setRodDown(int down) {
    for (GTMTE_Reactor_Rod_Hatch rod : mRodHatches) {
      rod.setRodDown(down);
    }
  }

  public abstract boolean checkMachineFunction(IGregTechTileEntity thisController);

  public boolean checkRodHatches(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
    if (aTileEntity == null) {
      return false;
    }
    IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
    if (aMetaTileEntity == null) {
      return false;
    }
    if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch) {
      ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
    }
    if (aMetaTileEntity instanceof GTMTE_Reactor_Rod_Hatch) {
      return mRodHatches.add((GTMTE_Reactor_Rod_Hatch) aMetaTileEntity);
    }
    return false;
  }

  @Override
  public boolean machineStructure(IGregTechTileEntity thisController) {
    mRodHatches.clear();
    return this.checkMachineFunction(thisController);
  }
}
