package com.impact.mods.gregtech.items.tools.behaviour;

import com.impact.client.gui.GUIHandler;
import com.impact.mods.gregtech.enums.BiomesOreGenerator;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.network.ToClient_String;
import com.impact.util.Utilits;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Behaviour_Aerostat extends Behaviour_None {
	
	public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Aerostat();
	
	protected MetaTileEntity mMetaTileEntity;
	
	public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
		TileEntity te = aWorld.getTileEntity(aX, aY, aZ);
		
		if (aPlayer instanceof EntityPlayerMP) {
			BiomeGenBase biomeGenBase = aWorld.getBiomeGenForCoords(aX, aZ);
			BiomesOreGenerator.generateOres(0, biomeGenBase).forEach(a -> {
				if (a.stackSize > 0) {
					GT_Utility.sendChatToPlayer(aPlayer, a.getDisplayName() + " size: " + a.stackSize);
				}
			});
		}
		
		if (aPlayer instanceof EntityPlayerMP && te instanceof IGregTechTileEntity) {
			IGregTechTileEntity gte = (IGregTechTileEntity) te;
			IMetaTileEntity aerostat = gte.getMetaTileEntity();
			if (aerostat instanceof GTMTE_Aerostat && ((GTMTE_Aerostat) aerostat).mMachine) {
				GTMTE_Aerostat as = (GTMTE_Aerostat) aerostat;
				if (!aPlayer.isSneaking()) {
					Utilits.openGui(aPlayer, GUIHandler.GUI_ID_FirstAerostat, gte);
					List<String> names = new ArrayList<>();
					GTMTE_Aerostat.getRadiusAeroStates(as.playerName, gte).forEach(a -> names.add(a.aerName));
					List<String> toClient = new ArrayList<>();
					toClient.add(as.playerName);
					toClient.add(as.aerName);
					toClient.addAll(names);
					String[] pArray = new String[toClient.size()];
					new ToClient_String(toClient.toArray(pArray)).sendToPlayer((EntityPlayerMP) aPlayer);
				} else {
					Utilits.openGui(aPlayer, GUIHandler.GUI_ID_FirstAerostat + 1, gte);
				}
			}
			return true;
		}
		return aPlayer instanceof EntityPlayerMP;
	}
	
	public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
		aList.add("1");
		aList.add("123");
		return aList;
	}
	
}
