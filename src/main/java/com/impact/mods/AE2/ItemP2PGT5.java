package com.impact.mods.AE2;

import appeng.api.AEApi;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import appeng.core.CreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemP2PGT5 extends Item implements IPartItem {
    public ItemP2PGT5() {
        this.setNoRepair();
        this.setUnlocalizedName("ae2gt5p2p_part");
        this.setTextureName("appliedenergistics2:ItemPart.P2PTunnel");
        this.setCreativeTab(CreativeTab.instance);
    }

    @SideOnly(Side.CLIENT)
    public int getSpriteNumber() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean extended) {
        list.add("Transfers GregTech5 power");
        list.add("Max voltage - unlimited");
        list.add("Max amperage - 64");
        list.add("Without losses and channel requirement");
        list.add("You can rename it on anvil");
    }

    @Nullable
    public IPart createPartFromItemStack(ItemStack is) {
        return new PartP2PGT5Power(is);
    }

    public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return AEApi.instance().partHelper().placeBus(is, x, y, z, side, player, w);
    }
}
