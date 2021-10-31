package com.impact.mods.gregtech.gui.parallelcomputer;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

public class GUI_SuperParallelComputer extends GUI_BASE {

  public GUI_SuperParallelComputer(InventoryPlayer aInventoryPlayer,
      IGregTechTileEntity aTileEntity, String aName) {
    super(aName, "MultiParallelBlockGUI.png", new Container_SuperParallelComputer(aInventoryPlayer, aTileEntity));
    mName = aName;
  }

  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    fontRendererObj.drawString(mName, 10, 8, 16448255);
    if (this.mContainer != null) {
      needTools(true);
      if ((mContainer).mDisplayErrorCode == 0) {
        if ((mContainer).mActive == 0) {
          fontRendererObj.drawString("Active:" + EnumChatFormatting.RED
              + " off", 10, 22, 16448255);
        } else {
          fontRendererObj.drawString("Active:" + EnumChatFormatting.GREEN
              + " on", 10, 22, 16448255);
          fontRendererObj.drawString("Max PP: " + EnumChatFormatting.YELLOW
                  + ((Container_SuperParallelComputer) mContainer).mMaxParallelCapacity, 10, 32,
              16448255);
          int salary = ((Container_SuperParallelComputer) mContainer).mMaxParallelCapacity
              - ((Container_SuperParallelComputer) mContainer).mCurrentParallelCapacity;
          String salaryText = salary < 0 ? EnumChatFormatting.RED + Integer.toString(salary)
              : EnumChatFormatting.YELLOW + Integer.toString(salary);
          fontRendererObj.drawString("Current PP: "
                  + salaryText, 10, 42, 16448255);
          fontRendererObj.drawString("Usage PP: " + EnumChatFormatting.GREEN
              + ((Container_SuperParallelComputer) mContainer).mCurrentParallelCapacity, 10, 52, 16448255);
        }
      }
    }
  }

  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    super.drawGuiContainerBackgroundLayer(par1, par2, par3);
    drawGuiBG(true, false);
  }
}
