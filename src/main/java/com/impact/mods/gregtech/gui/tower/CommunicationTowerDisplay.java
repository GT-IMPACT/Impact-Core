package com.impact.mods.gregtech.gui.tower;

import com.cleanroommc.modularui.api.UpOrDown;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import gregtech.common.gui.modularui.multiblock.ProcessingGuiState;
import gregtech.common.gui.modularui.multiblock.ProcessingMachineDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

import java.util.List;
import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;

final class CommunicationTowerDisplay extends ProcessingMachineDisplay {

    private static final int TEXT = 0xe4eaf0;
    static final int MUTED = 0x8999aa;
    static final int CONNECTED = 0x73ce8d;
    static final int DISCONNECTED = 0xef6b62;
    private final GTMTE_TowerCommunication machine;
    private final GenericSyncValue<NBTTagCompound, ?> readings;

    CommunicationTowerDisplay(
            GTMTE_TowerCommunication machine,
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
        if (!CommunicationTowerGui.showConnections(snapshot)) {
            super.draw(context, theme);
            return;
        }
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        font.drawString(machine.getLocalName(), 10, 8, MUTED);
        List<String> status = statusLines();
        if (!status.isEmpty()) {
            font.drawString(status.get(status.size() - 1), 10, 18, TEXT);
        }
        boolean uplink = snapshot.getBoolean("uplink");
        font.drawString(text(uplink ? "uplink_on" : "uplink_off"), 10, 33, uplink ? CONNECTED : DISCONNECTED);
        font.drawString(text("receivers"), 10, 45, MUTED);
        int[] receivers = snapshot.getIntArray("receivers");
        for (int index = 0; index < Math.min(4, receivers.length); index++) {
            int color = receivers[index] < 0 ? MUTED : receivers[index] == 1 ? CONNECTED : DISCONNECTED;
            drawCell(
                    font,
                    202 + index * 14,
                    43,
                    12,
                    11,
                    Integer.toString(index + 1),
                    color
            );
        }
        if (!uplink) {
            font.drawString(StatCollector.translateToLocal(snapshot.getString(NetworkDiagnostics.STATUS_TAG)),
                    10, 56, MUTED);
        }
        font.drawString(text("connections", snapshot.getInteger("connected"), snapshot.getInteger("total")),
                10, 67, TEXT);
        NBTTagList nodes = snapshot.getTagList("nodes", 10);
        if (nodes.tagCount() == 0) {
            font.drawString(text("no_connections"), 10, 91, MUTED);
        }
        for (int index = 0; index < Math.min(CommunicationTowerGui.PAGE_SIZE, nodes.tagCount()); index++) {
            boolean connected = nodes.getCompoundTagAt(index).getBoolean("connected");
            int x = CommunicationTowerGui.LEFT + index % CommunicationTowerGui.COLUMNS * CommunicationTowerGui.PITCH_X;
            int y = CommunicationTowerGui.TOP + index / CommunicationTowerGui.COLUMNS * CommunicationTowerGui.PITCH_Y;
            String number = Integer.toString(snapshot.getInteger("page") * CommunicationTowerGui.PAGE_SIZE + index + 1);
            drawCell(
                    font,
                    x,
                    y,
                    CommunicationTowerGui.WIDTH,
                    CommunicationTowerGui.HEIGHT,
                    number,
                    connected ? CONNECTED : DISCONNECTED
            );
        }
        int x = 10;
        for (boolean connected : new boolean[] { true, false }) {
            String label = StatCollector.translateToLocal(CommunicationTowerGui.stateKey(connected));
            Gui.drawRect(
                    x,
                    136,
                    x + 5,
                    141,
                    0xff000000 | (connected ? CONNECTED : DISCONNECTED)
            );
            font.drawString(label, x + 8, 134, MUTED);
            x += font.getStringWidth(label) + 20;
        }
        String page = (snapshot.getInteger("page") + 1) + "/" + snapshot.getInteger("pages");
        font.drawString(page, 228 - font.getStringWidth(page) / 2, 134, MUTED);
    }

    @Override
    public boolean onMouseScroll(UpOrDown direction, int amount) {
        return !CommunicationTowerGui.showConnections(readings.getValue()) && super.onMouseScroll(direction, amount);
    }

    private static void drawCell(
            FontRenderer font,
            int x,
            int y,
            int width,
            int height,
            String label,
            int color
    ) {
        Gui.drawRect(
                x,
                y,
                x + width,
                y + height,
                0xff000000 | color
        );
        Gui.drawRect(
                x + 1,
                y + 1,
                x + width - 1,
                y + height - 1,
                0xff131d23
        );
        // FONT_HEIGHT includes spacing below the glyphs; compensate for it when centering digits.
        int textY = y + (height - font.FONT_HEIGHT) / 2 + 1;
        font.drawString(label, x + (width - font.getStringWidth(label)) / 2, textY, TEXT);
    }

    private static String text(String key, Object... arguments) {
        return StatCollector.translateToLocalFormatted("impact.tower." + key, arguments);
    }
}
