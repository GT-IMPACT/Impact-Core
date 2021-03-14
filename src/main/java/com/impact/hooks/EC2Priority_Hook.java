package com.impact.hooks;

import com.impact.client.gui.ContainterPriority;
import com.impact.client.gui.GuiPriority;
import extracells.container.ContainerBusFluidStorage;
import extracells.gui.GuiBusFluidStorage;
import extracells.part.PartFluidStorage;
import gloomyfolken.hooklib.asm.Hook;
import net.minecraft.entity.player.EntityPlayer;

public class EC2Priority_Hook {

  @Hook(injectOnExit = true, isMandatory = true)
  public static Object getClientGuiElement(PartFluidStorage part, EntityPlayer player) {
    if (player.isSneaking()) {
      return new GuiPriority(new ContainterPriority(player.inventory, part), player);
    }
    return new GuiBusFluidStorage(part, player);
  }

  @Hook(injectOnExit = true, isMandatory = true)
  public static Object getServerGuiElement(PartFluidStorage part, EntityPlayer player) {
    if (player.isSneaking()) {
      return new ContainterPriority(player.inventory, part);
    }
    return new ContainerBusFluidStorage(part, player);
  }
}
