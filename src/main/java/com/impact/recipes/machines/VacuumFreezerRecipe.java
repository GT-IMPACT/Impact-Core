package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class VacuumFreezerRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {

/** ================================= start CORE MOD =================================*/
        //VacuumFreezer
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Pentacadmiummagnesiumhexaoxid, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Pentacadmiummagnesiumhexaoxid, 1L), 2000, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Titaniumonabariumdecacoppereikosaoxid, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Titaniumonabariumdecacoppereikosaoxid, 1L), 2000, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Uraniumtriplatinid, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Uraniumtriplatinid, 1L), 3000, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Vanadiumtriindinid, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Vanadiumtriindinid, 1L), 3000, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid, 1L), 4500, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L), 4500, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Neutronium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Neutronium, 1L), 5000, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Longasssuperconductornameforuvwire, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Longasssuperconductornameforuvwire, 1L), 2500, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Longasssuperconductornameforuhvwire, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Longasssuperconductornameforuhvwire, 1L), 2000, 122880);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Longasssuperconductornameforuevwire, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Longasssuperconductornameforuevwire, 1L), 2000, 500000);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Diamericiumtitanium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Diamericiumtitanium, 1L), 2000, 500000);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Naquadah, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadah, 1L), 990, 7860);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Europiumoxide, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Europiumoxide, 1L), 590, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Tritanium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tritanium, 1L), 969, 7860);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Americium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Americium, 1L), 735, 4096);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Platinum, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Platinum, 1L), 585, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Iridium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iridium, 1L), 576, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Osmiridium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Osmiridium, 1L), 573, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Osmium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Osmium, 1L), 570, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Tungsten, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tungsten, 1L), 549, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Europium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Europium, 1L), 453, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.HSSS, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.HSSS, 1L), 387, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Enderium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Enderium, 1L), 378, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.TungstenSteel, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.TungstenSteel, 1L), 357, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Palladium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Palladium, 1L), 318, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.HSSG, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.HSSG, 1L), 294, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.NaquadahAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahAlloy, 1L), 294, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Naquadria, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadria, 1L), 294, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.NaquadahEnriched, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahEnriched, 1L), 294, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Adamantium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Adamantium, 1L), 294, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.ElectrumFlux, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.ElectrumFlux, 1L), 294, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.TungstenCarbide, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.TungstenCarbide, 1L), 291, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.HSSE, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.HSSE, 1L), 243, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.NiobiumTitanium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NiobiumTitanium, 1L), 213, 4096);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Nichrome, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Nichrome, 1L), 168, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.VanadiumGallium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.VanadiumGallium, 1L), 165, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.YttriumBariumCuprate, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.YttriumBariumCuprate, 1L), 153, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Desh, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Desh, 1L), 147, 1920);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Titanium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Titanium, 1L), 144, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Draconium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Draconium, 1L), 294, 7860);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.DraconiumAwakened, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.DraconiumAwakened, 1L), 294, 31440);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Infuscolium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Infuscolium, 1L), 2000, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Quantium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Quantium, 1L), 300, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.BlackPlutonium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.BlackPlutonium, 1L), 400, 122880);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Adamantium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Adamantium, 1L), 800, 30720);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Trinium, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Trinium, 1L), 294, 7860);

        GT_Values.RA.addVacuumFreezerRecipe(CoreItems2.getRecipe(70, 1), CoreItems2.getRecipe(69, 1), 400, 480);
        GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Ledox, 1L), CoreItems2.getRecipe(71, 1), 400, 480);
        GT_Values.RA.addVacuumFreezerRecipe(CoreItems2.getRecipe(73, 1), CoreItems2.getRecipe(72, 1), 600, 1920);

        /* ================================= end CORE MOD =================================*/
    }
}
