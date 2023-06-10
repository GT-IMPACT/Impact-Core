package com.impact.mods.gregtech.tileentities.hatches.lasers;

import com.google.common.io.ByteArrayDataInput;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.network.GTNetworkHandler;
import com.impact.network.NetworkPackets;
import com.impact.util.vector.LaserPath;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import org.jetbrains.annotations.NotNull;
import space.impact.packet_network.network.packets.IStreamPacketReceiver;

import static com.impact.util.Utilits.getColorTier;
import static com.impact.util.Utilits.getColorToRGBA;
import static gregtech.api.enums.GT_Values.V;

public class GTMTE_LaserEnergy_Reflector extends GT_MetaTileEntity_BasicHull implements IStreamPacketReceiver {
	
	public final int mAmp;
	public byte mSide = 0;
	
	public GTMTE_LaserEnergy_Reflector(int aID, String aNameRegional, int aTier, int mAmp, ITexture... aTextures) {
		super(aID, "impact.laser.reflector.a" + mAmp + "." + aTier, aNameRegional, aTier, "", aTextures);
		this.mAmp = mAmp;
	}
	
	public GTMTE_LaserEnergy_Reflector(String aName, int aTier, int mAmp, int aInvSlotCount, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, aInvSlotCount, aDescription, aTextures);
		this.mAmp = mAmp;
	}
	
	@Override
	public String[] getDescription() {
		return new String[]{
				"The reflector serves to turn the laser (tunnel)",
				"Accepts up to 256A",
				"To set the entry point, use an RClick screwdriver on the side of the reflector",
				"Shift + RClick resets the entry point"
		};
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity te, byte aSide, byte aFacing, byte aColorIndex, boolean aConnected, boolean aRedstone) {
		if (mSide == aSide && aSide != aFacing) {
			return new ITexture[]{
					Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
					TextureFactory.of(Texture.Icons.OVERLAY_LASER_INPUT, getColorToRGBA(getColorTier(mTier)))
			};
		} else if (aSide == aFacing) {
			return new ITexture[]{
					Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
					TextureFactory.of(Texture.Icons.OVERLAY_LASER_OUTPUT, getColorToRGBA(getColorTier(mTier)))
			};
		}
		return mTextures[Math.min(2, aSide)][aColorIndex + 1];
	}
	
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_LaserEnergy_Reflector(mName, mTier, mAmp, mInventory.length, mDescriptionArray, mTextures);
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return aSide != getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isElectric() {
		return true;
	}
	
	@Override
	public long getMinimumStoredEU() {
		return V[this.mTier];
	}
	
	@Override
	public long maxEUInput() {
		return V[this.mTier];
	}
	
	@Override
	public long maxEUStore() {
		return V[mTier] * 24L * mAmp;
	}
	
	@Override
	public long maxAmperesIn() {
		return mAmp;
	}
	
	@Override
	public long maxAmperesOut() {
		return mAmp;
	}
	
	@Override
	public boolean isEnetOutput() {
		return true;
	}
	
	@Override
	public boolean isEnetInput() {
		return true;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setByte("mSideInput", mSide);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mSide = aNBT.getByte("mSideInput");
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		if (te.isServerSide() && aTick == 63) {
			sendServerSide(te);
		}
	}
	
	private void sendServerSide(IGregTechTileEntity te) {
		GTNetworkHandler.sendToAllAround(te, NetworkPackets.StreamPacket.transaction(mSide), 50);
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te.isServerSide()) {
			mSide = aPlayer.isSneaking() ? 6 : aSide;
			sendServerSide(te);
			
			if (mSide < 6) {
				GT_Utility.sendChatToPlayer(aPlayer, "Input side: " + ForgeDirection.getOrientation(mSide).name());
			} else {
				GT_Utility.sendChatToPlayer(aPlayer, "Input side: CLEAR");
			}
		}
	}
	
	public boolean pushLaser() {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		return LaserPath.laserPath(te, this, 50);
	}
	
	public boolean canConnect(byte side) {
		return mSide < 6 && side < 6 && mSide == GT_Utility.getOppositeSide(side) && mSide != getBaseMetaTileEntity().getFrontFacing();
	}
	
	public byte sideOut() {
		return getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public void receive(@NotNull ByteArrayDataInput data) {
		mSide = (byte) data.readInt();
	}
}
