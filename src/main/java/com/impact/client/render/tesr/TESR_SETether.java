package com.impact.client.render.tesr;

import com.github.technus.tectech.thing.block.QuantumStuffBlock;
import com.impact.common.block.blocks.Block_QuantumStuff;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static com.impact.core.Refstrings.MODID;

@SideOnly(Side.CLIENT)
public class TESR_SETether extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation tetherBeamTexture = new ResourceLocation(MODID, "textures/blocks/FullWhite.png");
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
		Tessellator tessellator = Tessellator.instance;
		int posX = te.xCoord;
		int posY = te.yCoord;
		int posZ = te.zCoord;
		this.bindTexture(tetherBeamTexture);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		OpenGlHelper.glBlendFunc(770, 1, 1, 0);
		float f2 = (float) te.getWorldObj().getTotalWorldTime() + partialTicks;
		float f3 = -f2 * 0.2F - (float) MathHelper.floor_float(-f2 * 0.1F);
		byte b0 = 1;
		double d3 = (double) f2 * 0.025D * (1.0D - (double) (b0 & 1) * 2.5D);
		tessellator.startDrawingQuads();
		if (te.getWorldObj().getBlock(posX, posY + 2, posZ) == Block_QuantumStuff.INSTANCE) {
			tessellator.setColorRGBA(121, 0, 1, 32);
		} else {
			tessellator.setColorRGBA(255, 255, 255, 32);
		}
		long tick = System.currentTimeMillis() / 150;
		int itick = (int) (tick % 60);
		double d5 = (double) b0 * 0.03D * itick / 20;
		double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
		double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
		double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
		double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
		double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
		double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
		double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
		double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
		double d23;
		if (te.getWorldObj().getBlock(posX, posY + 2, posZ) == Block_QuantumStuff.INSTANCE
				|| te.getWorldObj().getBlock(posX, posY + 2, posZ) == QuantumStuffBlock.INSTANCE) {
			d23 = (-11.5F * 1);
		} else {
			d23 = 0;
		}
		double d25 = 0.0D;
		double d27 = 1.0D;
		double d28 = (-1.0F + f3);
		double d29 = (double) (256.0F * 1) * (0.5D / d5) + d28;
		tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d27, d29);
		tessellator.addVertexWithUV(x + d7, y, z + d9, d27, d28);
		tessellator.addVertexWithUV(x + d11, y, z + d13, d25, d28);
		tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d25, d29);
		tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d27, d29);
		tessellator.addVertexWithUV(x + d19, y, z + d21, d27, d28);
		tessellator.addVertexWithUV(x + d15, y, z + d17, d25, d28);
		tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d25, d29);
		tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d27, d29);
		tessellator.addVertexWithUV(x + d11, y, z + d13, d27, d28);
		tessellator.addVertexWithUV(x + d19, y, z + d21, d25, d28);
		tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d25, d29);
		tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d27, d29);
		tessellator.addVertexWithUV(x + d15, y, z + d17, d27, d28);
		tessellator.addVertexWithUV(x + d7, y, z + d9, d25, d28);
		tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d25, d29);
		tessellator.draw();
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDepthMask(false);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA(255, 255, 255, 0);
		double d30 = 0.2D;
		double d4 = 0.2D;
		double d6 = 0.8D;
		double d8 = 0.2D;
		double d10 = 0.2D;
		double d12 = 0.8D;
		double d14 = 0.8D;
		double d16 = 0.8D;
		double d18 = (-11.0F * 1);
		double d20 = 0.0D;
		double d22 = 1.0D;
		double d24 = -1.0F + f3;
		double d26 = (double) (256.0F * 1) + d24;
		tessellator.addVertexWithUV(x + d30, y + d18, z + d4, d22, d26);
		tessellator.addVertexWithUV(x + d30, y, z + d4, d22, d24);
		tessellator.addVertexWithUV(x + d6, y, z + d8, d20, d24);
		tessellator.addVertexWithUV(x + d6, y + d18, z + d8, d20, d26);
		tessellator.addVertexWithUV(x + d14, y + d18, z + d16, d22, d26);
		tessellator.addVertexWithUV(x + d14, y, z + d16, d22, d24);
		tessellator.addVertexWithUV(x + d10, y, z + d12, d20, d24);
		tessellator.addVertexWithUV(x + d10, y + d18, z + d12, d20, d26);
		tessellator.addVertexWithUV(x + d6, y + d18, z + d8, d22, d26);
		tessellator.addVertexWithUV(x + d6, y, z + d8, d22, d24);
		tessellator.addVertexWithUV(x + d14, y, z + d16, d20, d24);
		tessellator.addVertexWithUV(x + d14, y + d18, z + d16, d20, d26);
		tessellator.addVertexWithUV(x + d10, y + d18, z + d12, d22, d26);
		tessellator.addVertexWithUV(x + d10, y, z + d12, d22, d24);
		tessellator.addVertexWithUV(x + d30, y, z + d4, d20, d24);
		tessellator.addVertexWithUV(x + d30, y + d18, z + d4, d20, d26);
		tessellator.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
	}
}