package com.impact.mods.gregtech.tileentities.basic;

import cpw.mods.fml.common.Loader;
import galaxyspace.core.configs.GSConfigDimensions;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import micdoodle8.mods.galacticraft.planets.asteroids.ConfigManagerAsteroids;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import static gregtech.api.enums.Textures.BlockIcons.*;

public class GTMTE_Solar extends GT_MetaTileEntity_BasicGenerator {
	
	private static final Textures.BlockIcons[] solar = new Textures.BlockIcons[]{
			SOLARPANEL_8V, SOLARPANEL_LV, SOLARPANEL_MV, SOLARPANEL_HV, SOLARPANEL_EV, SOLARPANEL_IV,
			SOLARPANEL_LuV, SOLARPANEL_ZPM, SOLARPANEL_UV, SOLARPANEL_UHV, SOLARPANEL_UEV, SOLARPANEL_UIV,
	};
	public boolean sunIsUp;
	public boolean skyIsVisible;
	private int generation;
	private boolean noSunWorld;
	private boolean wetBiome;
	
	public GTMTE_Solar(int aID, String aNameRegional, int aTier) {
		super(aID, "impact.basic.generator.solarpanel." + aTier, aNameRegional, aTier, new String[]{});
	}
	
	public GTMTE_Solar(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, aDescription, aTextures);
	}
	
	public boolean isOutputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isFacingValid(byte aSide) {
		return aSide > 1;
	}
	
	@Override
	public int getPollution() {
		return 0;
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipes() {
		return null;
	}
	
	@Override
	public String[] getDescription() {
		return new String[]{
				"Generating energy with sunlight",
				String.format("Day: %s EU/t", GT_Utility.formatNumbers(GT_Values.V[mTier])),
				String.format("Night: %s EU/t", GT_Utility.formatNumbers(GT_Values.V[mTier] / 8))
		};
	}
	
	@Override
	public int getEfficiency() {
		return 100;
	}
	
	public void updateVisibility(IGregTechTileEntity te) {
		World w = te.getWorld();
		boolean rainWeather = this.wetBiome && (w.isRaining() || w.isThundering());
		
		if (Loader.isModLoaded("GalacticraftCore")) {
			int Asteroids = ConfigManagerAsteroids.dimensionIDAsteroids;
			int KuiperBelt = GSConfigDimensions.dimensionIDKuiperBelt;
			if (w.provider.dimensionId == Asteroids || w.provider.dimensionId == KuiperBelt) {
				this.sunIsUp      = w.isDaytime();
				this.skyIsVisible = w.canBlockSeeTheSky(te.getXCoord(), te.getYCoord() + 1, te.getZCoord());
				return;
			}
		}
		this.noSunWorld   = w.provider.hasNoSky;
		this.sunIsUp      = w.isDaytime() && !rainWeather;
		this.skyIsVisible = w.canBlockSeeTheSky(te.getXCoord(), te.getYCoord() + 1, te.getZCoord()) && !this.noSunWorld;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		
		if (aTick % 20 == 0) {
			updateVisibility(te);
			
			if (this.sunIsUp && this.skyIsVisible) {
				this.generation = (int) GT_Values.V[mTier];
			} else if (this.skyIsVisible) {
				this.generation = (int) GT_Values.V[mTier] / 8;
			} else {
				this.generation = 0;
			}
			
			te.increaseStoredEnergyUnits(maxEUStore() - te.getStoredEU(), false);
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("generation", generation);
		aNBT.setBoolean("sunIsUp", sunIsUp);
		aNBT.setBoolean("skyIsVisible", skyIsVisible);
		aNBT.setBoolean("noSunWorld", noSunWorld);
		aNBT.setBoolean("wetBiome", wetBiome);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		generation   = aNBT.getInteger("generation");
		sunIsUp      = aNBT.getBoolean("sunIsUp");
		skyIsVisible = aNBT.getBoolean("skyIsVisible");
		noSunWorld   = aNBT.getBoolean("noSunWorld");
		wetBiome     = aNBT.getBoolean("wetBiome");
	}
	
	public long maxEUOutput() {
		return generation;
	}
	
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity te) {
		super.onFirstTick(te);
		World w = te.getWorld();
		this.noSunWorld = w.provider.hasNoSky;
		this.wetBiome   = w.getWorldChunkManager().getBiomeGenAt(te.getXCoord(), te.getZCoord()).getIntRainfall() > 0;
	}
	
	public ITexture[] getFront(byte aColor) {
		return new ITexture[]{super.getFront(aColor)[0], Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
	}
	
	@Override
	public ITexture[] getTop(byte aColor) {
		
		return new ITexture[]{super.getFront(aColor)[0], TextureFactory.of(solar[mTier])};
	}
	
	@Override
	public long maxEUStore() {
		return GT_Values.V[mTier] * 1000 * 4;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new GTMTE_Solar(this.mName, this.mTier, this.mDescriptionArray, this.mTextures);
	}
}