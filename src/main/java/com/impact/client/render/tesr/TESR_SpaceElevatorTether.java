package com.impact.client.render.tesr;

import com.impact.common.block.blocks.Block_SpaceElevatorTether;
import com.impact.common.te.TE_SpaceElevatorTether;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TESR_SpaceElevatorTether extends TileEntitySpecialRenderer {

    private static final double HALF_WIDTH = 1.7D / Math.sqrt(2.0D);
    private static final double TEXTURE_REPEAT_HEIGHT = 3.4D;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
        if (!(tile instanceof TE_SpaceElevatorTether)) {
            return;
        }
        int height = ((TE_SpaceElevatorTether) tile).getBeamHeight();
        if (height == 0) {
            return;
        }
        IIcon icon = Block_SpaceElevatorTether.getBeamIcon();
        if (icon == null) {
            return;
        }

        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT
                | GL11.GL_LIGHTING_BIT | GL11.GL_CURRENT_BIT | GL11.GL_TEXTURE_BIT);
        try {
            bindTexture(TextureMap.locationBlocksTexture);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);

            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setBrightness(15728880);
            int color = height > 0 ? 255 : 46;
            tessellator.setColorOpaque(color, color, color);
            double minX = x + 0.5D - HALF_WIDTH;
            double maxX = x + 0.5D + HALF_WIDTH;
            double minZ = z + 0.5D - HALF_WIDTH;
            double maxZ = z + 0.5D + HALF_WIDTH;
            int direction = Integer.signum(height);
            double length = Math.abs(height);

            // Repeat inside the sprite's UV bounds without wrapping into other atlas textures.
            for (double offset = 0; offset < length; offset += TEXTURE_REPEAT_HEIGHT) {
                double segment = Math.min(TEXTURE_REPEAT_HEIGHT, length - offset);
                double startY = y + direction * offset;
                double endY = y + direction * (offset + segment);
                double endV = icon.getMinV() + (icon.getMaxV() - icon.getMinV())
                        * segment / TEXTURE_REPEAT_HEIGHT;
                drawSide(tessellator, icon, minX, minZ, maxX, minZ, startY, endY, endV);
                drawSide(tessellator, icon, maxX, minZ, maxX, maxZ, startY, endY, endV);
                drawSide(tessellator, icon, maxX, maxZ, minX, maxZ, startY, endY, endV);
                drawSide(tessellator, icon, minX, maxZ, minX, minZ, startY, endY, endV);
            }
            tessellator.draw();
        } finally {
            GL11.glPopAttrib();
        }
    }

    private static void drawSide(Tessellator tessellator, IIcon icon, double x1, double z1, double x2, double z2,
            double startY, double endY, double endV) {
        tessellator.addVertexWithUV(x1, startY, z1, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(x2, startY, z2, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(x2, endY, z2, icon.getMaxU(), endV);
        tessellator.addVertexWithUV(x1, endY, z1, icon.getMinU(), endV);
    }
}
