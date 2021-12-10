package com.impact.common.item;

import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class KineticRotors extends Item implements ITieredDamagedItems {

    int x = 4;
    private final static int WEEK_SECOND = 604800;

    private static final KineticRotors kineticRotors = new KineticRotors();
    private final IIcon[] icons = new IIcon[x + 1];

    public static KineticRotors getInstance() {
        return kineticRotors;
    }

    public void registerItem() {
        super.setHasSubtypes(true);
        final String unlocalizedName = "impact_kinetic_rotors";
        super.setUnlocalizedName(unlocalizedName);
//        super.setMaxDamage(99);
        super.setMaxStackSize(1);
        GameRegistry.registerItem(getInstance(), unlocalizedName);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        for (int i = 0; i <= x; i++) {
            icons[i] = reg.registerIcon(Refstrings.MODID + ":impact_metatool.01/rotor." + i);
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < icons.length; i++) {
            ItemStack stack = new ItemStack(item, 1, i);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("rotorDamage", WEEK_SECOND);
            stack.stackTagCompound = nbt;
            list.add(stack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icons[meta];
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
        list.add("Rotors for Impact Wind Generator");
        int damage = stack.getTagCompound().getInteger("rotorDamage");
        double damageCalc = ((100F * (float) damage) / (float) WEEK_SECOND);
        list.add("Damage: " + new DecimalFormat("#.00").format(damageCalc) + "%");
        list.add("Real " + EnumChatFormatting.GREEN + "Days" + EnumChatFormatting.GRAY + " Working: " + EnumChatFormatting.GREEN + damage / (60 * 60 * 24) + "d");
        list.add("Real "+ EnumChatFormatting.GREEN + "Hours" + EnumChatFormatting.GRAY + " Working: " + EnumChatFormatting.GREEN + damage / (60 * 60) + "h");
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1D - (stack.getTagCompound().getInteger("rotorDamage") / (double) WEEK_SECOND);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasTagCompound();
    }

    @Override
    public int getCoefficient(ItemStack stack) {
        return stack.getItemDamage();
    }

    @Override
    public boolean isBroken(ItemStack stack) {
        return stack.getTagCompound().getInteger("rotorDamage") <= 0;
    }

    @Override
    public boolean isNew(ItemStack stack) {
        return stack.getTagCompound().getInteger("rotorDamage") == WEEK_SECOND;
    }

    @Override
    public Color getColor(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0: return new Color(91, 91, 91);
            case 1: return new Color(190, 101, 47);
            case 2: return new Color(172, 201, 188);
            case 3: return new Color(180, 102, 153);
            case 4: return new Color(62, 113, 187);
        }
        return Color.WHITE;
    }
}
