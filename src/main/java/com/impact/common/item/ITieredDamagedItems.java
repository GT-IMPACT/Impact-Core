package com.impact.common.item;

import net.minecraft.item.ItemStack;

import java.awt.*;

public interface ITieredDamagedItems {
    int getCoefficient(ItemStack stack);
    boolean isBroken(ItemStack stack);
    Color getColor(ItemStack stack);
    boolean isNew(ItemStack stack);
}