package com.impact.mods.gregtech.tileentities.covers;

import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.util.math.PackUnpackValues;
import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_GUICover;
import gregtech.api.gui.widgets.GT_GuiIntegerTextBox;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicBatteryBuffer;
import gregtech.api.net.GT_Packet_TileEntityCover;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import static com.impact.util.math.PackUnpackValues.unpackFloat1;
import static com.impact.util.math.PackUnpackValues.unpackFloat2;

public class GTC_AdvEUDetectorRange extends GT_CoverBehavior {

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if (aTimer % 20 == 0) {
            long tMax = 0;
            long tUsed = 0;
            if (aTileEntity instanceof IGregTechTileEntity) {
                IGregTechTileEntity tTileEntity = (IGregTechTileEntity) aTileEntity;
                IMetaTileEntity mTileEntity = tTileEntity.getMetaTileEntity();

                if (mTileEntity instanceof GTMTE_LapPowerStation) {
                    GTMTE_LapPowerStation buffer = (GTMTE_LapPowerStation) mTileEntity;
                    tMax = buffer.capacity;
                    tUsed = buffer.stored;
                } else if (mTileEntity instanceof GT_MetaTileEntity_BasicBatteryBuffer) {
                    tMax = aTileEntity.getEUCapacity();
                    tUsed = aTileEntity.getStoredEU();
                    GT_MetaTileEntity_BasicBatteryBuffer buffer = (GT_MetaTileEntity_BasicBatteryBuffer) mTileEntity;
                    if (buffer.mInventory != null) {
                        for (ItemStack aStack : buffer.mInventory) {
                            if (GT_ModHandler.isElectricItem(aStack)) {
                                if (aStack.getItem() instanceof GT_MetaBase_Item) {
                                    Long[] stats = ((GT_MetaBase_Item) aStack.getItem()).getElectricStats(aStack);
                                    if (stats != null) {
                                        tMax += stats[0];
                                        tUsed = tUsed + ((GT_MetaBase_Item) aStack.getItem()).getRealCharge(aStack);
                                    }
                                } else if (aStack.getItem() instanceof IElectricItem) {
                                    tUsed += (long) ic2.api.item.ElectricItem.manager.getCharge(aStack);
                                    tMax += (long) ((IElectricItem) aStack.getItem()).getMaxCharge(aStack);
                                }
                            }
                        }
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

        private int coverVariable;
        private long maxCapacity;
        private long valueStart, valueEnd;
        private float percentStart, percentEnd;

        public GUI(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
            super(aTileEntity, 176, 107, GT_Utility.intToStack(aCoverID));

            this.header = "Energy Detector - Range";

            this.side = aSide;
            this.coverID = aCoverID;
            this.coverVariable = aCoverVariable;
            this.maxCapacity = 1;
            this.startBoundValue = new GT_GuiIntegerTextBox(this, 1, startX, startY + 2, spaceX * 4 - 3, 12);
            this.endBoundValue = new GT_GuiIntegerTextBox(this, 3, startX, startY + 6 + spaceY * 2, spaceX * 4 - 3, 12);


            this.startBoundPercent = new GT_GuiIntegerTextBox(this, 2, startX, startY + spaceY + 2, spaceX * 4 - 3, 12);
            this.endBoundPercent = new GT_GuiIntegerTextBox(this, 4, startX, startY + 6 + spaceY * 3, spaceX * 4 - 3, 12);

            if (aTileEntity instanceof IGregTechTileEntity) {
                IGregTechTileEntity tTileEntity = (IGregTechTileEntity) aTileEntity;
                IMetaTileEntity mTileEntity = tTileEntity.getMetaTileEntity();

                if (mTileEntity instanceof GTMTE_LapPowerStation) {
                    GTMTE_LapPowerStation buffer = (GTMTE_LapPowerStation) mTileEntity;
                    maxCapacity = buffer.capacity;
                } else if (mTileEntity instanceof GT_MetaTileEntity_BasicBatteryBuffer) {
                    maxCapacity = aTileEntity.getEUCapacity();
                    GT_MetaTileEntity_BasicBatteryBuffer buffer = (GT_MetaTileEntity_BasicBatteryBuffer) mTileEntity;
                    if (buffer.mInventory != null) {
                        for (ItemStack aStack : buffer.mInventory) {
                            if (GT_ModHandler.isElectricItem(aStack)) {
                                if (aStack.getItem() instanceof GT_MetaBase_Item) {
                                    Long[] stats = ((GT_MetaBase_Item) aStack.getItem()).getElectricStats(aStack);
                                    if (stats != null) {
                                        maxCapacity += stats[0];
                                    }
                                } else if (aStack.getItem() instanceof IElectricItem) {
                                    maxCapacity += (long) ((IElectricItem) aStack.getItem()).getMaxCharge(aStack);
                                }
                            }
                        }
                    }
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

            valueStart = (long) ((double) percentStart / 100 * maxCapacity);
            valueEnd = (long) ((double) percentEnd / 100 * maxCapacity);

            startBoundValue.setText(String.valueOf(Math.abs(valueStart)));
            startBoundValue.setMaxStringLength(19);

            endBoundValue.setText(String.valueOf(Math.abs(valueEnd)));
            endBoundValue.setMaxStringLength(19);
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

            switch (box.id) {
                case 1:
                    valueStart = Math.min(i, maxCapacity);
                    percentStart = (long) ((double) valueStart / (double) maxCapacity * 100d);
                    coverVariable = PackUnpackValues.packFloats(percentStart, percentEnd);
                    break;
                case 2:
                    percentStart = Math.min(i, 100);
                    valueStart = (long) ((double) percentStart * (double) maxCapacity / 100d);
                    coverVariable = PackUnpackValues.packFloats(percentStart, percentEnd);
                    break;
                case 3:
                    valueEnd = Math.min(i, maxCapacity);
                    percentEnd = (long) ((double) valueEnd / (double) maxCapacity * 100d);
                    coverVariable = PackUnpackValues.packFloats(percentStart, percentEnd);
                    break;
                case 4:
                    percentEnd = Math.min(i, 100);
                    valueEnd = (long) ((double) percentEnd * (double) maxCapacity / 100d);
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
