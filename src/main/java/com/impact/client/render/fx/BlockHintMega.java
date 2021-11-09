package com.impact.client.render.fx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Dyes;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static com.impact.loader.ItemRegistery.UtilBlock;

@SideOnly(Side.CLIENT)
public class BlockHintMega extends EntityFX {
	private final IIcon[] icons = new IIcon[6];
	private double xMax, yMax, zMax;
	private Color color;
	
	public BlockHintMega(World world, int x, int y, int z) {
		super(world, x, y, z);
		particleGravity = 0;
		prevPosX        = posX;
		prevPosY        = posY;
		prevPosZ        = posZ;
		noClip          = true;
		particleMaxAge  = 2;
		xMax            = posX;
		yMax            = posY;
		zMax            = posZ;
		color			= Color.WHITE;
		for (int i = 0; i < 6; i++) icons[i] = UtilBlock.getIcon(i, 2);
	}
	
	public void lines(AxisAlignedBB aabb) {
		GL11.glBegin(GL11.GL_LINES);
		
		/*-top-*/
		GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);//a - 1
		GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);//b - 2
		
		GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);//a - 2
		GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);//b - 3
		
		GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);//a - 3
		GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);//b - 4
		
		GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);//a - 4
		GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);//b - 1
		
		/*-bottom-*/
		GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);//a - 1
		GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);//b - 2
		
		GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);//a - 2
		GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);//b - 3
		
		GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);//a - 3
		GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);//b - 4
		
		GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);//a - 4
		GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);//b - 1
		
		/*-side-/-edge-*/
		GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);//a
		GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);//b
		
		GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);//a
		GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);//b
		
		GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);//a
		GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);//b
		
		GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);//a
		GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);//b
		
		GL11.glEnd();
	}
	
	public BlockHintMega setMax(int x, int y, int z) {
		xMax = x;
		yMax = y;
		zMax = z;
		return this;
	}
	
	public BlockHintMega setIcon(Block block, int meta) {
		for (int i = 0; i < 6; i++) icons[i] = block.getIcon(i, meta);
		return this;
	}
	
	public BlockHintMega setColor(Color color) {
		this.color = color;
		return this;
	}
	
	public BlockHintMega setAge(int age) {
		particleMaxAge = age;
		return this;
	}
	
	@Override
	public void renderParticle(Tessellator tes, float subTickTime, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
		
		float x1 = (float) (posX - EntityFX.interpPosX);
		float y1 = (float) (posY - EntityFX.interpPosY);
		float z1 = (float) (posZ - EntityFX.interpPosZ);
		float x2 = (float) (xMax + 1D - EntityFX.interpPosX);
		float y2 = (float) (yMax + 1D - EntityFX.interpPosY);
		float z2 = (float) (zMax + 1D - EntityFX.interpPosZ);
		
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2).expand(0.01D, 0.01D, 0.01D);
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		tes.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), 50);
		box(aabb, tes);
		Color box = new Color(255, 0, 0, 128);
		tes.setColorRGBA(box.getRed(), box.getGreen(), box.getBlue(), 128);
		lines(aabb);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}
	
	public void box(AxisAlignedBB aabb, Tessellator tessellator) {
		for (int i = 0; i < 6; i++) {
			if (icons[i] == null) {
				continue;
			}
			double u = icons[i].getMinU();
			double U = icons[i].getMaxU();
			double v = icons[i].getMinV();
			double V = icons[i].getMaxV();
			switch (i) {//{DOWN, UP, NORTH, SOUTH, WEST, EAST}
				case 0:
					//top
					tessellator.addVertexWithUV(aabb.maxX, aabb.maxY, aabb.minZ, u, V);
					tessellator.addVertexWithUV(aabb.minX, aabb.maxY, aabb.minZ, u, v);
					tessellator.addVertexWithUV(aabb.minX, aabb.maxY, aabb.maxZ, U, v);
					tessellator.addVertexWithUV(aabb.maxX, aabb.maxY, aabb.maxZ, U, V);
					break;
				case 1:
					//bottom
					tessellator.addVertexWithUV(aabb.maxX, aabb.minY, aabb.maxZ, u, v);
					tessellator.addVertexWithUV(aabb.minX, aabb.minY, aabb.maxZ, u, V);
					tessellator.addVertexWithUV(aabb.minX, aabb.minY, aabb.minZ, U, V);
					tessellator.addVertexWithUV(aabb.maxX, aabb.minY, aabb.minZ, U, v);
					break;
				case 2:
					//north
					tessellator.addVertexWithUV(aabb.maxX, aabb.minY, aabb.minZ, U, V);
					tessellator.addVertexWithUV(aabb.minX, aabb.minY, aabb.minZ, U, v);
					tessellator.addVertexWithUV(aabb.minX, aabb.maxY, aabb.minZ, u, v);
					tessellator.addVertexWithUV(aabb.maxX, aabb.maxY, aabb.minZ, u, V);
					break;
				case 3:
					//south
					tessellator.addVertexWithUV(aabb.maxX, aabb.maxY, aabb.maxZ, U, V);
					tessellator.addVertexWithUV(aabb.minX, aabb.maxY, aabb.maxZ, U, v);
					tessellator.addVertexWithUV(aabb.minX, aabb.minY, aabb.maxZ, u, v);
					tessellator.addVertexWithUV(aabb.maxX, aabb.minY, aabb.maxZ, u, V);
					break;
				case 4:
					//west
					tessellator.addVertexWithUV(aabb.minX, aabb.maxY, aabb.maxZ, U, V);
					tessellator.addVertexWithUV(aabb.minX, aabb.maxY, aabb.minZ, U, v);
					tessellator.addVertexWithUV(aabb.minX, aabb.minY, aabb.minZ, u, v);
					tessellator.addVertexWithUV(aabb.minX, aabb.minY, aabb.maxZ, u, V);
					break;
				case 5:
					//east
					tessellator.addVertexWithUV(aabb.maxX, aabb.minY, aabb.maxZ, U, V);
					tessellator.addVertexWithUV(aabb.maxX, aabb.minY, aabb.minZ, U, v);
					tessellator.addVertexWithUV(aabb.maxX, aabb.maxY, aabb.minZ, u, v);
					tessellator.addVertexWithUV(aabb.maxX, aabb.maxY, aabb.maxZ, u, V);
					break;
			}
		}
	}
	
	@Override
	public int getFXLayer() {
		return 1;
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 2;
	}
}