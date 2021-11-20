package com.impact.mods.gregtech.enums;

import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static gregtech.api.enums.Materials.*;

public enum BiomesOreGenerator {
	
	IRON_VEIN("Iron", BiomeGenBase.desert, 0, Iron, Magnetite, BandedIron, YellowLimonite, BrownLimonite),
	COPPER_VEIN("Copper", BiomeGenBase.desert, 0, Copper, Tetrahedrite, Malachite, Chalcopyrite),
	TIN_VEIN("Tin", BiomeGenBase.plains, 0, Tin, Cassiterite),
	COAL_VEIN("Coal", BiomeGenBase.forest, 0, Coal, Lignite),
	NICKEL_VEIN("Nickel", BiomeGenBase.beach, 0, Nickel, Cobalt, Garnierite),
	
	NONE("Empty", null, -1);
	
	private final BiomeGenBase mBiome;
	private final List<ItemStack> mOre;
	private final String mName;
	private final int mTier;
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, Materials... materials) {
		this.mBiome = biomes;
		this.mName  = name + " Vein";
		this.mTier  = tier;
		this.mOre   = new ArrayList<>();
		Stream.of(materials).forEach(material -> this.mOre.add(GT_OreDictUnificator.get(OrePrefixes.crushed, material, 1)));
	}
	
	public static BiomesOreGenerator getFromName(String name) {
		for (BiomesOreGenerator b : BiomesOreGenerator.values()) {
			if (b.name().equals(name)) {
				return b;
			}
		}
		return NONE;
	}
	
	public static List<ItemStack> generatedOres(World w, int x, int z, int tier) {
		Chunk chunk = w.getChunkFromBlockCoords(x, z);
		ChunkPosition chunkPosition = chunk.getChunkCoordIntPair().func_151349_a(0);
		int xx = chunkPosition.chunkPosX - 8;
		int zz = chunkPosition.chunkPosZ - 8;
		int dim = w.provider.dimensionId;
		startGenerateOres(w, xx, zz);
		int[] m = {dim, xx, zz, tier};
		List<ItemStack> ores = new ArrayList<>();
		for (int[] i : Impact_API.sOreInChunk.keySet()) {
			if (i[0] == m[0] && i[1] == m[1] && i[2] == m[2] && i[3] == m[3]) {
				BiomesOreGenerator b = getFromName(Impact_API.sOreInChunk.get(i));
				ores.addAll(b.mOre);
			}
		}
		return ores;
	}
	
	public static void startGenerateOres(World w, int x, int z) {
		generate(w, x, z);
	}
	
	private static void generate(World w, int x, int z) {
		BiomeGenBase biomes = w.getBiomeGenForCoords(x, z);
		int dim = w.provider.dimensionId;
		for (BiomesOreGenerator biomesOreGenerator : BiomesOreGenerator.values()) {
			int[] m = {dim, x, z, biomesOreGenerator.mTier};
			for (int[] i : Impact_API.sOreInChunk.keySet()) {
				if (i[0] == m[0] && i[1] == m[1] && i[2] == m[2] && i[3] == m[3]) {
					return;
				}
			}
			if (biomesOreGenerator != NONE && biomes.biomeName.equals(biomesOreGenerator.mBiome.biomeName)) {
				Impact_API.sOreInChunk.put(m, biomesOreGenerator.name());
			}
		}
	}
}