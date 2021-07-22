package com.impact.mods.opencomputers;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityLaunchController;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Driver_GCLaunchController extends DriverSidedTileEntity {
	
	@Override
	public Class<?> getTileEntityClass() {
		return TileEntityLaunchController.class;
	}
	
	@Override
	public boolean worksWith(World world, int x, int y, int z, ForgeDirection side) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		return tileEntity instanceof TileEntityLaunchController;
	}
	
	@Override
	public ManagedEnvironment createEnvironment(World world, int x, int y, int z, ForgeDirection side) {
		return new ManagedEnvironmentReactor(((TileEntityLaunchController) world.getTileEntity(x, y, z)), "gc_launch_controller");
	}
	
	public static class ManagedEnvironmentReactor extends ManagedEnvironmentTile<TileEntityLaunchController> {
		
		public ManagedEnvironmentReactor(TileEntityLaunchController tile, String name) {
			super(tile, name);
		}
		
		@Override
		public int priority() {
			return 2;
		}
		
		@Callback(doc = "function(frequency:number):boolean -- ")
		public Object[] setFrequency(Context context, Arguments args) {
			int freq = args.checkInteger(0);
			try {
				tile.setFrequency(freq);
				boolean checkFreqValid = tile.destFrequency > 0;
				return new Object[]{checkFreqValid};
			} catch (Throwable e) {
				return new Object[]{false, "invalid"};
			}
		}

		@Callback(doc = "function():number -- ")
		public Object[] getFrequency(Context context, Arguments args) {
			try {
				return new Object[]{tile.destFrequency};
			} catch (Throwable e) {
				return new Object[]{-1};
			}
		}

		@Callback(doc = "function(frequency:number):boolean -- ")
		public Object[] setDestinationFrequency(Context context, Arguments args) {
			int freq = args.checkInteger(0);
			try {
				tile.setDestinationFrequency(freq);
				boolean checkFreqValid = tile.destFrequency > 0;
				return new Object[]{checkFreqValid};
			} catch (Throwable e) {
				return new Object[]{false, "invalid"};
			}
		}

		@Callback(doc = "function():number -- ")
		public Object[] getDestinationFrequency(Context context, Arguments args) {
			try {
				return new Object[]{tile.destFrequency};
			} catch (Throwable e) {
				return new Object[]{-1};
			}
		}

		@Callback(doc = "function():boolean -- ")
		public Object[] setLaunchWhen(Context context, Arguments args) {
			try {
				tile.launchSchedulingEnabled = args.checkBoolean(0);
				return new Object[]{};
			} catch (Throwable e) {
				return new Object[]{false, "invalid"};
			}
		}

		@Callback(doc = "function():boolean -- ")
		public Object[] getLaunchWhen(Context context, Arguments args) {
			try {
				return new Object[]{tile.launchSchedulingEnabled};
			} catch (Throwable e) {
				return new Object[]{false, "invalid"};
			}
		}

		@Callback(doc = "function():boolean -- ")
		public Object[] setRemovePad(Context context, Arguments args) {
			try {
				tile.launchPadRemovalDisabled = args.checkBoolean(0);
				return new Object[]{};
			} catch (Throwable e) {
				return new Object[]{false, "invalid"};
			}
		}

		@Callback(doc = "function():boolean -- ")
		public Object[] getRemovePad(Context context, Arguments args) {
			try {
				return new Object[]{tile.launchPadRemovalDisabled};
			} catch (Throwable e) {
				return new Object[]{false, "invalid"};
			}
		}
	}
}