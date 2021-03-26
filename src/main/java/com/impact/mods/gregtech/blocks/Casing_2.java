package com.impact.mods.gregtech.blocks;

import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_CYCLONE;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_ELECTROMAGNETIC;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_EXTRADIFICATOR;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_LSC;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_LSC_TOP;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_MACERATOR;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_MOON_MINER;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_NAQUADAH;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PRINTER;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PRINTER3X3;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PRINTER4X4;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PUMP;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PUMP_TOP;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_RAIL_ASSEMBLER_SIDE;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_RAIL_ASSEMBLER_TOP;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_SAW;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_SPACE_ELEVATOR_TOP;
import static com.impact.util.Utilits.BlockstackMeta;

import com.impact.mods.gregtech.GT_ItemList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.util.IIcon;

public class Casing_2 extends GT_Block_Casings_Abstract {

  public Casing_2() {
    super(IB_Casing_2.class, "gt.blockCase2", GT_Material_Casings.INSTANCE);
    for (byte b = 0; b < 16; b = (byte) (b + 1)) {
      Textures.BlockIcons.casingTexturePages[3][b + 16] /** 32 */ = new GT_CopiedBlockTexture(this,
          6, b);
    }
    //GT_LanguageManager
    //    .addStringLocalization(getUnlocalizedName() + ".0.name", "Nuclear Turbine Casing"); //400
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".1.name", "Electromagnetic Casing"); //401
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".2.name", "Extradification Casing"); //402
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".3.name", "Maceration Casing"); //403
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".4.name", "3D Printed Casing"); //404
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".5.name", "Configuration 3x3 Casing"); //405
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".6.name", "Configuration 4x4 Casing"); //406
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".7.name", "Primitive Pump Deck"); //407
    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name",
        "Lapotronic Super Capacitor Casing"); //408
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".9.name", "Wooden Casing"); //409
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".10.name", "Naquadah Base Casing"); //410
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".11.name", "Cyclone Casing"); //411
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".12.name", "Moon Miner Casing"); //412
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".13.name", "Rail Assembler Casing"); //413
    GT_LanguageManager
        .addStringLocalization(getUnlocalizedName() + ".14.name", "Space Elevator Casing"); //413

    //GT_ItemList.NukeTurbineCasing.set(BlockstackMeta(this, 0));
    GT_ItemList.ElectromagneticCasing.set(BlockstackMeta(this, 1));
    GT_ItemList.ExtradificationCasing.set(BlockstackMeta(this, 2));
    GT_ItemList.MacerationCasing.set(BlockstackMeta(this, 3));
    GT_ItemList.DDDPrinterCasing.set(BlockstackMeta(this, 4));
    GT_ItemList.DDDPrinterCasing3x3.set(BlockstackMeta(this, 5));
    GT_ItemList.DDDPrinterCasing4x4.set(BlockstackMeta(this, 6));
    GT_ItemList.PrimitiveWaterPumpCase.set(BlockstackMeta(this, 7));
    GT_ItemList.LSCC.set(BlockstackMeta(this, 8));
    GT_ItemList.SawCase.set(BlockstackMeta(this, 9));
    GT_ItemList.NqCasing.set(BlockstackMeta(this, 10));
    GT_ItemList.CycloneCasing.set(BlockstackMeta(this, 11));
    GT_ItemList.MoonMinerCasing.set(BlockstackMeta(this, 12));
    GT_ItemList.RailAssemblerCasing.set(BlockstackMeta(this, 13));
    GT_ItemList.SpaceElevatorCasing.set(BlockstackMeta(this, 14));

  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int aSide, int aMeta) {
    switch (aMeta) {
      //case 0:
      //  return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
      case 1:
        return CASING_ELECTROMAGNETIC.getIcon();
      case 2:
        return CASING_EXTRADIFICATOR.getIcon();
      case 3:
        return CASING_MACERATOR.getIcon();
      case 4:
        return CASING_PRINTER.getIcon();
      case 5:
        return CASING_PRINTER3X3.getIcon();
      case 6:
        return CASING_PRINTER4X4.getIcon();
      case 7:
        return aSide <= 1 ? CASING_PUMP_TOP.getIcon() : CASING_PUMP.getIcon();
      case 8:
        return aSide <= 1 ? CASING_LSC_TOP.getIcon() : CASING_LSC.getIcon();
      case 9:
        return CASING_SAW.getIcon();
      case 10:
        return CASING_NAQUADAH.getIcon();
      case 11:
        return CASING_CYCLONE.getIcon();
      case 12:
        return CASING_MOON_MINER.getIcon();
      case 13:
        return aSide <= 1 ? CASING_RAIL_ASSEMBLER_TOP.getIcon()
            : CASING_RAIL_ASSEMBLER_SIDE.getIcon();
      case 14:
        return CASING_SPACE_ELEVATOR_TOP.getIcon();
      default:
        return null;
    }
  }
}
