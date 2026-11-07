package com.impact.mods.gregtech.tileentities.covers

import gregtech.api.covers.CoverContext
import gregtech.api.interfaces.tileentity.IGearEnergyTileEntity
import gregtech.common.covers.Cover

public class GTCDebugEnergy(context: CoverContext) : Cover(
    context,
    null,
) {

    public override fun doCoverThings(
        redstone: Byte,
        timer: Long,
    ) {
        val tile = getTile()
        if (tile is IGearEnergyTileEntity) {
            tile.injectEnergyUnits(
                6.toByte(),
                tile.inputVoltage,
                tile.inputAmperage,
            )
        }
    }

    public override fun getMinimumTickRate(): Int {
        return 1
    }
}
