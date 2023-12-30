package com.impact.addon.nei.impactplugin.ores;

import com.impact.common.oregeneration.OreGenerator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import space.gtimpact.virtual_world.api.VirtualAPI;
import space.gtimpact.virtual_world.api.VirtualOreVein;

import java.util.ArrayList;
import java.util.List;

public class OreBuilderNEI {
	
	public static List<DefaultOre> defaultOres = new ArrayList<>();
	public static List<DefaultOre> smallOres = new ArrayList<>();
	public static List<DimOre> dimDefaultOres = new ArrayList<>();
	public static List<DimOre> dimSmallOres = new ArrayList<>();
	
	public static void BuildNEIOres() {
//		for (VirtualOreVein vein : VirtualAPI.VIRTUAL_ORES) {
//			List<String> nameDims = new ArrayList<>();
//			for (OreGenerator.Dimensions dimension : OreGenerator.Dimensions.values()) {
//				if (dimension.id == -1) continue;
//				for (int id : vein.getDimensions()) {
//					if (dimension.id == id) {
//						nameDims.add(dimension.name + (" (T" + dimension.tier + ")"));
//					}
//				}
//			}
//			if (vein.getLayer() == 1) {
//				DefaultOre defaultOre = new DefaultOre(nameDims, vein);
//				defaultOres.add(defaultOre);
//			} else if (vein.getLayer() == 0) {
//				DefaultOre smallOre = new DefaultOre(nameDims, vein);
//				smallOres.add(smallOre);
//			}
//		}
	}
	
	public static void BuildNEIDimOres() {
//		for (OreGenerator.Dimensions value : OreGenerator.Dimensions.values()) {
//			if (value.id == -1) continue;
//			List<VirtualOreVein> smallVeins = new ArrayList<>();
//			List<VirtualOreVein> veins = new ArrayList<>();
//			for (VirtualOreVein vein : VirtualAPI.VIRTUAL_ORES) {
//				if (vein.getLayer() == 0) {
//					for (int i : vein.getDimensions()) {
//						if (value.id == i) {
//							smallVeins.add(vein);
//						}
//					}
//				} else if (vein.getLayer() == 1) {
//					for (int i : vein.getDimensions()) {
//						if (value.id == i) {
//							veins.add(vein);
//						}
//					}
//				}
//			}
//			String name = value.name + " (T" + value.tier + ")";
//			dimSmallOres.add(new DimOre(name, smallVeins, 0));
//			dimDefaultOres.add(new DimOre(name, veins, 1));
//		}
	}
	
	public static class DimOre {
		public String dimName;
		public int tier;
		public List<VirtualOreVein> veins;
		
		public DimOre(String dName, List<VirtualOreVein> veins, int tier) {
			this.dimName = dName;
			this.veins   = veins;
			this.tier    = tier;
		}
	}
	
	public static class DefaultOre {
		public List<String> dims;
		public VirtualOreVein vein;
		
		public DefaultOre(List<String> dims, VirtualOreVein vein) {
			this.dims = dims;
			this.vein = vein;
		}
	}
}