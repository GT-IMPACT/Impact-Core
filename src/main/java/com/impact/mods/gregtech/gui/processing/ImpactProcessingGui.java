package com.impact.mods.gregtech.gui.processing;

import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.InteractionSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.textfield.TextFieldWidget;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.GTGuis;
import gregtech.common.gui.modularui.multiblock.ProcessingGuiControls;
import gregtech.common.gui.modularui.util.MachineModularSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static gregtech.api.gui.GT_Container_ProcessingMachine.GUI_WIDTH;
import static gregtech.api.gui.GT_Container_ProcessingMachine.LOCK_SLOT;
import static gregtech.api.gui.GT_Container_ProcessingMachine.PLAYER_INVENTORY_Y;

/** Adds IMPACT actions to the native GT processing panel without replacing its layout or synchronization. */
public final class ImpactProcessingGui {

    private final GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine;
    private final ModularPanel panel;
    private final PanelSyncManager sync;

    public ImpactProcessingGui(
            GT_MetaTileEntity_ProcessingMultiBlockBase<?> machine,
            ModularPanel panel,
            PanelSyncManager sync
    ) {
        this.machine = machine;
        this.panel = panel;
        this.sync = sync;
    }

    public void addAction(
            String id,
            int column,
            int row,
            IDrawable icon,
            String tooltip,
            BiConsumer<EntityPlayer, Boolean> action
    ) {
        addAction(id, column, row, icon, () -> StatCollector.translateToLocal(tooltip), action);
    }

    public void addAction(
            String id,
            int column,
            int row,
            IDrawable icon,
            Supplier<String> tooltip,
            BiConsumer<EntityPlayer, Boolean> action
    ) {
        InteractionSyncHandler click = new InteractionSyncHandler().setOnMousePressed(mouse -> {
            if (!mouse.isClient() && (mouse.mouseButton == 0 || mouse.mouseButton == 1) && canInteract(sync)) {
                action.accept(sync.getPlayer(), mouse.shift);
                machine.markDirty();
            }
        });
        sync.syncValue(id, click);
        panel.child(control(id, column, row, icon, tooltip).syncHandler(click));
    }

    public void addInput(int index) {
        addInventorySlot(index, leftX() + 9, PLAYER_INVENTORY_Y + 57, true);
    }

    public void addOutputs(int first, int count) {
        int rightX = GUI_WIDTH - leftX() - 36;
        for (int offset = 0; offset < count; offset++) {
            addInventorySlot(first + offset, rightX + offset % 2 * 18,
                    PLAYER_INVENTORY_Y + 39 + offset / 2 * 18, false);
        }
    }

    private void addInventorySlot(int index, int x, int y, boolean input) {
        String group = "impact_inventory_" + index;
        sync.registerSlotGroup(group, input ? 0 : 2);
        panel.child(new ItemSlot()
                .name(group)
                .slot(new MachineModularSlot(machine.getInventoryHandler(), index, machine.getBaseMetaTileEntity())
                        .slotGroup(group).accessibility(input, true))
                .background(GTGuiTextures.SLOT_ITEM_STANDARD)
                .pos(x, y));
    }

    public IPanelHandler addNameEditor(Supplier<String> name, Consumer<String> apply) {
        IPanelHandler editor = sync.syncedPanel("impact_name_editor", true, (editorSync, handler) -> {
            String[] draft = { name.get() };
            StringSyncValue text = new StringSyncValue(() -> draft[0], value -> {
                if (value != null && value.length() <= 16 && (editorSync.isClient() || canInteract(editorSync))) {
                    draft[0] = value;
                }
            }).allowC2S();
            editorSync.syncValue("stationName", text);
            ModularPanel popup = GTGuis.createPopUpPanel("impact_name_editor").size(176, 72);
            InteractionSyncHandler accept = new InteractionSyncHandler().setOnMousePressed(mouse -> {
                if (!mouse.isClient() && mouse.mouseButton == 0 && canInteract(editorSync)
                        && !draft[0].trim().isEmpty() && draft[0].length() <= 16) {
                    apply.accept(draft[0]);
                    machine.markDirty();
                    handler.closePanel();
                }
            });
            editorSync.syncValue("acceptName", accept);
            return popup
                    .child(IKey.lang("impact.processing.station_name").asWidget().pos(8, 8))
                    .child(new TextFieldWidget().value(text).setMaxLength(16).pos(8, 25).size(160, 12))
                    .child(new ButtonWidget<>().name("impact_accept_name").syncHandler(accept)
                            .background(GTGuiTextures.SLOT_ITEM_STANDARD)
                            .overlay(GTGuiTextures.OVERLAY_BUTTON_CHECKMARK)
                            .tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.processing.apply")))
                            .pos(79, 46).size(18));
        });
        panel.child(control("rename_station", -1, 3, GTGuiTextures.OVERLAY_BUTTON_STRUCTURE_UPDATE,
                "impact.processing.station_name").onMousePressed(button -> {
                    if (button == 0) {
                        editor.openPanel();
                    }
                    return true;
                }));
        return editor;
    }

    private ButtonWidget<?> control(
            String id,
            int column,
            int row,
            IDrawable icon,
            String tooltip
    ) {
        return control(id, column, row, icon, () -> StatCollector.translateToLocal(tooltip));
    }

    private ButtonWidget<?> control(
            String id,
            int column,
            int row,
            IDrawable icon,
            Supplier<String> tooltip
    ) {
        return new ImpactControlButton()
                .name("impact_" + id)
                .background(GTGuiTextures.SLOT_ITEM_STANDARD)
                .overlay(icon)
                .tooltipBuilder(builder -> builder.addLine(tooltip.get()))
                .tooltipAutoUpdate(true)
                .pos(leftX() + (column < 0 ? 9 : column * 18), PLAYER_INVENTORY_Y - 1 + row * 18)
                .size(18);
    }

    private int leftX() {
        return ProcessingGuiControls.controlX(machine, LOCK_SLOT) - 1;
    }

    private boolean canInteract(PanelSyncManager manager) {
        EntityPlayer player = manager.getPlayer();
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        return player != null && player.openContainer == manager.getContainer()
                && tile != null && !tile.isDead() && tile.getMetaTileEntity() == machine
                && manager.getContainer().canInteractWith(player);
    }
}
