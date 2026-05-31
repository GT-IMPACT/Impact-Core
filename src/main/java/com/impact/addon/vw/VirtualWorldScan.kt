package com.impact.addon.vw

import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import space.gtimpact.virtual_world.api.VirtualAPI
import space.gtimpact.virtual_world.api.core.WorldPos
import space.gtimpact.virtual_world.api.core.toChunkPos
import space.gtimpact.virtual_world.api.services.scanning.ScanMode
import space.gtimpact.virtual_world.api.services.scanning.ores.OreChunkResult
import space.gtimpact.virtual_world.api.services.scanning.ores.OreVeinScanResult

object VirtualWorldScan {

    @JvmStatic
    fun scanAll(te: IGregTechTileEntity, tierScanner: Int, player: EntityPlayer) {
        val radius = when (tierScanner) {
            2 -> 1
            3 -> 3
            4 -> 6
            5 -> 9
            else -> 7
        }

        VirtualAPI.scannerManager.scanOres(
            player = player,
            mode = ScanMode.WITH_AMOUNT,
            layer = 0,
            dimensionId = te.world.provider.dimensionId,
            radiusVeins = radius,
        )

        VirtualAPI.scannerManager.scanOres(
            player = player,
            mode = ScanMode.WITH_AMOUNT,
            layer = 1,
            dimensionId = te.world.provider.dimensionId,
            radiusVeins = radius,
        )

        VirtualAPI.scannerManager.scanFluids(
            player = player,
            mode = ScanMode.WITH_AMOUNT,
            dimensionId = te.world.provider.dimensionId,
            radiusVeins = radius,
        )
    }

    @JvmStatic
    fun scanCurrentOreVein(
        dimensionId: Int,
        x: Int,
        z: Int,
        layer: Int,
    ): OreVeinScanResult? {
        return VirtualAPI.scanning.scanOreAroundBlock(
            dimensionId = dimensionId,
            centerBlockPos = WorldPos(x, z),
            layerIndex = layer,
            radiusVeins = 0,
            mode = ScanMode.WITH_AMOUNT,
        ).results.firstOrNull()
    }

    @JvmStatic
    fun getMaxOresInVein(
        scanResult: OreVeinScanResult,
    ): Int {
        return scanResult.chunks.sumOf { it.ore.rangeSize.last }
    }

    @JvmStatic
    fun scanCurrentOreChunk(
        dimensionId: Int,
        x: Int,
        z: Int,
        layer: Int,
    ): OreChunkResult? {
        val chunkPos = WorldPos(x, z).toChunkPos()
        return scanCurrentOreVein(
            dimensionId = dimensionId,
            x = x,
            z = z,
            layer = layer,
        )?.chunks?.firstOrNull { it.deposit.chunkPos == chunkPos }
    }

    @JvmStatic
    fun mineVeinAndGetRemaining(
        dimensionId: Int,
        pos: WorldPos,
        layer: Int,
        amount: Int,
    ): Pair<Int, Int>? {
        val vein = scanCurrentOreVein(
            dimensionId = dimensionId,
            x = pos.x,
            z = pos.z,
            layer = layer,
        ) ?: return null

        val results = vein.chunks.mapNotNull { chunk ->
            VirtualAPI.mining.mineOreAtChunk(
                dimensionId = dimensionId,
                chunkPos = chunk.deposit.chunkPos,
                layerIndex = layer,
                amount = amount,
            )
        }

        val sumMax = results.sumOf { it.ore.rangeSize.last }
        val sumRemaining = results.sumOf { it.remainingAmount }

        return sumRemaining to sumMax
    }
}
