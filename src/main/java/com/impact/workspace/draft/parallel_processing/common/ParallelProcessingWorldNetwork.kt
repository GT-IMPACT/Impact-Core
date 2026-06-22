package com.impact.workspace.draft.parallel_processing.common

import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.adapters.commsPos
import com.impact.workspace.draft.comms.common.CommsPos
import com.impact.workspace.draft.comms.common.CommsWorldKey
import com.impact.workspace.draft.parallel_processing.adapters.isParallelHatchAlive
import com.impact.workspace.draft.parallel_processing.adapters.isParallelHatchOnline
import com.impact.workspace.draft.parallel_processing.adapters.isParallelMachineAlive
import com.impact.workspace.draft.parallel_processing.adapters.isParallelMachineOnline
import com.impact.workspace.draft.parallel_processing.integration.gt.computing.hatch.OutputParallelComputingHatch
import com.impact.workspace.draft.parallel_processing.integration.gt.processing.BaseParallelProcessingMachine
import java.util.UUID

class ParallelProcessingWorldNetwork(
    private val world: CommsWorldKey,
) {
    private val machines = LinkedHashMap<UUID, BaseParallelProcessingMachine<*>>()
    private val outputHatches = LinkedHashMap<CommsPos, OutputParallelComputingHatch>()
    private val machineToHatch = LinkedHashMap<UUID, CommsPos>()
    private val hatchToMachine = LinkedHashMap<CommsPos, UUID>()

    fun registerMachine(machine: BaseParallelProcessingMachine<*>) {
        machines[machine.parallelMachineId] = machine
    }

    fun unregisterMachine(machineId: UUID) {
        machines.remove(machineId)
        unlinkMachine(machineId)
    }

    fun registerOutputHatch(hatch: OutputParallelComputingHatch) {
        outputHatches[hatch.baseMetaTileEntity.commsPos()] = hatch
    }

    fun unregisterOutputHatch(pos: CommsPos) {
        outputHatches.remove(pos)
        unlinkHatch(pos)
    }

    fun getLinkedMachine(pos: CommsPos): BaseParallelProcessingMachine<*>? {
        purgeDeadEntries()

        val machineId = hatchToMachine[pos] ?: return null
        return machines[machineId]
    }

    fun refreshMachineLink(machine: BaseParallelProcessingMachine<*>): ParallelLinkStatus {
        registerMachine(machine)
        purgeDeadEntries()

        if (!machine.isParallelMachineOnline()) {
            unlinkMachine(machine.parallelMachineId)
            return ParallelLinkStatus.MACHINE_OFFLINE
        }

        val requiredPpu = machine.getRequiredPpu()
        if (requiredPpu <= 0) {
            unlinkMachine(machine.parallelMachineId)
            return ParallelLinkStatus.NO_INPUT_HATCH
        }

        val currentPos = machineToHatch[machine.parallelMachineId]
        val currentHatch = currentPos?.let(outputHatches::get)
        if (currentHatch != null && isValidLink(machine, currentHatch, requiredPpu)) {
            machine.applyParallelLinkedComputer(currentHatch.getParentId())
            return ParallelLinkStatus.SUCCESS
        }

        unlinkMachine(machine.parallelMachineId)

        val matchingHatches = outputHatches.values
            .filter { it.maxPpuTransfer == requiredPpu && it.isParallelHatchOnline() }
            .sortedWith(
                compareBy<OutputParallelComputingHatch> { it.baseMetaTileEntity.xCoord }
                    .thenBy { it.baseMetaTileEntity.yCoord }
                    .thenBy { it.baseMetaTileEntity.zCoord }
            )

        if (matchingHatches.isEmpty()) {
            machine.applyParallelLinkedComputer(null)
            return ParallelLinkStatus.NO_MATCHING_HATCH
        }

        val routedHatches = matchingHatches.filter { hatch ->
            val computerId = hatch.getParentId() ?: return@filter false
            CommsServer.hasConnection(
                world = world,
                computerId = computerId,
                executorId = machine.parallelMachineId,
            )
        }

        if (routedHatches.isEmpty()) {
            machine.applyParallelLinkedComputer(null)
            return ParallelLinkStatus.NO_COMMS_NETWORK
        }

        val freeHatch = routedHatches.firstOrNull { hatch ->
            val linkedMachineId = hatchToMachine[hatch.baseMetaTileEntity.commsPos()]
            linkedMachineId == null || linkedMachineId == machine.parallelMachineId
        }

        if (freeHatch == null) {
            machine.applyParallelLinkedComputer(null)
            return ParallelLinkStatus.HATCH_ALREADY_IN_USE
        }

        val pos = freeHatch.baseMetaTileEntity.commsPos()
        machineToHatch[machine.parallelMachineId] = pos
        hatchToMachine[pos] = machine.parallelMachineId
        machine.applyParallelLinkedComputer(freeHatch.getParentId())
        return ParallelLinkStatus.SUCCESS
    }

    private fun isValidLink(
        machine: BaseParallelProcessingMachine<*>,
        hatch: OutputParallelComputingHatch,
        requiredPpu: Int,
    ): Boolean {
        if (hatch.maxPpuTransfer != requiredPpu) return false
        if (!hatch.isParallelHatchOnline()) return false

        val computerId = hatch.getParentId() ?: return false
        return CommsServer.hasConnection(
            world = world,
            computerId = computerId,
            executorId = machine.parallelMachineId,
        )
    }

    private fun purgeDeadEntries() {
        machines.entries.removeIf { (_, machine) ->
            val dead = !machine.isParallelMachineAlive()
            if (dead) unlinkMachine(machine.parallelMachineId)
            dead
        }

        outputHatches.entries.removeIf { (pos, hatch) ->
            val dead = !hatch.isParallelHatchAlive()
            if (dead) unlinkHatch(pos)
            dead
        }
    }

    private fun unlinkMachine(machineId: UUID) {
        val hatchPos = machineToHatch.remove(machineId) ?: return
        hatchToMachine.remove(hatchPos)
        machines[machineId]?.applyParallelLinkedComputer(null)
    }

    private fun unlinkHatch(pos: CommsPos) {
        val machineId = hatchToMachine.remove(pos) ?: return
        machineToHatch.remove(machineId)
        machines[machineId]?.applyParallelLinkedComputer(null)
    }
}
