package com.impact.util.fluid;

import appeng.api.config.Upgrades;
import appeng.api.implementations.items.IUpgradeModule;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IItemList;
import appeng.api.storage.ICellWorkbenchItem;
import com.impact.util.fluid.IMultiFluidWatcher;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import gregtech.api.util.GT_Utility;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class MultiFluidHandler {

  public int maxDistinctFluids = 1;

  public List<FluidStack> fluids = new ArrayList<>(maxDistinctFluids);

  public int capacityPerFluid;

  public boolean locked = true;

  public boolean voidExcess = false;

  public List<IMultiFluidWatcher> watchers = new ArrayList<>();

  public FluidFilter filter = new FluidFilter();

	private class FluidFilter {
    private final int MAX_FILTERED_FLUIDS = 64;

		public HashSet<FluidStack> fluidList = new HashSet<>();
    public boolean block = false;

		public FluidFilter() {}

    public NBTTagCompound saveNBTData(NBTTagCompound nbt) {
      nbt = (nbt == null) ? new NBTTagCompound() : nbt;

      nbt.setBoolean("block", block);
      int c = 0;
      for (FluidStack f : fluidList) {
        nbt.setTag("" + c, f.writeToNBT(new NBTTagCompound()));
        c++;
      }
      return nbt;
    }

    public void loadNBTData(NBTTagCompound nbt) {
      block = nbt.getBoolean("block");
      fluidList.clear();
      for (int i = 0; i < MAX_FILTERED_FLUIDS; i++) {
        final NBTTagCompound fnbt = (NBTTagCompound) nbt.getTag("" + i);
        if (fnbt == null) {
          break;
        }
        fluidList.add(FluidStack.loadFluidStackFromNBT(fnbt));
      }
    }

    /**
     * Evaluate the filter
     */
    public boolean allowFluid(FluidStack fs) {
      FluidStack fluid = new FluidStack(fs.getFluid(), 0);
      if (fluidList.contains(fluid)) {
        return !block;
      }
      return block;
    }

    /**
     * Retain fluids in the allow list.
     */
    public boolean keepFluid(FluidStack fs) {
      FluidStack fluid = new FluidStack(fs.getFluid(), 0);
      return (!block) && fluidList.contains(fluid);
    }

    /**
     * Set the filter based on AE and/or fluid cell(s)
     */
    public void setFilter(ItemStack[] list) {
      // Default to empty block list.
      fluidList.clear();
      block = true;
		  for (final ItemStack item : list) {
			  if (item == null) { continue; }

        // ItemViewCell is private to AE, so accept all cells.
        if(item.getItem() instanceof ICellWorkbenchItem) {
          final ICellWorkbenchItem cwi = (ICellWorkbenchItem) item.getItem();
          final IInventory upgrades = cwi.getUpgradesInventory(item);

          // Default to an allow list. Look for an invert upgrade.
          block = false;
          for (int x = 0; x < upgrades.getSizeInventory(); x++) {
            final ItemStack is = upgrades.getStackInSlot(x);
            if (is == null) { continue; }
            if (is.getItem() instanceof IUpgradeModule) {
              final IUpgradeModule card = (IUpgradeModule) is.getItem();
              final Upgrades type = card.getType(is);
              if (type == null) { continue; }
              switch (type) {
                case INVERTER: block = true; break;
              }
            }
          }

          final IInventory config = cwi.getConfigInventory(item);
          for (int x = 0; x < config.getSizeInventory(); x++) {
            final ItemStack is = config.getStackInSlot(x);
            if (is == null) { continue; }
            final FluidStack fluidStack = GT_Utility.getFluidForFilledItem(is, true);
            if (fluidStack != null) {
              fluidList.add(new FluidStack(fluidStack.getFluid(), 0));
            }
          }
        } else {
          // Support fluid containers.
          final FluidStack fluidStack = GT_Utility.getFluidForFilledItem(item, true);
          if (fluidStack != null) {
            block = false;
            fluidList.add(new FluidStack(fluidStack.getFluid(), 0));
          }
        }
      }
	  }
  }
	
  public MultiFluidHandler(int aFluids) {
    this.maxDistinctFluids = aFluids;
  }

  public MultiFluidHandler(int capacityPerFluid, int aFluids) {
    this.capacityPerFluid = capacityPerFluid;
    this.maxDistinctFluids = aFluids;
  }

  public MultiFluidHandler(int capacityPerFluid, List<FluidStack> fluids, int aFluids) {
    this.capacityPerFluid = capacityPerFluid;
    this.fluids.addAll(fluids);
    this.maxDistinctFluids = aFluids;
  }
  /**
   * Lock internal tanks in case T.F.F.T is not running.
   *
   * @param state Lock state.
   */
  public void setLock(boolean state) {
    locked = state;
  }

  public void setFilterFluids(ItemStack is) {
    filter.setFilter(new ItemStack[]{is});

    // Add/remove empty fluids.
    boolean lastLocked = locked;
    locked = false;
    if (!filter.block) {
      for (FluidStack fluid : fluids) {
        pullFluid(new FluidStack(fluid.getFluid(), 0), true);
      }
      for (FluidStack fluid : filter.fluidList) {
        pushFluid(new FluidStack(fluid.getFluid(), 0), true);
      }
    }
    locked = lastLocked;
  }

  /**
   * Void excess for matching fluids.
   */
  public void setVoidExcess(boolean state) {
    voidExcess = state;
  }

  public void removeFluidWatcher(IMultiFluidWatcher watcher) {
    watchers.remove(watcher);
  }

  public void addFluidWatcher(IMultiFluidWatcher watcher) {
    watchers.add(watcher);
  }

  public void notifyWatchers(FluidStack fs, int newAmount) {
    watchers.forEach(watcher -> {
      watcher.onMultiFluidChange(fs, newAmount);
    });
  }

  public boolean getVoidExcess() {
    return voidExcess;
  }

  public boolean contains(FluidStack fluid) {
    return !locked && fluids.contains(fluid);
  }

  public int getCapacity() {
    return capacityPerFluid;
  }

  public List<FluidStack> getFluids() {
    return (!locked) ? fluids : new ArrayList<FluidStack>();
  }

  public FluidStack getFluid(int slot) {
    return (!locked && fluids.size() > 0 && slot >= 0 && slot < maxDistinctFluids)
        ? fluids.get(slot) : null;
  }

  public NBTTagCompound saveNBTData(NBTTagCompound nbt) {
    nbt = (nbt == null) ? new NBTTagCompound() : nbt;

    nbt.setInteger("capacityPerFluid", getCapacity());
    nbt.setBoolean("voidExcess", voidExcess);
    nbt.setTag("filter", filter.saveNBTData(new NBTTagCompound()));
    int c = 0;
    for (FluidStack f : fluids) {
      nbt.setTag("" + c, f.writeToNBT(new NBTTagCompound()));
      c++;
    }
    return nbt;
  }

  public void loadNBTData(NBTTagCompound nbt) {
    nbt = (nbt == null) ? new NBTTagCompound() : nbt;

    capacityPerFluid = nbt.getInteger("capacityPerFluid");
    if (nbt.hasKey("voidExcess")) {
      voidExcess = nbt.getBoolean("voidExcess");
    }

    final NBTTagCompound filterTag = (NBTTagCompound) nbt.getTag("filter");
    if (filterTag != null) {
      filter.loadNBTData(filterTag);
    }

    fluids.clear();
    final NBTTagCompound fluidsTag = (NBTTagCompound) nbt.getTag("fluids");
    for (int i = 0; i < this.maxDistinctFluids; i++) {
      final NBTTagCompound fnbt = (NBTTagCompound) fluidsTag.getTag("" + i);
      if (fnbt == null) {
        break;
      }
      fluids.add(FluidStack.loadFluidStackFromNBT(fnbt));
    }
  }

  /**
   * Fill fluid into a tank.
   *
   * @param push   Fluid type and quantity to be inserted.
   * @param doPush If false, fill will only be simulated.
   * @return Amount of fluid that was (or would have been, if simulated) filled.
   */
  public int pushFluid(FluidStack push, boolean doPush) {
    if (locked) {
      return 0;
    }
    if (fluids.size() == maxDistinctFluids && !contains(push)) {
      return 0;
    } else if (fluids.size() < maxDistinctFluids && !contains(push)) {
      if (!filter.allowFluid(push)) {
        return 0;
      }
      // Add new fluid
      final int remcap = getCapacity();
      final int fit = Math.min(remcap, push.amount);
      if (doPush) {
        fluids.add(new FluidStack(push.getFluid(), fit));
        notifyWatchers(push, fit);
      }
      return voidExcess ? push.amount : fit;
    } else {
      // Refuse to accept more fluid if the filter has changed.
      if (!filter.allowFluid(push)) {
        return 0;
      }
      // Add to existing fluid
      final FluidStack fs = fluids.get(fluids.indexOf(push));
      final int remcap = getCapacity() - fs.amount;
      final int fit = Math.min(remcap, push.amount);
      if (doPush && fit != 0) {
        fs.amount += fit;
        notifyWatchers(push, fs.amount);
      }
      return voidExcess ? push.amount : fit;
    }
  }

  /**
   * Fill fluid into the specified tank.
   *
   * @param push   Fluid type and quantity to be inserted.
   * @param slot   Tank the fluid should go into.
   * @param doPush If false, fill will only be simulated.
   * @return Amount of fluid that was (or would have been, if simulated) filled.
   */
  public int pushFluid(FluidStack push, int slot, boolean doPush) {
    if (locked) {
      return 0;
    }
    if (slot < 0 || slot >= maxDistinctFluids) {
      return 0;
    }
    if (!fluids.get(slot).equals(push)) {
      return 0;
    } else {
      // Refuse to accept more fluid if the filter has changed.
      if (!filter.allowFluid(push)) {
        return 0;
      }
      final FluidStack fs = fluids.get(slot);
      final int remcap = getCapacity() - fs.amount;
      final int fit = Math.min(remcap, push.amount);
      if (doPush && fit != 0) {
        fs.amount += fit;
        notifyWatchers(fs, fs.amount);
      }
      return voidExcess ? push.amount : fit;
    }
  }

  /**
   * Drains fluid out of the internal tanks.
   *
   * @param pull   Fluid type and quantity to be pulled.
   * @param doPull If false, drain will only be simulated.
   * @return Amount of fluid that was (or would have been, if simulated) pulled.
   */
  public int pullFluid(FluidStack pull, boolean doPull) {
    if (locked) {
      return 0;
    }
    if (!contains(pull)) {
      return 0;
    } else {
      final FluidStack src = fluids.get(fluids.indexOf(pull));
      final int rec = Math.min(pull.amount, src.amount);
      if (doPull && rec != 0) {
        src.amount -= rec;
        notifyWatchers(src, src.amount);
      }
      if (src.amount == 0 && !filter.keepFluid(src)) {
        fluids.remove(src);
        notifyWatchers(src, 0);
      }
      return rec;
    }
  }

  /**
   * Drains fluid out of the specified internal tank.
   *
   * @param pull   Fluid type and quantity to be pulled.
   * @param slot   Tank fluid should be drained from.
   * @param doPull If false, drain will only be simulated.
   * @return Amount of fluid that was (or would have been, if simulated) pulled.
   */
  public int pullFluid(FluidStack pull, int slot, boolean doPull) {
    if (locked) {
      return 0;
    }
    if (slot < 0 || slot >= maxDistinctFluids) {
      return 0;
    }
    if (!fluids.get(slot).equals(pull)) {
      return 0;
    } else {
      final FluidStack src = fluids.get(slot);
      final int rec = Math.min(pull.amount, src.amount);
      if (doPull && rec != 0) {
        src.amount -= rec;
        notifyWatchers(src, src.amount);
      }
      if (src.amount == 0 && !filter.keepFluid(src)) {
        fluids.remove(src);
        notifyWatchers(src, 0);
      }
      return rec;
    }
  }

  /**
   * Test whether the given fluid type and quantity can be inserted into the internal tanks.
   *
   * @param push Fluid type and quantity to be tested
   * @return True if there is sufficient space
   */
  public boolean couldPush(FluidStack push) {
    if (locked) {
      return false;
    }
    if (fluids.size() == maxDistinctFluids && !contains(push)) {
      return false;
    } else if (fluids.size() < maxDistinctFluids && !contains(push)) {
      if (!filter.allowFluid(push)) {
        return false;
      }
      return Math.min(getCapacity(), push.amount) > 0;
    } else {
      final int remcap = getCapacity() - fluids.get(fluids.indexOf(push)).amount;
      return Math.min(remcap, push.amount) > 0;
    }
  }
}
