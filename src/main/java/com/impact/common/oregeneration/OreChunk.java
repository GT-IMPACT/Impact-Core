package com.impact.common.oregeneration;

import com.google.gson.annotations.SerializedName;
import com.impact.mods.gregtech.enums.OreGenerator;
import com.impact.util.Utilits;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import java.util.Objects;

public class OreChunk {
	
	@SerializedName("x")
	public final int xChunk;
	@SerializedName("z")
	public final int zChunk;
	@SerializedName("layer")
	public final int tier;
	@SerializedName("size")
	public int sizeOreChunk;
	@SerializedName("generator")
	public String oreGenerator;
	
	public OreChunk(int xChunk, int zChunk, int tier, String oreGenerator) {
		this.xChunk       = xChunk;
		this.zChunk       = zChunk;
		this.tier         = tier;
		this.sizeOreChunk = OreGenerator.getFromName(oreGenerator) == OreGenerator.NONE ? 0 : calcChunkSize(tier);
		this.oreGenerator = oreGenerator;
	}
	
	private static int calcChunkSize(int tier) {
		int max = 100_000 * ((tier + 1) << tier);
		int min = 50_000 * ((tier + 1) << tier);
		return Utilits.getRandom(min, max);
	}
	
	public static OreChunk fromChunk(Chunk chunk) {
		ChunkCoordIntPair c = chunk.getChunkCoordIntPair();
		return new OreChunk(c.chunkXPos, c.chunkZPos, -1, "NONE");
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (o.hashCode() == hashCode()) return true;
		OreChunk region = (OreChunk) o;
		return xChunk == region.xChunk && zChunk == region.zChunk;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(xChunk, zChunk);
	}
}