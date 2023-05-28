package com.impact.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.impact.client.gui.button.ImpactGuiButton;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.impact.impact.ModPackVersion;
import static net.minecraft.util.StatCollector.translateToLocal;

@SideOnly(Side.CLIENT)
public class ImpactGuiMainMenu extends GuiScreen {
	
	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("impact", "textures/gui/title/title.png");
	private static final ResourceLocation background = new ResourceLocation("impact", "textures/gui/bg/bg.jpeg");
	
	public ImpactGuiMainMenu() {
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() {
		int offsetX = 100;
		int length = 160;
		int height = 20;
		int yButtons = this.height / 4 + 20;
		
		this.buttonList.add(new ImpactGuiButton(1, offsetX, yButtons + 84 - 24 - 30, length, height, I18n.format("menu.singleplayer")));
		this.buttonList.add(new ImpactGuiButton(2, offsetX, yButtons + 84 - 2 - 30, length, height, I18n.format("menu.multiplayer")));
		this.buttonList.add(new ImpactGuiButton(0, offsetX, yButtons + 84 + 20 - 30, length, height, I18n.format(translateToLocal("menu.options"))));
		this.buttonList.add(new ImpactGuiButton(4, offsetX, yButtons + 84 + 95 - 52, length, height, I18n.format("menu.quit")));
		
		int offsetRow = 30;
		this.buttonList.add(new ImpactGuiButton(20, offsetX + offsetRow - 30, yButtons + 84 + 42 - 25, 50, 20, I18n.format(translateToLocal("menu.website"))));
		this.buttonList.add(new ImpactGuiButton(21, offsetX + offsetRow + 25, yButtons + 84 + 42 - 25, 50, 20, I18n.format(translateToLocal("menu.discord"))));
		this.buttonList.add(new ImpactGuiButton(22, offsetX + offsetRow + 80, yButtons + 84 + 42 - 25, 50, 20, I18n.format(translateToLocal("menu.github"))));
	}
	
	protected void actionPerformed(GuiButton b) {
		if (b.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}
		if (b.id == 1) {
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}
		if (b.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		if (b.id == 4) {
			this.mc.shutdown();
		}
		if (b.id == 5) {
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}
		if (b.id == 6) {
			this.mc.displayGuiScreen(new GuiModList(this));
		}
		if (b.id == 20) {
			urlopen("https://gt-impact.github.io/");
		}
		if (b.id == 21) {
			urlopen("https://discord.gg/bMf2qvd");
		}
		if (b.id == 22) {
			urlopen("https://github.com/GT-IMPACT/IMPACT-MODPACK/issues/new/choose");
		}
	}
	
	private void urlopen(String url) {
		try {
			Class<?> oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
			oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new URI(url));
		} catch (Throwable throwable) {
			logger.error("Couldn't open link", throwable);
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GL11.glEnable(3008);

		this.mc.renderEngine.bindTexture(background);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, (this.height), this.zLevel, 0.0D, 1.0D);
		tessellator.addVertexWithUV((this.width), (this.height), this.zLevel, 1.0D, 1.0D);
		tessellator.addVertexWithUV((this.width), 0.0D, this.zLevel, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, this.zLevel, 0.0D, 0.0D);
		tessellator.draw();
		
		this.drawGradientRect(0, 0, this.width, this.height, 0xFF000000, 0);
		
		this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(50, -50, 0, 0, 250, 250);
		
		tessellator.setColorOpaque_I(-1);
		GL11.glPushMatrix();
		GL11.glTranslatef(50, 70.0F, 0.0F);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		
		float f1 = 1.8F - MathHelper.abs(MathHelper.sin((float) (Minecraft.getSystemTime() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
		GL11.glScalef(f1, f1, f1);
		GL11.glPopMatrix();
		
		List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
		for (int i = 0; i < brandings.size(); i++) {
			String brd = brandings.get(i);
			if (!Strings.isNullOrEmpty(brd)) {
				drawString(this.fontRendererObj, brd, 2, this.height - 10 + i * (this.fontRendererObj.FONT_HEIGHT + 1), 16777215);
			}
		}
		String eBug2 = translateToLocal("current_version") + ": " + EnumChatFormatting.YELLOW + ModPackVersion;
		drawString(this.fontRendererObj, eBug2, this.width - this.fontRendererObj.getStringWidth(eBug2) - 10, this.height - 10, -1);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}