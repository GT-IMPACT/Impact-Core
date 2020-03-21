package com.impact.main;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler
{
    public void addTexturePage(){}

    public void registerRenderInfo() {}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    public World getClientWorld()
    {
        return null;
    }

    public void registerRenderers() {}

    public void load() {
    }
    public EntityPlayer getPlayerEntity(final MessageContext context)
    {
        return context.getServerHandler().playerEntity;
    }

    public EntityPlayer getEntityPlayerFromContext(final MessageContext ctx)
    {
        return ctx.getServerHandler().playerEntity;
    }
}