package com.impact.mods.gregtech.enums;

import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum BiomesOreGenerator {
	
	IRON_VEIN("Iron Vein", BiomeGenBase.desert, 0, Materials.Iron.getDust(1), Materials.Magnetite.getDust(1)),
	COPPER_VEIN("Copper Vein", BiomeGenBase.desert, 0, Materials.Copper.getDust(1), Materials.Tetrahedrite.getDust(1)),
	;
	
	@Getter
	private final BiomeGenBase mBiome;
	@Getter
	private final ItemStack[] mOre;
	@Getter
	private final String mName;
	@Getter
	private final int mTier;
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, ItemStack... ore) {
		this.mBiome = biomes;
		this.mOre   = ore;
		this.mName  = name;
		this.mTier  = tier;
		Impact_API.sBiomeOres.put(name, this);
	}
	
	public static List<ItemStack> generatedOres(int tier, BiomeGenBase biomes) {
		List<ItemStack> ores = new ArrayList<>();
		for (BiomesOreGenerator biomesOreGenerator : Impact_API.sBiomeOres.values()) {
			if (tier == biomesOreGenerator.getMTier()
					&& biomes.biomeName.equals(biomesOreGenerator.getMBiome().biomeName)) {
				Collections.addAll(ores, biomesOreGenerator.getMOre());
			}
		}
		return ores;
	}
	
	public static void startGenerateOres(List<ItemStack> ores, int tier, BiomeGenBase biomes) {
		for (BiomesOreGenerator biomesOreGenerator : Impact_API.sBiomeOres.values()) {
			if (tier == biomesOreGenerator.getMTier() && biomes.biomeName.equals(biomesOreGenerator.getMBiome().biomeName)) {
				ores.addAll(Arrays.asList(biomesOreGenerator.getMOre()));
			}
		}
	}
}