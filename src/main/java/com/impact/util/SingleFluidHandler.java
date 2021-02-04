package com.impact.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class SingleFluidHandler {

  public int MAX_DISTINCT_FLUIDS = 1;

  public List<FluidStack> fluids = new ArrayList<>(MAX_DISTINCT_FLUIDS);

  public int capacityPerFluid;

  public boolean locked = true;

  public SingleFluidHandler() {

  }

  public SingleFluidHandler(int capacityPerFluid, int aFluids) {
    this.capacityPerFluid = capacityPerFluid;
  }

  public SingleFluidHandler(int capacityPerFluid, List<FluidStack> fluids, int aFluids) {
    this.capacityPerFluid = capacityPerFluid;
    this.fluids.addAll(fluids);
  }

  /**
   * Lock internal tanks in case T.F.F.T is not running.
   *
   * @param state Lock state.
   */
  public void setLock(boolean state) {
    locked = state;
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
    return (!locked && fluids.size() > 0 && slot >= 0 && slot < MAX_DISTINCT_FLUIDS)
        ? fluids.get(slot) : null;
  }

  public NBTTagCompound saveNBTData(NBTTagCompound nbt) {
    nbt = (nbt == null) ? new NBTTagCompound() : nbt;

    nbt.setInteger("capacityPerFluid", getCapacity());
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

    fluids.clear();
    final NBTTagCompound fluidsTag = (NBTTagCompound) nbt.getTag("fluids");
    for (int i = 0; i < this.MAX_DISTINCT_FLUIDS; i++) {
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
    if (fluids.size() == MAX_DISTINCT_FLUIDS && !contains(push)) {
      return 0;
    } else if (fluids.size() < MAX_DISTINCT_FLUIDS && !contains(push)) {
      // Add new fluid
      final int remcap = getCapacity();
      final int fit = Math.min(remcap, push.amount);
      if (doPush) {
        fluids.add(new FluidStack(push.getFluid(), fit));
      }
      return fit;
    } else {
      // Add to existing fluid
      final FluidStack fs = fluids.get(fluids.indexOf(push));
      final int remcap = getCapacity() - fs.amount;
      final int fit = Math.min(remcap, push.amount);
      if (doPush) {
        fs.amount += fit;
      }
      return fit;
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
    if (slot < 0 || slot >= MAX_DISTINCT_FLUIDS) {
      return 0;
    }
    if (!fluids.get(slot).equals(push)) {
      return 0;
    } else {
      final FluidStack fs = fluids.get(slot);
      final int remcap = getCapacity() - fs.amount;
      final int fit = Math.min(remcap, push.amount);
      if (doPush) {
        fs.amount += fit;
      }
      return fit;
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
      if (doPull) {
        src.amount -= rec;
      }
      if (src.amount == 0) {
        fluids.remove(src);
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
    if (slot < 0 || slot >= MAX_DISTINCT_FLUIDS) {
      return 0;
    }
    if (!fluids.get(slot).equals(pull)) {
      return 0;
    } else {
      final FluidStack pulled = fluids.get(slot);
      final int rec = Math.min(pull.amount, pulled.amount);
      if (doPull) {
        pulled.amount -= rec;
      }
      if (pulled.amount == 0) {
        fluids.remove(pulled);
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
    if (fluids.size() == MAX_DISTINCT_FLUIDS && !contains(push)) {
      return false;
    } else if (fluids.size() < MAX_DISTINCT_FLUIDS && !contains(push)) {
      return Math.min(getCapacity(), push.amount) > 0;
    } else {
      final int remcap = getCapacity() - fluids.get(fluids.indexOf(push)).amount;
      return Math.min(remcap, push.amount) > 0;
    }
  }
}
