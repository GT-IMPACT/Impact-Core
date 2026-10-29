package com.impact.util.fluid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import appeng.api.config.Upgrades;
import appeng.api.implementations.items.IUpgradeModule;
import appeng.api.storage.ICellWorkbenchItem;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.tile.inventory.IAEStackInventory;
import gregtech.api.util.GT_Utility;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Общее хранилище жидкостей с ёмкостью на тип, фильтром, блокировкой, уничтожением избытка и наблюдателями.
 */
public class MultiFluidHandler {

    public int maxDistinctFluids = 1;

    public List<FluidStack> fluids = new ArrayList<>(maxDistinctFluids);

    public int capacityPerFluid;

    public boolean locked = true;

    public boolean voidExcess = false;

    public FluidFilter filter = new FluidFilter();

    private final List<IMultiFluidWatcher> watchers = new ArrayList<>();
    private Runnable storageChangeListener = () -> {
    };

    public void setStorageChangeListener(Runnable listener) {
        storageChangeListener = listener;
    }

    public void markDirty() {
        storageChangeListener.run();
    }

    public List<IMultiFluidWatcher> getFluidWatchers() {
        return new ArrayList<>(watchers);
    }

    /** Снимок содержимого и текущего белого списка без создания слотов и изменения рабочего фильтра. */
    public static List<FluidStack> getDisplayFluids(List<FluidStack> storedFluids, ItemStack filterItem) {
        FluidFilter displayFilter = new FluidFilter();
        displayFilter.setFilter(new ItemStack[] { filterItem });

        List<FluidStack> displayFluids = new ArrayList<>();
        HashSet<Fluid> displayedTypes = new HashSet<>();
        for (FluidStack fluid : storedFluids) {
            if (fluid != null && fluid.amount > 0) {
                displayFluids.add(fluid.copy());
                displayedTypes.add(fluid.getFluid());
            }
        }

        if (!displayFilter.block) {
            for (FluidStack fluid : displayFilter.fluidList) {
                if (displayedTypes.add(fluid.getFluid())) {
                    displayFluids.add(new FluidStack(fluid.getFluid(), 0));
                }
            }
        }
        return displayFluids;
    }

    public void setCapacity(int capacity) {
        if (capacityPerFluid == capacity) {
            return;
        }
        capacityPerFluid = capacity;
        markDirty();
        notifyStateChange();
    }

    private void notifyStateChange() {
        for (IMultiFluidWatcher watcher : getFluidWatchers()) {
            watcher.onMultiFluidStateChange();
        }
    }

    /**
     * Разрешающий или запрещающий список типов жидкостей, заданный предметами контроллера.
     */
    private static class FluidFilter {
        private final int MAX_FILTERED_FLUIDS = 64;

        public HashSet<FluidStack> fluidList = new HashSet<>();
        public boolean block = true;

        /**
         * Создаёт пустой запрещающий список, который допускает любые жидкости.
         */
        public FluidFilter() {
        }

        /**
         * Сохраняет режим фильтра и список типов жидкостей под числовыми ключами.
         *
         * @param nbt NBT-компаунд состояния
         * @return заполненный компаунд; при входном {@code null} создаётся новый
         */
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

        /**
         * Восстанавливает режим фильтра и до 64 последовательно записанных типов жидкостей.
         *
         * @param nbt NBT-компаунд состояния
         */
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
         * Проверяет тип жидкости по разрешающему или запрещающему списку; количество и NBT стека не участвуют в выборе типа.
         *
         * @param fs жидкость, для которой выполняется операция
         * @return {@code true}, если фильтр разрешает жидкость
         */
        public boolean allowFluid(FluidStack fs) {
            FluidStack fluid = new FluidStack(fs.getFluid(), 0);
            if (fluidList.contains(fluid)) {
                return !block;
            }
            return block;
        }

        /**
         * Проверяет, следует ли сохранять пустой слот жидкости, явно указанной в разрешающем списке.
         *
         * @param fs жидкость, для которой выполняется операция
         * @return {@code true}, если опустошённый слот должен оставаться в хранилище
         */
        public boolean keepFluid(FluidStack fs) {
            FluidStack fluid = new FluidStack(fs.getFluid(), 0);
            return (!block) && fluidList.contains(fluid);
        }

        /**
         * Пересоздаёт фильтр из конфигурации ячеек AE2 или заполненных контейнеров.
         * Карта инверсии задаёт запрещающий список; отсутствие подходящих предметов снимает ограничения.
         *
         * @param list ячейки AE2 и заполненные контейнеры, задающие фильтр
         */
        public void setFilter(ItemStack[] list) {
            // Default to empty block list.
            fluidList.clear();
            block = true;
            for (final ItemStack item : list) {
                if (item == null) {
                    continue;
                }

                // ItemViewCell is private to AE, so accept all cells.
                if (item.getItem() instanceof ICellWorkbenchItem) {
                    final ICellWorkbenchItem cwi = (ICellWorkbenchItem) item.getItem();
                    final IInventory upgrades = cwi.getUpgradesInventory(item);

                    // Default to an allow list. Look for an invert upgrade.
                    block = false;
                    for (int x = 0; x < upgrades.getSizeInventory(); x++) {
                        final ItemStack is = upgrades.getStackInSlot(x);
                        if (is == null) {
                            continue;
                        }
                        if (is.getItem() instanceof IUpgradeModule) {
                            final IUpgradeModule card = (IUpgradeModule) is.getItem();
                            final Upgrades type = card.getType(is);
                            if (type == null) {
                                continue;
                            }
                            switch (type) {
                            case INVERTER:
                                block = true;
                                break;
                            }
                        }
                    }

                    // Legacy IInventory скрывает жидкостные записи современных ячеек AE2.
                    final IAEStackInventory config = cwi.getConfigAEInventory(item);
                    for (int x = 0; x < config.getSizeInventory(); x++) {
                        final IAEStack<?> configured = config.getAEStackInSlot(x);
                        final FluidStack fluidStack;
                        if (configured instanceof IAEFluidStack) {
                            fluidStack = ((IAEFluidStack) configured).getFluidStack();
                        } else if (configured instanceof IAEItemStack) {
                            fluidStack = GT_Utility.getFluidForFilledItem(((IAEItemStack) configured).getItemStack(),
                                    true);
                        } else {
                            continue;
                        }
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

    /**
     * Создаёт заблокированное хранилище с заданным пределом типов; ёмкость задаётся позднее.
     *
     * @param aFluids максимальное число различных жидкостей
     */
    public MultiFluidHandler(int aFluids) {
        this.maxDistinctFluids = aFluids;
    }

    /**
     * Создаёт заблокированное хранилище с заданным пределом типов и ёмкостью на тип.
     *
     * @param capacityPerFluid ёмкость для каждой жидкости в литрах
     * @param aFluids максимальное число различных жидкостей
     */
    public MultiFluidHandler(int capacityPerFluid, int aFluids) {
        this.capacityPerFluid = capacityPerFluid;
        this.maxDistinctFluids = aFluids;
    }

    /**
     * Создаёт заблокированное хранилище с заданным пределом типов и начальным содержимым.
     *
     * @param capacityPerFluid ёмкость для каждой жидкости в литрах
     * @param fluids начальные жидкости; список копируется, сами FluidStack остаются общими
     * @param aFluids максимальное число различных жидкостей
     */
    public MultiFluidHandler(int capacityPerFluid, List<FluidStack> fluids, int aFluids) {
        this.capacityPerFluid = capacityPerFluid;
        this.fluids.addAll(fluids);
        this.maxDistinctFluids = aFluids;
    }

    /**
     * Переключает доступ к жидкостям без удаления сохранённого содержимого.
     *
     * @param state {@code true} для запрета чтения, вставки и извлечения жидкостей
     */
    public void setLock(boolean state) {
        if (locked == state) {
            return;
        }
        locked = state;
        notifyStateChange();
    }

    /**
     * Обновляет фильтр по предмету контроллера, удаляет ненужные пустые слоты и добавляет разрешённые типы.
     * На время обновления снимает блокировку, затем восстанавливает её прежнее состояние.
     *
     * @param is предмет фильтра или {@code null} для снятия ограничений
     */
    public void setFilterFluids(ItemStack is) {
        NBTTagCompound previous = filter.saveNBTData(null);
        filter.setFilter(new ItemStack[] { is });
        if (previous.equals(filter.saveNBTData(null))) {
            return;
        }

        // Add/remove empty fluids.
        boolean lastLocked = locked;
        locked = false;
        if (!filter.block) {
            for (FluidStack fluid : new ArrayList<>(fluids)) {
                pullFluid(new FluidStack(fluid.getFluid(), 0), true);
            }
            for (FluidStack fluid : filter.fluidList) {
                pushFluid(new FluidStack(fluid.getFluid(), 0), true);
            }
        }
        locked = lastLocked;
        markDirty();
        notifyStateChange();
    }

    /**
     * Переключает уничтожение объёма сверх ёмкости; ограничения фильтра и числа типов остаются действующими.
     *
     * @param state {@code true} для уничтожения принимаемого избытка
     */
    public void setVoidExcess(boolean state) {
        if (voidExcess == state) {
            return;
        }
        voidExcess = state;
        markDirty();
        notifyStateChange();
    }

    /**
     * Удаляет одну подписку наблюдателя на изменения жидкостей.
     *
     * @param watcher наблюдатель за изменениями жидкостей
     */
    public void removeFluidWatcher(IMultiFluidWatcher watcher) {
        watchers.remove(watcher);
    }

    /**
     * Добавляет наблюдателя за изменением количества или удалением жидкости.
     *
     * @param watcher наблюдатель за изменениями жидкостей
     */
    public void addFluidWatcher(IMultiFluidWatcher watcher) {
        if (!watchers.contains(watcher)) {
            watchers.add(watcher);
        }
    }

    /**
     * Синхронно передаёт всем подписанным наблюдателям изменившуюся жидкость и её новое количество.
     *
     * @param fs жидкость, для которой выполняется операция
     * @param newAmount новое количество жидкости в хранилище; ноль означает опустошение
     */
    public void notifyWatchers(FluidStack fs, int newAmount) {
        markDirty();
        getFluidWatchers().forEach(watcher -> {
            watcher.onMultiFluidChange(fs, newAmount);
        });
    }

    /**
     * Возвращает состояние режима уничтожения избытка.
     *
     * @return {@code true}, если объём сверх ёмкости считается принятым и уничтожается
     */
    public boolean getVoidExcess() {
        return voidExcess;
    }

    /**
     * Проверяет наличие совпадающего типа и NBT жидкости в доступном хранилище; количество не сравнивается.
     *
     * @param fluid жидкость для операции
     * @return {@code true}, если слот найден и обработчик не заблокирован
     */
    public boolean contains(FluidStack fluid) {
        return !locked && fluids.contains(fluid);
    }

    /**
     * Возвращает максимальный сохраняемый объём для каждой жидкости.
     *
     * @return ёмкость одного жидкостного слота в литрах
     */
    public int getCapacity() {
        return capacityPerFluid;
    }

    /**
     * Предоставляет текущий список жидкостей только при снятой блокировке.
     * Возвращаемый доступный список является внутренним: удаление слотов при обходе требует снимка списка.
     *
     * @return внутренний список при открытом доступе, иначе новый пустой список
     */
    public List<FluidStack> getFluids() {
        return (!locked) ? fluids : new ArrayList<FluidStack>();
    }

    /**
     * Возвращает доступный стек по индексу фактически существующего слота.
     *
     * @param slot индекс жидкостного слота, начиная с нуля
     * @return внутренний FluidStack либо {@code null} при блокировке или неверном индексе
     */
    public FluidStack getFluid(int slot) {
        return (!locked && slot >= 0 && slot < fluids.size()) ? fluids.get(slot) : null;
    }

    /**
     * Сохраняет ёмкость, режим уничтожения избытка, фильтр и все жидкости в плоский NBT-компаунд.
     * Жидкостные записи имеют числовые ключи; контроллер помещает результат в свой тег {@code fluids}.
     *
     * @param nbt NBT-компаунд состояния
     * @return заполненный компаунд; при входном {@code null} создаётся новый
     */
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

    /**
     * Восстанавливает жидкости, ёмкость, режим уничтожения избытка и фильтр.
     * Принимает плоское состояние обработчика или NBT контроллера с тегом {@code fluids}; пропускает неизвестные жидкости.
     *
     * @param nbt NBT-компаунд состояния
     */
    public void loadNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        // Controllers wrap the handler's flat fluid entries in a "fluids" compound.
        // Also accept a handler compound directly for save/load round trips.
        final NBTTagCompound fluidsTag = nbt.hasKey("fluids", 10) ? nbt.getCompoundTag("fluids") : nbt;
        capacityPerFluid = fluidsTag.hasKey("capacityPerFluid") ? fluidsTag.getInteger("capacityPerFluid")
                : nbt.getInteger("capacityPerFluid");
        if (fluidsTag.hasKey("voidExcess")) {
            voidExcess = fluidsTag.getBoolean("voidExcess");
        }
        if (fluidsTag.hasKey("filter", 10)) {
            filter.loadNBTData(fluidsTag.getCompoundTag("filter"));
        } else if (nbt.hasKey("filter", 10)) {
            filter.loadNBTData(nbt.getCompoundTag("filter"));
        }

        fluids.clear();
        for (int i = 0; i < maxDistinctFluids; i++) {
            if (!fluidsTag.hasKey("" + i, 10)) {
                continue;
            }
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(fluidsTag.getCompoundTag("" + i));
            if (fluid != null) {
                fluids.add(fluid);
            }
        }
    }

    /**
     * Заполняет совпадающий слот либо создаёт новый с учётом фильтра, блокировки и лимита типов.
     * При симуляции содержимое не меняется; нулевой объём допускается для резервирования типа фильтром.
     *
     * @param push жидкость и количество для заполнения
     * @param doPush изменять хранилище; при {@code false} только рассчитать результат
     * @return принятый объём в литрах; при уничтожении избытка может превышать сохранённый объём
     */
    public int pushFluid(FluidStack push, boolean doPush) {
        if (push == null || push.amount < 0) {
            return 0;
        }
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
            final int remcap = Math.max(0, getCapacity());
            final int fit = Math.min(remcap, push.amount);
            if (doPush) {
                FluidStack stored = push.copy();
                stored.amount = fit;
                fluids.add(stored);
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
            final int remcap = Math.max(0, getCapacity() - fs.amount);
            final int fit = Math.min(remcap, push.amount);
            if (doPush && fit != 0) {
                fs.amount += fit;
                notifyWatchers(push, fs.amount);
            }
            return voidExcess ? push.amount : fit;
        }
    }

    /**
     * Заполняет только указанный существующий слот совпадающей жидкостью с учётом фильтра и блокировки.
     * При симуляции содержимое не меняется; режим уничтожения избытка учитывается при расчёте принятого объёма.
     *
     * @param push жидкость и количество для заполнения
     * @param slot индекс жидкостного слота, начиная с нуля
     * @param doPush изменять хранилище; при {@code false} только рассчитать результат
     * @return принятый объём в литрах; при уничтожении избытка может превышать сохранённый объём
     */
    public int pushFluid(FluidStack push, int slot, boolean doPush) {
        if (push == null || push.amount < 0) {
            return 0;
        }
        if (locked) {
            return 0;
        }
        if (slot < 0 || slot >= fluids.size()) {
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
            final int remcap = Math.max(0, getCapacity() - fs.amount);
            final int fit = Math.min(remcap, push.amount);
            if (doPush && fit != 0) {
                fs.amount += fit;
                notifyWatchers(fs, fs.amount);
            }
            return voidExcess ? push.amount : fit;
        }
    }

    /**
     * Извлекает доступный объём совпадающей жидкости, учитывая блокировку.
     * При реальном извлечении удаляет опустошённый слот, если фильтр не требует его сохранить; симуляция не меняет список.
     *
     * @param pull жидкость и количество для извлечения
     * @param doPull изменять хранилище; при {@code false} только рассчитать результат
     * @return извлечённый объём в литрах или ноль, если операция недоступна
     */
    public int pullFluid(FluidStack pull, boolean doPull) {
        if (pull == null || pull.amount < 0) {
            return 0;
        }
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
            if (doPull && src.amount == 0 && !filter.keepFluid(src)) {
                fluids.remove(src);
                notifyWatchers(src, 0);
            }
            return rec;
        }
    }

    /**
     * Извлекает жидкость только из указанного существующего слота при совпадении типа и NBT.
     * При симуляции список не меняется; при опустошении слот сохраняется только по требованию фильтра.
     *
     * @param pull жидкость и количество для извлечения
     * @param slot индекс жидкостного слота, начиная с нуля
     * @param doPull изменять хранилище; при {@code false} только рассчитать результат
     * @return извлечённый объём в литрах или ноль, если операция недоступна
     */
    public int pullFluid(FluidStack pull, int slot, boolean doPull) {
        if (pull == null || pull.amount < 0) {
            return 0;
        }
        if (locked) {
            return 0;
        }
        if (slot < 0 || slot >= fluids.size()) {
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
            if (doPull && src.amount == 0 && !filter.keepFluid(src)) {
                fluids.remove(src);
                notifyWatchers(src, 0);
            }
            return rec;
        }
    }

    /**
     * Симулирует вставку положительного объёма с учётом фильтра, блокировки и уничтожения избытка.
     *
     * @param push жидкость и количество для заполнения
     * @return {@code true}, если можно принять хотя бы часть жидкости
     */
    public boolean couldPush(FluidStack push) {
        return push != null && push.amount > 0 && pushFluid(push, false) > 0;
    }
}
