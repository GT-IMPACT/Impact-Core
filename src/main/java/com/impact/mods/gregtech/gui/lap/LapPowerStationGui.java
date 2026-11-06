package com.impact.mods.gregtech.gui.lap;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.gui.modularui.multiblock.MTEProcessingMultiBlockGui;
import net.minecraft.nbt.NBTTagCompound;

public final class LapPowerStationGui {

    private LapPowerStationGui() {
    }

    public static ModularPanel build(
            GTMTE_LapPowerStation machine,
            PosGuiData data,
            PanelSyncManager sync,
            UISettings settings
    ) {
        GenericSyncValue<NBTTagCompound, ?> readings = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> sync.isClient() ? new NBTTagCompound() : readTelemetry(machine))
                .adapter(ByteBufAdapters.NBT)
                .build();
        GenericSyncValue<NBTTagCompound, ?> history = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> {
                    NBTTagCompound snapshot = new NBTTagCompound();
                    if (!sync.isClient()) {
                        snapshot.setIntArray("charge", machine.getChargeHistory());
                    }
                    return snapshot;
                })
                .adapter(ByteBufAdapters.NBT)
                .build();
        sync.syncValue("lapReadings", readings);
        sync.syncValue("lapHistory", history);
        return new MTEProcessingMultiBlockGui(machine).build(data, sync, settings,
                state -> new LapPowerStationDisplay(machine, state, readings, history));
    }

    private static NBTTagCompound readTelemetry(GTMTE_LapPowerStation machine) {
        NBTTagCompound snapshot = new NBTTagCompound();
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        if (tile == null || tile.isDead() || tile.getMetaTileEntity() != machine) {
            return snapshot;
        }
        snapshot.setBoolean("formed", machine.mMachine);
        snapshot.setBoolean("enabled", tile.isAllowedToWork());
        snapshot.setLong("stored", machine.getStored());
        snapshot.setLong("capacity", machine.getMaxCapacity());
        snapshot.setLong("input", machine.intputLastTick);
        snapshot.setLong("output", machine.outputLastTick);
        return snapshot;
    }
}
