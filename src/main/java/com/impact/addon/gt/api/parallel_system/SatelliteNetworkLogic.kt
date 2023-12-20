package com.impact.addon.gt.api.parallel_system

import com.impact.core.impactLog.INFO
import com.impact.util.thread.ImpactThread

object SatelliteNetworkLogic {

    private val towers: HashSet<INetworkTower> = hashSetOf()
    private var isStartedUpdate: Boolean = false

    /**
     * Сервер запущен
     */
    fun onStartServer() {
        towers.clear()
    }

    /**
     * Сервер остановлен
     */
    fun onStopServer() {
        towers.clear()
    }

    /**
     * Выполнить обновление всей сети
     */
    private fun updateNetwork() {
        INFO("=== START UPDATE SATELLITE NETWORK ===")

        val start = System.currentTimeMillis()

        towers.forEach { it.onUpdateConnections() }

        INFO(Thread.currentThread().name)
        INFO(" ${System.currentTimeMillis() - start} ms")

        isStartedUpdate = false

        INFO("=== END UPDATE SATELLITE NETWORK ===")
    }

    /**
     * Обновить всю сеть
     */
    fun reloadNetwork() {
        if (!isStartedUpdate) {
            isStartedUpdate = true
            ImpactThread.addAction { updateNetwork() }
        }
    }

    /**
     * Удалить башню из сети
     */
    fun createTower(tower: INetworkTower) {
        towers.add(tower)
    }

    /**
     * Добавить башню в сеть
     */
    fun removeTower(tower: INetworkTower) {
        towers.remove(tower)
    }
}

interface INetworkMachine {

    var isSatelliteConnected: Boolean

    /**
     * Добавить машину в сети и вызвать обновление всей сети
     */
    fun createConnect() {
        SatelliteNetworkLogic.reloadNetwork()
    }

    /**
     * Удалить машину из сети и вызвать обновление всей сети
     */
    fun removeConnect() {
        SatelliteNetworkLogic.reloadNetwork()
    }
}

interface INetworkTower : INetworkMachine {

    val connections: HashSet<INetworkMachine>

    /**
     * Проверка всех подключенных машин на валидность, а также подключение новых машин
     */
    fun onUpdateConnections()

    /**
     * Обновление статуса у всех подключенных машин
     */
    fun updateStatusConnections() {
        connections.forEach { it.isSatelliteConnected = isSatelliteConnected }
    }

    /**
     * Обновление внутренней сети башни
     */
    fun updateLocalNetwork()

    /**
     * Создание башни при установке в мир
     */
    fun createTower() {
        SatelliteNetworkLogic.createTower(this)
    }

    /**
     * Удаление башни при выгрузки из мира
     */
    fun removeTower() {
        SatelliteNetworkLogic.removeTower(this)
    }

    /** Не используется */
    override fun createConnect() = Unit
    override fun removeConnect() = Unit
}
