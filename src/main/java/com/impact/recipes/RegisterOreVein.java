package com.impact.recipes;

import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

import com.impact.core.Impact_API;
import com.impact.impact;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

public class RegisterOreVein implements Runnable {

  public static final Map<BiomeGenBase, Integer> mBiomeToInteger = new HashMap<>();
  public static final Map<Integer, BiomeGenBase> mIntegerToBiome = new HashMap<>();

  public static final GT_Recipe.GT_Recipe_Map sVeinOres = new GT_Recipe.GT_Recipe_Map(
      new HashSet<>(1000),
      "impact.recipe.veinores",
      "Vein Ores Helper",
      null,
      RES_PATH_GUI + "basic/Default",
      6, 9, 0, 0,
      1, E, 1, E, false, false
  );
  public static BiomeGenBase[] mAllBiomes = new BiomeGenBase[]{
      BiomeGenBase.ocean,
      BiomeGenBase.plains,
      BiomeGenBase.desert,
      BiomeGenBase.extremeHills,
      BiomeGenBase.forest,
      BiomeGenBase.taiga,
      BiomeGenBase.swampland,
      BiomeGenBase.river,
      BiomeGenBase.frozenOcean,
      BiomeGenBase.frozenRiver,
      BiomeGenBase.icePlains,
      BiomeGenBase.iceMountains,
      BiomeGenBase.beach,
      BiomeGenBase.desertHills,
      BiomeGenBase.forestHills,
      BiomeGenBase.taigaHills,
      BiomeGenBase.extremeHillsEdge,
      BiomeGenBase.jungle,
      BiomeGenBase.jungleHills,
      BiomeGenBase.jungleEdge,
      BiomeGenBase.deepOcean,
      BiomeGenBase.stoneBeach,
      BiomeGenBase.coldBeach,
      BiomeGenBase.birchForest,
      BiomeGenBase.birchForestHills,
      BiomeGenBase.roofedForest,
      BiomeGenBase.coldTaiga,
      BiomeGenBase.coldTaigaHills,
      BiomeGenBase.megaTaiga,
      BiomeGenBase.megaTaigaHills,
      BiomeGenBase.extremeHillsPlus,
      BiomeGenBase.savanna,
      BiomeGenBase.savannaPlateau,
      BiomeGenBase.mesa,
      BiomeGenBase.mesaPlateau_F,
      BiomeGenBase.mesaPlateau
  };

  public static ItemStack typeOre(int aMeta) {
    return GT_ModHandler.getModItem("gregtech", "gt.blockores", 1, aMeta);
  }

  public void registerOres() {
    addRegisterOre(BiomeGenBase.ocean, typeOre(29), typeOre(48), typeOre(817), typeOre(534),
        typeOre(943));
    addRegisterOre(BiomeGenBase.plains, typeOre(944), typeOre(502), typeOre(503), typeOre(504),
        typeOre(810));
    addRegisterOre(BiomeGenBase.desert, typeOre(86), typeOre(902), typeOre(904));
    addRegisterOre(BiomeGenBase.extremeHills, typeOre(57), typeOre(89), typeOre(827));
  }

  public void addRegisterOre(BiomeGenBase biome, ItemStack... itemStack) {
    Impact_API.sBiomeOres.put(biome, itemStack.clone());
    impact.I_RA
        .addVeinOres(Impact_API.sBiomeOres.get(biome), RegisterOreVein.mBiomeToInteger.get(biome));
  }

  public void registerBiomeTypes() {
    for (int i = 0; i < mAllBiomes.length; i++) {
      RegisterOreVein.mBiomeToInteger.put(mAllBiomes[i], i);
    }
  }

  @Override
  public void run() {
    //registerBiomeTypes();
    //registerOres();
  }
}
