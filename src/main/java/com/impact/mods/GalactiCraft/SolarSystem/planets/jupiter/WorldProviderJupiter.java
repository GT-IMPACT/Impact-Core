//package com.impact.mods.GalactiCraft.SolarSystem.planets.jupiter;
//
//import com.impact.mods.GalactiCraft.GC_Config;
//import com.impact.mods.GalactiCraft.SolarSystemRegister;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.EmptyBiomeDecorator;
//import cpw.mods.fml.client.FMLClientHandler;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
//import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
//import micdoodle8.mods.galacticraft.api.vector.Vector3;
//import micdoodle8.mods.galacticraft.api.world.*;
//import micdoodle8.mods.galacticraft.core.entities.EntityLander;
//import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
//import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.EntityClientPlayerMP;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldServer;
//import net.minecraft.world.biome.WorldChunkManager;
//import net.minecraft.world.biome.WorldChunkManagerHell;
//import net.minecraft.world.chunk.IChunkProvider;
//import ru.vamig.worldengine.WE_Biome;
//import ru.vamig.worldengine.WE_ChunkProvider;
//import ru.vamig.worldengine.WE_WorldProvider;
//
//import java.util.Random;
//
//import static com.impact.util.Utilits.calculateGravity;
//
//public class WorldProviderJupiter extends WE_WorldProvider implements IGalacticraftWorldProvider, IExitHeight, ISolarLevel, ITeleportType {
//
//    public void registerWorldChunkManager() {
//        this.dimensionId = GC_Config.dimID_Jupiter;
//        this.worldChunkMgr = new WorldChunkManagerJupiter();
//    }
//
//    public String getDimensionName() {
//        return "Jupiter";
//    }
//
//    @SideOnly(Side.CLIENT)
//    public float getCloudHeight() {
//        return 560.0F;
//    }
//
//    public float getGravity() {
//        return calculateGravity(23.95F);
//    }
//
//    public double getMeteorFrequency() {
//        return 0.0D;
//    }
//
//    public double getFuelUsageMultiplier() {
//        return 2.5D;
//    }
//
//    public boolean canSpaceshipTierPass(int tier) {
//        return (tier >= SolarSystemRegister.pJupiter.getTierRequirement());
//    }
//
//    public float getFallDamageModifier() {
//        return 2.5F;
//    }
//
//    public float getSoundVolReductionAmount() {
//        return 0.5F;
//    }
//
//    public float getThermalLevelModifier() {
//        return 5.0F;
//    }
//
//    public float getWindLevel() {
//        return 16.0F;
//    }
//
//    public int AtmosphericPressure() {
//        return 4;
//    }
//
//    public boolean SolarRadiation() {
//        Minecraft minecraft = FMLClientHandler.instance().getClient();
//        EntityClientPlayerMP entityClientPlayerMP = minecraft.thePlayer;
//        return !((entityClientPlayerMP).posY <= 200.0D);
//    }
//
//    public double getHorizon() {
//        return 32.0D;
//    }
//
//    public int getAverageGroundLevel() {
//        return 16;
//    }
//
//    public CelestialBody getCelestialBody() {
//        return SolarSystemRegister.pJupiter;
//    }
//
//    public Vector3 getFogColor() {
//        return new Vector3(0.7490196078431373D, 0.6D, 0.14901960784313725D);
//    }
//
//    public Vector3 getSkyColor() {
//        return new Vector3(1.0D, 0.8509803921568627D, 0.4D);
//    }
//
//    public boolean canRainOrSnow() {
//        return false;
//    }
//
//    public boolean hasSunset() {
//        return false;
//    }
//
//    public long getDayLength() {
//        return 9553L;
//    }
//
//    public Class<? extends IChunkProvider> getChunkProviderClass() {
//        return JupiterChunkProvider.class;
//    }
//
//    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
//        return WorldChunkManagerHell.class;
//    }
//
//    public double getYCoordinateToTeleport() {
//        return 3000.0D;
//    }
//
//    public double getSolarEnergyMultiplier() {
//        return 0.0D;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public float getStarBrightness(float par1) {
//        return 0.0F;
//    }
//
//    public boolean hasBreathableAtmosphere() {
//        return false;
//    }
//
//    public boolean netherPortalsOperational() {
//        return false;
//    }
//
//    public boolean isGasPresent(IAtmosphericGas gas) {
//        return (gas == IAtmosphericGas.HYDROGEN || gas == IAtmosphericGas.HELIUM);
//    }
//
//    public float getSolarSize() {
//        return 0.1F;
//    }
//
//    public void genSettings(WE_ChunkProvider cp) {
//        cp.createChunkGen_List.clear();
//        cp.decorateChunkGen_List.clear();
//        cp.createChunkGen_List.add(new WE_JupiterGenerator());
//        WE_Biome.addBiomeToGeneration(cp, new JupiterBiome());
//    }
//
//    @Override
//    public BiomeDecoratorSpace getDecorator() {
//        return new EmptyBiomeDecorator();
//    }
//
//    @Override
//    public boolean canRespawnHere() {
//        return false;
//    }
//
//    @Override
//    public int getRespawnDimension(EntityPlayerMP player) {
//        return this.shouldForceRespawn() ? this.dimensionId : 0;
//    }
//
//    //region ITeleportType
//    @Override
//    public boolean useParachute() {
//        return ConfigManagerCore.disableLander;
//    }
//
//    @Override
//    public Vector3 getPlayerSpawnLocation(WorldServer server, EntityPlayerMP player) {
//        if (player != null) {
//            GCPlayerStats stats = GCPlayerStats.get(player);
//            double x = stats.coordsTeleportedFromX;
//            double z = stats.coordsTeleportedFromZ;
//            int limit = ConfigManagerCore.otherPlanetWorldBorders - 2;
//            if (limit > 20) {
//                if (x > limit) {
//                    z *= limit / x;
//                    x = limit;
//                } else if (x < -limit) {
//                    z *= -limit / x;
//                    x = -limit;
//                }
//                if (z > limit) {
//                    x *= limit / z;
//                    z = limit;
//                } else if (z < -limit) {
//                    x *= -limit / z;
//                    z = -limit;
//                }
//            }
//            return new Vector3(x, ConfigManagerCore.disableLander ? 250.0 : 900.0, z);
//        }
//
//        return null;
//    }
//
//    @Override
//    public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
//        return new Vector3(entity.posX, ConfigManagerCore.disableLander ? 250.0 : 900.0, entity.posZ);
//    }
//
//    @Override
//    public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
//        if (ConfigManagerCore.disableLander) {
//            final double x = (rand.nextDouble() * 2 - 1.0D) * 4.0D;
//            final double z = (rand.nextDouble() * 2 - 1.0D) * 4.0D;
//            return new Vector3(player.posX + x, 220.0D, player.posZ + z);
//        }
//        return null;
//    }
//
//    @Override
//    public void onSpaceDimensionChanged(World world, EntityPlayerMP player, boolean ridingAutoRocket) {
//        GCPlayerStats stats = GCPlayerStats.get(player);
//        if (!ridingAutoRocket && !ConfigManagerCore.disableLander && stats.teleportCooldown <= 0) {
//            if (player.capabilities.isFlying) {
//                player.capabilities.isFlying = false;
//            }
//
//            EntityLander lander = new EntityLander(player);
//            lander.setPosition(player.posX, player.posY, player.posZ);
//
//            if (!world.isRemote) {
//                world.spawnEntityInWorld(lander);
//            }
//
//            stats.teleportCooldown = 10;
//        }
//    }
//
//    @Override
//    public void setupAdventureSpawn(EntityPlayerMP player) {
//    }
//    //endregion
//
//}
