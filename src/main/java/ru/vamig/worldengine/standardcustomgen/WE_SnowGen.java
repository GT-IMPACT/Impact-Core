//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.standardcustomgen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import ru.vamig.worldengine.additions.*;

public class WE_SnowGen extends WE_CreateChunkGen_InXZ {
	public int snowPoint = 111;
	public int randomSnowPoint = 2;

	public Block snowBlock = Blocks.snow_layer;
	public byte snowBlockMeta = 0;

	public boolean genSnow = true;
	public Block iceBlock = Blocks.ice;
	public byte iceBlockMeta = 0;
	public Material freezeMaterial = Material.water;

	public int snowOnWaterRandom = 2;


	public void gen(WE_GeneratorData data) {
		for (int y = 255; y >= this.snowPoint + data.chunkProvider.rand.nextInt(this.randomSnowPoint + 1); y--) {
			if (getBlock(data, y) != null) {
				if (getBlock(data, y).getMaterial() == this.freezeMaterial) {
					setBlock(data, this.iceBlock, this.iceBlockMeta, y);
					if (this.genSnow && data.chunkProvider.rand.nextInt(this.snowOnWaterRandom + 1) == 0)
						setBlock(data, this.snowBlock, this.snowBlockMeta, y + 1);
					break;
				}
				if (this.genSnow)
					setBlock(data, this.snowBlock, this.snowBlockMeta, y + 1);
				break;
			}
		}
	}
}