package com.impact.mods.gregtech.gui.parallelcomputer;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.Widget;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ComputerRack;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.common.gui.modularui.multiblock.MTEProcessingMultiBlockGui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.Arrays;

public final class ParallelComputerGui {

    static final int MAX_RACKS = 32;
    static final int COLUMNS = 16;
    static final int RACK_LEFT = 10;
    static final int RACK_TOP = 80;
    static final int RACK_PITCH_X = 15;
    static final int RACK_PITCH_Y = 12;
    static final int RACK_WIDTH = 14;
    static final int RACK_HEIGHT = 11;

    private ParallelComputerGui() {
    }

    public static ModularPanel build(
            GTMTE_ParallelComputer machine,
            PosGuiData data,
            PanelSyncManager sync,
            UISettings settings
    ) {
        GenericSyncValue<NBTTagCompound, ?> readings = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> sync.isClient() ? new NBTTagCompound() : snapshot(machine))
                .adapter(ByteBufAdapters.NBT)
                .build();
        sync.syncValue("parallelComputerState", readings);
        ModularPanel panel = new MTEProcessingMultiBlockGui(machine).build(data, sync, settings,
                state -> new ParallelComputerDisplay(machine, state, readings));
        for (int index = 0; index < MAX_RACKS; index++) {
            final int rack = index;
            panel.child(new Widget<>()
                    .name("parallel_rack_" + index)
                    .pos(RACK_LEFT + index % COLUMNS * RACK_PITCH_X, RACK_TOP + index / COLUMNS * RACK_PITCH_Y)
                    .size(RACK_WIDTH, RACK_HEIGHT)
                    .invisible()
                    .setEnabledIf(widget -> showRacks(readings.getValue())
                            && rack < readings.getValue().getIntArray("rackCapacity").length
                            && readings.getValue().getIntArray("rackCapacity")[rack] >= 0)
                    .tooltipBuilder(tooltip -> {
                        NBTTagCompound snapshot = readings.getValue();
                        int[] capacity = snapshot.getIntArray("rackCapacity");
                        int[] states = snapshot.getIntArray("rackState");
                        if (rack < capacity.length && rack < states.length) {
                            tooltip.addLine(StatCollector.translateToLocalFormatted("impact.computer.rack", rack + 1));
                            tooltip.addLine(
                                    StatCollector.translateToLocalFormatted("impact.computer.capacity", capacity[rack])
                            );
                            tooltip.addLine(StatCollector.translateToLocal(stateKey(states[rack])));
                        }
                    })
                    .tooltipAutoUpdate(true));
        }
        return panel;
    }

    static boolean showRacks(NBTTagCompound snapshot) {
        return snapshot.getBoolean("formed") && snapshot.getInteger("errors") == 0;
    }

    static String stateKey(int state) {
        return "impact.computer." + (state == 2 ? "active" : state == 1 ? "idle" : "empty");
    }

    private static NBTTagCompound snapshot(GTMTE_ParallelComputer machine) {
        NBTTagCompound snapshot = new NBTTagCompound();
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        if (tile == null || tile.isDead() || tile.getMetaTileEntity() != machine) {
            return snapshot;
        }
        snapshot.setBoolean("formed", machine.mMachine);
        snapshot.setInteger("errors", tile.getErrorDisplayID());
        snapshot.setInteger("capacity", machine.getMaxCapacityPP());
        snapshot.setInteger("free", machine.getCurrentCapacityPP());
        long runningEUt = machine.getRunningEUt();
        long consumption = tile.isActive() && runningEUt < 0
                ? ProcessingLogic.efficiencyAdjustedEUt(-runningEUt, machine.mEfficiency) : 0;
        snapshot.setLong("consumption", consumption);

        int[] capacity = new int[MAX_RACKS];
        int[] states = new int[MAX_RACKS];
        Arrays.fill(capacity, -1);
        Arrays.fill(states, -1);
        int count = 0;
        int[] local = new int[3];
        for (GTMTE_ComputerRack rack : machine.sComputerRack) {
            IGregTechTileEntity rackTile = rack.getBaseMetaTileEntity();
            if (rackTile == null || rackTile.isDead() || rackTile.getMetaTileEntity() != rack) {
                continue;
            }
            machine.getExtendedFacing().getOffsetABC(new int[] {
                    rackTile.getXCoord() - tile.getXCoord(), rackTile.getYCoord() - tile.getYCoord(),
                    rackTile.getZCoord() - tile.getZCoord()
            }, local);
            if (local[0] != 1 || local[1] < -1 || local[1] > 0 || local[2] < 1 || local[2] > MAX_RACKS / 2) {
                continue;
            }
            // Top row first, front to back; keep gaps instead of shifting later racks.
            int index = (local[1] + 1) * (MAX_RACKS / 2) + local[2] - 1;
            capacity[index] = Math.max(0, rack.mCapacityP);
            states[index] = capacity[index] == 0 ? 0 : rackTile.isActive() ? 2 : 1;
            count = Math.max(count, index + 1);
        }
        snapshot.setIntArray("rackCapacity", Arrays.copyOf(capacity, count));
        snapshot.setIntArray("rackState", Arrays.copyOf(states, count));
        return snapshot;
    }
}
