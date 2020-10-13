//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine;

import cpw.mods.fml.common.IWorldGenerator;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
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
    public World worldObj;
    public Random rand;
    public List<WE_CreateChunkGen> createChunkGen_List = new ArrayList<>();
    public List<WE_CreateChunkGen_InXZ> createChunkGen_InXZ_List = new ArrayList<>();
    public List<WE_CreateChunkGen_InXYZ> createChunkGen_InXYZ_List = new ArrayList<>();
    public List<IWorldGenerator> decorateChunkGen_List = new ArrayList<>();
    public List<MapGenBaseMeta> worldGenerators = new ArrayList<>();
    public List<WE_Biome> biomesList = new ArrayList<>();
    public WE_Biome standardBiomeOnMap;
    public double biomemapPersistence = 1.0D;
    public double biomemapScaleX = 1.0D;
    public double biomemapScaleY = 1.0D;
    public int biomemapNumberOfOctaves = 1;
    public WE_Biome currentbiome;
    private BiomeDecoratorSpace decorator;


    public WE_ChunkProvider(WE_WorldProvider wp) {
        super(wp.worldObj, wp.getSeed(), wp.worldObj.getWorldInfo().isMapFeaturesEnabled());
        this.worldObj = wp.worldObj;
        this.rand = new Random(wp.getSeed());


        this.createChunkGen_List.add(new WE_TerrainGenerator());
        this.createChunkGen_List.add(new WE_CaveGen());
        this.createChunkGen_List.add(new WE_RavineGen());

        WE_LakeGen lavaLakes = new WE_LakeGen();
        lavaLakes.lakeBlock = Blocks.lava;
        lavaLakes.fGen = false;
        lavaLakes.maxY = 32;
        this.decorateChunkGen_List.add(lavaLakes);

        System.out.println("WorldEngine: -Applying your WorldEngine settings...");
        wp.genSettings(this);
        this.decorator = wp.getDecorator();
        WE_Biome.setChunkProvider(this);
        System.out.println("WorldEngine: -WorldEngine is configured successfully!");
    }


    public Chunk provideChunk(int chunkX, int chunkZ) {
        long chunk_X = chunkX * 16L, chunk_Z = chunkZ * 16L;
        WE_Biome.setChunkProvider(this);
        Block[] chunkBlocks = new Block[65536];
        byte[] chunkBlocksMeta = new byte[65536];
        this.rand.setSeed(this.worldObj.getSeed() * chunkX ^ (3 + chunkZ) ^ 0x758CF9L);

        WE_Biome[][] chunkBiomes = new WE_Biome[16][16];
        for (int j = 0; j < 16; j++) {
            for (int z = 0; z < 16; z++) {

                chunkBiomes[j][z] = WE_Biome.getBiomeAt(this, chunk_X + j, chunk_Z + z);
                this.currentbiome = chunkBiomes[j][z];
            }
        }


        for (int i = 0; i < this.createChunkGen_List.size(); i++) {
            this.createChunkGen_List.get(i).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, 0, 0, 0));
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int k;
                for (k = 0; k < this.createChunkGen_InXZ_List.size(); k++) {
                    this.createChunkGen_InXZ_List.get(k).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, 0, z));
                }
                for (k = 0; k < (chunkBiomes[x][z]).createChunkGen_InXZ_List.size(); k++) {
                    (chunkBiomes[x][z]).createChunkGen_InXZ_List.get(k).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, 0, z));
                }
                for (int y = 255; y >= 0; y--) {
                    int m;
                    for (m = 0; m < this.createChunkGen_InXYZ_List.size(); m++)
                        this.createChunkGen_InXYZ_List.get(m).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, y, z));
                    for (m = 0; m < (chunkBiomes[x][z]).createChunkGen_InXYZ_List.size(); m++) {
                        (chunkBiomes[x][z]).createChunkGen_InXYZ_List.get(m).gen(new WE_GeneratorData(this, chunkBlocks, chunkBlocksMeta, chunk_X, chunk_Z, chunkBiomes, x, y, z));
                    }
                }
            }
        }


        for (MapGenBaseMeta generator : this.worldGenerators) {
            generator.generate(this, this.worldObj, chunkX, chunkZ, chunkBlocks, chunkBlocksMeta);
        }


        WE_ChunkSmartLight chunk = new WE_ChunkSmartLight(this.worldObj, chunkBlocks, chunkBlocksMeta, chunkX, chunkZ);
        chunk.generateSkylightMap();
        return chunk;
    }


    public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;

        boolean flag = false;
        this.rand.setSeed(this.worldObj.getSeed() * chunkX + chunkZ ^ 0x248B36L);
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, this.worldObj, this.rand, chunkX, chunkZ, flag));


        long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        long var9 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(chunkX * var7 + chunkZ * var9 ^ this.worldObj.getSeed());

        for (int i = 0; i < this.decorateChunkGen_List.size(); i++) {
            this.decorateChunkGen_List.get(i).generate(this.rand, chunkX, chunkZ, this.worldObj, this, this);
        }
        WE_Biome b = WE_Biome.getBiomeAt(this, chunkX * 16L + this.rand.nextInt(16), chunkZ * 16L + this.rand.nextInt(16));
        for (int j = 0; j < b.decorateChunkGen_List.size(); j++) {
            b.decorateChunkGen_List.get(j).generate(this.rand, chunkX, chunkZ, this.worldObj, this, this);
        }

        decorateWorld(this.worldObj, this.rand, chunkX * 16, chunkZ * 16);


        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, this.worldObj, this.rand, chunkX, chunkZ, flag));

        BlockFalling.fallInstantly = false;
    }


    public void decorateWorld(World world, Random rand, int x, int z) {
        getBiomeGenerator().decorate(world, rand, x, z);
    }


    protected BiomeDecoratorSpace getBiomeGenerator() {
        return this.decorator;
    }

    public void genSetBlock(Block[] chunkBlocks, byte[] chunkBlocksMeta, int x, int y, int z, Block block, byte meta) {
        if (x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15) {
            chunkBlocks[(x * 16 + z) * 256 + y] = block;
            chunkBlocksMeta[(x * 16 + z) * 256 + y] = meta;
        }
    }

    public Block genReturnBlock(Block[] chunkBlocks, int x, int y, int z) {
        if (x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15) {
            return chunkBlocks[(x * 16 + z) * 256 + y];
        }
        return null;
    }

    public byte genReturnBlockMeta(byte[] chunkBlocksMeta, int x, int y, int z) {
        if (x >= 0 && x <= 15 && y >= 0 && y <= 255 && z >= 0 && z <= 15) {
            return chunkBlocksMeta[(x * 16 + z) * 256 + y];
        }
        return 0;
    }


    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int i, int j, int k) {
        WE_Biome biome = WE_Biome.getBiomeAt(this, i, k);

        if (biome != null) {


            if (biome != null) {
                return biome.getSpawnableList(par1EnumCreatureType);
            }
        }
        return null;
    }
}