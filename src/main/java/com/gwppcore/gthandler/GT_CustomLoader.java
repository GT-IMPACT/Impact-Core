package com.gwppcore.gthandler;


import com.gwppcore.gthandler.casings.GT_Loader_CasingsSC;

public class GT_CustomLoader {

	private static final GT_Loader_Items ItemLoader = new GT_Loader_Items();
	private static final GT_Loader_Machines MachineLoader = new GT_Loader_Machines();
	private static final GT_MachineRecipeLoader MachineRecipeLoader = new GT_MachineRecipeLoader();
	private static final GT_CraftingRecipeLoader CraftingRecipeLoader = new GT_CraftingRecipeLoader();
	private static final Worldgenloader Worldgenloader = new Worldgenloader();
	private static final GT_Loader_CasingsSC CasingLoader = new GT_Loader_CasingsSC();
	public static void run()
    {
		ItemLoader.run();
		MachineLoader.run();
		MachineRecipeLoader.run();
		CraftingRecipeLoader.run();
		Worldgenloader.run();
		CasingLoader.run();
    }
}
