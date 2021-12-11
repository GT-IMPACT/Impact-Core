package com.impact.common.oregeneration.generator;

import com.google.gson.annotations.SerializedName;
import com.impact.common.oregeneration.OreVein;
import com.impact.core.Impact_API;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OreVeinGenerator {
	
	@SerializedName("id")
	public final int oreVeinID;
	@SerializedName("x")
	public final int xVein;
	@SerializedName("z")
	public final int zVein;
	@SerializedName("chunks")
	public final List<OreChunkGenerator> oreChunkGenerators;
	
	public OreVeinGenerator(int xVein, int zVein, int oreVeinID) {
		this.xVein              = xVein;
		this.zVein              = zVein;
		this.oreVeinID          = oreVeinID;
		this.oreChunkGenerators = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (o.hashCode() == hashCode()) return true;
		OreVeinGenerator region = (OreVeinGenerator) o;
		return xVein == region.xVein && zVein == region.zVein;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(xVein, zVein);
	}
	
	public void createOreChunk() {
		OreVein oreVein = Impact_API.registerVeins.get(oreVeinID);
		for (int x = 0; x < 4; x++) {
			for (int z = 0; z < 4; z++) {
				OreChunkGenerator oreChunkGenerator = new OreChunkGenerator((xVein << 2) + x, (zVein << 2) + z);
				oreChunkGenerator.setSize(oreVein);
				oreChunkGenerators.add(oreChunkGenerator);
			}
		}
	}
}