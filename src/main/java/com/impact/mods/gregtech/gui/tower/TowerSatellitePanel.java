package com.impact.mods.gregtech.gui.tower;

import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.drawable.DynamicDrawable;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.InteractionSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.ParentWidget;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.ScrollingTextWidget;
import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.position.IPosition;
import com.impact.addon.gt.api.satellite.SatelliteDirectory;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import cpw.mods.fml.common.network.ByteBufUtils;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.GTWidgetThemes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

import static gregtech.api.gui.GT_Container_ProcessingMachine.GUI_WIDTH;

final class TowerSatellitePanel {

    private static final int PAGE_SIZE = 4;
    private static final int CARD_WIDTH = 108;
    private static final int CARD_HEIGHT = 33;
    private static final int WINDOW_HEIGHT = 110;
    private static final String PANEL = "impact_tower_satellites";
    private static final String TITLE = "impact.tower.satellites";
    private static final String SELECT_SATELLITE = "satelliteChoice";
    private static final int MUTED = 0x404040;
    private static final int CARD_TEXT = 0xffffff;

    private TowerSatellitePanel() {
    }

    static void addTo(GTMTE_TowerCommunication machine, ModularPanel tower, PanelSyncManager sync) {
        IPanelHandler picker = sync.syncedPanel(PANEL, true, (panelSync, handler) -> build(machine, tower, panelSync));
        tower.child(new ButtonWidget<>().name("impact_tower_choose_satellite")
                .background(GTGuiTextures.BUTTON_STANDARD)
                .overlay(IKey.lang(TITLE))
                .pos(GUI_WIDTH - 76, 4).size(66, 13)
                .onMousePressed(button -> {
                    if (button != 0 && button != 1) {
                        return false;
                    }
                    if (picker.isPanelOpen()) {
                        picker.closePanel();
                    } else {
                        picker.openPanel();
                    }
                    return true;
                }));
    }

    private static ModularPanel build(
            GTMTE_TowerCommunication machine,
            ModularPanel tower,
            PanelSyncManager sync
    ) {
        int[] page = { 0 };
        GenericSyncValue<NBTTagCompound, ?> state = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> sync.isClient() ? new NBTTagCompound() : snapshot(machine, sync.getPlayer(), page))
                .adapter(ByteBufAdapters.NBT)
                .build();
        sync.syncValue("satellites", state);
        sync.registerServerSyncedAction(SELECT_SATELLITE, packet -> {
            if (CommunicationTowerGui.canInteract(machine, sync)) {
                machine.getNetworkHandler().selectSatellite(ByteBufUtils.readUTF8String(packet), sync.getPlayer());
            }
        });

        ModularPanel popup = new ModularPanel(PANEL).size(240, WINDOW_HEIGHT).relative(tower)
                .top(CommunicationTowerGui.PAGE_BUTTON_Y - WINDOW_HEIGHT - 1)
                .child(ButtonWidget.panelCloseButton());
        popup.child(IKey.lang(TITLE).asWidget().widgetTheme(GTWidgetThemes.TEXT_TITLE)
                .textAlign(Alignment.CenterLeft).pos(8, 5).size(202, 14));
        popup.child(IKey.dynamic(() -> emptyMessage(state.getValue())).asWidget()
                .color(MUTED).shadow(false).pos(8, 33).size(224, 12));
        ParentWidget<?> cards = new ParentWidget<>().name("impact_satellite_cards")
                .pos(8, 19).size(224, 69).padding(0);
        List<String> displayedIds = new ArrayList<>(PAGE_SIZE);
        cards.onUpdateListener(widget -> refreshCards(cards, displayedIds, state, sync));
        popup.child(cards);
        addPageButton(machine, popup, sync, state, page, -1);
        addPageButton(machine, popup, sync, state, page, 1);
        popup.child(IKey.dynamic(() -> pageLabel(state.getValue())).asWidget()
                .color(MUTED).shadow(false).textAlign(Alignment.Center).pos(78, 93).size(84, 12));
        return popup;
    }

    private static void refreshCards(
            ParentWidget<?> cards,
            List<String> displayedIds,
            GenericSyncValue<NBTTagCompound, ?> state,
            PanelSyncManager sync
    ) {
        NBTTagList entries = state.getValue().getTagList("entries", 10);
        if (sameEntries(entries, displayedIds)) {
            return;
        }

        cards.removeAll();
        displayedIds.clear();
        for (int index = 0; index < entries.tagCount(); index++) {
            NBTTagCompound satellite = entries.getCompoundTagAt(index);
            displayedIds.add(satellite.getString("id"));
            cards.child(card(satellite, index, state, sync));
        }
        cards.scheduleResize();
    }

    private static boolean sameEntries(NBTTagList entries, List<String> displayedIds) {
        if (entries.tagCount() != displayedIds.size()) {
            return false;
        }
        for (int index = 0; index < entries.tagCount(); index++) {
            if (!displayedIds.get(index).equals(entries.getCompoundTagAt(index).getString("id"))) {
                return false;
            }
        }
        return true;
    }

    private static ButtonWidget<?> card(
            NBTTagCompound satellite,
            int row,
            GenericSyncValue<NBTTagCompound, ?> state,
            PanelSyncManager sync
    ) {
        String id = satellite.getString("id");
        ParentWidget<?> content = new ParentWidget<>().size(CARD_WIDTH, CARD_HEIGHT).padding(0);
        content.child(IKey.lang("impact.tower.satellite_dimension", satellite.getInteger("dimension")).asWidget()
                .color(CARD_TEXT).shadow(true).textAlign(Alignment.CenterLeft).pos(5, 3).size(51, 9));
        content.child(IKey.dynamic(() -> availability(state.getValue(), row)).asWidget()
                .color(() -> entry(state.getValue(), row).getBoolean("online")
                        ? CommunicationTowerDisplay.CONNECTED : CommunicationTowerDisplay.DISCONNECTED)
                .shadow(true).textAlign(Alignment.CenterRight).pos(57, 3).size(46, 9));
        String coordinates = satellite.getInteger("x") + ", " + satellite.getInteger("y")
                + ", " + satellite.getInteger("z");
        content.child(new ScrollingTextWidget(IKey.str(coordinates)).color(CARD_TEXT).shadow(true)
                .textAlign(Alignment.CenterLeft).pos(5, 13).size(98, 9));
        content.child(IKey.dynamic(() -> StatCollector.translateToLocal(isSelected(state.getValue(), id)
                ? "impact.tower.satellite_selected" : "impact.tower.satellite_select")).asWidget()
                .color(() -> isSelected(state.getValue(), id)
                        ? CommunicationTowerDisplay.CONNECTED : CommunicationTowerDisplay.MUTED)
                .shadow(true).textAlign(Alignment.CenterLeft).pos(5, 23).size(98, 9));

        return new SatelliteCardButton().name("impact_satellite_" + row)
                .background(new DynamicDrawable(() -> isSelected(state.getValue(), id)
                        ? GTGuiTextures.BUTTON_STANDARD_PRESSED : GTGuiTextures.BUTTON_STANDARD))
                .pos(row % 2 * 116, row / 2 * 36).size(CARD_WIDTH, CARD_HEIGHT).padding(0)
                .child(content)
                .tooltipBuilder(tooltip -> tooltip.addLine(StatCollector.translateToLocal(
                        entry(state.getValue(), row).getString("status"))))
                .tooltipAutoUpdate(true)
                .onMouseTapped(button -> {
                    if (button != 0) {
                        return false;
                    }
                    sync.callSyncedAction(SELECT_SATELLITE, packet -> ByteBufUtils.writeUTF8String(packet, id));
                    return true;
                });
    }

    private static void addPageButton(
            GTMTE_TowerCommunication machine,
            ModularPanel panel,
            PanelSyncManager sync,
            GenericSyncValue<NBTTagCompound, ?> state,
            int[] page,
            int direction
    ) {
        String key = direction < 0 ? "previous" : "next";
        InteractionSyncHandler click = new InteractionSyncHandler().setOnMousePressed(mouse -> {
            if (!mouse.isClient() && mouse.mouseButton == 0 && CommunicationTowerGui.canInteract(machine, sync)) {
                int lastPage = Math.max(0, state.getValue().getInteger("pages") - 1);
                page[0] = Math.max(0, Math.min(lastPage, page[0] + direction));
            }
        });
        sync.syncValue(key, click);
        IDrawable arrow = IKey.str(direction < 0 ? "<" : ">");
        ButtonWidget<?> button = new ButtonWidget<>().name("impact_satellite_" + key)
                .syncHandler(click)
                .background(new DynamicDrawable(() -> hasAdjacentPage(state.getValue(), direction)
                        ? GTGuiTextures.BUTTON_STANDARD : IDrawable.EMPTY))
                .disableHoverBackground()
                .overlay(new DynamicDrawable(() -> hasAdjacentPage(state.getValue(), direction)
                        ? arrow : IDrawable.EMPTY))
                .pos(direction < 0 ? 8 : 215, 91).size(17, 15)
                .setEnabledIf(widget -> hasAdjacentPage(state.getValue(), direction));
        button.setEnabled(false);
        panel.child(button);
    }

    private static NBTTagCompound snapshot(GTMTE_TowerCommunication machine, EntityPlayer viewer, int[] page) {
        NBTTagCompound tag = new NBTTagCompound();
        if (machine.getBaseMetaTileEntity() == null || viewer == null) {
            return tag;
        }
        SatelliteDirectory directory = SatelliteDirectory.get(machine.getBaseMetaTileEntity().getWorld());
        Map<String, IPosition> positions = directory.getPositions(viewer.getUniqueID());
        int pages = (positions.size() + PAGE_SIZE - 1) / PAGE_SIZE;
        page[0] = Math.min(page[0], Math.max(0, pages - 1));
        tag.setInteger("page", page[0]);
        tag.setInteger("pages", pages);
        String selected = machine.getNetworkHandler().getSatelliteId();
        if (positions.containsKey(selected)) {
            tag.setString("selected", selected);
        }
        NBTTagList entries = new NBTTagList();
        int skip = page[0] * PAGE_SIZE;
        for (Map.Entry<String, IPosition> entry : positions.entrySet()) {
            if (skip-- > 0) {
                continue;
            }
            String id = entry.getKey();
            IPosition position = entry.getValue();
            NetworkStatus status = directory.getStatus(id);
            NBTTagCompound row = new NBTTagCompound();
            row.setString("id", id);
            row.setInteger("dimension", position.getD());
            row.setInteger("x", position.getX());
            row.setInteger("y", position.getY());
            row.setInteger("z", position.getZ());
            row.setString("status", status.getTranslationKey());
            row.setBoolean("online", status == NetworkStatus.CONNECTED);
            entries.appendTag(row);
            if (entries.tagCount() == PAGE_SIZE) {
                break;
            }
        }
        tag.setTag("entries", entries);
        return tag;
    }

    private static NBTTagCompound entry(NBTTagCompound tag, int row) {
        return tag.getTagList("entries", 10).getCompoundTagAt(row);
    }

    private static boolean isSelected(NBTTagCompound state, String id) {
        return id.equals(state.getString("selected"));
    }

    private static String availability(NBTTagCompound tag, int row) {
        return StatCollector.translateToLocal(entry(tag, row).getBoolean("online")
                ? "impact.tower.satellite_online" : "impact.tower.satellite_offline");
    }

    private static String emptyMessage(NBTTagCompound state) {
        if (!state.hasKey("pages")) {
            return StatCollector.translateToLocal("impact.tower.loading_satellites");
        }
        return state.getInteger("pages") == 0 ? StatCollector.translateToLocal("impact.tower.no_satellites") : "";
    }

    private static String pageLabel(NBTTagCompound state) {
        int pages = state.getInteger("pages");
        return pages <= 1 ? "" : (state.getInteger("page") + 1) + " / " + pages;
    }

    private static boolean hasAdjacentPage(NBTTagCompound state, int direction) {
        int page = state.getInteger("page");
        return direction < 0 ? page > 0 : page + 1 < state.getInteger("pages");
    }

    private static final class SatelliteCardButton extends ButtonWidget<SatelliteCardButton> {

        @Override
        public Interactable.Result onMousePressed(int button) {
            return button == 0 ? Interactable.Result.SUCCESS : Interactable.Result.STOP;
        }
    }
}
