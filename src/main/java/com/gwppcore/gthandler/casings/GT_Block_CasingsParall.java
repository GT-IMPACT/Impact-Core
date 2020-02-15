package com.gwppcore.gthandler.casings;

import com.gwppcore.gthandler.CustomItemList;
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




public class GT_Block_CasingsParall
        extends GT_Block_Casings_Abstract {
    //public static boolean mConnectedMachineTextures = true;

    private static IIcon Up1, Up2, Up3, Up4, BendC, LaserC, PressC, ExtrudC, AssembC, CircAssembC, WireAssembC, WiremillC, ArcC, ElectrC;

    public GT_Block_CasingsParall() {
        super(GT_Item_CasingsParall.class, "gt.blockcasingsParall", GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[3][b] = new GT_CopiedBlockTexture(this, 6, b);
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

    @Override
    public void registerBlockIcons(IIconRegister aIconRegister) {
        //super.registerBlockIcons(aIconRegister);
        Up1 = aIconRegister.registerIcon("gregtech:iconsets/UPGRATE_CASING_T1");
        Up2 = aIconRegister.registerIcon("gregtech:iconsets/UPGRATE_CASING_T2");
        Up3 = aIconRegister.registerIcon("gregtech:iconsets/UPGRATE_CASING_T3");
        Up4 = aIconRegister.registerIcon("gregtech:iconsets/UPGRATE_CASING_T4");
        BendC = aIconRegister.registerIcon("gregtech:iconsets/BENDER_CASING");
        LaserC = aIconRegister.registerIcon("gregtech:iconsets/LASER_CASING");
        PressC = aIconRegister.registerIcon("gregtech:iconsets/PRESS_CASING");
        ExtrudC = aIconRegister.registerIcon("gregtech:iconsets/EXTRUDER_CASING");
        AssembC = aIconRegister.registerIcon("gregtech:iconsets/ASSEMBLER_CASING");
        CircAssembC = aIconRegister.registerIcon("gregtech:iconsets/CIRCUIT_ASSEMBLER_CASING");
        WireAssembC = aIconRegister.registerIcon("gregtech:iconsets/WIRE_ASSEMBLER_CASING");
        WiremillC = aIconRegister.registerIcon("gregtech:iconsets/WIREMILL_CASING");
        ArcC = aIconRegister.registerIcon("gregtech:iconsets/ARC_FURNACE_CASING");
        ElectrC = aIconRegister.registerIcon("gregtech:iconsets/ELECTROLYZER_CASING");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case  0: return Up1;
            case  1: return Up2;
            case  2: return Up3;
            case  3: return Up4;
            case  4: return BendC;
            case  5: return LaserC;
            case  6: return PressC;
            case  7: return ExtrudC;
            case  8: return AssembC;
            case  9: return CircAssembC;
            case 10: return WireAssembC;
            case 11: return WiremillC;
            case 12: return ArcC;
            case 13: return ElectrC;


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
