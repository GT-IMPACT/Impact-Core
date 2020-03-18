package com.gwppcore.item.Circuit_Programmer;

import com.gwppcore.gwppcore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.GT_Values;
import gregtech.api.items.GT_Generic_Item;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

import static com.gwppcore.guihandler.GUIHandler.GUI_ID_CIRCUITPROGRAMMER;

public class CircuitProgrammer extends GT_Generic_Item implements IElectricItem {

    public CircuitProgrammer() {
        super("BWCircuitProgrammer", "Circuit Programmer", "Programs Integrated Circuits");
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setHasSubtypes(false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        if (aStack != null && aStack.getTagCompound() != null);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        if (ElectricItem.manager.use(aStack, 100, aPlayer)) {
            aPlayer.openGui(gwppcore.instance, GUI_ID_CIRCUITPROGRAMMER, aWorld, 0, 0, 0);
        }
        return aStack;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List itemList) {
        ItemStack itemStack = new ItemStack(this, 1);
        if (this.getChargedItem(itemStack) == this) {
            ItemStack charged = new ItemStack(this, 1);
            ElectricItem.manager.charge(charged, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
            itemList.add(charged);
        }
        if (this.getEmptyItem(itemStack) == this) {
            itemList.add(new ItemStack(this, 1, this.getMaxDamage()));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aIconRegister) {
        this.mIcon = aIconRegister.registerIcon("gregtech:CircuitProgrammer");
    }

    public int getTier(ItemStack var1) {
        return 1;
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return 100000;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return GT_Values.V[1];
    }

}