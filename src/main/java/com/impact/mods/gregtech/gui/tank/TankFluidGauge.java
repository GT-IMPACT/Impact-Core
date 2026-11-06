package com.impact.mods.gregtech.gui.tank;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.widget.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import java.text.NumberFormat;

final class TankFluidGauge extends Widget<TankFluidGauge> {

    private static final int FRAME = 0xff52616b;
    private static final int EMPTY = 0xff141f25;
    private static final int ACCENT = 0xff69cbd4;
    private static final int TEXT = 0xe4eaf0;

    private final TankStorageGui.Readings readings;
    private final int index;
    private final boolean compact;
    private final NumberFormat amountFormat = NumberFormat.getIntegerInstance();

    TankFluidGauge(TankStorageGui.Readings readings, int index, boolean compact) {
        this.readings = readings;
        this.index = index;
        this.compact = compact;
        background(IDrawable.EMPTY);
        disableHoverBackground();
        tooltipAutoUpdate(true);
        tooltipBuilder(tooltip -> {
            FluidStack fluid = readings.fluid(index);
            if (fluid != null) {
                tooltip.addLine(fluid.getLocalizedName());
                tooltip.addLine(StatCollector.translateToLocalFormatted("impact.tank.amount_tooltip",
                        amountFormat.format(fluid.amount), amountFormat.format(readings.capacity())));
            }
        });
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> theme) {
        int width = getArea().width;
        int height = getArea().height;
        Gui.drawRect(0, 0, width, height, FRAME);
        Gui.drawRect(1, 1, width - 1, height - 1, EMPTY);

        FluidStack fluid = readings.fluid(index);
        int capacity = readings.capacity();
        if (fluid == null || capacity <= 0) {
            return;
        }
        if (compact) {
            drawCompact(context, fluid, capacity);
        } else {
            drawTank(context, fluid, capacity, width, height);
        }
    }

    private static void drawCompact(ModularGuiContext context, FluidStack fluid, int capacity) {
        GuiDraw.drawFluidTexture(fluid, 3, 3, 14, 14, context.getCurrentDrawingZ());
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        font.drawString(percent(fluid.amount, capacity) + "%", 20, 4, TEXT);
        Gui.drawRect(20, 14, 45, 17, 0xff2a3a43);
        int width = scaledAmount(fluid.amount, capacity, 25);
        Gui.drawRect(20, 14, 20 + width, 17, ACCENT);
    }

    private static void drawTank(ModularGuiContext context, FluidStack fluid, int capacity, int width, int height) {
        if (fluid.amount > 0) {
            int fillHeight = Math.max(1, scaledAmount(fluid.amount, capacity, height - 4));
            GuiDraw.drawFluidTexture(fluid, 2, height - 2 - fillHeight, width - 4, fillHeight,
                    context.getCurrentDrawingZ());
        }
        for (int quarter = 1; quarter <= 3; quarter++) {
            int y = quarter * height / 4;
            Gui.drawRect(2, y, 10, y + 1, 0xffafc5d0);
            Gui.drawRect(width - 10, y, width - 2, y + 1, 0xffafc5d0);
        }
    }

    static int percent(int amount, int capacity) {
        return scaledAmount(amount, capacity, 100);
    }

    private static int scaledAmount(int amount, int capacity, int scale) {
        return capacity > 0 ? (int) ((long) Math.min(amount, capacity) * scale / capacity) : 0;
    }
}
