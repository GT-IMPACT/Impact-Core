package com.impact.addon.gt.api.satellite;

import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.position.IPosition;
import com.impact.util.PositionObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public final class SatelliteDirectory extends WorldSavedData {

    private static final String NAME = "impact_satellites";

    private final Map<String, Entry> satellites = new TreeMap<>();
    private final Map<String, SatelliteControllerHandler> loaded = new HashMap<>();

    public SatelliteDirectory(String name) {
        super(name);
    }

    public static SatelliteDirectory get(World world) {
        SatelliteDirectory directory = (SatelliteDirectory) world.loadItemData(SatelliteDirectory.class, NAME);
        if (directory == null) {
            directory = new SatelliteDirectory(NAME);
            world.setItemData(NAME, directory);
        }
        return directory;
    }

    public Map<String, IPosition> getPositions(UUID owner) {
        Map<String, IPosition> positions = new LinkedHashMap<>();
        if (owner != null) {
            for (Map.Entry<String, Entry> entry : satellites.entrySet()) {
                if (owner.equals(entry.getValue().owner)) {
                    positions.put(entry.getKey(), entry.getValue().position);
                }
            }
        }
        return positions;
    }

    public boolean isOwnedBy(String id, UUID owner) {
        Entry entry = satellites.get(id);
        return entry != null && owner != null && owner.equals(entry.owner);
    }

    public boolean contains(String id) {
        return satellites.containsKey(id);
    }

    public NetworkStatus getStatus(String id) {
        SatelliteControllerHandler controller = loaded.get(id);
        if (controller != null) {
            return controller.getStatus();
        }
        return contains(id) ? NetworkStatus.SATELLITE_UNLOADED : NetworkStatus.SATELLITE_MISSING;
    }

    void register(IPosition position, SatelliteControllerHandler controller, UUID owner) {
        String id = idOf(position);
        loaded.put(id, controller);
        Entry entry = satellites.get(id);
        if (entry == null || !Objects.equals(entry.owner, owner)) {
            satellites.put(id, new Entry(new PositionObject(position), owner));
            markDirty();
        }
    }

    void unload(IPosition position, SatelliteControllerHandler controller) {
        loaded.remove(idOf(position), controller);
    }

    void remove(IPosition position, SatelliteControllerHandler controller) {
        String id = idOf(position);
        SatelliteControllerHandler current = loaded.get(id);
        if (current != null && current != controller) {
            return;
        }
        loaded.remove(id);
        if (satellites.remove(id) != null) {
            markDirty();
        }
    }

    private static String idOf(IPosition position) {
        return position.getD() + ":" + position.getX() + ":" + position.getY() + ":" + position.getZ();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        satellites.clear();
        loaded.clear();
        NBTTagList satellites = tag.getTagList("satellites", 10);
        for (int index = 0; index < satellites.tagCount(); index++) {
            NBTTagCompound saved = satellites.getCompoundTagAt(index);
            IPosition position = PositionObject.loadFromNBT(saved);
            this.satellites.put(idOf(position), new Entry(position, readOwner(saved)));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        NBTTagList satellites = new NBTTagList();
        for (Entry entry : this.satellites.values()) {
            NBTTagCompound saved = entry.position.saveToNBT();
            if (entry.owner != null) {
                saved.setString("owner", entry.owner.toString());
            }
            satellites.appendTag(saved);
        }
        tag.setTag("satellites", satellites);
    }

    private static UUID readOwner(NBTTagCompound tag) {
        if (!tag.hasKey("owner", 8)) {
            return null;
        }
        try {
            return UUID.fromString(tag.getString("owner"));
        } catch (IllegalArgumentException invalidOwner) {
            return null;
        }
    }

    private static final class Entry {

        private final IPosition position;
        private final UUID owner;

        private Entry(IPosition position, UUID owner) {
            this.position = position;
            this.owner = owner;
        }
    }
}
