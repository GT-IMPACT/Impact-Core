package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class ChemicalBathRecipe implements Runnable{
    @Override
    public void run(){

/**============= CORE MOD =============*/

//        //spacebox0
//        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 64), Materials.Glue.getFluid(1000), GT_ItemList.packEarth.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 8);
//        //spacebox1
//        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Moon, 64), Materials.Plastic.getMolten(144L), GT_ItemList.packMoon.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 20);
//        //spacebox2
//        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Mars, 64), Materials.PolyvinylChloride.getMolten(144L), GT_ItemList.packMars.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 50);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.PhobosStoneDust", 64L, 0), Materials.PolyvinylChloride.getMolten(144L), GT_ItemList.packPhobos.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 50);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.DeimosStoneDust", 64L, 0), Materials.PolyvinylChloride.getMolten(144L), GT_ItemList.packDeimos.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 50);
//        //spacebox3
//        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Asteroid, 64), Materials.Polystyrene.getMolten(144L), GT_ItemList.packAsteroids.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.CeresStoneDust", 64L, 0), Materials.Polystyrene.getMolten(144L), GT_ItemList.packCeres.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.EuropaStoneDust", 64L, 0), Materials.Polystyrene.getMolten(144L), GT_ItemList.packEuropa.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.GanymedStoneDust", 64L, 0), Materials.Polystyrene.getMolten(144L), GT_ItemList.packGanymed.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.CallistoStoneDust", 64L, 0), Materials.Polystyrene.getMolten(144L), GT_ItemList.packCallisto.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 125);
//        //spacebox4
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.IoStoneDust", 64L, 0), Materials.Polytetrafluoroethylene.getMolten(144L), GT_ItemList.packIo.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 312);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.VenusStoneDust", 64L, 0), Materials.Polytetrafluoroethylene.getMolten(144L), GT_ItemList.packVenus.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 312);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.MercuryCoreDust", 64L, 0), Materials.Polytetrafluoroethylene.getMolten(144L), GT_ItemList.packMercury.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 312);
//        //spacebox5
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.EnceladusStoneDust", 64L, 0), Materials.PolyphenyleneSulfide.getMolten(144L), GT_ItemList.packIapetus.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 780);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.TitanStoneDust", 64L, 0), Materials.PolyphenyleneSulfide.getMolten(144L), GT_ItemList.packTitan.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 780);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.OberonStoneDust", 64L, 0), Materials.PolyphenyleneSulfide.getMolten(144L), GT_ItemList.packOberon.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 780);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.MirandaStoneDust", 64L, 0), Materials.PolyphenyleneSulfide.getMolten(144L), GT_ItemList.packEris.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 780);
//        //spacebox6
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.TritonStoneDust", 64L, 0), Materials.Polybenzimidazole.getMolten(144L), GT_ItemList.packTriton.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 1950);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.ProteusStoneDust", 64L, 0), Materials.Polybenzimidazole.getMolten(144L), GT_ItemList.packProteus.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 1950);
//        //spacebox7
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.PlutoStoneDust", 64L, 0), Materials.Polybenzimidazole.getMolten(288L), GT_ItemList.packPluto.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 4875);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.MakeMakeStoneDust", 64L, 0), Materials.Polybenzimidazole.getMolten(288L), GT_ItemList.packMakeMake.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 4875);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.HaumeaStoneDust", 64L, 0), Materials.Polybenzimidazole.getMolten(288L), GT_ItemList.packHaumea.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 4875);
        //spacebox8
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.CentauriAStoneDust", 64L, 0), Materials.Radox.getMolten(144L), CustomItemList.packCentauriA.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.VegaBStoneDust", 64L, 0), Materials.Radoxpoly.getMolten(144L), CustomItemList.packVegaB.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.BarnardaEStoneDust", 64L, 0), Materials.Radoxpoly.getMolten(144L), CustomItemList.packBarnardaE.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.BarnardaFStoneDust", 64L, 0), Materials.Radoxpoly.getMolten(144L), CustomItemList.packBarnardaF.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);
//        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("spartakcore", "item.TCetiEStoneDust", 64L, 0), Materials.Radoxpoly.getMolten(144L), CustomItemList.packTCetiE.get(1L), GT_Values.NI, GT_Values.NI, new int[]{10000}, 200, 12188);

/* ================================= end CORE MOD =================================*/

/** ================================= start ANOTHER MOD =================================*/

/* ================================= end ANOTHER MOD =================================*/



    }
}
