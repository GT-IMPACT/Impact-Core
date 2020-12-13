package com.impact.mods.GTScanner;

import com.impact.core.CommonProxy;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import gregtech.common.GT_UndergroundOil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class GTScanner {

    public static KeyBinding checkOre;
    public static KeyBinding checkOil;
    public static GTScanner baseClassInstance;
    private static Minecraft mc = Minecraft.getMinecraft();
    public boolean isScan = false;
    public FluidStack unerOil = null;
    Map chunkInfo = new HashMap<>();
    int counter;

    public static void preInit() {
        CommonProxy.register_event(new GTScanner());
        GTScanner.init(new GTScanner());
    }

    public static void init(GTScanner baseClassInstance) {
        GTScanner.baseClassInstance = baseClassInstance;
        checkOre = new KeyBinding("Scan Ores on/off", 44, "GT Scanner Mod");
        ClientRegistry.registerKeyBinding(checkOre);

//        checkOil = new KeyBinding("Scan UnderOils on/off", 45, "GT Scanner Mod");
//        ClientRegistry.registerKeyBinding(checkOil);
    }

    public void SetScan(boolean value) {
        EnumChatFormatting[] color = new EnumChatFormatting[] {
                EnumChatFormatting.GREEN,
                EnumChatFormatting.WHITE,
                EnumChatFormatting.RED
        };
        String[] message;
        if (value) {
            message = new String[]{
                    "[GT Scan] On",
                    "[A] - Amount, [Y] - Y Coord"
            };
        }
        else message = new String[]{"[GT Scan] Off"};
        for (int i = 0; i < message.length; i++){
            ChatComponentText chatComponentText = new ChatComponentText(message[i]);
            ChatStyle chatComponentStyle = chatComponentText.getChatStyle();
            if (value) chatComponentStyle.setColor(color[i]);
            else chatComponentStyle.setColor(color[2]);

            mc.thePlayer.addChatMessage(chatComponentText);
        }
        this.isScan = value;
        if (this.isScan) scan();
    }

    public void scan() {
        this.chunkInfo.clear();
        int chunkCoordX = mc.thePlayer.chunkCoordX;
        int chunkCoordZ = mc.thePlayer.chunkCoordZ;
        World world = mc.thePlayer.worldObj;

        for (int x = chunkCoordX * 16; x <= chunkCoordX * 16 + 16; x++) {
            for (int z = chunkCoordZ * 16; z <= chunkCoordZ * 16 + 16; z++) {
                for (int y = 0; y <= world.getActualHeight(); y++) {

                    Block block = world.getBlock(x, y, z);
                    String blockName = block.getLocalizedName();
                    if (blockName.contains("ore")) {
                        int meta = world.getBlockMetadata(x, y, z);
                        for (ItemStack drop : block.getDrops(world, x, y, z, meta, 0)) {

                            if (drop.getDisplayName().contains("Ore") && !drop.getDisplayName().contains("Crushed")) {

                                if (this.chunkInfo.containsKey(drop.getDisplayName())) {

                                    Data oreData1 = (Data) this.chunkInfo.get(drop.getDisplayName());
                                    oreData1.count++;
                                    if (oreData1.height < y) {
                                        oreData1.height = y;
                                    }

                                    continue;
                                }
                                this.chunkInfo.put(drop.getDisplayName(), new Data());
                                Data Data = (Data) this.chunkInfo.get(drop.getDisplayName());
                                Data.height = y;
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void RenderGameOverlayEvent(RenderGameOverlayEvent event) {
        if (this.isScan) {

            this.counter++;
            if (this.counter % 500 == 0) {
                scan();
                this.counter = 0;
            }
            if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
                ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
                FontRenderer fontRender = mc.fontRenderer;
                int height = res.getScaledHeight();
                mc.entityRenderer.setupOverlayRendering();

                int x = 0;
                int y = height;

                y -= fontRender.FONT_HEIGHT * 16;

                String text;
                for (Object key : this.chunkInfo.keySet()) {

                    Data oreData = (Data) this.chunkInfo.get(key);

                    if (!(key instanceof FluidStack)) {
                        text =  EnumChatFormatting.WHITE + key.toString() + " - A: " +
                                EnumChatFormatting.YELLOW + oreData.count + EnumChatFormatting.WHITE + " Y: " +
                                EnumChatFormatting.YELLOW + oreData.height + EnumChatFormatting.RESET;
                        y -= fontRender.FONT_HEIGHT;
                        fontRender.drawStringWithShadow(text, x, y, 0);
                    }
                }
            }
        }
    }
    public void checkOil() {
        int pX = ((int) mc.thePlayer.posX) >> 4;
        int pZ = ((int) mc.thePlayer.posZ) >> 4;
        World world = mc.thePlayer.worldObj;
        String message;
        unerOil = GT_UndergroundOil.undergroundOilReadInformation(world.getChunkFromChunkCoords(pX, pZ));
        if (unerOil != null) {
            if (unerOil.amount > 0) {

                message = " " + EnumChatFormatting.WHITE + unerOil.getLocalizedName() +
                        " - A: " + EnumChatFormatting.YELLOW + unerOil.amount + EnumChatFormatting.WHITE + " L" + EnumChatFormatting.RESET;

                ChatComponentText chatComponentText = new ChatComponentText(message);
                mc.thePlayer.addChatMessage(chatComponentText);
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (GTScanner.checkOre.isPressed())
            SetScan(!this.isScan);
        //else if (GTScanner.checkOil.isPressed())
        //    checkOil();
    }

    public class Data {
        public int height = 0;
        public int amount = 0;
        public int count = 1;
    }

}


