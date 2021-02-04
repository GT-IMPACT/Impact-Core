package com.impact.recipes.machines;

import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;

public class VacuumFreezerRecipe implements Runnable {

  final Core_Items2 CoreItems2 = Core_Items2.getInstance();

  @Override
  public void run() {

/** ================================= start CORE MOD =================================*/
    //VacuumFreezer
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Pentacadmiummagnesiumhexaoxid, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Pentacadmiummagnesiumhexaoxid, 1L),
        400, 480);
    GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator
            .get(OrePrefixes.ingotHot, Materials.Titaniumonabariumdecacoppereikosaoxid, 1L),
        GT_OreDictUnificator
            .get(OrePrefixes.ingot, Materials.Titaniumonabariumdecacoppereikosaoxid, 1L), 500, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Uraniumtriplatinid, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Uraniumtriplatinid, 1L), 600, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Vanadiumtriindinid, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Vanadiumtriindinid, 1L), 600, 480);
    GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot,
        Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot,
            Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid, 1L), 600, 480);
    GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator
            .get(OrePrefixes.ingotHot, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
        GT_OreDictUnificator
            .get(OrePrefixes.ingot, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L), 720,
        480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Neutronium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Neutronium, 1L), 780, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator
            .get(OrePrefixes.ingotHot, Materials.Longasssuperconductornameforuvwire, 1L),
        GT_OreDictUnificator
            .get(OrePrefixes.ingot, Materials.Longasssuperconductornameforuvwire, 1L), 500, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator
            .get(OrePrefixes.ingotHot, Materials.Longasssuperconductornameforuhvwire, 1L),
        GT_OreDictUnificator
            .get(OrePrefixes.ingot, Materials.Longasssuperconductornameforuhvwire, 1L), 550,
        122880);
    GT_Values.RA.addVacuumFreezerRecipe(GT_OreDictUnificator
            .get(OrePrefixes.ingotHot, Materials.Longasssuperconductornameforuevwire, 1L),
        GT_OreDictUnificator
            .get(OrePrefixes.ingot, Materials.Longasssuperconductornameforuevwire, 1L), 600,
        500000);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Diamericiumtitanium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Diamericiumtitanium, 1L), 480,
        122880);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Naquadah, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadah, 1L), 420, 7860);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Europiumoxide, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Europiumoxide, 1L), 420, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Tritanium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tritanium, 1L), 736, 7860);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Americium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Americium, 1L), 592, 4096);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Platinum, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Platinum, 1L), 466, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Iridium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iridium, 1L), 424, 1920);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Osmiridium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Osmiridium, 1L), 437, 1920);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Osmium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Osmium, 1L), 458, 1920);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Tungsten, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tungsten, 1L), 419, 1920);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Europium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Europium, 1L), 377, 1920);
    GT_Values.RA
        .addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.HSSS, 1L),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.HSSS, 1L), 220, 1920);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Enderium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Enderium, 1L), 245, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.TungstenSteel, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.TungstenSteel, 1L), 231, 1920);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Palladium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Palladium, 1L), 216, 480);
    GT_Values.RA
        .addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.HSSG, 1L),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.HSSG, 1L), 186, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.NaquadahAlloy, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahAlloy, 1L), 220, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Naquadria, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadria, 1L), 197, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.NaquadahEnriched, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahEnriched, 1L), 188, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Adamantium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Adamantium, 1L), 173, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.ElectrumFlux, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.ElectrumFlux, 1L), 168, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.TungstenCarbide, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.TungstenCarbide, 1L), 215, 1920);
    GT_Values.RA
        .addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.HSSE, 1L),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.HSSE, 1L), 194, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.NiobiumTitanium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NiobiumTitanium, 1L), 152, 4096);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Nichrome, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Nichrome, 1L), 108, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.VanadiumGallium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.VanadiumGallium, 1L), 109, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.YttriumBariumCuprate, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.YttriumBariumCuprate, 1L), 96, 1920);
    GT_Values.RA
        .addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Desh, 1L),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Desh, 1L), 87, 1920);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Titanium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Titanium, 1L), 75, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Draconium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Draconium, 1L), 213, 7860);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.DraconiumAwakened, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.DraconiumAwakened, 1L), 224, 31440);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Infuscolium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Infuscolium, 1L), 398, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Quantium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Quantium, 1L), 280, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.BlackPlutonium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.BlackPlutonium, 1L), 300, 122880);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Adamantium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Adamantium, 1L), 490, 30720);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Trinium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Trinium, 1L), 294, 7860);
    GT_Values.RA
        .addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.BT6, 1L),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.BT6, 1L), 164, 480);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.CosmicNeutronium, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.CosmicNeutronium, 1L), 400, 122880);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.InfinityCatalyst, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.InfinityCatalyst, 1L), 500, 500000);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Infinity, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Infinity, 1L), 500, 2000000);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Oriharukon, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Oriharukon, 1L), 386, 7680);
    GT_Values.RA.addVacuumFreezerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Mytryl, 1L),
        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Mytryl, 1L), 324, 1920);

    GT_Values.RA
        .addVacuumFreezerRecipe(CoreItems2.getRecipe(70, 1), CoreItems2.getRecipe(69, 1), 300, 480);
    GT_Values.RA
        .addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Ledox, 1L),
            CoreItems2.getRecipe(71, 1), 300, 480);
    GT_Values.RA
        .addVacuumFreezerRecipe(CoreItems2.getRecipe(73, 1), CoreItems2.getRecipe(72, 1), 400,
            1920);

    /* ================================= end CORE MOD =================================*/
  }
}
