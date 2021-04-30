package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlockHint;
import static com.impact.loader.ItemRegistery.decorateBlock;

public class GTMTE_NuclearReactorII extends GTMTE_NuclearReactorBase {

  final Block GENERAL_CASING = GregTech_API.sBlockCasings3;
  final int GENERAL_CASING_META = 12;
  final Block SECOND_CASING = GregTech_API.sBlockCasings2;
  final int SECOND_CASING_META = 13;
  final int TEXTURE_HATCH = 44;

  public GTMTE_NuclearReactorII(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
    build();
  }

  public GTMTE_NuclearReactorII(String aName) {
    super(aName);
    build();
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    build();
    return new GTMTE_NuclearReactorII(super.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("Radiation!")
        .addTypeMachine("Nuclear Reactor")
        .addInfo("Default Mode (default rods): consumes water produces steam")
        .addInfo("Default Mode (MOX rods): consumes water produces superheated steam, all rods needed MOX")
        .addInfo("Fact Decay Mode (default or MOX rods): consumes coolant produces hot coolant, rods decays speed x5")
        .addInfo("")
        .addScrew("reactor mode")
        .addSeparator()
        .beginStructureBlock(0, 0, 0)
        .addController()
        .addNuclearRod("Any top middle casing (max x9)")
        .addInputHatch("Any casing (max x3)")
        .addOutputHatch("For steam / sh steam / hot coolant | Any casing (max x12)")
        .addCasingInfo("Radiation Proof Casing")
        .addOtherStructurePart("Steel Pipe Casing", "pipes!")
        .addOtherStructurePart("Machine Hull (max x1)", "what? yes, its for AE2 provider")
        .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
    if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getInformation();
    } else {
      return b.getStructureInformation();
    }
  }

  public void build() {
    registerMetaClass(
        GTMTE_NuclearReactorII.class, new IMultiblockInfoContainer<GTMTE_NuclearReactorII>() {
      //region Structure
      private final IStructureDefinition<GTMTE_NuclearReactorII> definition =
          StructureDefinition.<GTMTE_NuclearReactorII>builder()
              .addShape("main", new String[][]{
                  {"       ","   A   ","  AAA  ","  AAA  "," AAAAA "," AA~AA "},
                  {"  AAA  "," BAAAB "," B   B "," B   B ","AB   BA","AAAAAAA"},
                  {" AEEEA "," A   A ","A     A","A     A","A     A","AAAAAAA"},
                  {" AEEEA ","AA   AA","A     A","A     A","A     A","AAAAAAA"},
                  {" AEEEA "," A   A ","A     A","A     A","A     A","AAAAAAA"},
                  {"  AAA  "," BAAAB "," B   B "," B   B ","AB   BA","AAAAAAA"},
                  {"       ","   A   ","  AAA  ","  AAA  "," AAAAA "," AAAAA "}
              })
              .addElement('A', ofBlock(GENERAL_CASING, GENERAL_CASING_META))
              .addElement('B', ofBlock(SECOND_CASING, SECOND_CASING_META))
              .addElement('E', ofBlockHint(decorateBlock[2], 1))
              .build();
          private final String[] desc = new String[]{
              EnumChatFormatting.RED + "Impact Details:",
              " - Radiation Proof Casing",
              " - Steel Pipe Casing",
              " - " + EnumChatFormatting.RED + "RED" + EnumChatFormatting.RESET + " Nuclear Rod Hatch or Radiation Proof Casing",
          };
      //endregion

      @Override
      public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_NuclearReactorII tileEntity,
          ExtendedFacing aSide) {
        IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
        definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
            base.getXCoord(), base.getYCoord(), base.getZCoord(),
            3, 5, 0, hintsOnly);
      }

      @Override
      public String[] getDescription(ItemStack stackSize) {
        return desc;
      }
    });
  }

  public boolean checkMachineFunction(IGregTechTileEntity thisController) {
    setMaxTemp(100000);
    this.mWrench = true;
    this.mScrewdriver = true;
    this.mSoftHammer = true;
    this.mHardHammer = true;
    this.mSolderingTool = true;
    this.mCrowbar = true;
    boolean checkStructure = true;
    int x, y, z;
    int ID = 0;
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);
  
    for (x = -3; x <= 3; x++) {
      for (z = 0; z >= -6; z--) {
        if (x == 0 && z == 0) {
          continue;
        }
        if ((x == -3 || x == 3) && (z == 0 || z == -6)) {
          continue;
        }
      
        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, z);
        IGregTechTileEntity currentTE = thisController
                .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!addToMachineList(currentTE, TEXTURE_HATCH) && !addMachineHull(currentTE)) {
          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
                  && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
          } else {
            checkStructure = false;
          }
        }
      }
    }

    for (x = -3; x <= 3; x++) {
      for (z = 0; z >= -6; z--) {
        if ((x == -3 || x == 3) && (z == 0 || z == -6)) {
          continue;
        }
        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, z);
        if (x > -3 && x < 3 && z < 0 && z > -6) {
          if (((x == -2 || x == 2) && (z == -1 || z == -5))) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                == SECOND_CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
            } else {
              checkStructure = false;
            }
            continue;
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

    for (x = -3; x <= 3; x++) {
      for (z = 0; z >= -6; z--) {
        for (y = 2; y <= 3; y++) {
          if ((x == -3 || x == 3) && (z == 0 || z == -6)) {
            continue;
          }
          if ((x == -2 || x == 2) && (z == 0 || z == -6)) {
            continue;
          }
          if ((x == -3 || x == 3) && (z == -1 || z == -5)) {
            continue;
          }
          final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
          if (x > -3 && x < 3 && z < 0 && z > -6) {
            if (((x == -2 || x == 2) && (z == -1 || z == -5))) {
              if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                  == SECOND_CASING)
                  && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
              } else {
                checkStructure = false;
              }
              continue;
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
    }

    for (x = -3; x <= 3; x++) {
      for (z = 0; z >= -6; z--) {
          if ((x == -3 || x == 3) && (z == 0 || z == -6)) {
            continue;
          }
          if ((x == -2 || x == -1 || x == 1 || x == 2) && (z == 0 || z == -6)) {
            continue;
          }
          if ((x == -3 || x == 3) && (z == -1 || z == -2 || z == -4 || z == -5)) {
            continue;
          }
          final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 4, z);
          if (x > -3 && x < 3 && z < 0 && z > -6) {
            if (((x == -2 || x == 2) && (z == -1 || z == -5))) {
              if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                  == SECOND_CASING)
                  && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
              } else {
                checkStructure = false;
              }
              continue;
            }
            if (x > -2 && x < 2 && z < -1 && z > -5) {
              continue;
            }
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                == GENERAL_CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
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
      for (z = -1; z >= -5; z--) {
        if ((x == -2 || x == 2) && (z == -1 || z == -5)) {
          continue;
        }
        final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 5, z);
        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (x > -2 && x < 2 && z < -1 && z > -5) {
          if (checkRodHatches(currentTE, TEXTURE_HATCH, ID)) {
            ID++;
          } else {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                == GENERAL_CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
            } else {
              checkStructure = false;
            }
          }
          continue;
        }
        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
            == GENERAL_CASING)
            && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
        } else {
          checkStructure = false;
        }
      }
    }

    if (mOutputHatches.size() > 12) {
      checkStructure = false;
    }

    if (mInputHatches.size() > 3) {
      checkStructure = false;
    }
  
    if (mMachineHull.size() > 1) {
      checkStructure = false;
    }
  
    return checkStructure;
  }
}
