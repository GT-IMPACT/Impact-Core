package com.impact.mods.gregtech.gui.base;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_GUIContainer;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public class GT_GUIContainerMT_Machine extends GT_GUIContainer {

  public final GT_ContainerMetaTile_Machine mContainer;

  public GT_GUIContainerMT_Machine(GT_ContainerMetaTile_Machine aContainer, String aGUIbackground) {
    super(aContainer, aGUIbackground);
    mContainer = aContainer;
  }

  public GT_GUIContainerMT_Machine(InventoryPlayer aInventoryPlayer,
      IGregTechTileEntity aTileEntity, String aGUIbackground) {
    this(new GT_ContainerMetaTile_Machine(aInventoryPlayer, aTileEntity), aGUIbackground);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    super.drawGuiContainerBackgroundLayer(par1, par2, par3);
    if (GregTech_API.sColoredGUI && mContainer != null && mContainer.mTileEntity != null) {
      byte colorByte = mContainer.mTileEntity.getColorization();
      Dyes color;
      if (colorByte != -1) {
        color = Dyes.get(colorByte);
      } else {
        color = Dyes.MACHINE_METAL;
      }
      GL11.glColor3ub((byte) color.mRGBa[0], (byte) color.mRGBa[1], (byte) color.mRGBa[2]);
    } else {
      GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255);
    }
  }

  public void getTooltip(int mouseX, int mouseY, int startX, int startY, int hight, int weight,
      String[] strings) {
    int renderPosX = mouseX;
    int renderPosY = mouseY;
    mouseX -= (width - xSize) / 2;
    mouseY -= (height - ySize) / 2;
    if (mContainer.mTileEntity != null) {
      if (mouseX < startX || mouseY < startY) {
        return;
      }
      startY += hight;
      if (mouseX < (startX += weight)) {
        if (mouseY < startY) {
          renderText(getTooltipText(strings), renderPosX, renderPosY, fontRendererObj);
        }
      }
    }
  }

  public ArrayList<String> getTooltipText(String[] strings) {
    return new ArrayList<>(Arrays.asList(strings).subList(0, strings.length));
  }

  public void renderText(List strings, int x, int y, FontRenderer font) {
    if (!strings.isEmpty()) {
      GL11.glDisable(GL12.GL_RESCALE_NORMAL);
      GL11.glDisable(GL11.GL_LIGHTING);
      GL11.glDisable(GL11.GL_DEPTH_TEST);
      int k = 0;

      for (Object aP : strings) {
        String s = (String) aP;
        int l = font.getStringWidth(s);
        if (l > k) {
          k = l;
        }
      }

      int x2 = x + 12;
      int y2 = y - 12;
      int i1 = 8;

      if (strings.size() > 1) {
        i1 += 2 + (strings.size() - 1) * 10;
      }
      if (x2 + k > this.width) {
        x2 -= 28 + k;
      }
      if (y2 + i1 + 6 > this.height) {
        y2 = this.height - i1 - 6;
      }

      int j1 = 0xf0000000;//Фон тултипа
      this.drawGradientRect(x2 - 3, y2 - 4, x2 + k + 3, y2 - 3, j1, j1);
      this.drawGradientRect(x2 - 3, y2 + i1 + 3, x2 + k + 3, y2 + i1 + 4, j1, j1);
      this.drawGradientRect(x2 - 3, y2 - 3, x2 + k + 3, y2 + i1 + 3, j1, j1);
      this.drawGradientRect(x2 - 4, y2 - 3, x2 - 3, y2 + i1 + 3, j1, j1);
      this.drawGradientRect(x2 + k + 3, y2 - 3, x2 + k + 4, y2 + i1 + 3, j1, j1);
      int k1 = 0xff001040; //Обводка тултипа
      int l1 = (k1 & 0xfefefe) >> 1 | k1 & 0xff000000;//Затемнение обводки
      this.drawGradientRect(x2 - 3, y2 - 3 + 1, x2 - 3 + 1, y2 + i1 + 3 - 1, k1, l1);
      this.drawGradientRect(x2 + k + 2, y2 - 3 + 1, x2 + k + 3, y2 + i1 + 3 - 1, k1, l1);
      this.drawGradientRect(x2 - 3, y2 - 3, x2 + k + 3, y2 - 3 + 1, k1, k1);
      this.drawGradientRect(x2 - 3, y2 + i1 + 2, x2 + k + 3, y2 + i1 + 3, l1, l1);

      for (int i2 = 0; i2 < strings.size(); ++i2) {
        String s1 = (String) strings.get(i2);
        font.drawStringWithShadow(s1, x2, y2, -1);
        if (i2 == 0) {
          y2 += 2;
        }
        y2 += 10;
      }

      GL11.glEnable(GL11.GL_LIGHTING);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }
  }
}
