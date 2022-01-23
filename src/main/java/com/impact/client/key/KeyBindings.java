package com.impact.client.key;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {
	
	public static KeyBinding placeItem;
	
	public static void init() {
		placeItem = new KeyBinding("Place Any Item", Keyboard.KEY_P, "Impact");
		ClientRegistry.registerKeyBinding(placeItem);
	}
}