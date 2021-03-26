package com.impact.mods.nei.oreplugin.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DimensionHelper {

  public static String[] DimName = {
      "EndAsteroid",
      "GalacticraftCore_Moon",
      "GalacticraftMars_Asteroids",
      "GalacticraftMars_Mars",
      "GalaxySpace_BarnardC",
      "GalaxySpace_BarnardE",
      "GalaxySpace_BarnardF",
      "GalaxySpace_Callisto",
      "GalaxySpace_CentauriA",
      "GalaxySpace_Ceres",
      "GalaxySpace_Deimos",
      "GalaxySpace_Enceladus",
      "GalaxySpace_Europa",
      "GalaxySpace_Ganymede",
      "GalaxySpace_Haumea",
      "GalaxySpace_Io",
      "GalaxySpace_Kuiperbelt",
      "GalaxySpace_MakeMake",
      "GalaxySpace_Mercury",
      "GalaxySpace_Miranda",
      "GalaxySpace_Oberon",
      "GalaxySpace_Phobos",
      "GalaxySpace_Pluto",
      "GalaxySpace_Proteus",
      "GalaxySpace_TcetiE",
      "GalaxySpace_Titan",
      "GalaxySpace_Triton",
      "GalaxySpace_VegaB",
      "GalaxySpace_Venus",
      "Impact_GalacticModule_Deimos",
      "Impact_GalacticModule_Haumea",
      "Impact_GalacticModule_Makemake",
      "Impact_GalacticModule_Oberon",
      "Impact_GalacticModule_Phobos",
      "Impact_GalacticModule_Proteus",
      "Nether",
      "Overworld",
      "TheEnd",
      "Vanilla_EndAsteroids",
      "Twilight",
      "Underdark",
      "Mining_World"
  };

  public static String[] DimNameDisplayed =
      {
// first 2 letters if one word else 1 letter of every word, execpt capital letter in name, then 1rst + capital Moon = Mo, BarnardC = BC, EndAsteroid = EA
          "EA",
          "Mo",
          "As",
          "Ma",
          "BC",
          "BE",
          "BF",
          "Ca",
          "CA",
          "Ce",
          "De",
          "En",
          "Eu",
          "Ga",
          "Ha",
          "Io",
          "KB",
          "MM",
          "Me",
          "Mi",
          "Ob",
          "Ph",
          "Pl",
          "Pr",
          "TE",
          "Ti",
          "Tr",
          "VB",
          "Ve",
          "Ne",
          "Ow",
          "EN",//End = EN bc En = Encalus
          "VA",
          "TF",
          "DD",
          "Ar"
      };

  private static HashMap<String, List<String>> tooltipBuffer = new HashMap<>();

  private static List<String> computeString(String line) {
    String[] dims = line.split(",");
    for (int j = 0; j < dims.length; j++) {
      String s = dims[j];
      s = s.replaceAll(",", "");
      s = s.trim();
      for (int i = 0; i < DimNameDisplayed.length; i++) {
        if (s.equals(DimNameDisplayed[i])) {
          s = DimName[i].replaceAll("GalacticraftCore_", "").replaceAll("GalacticraftMars_", "")
              .replaceAll("GalaxySpace_", "").replaceAll("Mining_World", "Aroma World")
              .replaceAll("Vanilla_", "Vanilla ").replaceAll("Impact_GalacticModule_", "");
          
          switch (s) {
            case "Twilight":
              s = "Twilight Forrest";
              break;
            case "Underdark":
              s = "Deep Dark";
              break;
            case "EndAsteroid":
              s = "Far End Asteroids";
              break;
            case "Moon":
              s = s + " (T1)";
              break;
            case "Mars":
            case "Phobos":
            case "Deimos":
              s = s + " (T2)";
              break;
            case "Asteroids":
            case "Ceres":
            case "Europa":
            case "Ganymede":
            case "Callisto":
              s = s + " (T3)";
              break;
            case "Io":
            case "Venus":
            case "Mercury":
              s = s + " (T4)";
              break;
            case "Enceladus":
            case "Titan":
            case "Miranda":
            case "Oberon":
              s = s + " (T5)";
              break;
            case "Proteus":
            case "Triton":
              s = s + " (T6)";
              break;
            case "Pluto":
            case "Kuiperbelt":
            case "Haumea":
            case "MakeMake":
              s = s + " (T7)";
              break;
            case "Deep Dark":
            case "CentauriBb":
            case "CentauriA":
            case "VegaB":
            case "BarnardC":
            case "BarnardE":
            case "BarnardF":
            case "TcetiE":
              s = s + " (doesn't work)";
              break;
          }

          dims[j] = s;
        }
      }
    }
    return Arrays.asList(dims);
  }

  public static List<String> convertCondensedStringToToolTip(String line) {
    return tooltipBuffer.computeIfAbsent(line, (String tmp) -> computeString(line));
  }
}