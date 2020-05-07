package com.impact.item;

import com.impact.System.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class WoodBrickFormTool extends Item {

    public static WoodBrickFormTool WoodBrickFormTool = new WoodBrickFormTool();
    private IIcon icons;

    private WoodBrickFormTool() {
    }

    public static WoodBrickFormTool getInstance() {
        return WoodBrickFormTool;
    }

    public void registerItem() {
        super.setHasSubtypes(true);
        final String unlocalizedName = "impact_WoodBrickFormTool";
        super.setUnlocalizedName(unlocalizedName);
        super.setContainerItem(this);
        super.setMaxStackSize(1);
        super.setMaxDamage(50);
        super.setNoRepair();
        GameRegistry.registerItem(getInstance(), unlocalizedName);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons = reg.registerIcon(Refstrings.MODID + ":impact_metatool.01/4");
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icons;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 1; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
        list.add("Reusable: "+ (stack.getMaxDamage()-stack.getItemDamage()));
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) { return 0.0d; }

    @Override
    public boolean showDurabilityBar(ItemStack stack) { return false; }

    public ItemStack getContainerItem(ItemStack stack) {
        if (stack.getItemDamage() >= 0) {
            stack.setItemDamage(stack.getItemDamage() + 1);
            return stack;
        }
        return super.getContainerItem(stack);
    }

}
