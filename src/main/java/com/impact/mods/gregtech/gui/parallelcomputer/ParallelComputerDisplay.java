package com.impact.mods.gregtech.gui.parallelcomputer;

import com.cleanroommc.modularui.api.UpOrDown;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import gregtech.common.gui.modularui.multiblock.ProcessingGuiState;
import gregtech.common.gui.modularui.multiblock.ProcessingMachineDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.text.NumberFormat;
import java.util.List;

final class ParallelComputerDisplay extends ProcessingMachineDisplay {

    private static final int TEXT = 0xe4eaf0;
    private static final int MUTED = 0x8999aa;
    private static final int[] COLORS = { MUTED, 0xe8bf63, 0x73ce8d };
    private final GTMTE_ParallelComputer machine;
    private final GenericSyncValue<NBTTagCompound, ?> readings;
    private final NumberFormat format = NumberFormat.getIntegerInstance();

    ParallelComputerDisplay(
            GTMTE_ParallelComputer machine,
            ProcessingGuiState processing,
            GenericSyncValue<NBTTagCompound, ?> readings
    ) {
        super(machine, processing);
        this.machine = machine;
        this.readings = readings;
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> theme) {
        NBTTagCompound snapshot = readings.getValue();
        if (!ParallelComputerGui.showRacks(snapshot)) {
            super.draw(context, theme);
            return;
        }
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        font.drawString(machine.getLocalName(), 10, 8, MUTED);
        List<String> status = statusLines();
        if (!status.isEmpty()) {
            font.drawString(status.get(status.size() - 1), 10, 18, TEXT);
        }
        int capacity = Math.max(0, snapshot.getInteger("capacity"));
        int free = Math.max(0, Math.min(capacity, snapshot.getInteger("free")));
        font.drawString(text("capacity", format.format(capacity)), 10, 33, TEXT);
        font.drawString(text("free", format.format(free)), 10, 45, 0x69cbd4);
        Gui.drawRect(10, 57, 258, 61, 0xff26333c);
        int width = capacity > 0 ? (int) (248L * free / capacity) : 0;
        Gui.drawRect(258 - width, 57, 258, 61, 0xff69cbd4);

        int[] capacities = snapshot.getIntArray("rackCapacity");
        int[] states = snapshot.getIntArray("rackState");
        int positions = Math.min(ParallelComputerGui.MAX_RACKS, Math.min(capacities.length, states.length));
        int count = 0;
        for (int index = 0; index < positions; index++) {
            if (capacities[index] >= 0) {
                count++;
            }
        }
        font.drawString(text("racks", count), 10, 67, MUTED);
        if (count == 0) {
            font.drawString(text("no_racks"), 10, 91, MUTED);
        }
        for (int index = 0; index < positions; index++) {
            if (capacities[index] >= 0) {
                drawRack(font, index, Math.max(0, Math.min(2, states[index])));
            }
        }
        int x = 10;
        for (int state = 2; state >= 0; state--) {
            String label = StatCollector.translateToLocal(ParallelComputerGui.stateKey(state));
            Gui.drawRect(x, 136, x + 5, 141, 0xff000000 | COLORS[state]);
            font.drawString(label, x + 8, 134, MUTED);
            x += font.getStringWidth(label) + 20;
        }
        String consumption = text("consumption", format.format(snapshot.getLong("consumption")));
        font.drawString(consumption, 258 - font.getStringWidth(consumption), 134, 0xe8bf63);
    }

    @Override
    public boolean onMouseScroll(UpOrDown direction, int amount) {
        return !ParallelComputerGui.showRacks(readings.getValue()) && super.onMouseScroll(direction, amount);
    }

    private static void drawRack(FontRenderer font, int index, int state) {
        int x = ParallelComputerGui.RACK_LEFT + index % ParallelComputerGui.COLUMNS * ParallelComputerGui.RACK_PITCH_X;
        int y = ParallelComputerGui.RACK_TOP + index / ParallelComputerGui.COLUMNS * ParallelComputerGui.RACK_PITCH_Y;
        int right = x + ParallelComputerGui.RACK_WIDTH;
        int bottom = y + ParallelComputerGui.RACK_HEIGHT;
        Gui.drawRect(x, y, right, bottom, 0xff000000 | COLORS[state]);
        Gui.drawRect(x + 1, y + 1, right - 1, bottom - 1, state == 2 ? 0xff254638 : 0xff131d23);
        String number = Integer.toString(index + 1);
        int textY = y + (ParallelComputerGui.RACK_HEIGHT - font.FONT_HEIGHT) / 2 + 1;
        font.drawString(number, x + (ParallelComputerGui.RACK_WIDTH - font.getStringWidth(number)) / 2, textY, TEXT);
    }

    private static String text(String key, Object... arguments) {
        return StatCollector.translateToLocalFormatted("impact.computer." + key, arguments);
    }
}
