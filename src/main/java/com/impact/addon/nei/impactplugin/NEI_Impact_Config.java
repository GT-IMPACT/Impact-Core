package com.impact.addon.nei.impactplugin;

import codechicken.nei.NEIModContainer;
import codechicken.nei.api.IConfigureNEI;
import com.impact.addon.nei.impactplugin.builder.HandlerInfoRegister;
import com.impact.addon.nei.impactplugin.ores.NEI_Impact_HammerDrop;
import com.impact.core.Impact_API;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Optional;
import gregtech.api.util.GT_Recipe;

import java.util.Objects;

import static com.impact.mods.gregtech.GT_ItemList.*;
import static gregtech.api.enums.ItemList.*;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.*;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class NEI_Impact_Config implements IConfigureNEI {
	
	public static boolean sIsAdded = true;
	
	@Optional.Method(modid = "NotEnoughItems")
	public String getName() {
		return "Impact NEI Plugin";
	}
	
	@Optional.Method(modid = "NotEnoughItems")
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (o.hashCode() == hashCode()) return true;
		NEI_Impact_Config cfg = (NEI_Impact_Config) o;
		return cfg.getName().equals(getName()) && cfg.getVersion().equals(getVersion());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName(), getVersion());
	}
	
	@Override
	public void loadConfig() {
		
		if (NEIModContainer.plugins.contains(this)) {
			return;
		}
		
		
		sIsAdded = false;
		
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			new GT_NEI_NaquadahGen(GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator);
			new GT_NEI_EnrichedNaquadahGen(GT_Recipe.GT_Recipe_Map.sLiquidENqGenerator);
			new GT_NEI_HyperGen(GT_Recipe.GT_Recipe_Map.sHyperGenerator);
			new GT_NEI_HeavyMetalCyclone(GT_Recipe.GT_Recipe_Map.sCyclonRecipes);
			new NEI_Impact_RailAssembler(GTMTE_RailAssembler.sTrackAssemblerRecipes);
			new NEI_Impact_MEProvider(GT_RecipeMaps.sMESystemProvider);
			new NEI_Impact_HammerDrop(Impact_API.dropsFromBlock);
			new NEI_Impact_DryingRack();
			new GT_NEI_Pyro(GT_RecipeMaps.sPyrolyseOven);
			registerSingle();
			registerHandlerInfo();
		}
		sIsAdded = true;
	}
	
	private void registerSingle() {
	}
	
	private void registerHandlerInfo() {
		new HandlerInfoRegister(sMultiblockElectrolyzerRecipes, Machine_MultiblockElectrolyzer);
		new HandlerInfoRegister(sMultiblockCentrifugeRecipes, Machine_MultiblockCentrifuge);
		new HandlerInfoRegister(sFlotationUnitRecipes, Machine_FlotationUnit);
		new HandlerInfoRegister(sIndustrialPulverizerRecipes, Machine_IndustrialPulverizer);
		new HandlerInfoRegister(sSawMillVisual, SawMill);
		new HandlerInfoRegister(sImplosionRecipes, Machine_Multi_ImplosionCompressor);
		new HandlerInfoRegister(sWireAssemblerRecipes, Machine_LV_WireAssembler);
		new HandlerInfoRegister(sComponentAssemblerRecipes, Machine_LV_ComponentAssembler);
		new HandlerInfoRegister(sBasicline, Machine_AdvDDDPrinter);
		new HandlerInfoRegister(sPrimitiveLine, Machine_DDDPrinter);
		new HandlerInfoRegister(sTinyWormHoleRecipes, Machine_MultiblockTinyWormHole);
		new HandlerInfoRegister(GTMTE_RailAssembler.sTrackAssemblerRecipes, Rail_Assembler);
		new HandlerInfoRegister(GT_RecipeMaps.sDryingOven, Drying_Oven_LV);
		new HandlerInfoRegister(GT_RecipeMaps.sMESystemProvider, ME_System_Provider);
		new HandlerInfoRegister(sTesseractRecipes, Machine_MultiblockTesseract);
		new HandlerInfoRegister(GT_RecipeMaps.sPyrolyseOven, Pyrolyse);
		new HandlerInfoRegister(sLiquidENqGenerator, Naquadah_Liquid_Enriched);
		new HandlerInfoRegister(sLiquidNqGenerator, Naquadah_Liquid_multi);
		new HandlerInfoRegister(sHyperGenerator, Naquadah_multi);
		new HandlerInfoRegister(sCyclonRecipes, Heavy_Metal_Cyclone);
		new HandlerInfoRegister(sFreezerSolidficationRecipes, Machine_FreezSolidifier);
		new HandlerInfoRegister(sFarmRecipes, Machine_Multi_Farm);
		new HandlerInfoRegister(sAntimatterReactorFuels, Antimatter_Reactor);
		new HandlerInfoRegister(sBlastSmelterRecipes, Machine_BlastSmelter);
		new HandlerInfoRegister(sOrganicReplicatorFakeRecipes, Machine_LV_OrganicReplicator);
		new HandlerInfoRegister(sDisassemblerRecipes, Machine_LV_Disassembler);
		new HandlerInfoRegister(sCokeOvenRecipes, Machine_CokeOven);
		new HandlerInfoRegister(sCokeOvenRecipes, Machine_CokeOven);
		new HandlerInfoRegister(GT_RecipeMaps.sTheMill, The_Mill);
		new HandlerInfoRegister("gt.recipe.semifluidgeneratorfuel", Generator_Semi_Turbine_LV.get(1));
	}
}