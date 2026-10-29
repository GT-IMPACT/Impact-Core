package com.impact.util.fluid;

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
    void onMultiFluidChange(FluidStack fluid, int newAmount);

    default void onMultiFluidStateChange() {
    }
}
