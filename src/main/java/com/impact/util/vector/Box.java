package com.impact.util.vector;

import com.impact.impact;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Box {
	
	public int xMin = 0;
	public int yMin = 0;
	public int zMin = 0;
	public int xMax = 0;
	public int yMax = 0;
	public int zMax = 0;
	public boolean complete;
	public boolean render;
	public Map<Block, int[]> blocks = new HashMap<>();
	public Map<TileEntity, int[]> tiles = new HashMap<>();
	public Map<IGregTechTileEntity, int[]> gtTiles = new HashMap<>();
	
	public Box(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
		initialize(xMin, yMin, zMin, xMax, yMax, zMax);
	}
	
	public Box() {
		reset();
	}
	
	public void initialize(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
		if (xMin < xMax) {
			this.xMin = xMin;
			this.xMax = xMax;
		} else {
			this.xMin = xMax;
			this.xMax = xMin;
		}
		
		if (yMin < yMax) {
			this.yMin = yMin;
			this.yMax = yMax;
		} else {
			this.yMin = yMax;
			this.yMax = yMin;
		}
		
		if (zMin < zMax) {
			this.zMin = zMin;
			this.zMax = zMax;
		} else {
			this.zMin = zMax;
			this.zMax = zMin;
		}
		
		this.complete = xMin != 0 && yMin != 0 && zMin != 0 && xMax != 0 && yMax != 0 && zMax != 0;
		
		
	}
	
	public void initialize(Box box) {
		this.initialize(box.xMin, box.yMin, box.zMin, box.xMax, box.yMax, box.zMax);
	}
	
	public Box rotateLeft() {
		Box nBox = new Box();
		nBox.xMin = this.sizeZ() - 1 - this.zMin;
		nBox.yMin = this.yMin;
		nBox.zMin = this.xMin;
		nBox.xMax = this.sizeZ() - 1 - this.zMax;
		nBox.yMax = this.yMax;
		nBox.zMax = this.xMax;
		nBox.reorder();
		return nBox;
	}
	
	public void reorder() {
		int tmp;
		if (this.xMin > this.xMax) {
			tmp       = this.xMin;
			this.xMin = this.xMax;
			this.xMax = tmp;
		}
		if (this.yMin > this.yMax) {
			tmp       = this.yMin;
			this.yMin = this.yMax;
			this.yMax = tmp;
		}
		if (this.zMin > this.zMax) {
			tmp       = this.zMin;
			this.zMin = this.zMax;
			this.zMax = tmp;
		}
	}
	
	public int sizeX() {
		return this.xMax - this.xMin + 1;
	}
	
	public int sizeY() {
		return this.yMax - this.yMin + 1;
	}
	
	public int sizeZ() {
		return this.zMax - this.zMin + 1;
	}
	
	public void setBlocksInArea(World world) {
		for (int x = this.xMin; x <= this.xMax; ++x) {
			for (int y = this.yMin; y <= this.yMax; ++y) {
				for (int z = this.zMin; z <= this.zMax; ++z) {
					Block block = world.getBlock(x, y, z);
					if (block != Blocks.air && !block.getUnlocalizedName().equals("air") && block != Blocks.bedrock) {
						int meta = world.getBlockMetadata(x, y, z);
						
						int xx = this.xMax - x + 1;
						int yy = this.yMax - y + 1;
						int zz = this.zMax - z + 1;
						
						int[] arg = {meta, xx, yy, zz};
						
						blocks.put(block, arg);
					}
				}
			}
		}
	}
	
	public void setIGTInArea(World world) {
		for (int x = this.xMin; x <= this.xMax; ++x) {
			for (int y = this.yMin; y <= this.yMax; ++y) {
				for (int z = this.zMin; z <= this.zMax; ++z) {
					TileEntity te = world.getTileEntity(x, y, z);
					if (te != null) {
						if (te instanceof IGregTechTileEntity) {
							
							int xx = this.xMax - x + 1;
							int yy = this.yMax - y + 1;
							int zz = this.zMax - z + 1;
							
							int[] arg = {xx, yy, zz};
							gtTiles.put((IGregTechTileEntity) te, arg);
						}
					}
				}
			}
		}
	}
	
	public void setTilesInArea(World world) {
		for (int x = this.xMin; x <= this.xMax; ++x) {
			for (int y = this.yMin; y <= this.yMax; ++y) {
				for (int z = this.zMin; z <= this.zMax; ++z) {
					TileEntity te = world.getTileEntity(x, y, z);
					if (te != null) {
						
						int xx = this.xMax - x + 1;
						int yy = this.yMax - y + 1;
						int zz = this.zMax - z + 1;
						
						int[] arg = {xx, yy, zz};
						tiles.put(te, arg);
					}
				}
			}
		}
	}
	
	//TODO for builder
	public void setArea(IGregTechTileEntity gt) {
//		Vector3ic offset;
//		Map<TileEntity, int[]> tes = tiles;
//		Map<Block, int[]> blocks = blocks;
//
//		try {
//			for (TileEntity te : tes.keySet()) {
//				if (te != null) {
//					int[] arg = tes.get(te);
//					offset = Structure.goBuild(gt, arg[0], arg[1], arg[2]);
//
//					if (te instanceof IGregTechTileEntity) {
//						IGregTechTileEntity igt = (IGregTechTileEntity) te;
//						IMetaTileEntity greg = igt.getMetaTileEntity();
//						Block block = GregTech_API.sBlockMachines;
//						NBTTagCompound test = new NBTTagCompound();
//						greg.saveNBTData(test);
//						int meta = GregTech_API.METATILEENTITIES[igt.getMetaTileID()].getTileEntityBaseType();
//						Structure.setBlock(gt, offset, block, meta);
//						IGregTechTileEntity tile = Structure.getIGTE(gt, offset);
//						tile.setInitialValuesAsNBT(test, (short) igt.getMetaTileID());
//					}
//
//					Block block = te.blockType;
//					int meta = te.getBlockMetadata();
//					Structure.setBlock(gt, offset, block, meta);
//				}
//			}
////
////		for(Block b : blocks.keySet()) {
////			if (b != null) {
////				int[] arg = blocks.get(b);
////				w.setBlock(arg[1], arg[2], arg[3], b, arg[0], 3);
////			}
////		}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public boolean equals(Box box) {
		return xMin == box.xMin && yMin == box.yMin && zMin == box.zMin && xMax == box.xMax && yMax == box.yMax && zMax == box.zMax;
	}
	
	public void setRenderHint(World w) {
		Color clr = new Color(236, 11, 11);
		impact.proxy.remove_hint_particle(w, xMin, yMin, zMin);
		impact.proxy.hint_particleMega(w, xMin, yMin, zMin, xMax, yMax, zMax, clr, 60 * 2);
		render = true;
	}
	
	public void removeRenderHint(World w) {
		impact.proxy.remove_hint_particle(w, xMin, yMin, zMin);
		render = false;
	}
	
	public AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xMin, this.yMin, this.zMin, this.xMax, this.yMax, this.zMax);
	}
	
	public double distanceTo(Vector3i index) {
		return Math.sqrt(this.distanceToSquared(index));
	}
	
	public double distanceToSquared(Vector3i index) {
		int dx = index.x - (this.xMin + this.xMax - this.xMin + 1);
		int dy = index.y - (this.yMin + this.yMax - this.yMin + 1);
		int dz = index.z - (this.zMin + this.zMax - this.zMin + 1);
		return dx * dx + dy * dy + dz * dz;
	}
	
	public Vector3i getRandomBlockVector(Random rand) {
		int x = this.xMax > this.xMin ? this.xMin + rand.nextInt(this.xMax - this.xMin + 1) : this.xMin;
		int y = this.yMax > this.yMin ? this.yMin + rand.nextInt(this.yMax - this.yMin + 1) : this.yMin;
		int z = this.zMax > this.zMin ? this.zMin + rand.nextInt(this.zMax - this.zMin + 1) : this.zMin;
		return new Vector3i(x, y, z);
	}
	
	public void reset() {
		xMin = 0;
		yMin = 0;
		zMin = 0;
		xMax = 0;
		yMax = 0;
		zMax = 0;
	}
}