package com.gwppcore.main;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

}
