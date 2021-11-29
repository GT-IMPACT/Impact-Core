package com.impact.mods.gregtech.items.tools;

import gregtech.api.interfaces.IToolStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public interface IImpact_Tools {
    boolean startBreak(ItemStack stack, int x, int y, int z, EntityPlayer player);
    IToolStats getTools();
    boolean canAdDrop(ItemStack stack);
    boolean onItemUseFirst(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ);
    boolean onItemUse(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ);
    void addAdditionalToolTips(List aList, ItemStack aStack, EntityPlayer aPlayer);
}