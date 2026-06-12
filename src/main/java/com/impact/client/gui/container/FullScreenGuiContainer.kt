package com.impact.client.gui.container

import codechicken.nei.VisiblityData
import codechicken.nei.api.INEIGuiHandler
import codechicken.nei.api.TaggedInventoryArea
import cpw.mods.fml.common.Optional
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack

@SideOnly(Side.CLIENT)
@Optional.Interface(iface = "codechicken.nei.api.INEIGuiHandler", modid = "NotEnoughItems")
abstract class FullScreenGuiContainer(container: Container) : GuiContainer(container), INEIGuiHandler {

    override fun initGui() {

        xSize = width
        ySize = height

        super.initGui()

        guiLeft = 0
        guiTop = 0
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        // :P
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun modifyVisiblity(gui: GuiContainer, currentVisibility: VisiblityData): VisiblityData {

        currentVisibility.showNEI = false

        return currentVisibility
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun getItemSpawnSlots(gui: GuiContainer, item: ItemStack?): Iterable<Int> {
        return emptySet()
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun getInventoryAreas(gui: GuiContainer): List<TaggedInventoryArea> {
        return emptyList()
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun handleDragNDrop(gui: GuiContainer, mousex: Int, mousey: Int, draggedStack: ItemStack?, button: Int): Boolean {
        return false
    }

    @Optional.Method(modid = "NotEnoughItems")
    override fun hideItemPanelSlot(gui: GuiContainer, x: Int, y: Int, w: Int, h: Int): Boolean {
        return false
    }
}
