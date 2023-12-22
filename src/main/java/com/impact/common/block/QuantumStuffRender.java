package com.impact.common.block;

import com.impact.common.block.blocks.Block_QuantumStuff_Red;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

import static com.impact.impact.RANDOM;

public class QuantumStuffRender implements ISimpleBlockRenderingHandler {
	
	private static final Tessellator tes = Tessellator.instance;
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		GL11.glPushMatrix();
		//Get icons from custom register (useful for renderers and fluids)
		IIcon side = Block_QuantumStuff_Red.stuff;
		tes.startDrawingQuads();
		tes.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, side);
		tes.draw();
		tes.startDrawingQuads();
		tes.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, side);
		tes.draw();
		tes.startDrawingQuads();
		tes.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, side);
		tes.draw();
		tes.startDrawingQuads();
		tes.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, side);
		tes.draw();
		tes.startDrawingQuads();
		tes.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, side);
		tes.draw();
		tes.startDrawingQuads();
		tes.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, side);
		tes.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
									RenderBlocks renderer) {
		GL11.glPushMatrix();
		tes.setNormal(0F, 1F, 0F);
		tes.setBrightness(15728880);
		IIcon side = Block_QuantumStuff_Red.stuff;
		for (int i = 0; i < 6; i++) {
			float f = RANDOM.nextFloat(), g = f - RANDOM.nextFloat() / 2f, r = g - RANDOM.nextFloat() / 3f - 0.25f;
			tes.setColorOpaque_F(RANDOM.nextFloat(), 0, 0);
			float rotX = RANDOM.nextFloat() * 2 * (float) Math.PI, rotY = RANDOM.nextFloat() * 2 * (float) Math.PI, rotZ = RANDOM.nextFloat() * 2 * (float) Math.PI;
			tesAbuse(x, y, z, -1.425f, -1.425f, .1f, rotX, rotY, rotZ, side.getMinU(), side.getMaxV());
			tesAbuse(x, y, z, -1.425f, 1.425f, .1f, rotX, rotY, rotZ, side.getMinU(), side.getMinV());
			tesAbuse(x, y, z, 1.425f, 1.425f, .1f, rotX, rotY, rotZ, side.getMaxU(), side.getMinV());
			tesAbuse(x, y, z, 1.425f, -1.425f, .1f, rotX, rotY, rotZ, side.getMaxU(), side.getMaxV());
			
			tesAbuse(x, y, z, 1.425f, -1.425f, -.1f, rotX, rotY, rotZ, side.getMaxU(), side.getMaxV());
			tesAbuse(x, y, z, 1.425f, 1.425f, -.1f, rotX, rotY, rotZ, side.getMaxU(), side.getMinV());
			tesAbuse(x, y, z, -1.425f, 1.425f, -.1f, rotX, rotY, rotZ, side.getMinU(), side.getMinV());
			tesAbuse(x, y, z, -1.425f, -1.425f, -.1f, rotX, rotY, rotZ, side.getMinU(), side.getMaxV());
		}
		GL11.glPopMatrix();
		return true;
	}
	
	private void tesAbuse(int x, int y, int z, float sx, float sy, float sz, float rotX, float rotY,
						  float rotZ, float sideU, float sideV) {
		Vec3 pos = Vec3.createVectorHelper(sx, sy, sz);
		pos.rotateAroundX(rotX);
		pos.rotateAroundY(rotY);
		pos.rotateAroundZ(rotZ);
		tes.addVertexWithUV(pos.xCoord + x + .5f, pos.yCoord + y + .5f, pos.zCoord + z + .5f, sideU,
				sideV
		);
	}
	
	
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
	
	@Override
	public int getRenderId() {
		return Block_QuantumStuff_Red.renderID;
	}
}
