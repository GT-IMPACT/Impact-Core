package com.impact.mods.gregtech.tileentities.covers;

import com.impact.util.math.PackUnpackValues;
import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_GUICover;
import gregtech.api.gui.widgets.GT_GuiIntegerTextBox;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.net.GT_Packet_TileEntityCover;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import static com.impact.util.math.PackUnpackValues.unpackFloat1;
import static com.impact.util.math.PackUnpackValues.unpackFloat2;

public class GTC_AdvFluidDetectorRange extends GT_CoverBehavior {

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if (aTimer % 20 == 0) {

            int tMax = 0;
            int tUsed = 0;

            if ((aTileEntity instanceof IFluidHandler)) {

                FluidTankInfo tTank = ((IFluidHandler) aTileEntity).getTankInfo(ForgeDirection.UNKNOWN)[0];

                if (tTank != null) {
                    FluidStack tLiquid = tTank.fluid;
                    tMax = tTank.capacity;
                    if (tLiquid != null) {
                        tUsed = tLiquid.amount;
                    }
                }
            }

            int startBound = (int) ((double) unpackFloat1(aCoverVariable) / 100 * tMax);
            int endBound = (int) ((double) unpackFloat2(aCoverVariable) / 100 * tMax);

            byte power = (byte) (startBound <= tUsed && tUsed <= endBound ? 15 : 0);

            aTileEntity.setOutputRedstoneSignal(aSide, power);
        }
        return aCoverVariable;
    }

    public boolean isCoverPlaceable(byte aSide, GT_ItemStack aStack, ICoverable aTileEntity) {
        return aTileEntity instanceof IFluidHandler;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        return aCoverVariable;
    }

    public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 5;
    }

    @Override
    public boolean hasCoverGUI() {
        return true;
    }

    @Override
    public Object getClientGUI(byte aSide, int aCoverID, int coverData, ICoverable aTileEntity) {
        return new GUI(aSide, aCoverID, coverData, aTileEntity);
    }

    private class GUI extends GT_GUICover {

        private final static int startX = 10;
        private final static int startY = 25;
        private final static int spaceX = 18;
        private final static int spaceY = 18;

        private final byte side;
        private final int coverID;
        private final GT_GuiIntegerTextBox startBoundValue, startBoundPercent, endBoundValue, endBoundPercent;

        private int coverVariable, maxCapacity;
        private int valueStart, valueEnd;
        private float percentStart, percentEnd;

        public GUI(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
            super(aTileEntity, 176, 107, GT_Utility.intToStack(aCoverID));

            this.header = "Fluid Detector - Range";

            this.side = aSide;
            this.coverID = aCoverID;
            this.coverVariable = aCoverVariable;
            this.maxCapacity = 1;
            this.startBoundValue = new GT_GuiIntegerTextBox(this, 1, startX, startY + 2, spaceX * 4 - 3, 12);
            this.endBoundValue = new GT_GuiIntegerTextBox(this, 3, startX, startY + 6 + spaceY * 2, spaceX * 4 - 3, 12);


            this.startBoundPercent = new GT_GuiIntegerTextBox(this, 2, startX, startY + spaceY + 2, spaceX * 4 - 3, 12);
            this.endBoundPercent = new GT_GuiIntegerTextBox(this, 4, startX, startY + 6 + spaceY * 3, spaceX * 4 - 3, 12);

            if ((aTileEntity instanceof IFluidHandler)) {
                FluidTankInfo tTank = ((IFluidHandler) aTileEntity).getTankInfo(ForgeDirection.UNKNOWN)[0];
                if (tTank != null) {
                    maxCapacity = tTank.capacity;
                }
            }
//			percentStart = (int) ((double) valueStart / (double) maxCapacity * 100d);
            percentStart = unpackFloat1(coverVariable);
            startBoundPercent.setText(String.valueOf(Math.abs((int) percentStart)));
            startBoundPercent.setMaxStringLength(3);

//			percentEnd = (int) ((double) valueEnd / (double) maxCapacity * 100d);
            percentEnd = unpackFloat2(coverVariable);
            endBoundPercent.setText(String.valueOf(Math.abs((int) percentEnd)));
            endBoundPercent.setMaxStringLength(3);

            valueStart = (int) ((double) percentStart / 100 * maxCapacity);
            valueEnd = (int) ((double) percentEnd / 100 * maxCapacity);

            startBoundValue.setText(String.valueOf(Math.abs(valueStart)));
            startBoundValue.setMaxStringLength(12);

            endBoundValue.setText(String.valueOf(Math.abs(valueEnd)));
            endBoundValue.setMaxStringLength(12);
        }

        @Override
        public void drawExtras(int mouseX, int mouseY, float parTicks) {
            super.drawExtras(mouseX, mouseY, parTicks);
            this.getFontRenderer().drawString("L (Start Bound)", startX + spaceX * 4, 4 + startY, 0xFF555555);
            this.getFontRenderer().drawString("% (Start Bound)", startX + spaceX * 4, 4 + startY + spaceY, 0xFF555555);

            this.getFontRenderer().drawString("L (End Bound)", startX + spaceX * 4, 8 + startY + spaceY * 2, 0xFF555555);
            this.getFontRenderer().drawString("% (End Bound)", startX + spaceX * 4, 8 + startY + spaceY * 3, 0xFF555555);
        }

        @Override
        protected void onInitGui(int guiLeft, int guiTop, int gui_width, int gui_height) {
        }

        @Override
        public void onMouseWheel(int x, int y, int delta) {
            for (GT_GuiIntegerTextBox box : textBoxes) {
                if (box.isFocused()) {
                    int step = Math.max(1, Math.abs(delta / 120));
                    step = (isShiftKeyDown() ? 1000 : isCtrlKeyDown() ? 50 : 1) * (delta > 0 ? step : -step);
                    long i;
                    try {
                        i = Long.parseLong(box.getText());
                    } catch (NumberFormatException e) {
                        return;
                    }
                    i = i + step;
                    if (i > Integer.MAX_VALUE)
                        i = Integer.MAX_VALUE;
                    else if (i < Integer.MIN_VALUE)
                        i = Integer.MIN_VALUE;

                    box.setText(String.valueOf(i));
                    return;
                }
            }
        }

        @Override
        public void applyTextBox(GT_GuiIntegerTextBox box) {
            long i;
            String s = box.getText().trim();
            try {
                i = Long.parseLong(s);
            } catch (NumberFormatException e) {
                resetTextBox(box);
                return;
            }
            if (i > Integer.MAX_VALUE) {
                i = Integer.MAX_VALUE;
            } else if (i < Integer.MIN_VALUE) {
                i = Integer.MIN_VALUE;
            }

            switch (box.id) {
                case 1:
                    valueStart = Math.min((int) i, maxCapacity);
                    percentStart = (int) ((double) valueStart / (double) maxCapacity * 100d);
                    coverVariable = PackUnpackValues.packFloats(percentStart, percentEnd);
                    break;
                case 2:
                    percentStart = Math.min((int) i, 100);
                    valueStart = (int) ((double) percentStart * (double) maxCapacity / 100d);
                    coverVariable = PackUnpackValues.packFloats(percentStart, percentEnd);
                    break;
                case 3:
                    valueEnd = Math.min((int) i, maxCapacity);
                    percentEnd = (int) ((double) valueEnd / (double) maxCapacity * 100d);
                    coverVariable = PackUnpackValues.packFloats(percentStart, percentEnd);
                    break;
                case 4:
                    percentEnd = Math.min((int) i, 100);
                    valueEnd = (int) ((double) percentEnd * (double) maxCapacity / 100d);
                    coverVariable = PackUnpackValues.packFloats(percentStart, percentEnd);
                    break;
            }

            startBoundValue.setText(String.valueOf(valueStart));
            startBoundPercent.setText(String.valueOf((int) percentStart));

            endBoundValue.setText(String.valueOf(valueEnd));
            endBoundPercent.setText(String.valueOf((int) percentEnd));

            GT_Values.NW.sendToServer(new GT_Packet_TileEntityCover(side, coverID, coverVariable, tile));
        }

        @Override
        public void resetTextBox(GT_GuiIntegerTextBox box) {
            box.setText(String.valueOf(0));
        }
    }
}
