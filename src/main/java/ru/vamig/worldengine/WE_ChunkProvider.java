//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import ru.vamig.worldengine.additions.*;
import ru.vamig.worldengine.standardcustomgen.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WE_ChunkProvider extends ChunkProviderGenerate {

    public final World worldObj;
    public final Random rand;

    //////////////////
    //- Generators -//
    //////////////////
    public final List<WE_CreateChunkGen> createChunkGen_List = new ArrayList();
    public final List<WE_CreateChunkGen_InXZ> createChunkGen_InXZ_List = new ArrayList();
    public final List<WE_CreateChunkGen_InXYZ> createChunkGen_InXYZ_List = new ArrayList();
    public final List<IWorldGenerator> decorateChunkGen_List = new ArrayList();

    //////////////////////
    //- Biome Map Info -//
    //////////////////////
    public final List<WE_Biome> biomesList = new ArrayList();
    public WE_Biome standardBiomeOnMap;
    //-//
    public double biomemapPersistence = 1.0D, biomemapScaleX = 1.0D, biomemapScaleY = 1.0D;
    public int biomemapNumberOfOctaves = 1;

    /////
    //=//
    /////

    public WE_ChunkProvider(WE_WorldProvider wp) {
        super(wp.worldObj, wp.getSeed(), wp.worldObj.getWorldInfo().isMapFeaturesEnabled());
        worldObj = wp.worldObj;
        rand = new Random(wp.getSeed());

        /////
        //=//
        /////

        createChunkGen_List.add(new WE_TerrainGenerator());
        createChunkGen_List.add(new WE_CaveGen());
        createChunkGen_List.add(new WE_RavineGen());
        wp.genSettings(this);
    }

    public List getPossibleCreatures(EnumCreatureType type, int x, int y, int z) {
        WE_Biome b = WE_Biome.getBiomeAt(this, x, z);
        return /*type == EnumCreatureType.monster && this.scatteredFeatureGenerator.func_143030_a(x, y, z) ? this.scatteredFeatureGenerator.getScatteredFeatureSpawnList() :*/ b.getSpawnableList(type);
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ) {
        long chunk_X = (long) chunkX * 16L, chunk_Z = (long) chunkZ * 16L;
        Block[] chunkBlocks = new Block[65536];
        byte[] chunkBlocksMeta = new byte[65536];
        rand.setSeed(worldObj.getSeed() * (long) Math.pow(chunkX, 3) + (long) Math.pow(chunkZ, 2) * 9874L + 7684053L);
        //-//
        WE_Biome[][] chunkBiomes = new WE_Biome[16][16];
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                chunkBiomes[x][z] = WE_Biome.getBiomeAt(this, chunk_X + (long) x, chunk_Z + (long) z);

        /////
        //=//
        /////

        for (WE_CreateChunkGen we_createChunkGen : createChunkGen_List)
            we_createChunkGen.gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, 0, 0, 0));
        //-//
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++) {
                for (WE_CreateChunkGen_InXZ we_createChunkGen_inXZ : createChunkGen_InXZ_List)
                    we_createChunkGen_inXZ.gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, 0, z));
                for (int i = 0; i < chunkBiomes[x][z].createChunkGen_InXZ_List.size(); i++)
                    chunkBiomes[x][z].createChunkGen_InXZ_List.get(i).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, 0, z));
                //-//
                for (int y = 255; y >= 0; y--) {
                    for (WE_CreateChunkGen_InXYZ we_createChunkGen_inXYZ : createChunkGen_InXYZ_List)
                        we_createChunkGen_inXYZ.gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, y, z));
                    for (int i = 0; i < chunkBiomes[x][z].createChunkGen_InXYZ_List.size(); i++)
                        chunkBiomes[x][z].createChunkGen_InXYZ_List.get(i).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, y, z));
                }
            }

        /////
        //=//
        /////

        WE_ChunkSmartLight chunk = new WE_ChunkSmartLight(worldObj, chunkBlocks, chunkBlocksMeta, chunkX, chunkZ);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;
        //-//
        rand.setSeed(worldObj.getSeed() * chunkX + (long) Math.pow(chunkZ, 2) * 107L + 2394720L);
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, chunkX, chunkZ, false));

        /////
        //=//
        /////

        for (IWorldGenerator iWorldGenerator : decorateChunkGen_List)
            iWorldGenerator.generate(rand, chunkX, chunkZ, worldObj, this, this);
        //-//
        WE_Biome b = WE_Biome.getBiomeAt(this, (long) chunkX * 16L + (long) rand.nextInt(16), (long) chunkZ * 16L + (long) rand.nextInt(16));
        for (int i = 0; i < b.decorateChunkGen_List.size(); i++)
            b.decorateChunkGen_List.get(i).generate(rand, chunkX, chunkZ, worldObj, this, this);

        /////
        //=//
        /////

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, chunkX, chunkZ, false));
        //-//
        BlockFalling.fallInstantly = false;
    }

    public void genSetBlock(Block[] chunkBlocks, byte[] chunkBlocksMeta, int x, int y, int z, Block block, byte meta) {
        if (x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15) {
            int i = (x * 16 + z) * 256 + y;
            chunkBlocks[i] = block;
            chunkBlocksMeta[i] = meta;
        }
    }

    public Block genReturnBlock(Block[] chunkBlocks, int x, int y, int z) {
        if (x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15)
            return chunkBlocks[(x * 16 + z) * 256 + y];
        else
            return null;
    }

    public byte genReturnBlockMeta(byte[] chunkBlocksMeta, int x, int y, int z) {
        if (x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15)
            return chunkBlocksMeta[(x * 16 + z) * 256 + y];
        else
            return 0;
    }
}