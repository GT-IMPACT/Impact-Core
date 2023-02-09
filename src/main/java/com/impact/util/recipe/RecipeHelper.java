package com.impact.util.recipe;

import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.impact.core.Config.MAX_TICK_RATE;

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
        if (time < MAX_TICK_RATE) {
            time = MAX_TICK_RATE;
        }
        return time;
    }

    public static ItemStack[] resizeItemStackSize(ItemStack[] tOut) {
        List<ItemStack> overStacks = new ArrayList<>();
        for (ItemStack stack : tOut) {
            if (stack != null) {
                while (stack.getMaxStackSize() < stack.stackSize) {
                    ItemStack tmp = stack.copy();
                    tmp.stackSize   = tmp.getMaxStackSize();
                    stack.stackSize = stack.stackSize - stack.getMaxStackSize();
                    overStacks.add(tmp);
                }
            }
        }
        if (overStacks.size() > 0) {
            ItemStack[] tmp = new ItemStack[overStacks.size()];
            tmp = overStacks.toArray(tmp);
            tOut = ArrayUtils.addAll(tOut, tmp);
        }
        return removeNull(tOut).clone();
    }

    public static ItemStack[] resizeItemStackSizeChance(ItemStack[] tOut, GT_Recipe tRecipe, GT_MetaTileEntity_MultiParallelBlockBase<?> base) {
        return resizeItemStackSizeChance(tOut, tRecipe, base.mCheckParallelCurrent, true, base.getBaseMetaTileEntity());
    }
    
    public static ItemStack[] resizeItemStackSizeChance(ItemStack[] tOut, GT_Recipe tRecipe, GT_MetaTileEntity_MultiParallelBlockBase<?> base, boolean chance) {
        return resizeItemStackSizeChance(tOut, tRecipe, base.mCheckParallelCurrent, chance, base.getBaseMetaTileEntity());
    }

    public static ItemStack[] resizeItemStackSizeChance(ItemStack[] tOut, GT_Recipe tRecipe, int currentParallel, boolean chance, IGregTechTileEntity te) {
        if (tRecipe == null) return null;
        if (chance) {
            for (int f = 0; f < tOut.length; f++) {
                if (tRecipe.mOutputs[f] != null && tOut[f] != null) {
                    for (int g = 0; g < currentParallel; g++) {
                        if (te.getRandomNumber(10000) < tRecipe.getOutputChance(f)) {
                            tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
                        }
                    }
                }
            }
        }
        return resizeItemStackSize(tOut);
    }

    public static ItemStack[] removeNull(ItemStack[] in) {
        int countNull = 0;
        for (ItemStack stack : in) {
            if (stack == null || stack.stackSize == 0) {
                countNull++;
            }
        }
        ItemStack[] on = new ItemStack[in.length - countNull];
        for (int i = 0, j = 0; i < in.length; i++) {
            if (in[i] != null && in[i].stackSize > 0) {
                on[j] = in[i];
                j++;
            }
        }
        return on;
    }


}
