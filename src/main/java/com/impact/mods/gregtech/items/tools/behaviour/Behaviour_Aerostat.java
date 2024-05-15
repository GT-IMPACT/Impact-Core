package com.impact.mods.gregtech.items.tools.behaviour;

import com.gtnewhorizons.modularui.ModularUI;
import com.impact.addon.gt.api.aerostat.IAeroStat;
import com.impact.addon.gt.api.position.IPosition;
import com.impact.client.gui.GUIHandler;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.network.NetworkPackets;
import com.impact.util.Utilits;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import space.impact.packet_network.network.NetworkHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static space.gtimpact.virtual_world.extras.PlayerExtKt.send;

public class Behaviour_Aerostat extends Behaviour_None {
	
	public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Aerostat();
	
	public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
		TileEntity te = aWorld.getTileEntity(aX, aY, aZ);
		if (aPlayer instanceof EntityPlayerMP && te instanceof IGregTechTileEntity) {
			IGregTechTileEntity gte = (IGregTechTileEntity) te;
			IMetaTileEntity aerostat = gte.getMetaTileEntity();
			if (aerostat instanceof GTMTE_Aerostat && ((GTMTE_Aerostat) aerostat).mMachine) {
				GTMTE_Aerostat as = (GTMTE_Aerostat) aerostat;
				if (!aPlayer.isSneaking()) {

					Utilits.openGui(aPlayer, GUIHandler.GUI_ID_FirstAerostat, gte);
					List<String> names = new ArrayList<>();

					for (IAeroStat pos : as.currentLocationPlatforms) {
						names.add(pos.getName());
					}

					List<String> toClient = new ArrayList<>();
					toClient.add(as.getOwner());
					toClient.add(as.getName());
					toClient.addAll(names);
					String[] pArray = new String[toClient.size()];
					NetworkHandler.sendToPlayer(
							aPlayer,
							NetworkPackets.StreamPacket.transaction(toClient.toArray(pArray))
					);
				} else {
					Utilits.openGui(aPlayer, GUIHandler.GUI_ID_FirstAerostat + 1, gte);
				}
			}
			return true;
		}
		return aPlayer instanceof EntityPlayerMP;
	}
	
	public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
		aList.add("This is an Aerostat");
		aList.add("Shift + RClick to set name station");
		aList.add("RClick to select traveling stations");
		return aList;
	}
}