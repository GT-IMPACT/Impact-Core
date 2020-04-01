package com.impact.item.Circuit_Programmer;

import com.impact.System.Refstrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


public class GT_GUIContainer_CircuitProgrammer extends GuiContainer {

    public static final ResourceLocation texture = new ResourceLocation(Refstrings.MODID, "textures/gui/GUI_Circuit.png");

    public GT_GUIContainer_CircuitProgrammer(final InventoryPlayer inventoryPlayer) {
        super(new GT_Container_CircuitProgrammer(inventoryPlayer));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString("Circuit Programmer", 10, 8, 0x000);

        for (int i = 1; i < 9; i++) {
            fontRendererObj.drawString(Integer.toString(i), 6+i*18, 26, 0x000);
        }
            fontRendererObj.drawString("9", 6+18, 26+18, 0x000);

        for (int i = 1; i < 8; i++) {
            fontRendererObj.drawString(Integer.toString(i+9), 3+18+i*18, 26+18, 0x000);
        }
        for (int i = 1; i < 9; i++) {
            fontRendererObj.drawString(Integer.toString(i+16), 3+i*18, 26+18+18, 0x000);
        }
    }

    @Override

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(GT_GUIContainer_CircuitProgrammer.texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 177, 183);
    }
}