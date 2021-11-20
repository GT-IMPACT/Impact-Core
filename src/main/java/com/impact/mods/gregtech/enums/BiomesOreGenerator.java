package com.impact.mods.gregtech.enums;

import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

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
	
	;
	
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
	
	public static List<ItemStack> generatedOres(int tier, BiomeGenBase biomes) {
		List<ItemStack> ores = new ArrayList<>();
		for (BiomesOreGenerator biomesOreGenerator : Impact_API.sBiomeOres.values()) {
			if (tier == biomesOreGenerator.getMTier() && biomes.biomeName.equals(biomesOreGenerator.getMBiome().biomeName)) {
				ores.addAll(biomesOreGenerator.getMOre());
			}
		}
		return ores;
	}
	
	public static void startGenerateOres(List<ItemStack> ores, int tier, BiomeGenBase biomes) {
		for (BiomesOreGenerator biomesOreGenerator : Impact_API.sBiomeOres.values()) {
			if (tier == biomesOreGenerator.getMTier() && biomes.biomeName.equals(biomesOreGenerator.getMBiome().biomeName)) {
				ores.addAll(biomesOreGenerator.getMOre());
			}
		}
	}
}