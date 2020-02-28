package com.gwppcore.guihandler;

import com.gwppcore.GTSU.container.ContainerGTSU;
import com.gwppcore.GTSU.gui.GuiGTSU;
import com.gwppcore.GTSU.tileentity.TileEntityGTSU;
import com.gwppcore.item.Circuit_Programmer.GT_Container_CircuitProgrammer;
import com.gwppcore.item.Circuit_Programmer.GT_GUIContainer_CircuitProgrammer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 1:
                return new GT_Container_CircuitProgrammer(player.inventory);
        }

        TileEntity entity = world.getTileEntity(x, y, z);
        if(entity instanceof TileEntityGTSU) {
            return new ContainerGTSU(player, (TileEntityGTSU)entity);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            switch (ID) {
                case 1:
                    return new GT_GUIContainer_CircuitProgrammer(player.inventory);
            }
        } else
            return getServerGuiElement(ID, player, world, x, y, z);

        TileEntity entity = world.getTileEntity(x, y, z);
        if(entity instanceof TileEntityGTSU) {
            return new GuiGTSU(ID, new ContainerGTSU(player, (TileEntityGTSU)entity));
        }

        return null;
    }
}