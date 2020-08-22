package com.impact.System;


import com.impact.block.itemblock.IB_IGlass;
import com.impact.util.ToggleMetaData;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import cpw.mods.fml.relauncher.*;
import cpw.mods.fml.common.eventhandler.*;

public class EventHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(final MouseEvent event) {
        final EntityPlayer entityPlayer = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        if (Keyboard.isKeyDown(184) || Keyboard.isKeyDown(56) || Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29)) {
            final ItemStack itemStack = entityPlayer.getHeldItem();
            if (itemStack != null && itemStack.getItem() instanceof IB_IGlass) {
                if (event.dwheel != 0) {
                    PacketHandler.sendPacketToServer(new ToggleMetaData(event.dwheel > 0));
                }
                event.setCanceled(true);
            }
        }
    }
}