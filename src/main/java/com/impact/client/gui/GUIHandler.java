package com.impact.client.gui;

import static com.impact.core.impactLog.INFO;
import static com.impact.core.impactLog.WARNING;

import com.impact.impact;
import com.impact.mods.asp.client.GuiAdvSolarPanel;
import com.impact.mods.asp.common.te.TileEntitySolarPanel;
import com.impact.mods.gregtech.gui.Container_LapTopSetting;
import com.impact.mods.gregtech.gui.GUI_LapTopSetting;
import com.impact.mods.railcraft.carts.item.client.OpenableGUI;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

  public static final int GUI_ID_Solar = 0, GUI_ID_Carts = 2, GUI_ID_LapTop = 3;

  public GUIHandler() {
    NetworkRegistry.INSTANCE.registerGuiHandler(impact.instance, this);
  }

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    final TileEntity te = world.getTileEntity(x, y, z);
    Entity e = world.getEntityByID(x);

    switch (ID) {
      case GUI_ID_Solar:
        if (te instanceof TileEntitySolarPanel) {
          return ((TileEntitySolarPanel) te).getGuiContainer(player.inventory);
        }
      case GUI_ID_Carts:
        if (e instanceof OpenableGUI) {
          return ((OpenableGUI) e).getServerGuiElement(ID, player, world, x, y, z);
        }
      case GUI_ID_LapTop:
        if (world.getTileEntity(x, y, z) instanceof IGregTechTileEntity) {
          IGregTechTileEntity gte = (IGregTechTileEntity) world.getTileEntity(x, y, z);
          return new Container_LapTopSetting(player.inventory, gte);
      }
      INFO("GUIHandler Server - Loaded");
    }
    WARNING("GUIHandler Server - Not Loaded");
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    TileEntity te = world.getTileEntity(x, y, z);
    Entity e = world.getEntityByID(x);

    switch (ID) {
      case GUI_ID_Solar:
        if (te instanceof TileEntitySolarPanel) {
          return new GuiAdvSolarPanel((TileEntitySolarPanel) te, player.inventory);
        }
      case GUI_ID_Carts:
        if (e instanceof OpenableGUI) {
          return ((OpenableGUI) e).getClientGuiElement(ID, player, world, x, y, z);
        }
      case GUI_ID_LapTop:
        if (world.getTileEntity(x, y, z) instanceof IGregTechTileEntity) {
          IGregTechTileEntity gte = (IGregTechTileEntity) world.getTileEntity(x, y, z);
          return new GUI_LapTopSetting(player.inventory, gte, gte.getInventoryName());
        }
        INFO("GUIHandler Client - Loaded");
    }
    WARNING("GUIHandler Client - Not Loaded");
    return null;
  }
}