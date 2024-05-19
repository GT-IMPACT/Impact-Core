@file:Suppress("VulnerableCodeUsages")

package com.impact.network.special

import baubles.api.BaublesApi
import com.google.common.io.ByteArrayDataInput
import com.impact.common.armor.MaskOfVision
import com.impact.impact
import com.impact.util.vector.Vector3i
import io.netty.buffer.ByteBufOutputStream
import net.minecraft.client.Minecraft
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import space.impact.packet_network.network.packets.ImpactPacket

class LaserPushPacket(
    packetId: Int,
    val dim: Int = 0,
    private val vec1: Vector3i = Vector3i(),
    private val vec2: Vector3i = Vector3i(),
    val color: Int = 0,
    val mode: Int = 0,
    private val lifeTime: Int = 20,
    val type: Int = 1,
    private val endMode: Float = 1f,
) : ImpactPacket(packetId) {

    fun transaction(dim: Int, vec1: Vector3i, vec2: Vector3i, color: Int, mode: Int): LaserPushPacket {
        return LaserPushPacket(getPacketId(), dim, vec1, vec2, color, mode, 20, 1, 1f)
    }

    fun transaction(dim: Int, vec1: Vector3i, vec2: Vector3i, color: Int, mode: Int, lifeTime: Int, type: Int, endMode: Float): LaserPushPacket {
        return LaserPushPacket(getPacketId(), dim, vec1, vec2, color, mode, lifeTime, type, endMode)
    }

    override fun decode(input: ByteArrayDataInput): ImpactPacket {
        return LaserPushPacket(
            getPacketId(),
            dim = input.readInt(),
            vec1 = Vector3i(input.readInt(), input.readInt(), input.readInt()),
            vec2 = Vector3i(input.readInt(), input.readInt(), input.readInt()),
            color = input.readInt(),
            mode = input.readInt(),
            lifeTime = input.readInt(),
            type = input.readInt(),
            endMode = input.readFloat(),
        )
    }

    override fun encode(output: ByteBufOutputStream) {
        output.writeInt(dim)
        output.writeInt(vec1.x); output.writeInt(vec1.y); output.writeInt(vec1.z)
        output.writeInt(vec2.x); output.writeInt(vec2.y); output.writeInt(vec2.z)
        output.writeInt(color)
        output.writeInt(mode)
        output.writeInt(lifeTime)
        output.writeInt(type)
        output.writeFloat(endMode)
    }

    override fun processClient(mc: Minecraft, world: IBlockAccess) {
        if (world is World && dim == world.provider.dimensionId) {

            var mask = false
            val player = mc.thePlayer
            var stack = player.getCurrentArmor(3)
            if (stack == null || stack.item !is MaskOfVision) {
                val handler = BaublesApi.getBaubles(player)
                if (handler != null) {
                    for (i in 0 until handler.sizeInventory) {
                        stack = handler.getStackInSlot(i)
                        if (stack != null && stack.item is MaskOfVision) {
                            mask = true
                            break
                        }
                    }
                }
            } else {
                mask = true
            }

            when (mode) {
                0 -> if (mask) {
                    impact.proxy.beam(world, vec1, vec2, type, color, false, endMode, lifeTime)
                }

                1 -> if (mask) {
                    val translate = 0.5
                    impact.proxy.beam(
                        world,
                        vec1.x() + translate,
                        vec1.y() + translate,
                        vec1.z() + translate,
                        vec2.x() + translate,
                        vec2.y() + translate,
                        vec2.z() + translate, type,
                        color, false,
                        endMode, lifeTime
                    )
                }
            }
        }
    }
}
