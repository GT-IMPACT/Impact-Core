package com.impact.mods.GregTech.tileentities.multi.units;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.SpaceElevatorBlock;
import static com.impact.util.Utilits.isValidDim;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.core.Impact_API;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.enums.Texture;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.mods.GregTech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Utilits;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

public class GTMTE_SpaceElevator extends GT_MetaTileEntity_MultiParallelBlockBase {

  public static String mModed;
  Block CASING = Casing_Helper.sCaseCore2;
  byte CASING_META = 14;
  ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
  int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
  boolean isConnect = false;

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
        .addInfo("Setup is done using Laptop")
        .addInfo("Send a redstone signal to teleport")
        .addInfo("Passive usage: 1920 EU/t")
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

  @Override
  public boolean machineStructure(IGregTechTileEntity thisController) {
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

  public void teleportInventory(TileEntity tTile) {
    if (mInventory[0] != null) {
      if (tTile instanceof IInventory) {
        GT_Utility.moveOneItemStack(this, tTile, (byte) 0, (byte) 0,
            null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
      }
    }
  }

  public boolean checkCurrentDimension(int dim) {
    return dim == getBaseMetaTileEntity().getWorld().provider.dimensionId;
  }

  public void teleportEntity() {
    List entities_in_box = getBaseMetaTileEntity().getWorld().getEntitiesWithinAABB(
        Entity.class, Utilits.setBoxAABB(getBaseMetaTileEntity(), 1.5));
    for (Object tObject : entities_in_box) {
      if (((tObject instanceof Entity)) && (!((Entity) tObject).isDead)) {
        Entity tEntity = (Entity) tObject;
        if (tEntity.ridingEntity != null) {
          tEntity.mountEntity(null);
        }
        if (tEntity.riddenByEntity != null) {
          tEntity.riddenByEntity.mountEntity(null);
        }
        if (getBaseMetaTileEntity().getRedstone() && (!checkCurrentDimension(this.mTargetD) &&
            (!GT_Utility.moveEntityToDimensionAtCoords(tEntity,
                this.mTargetD, this.mTargetX + 0.5D,
                this.mTargetY + 1, this.mTargetZ + 0.5D)))) {
          isConnect = false;
        }
      }
    }
  }

  public void connect(TileEntity tTile) {
    if (tTile instanceof IGregTechTileEntity) {
      MetaTileEntity mte = (MetaTileEntity) ((IGregTechTileEntity) tTile).getMetaTileEntity();
      if (mte instanceof GTMTE_SpaceElevator) {
        ((GTMTE_SpaceElevator) mte).setCoord(new int[]{getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(),
            getBaseMetaTileEntity().getZCoord(), getBaseMetaTileEntity().getWorld().provider.dimensionId});
        this.isConnect = true;
      }
    }
  }

  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    super.onPostTick(aBaseMetaTileEntity, aTick);
    if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isActive()) {
      if (aTick % 20 * 3 == 0) {
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
        connect(tTile);
        if (this.isConnect) {
          teleportInventory(tTile);
          teleportEntity();
          this.isConnect = false;
        }
      }
    }
  }

  public int[] getCoords() {
    return Utilits.getCoordsBaseMTE(getBaseMetaTileEntity());
  }

  public void getFrequency(EntityPlayer aPlayer) {
    if (Impact_API.sElevatorSpace.get(Utilits.inToStringUUID(1, aPlayer)) != null) {
      GT_Utility.sendChatToPlayer(aPlayer, "Load Position");
      setCoord(Impact_API.sElevatorSpace.get(Utilits.inToStringUUID(1, aPlayer)));
    } else {
      GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Failed Load Position");
    }
  }

  public void setFrequency(EntityPlayer aPlayer) {
    Impact_API.sElevatorSpace.put(Utilits.inToStringUUID(1, aPlayer), getCoords());
    GT_Utility.sendChatToPlayer(aPlayer, "Save Position");
  }

  @Override
  public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
    super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
    ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
    if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
      int dimId = getBaseMetaTileEntity().getWorld().provider.dimensionId;
      if (isValidDim(dimId, "Orbit") || isValidDim(dimId, "Space")
          || isValidDim(dimId, "SS") || isValidDim(dimId, "SpaceStation")) {
        getFrequency(aPlayer);
      }
      if (dimId == 0) {
        setFrequency(aPlayer);
      }
      GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
    }
  }

  @Override
  public boolean checkRecipe(ItemStack itemStack) {
    this.mMaxProgresstime = 1;
    this.mEUt = -1920;
    this.mEfficiencyIncrease = 10000;
    this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
    return this.mEfficiency >= 10000;
  }

  @Override
  public int getPollutionPerTick(ItemStack aStack) {
    return 0;
  }


  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {
  }
}