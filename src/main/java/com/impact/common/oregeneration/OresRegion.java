package com.impact.common.oregeneration;

import com.impact.mods.gregtech.enums.OreGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OresRegion {
	
	public final int xRegion;
	public final int zRegion;
	public final int xVeinFirst;
	public final int zVeinFirst;
	
	public final List<OreVein> veins;
	
	public OresRegion(int xRegion, int zRegion) {
		this.xRegion    = xRegion;
		this.zRegion    = zRegion;
		this.xVeinFirst = xRegion << 3;
		this.zVeinFirst = zRegion << 3;
		this.veins      = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (o.hashCode() == hashCode()) return true;
		OresRegion region = (OresRegion) o;
		return xRegion == region.xRegion && zRegion == region.zRegion;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(xRegion, zRegion);
	}
	
	public void createVeins() {
		for (int x = 0; x < 8; x++) {
			for (int z = 0; z < 8; z++) {
				for (int layer = 0; layer < 2; layer++) {
					OreGenerator ore = OreGenerator.getRandomGenerator(layer);
					OreVein vein = new OreVein(xVeinFirst + x, zVeinFirst + z, ore.name(), ore.mTier);
					vein.createOreChunk();
					veins.add(vein);
				}
			}
		}
	}
}