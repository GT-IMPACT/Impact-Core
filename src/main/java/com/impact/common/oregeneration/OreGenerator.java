package com.impact.common.oregeneration;

import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.stream.Stream;

import static com.impact.util.Utilits.is;
import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.crushed;
import static gregtech.api.enums.OrePrefixes.dust;

public class OreGenerator {
	
	public static OreVein empty;
	
	public static void register() {
		//Empty
		empty = OreVeinBuilder.addVein(999, "Empty").setTier(-1).noChance().setWeight(20.0d).setColor(Empty).end();
		
		//"Minerals Basic" - Gypsum, Calcite - ДОБЫЧА С ПОРОДЫ
		//Tier 0 - OverWorld Ores
		OreVeinBuilder.addVein(0, "Iron Basic").addDim(0).setTier(0).setChance(20).setWeight(20.0d).setColor(Iron).addOres(Iron).end();
		OreVeinBuilder.addVein(1, "Copper Basic").addDim(0).setTier(0).setChance(100).setWeight(20.0d).setColor(Copper).addOres(Copper).end();
		OreVeinBuilder.addVein(2, "Tin Basic").addDim(0).setTier(0).setChance(100).setWeight(20.0d).setColor(Tin).addOres(Tin).end();
		OreVeinBuilder.addVein(3, "Coal Basic").addDim(0).setTier(0).setChance(100).setWeight(20.0d).setColor(Coal).addOres(Coal).end();
		OreVeinBuilder.addVein(4, "Redstone Basic").addDim(0).setTier(0).setChance(100).setWeight(20.0d).setColor(Redstone).addOres(Redstone).end();
		
		//Tier 1 - OverWorld Ores
		OreVeinBuilder.addVein(5, "Almandine").addDim(0).setTier(1).setWeight(5.0d).setColor(Almandine).addOres(Almandine, Pyrope, Sapphire, GreenSapphire).end();
		OreVeinBuilder.addVein(6, "Apatite").addDim(0).setTier(1).setWeight(5.0d).setColor(Apatite).addOres(Apatite, Apatite, Phosphorus, Pyrochlore).end();
		OreVeinBuilder.addVein(7, "Bentonite").addDim(0).setTier(1).setWeight(2.0d).setColor(Bentonite).addOres(Bentonite, Magnesite, Olivine, Glauconite).end();
		OreVeinBuilder.addVein(8, "Beryllium").addDim(0).setTier(1).setWeight(2.0d).setColor(Beryllium).addOres(Beryllium, Beryllium, Emerald, Thorium).end();
		OreVeinBuilder.addVein(9, "Limonite").addDim(0).setTier(1).setWeight(10.0d).setColor(Bentonite).addOres(BrownLimonite, YellowLimonite, BandedIron, Malachite).end();
		OreVeinBuilder.addVein(10, "Chalcopyrite").addDim(0).setTier(1).setWeight(10.0d).setColor(Chalcopyrite).addOres(Chalcopyrite, Iron, Pyrite, Copper).end();
		OreVeinBuilder.addVein(11, "Coal").addDim(0).setTier(1).setWeight(15.0d).setColor(Coal).addOres(Coal, Lignite, Coal, Lignite).end();
		OreVeinBuilder.addVein(12, "Galena").addDim(0).setTier(1).setWeight(7.0d).setColor(Galena).addOres(Galena, Galena, Silver, Lead).end();
		OreVeinBuilder.addVein(13, "Garnierite").addDim(0).setTier(1).setWeight(7.0d).setColor(Garnierite).addOres(Garnierite, Nickel, Cobaltite, Pentlandite).end();
		OreVeinBuilder.addVein(14, "Graphite").addDim(0).setTier(1).setWeight(2.0d).setColor(Graphite).addOres(Graphite, Graphite, Diamond, Coal).end();
		OreVeinBuilder.addVein(15, "Grossular").addDim(0).setTier(1).setWeight(3.0d).setColor(Grossular).addOres(Grossular, Spessartine, Pyrolusite, Tantalite).end();
		OreVeinBuilder.addVein(16, "Lazurite").addDim(0).setTier(1).setWeight(5.0d).setColor(Lazurite).addOres(Lazurite, Sodalite, Lapis, Calcite).end();
		OreVeinBuilder.addVein(17, "Magnetite").addDim(0).setTier(1).setWeight(8.0d).setColor(Magnetite).addOres(Magnetite, Iron, VanadiumMagnetite, Gold).end();
		OreVeinBuilder.addVein(18, "Redstone").addDim(0).setTier(1).setWeight(9.0d).setColor(Redstone).addOres(Redstone, Redstone, Ruby, Cinnabar).end();
		OreVeinBuilder.addVein(19, "Rock Salt").addDim(0).setTier(1).setWeight(6.0d).setColor(RockSalt).addOres(RockSalt, Salt, Lepidolite, Spodumene).end();
		OreVeinBuilder.addVein(20, "Tetrahedrite").addDim(0).setTier(1).setWeight(8.0d).setColor(Tetrahedrite).addOres(Tetrahedrite, Tetrahedrite, Copper, Stibnite).end();
		OreVeinBuilder.addVein(21, "Soapstone").addDim(0).setTier(1).setWeight(9.0d).setColor(Soapstone).addOres(Soapstone, Talc, Glauconite, Pentlandite).end();
		OreVeinBuilder.addVein(22, "Tin").addDim(0).setTier(1).setWeight(9.0d).setColor(Tin).addOres(Tin, Tin, Cassiterite, Tin).end();
		OreVeinBuilder.addVein(23, "Wulfenite").addDim(0).setTier(1).setWeight(1.5d).setColor(Wulfenite).addOres(Wulfenite, Molybdenite, Molybdenum, Powellite).end();
		OreVeinBuilder.addVein(24, "Oilsands").addDim(0).setTier(1).setWeight(4.0d).setColor(Oilsands).addOres(dust, Oilsands).addOres(Sulfur, Sulfur, Sulfur).end();
		OreVeinBuilder.addVein(25, "Bauxite").addDim(0).setTier(1).setWeight(3.0D).setColor(Bauxite).addOres(Bauxite, Aluminium, Ilmenite, Ilmenite).end();
		OreVeinBuilder.addVein(26, "Scheelite").addDim(0).setTier(1).setWeight(0.3d).setColor(Scheelite).addOres(Scheelite, Scheelite, Tungstate, Lithium).end();
		OreVeinBuilder.addVein(27, "Uranite").addDim(0).setTier(1).setWeight(1.5d).setColor(Uranium).addOres(Uraninite, Uraninite, Uranium, Uranium).setFluid(SulfuricAcid.getFluid(100)).end();
		OreVeinBuilder.addVein(28, "Quartz").addDim(0).setTier(1).setWeight(6.0d).setColor(NetherQuartz).addOres(NetherQuartz, Barite, Quartzite, CertusQuartz).end();
		OreVeinBuilder.addVein(29, "Zinc").addDim(0).setTier(1).setWeight(4.0d).setColor(Zinc).addOres(Sphalerite, Zinc, Sulfur, Pyrite).end();
		//Tier 2 - Space Ores
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
		int idRegion = Objects.hash(region.xRegion, region.zRegion);
		if (!Impact_API.regionsOres.containsKey(idRegion)) {
			region.createVeins();
			Impact_API.regionsOres.put(idRegion, region);
			return region;
		} else {
			return Impact_API.regionsOres.get(idRegion);
		}
	}
}