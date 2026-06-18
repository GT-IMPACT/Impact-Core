package com.impact.workspace.draft.parallel_processing.integration.gt.processing.hatch

import com.impact.util.Utilits
import com.impact.workspace.draft.comms.integration.gt.executor.ExecutorCommunicationHatch
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity

class InputParallelComputingHatch : ExecutorCommunicationHatch {

    private val maxPpu: Int

    constructor(id: Int, nameRegional: String, tier: Int, maxPpu: Int)
            : super(id, "impact.machine.parallelhatch.in.$maxPpu", nameRegional, tier, desc(maxPpu)) {
        this.maxPpu = maxPpu
    }

    constructor(name: String, tier: Int, description: Array<String>, textures: Array<Array<Array<ITexture>>>, maxPpu: Int)
            : super(name, tier, description, textures) {
        this.maxPpu = maxPpu
    }

    override fun newMetaEntity(te: IGregTechTileEntity): IMetaTileEntity {
        return InputParallelComputingHatch(mName, mTier.toInt(), mDescriptionArray, mTextures, maxPpu)
    }

    fun getMaxPpu(): Int {
        return maxPpu
    }

    companion object {
        private fun desc(maxPpu: Int) = arrayOf(
            Utilits.impactTag(),
            "PPU Receiver",
            "Received up to $maxPpu PPU",
            "Used in multiple machines",
        )
    }
}