package com.impact.mods.AE2;

import appeng.api.implementations.items.IMemoryCard;
import appeng.me.GridAccessException;
import appeng.me.cache.helpers.TunnelCollection;
import appeng.parts.p2p.PartP2PTunnel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

public abstract class PartDedicatedP2PTunnel<T extends PartDedicatedP2PTunnel> extends PartP2PTunnel<T> {
    public PartDedicatedP2PTunnel(ItemStack is) {
        super(is);
    }

    public boolean onPartActivate(EntityPlayer player, Vec3 pos) {
        ItemStack is = player.inventory.getCurrentItem();
        return (is != null && is.getItem() instanceof IMemoryCard) && super.onPartActivate(player, pos);
    }

    protected T getInput() {
        if (this.getFrequency() == 0L) {
            return null;
        } else {
            try {
                PartP2PTunnel tunnel = this.getProxy().getP2P().getInput(this.getFrequency());
                if (this.getClass().isInstance(tunnel)) {
                    return (T) tunnel;
                }
            } catch (GridAccessException var2) {
            }

            return null;
        }
    }

    protected TunnelCollection<PartP2PTunnel> getOutputs() throws GridAccessException {
        return this.getProxy().isActive() ? this.getProxy().getP2P().getOutputs(this.getFrequency(), this.getClass()) : new TunnelCollection(new ArrayList(), this.getClass());
    }
}
