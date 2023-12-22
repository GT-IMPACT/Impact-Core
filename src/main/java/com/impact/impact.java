package com.impact;

import com.impact.client.gui.GUIHandler;
import com.impact.command.Command_FixBQ;
import com.impact.core.CommonProxy;
import com.impact.core.Config;
import com.impact.core.SaveManager;
import com.impact.debug.recipes.DebugRecipes;
import com.impact.events.EventDropBlock;
import com.impact.events.TickHandler;
import com.impact.events.impactEvents;
import com.impact.loader.MainLoader;
import com.impact.mods.gregtech.enums.IRecipeAdder;
import com.impact.mods.gregtech.enums.RecipeAdder;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.network.RegisterPackets;
import com.impact.recipe.maps.RecipesJson;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import space.impact.impact.BuildConfigKt;
import java.io.File;
import java.util.Objects;
import java.util.Random;

import static com.impact.core.Refstrings.MODID;
import static com.impact.core.impactLog.INFO;

@Mod(
		modid = BuildConfigKt.MODID,
		name = BuildConfigKt.MODNAME,
		version = BuildConfigKt.VERSION,
		acceptedMinecraftVersions = "[1.7.10]",
		dependencies = "required-after:Forge@[10.13.2.1291,);after:UndergroundBiomes"
)

public class impact {
	@SidedProxy(clientSide = "com.impact.core.ClientProxy", serverSide = "com.impact.core.CommonProxy")
	public static CommonProxy proxy;
	@Mod.Instance(MODID)
	public static impact instance;
	public static String ModPackVersion = BuildConfigKt.VERSION;
	public static Config mConfig;
	public static IRecipeAdder I_RA;
	public static Random RANDOM = new Random();
	
	public impact() {
		RegisterPackets.register();
		impact.I_RA = new RecipeAdder();
		Texture.Icons.VOID.name();
	}
	
	public static MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }
	
	public static void chatFromServer(String text) {
		IChatComponent c = new ChatComponentText(text);
		c.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
		impact.getServer().getConfigurationManager().sendChatMsgImpl(c, true);
	}
	
	@Mod.EventHandler
	public void onServerStarted(FMLServerStartedEvent aEvent) {
		proxy.onServerStarted();
	}
	
	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent aEvent) {
		aEvent.registerServerCommand(new Command_FixBQ());
	}
	
	@SuppressWarnings("")
	@Mod.EventHandler
	public void onServerStopping(FMLServerStoppingEvent aEvent) {
		proxy.onServerStopping();
		Objects.requireNonNull(SaveManager.get()).onServerStopping();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		MainLoader.Init(event);
		new GUIHandler();
		INFO("MainLoader LOAD Loaded");
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		mConfig = new Config(new File("config/IMPACT/impact.cfg"));
		INFO("Config Loaded");
		
		MainLoader.preInit(event);
		INFO("MainLoader PREINIT Loaded ");
		//MainLoader.preInitClient();
		
		CommonProxy.register_event(new impactEvents());
		CommonProxy.register_event(new TickHandler());
		CommonProxy.register_event(new EventDropBlock());
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MainLoader.postInit(event);
		proxy.postInit();
		RecipesJson.load();
		RecipesJson.loadCrafting();

		if (BuildConfigKt.IS_DEBUG) {
			DebugRecipes.INSTANCE.init();
		}
	}
	
	@Mod.EventHandler
	public void serverAboutToStart(final FMLServerAboutToStartEvent event) {
		SaveManager.onServerAboutToStart();
	}
	
	@Mod.EventHandler
	private void serverStopped(final FMLServerStoppedEvent event) {
		Objects.requireNonNull(SaveManager.get()).onServerStopped();
	}
	
	@Mod.EventHandler
	private void onLoadComplete(FMLLoadCompleteEvent event) {
		proxy.onLoadComplete(event);
	}
}