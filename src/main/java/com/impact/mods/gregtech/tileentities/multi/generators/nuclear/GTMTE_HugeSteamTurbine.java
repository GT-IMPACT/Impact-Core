package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlockHint;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static com.impact.loader.ItemRegistery.decorateBlock;

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
import net.minecraft.nbt.NBTTagCompound;
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
  long mOutputSalary = 0;

  public GTMTE_HugeSteamTurbine(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
    build();
  }

  public GTMTE_HugeSteamTurbine(String aName) {
    super(aName);
    build();
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
    build();
    return new GTMTE_HugeSteamTurbine(this.mName);
  }
  
  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("Mega steam turbine!")
        .addInfo("The turbine operates at 100% efficiency")
        .addInfo("Accepts both steam (2L = 1 EU/t)")
        .addInfo("and superheated steam (1L = 1 EU/t)")
        .addSeparator()
        .addController()
        .addDynamoHatch("Any casing back side (max x9")
        .addMaintenanceHatch("Any casing")
        .addInputHatch("Any casing (max x20)")
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
    return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Huge Steam Turbine",
        "MultiParallelBlockGUI.png");
  }

  public void build() {
    registerMetaClass(
        GTMTE_HugeSteamTurbine.class, new IMultiblockInfoContainer<GTMTE_HugeSteamTurbine>() {
          //region Structure
          private final IStructureDefinition<GTMTE_HugeSteamTurbine> definition =
              StructureDefinition.<GTMTE_HugeSteamTurbine>builder()
                  .addShape("main", new String[][]{
                      {"     "," BBB "," B~B "," BBB "},
                      {"     ","BBBBB","B A B","BBBBB"},
                      {" BBB ","BBBBB","B A B","BBBBB"},
                      {"BBBBB","BBBBB","B A B","BBBBB"},
                      {"BBBBB","BBABB","B A B","BBBBB"},
                      {"BBBBB","BBBBB","B A B","BBBBB"},
                      {" BBB ","BBBBB","BBBBB","BBBBB"},
                      {"     "," CCC "," CCC "," CCC "}
                  })
                  .addElement('A', ofBlock(GEARBOX, GEARBOX_META))
                  .addElement('B', ofBlock(CASING, CASING_META))
                  .addElement('C', ofBlockHint(decorateBlock[2], 1))
                  .build();
          private final String[] desc = new String[]{
              EnumChatFormatting.RED + "Impact Details:",
              " - Huge Turbine Casing",
              " - Steel GearBox Casing",
              " - " + EnumChatFormatting.RED + "RED" + EnumChatFormatting.RESET + " Dynamo Hatch or Huge Turbine Casing",
          };
          //endregion

          @Override
          public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_HugeSteamTurbine tileEntity,
              ExtendedFacing aSide) {
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
  public boolean machineStructure(IGregTechTileEntity thisController) {
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
            if (x == 0 && z > -4) {
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
          if (!addInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)) {
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

    if (mInputHatches.size() > 20) {
      formationChecklist = false;
    }

    if (mMaintenanceHatches.size() != 1) {
      formationChecklist = false;
    }

    if (mDynamoHatches.size() > 9 ||
        mDynamoHatchesTT.size() > 9 ||
        mDynamoTunnelsTT.size() > 9) {
      formationChecklist = false;
    }
    
    if (formationChecklist) {
      rotorTopTrigger(false);
    }
    return formationChecklist;
  }
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (aTick % 200 == 0) {
			rotorTopTrigger(aBaseMetaTileEntity.isActive());
		}
	}
	
	@Override
  public boolean checkRecipe(ItemStack itemStack) {
    ArrayList<FluidStack> tFluids = getStoredFluids();
    this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
    int totalFlow = 0;
    int countEU = 0;
    long outEU = 0;
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
      outEU = totalFlow / countEU;
    }

    outEU = mEfficiency < 10000 ? outEU / 2 : outEU;

    mOutputSalary = Math.min(outEU, getMaxOutputVoltage());

    this.mMaxProgresstime = 8;
    this.mEfficiencyIncrease = 10000;
    return true;
  }

  @Override
  public boolean onRunningTick(ItemStack aStack) {
    super.addEnergyOutputMultipleDynamos(mOutputSalary, true);
    return super.onRunningTick(aStack);
  }

  private void rotorTopTrigger(boolean shouldExist) {
    IGregTechTileEntity base = getBaseMetaTileEntity();
    if (base != null && base.getWorld() != null) {
      int xDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetX * 4 + base.getXCoord();
      int yDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetY * 4 + base.getYCoord();
      int zDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetZ * 4 + base.getZCoord();
      Block block = base.getWorld().getBlock(xDir, yDir, zDir);
      if (shouldExist) {
        if (block != null) {
          base.getWorld().setBlock(xDir, yDir, zDir, InsideBlock, 3, 2);
        }
      }
      if (!base.isAllowedToWork()) {
        base.getWorld().setBlock(xDir, yDir, zDir, GregTech_API.sBlockCasings2, 3, 2);
      }
      if (!shouldExist) {
        base.getWorld().setBlock(xDir, yDir, zDir, InsideBlock, 4, 2);
      }
    }
  }

  @Override
  public String[] getInfoData() {
    return new String[]{
        "Steam Input: " + EnumChatFormatting.RED + "" + mStoredFluids + EnumChatFormatting.RESET
            + " L/s",
        "Output Energy: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(mOutputSalary)
            + EnumChatFormatting.RESET + " EU/t",
        "Void Steam: " + EnumChatFormatting.RED + Math.abs(mStoredFluids - mOutputSalary)
            + EnumChatFormatting.RESET + "L/t",
        "Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F
            + EnumChatFormatting.YELLOW + " %",
        "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
            ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
            : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
    };
  }
}