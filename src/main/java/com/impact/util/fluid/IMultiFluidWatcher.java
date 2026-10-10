package com.impact.util.fluid;

import appeng.api.config.Upgrades;
import appeng.api.implementations.items.IUpgradeModule;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IItemList;
import appeng.api.storage.ICellWorkbenchItem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import gregtech.api.util.GT_Utility;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

/**
 * Наблюдатель за изменением количества жидкости в общем хранилище бака.
 */
public interface IMultiFluidWatcher {
    /**
     * Получает уведомление об изменившейся жидкости и её новом количестве.
     * Вызывается синхронно из обработчика; стек может быть внутренним объектом хранилища.
     *
     * @param fluid жидкость для операции
     * @param newAmount новое количество жидкости в хранилище; ноль означает опустошение
     */
    public void onMultiFluidChange(FluidStack fluid, int newAmount);
}
