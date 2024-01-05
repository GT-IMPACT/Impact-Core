package com.impact.util.multis

import gregtech.GT_Mod
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase
import gregtech.api.util.GT_Recipe
import net.minecraft.world.chunk.Chunk

object WorldProperties {

    @JvmStatic
	fun needCleanroom(tRecipe: GT_Recipe, base: GT_MetaTileEntity_MultiBlockBase): Boolean {
        var isNotCleanroom = true
        if (tRecipe.mSpecialValue == -200 && (base.mCleanroom == null || base.mCleanroom.mEfficiency == 0)) {
            isNotCleanroom = false
            base.stopMachine()
        }
        return isNotCleanroom
    }

    @JvmStatic
	fun needSpace(tRecipe: GT_Recipe, base: GT_MetaTileEntity_MultiBlockBase): Boolean {
        var isNotSpace = true
        if (GT_Mod.gregtechproxy.mLowGravProcessing && tRecipe.mSpecialValue == -100
            && !GT_MetaTileEntity_BasicMachine.isValidForLowGravity(tRecipe, base.baseMetaTileEntity.world.provider.dimensionId)) {
            isNotSpace = false
            base.stopMachine()
        }
        return isNotSpace
    }

    val IGregTechTileEntity.chunk: Chunk
        get() = world.getChunkFromBlockCoords(xCoord, zCoord)
}
