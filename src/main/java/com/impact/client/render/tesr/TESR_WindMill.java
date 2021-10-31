package com.impact.client.render.tesr;

import com.impact.client.render.models.Model_Rotor;
import com.impact.common.te.TE_WindMill;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class TESR_WindMill extends TileEntitySpecialRenderer {
	
	private Model_Rotor model;
	
	public void renderBlockRotor(TE_WindMill tileEntity, World world, int posX, int posY, int posZ) {
		if (tileEntity == null) return;
		if (!tileEntity.hasRotorHidden()) {
			model = new Model_Rotor(12 * 3 * 2);
			Tessellator tessellator = Tessellator.instance;
			float brightness = world.getBlockLightValue(posX, posY, posZ);
			int skyBrightness = world.getLightBrightnessForSkyBlocks(posX, posY + 2, posZ, 0);
			int skyBrightness1 = skyBrightness % 65536;
			int skyBrightness2 = skyBrightness / 65536;
			tessellator.setColorOpaque_F(brightness, brightness, brightness);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, skyBrightness1, skyBrightness2);
			short facing = tileEntity.getFacing();
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			
			if (facing == 2 || facing == 3 || facing == 4 || facing == 5) {
				int dir = facing == 4 ? 0 : facing == 2 ? 1 : facing == 5 ? 2 : facing;
				GL11.glRotatef(dir * -90F, 0F, 1F, 0F);
			} else if (facing == 1) {
				GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
			}
			int speed = tileEntity.getSpeed();
			boolean stop = speed == 0;
			speed = speed < 4 ? 4 : Math.max(10, speed);
			if (!stop) {
				GL11.glRotatef(360 - System.currentTimeMillis() / speed % 360, 1.0F, 0.0F, 0.0F);
			}
			GL11.glTranslatef(-0.25F, 0.0F, 0.0F);
			bindTexture(new ResourceLocation(model.getRenderTexture()));
			Color color = new Color(tileEntity.getRGB(), false);
			GL11.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
			model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) posX, (float) posY, (float) posZ);
		TE_WindMill tileEntityWindmill = (TE_WindMill) tileEntity;
		renderBlockRotor(tileEntityWindmill, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
		GL11.glPopMatrix();
	}
}