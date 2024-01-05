package com.impact.mods.gregtech.items.tools.behaviour

import com.impact.addon.vw.VirtualWorldScan
import com.impact.util.Utilits
import gregtech.api.interfaces.IItemBehaviour
import gregtech.api.items.GT_MetaBase_Item
import gregtech.api.util.GT_Utility
import gregtech.common.items.behaviors.Behaviour_None
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumChatFormatting
import net.minecraft.world.World
import space.gtimpact.virtual_world.common.items.ScannerTool

class BehaviourOreProbe : Behaviour_None() {

    override fun onItemUseFirst(
        aItem: GT_MetaBase_Item, aStack: ItemStack, aPlayer: EntityPlayer,
        aWorld: World, aX: Int, aY: Int, aZ: Int, aSide: Int, hitX: Float,
        hitY: Float, hitZ: Float
    ): Boolean {
        if (aPlayer is EntityPlayerMP) {
            val ch = aWorld.getChunkFromBlockCoords(aX, aZ)

            val count = VirtualWorldScan.scanVeinOre(ch, 0, aPlayer)

            if (count != null) {
                GT_Utility.sendChatToPlayer(aPlayer, "Presumably there is Ore here: " + EnumChatFormatting.YELLOW + Utilits.translateGTItemStack(count.vein.ores.first().ore))
            } else {
                GT_Utility.sendChatToPlayer(aPlayer, "Not found Ores")
            }

            return true
        }
        return false
    }

    companion object {
        @JvmField
        val INSTANCE: IItemBehaviour<GT_MetaBase_Item> = BehaviourOreProbe()
    }
}