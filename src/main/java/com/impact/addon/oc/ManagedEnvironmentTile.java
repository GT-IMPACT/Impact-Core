package com.impact.addon.oc;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;

public abstract class ManagedEnvironmentTile<T> extends ManagedEnvironment implements NamedBlock {
	
	protected final T tile;
	protected final String name;
	
	public ManagedEnvironmentTile(final T tile, final String name) {
		this.tile = tile;
		this.name = name;
		this.setNode(Network.newNode(this, Visibility.Network).withComponent(name, Visibility.Network).create());
	}
	
	@Override
	public String preferredName() {
		return name;
	}
	
	@Override
	public int priority() {
		return 0;
	}
}
