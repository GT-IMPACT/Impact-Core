package com.impact.mods.gregtech.blocks.casings;

import com.impact.mods.gregtech.blocks.Build_Casing_Helper;
import com.impact.mods.gregtech.blocks.itemblocks.IB_Casing_3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IAxeWrenchable;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.util.IIcon;

import static com.impact.mods.gregtech.blocks.Build_Casing_Helper.*;
import static com.impact.mods.gregtech.enums.Texture.Icons.*;
import static com.impact.util.Utilits.BlockstackMeta;
import static gregtech.api.util.GT_LanguageManager.addStringLocalization;

public class Casing_3 extends GT_Block_Casings_Abstract implements IAxeWrenchable {

    private static final Build_Casing_Helper[] casing_helpers = {
            ME_CASING, AEROSTATE_PLATFORM, MILL,
    };

    public Casing_3() {
        super(IB_Casing_3.class, "gt.blockCase3", GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[3][b + 32] /** 48 */ = new GT_CopiedBlockTexture(this, 6, b);
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
                return CASING_ME.getIcon();
            case 1:
                return aSide == 1? PLATFORM_AEROSTATE_TOP.getIcon() : PLATFORM_AEROSTATE_SIDE.getIcon();
            case 2:
                return MILL_CASING.getIcon();
            default:
                return null;
        }
    }
    
    @Override
    public boolean apply() {
        return true;
    }
    
    @Override
    public int getHarvestLevel(int aMeta) {
        if (aMeta == 1 || aMeta == 2) {
            return 0;
        }
        return super.getHarvestLevel(aMeta);
    }
}