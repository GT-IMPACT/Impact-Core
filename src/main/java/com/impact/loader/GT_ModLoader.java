package com.impact.loader;


import com.impact.mods.GregTech.GTregister.*;
import com.impact.recipes.*;
import com.impact.mods.GregTech.casings.GT_Loader_Casings;
import com.impact.recipes.debug.DEBUG_Recipe;
import com.impact.recipes.machines.*;
import cpw.mods.fml.common.Loader;

public class GT_ModLoader {

	private static final GT_ItemRegister ItemLoader = new GT_ItemRegister();
	private static final GT_Loader_Casings CasingsLoader = new GT_Loader_Casings();
	private static final GT_Machines_MultiRegister MachineMultiLoader = new GT_Machines_MultiRegister();
	private static final GT_Machines_BasicRegister MachineBasicLoader = new GT_Machines_BasicRegister();
	private static final HandRecipe CraftingRecipeLoader = new HandRecipe();
	private static final GT_WorldGenRegister Worldgenloader = new GT_WorldGenRegister();
	private static final ModLoader ModLoader = new ModLoader();


	/** ========================= */
	private static final DEBUG_Recipe DEBUG_Recipe = new DEBUG_Recipe();
	private static final CentrifugeRecipe CentrifugeRecipe = new CentrifugeRecipe();
	private static final PulveriserRecipe PulveriserRecipe = new PulveriserRecipe();
	private static final LaserEngraverRecipe LaserEngraverRecipe = new LaserEngraverRecipe();
	private static final FormingPressRecipe FormingPressRecipe = new FormingPressRecipe();
	private static final ChemicalBathRecipe ChemicalBathRecipe = new ChemicalBathRecipe();
	private static final AssemblerRecipe AssemblerRecipe = new AssemblerRecipe();
	private static final FreezSolidifierRecipe FreezSolidifierRecipe = new FreezSolidifierRecipe();
	private static final Printer3DRecipe Printer3DRecipe = new Printer3DRecipe();
	private static final BlastSmelterRecipe BlastSmelterRecipe = new BlastSmelterRecipe();
	private static final ComponentAssemblerRecipe ComponentAssemblerRecipe = new ComponentAssemblerRecipe();
	private static final CompessorRecipe CompessorRecipe = new CompessorRecipe();
	private static final AlloySmelterRecipe AlloySmelterRecipe = new AlloySmelterRecipe();
	private static final MixerRecipe MixerRecipe = new MixerRecipe();
	private static final EBFRecipe EBFRecipe = new EBFRecipe();
	private static final FluidCannerRecipe FluidCannerRecipe = new FluidCannerRecipe();
	private static final CircuitAssemblerRecipe CircuitAssemblerRecipe = new CircuitAssemblerRecipe();
	private static final FarmRecipe FarmRecipe = new FarmRecipe();
	private static final CuttingRecipe CuttingRecipe = new CuttingRecipe();
	private static final VacuumFreezerRecipe VacuumFreezerRecipe = new VacuumFreezerRecipe();
	private static final AssemblyLineRecipe AssemblyLineRecipe = new AssemblyLineRecipe();
	private static final OpenComputersRecipe OpenComputersRecipe = new OpenComputersRecipe();
	private static final ForgeHammerRecipe ForgeHammerRecipe = new ForgeHammerRecipe();
	private static final FluidExtractorRecipe FluidExtractorRecipe = new FluidExtractorRecipe();
	private static final ExtruderRecipe ExtruderRecipe = new ExtruderRecipe();
	private static final ImplosionCompressorRecipe ImplosionCompressorRecipe = new ImplosionCompressorRecipe();

	public static void run() {
		ItemLoader.run();
		CasingsLoader.run();
		MachineMultiLoader.run();
		MachineBasicLoader.run();
		CraftingRecipeLoader.run();
		Worldgenloader.run();
		ModLoader.run();
	/** ========================= */
		DEBUG_Recipe.run();
		CentrifugeRecipe.run();
		PulveriserRecipe.run();
		LaserEngraverRecipe.run();
		FormingPressRecipe.run();
		ChemicalBathRecipe.run();
		AssemblerRecipe.run();
		FreezSolidifierRecipe.run();
		Printer3DRecipe.run();
		BlastSmelterRecipe.run();
		ComponentAssemblerRecipe.run();
		CompessorRecipe.run();
		AlloySmelterRecipe.run();
		MixerRecipe.run();
		EBFRecipe.run();
		FluidCannerRecipe.run();
		CircuitAssemblerRecipe.run();
		FarmRecipe.run();
		CuttingRecipe.run();
		VacuumFreezerRecipe.run();
		AssemblyLineRecipe.run();
		OpenComputersRecipe.run();
		ForgeHammerRecipe.run();
		FluidExtractorRecipe.run();
		ExtruderRecipe.run();
		ImplosionCompressorRecipe.run();
    }
}
