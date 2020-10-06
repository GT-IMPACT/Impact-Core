package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class SifterRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        GT_Values.RA.addSifterRecipe(GT_ModHandler.getModItem("GalaxySpace", "barnardaCdirt", 1L), new ItemStack[] {GT_ModHandler.getModItem("minecraft", "sand", 1L), GT_ModHandler.getModItem("GalaxySpace", "barnardaCdandelions", 1L, 0), GT_ModHandler.getModItem("GalaxySpace", "barnardaCdandelions", 1L, 1), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Gallium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Yttrium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Niobium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Neutronium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.InfinityCatalyst, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DraconiumAwakened, 1L)}, new int[] {5000,2000,2000,2500,2000,1500,900,250,250}, 20, 2000000);
        GT_Values.RA.addSifterRecipe(GT_OreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Tin, 1L), new ItemStack[] {GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L)}, new int[] {5000,3000,2000,1500,1200,1000,900,500,250}, 200, 480);
        GT_Values.RA.addSifterRecipe(GT_OreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Cassiterite, 1L), new ItemStack[] {GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Zirconium, 1L)}, new int[] {5600,3300,2200,1600,1400,1100,950,600,30}, 220, 480);
        GT_Values.RA.addSifterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.RareEarth, 1L), new ItemStack[] {GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Neodymium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Yttrium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lanthanum, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cerium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cadmium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Caesium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Strontium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Samarium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gadolinium, 1L)}, new int[] {600, 600, 600, 600, 600, 600, 400, 200, 100}, 600, 480);

    }
}
