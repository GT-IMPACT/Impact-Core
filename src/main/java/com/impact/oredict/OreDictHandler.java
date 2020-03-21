package com.impact.oredict;

import com.impact.item.ItemList;

import net.minecraftforge.oredict.OreDictionary;

public class OreDictHandler {
	
	public static void reg_additional() {
		OreDictionary.registerOre("CoinGWPP", ItemList.Coin.getIS());

	}
	
	public static void run() {
		reg_additional();
	}
}
