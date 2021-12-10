package com.impact.common.oregeneration.generator;

import com.google.gson.annotations.SerializedName;
import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.util.Utilits;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import java.util.Objects;

public class OreChunkGenerator {
	
	@SerializedName("x")
	public final int xChunk;
	@SerializedName("z")
	public final int zChunk;
	@SerializedName("size")
	public int sizeOreChunk;
	
	public OreChunkGenerator(int xChunk, int zChunk, int tier) {
		this.xChunk       = xChunk;
		this.zChunk       = zChunk;
		this.sizeOreChunk = calcChunkSize(tier);
	}
	
	private static int calcChunkSize(int tier) {
		int max = 100_000 * ((tier + 1) << tier);
		int min = 50_000 * ((tier + 1) << tier);
		return Utilits.getRandom(min, max);
	}
	
	public static OreChunkGenerator fromChunk(Chunk chunk) {
		ChunkCoordIntPair c = chunk.getChunkCoordIntPair();
		return new OreChunkGenerator(c.chunkXPos, c.chunkZPos, -1);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (o.hashCode() == hashCode()) return true;
		OreChunkGenerator region = (OreChunkGenerator) o;
		return xChunk == region.xChunk && zChunk == region.zChunk;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(xChunk, zChunk);
	}
}