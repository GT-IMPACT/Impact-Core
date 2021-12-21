package com.impact.mods.gregtech.items.tools.behaviour;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.util.Utilits;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Behaviour_OreProbe extends Behaviour_None {
	
	public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_OreProbe();
	
	public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
		if (aPlayer instanceof EntityPlayerMP) {
			Chunk ch = aWorld.getChunkFromBlockCoords(aX, aZ);
			OreVein oreVein = OreGenerator.getOreVein(ch, 0);
			if (oreVein != null) {
				int countOres = oreVein.ores.size();
				String name = oreVein.ores.get(Utilits.getRandom(0, countOres - 1)).getDisplayName();
				GT_Utility.sendChatToPlayer(aPlayer, "Presumably there is Ore here: " + EnumChatFormatting.YELLOW + name);
			} else {
				GT_Utility.sendChatToPlayer(aPlayer, "Not found Ores");
			}
			
			return true;
		}
		return false;
	}
}