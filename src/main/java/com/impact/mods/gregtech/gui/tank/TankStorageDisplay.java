package com.impact.mods.gregtech.gui.tank;

import com.cleanroommc.modularui.api.UpOrDown;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.common.gui.modularui.multiblock.ProcessingGuiState;
import gregtech.common.gui.modularui.multiblock.ProcessingMachineDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import java.text.NumberFormat;

final class TankStorageDisplay extends ProcessingMachineDisplay {

    private static final int MUTED = 0x8999aa;
    private static final int TEXT = 0xe4eaf0;
    private static final int ACCENT = 0x69cbd4;

    private final GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine;
    private final TankStorageGui.Readings readings;
    private final int slots;
    private final NumberFormat amountFormat = NumberFormat.getIntegerInstance();

    TankStorageDisplay(
            GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine,
            ProcessingGuiState state,
            TankStorageGui.Readings readings,
            int slots
    ) {
        super(machine, state);
        this.machine = machine;
        this.readings = readings;
        this.slots = slots;
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> theme) {
        int capacity = readings.capacity();
        if (capacity <= 0) {
            super.draw(context, theme);
            return;
        }

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        font.drawString(machine.getLocalName(), 10, 8, MUTED);
        boolean hasError = drawError(font);
        if (slots == 1) {
            drawSingle(font, capacity);
        } else if (!hasError) {
            drawMulti(font, capacity);
        }
    }

    @Override
    public boolean onMouseScroll(UpOrDown direction, int amount) {
        return readings.capacity() <= 0 && super.onMouseScroll(direction, amount);
    }

    private boolean drawError(FontRenderer font) {
        for (String status : statusLines()) {
            if (status.startsWith(EnumChatFormatting.RED.toString())) {
                font.drawString(status, 10, 18, TEXT);
                return true;
            }
        }
        return false;
    }

    private void drawSingle(FontRenderer font, int capacity) {
        FluidStack fluid = readings.fluid(0);
        int amount = fluid == null ? 0 : fluid.amount;
        font.drawString(TankFluidGauge.percent(amount, capacity) + "%", 132, 37, ACCENT);
        font.drawString(StatCollector.translateToLocal("impact.tank.stored"), 132, 65, MUTED);
        font.drawString(amountFormat.format(amount) + " L", 132, 75, TEXT);
        font.drawString(StatCollector.translateToLocal("impact.tank.capacity"), 132, 101, MUTED);
        font.drawString(amountFormat.format(capacity) + " L", 132, 111, TEXT);
    }

    private void drawMulti(FontRenderer font, int capacity) {
        String usage = StatCollector.translateToLocalFormatted("impact.tank.fluid_count", readings.count(), slots);
        font.drawString(usage, 10, 18, TEXT);
        String perFluid = StatCollector.translateToLocalFormatted("impact.tank.per_fluid",
                amountFormat.format(capacity));
        font.drawString(perFluid, 258 - font.getStringWidth(perFluid), 18, MUTED);
    }
}
