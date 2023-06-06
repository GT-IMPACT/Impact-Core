package com.impact.addon.oc;

import li.cil.oc.api.Driver;

public class Driver_Register {
	
	public static void init() {
		Driver.add(new Driver_NuclearReactor());
		Driver.add(new Driver_LapPowerStation());
		Driver.add(new Driver_GCLaunchController());
	}
}
