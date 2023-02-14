package com.impact.client.render.models;

import gregtech.api.util.GT_Utility;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class DonateCapeRenderer extends RenderPlayer {
	
	private static final ResourceLocation DONATE_CAPE = new ResourceLocation("impact:textures/DonateCape.png");
	
	private static final List<String> donateList = new ArrayList<>();
	
	static {
		donateList.add("4gname");
		donateList.add("Developer");
	}

	public DonateCapeRenderer() {
		setRenderManager(RenderManager.instance);
	}
	
	public void render(RenderPlayerEvent.Specials.Pre event) {
		AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
		
		float partialTicks = event.partialRenderTick;
		if (player.isInvisible()) return;
		
		if (GT_Utility.getPotion(player, Potion.invisibility.id)) return;
		
		try {
			ResourceLocation cape = null;
			
			for (String name : donateList) {
				if (player.getDisplayName().equalsIgnoreCase(name)) {
					cape = DONATE_CAPE;
					break;
				}	
			}
			
			if (cape != null && !player.getHideCape()) {
				bindTexture(cape);
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, 0.0F, 0.125F);
				double d0 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * partialTicks);
				double d1 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * partialTicks);
				double d2 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks);
				float f6 = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
				double d3 = MathHelper.sin(f6 * 3.141593F / 180.0F);
				double d4 = -MathHelper.cos(f6 * 3.141593F / 180.0F);
				float f7 = (float) d1 * 10.0F;
				float f8 = (float) (d0 * d3 + d2 * d4) * 100.0F;
				float f9 = (float) (d0 * d4 - d2 * d3) * 100.0F;
				if (f7 < -6.0F) {
					f7 = -6.0F;
				}
				if (f7 > 32.0F) {
					f7 = 32.0F;
				}
				if (f8 < 0.0F) {
					f8 = 0.0F;
				}
				float f10 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
				f7 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f10;
				if (player.isSneaking()) {
					f7 += 25.0F;
				}
				GL11.glRotatef(6.0F + f8 / 2.0F + f7, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(f9 / 2.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-f9 / 2.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				((ModelBiped) this.mainModel).renderCloak(0.0625F);
				GL11.glPopMatrix();
			}
			
		} catch (Exception ignored) {}
	}
}
