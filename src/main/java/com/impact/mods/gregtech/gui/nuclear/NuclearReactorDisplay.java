package com.impact.mods.gregtech.gui.nuclear;

import com.cleanroommc.modularui.api.UpOrDown;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import gregtech.common.gui.modularui.multiblock.ProcessingGuiState;
import gregtech.common.gui.modularui.multiblock.ProcessingMachineDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;
import java.util.Locale;

final class NuclearReactorDisplay extends ProcessingMachineDisplay {

    private static final int MUTED = 0x8999aa;
    private static final int TEXT = 0xe4eaf0;
    private static final int GREEN = 0x73ce8d;
    private static final int YELLOW = 0xe8bf63;
    private final GTMTE_NuclearReactorBase<?> reactor;
    private final GenericSyncValue<NBTTagCompound, ?> state;

    NuclearReactorDisplay(
            GTMTE_NuclearReactorBase<?> reactor,
            ProcessingGuiState processing,
            GenericSyncValue<NBTTagCompound, ?> state
    ) {
        super(reactor, processing);
        this.reactor = reactor;
        this.state = state;
    }

    @Override
    public boolean onMouseScroll(UpOrDown direction, int amount) {
        return false;
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> theme) {
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        font.drawString(reactor.getLocalName(), 10, 8, MUTED);
        NBTTagCompound snapshot = state.getValue();
        if (!snapshot.getBoolean("ready")) {
            return;
        }
        List<String> status = statusLines();
        if (!status.isEmpty()) {
            int statusIndex = snapshot.getInteger("errors") == 0 ? status.size() - 1 : 0;
            font.drawString(status.get(statusIndex), 10, 18, TEXT);
        }
        drawRods(font, snapshot.getIntArray("rods"));
        if (snapshot.getInteger("errors") != 0) {
            int y = 30;
            for (int index = 1; index < status.size(); index++) {
                for (Object line : font.listFormattedStringToWidth(status.get(index), 120)) {
                    font.drawString((String) line, 138, y, TEXT);
                    y += 10;
                }
            }
        } else {
            drawReadings(font, snapshot);
        }
    }

    private void drawRods(FontRenderer font, int[] positions) {
        font.drawString(text("rods"), 10, 30, MUTED);
        if (positions.length == 0) {
            font.drawString(text("no_rods"), 10, 50, YELLOW);
        }
        int columns = positions.length <= 1 ? 1 : positions.length <= 9 ? 3 : 5;
        for (int index = 0; index < positions.length; index++) {
            int x = 10 + index % columns * 23;
            int y = 42 + index / columns * 15;
            int position = Math.max(0, Math.min(10, positions[index]));
            Gui.drawRect(x, y, x + 22, y + 14, 0xff52616b);
            Gui.drawRect(x + 1, y + 1, x + 21, y + 13, 0xff131d23);
            int height = position * 12 / 10;
            Gui.drawRect(x + 1, y + 1, x + 21, y + 1 + height, 0xff356b55);
            String percent = Integer.toString(position * 10);
            font.drawString(percent, x + (22 - font.getStringWidth(percent)) / 2, y + 3, TEXT);
        }
        boolean mixed = false;
        for (int position : positions) {
            mixed |= position != positions[0];
        }
        font.drawString(text(mixed ? "mixed_rods" : "all_rods"), 10, 119, MUTED);
    }

    private void drawReadings(FontRenderer font, NBTTagCompound snapshot) {
        int temperature = snapshot.getInteger("temperature");
        int maximum = snapshot.getInteger("maximum");
        int boundedTemperature = Math.max(0, Math.min(temperature, maximum));
        int percent = maximum > 0 ? (int) (100L * boundedTemperature / maximum) : 0;
        int barWidth = maximum > 0 ? (int) (118L * boundedTemperature / maximum) : 0;
        int heatColor = percent >= 90 ? 0xef6b62 : percent >= 70 ? YELLOW : GREEN;
        String temperatureLabel = text("temperature") + ": ";
        font.drawString(temperatureLabel, 138, 30, MUTED);
        font.drawString(percent + "%", 138 + font.getStringWidth(temperatureLabel), 30, heatColor);
        Gui.drawRect(138, 42, 256, 46, 0xff26333c);
        Gui.drawRect(138, 42, 138 + barWidth, 46, 0xff000000 | heatColor);
        boolean decay = snapshot.getBoolean("decay");
        boolean holding = snapshot.getBoolean("holding") && !decay;
        font.drawString(text("hold_label"), 138, 71, holding ? GREEN : MUTED);
        Gui.drawRect(234, 73, 256, 77, holding ? 0xff356b55 : 0xff26333c);
        font.drawString(text("decay"), 138, 85, decay ? YELLOW : MUTED);
        Gui.drawRect(234, 87, 256, 91, decay ? 0xff8c7032 : 0xff26333c);
        font.drawString(text(snapshot.getBoolean("mox") ? "mox_on" : "mox_off"), 138, 100, TEXT);
        font.drawString(flow("input", snapshot.getDouble("input")), 138, 112, 0x69cbd4);
        font.drawString(flow("output", snapshot.getDouble("output")), 138, 124, 0x69cbd4);
    }

    private static String flow(String key, double amount) {
        return StatCollector.translateToLocalFormatted("impact.reactor." + key,
                String.format(Locale.ROOT, "%.1f", amount));
    }

    private static String text(String key) {
        return StatCollector.translateToLocal("impact.reactor." + key);
    }
}
