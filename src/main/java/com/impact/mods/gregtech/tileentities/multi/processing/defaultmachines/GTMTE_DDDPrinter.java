package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore2;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

public class GTMTE_DDDPrinter extends GT_MetaTileEntity_MultiParallelBlockBase {

  Block CASING = Casing_Helper.sCaseCore2;
  byte CASING_META = 4;
  ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
  int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

  public GTMTE_DDDPrinter(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
    holo();
  }


  public GTMTE_DDDPrinter(String aName) {
    super(aName);
  }

  @Override
  public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
      final byte aFacing,
      final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
    return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ?
        Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_DDDPrinter(this.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("ddd_printer.info.0")
        .addTypeMachine("ddd_printer.name")
        .addScrew()
        .addSeparatedBus()
        .addSeparator()
        .addController()
        .addEnergyHatch("ddd_printer.hatches")
        .addMaintenanceHatch("ddd_printer.hatches")
        .addInputBus("ddd_printer.hatch.bus.in")
        .addOutputBus("ddd_printer.hatches")
        .addCasingInfo("ddd_printer.case")
        .addOtherStructurePart("ddd_printer.other.0", "ddd_printer.other.1")
        .addOtherStructurePart("ddd_printer.other.2", "ddd_printer.other.3")
        .signAndFinalize();
    if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getInformation();
    } else {
      return b.getStructureInformation();
    }
  }

  public void holo() {
    //3D Printer
    registerMetaClass(GTMTE_DDDPrinter.class, new IMultiblockInfoContainer<GTMTE_DDDPrinter>() {
      //region Structure
      private final IStructureDefinition<GTMTE_DDDPrinter> definition =
          StructureDefinition.<GTMTE_DDDPrinter>builder()
              .addShape("main", new String[][]{

                  {"03330", "~3330", "00000",},
                  {"03330", "01110", "00000",},
                  {"03330", "01110", "00000",},
                  {"03330", "01110", "00000",},
                  {"03330", "03330", "00000",},
              })
              .addElement('0', ofBlock(sCaseCore2, 4))
              .addElement('1', ofBlock(sCaseCore2, 5))
              .addElement('3', ofBlock(IGlassBlock, 0))
              .build();
      private final String[] desc = new String[]{
          EnumChatFormatting.RED + "Impact Details:",
          "- 3D Printed Casing",
          "- I-Glass (any I-Glass)",
          "- Configuration Casing (3x3)",
          "- Hatches (any 3D Printed Casing)",
      };
      //endregion

      @Override
      public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_DDDPrinter tileEntity,
          ExtendedFacing aSide) {
        IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
        definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
            base.getXCoord(), base.getYCoord(), base.getZCoord(),
            0, 1, 0, hintsOnly);
      }

      @Override
      public String[] getDescription(ItemStack stackSize) {
        return desc;
      }
    });
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
        "MultiParallelBlockGUI.png", "3x3 Crafting");
  }

  @Override
  public boolean checkRecipe(ItemStack itemStack) {
    return impactRecipe(itemStack);
  }

  @Override
  public GT_Recipe.GT_Recipe_Map getRecipeMap() {
    return GT_Recipe.GT_Recipe_Map.sPrimitiveLine;
  }

  @Override
  public boolean machineStructure(IGregTechTileEntity thisController) {
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    int minCasingAmount = 12; // Минимальное количество кейсов
    boolean formationChecklist = true; // Если все ок, машина собралась

    for (byte X = 0; X <= 4; X++) {
      for (byte Y = -1; Y <= 1; Y++) {
        for (int Z = 0; Z >= -4; Z--) {

          if (X == 0 && Z == 0 && Y == 0) {
            continue;
          }

          final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

          if ((X == 1 || X == 2 || X == 3) && (Z == -1 || Z == -2 || Z == -3) && Y == 0) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                == Casing_Helper.sCaseCore2)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 5)) {
            } else {
              formationChecklist = false;
            }
            continue;
          }

          if ((X == 1 || X == 2 || X == 3) && (Z == 0 || Z == -4) && Y == 0) {
            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
            } else {
              formationChecklist = false;
            }
            continue;
          }

          if ((X == 1 || X == 2 || X == 3) && Y == 1) {
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

    if (this.mInputBusses.size() > 30) {
      formationChecklist = false;
    }
    if (this.mOutputBusses.size() > 3) {
      formationChecklist = false;
    }
    if (this.mEnergyHatches.size() > 4) {
      formationChecklist = false;
    }
    if (this.mMaintenanceHatches.size() != 1) {
      formationChecklist = false;
    }

    return formationChecklist;
  }

  @Override
  public int getPollutionPerTick(ItemStack aStack) {
    return 0;
  }


  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {
    super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
    if (aPlayer.isSneaking()) {
      ScrewClick(aSide, aPlayer, aX, aY, aZ);
    }
  }
}