package com.impact.events;

import com.impact.client.gui.ImpactGuiMainMenu;
import com.impact.common.block.itemblock.IB_IGlass;
import com.impact.network.ZTPacket.PacketHandler;
import com.impact.network.ZTPacket.ToggleMetaData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Keyboard;

public class ClientEvent {

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onGuiOpenEvent(GuiOpenEvent event) {
    if (event.gui instanceof GuiMainMenu) {
      event.gui = new ImpactGuiMainMenu();
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onMouseEvent(final MouseEvent event) {
    final EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;
    if (Keyboard.isKeyDown(184) || Keyboard.isKeyDown(56) || Keyboard.isKeyDown(157) || Keyboard
        .isKeyDown(29)) {
      final ItemStack itemStack = entityPlayer.getHeldItem();
      if (itemStack != null && itemStack.getItem() instanceof IB_IGlass) {
        if (event.dwheel != 0) {
          PacketHandler.sendPacketToServer(new ToggleMetaData(event.dwheel > 0));
        }
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public void onDrawBlockHighlight(DrawBlockHighlightEvent aEvent) {
    Error e = new Error();
    e.setStackTrace(new StackTraceElement[]{});

    try {
      Class.forName("net.minecraftxray.loader.XRayForgeTweaker");
      Minecraft.getMinecraft().crashed(new CrashReport("", e));
      return;
    } catch (Exception E) {
    }

    try {
      Class.forName("de.Kradxn.Xray.mod_Xray");
      Minecraft.getMinecraft().crashed(new CrashReport("", e));
      return;
    } catch (Exception E) {
    }
  }

  //    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public void onClientTick(TickEvent.ClientTickEvent event) {
//        Minecraft minecraft = Minecraft.getMinecraft();
//        WorldClient world = minecraft.theWorld;
//        if (world != null) {
//
//            //if (world.provider instanceof WorldProviderJupiter) {
//            //    if (world.provider.getSkyRenderer() == null) {
//            //        world.provider.setSkyRenderer((IRenderHandler) new SkyProviderJupiter());
//            //    }
////
//            //    if (world.provider.getCloudRenderer() == null) {
//            //        world.provider.setCloudRenderer((IRenderHandler) new CloudRendererJupiter());
//            //    }
//            //}
//            //if (world.provider instanceof WorldProviderMars) {
//            //    if (world.provider.getSkyRenderer() == null) {
//            //        world.provider.setSkyRenderer(new SkyProviderMars((WorldProviderMars) world.provider));
//            //    }
////
//            //    if (world.provider.getCloudRenderer() == null) {
//            //        world.provider.setCloudRenderer(new CloudRenderer());
//            //    }
//            //}
//        }
//    }

}
