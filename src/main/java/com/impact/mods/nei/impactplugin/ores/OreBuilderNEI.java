package com.impact.mods.nei.impactplugin.ores;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.core.Impact_API;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class OreBuilderNEI {
	
	public static List<DefaultOre> defaultOres = new ArrayList<>();
	public static List<DefaultOre> smallOres = new ArrayList<>();
	public static List<DimOre> dimDefaultOres = new ArrayList<>();
	public static List<DimOre> dimSmallOres = new ArrayList<>();
	
	public static void BuildNEIOres() {
		for (OreVein oreGenerator : Impact_API.registerVeins.values()) {
			if (oreGenerator.idVein != 999) {
				List<String> nameDims = new ArrayList<>();
				for (OreGenerator.Dimensions dimension : OreGenerator.Dimensions.values()) {
					if (dimension.id == -1) continue;
					for (int id : oreGenerator.idDim) {
						if (dimension.id == id) {
							nameDims.add(dimension.name + (" (T" + dimension.tier + ")"));
						}
					}
				}
				if (oreGenerator.tierVein == 1) {
					DefaultOre defaultOre = new DefaultOre(oreGenerator.nameVein, oreGenerator.tierVein, nameDims, oreGenerator.size, oreGenerator.specialFluid, oreGenerator.ores, oreGenerator.chanceOres);
					defaultOres.add(defaultOre);
				} else if (oreGenerator.tierVein == 0) {
					DefaultOre smallOre = new DefaultOre(oreGenerator.nameVein, oreGenerator.tierVein, nameDims, oreGenerator.size, oreGenerator.specialFluid, oreGenerator.ores, oreGenerator.chanceOres);
					smallOres.add(smallOre);
				}
			}
		}
	}
	
	public static void BuildNEIDimOres() {
		for (OreGenerator.Dimensions value : OreGenerator.Dimensions.values()) {
			if (value.id == -1) continue;
			List<OreVein> smallVeins = new ArrayList<>();
			List<OreVein> veins = new ArrayList<>();
			for (OreVein vein : Impact_API.registerVeins.values()) {
				if (vein.tierVein == 0) {
					for (int i : vein.idDim) {
						if (value.id == i) {
							smallVeins.add(vein);
						}
					}
				} else if (vein.tierVein == 1) {
					for (int i : vein.idDim) {
						if (value.id == i) {
							veins.add(vein);
						}
					}
				}
			}
			String name = value.name + " (T" + value.tier + ")";
			dimSmallOres.add(new DimOre(name, smallVeins, 0));
			dimDefaultOres.add(new DimOre(name, veins, 1));
		}
	}
	
	public static class DimOre {
		public String dimName;
		public int tier;
		public List<OreVein> veins;
		
		public DimOre(String dName, List<OreVein> veins, int tier) {
			this.dimName = dName;
			this.veins   = veins;
			this.tier    = tier;
		}
	}
	
	public static class DefaultOre {
		public String name;
		public int tier;
		public List<ItemStack> stacks;
		public short[] chance;
		public List<String> dim;
		public int[] size;
		public FluidStack specialFluid;
		
		public DefaultOre(String name, int tier, List<String> dim, int[] size, FluidStack specialFluid, List<ItemStack> stacks, short[] chance) {
			this.name         = name;
			this.tier         = tier;
			this.dim          = dim;
			this.size         = size;
			this.stacks       = stacks;
			this.chance       = chance;
			this.specialFluid = specialFluid;
		}
	}
}