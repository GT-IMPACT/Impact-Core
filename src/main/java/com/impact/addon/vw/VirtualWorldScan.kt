package com.impact.addon.vw

import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.world.chunk.Chunk
import space.gtimpact.virtual_world.api.OreVeinCount
import space.gtimpact.virtual_world.api.VirtualAPI
import space.gtimpact.virtual_world.api.prospect.scanFluids
import space.gtimpact.virtual_world.api.prospect.scanOres
import space.gtimpact.virtual_world.common.items.ScannerTool.Companion.TYPE_FLUIDS
import space.gtimpact.virtual_world.common.items.ScannerTool.Companion.TYPE_ORES
import space.gtimpact.virtual_world.extras.send
import space.gtimpact.virtual_world.extras.toTranslate

object VirtualWorldScan {

    @JvmStatic
    fun scanStart(te: IGregTechTileEntity, tierScanner: Int, player: EntityPlayer) {
        val radius = when (tierScanner) {
            2 -> 9
            3 -> 11
            4 -> 13
            5 -> 15
            else -> 7
        }

        scanOres(te.world, 0, player as EntityPlayerMP, radius)
        scanOres(te.world, 1, player, radius)
        scanFluids(te.world, player, radius)
    }

    @JvmStatic
    fun scanVeinOre(chunk: Chunk, layer: Int, player: EntityPlayer, needScanSize: Boolean = false): OreVeinCount? {
        scanOres(chunk.worldObj, layer, player, 4, needScanSize = needScanSize)
        val list = arrayListOf<OreVeinCount?>()
        VirtualAPI.getVeinChunks(chunk).forEach {
            chunk.worldObj.getChunkFromChunkCoords(it.chunkXPos, it.chunkZPos)?.also { ch ->
                list += VirtualAPI.getOreInfoChunk(ch, layer)
            }
        }
        return list.firstOrNull()?.let { count ->
            count.copy(size = list.sumOf { it?.size ?: 0 })
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
