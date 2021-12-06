package com.impact.common.oregeneration;

import com.impact.mods.gregtech.enums.OreGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OreVein {
	
	public final int xVein;
	public final int zVein;
	
	public final int xChunkFirst;
	public final int zChunkFirst;
	public final int tier;
	public final String oreGenerator;
	public final String veinName;
	public final List<OreChunk> oreChunks;
	
	public OreVein(int xVein, int zVein, String oreGenerator, int tier) {
		this.xVein        = xVein;
		this.zVein        = zVein;
		this.xChunkFirst  = xVein << 2;
		this.zChunkFirst  = zVein << 2;
		this.tier = tier;
		this.oreChunks    = new ArrayList<>();
		this.oreGenerator = oreGenerator;
		OreGenerator oreGen = OreGenerator.getFromName(oreGenerator);
		this.veinName = oreGen.mName;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (o.hashCode() == hashCode()) return true;
		OreVein region = (OreVein) o;
		return xVein == region.xVein && zVein == region.zVein && tier == region.tier;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(xVein, zVein, tier);
	}
	
	public void createOreChunk() {
		for (int x = 0; x < 4; x++) {
			for (int z = 0; z < 4; z++) {
				OreChunk oreChunk = new OreChunk(xChunkFirst + x, zChunkFirst + z, tier, oreGenerator);
				oreChunks.add(oreChunk);
			}
		}
	}
}