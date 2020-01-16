package com.gwppcore.main;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import static com.gwppcore.main.ConfigHandler.CONFIG_HANDLER;

public class ClientProxy extends CommonProxy {


	@Override
    public void registerRenderInfo()
    {
		MinecraftForge.EVENT_BUS.register(CONFIG_HANDLER);
    }
	
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
