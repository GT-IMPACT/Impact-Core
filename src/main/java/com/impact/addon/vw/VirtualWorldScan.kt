package com.impact.addon.vw

import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.chunk.Chunk
import space.gtimpact.virtual_world.api.OreVeinCount
import space.gtimpact.virtual_world.api.VirtualAPI

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

        VirtualAPI.scanOres(te.world, 0, player, radius, true)
        VirtualAPI.scanOres(te.world, 1, player, radius, true)
        VirtualAPI.scanFluids(te.world, player, radius, true)
    }

    @JvmStatic
    fun scanVeinOre(chunk: Chunk, layer: Int, player: EntityPlayer, needScanSize: Boolean = false): OreVeinCount? {
        VirtualAPI.scanOres(chunk.worldObj, layer, player, 4, needScanSize = needScanSize)
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
}
