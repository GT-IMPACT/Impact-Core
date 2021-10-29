package com.impact.events;

import com.impact.client.gui.ImpactGuiMainMenu;
import com.impact.client.key.KeyBindings;
import com.impact.common.block.itemblock.IB_IGlass;
import com.impact.core.Config;
import com.impact.network.ImpactNetwork;
import com.impact.network.ImpactPacketMetaDataPacket;
import com.impact.network.ImpactPacketPlacedItem;
import com.impact.util.Utilits;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Keyboard;

public class ClientEvent {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiOpenEvent(GuiOpenEvent event) {
		if (Config.mainMenu && event.gui instanceof GuiMainMenu) {
			event.gui = new ImpactGuiMainMenu();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouseEvent(final MouseEvent event) {
		final EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;
		if (Keyboard.isKeyDown(Keyboard.KEY_RMENU) ||
				Keyboard.isKeyDown(Keyboard.KEY_LMENU) ||
				Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) ||
				Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			final ItemStack itemStack = entityPlayer.getHeldItem();
			if (itemStack != null && itemStack.getItem() instanceof IB_IGlass) {
				if (event.dwheel != 0) {
					ImpactNetwork.INSTANCE.sendToServer(new ImpactPacketMetaDataPacket(
							event.dwheel > 0, entityPlayer));
				}
				event.setCanceled(true);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Config.placedItems) {
			if (KeyBindings.placeItem.isPressed()) {
				EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
				WorldClient world = Minecraft.getMinecraft().theWorld;
				MovingObjectPosition mop = Utilits.raytraceFromEntity(world, player, 4.5D);
				if (mop != null) {
					ImpactNetwork.INSTANCE.sendToServer(new ImpactPacketPlacedItem(
							(byte) mop.sideHit, mop.blockX, mop.blockY, mop.blockZ, player));
				}
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
		} catch (Exception ignored) {
		}
		
		try {
			Class.forName("de.Kradxn.Xray.mod_Xray");
			Minecraft.getMinecraft().crashed(new CrashReport("", e));
			return;
		} catch (Exception ignored) {
		}
		
		try {
			Class.forName("forgefuck.team.xenobyte.ModulesList");
			Minecraft.getMinecraft().crashed(new CrashReport("", e));
			return;
		} catch (Exception ignored) {
		}
		
		try {
			Class.forName("forgefuck.team.xenobyte.XenoByte");
			Minecraft.getMinecraft().crashed(new CrashReport("", e));
			return;
		} catch (Exception ignored) {
		}

	}
}
