//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.additions;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class WE_ChunkSmartLight extends Chunk {
	public WE_ChunkSmartLight(World p1, Block[] p2, byte[] p3, int p4, int p5) {
		super(p1, p2, p3, p4, p5);
	}

	public void generateSkylightMap() {
		int i = getTopFilledSegment();
		this.heightMapMinimum = Integer.MAX_VALUE;

		for (int j = 0; j < 16; j++) {
			for (int k = 0; k < 16; k++) {
				this.precipitationHeightMap[j + (k << 4)] = -999;

				for (int l = i + 16; l > 0; l--) {
					if (func_150808_b(j, l - 1, k) != 0) {
						this.heightMap[k << 4 | j] = l;
						if (l < this.heightMapMinimum)
							this.heightMapMinimum = l;
						break;
					}
				}
				if (!this.worldObj.provider.hasNoSky) {
					int k1 = 15, i1 = i + 15;
					do {
						int j1 = func_150808_b(j, i1, k);
						if (j1 == 0 && k1 != 15) {
							j1 = 1;
						}
						k1 -= j1;
						if (k1 <= 0)
							continue;
						ExtendedBlockStorage extendedblockstorage = getBlockStorageArray()[i1 >> 4];
						if (extendedblockstorage == null)
							continue;
						extendedblockstorage.setExtSkylightValue(j, i1 & 0xF, k, k1);
						this.worldObj.func_147479_m((this.xPosition << 4) + j, i1, (this.zPosition << 4) + k);


						--i1;
					} while (i1 > 0 && k1 > 0);
				}
			}
		}


		this.isModified = true;
	}
}