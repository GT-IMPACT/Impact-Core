package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.api.satellite.gui.SatelliteNetworkContainer;
import com.impact.api.satellite.gui.SatelliteNetworkGui;
import com.impact.api.position.IPosition;
import com.impact.api.satellite.IConnection;
import com.impact.api.satellite.ISatellite;
import com.impact.api.satellite.ISatelliteNetwork;
import com.impact.api.satellite.SatelliteNetworkManager;
import com.impact.api.security.ISecurity;
import com.impact.client.gui.GUIHandler;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_GREEN;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static gregtech.api.enums.Dyes.MACHINE_METAL;


public class GTMTE_CommunicationTower_Receiver extends GT_MetaTileEntity_Hatch implements ISatelliteNetwork, ISecurity {
	
	public boolean isConnected;
	public int mFrequency;
	
	private String security = null;
	
	private boolean isFindSatellite = false;
	
	public ISatellite satellite = null;
	private IPosition satellitePos = null;
	
	public GTMTE_CommunicationTower_Receiver(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional, 3, 0, new String[]{
				Utilits.impactTag(),
				"Receiving signals from orbit",
				"Used in Communication Tower",
				EnumChatFormatting.RED + "▉" + EnumChatFormatting.GRAY + " - Error",
				EnumChatFormatting.GREEN + "▉" + EnumChatFormatting.GRAY + " - All Right"
		});
	}
	
	public GTMTE_CommunicationTower_Receiver(String aName, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, 3, 0, aDescription, aTextures);
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new SatelliteNetworkContainer(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new SatelliteNetworkGui(aPlayerInventory, aBaseMetaTileEntity, "Communication Receiver");
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(PRL_HATCH_GREEN, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA()))
		};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA()))
		};
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new GTMTE_CommunicationTower_Receiver(mName, mDescriptionArray, mTextures);
	}
	
	@Override
	public boolean isOutputFacing(byte aSide) {
		return false;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
		if (aBaseMetaTileEntity.isClientSide()) return true;
		if (aSide == aBaseMetaTileEntity.getFrontFacing())
			GT_Utility.sendChatToPlayer(aPlayer, "Connection only with Laptop!");
		return true;
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te.isServerSide()) {
			
			NBTTagCompound tag = aPlayer.getCurrentEquippedItem().stackTagCompound;
			if (tag == null) return;
			
			String toolSecurity = tag.getString("security_key");
			
			if (security == null && !toolSecurity.isEmpty()) {
				security = toolSecurity;
			}
			
			if (security == null || !security.equals(toolSecurity)) {
				Utilits.openGui(aPlayer, GUIHandler.GUI_ID_Security, te);
				return;
			}
			
			te.openGUI(aPlayer);
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide() && aTick % 100 == 0 && !isFindSatellite) {
			if (satellitePos != null) {
				satellite = SatelliteNetworkManager.INSTANCE.getSatelliteInWorld(satellitePos);
				if (satellite != null) {
					satellite.onFirstConnect(mFrequency, this);
					isFindSatellite = true;
				}
			}
		}
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mFrequency", this.mFrequency);
		aNBT.setBoolean("mIsReceive", this.isConnected);
		aNBT.setString("security", this.security);
		
		if (satellite != null) {
			NBTTagCompound satelliteNbt = satellite.getPosition().saveToNBT();
			aNBT.setTag("satellite", satelliteNbt);
		}
	}
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.mFrequency  = aNBT.getInteger("mFrequency");
		this.isConnected = aNBT.getBoolean("mIsReceive");
		this.security    = aNBT.getString("security");
		if (security.isEmpty()) security = null;
		
		NBTTagCompound satelliteNbt = aNBT.getCompoundTag("satellite");
		if (satelliteNbt != null) {
			satellitePos = PositionObject.loadFromNBT(satelliteNbt);
		}
	}
	
	@Override
	public void inValidate() {
		onDisconnect();
		super.inValidate();
	}
	
	@Override
	public void onDisconnect() {
		if (satellite != null) {
			satellite.disconnect(this);
			satellite = null;
			getBaseMetaTileEntity().setActive(isConnected = false);
		}
	}
	
	public boolean hasConnected() {
		return isConnected;
	}
	
	@Override
	public void updateFrequency(int frequency) {
		onDisconnect();
		mFrequency = frequency;
	}
	
	@Override
	public int getFrequency() {
		return mFrequency;
	}
	
	@Override
	public void onFirstConnect(@NotNull IPosition pos) {
		onDisconnect();
		satellite = SatelliteNetworkManager.INSTANCE.getSatelliteInWorld(pos);
		if (satellite != null) {
			isConnected = satellite.onFirstConnect(mFrequency, this);
			getBaseMetaTileEntity().setActive(isConnected);
		}
	}
	
	@Override
	public void onChangeConnection(boolean isConnected) {
		this.isConnected = isConnected;
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null) {
			te.setActive(isConnected);
		}
	}
	
	@Override
	public boolean isConnected(@Nullable IConnection connection) {
		return connection != null && connection.getFrequency() == getFrequency();
	}
	
	@Override
	public void updateConnectionStatus(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	@Override
	public boolean getConnectionStatus() {
		return isConnected;
	}
	
	@Override
	public void updateSecurity(String key) {
		security = key;
	}
	
	@Override
	public String getSecurity() {
		return security;
	}
}