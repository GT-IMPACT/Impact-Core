package com.impact.common.oregeneration;

import com.google.gson.annotations.SerializedName;
import com.impact.mods.gregtech.enums.OreGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OreVein {
	
	@SerializedName("name")
	public final String veinName;
	@SerializedName("x")
	public final int xVein;
	@SerializedName("z")
	public final int zVein;
	@SerializedName("layer")
	public final int tier;
	@SerializedName("generator")
	public final String oreGenerator;
	@SerializedName("chunks")
	public final List<OreChunk> oreChunks;
	
	public OreVein(int xVein, int zVein, String oreGenerator, int tier) {
		this.xVein        = xVein;
		this.zVein        = zVein;
		this.tier         = tier;
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
				OreChunk oreChunk = new OreChunk((xVein << 2) + x, (zVein << 2) + z, tier, oreGenerator);
				oreChunks.add(oreChunk);
			}
		}
	}
}