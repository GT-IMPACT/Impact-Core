package com.impact.addon.vw

import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.world.chunk.Chunk
import space.gtimpact.virtual_world.api.FluidGenerator.getVein
import space.gtimpact.virtual_world.api.OreGenerator.getVeinAndChunk
import space.gtimpact.virtual_world.api.VirtualAPI
import space.gtimpact.virtual_world.common.items.ScannerTool.Companion.TYPE_FLUIDS
import space.gtimpact.virtual_world.common.items.ScannerTool.Companion.TYPE_ORES
import space.gtimpact.virtual_world.extras.send
import space.gtimpact.virtual_world.extras.toTranslate
import space.gtimpact.virtual_world.network.FindVeinsPacket
import space.gtimpact.virtual_world.network.VirtualOresNetwork

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

        val chX = te.xCoord shr 4
        val chZ = te.zCoord shr 4

        val chunks: ArrayList<Chunk> = arrayListOf()
        for (x in -radius..radius) {
            for (z in -radius..radius) {
                if (x != -radius && x != radius && z != -radius && z != radius) {
                    chunks += te.world.getChunkFromChunkCoords(chX + x, chZ + z)
                }
            }
        }
        val packet = FindVeinsPacket(chX, chZ, player.posX.toInt(), player.posZ.toInt(), radius - 1, type)
        for (chunk in chunks) {
            when (type) {
                TYPE_ORES -> scanOres(chunk, packet, layer)
                TYPE_FLUIDS -> scanFluids(chunk, packet)
            }
        }
        packet.level = radius - 1
        VirtualOresNetwork.sendToPlayer(packet, player as EntityPlayerMP)
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

    /**
     * Scanning Virtual Fluids
     */
    private fun scanFluids(chunk: Chunk, packet: FindVeinsPacket) {
        VirtualAPI.generateFluidRegion(chunk).also { region ->
            region.getVein(chunk)?.let { veinFluid ->
                VirtualAPI.getVirtualFluidVeinById(veinFluid.fluidId).also { vein ->
                    val size = veinFluid.size.toDouble() / vein.rangeSize.last.toDouble() * 100.0
                    fillPacketForChunk(chunk, packet, vein.id, size.toInt() / 1000)
                }
            }
        }
    }

    /**
     * Scanning Virtual Ores
     */
    private fun scanOres(chunk: Chunk, packet: FindVeinsPacket, layer: Int) {
        VirtualAPI.generateOreRegion(chunk).also { region ->
            region.getVeinAndChunk(chunk, layer)?.let { (veinOre, chunkOre) ->
                VirtualAPI.getVirtualOreVeinInChunk(veinOre, layer, region.dim)?.also { ore ->
                    val size = chunkOre.size.toDouble() / ore.rangeSize.last.toDouble() * 100.0
                    fillPacketForChunk(chunk, packet, ore.id, size.toInt() / 1000)
                }
            }
        }
    }

    /**
     * Fill packet by chunk`s coordinates
     */
    private fun fillPacketForChunk(chunk: Chunk, packet: FindVeinsPacket, idComponent: Int, size: Int) {
        for (xx in 0..15) {
            for (zz in 0..15) {
                packet.addRenderComponent(chunk.xPosition * 16 + xx, chunk.zPosition * 16 + zz, idComponent, size)
            }
        }
    }
}
