package com.impact.mods.GregTech.casings.Page3_0_15;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.util.IIcon;

import static com.impact.api.enums.Texture.Icons.*;
import static com.impact.util.Utilits.BlockstackMeta;


public class GT_Block_Case1
        extends GT_Block_Casings_Abstract {

    public GT_Block_Case1() {
        super(GT_Item_Case1.class, "gt.blockCase1", GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[3][b] /** 16 */ = new GT_CopiedBlockTexture(this, 6, b);
        }
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name",  "Upgrade Casing T1"         );  // 0   384
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name",  "Upgrade Casing T2"         );  // 1   385
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name",  "Upgrade Casing T3"         );  // 2   386
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name",  "Upgrade Casing T4"         );  // 3   387
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name",  "PBE Casing"                );  // 4   388
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name",  "Engraver Casing"           );  // 5   389
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name",  "Assembler Casing"          );  // 6   390
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name",  "Centrifuge Casing"         );  // 7   391
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name",  "Electrolyzer Casing"       );  // 8   392
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name",  "Wire Factory Casing"       );  // 9   393
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Supply Production Casing"  );  // 10  394
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Utility Machine Casing"    );  // 11  395
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Brewmenter Casing"         );  // 12  396
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Arc Casing"                );  // 13  397
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Cutting Casing"            );  // 14  398
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Mixing Casing"             );  // 15  399

        GT_ItemList.UpgradeCasingT1         .set(BlockstackMeta(this, 0));
        GT_ItemList.UpgradeCasingT2         .set(BlockstackMeta(this, 1));
        GT_ItemList.UpgradeCasingT3         .set(BlockstackMeta(this, 2));
        GT_ItemList.UpgradeCasingT4         .set(BlockstackMeta(this, 3));
        GT_ItemList.PBECasing               .set(BlockstackMeta(this, 4));
        GT_ItemList.EngraverCasing          .set(BlockstackMeta(this, 5));
        GT_ItemList.AssemblerCasing         .set(BlockstackMeta(this, 6));
        GT_ItemList.CentrifugeCasing        .set(BlockstackMeta(this, 7));
        GT_ItemList.ElectrolyzerCasing      .set(BlockstackMeta(this, 8));
        GT_ItemList.WireFactoryCasing       .set(BlockstackMeta(this, 9));
        GT_ItemList.SupplyProductionCasing  .set(BlockstackMeta(this, 10));
        GT_ItemList.UtilityMachineCasing    .set(BlockstackMeta(this, 11));
        GT_ItemList.BrewmenterCasing        .set(BlockstackMeta(this, 12));
        GT_ItemList.ArcCasing               .set(BlockstackMeta(this, 13));
        GT_ItemList.CuttingCasing           .set(BlockstackMeta(this, 14));
        GT_ItemList.MixingCasing            .set(BlockstackMeta(this, 15));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case  0: return CASING_PARALLEL1.getIcon();
            case  1: return CASING_PARALLEL2.getIcon();
            case  2: return CASING_PARALLEL3.getIcon();
            case  3: return CASING_PARALLEL4.getIcon();
            case  4: return CASING_PBE.getIcon();
            case  5: return CASING_LASER.getIcon();
            case  6: return CASING_ASSEMBLER.getIcon();
            case  7: return CASING_CENTRIFUGE.getIcon();
            case  8: return CASING_ELECTROLYZER.getIcon();
            case  9: return CASING_WIRE.getIcon();
            case 10: return CASING_SUPPLY.getIcon();
            case 11: return CASING_UTILITY.getIcon();
            case 12: return CASING_BREWMENTER.getIcon();
            case 13: return CASING_ARC.getIcon();
            case 14: return CASING_CUTTER.getIcon();
            case 15: return CASING_MIXER.getIcon();

            default: return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
        }
    }
}
