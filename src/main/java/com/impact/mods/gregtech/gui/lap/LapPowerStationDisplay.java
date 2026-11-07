package com.impact.mods.gregtech.gui.lap;

import com.cleanroommc.modularui.api.UpOrDown;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.common.gui.modularui.multiblock.ProcessingGuiState;
import gregtech.common.gui.modularui.multiblock.ProcessingMachineDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.text.NumberFormat;

final class LapPowerStationDisplay extends ProcessingMachineDisplay {

    private static final int LEFT = 10;
    private static final int RIGHT = 258;
    private static final int CHART_LEFT = 40;
    private static final int CHART_TOP = 53;
    private static final int CHART_BOTTOM = 104;

    private static final int MUTED = 0x8999aa;
    private static final int TEXT = 0xe4eaf0;
    private static final int CHARGE = 0x69e4ce;
    private static final int INPUT = 0x73ce8d;
    private static final int OUTPUT = 0xe8bf63;
    private static final int GRID = 0xff23353d;
    private static final int FILL = 0xb0245653;
    private static final int TRACE = 0xff000000 | CHARGE;

    private final GTMTE_LapPowerStation machine;
    private final GenericSyncValue<NBTTagCompound, ?> readings;
    private final GenericSyncValue<NBTTagCompound, ?> history;
    private final NumberFormat energyFormat = NumberFormat.getIntegerInstance();
    private final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    LapPowerStationDisplay(
            GTMTE_LapPowerStation machine,
            ProcessingGuiState processing,
            GenericSyncValue<NBTTagCompound, ?> readings,
            GenericSyncValue<NBTTagCompound, ?> history
    ) {
        super(machine, processing);
        this.machine = machine;
        this.readings = readings;
        this.history = history;
        percentFormat.setMaximumFractionDigits(2);
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> theme) {
        NBTTagCompound telemetry = readings.getValue();
        long capacity = telemetry.getLong("capacity");
        if (!telemetry.getBoolean("formed") || capacity <= 0) {
            super.draw(context, theme);
            return;
        }

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        long stored = telemetry.getLong("stored");
        NBTTagCompound selectedHistory = history.getValue();
        long input = selectedHistory.getLong("averageInput");
        long output = selectedHistory.getLong("averageOutput");

        font.drawString(machine.getLocalName(), LEFT, 8, MUTED);
        String percent = percentFormat.format(LapChargeHistory.fraction(stored, capacity));
        drawRight(font, percent, 8, CHARGE);
        font.drawString(energy("stored", stored), LEFT, 19, TEXT);
        font.drawString(energy("capacity", capacity), LEFT, 29, TEXT);
        font.drawString(text("history"), LEFT, 41, MUTED);

        if (telemetry.getBoolean("enabled")) {
            String direction = input > output ? "charging" : input < output ? "discharging" : "steady";
            int color = input > output ? INPUT : input < output ? OUTPUT : MUTED;
            drawRight(font, text(direction), 41, color);
        } else {
            drawRight(font, text("disabled"), 41, MUTED);
        }

        drawHistory(font, selectedHistory);
        font.drawString(text("average_input", energyFormat.format(input)), LEFT, 122, INPUT);
        font.drawString(text("average_output", energyFormat.format(output)), LEFT, 134, OUTPUT);
    }

    @Override
    public boolean onMouseScroll(UpOrDown direction, int amount) {
        if (readings.getValue().getBoolean("formed") && readings.getValue().getLong("capacity") > 0) {
            return false;
        }
        return super.onMouseScroll(direction, amount);
    }

    private static void drawHistory(FontRenderer font, NBTTagCompound history) {
        int[] samples = history.getIntArray("charge");
        Gui.drawRect(CHART_LEFT, CHART_TOP, RIGHT, CHART_BOTTOM + 1, 0xff101a20);
        for (int percent = 0; percent <= 100; percent += 25) {
            int y = CHART_BOTTOM - percent * (CHART_BOTTOM - CHART_TOP) / 100;
            Gui.drawRect(CHART_LEFT, y, RIGHT, y + 1, GRID);
            if (percent % 50 == 0) {
                font.drawString(percent + "%", LEFT, y - 3, MUTED);
            }
        }
        for (int quarter = 0; quarter <= 4; quarter++) {
            int x = CHART_LEFT + quarter * (RIGHT - CHART_LEFT - 1) / 4;
            Gui.drawRect(x, CHART_TOP, x + 1, CHART_BOTTOM + 1, GRID);
        }

        int previousY = 0;
        for (int index = 0; index < samples.length; index++) {
            int x = sampleX(index, samples.length);
            int nextX = index + 1 < samples.length ? sampleX(index + 1, samples.length) : RIGHT;
            int y = CHART_BOTTOM - samples[index] * (CHART_BOTTOM - CHART_TOP) / LapChargeHistory.FULL_CHARGE;
            Gui.drawRect(x, y + 1, nextX, CHART_BOTTOM + 1, FILL);
            Gui.drawRect(x, y, nextX, y + 1, TRACE);
            if (index > 0) {
                Gui.drawRect(x, Math.min(y, previousY), x + 1, Math.max(y, previousY) + 1, TRACE);
            }
            previousY = y;
        }

        if (samples.length == 0) {
            String waiting = text("collecting");
            int center = (CHART_LEFT + RIGHT - font.getStringWidth(waiting)) / 2;
            font.drawString(waiting, center, 75, MUTED);
        }
        String period = history.hasKey("period") ? history.getString("period") : LapHistoryPeriod.MINUTE.key;
        font.drawString("-" + text("period." + period), CHART_LEFT, 109, MUTED);
        drawRight(font, text("now"), 109, MUTED);
    }

    private static int sampleX(int index, int count) {
        int windowIndex = LapChargeHistory.POINTS - count + index;
        return CHART_LEFT + windowIndex * (RIGHT - CHART_LEFT - 1) / (LapChargeHistory.POINTS - 1);
    }

    private static void drawRight(FontRenderer font, String label, int y, int color) {
        font.drawString(label, RIGHT - font.getStringWidth(label), y, color);
    }

    private String energy(String key, long amount) {
        return StatCollector.translateToLocalFormatted("impact.waila.lsc." + key, energyFormat.format(amount));
    }

    private static String text(String key, Object... arguments) {
        return StatCollector.translateToLocalFormatted("impact.lap." + key, arguments);
    }
}
