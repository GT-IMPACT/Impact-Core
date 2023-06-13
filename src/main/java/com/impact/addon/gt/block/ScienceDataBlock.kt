package com.impact.addon.gt.block

import com.impact.common.block.blocks.GTUpdateBlockAPI
import com.impact.mods.gregtech.blocks.itemblocks.IB_Base
import com.impact.mods.gregtech.enums.Texture
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon

class ScienceDataBlock : GTUpdateBlockAPI(Material.iron) {

    companion object {
        val INSTANCE = ScienceDataBlock()
        const val OBSERVATORY_PROCESSOR = 0
    }

    fun registerBlock(): Block {
        val blockName = "impact_science_block"
        INSTANCE.setBlockName(blockName)
        INSTANCE.setHardness(5.0f)
        INSTANCE.setResistance(6.0f)
        GameRegistry.registerBlock(INSTANCE, ScienceDataBlockItem::class.java, blockName)
        return INSTANCE
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, l: MutableList<Any?>) {
        l.add(ItemStack(item, 1, OBSERVATORY_PROCESSOR))
    }

    override fun getIcon(side: Int, meta: Int): IIcon? {
        return when(meta) {
            OBSERVATORY_PROCESSOR -> Texture.Icons.OBSERVATORY_PROCESSOR.icon
            else -> null
        }
    }
}

class ScienceDataBlockItem(block: Block?) : IB_Base(block) {
    override fun getMetadata(meta: Int) = meta
    override fun getHasSubtypes() = true
    override fun getUnlocalizedName(stack: ItemStack) = super.getUnlocalizedName() + "." + stack.itemDamage
    override fun addInformation(
        stack: ItemStack, player: EntityPlayer, lines: MutableList<Any?>,
        advancedTooltips: Boolean
    ) {
        lines.add(mNoMobsToolTip)
        lines.add(mNoTileEntityToolTip)
    }
}
