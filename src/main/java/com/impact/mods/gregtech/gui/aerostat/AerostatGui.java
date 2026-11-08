package com.impact.mods.gregtech.gui.aerostat;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.drawable.DynamicDrawable;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.drawable.Rectangle;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.StringValue;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.InteractionSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.widget.ParentWidget;
import com.cleanroommc.modularui.widget.Widget;
import com.cleanroommc.modularui.widget.scroll.VerticalScrollData;
import com.cleanroommc.modularui.widgets.ListWidget;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.ScrollingTextWidget;
import com.cleanroommc.modularui.widgets.textfield.TextFieldWidget;
import com.impact.addon.gt.api.aerostat.IAeroStat;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.modularui2.GTGuis;
import gregtech.api.modularui2.GTGuiTextures;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

/** One panel instance owns one player's draft and selected station ID. */
public final class AerostatGui {

    private static final int INK = 0x3F3627;
    private static final int MUTED = 0x6B604C;
    private static final int TEXT = 0xEEE5D3;
    private static final int LIST_MUTED = 0xBBAF96;
    private static final int ERROR = 0x9B4435;
    private static final IDrawable WOOD = UITexture.builder()
            .location("minecraft", "blocks/planks_oak")
            .tiled(16, 16)
            .build();
    private static final IDrawable PAPER = solid(0xC5B899);
    private static final IDrawable LIGHT_EDGE = solid(0xE3D8BD);
    private static final IDrawable DARK_EDGE = solid(0x55462F);
    private static final IDrawable INPUT = solid(0x302A21);
    private static final IDrawable ROW_HOVER = solid(0x494032);
    private static final IDrawable ROW_SELECTED = solid(0x62553F);
    private static final IDrawable FUEL = solid(0xB89B5F);
    private static final IDrawable FUEL_LOW = solid(0xA66E48);
    private final GTMTE_Aerostat machine;
    private final PanelSyncManager sync;
    private final EntityPlayer player;
    private final GenericSyncValue<NBTTagCompound, ?> state;
    private final StringSyncValue selected;
    private final StringSyncValue name;
    private final StringValue search = new StringValue("");
    private final NumberFormat numbers = NumberFormat.getIntegerInstance();
    private String selectedId = "";
    private String draft;
    private boolean editingName;
    private ListWidget<IWidget, ?> stationList;
    private List<NBTTagCompound> displayedStations = new ArrayList<>();
    private String displayedQuery = "";
    private NBTTagCompound filteredSnapshot;
    private String filteredQuery;
    private final List<NBTTagCompound> filtered = new ArrayList<>();

    private AerostatGui(GTMTE_Aerostat machine, PosGuiData data, PanelSyncManager sync) {
        this.machine = machine;
        this.sync = sync;
        player = data.getPlayer();
        draft = machine.getName();
        state = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> sync.isClient() ? new NBTTagCompound() : snapshot())
                .adapter(ByteBufAdapters.NBT)
                .build();
        selected = new StringSyncValue(() -> selectedId, value -> {
            if (sync.isClient() || (canInteract() && machine.getReachableStation(value) != null)) {
                selectedId = value;
            }
        }).allowC2S();
        name = new StringSyncValue(() -> draft, value -> {
            if (value != null && value.length() <= GTMTE_Aerostat.MAX_NAME_LENGTH
                    && (sync.isClient() || canRename())) {
                draft = value;
            }
        }).allowC2S();
        sync.syncValue("aerostatState", state);
        sync.syncValue("aerostatSelected", selected);
        sync.syncValue("aerostatName", name);
    }

    public static ModularPanel build(
            GTMTE_Aerostat machine,
            PosGuiData data,
            PanelSyncManager sync,
            UISettings settings
    ) {
        return new AerostatGui(machine, data, sync).panel(data, settings);
    }

    private ModularPanel panel(PosGuiData data, UISettings settings) {
        ModularPanel panel = GTGuis.mteTemplatePanelBuilder(machine, data, sync, settings)
                .setWidth(300)
                .setHeight(232)
                .doesBindPlayerInventory(false)
                .doesAddTitle(false)
                .doesAddCoverTabs(false)
                .doesAddGregTechLogo(false)
                .build();
        panel.padding(0).background((context, x, y, width, height, theme) -> {
            WOOD.draw(context, x, y, width, height, theme);
            LIGHT_EDGE.draw(context, x, y, width, 1, theme);
            LIGHT_EDGE.draw(context, x, y, 1, height, theme);
            DARK_EDGE.draw(context, x, y + height - 2, width, 2, theme);
            DARK_EDGE.draw(context, x + width - 2, y, 2, height, theme);
            DARK_EDGE.draw(context, x + 6, y + 6, width - 12, height - 12, theme);
            PAPER.draw(context, x + 7, y + 7, width - 14, height - 14, theme);
        });
        addHeader(panel);
        addStationList(panel);
        addFlightDetails(panel);
        return panel;
    }

    private void addHeader(ModularPanel panel) {
        panel.child(IKey.lang("impact.aerostat.title").asWidget()
                .color(INK).shadow(false).pos(14, 12).size(100, 10));
        panel.child(IKey.dynamic(() -> text("range", readings().getInteger("range"))).asWidget()
                .color(MUTED).shadow(false).textAlign(Alignment.CenterRight).pos(142, 12).size(130, 10));
        panel.child(new ScrollingTextWidget(IKey.dynamic(() -> readings().getString("name")))
                .color(INK).shadow(false).textAlign(Alignment.CenterLeft).pos(14, 25).size(246, 18)
                .tooltipBuilder(tooltip -> {
                    tooltip.addLine(readings().getString("name"));
                    tooltip.addLine(text("owner", readings().getString("owner")));
                }).tooltipAutoUpdate(true)
                .setEnabledIf(widget -> !editingName));
        panel.child(new ButtonWidget<>().name("aerostat_edit_name")
                .background(GTGuiTextures.BUTTON_STANDARD)
                .overlay(GuiTextures.EDIT.asIcon().size(12))
                .tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.aerostat.rename")))
                .onMousePressed(button -> {
                    if (button == 0 && isOwner()) {
                        name.setStringValue(readings().getString("name"));
                        editingName = true;
                    }
                    return true;
                }).pos(268, 25).size(18)
                .setEnabledIf(widget -> !editingName && isOwner()));
        panel.child(new TextFieldWidget().name("aerostat_name")
                .value(name).autoUpdateOnChange(true).setMaxLength(GTMTE_Aerostat.MAX_NAME_LENGTH)
                .setTextColor(TEXT).background(GTGuiTextures.BACKGROUND_TEXT_FIELD).padding(3)
                .tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.aerostat.name_hint")))
                .pos(14, 25).size(132, 18)
                .disabled().setEnabledIf(widget -> editingName && isOwner()));
        panel.child(action("save_name", "save", this::isOwner, () -> {
            if (canRename()) {
                machine.setLocationName(draft);
                draft = machine.getName();
            }
        })
                .tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.aerostat.save")))
                .pos(150, 25).size(40, 18)
                .disabled().setEnabledIf(widget -> editingName));
        panel.child(action("reset_name", "reset", this::isOwner, () -> {
            if (canRename()) {
                machine.setLocationName("");
                draft = machine.getName();
            }
        }).tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.aerostat.reset_hint")))
                .pos(194, 25).size(42, 18)
                .disabled().setEnabledIf(widget -> editingName));
        panel.child(new ButtonWidget<>().name("aerostat_cancel_name")
                .background(GTGuiTextures.BUTTON_STANDARD)
                .overlay(IKey.lang("gui.cancel").color(0xFFFFFF))
                .tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.aerostat.cancel")))
                .onMousePressed(button -> {
                    if (button == 0) {
                        editingName = false;
                    }
                    return true;
                }).pos(240, 25).size(46, 18)
                .disabled().setEnabledIf(widget -> editingName));
    }

    private void addStationList(ModularPanel panel) {
        panel.child(new TextFieldWidget().name("aerostat_search").value(search)
                .autoUpdateOnChange(true).setMaxLength(64)
                .setTextColor(TEXT).hintText(text("search_short")).hintColor(LIST_MUTED)
                .background(GTGuiTextures.BACKGROUND_TEXT_FIELD).padding(4)
                .tooltip(tooltip -> tooltip.addLine(IKey.lang("impact.aerostat.search")))
                .pos(14, 65).size(272, 18));
        panel.child(IKey.dynamic(() -> text("stations_count", filteredStations().size(), allStations().tagCount()))
                .asWidget().color(MUTED).shadow(false).pos(22, 89).size(140, 9));
        panel.child(IKey.lang("impact.aerostat.distance_label").asWidget()
                .color(MUTED).shadow(false).textAlign(Alignment.CenterRight).pos(166, 89).size(52, 9));
        panel.child(IKey.lang("impact.aerostat.fuel_label").asWidget()
                .color(MUTED).shadow(false).textAlign(Alignment.CenterRight).pos(234, 89).size(42, 9));
        panel.child(new Widget<>().pos(12, 100).size(276, 94)
                .background((context, x, y, width, height, theme) -> {
                    DARK_EDGE.draw(context, x, y, width, height, theme);
                    LIGHT_EDGE.draw(context, x + 1, y + 1, width - 1, height - 1, theme);
                    INPUT.draw(context, x + 1, y + 1, width - 2, height - 2, theme);
                }));
        stationList = new ListWidget<>().scrollDirection(new VerticalScrollData(false, 4))
                .name("aerostat_stations").padding(0).pos(13, 102).size(274, 90);
        stationList.getScrollData().setScrollSpeed(18);
        stationList.onUpdateListener(widget -> refreshStations());
        refreshStations();
        panel.child(stationList);
        panel.child(IKey.dynamic(() -> text(allStations().tagCount() == 0 ? "no_stations" : "no_matches"))
                .asWidget().color(LIST_MUTED).shadow(false).textAlign(Alignment.CENTER)
                .pos(22, 131).size(250, 28)
                .setEnabledIf(widget -> filteredStations().isEmpty()));
    }

    private void refreshStations() {
        List<NBTTagCompound> matches = filteredStations();
        String query = search.getStringValue().trim();
        if (matches.equals(displayedStations) && query.equals(displayedQuery)) {
            return;
        }
        int scroll = query.equals(displayedQuery) ? stationList.getScrollData().getScroll() : 0;
        stationList.removeAll();
        for (NBTTagCompound station : matches) {
            stationList.child(stationButton(station));
        }
        displayedStations = new ArrayList<>(matches);
        displayedQuery = query;
        stationList.getScrollData().setScrollSize(matches.size() * 18);
        stationList.getScrollData().scrollTo(stationList.getScrollArea(), scroll);
    }

    private ButtonWidget<?> stationButton(NBTTagCompound station) {
        String id = station.getString("id");
        ParentWidget<?> content = new ParentWidget<>().pos(0, 0).size(270, 18).padding(0);
        content.child(IKey.str(">").asWidget().color(TEXT).shadow(false).pos(2, 0).size(6, 18)
                .setEnabledIf(widget -> id.equals(selected.getStringValue())));
        content.child(new ScrollingTextWidget(IKey.str(station.getString("name")))
                .color(TEXT).shadow(false).textAlign(Alignment.CenterLeft).pos(12, 0).size(142, 18));
        content.child(IKey.str(text("distance", station.getInteger("distance"))).asWidget()
                .color(LIST_MUTED).shadow(false).textAlign(Alignment.CenterRight).pos(164, 0).size(41, 18));
        content.child(IKey.dynamic(() -> numbers.format(station.getInteger("cost"))).asWidget()
                .color(() -> station.getInteger("cost") > fuel() ? 0xD3987B : TEXT)
                .shadow(false).textAlign(Alignment.CenterRight).pos(210, 0).size(53, 18));
        return new ButtonWidget<>().name("aerostat_station_" + id)
                .background(new DynamicDrawable(() -> id.equals(selected.getStringValue())
                        ? ROW_SELECTED : IDrawable.NONE))
                .hoverBackground(new DynamicDrawable(() -> id.equals(selected.getStringValue())
                        ? ROW_SELECTED : ROW_HOVER))
                .child(content).left(0).size(270, 18).padding(0)
                .onMousePressed(button -> {
                    if (button == 0) {
                        selected.setStringValue(id);
                    }
                    return true;
                })
                .tooltipBuilder(tooltip -> {
                    tooltip.addLine(station.getString("name"));
                    tooltip.addLine(coordinates(station));
                    tooltip.addLine(text("cost", numbers.format(station.getInteger("cost"))));
                }).tooltipAutoUpdate(true);
    }

    private void addFlightDetails(ModularPanel panel) {
        panel.child(IKey.dynamic(() -> text("fuel", numbers.format(fuel()),
                        numbers.format(GTMTE_Aerostat.MAX_BUFFER))).asWidget()
                .color(INK).shadow(false).pos(14, 49).size(174, 10));
        panel.child(new Widget<>().name("aerostat_fuel").pos(192, 49).size(94, 8)
                .background((context, x, y, width, height, theme) -> {
                    DARK_EDGE.draw(context, x, y, width, height, theme);
                    LIGHT_EDGE.draw(context, x + 1, y + 1, width - 1, height - 1, theme);
                    INPUT.draw(context, x + 1, y + 1, width - 2, height - 2, theme);
                    int filled = (width - 2) * fuel() / GTMTE_Aerostat.MAX_BUFFER;
                    if (filled > 0) {
                        IDrawable fill = selectedStation() != null
                                && selectedStation().getInteger("cost") > fuel() ? FUEL_LOW : FUEL;
                        fill.draw(context, x + 1, y + 1, filled, height - 2, theme);
                    }
                }));
        panel.child(new ScrollingTextWidget(IKey.dynamic(() -> selectedStation() == null
                        ? text("select_short") : selectedStation().getString("name")))
                .color(INK).shadow(false).pos(14, 200).size(176, 10));
        panel.child(IKey.dynamic(() -> {
                    if (selectedStation() == null) {
                        return "";
                    }
                    if (!canFly()) {
                        return status();
                    }
                    return text("after_flight", numbers.format(fuel() - selectedStation().getInteger("cost")));
                }).asWidget().name("aerostat_status")
                .color(() -> selectedStation() != null && !canFly() ? ERROR : MUTED)
                .shadow(false).pos(14, 213).size(176, 10));
        panel.child(action("travel", "travel", this::canFly,
                () -> machine.travelTo(sync.getPlayer(), selectedId)).pos(200, 200).size(86, 22));
    }

    private ButtonWidget<?> action(String id, String label, BooleanSupplier enabled, Runnable action) {
        InteractionSyncHandler handler = new InteractionSyncHandler().setOnMousePressed(mouse -> {
            if (!mouse.isClient() && mouse.mouseButton == 0 && canInteract()) {
                action.run();
            }
        });
        sync.syncValue("aerostat_" + id, handler);
        return new ButtonWidget<>().name("aerostat_" + id).syncHandler(handler)
                .background(new DynamicDrawable(() -> enabled.getAsBoolean()
                        ? GTGuiTextures.BUTTON_STANDARD : GTGuiTextures.BUTTON_STANDARD_DISABLED))
                .onMousePressed(button -> {
                    if (button != 0 || !enabled.getAsBoolean()) {
                        return true;
                    }
                    editingName = false;
                    return false;
                })
                .overlay(IKey.lang("impact.aerostat." + label)
                        .color(() -> enabled.getAsBoolean() ? 0xFFFFFF : 0x808080));
    }

    private boolean canFly() {
        NBTTagCompound station = selectedStation();
        return station != null && station.getInteger("cost") <= fuel() && !readings().getBoolean("riding");
    }

    private static IDrawable solid(int color) {
        return new Rectangle().color(0xFF000000 | color).canApplyTheme(false);
    }

    private boolean canInteract() {
        EntityPlayer player = sync.getPlayer();
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        return player != null && player.openContainer == sync.getContainer() && machine.mMachine
                && tile != null && !tile.isDead() && tile.getMetaTileEntity() == machine
                && GTMTE_Aerostat.hasAerostat(player) && sync.getContainer().canInteractWith(player);
    }

    private boolean canRename() {
        return canInteract() && sync.getPlayer().getCommandSenderName().equals(machine.getOwner());
    }

    private boolean isOwner() {
        return player.getCommandSenderName().equals(readings().getString("owner"));
    }

    private NBTTagCompound readings() {
        return state.getValue();
    }

    private int fuel() {
        return Math.max(0, Math.min(GTMTE_Aerostat.MAX_BUFFER, readings().getInteger("fuel")));
    }

    private NBTTagList allStations() {
        return readings().getTagList("stations", 10);
    }

    private List<NBTTagCompound> filteredStations() {
        String query = search.getStringValue().trim().toLowerCase(Locale.ROOT);
        if (readings() == filteredSnapshot && query.equals(filteredQuery)) {
            return filtered;
        }
        filteredQuery = query;
        filteredSnapshot = readings();
        filtered.clear();
        NBTTagList stations = allStations();
        for (int index = 0; index < stations.tagCount(); index++) {
            NBTTagCompound station = stations.getCompoundTagAt(index);
            if (station.getString("name").toLowerCase(Locale.ROOT).contains(query)
                    || coordinates(station).contains(query)) {
                filtered.add(station);
            }
        }
        return filtered;
    }

    private NBTTagCompound selectedStation() {
        NBTTagList stations = allStations();
        for (int index = 0; index < stations.tagCount(); index++) {
            NBTTagCompound station = stations.getCompoundTagAt(index);
            if (station.getString("id").equals(selected.getStringValue())) {
                return station;
            }
        }
        return null;
    }

    private String status() {
        NBTTagCompound station = selectedStation();
        if (station == null) {
            return text("select_short");
        }
        if (readings().getBoolean("riding")) {
            return text("dismount_short");
        }
        return station.getInteger("cost") > fuel() ? text("no_fuel_short") : text("ready_short");
    }

    private NBTTagCompound snapshot() {
        if (!selectedId.isEmpty() && machine.getReachableStation(selectedId) == null) {
            selectedId = "";
        }
        NBTTagCompound snapshot = new NBTTagCompound();
        snapshot.setString("name", machine.getName());
        snapshot.setString("owner", machine.getOwner());
        snapshot.setInteger("fuel", machine.curBuffer);
        snapshot.setInteger("range", machine.getTravelRange());
        snapshot.setBoolean("riding", player.ridingEntity != null);
        NBTTagList stations = new NBTTagList();
        for (IAeroStat station : machine.currentLocationPlatforms) {
            if (machine.getReachableStation(station.getUuid()) == null) {
                continue;
            }
            NBTTagCompound entry = new NBTTagCompound();
            entry.setString("id", station.getUuid());
            entry.setString("name", station.getName());
            entry.setInteger("x", station.getPos().getX());
            entry.setInteger("y", station.getPos().getY());
            entry.setInteger("z", station.getPos().getZ());
            int distance = machine.distanceTo(station.getPos());
            entry.setInteger("distance", distance);
            entry.setInteger("cost", distance * GTMTE_Aerostat.FUEL_PER_BLOCK);
            stations.appendTag(entry);
        }
        snapshot.setTag("stations", stations);
        return snapshot;
    }

    private static String coordinates(NBTTagCompound station) {
        return station.getInteger("x") + ", " + station.getInteger("y") + ", " + station.getInteger("z");
    }

    private static String text(String key, Object... arguments) {
        return StatCollector.translateToLocalFormatted("impact.aerostat." + key, arguments);
    }
}
