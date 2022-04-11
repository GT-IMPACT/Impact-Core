package com.impact.mods.nei.impactplugin;

import codechicken.nei.NEIModContainer;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.HandlerInfo;
import com.impact.core.Impact_API;
import com.impact.core.Refstrings;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler;
import com.impact.mods.nei.impactplugin.builder.BuilderNEI;
import com.impact.mods.nei.impactplugin.builder.HandlerInfoRegister;
import com.impact.mods.nei.impactplugin.ores.NEI_Impact_DimOres;
import com.impact.mods.nei.impactplugin.ores.NEI_Impact_HammerDrop;
import com.impact.mods.nei.impactplugin.ores.NEI_Impact_Ores;
import com.impact.mods.nei.impactplugin.ores.OreBuilderNEI;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Recipe;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.Objects;

import static com.impact.mods.gregtech.GT_ItemList.*;
import static com.impact.util.Utilits.getFluidDisplay;
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
			new NEI_Impact_Ores(OreBuilderNEI.defaultOres, 1);
			new NEI_Impact_DimOres(OreBuilderNEI.dimDefaultOres, 1);
			new NEI_Impact_Ores(OreBuilderNEI.smallOres, 0);
			new NEI_Impact_DimOres(OreBuilderNEI.dimSmallOres, 0);
			new NEI_Impact_HammerDrop(Impact_API.dropsFromBlock);
			new NEI_Impact_DryingRack();
			registerSingle();
			registerHandlerInfo();
		}
		sIsAdded = true;
	}
	
	private void registerSingle() {
		if (Loader.isModLoaded("GalacticraftCore")) {
			BuilderNEI.start()
					.addRecipeName("Rocket Fuel Production").addNameOverlayID("rocket_fuel")
					.addGuiTexture("gregtech:textures/gui/basic/RocketFuel.png").addRectangleRecipe(0, 0, 0, 0)
					.addInput(new ItemStack(GCItems.rocketTier1), 25, 22)
					.addInput(getFluidDisplay(new FluidStack(ItemList.sRocketFuel, 1000)), 125, 22)
					.addOutput(new ItemStack(GCItems.fuelCanister), 75, 22)
					.addText(4, 85, Color.BLACK, "For any Rockets")
					.addText(4, 95, Color.BLACK, "Need to make the rocket fuel")
					.addText(4, 105, Color.BLACK, "Need to fill the canister")
					.addText(4, 115, Color.BLACK, "Need to fuel the rocket")
					.addHandlerInfo(new HandlerInfo.Builder("rocket_fuel", "Impact-Core", Refstrings.MODID)
							.setDisplayStack(new ItemStack(GCItems.fuelCanister)).setMaxRecipesPerPage(2).setHeight(145).setWidth(166).setShiftY(6).build())
					.build();
		}
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
		new HandlerInfoRegister(sPyrolyseBasicVisual, Pyrolyse);
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