package com.impact.mods.opencomputers;

import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Driver_LapPowerStation extends DriverSidedTileEntity {
	
	@Override
	public Class<?> getTileEntityClass() {
		return BaseMetaTileEntity.class;
	}
	
	@Override
	public boolean worksWith(World world, int x, int y, int z, ForgeDirection side) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		return tileEntity instanceof BaseMetaTileEntity
				&& ((BaseMetaTileEntity) tileEntity).getMetaTileEntity() instanceof GTMTE_LapPowerStation;
	}
	
	@Override
	public ManagedEnvironment createEnvironment(World world, int x, int y, int z, ForgeDirection side) {
		return new ManagedEnvironmentLSC(((BaseMetaTileEntity) world.getTileEntity(x, y, z)), "impact_lsc");
	}
	
	public static class ManagedEnvironmentLSC extends ManagedEnvironmentTile<BaseMetaTileEntity> {
		
		public ManagedEnvironmentLSC(BaseMetaTileEntity tile, String name) {
			super(tile, name);
		}
		
		@Override
		public int priority() {
			return 2;
		}
		
		@Callback(doc = "function():array -- Get all info. Stored, Capacity, Input, Output")
		public Object[] getStoredLSC(Context context, Arguments args) {
			try {
				long stored = ((GTMTE_LapPowerStation) tile.getMetaTileEntity()).stored;
				long capacity = ((GTMTE_LapPowerStation) tile.getMetaTileEntity()).capacity;
				long in = ((GTMTE_LapPowerStation) tile.getMetaTileEntity()).intputLastTick;
				long out = ((GTMTE_LapPowerStation) tile.getMetaTileEntity()).outputLastTick;
				return new Object[]{stored, capacity, in, out};
			} catch (Throwable e) {
				return new Object[]{"invalid argument"};
			}
		}
		
		@Callback(doc = "function():number -- Get capacity percent.")
		public Object[] getChargePercent(Context context, Arguments args) {
			try {
				long capacity = ((GTMTE_LapPowerStation) tile.getMetaTileEntity()).capacity;
				long stored = ((GTMTE_LapPowerStation) tile.getMetaTileEntity()).stored;
				int charhgePercent = (int) (100 * stored / capacity);
				return new Object[]{charhgePercent};
			} catch (Throwable e) {
				return new Object[]{"invalid argument"};
			}
		}
		
		@Callback(doc = "function(per:number):number -- Get selected stored percent. " +
				"stored < per = -1, stored == per = 0, stored > per = 1")
		public Object[] getSelectChargePercent(Context context, Arguments args) {
			int aCharge = args.checkInteger(0);
			try {
				long capacity = ((GTMTE_LapPowerStation) tile.getMetaTileEntity()).capacity;
				long stored = ((GTMTE_LapPowerStation) tile.getMetaTileEntity()).stored;
				int charhgePercent = (int) (100 * stored / capacity);
				return new Object[]{Integer.compare(charhgePercent, aCharge)};
			} catch (Throwable e) {
				return new Object[]{"invalid argument"};
			}
		}
	}
}