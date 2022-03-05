package com.impact.mods.gregtech.tileentities.hatches.lasers;

import com.impact.mods.gregtech.enums.Texture;
import com.impact.util.vector.LaserPath;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DynamoMulti;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

import static com.impact.util.Utilits.getColorTier;
import static com.impact.util.Utilits.getColorToRGBA;
import static gregtech.api.enums.GT_Values.V;

public class GTMTE_LaserEnergy_Out extends GT_MetaTileEntity_Hatch_DynamoMulti {
	
	public GTMTE_LaserEnergy_Out(int aID, String aNameRegional, int aTier, int aAmp) {
		super(aID, "impact.hatch.laser.multi.dynamo.a" + aAmp + "." + aTier, aNameRegional, aTier, aAmp, new String[]{
				"Energy Extractor Laser for Multiblocks",
				"Accepts up to " + EnumChatFormatting.YELLOW + aAmp + EnumChatFormatting.GRAY + " Amp"
		});
	}
	
	public GTMTE_LaserEnergy_Out(String aName, int aTier, int aAmp, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, aAmp, aDescription, aTextures);
	}
	
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(Texture.Icons.OVERLAY_LASER_OUTPUT, getColorToRGBA(getColorTier(mTier)))};
	}
	
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(Texture.Icons.OVERLAY_LASER_OUTPUT, getColorToRGBA(getColorTier(mTier)))};
	}
	
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_LaserEnergy_Out(this.mName, this.mTier, this.Amp, this.mDescription, this.mTextures);
	}
	
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	public boolean isEnetOutput() {
		return false;
	}
	
	public boolean isOutputFacing(byte aSide) {
		return aSide == this.getBaseMetaTileEntity().getFrontFacing();
	}
	
	public boolean isValidSlot(int aIndex) {
		return false;
	}
	
	public long getMinimumStoredEU() {
		return 128L * (long) this.Amp;
	}
	
	public long maxEUOutput() {
		return V[this.mTier];
	}
	
	public long maxEUStore() {
		return V[this.mTier] * 24L * (long) this.Amp;
	}
	
	public long maxAmperesOut() {
		return this.Amp;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		if (aBaseMetaTileEntity.isServerSide()) {
			byte Tick = (byte) (aTick % 20);
			if (16 == Tick) {
//				setEUVar(maxEUStore()); //DEBUG
				if (aBaseMetaTileEntity.getStoredEU() > 0) {
					setEUVar(aBaseMetaTileEntity.getStoredEU() - Amp);
					if (aBaseMetaTileEntity.getStoredEU() < 0) {
						setEUVar(0);
					}
				}
				if (aBaseMetaTileEntity.getStoredEU() > getMinimumStoredEU()) {
					pushLaser(aBaseMetaTileEntity);
				}
			}
		}
	}
	
	private boolean pushLaser(IGregTechTileEntity te) {
		return LaserPath.laserPath(te, null, 500);
	}
}