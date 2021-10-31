package com.impact.mods.gregtech.blocks.itemblocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_LanguageManager;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public abstract class IB_Base extends ItemBlock {

  protected final String mNoMobsToolTip = GT_LanguageManager
      .addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
  protected final String mNoTileEntityToolTip = GT_LanguageManager
      .addStringLocalization("gt.notileentityinthisblock", "This is NOT a TileEntity!");
  protected final String mUpgrade1Tooltip = GT_LanguageManager
      .addStringLocalization("gt.upgradeparallel1",
          "" + EnumChatFormatting.AQUA + "4" + EnumChatFormatting.GRAY
              + " Parallel Point");
  protected final String mUpgrade2Tooltip = GT_LanguageManager
      .addStringLocalization("gt.upgradeparallel2",
          "" + EnumChatFormatting.AQUA + "16" + EnumChatFormatting.GRAY
              + " Parallel Point");
  protected final String mUpgrade3Tooltip = GT_LanguageManager
      .addStringLocalization("gt.upgradeparallel3",
          "" + EnumChatFormatting.AQUA + "64" + EnumChatFormatting.GRAY
              + " Parallel Point");
  protected final String mUpgrade4Tooltip = GT_LanguageManager
      .addStringLocalization("gt.upgradeparallel4",
          "" + EnumChatFormatting.AQUA + "256" + EnumChatFormatting.GRAY
              + " Parallel Point");
  protected final String mConfiguration3Dprinter3x3 = GT_LanguageManager
      .addStringLocalization("gt.3Dprinter33",
          "Configuration Casing for 3D Printer: " + EnumChatFormatting.GREEN + "3x3");
  protected final String mConfiguration3Dprinter4x4 = GT_LanguageManager
      .addStringLocalization("gt.3Dprinter44",
          "Configuration Casing for 3D Printer: " + EnumChatFormatting.GREEN + "4x4");

  public IB_Base(Block par1) {
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

  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
    super.addInformation(aStack, aPlayer, aList, aF3_H);
  }
}
