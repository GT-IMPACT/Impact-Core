package com.impact.mods.gregtech.enums;

import com.impact.common.oregeneration.OreChunk;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.OresRegion;
import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static com.impact.util.Utilits.is;
import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.crushed;
import static gregtech.api.enums.OrePrefixes.dust;

// TODO: 21.11.2021 add Veins
public enum OreGenerator {
	
	//Tier 0 - Overworld Ores
	
	IRON_BASIC_VEIN(0, "Iron Basic", 0, null, Iron),
	COPPER_BASIC_VEIN(1, "Copper Basic", 0, null, Copper),
	TIN_BASIC_VEIN(2, "Tin Basic", 0, null, Tin),
	COAL_BASIC_VEIN(3, "Coal Basic", 0, null, Coal, Lignite),
	REDSTONE_BASIC_VEIN(4, "Redstone Basic", 0, null, Redstone),

//	MINERALS_BASIC_VEIN("Minerals Basic", null, 0, Gypsum, Calcite), ДОБЫЧА С ПОРОДЫ
	//Tier 1 - Overworld Ores
	
	ALMANDINE_VEIN(5, "Almandine", 1, null, Almandine, Pyrope, Sapphire, GreenSapphire),
	APATITE_VEIN(6, "Apatite", 1, null, Apatite, Apatite, Phosphorus, Pyrochlore),
	BENTONITE_VEIN(7, "Bentonite", 1, null, Bentonite, Magnesite, Olivine, Glauconite),
	BERYLLIUM_VEIN(8, "Beryllium", 1, null, Beryllium, Beryllium, Emerald, Thorium),
	LIMONITE_VEIN(9, "Limonite", 1, null, BrownLimonite, YellowLimonite, BandedIron, Malachite),
	CHALCOPYRITE_VEIN(10, "Chalcopyrite", 1, null, Chalcopyrite, Iron, Pyrite, Copper),
	COAL_VEIN(11, "Coal", 1, null, Coal, Lignite, Coal, Lignite),
	GALENA_VEIN(12, "Galena", 1, null, Galena, Galena, Silver, Lead),
	GARNIERITE_VEIN(13, "Garnierite", 1, null, Garnierite, Nickel, Cobaltite, Pentlandite),
	GRAPHITE_VEIN(14, "Graphite", 1, null, Graphite, Graphite, Diamond, Coal),
	GROSSULAR_VEIN(15, "Grossular", 1, null, Grossular, Spessartine, Pyrolusite, Tantalite),
	LAZURITE_VEIN(16, "Lazurite", 1, null, Lazurite, Sodalite, Lapis, Calcite),
	MAGNETITE_VEIN(17, "Magnetite", 1, null, Magnetite, Iron, VanadiumMagnetite, Gold),
	REDSTONE_VEIN(18, "Redstone", 1, null, Redstone, Redstone, Ruby, Cinnabar),
	ROCK_SALT_VEIN(19, "Rock Salt", 1, null, RockSalt, Salt, Lepidolite, Spodumene),
	SOAPSTONE_VEIN(20, "Soapstone", 1, null, Soapstone, Talc, Glauconite, Pentlandite),
	TETRAHEDRITE_VEIN(21, "Tetrahedrite", 1, null, Tetrahedrite, Tetrahedrite, Copper, Stibnite),
	TIN_VEIN(22, "Tin", 1, null, Tin, Tin, Cassiterite, Tin),
	WULFENITE_VEIN(23, "Wulfenite", 1, null, Wulfenite, Molybdenite, Molybdenum, Powellite),
	OILSANDS_VEIN(24, "Oilsands", 1, null, new short[]{10, 10, 10, 0}, is(dust, Oilsands), is(crushed, Sulfur), is(crushed, Sulfur), is(crushed, Sulfur)),
	BAUXITE_VEIN(25, "Bauxite", 1, null, Bauxite, Aluminium, Ilmenite, Ilmenite),
	SCHEELITE_VEIN(26, "Scheelite", 1, null, Scheelite, Scheelite, Tungstate, Lithium),
	URANITE_VEIN(27, "Uranite", 1, SulfuricAcid.getFluid(100), Uraninite, Uraninite, Uranium, Uranium),
	QUARTZ_VEIN(28, "Quartz", 1, null, NetherQuartz, Barite, Quartzite, CertusQuartz),
	ZINC_VEIN(29, "Zinc", 1, null, Sphalerite, Zinc, Sulfur, Pyrite),
	PLATINUM_VEIN(30, "Platinum", 1, null, Sheldonite, Palladium, Platinum, Iridium),
	
	//Tier 2 - Space Ores
	
	
	NONE(999, "Empty", -1, null, Empty);

//
	
	public final List<ItemStack> mOre;
	public final String mName;
	public final int mTier;
	public final int id;
	public final short[] mChance;
	public final FluidStack specialFluid;
	public final short[] colorVein;
	
	OreGenerator(int id, String name, int tier, FluidStack specialFluid, Materials... materials) {
		this.id           = id;
		this.mName        = name;
		this.mTier        = tier;
		this.specialFluid = specialFluid;
		this.colorVein    = materials[0].mRGBa;
		this.mOre         = new ArrayList<>();
		switch (tier) {
			case 1:
			case 2:
			case 3:
				this.mChance = new short[]{9000, 6000, 6000, 3000};
				break;
			default:
				mChance = new short[materials.length];
				Arrays.fill(this.mChance, (short) 10000);
		}
		
		Stream.of(materials).forEach(material -> this.mOre.add(GT_OreDictUnificator.get(crushed, material, 1)));
	}
	
	OreGenerator(int id, String name, int tier, FluidStack specialFluid, short[] color, ItemStack... stacks) {
		this.id           = id;
		this.mName        = name;
		this.mTier        = tier;
		this.specialFluid = specialFluid;
		this.colorVein    = color;
		this.mOre         = new ArrayList<>();
		switch (tier) {
			case 1:
			case 2:
			case 3:
				this.mChance = new short[]{9000, 6000, 6000, 3000};
				break;
			default:
				mChance = new short[stacks.length];
				Arrays.fill(this.mChance, (short) 10000);
		}
		this.mOre.addAll(Arrays.asList(stacks));
	}
	
	OreGenerator(int id, String name, int tier, FluidStack specialFluid, short[] chance, Materials... materials) {
		this.id           = id;
		this.mName        = name;
		this.mTier        = tier;
		this.mChance      = chance;
		this.colorVein    = materials[0].mRGBa;
		this.specialFluid = specialFluid;
		this.mOre         = new ArrayList<>();
		Stream.of(materials).forEach(material -> this.mOre.add(GT_OreDictUnificator.get(crushed, material, 1)));
	}
	
	public static OreGenerator getFromName(String name) {
		for (OreGenerator b : OreGenerator.values()) {
			if (b.name().equals(name)) {
				return b;
			}
		}
		return NONE;
	}
	
	public static OreGenerator getRandomGenerator(int tier) {
		List<OreGenerator> generators = new ArrayList<>();
		generators.add(NONE);
		for (OreGenerator oreGenerator : OreGenerator.values()) {
			if (oreGenerator.mTier == tier) {
				generators.add(oreGenerator);
			}
		}
		return generators.get(new Random().nextInt(generators.size()));
	}
	
	public static int sizeChunk(Chunk chunk, int tier) {
		OreChunk oreChunk = getChunk(chunk, tier);
		if (oreChunk == null) return 0;
		return oreChunk.sizeOreChunk;
	}
	
	public static OreChunk getChunk(Chunk chunk, int tier) {
		OreChunk oreChunk = OreChunk.fromChunk(chunk);
		OreVein oreVein = getVein(chunk, tier);
		if (oreVein == null) return null;
		for (OreChunk c : oreVein.oreChunks) {
			if (c.equals(oreChunk)) {
				return c;
			}
		}
		return null;
	}
	
	public static OreChunk getChunkFromIGT(IGregTechTileEntity te, int tier) {
		Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
		return getChunk(chunk, tier);
	}
	
	public static void amountIncrease(Chunk chunk, int tier, int minus) {
		OreChunk oreChunk = getChunk(chunk, tier);
		if (oreChunk != null) {
			oreChunk.sizeOreChunk -= minus;
		}
	}
	
	public static void amountIncrease(IGregTechTileEntity te, int tier, int minus) {
		OreChunk oreChunk = getChunkFromIGT(te, tier);
		if (oreChunk != null) {
			oreChunk.sizeOreChunk -= minus;
		}
	}
	
	public static OreVein getVein(Chunk ch, int tier) {
		OreChunk chunk = OreChunk.fromChunk(ch);
		OresRegion ore = getRegions(ch);
		if (ore == null) return null;
		int xVein = chunk.xChunk >> 2;
		int zVein = chunk.zChunk >> 2;
		OreVein oreVein = new OreVein(xVein, zVein, "NONE", tier);
		for (OreVein v : ore.veins) {
			if (v.equals(oreVein)) {
				return v;
			}
		}
		return null;
	}
	
	public static OresRegion getRegions(Chunk ch) {
		OreChunk chunk = OreChunk.fromChunk(ch);
		int xReg = chunk.xChunk >> 5;
		int zReg = chunk.zChunk >> 5;
		OresRegion ore = new OresRegion(xReg, zReg);
		if (!Impact_API.regionsOres.contains(ore)) {
			ore.createVeins();
			Impact_API.regionsOres.add(ore);
			return ore;
		} else {
			for (OresRegion r : Impact_API.regionsOres) {
				if (r.equals(ore)) {
					return r;
				}
			}
		}
		return null;
	}
}