package com.impact.mods.gregtech.blocks.casings;

import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_ARC;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_ASSEMBLER;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_BREWMENTER;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_CENTRIFUGE;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_CUTTER;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_ELECTROLYZER;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_LASER;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_MIXER;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PARALLEL1;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PARALLEL2;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PARALLEL3;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PARALLEL4;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_PBE;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_SUPPLY;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_UTILITY;
import static com.impact.mods.gregtech.enums.Texture.Icons.CASING_WIRE;
import static com.impact.util.Utilits.BlockstackMeta;
import static gregtech.api.util.GT_LanguageManager.addStringLocalization;

import com.impact.mods.gregtech.GT_ItemList;
import com.impact.mods.gregtech.blocks.itemblocks.IB_Casing_1;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;

import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.util.IIcon;


public class Casing_1 extends GT_Block_Casings_Abstract {

  public Casing_1() {
    super(IB_Casing_1.class, "gt.blockCase1", GT_Material_Casings.INSTANCE);
    for (byte b = 0; b < 16; b = (byte) (b + 1)) {
      Textures.BlockIcons.casingTexturePages[3][b] /** 16 */ = new GT_CopiedBlockTexture(this, 6,
          b);
    }
    
    addStringLocalization(getUnlocalizedName() + ".0.name", "Parallel CPU T1");  // 0   384
    addStringLocalization(getUnlocalizedName() + ".1.name", "Parallel CPU T2");  // 1   385
    addStringLocalization(getUnlocalizedName() + ".2.name", "Parallel CPU T3");  // 2   386
    addStringLocalization(getUnlocalizedName() + ".3.name", "Parallel CPU T4");  // 3   387
    addStringLocalization(getUnlocalizedName() + ".4.name", "PBE Casing");  // 4   388
    addStringLocalization(getUnlocalizedName() + ".5.name", "Engraver Casing");  // 5   389
    addStringLocalization(getUnlocalizedName() + ".6.name", "Assembler Casing");  // 6   390
    addStringLocalization(getUnlocalizedName() + ".7.name", "Centrifuge Casing");  // 7   391
    addStringLocalization(getUnlocalizedName() + ".8.name", "Electrolyzer Casing");  // 8   392
    addStringLocalization(getUnlocalizedName() + ".9.name", "Wire Factory Casing");  // 9   393
    addStringLocalization(getUnlocalizedName() + ".10.name", "Supply Production Casing");  // 10  394
    addStringLocalization(getUnlocalizedName() + ".11.name", "Utility Machine Casing");  // 11  395
    addStringLocalization(getUnlocalizedName() + ".12.name", "Brewmenter Casing");  // 12  396
    addStringLocalization(getUnlocalizedName() + ".13.name", "Arc Casing");  // 13  397
    addStringLocalization(getUnlocalizedName() + ".14.name", "Cutting Casing");  // 14  398
    addStringLocalization(getUnlocalizedName() + ".15.name", "Mixing Casing");  // 15  399

    GT_ItemList.UpgradeCasingT1.set(BlockstackMeta(this, 0));
    GT_ItemList.UpgradeCasingT2.set(BlockstackMeta(this, 1));
    GT_ItemList.UpgradeCasingT3.set(BlockstackMeta(this, 2));
    GT_ItemList.UpgradeCasingT4.set(BlockstackMeta(this, 3));
    GT_ItemList.PBECasing.set(BlockstackMeta(this, 4));
    GT_ItemList.EngraverCasing.set(BlockstackMeta(this, 5));
    GT_ItemList.AssemblerCasing.set(BlockstackMeta(this, 6));
    GT_ItemList.CentrifugeCasing.set(BlockstackMeta(this, 7));
    GT_ItemList.ElectrolyzerCasing.set(BlockstackMeta(this, 8));
    GT_ItemList.WireFactoryCasing.set(BlockstackMeta(this, 9));
    GT_ItemList.SupplyProductionCasing.set(BlockstackMeta(this, 10));
    GT_ItemList.UtilityMachineCasing.set(BlockstackMeta(this, 11));
    GT_ItemList.BrewmenterCasing.set(BlockstackMeta(this, 12));
    GT_ItemList.ArcCasing.set(BlockstackMeta(this, 13));
    GT_ItemList.CuttingCasing.set(BlockstackMeta(this, 14));
    GT_ItemList.MixingCasing.set(BlockstackMeta(this, 15));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int aSide, int aMeta) {
    switch (aMeta) {
      case 0:
        return CASING_PARALLEL1.getIcon();
      case 1:
        return CASING_PARALLEL2.getIcon();
      case 2:
        return CASING_PARALLEL3.getIcon();
      case 3:
        return CASING_PARALLEL4.getIcon();
      case 4:
        return CASING_PBE.getIcon();
      case 5:
        return CASING_LASER.getIcon();
      case 6:
        return CASING_ASSEMBLER.getIcon();
      case 7:
        return CASING_CENTRIFUGE.getIcon();
      case 8:
        return CASING_ELECTROLYZER.getIcon();
      case 9:
        return CASING_WIRE.getIcon();
      case 10:
        return CASING_SUPPLY.getIcon();
      case 11:
        return CASING_UTILITY.getIcon();
      case 12:
        return CASING_BREWMENTER.getIcon();
      case 13:
        return CASING_ARC.getIcon();
      case 14:
        return CASING_CUTTER.getIcon();
      case 15:
        return CASING_MIXER.getIcon();

      default:
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
    }
  }
}
