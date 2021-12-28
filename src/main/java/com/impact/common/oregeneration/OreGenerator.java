package com.impact.common.oregeneration;

import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import java.util.Objects;

import static com.impact.common.oregeneration.OreGenerator.Dimensions.*;
import static com.impact.common.oregeneration.OreVeinBuilder.addVein;
import static com.impact.core.Impact_API.worldDimensions;
import static com.impact.mods.nei.impactplugin.ores.OreBuilderNEI.BuildNEIDimOres;
import static com.impact.mods.nei.impactplugin.ores.OreBuilderNEI.BuildNEIOres;
import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.dust;

public class OreGenerator {
	
	public static OreVein empty;
	
	public static void register() {
		//Empty
		empty = addVein(999, "Empty").setTier(-1).noChance().setWeight(20.0d).setColor(Empty).end();
		
		addVein(0, "Uraninite")
				.addDim(KuiperBelt, Haumea, Ganymede, Proteus, Deimos, Makemake, Ceres, Phobos, Dimensions.Titan)
				.setSize(10, 400).setTier(1).setChance(100, 80, 60, 40).setWeight(20)
				.setColor(Uran)
				.addOres(Uraninite, Uraninite, Uranium, Uranium)
				.end();
		addVein(1, "Scheelite")
				.addDim(Dimensions.Mars, KuiperBelt, Enceladus, Oberon, Haumea, Ganymede, Asteroids, Deimos, Pluto, Callisto,
						Triton, Makemake, Dimensions.Titan, Overworld
				)
				.setSize(10, 400).setTier(1).setChance(100, 80, 60, 40).setWeight(10)
				.setColor(Scheelite)
				.addOres(Scheelite, Scheelite, Tungstate, Lithium)
				.end();
		addVein(2, "Orichalcum")
				.addDim(Haumea, Miranda, Triton, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(40)
				.setColor(Orichalcum)
				.addOres(Orichalcum, Tanzanite, Opal, BlueTopaz)
				.end();
		addVein(3, "Graphite")
				.addDim(KuiperBelt, Overworld, Dimensions.Mercury, Enceladus, Ganymede, Miranda, Proteus, Io, Pluto, Asteroids, Phobos, Dimensions.Titan)
				.setSize(10, 100).setTier(1).setChance(100, 80, 60, 40).setWeight(40)
				.setColor(Graphite)
				.addOres(Graphite, Graphite, Diamond, Coal)
				.end();
		addVein(4, "Lazurite")
				.addDim(Enceladus, Overworld, Asteroids, Deimos, Ceres)
				.setSize(50, 400).setTier(1).setChance(100, 80, 60, 40).setWeight(40)
				.setColor(Lazurite)
				.addOres(Lazurite, Sodalite, Lapis, Calcite)
				.end();
		addVein(5, "Wulfenite")
				.addDim(Overworld, Dimensions.Mercury, Proteus, Asteroids, Pluto, Dimensions.Moon, Ceres, Phobos, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setChance(100, 80, 60, 40).setWeight(10)
				.setColor(Wulfenite)
				.addOres(Wulfenite, Molybdenite, Molybdenum, Powellite)
				.end();
		addVein(6, "Oilsands")
				.addDim(Overworld, Europa)
				.setSize(200, 400).setTier(1).setWeight(40)
				.setColor(Oilsands)
				.addOres(dust, Oilsands).addOres(Sulfur, Sulfur, Sulfur)
				.end();
		addVein(7, "Orichalcum")
				.addDim(Haumea, Miranda, Pluto, Makemake, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(40)
				.setColor(Orichalcum)
				.addOres(Orichalcum, Tanzanite, Europium, MeteoricIron)
				.end();
		addVein(8, "Sheldonite")
				.addDim(Venus, Enceladus, Haumea, Miranda, Pluto, Makemake, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(20)
				.setColor(Sheldonite)
				.addOres(Sheldonite, Osmirinigon, Chromite, MeteoricIron)
				.end();
		addVein(9, "Bentonite")
				.addDim(Overworld, Enceladus, Haumea, Asteroids, Makemake, Ceres)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(60)
				.setColor(Bentonite)
				.addOres(Bentonite, Magnesite, Olivine, Glauconite)
				.end();
		addVein(10, "Nickel")
				.addDim(KuiperBelt, Oberon, Miranda, Proteus, Pluto, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(10)
				.setColor(Nickel)
				.addOres(Nickel, Osmium, Iridium, Nickel)
				.end();
		addVein(11, "Tungsten")
				.addDim(Oberon, Proteus, Pluto, Triton, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(16)
				.setColor(Tungsten)
				.addOres(Tungsten, Silicon, DeepIron, Andradite)
				.end();
		addVein(12, "Naqlatigon")
				.addDim(Oberon, Proteus, Pluto, Triton, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(40)
				.setColor(Naqlatigon)
				.addOres(Naqlatigon, Naquadah, Pyrolusite, Ilmenite)
				.end();
		addVein(13, "Coal")
				.addDim(Overworld)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(80)
				.setColor(Coal)
				.addOres(Coal, Coal, Coal, Lignite)
				.end();
		addVein(14, "Grossular")
				.addDim(Overworld, Oberon, Asteroids, Io, Triton, Ceres, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(20)
				.setColor(Grossular)
				.addOres(Grossular, Spessartine, Pyrolusite, Tantalite)
				.end();
		addVein(15, "Desh")
				.addDim(Dimensions.Mars, Enceladus, Miranda, Triton, Makemake, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(30)
				.setColor(Desh)
				.addOres(Desh, Desh, Scheelite, Tungstate)
				.end();
		addVein(16, "Nickel")
				.addDim(Venus, KuiperBelt, Dimensions.Mercury, Miranda, Io, Triton, Ceres, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setChance(100, 80, 60, 40).setWeight(30)
				.setColor(Nickel)
				.addOres(Nickel, Iridium, Palladium, Platinum)
				.end();
		addVein(17, "Quartz")
				.addDim(Dimensions.Mars, Venus, Overworld, Enceladus, Proteus, Io, Dimensions.Moon)
				.setSize(50, 400).setTier(1).setWeight(30).setChance(100, 80, 60, 40)
				.setColor(Quartzite)
				.addOres(Quartzite, NetherQuartz, CertusQuartz, Barite)
				.end();
		addVein(18, "Pitchblende")
				.addDim(Dimensions.Mars, Venus, KuiperBelt, Oberon, Haumea, Io, Makemake, Phobos)
				.setSize(50, 400).setTier(1).setWeight(40).setChance(100, 80, 60, 40)
				.setColor(Pitchblende)
				.addOres(Pitchblende, Pitchblende, Uraninite, Uraninite)
				.end();
		addVein(19, "Desh")
				.addDim(Haumea, Triton)
				.setSize(50, 400).setTier(1).setWeight(40).setChance(100, 80, 60, 40)
				.setColor(Desh)
				.addOres(Desh, Europium, Quantium, Tantalite)
				.end();
		addVein(20, "Redstone")
				.addDim(Dimensions.Mars, Venus, Overworld, Dimensions.Mercury, Ganymede, Miranda)
				.setSize(50, 400).setTier(1).setWeight(60).setChance(100, 80, 60, 40)
				.setColor(Redstone)
				.addOres(Redstone, Redstone, Ruby, Cinnabar)
				.end();
		addVein(21, "Almandine")
				.addDim(Overworld, Enceladus, Haumea, Proteus, Makemake)
				.setSize(50, 400).setTier(1).setWeight(60).setChance(100, 80, 60, 40)
				.setColor(Almandine)
				.addOres(Almandine, Pyrope, Sapphire, GreenSapphire)
				.end();
		addVein(22, "Uranium 238")
				.addDim(Makemake)
				.setSize(50, 400).setTier(1).setWeight(20).setChance(100, 80, 60, 40)
				.setColor(Uranium)
				.addOres(Uranium, Plutonium, Forcicium, Monazite)
				.end();
		addVein(23, "Bastnasite")
				.addDim(Venus, Enceladus, Haumea, Deimos, Io, Callisto, Triton, Makemake, Dimensions.Moon, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(30).setChance(100, 80, 60, 40)
				.setColor(Bastnasite)
				.addOres(Bastnasite, Bastnasite, Monazite, Neodymium)
				.end();
		addVein(24, "Mithril")
				.addDim(KuiperBelt, Dimensions.Mercury, Ganymede, Miranda, Asteroids)
				.setSize(50, 400).setTier(1).setWeight(40).setChance(100, 80, 60, 40)
				.setColor(Mithril)
				.addOres(Mithril, GarnetRed, GarnetYellow, Electrotine)
				.end();
		addVein(25, "Thorium")
				.addDim(Haumea, Pluto, Triton)
				.setSize(50, 400).setTier(1).setWeight(60).setChance(100, 80, 60, 40)
				.setColor(Thorium)
				.addOres(Thorium, Europium, Plutonium241, Uranium235)
				.end();
		addVein(26, "Black Plutonium")
				.addDim(Haumea, Pluto, Triton)
				.setSize(50, 400).setTier(1).setWeight(40).setChance(100, 80, 60, 40)
				.setColor(BlackPlutonium)
				.addOres(BlackPlutonium, GarnetRed, GarnetYellow, Infuscolium)
				.end();
		addVein(27, "Garnierite")
				.addDim(Dimensions.Titan, Dimensions.Mars, Overworld, Proteus, Asteroids, Deimos, Triton, Phobos)
				.setSize(50, 400).setTier(1).setWeight(40).setChance(100, 80, 60, 40)
				.setColor(Garnierite)
				.addOres(Garnierite, Nickel, Cobaltite, Pentlandite)
				.end();
		addVein(28, "Rutile")
				.addDim(Dimensions.Mercury, Ganymede, Miranda, Proteus, Pluto, Callisto, Asteroids, Dimensions.Moon, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(16).setChance(100, 80, 60, 40)
				.setColor(Rutile)
				.addOres(Rutile, Chromite, Uvarovite, Perlite)
				.end();
		addVein(29, "Platinum")
				.addDim(KuiperBelt, Dimensions.Mercury, Oberon, Ganymede, Io, Pluto, Callisto, Ceres, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(10).setChance(100, 80, 60, 40)
				.setColor(Platinum)
				.addOres(Platinum, Chromite, Sheldonite, Palladium)
				.end();
		addVein(30, "Mysterious Crystal")
				.addDim(Enceladus, Miranda, Triton)
				.setSize(50, 400).setTier(1).setWeight(16).setChance(100, 80, 60, 40)
				.setColor(MysteriousCrystal)
				.addOres(MysteriousCrystal, Mithril, Amethyst, Lapotron)
				.end();
		addVein(31, "Diamond")
				.addDim(Enceladus, Pluto)
				.setSize(50, 400).setTier(1).setWeight(100).setChance(100, 80, 60, 40)
				.setColor(Diamond)
				.addOres(Diamond, Bentonite, Diamond, Bentonite)
				.end();
		addVein(32, "Arsenic")
				.addDim(Dimensions.Mercury, Phobos, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(60).setChance(100, 80, 60, 40)
				.setColor(Arsenic)
				.addOres(Arsenic, Bismuth, Antimony, Antimony)
				.end();
		addVein(33, "Chalcopyrite")
				.addDim(Overworld, Proteus, Asteroids, Callisto, Dimensions.Moon, Ceres, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(80).setChance(100, 80, 60, 40)
				.setColor(Chalcopyrite)
				.addOres(Chalcopyrite, Iron, Pyrite, Copper)
				.end();
		addVein(34, "Vanadium")
				.addDim(Proteus, Makemake, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(60).setChance(100, 80, 60, 40)
				.setColor(Vanadium)
				.addOres(Vanadium, Magnetite, Gold, Chrome)
				.end();
		addVein(35, "Bauxite")
				.addDim(KuiperBelt, Dimensions.Mercury, Haumea, Ganymede, Proteus, Asteroids, Pluto, Makemake, Dimensions.Moon, Phobos, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(80).setChance(100, 80, 60, 40)
				.setColor(Bauxite)
				.addOres(Bauxite, Ilmenite, Aluminium, Ilmenite)
				.end();
		addVein(36, "Bauxite")
				.addDim(Overworld)
				.setSize(4, 50).setTier(1).setWeight(20).setChance(100, 80, 60, 40)
				.setColor(Bauxite)
				.addOres(Bauxite, Ilmenite, Aluminium, Ilmenite)
				.end();
		addVein(37, "Soapstone")
				.addDim(Overworld, Oberon, Miranda, Ceres)
				.setSize(50, 400).setTier(1).setWeight(40).setChance(100, 80, 60, 40)
				.setColor(Soapstone)
				.addOres(Soapstone, Talc, Glauconite, Pentlandite)
				.end();
		addVein(38, "Neutronium")
				.addDim(KuiperBelt, Makemake)
				.setSize(50, 400).setTier(1).setWeight(10).setChance(100, 80, 60, 40)
				.setColor(Neutronium)
				.addOres(Neutronium, Adamantium, Naquadah, Titanium)
				.end();
		addVein(39, "Stibnite")
				.addDim(Oberon, Pluto, Triton, Dimensions.Moon, Ceres)
				.setSize(50, 400).setTier(1).setWeight(100).setChance(100, 80, 60, 40)
				.setColor(Stibnite)
				.addOres(Stibnite, Tantalite, Stibnite, Tantalite)
				.end();
		addVein(40, "Magnetite")
				.addDim(Dimensions.Mars, Overworld, Asteroids, Pluto, Callisto, Triton, Phobos)
				.setSize(50, 400).setTier(1).setWeight(160).setChance(100, 80, 60, 40)
				.setColor(Gold)
				.addOres(Magnetite, Magnetite, VanadiumMagnetite, Gold)
				.end();
		addVein(41, "Brown Limonite")
				.addDim(Dimensions.Mars, Overworld, Dimensions.Mercury, Oberon, Ganymede, Pluto, Callisto, Ceres)
				.setSize(50, 400).setTier(1).setWeight(120).setChance(100, 80, 60, 40)
				.setColor(BrownLimonite)
				.addOres(BrownLimonite, YellowLimonite, BandedIron, Malachite)
				.end();
		addVein(42, "Plutonium 239")
				.addDim(Haumea, Pluto, Triton)
				.setSize(50, 400).setTier(1).setWeight(100).setChance(100, 80, 60, 40)
				.setColor(Plutonium)
				.addOres(Plutonium, Uranium, Plutonium, Uranium)
				.end();
		addVein(43, "Beryllium")
				.addDim(Dimensions.Mars, Venus, Overworld, Enceladus, Haumea, Pluto, Makemake, Ceres, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(30).setChance(100, 80, 60, 40)
				.setColor(Beryllium)
				.addOres(Beryllium, Beryllium, Emerald, Thorium)
				.end();
		addVein(44, "Copper")
				.addDim(Makemake)
				.setSize(50, 400).setTier(1).setWeight(100).setChance(100, 80, 60, 40)
				.setColor(Copper)
				.addOres(Copper, Antimony, Copper, Antimony)
				.end();
		addVein(45, "Coal")
				.addDim(Overworld)
				.setSize(50, 400).setTier(1).setWeight(160).setChance(100, 80, 60, 40)
				.setColor(Coal)
				.addOres(Coal, Lignite, Coal, Lignite)
				.end();
		addVein(46, "Sheldonite")
				.addDim(Haumea, Ganymede, Proteus, Asteroids, Pluto, Triton, Dimensions.Moon, Overworld)
				.setSize(50, 400).setTier(1).setWeight(5).setChance(100, 80, 60, 40)
				.setColor(Sheldonite)
				.addOres(Sheldonite, Palladium, Platinum, Iridium)
				.end();
		addVein(47, "Tetrahedrite")
				.addDim(Dimensions.Mars, Venus, KuiperBelt, Ganymede, Miranda, Asteroids, Deimos, Overworld)
				.setSize(50, 400).setTier(1).setWeight(70).setChance(100, 80, 60, 40)
				.setColor(Tetrahedrite)
				.addOres(Tetrahedrite, Tetrahedrite, Copper, Stibnite)
				.end();
		addVein(48, "Galena")
				.addDim(Dimensions.Mars, Venus, Overworld, Oberon, Ganymede, Triton, Dimensions.Moon)
				.setSize(50, 400).setTier(1).setWeight(40).setChance(100, 80, 60, 40)
				.setColor(Galena)
				.addOres(Galena, Galena, Silicon, Lead)
				.end();
		addVein(49, "Naquadah")
				.addDim(Venus, KuiperBelt, Dimensions.Mercury, Oberon, Haumea, Pluto, Asteroids, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(30).setChance(100, 80, 60, 40)
				.setColor(Naquadah)
				.addOres(Naquadah, Naquadah, Naquadah, Plutonium)
				.end();
		addVein(50, "Apatite")
				.addDim(Overworld)
				.setSize(50, 400).setTier(1).setWeight(60).setChance(100, 80, 60, 40)
				.setColor(Apatite)
				.addOres(Apatite, Apatite, Phosphorus, Pyrochlore)
				.end();
		addVein(51, "Tantalite")
				.addDim(Enceladus, Miranda, Proteus, Pluto, Makemake, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(10).setChance(100, 80, 60, 40)
				.setColor(Tantalite)
				.addOres(Tantalite, Ilmenite, Tungstate, Pyrolusite)
				.end();
		addVein(52, "Chrome")
				.addDim(Europa)
				.setSize(50, 400).setTier(1).setWeight(5).setChance(100, 80, 60, 40)
				.setColor(Chrome)
				.addOres(Chrome, Tungsten, Molybdenum, Manganese)
				.end();
		addVein(53, "Cassiterite")
				.addDim(Enceladus, Europa, Pluto, Callisto, Ceres)
				.setSize(50, 400).setTier(1).setWeight(20).setChance(100, 80, 60, 40)
				.setColor(Cassiterite)
				.addOres(Cassiterite, Ledox, Palladium, Zinc)
				.end();
		addVein(54, "Magnetite")
				.addDim(Overworld, Deimos, Io, Makemake, Ceres)
				.setSize(50, 400).setTier(1).setWeight(160).setChance(100, 80, 60, 40)
				.setColor(Iron)
				.addOres(Magnetite, Magnetite, Iron, VanadiumMagnetite)
				.end();
		addVein(55, "Meteoric Iron")
				.addDim(Proteus, Makemake)
				.setSize(50, 400).setTier(1).setWeight(60).setChance(100, 80, 60, 40)
				.setColor(MeteoricIron)
				.addOres(MeteoricIron, Ledox, Chromite, MeteoricIron)
				.end();
		addVein(56, "Rock Salt")
				.addDim(Dimensions.Mars, Overworld)
				.setSize(50, 400).setTier(1).setWeight(50).setChance(100, 80, 60, 40)
				.setColor(RockSalt)
				.addOres(RockSalt, Salt, Lepidolite, Spodumene)
				.end();
		addVein(57, "Tin")
				.addDim(Venus, Overworld, Io, Asteroids, Miranda)
				.setSize(50, 400).setTier(1).setWeight(50).setChance(100, 80, 60, 40)
				.setColor(Tin)
				.addOres(Tin, Tin, Cassiterite, Tin)
				.end();
		addVein(59, "Zinc")
				.addDim(Dimensions.Mars, Venus, Deimos, Io, Phobos, Overworld)
				.setSize(50, 400).setTier(1).setWeight(50).setChance(100, 80, 60, 40)
				.setColor(Zinc)
				.addOres(Sphalerite, Zinc, Sulfur, Pyrite)
				.end();
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
	
	public enum Dimensions {
		Overworld(0, 0),
		
		Moon(-28, 1),
		
		Mars(-29, 2), Deimos(-2053, 2), Phobos(-2052, 2),
		
		Asteroids(-30, 3), Ceres(-2002, 3), Ganymede(-2011, 3), Callisto(-2017, 3), Europa(-2010, 3),
		
		Venus(-2001, 4), Mercury(-2000, 4), Io(-2009, 4),
		
		Miranda(-2019, 5), Enceladus(-2012, 5), Titan(-2013, 5), Oberon(-2056, 5),
		
		Proteus(-2055, 6), Triton(-2016, 6),
		
		Makemake(-2050, 7), Pluto(-2003, 7), Haumea(-2051, 7), KuiperBelt(-2004, 7);
		
		public int id;
		public int tier;
		
		Dimensions(int id, int tier) {
			this.id = id;
			this.tier = tier;
		}
	}
}