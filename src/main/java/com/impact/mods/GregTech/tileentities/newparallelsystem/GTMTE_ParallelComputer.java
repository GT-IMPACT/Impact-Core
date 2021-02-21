package com.impact.mods.GregTech.tileentities.newparallelsystem;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static com.mojang.realmsclient.gui.ChatFormatting.GREEN;
import static com.mojang.realmsclient.gui.ChatFormatting.RED;
import static com.mojang.realmsclient.gui.ChatFormatting.RESET;
import static com.mojang.realmsclient.gui.ChatFormatting.YELLOW;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

public class GTMTE_ParallelComputer extends GT_MetaTileEntity_MultiParallelBlockBase {

  public int mMaxCapacityPP = 0;
  public int mCurrentCapacityPP = 0;
  public static Block CASING = Casing_Helper.sCasePage8_3;
  public static byte CASING_META = 7;
  public static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[8][CASING_META + 64];
  public static int CASING_TEXTURE_ID = CASING_META + 64 + 128 * 8;

  //region Register
  public GTMTE_ParallelComputer(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
    run();
  }

  public GTMTE_ParallelComputer(String aName) {
    super(aName);
    run();
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    run();
    return new GTMTE_ParallelComputer(this.mName);
  }
  //endregion

  @Override
  public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
      final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
    return aSide == aFacing ? new ITexture[]{INDEX_CASE,
        new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)}
        : new ITexture[]{INDEX_CASE};
  }

  public void run() {
    registerMetaClass(GTMTE_ParallelComputer.class,
        new IMultiblockInfoContainer<GTMTE_ParallelComputer>() {
          //region Structure
          private final IStructureDefinition<GTMTE_ParallelComputer> definition =
              StructureDefinition.<GTMTE_ParallelComputer>builder()
                  .addShape("main", new String[][]{
                      {"AAAAA", "AAAAA", "AA~AA", "AAAAA", "AAAAA"},
                      {"CCCCC", "CBBBC", "CBBBC", "CBBBC", "CCCCC"},
                      {"CCCCC", "CBBBC", "CBBBC", "CBBBC", "CCCCC"},
                      {"CCCCC", "CBBBC", "CBBBC", "CBBBC", "CCCCC"},
                      {"CCCCC", "CBBBC", "CBBBC", "CBBBC", "CCCCC"},
                      {"CCCCC", "CBBBC", "CBBBC", "CBBBC", "CCCCC"},
                      {"CCCCC", "CBBBC", "CBBBC", "CBBBC", "CCCCC"},
                      {"CCCCC", "CBBBC", "CBBBC", "CBBBC", "CCCCC"},
                      {"AAAAA", "AAAAA", "AAAAA", "AAAAA", "AAAAA"}
                  })
                  .addElement('A', ofBlock(CASING, CASING_META))
                  .addElement('B', ofBlock(Casing_Helper.sCaseCore1, 0))
                  .addElement('C', ofBlock(IGlassBlock))
                  .build();
          private final String[] desc = new String[]{
              EnumChatFormatting.RED + "Impact Details:",
              //todo
          };

          //endregion
          @Override
          public void construct(ItemStack stackSize, boolean hintsOnly,
              GTMTE_ParallelComputer tileEntity, ExtendedFacing aSide) {
            IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
            definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                base.getXCoord(), base.getYCoord(), base.getZCoord(),
                2, 2, 0, hintsOnly);
          }

          @Override
          public String[] getDescription(ItemStack stackSize) {
            return desc;
          }
        });
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addTypeMachine("Block Digger")
        .addSeparator()
        .addController()
        .addEnergyHatch("Any casing")
        .addMaintenanceHatch("Any casing")
        .addOutputBus("Any casing (max x1)")
        .addInputHatch("Any casing (max x1)")
        .addOtherStructurePart("Block of Digger (x1)", "to the very center from below")
        .addCasingInfo("Moon Miner Casing")
        .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
    if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getInformation();
    } else {
      return b.getStructureInformation();
    }
  }

  @Override
  public boolean checkRecipe(ItemStack aStack) {
    this.mMaxProgresstime = 10;
    this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
    this.mEfficiencyIncrease = 10000;
    this.mEUt = 0; // TODO: 21.02.2021 CHANGE
    for (GTMTE_ParallelHatch_Output ph : sParallHatchesOut) {
      if ((getCurrentCapacityPP() - ph.getMaxParallel()) < 0) {
        ph.getBaseMetaTileEntity().setActive(false);
      }
    }
    return true;
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {
      connect(aBaseMetaTileEntity);
      int maxCur = 0;
      boolean isActive = false;
      for (GTMTE_ComputerRack rack : sComputerRack) {
        if (aBaseMetaTileEntity.isActive()) {
          maxCur += rack.mCapacityP;
          isActive = true;
        }
        rack.getBaseMetaTileEntity().setActive(isActive);
      }
      setMaxCapacityPP(maxCur);
    }
  }

  public void connect(IGregTechTileEntity aBaseMetaTileEntity) {
    boolean isActive = false;
    for (GTMTE_ParallelHatch_Output ph : sParallHatchesOut) {
      if (aBaseMetaTileEntity.isActive()) {
        if (ph.getBaseMetaTileEntity().isAllowedToWork() || mIsConnect) {
          isActive = true;
        }
      }
      ph.setRecipe(isActive);
    }
  }


  public int getCurrentCapacityPP() {
    return mCurrentCapacityPP;
  }

  public void setCurrentCapacityPP(int curCaPP) {
    mCurrentCapacityPP = curCaPP;
  }

  public int getMaxCapacityPP() {
    return mMaxCapacityPP;
  }

  public void setMaxCapacityPP(int setMax) {
    setCurrentCapacityPP(setMax);
    mMaxCapacityPP = setMax;
  }

  @Override
  public boolean checkMachine(IGregTechTileEntity thisController, ItemStack aStack) {
    sParallHatchesOut.clear();
    sComputerRack.clear();
    int aTotalParallelCapacity = 0;

    //region Structure
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    boolean formationChecklist = true;

    for (int X = 0; X <= 1; X++) {
      for (int Y = -1; Y <= 2; Y++) {
        if (X == 0 && Y == 0) {
          continue;
        }

        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);

        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
              == CASING_META)) {
          } else {
            formationChecklist = false;
          }
        }
      }
    }

    int additionalZ = -4;
    int ParallelsZ = 0;
    int RacksZ = 0;
    for (int X = 0; X <= 1; X++) {
      for (int Y = -1; Y <= 2; Y++) {
        for (int Z = -1; Z >= -17; Z--) {
          final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
          IGregTechTileEntity currentTE = thisController
              .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

          //Racks
          if (X == 1 && (Y == 0 || Y == 1)) {
            if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                && !super.addRackHatch(currentTE, CASING_TEXTURE_ID)) {
              if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING
                  && thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                  == CASING_META) {
                RacksZ = Z;
                break;
              } else if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                  == InsideBlock
                  && thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                  == 2) {
              } else {
                formationChecklist = false;
              }
            }
          }

          //Parallels
          if (X == 0 && (Y == 0 || Y == 1)) {
            if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                && !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {

              if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                  && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                  == CASING_META)) {
                ParallelsZ = Z;
                break;
              } else if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                  == InsideBlock
                  && thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                  == 2) {
              } else {
                formationChecklist = false;
              }
            }
          }
        }
      }
    }

    if (ParallelsZ < RacksZ || RacksZ < ParallelsZ) {
      formationChecklist = false;
    } else {
      additionalZ = RacksZ;
    }

    for (int X = 0; X <= 1; X++) {
      for (int Y = -1; Y <= 2; Y++) {
        for (int Z = -1; Z >= additionalZ; Z--) {
          final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
          if (Z == additionalZ) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                == CASING_META)) {
            } else {
              formationChecklist = false;
            }
          }
        }
      }
    }
    //endregion
    int cur = 0;
    for (GTMTE_ParallelHatch_Output ph : sParallHatchesOut) {
      if ((getCurrentCapacityPP() - ph.getMaxParallel()) >= 0) {
        if (ph.getBaseMetaTileEntity().isAllowedToWork()) {
          cur = ph.getMaxParallel();
          mCurrentCapacityPP -= cur;
        }
      }
    }

    this.mWrench = true;
    this.mScrewdriver = true;
    this.mSoftHammer = true;
    this.mHardHammer = true;
    this.mSolderingTool = true;
    this.mCrowbar = true;

    return formationChecklist;
  }

  @Override
  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setInteger("mMaxCapacityPP", mMaxCapacityPP);
    aNBT.setInteger("mCurrentCapacityPP", mCurrentCapacityPP);
  }

  @Override
  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    mMaxCapacityPP = aNBT.getInteger("mMaxCapacityPP");
    mCurrentCapacityPP = aNBT.getInteger("mCurrentCapacityPP");
  }

  @Override
  public int getParallelCurrent() {
    if (getMaxCapacityPP() < 4) {
      return -1;
    }
    return getMaxCapacityPP();
  }

  @Override
  public int getPollutionPerTick(ItemStack aStack) {
    return 0;
  }

  public String[] getInfoData() {
    return new String[]{
        "Storage PP: " + GREEN + getMaxCapacityPP() + RESET + " / " + YELLOW
            + getCurrentCapacityPP() + "PP",
        "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
        "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
        "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good "
            + YELLOW + mEfficiency / 100.0F + " %" + RESET
            : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
    };
  }
}
