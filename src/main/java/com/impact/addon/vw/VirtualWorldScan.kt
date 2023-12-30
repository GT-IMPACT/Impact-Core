package com.impact.addon.vw

import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import space.gtimpact.virtual_world.common.items.ScannerTool.Companion.TYPE_FLUIDS
import space.gtimpact.virtual_world.common.items.ScannerTool.Companion.TYPE_ORES
import space.gtimpact.virtual_world.extras.send
import space.gtimpact.virtual_world.extras.toTranslate

object VirtualWorldScan {

    @JvmStatic
    fun scanStart(te: IGregTechTileEntity, tierScanner: Int, type: Int, layer: Int, player: EntityPlayer) {
        val radius = when(tierScanner) {
            2 -> 9
            3 -> 11
            4 -> 13
            5 -> 15
            else -> 7
        }

        when (type) {
            TYPE_ORES -> space.gtimpact.virtual_world.api.prospect.scanOres(te.world, layer, player as EntityPlayerMP, radius)
            TYPE_FLUIDS -> space.gtimpact.virtual_world.api.prospect.scanFluids(te.world, player as EntityPlayerMP, radius)
        }
    }

    @JvmStatic
    fun sendChatChangeType(player: EntityPlayer, type: Int) {
        when (type) {
            TYPE_ORES -> player.send("scanner.change_mode.0".toTranslate())
            TYPE_FLUIDS -> player.send("scanner.change_mode.1".toTranslate())
        }
    }

    @JvmStatic
    fun sendChatChangeLayer(player: EntityPlayer, layer: Int) {
        player.send("scanner.change_layer".toTranslate() + layer)
    }
}
