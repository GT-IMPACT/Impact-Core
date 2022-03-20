package com.impact.common.oregeneration;

import com.impact.core.Config;
import com.impact.util.Utilits;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static gregtech.api.enums.OrePrefixes.crushed;

public class OreVeinBuilder {
	private int id;
	private String name;
	private int tier;
	private double weight;
	private short[] color;
	private int[] chance;
	private FluidStack fluid;
	private List<ItemStack> ores;
	private List<Integer> idDim;
	private int[] size;
	
	public static OreVeinBuilder addVein(int id, String name) {
		OreVeinBuilder builder = new OreVeinBuilder();
		builder.id     = id;
		builder.name   = name;
		builder.tier   = 0;
		builder.color  = new short[]{0, 0, 0, 0};
		builder.fluid  = null;
		builder.chance = new int[]{90, 60, 60, 30};
		builder.ores   = new ArrayList<>();
		builder.idDim  = new ArrayList<>();
		builder.size   = new int[]{0, 0};
		return builder;
	}
	
	public OreVeinBuilder setTier(int tier) {
		this.tier = tier;
		return this;
	}
	
	public OreVeinBuilder setWeight(double weight) {
		this.weight = weight;
		return this;
	}
	
	public OreVeinBuilder setColor(short... color) {
		this.color = color;
		return this;
	}
	
	public OreVeinBuilder setColor(Materials material) {
		this.color = material.mRGBa;
		return this;
	}
	
	public OreVeinBuilder setChance(int... chance) {
		this.chance = chance;
		return this;
	}
	
	public OreVeinBuilder noChance() {
		this.chance = null;
		return this;
	}
	
	public OreVeinBuilder setDefaultChance() {
		this.chance = new int[]{90, 60, 60, 30};
		return this;
	}
	
	public OreVeinBuilder setFluid(FluidStack fluid) {
		this.fluid = fluid;
		return this;
	}
	
	public OreVeinBuilder addOres(ItemStack... ores) {
		Collections.addAll(this.ores, ores);
		return this;
	}
	
	public OreVeinBuilder addOres(Materials... ores) {
		Stream.of(ores).forEach(material -> this.ores.add(GT_OreDictUnificator.get(crushed, material, 1)));
		return this;
	}
	
	public OreVeinBuilder addOres(ItemStack ore) {
		this.ores.add(ore);
		return this;
	}
	
	public OreVeinBuilder addOres(OrePrefixes prefix, Materials material) {
		this.ores.add(Utilits.is(prefix, material));
		return this;
	}
	
	public OreVeinBuilder addDim(Integer... dim) {
		Collections.addAll(idDim, dim);
		return this;
	}
	
	public OreVeinBuilder addDim(OreGenerator.Dimensions... dim) {
		List<Integer> dims = new ArrayList<>();
		for (OreGenerator.Dimensions dimensions : dim) {
			dims.add(dimensions.id);
		}
		if (dims.contains(0)) {
			dims.add(Config.MiningWorldID);
		}
		this.idDim = dims;
		return this;
	}
	
	public OreVeinBuilder setSize(int min, int max) {
		this.size = new int[]{min, max};
		return this;
	}
	
	public OreVein end() {
		short[] fixChance;
		if (chance == null) {
			fixChance = null;
		} else {
			fixChance = new short[chance.length];
			for (int i = 0; i < chance.length; i++) {
				fixChance[i] = (short) chance[i];
			}
		}
		if (tier == 0) {
			name = "Small " + name;
		}
		return OreVein.addVein(id, name, tier, idDim, size, weight, color, fixChance, fluid, ores);
	}
}