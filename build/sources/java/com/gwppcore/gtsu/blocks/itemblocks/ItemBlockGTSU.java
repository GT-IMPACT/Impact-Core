package com.gwppcore.gtsu.blocks.itemblocks;

import com.gwppcore.gtsu.NumberPrettifier;
import com.gwppcore.gtsu.TierHelper;
import com.gwppcore.gtsu.tileentity.TileEntityGTSU;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

import static com.gwppcore.gtsu.TierHelper.V;

public class ItemBlockGTSU extends ItemBlock {

    public ItemBlockGTSU(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltipList, boolean par4) {
    	if (stack.hasTagCompound()) {
    		int tier = stack.getTagCompound().getInteger(TileEntityGTSU.NBTVAL_TIER);

    		String output = StatCollector.translateToLocal("ic2.item.tooltip.Output") + " " + V[tier] + "EU/t";
    		String capacity = StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " " + NumberPrettifier.getPrettifiedNumber(TierHelper.getMaxStorageForTier(tier));
    		String stored = StatCollector.translateToLocal("ic2.item.tooltip.Store") + " " + NumberPrettifier.getPrettifiedNumber(stack.getTagCompound().getLong(TileEntityGTSU.NBTVAL_ENERGY));
    		
    		tooltipList.add(output + " " + capacity);
    		tooltipList.add(stored);
    	}
    }

}

