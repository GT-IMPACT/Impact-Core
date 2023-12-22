package com.impact.common.oregeneration;

import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.world.chunk.Chunk;

import java.util.Objects;

import static com.impact.addon.nei.impactplugin.ores.OreBuilderNEI.BuildNEIDimOres;
import static com.impact.addon.nei.impactplugin.ores.OreBuilderNEI.BuildNEIOres;

public class OreGenerator {
	
	public static OreVein empty;
	
	public static void register() {
		BuildNEIDimOres();
		BuildNEIOres();
	}
	
	public static int sizeChunk(Chunk chunk, int tier) {
		OreChunkGenerator oreChunkGenerator = getChunk(chunk, tier);
		if (oreChunkGenerator == null) return 0;
		return oreChunkGenerator.sizeOreChunk;
	}
	
	public static OreChunkGenerator getChunk(Chunk chunk, int tier) {
		OreChunkGenerator oreChunkGenerator = OreChunkGenerator.fromChunk(chunk);
		OreVeinGenerator oreVein = getVein(chunk, tier);
		if (oreVein == null) return null;
		for (OreChunkGenerator c : oreVein.oreChunkGenerators) {
			if (c.equals(oreChunkGenerator)) {
				return c;
			}
		}
		return null;
	}
	
	public static OreChunkGenerator getChunkFromIGT(IGregTechTileEntity te, int tier) {
		Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
		return getChunk(chunk, tier);
	}
	
	public static void amountIncrease(Chunk chunk, int tier, int minus) {
		OreChunkGenerator oreChunkGenerator = getChunk(chunk, tier);
		if (oreChunkGenerator != null) {
			oreChunkGenerator.sizeOreChunk -= minus;
			if (oreChunkGenerator.sizeOreChunk <= 0) {
				oreChunkGenerator.sizeOreChunk = 0;
			}
		}
	}
	
	public static void amountIncrease(IGregTechTileEntity te, int tier, int minus) {
		OreChunkGenerator oreChunkGenerator = getChunkFromIGT(te, tier);
		if (oreChunkGenerator != null) {
			oreChunkGenerator.sizeOreChunk -= minus;
			if (oreChunkGenerator.sizeOreChunk <= 0) {
				oreChunkGenerator.sizeOreChunk = 0;
			}
		}
	}
	
	public static OreVein getOreVein(IGregTechTileEntity te, int tier) {
		Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
		OreVeinGenerator gen = getVein(chunk, tier);
		if (gen != null) {
			if (Impact_API.registerVeins.containsKey(gen.oreVeinID)) {
				return Impact_API.registerVeins.get(gen.oreVeinID);
			}
		}
		return empty;
	}
	
	public static OreVein getOreVein(Chunk chunk, int tier) {
		OreVeinGenerator gen = getVein(chunk, tier);
		if (gen != null) {
			if (Impact_API.registerVeins.containsKey(gen.oreVeinID)) {
				return Impact_API.registerVeins.get(gen.oreVeinID);
			}
		}
		return empty;
	}
	
	public static OreVeinGenerator getVein(Chunk ch, int tier) {
		OreChunkGenerator chunk = OreChunkGenerator.fromChunk(ch);
		OresRegionGenerator ore = getRegions(ch);
		if (ore == null) return null;
		int xVein = chunk.xChunk >> 2;
		int zVein = chunk.zChunk >> 2;
		OreVeinGenerator oreVein = new OreVeinGenerator(xVein, zVein, 999);
		if (!ore.veins.containsKey(tier)) return null;
		for (OreVeinGenerator v : ore.veins.get(tier)) {
			if (v.equals(oreVein)) {
				return v;
			}
		}
		return null;
	}
	
	public static OresRegionGenerator getRegions(Chunk ch) {
		int dim = ch.worldObj.provider.dimensionId;
		OreChunkGenerator chunk = OreChunkGenerator.fromChunk(ch);
		int xReg = chunk.xChunk >> 5;
		int zReg = chunk.zChunk >> 5;
		OresRegionGenerator region = new OresRegionGenerator(xReg, zReg, dim);
		int idRegion = Objects.hash(region.xRegion, region.zRegion, dim);
		if (!Impact_API.regionsOres.containsKey(idRegion)) {
			region.createVeins();
			Impact_API.regionsOres.put(idRegion, region);
			return region;
		} else {
			return Impact_API.regionsOres.get(idRegion);
		}
	}
	
	public enum Dimensions {
		NO(-1,-1, ""),
		
		Overworld(0, 0, "Overworld"),
		
		Moon(-28, 1, "Moon"),
		
		Mars(-29, 2, "Mars"), Deimos(-2053, 2, "Deimos"), Phobos(-2052, 2, "Phobos"),
		
		Asteroids(-30, 3, "Asteroids"), Ceres(-2002, 3, "Ceres"), Ganymede(-2011, 3, "Ganymede"), Callisto(-2017, 3, "Callisto"), Europa(-2010, 3, "Europa"),
		
		Venus(-2001, 4, "Venus"), Mercury(-2000, 4, "Mercury"), Io(-2009, 4, "Io"),
		
		Miranda(-2019, 5, "Miranda"), Enceladus(-2012, 5, "Enceladus"), Titan(-2013, 5, "Titan"), Oberon(-2056, 5, "Oberon"),
		
		Proteus(-2055, 6, "Proteus"), Triton(-2016, 6, "Triton"),
		
		Makemake(-2050, 7, "Makemake"), Pluto(-2003, 7, "Pluto"), Haumea(-2051, 7, "Haumea"), KuiperBelt(-2004, 7, "Kuiper Belt");
		
		public int id;
		public int tier;
		public String name;
		
		Dimensions(int id, int tier, String name) {
			this.id = id;
			this.tier = tier;
			this.name = name;
		}
	}
}