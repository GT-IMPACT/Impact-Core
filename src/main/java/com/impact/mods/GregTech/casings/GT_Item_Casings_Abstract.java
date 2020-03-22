package com.impact.mods.GregTech.casings;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public abstract class GT_Item_Casings_Abstract
        extends ItemBlock {
    protected final String mNoMobsToolTip = GT_LanguageManager.addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
    protected final String mAuthor = GT_LanguageManager.addStringLocalization("gt.authorcase", "Added by " + EnumChatFormatting.YELLOW +"4gname");
    protected final String mNoTileEntityToolTip = GT_LanguageManager.addStringLocalization("gt.notileentityinthisblock", "This is NOT a TileEntity!");
    protected final String mUpgrade1Tooltip = GT_LanguageManager.addStringLocalization("gt.upgradeparallel1", "Upgrade to" + EnumChatFormatting.AQUA + " 4"+ EnumChatFormatting.GRAY +" Parallel Point");
    protected final String mUpgrade2Tooltip = GT_LanguageManager.addStringLocalization("gt.upgradeparallel2", "Upgrade to" + EnumChatFormatting.AQUA + " 16"+ EnumChatFormatting.GRAY +" Parallel Point");
    protected final String mUpgrade3Tooltip = GT_LanguageManager.addStringLocalization("gt.upgradeparallel3", "Upgrade to" + EnumChatFormatting.AQUA + " 64"+ EnumChatFormatting.GRAY +" Parallel Point");
    protected final String mUpgrade4Tooltip = GT_LanguageManager.addStringLocalization("gt.upgradeparallel4", "Upgrade to" + EnumChatFormatting.AQUA + " 256"+ EnumChatFormatting.GRAY +" Parallel Point");


    public GT_Item_Casings_Abstract(Block par1) {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
    }

    public int getMetadata(int aMeta) {
        return aMeta;
    }

    public String getUnlocalizedName(ItemStack aStack) {
        return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
    }

    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
    }
}
