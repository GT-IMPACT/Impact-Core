package com.impact.mods.gregtech.items.tools.behaviour;

import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;


public class Behaviour_Aerostat extends Behaviour_None {
	
	public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Aerostat();
	
    @Override
    public boolean onItemUseFirst(
            GT_MetaBase_Item item,
            ItemStack stack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int side,
            float hitX,
            float hitY,
            float hitZ
    ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof IGregTechTileEntity)) {
            return false;
        }
        IMetaTileEntity machine = ((IGregTechTileEntity) tile).getMetaTileEntity();
        if (!(machine instanceof GTMTE_Aerostat) || !((GTMTE_Aerostat) machine).mMachine) {
            return false;
        }
        if (!world.isRemote) {
            ((GTMTE_Aerostat) machine).openStationGui(player);
        }
        return true;
    }
	
    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item item, List<String> tooltips, ItemStack stack) {
        tooltips.add(StatCollector.translateToLocal("impact.aerostat.item_hint"));
        return tooltips;
    }
}
