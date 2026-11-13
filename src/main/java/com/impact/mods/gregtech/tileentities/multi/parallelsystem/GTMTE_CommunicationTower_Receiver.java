package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import cpw.mods.fml.common.Optional;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.impact.addon.gt.api.parallel_system.INetworkTower;
import com.impact.addon.gt.api.position.IPosition;
import com.impact.addon.gt.api.satellite.IConnection;
import com.impact.addon.gt.api.satellite.ISatelliteNetwork;
import com.impact.addon.gt.api.satellite.SatelliteReceiverHandler;
import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import com.impact.addon.gt.api.parallel_system.NetworkNodeAccess;
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
import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_GREEN;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static gregtech.api.enums.Dyes.MACHINE_METAL;


public class GTMTE_CommunicationTower_Receiver extends GT_MetaTileEntity_Hatch
        implements ISatelliteNetwork, IHandledNetworkNode {

    private INetworkTower tower;
    private final SatelliteReceiverHandler networkHandler = new SatelliteReceiverHandler(this, this::onNetworkChanged);

    private void onNetworkChanged(boolean connected) {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        if (tile != null) {
            tile.setActive(connected);
        }
        if (!networkHandler.isTowerManaged()) {
            callTower();
        }
    }

	public GTMTE_CommunicationTower_Receiver(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional, 3, 0, new String[]{
				Utilits.impactTag(),
				"Receiving signals from orbit",
				"Used in Communication Tower",
				EnumChatFormatting.RED + "[]" + EnumChatFormatting.GRAY + " - Error",
				EnumChatFormatting.GREEN + "[]" + EnumChatFormatting.GRAY + " - All Right"
		});
	}

	public GTMTE_CommunicationTower_Receiver(String aName, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, 3, 0, aDescription, aTextures);
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
        if (aBaseMetaTileEntity.isServerSide()) {
            NetworkNodeLifecycle.load(this);
        }
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
            GT_Utility.sendChatToPlayer(player, StatCollector.translateToLocal(NetworkDiagnostics.BIND_SATELLITE_KEY));
        }
        return true;
    }

    @Override
    public void saveNBTData(NBTTagCompound tag) {
        super.saveNBTData(tag);
        networkHandler.save(tag);
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        super.loadNBTData(tag);
        networkHandler.load(tag);
    }

	@Override
	public void inValidate() {
        NetworkNodeLifecycle.unload(this);
        super.inValidate();
	}

    @Override
    public void onDisconnect() {
        networkHandler.disconnect();
    }

    private void callTower() {
        if (tower != null) {
            tower.updateLocalNetwork();
        }
    }

    public boolean hasConnected() {
        return getConnectionStatus();
    }

    @Override
    public void updateFrequency(int frequency) {
        networkHandler.setFrequency(frequency);
        NetworkNodeAccess.markConfigurationDirty(this);
    }

    @Override
    public int getFrequency() {
        return networkHandler.getFrequency();
    }

    @Override
    public void onFirstConnect(@NotNull IPosition pos) {
        networkHandler.setTarget(pos);
        NetworkNodeAccess.markConfigurationDirty(this);
    }

    @Override
    public void onChangeConnection(boolean connected) {
        networkHandler.setConnected(connected);
    }

	@Override
	public boolean isConnected(@Nullable IConnection connection) {
		return connection != null && connection.getFrequency() == getFrequency();
	}

	@Override
    public void updateConnectionStatus(boolean isConnected) {
        networkHandler.setConnected(isConnected);
    }

	@Override
	public boolean getConnectionStatus() {
		return networkHandler.isConnected();
	}

    public void connectToTower(INetworkTower tower) {
        if (this.tower != tower) {
            this.tower = null;
            networkHandler.releaseTowerStatus();
        }
        this.tower = tower;
        callTower();
    }

    @Override
    public SatelliteReceiverHandler getNetworkHandler() {
        return networkHandler;
    }


    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
                             IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        NetworkDiagnostics.appendConnection(tag, tooltip, tag.getBoolean("isActiveTowerReciver"));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(
            EntityPlayerMP player,
            TileEntity tile,
            NBTTagCompound tag,
            World world,
            int x,
            int y,
            int z
    ) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        NetworkDiagnostics.write(tag, this);
        final boolean isActiveTowerReciver = getConnectionStatus();
        tag.setBoolean("isActiveTowerReciver", isActiveTowerReciver);
    }
}
