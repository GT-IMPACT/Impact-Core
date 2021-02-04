package com.impact.mods.GTScanner;

import com.impact.core.ClientProxy;
import com.impact.core.CommonProxy;
import com.impact.network.ImpactNetwork;
import com.impact.network.ImpactPacketGTScanner;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fluids.FluidStack;

public class GTScanner {

  public static GTScanner baseClassInstance;
  public static FluidStack udnerOil = null;
  public boolean isScan = false;
  Map chunkInfo = new HashMap<>();
  int counter;

  public static void preInit() {
    CommonProxy.register_event(new GTScanner());
    GTScanner.init(new GTScanner());
  }

  public static void init(GTScanner baseClassInstance) {
    GTScanner.baseClassInstance = baseClassInstance;
  }

  public static void setFluidStack(FluidStack aOil) {
    udnerOil = aOil;
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onKeyInput(InputEvent.KeyInputEvent event) {
    if (ClientProxy.checkOre.isPressed()) {
      SetScan(!this.isScan);
    }
  }

  @SideOnly(Side.CLIENT)
  public void SetScan(boolean value) {
    EnumChatFormatting[] color = new EnumChatFormatting[]{
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
    } else {
      message = new String[]{"[GT Scan] Off"};
    }
    for (int i = 0; i < message.length; i++) {
      ChatComponentText chatComponentText = new ChatComponentText(message[i]);
      ChatStyle chatComponentStyle = chatComponentText.getChatStyle();
      if (value) {
        chatComponentStyle.setColor(color[i]);
      } else {
        chatComponentStyle.setColor(color[2]);
      }

      Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponentText);
    }
    this.isScan = value;
    if (this.isScan) {
      scan();
    }
  }

  @SideOnly(Side.CLIENT)
  public void scan() {
    this.chunkInfo.clear();
    int chunkCoordX = Minecraft.getMinecraft().thePlayer.chunkCoordX;
    int chunkCoordZ = Minecraft.getMinecraft().thePlayer.chunkCoordZ;
    World world = Minecraft.getMinecraft().thePlayer.worldObj;

    ImpactNetwork.INSTANCE
        .sendToServer(new ImpactPacketGTScanner(Minecraft.getMinecraft().thePlayer));

    for (int x = chunkCoordX * 16; x <= chunkCoordX * 16 + 16; x++) {
      for (int z = chunkCoordZ * 16; z <= chunkCoordZ * 16 + 16; z++) {
        for (int y = 0; y <= world.getActualHeight(); y++) {

          Block block = world.getBlock(x, y, z);
          String blockName = block.getLocalizedName();
          if (blockName.contains("ore")) {
            int meta = world.getBlockMetadata(x, y, z);
            for (ItemStack drop : block.getDrops(world, x, y, z, meta, 0)) {

              if (drop.getDisplayName().contains("Ore") && !drop.getDisplayName()
                  .contains("Crushed")) {

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

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void RenderGameOverlayEvent(RenderGameOverlayEvent event) {
    if (this.isScan) {

      this.counter++;
      if (this.counter % 500 == 0) {
        scan();
        this.counter = 0;
      }
      if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(),
            Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        FontRenderer fontRender = Minecraft.getMinecraft().fontRenderer;
        int height = res.getScaledHeight();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();

        int x = 0;
        int y = height;

        y -= fontRender.FONT_HEIGHT * 16;

        String text;
        for (Object key : this.chunkInfo.keySet()) {

          Data oreData = (Data) this.chunkInfo.get(key);

          if (!(key instanceof FluidStack)) {
            text = " " + EnumChatFormatting.WHITE + key.toString() + " - A: " +
                EnumChatFormatting.YELLOW + oreData.count + EnumChatFormatting.WHITE + " Y: " +
                EnumChatFormatting.YELLOW + oreData.height + EnumChatFormatting.RESET;
            y -= fontRender.FONT_HEIGHT;
            fontRender.drawStringWithShadow(text, x, y, 0);
          }
        }

        if (udnerOil != null) {
          if (udnerOil.amount > 0 && udnerOil.amount < 100) {
            text = " ▉ " + EnumChatFormatting.WHITE + udnerOil.getLocalizedName() +
                " - A: " + EnumChatFormatting.YELLOW + udnerOil.amount + EnumChatFormatting.WHITE
                + " L" + EnumChatFormatting.RESET;
            y -= fontRender.FONT_HEIGHT;
            fontRender.drawStringWithShadow(text, x, y, udnerOil.getFluid().getColor(udnerOil));
          }
        }
      }
    }
  }

  public class Data {

    public int height = 0;
    public int amount = 0;
    public int count = 1;
  }

}


