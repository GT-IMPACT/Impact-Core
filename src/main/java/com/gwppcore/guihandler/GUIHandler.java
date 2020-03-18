package com.gwppcore.guihandler;

import com.gwppcore.GTSU.container.ContainerGTSU;
import com.gwppcore.GTSU.gui.GuiGTSU;
import com.gwppcore.GTSU.tileentity.TileEntityGTSU;
import com.gwppcore.item.Circuit_Programmer.GT_Container_CircuitProgrammer;
import com.gwppcore.item.Circuit_Programmer.GT_GUIContainer_CircuitProgrammer;
import com.gwppcore.modChest.Steel_Chest.ContainerSteelChest;
import com.gwppcore.modChest.Steel_Chest.GuiSteelChest;
import com.gwppcore.modChest.Steel_Chest.TESteelChest;
import com.gwppcore.modChest.WroughtIron_Chest.ContainerWroughtIronChest;
import com.gwppcore.modChest.WroughtIron_Chest.GuiWroughtIronChest;
import com.gwppcore.modChest.WroughtIron_Chest.TEWroughtIronChest;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

    public static final int GUI_ID_CIRCUITPROGRAMMER = 1, GUI_ID_GTSU = 2, GUI_ID_WroughtIronChest = 3, GUI_ID_SteelChest = 4;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity entity = world.getTileEntity(x, y, z);
        if (entity == null) return null;
        switch (ID) {
            case GUI_ID_CIRCUITPROGRAMMER:
                    return new GT_Container_CircuitProgrammer(player.inventory);
            case GUI_ID_GTSU:
                if(entity instanceof TileEntityGTSU)
                    return new ContainerGTSU((TileEntityGTSU)entity, player);
            case GUI_ID_WroughtIronChest:
                if (entity instanceof TEWroughtIronChest)
                    return new ContainerWroughtIronChest((TEWroughtIronChest) entity, player.inventory);
            case GUI_ID_SteelChest:
                if (entity instanceof TESteelChest)
                    return new ContainerSteelChest((TESteelChest) entity, player.inventory);

            default:
                    return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity == null) return null;
        switch (ID) {
            case GUI_ID_CIRCUITPROGRAMMER:
                return new GT_GUIContainer_CircuitProgrammer(player.inventory);
            case GUI_ID_GTSU:
                if (entity instanceof TileEntityGTSU)
                    return new GuiGTSU(ID, new ContainerGTSU((TileEntityGTSU) entity, player));
            case GUI_ID_WroughtIronChest:
                if (entity instanceof TEWroughtIronChest)
                    return new GuiWroughtIronChest((TEWroughtIronChest) entity, player.inventory);
            case GUI_ID_SteelChest:
                if (entity instanceof TESteelChest)
                    return new GuiSteelChest((TESteelChest) entity, player.inventory);

            default:
                return null;
        }
    }

}