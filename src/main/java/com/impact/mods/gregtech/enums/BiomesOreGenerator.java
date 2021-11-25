package com.impact.mods.gregtech.enums;

import com.impact.core.Impact_API;
import com.impact.util.Utilits;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.crushed;
import static gregtech.api.enums.OrePrefixes.dust;

// TODO: 21.11.2021 add Veins
public enum BiomesOreGenerator {
	
	IRON_BASIC_VEIN(    "Iron Basic",      BiomeGenBase.plains,       0, Iron),
	COPPER_BASIC_VEIN(  "Copper Basic",    BiomeGenBase.taiga,        0, Copper),
	TIN_BASIC_VEIN(     "Tin Basic",       BiomeGenBase.forest,       0, Tin),
	COAL_BASIC_VEIN(    "Coal Basic",      BiomeGenBase.extremeHills, 0, Coal, Lignite),
	REDSTONE_BASIC_VEIN("Redstone Basic",  BiomeGenBase.desert,       0, Redstone),

//	MINERALS_BASIC_VEIN("Minerals Basic", null, 0, Gypsum, Calcite), ДОБЫЧА С ПОРОДЫ
	
	ALMANDINE_VEIN(   "Almandine",    BiomeGenBase.stoneBeach,       1, Almandine, Pyrope, Sapphire, GreenSapphire),
	APATITE_VEIN(     "Apatite",      BiomeGenBase.coldTaiga,        1, Apatite, Apatite, Phosphorus, Pyrochlore),
	BENTONITE_VEIN(   "Bentonite",    BiomeGenBase.birchForest,      1, Bentonite, Magnesite, Olivine, Glauconite),
	BERYLLIUM_VEIN(   "Beryllium",    BiomeGenBase.forestHills,      1, Beryllium, Beryllium, Emerald, Thorium),
	LIMONITE_VEIN(    "Limonite",     BiomeGenBase.plains,           1, BrownLimonite, YellowLimonite, BandedIron, Malachite),
	CHALCOPYRITE_VEIN("Chalcopyrite", BiomeGenBase.taiga,            1, Chalcopyrite, Iron, Pyrite, Copper),
	COAL_VEIN(        "Coal",         BiomeGenBase.extremeHills,     1, Coal, Lignite, Coal, Lignite),
	GALENA_VEIN(      "Galena",       BiomeGenBase.desertHills,      1, Galena, Galena, Silver, Lead),
	GARNIERITE_VEIN(  "Garnierite",   BiomeGenBase.beach,            1, Garnierite, Nickel, Cobaltite, Pentlandite),
	GRAPHITE_VEIN(    "Graphite",     BiomeGenBase.mesa,             1, Graphite, Graphite, Diamond, Coal),
	GROSSULAR_VEIN(   "Grossular",    BiomeGenBase.mesaPlateau,      1, Grossular, Spessartine, Pyrolusite, Tantalite),
	LAZURITE_VEIN(    "Lazurite",     BiomeGenBase.river,            1, Lazurite, Sodalite, Lapis, Calcite),
	MAGNETITE_VEIN(   "Magnetite",    BiomeGenBase.icePlains,        1, Magnetite, Iron, VanadiumMagnetite, Gold),
	REDSTONE_VEIN(    "Redstone",     BiomeGenBase.desert,           1, Redstone, Redstone, Ruby, Cinnabar),
	ROCK_SALT_VEIN(   "Rock Salt",    BiomeGenBase.ocean,            1, RockSalt, Salt, Lepidolite, Spodumene),
	SOAPSTONE_VEIN(   "Soapstone",    BiomeGenBase.extremeHillsPlus, 1, Soapstone, Talc, Glauconite, Pentlandite),
	TETRAHEDRITE_VEIN("Tetrahedrite", BiomeGenBase.taigaHills,       1, Tetrahedrite, Tetrahedrite, Copper, Stibnite),
	TIN_VEIN(         "Tin",          BiomeGenBase.forest,           1, Tin, Tin, Cassiterite, Tin),
	WULFENITE_VEIN(   "Wulfenite",    BiomeGenBase.savanna,          1, Wulfenite, Molybdenite, Molybdenum, Powellite),
	OILSANDS_VEIN(    "Oilsands",     BiomeGenBase.swampland,        1, is(dust, Oilsands), is(crushed, Sulfur), is(crushed, Sulfur), is(crushed, Sulfur)),
	BAUXITE_VEIN(     "Bauxite",      BiomeGenBase.jungle,           1, Bauxite, Aluminium, Ilmenite, Ilmenite),
	SCHEELITE_VEIN(   "Scheelite",    BiomeGenBase.megaTaigaHills,   1, Scheelite, Scheelite, Tungstate, Lithium),
	URANITE_VEIN(	  "Uranite",      BiomeGenBase.mesaPlateau_F,    1, Uraninite, Uraninite, Uranium, Uranium),
	QUARTZ_VEIN(	  "Quartz",       BiomeGenBase.birchForestHills, 1, NetherQuartz, Barite, Quartzite, CertusQuartz),
	ZINC_VEIN(	      "Zinc",         BiomeGenBase.savannaPlateau,   1, Sphalerite, Zinc, Sulfur, Pyrite),
	
	NONE("", null, -1, Empty);
	
	public final BiomeGenBase mBiome;
	public final List<ItemStack> mOre;
	public final String mName;
	public final int mTier;
	public final short[] mChance;
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, Materials... materials) {
		this.mBiome = biomes;
		this.mName  = name + " Vein";
		this.mTier  = tier;
		this.mOre   = new ArrayList<>();
		switch (tier) {
			case 1:
			case 2:
			case 3:
				this.mChance = new short[]{9000, 6000, 6000, 3000};
				break;
			default:
				mChance = new short[materials.length];
				Arrays.fill(this.mChance, (short) 10000);
		}
		Stream.of(materials).forEach(material -> this.mOre.add(GT_OreDictUnificator.get(crushed, material, 1)));
	}
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, ItemStack... stacks) {
		this.mBiome = biomes;
		this.mName  = name + " Vein";
		this.mTier  = tier;
		this.mOre   = new ArrayList<>();
		switch (tier) {
			case 1:
			case 2:
			case 3:
				this.mChance = new short[]{9000, 6000, 6000, 3000};
				break;
			default:
				mChance = new short[stacks.length];
				Arrays.fill(this.mChance, (short) 10000);
		}
		this.mOre.addAll(Arrays.asList(stacks));
	}
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, short[] chance, Materials... materials) {
		this.mBiome  = biomes;
		this.mName   = name + " Vein";
		this.mTier   = tier;
		this.mChance = chance;
		this.mOre    = new ArrayList<>();
		Stream.of(materials).forEach(material -> this.mOre.add(GT_OreDictUnificator.get(crushed, material, 1)));
	}
	
	private static ItemStack is(OrePrefixes prefixes, Materials materials) {
		return GT_OreDictUnificator.get(prefixes, materials, 1);
	}
	
	public static BiomesOreGenerator getFromName(String name) {
		for (BiomesOreGenerator b : BiomesOreGenerator.values()) {
			if (b.name().equals(name)) {
				return b;
			}
		}
		return NONE;
	}
	
	public static BiomesOreGenerator generatedOres(World w, int x, int z, int tier) {
		Chunk chunk = w.getChunkFromBlockCoords(x, z);
		ChunkPosition chunkPosition = chunk.getChunkCoordIntPair().func_151349_a(0);
		int xx = chunkPosition.chunkPosX - 8;
		int zz = chunkPosition.chunkPosZ - 8;
		int dim = w.provider.dimensionId;
		startGenerateOres(w, xx, zz);
		VeinChunk m = new VeinChunk(dim, xx, zz, tier, -1);
		for (VeinChunk i : Impact_API.sOreInChunk.keySet()) {
			if (i.equals(m)) {
				return getFromName(Impact_API.sOreInChunk.get(i));
			}
		}
		return NONE;
	}
	
	public static int getCurrentSizeVein(World w, int x, int z, int tier) {
		BiomesOreGenerator biomesOreGenerator = generatedOres(w, x, z, tier);
		if (biomesOreGenerator.equals(NONE)) return 0;
		Chunk chunk = w.getChunkFromBlockCoords(x, z);
		ChunkPosition chunkPosition = chunk.getChunkCoordIntPair().func_151349_a(0);
		int xx = chunkPosition.chunkPosX - 8;
		int zz = chunkPosition.chunkPosZ - 8;
		int dim = w.provider.dimensionId;
		VeinChunk m = new VeinChunk(dim, xx, zz, tier, -1);
		for (VeinChunk i : Impact_API.sOreInChunk.keySet()) {
			if (i.equals(m)) {
				return i.sizeVein;
			}
		}
		return 0;
	}
	
	public static void increaseCycle(World w, int x, int z, int tier, int sizeIncrease) {
		if (sizeIncrease <= 0) return;
		BiomesOreGenerator biomesOreGenerator = generatedOres(w, x, z, tier);
		if (biomesOreGenerator.equals(NONE)) return;
		Chunk chunk = w.getChunkFromBlockCoords(x, z);
		ChunkPosition chunkPosition = chunk.getChunkCoordIntPair().func_151349_a(0);
		int xx = chunkPosition.chunkPosX - 8;
		int zz = chunkPosition.chunkPosZ - 8;
		int dim = w.provider.dimensionId;
		VeinChunk m = new VeinChunk(dim, xx, zz, tier, -1);
		for (VeinChunk i : Impact_API.sOreInChunk.keySet()) {
			if (i.equals(m)) {
				m.sizeVein = i.sizeVein - sizeIncrease;
				String currentBiome = Impact_API.sOreInChunk.get(i);
				Impact_API.sOreInChunk.remove(i, currentBiome);
				Impact_API.sOreInChunk.put(m, currentBiome);
			}
		}
	}
	
	public static void startGenerateOres(World w, int x, int z) {
		generate(w, x, z);
	}
	
	private static void generate(World w, int x, int z) {
		BiomeGenBase biomes = w.getBiomeGenForCoords(x, z);
		int dim = w.provider.dimensionId;
		for (BiomesOreGenerator biomesOreGenerator : BiomesOreGenerator.values()) {
			int sizeVein = Utilits.getRandom(50_000, 100_000);
			VeinChunk m = new VeinChunk(dim, x, z, biomesOreGenerator.mTier, sizeVein);
			for (VeinChunk i : Impact_API.sOreInChunk.keySet()) {
				if (i.equals(m)) {
					return;
				}
			}
			if (biomesOreGenerator != NONE && biomesOreGenerator.mBiome.biomeName.equals(biomes.biomeName)) {
				Impact_API.sOreInChunk.put(m, biomesOreGenerator.name());
			}
		}
	}
}