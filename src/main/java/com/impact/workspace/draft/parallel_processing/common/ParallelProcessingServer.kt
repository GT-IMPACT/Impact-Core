package com.impact.workspace.draft.parallel_processing.common

import com.impact.workspace.draft.comms.adapters.commsPos
import com.impact.workspace.draft.comms.adapters.commsWorldKey
import com.impact.workspace.draft.comms.common.CommsWorldKey
import com.impact.workspace.draft.parallel_processing.integration.gt.computing.hatch.OutputParallelComputingHatch
import com.impact.workspace.draft.parallel_processing.integration.gt.processing.BaseParallelProcessingMachine

object ParallelProcessingServer {

    private val worlds = LinkedHashMap<CommsWorldKey, ParallelProcessingWorldNetwork>()

    fun registerMachine(machine: BaseParallelProcessingMachine<*>) {
        getWorld(machine.baseMetaTileEntity.commsWorldKey()).registerMachine(machine)
    }

    fun unregisterMachine(machine: BaseParallelProcessingMachine<*>) {
        getWorld(machine.baseMetaTileEntity.commsWorldKey()).unregisterMachine(machine.parallelMachineId)
    }

    fun refreshMachineLink(machine: BaseParallelProcessingMachine<*>): ParallelLinkStatus {
        return getWorld(machine.baseMetaTileEntity.commsWorldKey())
            .refreshMachineLink(machine)
    }

    fun registerOutputHatch(hatch: OutputParallelComputingHatch) {
        getWorld(hatch.baseMetaTileEntity.commsWorldKey())
            .registerOutputHatch(hatch)
    }

    fun unregisterOutputHatch(hatch: OutputParallelComputingHatch) {
        getWorld(hatch.baseMetaTileEntity.commsWorldKey())
            .unregisterOutputHatch(hatch.baseMetaTileEntity.commsPos())
    }

    fun getLinkedMachine(hatch: OutputParallelComputingHatch): BaseParallelProcessingMachine<*>? {
        return getWorld(hatch.baseMetaTileEntity.commsWorldKey())
            .getLinkedMachine(hatch.baseMetaTileEntity.commsPos())
    }

    private fun getWorld(world: CommsWorldKey): ParallelProcessingWorldNetwork {
        return worlds.getOrPut(world) {
            ParallelProcessingWorldNetwork(world)
        }
    }
}
