package com.impact.client.render;

import baubles.api.BaublesApi;
import com.impact.common.armor.MaskOfVision;
import com.impact.network.special.TowerConnectionsPacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public final class TowerConnectionRenderer {

    public static final TowerConnectionRenderer INSTANCE = new TowerConnectionRenderer();

    // A heartbeat arrives every 20 server ticks. Keep the previous geometry across delayed updates.
    private static final int SNAPSHOT_TIMEOUT = 120;
    private static final int WORKING = 0x73ce8d;
    private static final int IDLE = 0x70bde9;
    private static final int[] TUBE_FACES = {
            0, 1, 5, 4, 1, 2, 6, 5, 2, 3, 7, 6, 3, 0, 4, 7,
            3, 2, 1, 0, 4, 5, 6, 7
    };

    private final Map<ChunkPosition, TowerOverlay> towers = new HashMap<>();
    private World world;
    private long clientTick;
    private boolean wearingMask;

    private TowerConnectionRenderer() {
    }

    public void receive(World packetWorld, TowerConnectionsPacket packet) {
        updateWorld(packetWorld);
        ChunkPosition position = new ChunkPosition(packet.getX(), packet.getY(), packet.getZ());
        if (packet.getLinkCount() == 0) {
            towers.remove(position);
            return;
        }
        TowerOverlay overlay = towers.get(position);
        if (overlay == null || !overlay.packet.hasSameLinks(packet)) {
            overlay = new TowerOverlay(position, packet);
            towers.put(position, overlay);
        }
        overlay.expiresAt = clientTick + SNAPSHOT_TIMEOUT;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft minecraft = Minecraft.getMinecraft();
        updateWorld(minecraft.theWorld);
        if (world == null || minecraft.isGamePaused()) {
            return;
        }
        clientTick++;
        if (!towers.isEmpty()) {
            towers.values().removeIf(overlay -> overlay.expiresAt <= clientTick);
        }
        wearingMask = !towers.isEmpty() && hasMask(minecraft.thePlayer);
    }

    private void updateWorld(World nextWorld) {
        if (world != nextWorld) {
            world = nextWorld;
            towers.clear();
            clientTick = 0;
            wearingMask = false;
        }
    }

    private static boolean hasMask(EntityPlayer player) {
        if (player == null) {
            return false;
        }
        if (isMask(player.getCurrentArmor(3))) {
            return true;
        }
        IInventory baubles = BaublesApi.getBaubles(player);
        if (baubles != null) {
            for (int slot = 0; slot < baubles.getSizeInventory(); slot++) {
                if (isMask(baubles.getStackInSlot(slot))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isMask(ItemStack stack) {
        return stack != null && stack.getItem() instanceof MaskOfVision;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        Entity camera = minecraft.renderViewEntity;
        if (!wearingMask || towers.isEmpty() || world != minecraft.theWorld || camera == null) {
            return;
        }
        double cameraX = camera.lastTickPosX + (camera.posX - camera.lastTickPosX) * event.partialTicks;
        double cameraY = camera.lastTickPosY + (camera.posY - camera.lastTickPosY) * event.partialTicks;
        double cameraZ = camera.lastTickPosZ + (camera.posZ - camera.lastTickPosZ) * event.partialTicks;

        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT
                | GL11.GL_CURRENT_BIT | GL11.GL_TEXTURE_BIT);
        try {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            // Draw only hidden fragments in the faint pass, so visible lines do not accumulate both opacities.
            GL11.glDepthFunc(GL11.GL_GREATER);
            drawPass(cameraX, cameraY, cameraZ, 27);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            drawPass(cameraX, cameraY, cameraZ, 205);
        } finally {
            GL11.glPopAttrib();
        }
    }

    private void drawPass(double cameraX, double cameraY, double cameraZ, int alpha) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        for (TowerOverlay overlay : towers.values()) {
            for (Link link : overlay.links) {
                tessellator.setColorRGBA(link.color >> 16, (link.color >> 8) & 255, link.color & 255, alpha);
                double radius = link.beam.radius(cameraX, cameraY, cameraZ);
                link.beam.draw(tessellator, cameraX, cameraY, cameraZ, radius);
                link.marker.draw(tessellator, cameraX, cameraY, cameraZ, Math.max(0.13, radius * 2.0));
            }
        }
        tessellator.draw();
    }

    private static final class TowerOverlay {

        private final TowerConnectionsPacket packet;
        private final Link[] links;
        private long expiresAt;

        private TowerOverlay(ChunkPosition tower, TowerConnectionsPacket packet) {
            this.packet = packet;
            links = new Link[packet.getLinkCount()];
            double startX = tower.chunkPosX + 0.5;
            double startY = tower.chunkPosY + 18.12;
            double startZ = tower.chunkPosZ + 0.5;
            for (int index = 0; index < links.length; index++) {
                double endX = packet.getLinkX(index) + 0.5;
                double endY = packet.getLinkY(index) + 1.16;
                double endZ = packet.getLinkZ(index) + 0.5;
                links[index] = new Link(
                        new Beam(startX, startY, startZ, endX, endY, endZ),
                        new Beam(endX, endY - 0.09, endZ, endX, endY + 0.14, endZ),
                        packet.isLinkActive(index) ? WORKING : IDLE
                );
            }
        }
    }

    private static final class Link {

        private final Beam beam;
        private final Beam marker;
        private final int color;

        private Link(Beam beam, Beam marker, int color) {
            this.beam = beam;
            this.marker = marker;
            this.color = color;
        }
    }

    private static final class Beam {

        private final double startX;
        private final double startY;
        private final double startZ;
        private final double endX;
        private final double endY;
        private final double endZ;
        private final double[] corners = new double[12];

        private Beam(
                double startX,
                double startY,
                double startZ,
                double endX,
                double endY,
                double endZ
        ) {
            this.startX = startX;
            this.startY = startY;
            this.startZ = startZ;
            this.endX = endX;
            this.endY = endY;
            this.endZ = endZ;
            double dx = endX - startX;
            double dy = endY - startY;
            double dz = endZ - startZ;
            double horizontal = Math.hypot(dx, dz);
            double length = Math.hypot(horizontal, dy);
            if (length == 0.0) {
                return;
            }
            double ux = horizontal == 0.0 ? 1.0 : dz / horizontal;
            double uz = horizontal == 0.0 ? 0.0 : -dx / horizontal;
            double vx = dy / length * uz;
            double vy = (dz * ux - dx * uz) / length;
            double vz = -dy / length * ux;
            // Two perpendicular axes give the tube a closed cross section independent of the camera.
            for (int corner = 0; corner < 4; corner++) {
                int uSign = corner == 0 || corner == 3 ? -1 : 1;
                int vSign = corner < 2 ? -1 : 1;
                int offset = corner * 3;
                corners[offset] = uSign * ux + vSign * vx;
                corners[offset + 1] = vSign * vy;
                corners[offset + 2] = uSign * uz + vSign * vz;
            }
        }

        private double radius(double cameraX, double cameraY, double cameraZ) {
            double dx = (startX + endX) * 0.5 - cameraX;
            double dy = (startY + endY) * 0.5 - cameraY;
            double dz = (startZ + endZ) * 0.5 - cameraZ;
            return Math.min(0.26, 0.045 + Math.sqrt(dx * dx + dy * dy + dz * dz) * 0.0012);
        }

        private void draw(
                Tessellator tessellator,
                double cameraX,
                double cameraY,
                double cameraZ,
                double radius
        ) {
            for (int vertex : TUBE_FACES) {
                boolean end = vertex >= 4;
                int offset = (vertex & 3) * 3;
                tessellator.addVertex(
                        (end ? endX : startX) - cameraX + corners[offset] * radius,
                        (end ? endY : startY) - cameraY + corners[offset + 1] * radius,
                        (end ? endZ : startZ) - cameraZ + corners[offset + 2] * radius
                );
            }
        }
    }
}
