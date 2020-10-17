package com.impact.mods.GalactiCraft.SolarSystem.planets.mars;

import net.minecraft.block.BlockFalling;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import ru.vamig.worldengine.WE_ChunkProvider;
import ru.vamig.worldengine.WE_WorldProvider;

import java.util.Random;


public class MarsChunkProvider extends WE_ChunkProvider {

    public MarsChunkProvider(WE_WorldProvider wp) {
        super(wp);
    }

    public void decoratePlanet(World world, Random rand, int chunkX, int chunkZ) {
    }

    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        BlockFalling.fallInstantly = true;
        int k = par2 * 16;
        int l = par3 * 16;
        this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(par2 * i1 + par3 * j1 ^ this.worldObj.getSeed());

        decoratePlanet(this.worldObj, this.rand, k, l);
        BlockFalling.fallInstantly = false;
    }

    public void recreateStructures(int par1, int par2) {
    }

}
