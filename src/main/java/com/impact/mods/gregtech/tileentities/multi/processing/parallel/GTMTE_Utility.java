package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static gregtech.api.enums.GT_Values.V;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.GUI_BASE;
import com.impact.mods.gregtech.tileentities.hatches.GTMTE_BoxinatorInputBus;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.multis.OverclockCalculate;
import com.impact.util.multis.WorldProperties;
import com.impact.libs.tooltip.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

public class GTMTE_Utility extends GT_MetaTileEntity_MultiParallelBlockBase {

  public static String mModed;
  public final ArrayList<GTMTE_BoxinatorInputBus> sBoxinatorHatch = new ArrayList<>();
  Block CASING = Casing_Helper.sCaseCore1;
  byte CASING_META = 11;
  ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
  int CASING_TEXTURE_ID = CASING_META + 128 * 3;

  public GTMTE_Utility(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GTMTE_Utility(String aName) {
    super(aName);
  }

  @Override
  public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
      final byte aFacing,
      final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
    return aSide == aFacing
        ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
        aActive
            ? Textures.BlockIcons.MP1a
            : Textures.BlockIcons.MP1)}
        : new ITexture[]{INDEX_CASE};
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_Utility(this.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("One-block machine analog")
        .addParallelInfo(1, 256)
        .addTypeMachine("Compressor, Extractor, Canning,")
        .addTypeMachine("Packager, Recycler, Hammer,")
        .addTypeMachine("Lathe, Polarizer")
        .addScrew()
        .addSeparatedBus()
        .addSeparator()
        .addController()
        .addEnergyHatch("Any casing")
        .addMaintenanceHatch("Any casing")
        .addMuffler("Any casing")
        .addInputBus("Any casing (max x6)")
        .addOutputBus("Any casing (max x3)")
        .addParallelHatch("Any casing (max x1)")
        .addCasingInfo("Utility Machine Casing")
        .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
    if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getInformation();
    } else {
      return b.getStructureInformation();
    }
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
        "MultiParallelBlockGUI.png", mModed);
  }

  @Override
  public GT_Recipe.GT_Recipe_Map getRecipeMap() {
    return mMode == 0 ? GT_Recipe.GT_Recipe_Map.sCompressorRecipes
        : mMode == 1 ? GT_Recipe.GT_Recipe_Map.sExtractorRecipes :
            mMode == 2 ? GT_Recipe.GT_Recipe_Map.sCannerRecipes
                : mMode == 3 ? GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes :
                    mMode == 4 ? GT_Recipe.GT_Recipe_Map.sRecyclerRecipes
                        : mMode == 5 ? GT_Recipe.GT_Recipe_Map.sHammerRecipes :
                            mMode == 6 ? GT_Recipe.GT_Recipe_Map.sLatheRecipes
                                : GT_Recipe.GT_Recipe_Map.sPolarizerRecipes;
  }

  public boolean checkBoxinatorHatch(IGregTechTileEntity aTileEntity,
      int aBaseCasingIndex) {
    if (aTileEntity == null) {
      return false;
    } else {
      final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
      if (aMetaTileEntity == null) {
        return false;
      } else if (aMetaTileEntity instanceof GTMTE_BoxinatorInputBus) {
        ((GTMTE_BoxinatorInputBus) aMetaTileEntity).updateTexture(aBaseCasingIndex);
        return sBoxinatorHatch.add((GTMTE_BoxinatorInputBus) aMetaTileEntity);
      } else {
        return false;
      }
    }
  }

  @Override
  public boolean machineStructure(IGregTechTileEntity thisController) {
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    int minCasingAmount = 12; // Минимальное количество кейсов
    boolean formationChecklist = true; // Если все ок, машина собралась

    for (byte X = -1; X <= 4; X++) {
      for (byte Z = 0; Z >= -4; Z--) {

        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -1, Z);
        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !checkBoxinatorHatch(currentTE, CASING_TEXTURE_ID)
            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
              == CASING_META)) {
            minCasingAmount--;
          } else {
            formationChecklist = false;
          }
        }
      }
    }
    for (byte X = -1; X <= 1; X++) {
      for (byte Z = 0; Z >= -4; Z--) {
        for (byte Y = 0; Y <= 1; Y++) {
          final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

          if (X == 0 && Y == 0 && Z == 0) {
            continue;
          }

          if ((Z == -1 || Z == -2 || Z == -3) && X == 0 && Y == 0) {
            continue;
          }
          if ((Z == -1 || Z == -2 || Z == -3) && X == -1 && Y == 0) {
            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
            } else {
              formationChecklist = false;
            }
            continue;
          }

          IGregTechTileEntity currentTE = thisController
              .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
          if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !checkBoxinatorHatch(currentTE, CASING_TEXTURE_ID)
              && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                == CASING_META)) {
              minCasingAmount--;
            } else {
              formationChecklist = false;
            }
          }
        }
      }
    }
    for (byte X = 2; X <= 4; X++) {
      for (byte Z = -1; Z >= -3; Z--) {
        for (byte Y = 0; Y <= 2; Y++) {
          final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

          IGregTechTileEntity currentTE = thisController
              .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
          if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !checkBoxinatorHatch(currentTE, CASING_TEXTURE_ID)
              && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                == CASING_META)) {
              minCasingAmount--;
            } else {
              formationChecklist = false;
            }
          }
        }
      }
    }
    if (this.mInputBusses.size() > 6) {
      formationChecklist = false;
    }
    if (this.mOutputBusses.size() > 3) {
      formationChecklist = false;
    }
    if (this.mEnergyHatches.size() > 4) {
      formationChecklist = false;
    }
    if (this.mMufflerHatches.size() != 1) {
      formationChecklist = false;
    }
    if (this.mMaintenanceHatches.size() != 1) {
      formationChecklist = false;
    }
    if (this.sBoxinatorHatch.size() > 2) {
      formationChecklist = false;
    }
    if (this.sParallHatchesIn.size() > 1) {
      formationChecklist = false;
    }
    if (this.sParallHatchesOut.size() != 0) {
      formationChecklist = false;
    }
    return formationChecklist;
  }

  @Override
  public boolean checkRecipe(ItemStack itemStack) {
    if (getRecipeMap() != GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes) {
      return impactRecipeCheckStackSize();
    }
    return checkRecipeBoxinator();
  }

  public boolean checkRecipeBoxinator() {
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
          if (!WorldProperties.needCleanroom(tRecipe, this)) {
            return false;
          }
          if (!WorldProperties.needSpace(tRecipe, this)) {
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
              OverclockCalculate.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)),
                  tRecipe.mDuration * (divider * 2), 1, nominalV, this);
            } else {
              OverclockCalculate.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV,
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

  @Override
  public int getPollutionPerTick(ItemStack aStack) {
    return 50 * mCheckParallelCurrent;
  }


  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {
    if (aPlayer.isSneaking()) {
      ScrewClick(aSide, aPlayer, aX, aY, aZ);
    } else if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
      mMode++;
      if (mMode > 7) {
        mMode = 0;
      }

      mModed = (mMode == 0 ? " Compressor "
          : mMode == 1 ? " Extractor " : mMode == 2 ? " Canning " :
              mMode == 3 ? " Packager " : mMode == 4 ? " Recycler "
                  : mMode == 5 ? " Hammer " : mMode == 6 ? " Lathe " : " Polarizer ");
      GT_Utility.sendChatToPlayer(aPlayer,
          "Now" + EnumChatFormatting.YELLOW + mModed + EnumChatFormatting.RESET + "Mode");
    }
  }
}