package com.impact.events;

import com.impact.core.Impact_API;
import com.impact.mods.gregtech.enums.DropsBlock;
import com.impact.mods.gregtech.items.tools.GTMG_Tool_WorkRadius;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

import static com.impact.util.Utilits.getBlock;
import static com.impact.util.Utilits.is;
import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.*;

public class EventDropBlock {
	
	static {
		
		DropsBlock.add(Blocks.stone, 0, 0.05f, dustImpure, Stone, Iron, Copper, Tin, Coal);

		String ubc = "UndergroundBiomes";
		String name = "igneousStone";
		DropsBlock.add(getBlock(ubc, name), 0, 0.05f, dustImpure, RedGranite, Iron, Copper, Tin, Coal); //Red Granite
		DropsBlock.add(getBlock(ubc, name), 1, 0.05f, dustImpure, BlackGranite, Iron, Copper, Tin, Coal); //Black Granite
		DropsBlock.add(getBlock(ubc, name), 2, 0.05f, is(dust, PotassiumFeldspar), is(dustImpure, Iron), is(dustImpure, Copper), is(dustImpure, Tin), is(dustImpure, Coal)); //Rhyolite
		DropsBlock.add(getBlock(ubc, name), 3, 0.05f, is(dust, SiliconDioxide), is(dustImpure, Iron), is(dustImpure, Copper), is(dustImpure, Tin), is(dustImpure, Coal)); //Andesite
		DropsBlock.add(getBlock(ubc, name), 4, 0.05f, is(dust, PotassiumFeldspar), is(dustImpure, Iron), is(dustImpure, Copper), is(dustImpure, Tin), is(dustImpure, Coal)); //Gabbro
		DropsBlock.add(getBlock(ubc, name), 5, 0.05f, dustImpure, Basalt, Iron, Copper, Tin, Coal); //Basalt
		DropsBlock.add(getBlock(ubc, name), 6, 0.05f, is(dust, Biotite), is(dustImpure, Iron), is(dustImpure, Copper), is(dustImpure, Tin), is(dustImpure, Coal)); //Komatite
		DropsBlock.add(getBlock(ubc, name), 7, 0.05f, is(dust, SiliconDioxide), is(dustImpure, Iron), is(dustImpure, Copper), is(dustImpure, Tin), is(dustImpure, Coal)); //Dacite
		
		name = "metamorphicStone";
		DropsBlock.add(getBlock(ubc, name), 0, 0.05f, is(dust, SiliconDioxide), is(dustImpure, Iron), is(dustImpure, Copper), is(dustImpure, Tin), is(dustImpure, Coal)); //Gneiss
		DropsBlock.add(getBlock(ubc, name), 1, 0.05f, is(dust, SiliconDioxide), is(dustImpure, Iron), is(dustImpure, Copper), is(dustImpure, Tin), is(dustImpure, Coal)); //Eclogite
		DropsBlock.add(getBlock(ubc, name), 2, 0.05f, dustImpure, Marble, Iron, Copper, Tin, Coal); //Marble
		DropsBlock.add(getBlock(ubc, name), 3, 0.05f, dustImpure, Quartzite, Iron, Copper, Tin, Coal); //Quartzite
		DropsBlock.add(getBlock(ubc, name), 4, 0.05f, dustImpure, Glauconite, Iron, Copper, Tin, Coal); //Blue Schist
		DropsBlock.add(getBlock(ubc, name), 5, 0.05f, dustImpure, Glauconite, Iron, Copper, Tin, Coal); //Green Schist
		DropsBlock.add(getBlock(ubc, name), 6, 0.05f, dustImpure, Soapstone, Iron, Copper, Tin, Coal); //Soapstone
		DropsBlock.add(getBlock(ubc, name), 7, 0.05f, is(dust, SiliconDioxide), is(dustImpure, Iron), is(dustImpure, Copper), is(dustImpure, Tin), is(dustImpure, Coal)); //Migmatite
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