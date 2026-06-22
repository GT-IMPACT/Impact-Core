package com.impact.workspace.draft.parallel_processing.integration.gt.computing.delegate

import com.impact.workspace.draft.parallel_processing.integration.gt.computing.hatch.OutputParallelComputingHatch
import com.impact.workspace.draft.parallel_processing.integration.gt.computing.hatch.RackParallelComputingHatch
import com.impact.workspace.draft.parallel_processing.common.ParallelProcessingServer
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import java.util.UUID

class ParallelComputingMachineDelegate {

    private var parentId: UUID? = null
    private val computingRackHatches = arrayListOf<RackParallelComputingHatch>()
    private val ppuOutHatches = arrayListOf<OutputParallelComputingHatch>()

    fun setParent(parentId: UUID?) {
        this.parentId = parentId
    }

    fun clearHatches() {
        computingRackHatches.clear()
        ppuOutHatches.clear()
    }

    fun addMachineRack(igt: IGregTechTileEntity?, textureIndex: Int): Boolean {
        val mte = igt?.metaTileEntity as? RackParallelComputingHatch ?: return false
        mte.updateTexture(textureIndex)
        mte.bindParent(parentId)
        return computingRackHatches.add(mte)
    }

    fun addMachineCommunication(te: IGregTechTileEntity?, textureIndex: Int): Boolean {
        val mte = te?.metaTileEntity as? OutputParallelComputingHatch ?: return false
        mte.updateTexture(textureIndex)
        mte.bindParent(parentId)
        return ppuOutHatches.add(mte)
    }

    fun inactiveCommunication() {
        ppuOutHatches.forEach { hatch ->
            hatch.baseMetaTileEntity.isActive = false
            ParallelProcessingServer.getLinkedMachine(hatch)?.applyReceivedPpu(0)
        }
    }

    fun inactive() {
        inactiveCommunication()
        computingRackHatches.forEach { hatch ->
            hatch.baseMetaTileEntity.isActive = false
        }
    }

    fun removeBindings() {
        inactive()
        ppuOutHatches.forEach { hatch ->
            hatch.removeBinding()
        }
    }

    fun calculatePpu(): Int {
        val parentId = parentId ?: return 0
        var tempPpu = 0
        computingRackHatches.forEach { hatch ->
            val ppu = hatch.getPpu(parentId)
            tempPpu += ppu
            hatch.baseMetaTileEntity.isActive = ppu > 0
        }
        return tempPpu
    }

    fun transmitPpu(initialPpu: Int): Int {
        var tempPpu = initialPpu

        ppuOutHatches.forEach { hatch ->
            val machine = ParallelProcessingServer.getLinkedMachine(hatch)
            if (machine == null) {
                hatch.baseMetaTileEntity.isActive = false
                return@forEach
            }

            val transferredPpu = tempPpu.coerceAtMost(hatch.maxPpuTransfer)
            machine.applyReceivedPpu(transferredPpu)
            hatch.baseMetaTileEntity.isActive = transferredPpu > 0
            tempPpu -= transferredPpu
        }

        return tempPpu
    }
}
