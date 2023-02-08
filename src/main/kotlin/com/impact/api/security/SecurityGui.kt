package com.impact.api.security

import com.impact.network.IPacketString
import com.impact.network.ToServer_String
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class SecurityGui(player: EntityPlayer) : GuiContainer(SecurityContainer(player)), IPacketString {

    private lateinit var gui: GuiTextField
    private lateinit var apply: GuiButton

    private var currentKey: String = ""

    override fun initGui() {
        super.initGui()
        buttonList.add(GuiButton(0, guiLeft + xSize / 2 - 120 / 2, guiTop + 75, 120, 20, "Accept").also { apply = it })
        gui = GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 120 / 2, guiTop + 20, 120, fontRendererObj.FONT_HEIGHT)
        gui.enableBackgroundDrawing = false
        gui.maxStringLength = 16
        gui.setTextColor(16777215)
        gui.visible = true
        gui.isFocused = true
        gui.text = ""
    }

    override fun drawGuiContainerBackgroundLayer(f: Float, x: Int, y: Int) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
        mc.textureManager.bindTexture(ResourceLocation("gregtech:textures/gui/Aerostat.png"))
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
        this.fontRendererObj.drawString("Set Security Key:", guiLeft + 28, guiTop + 8, 0)
        this.fontRendererObj.drawString("Current Security Key:", guiLeft + 28, guiTop + 40, 0)
        this.fontRendererObj.drawString(currentKey.ifEmpty { "NO KEY" }, guiLeft + 28, guiTop + 50, 0)
        try {
            apply.enabled = gui.text.isNotEmpty()
        } catch (e: NumberFormatException) {
            apply.enabled = false
        }
        gui.drawTextBox()
    }

    override fun keyTyped(character: Char, key: Int) {
        if (!checkHotbarKeys(key)) {
            if (key == 28) {
                actionPerformed(apply)
            }
            if (character == ' ' && this.gui.text.isEmpty()) {
                return
            }
            if (this.gui.textboxKeyTyped(character, key)) {
                val out = gui.text
                gui.text = out
            } else {
                super.keyTyped(character, key)
            }
        }
    }

    override fun actionPerformed(btn: GuiButton) {
        if (btn == apply) {
            ToServer_String(gui.text).sendToServer()
        }
    }

    override fun update(vararg str: String) {
        currentKey = str.firstOrNull() ?: ""
    }
}