package com.impact.common.item;

import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class Core_Items3 extends Item {

    public static int x = 2; //количество предметов

    private static final Core_Items3 Core_Items3 = new Core_Items3();
    private final IIcon[] icons = new IIcon[x + 1];

    private Core_Items3() {
    }

    public static Core_Items3 getInstance() {
        return Core_Items3;
    }

    public void registerItem() {
        super.setHasSubtypes(true);
        final String unlocalizedName = "impact_item3";
        super.setUnlocalizedName(unlocalizedName);
        super.setMaxStackSize(8);
        GameRegistry.registerItem(getInstance(), unlocalizedName);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        for (int i = 0; i < x; i++) {
            icons[i] = reg.registerIcon(Refstrings.MODID + ":impact_metaitem.03/" + i);
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icons[meta];
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < x; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
        switch (stack.getItemDamage()) {
            case 0:
                list.add("Capacity: 0 / 1,000 particles");
                break;
            case 1:
                list.add("Capacity: 1,000 / 1,000 particles");
                break;
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 0.0d;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    public ItemStack get(int meta) {
        return new ItemStack(getInstance(), 1, meta);
    }

    public ItemStack get(int meta, int amount) {
        return new ItemStack(getInstance(), amount, meta);
    }
}