package com.impact.mods.gregtech.gui.tower;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.serialization.ByteBufAdapters;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.Widget;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.impact.addon.gt.api.parallel_system.INetworkMachine;
import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_CommunicationTower_Receiver;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.gui.modularui.multiblock.MTEProcessingMultiBlockGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Comparator;

public final class CommunicationTowerGui {

    static final int PAGE_SIZE = 32;
    static final int COLUMNS = 8;
    static final int LEFT = 10;
    static final int TOP = 80;
    static final int PITCH_X = 31;
    static final int PITCH_Y = 12;
    static final int WIDTH = 28;
    static final int HEIGHT = 11;
    static final int PAGE_BUTTON_Y = 133;

    private CommunicationTowerGui() {
    }

    public static ModularPanel build(
            GTMTE_TowerCommunication machine,
            PosGuiData data,
            PanelSyncManager sync,
            UISettings settings
    ) {
        int[] page = { 0 };
        GenericSyncValue<NBTTagCompound, ?> readings = GenericSyncValue.builder(NBTTagCompound.class)
                .getter(() -> sync.isClient() ? new NBTTagCompound() : snapshot(machine, page))
                .adapter(ByteBufAdapters.NBT)
                .build();
        sync.syncValue("communicationTowerState", readings);
        ModularPanel panel = new MTEProcessingMultiBlockGui(machine).build(data, sync, settings,
                state -> new CommunicationTowerDisplay(machine, state, readings));
        TowerSatellitePanel.addTo(machine, panel, sync);
        for (int index = 0; index < PAGE_SIZE; index++) {
            final int connection = index;
            panel.child(new Widget<>()
                    .name("tower_connection_" + index)
                    .pos(LEFT + index % COLUMNS * PITCH_X, TOP + index / COLUMNS * PITCH_Y)
                    .size(WIDTH, HEIGHT)
                    .invisible()
                    .setEnabledIf(widget -> showConnections(readings.getValue())
                            && connection < readings.getValue().getTagList("nodes", 10).tagCount())
                    .tooltipBuilder(tooltip -> {
                        NBTTagList nodes = readings.getValue().getTagList("nodes", 10);
                        if (connection < nodes.tagCount()) {
                            NBTTagCompound node = nodes.getCompoundTagAt(connection);
                            tooltip.addLine(StatCollector.translateToLocal(node.getString("name")));
                            tooltip.addLine(StatCollector.translateToLocalFormatted(
                                    "impact.tower.address",
                                    node.getInteger("x"),
                                    node.getInteger("y"),
                                    node.getInteger("z")
                            ));
                            tooltip.addLine(StatCollector.translateToLocal(stateKey(node.getBoolean("connected"))));
                            if (!node.getBoolean("connected")) {
                                tooltip.addLine(StatCollector.translateToLocal(node.getString(NetworkDiagnostics.STATUS_TAG)));
                            }
                        }
                    })
                    .tooltipAutoUpdate(true));
        }
        addPageButton(
                machine,
                panel,
                sync,
                readings,
                page,
                -1
        );
        addPageButton(
                machine,
                panel,
                sync,
                readings,
                page,
                1
        );
        return panel;
    }

    static boolean showConnections(NBTTagCompound snapshot) {
        return snapshot.getBoolean("formed") && snapshot.getInteger("errors") == 0;
    }

    static String stateKey(boolean connected) {
        return "impact.tower." + (connected ? "connected" : "disconnected");
    }

    private static void addPageButton(
            GTMTE_TowerCommunication machine,
            ModularPanel panel,
            PanelSyncManager sync,
            GenericSyncValue<NBTTagCompound, ?> readings,
            int[] page,
            int direction
    ) {
        String name = direction < 0 ? "tower_previous" : "tower_next";
        sync.registerServerSyncedAction(name, packet -> {
            if (canInteract(machine, sync) && showConnections(readings.getValue())) {
                int lastPage = Math.max(0, readings.getValue().getInteger("pages") - 1);
                page[0] = Math.max(0, Math.min(lastPage, page[0] + direction));
            }
        });
        panel.child(new ButtonWidget<>()
                .name("impact_" + name)
                .background(GTGuiTextures.BUTTON_STANDARD)
                .overlay(IKey.str(direction < 0 ? "<" : ">"))
                .pos(direction < 0 ? 198 : 246, PAGE_BUTTON_Y)
                .size(12, 11)
                .setEnabledIf(widget -> showConnections(readings.getValue())
                        && (direction < 0 ? readings.getValue().getInteger("page") > 0
                        : readings.getValue().getInteger("page") + 1 < readings.getValue().getInteger("pages")))
                .onMousePressed(button -> {
                    if (button != 0) {
                        return false;
                    }
                    sync.callSyncedAction(name);
                    return true;
                }));
    }

    static boolean canInteract(GTMTE_TowerCommunication machine, PanelSyncManager sync) {
        EntityPlayer player = sync.getPlayer();
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        return player != null && player.openContainer == sync.getContainer()
                && tile != null && !tile.isDead() && tile.getMetaTileEntity() == machine
                && sync.getContainer().canInteractWith(player);
    }

    private static NBTTagCompound snapshot(GTMTE_TowerCommunication machine, int[] page) {
        NBTTagCompound snapshot = new NBTTagCompound();
        IGregTechTileEntity tile = machine.getBaseMetaTileEntity();
        if (tile == null || tile.isDead() || tile.getMetaTileEntity() != machine) {
            return snapshot;
        }
        snapshot.setBoolean("formed", machine.mMachine);
        snapshot.setInteger("errors", tile.getErrorDisplayID());
        snapshot.setBoolean("uplink", machine.isSatelliteConnected());
        NetworkDiagnostics.write(snapshot, machine);
        ArrayList<INetworkMachine> connections = new ArrayList<>();
        int connected = 0;
        for (INetworkMachine node : machine.getConnections()) {
            if (node.hasShowConnect() && nodeTile(node) != null) {
                connections.add(node);
                if (node.isSatelliteConnected()) {
                    connected++;
                }
            }
        }
        connections.sort(Comparator.comparingInt((INetworkMachine node) -> nodeTile(node).xCoord)
                .thenComparingInt(node -> nodeTile(node).yCoord)
                .thenComparingInt(node -> nodeTile(node).zCoord));
        int pages = Math.max(1, (connections.size() + PAGE_SIZE - 1) / PAGE_SIZE);
        page[0] = Math.max(0, Math.min(pages - 1, page[0]));
        snapshot.setInteger("total", connections.size());
        snapshot.setInteger("connected", connected);
        snapshot.setInteger("page", page[0]);
        snapshot.setInteger("pages", pages);
        NBTTagList nodes = new NBTTagList();
        int end = Math.min(connections.size(), (page[0] + 1) * PAGE_SIZE);
        for (int index = page[0] * PAGE_SIZE; index < end; index++) {
            INetworkMachine node = connections.get(index);
            TileEntity nodeTile = nodeTile(node);
            NBTTagCompound entry = new NBTTagCompound();
            entry.setString("name", node instanceof IMetaTileEntity
                    ? "gt.blockmachines." + ((IMetaTileEntity) node).getMetaName() + ".name" : "impact.tower.device");
            entry.setInteger("x", nodeTile.xCoord);
            entry.setInteger("y", nodeTile.yCoord);
            entry.setInteger("z", nodeTile.zCoord);
            entry.setBoolean("connected", node.isSatelliteConnected());
            if (node instanceof IHandledNetworkNode) {
                NetworkDiagnostics.write(entry, (IHandledNetworkNode) node);
            }
            nodes.appendTag(entry);
        }
        snapshot.setTag("nodes", nodes);
        ArrayList<GTMTE_CommunicationTower_Receiver> receivers = new ArrayList<>();
        for (GTMTE_CommunicationTower_Receiver receiver : machine.sCommunReceiver) {
            IGregTechTileEntity receiverTile = receiver.getBaseMetaTileEntity();
            if (receiverTile != null && !receiverTile.isDead() && receiverTile.getMetaTileEntity() == receiver) {
                receivers.add(receiver);
            }
        }
        receivers.sort(Comparator.comparingInt((GTMTE_CommunicationTower_Receiver receiver) ->
                        receiver.getBaseMetaTileEntity().getXCoord())
                .thenComparingInt(receiver -> receiver.getBaseMetaTileEntity().getYCoord())
                .thenComparingInt(receiver -> receiver.getBaseMetaTileEntity().getZCoord()));
        int[] states = { -1, -1, -1, -1 };
        for (int index = 0; index < Math.min(states.length, receivers.size()); index++) {
            GTMTE_CommunicationTower_Receiver receiver = receivers.get(index);
            states[index] = receiver.hasConnected() && receiver.getBaseMetaTileEntity().isActive() ? 1 : 0;
        }
        snapshot.setIntArray("receivers", states);
        return snapshot;
    }

    private static TileEntity nodeTile(INetworkMachine node) {
        if (node instanceof IMetaTileEntity) {
            IGregTechTileEntity tile = ((IMetaTileEntity) node).getBaseMetaTileEntity();
            if (tile instanceof TileEntity && !tile.isDead() && tile.getMetaTileEntity() == node) {
                return (TileEntity) tile;
            }
        } else if (node instanceof TileEntity && !((TileEntity) node).isInvalid()) {
            return (TileEntity) node;
        }
        return null;
    }
}
