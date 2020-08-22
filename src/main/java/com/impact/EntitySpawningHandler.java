package com.impact;


import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityEnderman;
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
            "EvolvedSpider",
            "EvolvedZombie",
            "EvolvedCreeper",
            "EvolvedSkeleton",
            "EvolvedSkeletonBoss",
            "AlienVillager",
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
