package com.impact.mods.nei.impactplugin;

import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.HandlerInfo;
import com.impact.core.Refstrings;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler;
import com.impact.mods.nei.impactplugin.builder.BuilderNEI;
import com.impact.mods.nei.impactplugin.ores.NEI_Impact_OreBiomes;
import com.impact.mods.nei.impactplugin.ores.OreBiome;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Recipe;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

import static com.impact.util.Utilits.getFluidDisplay;

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
	public void loadConfig() {
		RecipeProcessorLoader.init();
		
		sIsAdded = false;
		
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			new GT_NEI_NaquadahGen(GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator);
			new GT_NEI_EnrichedNaquadahGen(GT_Recipe.GT_Recipe_Map.sLiquidENqGenerator);
			new GT_NEI_HyperGen(GT_Recipe.GT_Recipe_Map.sHyperGenerator);
			new GT_NEI_HeavyMetalCyclone(GT_Recipe.GT_Recipe_Map.sCyclonRecipes);
			new NEI_Impact_RailAssembler(GTMTE_RailAssembler.sTrackAssemblerRecipes);
			new NEI_Impact_OreBiomes(OreBiome.startNEIBiomes());
			registerSingle();
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
					.addText(4, 85, Color.BLACK, "For any Rockets").addText(4, 95, Color.BLACK, "Need to make the rocket fuel")
					.addText(4, 105, Color.BLACK, "Need to fill the canister").addText(4, 115, Color.BLACK, "Need to fuel the rocket")
					.addHandlerInfo(new HandlerInfo.Builder("rocket_fuel", "Impact-Core", Refstrings.MODID)
							.setDisplayStack(new ItemStack(GCItems.fuelCanister)).setMaxRecipesPerPage(2).setHeight(145).setWidth(166).setShiftY(6).build())
					.build();
		}
	}
}