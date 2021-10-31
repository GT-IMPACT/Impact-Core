package com.impact.mods.gregtech.blocks.other;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_LanguageManager;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GT_Item_LongDistancePipe extends ItemBlock {

  protected final String mNoMobsToolTip = GT_LanguageManager
      .addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
  protected final String mNoTileEntityToolTip = GT_LanguageManager
      .addStringLocalization("gt.notileentityinthisblock", "This is NOT a TileEntity!");

  public GT_Item_LongDistancePipe(Block par1) {
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

  @SuppressWarnings("unchecked")
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
    super.addInformation(aStack, aPlayer, aList, aF3_H);
    aList.add(this.mNoMobsToolTip);
    aList.add(this.mNoTileEntityToolTip);
  }
}
