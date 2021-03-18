package com.impact.client.gui;

import appeng.client.gui.widgets.GuiNumberBox;
import appeng.core.AEConfig;
import appeng.core.AppEng;
import com.impact.network.ImpactNetwork;
import com.impact.network.ImpactPacketPriority;
import java.text.DecimalFormat;
import java.text.ParseException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiPriority extends GuiContainer {

  private static final ResourceLocation BACKGROUND = new ResourceLocation(AppEng.MOD_ID, "textures/" + "guis/priority.png");
  ContainterPriority container;
  private GuiNumberBox priority;
  private GuiButton plus1;
  private GuiButton plus10;
  private GuiButton plus100;
  private GuiButton plus1000;
  private GuiButton minus1;
  private GuiButton minus10;
  private GuiButton minus100;
  private GuiButton minus1000;
  EntityPlayer player;

  public GuiPriority(ContainterPriority container, EntityPlayer player) {
    super(container);
    this.container = container;
    this.player = player;
  }

  @SuppressWarnings("unchecked")
  public void initGui() {
    super.initGui();
    final int a = AEConfig.instance.priorityByStacksAmounts(0);
    final int b = AEConfig.instance.priorityByStacksAmounts(1);
    final int c = AEConfig.instance.priorityByStacksAmounts(2);
    final int d = AEConfig.instance.priorityByStacksAmounts(3);

    this.buttonList.add(this.plus1 = new GuiButton(0, this.guiLeft + 20, this.guiTop + 32, 22, 20, "+" + a));
    this.buttonList.add(this.plus10 = new GuiButton(0, this.guiLeft + 48, this.guiTop + 32, 28, 20, "+" + b));
    this.buttonList.add(this.plus100 = new GuiButton(0, this.guiLeft + 82, this.guiTop + 32, 32, 20, "+" + c));
    this.buttonList.add(this.plus1000 = new GuiButton(0, this.guiLeft + 120, this.guiTop + 32, 38, 20, "+" + d));

    this.buttonList.add(this.minus1 = new GuiButton(0, this.guiLeft + 20, this.guiTop + 69, 22, 20, "-" + a));
    this.buttonList.add(this.minus10 = new GuiButton(0, this.guiLeft + 48, this.guiTop + 69, 28, 20, "-" + b));
    this.buttonList.add(this.minus100 = new GuiButton(0, this.guiLeft + 82, this.guiTop + 69, 32, 20, "-" + c));
    this.buttonList.add(this.minus1000 = new GuiButton(0, this.guiLeft + 120, this.guiTop + 69, 38, 20, "-" + d));

    this.priority = new GuiNumberBox(this.fontRendererObj, this.guiLeft + 62, this.guiTop + 57, 59, this.fontRendererObj.FONT_HEIGHT, Long.class);
    this.priority.setEnableBackgroundDrawing(false);
    this.priority.setMaxStringLength(16);
    this.priority.setTextColor(0xFFFFFF);
    this.priority.setVisible(true);
    this.priority.setFocused(true);
    ((ContainterPriority) this.inventorySlots).setTextField(this.priority);
  }

  protected final void drawGuiContainerBackgroundLayer(float f, int x, int y) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(BACKGROUND);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    this.priority.drawTextBox();
  }

  @Override
  protected void actionPerformed(final GuiButton btn) {
    super.actionPerformed(btn);

    final boolean isPlus = btn == this.plus1 || btn == this.plus10 || btn == this.plus100 || btn == this.plus1000;
    final boolean isMinus = btn == this.minus1 || btn == this.minus10 || btn == this.minus100 || btn == this.minus1000;

    if (isPlus || isMinus) {
      this.addQty(this.getQty(btn));
    }
  }

  protected int getQty(final GuiButton btn) {
    try {
      final DecimalFormat df = new DecimalFormat("+#;-#");
      return df.parse(btn.displayString).intValue();
    } catch (final ParseException e) {
      return 0;
    }
  }

  private void addQty(final int i) {
    try {
      String out = this.priority.getText();

      boolean fixed = false;
      while (out.startsWith("0") && out.length() > 1) {
        out = out.substring(1);
        fixed = true;
      }

      if (fixed) {
        this.priority.setText(out);
      }

      if (out.isEmpty()) {
        out = "0";
      }

      long result = Long.parseLong(out);
      result += i;

      this.priority.setText(out = Long.toString(result));
      ImpactNetwork.INSTANCE.sendToServer(new ImpactPacketPriority(out, this.player));
    } catch (final NumberFormatException e) {
      // nope..
      this.priority.setText("0");
    } catch (Exception ignored) {
      int a = 0;
    }
  }

  @Override
  protected void keyTyped(final char character, final int key) {
    if (!this.checkHotbarKeys(key)) {
      if ((key == 211 || key == 205 || key == 203 || key == 14 || character == '-' || Character.isDigit(character)) && this.priority.textboxKeyTyped(character, key)) {
        try {
          String out = this.priority.getText();

          boolean fixed = false;
          while (out.startsWith("0") && out.length() > 1) {
            out = out.substring(1);
            fixed = true;
          }

          if (fixed) {
            this.priority.setText(out);
          }

          if (out.isEmpty()) {
            out = "0";
          }
          ImpactNetwork.INSTANCE.sendToServer(new ImpactPacketPriority(out, player));
        } catch (Exception ignored) {
          int a = 0;
        }
      } else {
        super.keyTyped(character, key);
      }
    }
  }
}
