package com.impact.network

import com.impact.common.block.itemblock.IB_IGlass
import com.impact.common.te.TilePlacedItem
import com.impact.loader.ItemRegistery
import com.impact.network.special.LaserPushPacket
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.client.Minecraft
import net.minecraftforge.common.util.ForgeDirection
import space.impact.packet_network.network.packets.IStreamPacketReceiver
import space.impact.packet_network.network.packets.createPacketStream

object NetworkPackets {

    @JvmField
    val StreamPacket = createPacketStream(1000) { isServer, data  ->
        (tileEntity as? IGregTechTileEntity)?.also { igt ->
            igt.metaTileEntity?.also { mte ->
                (mte as? IStreamPacketReceiver)?.also { sender ->
                    sender.receive(data)
                    if (!isServer) igt.issueTextureUpdate()
                }
            }
        }
        if (isServer) {
            serverPlayer?.also {
                val currentContainer = it.openContainer
                if (currentContainer is IStreamPacketReceiver) currentContainer.receive(data)
            }
        } else {
            val currentScreen = Minecraft.getMinecraft().currentScreen
            if (currentScreen is IStreamPacketReceiver) currentScreen.receive(data)
        }
    }

    @JvmField
    val PlacedItemsPacket = createPacketStream(1001) {  isServer, data  ->
        if (isServer) {
            data.readInt() //skip size array
            val side = data.readInt()
            serverWorld?.also { world ->
                val dir = ForgeDirection.getOrientation(side)
                val xx = data.readInt() + dir.offsetX
                val yy = data.readInt() + dir.offsetY
                val zz = data.readInt() + dir.offsetZ
                if (world.isAirBlock(xx, yy, zz) && serverPlayer?.heldItem != null) {
                    serverPlayer?.heldItem?.also { stack ->
                        world.setBlock(xx, yy, zz, ItemRegistery.placedItem, side, 2)
                        val placedItem = world.getTileEntity(xx, yy, zz) as? TilePlacedItem
                        placedItem?.also {
                            it.setStack(stack.copy())
                            serverPlayer?.destroyCurrentEquippedItem()
                        } ?: world.setBlockToAir(xx, yy, zz)
                    }
                }
            }
        }
    }

    @JvmField
    val MetaBlockGlassPacket = createPacketStream(1002) {  isServer, data  ->
        if (isServer) {
            val isBool = data.readBoolean()
            serverPlayer?.heldItem?.also { stack ->
                if (stack.item is IB_IGlass) {
                    val maxDmg = 15
                    var newDamage = stack.itemDamage
                    newDamage += if (isBool) 1 else -1
                    newDamage = if (newDamage > maxDmg) 0 else if (newDamage < 0) maxDmg else newDamage
                    stack.itemDamage = newDamage
                }
            }
        }
    }

    @JvmField
    val LaserPushPacket = LaserPushPacket(1003)

//    @JvmField
//    val RecipeToolPacket = createPacketStream(1004) { _, data ->
//       runCatching {
//           val player = serverPlayer ?: Minecraft.getMinecraft().thePlayer
//           player?.heldItem?.also { stack ->
//               val item = stack.item
//               if (item is RecipeToolItem) {
//                   item.updateMap(data.readInt())
//               }
//           }
//       }
//    }
}
