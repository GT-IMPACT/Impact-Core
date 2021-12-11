package com.impact.events;


import com.impact.util.string.Language;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.Arrays;
import java.util.List;

public class impactEvents {
	
	public static List<String> bannedEntities = Arrays.asList(
			"Creeper", "Skeleton", "Spider", "Giant", "Zombie", "Slime", "Ghast",
			"PigZombie", "Enderman", "Silverfish", "Blaze", "LavaSlime", "WitherBoss", "Bat", "Witch",
			"MushroomCow", "SnowMan", "GalacticraftCore.EvolvedSpider",
			"GalacticraftCore.EvolvedZombie", "GalacticraftCore.EvolvedCreeper",
			"GalacticraftCore.EvolvedSkeleton", "GalacticraftCore.EvolvedSkeletonBoss",
			"GalacticraftCore.EvolvedBossBlaze", "GalacticraftCore.EvolvedBossGhast",
			"GalacticraftCore.EvolvedCrystalBoss", "GalacticraftCore.EvolvedBossSlime",
			"GalacticraftCore.EvolvedBossWolf", "GalacticraftCore.EvolvedFireCreeper",
			"GalacticraftCore.EvolvedFireSkeleton", "GalacticraftCore.EvolvedFireSpider",
			"GalacticraftCore.EvolvedEnderman", "GalacticraftCore.Tentacles",
			"GalacticraftCore.EvolvedColdBlaze", "GalacticraftCore.Crawler",
			"GalacticraftCore.EvolvedBlaze", "GalacticraftCore.AlienVillager",
			"GalacticraftMars.CreeperBoss", "CaveSpider"
	);
	
	@SubscribeEvent
	public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
		if (event.entity != null && !bannedEntities.isEmpty() && bannedEntities.contains(EntityList.getEntityString(event.entity))) {
			event.world.removeEntity(event.entity);
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "=================================================="));
		event.player.addChatMessage(new ChatComponentText(" "));
		event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.BOLD + Language.translate("welcome.0", "Welcome to IMPACT")));
		event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + Language.translate("welcome.1", "Please bring comments to") + " " + EnumChatFormatting.AQUA + "https://discord.gg/bMf2qvd"));
		event.player.addChatMessage(new ChatComponentText(" "));
		event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "=================================================="));
	}
}