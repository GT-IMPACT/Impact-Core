package com.impact.mods.railcraft.carts.item.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiWIronChestCart extends GuiContainer {
	
	private static final ResourceLocation ChestTexture = new ResourceLocation(
			"chestup",
			"textures/gui/wriron.png"
	);
	
	public GuiWIronChestCart(IInventory invPlayer, IInventory cart) {
		super(new ContainerWIronChestCart(invPlayer, cart));
		this.ySize = 190;
		this.xSize = 177;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(ChestTexture);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(this.guiLeft, this.guiTop, 0.0D, 0.0D, 0.0D);
		tessellator.addVertexWithUV(this.guiLeft, this.guiTop + 500, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(this.guiLeft + 500, this.guiTop + 500, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(this.guiLeft + 500, this.guiTop, 0.0D, 1.0D, 0.0D);
		tessellator.draw();
	}
}