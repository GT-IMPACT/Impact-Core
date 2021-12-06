package com.impact.mods.nei.impactplugin.ores;

import com.impact.mods.gregtech.enums.OreGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class OreBiome {
	
	public static List<OreBiome> biomesOreList = new ArrayList<>();
	
	public static List<OreBiome> startNEIBiomes() {
		for (OreGenerator oreGenerator : OreGenerator.values()) {
			if (!oreGenerator.equals(OreGenerator.NONE)) {
				new OreBiome(oreGenerator.mName, oreGenerator.mTier, oreGenerator.specialFluid, null, oreGenerator.mOre, oreGenerator.mChance);
			}
		}
		return biomesOreList;
	}

	public String name;
	public int tier;
	public BiomeGenBase biomeGenBase;
	public List<ItemStack> stacks;
	public short[] chance;
	public FluidStack specialFluid;
	
	public OreBiome(String name, int tier, FluidStack specialFluid, BiomeGenBase biomeGenBase, List<ItemStack> stacks, short[] chance) {
		this.name         = name;
		this.tier         = tier;
		this.biomeGenBase = biomeGenBase;
		this.stacks       = stacks;
		this.chance       = chance;
		this.specialFluid = specialFluid;
		if (!name.isEmpty()) biomesOreList.add(this);
	}
}