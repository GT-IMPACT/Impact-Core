package com.gwppcore.gthandler.casings.glass1.glassed;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

import static net.minecraft.util.StatCollector.translateToLocal;

public class GlassBlocksItem extends ItemBlock {
    public static GlassBlocksItem INSTANCE;

    public GlassBlocksItem(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        aList.add( "STEKLO EPTA");//Glassy & Classy


    }
}
