package com.impact.mods.GregTech.blocks;

import com.impact.mods.GregTech.GT_ItemList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.util.IIcon;

import static com.impact.mods.GregTech.enums.Texture.Icons.*;
import static com.impact.util.Utilits.BlockstackMeta;

public class Casing_2 extends GT_Block_Casings_Abstract {

    public Casing_2() {
        super(IB_Casing_2.class, "gt.blockCase2", GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[3][b+16] /** 32 */ = new GT_CopiedBlockTexture(this, 6, b);
        }
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name",  "Nuclear Turbine Casing"); //400
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name",  "Electromagnetic Casing"); //401
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name",  "Extradification Casing"); //402
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name",  "Maceration Casing"); //403
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name",  "3D Printed Casing"); //404
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name",  "Configuration 3x3 Casing"); //405
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name",  "Configuration 4x4 Casing"); //406
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name",  "Primitive Pump Deck"); //407
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name",  "Lapotronic Super Capacitor Casing"); //408
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name",  "Wooden Casing"); //409
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Naquadah Base Casing"); //410
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Cyclone Casing"); //411
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Moon Miner Casing"); //412
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Rail Assembler Casing"); //413

        GT_ItemList.NukeTurbineCasing       .set(BlockstackMeta(this, 0));
        GT_ItemList.ElectromagneticCasing   .set(BlockstackMeta(this, 1));
        GT_ItemList.ExtradificationCasing   .set(BlockstackMeta(this, 2));
        GT_ItemList.MacerationCasing        .set(BlockstackMeta(this, 3));
        GT_ItemList.DDDPrinterCasing        .set(BlockstackMeta(this, 4));
        GT_ItemList.DDDPrinterCasing3x3     .set(BlockstackMeta(this, 5));
        GT_ItemList.DDDPrinterCasing4x4     .set(BlockstackMeta(this, 6));
        GT_ItemList.PrimitiveWaterPumpCase  .set(BlockstackMeta(this, 7));
        GT_ItemList.LSCC                    .set(BlockstackMeta(this, 8));
        GT_ItemList.SawCase                 .set(BlockstackMeta(this, 9));
        GT_ItemList.NqCasing                .set(BlockstackMeta(this, 10));
        GT_ItemList.CycloneCasing           .set(BlockstackMeta(this, 11));
        GT_ItemList.MoonMinerCasing         .set(BlockstackMeta(this, 12));
        GT_ItemList.RailAssemblerCasing     .set(BlockstackMeta(this, 13));

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
           case 0:  return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
           case 1:  return CASING_ELECTROMAGNETIC.getIcon();
           case 2:  return CASING_EXTRADIFICATOR.getIcon();
           case 3:  return CASING_MACERATOR.getIcon();
           case 4:  return CASING_PRINTER.getIcon();
           case 5:  return CASING_PRINTER3X3.getIcon();
           case 6:  return CASING_PRINTER4X4.getIcon();
           case 7:  return aSide <= 1 ? CASING_PUMP_TOP.getIcon() : CASING_PUMP.getIcon();
           case 8:  return aSide <= 1 ? CASING_LSC_TOP.getIcon() : CASING_LSC.getIcon();
           case 9:  return CASING_SAW.getIcon();
           case 10: return CASING_NAQUADAH.getIcon();
           case 11: return CASING_CYCLONE.getIcon();
           case 12: return CASING_MOON_MINER.getIcon();
           case 13: return aSide <= 1 ? CASING_RAIL_ASSEMBLER_TOP.getIcon() : CASING_RAIL_ASSEMBLER_SIDE.getIcon();
           default: return null;
        }
    }

//    @SideOnly(Side.CLIENT)
//    public IIcon getIcon(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide) {
//        int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
////        if (tMeta != 10) {
//            return getIcon(aSide, tMeta);
////        }
////        final int tStartIndex = 0;
////        if (tMeta == 10) {
////            boolean[] tConnectedSides = {(aWorld.getBlock(xCoord, yCoord - 1, zCoord) == this) && (aWorld.getBlockMetadata(xCoord, yCoord - 1, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord + 1, zCoord) == this) && (aWorld.getBlockMetadata(xCoord, yCoord + 1, zCoord) == tMeta), (aWorld.getBlock(xCoord + 1, yCoord, zCoord) == this) && (aWorld.getBlockMetadata(xCoord + 1, yCoord, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord, zCoord + 1) == this) && (aWorld.getBlockMetadata(xCoord, yCoord, zCoord + 1) == tMeta), (aWorld.getBlock(xCoord - 1, yCoord, zCoord) == this) && (aWorld.getBlockMetadata(xCoord - 1, yCoord, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord, zCoord - 1) == this) && (aWorld.getBlockMetadata(xCoord, yCoord, zCoord - 1) == tMeta)};
////            switch (aSide) {
////                case 0:
////                    if (tConnectedSides[0]) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 6)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 2)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 3)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 4)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 5)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 8)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 9)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 10)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 11)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (!tConnectedSides[2])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 1)].getIcon();
////                    }
////                    if ((!tConnectedSides[5]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 0)].getIcon();
////                    }
////                case 1:
////                    if (tConnectedSides[1]) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 6)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 2)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 3)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 4)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 5)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 8)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 9)].getIcon();
////                    }
////                    if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 10)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 11)].getIcon();
////                    }
////                    if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 1)].getIcon();
////                    }
////                    if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 0)].getIcon();
////                    }
////                case 2:
////                    if (tConnectedSides[5]) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 6)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 2)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 5)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 4)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 3)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 11)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 10)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 9)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 8)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 1)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 0)].getIcon();
////                    }
////                case 3:
////                    if (tConnectedSides[3]) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 6)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 4)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 5)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 2)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 3)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 10)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 11)].getIcon();
////                    }
////                    if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 8)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 9)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 1)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 0)].getIcon();
////                    }
////                case 4:
////                    if (tConnectedSides[4]) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 6)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 5)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 4)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 3)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 2)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 10)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 9)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 8)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 11)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 0)].getIcon();
////                    }
////                    if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 1)].getIcon();
////                    }
////                case 5:
////                    if (tConnectedSides[2]) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 6)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 5)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 2)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 3)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 4)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 11)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 8)].getIcon();
////                    }
////                    if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 9)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 10)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
////                    }
////                    if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 0)].getIcon();
////                    }
////                    if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
////                        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 1)].getIcon();
////                    }
////                    break;
////            }
////        }
////        return Texture.Icons.CONNECTED_FUSHULLS[(tStartIndex + 7)].getIcon();
//
//
//    }

}
