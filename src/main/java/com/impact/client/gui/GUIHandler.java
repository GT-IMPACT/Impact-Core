package com.impact.client.gui;

import com.impact.mods.ASP.client.GuiAdvSolarPanel;
import com.impact.mods.ASP.common.TE.TileEntitySolarPanel;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.impact.core.impactLog.INFO;
import static com.impact.core.impactLog.WARNING;

public class GUIHandler implements IGuiHandler {

    public static final int GUI_ID_Solar = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te == null) return null;
        switch (ID) {
            case GUI_ID_Solar:
                if (te instanceof TileEntitySolarPanel)
                    return ((TileEntitySolarPanel) te).getGuiContainer(player.inventory);
                INFO("GUIHandler Server - Loaded");
        }
        WARNING("GUIHandler Server - Not Loaded");
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te == null) return null;
        switch (ID) {
            case GUI_ID_Solar:
                if (te instanceof TileEntitySolarPanel)
                    return new GuiAdvSolarPanel((TileEntitySolarPanel) te, player.inventory);
                INFO("GUIHandler Client - Loaded");
        }
        WARNING("GUIHandler Client - Not Loaded");
        return null;
    }
}