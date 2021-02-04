package com.impact.client.gui;

import static com.impact.impact.ModPackVersion;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.impact.client.gui.button.ImpactGuiButton;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ImpactGuiMainMenu extends GuiScreen implements GuiYesNoCallback {

  private static final Logger logger = LogManager.getLogger();
  private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("impact",
      "textures/gui/title/title.png");

  private static final ResourceLocation[] background = new ResourceLocation[]{
      new ResourceLocation("impact:textures/gui/bg/bg1.png"),
      new ResourceLocation("impact:textures/gui/bg/bg2.png"),
      new ResourceLocation("impact:textures/gui/bg/bg3.png"),
      new ResourceLocation("impact:textures/gui/bg/bg4.png"),
      new ResourceLocation("impact:textures/gui/bg/bg5.png")
  };

  public ImpactGuiMainMenu() {
  }

  public boolean doesGuiPauseGame() {
    return false;
  }

  @SuppressWarnings("unchecked")
  public void initGui() {
    int yButtons = this.height / 4 + 20;
    int xButtons = this.width / 2 - 50;
    int pOptions = (this.height > 300 && this.width > 300) ? 250 : 120;

    this.buttonList.add(new ImpactGuiButton(1, xButtons - 30, yButtons + 84 - 24 - 30, 160, 20,
        I18n.format("menu.singleplayer")));
    this.buttonList.add(new ImpactGuiButton(2, xButtons - 30, yButtons + 84 - 2 - 30, 160, 20,
        I18n.format("menu.multiplayer")));
    //this.buttonList.add(new ImpactGuiButton(6, xButtons-30, yButtons + 84 + 20 - 30, 160, 20, I18n.format("Mods")));
    this.buttonList.add(new ImpactGuiButton(0, xButtons - 30, yButtons + 84 + 20 - 30, 160, 20,
        I18n.format("menu.options")));
    this.buttonList.add(new ImpactGuiButton(4, xButtons - 30, yButtons + 84 + 95 - 52, 160, 20,
        I18n.format("menu.quit")));

    this.buttonList.add(new ImpactGuiButton(20, xButtons - 25 - 5, yButtons + 84 + 42 - 25, 50, 20,
        I18n.format("Website")));
    this.buttonList.add(new ImpactGuiButton(21, xButtons + 25, yButtons + 84 + 42 - 25, 50, 20,
        I18n.format("Discord")));
    this.buttonList.add(new ImpactGuiButton(22, xButtons + 75 + 5, yButtons + 84 + 42 - 25, 50, 20,
        I18n.format("GitHub")));
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
    if (b.id == 3) {
      urlopen(
          "https://forum.micdoodle8.com/index.php?threads/1-7-10-galaxy-space-stable.5298/");
    }
    if (b.id == 4) {
      this.mc.shutdown();
    }
    if (b.id == 5) {
      this.mc.displayGuiScreen(
          new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
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

    long tick = System.currentTimeMillis() / 10000;
    this.mc.renderEngine.bindTexture(background[(int) (tick % 5)]);

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

    drawTexturedModalRect(this.width / 2 - 50, 30, 0, 0, 205, 205);

    tessellator.setColorOpaque_I(-1);
    GL11.glPushMatrix();
    GL11.glTranslatef((this.width / 2F + 90F), 70.0F, 0.0F);
    GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);

    float f1 = 1.8F - MathHelper.abs(
        MathHelper.sin((float) (Minecraft.getSystemTime() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
    GL11.glScalef(f1, f1, f1);
    GL11.glPopMatrix();

    List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
    for (int i = 0; i < brandings.size(); i++) {
      String brd = brandings.get(i);
      if (!Strings.isNullOrEmpty(brd)) {
        drawString(this.fontRendererObj, brd, 2,
            this.height - 10 + i * (this.fontRendererObj.FONT_HEIGHT + 1), 16777215);
      }
    }

    try {
      URL url = new URL("https://gt-impact.github.io/version");

      InputStream in = url.openStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      StringBuffer buf = new StringBuffer();
      while (true) {
        String line = br.readLine();
        if (line != null) {
          buf.append(line).append("");
        } else {
          break;
        }
      }
      String content = buf.toString();

      String eBug1 = "Latest version: " + EnumChatFormatting.GREEN + content;
      String eBug2 = "Current version: " + EnumChatFormatting.YELLOW + ModPackVersion;
      drawString(this.fontRendererObj, eBug1,
          this.width - this.fontRendererObj.getStringWidth(eBug1) - 2, this.height - 20, -1);
      drawString(this.fontRendererObj, eBug2,
          this.width - this.fontRendererObj.getStringWidth(eBug2) - 2, this.height - 10, -1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
