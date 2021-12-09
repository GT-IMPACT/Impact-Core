package com.impact.common.oregeneration;


import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import net.minecraftforge.common.DimensionManager;

import java.util.*;

public class OreVeinRandomizer {
	
	public static Map<Integer, Map<Integer, List<OreVein>>> resizedAllVeins = new HashMap<>();
	
	public static void resizeVeins() {
		resizedAllVeins.clear();
		for (int dim = 0; dim < DimensionManager.getIDs().length; dim++) {
			int dimensionID = DimensionManager.getIDs()[dim];
			Map<Integer, List<OreVein>> resizedLayersVeins = new HashMap<>();
			for (int layer = 0; layer < OresRegionGenerator.layers; layer++) {
				List<OreVein> oreVeinsLayer = new ArrayList<>();
				for (OreVein candidate : Impact_API.registerVeins.values()) {
					if (candidate.tierVein == layer) {
						for (int dimension : candidate.idDim) {
							if (dimension == dimensionID) oreVeinsLayer.add(candidate);
						}
					}
				}
				resizedLayersVeins.put(layer, oreVeinsLayer);
			}
			resizedAllVeins.put(dimensionID, resizedLayersVeins);
		}
	}
	
	public static OreVein getVein(int tier, int dim) {
		if (!resizedAllVeins.containsKey(dim)) return OreGenerator.empty;
		if (!resizedAllVeins.get(dim).containsKey(tier)) return OreGenerator.empty;
		double total = 0d;
		for (OreVein candidate : resizedAllVeins.get(dim).get(tier)) {
			total += candidate.maxWeight;
			candidate.currentWeight = total;
		}
		Random rand = new Random();
		double r = rand.nextDouble() * total;
		for (OreVein candidate : resizedAllVeins.get(dim).get(tier)) {
			if (candidate.currentWeight > r) {
				candidate.reduceWeight();
				return candidate;
			} else {
				candidate.increaseWeight();
			}
		}
		return null;
	}
}