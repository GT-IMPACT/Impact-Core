package com.gwppcore.gthandler;


import com.gwppcore.gthandler.casings.GT_Loader_CasingsParall;

public class GT_CustomLoader {

	private static final GT_Loader_Items ItemLoader = new GT_Loader_Items();
	private static final GT_Loader_CasingsParall CasingsLoader = new GT_Loader_CasingsParall();
	private static final GT_Loader_Machines MachineLoader = new GT_Loader_Machines();
	private static final GT_MachineRecipeLoader MachineRecipeLoader = new GT_MachineRecipeLoader();
	private static final GT_CraftingRecipeLoader CraftingRecipeLoader = new GT_CraftingRecipeLoader();
	private static final Worldgenloader Worldgenloader = new Worldgenloader();
	public static void run()
    {
		ItemLoader.run();
		CasingsLoader.run();
		MachineLoader.run();
		MachineRecipeLoader.run();
		CraftingRecipeLoader.run();
		Worldgenloader.run();
    }
}
