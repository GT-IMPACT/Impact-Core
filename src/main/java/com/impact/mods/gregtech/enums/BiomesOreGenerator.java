package com.impact.mods.gregtech.enums;

import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

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
	
	@Getter
	private final BiomeGenBase mBiome;
	@Getter
	private final List<ItemStack> mOre;
	@Getter
	private final String mName;
	@Getter
	private final int mTier;
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, Materials... materials) {
		this.mBiome = biomes;
		this.mName  = name + " Vein";
		this.mTier  = tier;
		this.mOre   = new ArrayList<>();
		Stream.of(materials).forEach(material -> this.mOre.add(material.getDust(1)));
		Impact_API.sBiomeOres.put(name, this);
	}

	public static BiomesOreGenerator getFromName(String name) {
		for (BiomesOreGenerator b : BiomesOreGenerator.values()) {
			if (b.mName.equals(name)) {
				return b;
			}
		}
		return NONE;
	}
	
	public static void generatedOres(List<ItemStack> ores, World w, int x, int z) {
		int dim = w.provider.dimensionId;
		int[] m = {dim, x, z};
		if (Impact_API.sOreInChunk.containsKey(m)) {
			ores.addAll(Impact_API.sOreInChunk.get(m).mOre);
		}
	}
	
	public static void startGenerateOres(BiomeGenBase biomes, Chunk chunk) {
		for (BiomesOreGenerator biomesOreGenerator : Impact_API.sBiomeOres.values()) {
			if (biomes.biomeName.equals(biomesOreGenerator.mBiome.biomeName)) {
				int dim = chunk.worldObj.provider.dimensionId;
				int x = chunk.xPosition;
				int z = chunk.zPosition;
				int[] m = {dim, x, z};
				Impact_API.sOreInChunk.put(m, biomesOreGenerator);
			}
		}
	}

	public static void save(Chunk chunk, NBTTagCompound nbt) {
		int dim = chunk.worldObj.provider.dimensionId;
		int x = chunk.xPosition;
		int z = chunk.zPosition;
		int[] m = {dim, x, z};
		if (!Impact_API.sOreInChunk.containsKey(m)) {
			BiomesOreGenerator biomesOreGenerator = Impact_API.sOreInChunk.get(m);
			nbt.setString("vein_name", biomesOreGenerator.mName);
		}
	}

	public static void load(Chunk chunk, NBTTagCompound nbt) {
		int dim = chunk.worldObj.provider.dimensionId;
		int x = chunk.xPosition;
		int z = chunk.zPosition;
		int[] m = {dim, x, z};
		ChunkPosition
		if (nbt.hasKey("vein_name")) {
			if (Impact_API.sOreInChunk.containsKey(m)) {
				Impact_API.sOreInChunk.replace(m, getFromName(nbt.getString("vein_name")));
			}
		} else {
			BiomeGenBase biomes = chunk.worldObj.getBiomeGenForCoords(x, z);
			startGenerateOres(biomes, chunk);
		}
	}
}