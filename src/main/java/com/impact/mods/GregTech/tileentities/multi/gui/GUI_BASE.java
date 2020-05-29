package com.impact.mods.GregTech.tileentities.multi.gui;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerInputHandler;
import codechicken.nei.guihook.IContainerTooltipHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase.isValidMetaTileEntity;


abstract class GUI_BASE extends GT_GUIContainerMetaTile_Machine {

    static {
        GuiContainerManager.addInputHandler(new GUI_BASE.GT_RectHandler());
        GuiContainerManager.addTooltipHandler(new GUI_BASE.GT_RectHandler());
    }

    abstract String getmMode();

    String mName = "";
    public String mNEI;
    private boolean  mWorkUpdate = false, mWorks = true;
    public int mEUt = 0;
    public ArrayList<GT_MetaTileEntity_Hatch_Energy> mEnergyHatches = new ArrayList();

    public GUI_BASE(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile) {
        super(new GT_Container_MultiParallelMachine(aInventoryPlayer, aTileEntity, true, true),
                RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay" : aTextureFile ));
        mName = aName;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(mName, 10, 8, 16448255);

        if (this.mContainer != null) {
            if (mContainer != null) {
                if ((((GT_Container_MultiParallelMachine) mContainer).mDisplayErrorCode & 64) != 0) {
                    fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 30, 0);
                    fontRendererObj.drawString(EnumChatFormatting.RED + "Incomplete Structure", 25, 40, 0);
                    fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 50, 0);
                } else {
                    if ((((GT_Container_MultiParallelMachine) this.mContainer).mDisplayErrorCode & 1) != 0) {
                        this.fontRendererObj.drawString(this.trans("132", EnumChatFormatting.WHITE +"Need" + EnumChatFormatting.RED +" Wrench"), 10, 20, 0);
                    }

                    if ((((GT_Container_MultiParallelMachine) this.mContainer).mDisplayErrorCode & 2) != 0) {
                        this.fontRendererObj.drawString(this.trans("133", EnumChatFormatting.WHITE +"Need"+ EnumChatFormatting.RED +" Screwdriver"), 10, 29, 0);
                    }

                    if ((((GT_Container_MultiParallelMachine) this.mContainer).mDisplayErrorCode & 4) != 0) {
                        this.fontRendererObj.drawString(this.trans("134", EnumChatFormatting.WHITE +"Need"+ EnumChatFormatting.RED +" SoftHammer"), 10, 38, 0);
                    }

                    if ((((GT_Container_MultiParallelMachine) this.mContainer).mDisplayErrorCode & 8) != 0) {
                        this.fontRendererObj.drawString(this.trans("135", EnumChatFormatting.WHITE +"Need"+ EnumChatFormatting.RED +" Hammer"), 10, 47, 0);
                    }

                    if ((((GT_Container_MultiParallelMachine) this.mContainer).mDisplayErrorCode & 16) != 0) {
                        this.fontRendererObj.drawString(this.trans("136", EnumChatFormatting.WHITE +"Need"+ EnumChatFormatting.RED +" Soldering"), 10, 56, 0);
                    }

                    if ((((GT_Container_MultiParallelMachine) this.mContainer).mDisplayErrorCode & 32) != 0) {
                        this.fontRendererObj.drawString(this.trans("137", EnumChatFormatting.WHITE +"Need"+ EnumChatFormatting.RED +" Crowbar"), 10, 65, 0);
                    }
                }
            }

            if (((GT_Container_MultiParallelMachine) mContainer).mDisplayErrorCode == 0) {
                fontRendererObj.drawString(EnumChatFormatting.GREEN + (getmMode() == null? "Select " : getmMode())  + "mode", 6, 36, 16448255);
                if (((GT_Container_MultiParallelMachine) mContainer).mActive == 0) {
                    fontRendererObj.drawString("Progress:"+EnumChatFormatting.RED+" not working", 10, 22, 16448255);
                } else {
                    double tScale = ( (double) this.mContainer.mProgressTime / (double) this.mContainer.mMaxProgressTime)*100;
                    if ((int)tScale > 0 && (int)tScale < 100) {
                        fontRendererObj.drawString("Progress: " +EnumChatFormatting.GREEN + this.mContainer.mProgressTime/20 +EnumChatFormatting.WHITE + " / " +EnumChatFormatting.YELLOW + this.mContainer.mMaxProgressTime/20 +EnumChatFormatting.WHITE + " sec", 10, 22, 16448255);
                        fontRendererObj.drawString(GT_Utility.formatNumbers((int) tScale) + "%", 71, 56, 16448255);
                    }
                }
            }
        }
    }






    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        if (this.mContainer != null) {
            if (((GT_Container_MultiParallelMachine) mContainer).mDisplayErrorCode == 0) {
                if (((GT_Container_MultiParallelMachine) mContainer).mActive == 0) {
                    drawTexturedModalRect(x + 152, y + 61, 236, 24, 16, 16);
                    drawTexturedModalRect(x + 157, y + 16, 238, 0, 9, 9);
                } else
                    drawTexturedModalRect(x + 152, y + 61, 236, 39, 16, 16);
                    drawTexturedModalRect(x + 157, y + 16, 247, 0, 9, 9);
                double tScale = (double) this.mContainer.mProgressTime / (double) this.mContainer.mMaxProgressTime;
                drawTexturedModalRect(x + 22, y + 55, 0, 232, Math.min(113, (int) (tScale * 113)), 9);
                drawTexturedModalRect(x + 19, y + 52, 0, 241, 119, 15);
            } else {
                drawTexturedModalRect(x + 152, y + 61, 236, 9, 16, 16);
            }
        }
    }
    public long getMaxInputVoltage() {
        long rVoltage = 0L;
        Iterator var3 = this.mEnergyHatches.iterator();

        while(var3.hasNext()) {
            GT_MetaTileEntity_Hatch_Energy tHatch = (GT_MetaTileEntity_Hatch_Energy)var3.next();
            if (isValidMetaTileEntity(tHatch)) {
                rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage();
            }
        }

        return rVoltage;
    }

    public String trans(String aKey, String aEnglish) {
        return GT_LanguageManager.addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
    }

    public static class GT_RectHandler
            implements IContainerInputHandler, IContainerTooltipHandler {
        public boolean mouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
            if (canHandle(gui)) {
                if (button == 0) {
                    return transferRect(gui, false);
                }
                if (button == 1) {
                    return transferRect(gui, true);
                }
            }
            return false;
        }

        public boolean lastKeyTyped(GuiContainer gui, char keyChar, int keyCode) {
            return false;
        }

        public boolean canHandle(GuiContainer gui) {
            return (gui instanceof GUI_BASE && GT_Utility.isStringValid(((GUI_BASE) gui).mNEI));
        }

        public List<String> handleTooltip(GuiContainer gui, int mousex, int mousey, List<String> currenttip) {
            if ((canHandle(gui)) && (currenttip.isEmpty())) {
                 if (gui instanceof GUI_BASE && new Rectangle(148, 52, 17, 17).contains(new Point(GuiDraw.getMousePosition().x - ((GUI_BASE) gui).getLeft() - codechicken.nei.recipe.RecipeInfo.getGuiOffset(gui)[0], GuiDraw.getMousePosition().y - ((GUI_BASE) gui).getTop() - codechicken.nei.recipe.RecipeInfo.getGuiOffset(gui)[1]))) {
                    currenttip.add("NEI");
                }

            }
            return currenttip;
        }

        private boolean transferRect(GuiContainer gui, boolean usage) {
            if (gui instanceof GUI_BASE) {
                return (canHandle(gui)) && (new Rectangle(148, 52, 17, 17).contains(new Point(GuiDraw.getMousePosition().x - ((GUI_BASE) gui).getLeft() - codechicken.nei.recipe.RecipeInfo.getGuiOffset(gui)[0], GuiDraw.getMousePosition().y - ((GUI_BASE) gui).getTop() - codechicken.nei.recipe.RecipeInfo.getGuiOffset(gui)[1]))) && (usage ? GuiUsageRecipe.openRecipeGui(((GUI_BASE) gui).mNEI, new Object[0]) : GuiCraftingRecipe.openRecipeGui(((GUI_BASE) gui).mNEI, new Object[0]));
            }
            return false;
        }

        public List<String> handleItemDisplayName(GuiContainer gui, ItemStack itemstack, List<String> currenttip) {
            return currenttip;
        }

        public List<String> handleItemTooltip(GuiContainer gui, ItemStack itemstack, int mousex, int mousey, List<String> currenttip) {
            return currenttip;
        }

        public boolean keyTyped(GuiContainer gui, char keyChar, int keyCode) {
            return false;
        }

        public void onKeyTyped(GuiContainer gui, char keyChar, int keyID) {
        }

        public void onMouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
        }

        public void onMouseUp(GuiContainer gui, int mousex, int mousey, int button) {
        }

        public boolean mouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
            return false;
        }

        public void onMouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
        }

        public void onMouseDragged(GuiContainer gui, int mousex, int mousey, int button, long heldTime) {
        }
    }
}
