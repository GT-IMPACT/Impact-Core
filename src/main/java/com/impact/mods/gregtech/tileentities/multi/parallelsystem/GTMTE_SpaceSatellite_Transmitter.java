package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.addon.gt.api.position.IPosition;
import com.impact.addon.gt.api.satellite.IConnection;
import com.impact.addon.gt.api.satellite.ISatellite;
import com.impact.addon.gt.api.satellite.ISatelliteNetwork;
import com.impact.addon.gt.api.satellite.SatelliteNetworkHandler;
import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import com.impact.addon.gt.api.parallel_system.NetworkNodeAccess;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;

public class GTMTE_SpaceSatellite_Transmitter extends GT_MetaTileEntity_Hatch
        implements ISatellite, IHandledNetworkNode {

    private final SatelliteNetworkHandler networkHandler = new SatelliteNetworkHandler(this);

	public GTMTE_SpaceSatellite_Transmitter(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional, 3, 0, new String[]{
				Utilits.impactTag(),
				"Transmission of signals from orbit", "Used in Space Satellite",
				EnumChatFormatting.RED + "[]" + EnumChatFormatting.GRAY + " - Error",
				EnumChatFormatting.YELLOW + "[]" + EnumChatFormatting.GRAY + " - All Right"
		});
	}

	public GTMTE_SpaceSatellite_Transmitter(String aName, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, 3, 0, aDescription, aTextures);
	}

	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new GTMTE_SpaceSatellite_Transmitter(mName, mDescriptionArray, mTextures);
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
    public boolean onRightclick(
            IGregTechTileEntity tile,
            EntityPlayer player,
            byte side,
            float hitX,
            float hitY,
            float hitZ
    ) {
        if (tile.isServerSide()) {
            GT_Utility.sendChatToPlayer(player, StatCollector.translateToLocal("impact.satellite.auto_register"));
        }
        return true;
    }

	@Override
    public void onScrewdriverRightClick(
            byte aSide,
            EntityPlayer aPlayer,
            float aX,
            float aY,
            float aZ
    ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        if (getBaseMetaTileEntity().isServerSide()) {
            GT_Utility.sendChatToPlayer(aPlayer, "Check: " + getConnectionStatus());
        }
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("mIsTransmit", getConnectionStatus());
        aNBT.setInteger("mFrequency", getFrequency());
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        networkHandler.setConnected(false);
        networkHandler.setFrequency(aNBT.getInteger("mFrequency"));
    }

	@Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);

        if (aBaseMetaTileEntity.isServerSide()) {
            NetworkNodeLifecycle.load(this);
        }
    }

    @Override
    public void inValidate() {
        if (getBaseMetaTileEntity().isServerSide()) {
            NetworkNodeLifecycle.unload(this);
        }

        super.inValidate();
    }

	@Override
    public int getFrequency() {
        return networkHandler.getFrequency();
    }

	@Override
    public void updateFrequency(int frequency) {
        networkHandler.setFrequency(frequency);
        NetworkNodeAccess.markConfigurationDirty(this);
    }

    @Override
    public boolean onFirstConnect(int frequency, @NotNull ISatelliteNetwork connection) {
        return networkHandler.connect(frequency, connection);
    }

    @Override
    public void notifyConnections() {
        networkHandler.notifyConnections();
    }

	@NotNull
	@Override
	public IPosition getPosition() {
		return new PositionObject(getBaseMetaTileEntity());
	}

	@NotNull
	@Override
    public Set<ISatelliteNetwork> getConnections() {
        return networkHandler.getConnections();
    }

	@Override
	public boolean isConnected(@Nullable IConnection connection) {
		return connection != null && connection.getFrequency() == getFrequency();
	}

    @Override
    public void updateConnectionStatus(boolean connected) {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        if (tile != null) {
            tile.setActive(connected);
        }
        networkHandler.setConnected(connected);
    }

    @Override
    public boolean getConnectionStatus() {
        return networkHandler.isConnected();
    }

    @Override
    public void disconnect(@NotNull ISatelliteNetwork connection) {
        networkHandler.disconnect(connection);
    }

    @Override
    public SatelliteNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

}
