package com.impact.mixins.vanilla;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(BlockHopper.class)
public class BlockHopper_Mixin extends Block {
	
	@Unique
	private static final EnumMap<? extends EnumFacing, List<AxisAlignedBB>> bounds;
	
	static {
		List<AxisAlignedBB> commonBounds = ImmutableList.of(
				makeAABB(0, 10, 0, 16, 16, 16),
				makeAABB(4, 4, 4, 12, 10, 12)
		);
		bounds = (EnumMap) Stream.of(EnumFacing.values()).filter((t) -> t != EnumFacing.UP).collect(Collectors.toMap((a) -> {
			return a;
		}, (a) -> {
			return new ArrayList(commonBounds);
		}, (u, v) -> {
			throw new IllegalStateException();
		}, () -> {
			return new EnumMap(EnumFacing.class);
		}));
		bounds.get(EnumFacing.DOWN).add(makeAABB(6, 0, 6, 10, 4, 10));
		bounds.get(EnumFacing.NORTH).add(makeAABB(6, 4, 0, 10, 8, 4));
		bounds.get(EnumFacing.SOUTH).add(makeAABB(6, 4, 12, 10, 8, 16));
		bounds.get(EnumFacing.WEST).add(makeAABB(0, 4, 6, 4, 8, 10));
		bounds.get(EnumFacing.EAST).add(makeAABB(12, 4, 6, 16, 8, 10));
	}
	
	protected BlockHopper_Mixin(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	private static AxisAlignedBB makeAABB(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		return AxisAlignedBB.getBoundingBox((float) fromX / 16.0F, (float) fromY / 16.0F, (float) fromZ / 16.0F, (float) toX / 16.0F, (float) toY / 16.0F, (float) toZ / 16.0F);
	}
	
	private static MovingObjectPosition rayTrace(Vec3 pos, Vec3 start, Vec3 end, AxisAlignedBB boundingBox) {
		Vec3 vec3d = start.addVector(-pos.xCoord, -pos.yCoord, -pos.zCoord);
		Vec3 vec3d1 = end.addVector(-pos.xCoord, -pos.yCoord, -pos.zCoord);
		MovingObjectPosition raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
		if (raytraceresult == null) {
			return null;
		} else {
			Vec3 res = raytraceresult.hitVec.addVector(pos.xCoord, pos.yCoord, pos.zCoord);
			return new MovingObjectPosition((int) res.xCoord, (int) res.yCoord, (int) res.zCoord, raytraceresult.sideHit, pos);
		}
	}
	
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
		Vec3 pos = Vec3.createVectorHelper(x, y, z);
		EnumFacing facing = EnumFacing.values()[BlockHopper.getDirectionFromMetadata(world.getBlockMetadata(x, y, z))];
		return ((List) bounds.get(facing)).stream().map((bb) -> rayTrace(pos, start, end, (AxisAlignedBB) bb)).anyMatch(Objects::nonNull) ? super.collisionRayTrace(world, x, y, z, start, end) : null;
	}
}
