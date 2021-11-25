package com.impact.events;

import com.impact.core.Impact_API;
import com.impact.mods.gregtech.enums.DropsBlock;
import com.impact.mods.gregtech.items.tools.GTMG_Tool_WorkRadius;
import com.impact.mods.gregtech.items.tools.IImpact_Tools;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.dustImpure;
import static gregtech.api.enums.OrePrefixes.nugget;

public class EventDropBlock {
	
	static {
		DropsBlock.add(Blocks.stone, 0, 0.1f, dustImpure, Iron, Copper, Tin, Coal);
	}
//
//		String ubc = "UndergroundBiomes";
//		DropsBlock.add(getBlock(ubc, "igneousStone"), 0, 0.1f);
//		DropsBlock.add(getBlock(ubc, "igneousStone"), 1, 0.1f);
//		DropsBlock.add(getBlock(ubc, "igneousStone"), 2, 0.1f);
//		DropsBlock.add(getBlock(ubc, "igneousStone"), 3, 0.1f);
//		DropsBlock.add(getBlock(ubc, "igneousStone"), 4, 0.1f);
//		DropsBlock.add(getBlock(ubc, "igneousStone"), 5, 0.1f, nugget, Neutronium);
//		DropsBlock.add(getBlock(ubc, "igneousStone"), 6, 0.1f);
//		DropsBlock.add(getBlock(ubc, "igneousStone"), 7, 0.1f);
//
//		DropsBlock.add(getBlock(ubc, "metamorphicStone"), 0, 0.1f);
//		DropsBlock.add(getBlock(ubc, "metamorphicStone"), 1, 0.1f);
//		DropsBlock.add(getBlock(ubc, "metamorphicStone"), 2, 0.1f);
//		DropsBlock.add(getBlock(ubc, "metamorphicStone"), 3, 0.1f);
//		DropsBlock.add(getBlock(ubc, "metamorphicStone"), 4, 0.1f);
//		DropsBlock.add(getBlock(ubc, "metamorphicStone"), 5, 0.1f);
//		DropsBlock.add(getBlock(ubc, "metamorphicStone"), 6, 0.1f);
//		DropsBlock.add(getBlock(ubc, "metamorphicStone"), 7, 0.1f);
//
//		DropsBlock.add(getBlock(ubc, "sedimentaryStone"), 0, 0.1f);
//		DropsBlock.add(getBlock(ubc, "sedimentaryStone"), 1, 0.1f);
//		DropsBlock.add(getBlock(ubc, "sedimentaryStone"), 2, 0.1f);
//		DropsBlock.add(getBlock(ubc, "sedimentaryStone"), 3, 0.1f);
//		DropsBlock.add(getBlock(ubc, "sedimentaryStone"), 4, 0.1f);
//		DropsBlock.add(getBlock(ubc, "sedimentaryStone"), 5, 0.1f);
//		DropsBlock.add(getBlock(ubc, "sedimentaryStone"), 6, 0.1f);
//		DropsBlock.add(getBlock(ubc, "sedimentaryStone"), 7, 0.1f);
//	}
	
	@SubscribeEvent
	public void harvestDropEvent(BlockEvent.HarvestDropsEvent event) {
		EntityPlayer player = event.harvester;
		if (player != null) {
			ItemStack tool = player.getCurrentEquippedItem();
			if (tool != null && tool.getItem() instanceof GTMG_Tool_WorkRadius) {
				GTMG_Tool_WorkRadius dropTool = (GTMG_Tool_WorkRadius) tool.getItem();
				if (dropTool.canAdDrop(tool)) {
					for (DropsBlock dropBlock : Impact_API.dropsFromBlock) {
						if (dropBlock.block != null && dropBlock.block != Blocks.air) {
							if (dropBlock.block == event.block && dropBlock.meta == event.blockMetadata) {
								event.drops.addAll(dropBlock.drop);
								event.dropChance = dropBlock.chance;
							}
						}
					}
				}
			}
		}
	}
}