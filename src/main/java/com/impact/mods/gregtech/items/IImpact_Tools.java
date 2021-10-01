package com.impact.mods.gregtech.items;

import gregtech.api.interfaces.IToolStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IImpact_Tools {

    boolean startBreak(ItemStack stack, int x, int y, int z, EntityPlayer player);

    IToolStats getTools();
}
