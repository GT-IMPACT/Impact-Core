package com.impact.client.render.tesr;

import com.impact.client.render.models.Model_TheMill;
import com.impact.common.te.TE_TheMill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TESR_TheMill extends TileEntitySpecialRenderer {
	
	static Model_TheMill model = new Model_TheMill();
	
	public TESR_TheMill() {
	}
	
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		TE_TheMill mill = (TE_TheMill) tile;
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		GL11.glRotated(mill.getFacing() == 2 ? 0.0D : (mill.getFacing() == 3 ? 180.0D : (mill.getFacing() == 4 ? 90.0D : -90.0D)), 0.0D, 1.0D, 0.0D);
		float rot = 360.0F * (mill.rotation + (mill.canTurn && mill.rotation != 0.0F ? f * mill.prevRotation : 0.0F));
		model.setRotateAngle(model.axel, 0.0F, 0.0F, -((float)Math.toRadians(rot)));
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("impact","textures/models/windmill.png"));
		model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}