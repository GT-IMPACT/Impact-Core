package com.impact.loader;

import cpw.mods.fml.common.Loader;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.item.ItemStack;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class OreDictRegister {
	
	public static void register_space_dust() {
		
		if (Loader.isModLoaded("GalaxySpace")) {
			reg_dust(
					OreDictTypes.Enceladus.name(),
					GT_ModHandler.getModItem("GalaxySpace", "item.GlowstoneDusts", 1L, 2)
			);
			reg_dust(
					OreDictTypes.Io.name(),
					GT_ModHandler.getModItem("GalaxySpace", "item.GlowstoneDusts", 1L, 1)
			);
			reg_dust(
					OreDictTypes.Pluto.name(),
					GT_ModHandler.getModItem("GalaxySpace", "item.GlowstoneDusts", 1L, 4)
			);
			reg_dust(
					OreDictTypes.Proteus.name(),
					GT_ModHandler.getModItem("GalaxySpace", "item.GlowstoneDusts", 1L, 3)
			);
			reg_dust(
					OreDictTypes.Ceres.name(),
					GT_ModHandler.getModItem("GalaxySpace", "item.GlowstoneDusts", 1L)
			);
		}
	}
	
	public static void register_space_rocks() {
		reg_rock(
				OreDictTypes.Mars.name(),
				GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, 9)
		);
		for (int i = 0; i < 3; i++) {
			reg_rock(
					OreDictTypes.Mars.name(),
					GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, i + 4)
			);
			reg_rock(
					OreDictTypes.Asteroids.name(),
					GT_ModHandler.getModItem("GalacticraftMars", "tile.asteroidsBlock", 1L, i)
			);
		}
		
		if (Loader.isModLoaded("GalaxySpace")) {
			for (int i = 0; i < 3; i++) {
				reg_rock(
						OreDictTypes.Phobos.name(),
						GT_ModHandler.getModItem("GalaxySpace", "phobosblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Io.name(),
						GT_ModHandler.getModItem("GalaxySpace", "ioblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.PlanetMercury.name(),
						GT_ModHandler.getModItem("GalaxySpace", "mercuryblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Titan.name(),
						GT_ModHandler.getModItem("GalaxySpace", "titanblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Oberon.name(),
						GT_ModHandler.getModItem("GalaxySpace", "oberonblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Miranda.name(),
						GT_ModHandler.getModItem("GalaxySpace", "mirandablocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Proteus.name(),
						GT_ModHandler.getModItem("GalaxySpace", "proteusblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Triton.name(),
						GT_ModHandler.getModItem("GalaxySpace", "tritonblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Pluto.name(),
						GT_ModHandler.getModItem("GalaxySpace", "plutoblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Pluto.name(),
						GT_ModHandler.getModItem("GalaxySpace", "plutoblocks", 1L, (i + 3))
				);
				reg_rock(
						OreDictTypes.TcetiE.name(),
						GT_ModHandler.getModItem("GalaxySpace", "tcetieblocks", 1L, i)
				);
			}
		}
		
		if (Loader.isModLoaded("GalaxySpace")) {
			reg_rock(
					OreDictTypes.Europa.name(),
					GT_ModHandler.getModItem("GalaxySpace", "europaunderwatergeyser", 1L, 0)
			);
			reg_rock(
					OreDictTypes.Europa.name(),
					GT_ModHandler.getModItem("GalaxySpace", "europageyser", 1L, 0)
			);
			reg_rock(
					OreDictTypes.Haumea.name(),
					GT_ModHandler.getModItem("GalaxySpace", "haumeablocks", 1L, 0)
			);
			reg_rock(
					OreDictTypes.CentauriA.name(),
					GT_ModHandler.getModItem("GalaxySpace", "acentauribbgrunt", 1L, 0)
			);
			reg_rock(
					OreDictTypes.CentauriA.name(),
					GT_ModHandler.getModItem("GalaxySpace", "acentauribbsubgrunt", 1L, 0)
			);
			reg_rock(
					OreDictTypes.VegaB.name(),
					GT_ModHandler.getModItem("GalaxySpace", "vegabsubgrunt", 1L, 0)
			);
			reg_rock(
					OreDictTypes.VegaB.name(),
					GT_ModHandler.getModItem("GalaxySpace", "vegabgrunt", 1L, 0)
			);
			reg_rock(
					OreDictTypes.BarnardaE.name(),
					GT_ModHandler.getModItem("GalaxySpace", "barnardaEgrunt", 1L, 0)
			);
			reg_rock(
					OreDictTypes.BarnardaE.name(),
					GT_ModHandler.getModItem("GalaxySpace", "barnardaEsubgrunt", 1L, 0)
			);
			reg_rock(
					OreDictTypes.BarnardaF.name(),
					GT_ModHandler.getModItem("GalaxySpace", "barnardaFsubgrunt", 1L, 0)
			);
			reg_rock(
					OreDictTypes.BarnardaF.name(),
					GT_ModHandler.getModItem("GalaxySpace", "barnardaFgrunt", 1L, 0)
			);
			reg_rock(
					OreDictTypes.Io.name(),
					GT_ModHandler.getModItem("GalaxySpace", "ioglowstone", 1L, 0)
			);
			reg_rock(
					OreDictTypes.Enceladus.name(),
					GT_ModHandler.getModItem("GalaxySpace", "enceladusglowstone", 1L, 0)
			);
			reg_rock(
					OreDictTypes.Pluto.name(),
					GT_ModHandler.getModItem("GalaxySpace", "plutoglowstone", 1L, 0)
			);
			reg_rock(
					OreDictTypes.Proteus.name(),
					GT_ModHandler.getModItem("GalaxySpace", "proteusglowstone", 1L, 0)
			);
			reg_rock(
					OreDictTypes.Ceres.name(),
					GT_ModHandler.getModItem("GalaxySpace", "ceresglowstone", 1L, 0)
			);
			
			for (int i = 0; i < 2; i++) {
				reg_rock(
						OreDictTypes.Deimos.name(),
						GT_ModHandler.getModItem("GalaxySpace", "deimosblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Venus.name(),
						GT_ModHandler.getModItem("GalaxySpace", "venusblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Ceres.name(),
						GT_ModHandler.getModItem("GalaxySpace", "ceresblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Europa.name(),
						GT_ModHandler.getModItem("GalaxySpace", "europagrunt", 1L, i)
				);
				reg_rock(
						OreDictTypes.Ganymede.name(),
						GT_ModHandler.getModItem("GalaxySpace", "ganymedeblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Callisto.name(),
						GT_ModHandler.getModItem("GalaxySpace", "callistoblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.Enceladus.name(),
						GT_ModHandler.getModItem("GalaxySpace", "enceladusblocks", 1L, i)
				);
				reg_rock(
						OreDictTypes.MakeMake.name(),
						GT_ModHandler.getModItem("GalaxySpace", "makemakegrunt", 1L, i)
				);
			}
		}
		
	}
	
	public static void reg_additional() {
		if (Loader.isModLoaded("GalaxySpace")) {
			for (int i = 0; i < 6; i++) {
				registerOre("cropSpace", GT_ModHandler.getModItem("GalaxySpace", "tcetiedandelions", 1L, i));
				registerOre("cropTcetiESeaweed", GT_ModHandler.getModItem("GalaxySpace", "tcetiedandelions", 1L, i));
			}
		}
		registerOre("dustFertilizer", GT_ModHandler.getModItem("IC2", "itemFertilizer", 1L, 0));
		registerOre("stickDesh", GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 1));
		registerOre("ingotDesh", GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 2));
		registerOre("oreIlmenite", GT_ModHandler.getModItem("GalacticraftMars", "tile.asteroidsBlock", 1L, 4));
		registerOre("oreDesh", GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, 2));
		registerOre("dustSilicon", GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 2));
		registerOre("blockDesh", GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, 8));
		registerOre("blockMeteoricIron", GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 12));
	}
	
	public static void register_all() {
		reg_additional();
		register_space_rocks();
		register_space_dust();
	}
	
	private static void reg_dust(String S, ItemStack I) {
		if (I != null) {
			String p = I.getDisplayName().replaceAll("Dust", "").replaceAll(" ", "").trim();
			registerOre(OreDictTypes.dust.name() + p, I);
			registerOre(OreDictTypes.dust.name() + S, I);
			registerOre(OreDictTypes.dust.name() + OreDictTypes.Space.name(), I);
		}
	}
	
	private static void reg_rock(String S, ItemStack I) {
		if (I != null) {
			registerOre(OreDictTypes.rock.name() + S, I);
			registerOre(OreDictTypes.rock.name() + OreDictTypes.Space.name(), I);
		}
		
	}
	
	public enum OreDictTypes {
		dust, rock, Space, BarnardaE, BarnardaF, Callisto, CentauriA, Ceres, Deimos, Enceladus,
		Europa, Ganymede, Haumea, Io, MakeMake, PlanetMercury, Miranda, Oberon, Phobos, Pluto,
		Proteus, TcetiE, Titan, Triton, VegaB, Venus, Mars, Asteroids
	}
}