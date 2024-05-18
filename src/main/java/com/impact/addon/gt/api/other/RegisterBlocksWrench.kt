package com.impact.addon.gt.api.other

import gregtech.api.GregTech_API
import gregtech.api.interfaces.IActionWrench
import gregtech.api.util.GT_Log
import gregtech.api.util.GT_ModHandler
import net.minecraft.block.Block
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.world.World

object RegisterBlocksWrench {

    @JvmStatic
    fun register() {
        try {
            var stack = GT_ModHandler.getModItem("ae2fc", "certus_quartz_tank", 1L)
            if (stack != null) GregTech_API.registerWrenchBlock(Block.getBlockFromItem(stack.item), stack.itemDamage) { player, block, meta, te, side ->

                if (!player.isSneaking) return@registerWrenchBlock false

                return@registerWrenchBlock runCatching {
                    val clazz = Class.forName("com.glodblock.github.common.block.BlockCertusQuartzTank")
                    if (te == null) return@registerWrenchBlock false

                    if (clazz.isInstance(block)) {
                        te.worldObj?.also { world ->
                            val aX = te.xCoord
                            val aY = te.yCoord
                            val aZ = te.zCoord
                            val getDropWithNBT = clazz.getMethod("getDropWithNBT", World::class.java, Int::class.java, Int::class.java, Int::class.java)
                            (getDropWithNBT.invoke(block, world, aX, aY, aZ) as? ItemStack)?.also { stack ->
                                val f = 0.7f
                                val x: Double = (world.rand.nextFloat() * f).toDouble() + (1.0f - f).toDouble() * 0.5
                                val y: Double = (world.rand.nextFloat() * f).toDouble() + (1.0f - f).toDouble() * 0.5
                                val z: Double = (world.rand.nextFloat() * f).toDouble() + (1.0f - f).toDouble() * 0.5
                                val entityItem = EntityItem(world, aX.toDouble() + x, aY.toDouble() + y, aZ.toDouble() + z, stack)
                                entityItem.delayBeforeCanPickup = 10
                                world.spawnEntityInWorld(entityItem)
                                world.setBlockToAir(aX, aY, aZ)
                            }
                        }
                    }
                    return@registerWrenchBlock true
                }.getOrDefault(false)
            }

            val chestAction = IActionWrench { _, block, meta, te, side ->
                return@IActionWrench runCatching {
                    val clazz = Class.forName("chestup.TileEntityIronChest")
                    if (te == null) return@IActionWrench false
                    val aX = te.xCoord
                    val aY = te.yCoord
                    val aZ = te.zCoord
                    if (clazz.isInstance(te)) {
                        val field = clazz.getDeclaredField("facing")
                        field.isAccessible = true
                        val facing = field.getInt(te)
                        if (facing != side) {
                            val setFacingMethod = clazz.getMethod("setFacing", Int::class.java)
                            setFacingMethod.invoke(te, side)
                            te.worldObj?.markBlockForUpdate(aX, aY, aZ)
                        } else {
                            te.worldObj?.also { world ->
                                world.spawnEntityInWorld(EntityItem(world, aX + 0.5, aY + 0.5, aZ + 0.5, ItemStack(block, 1, meta)))
                                world.setBlockToAir(aX, aY, aZ)
                            }
                        }
                        return@IActionWrench true
                    }
                    return@IActionWrench false
                }.getOrDefault(false)
            }
            repeat(10) {
                stack = GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, it)
                if (stack != null) GregTech_API.registerWrenchBlock(Block.getBlockFromItem(stack.item), stack.itemDamage, chestAction)
            }
        } catch (e: Exception) {
            e.printStackTrace(GT_Log.err)
        }
    }
}
