package com.impact.mods.gregtech.gui.lap;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.DynamicDrawable;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.InteractionSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.Widget;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.modularui.multiblock.MTEProcessingMultiBlockGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public final class LapPowerStationGui {

    private LapPowerStationGui() {
    }

    public static ModularPanel build(
            GTMTE_LapPowerStation machine,
            PosGuiData data,
            PanelSyncManager sync,
            UISettings settings
    ) {
        LapHistoryPeriod[] selected = { LapHistoryPeriod.MINUTE };
        GenericSyncValue<NBTTagCompound, ?> readings = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> sync.isClient() ? new NBTTagCompound() : readTelemetry(machine))
                .adapter(ByteBufAdapters.NBT)
                .build();
        GenericSyncValue<NBTTagCompound, ?> history = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> {
                    NBTTagCompound snapshot = new NBTTagCompound();
                    if (!sync.isClient()) {
                        LapHistoryPeriod period = selected[0];
                        snapshot.setString("period", period.key);
                        snapshot.setIntArray("charge", machine.getChargeHistory(period));
                        snapshot.setLong("averageInput", machine.getAverageInput(period));
                        snapshot.setLong("averageOutput", machine.getAverageOutput(period));
                    }
                    return snapshot;
                })
                .adapter(ByteBufAdapters.NBT)
                .build();
        sync.syncValue("lapReadings", readings);
        sync.syncValue("lapHistory", history);
        ModularPanel panel = new MTEProcessingMultiBlockGui(machine).build(data, sync, settings,
                state -> new LapPowerStationDisplay(machine, state, readings, history));
        for (LapHistoryPeriod period : LapHistoryPeriod.values()) {
            InteractionSyncHandler click = new InteractionSyncHandler().setOnMousePressed(mouse -> {
                if (!mouse.isClient() && mouse.mouseButton == 0 && canInteract(machine, sync)) {
                    selected[0] = period;
                }
            });
            sync.syncValue("lap_period_" + period.key, click);
            panel.child(new ButtonWidget<>()
                    .name("impact_lap_period_" + period.key)
                    .syncHandler(click)
                    .background(new DynamicDrawable(() -> period.key.equals(history.getValue().getString("period"))
                            ? GTGuiTextures.BUTTON_STANDARD_PRESSED : GTGuiTextures.BUTTON_STANDARD))
                    .overlay(IKey.lang("impact.lap.period." + period.key))
                    .tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.lap.period_tooltip." + period.key)))
                    .pos(104 + period.ordinal() * 32, 108)
                    .size(30, 11)
                    .setEnabledIf(widget -> showHistory(readings.getValue())));
        }
        addInstantTooltip(panel, readings, true);
        addInstantTooltip(panel, readings, false);
        return panel;
    }

    private static boolean showHistory(NBTTagCompound readings) {
        return readings.getBoolean("formed") && readings.getLong("capacity") > 0;
    }

    private static void addInstantTooltip(
            ModularPanel panel,
            GenericSyncValue<NBTTagCompound, ?> readings,
            boolean input
    ) {
        String key = input ? "input" : "output";
        panel.child(new Widget<>()
                .name("lap_instant_" + key)
                .pos(10, input ? 121 : 133)
                .size(248, 11)
                .invisible()
                .setEnabledIf(widget -> showHistory(readings.getValue()))
                .tooltipBuilder(tooltip -> tooltip.addLine(StatCollector.translateToLocalFormatted(
                        "impact.lap.instant_" + key,
                        GT_Utility.formatNumbers(readings.getValue().getLong(key))
                )))
                .tooltipAutoUpdate(true));
    }

    private static boolean canInteract(GTMTE_LapPowerStation machine, PanelSyncManager sync) {
        EntityPlayer player = sync.getPlayer();
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        return player != null && player.openContainer == sync.getContainer()
                && tile != null && !tile.isDead() && tile.getMetaTileEntity() == machine
                && sync.getContainer().canInteractWith(player);
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
