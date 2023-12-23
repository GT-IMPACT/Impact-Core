package com.impact.mods.gregtech.tileentities.covers

import gregtech.api.interfaces.tileentity.ICoverable
import gregtech.api.interfaces.tileentity.IGearEnergyTileEntity
import gregtech.api.util.GT_CoverBehavior
import net.minecraft.entity.player.EntityPlayer

class GTCDebugEnergy : GT_CoverBehavior() {

    override fun doCoverThings(aSide: Byte, aInputRedstone: Byte, aCoverID: Int, aCoverVariable: Int, te: ICoverable?, aTimer: Long): Int {

        if (te is IGearEnergyTileEntity) {

            te.injectEnergyUnits(6.toByte(), te.inputVoltage, te.inputAmperage)
        }

        return super.doCoverThings(aSide, aInputRedstone, aCoverID, aCoverVariable, te, aTimer)
    }

    override fun onCoverScrewdriverclick(aSide: Byte, aCoverID: Int, aCoverVariable: Int, aTileEntity: ICoverable?, aPlayer: EntityPlayer, aX: Float, aY: Float, aZ: Float): Int {
        return super.onCoverScrewdriverclick(aSide, aCoverID, aCoverVariable, aTileEntity, aPlayer, aX, aY, aZ)
    }

    override fun getTickRate(aSide: Byte, aCoverID: Int, aCoverVariable: Int, aTileEntity: ICoverable?): Int {
        return 1
    }
}
