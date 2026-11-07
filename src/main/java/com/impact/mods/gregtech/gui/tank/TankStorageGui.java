package com.impact.mods.gregtech.gui.tank;

import com.cleanroommc.modularui.drawable.DynamicDrawable;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.impact.mods.gregtech.gui.processing.ImpactProcessingGui;
import com.impact.util.fluid.IMultiFluidTank;
import com.impact.util.fluid.MultiFluidHandler;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.gui.modularui.multiblock.MTEProcessingMultiBlockGui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

public final class TankStorageGui {

    private TankStorageGui() {
    }

    public static <T extends GT_MetaTileEntity_ProcessingMultiBlockBase<?> & IMultiFluidTank> ModularPanel build(
            T machine,
            PosGuiData data,
            PanelSyncManager sync,
            UISettings settings,
            int slots
    ) {
        GenericSyncValue<NBTTagCompound, ?> storage = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> sync.isClient() ? new NBTTagCompound() : snapshot(machine, slots))
                .adapter(ByteBufAdapters.NBT)
                .build();
        sync.syncValue("tankStorage", storage);
        Readings readings = new Readings(storage, slots);
        ModularPanel panel = new MTEProcessingMultiBlockGui(machine).build(data, sync, settings,
                state -> new TankStorageDisplay(machine, state, readings, slots));
        new ImpactProcessingGui(machine, panel, sync).addAction(
            "tank_auto_void",
            0,
            1,
            new DynamicDrawable(() -> readings.voidExcess()
                ? GTGuiTextures.OVERLAY_BUTTON_TANK_VOID_EXCESS : GTGuiTextures.OVERLAY_BUTTON_VOID_EXCESS_NONE),
            () -> StatCollector.translateToLocal(readings.voidExcess()
                ? "impact.tank.auto_void.enabled" : "impact.tank.auto_void.disabled")
                + "\n" + StatCollector.translateToLocal("impact.tank.auto_void.tooltip"),
            (player, shift) -> machine.setVoidExcessEnabled(!machine.isVoidExcessEnabled())
        );

        if (slots == 1) {
            panel.child(gauge(readings, 0, false).pos(14, 30).size(102, 108));
        } else {
            for (int index = 0; index < slots; index++) {
                int x = 10 + index % 5 * 50;
                int y = 31 + index / 5 * 22;
                panel.child(gauge(readings, index, true).pos(x, y).size(48, 20));
            }
        }
        return panel;
    }

    private static TankFluidGauge gauge(Readings readings, int index, boolean compact) {
        return new TankFluidGauge(readings, index, compact)
                .name("tank_fluid_" + index)
                .setEnabledIf(widget -> readings.capacity() > 0);
    }

    private static <T extends GT_MetaTileEntity_ProcessingMultiBlockBase<?> & IMultiFluidTank> NBTTagCompound snapshot(
            T machine,
            int slots
    ) {
        NBTTagCompound snapshot = new NBTTagCompound();
        snapshot.setBoolean("voidExcess", machine.isVoidExcessEnabled());
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        MultiFluidHandler handler = machine.getMultiFluidHandler();
        if (tile == null || tile.isDead() || tile.getMetaTileEntity() != machine || handler == null) {
            return snapshot;
        }
        snapshot.setInteger("capacity", handler.getCapacity());

        // The monitor can inspect contents while the tank is switched off and its storage is locked.
        NBTTagList fluids = new NBTTagList();
        for (FluidStack fluid : handler.fluids) {
            if (fluid != null && fluid.amount >= 0) {
                fluids.appendTag(fluid.writeToNBT(new NBTTagCompound()));
                if (fluids.tagCount() == slots) {
                    break;
                }
            }
        }
        snapshot.setTag("fluids", fluids);
        return snapshot;
    }

    static final class Readings {

        private final GenericSyncValue<NBTTagCompound, ?> sync;
        private final FluidStack[] fluids;
        private NBTTagCompound lastSnapshot;
        private int capacity;
        private int count;
        private boolean voidExcess;

        Readings(GenericSyncValue<NBTTagCompound, ?> sync, int slots) {
            this.sync = sync;
            fluids = new FluidStack[slots];
        }

        int capacity() {
            refresh();
            return capacity;
        }

        int count() {
            refresh();
            return count;
        }

        boolean voidExcess() {
            refresh();
            return voidExcess;
        }

        FluidStack fluid(int index) {
            refresh();
            return fluids[index];
        }

        private void refresh() {
            NBTTagCompound snapshot = sync.getValue();
            if (snapshot == lastSnapshot) {
                return;
            }
            lastSnapshot = snapshot;
            voidExcess = snapshot.getBoolean("voidExcess");
            capacity = snapshot.getInteger("capacity");
            NBTTagList entries = snapshot.getTagList("fluids", 10);
            count = Math.min(entries.tagCount(), fluids.length);
            for (int index = 0; index < fluids.length; index++) {
                fluids[index] = index < count
                        ? FluidStack.loadFluidStackFromNBT(entries.getCompoundTagAt(index))
                        : null;
            }
        }
    }
}
