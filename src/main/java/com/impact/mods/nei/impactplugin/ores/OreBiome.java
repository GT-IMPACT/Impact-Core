package com.impact.mods.nei.impactplugin.ores;

import com.impact.mods.gregtech.enums.BiomesOreGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.List;

public class OreBiome {
	
	public static List<OreBiome> biomesOreList = new ArrayList<>();
	
	public static List<OreBiome> startNEIBiomes() {
		for (BiomesOreGenerator biomesOreGenerator : BiomesOreGenerator.values()) {
			if (!biomesOreGenerator.equals(BiomesOreGenerator.NONE)) {
				new OreBiome(biomesOreGenerator.mName, biomesOreGenerator.mTier, biomesOreGenerator.mBiome, biomesOreGenerator.mOre, biomesOreGenerator.mChance);
			}
		}
		return biomesOreList;
	}

	public String name;
	public int tier;
	public BiomeGenBase biomeGenBase;
	public List<ItemStack> stacks;
	public short[] chance;
	
	public OreBiome(String name, int tier, BiomeGenBase biomeGenBase, List<ItemStack> stacks, short[] chance) {
		this.name         = name;
		this.tier         = tier;
		this.biomeGenBase = biomeGenBase;
		this.stacks       = stacks;
		this.chance       = chance;
		if (!name.isEmpty()) biomesOreList.add(this);
	}
}