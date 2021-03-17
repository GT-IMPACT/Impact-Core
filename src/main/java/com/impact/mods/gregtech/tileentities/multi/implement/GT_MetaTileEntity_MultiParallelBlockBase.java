package com.impact.mods.gregtech.tileentities.multi.implement;

import static com.impact.core.Refstrings.MODID;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine.isValidForLowGravity;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DynamoMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DynamoTunnel;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.client.gui.GUIHandler;
import com.impact.core.Impact_API;
import com.impact.mods.gregtech.gui.GT_Container_MultiParallelMachine;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_SawMill;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ComputerRack;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Receiver;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Transmitter;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import com.impact.util.Utilits;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nonnegative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ArrayUtils;

public abstract class GT_MetaTileEntity_MultiParallelBlockBase extends
    GT_MetaTileEntity_MultiBlockBase {

  public final HashSet<GTMTE_ParallelHatch_Input> sParallHatchesIn = new HashSet<>();
  public final HashSet<GTMTE_ParallelHatch_Output> sParallHatchesOut = new HashSet<>();
  public final HashSet<GTMTE_SpaceSatellite_Transmitter> sCommunTransmitter = new HashSet<>();
  public final HashSet<GTMTE_SpaceSatellite_Receiver> sCommunReceiver = new HashSet<>();
  public final HashSet<GTMTE_ComputerRack> sComputerRack = new HashSet<>();
  public boolean mRecipeCheckParallel = false;
  public int mParallel = 0;
  public int modeBuses = 0;
  public byte mMode = -1;
  public int mFrequency = -1;
  public int mTargetX = 0;
  public int mTargetY = 0;
  public int mTargetZ = 0;
  public int mTargetD = 0;
  public boolean mIsConnect = false;
  public TileEntity tile = null;

  public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName,
      final String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GT_MetaTileEntity_MultiParallelBlockBase(final String aName) {
    super(aName);
  }

  public static void calculateOverclockedNessMulti(@Nonnegative int aEUt,
      @Nonnegative int aDuration, @Nonnegative int mAmperage, @Nonnegative long maxInputVoltage,
      GT_MetaTileEntity_MultiParallelBlockBase base) {
    byte mTier = (byte) Math.max(0, GT_Utility.getTier(maxInputVoltage));
    if (mTier == 0) {
      //Long time calculation
      long xMaxProgresstime = ((long) aDuration) << 1;
      if (xMaxProgresstime > Integer.MAX_VALUE - 1) {
        //make impossible if too long
        base.mEUt = Integer.MAX_VALUE - 1;
        base.mMaxProgresstime = Integer.MAX_VALUE - 1;
      } else {
        base.mEUt = aEUt >> 2;
        base.mMaxProgresstime = (int) xMaxProgresstime;
      }
    } else {
      //Long EUt calculation
      long xEUt = aEUt;
      //Isnt too low EUt check?
      long tempEUt = Math.max(xEUt, V[1]);

      base.mMaxProgresstime = aDuration;

      while (tempEUt <= V[mTier - 1] * mAmperage) {
        tempEUt <<= 2;//this actually controls overclocking
        //xEUt *= 4;//this is effect of everclocking
        base.mMaxProgresstime >>= 1;//this is effect of overclocking
        xEUt = base.mMaxProgresstime <= 0 ? xEUt >> 1
            : xEUt << 2;//U know, if the time is less than 1 tick make the machine use less power
      }

      while (xEUt > maxInputVoltage) {
        //downclock one notch until we are good again, we have overshot.
        xEUt >>= 2;
        base.mMaxProgresstime <<= 1;
      }

      if (xEUt > Integer.MAX_VALUE - 1) {
        base.mEUt = Integer.MAX_VALUE - 1;
        base.mMaxProgresstime = Integer.MAX_VALUE - 1;
      } else {
        base.mEUt = (int) xEUt;
        if (base.mEUt == 0) {
          base.mEUt = 1;
        }
        if (base.mMaxProgresstime <= 0) {
          base.mMaxProgresstime = 1;//set time to 1 tick
        }
      }
    }
  }

  //TODO: 20.02.2021 Future
  public static void calculateOverclockedNew(@Nonnegative int aEUt,
      @Nonnegative int aDuration, @Nonnegative int mAmperage, @Nonnegative long maxInputVoltage,
      GT_MetaTileEntity_MultiParallelBlockBase base) {
    byte mTier = (byte) Math.max(0, GT_Utility.getTier(maxInputVoltage));
    if (mTier == 0) {
      //Long time calculation
      long xMaxProgresstime = ((long) aDuration) << 1;
      if (xMaxProgresstime > Integer.MAX_VALUE - 1) {
        //make impossible if too long
        base.mEUt = Integer.MAX_VALUE - 1;
        base.mMaxProgresstime = Integer.MAX_VALUE - 1;
      } else {
        base.mEUt = aEUt >> 2;
        base.mMaxProgresstime = (int) xMaxProgresstime;
      }
    } else {
      //Long EUt calculation
      long xEUt = aEUt;
      //Isnt too low EUt check?
      long tempEUt = Math.max(xEUt, V[1]);
      base.mMaxProgresstime = aDuration;
      while (tempEUt <= V[mTier - 1] * mAmperage) {
        tempEUt = (tempEUt << 2) * 3 / 2;
//        tempEUt <<= 2; //bit shift to 2 / example: 480 << 2 = 1920
//        tempEUt *= 3; //1920 * 4 = 7680  | 1920 * 3 = 5760
        base.mMaxProgresstime >>= 2; //this is effect of overclocking
        xEUt = base.mMaxProgresstime <= 0 ? (xEUt >> 2) * 3 / 2 : (xEUt << 2) * 3 / 2;
        //U know, if the time is less than 1 tick make the machine use less power
      }
      while (xEUt > maxInputVoltage) {
        //downclock one notch until we are good again, we have overshot.
        xEUt = (xEUt << 2) * 3 / 2;
        base.mMaxProgresstime <<= 2;
      }
      if (xEUt > Integer.MAX_VALUE - 1) {
        base.mEUt = Integer.MAX_VALUE - 1;
        base.mMaxProgresstime = Integer.MAX_VALUE - 1;
      } else {
        base.mEUt = (int) xEUt;
        if (base.mEUt == 0) {
          base.mEUt = 1;
        }
        if (base.mMaxProgresstime <= 0) {
          base.mMaxProgresstime = 1;
        }
      }
    }
  }

  public static void calculateOverclockedNessMultiPefectOC(int aEUt, int aDuration, int mAmperage,
      long maxInputVoltage, GT_MetaTileEntity_MultiParallelBlockBase base) {
    byte mTier = (byte) Math.max(0, GT_Utility.getTier(maxInputVoltage));
    if (mTier == 0) {
      //Long time calculation
      long xMaxProgresstime = ((long) aDuration) << 1;
      if (xMaxProgresstime > Integer.MAX_VALUE - 1) {
        //make impossible if too long
        base.mEUt = Integer.MAX_VALUE - 1;
        base.mMaxProgresstime = Integer.MAX_VALUE - 1;
      } else {
        base.mEUt = aEUt >> 2;
        base.mMaxProgresstime = (int) xMaxProgresstime;
      }
    } else {
      long xEUt = aEUt;
      //Isnt too low EUt check?
      long tempEUt = Math.max(xEUt, V[1]);

      base.mMaxProgresstime = aDuration;

      while (tempEUt <= V[mTier - 1] * mAmperage) {
        tempEUt <<= 1;//this actually controls overclocking
        //this is effect of overclocking
        xEUt = base.mMaxProgresstime <= 0 ? xEUt >> 1
            : xEUt << 1;//U know, if the time is less than 1 tick make the machine use less power
      }

      while (xEUt > maxInputVoltage) {
        //downclock one notch until we are good again, we have overshot.
        xEUt >>= 1;
        base.mMaxProgresstime <<= 1;
      }
      if (xEUt > Integer.MAX_VALUE - 1) {
        base.mEUt = Integer.MAX_VALUE - 1;
        base.mMaxProgresstime = Integer.MAX_VALUE - 1;
      } else {
        base.mEUt = (int) xEUt;
        if (base.mEUt == 0) {
          base.mEUt = 1;
        }
        if (base.mMaxProgresstime <= 0) {
          base.mMaxProgresstime = 1;//set time to 1 tick
        }
      }
    }
  }

  public static ItemStack[] clean(final ItemStack[] v) {
    List<ItemStack> list = new ArrayList<ItemStack>(Arrays.asList(v));
    list.removeAll(Collections.singleton(null));
    return list.toArray(new ItemStack[list.size()]);
  }

  public static boolean isValidMetaTileEntity(MetaTileEntity aMetaTileEntity) {
    return aMetaTileEntity.getBaseMetaTileEntity() != null
        && aMetaTileEntity.getBaseMetaTileEntity().getMetaTileEntity() == aMetaTileEntity
        && !aMetaTileEntity.getBaseMetaTileEntity().isDead();
  }

  public void ScrewClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
    if (aPlayer.isSneaking()) {
      if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
        modeBuses++;
        if (modeBuses > 1) {
          modeBuses = 0;
        }

        GT_Utility.sendChatToPlayer(aPlayer, "Buses separated " + (modeBuses == 0 ? "on" : "off"));
      }
    }
  }

  @Override
  public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_Container_MultiParallelMachine(aPlayerInventory, aBaseMetaTileEntity);
  }

  public int getMaxEfficiency(ItemStack aStack) {
    return 10000;
  }

  public boolean isCorrectMachinePart(ItemStack aStack) {
    return true;
  }

  public boolean isFacingValid(byte aFacing) {
    return aFacing > 1;
  }

  public int getDamageToComponent(ItemStack aStack) {
    return 0;
  }

  public boolean explodesOnComponentBreak(ItemStack aStack) {
    return false;
  }


  public boolean impactRecipe() {
    ArrayList<ItemStack> tInputList = getStoredInputs();
    int tInputList_sS = tInputList.size();
    for (int i = 0; i < tInputList_sS - 1; i++) {
      for (int j = i + 1; j < tInputList_sS; j++) {
        if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
          if ((tInputList.get(i)).stackSize >= (tInputList.get(j)).stackSize) {
            tInputList.remove(j--);
            tInputList_sS = tInputList.size();
          } else {
            tInputList.remove(i--);
            tInputList_sS = tInputList.size();
            break;
          }
        }
      }
    }
    tInputList.add(mInventory[1]);
    ItemStack[] inputs = tInputList.toArray(new ItemStack[tInputList.size()]);

    ArrayList<FluidStack> tFluidList = getStoredFluids();
    int tFluidList_sS = tFluidList.size();
    for (int i = 0; i < tFluidList_sS - 1; i++) {
      for (int j = i + 1; j < tFluidList_sS; j++) {
        if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
          if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
            tFluidList.remove(j--);
            tFluidList_sS = tFluidList.size();
          } else {
            tFluidList.remove(i--);
            tFluidList_sS = tFluidList.size();
            break;
          }
        }
      }
    }
    FluidStack[] fluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

    if (inputs.length > 0 || fluids.length > 0) {
      long voltage = getMaxInputVoltageVanila();
      byte tier = (byte) Math.max(1, GT_Utility.getTier(voltage));
      GT_Recipe recipe = getRecipeMap().findRecipe(getBaseMetaTileEntity(), false,
          false, GT_Values.V[tier], fluids, inputs);
      if (recipe != null && recipe.isRecipeInputEqual(true, fluids, inputs)) {

        if (!needCleanroom(recipe)) {
          return false;
        }
        if (!needSpace(recipe)) {
          return false;
        }

        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;

        int EUt = recipe.mEUt;
        int maxProgresstime = recipe.mDuration;

        while (EUt <= gregtech.api.enums.GT_Values.V[tier - 1] && maxProgresstime > 2) {
          EUt *= 4;
          maxProgresstime /= 2;
        }
        if (getBaseMetaTileEntity().getMetaTileEntity() instanceof GTMTE_SawMill) {
          EUt /= 4;
          maxProgresstime *= 2;
        }
        if (maxProgresstime < 2) {
          maxProgresstime = 2;
          EUt = recipe.mEUt * recipe.mDuration / 2;
        }

        this.mEUt = -EUt;
        this.mMaxProgresstime = maxProgresstime;
        this.mOutputItems = recipe.mOutputs;
        this.mOutputFluids = recipe.mFluidOutputs;
        this.updateSlots();
        return true;
      }
    }
    return false;
  }

  public boolean impactRecipe(ItemStack itemStack) {
    ArrayList<ItemStack> tInputList = null;
    ArrayList<FluidStack> tFluidList = null;
    ItemStack[] tInputs = null;
    FluidStack[] tFluids = null;
    for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
      if (modeBuses == 0) {
        ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
        tBus.mRecipeMap = getRecipeMap();
        if (isValidMetaTileEntity(tBus)) {
          for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
            if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null) {
              tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
            }
          }
        }
        tInputList = this.getStoredInputs();
        tFluidList = this.getStoredFluids();
        tInputs = tBusItems.toArray(new ItemStack[]{});
        tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      } else {
        tInputList = this.getStoredInputs();
        tFluidList = this.getStoredFluids();
        tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);
        tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      }
      if (tInputList.size() > 0 || tFluidList.size() > 0) {
        long nominalV = getMaxInputVoltage();
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
        GT_Recipe tRecipe;
        tRecipe = getRecipeMap()
            .findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);

        if (tRecipe != null) {

          if (!needCleanroom(tRecipe)) {
            return false;
          }
          if (!needSpace(tRecipe)) {
            return false;
          }

          ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
          ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();
          boolean found_Recipe = false;
          int processed = 0;
          while ((this.getStoredFluids().size() | this.getStoredInputs().size()) > 0
              && processed < 1) {
            if ((tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe
                .isRecipeInputEqual(true, tFluids, tInputs)) {
              found_Recipe = true;
              for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                outputItems.add(tRecipe.getOutput(i));
              }
              for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
                outputFluids.add(tRecipe.getFluidOutput(i));
              }
              ++processed;
            } else {
              break;
            }
          }
          if (found_Recipe) {
            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            long actualEUT = (long) (tRecipe.mEUt) * processed;

            calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);

            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1
                && this.mEUt == Integer.MAX_VALUE - 1) {
              return false;
            }
            if (this.mEUt > 0) {
              this.mEUt = (-this.mEUt);
            }
            this.mOutputItems = new ItemStack[outputItems.size()];
            this.mOutputItems = outputItems.toArray(this.mOutputItems);
            this.mOutputFluids = new FluidStack[outputFluids.size()];
            this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);
            this.updateSlots();
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean impactRecipeCheckStackSize(boolean aChance) {
    if (sParallHatchesIn.size() > 0 && getRecipeCheckParallel()) {
      return false;
    }
    ArrayList<ItemStack> tInputList = null;
    ArrayList<FluidStack> tFluidList = null;
    ItemStack[] tInputs = null;
    FluidStack[] tFluids = null;
    for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
      if (modeBuses == 0) {
        ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
        tBus.mRecipeMap = getRecipeMap();
        if (isValidMetaTileEntity(tBus)) {
          for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
            if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null) {
              tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
            }
          }
        }
        tInputList = this.getStoredInputs();
        tFluidList = this.getStoredFluids();
        tInputs = tBusItems.toArray(new ItemStack[]{});
        tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      } else {
        tInputList = this.getStoredInputs();
        tFluidList = this.getStoredFluids();
        tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);
        tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      }
      if (tInputList.size() > 0 || tFluidList.size() > 0) {

        GT_Recipe tRecipe;

        long nominalV = getMaxInputVoltage();
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));

        tRecipe = getRecipeMap()
            .findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);

        if (tRecipe != null) {

          if (!needCleanroom(tRecipe)) {
            return false;
          }
          if (!needSpace(tRecipe)) {
            return false;
          }

          ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
          ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();

          boolean found_Recipe = false;
          int processed = 0;

          ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];

          while ((tFluidList.size() > 0 || tInputList.size() > 0) && processed < mParallel) {
            if ((tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe
                .isRecipeInputEqual(true, tFluids, tInputs)) {
              found_Recipe = true;
              for (int h = 0; h < tRecipe.mOutputs.length; h++) {
                if (tRecipe.getOutput(h) != null) {
                  tOut[h] = tRecipe.getOutput(h).copy();
                  tOut[h].stackSize = 0;
                }
              }

              for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
                outputFluids.add(tRecipe.getFluidOutput(i));
              }

              ++processed;

            } else {
              break;
            }
          }

          if (aChance) {
            for (int f = 0; f < tOut.length; f++) {
              if (tRecipe.mOutputs[f] != null && tOut[f] != null) {
                for (int g = 0; g < processed; g++) {
                  if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe
                      .getOutputChance(f)) {
                    tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
                  }
                }
              }
            }
          }

          tOut = clean(tOut);

          List<ItemStack> overStacks = new ArrayList<ItemStack>();

          for (int f = 0; f < tOut.length; f++) {
            while (tOut[f].getMaxStackSize() < tOut[f].stackSize) {
              if (tOut[f] != null) {
                ItemStack tmp = tOut[f].copy();
                tmp.stackSize = tmp.getMaxStackSize();
                tOut[f].stackSize = tOut[f].stackSize - tOut[f].getMaxStackSize();
                overStacks.add(tmp);
              }
            }
          }

          if (overStacks.size() > 0) {
            ItemStack[] tmp = new ItemStack[overStacks.size()];
            tmp = overStacks.toArray(tmp);
            tOut = ArrayUtils.addAll(tOut, tmp);
          }

          List<ItemStack> tSList = new ArrayList<ItemStack>();

          for (ItemStack tS : tOut) {
            if (tS.stackSize > 0) {
              tSList.add(tS);
            }
          }

          tOut = tSList.toArray(new ItemStack[tSList.size()]);

          if (found_Recipe) {

            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            long actualEUT = (long) (tRecipe.mEUt) * processed;

            if (actualEUT > Integer.MAX_VALUE) {
              byte divider = 0;
              while (actualEUT > Integer.MAX_VALUE) {
                actualEUT = actualEUT / 2;
                divider++;
              }
              calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)),
                  tRecipe.mDuration * (divider * 2), 1, nominalV, this);
            } else {
              calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV,
                  this);
            }

            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1
                && this.mEUt == Integer.MAX_VALUE - 1) {
              return false;
            }

            if (this.mEUt > 0) {
              this.mEUt = (-this.mEUt);
            }

            int TimeProgress;
            switch (mParallel) {
              default:
                TimeProgress = this.mMaxProgresstime;
                break;
              case 16:
                TimeProgress = this.mMaxProgresstime / 2;
                break;
              case 64:
                TimeProgress = this.mMaxProgresstime / 3;
                break;
              case 256:
                TimeProgress = this.mMaxProgresstime / 4;
                break;
            }

            this.mMaxProgresstime = TimeProgress;
            if (this.mMaxProgresstime < 1) {
              this.mMaxProgresstime = 1;
            }

            this.mOutputItems = tOut;
            this.mOutputFluids = new FluidStack[outputFluids.size()];
            this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);

            this.updateSlots();
            return true;
          }
        }
      }
    }
    return false;
  }

  private ArrayList<ItemStack> separateBus() {
    return null;
  }

  public boolean needCleanroom(GT_Recipe tRecipe) {
    boolean isNotCleanroom = true;
    if (tRecipe.mSpecialValue == -200 && (mCleanroom == null
        || mCleanroom.mEfficiency == 0)) {
      isNotCleanroom = false;
    }
    return isNotCleanroom;
  }

  public boolean needSpace(GT_Recipe tRecipe) {
    boolean isNotSpace = true;
    if (GT_Mod.gregtechproxy.mLowGravProcessing && (tRecipe.mSpecialValue == -100)
        && !isValidForLowGravity(tRecipe,
        getBaseMetaTileEntity().getWorld().provider.dimensionId)) {
      isNotSpace = false;
    }
    return isNotSpace;
  }

  public boolean impactRecipeWithStackSize() {
    if (sParallHatchesIn.size() > 0 && getRecipeCheckParallel()) {
      return false;
    }
    ArrayList<ItemStack> tInputList;
    ArrayList<FluidStack> tFluidList;
    ItemStack[] tInputs;
    FluidStack[] tFluids;
    for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
      if (modeBuses == 0) {
        ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
        tBus.mRecipeMap = getRecipeMap();
        if (isValidMetaTileEntity(tBus)) {
          for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
            if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null) {
              tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
            }
          }
        }
        tInputList = this.getStoredInputs();
        tFluidList = this.getStoredFluids();
        tInputs = tBusItems.toArray(new ItemStack[]{});
        tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      } else {
        tInputList = this.getStoredInputs();
        tFluidList = this.getStoredFluids();
        tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);
        tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      }
      if (tInputList.size() > 0 || tFluidList.size() > 0) {
        long nominalV = getMaxInputVoltage();
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
        GT_Recipe tRecipe = getRecipeMap()
            .findRecipe(this.getBaseMetaTileEntity(), false, false, V[tTier], tFluids, tInputs);
        if (tRecipe != null) {
          if (!needCleanroom(tRecipe)) {
            return false;
          }
          if (!needSpace(tRecipe)) {
            return false;
          }
          ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
          ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();
          boolean found_Recipe = false;
          int processed = 0;
          ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];
          while ((tFluidList.size() > 0 || tInputList.size() > 0) && processed < mParallel) {
            if ((tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe
                .isRecipeInputEqual(true, tFluids, tInputs)) {
              found_Recipe = true;
              for (int h = 0; h < tRecipe.mOutputs.length; h++) {
                if (tRecipe.getOutput(h) != null) {
                  tOut[h] = tRecipe.getOutput(h).copy();
                  tOut[h].stackSize = 0;
                }
              }
              for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
                outputFluids.add(tRecipe.getFluidOutput(i));
              }
              ++processed;
            } else {
              break;
            }
          }
          for (int f = 0; f < tOut.length; f++) {
            if (tRecipe.mOutputs[f] != null && tOut[f] != null) {
              for (int g = 0; g < processed; g++) {
                if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe
                    .getOutputChance(f)) {
                  tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
                }
              }
            }
          }
          tOut = clean(tOut);
          List<ItemStack> overStacks = new ArrayList<ItemStack>();
          for (ItemStack stack : tOut) {
            while (stack.getMaxStackSize() < stack.stackSize) {
              ItemStack tmp = stack.copy();
              tmp.stackSize = tmp.getMaxStackSize();
              stack.stackSize = stack.stackSize - stack.getMaxStackSize();
              overStacks.add(tmp);
            }
          }
          if (overStacks.size() > 0) {
            ItemStack[] tmp = new ItemStack[overStacks.size()];
            tmp = overStacks.toArray(tmp);
            tOut = ArrayUtils.addAll(tOut, tmp);
          }
          List<ItemStack> tSList = new ArrayList<ItemStack>();
          for (ItemStack tS : tOut) {
            if (tS.stackSize > 0) {
              tSList.add(tS);
            }
          }
          tOut = tSList.toArray(new ItemStack[tSList.size()]);
          if (found_Recipe) {
            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            long actualEUT = (long) (tRecipe.mEUt) * processed;

            if (actualEUT > Integer.MAX_VALUE) {
              byte divider = 0;
              while (actualEUT > Integer.MAX_VALUE) {
                actualEUT = actualEUT / 2;
                divider++;
              }
              calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)),
                  tRecipe.mDuration * (divider * 2), 1, nominalV, this);
            } else {
              calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV,
                  this);
            }
            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1
                && this.mEUt == Integer.MAX_VALUE - 1) {
              return false;
            }
            if (this.mEUt > 0) {
              this.mEUt = (-this.mEUt);
            }
            int TimeProgress;
            switch (mParallel) {
              default:
                TimeProgress = this.mMaxProgresstime;
                break;
              case 16:
                TimeProgress = this.mMaxProgresstime / 2;
                break;
              case 64:
                TimeProgress = this.mMaxProgresstime / 3;
                break;
              case 256:
                TimeProgress = this.mMaxProgresstime / 4;
                break;
            }
            this.mMaxProgresstime = TimeProgress;
            if (this.mMaxProgresstime < 1) {
              this.mMaxProgresstime = 1;
            }
            this.mOutputItems = tOut;
            this.mOutputFluids = new FluidStack[outputFluids.size()];
            this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);

            this.updateSlots();
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean impactRecipeCheckStackSize() {
    if (sParallHatchesIn.size() > 0 && getRecipeCheckParallel()) {
      return false;
    }
    ArrayList<ItemStack> tInputList;
    ArrayList<FluidStack> tFluidList;
    ItemStack[] tInputs;
    FluidStack[] tFluids;
    for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
      if (modeBuses == 0) {
        ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
        tBus.mRecipeMap = getRecipeMap();
        if (isValidMetaTileEntity(tBus)) {
          for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
            if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null) {
              tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
            }
          }
        }
        tInputList = this.getStoredInputs();
        tFluidList = this.getStoredFluids();
        tInputs = tBusItems.toArray(new ItemStack[]{});
        tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      } else {
        tInputList = this.getStoredInputs();
        tFluidList = this.getStoredFluids();
        tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);
        tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      }
      if (tInputList.size() > 0 || tFluidList.size() > 0) {
        long nominalV = getMaxInputVoltage();
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
        GT_Recipe tRecipe = getRecipeMap()
            .findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);
        if (tRecipe != null) {
          if (!needCleanroom(tRecipe)) {
            return false;
          }
          if (!needSpace(tRecipe)) {
            return false;
          }
          ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
          ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();
          boolean found_Recipe = false;
          int processed = 0;
          ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];
          while ((tFluidList.size() > 0 || tInputList.size() > 0) && processed < mParallel) {
            if ((tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe
                .isRecipeInputEqual(true, tFluids, tInputs)) {
              found_Recipe = true;
              for (int h = 0; h < tRecipe.mOutputs.length; h++) {
                if (tRecipe.getOutput(h) != null) {
                  tOut[h] = tRecipe.getOutput(h).copy();
                  tOut[h].stackSize = 0;
                }
              }
              for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
                outputFluids.add(tRecipe.getFluidOutput(i));
              }
              ++processed;
            } else {
              break;
            }
          }
          for (int f = 0; f < tOut.length; f++) {
            if (tRecipe.mOutputs[f] != null && tOut[f] != null) {
              for (int g = 0; g < processed; g++) {
                if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe
                    .getOutputChance(f)) {
                  tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
                }
              }
            }
          }
          tOut = clean(tOut);
          List<ItemStack> overStacks = new ArrayList<ItemStack>();
          for (ItemStack stack : tOut) {
            while (stack.getMaxStackSize() < stack.stackSize) {
              ItemStack tmp = stack.copy();
              tmp.stackSize = tmp.getMaxStackSize();
              stack.stackSize = stack.stackSize - stack.getMaxStackSize();
              overStacks.add(tmp);
            }
          }
          if (overStacks.size() > 0) {
            ItemStack[] tmp = new ItemStack[overStacks.size()];
            tmp = overStacks.toArray(tmp);
            tOut = ArrayUtils.addAll(tOut, tmp);
          }
          List<ItemStack> tSList = new ArrayList<ItemStack>();
          for (ItemStack tS : tOut) {
            if (tS.stackSize > 0) {
              tSList.add(tS);
            }
          }
          tOut = tSList.toArray(new ItemStack[tSList.size()]);
          if (found_Recipe) {
            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            long actualEUT = (long) (tRecipe.mEUt) * processed;

            if (actualEUT > Integer.MAX_VALUE) {
              byte divider = 0;
              while (actualEUT > Integer.MAX_VALUE) {
                actualEUT = actualEUT / 2;
                divider++;
              }
              calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)),
                  tRecipe.mDuration * (divider * 2), 1, nominalV, this);
            } else {
              calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV,
                  this);
            }
            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1
                && this.mEUt == Integer.MAX_VALUE - 1) {
              return false;
            }
            if (this.mEUt > 0) {
              this.mEUt = (-this.mEUt);
            }
            int TimeProgress;
            switch (mParallel) {
              default:
                TimeProgress = this.mMaxProgresstime;
                break;
              case 16:
                TimeProgress = this.mMaxProgresstime / 2;
                break;
              case 64:
                TimeProgress = this.mMaxProgresstime / 3;
                break;
              case 256:
                TimeProgress = this.mMaxProgresstime / 4;
                break;
            }
            this.mMaxProgresstime = TimeProgress;
            if (this.mMaxProgresstime < 1) {
              this.mMaxProgresstime = 1;
            }
            this.mOutputItems = tOut;
            this.mOutputFluids = new FluidStack[outputFluids.size()];
            this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);

            this.updateSlots();
            return true;
          }
        }
      }
    }
    return false;
  }

  public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
    final Vector3i offset = new Vector3i();

    // В любом направлении по оси Z
    if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
      offset.x = x;
      offset.y = y;
      offset.z = z;
    }
    if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
      offset.x = -x;
      offset.y = y;
      offset.z = -z;
    }
    // В любом направлении по оси X
    if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
      offset.x = z;
      offset.y = y;
      offset.z = -x;
    }
    if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
      offset.x = -z;
      offset.y = y;
      offset.z = x;
    }
    // в любом направлении по оси Y
    if (forgeDirection.y() == -1) {
      offset.x = x;
      offset.y = z;
      offset.z = y;
    }
    return offset;
  }

  public long getMaxInputVoltageVanila() {
    long rVoltage = 0;
    for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage();
      }
    }
    for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : mEnergyHatchesTT) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.Amperes;
      }
    }
    for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : mEnergyTunnelsTT) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.Amperes;
      }
    }
    return rVoltage;
  }

  public long getMaxInputVoltage() {
    long rVoltage = 0;
    for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.mAmpers;
      }
    }
    for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : mEnergyHatchesTT) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.Amperes;
      }
    }
    for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : mEnergyTunnelsTT) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.Amperes;
      }
    }
    return rVoltage;
  }

  public long getMaxOutputVoltage() {
    long rVoltage = 0;
    for (GT_MetaTileEntity_Hatch_Dynamo tHatch : mDynamoHatches) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.mAmpers;
      }
    }
    for (GT_MetaTileEntity_Hatch_DynamoMulti tHatch : mDynamoHatchesTT) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.Amperes;
      }
    }
    for (GT_MetaTileEntity_Hatch_DynamoTunnel tHatch : mDynamoTunnelsTT) {
      if (isValidMetaTileEntity(tHatch)) {
        rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.Amperes;
      }
    }
    return rVoltage;
  }

  public int getTierEnergyHatch() {
    int aTier = 0;
    for (GT_MetaTileEntity_Hatch_Energy tEHatch : mEnergyHatches) {
      if (isValidMetaTileEntity(tEHatch)) {
        aTier = tEHatch.mTier;
      }
    }
    for (GT_MetaTileEntity_Hatch_EnergyMulti tEHatch : mEnergyHatchesTT) {
      if (isValidMetaTileEntity(tEHatch)) {
        aTier = tEHatch.mTier;
      }
    }
    for (GT_MetaTileEntity_Hatch_EnergyTunnel tEHatch : mEnergyTunnelsTT) {
      if (isValidMetaTileEntity(tEHatch)) {
        aTier = tEHatch.mTier;
      }
    }
    return aTier;
  }

  @Override
  public void saveNBTData(NBTTagCompound aNBT) {
    aNBT.setByte("mMode", mMode);
    aNBT.setInteger("mTargetX", this.mTargetX);
    aNBT.setInteger("mTargetY", this.mTargetY);
    aNBT.setInteger("mTargetZ", this.mTargetZ);
    aNBT.setInteger("mTargetD", this.mTargetD);
    aNBT.setInteger("mFrequency", this.mFrequency);
    aNBT.setBoolean("mIsReceive", this.mIsConnect);
    aNBT.setInteger("mParallel", this.mParallel);
    super.saveNBTData(aNBT);
  }

  @Override
  public void loadNBTData(NBTTagCompound aNBT) {
    this.mMode = aNBT.getByte("mMode");
    this.mTargetX = aNBT.getInteger("mTargetX");
    this.mTargetY = aNBT.getInteger("mTargetY");
    this.mTargetZ = aNBT.getInteger("mTargetZ");
    this.mTargetD = aNBT.getInteger("mTargetD");
    this.mFrequency = aNBT.getInteger("mFrequency");
    this.mIsConnect = aNBT.getBoolean("mIsReceive");
    this.mParallel = aNBT.getInteger("mParallel");
    super.loadNBTData(aNBT);
  }

  @Override
  public String[] addInfoData() {
    final ArrayList<String> ll = new ArrayList<>();

    ll.add(mParallel > 1 ? "Parallel Point: " + Integer.toString(mParallel) : "Parallel not found");

    final String[] a = new String[ll.size()];
    return ll.toArray(a);
  }

  public int getPollutionPerTick(ItemStack aStack) {
    return 0;
  }

  /*
   * NEW PARALLEL SYSTEM TEST
   */

  @Override
  public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
    super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
    if (!aPlayer.isSneaking()) {
      if (sParallHatchesIn.size() > 0 || getBaseMetaTileEntity()
          .getMetaTileEntity() instanceof GTMTE_ParallelComputer) {
        aPlayer.openGui(MODID, GUIHandler.GUI_ID_LapTop, this.getBaseMetaTileEntity().getWorld(),
            this.getBaseMetaTileEntity().getXCoord(), this.getBaseMetaTileEntity().getYCoord(),
            this.getBaseMetaTileEntity().getZCoord());
      }
    }
  }

  public void setFrequency(int aFreq, EntityPlayer aPlayer) {
    mFrequency = aFreq;
    Impact_API.sCommunicationTower.put(Integer.toString(aFreq) + aPlayer.getUniqueID(),
        new int[]{getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(),
            getBaseMetaTileEntity().getZCoord(),
            getBaseMetaTileEntity().getWorld().provider.dimensionId});
    GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + EnumChatFormatting.GREEN + aFreq);
    GT_Utility.sendChatToPlayer(aPlayer, "UUID: " + EnumChatFormatting.YELLOW + aPlayer.getUniqueID()); //// TODO: 21.02.2021 DEL
  }

  public void getFrequency(int aFreq, EntityPlayer aPlayer) {
    if (Impact_API.sCommunicationTower.get(Utilits.inToStringUUID(aFreq, aPlayer)) != null) {
      //GT_Utility.sendChatToPlayer(aPlayer, "Coords: " + Arrays.toString(Impact_API.sCommunicationTower.get(aFreq)));
      if (Utilits.distanceBetween2D(getBaseMetaTileEntity().getXCoord(),
          Impact_API.sCommunicationTower.get(Utilits.inToStringUUID(aFreq, aPlayer))[0],
          getBaseMetaTileEntity().getZCoord(), Impact_API.sCommunicationTower.get(Utilits.inToStringUUID(aFreq, aPlayer))[2]) < 256) {
        setCoord(Impact_API.sCommunicationTower.get(Utilits.inToStringUUID(aFreq, aPlayer)));
        GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.GREEN + "Connection successful");
      } else {
        GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Too far for connection");
      }
    } else {
      GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Frequency not found");
    }
  }

  public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
    clearHatches();
    return machineStructure(thisController);
  }

  public abstract boolean machineStructure(IGregTechTileEntity thisController);

  public void setCoord(int[] coords) {
    this.mTargetX = coords[0];
    this.mTargetY = coords[1];
    this.mTargetZ = coords[2];
    this.mTargetD = coords[3];
  }

  public boolean addCommunicationHatchToMachineList(IGregTechTileEntity aTileEntity,
      int aBaseCasingIndex) {
    if (aTileEntity == null) {
      return false;
    } else {
      final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
      if (aMetaTileEntity == null) {
        return false;
      } else if (aMetaTileEntity instanceof GTMTE_SpaceSatellite_Receiver) {
        ((GTMTE_SpaceSatellite_Receiver) aMetaTileEntity).updateTexture(aBaseCasingIndex);
        return sCommunReceiver.add((GTMTE_SpaceSatellite_Receiver) aMetaTileEntity);
      } else if (aMetaTileEntity instanceof GTMTE_SpaceSatellite_Transmitter) {
        ((GTMTE_SpaceSatellite_Transmitter) aMetaTileEntity).updateTexture(aBaseCasingIndex);
        return sCommunTransmitter.add((GTMTE_SpaceSatellite_Transmitter) aMetaTileEntity);
      } else {
        return false;
      }
    }
  }

  public boolean addParallHatchToMachineList(IGregTechTileEntity aTileEntity,
      int aBaseCasingIndex) {
    if (aTileEntity == null) {
      return false;
    } else {
      final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
      if (aMetaTileEntity == null) {
        return false;
      } else if (aMetaTileEntity instanceof GTMTE_ParallelHatch_Input) {
        ((GTMTE_ParallelHatch_Input) aMetaTileEntity).updateTexture(aBaseCasingIndex);
        return sParallHatchesIn.add((GTMTE_ParallelHatch_Input) aMetaTileEntity);
      } else if (aMetaTileEntity instanceof GTMTE_ParallelHatch_Output) {
        ((GTMTE_ParallelHatch_Output) aMetaTileEntity).updateTexture(aBaseCasingIndex);
        return sParallHatchesOut.add((GTMTE_ParallelHatch_Output) aMetaTileEntity);
      } else {
        return false;
      }
    }
  }

  public boolean addRackHatch(IGregTechTileEntity aTileEntity,
      int aBaseCasingIndex) {
    if (aTileEntity == null) {
      return false;
    } else {
      final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
      if (aMetaTileEntity == null) {
        return false;
      } else if (aMetaTileEntity instanceof GTMTE_ComputerRack) {
        ((GTMTE_ComputerRack) aMetaTileEntity).updateTexture(aBaseCasingIndex);
        return sComputerRack.add((GTMTE_ComputerRack) aMetaTileEntity);
      } else {
        return false;
      }
    }
  }

  public boolean getRecipeCheckParallel() {
    return !mRecipeCheckParallel;
  }

  public void setRecipeCheckParallel(boolean isTrue) {
    mRecipeCheckParallel = isTrue;
  }

  public int getParallelCurrent() {
    return mParallel;
  }

  public int getParallel() {
    return mParallel;
  }

  public void setParallel(int setParallel) {
    mParallel = setParallel;
  }

  public void clearHatches() {
    sParallHatchesOut.clear();
    sParallHatchesIn.clear();
    sComputerRack.clear();
    sCommunReceiver.clear();
    sCommunTransmitter.clear();
    mDynamoTunnelsTT.clear();
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {
      connectParallelHatches();
      connectParallelComputer(aBaseMetaTileEntity);
    }
  }

  public void connectParallelHatches() {
    int maxParallel = 1;
    boolean isDebug = false;
    if (sParallHatchesIn.size() > 0) { //todo parallel
      for (GTMTE_ParallelHatch_Input ph : sParallHatchesIn) {
        maxParallel = ph.getMaxParallel();
        setRecipeCheckParallel(ph.getTrueRecipe());
        isDebug = ph.isDebug;
      }
      setParallel(maxParallel);
      if (isDebug) {
        setRecipeCheckParallel(true);
        mIsConnect = true;
        return;
      }
      if (getRecipeCheckParallel() || !mIsConnect) {
        setParallel(1); //todo check future
      }
    } else {
      setParallel(1);
    }
  }

  public void connectParallelComputer(IGregTechTileEntity aBaseMetaTileEntity) {
    mIsConnect = false;
    boolean isDebug = false;
    if (sParallHatchesIn.size() > 0 || aBaseMetaTileEntity
        .getMetaTileEntity() instanceof GTMTE_ParallelComputer) {

      for (GTMTE_ParallelHatch_Input ph : sParallHatchesIn) {
        isDebug = ph.isDebug;
      }
      if (isDebug) {
        mIsConnect = true;
        return;
      }
      tile = aBaseMetaTileEntity.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
      if (tile != null && tile instanceof IGregTechTileEntity) {
        IMetaTileEntity aComTower = ((IGregTechTileEntity) tile).getMetaTileEntity();
        if (aComTower instanceof GTMTE_TowerCommunication) {
          if (aComTower.getBaseMetaTileEntity().isActive()
              && mFrequency == ((GTMTE_TowerCommunication) aComTower).mFrequency) {
              mIsConnect = ((GTMTE_TowerCommunication) aComTower).mIsConnect;
          }
        }
      }
    }
  }
}
