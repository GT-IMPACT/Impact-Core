package com.impact.mods.gregtech.blocks.casings;

import com.impact.mods.gregtech.blocks.Build_Casing_Helper;
import com.impact.mods.gregtech.blocks.itemblocks.IB_Casing_2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.util.IIcon;

import static com.impact.mods.gregtech.blocks.Build_Casing_Helper.*;
import static com.impact.mods.gregtech.enums.Texture.Icons.*;
import static com.impact.util.Utilits.BlockstackMeta;
import static gregtech.api.util.GT_LanguageManager.addStringLocalization;

public class Casing_2 extends GT_Block_Casings_Abstract {

    private static final Build_Casing_Helper[] casing_helpers = {
            NUKE_TURBINE_CASING, ELECTROMAGNETIC_CASING, EXTRADIFICATION_CASING, MACERATION_CASING, DDD_PRINTER_CASING,
            DDD_PRINTER_CASING3X3, DDD_PRINTER_CASING4X4, PRIMITIVE_WATER_PUMP_CASING, LSCC_CASING, SAW_CASING,
            NQ_CASING, CYCLONE_CASING, MOON_MINER_CASING, RAILASSEMBLER_CASING, SPACE_ELEVATOR_CASING, LAB_SAFELG_CASING
    };

    public Casing_2() {
        super(IB_Casing_2.class, "gt.blockCase2", GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[3][b + 16] = new GT_CopiedBlockTexture(this, 6, b);
        }
        for (int i = 0; i < casing_helpers.length; i++) {
            addStringLocalization(getUnlocalizedName() + "." + i + ".name", casing_helpers[i].getName());
            casing_helpers[i].getItemContainer().set(BlockstackMeta(this, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
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
                return aSide <= 1 ? CASING_RAIL_ASSEMBLER_TOP.getIcon() : CASING_RAIL_ASSEMBLER_SIDE.getIcon();
            case 14:
                return CASING_SPACE_ELEVATOR_TOP.getIcon();
            case 15:
                return CASING_LAB_SAVE_LG.getIcon();
            default:
                return null;
        }
    }
}