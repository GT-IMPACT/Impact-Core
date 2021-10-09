package com.impact.util.recipe;

import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.multis.WorldProperties;
import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecipeHelper {

    public static int calcTimeParallel(GT_MetaTileEntity_MultiParallelBlockBase base) {
        int time;
        switch (base.mParallel) {
            default:
                time = base.mMaxProgresstime;
                break;
            case 16:
                time = base.mMaxProgresstime / 2;
                break;
            case 64:
                time = base.mMaxProgresstime / 3;
                break;
            case 256:
                time = base.mMaxProgresstime / 4;
                break;
        }
        if (time < 1) {
            time = 1;
        }
        return time;
    }

    public static ItemStack[] resizeItemStackSize(ItemStack[] tOut) {
        List<ItemStack> overStacks = new ArrayList<>();
        for (ItemStack stack : tOut) {
            while (stack.getMaxStackSize() < stack.stackSize) {
                ItemStack tmp = stack.copy();
                tmp.stackSize = tmp.getMaxStackSize();
                stack.stackSize = stack.stackSize - stack.getMaxStackSize();
                overStacks.add(tmp);
            }
        }
        if (overStacks.size() > 0) {
            ItemStack[] tmp = new ItemStack[overStacks.size()];
            tmp = overStacks.toArray(tmp);
            tOut = ArrayUtils.addAll(tOut, tmp);
        }
        List<ItemStack> tSList = new ArrayList<>();
        for (ItemStack tS : tOut) {
            if (tS.stackSize > 0) {
                tSList.add(tS);
            }
        }
        return tSList.toArray(new ItemStack[tSList.size()]);
    }

    public static ItemStack[] resizeItemStackSizeChance(ItemStack[] tOut, GT_Recipe tRecipe, GT_MetaTileEntity_MultiParallelBlockBase base) {
        return resizeItemStackSizeChance(tOut, tRecipe, base, true);
    }
    public static ItemStack[] resizeItemStackSizeChance(ItemStack[] tOut, GT_Recipe tRecipe, GT_MetaTileEntity_MultiParallelBlockBase base, boolean chance) {
        if (chance) {
            for (int f = 0; f < tOut.length; f++) {
                if (tRecipe.mOutputs[f] != null && tOut[f] != null) {
                    for (int g = 0; g < base.mCheckParallelCurrent; g++) {
                        if (base.getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(f)) {
                            tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
                        }
                    }
                }
            }
        }
        tOut = clean(tOut);
        resizeItemStackSize(tOut);
        return tOut;
    }

    public static ItemStack[] clean(final ItemStack[] v) {
        List<ItemStack> list = new ArrayList<>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new ItemStack[list.size()]);
    }

}
