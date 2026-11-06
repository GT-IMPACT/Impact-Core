package com.impact.mods.gregtech.gui.nuclear;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.IGuiAction;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.widgets.SliderWidget;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.gui.modularui.multiblock.MTEProcessingMultiBlockGui;
import net.minecraft.nbt.NBTTagCompound;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public final class NuclearReactorGui {

    private NuclearReactorGui() {
    }

    public static ModularPanel build(
            GTMTE_NuclearReactorBase<?> machine,
            PosGuiData data,
            PanelSyncManager sync,
            UISettings settings
    ) {
        GenericSyncValue<NBTTagCompound, ?> reactor = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> snapshot(machine, sync))
                .adapter(ByteBufAdapters.NBT)
                .build();
        sync.syncValue("reactorState", reactor);
        ModularPanel panel = new MTEProcessingMultiBlockGui(machine).build(data, sync, settings,
                state -> new NuclearReactorDisplay(machine, state, reactor));
        panel.child(RodPositionSlider.create(machine, sync, () -> {
            int[] positions = machine.getRodPosition();
            return positions.length == 0 ? 0 : positions[0];
        }, machine::setRodPosition)
                .pos(10, 130)
                .size(112, 12)
                .setEnabledIf(widget -> reactor.getValue().getIntArray("rods").length > 0));
        addModes(machine, panel, sync, reactor);
        return panel;
    }

    private static void addModes(
            GTMTE_NuclearReactorBase<?> machine,
            ModularPanel panel,
            PanelSyncManager sync,
            GenericSyncValue<NBTTagCompound, ?> reactor
    ) {
        panel.child(modeSwitch(
                machine,
                sync,
                "hold_temperature",
                () -> machine.stopTemp && !machine.isFastDecay,
                enabled -> {
                    if (!machine.isFastDecay) {
                        machine.stopTemp = enabled;
                    }
                }
        )
                .pos(234, 70)
                .setEnabledIf(widget -> controlsVisible(reactor)));
        panel.child(modeSwitch(
                machine,
                sync,
                "fast_decay",
                () -> machine.isFastDecay,
                enabled -> {
                    if (enabled != machine.isFastDecay) {
                        machine.toggleFastDecay(sync.getPlayer());
                    }
                }
        )
                .pos(234, 84)
                .setEnabledIf(widget -> controlsVisible(reactor))
                .tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.reactor.decay_requirement"))));
    }

    private static boolean controlsVisible(GenericSyncValue<NBTTagCompound, ?> reactor) {
        NBTTagCompound state = reactor.getValue();
        return state.getBoolean("ready") && state.getInteger("errors") == 0;
    }

    private static SliderWidget modeSwitch(
            GTMTE_NuclearReactorBase<?> machine,
            PanelSyncManager sync,
            String name,
            BooleanSupplier getter,
            Consumer<Boolean> setter
    ) {
        DoubleSyncValue value = new DoubleSyncValue(() -> getter.getAsBoolean() ? 1 : 0, requested -> {
            if (!sync.isClient() && RodPositionSlider.canInteract(machine, sync) && (requested == 0 || requested == 1)) {
                setter.accept(requested == 1);
                machine.markDirty();
            }
        }).allowC2S();
        sync.syncValue(name, value);
        return new ModeSlider()
                .name("reactor_" + name)
                .bounds(0, 1)
                .stopper(1)
                .value(value)
                .sliderTexture(GTGuiTextures.BUTTON_STANDARD)
                .sliderSize(8, 10)
                .size(22, 10);
    }

    private static NBTTagCompound snapshot(GTMTE_NuclearReactorBase<?> machine, PanelSyncManager sync) {
        NBTTagCompound state = new NBTTagCompound();
        if (sync.isClient()) {
            return state;
        }
        state.setBoolean("ready", true);
        state.setIntArray("rods", machine.getRodPosition());
        state.setInteger("temperature", (int) machine.mCurrentTemp);
        state.setInteger("maximum", machine.maxTemperature());
        state.setBoolean("holding", machine.stopTemp);
        state.setBoolean("decay", machine.isFastDecay);
        state.setBoolean("mox", machine.isMoxFuel);
        state.setDouble("input", Math.ceil(machine.mCurrentInput) / 2.0);
        state.setDouble("output", Math.ceil(machine.mCurrentOutput) / 2.0);
        state.setInteger("errors", machine.getBaseMetaTileEntity().getErrorDisplayID());
        return state;
    }

    private static final class ModeSlider extends SliderWidget {

        private int pendingPosition = -1;

        private ModeSlider() {
            listenGuiAction((IGuiAction.MouseReleased) button -> {
                if (pendingPosition < 0) {
                    return false;
                }
                int position = pendingPosition;
                pendingPosition = -1;
                super.setValue(position, position != getSliderValue());
                return true;
            });
        }

        @Override
        public void setValue(double value, boolean setSource) {
            if (setSource) {
                // Preview the position while dragging and send the request only on release.
                pendingPosition = value < 0.5 ? 0 : 1;
                super.setValue(pendingPosition, false);
            } else {
                super.setValue(value, false);
            }
        }
    }
}
