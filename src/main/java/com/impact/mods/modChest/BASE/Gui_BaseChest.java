package com.impact.mods.modChest.BASE;

import com.impact.System.Refstrings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Gui_BaseChest extends GuiContainer {

    private static int mScale;
    private static int mNamedPos;
    public static TE_BaseChest TE;
    private static int IDgui;


    public void RegisterGui(
    int aIDgui, TE_BaseChest aTE, int aGuiXSize, int aGuiYSize, int aGuiScale, int aGuiNamedPos) {
        xSize = aGuiXSize;
        ySize = aGuiYSize;
        IDgui = aIDgui;
        TE = aTE;
        mScale = aGuiScale;
        mNamedPos = aGuiNamedPos;
    }

    public Gui_BaseChest(TE_BaseChest aTE, InventoryPlayer aIP) {
        super(new Container_BaseChest(TE, aIP));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(new ResourceLocation(Refstrings.MODID, "textures/gui/chest" + IDgui + ".png"));
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0.0, 0.0); // начальная позиция
        tessellator.addVertexWithUV(guiLeft, guiTop + mScale, 0, 0.0, 1.0);
        tessellator.addVertexWithUV(guiLeft + mScale, guiTop + mScale, 0, 1.0, 1.0);
        tessellator.addVertexWithUV(guiLeft + mScale, guiTop, 0, 1.0, 0.0);
        tessellator.draw();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        fontRendererObj.drawString(I18n.format(TE.getInventoryName()), mNamedPos, 7, 0x404040);
    }
}