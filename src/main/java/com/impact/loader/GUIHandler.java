package com.impact.loader;

import com.impact.item.Circuit_Programmer.GT_Container_CircuitProgrammer;
import com.impact.item.Circuit_Programmer.GT_GUIContainer_CircuitProgrammer;
import com.impact.mods.GTSU.container.ContainerGTSU;
import com.impact.mods.GTSU.gui.GuiGTSU;
import com.impact.mods.GTSU.tileentity.TileEntityGTSU;
import com.impact.mods.modChest.BASE.Container_BaseChest;
import com.impact.mods.modChest.BASE.Gui_BaseChest;
import com.impact.mods.modChest.BASE.TE_BaseChest;
import com.impact.mods.modSolar.client.GuiAdvSolarPanel;
import com.impact.mods.modSolar.common.TE.TileEntitySolarPanel;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

    public static final int GUI_ID_GTSU = 0, GUI_ID_CIRCUITPROGRAMMER = 1, GUI_ID_Chest = 2, GUI_ID_Solar = 3;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te != null) {
            switch (ID) {
                case GUI_ID_GTSU:
                    return new ContainerGTSU((TileEntityGTSU) te, player);
                case GUI_ID_CIRCUITPROGRAMMER:
                    return new GT_Container_CircuitProgrammer(player);
                case GUI_ID_Chest:
                    return new Container_BaseChest((TE_BaseChest) te, player.inventory);
                case GUI_ID_Solar:
                    return ((TileEntitySolarPanel) te).getGuiContainer(player.inventory);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null) {
            switch (ID) {
                case GUI_ID_GTSU:
                    return new GuiGTSU(new ContainerGTSU((TileEntityGTSU) te, player));
                case GUI_ID_CIRCUITPROGRAMMER:
                    return new GT_GUIContainer_CircuitProgrammer(player);
                case GUI_ID_Chest:
                    return new Gui_BaseChest((TE_BaseChest) te, player.inventory);
                case GUI_ID_Solar:
                    return new GuiAdvSolarPanel((TileEntitySolarPanel) te, player.inventory);
            }
        }
        return null;
    }
}