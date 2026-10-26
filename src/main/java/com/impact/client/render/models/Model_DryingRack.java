package com.impact.client.render.models;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class Model_DryingRack implements ISimpleBlockRenderingHandler {
	public static int model = RenderingRegistry.getNextAvailableRenderId();
	
	public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, int meta) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glPushMatrix();
		try {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconSafe(block.getIcon(0, meta)));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconSafe(block.getIcon(1, meta)));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1F);
			renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconSafe(block.getIcon(2, meta)));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconSafe(block.getIcon(3, meta)));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1F, 0.0F, 0.0F);
			renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconSafe(block.getIcon(4, meta)));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconSafe(block.getIcon(5, meta)));
			tessellator.draw();
		} finally {
			GL11.glPopMatrix();
		}
	}
	
	public static IIcon getIconSafe(IIcon icon) {
		if (icon != null)
			return icon;
		return ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if (modelID != model) {
			return;
		}
		double minX = renderer.renderMinX;
		double minY = renderer.renderMinY;
		double minZ = renderer.renderMinZ;
		double maxX = renderer.renderMaxX;
		double maxY = renderer.renderMaxY;
		double maxZ = renderer.renderMaxZ;
		try {
			renderer.setRenderBounds(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);
			renderStandardInvBlock(renderer, block, metadata);
		} finally {
			renderer.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		if (modelID != model) {
			return false;
		}
		block.setBlockBoundsBasedOnState(world, x, y, z);
		renderer.setRenderBoundsFromBlock(block);
		return renderer.renderStandardBlock(block, x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return true;
	}
	
	@Override
	public int getRenderId() {
		return model;
	}
}
