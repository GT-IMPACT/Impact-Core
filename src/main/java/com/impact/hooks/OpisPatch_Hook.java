package com.impact.hooks;

import com.google.common.collect.HashBasedTable;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.blocks.GT_Block_Machines;
import mcp.mobius.mobiuscore.profiler.ProfilerSection;
import mcp.mobius.opis.data.holders.basetypes.CoordinatesBlock;
import mcp.mobius.opis.data.holders.newtypes.DataBlockTileEntityPerClass;
import mcp.mobius.opis.data.managers.TileEntityManager;
import mcp.mobius.opis.data.profilers.ProfilerTileEntityUpdate;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Andrey Solodovnikov (https://github.com/AndreySolodovnikov)
 */
public class OpisPatch_Hook {
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static ArrayList<DataBlockTileEntityPerClass> getCumulativeTimingTileEntities(TileEntityManager ignored) { //shitty decompile but it works
		HashBasedTable<Integer, Integer, DataBlockTileEntityPerClass> data = HashBasedTable.create();
		CoordinatesBlock coord;
		int id;
		int meta;
		Block block;
		TileEntity tileEntity;
		for (Iterator<CoordinatesBlock> iterator = ((ProfilerTileEntityUpdate) ProfilerSection.TILEENT_UPDATETIME.getProfiler()).data.keySet().iterator();
			 iterator.hasNext();
			 data.get(id, meta).add(((ProfilerTileEntityUpdate) ProfilerSection.TILEENT_UPDATETIME.getProfiler()).data.get(coord).getGeometricMean())) {
			coord = iterator.next();
			World world = DimensionManager.getWorld(coord.dim);
			id = Block.getIdFromBlock(block = world.getBlock(coord.x, coord.y, coord.z));
			if (block instanceof GT_Block_Machines && (tileEntity = world.getTileEntity(coord.x, coord.y, coord.z)) instanceof IGregTechTileEntity) {
				meta = ((IGregTechTileEntity) tileEntity).getMetaTileID();
			} else {
				meta = world.getBlockMetadata(coord.x, coord.y, coord.z);
			}
			if (!data.contains(id, meta)) {
				data.put(id, meta, new DataBlockTileEntityPerClass(id, meta));
			}
		}
		return new ArrayList(data.values());
	}
}