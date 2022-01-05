package com.impact.events;

import com.impact.core.Impact_API;
import com.impact.mods.gregtech.enums.DropsBlock;
import com.impact.mods.gregtech.items.tools.GTMG_Tool_WorkRadius;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

import static com.impact.common.item.Core_List_Items.*;
import static com.impact.util.Utilits.getBlock;
import static com.impact.util.Utilits.is;
import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.*;

public class EventDropBlock {
	
	static {
		ItemStack[] dropCrashedStone = {DropCrashedStone.get(1), DropCrashedMetallic.get(1), DropCrashedCoal.get(1)};
		DropsBlock.add(Blocks.stone, 0, 0.15f, dropCrashedStone);
		String ubc = "UndergroundBiomes";
		String name = "igneousStone";
		for (int i = 0; i < 8; i++) {
			//Red Granite, Black Granite, Rhyolite, Andesite, Gabbro, Basalt, Komatite, Dacite
			DropsBlock.add(getBlock(ubc, name), i, 0.15f, dropCrashedStone);
		}
		name = "metamorphicStone";
		for (int i = 0; i < 8; i++) {
			//Gneiss, Eclogite, Marble, Quartzite, Blue Schist, Green Schist, Soapstone, Migmatite
			DropsBlock.add(getBlock(ubc, name), i, 0.15f, dropCrashedStone);
		}
	}
	
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
								for (int i = 0; i < dropBlock.drop.size(); i++) {
									float a = event.world.rand.nextFloat();
									if (a <= dropBlock.chance[i]) {
										event.drops.add(dropBlock.drop.get(i));
										event.dropChance = dropBlock.chance[i];
									}
								}
								return;
							}
						}
					}
				}
			}
		}
	}
}