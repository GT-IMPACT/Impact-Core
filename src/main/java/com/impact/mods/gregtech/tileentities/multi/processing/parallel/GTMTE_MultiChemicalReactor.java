package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static gregtech.api.enums.GT_Values.V;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.gregtech.gui.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
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

public class GTMTE_MultiChemicalReactor extends GT_MetaTileEntity_MultiParallelBlockBase {

  Block CASING = GregTech_API.sBlockCasings8;
  int CASING_META = 0;
  int CASING_TEXTURE_ID = 176;

  public GTMTE_MultiChemicalReactor(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
    build();
  }

  public GTMTE_MultiChemicalReactor(String aName) {
    super(aName);
    build();
  }

  @Override
  public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive,
      boolean aRedstone) {
    if (aSide == 1) {
      return new ITexture[] {
          Textures.BlockIcons.casingTexturePages[1][48],
          new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR_ACTIVE
              : Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR) };
    }
    return new ITexture[] { Textures.BlockIcons.casingTexturePages[1][48] };
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    build();
    return new GTMTE_MultiChemicalReactor(this.mName);
  }

  private void build() {
    registerMetaClass(GTMTE_MultiChemicalReactor.class,
        new IMultiblockInfoContainer<GTMTE_MultiChemicalReactor>() {
          //region Structure
          private final IStructureDefinition<GTMTE_MultiChemicalReactor> definition =
              StructureDefinition.<GTMTE_MultiChemicalReactor>builder()
                  .addShape("main", new String[][]{
                      {" AAA ", " ACA ", " ACA ", " ACA ", " AAA "},
                      {"AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA"},
                      {"AA~AA", "CB BC", "CB BC", "CB BC", "AAAAA"},
                      {"AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA"},
                      {" AAA ", " ACA ", " ACA ", " ACA ", " AAA "},
                  })
                  .addElement('A', ofBlock(CASING, CASING_META))
                  .addElement('B', ofBlock(GregTech_API.sBlockCasings8, 1))
                  .addElement('C', ofBlock(IGlassBlock))
                  .build();
          private final String[] desc = new String[]{
              EnumChatFormatting.RED + "Impact Details:",
              "It's minimal length structure",
              " - Chemically Inert Machine Casings",
              " - PTFE Pipe Machine Casing",
              " - I-Glass"
          };

          //endregion
          @Override
          public void construct(ItemStack stackSize, boolean hintsOnly,
              GTMTE_MultiChemicalReactor tileEntity, ExtendedFacing aSide) {
            IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
            definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                base.getXCoord(), base.getYCoord(), base.getZCoord(),
                2, 0, 2, hintsOnly);
          }

          @Override
          public String[] getDescription(ItemStack stackSize) {
            return desc;
          }
        });
  }

  public boolean isFacingValid(byte aFacing) {
    return aFacing > 1;
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("Very big chemical, all right!")
        .addParallelInfo(1, 256)
        .addTypeMachine("Multi Chemical Reactor")
        .addSeparator()
        .addController()
        .addEnergyHatch("Any casing")
        .addMaintenanceHatch("Any casing")
        .addInputHatch("Any casing (max x16)")
        .addOutputHatch("Any casing (max x8)")
        .addInputBus("Any casing (max x8)")
        .addOutputBus("Any casing (max x3)")
        .addParallelHatch("Any casing (max x1)")
        .addOtherStructurePart("PTFE Pipe Machine Casing", "inside the hollow")
        .addCasingInfo("Chemically Inert Machine Casings")
        .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
    if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getInformation();
    } else {
      return b.getStructureInformation();
    }
  }

  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Electrom. Induction Furn.",
        "MultiParallelBlockGUI.png", "");
  }

  @Override
  public GT_Recipe_Map getRecipeMap() {
    return GT_Recipe_Map.sMultiblockChemicalRecipes;
  }

  public boolean checkRecipe(ItemStack itemStack) {
    if (sParallHatchesIn.size() > 0 && getRecipeCheckParallel()) {
      return false;
    }
    ArrayList<ItemStack> tInputList = this.getStoredInputs();
    ArrayList<FluidStack> tFluidList = this.getStoredFluids();
    ItemStack[] tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);
    FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

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
    return false;
  }

  @Override
  public boolean machineStructure(IGregTechTileEntity thisController) {
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    boolean formationChecklist = true; // Если все ок, машина собралась

    int x, y, z;

    for (x = -2; x <= 2; x++) {
      for (y = 0; y >= -4; y--) {
        for (z = -2; z <= 2; z++) {
          if (x == 0 && y == 0 && z == 0) {
            continue;
          }

          final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);

          if ((x == -2 || x == 2) && (z == 2 || z == -2)) {
            continue;
          }

          if ((y >= -3 && y <= -1) && (x == 0) && (z == -2 || z == 2)) {

            //debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), IGlassBlock);

            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock)) {
            } else {
              formationChecklist = false;
            }
            continue;
          }

          if ((y >= -3 && y <= -1) && (x == -2 || x == 2) && (z == 0)) {

            //debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), IGlassBlock);

            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock)) {
            } else {
              formationChecklist = false;
            }
            continue;
          }

          if ((y >= -3 && y <= -1) && x == 0 && z == 0) {
            continue;
          }

          if ((y >= -3 && y <= -1) && (x >= -1 && x <= 1) && (z >= -1 && z <= 1)) {

            if (x == 0 && z == 0) {
              continue;
            }

            //debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), GregTech_API.sBlockCasings8, 1);

            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings8)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
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

            //debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), CASING, CASING_META);

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

    if (this.mInputBusses.size() > 8) {
      formationChecklist = false;
    }
    if (this.mInputHatches.size() > 16) {
      formationChecklist = false;
    }
    if (this.mOutputBusses.size() > 3) {
      formationChecklist = false;
    }
    if (this.mOutputHatches.size() > 8) {
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
}