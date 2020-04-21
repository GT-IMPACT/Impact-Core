package com.impact.mods.modChest.BASE;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public abstract class Gui_BaseChest extends GuiContainer {

	public Gui_BaseChest(Container container) {
		super(container);
		xSize = getXSize();
		ySize = getYSize();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture( getPathTexture() );
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0.0, 0.0); // начальная позиция
		tessellator.addVertexWithUV(guiLeft, guiTop + getScale(), 0, 0.0, 1.0);
		tessellator.addVertexWithUV(guiLeft + getScale(), guiTop + getScale(), 0, 1.0, 1.0);
		tessellator.addVertexWithUV(guiLeft + getScale(), guiTop, 0, 1.0, 0.0);
		tessellator.draw();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		fontRendererObj.drawString(I18n.format(getTileEntity().getInventoryName()), getNamedPos(), 7, 0x404040);
	}

	@Nonnull
	public abstract TE_BaseChest getTileEntity();
	public abstract ResourceLocation getPathTexture();
	public abstract int getXSize();
	public abstract int getYSize();
	public abstract int getScale();
	public abstract int getNamedPos();
}