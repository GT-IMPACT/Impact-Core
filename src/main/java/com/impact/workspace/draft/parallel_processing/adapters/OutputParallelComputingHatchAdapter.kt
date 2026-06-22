package com.impact.workspace.draft.parallel_processing.adapters

import com.impact.workspace.draft.parallel_processing.integration.gt.computing.hatch.OutputParallelComputingHatch
import gregtech.api.interfaces.tileentity.IGregTechTileEntity

internal fun OutputParallelComputingHatch.isParallelHatchOnline(): Boolean {
    return isParallelHatchAlive()
}

internal fun OutputParallelComputingHatch.isParallelHatchAlive(): Boolean {
    val te = baseMetaTileEntity
    if (!te.isServerSide || te.isDead) return false

    val current = te.getTileEntity(te.xCoord, te.yCoord.toInt(), te.zCoord) as? IGregTechTileEntity ?: return false
    return current.metaTileEntity === this
}
