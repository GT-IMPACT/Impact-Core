package com.impact;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityList;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.Arrays;
import java.util.List;

public class EntitySpawningHandler {

    public static List<String> bannedEntities = Arrays.asList(
            "Creeper",
            "Skeleton",
            "Spider",
            "Giant",
            "Zombie",
            "Slime",
            "Ghast",
            "PigZombie",
            "Enderman",
            "Silverfish",
            "Blaze",
            "LavaSlime",
            "WitherBoss",
            "Bat",
            "Witch",
            "MushroomCow",
            "SnowMan",
            "Ozelot",
            "GalacticraftCore.EvolvedSpider",
            "GalacticraftCore.EvolvedZombie",
            "GalacticraftCore.EvolvedCreeper",
            "GalacticraftCore.EvolvedSkeleton",
            "GalacticraftCore.EvolvedSkeletonBoss",
            "GalacticraftCore.EvolvedBossBlaze",
            "GalacticraftCore.EvolvedBossGhast",
            "GalacticraftCore.EvolvedCrystalBoss",
            "GalacticraftCore.EvolvedBossSlime",
            "GalacticraftCore.EvolvedBossWolf",
            "GalacticraftCore.EvolvedFireCreeper",
            "GalacticraftCore.EvolvedFireSkeleton",
            "GalacticraftCore.EvolvedFireSpider",
            "GalacticraftCore.EvolvedEnderman",
            "GalacticraftCore.Tentacles",
            "GalacticraftCore.EvolvedColdBlaze",
            "GalacticraftCore.Crawler",
            "GalacticraftCore.EvolvedBlaze",
            "GalacticraftCore.AlienVillager",
            "GalacticraftMars.CreeperBoss",
            "CaveSpider"
    );

    @SubscribeEvent
    public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if (event.entity != null && !bannedEntities.isEmpty() && bannedEntities.contains(EntityList.getEntityString(event.entity))) {
            event.world.removeEntity(event.entity);
            event.setCanceled(true);
        }
    }
}
