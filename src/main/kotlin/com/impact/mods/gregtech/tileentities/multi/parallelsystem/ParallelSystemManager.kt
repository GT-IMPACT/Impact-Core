package com.impact.mods.gregtech.tileentities.multi.parallelsystem

import com.impact.core.Impact_API
import com.impact.util.PositionObject
import com.impact.util.Utilits
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.util.GT_Utility
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumChatFormatting.*

object ParallelSystemManager {

    fun connectToSatellite(f: Int, p: EntityPlayer, te: IGregTechTileEntity): Boolean {
        val uuid = Utilits.inToStringUUID(f, p)
        val pos = PositionObject(te).coords

        val coord = Impact_API.sSpaceSatellite.put(uuid, pos)

        val text = if (coord != null) "Frequency: $YELLOW$f" else "Error connect: $RED"
        GT_Utility.sendChatToPlayer(p, text)

        return coord != null
    }

    fun connectToCommunicator(f: Int, p: EntityPlayer): PositionObject? {
        val coord = Impact_API.sSpaceSatellite[Utilits.inToStringUUID(f, p)]
        return coord?.let { PositionObject(it) }
    }

    fun connectToComputer() {
        //TODO
    }

    fun connectToMachine() {
        //TODO
    }
}