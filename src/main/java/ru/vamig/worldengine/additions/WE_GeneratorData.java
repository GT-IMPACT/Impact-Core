//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.additions;

import net.minecraft.block.Block;
import ru.vamig.worldengine.*;

public class WE_GeneratorData {
	public WE_ChunkProvider chunkProvider;
	public Block[] chunkBlocks;
	public byte[] chunkBlocksMeta;
	public long chunk_X;
	public long chunk_Z;
	public WE_Biome[][] biomes;
	public int cr_x;
	public int cr_y;
	public int cr_z;

	public WE_GeneratorData(WE_ChunkProvider cp, Block[] cb, byte[] cbm, long cx, long cz, WE_Biome[][] b, int x, int y, int z) {
		this.chunkProvider = cp;

		this.chunkBlocks = cb;
		this.chunkBlocksMeta = cbm;

		this.chunk_X = cx;
		this.chunk_Z = cz;

		this.biomes = b;

		this.cr_x = x;
		this.cr_y = y;
		this.cr_z = z;
	}
}