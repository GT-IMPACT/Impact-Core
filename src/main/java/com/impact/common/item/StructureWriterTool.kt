package com.impact.common.item

import com.impact.core.Impact_API
import com.impact.impact
import com.impact.util.ItemNBTHelper.getInteger
import com.impact.util.ItemNBTHelper.setInteger
import com.impact.util.vector.Box
import com.impact.util.vector.Structure.getVectorBySide
import com.impact.util.vector.Vector3i
import cpw.mods.fml.common.registry.GameRegistry
import gregtech.api.util.GT_Utility
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import space.gtimpact.virtual_world.extras.send
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing
import space.impact.api.multiblocks.structure.StructureUtility
import java.awt.Color
import kotlin.math.abs

class StructureWriterTool : Item() {

    companion object {
        @JvmField
        val INSTANCE = StructureWriterTool()
    }

    fun registerItem() {
        super.setHasSubtypes(true)
        val unlocalizedName = "impact_structure_writer_tool"
        super.setUnlocalizedName(unlocalizedName)
        GameRegistry.registerItem(INSTANCE, unlocalizedName)
    }

    var startVec = Vector3i()
    var endVec = Vector3i()

    override fun onItemUse(
        stack: ItemStack,
        player: EntityPlayer,
        world: World,
        x: Int,
        y: Int,
        z: Int,
        side: Int,
        hitX: Float,
        hitY: Float,
        hitZ: Float
    ): Boolean {
        if (player !is EntityPlayerMP) return false
        var currentMode = getInteger(stack, "mode")
        if (player.isSneaking) {
            setInteger(stack, "mode", if (currentMode <= 2) ++currentMode else 0)
            player.send("Change Mode mode = $currentMode")
            return true
        }
        when (currentMode) {
            0 -> {
                impact.proxy.remove_hint_particle(world, startVec.x, startVec.y, startVec.z)
                startVec = Vector3i(x, y, z)
                impact.proxy.hint_particle(world, startVec.x, startVec.y, startVec.z, ImpactAPI.getBlockHint(), ImpactAPI.RED, 600)
            }
            1 -> {
                impact.proxy.remove_hint_particle(world, endVec.x, endVec.y, endVec.z)
                endVec = Vector3i(x, y, z)
                impact.proxy.hint_particle(world, endVec.x, endVec.y, endVec.z, ImpactAPI.getBlockHint(), ImpactAPI.BLUE, 600)
            }
            2 -> {
                impact.proxy.hint_particleMega(
                    world,
                    startVec.x, startVec.y, startVec.z,
                    endVec.x, endVec.y, endVec.z,
                    Color.CYAN, 600
                )
            }
            3 -> {
                val box = Box(startVec.x, startVec.y, startVec.z, endVec.x, endVec.y, endVec.z)

                val pseudoJavaCode = StructureUtility.getPseudoJavaCode(
                    world, ExtendedFacing.of(ForgeDirection.getOrientation(side)),
                    x, y, z,
                    0, 0, 0,
                    { te -> te.javaClass.canonicalName },
                    box.sizeX(), box.sizeY(), box.sizeZ(),
                    false
                )
                println(pseudoJavaCode)
            }
        }
        return true
    }

    override fun addInformation(s: ItemStack, p: EntityPlayer, list: MutableList<Any?>, d: Boolean) {
        val modeName = when (getInteger(s, "mode")) {
            0 -> "Set Start Position Block"
            1 -> "Set End Position Block"
            2 -> "Show Area"
            3 -> "Check Structure by click to Start Position"
            else -> ""
        }
        list.add(modeName)
    }
}