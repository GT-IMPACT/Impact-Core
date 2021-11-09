package com.impact.mods.opencomputers;

import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
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
				&& ((BaseMetaTileEntity) tileEntity).getMetaTileEntity() instanceof GTMTE_NuclearReactorBase<?>;
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
					((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).setRodPosition(levelRod);
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
					((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).setSelectRodPosition(rodID, levelRod);
				}
				return new Object[]{check};
			} catch (Throwable e) {
				return new Object[]{false, "invalid level or rodID"};
			}
		}
		
		@Callback(doc = "function():array -- Get information about all reactor rods.")
		public Object[] getStatusRod(Context context, Arguments args) {
			try {
				return new Object[]{((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).getRodPosition()};
			} catch (Throwable e) {
				return new Object[]{new int[]{}, "invalid argument"};
			}
		}
		
		@Callback(doc = "function(rodId:number):string, number, number -- Get information about the selected reactor rod")
		public Object[] getSelectStatusRod(Context context, Arguments args) {
			try {
				int rodID = args.checkInteger(0) - 1;
				GTMTE_NuclearReactorBase<?> reactor = ((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity());
				
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
				((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).setFastDecayMode(changeMode);
				return new Object[]{true};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
		
		@Callback(doc = "function():boolean -- Check out the fast decay or default reactor mode.")
		public Object[] getFastDecayMode(Context context, Arguments args) {
			try {
				boolean isFastDecay = ((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).isFastDecay;
				return new Object[]{isFastDecay};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
		
		@Callback(doc = "function():string, number -- Get reactor output fluid L/s.")
		public Object[] getOutputFluid(Context context, Arguments args) {
			try {
				GTMTE_NuclearReactorBase<?> reactor = ((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity());
				
				int aOutput = (int) Math.ceil(reactor.mCurrentOutput * 20D);
				String aName = reactor.getInputFluid().getLocalizedName();
				
				return new Object[]{new Object[]{aName, aOutput}};
			} catch (Throwable e) {
				return new Object[]{"not found fluid"};
			}
		}
		
		@Callback(doc = "function():string, number -- Get reactor input fluid L/s.")
		public Object[] getInputFluid(Context context, Arguments args) {
			try {
				GTMTE_NuclearReactorBase<?> reactor = ((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity());
				
				int aInput = (int) Math.ceil(reactor.mCurrentInput * 20D);
				String aName = reactor.getInputFluid().getLocalizedName();
				
				return new Object[]{new Object[]{aName, aInput}};
			} catch (Throwable e) {
				return new Object[]{"not found fluid"};
			}
		}
		
		@Callback(doc = "function():boolean -- Get MOX Fuel.")
		public Object[] getMox(Context context, Arguments args) {
			try {
				boolean isMox = ((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).isMoxFuel;
				return new Object[]{isMox};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
		
		@Callback(doc = "function(active:boolean):boolean -- Set Active Reactor.")
		public Object[] setActiveReactor(Context context, Arguments args) {
			try {
				tile.getMetaTileEntity().getBaseMetaTileEntity().setActive(args.isBoolean(0));
				((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).mFirstStart = args.isBoolean(0);
				return new Object[]{true};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
		
		@Callback(doc = "function():number -- Set Active Reactor.")
		public Object[] getTemperature(Context context, Arguments args) {
			try {
				double tScale = (double) ((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).mCurrentTemp
						/ (double) ((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).maxTemperature();
				tScale = tScale <= 0 ? 0 : tScale;
				int temperature = Math.min(((int) (100 * tScale)), 100);
				return new Object[]{temperature};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
		
		@Callback(doc = "function():boolean -- Get Active Reactor.")
		public Object[] getActiveReactor(Context context, Arguments args) {
			try {
				boolean isActive = tile.getMetaTileEntity().getBaseMetaTileEntity().isActive();
				return new Object[]{isActive};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
		
		@Callback(doc = "function(rodId:number):tuple -- Get input hatch info.")
		public Object[] getInputHatchStatus(Context context, Arguments args) {
			try {
				int rodID = args.checkInteger(0) - 1;
				GT_MetaTileEntity_Hatch_Input hatch = ((GTMTE_NuclearReactorBase<?>) tile.getMetaTileEntity()).mInputHatches.get(rodID);
				return new Object[]{hatch.mFluid.getLocalizedName(), hatch.mFluid.amount, hatch.getCapacity()};
			} catch (Throwable e) {
				return new Object[]{false, "invalid argument"};
			}
		}
	}
}