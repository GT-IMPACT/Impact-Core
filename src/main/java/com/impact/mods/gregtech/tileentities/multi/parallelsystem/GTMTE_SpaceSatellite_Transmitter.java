package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.google.common.collect.Sets;
import com.impact.api.position.IPosition;
import com.impact.api.satellite.IConnection;
import com.impact.api.satellite.ISatellite;
import com.impact.api.satellite.ISatelliteNetwork;
import com.impact.api.satellite.SatelliteNetworkManager;
import com.impact.api.satellite.gui.SatelliteNetworkContainer;
import com.impact.api.satellite.gui.SatelliteNetworkGui;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;

public class GTMTE_SpaceSatellite_Transmitter extends GT_MetaTileEntity_Hatch implements ISatellite, ISecurity {
	
	//Передатчик
	public boolean isConnected = false;
	private int mFrequency = 0;
	private String security = null;
	
	private final HashSet<ISatelliteNetwork> towers = Sets.newHashSet();
	
	public GTMTE_SpaceSatellite_Transmitter(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional, 3, 0, new String[]{Utilits.impactTag(), "Transmission of signals from orbit", "Used in Space Satellite", EnumChatFormatting.RED + "▉" + EnumChatFormatting.GRAY + " - Error", EnumChatFormatting.YELLOW + "▉" + EnumChatFormatting.GRAY + " - All Right"});
	}
	
	public GTMTE_SpaceSatellite_Transmitter(String aName, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, 3, 0, aDescription, aTextures);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new GTMTE_SpaceSatellite_Transmitter(mName, mDescriptionArray, mTextures);
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new SatelliteNetworkContainer(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new SatelliteNetworkGui(aPlayerInventory, aBaseMetaTileEntity, "Communication Transmitter");
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(PRL_HATCH_YELLOW, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
				/*TextureFactory.of(EM_D_CONN)*/};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
				/*TextureFactory.of(EM_D_CONN)*/};
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
	public boolean isOutputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return false;
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
		if (aBaseMetaTileEntity.isClientSide()) {
			return true;
		}
		if (aSide == aBaseMetaTileEntity.getFrontFacing()) {
			GT_Utility.sendChatToPlayer(aPlayer, "Connection only with Laptop!");
		}
		return true;
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			GT_Utility.sendChatToPlayer(aPlayer, "Check: " + isConnected);
		}
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
			
			NBTTagCompound tagPos = new PositionObject(te).saveToNBT();
			tag.setTag("satellite", tagPos);
			
			te.openGUI(aPlayer);
		}
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("mIsTransmit", this.isConnected);
		aNBT.setInteger("mFrequency", this.mFrequency);
		aNBT.setString("security", this.security);
	}
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.isConnected = aNBT.getBoolean("mIsTransmit");
		this.mFrequency  = aNBT.getInteger("mFrequency");
		this.security    = aNBT.getString("security");
		if (security.isEmpty()) security = null;
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		
		if (aBaseMetaTileEntity.isServerSide()) {
			SatelliteNetworkManager.INSTANCE.addSatelliteToWorld(this);
			notifyConnections();
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long tick) {
		super.onPostTick(te, tick);
	}
	
	@Override
	public void inValidate() {
		if (getBaseMetaTileEntity().isServerSide()) {
			SatelliteNetworkManager.INSTANCE.removeSatelliteFromWorld(this);
			updateConnectionStatus(false);
			notifyConnections();
		}
		
		super.inValidate();
	}
	
	@Override
	public int getFrequency() {
		return mFrequency;
	}
	
	@Override
	public void updateFrequency(int frequency) {
		mFrequency = frequency;
		checkStatusTowers();
	}
	
	@Override
	public boolean onFirstConnect(int frequency, @NotNull ISatelliteNetwork connection) {
		if (frequency == getFrequency()) {
			return towers.add(connection);
		}
		return false;
	}
	
	@Override
	public void notifyConnections() {
		for (ISatelliteNetwork tower : towers) {
			tower.onChangeConnection(isConnected);
		}
	}
	
	@NotNull
	@Override
	public IPosition getPosition() {
		return new PositionObject(getBaseMetaTileEntity());
	}
	
	@NotNull
	@Override
	public Set<ISatelliteNetwork> getConnections() {
		return towers;
	}
	
	@Override
	public boolean isConnected(@Nullable IConnection connection) {
		return connection != null && connection.getFrequency() == getFrequency();
	}
	
	@Override
	public void updateConnectionStatus(boolean isConnected) {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null) te.setActive(isConnected);
		this.isConnected = isConnected;
		notifyConnections();
	}
	
	@Override
	public boolean getConnectionStatus() {
		return isConnected;
	}
	
	private void checkStatusTowers() {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null && te.isServerSide()) {
			List<ISatelliteNetwork> removeCandidates = new ArrayList<>();
			for (ISatelliteNetwork tower : towers) {
				if (tower.getFrequency() != getFrequency()) {
					removeCandidates.add(tower);
				}
			}
			for (ISatelliteNetwork removeCandidate : removeCandidates) {
				removeCandidate.onDisconnect();
				towers.remove(removeCandidate);
			}
		}
	}
	
	@Override
	public void disconnect(@NotNull ISatelliteNetwork connection) {
		towers.remove(connection);
		checkStatusTowers();
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