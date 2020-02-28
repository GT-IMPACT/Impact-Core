package com.gwppcore.gthandler.casings.case1;

import com.gwppcore.gthandler.CustomItemList;
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
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Bending Casing");            // 4   388
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Laser Engraver Casing");     // 5   389
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Pressing Casing");           // 6   390
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Extruding Casing");          // 7   391
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Assembling Casing");         // 8   392
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Circuit Assembling Casing"); // 9   393
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Wire Assembling Casing");   // 10  394
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Wiremill Casing");          // 11  395
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Arc Casing");               // 12  396
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Electrolyser Casing");      // 13  397

/*
        ================================================================================================================
        ================================================================================================================
*/
        CustomItemList.Upgradet1.set(new ItemStack(this, 1, 0));
        CustomItemList.Upgradet2.set(new ItemStack(this, 1, 1));
        CustomItemList.Upgradet3.set(new ItemStack(this, 1, 2));
        CustomItemList.Upgradet4.set(new ItemStack(this, 1, 3));
        CustomItemList.BenderCase.set(new ItemStack(this, 1, 4));
        CustomItemList.LaserCase.set(new ItemStack(this, 1, 5));
        CustomItemList.PresserCase.set(new ItemStack(this, 1, 6));
        CustomItemList.ExtruderCase.set(new ItemStack(this, 1, 7));
        CustomItemList.AssemblerCase.set(new ItemStack(this, 1, 8));
        CustomItemList.CircuitAssemblerCase.set(new ItemStack(this, 1, 9));
        CustomItemList.WireAssemblerCase.set(new ItemStack(this, 1, 10));
        CustomItemList.WiremillCase.set(new ItemStack(this, 1, 11));
        CustomItemList.ArcPlasmaCase.set(new ItemStack(this, 1, 12));
        CustomItemList.ElectrolyzerCase.set(new ItemStack(this, 1, 12));
    }

    private static IIcon UPGRATE_CASING_T1, UPGRATE_CASING_T2, UPGRATE_CASING_T3, UPGRATE_CASING_T4, BENDER_CASING,
                         LASER_CASING, PRESS_CASING, EXTRUDER_CASING, ASSEMBLER_CASING, CIRCUIT_ASSEMBLER_CASING,
                         WIRE_ASSEMBLER_CASING, WIREMILL_CASING, ARC_FURNACE_CASING, ELECTROLYZER_CASING;

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
