package com.impact.common.oregeneration;

import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;
import java.util.Objects;

import static com.impact.common.oregeneration.OreGenerator.Dimensions.*;
import static com.impact.common.oregeneration.OreVeinBuilder.addVein;
import static com.impact.mods.nei.impactplugin.ores.OreBuilderNEI.BuildNEIDimOres;
import static com.impact.mods.nei.impactplugin.ores.OreBuilderNEI.BuildNEIOres;
import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.dust;

public class OreGenerator {
	
	public static OreVein empty;
	
	public static void register() {
		//Empty
		empty = addVein(999, "Empty").setTier(-1).noChance().setWeight(20).setColor(Empty).end();
		
		addVein(0, "Uraninite")
				.addDim(KuiperBelt, Haumea, Ganymede, Proteus, Deimos, Makemake, Ceres, Phobos, Dimensions.Titan)
				.setSize(10, 400).setTier(1).setDefaultChance().setWeight(20)
				.setColor(Uran)
				.addOres(Uraninite, Uraninite, Uranium, Uranium)
				.end();
		addVein(1, "Scheelite")
				.addDim(Dimensions.Mars, KuiperBelt, Enceladus, Oberon, Haumea, Ganymede, Asteroids, Deimos, Pluto, Callisto,
						Triton, Makemake, Dimensions.Titan, Overworld
				)
				.setSize(10, 400).setTier(1).setDefaultChance().setWeight(10)
				.setColor(Scheelite)
				.addOres(Scheelite, Scheelite, Tungstate, Lithium)
				.end();
		addVein(2, "Orichalcum")
				.addDim(Haumea, Miranda, Triton, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(40)
				.setColor(Orichalcum)
				.addOres(Orichalcum, Tanzanite, Opal, BlueTopaz)
				.end();
		addVein(3, "Graphite")
				.addDim(KuiperBelt, Overworld, Dimensions.Mercury, Enceladus, Ganymede, Miranda, Proteus, Io, Pluto, Asteroids, Phobos, Dimensions.Titan)
				.setSize(10, 100).setTier(1).setDefaultChance().setWeight(40)
				.setColor(Graphite)
				.addOres(Graphite, Graphite, Diamond, Coal)
				.end();
		addVein(4, "Lazurite")
				.addDim(Enceladus, Overworld, Asteroids, Deimos, Ceres)
				.setSize(50, 400).setTier(1).setDefaultChance().setWeight(40)
				.setColor(Lazurite)
				.addOres(Lazurite, Sodalite, Lapis, Calcite)
				.end();
		addVein(5, "Wulfenite")
				.addDim(Overworld, Dimensions.Mercury, Proteus, Asteroids, Pluto, Dimensions.Moon, Ceres, Phobos, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setDefaultChance().setWeight(10)
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
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(40)
				.setColor(Orichalcum)
				.addOres(Orichalcum, Tanzanite, Europium, MeteoricIron)
				.end();
		addVein(8, "Sheldonite")
				.addDim(Venus, Enceladus, Haumea, Miranda, Pluto, Makemake)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(20)
				.setColor(Sheldonite)
				.addOres(Sheldonite, Osmirinigon, Chromite, MeteoricIron)
				.end();
		addVein(9, "Bentonite")
				.addDim(Overworld, Enceladus, Haumea, Asteroids, Makemake, Ceres)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(60)
				.setColor(Bentonite)
				.addOres(Bentonite, Magnesite, Olivine, Glauconite)
				.end();
		addVein(10, "Nickel")
				.addDim(KuiperBelt, Oberon, Miranda, Proteus, Pluto, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(10)
				.setColor(Nickel)
				.addOres(Nickel, Osmium, Iridium, Nickel)
				.end();
		addVein(11, "Tungsten")
				.addDim(Oberon, Proteus, Pluto, Triton, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(16)
				.setColor(Tungsten)
				.addOres(Tungsten, Silicon, DeepIron, Andradite)
				.end();
		addVein(12, "Naqlatigon")
				.addDim(Oberon, Proteus, Pluto, Triton, Dimensions.Titan, Dimensions.Mercury, Haumea, KuiperBelt)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(40)
				.setColor(Naqlatigon)
				.addOres(Naqlatigon, Naquadah, Pyrolusite, Ilmenite)
				.end();
		addVein(13, "Coal")
				.addDim(Overworld)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(80)
				.setColor(Coal)
				.addOres(Coal, Coal, Coal, Lignite)
				.end();
		addVein(14, "Grossular")
				.addDim(Overworld, Oberon, Asteroids, Io, Triton, Ceres, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(20)
				.setColor(Grossular)
				.addOres(Grossular, Spessartine, Pyrolusite, Tantalite)
				.end();
		addVein(15, "Desh")
				.addDim(Dimensions.Mars, Enceladus, Miranda, Triton, Makemake, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(30)
				.setColor(Desh)
				.addOres(Desh, Desh, Scheelite, Tungstate)
				.end();
		addVein(16, "Nickel")
				.addDim(Venus, KuiperBelt, Dimensions.Mercury, Miranda, Io, Triton, Ceres, Dimensions.Titan)
				.setSize(50, 200).setTier(1).setDefaultChance().setWeight(30)
				.setColor(Nickel)
				.addOres(Nickel, Iridium, Palladium, Platinum)
				.end();
		addVein(17, "Quartz")
				.addDim(Dimensions.Mars, Venus, Overworld, Enceladus, Proteus, Io, Dimensions.Moon, Phobos)
				.setSize(50, 400).setTier(1).setWeight(30).setDefaultChance()
				.setColor(Quartzite)
				.addOres(Quartzite, NetherQuartz, CertusQuartz, Barite)
				.end();
		addVein(18, "Pitchblende")
				.addDim(Dimensions.Mars, Venus, KuiperBelt, Oberon, Haumea, Io, Makemake, Phobos)
				.setSize(50, 400).setTier(1).setWeight(40).setDefaultChance()
				.setColor(Pitchblende)
				.addOres(Pitchblende, Pitchblende, Uraninite, Uraninite)
				.end();
		addVein(19, "Desh")
				.addDim(Haumea, Triton)
				.setSize(50, 400).setTier(1).setWeight(40).setDefaultChance()
				.setColor(Desh)
				.addOres(Desh, Europium, Quantium, Tantalite)
				.end();
		addVein(20, "Redstone")
				.addDim(Dimensions.Mars, Venus, Overworld, Dimensions.Mercury, Ganymede, Miranda)
				.setSize(50, 400).setTier(1).setWeight(60).setDefaultChance()
				.setColor(Redstone)
				.addOres(Redstone, Redstone, Ruby, Cinnabar)
				.end();
		addVein(21, "Almandine")
				.addDim(Overworld, Enceladus, Haumea, Proteus, Makemake)
				.setSize(50, 400).setTier(1).setWeight(60).setDefaultChance()
				.setColor(Almandine)
				.addOres(Almandine, Pyrope, Sapphire, GreenSapphire)
				.end();
		addVein(22, "Uranium 238")
				.addDim(Makemake)
				.setSize(50, 400).setTier(1).setWeight(20).setDefaultChance()
				.setColor(Uranium)
				.addOres(Uranium, Plutonium, Forcicium, Monazite)
				.end();
		addVein(23, "Bastnasite")
				.addDim(Venus, Enceladus, Haumea, Deimos, Io, Callisto, Triton, Makemake, Dimensions.Moon, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(30).setDefaultChance()
				.setColor(Bastnasite)
				.addOres(Bastnasite, Bastnasite, Monazite, Neodymium)
				.end();
		addVein(24, "Mithril")
				.addDim(KuiperBelt, Dimensions.Mercury, Ganymede, Miranda, Asteroids)
				.setSize(50, 400).setTier(1).setWeight(40).setDefaultChance()
				.setColor(Mithril)
				.addOres(Mithril, GarnetRed, GarnetYellow, Electrotine)
				.end();
		addVein(25, "Thorium")
				.addDim(Haumea, Pluto, Triton)
				.setSize(50, 400).setTier(1).setWeight(60).setDefaultChance()
				.setColor(Thorium)
				.addOres(Thorium, Europium, Plutonium241, Uranium235)
				.end();
		addVein(26, "Black Plutonium")
				.addDim(Haumea, Pluto, Makemake)
				.setSize(50, 400).setTier(1).setWeight(40).setDefaultChance()
				.setColor(BlackPlutonium)
				.addOres(BlackPlutonium, GarnetRed, GarnetYellow, Infuscolium)
				.end();
		addVein(27, "Garnierite")
				.addDim(Dimensions.Titan, Dimensions.Mars, Overworld, Proteus, Asteroids, Deimos, Triton, Phobos)
				.setSize(50, 400).setTier(1).setWeight(40).setDefaultChance()
				.setColor(Garnierite)
				.addOres(Garnierite, Nickel, Cobaltite, Pentlandite)
				.end();
		addVein(28, "Rutile")
				.addDim(Dimensions.Mercury, Ganymede, Miranda, Proteus, Pluto, Callisto, Asteroids, Dimensions.Moon, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(16).setDefaultChance()
				.setColor(Rutile)
				.addOres(Rutile, Chromite, Uvarovite, Perlite)
				.end();
		addVein(29, "Platinum")
				.addDim(KuiperBelt, Dimensions.Mercury, Oberon, Ganymede, Io, Pluto, Callisto, Ceres, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(10).setDefaultChance()
				.setColor(Platinum)
				.addOres(Platinum, Chromite, Sheldonite, Palladium)
				.end();
		addVein(30, "Mysterious Crystal")
				.addDim(Enceladus, Miranda, Triton)
				.setSize(50, 400).setTier(1).setWeight(16).setDefaultChance()
				.setColor(MysteriousCrystal)
				.addOres(MysteriousCrystal, Mithril, Amethyst, Lapotron)
				.end();
		addVein(31, "Diamond")
				.addDim(Enceladus, Pluto)
				.setSize(50, 400).setTier(1).setWeight(100).setDefaultChance()
				.setColor(Diamond)
				.addOres(Diamond, Bentonite, Diamond, Bentonite)
				.end();
		addVein(32, "Arsenic")
				.addDim(Dimensions.Mercury, Phobos, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(60).setDefaultChance()
				.setColor(Arsenic)
				.addOres(Arsenic, Bismuth, Antimony, Antimony)
				.end();
		addVein(33, "Chalcopyrite")
				.addDim(Overworld, Proteus, Asteroids, Callisto, Dimensions.Moon, Ceres, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(80).setDefaultChance()
				.setColor(Chalcopyrite)
				.addOres(Chalcopyrite, Iron, Pyrite, Copper)
				.end();
		addVein(34, "Vanadium")
				.addDim(Proteus, Makemake, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(60).setDefaultChance()
				.setColor(Vanadium)
				.addOres(Vanadium, Magnetite, Gold, Chrome)
				.end();
		addVein(35, "Bauxite")
				.addDim(KuiperBelt, Dimensions.Mercury, Haumea, Ganymede, Proteus, Asteroids, Pluto, Makemake, Dimensions.Moon, Phobos, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(80).setDefaultChance()
				.setColor(Bauxite)
				.addOres(Bauxite, Ilmenite, Aluminium, Ilmenite)
				.end();
		addVein(36, "Bauxite")
				.addDim(Overworld)
				.setSize(4, 50).setTier(1).setWeight(20).setDefaultChance()
				.setColor(Bauxite)
				.addOres(Bauxite, Ilmenite, Aluminium, Ilmenite)
				.end();
		addVein(37, "Soapstone")
				.addDim(Overworld, Oberon, Miranda, Ceres)
				.setSize(50, 400).setTier(1).setWeight(40).setDefaultChance()
				.setColor(Soapstone)
				.addOres(Soapstone, Talc, Glauconite, Pentlandite)
				.end();
		addVein(38, "Neutronium")
				.addDim(KuiperBelt, Makemake)
				.setSize(50, 400).setTier(1).setWeight(10).setDefaultChance()
				.setColor(Neutronium)
				.addOres(Neutronium, Adamantium, Naquadah, Titanium)
				.end();
		addVein(39, "Stibnite")
				.addDim(Oberon, Pluto, Triton, Dimensions.Moon, Ceres)
				.setSize(50, 400).setTier(1).setWeight(100).setDefaultChance()
				.setColor(Stibnite)
				.addOres(Stibnite, Tantalite, Stibnite, Tantalite)
				.end();
		addVein(40, "Gold Magnetite")
				.addDim(Dimensions.Mars, Overworld, Asteroids, Pluto, Callisto, Triton, Phobos)
				.setSize(50, 400).setTier(1).setWeight(160).setDefaultChance()
				.setColor(Gold)
				.addOres(Magnetite, Magnetite, VanadiumMagnetite, Gold)
				.end();
		addVein(41, "Brown Limonite")
				.addDim(Dimensions.Mars, Overworld, Dimensions.Mercury, Oberon, Ganymede, Pluto, Callisto, Ceres)
				.setSize(50, 400).setTier(1).setWeight(120).setDefaultChance()
				.setColor(BrownLimonite)
				.addOres(BrownLimonite, YellowLimonite, BandedIron, Malachite)
				.end();
		addVein(42, "Plutonium 239")
				.addDim(Haumea, Pluto, Triton)
				.setSize(50, 400).setTier(1).setWeight(100).setDefaultChance()
				.setColor(Plutonium)
				.addOres(Plutonium, Uranium, Plutonium, Uranium)
				.end();
		addVein(43, "Beryllium")
				.addDim(Dimensions.Mars, Venus, Overworld, Enceladus, Haumea, Pluto, Makemake, Ceres, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(30).setDefaultChance()
				.setColor(Beryllium)
				.addOres(Beryllium, Beryllium, Emerald, Thorium)
				.end();
		addVein(44, "Copper")
				.addDim(Makemake)
				.setSize(50, 400).setTier(1).setWeight(100).setDefaultChance()
				.setColor(Copper)
				.addOres(Copper, Antimony, Copper, Antimony)
				.end();
		addVein(45, "Coal")
				.addDim(Overworld)
				.setSize(50, 400).setTier(1).setWeight(160).setDefaultChance()
				.setColor(Coal)
				.addOres(Coal, Lignite, Coal, Lignite)
				.end();
		addVein(46, "Sheldonite")
				.addDim(Haumea, Ganymede, Proteus, Asteroids, Pluto, Triton, Dimensions.Moon, Overworld)
				.setSize(50, 400).setTier(1).setWeight(5).setDefaultChance()
				.setColor(Sheldonite)
				.addOres(Sheldonite, Palladium, Platinum, Iridium)
				.end();
		addVein(47, "Tetrahedrite")
				.addDim(Dimensions.Mars, Venus, KuiperBelt, Ganymede, Miranda, Asteroids, Deimos, Overworld)
				.setSize(50, 400).setTier(1).setWeight(70).setDefaultChance()
				.setColor(Tetrahedrite)
				.addOres(Tetrahedrite, Tetrahedrite, Copper, Stibnite)
				.end();
		addVein(48, "Galena")
				.addDim(Dimensions.Mars, Venus, Overworld, Oberon, Ganymede, Triton, Dimensions.Moon)
				.setSize(50, 400).setTier(1).setWeight(40).setDefaultChance()
				.setColor(Galena)
				.addOres(Galena, Galena, Silver, Lead)
				.end();
		addVein(49, "Naquadah")
				.addDim(Venus, KuiperBelt, Dimensions.Mercury, Oberon, Haumea, Pluto, Asteroids, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(30).setDefaultChance()
				.setColor(Naquadah)
				.addOres(Naquadah, Naquadah, Naquadah, Plutonium)
				.end();
		addVein(50, "Apatite")
				.addDim(Overworld)
				.setSize(50, 400).setTier(1).setWeight(60).setDefaultChance()
				.setColor(Apatite)
				.addOres(Apatite, Apatite, Phosphorus, Pyrochlore)
				.end();
		addVein(51, "Tantalite")
				.addDim(Enceladus, Miranda, Proteus, Pluto, Makemake, Dimensions.Titan)
				.setSize(50, 400).setTier(1).setWeight(10).setDefaultChance()
				.setColor(Tantalite)
				.addOres(Tantalite, Ilmenite, Tungstate, Pyrolusite)
				.end();
		addVein(52, "Chrome")
				.addDim(Europa)
				.setSize(50, 400).setTier(1).setWeight(5).setDefaultChance()
				.setColor(Chrome)
				.addOres(Chrome, Tungsten, Molybdenum, Manganese)
				.end();
		addVein(53, "Cassiterite")
				.addDim(Enceladus, Europa, Pluto, Callisto, Ceres)
				.setSize(50, 400).setTier(1).setWeight(20).setDefaultChance()
				.setColor(Cassiterite)
				.addOres(Cassiterite, Ledox, Palladium, Zinc)
				.end();
		addVein(54, "Iron Magnetite")
				.addDim(Overworld, Deimos, Io, Makemake, Ceres)
				.setSize(50, 400).setTier(1).setWeight(160).setDefaultChance()
				.setColor(Iron)
				.addOres(Magnetite, Magnetite, Iron, VanadiumMagnetite)
				.end();
		addVein(55, "Meteoric Iron")
				.addDim(Proteus, Makemake)
				.setSize(50, 400).setTier(1).setWeight(60).setDefaultChance()
				.setColor(MeteoricIron)
				.addOres(MeteoricIron, Ledox, Chromite, MeteoricIron)
				.end();
		addVein(56, "Rock Salt")
				.addDim(Dimensions.Mars, Overworld)
				.setSize(50, 400).setTier(1).setWeight(50).setDefaultChance()
				.setColor(RockSalt)
				.addOres(RockSalt, Salt, Lepidolite, Spodumene)
				.end();
		addVein(57, "Tin")
				.addDim(Venus, Overworld, Io, Asteroids, Miranda)
				.setSize(50, 400).setTier(1).setWeight(50).setDefaultChance()
				.setColor(Tin)
				.addOres(Tin, Tin, Cassiterite, Tin)
				.end();
		addVein(58, "Zinc")
				.addDim(Dimensions.Mars, Venus, Deimos, Io, Phobos, Overworld)
				.setSize(50, 400).setTier(1).setWeight(50).setDefaultChance()
				.setColor(Zinc)
				.addOres(Sphalerite, Zinc, Sulfur, Pyrite)
				.end();
		
		addVein(500, "Copper")
				.addDim(Dimensions.Mars, Overworld, Proteus, Triton, Phobos, Asteroids)
				.setSize(1, 10).setTier(0).setWeight(32).noChance()
				.setColor(Copper)
				.addOres(Copper)
				.end();
		addVein(501, "Tin")
				.addDim(Overworld, Dimensions.Mars, Deimos, Ganymede, Dimensions.Titan, Proteus, Asteroids)
				.setSize(1, 10).setTier(0).setWeight(32).noChance()
				.setColor(Tin)
				.addOres(Tin)
				.end();
		addVein(502, "Bismuth")
				.addDim(Overworld, Dimensions.Mars, Callisto, Dimensions.Mercury, Io, Proteus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Bismuth)
				.addOres(Bismuth)
				.end();
		addVein(503, "Coal")
				.addDim(Overworld)
				.setSize(1, 10).setTier(0).setWeight(24).noChance()
				.setColor(Coal)
				.addOres(Coal)
				.end();
		addVein(504, "Iron")
				.addDim(Overworld, Dimensions.Mars, Io, Callisto, Triton, Enceladus, Asteroids, Haumea, Ganymede, Miranda, Phobos, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(16).noChance()
				.setColor(Iron)
				.addOres(Iron)
				.end();
		addVein(505, "Lead")
				.addDim(Overworld, Dimensions.Mars, Venus, Dimensions.Mercury, Pluto, Triton, Asteroids, Makemake, Oberon, Ceres, Ganymede, Deimos)
				.setSize(1, 10).setTier(0).setWeight(16).noChance()
				.setColor(Lead)
				.addOres(Lead)
				.end();
		addVein(506, "Zinc")
				.addDim(Overworld, Dimensions.Mars, Io, Dimensions.Mercury, Enceladus, Haumea, Ganymede, Proteus, Dimensions.Titan )
				.setSize(1, 10).setTier(0).setWeight(12).noChance()
				.setColor(Zinc)
				.addOres(Zinc)
				.end();
		addVein(507, "Gold")
				.addDim(Overworld, Dimensions.Mars, Venus, Pluto, Callisto, Asteroids, Ceres, Miranda, Phobos, Dimensions.Mercury)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Gold)
				.addOres(Gold)
				.end();
		addVein(508, "Silver")
				.addDim(Overworld, Io, Pluto, Triton, Enceladus, Oberon, Proteus, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Silver)
				.addOres(Silver)
				.end();
		addVein(509, "Nickel")
				.addDim(Overworld, Dimensions.Mars, Venus, Dimensions.Mercury, Pluto, Makemake, Asteroids, Ceres, Deimos)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Nickel)
				.addOres(Nickel)
				.end();
		addVein(510, "Lapis")
				.addDim(Overworld, Io, Ganymede, Enceladus, Phobos, Oberon)
				.setSize(1, 10).setTier(0).setWeight(4).noChance()
				.setColor(Lapis)
				.addOres(Lapis)
				.end();
		addVein(511, "Diamond")
				.addDim(Overworld, Venus, Callisto, Triton, Enceladus, Asteroids, Oberon, Ceres, Deimos, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Diamond)
				.addOres(Diamond)
				.end();
		addVein(512, "Emerald")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Emerald)
				.addOres(Emerald)
				.end();
		addVein(513, "Ruby")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Ruby)
				.addOres(Ruby)
				.end();
		addVein(514, "Sapphire")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Sapphire)
				.addOres(Sapphire)
				.end();
		addVein(515, "Green Sapphire")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(GreenSapphire)
				.addOres(GreenSapphire)
				.end();
		addVein(516, "Olivine")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Olivine)
				.addOres(Olivine)
				.end();
		addVein(517, "Topaz")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Topaz)
				.addOres(Topaz)
				.end();
		addVein(518, "Tanzanite")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Tanzanite)
				.addOres(Tanzanite)
				.end();
		addVein(519, "Amethyst")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Amethyst)
				.addOres(Amethyst)
				.end();
		addVein(520, "Opal")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(Opal)
				.addOres(Opal)
				.end();
		addVein(521, "Blue Topaz")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(BlueTopaz)
				.addOres(BlueTopaz)
				.end();
		addVein(522, "Red Garnet")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(2).noChance()
				.setColor(GarnetRed)
				.addOres(GarnetRed)
				.end();
		addVein(523, "Yellow Garnet")
				.addDim(Overworld, Haumea, Proteus, Enceladus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(3).noChance()
				.setColor(GarnetYellow)
				.addOres(GarnetYellow)
				.end();
		addVein(524, "Iridium")
				.addDim(Venus, Io, Dimensions.Mercury, Pluto, Callisto, Triton, Enceladus, Asteroids, Oberon, Ceres, Ganymede, Proteus, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Iridium)
				.addOres(Iridium)
				.end();
		addVein(525, "Nether Quartz")
				.addDim(Overworld, Enceladus)
				.setSize(1, 10).setTier(0).setWeight(20).noChance()
				.setColor(NetherQuartz)
				.addOres(NetherQuartz)
				.end();
		addVein(526, "Saltpeter")
				.addDim(Overworld, Dimensions.Mars, Venus, Io, Ganymede, Proteus, Enceladus, Deimos)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Saltpeter)
				.addOres(Saltpeter)
				.end();
		addVein(527, "Sulfur")
				.addDim(Overworld)
				.setSize(1, 10).setTier(0).setWeight(16).noChance()
				.setColor(Sulfur)
				.addOres(Sulfur)
				.end();
		addVein(528, "Osmium")
				.addDim(Haumea, Ganymede, Miranda, Pluto, Proteus, Triton, Makemake, Dimensions.Titan, Oberon)
				.setSize(1, 10).setTier(0).setWeight(1).noChance()
				.setColor(Osmium)
				.addOres(Osmium)
				.end();
		addVein(529, "Titanium")
				.addDim(Dimensions.Mars, Venus, Io, Dimensions.Mercury, Pluto, Callisto, Triton, Enceladus, Makemake, Asteroids,
						Oberon, Haumea, Ceres, Ganymede, Miranda, Proteus, Deimos, Phobos, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(32).noChance()
				.setColor(Titanium)
				.addOres(Titanium)
				.end();
		addVein(530, "Meteoric Iron")
				.addDim(Dimensions.Mars, Venus, Dimensions.Moon, Io, Pluto, Deimos, Phobos)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(MeteoricIron)
				.addOres(MeteoricIron)
				.end();
		addVein(531, "Neutronium")
				.addDim(Makemake)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Neutronium)
				.addOres(Neutronium)
				.end();
		addVein(532, "Chromite")
				.addDim(Dimensions.Mars, Venus, Io, Dimensions.Mercury, Pluto, Callisto, Triton, Enceladus, Makemake, Asteroids,
						Oberon, Haumea, Ceres, Ganymede, Proteus, Deimos, Phobos, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Chromite)
				.addOres(Chromite)
				.end();
		addVein(533, "Tungstate")
				.addDim(Dimensions.Mars, Venus, Io, Dimensions.Mercury, Pluto, Callisto, Triton, Enceladus, Asteroids, Oberon,
						Ceres, Ganymede, Miranda, Proteus, Deimos, Phobos, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Tungstate)
				.addOres(Tungstate)
				.end();
		addVein(534, "Naquadah")
				.addDim(Venus, Io, Ceres, Dimensions.Mercury, Pluto, Proteus, Enceladus)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Naquadah)
				.addOres(Naquadah)
				.end();
		addVein(535, "Quantium")
				.addDim(Pluto, Triton)
				.setSize(1, 10).setTier(0).setWeight(6).noChance()
				.setColor(Quantium)
				.addOres(Quantium)
				.end();
		addVein(536, "Mithril")
				.addDim(Venus, Ganymede, Triton, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Mithril)
				.addOres(Mithril)
				.end();
		addVein(537, "Ledox")
				.addDim(Haumea, Dimensions.Mercury, Pluto, Callisto, Enceladus, Oberon)
				.setSize(1, 10).setTier(0).setWeight(4).noChance()
				.setColor(Ledox)
				.addOres(Ledox)
				.end();
		addVein(538, "Oriharukon")
				.addDim(Pluto, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(6).noChance()
				.setColor(Oriharukon)
				.addOres(Oriharukon)
				.end();
		addVein(539, "Meitnerium")
				.addDim(Haumea, Pluto, Makemake)
				.setSize(1, 10).setTier(0).setWeight(4).noChance()
				.setColor(Draconium)
				.addOres(Draconium)
				.end();
		addVein(540, "Desh")
				.addDim(Dimensions.Mars, Haumea, Miranda, Proteus, Callisto, Triton, Deimos, Makemake, Phobos, Dimensions.Mercury)
				.setSize(1, 10).setTier(0).setWeight(6).noChance()
				.setColor(Desh)
				.addOres(Desh)
				.end();
		addVein(541, "Deep Iron")
				.addDim(Dimensions.Mercury, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(DeepIron)
				.addOres(DeepIron)
				.end();
		addVein(542, "Graphite")
				.addDim(Venus, Io, Dimensions.Mercury, Dimensions.Titan)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Graphite)
				.addOres(Graphite)
				.end();
		addVein(543, "Forcicium")
				.addDim(Pluto, Proteus, Makemake)
				.setSize(1, 10).setTier(0).setWeight(8).noChance()
				.setColor(Forcicium)
				.addOres(Forcicium)
				.end();
		addVein(544, "Enriched Naquadah")
				.addDim(Miranda, Proteus)
				.setSize(1, 10).setTier(0).setWeight(6).noChance()
				.setColor(NaquadahEnriched)
				.addOres(NaquadahEnriched)
				.end();
		addVein(545, "Naquadria")
				.addDim(KuiperBelt)
				.setSize(1, 10).setTier(0).setWeight(4).noChance()
				.setColor(Naquadria)
				.addOres(Naquadria)
				.end();
		addVein(546, "Redstone")
				.addDim(Overworld, Dimensions.Mars, Ganymede, Dimensions.Mercury, Dimensions.Titan, Proteus, Enceladus)
				.setSize(1, 10).setTier(0).setWeight(16).noChance()
				.setColor(Redstone)
				.addOres(Redstone)
				.end();
		addVein(547, "Platinum")
				.addDim(Dimensions.Mars, Phobos, Asteroids, Ceres, Callisto, Miranda, Oberon, Makemake, Pluto, Haumea)
				.setSize(1, 10).setTier(0).setWeight(16).noChance()
				.setColor(Platinum)
				.addOres(Platinum)
				.end();
		
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