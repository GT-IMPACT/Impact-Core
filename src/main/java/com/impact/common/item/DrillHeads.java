package com.impact.common.item;

import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class DrillHeads extends Item implements ITieredDamagedItems {

    int x = 7; //Iron (Steam), Steel(LV), Stain.Steel(MV), Titanium(HV), Tang.Steel(EV), Inconel-690(IV), Iridium(LuV), Neutronium(ZPM)

    private static final DrillHeads drillHeads = new DrillHeads();
    private final IIcon[] icons = new IIcon[x + 1];

    public static DrillHeads getInstance() {
        return drillHeads;
    }

    public void registerItem() {
        super.setHasSubtypes(true);
        final String unlocalizedName = "impact_drillHeads";
        super.setUnlocalizedName(unlocalizedName);
        super.setMaxStackSize(1);
        GameRegistry.registerItem(getInstance(), unlocalizedName);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        for (int i = 0; i <= x; i++) {
            icons[i] = reg.registerIcon(Refstrings.MODID + ":impact_metatool.01/drill_head." + i);
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < icons.length; i++) {
            ItemStack stack = new ItemStack(item, 1, i);
            if (stack.hasTagCompound()) {
                stack.stackTagCompound.setInteger("drillDamage", damage(stack));
            } else {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setInteger("drillDamage", damage(stack));
                stack.stackTagCompound = nbt;
            }
            list.add(stack);
        }
    }
    
    @Override
    public void onCreated(ItemStack stack, World w, EntityPlayer p) {
        super.onCreated(stack, w, p);
        if (stack.hasTagCompound()) {
            stack.stackTagCompound.setInteger("drillDamage", damage(stack));
        } else {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("drillDamage", damage(stack));
            stack.stackTagCompound = nbt;
        }
    }
    
    public static ItemStack create(ItemStack stack) {
        if (stack.hasTagCompound()) {
            stack.stackTagCompound.setInteger("drillDamage", damage(stack));
        } else {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("drillDamage", damage(stack));
            stack.stackTagCompound = nbt;
        }
        return stack;
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
        list.add("Drill Heads for Impact Miners");
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("drillDamage")) {
            int damage = stack.getTagCompound().getInteger("drillDamage");
            list.add(EnumChatFormatting.WHITE + "Durability: " + EnumChatFormatting.GREEN + GT_Utility.formatNumbers(damage) + " / " + GT_Utility.formatNumbers(damage(stack)));
            String[] a = {"Iron", "Steel", "Stainless Steel", "Titanium", "Tungstensteel", "Inconel-690", "Iridium", "Neutronium"};
            list.add(EnumChatFormatting.WHITE + a[stack.getItemDamage()] + " " + EnumChatFormatting.YELLOW + stack.getItemDamage() + " lvl");
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1D - (stack.getTagCompound().getInteger("drillDamage") / (double) damage(stack));
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
        return stack.getTagCompound().getInteger("drillDamage") <= 0;
    }

    @Override
    public boolean isNew(ItemStack stack) {
        return stack.getTagCompound().getInteger("drillDamage") == damage(stack);
    }
    
    private static int damage(ItemStack stack) {
        int meta = stack.getItemDamage();
        return 10_000 * ((meta + 1) << meta);
    }

    @Override
    public Color getColor(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0: return new Color(200, 200, 200); // Iron
            case 1: return new Color(128, 128, 128); // Steel
            case 2: return new Color(200, 200, 220); // Stain.Steel
            case 3: return new Color(220, 160, 240); // Titanium
            case 4: return new Color(100, 100, 160); // Tang.Steel
            case 5: return new Color(221,238,1);     // Inconel-690
            case 6: return new Color(240, 240, 245); // Iridium
            case 7: return new Color(255, 255, 255); // Neutronium
        }
        return Color.WHITE;
    }
}