package com.impact.mods.GregTech.tileentities.multi;

import static com.impact.loader.ItemRegistery.IGlassBlock;

import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

public class GTMTE_Extradifier extends GT_MetaTileEntity_MultiParallelBlockBase {

  public static String mModed;
  Block CASING = Casing_Helper.sCaseCore2;
  byte CASING_META = 2;
  ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
  int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

  public GTMTE_Extradifier(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GTMTE_Extradifier(String aName) {
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
    return new GTMTE_Extradifier(this.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("One-block machine analog")
        .addParallelInfo(1, 256)
        .addTypeMachine("Fluid Extractor, Fluid Solidifier, Fluid Heater")
        .addScrew()
        .addSeparatedBus()
        .addSeparator()
        .addController()
        .addEnergyHatch("Any casing")
        .addMaintenanceHatch("Any casing")
        .addInputBus("Any casing (max x6)")
        .addOutputBus("Any casing (max x6)")
        .addOutputHatch("Any casing (max x6)")
        .addInputHatch("Any casing (max x6)")
        .addParallelHatch("Any casing (max x1)")
        .addCasingInfo("Extradification Casing")
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
    return mMode == 0 ? GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes
        : mMode == 1 ? GT_Recipe.GT_Recipe_Map.sFluidSolidficationRecipes
            : GT_Recipe.GT_Recipe_Map.sFluidHeaterRecipes;
  }

  @Override
  public boolean machineStructure(IGregTechTileEntity thisController) {
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    int minCasingAmount = 12; // Минимальное количество кейсов
    boolean formationChecklist = true; // Если все ок, машина собралась

    for (byte X = -2; X <= 2; X++) {
      for (byte Z = 0; Z >= -4; Z--) {

        if (X == 0 && Z == 0) {
          continue;
        }

        if ((X == -2 || X == 2) && (Z == 0 || Z == -4)) {
          continue;
        }

        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);

        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
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
    for (byte X = -2; X <= 2; X++) {
      for (byte Z = 0; Z >= -4; Z--) {
        for (byte Y = 1; Y <= 3; Y++) {
          final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

          if (X == 0 && Z == 0) {
            continue;
          }

          if ((X == -1 || X == 0 || X == 1) && (Z == 0 || Z == -4)) {
            continue;
          }

          if ((Z == -1 || Z == -2 || Z == -3) && (X == -2 || X == 2)) {
            continue;
          }

          String glass = thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              .getUnlocalizedName();

          if ((X == 0 && (Z == -1 || Z == -3)) || (Z == -2 && (X == -1 || X == 1))) {
            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
            } else {
              formationChecklist = false;
            }
            continue;
          }

          if (X == 0 && Z == -2) {
            continue;
          }

          IGregTechTileEntity currentTE = thisController
              .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
          if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
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
    for (byte X = -2; X <= 2; X++) {
      for (byte Z = 0; Z >= -4; Z--) {

        if ((X == -2 || X == 2) && (Z == 0 || Z == -4)) {
          continue;
        }

        final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 4, Z);

        String glass = thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
            .getUnlocalizedName();

        if ((X == 0 && (Z == -1 || Z == -3)) || (Z == -2 && (X == -1 || X == 1))) {
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

    if (this.mInputBusses.size() > 6) {
      formationChecklist = false;
    }
    if (this.mInputHatches.size() > 6) {
      formationChecklist = false;
    }
    if (this.mOutputBusses.size() > 6) {
      formationChecklist = false;
    }
    if (this.mOutputHatches.size() > 6) {
      formationChecklist = false;
    }
    if (this.mEnergyHatches.size() > 4) {
      formationChecklist = false;
    }
    if (this.mMaintenanceHatches.size() != 1) {
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
    return impactRecipeCheckStackSize(true);
  }

  @Override
  public int getPollutionPerTick(ItemStack aStack) {
    return 0;
  }


  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {

    if (aPlayer.isSneaking()) {
      ScrewClick(aSide, aPlayer, aX, aY, aZ);
    } else if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
      mMode++;
      if (mMode > 2) {
        mMode = 0;
      }

      mModed = (mMode == 0 ? " Fluid Extractor "
          : mMode == 1 ? " Fluid Solidifier " : " Fluid Heater ");
      GT_Utility.sendChatToPlayer(aPlayer,
          "Now" + EnumChatFormatting.YELLOW + mModed + EnumChatFormatting.RESET + "Mode");
    }
  }

}