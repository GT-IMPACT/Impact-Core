package com.impact.client.gui.button;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ImpactGuiButton extends GuiButton {
	
	protected static final ResourceLocation buttonTextures = new ResourceLocation("impact:textures/gui/widgets/widgets.png");
	
	public ImpactGuiButton(int id, int x, int y, String title) {
		this(id, x, y, 200, 20, title);
	}
	
	public ImpactGuiButton(int id, int x, int y, int width, int height, String title) {
		super(id, x, y, width, height, title);
	}
	
	public void drawButton(Minecraft mc, int x, int y) {
		if (this.visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(buttonTextures);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
			int k = this.getHoverState(this.field_146123_n);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(770, 771);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
			this.mouseDragged(mc, x, y);
			int l = 14737632;
			if (this.packedFGColour != 0) {
				l = this.packedFGColour;
			} else if (!this.enabled) {
				l = 10526880;
			} else if (this.field_146123_n) {
				l = 0xFFF5911E;
			}
			this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
		}
	}
	
	protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
	
	}
	
	public void mouseReleased(int p_146118_1_, int p_146118_2_) {
	}
	
	public boolean mousePressed(Minecraft mc, int x, int y) {
		return this.enabled && this.visible && x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
	}
	
	public boolean func_146115_a() {
		return this.field_146123_n;
	}
	
	public void func_146111_b(int p_146111_1_, int p_146111_2_) {
	}
	
	public void func_146113_a(SoundHandler sound) {
		sound.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	}
	
	public int getButtonWidth() {
		return this.width;
	}
	
	public int func_154310_c() {
		return this.height;
	}
}