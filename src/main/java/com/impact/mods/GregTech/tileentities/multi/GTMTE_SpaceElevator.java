package com.impact.mods.GregTech.tileentities.multi;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.SpaceElevatorBlock;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.enums.Texture;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

public class GTMTE_SpaceElevator extends GT_MetaTileEntity_MultiParallelBlockBase {

  public static String mModed;
  public int mTargetX = 0;
  public int mTargetY = 0;
  public int mTargetZ = 0;
  public int mTargetD = Integer.MIN_VALUE;//0
  Block CASING = Casing_Helper.sCaseCore2;
  byte CASING_META = 14;
  ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
  int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

  public GTMTE_SpaceElevator(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
    holo();
  }

  public GTMTE_SpaceElevator(String aName) {
    super(aName);
  }

  @Override
  public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
      final byte aFacing,
      final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
    return aSide == 1
        ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
        aActive
            ? Texture.Icons.OVERLAY_SPACE_ELEVATOR_ACTIVE
            : Texture.Icons.OVERLAY_SPACE_ELEVATOR)}
        : new ITexture[]{INDEX_CASE};
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_SpaceElevator(this.mName);
  }

  public boolean isFacingValid(byte aFacing) {
    return aFacing > 1;
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("Teleportation on Space Satellite")
        .addTypeMachine("Space Elevator")
        .addController()
        .addEnergyHatch("Any casing")
        .addCasingInfo("Space Elevator Casing")
        .addOtherStructurePart("Space Elevator Hawser", "Center below Controller")
        .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
    if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getInformation();
    } else {
      return b.getStructureInformation();
    }
  }

  public void holo() {
    registerMetaClass(GTMTE_SpaceElevator.class,
        new IMultiblockInfoContainer<GTMTE_SpaceElevator>() {
          //region Structure
          private final IStructureDefinition<GTMTE_SpaceElevator> definition =
              StructureDefinition.<GTMTE_SpaceElevator>builder()
                  .addShape("main", new String[][]{
                      {" AAAAA ", " A   A ", " A   A ", " A   A ", " AAAAA ", " AAAAA "},
                      {"AAAAAAA", "AA   AA", "AA   AA", "AA   AA", "AAAAAAA", "AAAAAAA"},
                      {"AA   AA", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA"},
                      {"AA   AA", "       ", "       ", "       ", "AAA~AAA", "AAABAAA"},
                      {"AA   AA", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA"},
                      {"AAAAAAA", "AA   AA", "AA   AA", "AA   AA", "AAAAAAA", "AAAAAAA"},
                      {" AAAAA ", " A   A ", " A   A ", " A   A ", " AAAAA ", " AAAAA "}
                  })
                  .addElement('A', ofBlock(CASING, CASING_META))
                  .addElement('B', ofBlock(SpaceElevatorBlock))
                  .build();
          private final String[] desc = new String[]{
              EnumChatFormatting.RED + "Impact Details:",
              "- Space Elevator Casing",
              "- Space Elevator Hawser",
              "- Hatches (any Space Elevator Casing)",
          };
          //endregion

          @Override
          public void construct(ItemStack stackSize, boolean hintsOnly,
              GTMTE_SpaceElevator tileEntity, ExtendedFacing aSide) {
            IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
            definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                base.getXCoord(), base.getYCoord(), base.getZCoord(),
                3, 4, 3, hintsOnly);
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
        "MultiParallelBlockGUI.png");

  }

  public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    boolean formationChecklist = true;

    for (int X = -3; X <= 3; X++) {
      for (byte Y = -1; Y <= 4; Y++) {
        for (byte Z = 3; Z >= -3; Z--) {

          if (X == 0 && Y == 0 && Z == 0) {
            continue;
          }

          if ((X == -3 || X == 3) && (Z == -3 || Z == 3)) {
            continue;
          }

          if ((X == -1 || X == 0 || X == 1) && (Y == 1 || Y == 2 || Y == 3)) {
            continue;
          }
          if ((Z == -1 || Z == 0 || Z == 1) && (Y == 1 || Y == 2 || Y == 3)) {
            continue;
          }

          if ((X == -1 || X == 0 || X == 1) && (Z == -1 || Z == 0 || Z == 1) && Y == 4) {
            continue;
          }

          final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

          if (X == 0 && Y == -1 && Z == 0) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
                == SpaceElevatorBlock)) {
            } else {
              formationChecklist = false;
            }
            continue;
          }

          IGregTechTileEntity currentTE = thisController
              .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
          if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
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
    }

    mWrench = true;
    mScrewdriver = true;
    mSoftHammer = true;
    mHardHammer = true;
    mSolderingTool = true;
    mCrowbar = true; //todo Пересмотреть мейнтенанс
    return formationChecklist;
  }

  public void saveNBTData(NBTTagCompound aNBT) {
    aNBT.setInteger("mTargetX", this.mTargetX);
    aNBT.setInteger("mTargetY", this.mTargetY);
    aNBT.setInteger("mTargetZ", this.mTargetZ);
    aNBT.setInteger("mTargetD", this.mTargetD);
  }

  public void loadNBTData(NBTTagCompound aNBT) {
    this.mTargetX = aNBT.getInteger("mTargetX");
    this.mTargetY = aNBT.getInteger("mTargetY");
    this.mTargetZ = aNBT.getInteger("mTargetZ");
    this.mTargetD = aNBT.getInteger("mTargetD");
  }

  @Override
  public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
    if (getBaseMetaTileEntity().isServerSide()) {
      if ((this.mTargetX == 0) && (this.mTargetY == 0) && (this.mTargetZ == 0) && (this.mTargetD
          == Integer.MIN_VALUE)) {
        this.mTargetX = aBaseMetaTileEntity.getXCoord();
        this.mTargetY = aBaseMetaTileEntity.getYCoord();
        this.mTargetZ = aBaseMetaTileEntity.getZCoord();
        this.mTargetD = aBaseMetaTileEntity.getWorld().provider.dimensionId;
      }
    }
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (getBaseMetaTileEntity().isServerSide()) {
      if (aBaseMetaTileEntity.getRedstone()) {
        mMaxProgresstime = 20 * 3;
        mProgresstime = 0;
      }

      if (mProgresstime > 20 * 2) {
        if (mInventory[0] != null) {
          TileEntity tTile = null;
          if (this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId) {
            tTile = getBaseMetaTileEntity()
                .getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
          } else {
            World tWorld = DimensionManager.getWorld(this.mTargetD);
            if (tWorld != null) {
              tTile = tWorld.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
            }
          }
          if (tTile instanceof IInventory) {
            GT_Utility
                .moveOneItemStack(this, tTile, (byte) 0, (byte) 0, null, false, (byte) 64, (byte) 1,
                    (byte) 64, (byte) 1);
          }
        }

        if (getBaseMetaTileEntity().getWorld().provider.dimensionId != 0) {
          TileEntity tTile = null;
          World tWorld = DimensionManager.getWorld(this.mTargetD);
          if (tWorld != null) {
            tTile = tWorld.getTileEntity(this.mTargetX, this.mTargetY - 1, this.mTargetZ);
            if (tTile instanceof IGregTechTileEntity) {
              MetaTileEntity mte = (MetaTileEntity) ((IGregTechTileEntity) tTile)
                  .getMetaTileEntity();
              if (mte instanceof GTMTE_SpaceElevator) {
                ((GTMTE_SpaceElevator) mte).mTargetD = getBaseMetaTileEntity()
                    .getWorld().provider.dimensionId;
                ((GTMTE_SpaceElevator) mte).mTargetX = getBaseMetaTileEntity().getXCoord();
                ((GTMTE_SpaceElevator) mte).mTargetY = getBaseMetaTileEntity().getYCoord() + 1;
                ((GTMTE_SpaceElevator) mte).mTargetZ = getBaseMetaTileEntity().getZCoord();
              }
            }
          }
        }

        if (!((this.mTargetX == getBaseMetaTileEntity().getXCoord()) && (this.mTargetY
            == getBaseMetaTileEntity().getYCoord()) &&
            (this.mTargetZ == getBaseMetaTileEntity().getZCoord()) && (this.mTargetD
            == getBaseMetaTileEntity().getWorld().provider.dimensionId))) {
          List entities_in_box = getBaseMetaTileEntity().getWorld().getEntitiesWithinAABB(
              Entity.class, AxisAlignedBB.getBoundingBox(
                  getBaseMetaTileEntity().getOffsetX(getBaseMetaTileEntity().getFrontFacing(), 2)
                      - 1.5D,
                  getBaseMetaTileEntity().getOffsetY(getBaseMetaTileEntity().getFrontFacing(), 2)
                      - 2,
                  getBaseMetaTileEntity().getOffsetZ(getBaseMetaTileEntity().getFrontFacing(), 2)
                      - 1.5D,
                  getBaseMetaTileEntity().getOffsetX(getBaseMetaTileEntity().getFrontFacing(), 2)
                      + 1.5D,
                  getBaseMetaTileEntity().getOffsetY(getBaseMetaTileEntity().getFrontFacing(), 2)
                      + 2,
                  getBaseMetaTileEntity().getOffsetZ(getBaseMetaTileEntity().getFrontFacing(), 2)
                      + 1.5D)
          );

          for (Object tObject : entities_in_box) {
            if (((tObject instanceof Entity)) && (!((Entity) tObject).isDead)) {
              Entity tEntity = (Entity) tObject;
              if (getBaseMetaTileEntity().isActive()) {

                if (tEntity.ridingEntity != null) {
                  tEntity.mountEntity(null);
                }
                if (tEntity.riddenByEntity != null) {
                  tEntity.riddenByEntity.mountEntity(null);
                }
                if ((this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId)
                    || (!GT_Utility
                    .moveEntityToDimensionAtCoords(tEntity, this.mTargetD, this.mTargetX + 0.5D,
                        this.mTargetY + 0.5D, this.mTargetZ + 0.5D))) {
                  if ((tEntity instanceof EntityLivingBase)) {
                    ((EntityLivingBase) tEntity)
                        .setPositionAndUpdate(this.mTargetX + 0.5D, this.mTargetY + 0.5D,
                            this.mTargetZ + 0.5D);
                  } else {
                    tEntity.setPosition(this.mTargetX + 0.5D, this.mTargetY + 0.5D,
                        this.mTargetZ + 0.5D);
                  }
                }
              }
            }

          }
        }
      }
    }
  }

  @Override
  public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
    super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
    ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
    NBTTagCompound tNBT = tCurrentItem.getTagCompound();
    if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
      if (getBaseMetaTileEntity().getWorld().provider.dimensionId == 0) {
        tNBT.setInteger("mSpaceElevatorX", this.mTargetX);
        tNBT.setInteger("mSpaceElevatorY", this.mTargetY + 1);
        tNBT.setInteger("mSpaceElevatorZ", this.mTargetZ);
        tNBT.setInteger("mSpaceElevatorD", this.mTargetD);
        GT_Utility.sendChatToPlayer(aPlayer, "Save Position");
      } else {
        if (tNBT != null) {
          this.mTargetX = tNBT.getInteger("mSpaceElevatorX");
          this.mTargetY = tNBT.getInteger("mSpaceElevatorY");
          this.mTargetZ = tNBT.getInteger("mSpaceElevatorZ");
          this.mTargetD = tNBT.getInteger("mSpaceElevatorD");
          GT_Utility.sendChatToPlayer(aPlayer, "Load Position: X"
              + this.mTargetX + " Y " + this.mTargetY + " Z " + this.mTargetZ + " Dim "
              + this.mTargetD);
        } else {
          GT_Utility.sendChatToPlayer(aPlayer, "Not found Space Elevator");
        }
      }
      GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
    }
  }

  @Override
  public boolean checkRecipe(ItemStack itemStack) {
    this.mMaxProgresstime = 1;
    this.mEUt = 0; //todo добавить и отбалансить потрбление
    this.mEfficiencyIncrease = 10000;
    return true;
  }

  @Override
  public int getPollutionPerTick(ItemStack aStack) {
    return 0;
  }


  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {

  }
}