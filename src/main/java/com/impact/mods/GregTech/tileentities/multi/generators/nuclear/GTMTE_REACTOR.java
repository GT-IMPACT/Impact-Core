package com.impact.mods.GregTech.tileentities.multi.generators.nuclear;

import static com.impact.mods.GregTech.blocks.Casing_Helper.sCaseCore1;

import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

public class GTMTE_REACTOR extends GT_MetaTileEntity_MultiParallelBlockBase {

  public ArrayList<GTMTE_Reactor_Rod_Hatch> mRodHatches = new ArrayList<>();
  int amountFuel = 0;
  Block INDEX_PAGE = GregTech_API.sBlockCasings3;
  byte INDEX_CASE_PAGE = 12;
  ITexture INDEX_CASE = Textures.BlockIcons.CASING_BLOCKS[12 + 32];
  int INDEX_CASE1 = INDEX_CASE_PAGE + (3 * 128);
  Block INDEX_PAGE_PARALLEL = sCaseCore1;
  int mCoolingCasing;

  public GTMTE_REACTOR(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GTMTE_REACTOR(String aName) {
    super(aName);
  }

  @Override
  public GT_Recipe.GT_Recipe_Map getRecipeMap() {
    return null;
  }

  @Override
  public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
      byte aColorIndex, boolean aActive, boolean aRedstone) {
    if (aSide == aFacing) {
      return new ITexture[]{INDEX_CASE,
          new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)};
    }
    return new ITexture[]{INDEX_CASE};
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_REACTOR(this.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("One-block machine analog")
        .addParallelInfo(4, 256)
        .addInfo("Parallel Point will upped Upgrade Casing")
        .addPollution(200, 12800)
        .addSeparator()
        .beginStructureBlock(3, 3, 3)
        .addController()
        .addParallelCase("Middle сenter")
        .addEnergyHatch("Any casing")
        .addMaintenanceHatch("Any casing")
        .addInputBus("Any casing (only x1)")
        .addOutputBus("Any casing (only x1)")
        .addCasingInfo("Wiremill Casing")
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
        "MultiParallelBlockGUI.png");
  }

  @Override
  public boolean checkRecipe(ItemStack aStack) {
    long aTemp = 0;
    long aCountCells = 0;

    if (mRodHatches.size() > 0) {

      for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {
        aCountCells += rod_hatch.mCountCells;
        aTemp += (rod_hatch.mTemp * rod_hatch.mDownRod * 4);
      }

      if (!depleteInput(Materials.Water.getFluid((aTemp * aCountCells) + 1))) {
        super.mEfficiency = 0;
        return false;
      }
      //todo отбалансить роды
      mMaxProgresstime = 20;
      mEfficiencyIncrease = 100;

      if (super.mEfficiency == getMaxEfficiency(null)) {
        addOutput(Materials.Water.getGas((aTemp * aCountCells) * 160));
      }

      return true;
    }

    super.mEUt = 0;
    super.mEfficiency = 0;
    return false;
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aTick % 20 == 0) {
      for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {
        rod_hatch.setStartReactor(aBaseMetaTileEntity.isActive());
      }
    }
  }

  public boolean onRunningTick(ItemStack aStack) {
    return super.onRunningTick(aStack);
  }

  public boolean checkMachineFunction(IGregTechTileEntity thisController, ItemStack aStack) {
    this.mWrench = true;
    this.mScrewdriver = true;
    this.mSoftHammer = true;
    this.mHardHammer = true;
    this.mSolderingTool = true;
    this.mCrowbar = true;
    boolean checkStructure = true; //todo перенести структуру в код
    int x, y, z;
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    for (x = 0; x <= 5; x++) {
      if (x == 0) {
        continue;
      }
      final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, 0);
      IGregTechTileEntity currentTE = thisController
          .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
      if (!checkRodHatches(currentTE, 44)
          && !addToMachineList(currentTE, 44)) {
        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
            == GregTech_API.sBlockCasings3)
            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 12)) {
        } else {
          checkStructure = false;
        }
      }
    }

    return checkStructure;
  }

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

  public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
    mRodHatches.clear();
    boolean result = this.checkMachineFunction(aBaseMetaTileEntity, aStack);
    if (!result) {
      this.mCoolingCasing = 0;
    }
    return result;
  }

  @Override
  public String[] getInfoData() {
    return new String[]{
        "Output SC Steam: " + EnumChatFormatting.GREEN + this.amountFuel * this.mCoolingCasing
            + EnumChatFormatting.RESET + " L",
        "Input Water: " + EnumChatFormatting.RED
            + ((this.amountFuel * this.mCoolingCasing) + 160) / 5 + EnumChatFormatting.RESET + " L",
        "Decay Time: " + EnumChatFormatting.GREEN + this.mProgresstime / 20
            + EnumChatFormatting.RESET + " / " + EnumChatFormatting.YELLOW
            + this.mMaxProgresstime / 20 + EnumChatFormatting.RESET + " s",
        "Cooling Coefficient: " + EnumChatFormatting.YELLOW + this.mCoolingCasing
            + EnumChatFormatting.RESET + " L",
        "Efficiency: " + EnumChatFormatting.YELLOW + (mEfficiency / 100F) + " %",
    };
  }

}
