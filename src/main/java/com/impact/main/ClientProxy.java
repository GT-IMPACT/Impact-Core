package com.impact.main;

import com.impact.GregTech.casings.glass1.glassed.GlassBlocks;
import com.impact.GregTech.casings.glass1.glassed.GlassBlocksRender;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import static com.impact.main.ConfigHandler.CONFIG_HANDLER;

public class ClientProxy extends CommonProxy {


	@Override
    public void registerRenderInfo()
    {
		MinecraftForge.EVENT_BUS.register(CONFIG_HANDLER);

        GlassBlocks.renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(GlassBlocks.renderID, new GlassBlocksRender());

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
