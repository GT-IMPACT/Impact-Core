package com.impact.network.special;

import com.google.common.io.ByteArrayDataInput;
import com.impact.client.render.TowerConnectionRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import space.impact.packet_network.network.packets.ImpactPacket;

public final class TowerConnectionsPacket extends ImpactPacket {

    public static final int RANGE = 512;
    public static final int LINK_SIZE = 4;
    public static final int MAX_LINKS = 32768;
    private static final int PACKET_ID = 1005;
    public static final TowerConnectionsPacket TYPE = new TowerConnectionsPacket(new int[0]);

    private final int[] links;

    private TowerConnectionsPacket(int[] links) {
        super(PACKET_ID);
        this.links = links;
    }

    public static TowerConnectionsPacket snapshot(int[] links, int count) {
        if (count < 0 || count > MAX_LINKS || count * LINK_SIZE > links.length) {
            throw new IllegalArgumentException("Invalid tower link count: " + count);
        }
        return new TowerConnectionsPacket(Arrays.copyOf(links, count * LINK_SIZE));
    }

    public static TowerConnectionsPacket empty() {
        return new TowerConnectionsPacket(new int[0]);
    }

    public int getLinkCount() {
        return links.length / LINK_SIZE;
    }

    public int getLinkX(int index) {
        return links[index * LINK_SIZE];
    }

    public int getLinkY(int index) {
        return links[index * LINK_SIZE + 1];
    }

    public int getLinkZ(int index) {
        return links[index * LINK_SIZE + 2];
    }

    public boolean isLinkActive(int index) {
        return links[index * LINK_SIZE + 3] != 0;
    }

    public boolean hasSameLinks(TowerConnectionsPacket other) {
        return Arrays.equals(links, other.links);
    }

    @Override
    public void encode(ByteBufOutputStream output) {
        try {
            output.writeInt(getLinkCount());
            for (int index = 0; index < getLinkCount(); index++) {
                output.writeInt(getLinkX(index));
                output.writeInt(getLinkY(index));
                output.writeInt(getLinkZ(index));
                output.writeBoolean(isLinkActive(index));
            }
        } catch (IOException failure) {
            throw new IllegalStateException("Could not write tower connections", failure);
        }
    }

    @Override
    public TowerConnectionsPacket decode(ByteArrayDataInput input) {
        int count = input.readInt();
        if (count < 0 || count > MAX_LINKS) {
            throw new IllegalArgumentException("Invalid tower link count: " + count);
        }
        int[] decoded = new int[count * LINK_SIZE];
        for (int offset = 0; offset < decoded.length; offset += LINK_SIZE) {
            decoded[offset] = input.readInt();
            decoded[offset + 1] = input.readInt();
            decoded[offset + 2] = input.readInt();
            decoded[offset + 3] = input.readBoolean() ? 1 : 0;
        }
        return new TowerConnectionsPacket(decoded);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void processClient(Minecraft minecraft, IBlockAccess world) {
        if (world instanceof World && ((World) world).provider.dimensionId == getDimId()) {
            minecraft.func_152344_a(() -> {
                if (minecraft.theWorld == world) {
                    TowerConnectionRenderer.INSTANCE.receive((World) world, this);
                }
            });
        }
    }
}
