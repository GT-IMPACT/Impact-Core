package com.impact.util.thread

import com.impact.core.Config
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ImpactThread {

    private var service: ExecutorService = Executors.newFixedThreadPool(3)

    /**
     * Выполнить действие в пуле тредов и вернуть результат, если включено в конфиге
     */
    fun <T> addAction(action: Callable<T>, onDone: (T) -> Unit) {
        if (Config.isEnabledExperimentalMultiThreading)
            onDone(service.submit(action).get())
        else
            onDone(action.call())
    }

    /**
     * Выполнить действие в пуле тредов, если включено в конфиге
     */
    fun addAction(action: Runnable) {
        if (Config.isEnabledExperimentalMultiThreading)
            service.execute(action)
        else
            action.run()
    }
}
