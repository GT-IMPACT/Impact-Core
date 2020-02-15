package com.gwppcore.util;

import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class Utilits {

    public static boolean invertBoolean(final boolean booleans) {
        if (booleans == true) {
            return false;
        }
        return true;
    }

    public static String translateGTItemStack(ItemStack itemStack){
        if (!GT_Utility.isStackValid(itemStack))
            return "Not a Valid ItemStack:"+itemStack;
        String ret = GT_LanguageManager.getTranslation(GT_LanguageManager.getTranslateableItemStackName(itemStack));
        if (!ret.contains("%material"))
            return ret;
        String matname = "";
        if (Utilits.checkStackAndPrefix(itemStack))
            matname = GT_OreDictUnificator.getAssociation(itemStack).mMaterial.mMaterial.mDefaultLocalName;
        return ret.replace("%material", matname);
    }

    public static boolean checkStackAndPrefix(ItemStack itemStack) {
        return itemStack != null && GT_OreDictUnificator.getAssociation(itemStack) != null && GT_OreDictUnificator.getAssociation(itemStack).mPrefix != null && GT_OreDictUnificator.getAssociation(itemStack).mMaterial != null && GT_OreDictUnificator.getAssociation(itemStack).mMaterial.mMaterial != null;
    }

}
