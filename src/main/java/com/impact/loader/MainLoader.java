package com.impact.loader;

import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.common.block.netherportal.BlockHandler;
import com.impact.common.block.netherportal.BlockNullPortal;
import com.impact.common.item.*;
import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.te.*;
import com.impact.impact;
import com.impact.mods.gregtech.Basic_Register;
import com.impact.mods.gregtech.GT_ItemRegister;
import com.impact.mods.gregtech.Multi_Register;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.items.tools.GTMG_Tool_WorkRadius;
import com.impact.mods.opencomputers.Driver_Register;
import com.impact.mods.railcraft.carts.item.events.Module;
import com.impact.util.string.Lang;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import static codechicken.nei.api.API.hideItem;
import static com.impact.common.item.Core_List_Items.registerOreDictNames;
import static com.impact.core.Config.DisableNether;
import static com.impact.core.impactLog.INFO;
import static com.impact.impact.getModules;

public class MainLoader {
	
	private MainLoader() {
	}
	
	public static void Init(FMLInitializationEvent event) {
		ItemRegistery.run();
		
		OreDictRegister.register_all();
		INFO("[load] OreDict Register List - Loaded");
		
		Block_QuantumStuff.run();
		INFO("[load] Quantum Stuff registered");
		
		for (Module module : getModules()) {
			if (module.getIsActive()) {
				module.load(event);
			}
		}
		new GTMG_Tool_WorkRadius.ProccessToolHead();
		new GTMG_Tool_WorkRadius();
		if (Loader.isModLoaded("OpenComputers")) {
			Driver_Register.init();
			INFO("[Init] OpenComputers Integration - Loaded");
		}
		impact.proxy.registerRenderInfo();
		INFO("[Init] Item Registery - Loaded");
	}
	
	public static void preInit(FMLPreInitializationEvent event) {

		
		Covers.getInstance().registerItem();
		INFO("[preInit] Covers - Loaded");
		
		Core_Items.getInstance().registerItem();
		INFO("[preInit] Meta Items 1 - Loaded");
		
		Core_Items2.getInstance().registerItem();
		INFO("[preInit] Meta Items 2 - Loaded");
		
		Core_Items3.getInstance().registerItem();
		INFO("[preInit] Meta Items 3 - Loaded");
		
		WoodBrickFormTool.getInstance().registerItem();
		INFO("[preInit] Wood Brick Form Tool - Loaded");
		
		FakeCircuits.getInstance().registerItem();
		INFO("[preInit] Fake Circuits - Loaded");
		
//		KineticRotors.getInstance().registerItem();
//		INFO("[preInit] Kinetic Rotors - Loaded");
		
		DrillHeads.getInstance().registerItem();
		INFO("[preInit] Drill Heads - Loaded");
		
		registerOreDictNames();
		INFO("[preInit] Meta Items OreDict List - Loaded");
		
		ItemRegistery.registerBlocks();
		INFO("[preInit] Meta Blocks - Loaded");
		
		for (byte i = 0; i <= 7; i++) {
			hideItem(new ItemStack(FakeCircuits.getInstance(), 1, i));
		}
		INFO("[preInit] Hide NEI Items - Loaded");
		
		if (DisableNether) {
			BlockHandler.replaceBlock(Blocks.portal, BlockNullPortal.class, ItemBlock.class);
			INFO("[preInit] Disabled Nether Portal - Loaded");
		}
		
		for (Module module : getModules()) {
			if (!module.areRequirementsMet() && module.getIsActive()) {
				module.setIsActive(false);
			}
		}
		
		for (Module module : getModules()) {
			if (module.getIsActive()) {
				module.init(event);
			}
		}
		
		GameRegistry.registerTileEntity(TE_SpaceElevatorTether.class, "space_elevator_tether");
		GameRegistry.registerTileEntity(TE_NqTether.class, "nq_tether");
		GameRegistry.registerTileEntity(TilePlacedItem.class, "TilePlacedItem");
		GameRegistry.registerTileEntity(TE_WindMill.class, "TileWindMill");
		GameRegistry.registerTileEntity(TE_TheMill.class, "TheMill");
		GameRegistry.registerTileEntity(TE_DryingRack.class, "DryingRack");
	}
	
	
	public static void postInit(FMLPostInitializationEvent event) {
		new GT_ItemRegister().run();
		new Casing_Helper().run();
		new Multi_Register().run();
		new Basic_Register().run();
		new ModLoader().run();
		OreGenerator.register();
		INFO(Lang.impact.eng + " Lang Loaded");
		for (Module module : getModules()) {
			if (module.getIsActive()) {
				module.postInit(event);
			}
		}
	}
}