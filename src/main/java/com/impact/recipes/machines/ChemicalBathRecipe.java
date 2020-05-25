package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

import static com.impact.item.Core_List_Items.*;

public class ChemicalBathRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {

/**============= CORE MOD =============*/

        //spacebox0
        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 64), Materials.Glue.getFluid(1000), GT_ItemList.packEarth.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 8);
        //spacebox1
        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Moon, 64), Materials.Plastic.getMolten(144L), GT_ItemList.packMoon.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 20);
        //spacebox2
        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Mars, 64), Materials.PolyvinylChloride.getMolten(144L), GT_ItemList.packMars.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 50);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustPhobos", 64), Materials.PolyvinylChloride.getMolten(144L), GT_ItemList.packPhobos.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 50);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustDeimos", 64), Materials.PolyvinylChloride.getMolten(144L), GT_ItemList.packDeimos.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 50);
        //spacebox3
        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Asteroid, 64), Materials.Polystyrene.getMolten(144L), GT_ItemList.packAsteroids.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustCeres", 64), Materials.Polystyrene.getMolten(144L), GT_ItemList.packCeres.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustEuropa", 64), Materials.Polystyrene.getMolten(144L), GT_ItemList.packEuropa.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustGanymed", 64), Materials.Polystyrene.getMolten(144L), GT_ItemList.packGanymed.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustCallisto", 64), Materials.Polystyrene.getMolten(144L), GT_ItemList.packCallisto.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
        //spacebox4
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustIo", 64), Materials.Polytetrafluoroethylene.getMolten(144L), GT_ItemList.packIo.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 312);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustVenus", 64), Materials.Polytetrafluoroethylene.getMolten(144L), GT_ItemList.packVenus.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 312);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustMercuryP", 64), Materials.Polytetrafluoroethylene.getMolten(144L), GT_ItemList.packMercury.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 312);
        //spacebox5
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustEnceladus", 64), Materials.PolyphenyleneSulfide.getMolten(144L), GT_ItemList.packIapetus.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 780);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustTitan", 64), Materials.PolyphenyleneSulfide.getMolten(144L), GT_ItemList.packTitan.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 780);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustOberon", 64), Materials.PolyphenyleneSulfide.getMolten(144L), GT_ItemList.packOberon.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 780);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustMiranda", 64), Materials.PolyphenyleneSulfide.getMolten(144L), GT_ItemList.packEris.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 780);
        //spacebox6
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustTriton", 64), Materials.Polybenzimidazole.getMolten(144L), GT_ItemList.packTriton.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 1950);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustProteus", 64), Materials.Polybenzimidazole.getMolten(144L), GT_ItemList.packProteus.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 1950);
        //spacebox7
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustPluto", 64), Materials.Polybenzimidazole.getMolten(288L), GT_ItemList.packPluto.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 4875);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustMakeMake", 64), Materials.Polybenzimidazole.getMolten(288L), GT_ItemList.packMakeMake.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 4875);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustHaumea", 64), Materials.Polybenzimidazole.getMolten(288L), GT_ItemList.packHaumea.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 4875);
        //spacebox8
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustCentauriA", 64), Materials.RadoxPolymer.getMolten(144L), GT_ItemList.packCentauriA.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustVegaB", 64), Materials.RadoxPolymer.getMolten(144L), GT_ItemList.packVegaB.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustBarnardaE", 64), Materials.RadoxPolymer.getMolten(144L), GT_ItemList.packBarnardaE.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustBarnardaF", 64), Materials.RadoxPolymer.getMolten(144L), GT_ItemList.packBarnardaF.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);
        GT_Values.RA.addChemicalBathRecipe(GregTech_API.getStackofAmountFromOreDict("dustTCetiE", 64), Materials.RadoxPolymer.getMolten(144L), GT_ItemList.packTCetiE.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);

        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("minecraft", "glass", 1L, 0), Materials.BorosilicateGlass.getMolten(144L), GT_ModHandler.getModItem("impact", "GlassBlock", 1L, 0), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 2);

        for (byte i = 0; i <= 15; i = (byte) (i + 1)) {
            for (int j = 0; j < Dyes.VALUES[i].getSizeOfFluidList(); j++) {
                if (i != 15) {
                    GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("impact", "GlassBlock", 1L, 0), Dyes.VALUES[i].getFluidDye(j, 18L), GT_ModHandler.getModItem("impact", "GlassBlock", 1L, (15 - i)), GT_Values.NI, GT_Values.NI, null, 200, 2);
                }
            }
        }

        for (byte i = 1; i <= 15; i++) {
            if (i != 0) {
                GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("impact", "GlassBlock", 1L, i), Materials.Chlorine.getMolten(50L), GT_ModHandler.getModItem("impact", "GlassBlock", 1L, 0), GT_Values.NI, GT_Values.NI, null, 200, 2);
            }
        }

        /* ================================= end CORE MOD =================================*/

        //Lens
        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.lens, Materials.ReinforcedGlass, 1L), Materials.Chlorine.getGas(100L),CoreItems2.getRecipe(RefinedReinforcedGlassLense.getMetaID(), 1), GT_Values.NI, GT_Values.NI, null, 600, 16);
        GT_Values.RA.addChemicalBathRecipe(CoreItems2.getRecipe(RefinedReinforcedGlassLense.getMetaID(), 1), Materials.Radon.getGas(250), CoreItems2.getRecipe(ChargedGlassLense.getMetaID(), 1), null, null, null, 384, 480);
        GT_Values.RA.addChemicalBathRecipe(CoreItems2.getRecipe(HugeRefinedReinforcedGlassLense.getMetaID(), 1), Materials.Radon.getGas(1250), CoreItems2.getRecipe(HugeChargedGlassLense.getMetaID(), 1), null, null, null, 384, 1920);
        GT_Values.RA.addLaserEngraverRecipe(CoreItems2.getRecipe(RefinedReinforcedGlassLense.getMetaID(), 3), GT_Utility.copyAmount(0, GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 1)), CoreItems2.getRecipe(HugeRefinedReinforcedGlassLense.getMetaID(), 1), 2400, 2000, false);

        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Mithril, 2), Materials.SulfuricAcid.getFluid(1000), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Mytryl, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.SluiceSand, 1), new int[]{10000, 8000, 7500}, 400, 1920);
        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Forcicium, 2), Materials.SulfuricAcid.getFluid(1000), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.MysteriousCrystal, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lutetium, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Trinium, 1), new int[]{10000, 8000, 7500}, 600, 30720);


    }
}
