package com.impact.mods.gregtech.blocks.casings;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static com.impact.mods.gregtech.enums.Texture.Icons.COMPUTER_CASING;
import static com.impact.mods.gregtech.enums.Texture.Icons.SPACE_SATELLITE_CASING;
import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_CASING;
import static com.impact.util.Utilits.BlockstackMeta;

import com.impact.mods.gregtech.GT_ItemList;
import com.impact.mods.gregtech.blocks.itemblocks.IB_Casing_9;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_HugeSteamTurbine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class Casing_9 extends GT_Block_Casings_Abstract {


  public static boolean mConnectedMachineTextures = true;

  public Casing_9() {
    super(IB_Casing_9.class, "gt.blockcasingsSC", GT_Material_Casings.INSTANCE);
    GT_Utility.addTexturePage((byte) 8);
    for (byte b = 0; b < 16; b = (byte) (b + 1)) {
      Textures.BlockIcons.casingTexturePages[8][b + 64] = new GT_CopiedBlockTexture(this, 6, b);
    }
    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Farm Casing");
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".4.name", "Huge Turbine Casing");
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".5.name", "Space Satellite Casing");

    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Tower Casing");
    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Computer Casing");

    GT_ItemList.Casing_Farm.set(BlockstackMeta(this, 3));
    GT_ItemList.Huge_Casing_Turbine.set(BlockstackMeta(this, 4));
    GT_ItemList.Space_Satellite_Casing.set(BlockstackMeta(this, 5));
    GT_ItemList.Tower_Casing.set(BlockstackMeta(this, 6));
    GT_ItemList.Computer_Casing.set(BlockstackMeta(this, 7));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int aSide, int aMeta) {
    switch (aMeta) {
      case 3:
        return Textures.BlockIcons.MACHINE_CASING_PIPE_STEEL.getIcon();
      case 4:
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
      case 5:
        return SPACE_SATELLITE_CASING.getIcon();
      case 6:
        return TOWER_CASING.getIcon();
      case 7:
        return COMPUTER_CASING.getIcon();
      default:
        return null;
    }
  }

  private IIcon getTurbineCasing(int meta, int iconIndex, boolean active) {
    return active ? Textures.BlockIcons.TURBINE_ACTIVE[iconIndex].getIcon()
        : Textures.BlockIcons.TURBINE[iconIndex].getIcon();
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide) {
    int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
    if (tMeta != 4) {
      getIcon(aSide, tMeta);
    } else {
      if (aSide == 1) {
        for (int xi = -2; xi <= 2; xi++) {
          for (int zi = -2; zi <= 2; zi++) {
            int metaBlock =  aWorld.getBlockMetadata(xCoord + xi, yCoord - 2, zCoord + zi+ 1);
            if (aWorld.getBlock(xCoord + xi, yCoord - 2, zCoord + zi+ 1).equals(InsideBlock)) {
              if (xi < 2 && xi > -2 && zi < 1) {
                try {
                  return getTurbineCasing(tMeta, -xi + 1 - zi * 3, metaBlock == 3);
                } catch (Exception e) {
                  return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
                }
              }
            }
          }
        }
      }
      if ((aSide == 2) || (aSide == 3)) {
        TileEntity tTileEntity;
        IMetaTileEntity tMetaTileEntity;
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord - 1, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 0, true);
          }
          return getTurbineCasing(tMeta, 0, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 3, true);
          }
          return getTurbineCasing(tMeta, 3, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord + 1, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 6, true);
          }
          return getTurbineCasing(tMeta, 6, false);
        }
        if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 1, true);
          }
          return getTurbineCasing(tMeta, 1, false);
        }
        if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 7, true);
          }
          return getTurbineCasing(tMeta, 7, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord + 1, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 8, true);
          }
          return getTurbineCasing(tMeta, 8, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 5, true);
          }
          return getTurbineCasing(tMeta, 5, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord - 1, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 2, true);
          }
          return getTurbineCasing(tMeta, 2, false);
        }
      } else if ((aSide == 4) || (aSide == 5)) {
        TileEntity tTileEntity;
        Object tMetaTileEntity;
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord, yCoord - 1, zCoord + (aSide == 4 ? 1 : -1))))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 0, true);
          }
          return getTurbineCasing(tMeta, 0, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord, yCoord, zCoord + (aSide == 4 ? 1 : -1))))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 3, true);
          }
          return getTurbineCasing(tMeta, 3, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord, yCoord + 1, zCoord + (aSide == 4 ? 1 : -1))))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 6, true);
          }
          return getTurbineCasing(tMeta, 6, false);
        }
        if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 1, true);
          }
          return getTurbineCasing(tMeta, 1, false);
        }
        if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord)))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 7, true);
          }
          return getTurbineCasing(tMeta, 7, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord, yCoord + 1, zCoord + (aSide == 5 ? 1 : -1))))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 8, true);
          }
          return getTurbineCasing(tMeta, 8, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord, yCoord, zCoord + (aSide == 5 ? 1 : -1))))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 5, true);
          }
          return getTurbineCasing(tMeta, 5, false);
        }
        if ((null != (tTileEntity = aWorld
            .getTileEntity(xCoord, yCoord - 1, zCoord + (aSide == 5 ? 1 : -1))))
            && ((tTileEntity instanceof IGregTechTileEntity)) && (
            ((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null
            != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()))
            && ((tMetaTileEntity instanceof GTMTE_HugeSteamTurbine))) {
          if (((IGregTechTileEntity) tTileEntity).isActive()) {
            return getTurbineCasing(tMeta, 2, true);
          }
          return getTurbineCasing(tMeta, 2, false);
        }
      }


    }
    return getIcon(aSide, tMeta);
  }
}
