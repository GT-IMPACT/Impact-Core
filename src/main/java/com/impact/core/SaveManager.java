package com.impact.core;

import com.google.common.base.Preconditions;
import gregtech.api.util.GT_Log;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import java.io.File;

public class SaveManager {
	
	private static final String IMPACT_DIRECTORY_NAME = "IMPACT";
	private static final String PARALLEL_SYSTEM = "parallelsystem";
	private static final String AERO_STATE_SYSTEM = "aerostatesystem";
	private static final String BIOMES_ORES = "ores";
	
	@Nullable
	private static SaveManager instance;
	
	public final File impactDirectory;
	public final File parallelSystemDirectory;
	public final File aerostateSystemDirectory;
	public final File oresDirectory;
	
	private SaveManager(@Nullable final File worldDirectory) {
		
		Preconditions.checkNotNull(worldDirectory);
		Preconditions.checkArgument(worldDirectory.isDirectory());
		
		this.impactDirectory          = new File(worldDirectory, IMPACT_DIRECTORY_NAME);
		this.parallelSystemDirectory  = new File(this.impactDirectory, PARALLEL_SYSTEM);
		this.aerostateSystemDirectory = new File(this.impactDirectory, AERO_STATE_SYSTEM);
		this.oresDirectory            = new File(this.impactDirectory, BIOMES_ORES);
	}
	
	public static void onServerAboutToStart() {
		final File worldDirectory = DimensionManager.getCurrentSaveRootDirectory();
		final SaveManager newInstance = new SaveManager(worldDirectory);
		instance = newInstance;
		newInstance.onServerStarting();
	}
	
	public static SaveManager get() {
		return instance != null ? instance : null;
	}
	
	private void onServerStarting() {
		if (!this.impactDirectory.isDirectory() && !this.impactDirectory.mkdir()) {
			GT_Log.err.println(new IllegalStateException("Failed to create " + this.impactDirectory.getAbsolutePath()));
		}
		
		if (!this.parallelSystemDirectory.isDirectory() && !this.parallelSystemDirectory.mkdir()) {
			GT_Log.err.println(new IllegalStateException("Failed to create " + this.parallelSystemDirectory.getAbsolutePath()));
		}
		
		if (!this.aerostateSystemDirectory.isDirectory() && !this.aerostateSystemDirectory.mkdir()) {
			GT_Log.err.println(new IllegalStateException("Failed to create " + this.aerostateSystemDirectory.getAbsolutePath()));
		}
		
		if (!this.oresDirectory.isDirectory() && !this.oresDirectory.mkdir()) {
			GT_Log.err.println(new IllegalStateException("Failed to create " + this.oresDirectory.getAbsolutePath()));
		}
	}
	
	public void onServerStopping() {
	}
	
	public void onServerStopped() {
		Preconditions.checkNotNull(instance);
		instance = null;
	}
}