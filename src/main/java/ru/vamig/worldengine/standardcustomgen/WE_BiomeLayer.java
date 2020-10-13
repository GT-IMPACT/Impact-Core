//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.standardcustomgen;

import net.minecraft.block.Block;
import ru.vamig.worldengine.additions.WE_CreateChunkGen_InXZ;
import ru.vamig.worldengine.additions.WE_GeneratorData;

import java.util.ArrayList;
import java.util.List;

public class WE_BiomeLayer extends WE_CreateChunkGen_InXZ {

    public List<Block> layerBlock = new ArrayList<>();
    public List<Byte> layerBlockMeta = new ArrayList<>();

    public List<Block> layerReplacingBlock = new ArrayList<>();
    public List<Byte> layerReplacingBlockMeta = new ArrayList<>();

    public List<Integer> layerStart = new ArrayList<>();
    public List<Integer> layerRandomStart = new ArrayList<>();
    public List<Integer> layerEnd = new ArrayList<>();
    public List<Integer> layerRandomEnd = new ArrayList<>();


    public List<Boolean> layerUnderWaterGen = new ArrayList<>();

    public void add(Block block, byte meta, Block replacingBlock, byte replacingBlockMeta, int start, int r_start, int end, int r_end, boolean underWater) {
        this.layerBlock.add(block);
        this.layerBlockMeta.add(meta);

        this.layerReplacingBlock.add(replacingBlock);
        this.layerReplacingBlockMeta.add(replacingBlockMeta);

        this.layerStart.add(start);
        this.layerRandomStart.add(r_start);
        this.layerEnd.add(end);
        this.layerRandomEnd.add(r_end);

        this.layerUnderWaterGen.add(underWater);
    }

    public void add(Block block, byte meta, int start, int r_start, int end, int r_end, boolean underWater) {
        this.layerBlock.add(block);
        this.layerBlockMeta.add(meta);

        this.layerReplacingBlock.add(null);
        this.layerReplacingBlockMeta.add((byte) -1);

        this.layerStart.add(start);
        this.layerRandomStart.add(r_start);
        this.layerEnd.add(end);
        this.layerRandomEnd.add(r_end);

        this.layerUnderWaterGen.add(underWater);
    }


    public void gen(WE_GeneratorData data) {
        for (int i = 0; i < this.layerBlock.size(); i++) {
            int startPoint = this.layerStart.get(i), endPoint = this.layerEnd.get(i);
            if (this.layerStart.get(i) < 0 || this.layerEnd.get(i) < 0) {
                if (this.layerStart.get(i) < -255)
                    startPoint = Math.abs(this.layerStart.get(i) % 256);
                if (this.layerEnd.get(i) < -255)
                    endPoint = Math.abs(this.layerEnd.get(i) % 256);
                for (int y = 255; y >= 0; y--) {
                    if (getBlock(data, y) != null) {
                        if (this.layerStart.get(i) < 0)
                            startPoint += y;
                        if (this.layerEnd.get(i) < 0)
                            endPoint += y;
                        break;
                    }
                }
            }
            if (this.layerRandomStart.get(i) > 0) {
                startPoint += data.chunkProvider.rand.nextInt(this.layerRandomStart.get(i) + 1);
            } else if (this.layerRandomStart.get(i) < 0) {
                startPoint -= data.chunkProvider.rand.nextInt(Math.abs(this.layerRandomStart.get(i)) + 1);
            }
            if (this.layerRandomEnd.get(i) > 0) {
                endPoint += data.chunkProvider.rand.nextInt(this.layerRandomEnd.get(i) + 1);
            } else if (this.layerRandomEnd.get(i) < 0) {
                endPoint -= data.chunkProvider.rand.nextInt(Math.abs(this.layerRandomEnd.get(i)) + 1);
            }
            if (!this.layerUnderWaterGen.get(i) && getBlock(data, startPoint + 1) != null &&
                    getBlock(data, startPoint + 1).getMaterial().isLiquid()) {
                return;
            }
            if (this.layerReplacingBlockMeta.get(i) != -1) {
                for (int y = startPoint; y >= endPoint; y--) {
                    if (getBlock(data, y) == this.layerReplacingBlock.get(i) && getBlockMeta(data, y) == this.layerReplacingBlockMeta.get(i))
                        setBlock(data, this.layerBlock.get(i), this.layerBlockMeta.get(i), y);
                }
            } else {
                for (int y = startPoint; y >= endPoint; y--)
                    setBlock(data, this.layerBlock.get(i), this.layerBlockMeta.get(i), y);
            }

        }
    }
}