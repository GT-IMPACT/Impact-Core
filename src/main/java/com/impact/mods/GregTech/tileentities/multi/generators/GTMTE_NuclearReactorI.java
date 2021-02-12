package com.impact.mods.GregTech.tileentities.multi.generators;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlockHint;
import static com.impact.loader.ItemRegistery.decorateBlock;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.GregTech.tileentities.hatches.GTMTE_Reactor_Rod_Hatch;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

public class GTMTE_NuclearReactorI extends GTMTE_NuclearReactorBase {

  final Block GENERAL_CASING = GregTech_API.sBlockCasings3;
  final int GENERAL_CASING_META = 12;
  final Block SECOND_CASING = GregTech_API.sBlockCasings8;
  final int SECOND_CASING_META = 1;
  final int TEXTURE_HATCH = 44;

  public GTMTE_NuclearReactorI(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
    build();
  }

  public GTMTE_NuclearReactorI(String aName) {
    super(aName);
    build();
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    build();
    return new GTMTE_NuclearReactorI(super.mName);
  }

  public void build() {
    registerMetaClass(
        GTMTE_NuclearReactorI.class, new IMultiblockInfoContainer<GTMTE_NuclearReactorI>() {
      //region Structure
      private final IStructureDefinition<GTMTE_NuclearReactorI> definition =
          StructureDefinition.<GTMTE_NuclearReactorI>builder()
              .addShape("main", new String[][] {
                  {"     ","  A  "," AAA "," A~A "},
                  {" AAA "," BAB ","AB BA","AAAAA"},
                  {" ACA ","AA AA","A   A","AAAAA"},
                  {" AAA "," BAB ","AB BA","AAAAA"},
                  {"     ","  A  "," AAA "," AAA "}
              })
              .addElement('A', ofBlock(GENERAL_CASING, GENERAL_CASING_META))
              .addElement('B', ofBlock(SECOND_CASING, SECOND_CASING_META))
              .addElement('C', ofBlockHint(decorateBlock[2], 1))
              .build();
      private final String[] desc = new String[]{
          EnumChatFormatting.RED + "Impact Details:",
          GENERAL_CASING.getLocalizedName(),
          SECOND_CASING.getLocalizedName(),
      };
      //endregion

      @Override
      public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_NuclearReactorI tileEntity,
          ExtendedFacing aSide) {
        IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
        definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
            base.getXCoord(), base.getYCoord(), base.getZCoord(),
            2, 3, 0, hintsOnly);
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
        .addInfo("Radiation!")
        .addTypeMachine("Nuclear Reactor")
        .addInfo("A nuclear reactor that consumes water produces steam")
        .addInfo("at temperatures above 50 degrees, it produces superheated steam and")
        .addInfo("emits radiation within a radius of 10 blocks")
        .addSeparator()
        .beginStructureBlock(0, 0, 0)
        .addController()
        .addNuclearRod("Any top middle casing (max x1)")
        .addInputHatch("Any casing (max x1)")
        .addOutputHatch("Any casing (max x6)")
        .addCasingInfo("Radiation Proof Casing")
        .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
    if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getInformation();
    } else {
      return b.getStructureInformation();
    }
  }

  public boolean checkMachineFunction(IGregTechTileEntity thisController) {
    this.mWrench = true;
    this.mScrewdriver = true;
    this.mSoftHammer = true;
    this.mHardHammer = true;
    this.mSolderingTool = true;
    this.mCrowbar = true;
    boolean checkStructure = true;
    int x, y, z;
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    for (x = -2; x <= 2; x++) {
      for (z = 0; z >= -4; z--) {
        if (x == 0 && z == 0) {
          continue;
        }
        if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
          continue;
        }
        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, z);
        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              == GENERAL_CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
          } else {
              checkStructure = false;
          }
        }
      }
    }

    for (x = -2; x <= 2; x++) {
      for (z = 0; z >= -4; z--) {
        if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
          continue;
        }
        if (x == 0 && (z == -1 || z == -2 || z == -3)) {
          continue;
        }
        if ((x == -1 || x == 1) && z == -2) {
          continue;
        }


        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, z);
        if ((x == -1 || x == 1) && (z == -1 || z == -3)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              == SECOND_CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
          } else {
            checkStructure = false;
          }
          continue;
        }

        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              == GENERAL_CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
          } else {
            checkStructure = false;
          }
        }
      }
    }

    for (x = -2; x <= 2; x++) {
      for (z = 0; z >= -4; z--) {
        if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
          continue;
        }
        if (x == 0 && z == -2) {
          continue;
        }
        if ((x == -1 || x == 1) && (z == 0 || z == -4)) {
          continue;
        }

        if ((x == -2 || x == 2) && (z == -1 || z == -3)) {
          continue;
        }

        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 2, z);
        if ((x == -1 || x == 1) && (z == -1 || z == -3)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              == SECOND_CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
          } else {
            checkStructure = false;
          }
          continue;
        }

        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              == GENERAL_CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
          } else {
            checkStructure = false;
          }
        }
      }
    }

    for (x = -1; x <= 1; x++) {
      for (z = -1; z >= -3; z--) {
        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 3, z);
        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

        if (x == 0 && z == -2) {
          if (!checkRodHatches(currentTE, TEXTURE_HATCH)) {
            checkStructure = false;
          }
          continue;
        }
        if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              == GENERAL_CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
          } else {
            checkStructure = false;
          }
        }
      }
    }

    return checkStructure;
  }

  @Override
  public String[] getInfoData() {
    float temp = 0;
    for (GTMTE_Reactor_Rod_Hatch rod : mRodHatches) {
      temp += rod.mTemp * 10F;
    }
    return new String[]{
        "Current Temperature: " + EnumChatFormatting.RED + (100F * mCurrentTemp / temp) + " %",
        "Input Water: " + EnumChatFormatting.RED
            + mCurrentOutput + EnumChatFormatting.RESET + " L/t",
        "Output Steam: " + EnumChatFormatting.GREEN + (int)(mCurrentOutput * 160F)
            + EnumChatFormatting.RESET + " L/t"
    };
  }

}
