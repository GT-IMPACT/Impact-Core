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
	
	public OreChunkGenerator(int xChunk, int zChunk) {
		this.xChunk       = xChunk;
		this.zChunk       = zChunk;
		this.sizeOreChunk = 0;
	}
	
	public void setSize(OreVein oreVein) {
		int max = oreVein.size[0] * 1000;
		int min = oreVein.size[1] * 1000;
		this.sizeOreChunk = Utilits.getRandom(min, max);
	}
	
	public static OreChunkGenerator fromChunk(Chunk chunk) {
		ChunkCoordIntPair c = chunk.getChunkCoordIntPair();
		return new OreChunkGenerator(c.chunkXPos, c.chunkZPos);
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