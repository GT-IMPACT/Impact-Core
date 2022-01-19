package com.impact.mods.gregtech.items.tools.behaviour;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Behaviour_DebugOreGen extends Behaviour_None {
	
	public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_DebugOreGen();
	
	public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
		if (aPlayer instanceof EntityPlayerMP) {
			Chunk ch = aWorld.getChunkFromBlockCoords(aX, aZ);
			int dimId = aWorld.provider.dimensionId;
			if (aPlayer.isSneaking()) {
				OresRegionGenerator region;
				switch (aStack.stackSize) {
					case 1:
						region = OreGenerator.getRegions(ch);
						int idRegion = Objects.hash(region.xRegion, region.zRegion, dimId);
						Impact_API.regionsOres.remove(idRegion, region);
						GT_Utility.sendChatToPlayer(aPlayer, "Current Ore Region Cleared");
						break;
					case 2:
						region = OreGenerator.getRegions(ch);
						if (region == null) break;
						if (!region.veins.containsKey(dimId)) break;
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
						if (!region.veins.containsKey(dimId)) break;
						for (int i = 0; i < OresRegionGenerator.layers; i++) {
							List<OreVeinGenerator> veins = region.veins.get(i);
							for (OreVeinGenerator vein : veins) {
								OreVein oreVein = Impact_API.registerVeins.get(vein.oreVeinID);
								for (OreChunkGenerator oreChunkGenerator : vein.oreChunkGenerators) {
									Chunk chunks = aWorld.getChunkFromChunkCoords(oreChunkGenerator.xChunk, oreChunkGenerator.zChunk);
									OreChunkGenerator chunk = OreGenerator.getChunk(chunks, i);
									if (chunk != null) {
										chunk.setSize(oreVein);
									}
								}
							}
						}
						GT_Utility.sendChatToPlayer(aPlayer, "Current Ore Region Cycles Filled");
						break;
					case 4:
						for (int id : DimensionManager.getIDs()) {
							String name = DimensionManager.getProvider(id).getDimensionName();
							GT_Utility.sendChatToPlayer(aPlayer, "ID: " + EnumChatFormatting.GREEN + id + EnumChatFormatting.RESET + " Name: " + EnumChatFormatting.GREEN + name);
							System.out.println(id + "  " + name);
						}
						break;
					case 5:
						long millis = System.currentTimeMillis();
						Map<Integer, OresRegionGenerator> rem = new HashMap<>();
						Impact_API.regionsOres.forEach((hash, reg) -> {
							if (reg.dim == dimId) {
								rem.put(hash, reg);
							}
						});
						rem.forEach(Impact_API.regionsOres::remove);
						GT_Utility.sendChatToPlayer(aPlayer, "Removed id: " + dimId + "! Lag: " + (System.currentTimeMillis() - millis) + "ms");
						break;
					case 61:
						long start = System.currentTimeMillis();
						Map<Integer, OresRegionGenerator> replace = new HashMap<>();
						Impact_API.regionsOres.forEach((hash, reg) -> {
							if (reg.dim == dimId) {
								replace.put(hash, reg);
							}
						});
						replace.forEach(Impact_API.regionsOres::remove);
						for (int x = -10; x <= 10; x++) {
							for (int y = -10; y <= 10; y++) {
								OresRegionGenerator fuckServer = new OresRegionGenerator(x, y, dimId);
								fuckServer.createVeins();
								int idReg = Objects.hash(fuckServer.xRegion, fuckServer.zRegion, fuckServer.dim);
								Impact_API.regionsOres.put(idReg, fuckServer);
							}
						}
						GT_Utility.sendChatToPlayer(aPlayer, "Lag: " + (System.currentTimeMillis() - start) + "ms");
						break;
						
				}
			} else {
				switch (aStack.stackSize) {
					case 1:
					case 2:
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
						break;
					case 50:
					case 51:
						int tierVein = aStack.stackSize - 50;
						int sizeVein = 0;
						OreVeinGenerator o = OreGenerator.getVein(ch, tierVein);
						if (o != null) {
							for (OreChunkGenerator oo : o.oreChunkGenerators) {
								sizeVein += oo.sizeOreChunk;
							}
						}
						GT_Utility.sendChatToPlayer(aPlayer, "Layer " + tierVein + ", Vein Size Cycles: " + GT_Utility.formatNumbers(sizeVein));
						break;
					case 60:
					case 61:
						int tierChunk = aStack.stackSize - 60;
						int sizeChunk = OreGenerator.sizeChunk(ch, tierChunk);
						GT_Utility.sendChatToPlayer(aPlayer, "Layer " + tierChunk + ", Chunk Size Cycles: " + GT_Utility.formatNumbers(sizeChunk));
						break;
				}
			}
			return true;
		}
		return false;
	}
	
	public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
		aList.add(EnumChatFormatting.RED + "DEBUG ORE GEN");
		aList.add(" ");
		aList.add(EnumChatFormatting.GREEN + "SHIFT + RCLICK - Stacksize this item:");
		aList.add("   -  x1 - Remove full Region");
		aList.add("   -  x2 - Remove only Region Veins Cycles");
		aList.add("   -  x3 - Filled Region Veins Cycles");
		aList.add("   -  x4 - Print All ID and Name World Dimensions");
		aList.add("   -  x5 - Remove full Dimension");
		aList.add("   - x61 - Generate Regions Area (Cube) -10K x 10K blocks");
		aList.add(" ");
		aList.add(EnumChatFormatting.YELLOW + "RCLICK - Stacksize this item: ");
		aList.add("   -  x1 - 0 Ore Layer increase current Ore Chunk (10,000 cycles)");
		aList.add("   -  x2 - 1 Ore Layer increase current Ore Chunk (10,000 cycles)");
		aList.add("   - x50 - Check 0 layer Vein Size");
		aList.add("   - x51 - Check 1 layer Vein Size");
		aList.add("   - x60 - Check 0 layer Chunk Size");
		aList.add("   - x61 - Check 1 layer Chunk Size");
		return aList;
	}
}