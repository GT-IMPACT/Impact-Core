package com.impact.mods.gregtech.gui;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;


public class GUI_NuclearReactor extends GT_GUIContainerMT_Machine {

  String mName = "";

  public GUI_NuclearReactor(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity,
      String aName,
      String aTextureFile) {
    super(new GT_Container_NuclearReactor(aInventoryPlayer, aTileEntity),
        RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay"
            : aTextureFile));
    mName = aName;
  }

  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    fontRendererObj.drawString(mName, 10, 8, 16448255);

    if (this.mContainer != null) {

      GT_Container_NuclearReactor container = (GT_Container_NuclearReactor) this.mContainer;
      fontRendererObj
              .drawString(container.isFastDecay ? "Fast Decay Mode" : "Default Mode", 10, 70, 16448255);

      if (((mContainer).mDisplayErrorCode & 64) != 0) {
        fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 30, 0);
        fontRendererObj.drawString(EnumChatFormatting.RED + "Incomplete Structure", 25, 40, 0);
        fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 50, 0);
      } else {
        if (((this.mContainer).mDisplayErrorCode & 1) != 0) {
          this.fontRendererObj.drawString(this.trans("132",
              EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Wrench"), 10, 20, 0);
        }

        if (((this.mContainer).mDisplayErrorCode & 2) != 0) {
          this.fontRendererObj.drawString(this.trans("133",
              EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Screwdriver"), 10, 29,
              0);
        }

        if (((this.mContainer).mDisplayErrorCode & 4) != 0) {
          this.fontRendererObj.drawString(this.trans("134",
              EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " SoftHammer"), 10, 38,
              0);
        }

        if (((this.mContainer).mDisplayErrorCode & 8) != 0) {
          this.fontRendererObj.drawString(this.trans("135",
              EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Hammer"), 10, 47, 0);
        }

        if (((this.mContainer).mDisplayErrorCode & 16) != 0) {
          this.fontRendererObj.drawString(this.trans("136",
              EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Soldering"), 10, 56,
              0);
        }

        if (((this.mContainer).mDisplayErrorCode & 32) != 0) {
          this.fontRendererObj.drawString(this.trans("137",
              EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Crowbar"), 10, 65, 0);
        }
      }

      if ((mContainer).mDisplayErrorCode == 0) {
        if ((mContainer).mActive == 0) {
          fontRendererObj
              .drawString("Progress:" + EnumChatFormatting.RED + " not working", 10, 22, 16448255);
        } else {
          double tScale = (double) container.mTemp / (double) container.mMaxTemp;
          tScale = tScale <= 0 ? 0 : tScale;
          int temperature = Math.min(((int) (100 * tScale)), 100);
          fontRendererObj
              .drawString("Temp: " + temperature + "%", 96, 148, 16448255);

          fontRendererObj
              .drawString("Input " + (container.isFastDecay ? "Coolant:" : " Water:"), 10, 95, 16448255);
          fontRendererObj
              .drawString(container.mCurrFluid < 0 ? "All Rods UP" : container.mCurrFluid + " L/t", 10, 105, 16448255);

//          fontRendererObj
//              .drawString("" + Arrays.toString(container.mHatchesRodPosition) + "", 10, 75, 16448255);

          fontRendererObj
              .drawString("Output " + (container.isFastDecay ? "Hot Coolant:" : temperature < 50 ? "Steam:" : "SH Steam:"), 10, 120, 16448255);
          fontRendererObj
              .drawString((container.mCurrFluid * (container.isFastDecay ? 1 : 160)) < 0 ? "All Rods UP" : (container.mCurrFluid * (container.isFastDecay ? 1 : 160)) + " L/t", 10, 130, 16448255);
        }
      }
    }
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float par3) {
    super.drawScreen(mouseX, mouseY, par3);
    if (mContainer != null) {
      if (mContainer.mTileEntity != null) {
        getTooltip(mouseX, mouseY, 155, 5, 16, 16, new String[]{
            "Start/Stop machine",
            EnumChatFormatting.DARK_GRAY + "Button color:",
            EnumChatFormatting.RED + "    Any Problem",
            EnumChatFormatting.YELLOW + "    Ready to start",
            EnumChatFormatting.GREEN + "    Starting",
        });
        getTooltip(mouseX, mouseY, 155, 37, 16, 16, new String[]{
            "Up all rods (10%)"
        });
        getTooltip(mouseX, mouseY, 155, 55, 16, 16, new String[]{
            "Down all rods (10%)"
        });
        getTooltip(mouseX, mouseY, 155, 73, 16, 16, new String[]{
            "Up all rods completely"
        });
      }
    }
  }


  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    super.drawGuiContainerBackgroundLayer(par1, par2, par3);
    int x = (this.width - this.xSize) / 2;
    int y = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    if (this.mContainer != null) {
      if ((mContainer).mDisplayErrorCode == 0) {
        if ((mContainer).mActive == 0) {
          drawTexturedModalRect(x + 155, y + 4, 236, 24, 16, 16);
          drawTexturedModalRect(x + 141, y + 8, 238, 0, 9, 9);
        } else {
          drawTexturedModalRect(x + 155, y + 4, 236, 39, 16, 16);
        }
        drawTexturedModalRect(x + 141, y + 8, 247, 0, 9, 9);
        GT_Container_NuclearReactor container = (GT_Container_NuclearReactor) this.mContainer;
        double tScale = (double) container.mTemp / (double) container.mMaxTemp;
        drawTexturedModalRect(x + 159, y + 144 - (int) Math.min(30D, tScale * 30D), 228, 30 - (int) Math.min(30D, tScale * 30D), 8, 30);
      } else {
        drawTexturedModalRect(x + 155, y + 4, 236, 9, 16, 16);
      }
    }
  }

  public String trans(String aKey, String aEnglish) {
    return GT_LanguageManager
        .addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
  }
}
