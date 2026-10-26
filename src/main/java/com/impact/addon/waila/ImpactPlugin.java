package com.impact.addon.waila;

import static net.minecraft.util.StatCollector.translateToLocalFormatted;

import com.impact.common.te.TE_DryingRack;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class ImpactPlugin implements IWailaDataProvider {

    public static void callbackRegister(IWailaRegistrar registrar) {
        ImpactPlugin provider = new ImpactPlugin();
        registrar.registerBodyProvider(provider, TE_DryingRack.class);
        registrar.registerNBTProvider(provider, TE_DryingRack.class);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        NBTTagCompound tag = accessor.getNBTData();
        tooltip.add(translateToLocalFormatted("impact.waila.drying_rack.progress",
                tag.getInteger("dryingRack.time") / 20, tag.getInteger("dryingRack.maxTime") / 20));
        return tooltip;
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
                                      World world, int x, int y, int z) {
        TE_DryingRack rack = (TE_DryingRack) tile;
        tag.setInteger("dryingRack.time", rack.currentTime);
        tag.setInteger("dryingRack.maxTime", rack.maxTime);
        return tag;
    }
}
