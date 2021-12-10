package com.impact.mods.gregtech.items.tools.behaviour;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import com.impact.util.Utilits;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.List;
import java.util.Objects;

public class Behaviour_DebugOreGen extends Behaviour_None {
	
	public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_DebugOreGen();
	
	public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
		if (aPlayer instanceof EntityPlayerMP) {
			Chunk ch = aWorld.getChunkFromBlockCoords(aX, aZ);
			if (aPlayer.isSneaking()) {
				OresRegionGenerator region;
				switch (aStack.stackSize) {
					case 1:
						region = OreGenerator.getRegions(ch);
						int idRegion = Objects.hash(region.xRegion, region.zRegion);
						Impact_API.regionsOres.remove(idRegion, region);
						GT_Utility.sendChatToPlayer(aPlayer, "Current Ore Region Cleared");
						break;
					case 2:
						region = OreGenerator.getRegions(ch);
						if (region == null) break;
						if (!region.veins.containsKey(aWorld.provider.dimensionId)) break;
						for (int i = 0; i < OresRegionGenerator.layers; i++) {
							List<OreVeinGenerator> veins = region.veins.get(i);
							for (OreVeinGenerator vein : veins) {
								for (OreChunkGenerator oreChunkGenerator : vein.oreChunkGenerators) {
									Chunk chunks = aWorld.getChunkFromChunkCoords(oreChunkGenerator.xChunk, oreChunkGenerator.zChunk);
									OreChunkGenerator chunk = OreGenerator.getChunk(chunks, i);
									if (chunk != null) {
										chunk.sizeOreChunk = 0;
									}
								}
							}
						}
						GT_Utility.sendChatToPlayer(aPlayer, "Current Ore Region Cycles Removed");
						break;
					case 3:
						region = OreGenerator.getRegions(ch);
						if (region == null) break;
						if (!region.veins.containsKey(aWorld.provider.dimensionId)) break;
						for (int i = 0; i < OresRegionGenerator.layers; i++) {
							List<OreVeinGenerator> veins = region.veins.get(i);
							for (OreVeinGenerator vein : veins) {
								for (OreChunkGenerator oreChunkGenerator : vein.oreChunkGenerators) {
									Chunk chunks = aWorld.getChunkFromChunkCoords(oreChunkGenerator.xChunk, oreChunkGenerator.zChunk);
									OreChunkGenerator chunk = OreGenerator.getChunk(chunks, i);
									if (chunk != null) {
										int max = 100_000 * ((i + 1) << i);
										int min = 50_000 * ((i + 1) << i);
										chunk.sizeOreChunk = Utilits.getRandom(min, max);
									}
								}
							}
						}
						GT_Utility.sendChatToPlayer(aPlayer, "Current Ore Region Cycles Filled");
						break;
					case 61:
						long start = System.currentTimeMillis();
						Impact_API.regionsOres.clear();
						for (int x = -10; x <= 10; x++) {
							for (int y = -10; y <= 10; y++) {
								OresRegionGenerator fuckServer = new OresRegionGenerator(x, y, aWorld.provider.dimensionId);
								fuckServer.createVeins();
								int idReg = Objects.hash(fuckServer.xRegion, fuckServer.zRegion);
								Impact_API.regionsOres.put(idReg, fuckServer);
							}
						}
						GT_Utility.sendChatToPlayer(aPlayer, "Lag: " + (System.currentTimeMillis() - start) + "ms");
						break;
						
				}
			} else {
				int layer = aStack.stackSize - 1;
				OreChunkGenerator chunk = OreGenerator.getChunk(ch, layer);
				if (chunk != null) {
					OreVein vein = OreGenerator.getOreVein(ch, layer);
					GT_Utility.sendChatToPlayer(aPlayer, "Name Vein: " + Impact_API.registerVeins.get(vein.idVein).nameVein);
					OreGenerator.amountIncrease(ch, layer, 10_000);
					GT_Utility.sendChatToPlayer(aPlayer, "Current Ore Chunk Increase 10,000 cycles, Cycles: " + chunk.sizeOreChunk);
				} else {
					GT_Utility.sendChatToPlayer(aPlayer, "Not Found Ore Chunk! Please conduct a geo-exploration");
				}
			}
			return true;
		}
		return false;
	}
	
	public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
		aList.add("Debug Ore Gen");
		aList.add("Shift + RClick - clear current Ore Region (512 x 512)");
		aList.add("Stacksize this item:");
		aList.add("   - 1x - Remove full Region");
		aList.add("   - 2x - Remove only Region Veins Cycles");
		aList.add("   - 3x - Filled Region Veins Cycles");
		aList.add("   - 61x - Generate Regions Area (Cube) -10K x 10K blocks");
		aList.add(" ");
		aList.add("RClick - increase current Ore Chunk (10,000 cycles)");
		aList.add("Stacksize this item: ");
		aList.add("   - 1x - 0 Ore Layer");
		aList.add("   - 2x - 1 Ore Layer");
		aList.add("   - 3x - 2 Ore Layer");
		return aList;
	}
}