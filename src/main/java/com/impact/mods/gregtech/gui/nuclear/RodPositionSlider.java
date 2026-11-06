package com.impact.mods.gregtech.gui.nuclear;

import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SliderWidget;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.modularui2.GTGuiTextures;
import net.minecraft.entity.player.EntityPlayer;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/** The same ten-step control is used by the reactor and its individual rod hatches. */
public final class RodPositionSlider {

    private RodPositionSlider() {
    }

    public static SliderWidget create(
            MetaTileEntity machine,
            PanelSyncManager sync,
            IntSupplier position,
            IntConsumer setPosition
    ) {
        DoubleSyncValue value = new DoubleSyncValue(position::getAsInt, requested -> {
            if (!sync.isClient() && canInteract(machine, sync) && Double.isFinite(requested)
                    && requested >= 0 && requested <= 10 && requested == Math.rint(requested)) {
                setPosition.accept((int) requested);
                machine.markDirty();
            }
        }).allowC2S();
        sync.syncValue("rodPosition", value);
        return new SliderWidget()
                .name("rod_position_slider")
                .bounds(0, 10)
                .stopper(1)
                .scrollValues(1, 1, 1, 1)
                .sliderSize(8, 12)
                .sliderTexture(GTGuiTextures.BUTTON_STANDARD)
                .value(value);
    }

    static boolean canInteract(MetaTileEntity machine, PanelSyncManager sync) {
        EntityPlayer player = sync.getPlayer();
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        return player != null && player.openContainer == sync.getContainer()
                && tile != null && !tile.isDead() && tile.getMetaTileEntity() == machine
                && sync.getContainer().canInteractWith(player);
    }
}
