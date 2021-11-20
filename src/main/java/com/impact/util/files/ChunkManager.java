package com.impact.util.files;

import com.impact.mods.gregtech.enums.BiomesOreGenerator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkManager {

    public static void chunkSave(World w, Chunk chunk, NBTTagCompound nbt) {
        BiomesOreGenerator.save(chunk, nbt);
    }

    public static void chunkLoad(World w, Chunk chunk, NBTTagCompound nbt) {
        BiomesOreGenerator.load(chunk, nbt);
    }
}