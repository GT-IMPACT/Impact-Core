package com.impact.mods.nei.impactplugin;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.IRecipeHandler;
import com.github.vfyjxf.nee.processor.IRecipeProcessor;
import com.impact.mods.gregtech.GT_RecipeMaps;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;

import javax.annotation.Nonnull;
import java.util.*;

import static com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler.sTrackAssemblerRecipes;

public class ImpactRecipeProcessor implements IRecipeProcessor {

    private static final Class<?> printer3DClz, pyroClz, sawMillClz;

    private static final Class<?> heavymetalClz, railAssemblCls, MEProvider;

    static {
        Class<?> printer3d = null;
        Class<?> pyro = null;
        Class<?> sawMill = null;
        Class<?> heavymetal = null;
        Class<?> railAssembl = null;
        Class<?> meprovider = null;
        try {
            printer3d = Class.forName("gregtech.nei.GT_NEI_3DPrinter");
            pyro = Class.forName("gregtech.nei.GT_NEI_Pyro");
            sawMill = Class.forName("gregtech.nei.GT_NEI_SawMill");
            heavymetal = Class.forName("com.impact.mods.nei.impactplugin.GT_NEI_HeavyMetalCyclone");
            railAssembl = Class.forName("com.impact.mods.nei.impactplugin.NEI_Impact_RailAssembler");
            meprovider = Class.forName("com.impact.mods.nei.impactplugin.NEI_Impact_MEProvider");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        printer3DClz = printer3d;
        pyroClz = pyro;
        sawMillClz = sawMill;
        heavymetalClz = heavymetal;
        railAssemblCls = railAssembl;
        MEProvider = meprovider;
    }

    @Nonnull
    @Override
    public Set<String> getAllOverlayIdentifier() {
        Set<String> identifiers = new HashSet<>();
        identifiers.add(sTrackAssemblerRecipes.mNEIName);
        identifiers.add(GT_RecipeMaps.sMESystemProvider.mNEIName);
        identifiers.add(GT_Recipe.GT_Recipe_Map.sSawMillVisual.mNEIName);
        identifiers.add(GT_Recipe.GT_Recipe_Map.sPyrolyseBasicVisual.mNEIName);
        identifiers.add(GT_Recipe.GT_Recipe_Map.sPrimitiveLine.mNEIName);
        identifiers.add(GT_Recipe.GT_Recipe_Map.sBasicline.mNEIName);
        return identifiers;
    }

    @Nonnull
    @Override
    public String getRecipeProcessorId() {
        return "ImpactProcessor";
    }

    @Nonnull
    @Override
    public List<PositionedStack> getRecipeInput(IRecipeHandler recipe, int recipeIndex, String identifier) {
        if (printer3DClz.isInstance(recipe)
                || MEProvider.isInstance(recipe)
                || pyroClz.isInstance(recipe)
                || sawMillClz.isInstance(recipe)
                || heavymetalClz.isInstance(recipe)
                || railAssemblCls.isInstance(recipe)) {
            List<PositionedStack> recipeInputs = new ArrayList<>(recipe.getIngredientStacks(recipeIndex));
            recipeInputs.removeIf(positionedStack -> GT_Utility.getFluidFromDisplayStack(positionedStack.items[0]) != null || positionedStack.item.stackSize == 0);
            return recipeInputs;
        }
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public List<PositionedStack> getRecipeOutput(IRecipeHandler recipe, int recipeIndex, String identifier) {
        if (printer3DClz.isInstance(recipe)
                || MEProvider.isInstance(recipe)
                || pyroClz.isInstance(recipe)
                || sawMillClz.isInstance(recipe)
                || heavymetalClz.isInstance(recipe)
                || railAssemblCls.isInstance(recipe)) {
            List<PositionedStack> recipeOutputs = new ArrayList<>(recipe.getOtherStacks(recipeIndex));
            recipeOutputs.removeIf(positionedStack -> GT_Utility.getFluidFromDisplayStack(positionedStack.items[0]) != null);
            return recipeOutputs;
        }
        return Collections.emptyList();
    }
}