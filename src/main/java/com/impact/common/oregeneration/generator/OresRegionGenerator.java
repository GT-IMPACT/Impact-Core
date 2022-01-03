package com.impact.common.oregeneration.generator;

import com.google.gson.annotations.SerializedName;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.OreVeinRandomizer;

import java.util.*;

public class OresRegionGenerator {
	
	public static int layers = 2;
	
	@SerializedName("x")
	public final int xRegion;
	@SerializedName("z")
	public final int zRegion;
	@SerializedName("dimension")
	public final int dim;
	public final Map<Integer, List<OreVeinGenerator>> veins;
	
	public OresRegionGenerator(int xRegion, int zRegion, int dim) {
		this.xRegion = xRegion;
		this.zRegion = zRegion;
		this.dim     = dim;
		this.veins   = new HashMap<>();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (o.hashCode() == hashCode()) return true;
		OresRegionGenerator region = (OresRegionGenerator) o;
		return xRegion == region.xRegion && zRegion == region.zRegion && dim == region.dim;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(xRegion, zRegion, dim);
	}
	
	public void createVeins() {
		for (int layer = 0; layer < layers; layer++) {
			List<OreVeinGenerator> layerVeins = new ArrayList<>();
			for (int x = 0; x < 8; x++) {
				for (int z = 0; z < 8; z++) {
					OreVein ore = OreVeinRandomizer.getVein(layer, dim);
					if (ore != null) {
						OreVeinGenerator vein = new OreVeinGenerator((xRegion << 3) + x, (zRegion << 3) + z, ore.idVein);
						vein.createOreChunk();
						layerVeins.add(vein);
					}
				}
			}
			veins.put(layer, layerVeins);
		}
	}
}