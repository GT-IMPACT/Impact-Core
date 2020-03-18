package com.gwppcore.modChest;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public abstract class Gui_BaseChest extends GuiContainer
{

	public Gui_BaseChest(@Nonnull final Container container)
	{
		super(container);
		xSize = getXSize();
		ySize = getYSize();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture( getPathTexture() );
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0.0, 0.0); // начальная позиция
		tessellator.addVertexWithUV(guiLeft, guiTop + xSize*3-13, 0, 0.0, 1.0);
		tessellator.addVertexWithUV(guiLeft + xSize*3-13, guiTop + xSize*3-13, 0, 1.0, 1.0);
		tessellator.addVertexWithUV(guiLeft + xSize*3-1, guiTop, 0, 1.0, 0.0);
		tessellator.draw();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int p_146979_1_, final int p_146979_2_)
	{
		fontRendererObj.drawString(I18n.format(getTileEntity().getInventoryName()), getNamedPos(), 7, 0x404040);
	}

	@Nonnull
	protected abstract TE_BaseChest getTileEntity();
	protected abstract ResourceLocation getPathTexture();
	protected abstract int getXSize();
	protected abstract int getYSize();
	protected abstract int getNamedPos();
}