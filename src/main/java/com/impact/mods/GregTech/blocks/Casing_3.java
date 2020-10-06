package com.impact.mods.GregTech.blocks;

import com.impact.mods.GregTech.GT_ItemList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import net.minecraft.util.IIcon;

import static com.impact.util.Utilits.BlockstackMeta;

public class Casing_3 extends GT_Block_Casings_Abstract {


    public Casing_3() {
        super(IB_Casing_3.class, "gt.blockcasingsSC", GT_Material_Casings.INSTANCE);
        GT_Utility.addTexturePage((byte) 8);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[8][b + 64] = new GT_CopiedBlockTexture(this, 6, b);
        }

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Farm Casing");

        GT_ItemList.Casing_Farm.set(BlockstackMeta(this, 3));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 3: return Textures.BlockIcons.MACHINE_CASING_PIPE_STEEL.getIcon();
            default: return null;
        }
    }


}
