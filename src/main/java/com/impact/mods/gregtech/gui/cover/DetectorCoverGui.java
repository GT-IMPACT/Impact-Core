package com.impact.mods.gregtech.gui.cover;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.LongSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ToggleButton;
import com.cleanroommc.modularui.widgets.layout.Flow;
import com.impact.mods.gregtech.tileentities.covers.DetectorCover;
import gregtech.api.modularui2.CoverGuiData;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.gui.modularui.cover.base.CoverBaseGui;

public class DetectorCoverGui extends CoverBaseGui<DetectorCover> {

    public DetectorCoverGui(DetectorCover cover) {
        super(cover);
    }

    @Override
    protected String getGuiId() {
        return "impact.cover." + (cover.isFluid() ? "fluid" : "energy") + (cover.isRange() ? "_range" : "_bound");
    }

    @Override
    public void addUIWidgets(PanelSyncManager syncManager, Flow column, CoverGuiData data) {
        LongSyncValue capacity = new LongSyncValue(cover::getCapacity);
        syncManager.syncValue("capacity", capacity);
        Flow fields = makeRowLayout().paddingRight(TICK_RATE_BUTTON_SIZE);
        if (cover.isRange()) {
            addRangeBound(syncManager, fields, capacity, true);
            addRangeBound(syncManager, fields, capacity, false);
        } else {
            IntSyncValue setting = new IntSyncValue(this::getBoundLimit, this::setBoundLimit).allowC2S();
            syncManager.syncValue("setting", setting);
            long minimum = cover.isFluid() ? Integer.MIN_VALUE : 0;
            fields.child(makeNamedColumn(IKey.str(cover.isFluid() ? "L (Limit)" : "% (Limit)"))
                    .child(makeNumberField(100).value(setting).numbersLong(() -> minimum,
                            () -> cover.isFluid() ? Math.min(Integer.MAX_VALUE, capacity.getLongValue()) : 100L)));
            if (cover.isFluid()) {
                LongSyncValue percent = new LongSyncValue(
                    () -> getBoundPercent(capacity.getLongValue()),
                    value -> setBoundPercent(value, capacity.getLongValue())
                ).allowC2S();
                syncManager.syncValue("percent", percent);
                fields.child(makeNamedColumn(IKey.str("% (Limit)"))
                        .child(makeNumberField(100).value(percent).numbersLong(Integer.MIN_VALUE, 100)));
            } else {
                BooleanSyncValue inverted = new BooleanSyncValue(cover::isInverted, cover::setInverted).allowC2S();
                syncManager.syncValue("inverted", inverted);
                fields.child(positionRow(Flow
                        .row().child(new ToggleButton().value(inverted)
                                .overlay(GTGuiTextures.OVERLAY_BUTTON_REDSTONE_ON).size(16))
                        .child(IKey.str("Inverted").asWidget())));
            }
            fields.child(
                    IKey.dynamic(() -> cover.getSetting() > 0 ? "Signal: stored >= limit" : "Signal: stored <= |limit|")
                            .asWidget());
        }
        column.child(fields);
    }

    private int getBoundLimit() {
        return cover.isFluid() ? cover.getSetting() : (int) Math.min(100L, Math.abs((long) cover.getSetting()));
    }

    private void setBoundLimit(int limit) {
        if (cover.isFluid()) {
            cover.setSetting(limit);
        } else {
            int percent = Math.max(0, Math.min(100, limit));
            cover.setSetting(cover.isInverted() ? -percent : percent);
        }
    }

    private long getBoundPercent(long capacity) {
        return (long) ((double) cover.getSetting() / Math.max(1L, capacity) * 100);
    }

    private void setBoundPercent(long percent, long capacity) {
        double threshold = (double) percent / 100 * Math.max(1L, capacity);
        cover.setSetting((int) Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, threshold)));
    }

    private void addRangeBound(PanelSyncManager syncManager, Flow fields, LongSyncValue capacity, boolean start) {
        IntSyncValue percent = new IntSyncValue(start ? cover::getStartPercent : cover::getEndPercent,
                start ? cover::setStartPercent : cover::setEndPercent).allowC2S();
        LongSyncValue amount = new LongSyncValue(
                () -> (long) ((double) percent.getIntValue() / 100 * capacity.getLongValue()),
                value -> percent.setIntValue((int) ((double) value / Math.max(1L, capacity.getLongValue()) * 100)))
                        .allowC2S();
        String name = start ? "start" : "end";
        syncManager.syncValue(name + "_percent", percent);
        syncManager.syncValue(name + "_amount", amount);
        fields.child(
                makeNamedColumn(IKey.str((start ? "Start bound (" : "End bound (") + (cover.isFluid() ? "L)" : "EU)")))
                        .child(makeNumberField(120).value(amount).numbersLong(() -> 0L, capacity::getLongValue)));
        fields.child(makeNamedColumn(IKey.str(start ? "Start bound (%)" : "End bound (%)"))
                .child(makeNumberField(80).value(percent).numbersLong(0, 100)));
    }
}
