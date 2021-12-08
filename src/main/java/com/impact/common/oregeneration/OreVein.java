package com.impact.common.oregeneration;

import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static gregtech.api.enums.OrePrefixes.crushed;

public class OreVein {
	
	public int idVein;
	public String nameVein;
	public int tierVein;
	public double currentWeight;
	public double maxWeight;
	public short[] colorVein;
	public short[] chanceOres;
	public int[] idDim;
	public FluidStack specialFluid;
	public List<ItemStack> ores;
	
	private OreVein(int id, String name, int tier, int[] dim, double weight, short[] color, short[] chance, FluidStack specialFluid, List<ItemStack> ores) {
		this.idVein        = id;
		this.idDim         = dim;
		this.nameVein      = name;
		this.tierVein      = tier;
		this.colorVein     = color;
		this.specialFluid  = specialFluid;
		this.ores          = ores;
		if (chance != null) {
			this.chanceOres = chance;
			if (this.chanceOres.length != this.ores.size()) {
				throw new ArrayIndexOutOfBoundsException("Amount of items in the Ore Vein does not correspond to amount of chances");
			}
			for (int i = 0; i < chanceOres.length; i++) {
				chanceOres[i] *= 100;
			}
		}
		this.currentWeight = weight;
		this.maxWeight     = weight;
		Impact_API.registerVeins.put(id, this);
	}
	
	public static OreVein addVein(int id, String name, int tier, int[] dim, double weight, short[] chance, FluidStack specialFluid, List<Materials> crushedMaterials) {
		List<ItemStack> ore = new ArrayList<>();
		crushedMaterials.forEach(material -> ore.add(GT_OreDictUnificator.get(crushed, material, 1)));
		return new OreVein(id, name, tier, dim, weight, crushedMaterials.get(0).mRGBa, chance, specialFluid, ore);
	}
	
	public static OreVein addVein(int id, String name, int tier, int[] dim, double weight, short chance, FluidStack specialFluid, List<Materials> crushedMaterials) {
		List<ItemStack> ore = new ArrayList<>();
		crushedMaterials.forEach(material -> ore.add(GT_OreDictUnificator.get(crushed, material, 1)));
		short[] chances = new short[ore.size()];
		Arrays.fill(chances, chance);
		return new OreVein(id, name, tier, dim, weight, crushedMaterials.get(0).mRGBa, chances, specialFluid, ore);
	}
	
	public static OreVein addVein(int id, String name, int tier, int[] dim, double weight, short[] color, short[] chance, FluidStack specialFluid, List<ItemStack> ores) {
		return new OreVein(id, name, tier, dim, weight, color, chance, specialFluid, ores);
	}
	
	public static OreVein addVein(int id, String name, int tier, int[] dim, double weight, short[] color, short chance, FluidStack specialFluid, List<ItemStack> ores) {
		short[] chances = new short[ores.size()];
		Arrays.fill(chances, chance);
		return new OreVein(id, name, tier, dim, weight, color, chances, specialFluid, ores);
	}
	
	public static OreVein addVein(int id, String name, int tier, int[] dim, double weight, short[] chance, FluidStack specialFluid, Materials... crushedMaterials) {
		List<ItemStack> ore = new ArrayList<>();
		Stream.of(crushedMaterials).forEach(material -> ore.add(GT_OreDictUnificator.get(crushed, material, 1)));
		return new OreVein(id, name, tier, dim, weight, crushedMaterials[0].mRGBa, chance, specialFluid, ore);
	}
	
	public static OreVein addVein(int id, String name, int tier, int[] dim, double weight, short chance, FluidStack specialFluid, Materials... crushedMaterials) {
		List<ItemStack> ore = new ArrayList<>();
		Stream.of(crushedMaterials).forEach(material -> ore.add(GT_OreDictUnificator.get(crushed, material, 1)));
		short[] chances = new short[ore.size()];
		Arrays.fill(chances, chance);
		return new OreVein(id, name, tier, dim, weight, crushedMaterials[0].mRGBa, chances, specialFluid, ore);
	}
	
	public static OreVein addVein(int id, String name, int tier, int[] dim, double weight, short[] color, short[] chance, FluidStack specialFluid, ItemStack... ores) {
		return new OreVein(id, name, tier, dim, weight, color, chance, specialFluid, Arrays.asList(ores));
	}
	
	public static OreVein addVein(int id, String name, int tier, int[] dim, double weight, short[] color, short chance, FluidStack specialFluid, ItemStack... ores) {
		short[] chances = new short[ores.length];
		Arrays.fill(chances, chance);
		return new OreVein(id, name, tier, dim, weight, color, chances, specialFluid, Arrays.asList(ores));
	}
	
	public void reduceWeight() {
		currentWeight -= 2.0D;
		if (currentWeight < 0.0D) {
			currentWeight = 0.0D;
		}
	}
	
	public void increaseWeight() {
		currentWeight += 1.0D;
		if (currentWeight > maxWeight) {
			currentWeight = maxWeight;
		}
	}
}