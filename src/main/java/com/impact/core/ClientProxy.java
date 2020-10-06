package com.impact.core;

import com.impact.common.block.QuantumStuffRender;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.client.render.TESR_SETether;
import com.impact.common.te.TE_NqTether;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderInfo() {
        Block_QuantumStuff.renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(Block_QuantumStuff.renderID, new QuantumStuffRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TE_NqTether.class, new TESR_SETether());
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }


}
