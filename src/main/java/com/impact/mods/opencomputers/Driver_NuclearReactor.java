package com.impact.mods.opencomputers;

import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.items.GT_RadioactiveCell_Item.getDurabilityOfStack;

public class Driver_NuclearReactor extends DriverSidedTileEntity {
	
	@Override
	public Class<?> getTileEntityClass() {
		return BaseMetaTileEntity.class;
	}
	
	@Override
	public boolean worksWith(World world, int x, int y, int z, ForgeDirection side) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		return tileEntity instanceof BaseMetaTileEntity
				&& ((BaseMetaTileEntity) tileEntity).getMetaTileEntity() instanceof GTMTE_NuclearReactorBase;
	}
	
	@Override
	public ManagedEnvironment createEnvironment(World world, int x, int y, int z, ForgeDirection side) {
		return new ManagedEnvironmentReactor(((BaseMetaTileEntity) world.getTileEntity(x, y, z)), "impact_reactor");
	}
	
	public static class ManagedEnvironmentReactor extends ManagedEnvironmentTile<BaseMetaTileEntity> {
		
		public ManagedEnvironmentReactor(BaseMetaTileEntity tile, String name) {
			super(tile, name);
		}
		
		@Override
		public int priority() {
			return 2;
		}
		
		@Callback(doc = "function(level:number):boolean -- Set level of all reactor rods (from 0 to 10).")
		public Object[] setLevelRods(Context context, Arguments args) {
			int levelRod = args.checkInteger(0);
			try {
				boolean check = levelRod >= 0 && levelRod <= 10;
				if (check) {
					((GTMTE_NuclearReactorBase) tile.getMetaTileEntity()).setRodPosition(levelRod);
				}
				return new Object[]{check};
			} catch (Throwable e) {
				return new Object[]{false, "invalid level"};
			}
		}
		
		@Callback(doc = "function(rodId:number, level:number):boolean -- Set level of the selected reactor rod (from 0 to 10).")
		public Object[] setSelectLevelRod(Context context, Arguments args) {
			int rodID = args.checkInteger(0);
			int levelRod = args.checkInteger(1);
			try {
				boolean check = levelRod >= 0 && levelRod <= 10;
				if (check) {
					((GTMTE_NuclearReactorBase) tile.getMetaTileEntity()).setSelectRodPosition(rodID, levelRod);
				}
				return new Object[]{check};
			} catch (Throwable e) {
				return new Object[]{false, "invalid level or rodID"};
			}
		}
		
		@Callback(doc = "function():array -- Get information about all reactor rods.")
		public Object[] getStatusRod(Context context, Arguments args) {
			try {
				return new Object[]{((GTMTE_NuclearReactorBase) tile.getMetaTileEntity()).getRodPosition()};
			} catch (Throwable e) {
				return new Object[]{new int[]{}, "invalid argument"};
			}
		}
		
		@Callback(doc = "function(rodId:number):string, number, number -- Get information about the selected reactor rod")
		public Object[] getSelectStatusRod(Context context, Arguments args) {
			try {
				int rodID = args.checkInteger(0) - 1;
				GTMTE_NuclearReactorBase reactor = ((GTMTE_NuclearReactorBase) tile.getMetaTileEntity());
				
				if (reactor.mRodHatches.size() > rodID) {
					GTMTE_Reactor_Rod_Hatch hatch = reactor.mRodHatches.get(rodID);
					ItemStack rod = hatch.mInventory[0];
					String name = rod.getDisplayName();
					int maxRodDamage = hatch.getMaxDurability();
					int CurrentRodDamage = maxRodDamage - getDurabilityOfStack(rod);
					
					return new Object[]{new Object[]{name, CurrentRodDamage, maxRodDamage}};
				}
				return new Object[]{"not found rod", 0};
			} catch (Throwable e) {
				return new Object[]{new int[]{}, "invalid argument"};
			}
		}
		
		@Callback(doc = "function(mode:boolean):boolean -- Change reactor mode to fast decay or default.")
		public Object[] setFastDecayMode(Context context, Arguments args) {
			boolean changeMode = args.checkBoolean(0);
			try {
				((GTMTE_NuclearReactorBase) tile.getMetaTileEntity()).setFastDecayMode(changeMode);
				return new Object[]{true};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
		
		@Callback(doc = "function():boolean -- Check out the fast decay or default reactor mode.")
		public Object[] getFastDecayMode(Context context, Arguments args) {
			try {
				boolean isFastDecay = ((GTMTE_NuclearReactorBase) tile.getMetaTileEntity()).isFastDecay;
				return new Object[]{isFastDecay};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
		
		@Callback(doc = "function():string, number -- Get reactor output fluid.")
		public Object[] getOutputFluid(Context context, Arguments args) {
			try {
				FluidStack aFluid = ((GTMTE_NuclearReactorBase) tile.getMetaTileEntity()).getOutputFluid();
				int aOutput = aFluid.amount;
				String aName = aFluid.getLocalizedName();
				return new Object[]{new Object[]{aName, aOutput}};
			} catch (Throwable e) {
				return new Object[]{"not found fluid"};
			}
		}
		
		@Callback(doc = "function():string, number -- Get reactor input fluid.")
		public Object[] getInputFluid(Context context, Arguments args) {
			try {
				FluidStack aFluid = ((GTMTE_NuclearReactorBase) tile.getMetaTileEntity()).getInputFluid();
				int aInput = aFluid.amount;
				String aName = aFluid.getLocalizedName();
				return new Object[]{new Object[]{aName, aInput}};
			} catch (Throwable e) {
				return new Object[]{"not found fluid"};
			}
		}
		
		@Callback(doc = "function():boolean -- Get MOX Fuel.")
		public Object[] getMox(Context context, Arguments args) {
			try {
				boolean isMox = ((GTMTE_NuclearReactorBase) tile.getMetaTileEntity()).isMoxFuel;
				return new Object[]{isMox};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
	}
}