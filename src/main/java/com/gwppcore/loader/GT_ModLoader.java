package com.gwppcore.loader;


import com.gwppcore.GregTech.GTregister.GT_ItemRegister;
import com.gwppcore.GregTech.GTregister.GT_Machines_BasicRegister;
import com.gwppcore.GregTech.GTregister.GT_Machines_MultiRegister;
import com.gwppcore.GregTech.GTregister.GT_WorldGenRegister;
import com.gwppcore.recipes.*;
import com.gwppcore.GregTech.casings.GT_Loader_Casings;
import com.gwppcore.recipes.debug.DEBUG_Recipe;

public class GT_ModLoader {

	private static final GT_ItemRegister ItemLoader = new GT_ItemRegister();
	private static final GT_Loader_Casings CasingsLoader = new GT_Loader_Casings();
	private static final GT_Machines_MultiRegister MachineMultiLoader = new GT_Machines_MultiRegister();
	private static final GT_Machines_BasicRegister MachineBasicLoader = new GT_Machines_BasicRegister();
	private static final GTHandRecipe CraftingRecipeLoader = new GTHandRecipe();
	private static final GT_WorldGenRegister Worldgenloader = new GT_WorldGenRegister();

	private static final DEBUG_Recipe MachineRecipeLoader = new DEBUG_Recipe();
	private static final CentrifugeRecipe CentrifugeRecipe = new CentrifugeRecipe();
	private static final PulveriserRecipe PulveriserRecipe = new PulveriserRecipe();
	private static final LaserEngraverRecipe LaserEngraverRecipe = new LaserEngraverRecipe();
	private static final FormingPressRecipe FormingPressRecipe = new FormingPressRecipe();
	private static final ChemicalBathRecipe ChemicalBathRecipe = new ChemicalBathRecipe();

	public static void run()
    {
		ItemLoader.run();
		CasingsLoader.run();
		MachineMultiLoader.run();
		MachineBasicLoader.run();
		CraftingRecipeLoader.run();
		Worldgenloader.run();
	/** ========================= */
		MachineRecipeLoader.run();
		CentrifugeRecipe.run();
		PulveriserRecipe.run();
		LaserEngraverRecipe.run();
		FormingPressRecipe.run();
		ChemicalBathRecipe.run();
    }
}
