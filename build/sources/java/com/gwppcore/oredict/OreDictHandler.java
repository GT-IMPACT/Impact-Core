package com.gwppcore.oredict;

import com.gwppcore.item.ItemList;

import cpw.mods.fml.common.Loader;
import gregtech.api.util.GT_ModHandler;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHandler {
	
	public static void reg_additional() {
		OreDictionary.registerOre("CoinGWPP", ItemList.Coin.getIS());

	}
	
	public static void register_all() {
		reg_additional();
	}
}
