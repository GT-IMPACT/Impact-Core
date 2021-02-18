package com.impact.mods.GregTech.tileentities.multi;

import static com.impact.util.Utilits.isB;
import static gregtech.api.enums.GT_Values.V;

import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

public class GTMTE_AdvancedPyrolyse extends GT_MetaTileEntity_MultiParallelBlockBase {

  public int mParallelPoint = 1;
  Block CASING = GregTech_API.sBlockCasings8;
  byte CASING_META = 3;
  ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[1][48 + CASING_META];
  int CASING_TEXTURE_ID = CASING_META + 48 + 128;
  int frameId = 4096 + Materials.HSLA.mMetaItemSubID;
  int frameMeta = GregTech_API.METATILEENTITIES[frameId].getTileEntityBaseType();

  public GTMTE_AdvancedPyrolyse(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GTMTE_AdvancedPyrolyse(String aName) {
    super(aName);
  }

  @Override
  public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
      final byte aFacing,
      final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
    return aSide == aFacing ?
        new ITexture[]{INDEX_CASE,
            new GT_RenderedTexture(
                aActive ? Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE
                    : Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER)} :
        new ITexture[]{INDEX_CASE};
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_AdvancedPyrolyse(this.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addTypeMachine("Pyrolyse oven")
        .addInfo("Converts hydrocarbons into gases, wood tar and solid fuels")
        .addInfo("The process emits gases throughout the entire time (60s):")
        .addInfo("for 7s - CO, for 14s - H\u2082, for 21s - CH\u2084, for 28s - CO\u2082 ")
        .addInfo("and 35s - solid fuels and wood tar")
        .addInfo("Input/output of products depends of energy hatch, time does not change")
        .addInfo("LV - 1x, MV - 2x, HV - 3x and etc")
        .addPollution(100, "x tier energy hatch")
        .addSeparator()
        .addController()
        .addEnergyHatch("Any HSLA casing")
        .addMaintenanceHatch("Any HSLA casing")
        .addMuffler("Any HSLA casing")
        .addInputBus("Any HSLA casing (max x1)")
        .addOutputBus("Any HSLA casing (max x1)")
        .addOutputHatch("Any HSLA casing (max x1)")
        .addCasingInfo("HSLA Casing")
        .addOtherStructurePart("Steel Pipe Casing", "Middle line")
        .addOtherStructurePart("HSLA Frame", "Bottom angles")
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
        "MultiParallelBlockGUI.png", " Pyrolyse ");
  }

  @Override
  public boolean checkRecipe(ItemStack itemStack) {
    int xPar = tierHatch() * 2;

    ArrayList<ItemStack> tInputList = getStoredInputs();
    ArrayList<FluidStack> tFluidList = this.getStoredFluids();
    ItemStack[] tInputs = tInputList.toArray(new ItemStack[]{});
    FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

    if (tInputList.size() > 0 || tFluidList.size() > 0) {

      GT_Recipe tRecipe;

      long nominalV = getMaxInputVoltage();
      byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));

      tRecipe = getRecipeMap()
          .findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);

      if (tRecipe != null) {
        ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
        ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();

        boolean found_Recipe = false;
        int processed = 0;

        ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];

        while ((tFluidList.size() > 0 || tInputList.size() > 0) && processed < xPar) {
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
        mParallelPoint = processed;

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

          this.mEUt = (tRecipe.mEUt) * processed * tierHatch() / 2;

            if (this.mEUt > 0) {
                this.mEUt = (-this.mEUt);
            }

          this.mMaxProgresstime = tRecipe.mDuration;

          this.mOutputItems = tOut;
          this.mOutputFluids = new FluidStack[outputFluids.size()];
          this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);

          this.updateSlots();
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public GT_Recipe.GT_Recipe_Map getRecipeMap() {
    return GT_Recipe.GT_Recipe_Map.sPyrolyseBasic;
  }

  public int tierHatch() {
    int Tier = 0;
      for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
          if (isValidMetaTileEntity(tHatch)) {
              Tier = tHatch.mTier;
          }
      }
    setParallel(Math.max(Tier, 1));
    return Math.max(Tier, 1);
  }

  @Override
  public boolean onRunningTick(ItemStack aStack) {
    switch (this.mProgresstime) {
      case 7 * 20:
        addOutput(Materials.CarbonMonoxide.getGas(72L * mParallelPoint));
        break;
      case 14 * 20:
        addOutput(Materials.Hydrogen.getGas(288L * mParallelPoint));
        break;
      case 21 * 20:
        addOutput(Materials.Methane.getGas(144L * mParallelPoint));
        break;
      case 28 * 20:
        addOutput(Materials.CarbonDioxide.getGas(216L * mParallelPoint));
        break;
      case 35 * 20:
        addOutput(
            GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Charcoal, 5 * mParallelPoint));
        break;
    }
    return super.onRunningTick(aStack);
  }

  public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
    TThatches();
    // Вычисляем вектор направления, в котором находится задняя поверхность контроллера
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    boolean formationChecklist = true; // Если все ок, машина собралась

    for (byte X = 0; X <= 8; X++) {
      for (byte Z = 0; Z >= -2; Z--) {

        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, -1, Z);

        if (isB(Z, -1) && isB(X, 4, 8)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
          } else {
            formationChecklist = false;
          }
          continue;
        }
          if ((isB(Z, 0) || isB(Z, -2)) && (isB(X, 1) || isB(X, 3) || isB(X, 5, 7))) {
              continue;
          }
          if (isB(Z, -1, 3)) {
              continue;
          }

        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
            == GregTech_API.sBlockMachines)
            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == frameMeta)) {
        } else {
          formationChecklist = false;
        }
      }
    }

    for (byte X = 0; X <= 8; X++) {
      for (byte Z = 0; Z >= -2; Z--) {
          if (X == 0 && Z == 0) {
              continue;
          }

        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);

        if (isB(Z, -1) && isB(X, 1, 4)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              == GregTech_API.sBlockCasings2)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 13)) {
          } else {
            formationChecklist = false;
          }
          continue;
        }

          if ((isB(Z, 0) || isB(Z, -2)) && isB(X, 3)) {
              continue;
          }
          if (isB(Z, -1) && isB(X, 5, 7)) {
              continue;
          }

        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
          } else {
            formationChecklist = false;
          }
        }
      }
    }
//
    for (byte X = 0; X <= 8; X++) {
      for (byte Z = 0; Z >= -2; Z--) {

        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 1, Z);

          if (isB(X, 3)) {
              continue;
          }

        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 3)) {
          } else {
            formationChecklist = false;
          }
        }
      }
    }

      if (this.mInputBusses.size() > 1) {
          formationChecklist = false;
      }
      if (this.mInputHatches.size() > 1) {
          formationChecklist = false;
      }
      if (this.mOutputHatches.size() > 1) {
          formationChecklist = false;
      }
      if (this.mOutputBusses.size() > 1) {
          formationChecklist = false;
      }
      if (this.mEnergyHatches.size() > 1) {
          formationChecklist = false;
      }
      if (this.mMaintenanceHatches.size() != 1) {
          formationChecklist = false;
      }
      if (this.mMufflerHatches.size() != 1) {
          formationChecklist = false;
      }
    return formationChecklist;
  }

  @Override
  public int getPollutionPerTick(ItemStack aStack) {
    return 5 * mParallel;
  }
}