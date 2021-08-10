package com.impact.mods.gregtech.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.util.IIcon;

import static com.impact.mods.gregtech.GT_ItemList.*;
import static com.impact.mods.gregtech.enums.Texture.Icons.*;
import static com.impact.util.Utilits.BlockstackMeta;
import static gregtech.api.util.GT_LanguageManager.addStringLocalization;

public class Casing_2 extends GT_Block_Casings_Abstract {

  public Casing_2() {
    super(IB_Casing_2.class, "gt.blockCase2", GT_Material_Casings.INSTANCE);
    for (byte b = 0; b < 16; b = (byte) (b + 1)) {
      Textures.BlockIcons.casingTexturePages[3][b + 16] /** 32 */ = new GT_CopiedBlockTexture(this, 6, b);
    }
    addStringLocalization(getUnlocalizedName() + ".0.name",             "Nuclear Turbine Casing"); //400
    addStringLocalization(getUnlocalizedName() + ".1.name",             "Electromagnetic Casing"); //401
    addStringLocalization(getUnlocalizedName() + ".2.name",             "Extradification Casing"); //402
    addStringLocalization(getUnlocalizedName() + ".3.name",                  "Maceration Casing"); //403
    addStringLocalization(getUnlocalizedName() + ".4.name",                  "3D Printed Casing"); //404
    addStringLocalization(getUnlocalizedName() + ".5.name",           "Configuration 3x3 Casing"); //405
    addStringLocalization(getUnlocalizedName() + ".6.name",           "Configuration 4x4 Casing"); //406
    addStringLocalization(getUnlocalizedName() + ".7.name",                "Primitive Pump Deck"); //407
    addStringLocalization(getUnlocalizedName() + ".8.name",  "Lapotronic Super Capacitor Casing"); //408
    addStringLocalization(getUnlocalizedName() + ".9.name",                      "Wooden Casing"); //409
    addStringLocalization(getUnlocalizedName() + ".10.name",              "Naquadah Base Casing"); //410
    addStringLocalization(getUnlocalizedName() + ".11.name",                    "Cyclone Casing"); //411
    addStringLocalization(getUnlocalizedName() + ".12.name",                 "Moon Miner Casing"); //412
    addStringLocalization(getUnlocalizedName() + ".13.name",             "Rail Assembler Casing"); //413
    addStringLocalization(getUnlocalizedName() + ".14.name",             "Space Elevator Casing"); //414
    addStringLocalization(getUnlocalizedName() + ".15.name",       "Lab-Safe Low Gravity Casing"); //415

    NukeTurbineCasing          .set(BlockstackMeta(this,  0));
    ElectromagneticCasing      .set(BlockstackMeta(this,  1));
    ExtradificationCasing      .set(BlockstackMeta(this,  2));
    MacerationCasing           .set(BlockstackMeta(this,  3));
    DDDPrinterCasing           .set(BlockstackMeta(this,  4));
    DDDPrinterCasing3x3        .set(BlockstackMeta(this,  5));
    DDDPrinterCasing4x4        .set(BlockstackMeta(this,  6));
    PrimitiveWaterPumpCase     .set(BlockstackMeta(this,  7));
    LSCC                       .set(BlockstackMeta(this,  8));
    SawCase                    .set(BlockstackMeta(this,  9));
    NqCasing                   .set(BlockstackMeta(this, 10));
    CycloneCasing              .set(BlockstackMeta(this, 11));
    MoonMinerCasing            .set(BlockstackMeta(this, 12));
    RailAssemblerCasing        .set(BlockstackMeta(this, 13));
    SpaceElevatorCasing        .set(BlockstackMeta(this, 14));
    LabSafeLGCasing            .set(BlockstackMeta(this, 15));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int aSide, int aMeta) {
    switch (aMeta) {
      case 0: return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
      case 1: return CASING_ELECTROMAGNETIC.getIcon();
      case 2: return CASING_EXTRADIFICATOR.getIcon();
      case 3: return CASING_MACERATOR.getIcon();
      case 4: return CASING_PRINTER.getIcon();
      case 5: return CASING_PRINTER3X3.getIcon();
      case 6: return CASING_PRINTER4X4.getIcon();
      case 7: return aSide <= 1 ? CASING_PUMP_TOP.getIcon() : CASING_PUMP.getIcon();
      case 8: return aSide <= 1 ? CASING_LSC_TOP.getIcon() : CASING_LSC.getIcon();
      case 9: return CASING_SAW.getIcon();
      case 10: return CASING_NAQUADAH.getIcon();
      case 11: return CASING_CYCLONE.getIcon();
      case 12: return CASING_MOON_MINER.getIcon();
      case 13: return aSide <= 1 ? CASING_RAIL_ASSEMBLER_TOP.getIcon() : CASING_RAIL_ASSEMBLER_SIDE.getIcon();
      case 14: return CASING_SPACE_ELEVATOR_TOP.getIcon();
      case 15: return CASING_LAB_SAVE_LG.getIcon();
      default:
        return null;
    }
  }
}