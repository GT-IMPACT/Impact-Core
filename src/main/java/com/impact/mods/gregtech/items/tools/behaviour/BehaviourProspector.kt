package com.impact.mods.gregtech.items.tools.behaviour

import gregtech.api.GregTech_API
import gregtech.api.items.GT_MetaBase_Item
import gregtech.api.util.GT_Utility
import gregtech.common.items.behaviors.Behaviour_None
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import space.gtimpact.virtual_world.api.VirtualAPI
import space.gtimpact.virtual_world.api.VirtualAPI.LAYERS_VIRTUAL_ORES
import space.gtimpact.virtual_world.config.Config
import space.gtimpact.virtual_world.extras.send
import space.gtimpact.virtual_world.extras.toTranslate

class BehaviourProspector(
    private val tier: Int,
    private val radiusWork: Int,
) : Behaviour_None() {

    companion object {
        internal const val TYPE_ORES_0 = 0
        internal const val TYPE_ORES_1 = 1
        internal const val TYPE_FLUIDS = 2

        internal const val TYPES_COUNT = 3

        internal const val NBT_TYPE = "type_mode"
        internal const val NBT_LAYER = "layer_id"
    }

    private fun ItemStack.setNBT(data: Int, key: String) {
        val nbt = tagCompound ?: NBTTagCompound().apply { tagCompound = this }
        val props = nbt.getTag("props") ?: NBTTagCompound().apply { nbt.setTag("props", this) }
        (props as NBTTagCompound).setInteger(key, data)
    }

    private fun ItemStack.getNBTInt(key: String): Int {
        val nbt = tagCompound?.getCompoundTag("props") ?: return 0
        return nbt.getInteger(key)
    }

    fun changeLayer(player: EntityPlayer, stack: ItemStack, layer: Int) {
        // Set ore layer #
        player.send("scanner.change_layer".toTranslate() + layer)
        stack.setNBT(layer, NBT_LAYER)
    }

    override fun onItemUseFirst(
        item: GT_MetaBase_Item, stack: ItemStack, player: EntityPlayer,
        world: World, aX: Int, aY: Int, aZ: Int, aSide: Int, hitX: Float,
        hitY: Float, hitZ: Float,
    ): Boolean {
        if (player is EntityPlayerMP) {

            var type = stack.getNBTInt(NBT_TYPE)
            val layer = stack.getNBTInt(NBT_LAYER)

            when (stack.stackSize) {
                //for debug
                2 -> VirtualAPI.addCustomObject(stack, "Test Point ${world.rand.nextInt(99)}", player)

                in 3..64 -> if (Config.enableDebug && player.capabilities.isCreativeMode) {
                    val chunk = world.getChunkFromBlockCoords(player.posX.toInt(), player.posZ.toInt())

                    when (type) {
                        TYPE_ORES_0, TYPE_ORES_1 -> VirtualAPI.extractOreFromChunk(chunk, layer, 1000 * stack.stackSize)?.also { data ->
                            player.send("${data.vein.name}: ${data.size}")
                        }

                        TYPE_FLUIDS -> VirtualAPI.extractFluidFromVein(chunk, 1000 * stack.stackSize / 16)?.also { data ->
                            player.send("${data.vein.name}: ${data.size}")
                        }
                    }
                }

                else -> {
                    if (player.isSneaking) {
                        type++

                        if (type >= TYPES_COUNT) {
                            type = 0
                        }

                        when(type) {
                            TYPE_ORES_0 -> {
                                player.send("scanner.change_mode.0".toTranslate()) //Set mod: Underground Ores
                                changeLayer(player, stack, 0)
                            }
                            TYPE_ORES_1 -> {
                                player.send("scanner.change_mode.0".toTranslate()) //Set mod: Underground Ores
                                changeLayer(player, stack, 1)
                            }
                            TYPE_FLUIDS -> {
                                player.send("scanner.change_mode.1".toTranslate()) //Set mod: Underground Fluids
                            }
                        }
                        stack.setNBT(type, NBT_TYPE)
                        return true
                    }

                    val discharge = item.getMaxCharge(stack) * .1

                    if (item.canUse(stack, discharge)) {
                        item.discharge(stack, discharge, tier, true, false, false)
                        GT_Utility.doSoundAtClient(
                            GregTech_API.sSoundList[108],
                            1, 1.0f,
                            aX.toDouble(), aY.toDouble(), aZ.toDouble()
                        )
                        when (type) {
                            TYPE_ORES_0, TYPE_ORES_1 -> VirtualAPI.scanOres(world, layer, player, radiusWork, true)
                            TYPE_FLUIDS -> VirtualAPI.scanFluids(world, player, radiusWork, true)
                        }
                    }
                }
            }

            return true
        }
        return false
    }

    override fun getAdditionalToolTips(item: GT_MetaBase_Item?, tooltip: MutableList<String>, stack: ItemStack): List<String> {

        val mode = stack.getNBTInt(NBT_TYPE)
        val layer = stack.getNBTInt(NBT_LAYER)

        tooltip += "scanner.tooltip.0".toTranslate() // Change scanner mode: SHIFT + Right Click
        val modName = when (mode) {
            TYPE_ORES_0, TYPE_ORES_1 -> "scanner.tooltip.2".toTranslate() // Virtual Ores
            TYPE_FLUIDS -> "scanner.tooltip.4".toTranslate() // Virtual Fluids else
            else -> ""
        }
        // Current scanner mode:
        tooltip += "scanner.tooltip.1".toTranslate() + " " + modName
        if (mode != TYPE_FLUIDS) {
            tooltip += "scanner.tooltip.6".toTranslate() + layer // Current ore layer: #
        }
        // To scan the area use Right Click
        tooltip += "scanner.tooltip.5".toTranslate()
        tooltip += ""
        tooltip += "scanner.tooltip.7".toTranslate(radiusWork)
        tooltip += ""

        if (Config.enableDebug)
            tooltip += listOf(
                "2 stackSize create point item",
                "3..64 stackSize extract current chunk stackSize * 1000",
            )

        return super.getAdditionalToolTips(item, tooltip, stack)
    }
}