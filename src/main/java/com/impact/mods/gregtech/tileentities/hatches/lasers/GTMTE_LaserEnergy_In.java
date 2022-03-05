package com.impact.mods.gregtech.tileentities.hatches.lasers;

import com.impact.mods.gregtech.enums.Texture;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

import static com.impact.util.Utilits.getColorTier;
import static com.impact.util.Utilits.getColorToRGBA;
import static gregtech.api.enums.GT_Values.V;

public class GTMTE_LaserEnergy_In extends GT_MetaTileEntity_Hatch_EnergyMulti {
	
	public GTMTE_LaserEnergy_In(int aID, String aNameRegional, int aTier, int aAmp) {
		super(aID, "impact.hatch.laser.multi.energy.a" + aAmp + "." + aTier, aNameRegional, aTier, aAmp, new String[]{
				"Energy Injector Laser for Multiblocks",
				"Accepts up to " + EnumChatFormatting.YELLOW + aAmp + EnumChatFormatting.GRAY + " Amp"
		});
	}
	
	public GTMTE_LaserEnergy_In(String aName, int aTier, int aAmp, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, aAmp, aDescription, aTextures);
	}
	
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{
				aBaseTexture,
				TextureFactory.of(Texture.Icons.OVERLAY_LASER_INPUT, getColorToRGBA(getColorTier(mTier)))
		};
	}
	
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{
				aBaseTexture,
				TextureFactory.of(Texture.Icons.OVERLAY_LASER_INPUT, getColorToRGBA(getColorTier(mTier)))
		};
	}
	
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_LaserEnergy_In(this.mName, this.mTier, this.Amp, this.mDescription, this.mTextures);
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public long getMinimumStoredEU() {
		return 128L * (long) this.Amp;
	}
	
	@Override
	public long maxEUInput() {
		return V[mTier];
	}
	
	@Override
	public long maxEUStore() {
		return V[this.mTier] * 24L * (long) this.Amp;
	}
	
	@Override
	public boolean isEnetOutput() {
		return false;
	}
	
	@Override
	public boolean isEnetInput() {
		return true;
	}
	
	public boolean canConnect(byte side) {
		return isInputFacing(side);
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		if (te.isServerSide()) {
			byte Tick = (byte) (aTick % 20);
			if (16 == Tick) {
				if (te.getStoredEU() > 0) {
					setEUVar(te.getStoredEU() - Amp);
					if (te.getStoredEU() < 0) {
						setEUVar(0);
					}
				}
			}
		}
	}
}