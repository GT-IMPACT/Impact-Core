package com.gwppcore.GregTech.casings.case1;

import com.gwppcore.GregTech.GTregister.GT_ItemList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
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
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Upgrade Casing T1");         // 0   384
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Upgrade Casing T2");         // 1   385
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Upgrade Casing T3");         // 2   386
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Upgrade Casing T4");         // 3   387
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "PBE Casing");                // 4   388
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Engraver Casing");           // 5   389
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Assembler Casing");          // 6   390
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Centrifuge Casing");         // 7   391
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Electrolyzer Casing");       // 8   392
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Wire Factory Casing");       // 9   393
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Supply Production Casing"); // 10  394
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Utility Machine Casing");   // 11  395
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Brewmenter Casing");               // 12  396
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Electrolyser Casing");      // 13  397

/*
        ================================================================================================================
        ================================================================================================================
*/
        GT_ItemList.Upgradet1.set(new ItemStack(this, 1, 0));
        GT_ItemList.Upgradet2.set(new ItemStack(this, 1, 1));
        GT_ItemList.Upgradet3.set(new ItemStack(this, 1, 2));
        GT_ItemList.Upgradet4.set(new ItemStack(this, 1, 3));
        GT_ItemList.BenderCase.set(new ItemStack(this, 1, 4));
        GT_ItemList.LaserCase.set(new ItemStack(this, 1, 5));
        GT_ItemList.PresserCase.set(new ItemStack(this, 1, 6));
        GT_ItemList.ExtruderCase.set(new ItemStack(this, 1, 7));
        GT_ItemList.AssemblerCase.set(new ItemStack(this, 1, 8));
        GT_ItemList.CircuitAssemblerCase.set(new ItemStack(this, 1, 9));
        GT_ItemList.WireAssemblerCase.set(new ItemStack(this, 1, 10));
        GT_ItemList.WiremillCase.set(new ItemStack(this, 1, 11));
        GT_ItemList.ArcPlasmaCase.set(new ItemStack(this, 1, 12));
        GT_ItemList.ElectrolyzerCase.set(new ItemStack(this, 1, 12));
    }

    private static IIcon UPGRATE_CASING_T1, UPGRATE_CASING_T2, UPGRATE_CASING_T3, UPGRATE_CASING_T4, BENDER_CASING,
                         LASER_CASING, PRESS_CASING, EXTRUDER_CASING, ASSEMBLER_CASING, CIRCUIT_ASSEMBLER_CASING,
                         WIRE_ASSEMBLER_CASING, WIREMILL_CASING, ARC_FURNACE_CASING, ELECTROLYZER_CASING;

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
        //super.registerBlockIcons(aIconRegister);
        UPGRATE_CASING_T1 = aIconRegister.registerIcon(RES_BLOCK + "UPGRATE_CASING_T1");
        UPGRATE_CASING_T2 = aIconRegister.registerIcon(RES_BLOCK + "UPGRATE_CASING_T2");
        UPGRATE_CASING_T3 = aIconRegister.registerIcon(RES_BLOCK + "UPGRATE_CASING_T3");
        UPGRATE_CASING_T4 = aIconRegister.registerIcon(RES_BLOCK + "UPGRATE_CASING_T4");
        BENDER_CASING = aIconRegister.registerIcon(RES_BLOCK + "BENDER_CASING");
        LASER_CASING = aIconRegister.registerIcon(RES_BLOCK + "LASER_CASING");
        PRESS_CASING = aIconRegister.registerIcon(RES_BLOCK + "PRESS_CASING");
        EXTRUDER_CASING = aIconRegister.registerIcon(RES_BLOCK + "EXTRUDER_CASING");
        ASSEMBLER_CASING = aIconRegister.registerIcon(RES_BLOCK + "ASSEMBLER_CASING");
        CIRCUIT_ASSEMBLER_CASING = aIconRegister.registerIcon(RES_BLOCK +"CIRCUIT_ASSEMBLER_CASING");
        WIRE_ASSEMBLER_CASING = aIconRegister.registerIcon(RES_BLOCK + "WIRE_ASSEMBLER_CASING");
        WIREMILL_CASING = aIconRegister.registerIcon(RES_BLOCK + "WIREMILL_CASING");
        ARC_FURNACE_CASING = aIconRegister.registerIcon(RES_BLOCK + "ARC_FURNACE_CASING");
        ELECTROLYZER_CASING = aIconRegister.registerIcon(RES_BLOCK + "ELECTROLYZER_CASING");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case  0: return UPGRATE_CASING_T1;
            case  1: return UPGRATE_CASING_T2;
            case  2: return UPGRATE_CASING_T3;
            case  3: return UPGRATE_CASING_T4;
            case  4: return BENDER_CASING;
            case  5: return LASER_CASING;
            case  6: return PRESS_CASING;
            case  7: return EXTRUDER_CASING;
            case  8: return ASSEMBLER_CASING;
            case  9: return CIRCUIT_ASSEMBLER_CASING;
            case 10: return WIRE_ASSEMBLER_CASING;
            case 11: return WIREMILL_CASING;
            case 12: return ARC_FURNACE_CASING;
            case 13: return ELECTROLYZER_CASING;

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
