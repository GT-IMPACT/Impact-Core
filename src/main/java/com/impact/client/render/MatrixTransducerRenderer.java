package com.impact.client.render;

import com.impact.client.render.models.MatrixTransducerMesh;
import com.impact.client.render.models.MatrixTransducerMesh.Face;
import com.impact.common.block.blocks.Block_MatrixTransducer;
import com.impact.common.block.blocks.Block_UtilBlock;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public final class MatrixTransducerRenderer implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_CURRENT_BIT | GL11.GL_TEXTURE_BIT);
        GL11.glPushMatrix();
        try {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            Tessellator.instance.startDrawingQuads();
            render(0, 0, 0, Block_UtilBlock.UtilBlock.getIcon(0, 2), false);
            Tessellator.instance.draw();
        } finally {
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        boolean override = renderer.hasOverrideBlockTexture();
        IIcon icon = override ? renderer.overrideBlockTexture : Block_UtilBlock.UtilBlock.getIcon(0, 2);
        render(x, y, z, icon, override);
        return true;
    }

    private static void render(double x, double y, double z, IIcon icon, boolean override) {
        Tessellator tessellator = Tessellator.instance;
        for (Face face : MatrixTransducerMesh.getFaces()) {
            tessellator.setNormal(face.axis == 0 ? face.sign : 0,
                    face.axis == 1 ? face.sign : 0, face.axis == 2 ? face.sign : 0);
            int color = override ? 0xFFFFFF : face.getColor();
            double inset = override ? 0 : face.getInset();
            tessellator.setColorOpaque_I(color);
            for (int corner = 0; corner < 4; corner++) {
                vertex(tessellator, face, corner, inset, x, y, z, icon);
            }
            if (!override) {
                int red = (color >> 16 & 255);
                int green = (color >> 8 & 255);
                int blue = (color & 255);
                tessellator.setColorOpaque(red + (255 - red) / 3, green + (255 - green) / 3,
                        blue + (255 - blue) / 3);
                for (int edge = 0; edge < 4; edge++) {
                    int next = (edge + 1) % 4;
                    vertex(tessellator, face, edge, 0, x, y, z, icon);
                    vertex(tessellator, face, next, 0, x, y, z, icon);
                    vertex(tessellator, face, next, inset, x, y, z, icon);
                    vertex(tessellator, face, edge, inset, x, y, z, icon);
                }
            }
        }
    }

    private static void vertex(Tessellator tessellator, Face face, int corner, double inset,
            double x, double y, double z, IIcon icon) {
        double u = corner == 1 || corner == 2 ? icon.getMaxU() : icon.getMinU();
        double v = corner >= 2 ? icon.getMaxV() : icon.getMinV();
        tessellator.addVertexWithUV(x + face.coordinate(corner, 0, inset),
                y + face.coordinate(corner, 1, inset), z + face.coordinate(corner, 2, inset), u, v);
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Block_MatrixTransducer.renderID;
    }
}
