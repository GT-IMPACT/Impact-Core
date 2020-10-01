package com.impact;


import com.impact.System.ImpactGuiMainMenu;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityList;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.Arrays;
import java.util.List;

public class impactEvents {

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

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent event) {
        if (event.gui instanceof net.minecraft.client.gui.GuiMainMenu) {
            event.gui = new ImpactGuiMainMenu();
        }
    }
}
