package com.impact.mods.GregTech.casings.case1;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;




public class GT_Block_Case1
        extends GT_Block_Casings_Abstract {
    //public static boolean mConnectedMachineTextures = true;
    public static final String RES_BLOCK = "gregtech:iconsets/";



    public GT_Block_Case1() {
        super(GT_Item_Case1.class, "gt.blockCase1", GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[3][b] /** 16 */ = new GT_CopiedBlockTexture(this, 6, b);
            /*IMPORTANT for block recoloring*/
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

/*
        ================================================================================================================
        ================================================================================================================
*/
        GT_ItemList.UpgradeCasingT1         .set(new ItemStack(this, 1, 0));
        GT_ItemList.UpgradeCasingT2         .set(new ItemStack(this, 1, 1));
        GT_ItemList.UpgradeCasingT3         .set(new ItemStack(this, 1, 2));
        GT_ItemList.UpgradeCasingT4         .set(new ItemStack(this, 1, 3));
        GT_ItemList.PBECasing               .set(new ItemStack(this, 1, 4));
        GT_ItemList.EngraverCasing          .set(new ItemStack(this, 1, 5));
        GT_ItemList.AssemblerCasing         .set(new ItemStack(this, 1, 6));
        GT_ItemList.CentrifugeCasing        .set(new ItemStack(this, 1, 7));
        GT_ItemList.ElectrolyzerCasing      .set(new ItemStack(this, 1, 8));
        GT_ItemList.WireFactoryCasing       .set(new ItemStack(this, 1, 9));
        GT_ItemList.SupplyProductionCasing  .set(new ItemStack(this, 1, 10));
        GT_ItemList.UtilityMachineCasing    .set(new ItemStack(this, 1, 11));
        GT_ItemList.BrewmenterCasing        .set(new ItemStack(this, 1, 12));
        GT_ItemList.ArcCasing               .set(new ItemStack(this, 1, 13));
        GT_ItemList.CuttingCasing           .set(new ItemStack(this, 1, 14));
        GT_ItemList.MixingCasing            .set(new ItemStack(this, 1, 15));
    }

    private static IIcon UpgradeCasingT1, UpgradeCasingT2, UpgradeCasingT3, UpgradeCasingT4, PBECasing,
    EngraverCasing, AssemblerCasing, CentrifugeCasing, ElectrolyzerCasing, WireFactoryCasing,
    SupplyProductionCasing, UtilityMachineCasing, BrewmenterCasing, ArcCasing, CuttingCasing,
    MixingCasing

    ;

//    public static final IIconContainer[] mTextures = new IIconContainer[]
//            {
//                    new Textures.BlockIcons.CustomIcon(RES_BLOCK + "UPGRATE_CASING_T1"),
//                    new Textures.BlockIcons.CustomIcon(RES_BLOCK + "UPGRATE_CASING_T1"),
//                    new Textures.BlockIcons.CustomIcon(RES_BLOCK + "UPGRATE_CASING_T1"),
//                    new Textures.BlockIcons.CustomIcon(RES_BLOCK + "UPGRATE_CASING_T1"),
//                    new Textures.BlockIcons.CustomIcon(RES_BLOCK + "UPGRATE_CASING_T1"),
//            };

    @Override
    public void registerBlockIcons(IIconRegister aIconRegister) {
        UpgradeCasingT1         = aIconRegister.registerIcon(RES_BLOCK +  "UpgradeCasingT1"         );
        UpgradeCasingT2         = aIconRegister.registerIcon(RES_BLOCK +  "UpgradeCasingT2"         );
        UpgradeCasingT3         = aIconRegister.registerIcon(RES_BLOCK +  "UpgradeCasingT3"         );
        UpgradeCasingT4         = aIconRegister.registerIcon(RES_BLOCK +  "UpgradeCasingT4"         );
        PBECasing               = aIconRegister.registerIcon(RES_BLOCK +  "PBECasing"               );
        EngraverCasing          = aIconRegister.registerIcon(RES_BLOCK +  "EngraverCasing"          );
        AssemblerCasing         = aIconRegister.registerIcon(RES_BLOCK +  "AssemblerCasing"         );
        CentrifugeCasing        = aIconRegister.registerIcon(RES_BLOCK +  "CentrifugeCasing"        );
        ElectrolyzerCasing      = aIconRegister.registerIcon(RES_BLOCK +  "ElectrolyzerCasing"      );
        WireFactoryCasing       = aIconRegister.registerIcon(RES_BLOCK +  "WireFactoryCasing"       );
        SupplyProductionCasing  = aIconRegister.registerIcon(RES_BLOCK +  "SupplyProductionCasing"  );
        UtilityMachineCasing    = aIconRegister.registerIcon(RES_BLOCK +  "UtilityMachineCasing"    );
        BrewmenterCasing        = aIconRegister.registerIcon(RES_BLOCK +  "BrewmenterCasing"        );
        ArcCasing               = aIconRegister.registerIcon(RES_BLOCK +  "ArcCasing"               );
        CuttingCasing           = aIconRegister.registerIcon(RES_BLOCK +  "CuttingCasing"           );
        MixingCasing            = aIconRegister.registerIcon(RES_BLOCK +  "MixingCasing"            );
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case  0: return UpgradeCasingT1;
            case  1: return UpgradeCasingT2;
            case  2: return UpgradeCasingT3;
            case  3: return UpgradeCasingT4;
            case  4: return PBECasing;
            case  5: return EngraverCasing;
            case  6: return AssemblerCasing;
            case  7: return CentrifugeCasing;
            case  8: return ElectrolyzerCasing;
            case  9: return WireFactoryCasing;
            case 10: return SupplyProductionCasing;
            case 11: return UtilityMachineCasing;
            case 12: return BrewmenterCasing;
            case 13: return ArcCasing;
            case 14: return CuttingCasing;
            case 15: return MixingCasing;

            default: return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide) {
        int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
        return getIcon(aSide, tMeta);
    }
}
