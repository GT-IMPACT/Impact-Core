package com.impact.mods.GregTech.enums;

import com.impact.core.Impact_API;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeOreRegister implements Comparable<BiomeOreRegister> {

  private final BiomeGenBase mBiome;
  private final ItemStack[] mOre;
  private final String mName;

  public BiomeOreRegister(String name, BiomeGenBase biome, ItemStack... ore) {
    this.mBiome = biome;
    this.mOre = ore;
    this.mName = name;
    Impact_API.sBiomeOres.put(name, this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BiomeOreRegister) {
      return compareTo((BiomeOreRegister) obj) == 0;
    }
    return false;
  }

  @Override
  public int compareTo(BiomeOreRegister o) {
    return mName.compareTo(o.mName);
  }

  public static void run() {
//    new BiomeOreRegister("Iron Vein", BiomeGenBase.desert, )
  }
}
