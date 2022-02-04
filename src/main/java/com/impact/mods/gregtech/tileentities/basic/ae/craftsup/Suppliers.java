package com.impact.mods.gregtech.tileentities.basic.ae.craftsup;

import appeng.api.storage.data.IAEItemStack;
import lombok.AllArgsConstructor;

public class Suppliers {
	
	@AllArgsConstructor
	public static class Request {
		IAEItemStack stack;
		int channel;
		long amount;
	}
	
	@AllArgsConstructor
	public static class BoundProperty {
		IAEItemStack stack;
		int makeOrderAmount;
		int orderCount;
		int channel;
	}
}