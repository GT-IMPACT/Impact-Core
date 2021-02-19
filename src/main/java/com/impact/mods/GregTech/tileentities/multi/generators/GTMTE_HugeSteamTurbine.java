package com.impact.mods.GregTech.tileentities.multi.generators;

import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import java.text.NumberFormat;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

public class GTMTE_HugeSteamTurbine extends GT_MetaTileEntity_MultiParallelBlockBase {

  final Block CASING = Casing_Helper.sCasePage8_3;
  final Block GEARBOX = GregTech_API.sBlockCasings2;
  final byte CASING_META = 4;
  final byte GEARBOX_META = 3;
  final ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[0][16];
  final int CASING_TEXTURE_ID = 16;
  int mStoredFluids = 0;

  public GTMTE_HugeSteamTurbine(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GTMTE_HugeSteamTurbine(String aName) {
    super(aName);
  }

  @Override
  public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
      final byte aFacing,
      final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
    return aSide == aFacing
        ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
        aActive
            ? Textures.BlockIcons.LARGETURBINE_ST_ACTIVE5
            : Textures.BlockIcons.LARGETURBINE_ST5)}
        : new ITexture[]{INDEX_CASE};
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_HugeSteamTurbine(this.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("Mega steam turbine!")
        .addInfo("The turbine operates at 100% efficiency")
        .addInfo("Accepts both steam (2L = 1EU / t)")
        .addInfo("and superheated steam (1L = 1EU / t)")
        .addSeparator()
        .addController()
        .addDynamoHatch("Any casing back side (max x9")
        .addMaintenanceHatch("Any casing")
        .addInputHatch("Any casing (max x15)")
        .addCasingInfo("Huge Turbine Casing and I-Glass")
        .addOtherStructurePart("Steel GearBox Casing", "inside structure")
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
    return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Liquid Nq Generator",
        "MultiParallelBlockGUI.png");
  }

  public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);
    int aCasingAmount = 0;
    boolean formationChecklist = true;
    int x, y, z;
    for (x = -1; x <= 1; x++) {
      for (y = -1; y <= 1; y++) {
        if (x == 0 && y == 0) {
          continue;
        }
        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, 0);
        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
            == CASING_META)) {
          aCasingAmount++;
        } else {
          formationChecklist = false;
        }
      }
    }
    for (x = -2; x <= 2; x++) {
      for (y = -1; y <= 1; y++) {
        for (z = -1; z >= -6; z--) {
          final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
          if (x >= -1 && x <= 1 && z <= -1 && z >= -5 && y == 0) {
            if (x == 0 && z != -5) {
              if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                  == GEARBOX)
                  && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GEARBOX_META)) {
                aCasingAmount++;
              } else {
                formationChecklist = false;
              }
              continue;
            }
            continue;
          }
          if (x == 0 && z == -4 && y == 1) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                == GEARBOX)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GEARBOX_META)) {
              aCasingAmount++;
            } else {
              formationChecklist = false;
            }
            continue;
          }
          IGregTechTileEntity currentTE = thisController
              .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
          if (!addInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                == CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                == CASING_META)) {
              aCasingAmount++;
            } else {
              formationChecklist = false;
            }
          }
        }
      }
    }
    for (x = -2; x <= 2; x++) {
      for (z = -2; z >= -6; z--) {
        if ((x == -2 || x == 2) && (z == -2 || z == -6)) {
          continue;
        }
        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 2, z);
        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
            == CASING)
            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
            == CASING_META)) {
          aCasingAmount++;
        } else {
          formationChecklist = false;
        }
      }
    }
    for (x = -1; x <= 1; x++) {
      for (y = -1; y <= 1; y++) {
        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, -7);
        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!addDynamoToMachineList(currentTE, CASING_TEXTURE_ID)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
              == CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
              == CASING_META)) {
            aCasingAmount++;
          } else {
            formationChecklist = false;
          }
        }
      }
    }
    System.out.println(aCasingAmount); //TODO del
    return formationChecklist;
  }

  @Override
  public boolean checkRecipe(ItemStack itemStack) {
    ArrayList<FluidStack> tFluids = getStoredFluids();
    int tEU = 0;
    int totalFlow = 0;
    int countEU = 0;
    this.mStoredFluids = 0;
    int countFluid = 0;
    for (FluidStack fluidStack : tFluids) {
      String fluidName = fluidStack.getUnlocalizedName();
      if (fluidName.equals("fluid.steam") || fluidName.equals("ic2.fluidSteam")) {
        countFluid = fluidStack.amount;
        depleteInput(new FluidStack(fluidStack, countFluid));
        this.mStoredFluids += countFluid;
        totalFlow += countFluid;
        countEU = 2;
      } else if (fluidName.equals("ic2.fluidSuperheatedSteam")) {
        countFluid = fluidStack.amount;
        depleteInput(new FluidStack(fluidStack, countFluid));
        this.mStoredFluids += countFluid;
        totalFlow += countFluid;
        countEU = 1;
      }
      tEU = totalFlow / countEU;
    }
    this.mEUt = tEU;
    this.mMaxProgresstime = 1;
    this.mEfficiencyIncrease = 10000;
    return true;
  }

  @Override
  public String[] getInfoData() {
    return new String[]{
        "Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt)
            + EnumChatFormatting.RESET + " EU/t",
        "Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F
            + EnumChatFormatting.YELLOW + " %",
        "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
            ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
            : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
        "Steam: " + EnumChatFormatting.RED + "" + mStoredFluids + EnumChatFormatting.RESET
            + " L/s"
    };
  }
}