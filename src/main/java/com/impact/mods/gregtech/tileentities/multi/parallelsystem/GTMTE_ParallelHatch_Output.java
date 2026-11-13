package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.INetworkNodeHandler;
import com.impact.addon.gt.api.parallel_system.IParallelIn;
import com.impact.addon.gt.api.parallel_system.IParallelOut;
import com.impact.addon.gt.api.parallel_system.IParallelTransmitter;
import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;
import com.impact.addon.gt.api.parallel_system.NetworkNodeAccess;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.position.IPosition;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;
import static net.minecraft.util.StatCollector.translateToLocal;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;

public class GTMTE_ParallelHatch_Output extends GT_MetaTileEntity_Hatch implements IParallelOut, IParallelTransmitter, IHandledNetworkNode {

    private final int maxParallel;
    private String machineName;
    private String address;

    private IParallelIn pin;
    private IPosition pinPos;

    private boolean isConnected;
    private int allocatedParallel;
    private long allocationRevision;
    private NetworkStatus networkStatus = NetworkStatus.NO_TARGET;
    private NetworkStatus receiverStatus = NetworkStatus.NO_TARGET;

    public GTMTE_ParallelHatch_Output(
            int aID,
            String aName,
            String aNameRegional,
            int aTier,
            int aMaxParallel
    ) {
        super(aID, aName, aNameRegional, aTier, 0, new String[]{
                Utilits.impactTag(),
                "Parallel points transmitter",
                "Used in Parallel Computer"
        });
        maxParallel = aMaxParallel;
    }

    public GTMTE_ParallelHatch_Output(
            String aName,
            int aTier,
            String[] aDescription,
            ITexture[][][] aTextures,
            int aMaxParallel
    ) {
        super(aName, aTier, 0, aDescription, aTextures);
        maxParallel = aMaxParallel;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new GTMTE_ParallelHatch_Output(mName, mTier, mDescriptionArray, mTextures, maxParallel);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] {
                aBaseTexture,
                TextureFactory.of(
                        PRL_HATCH_YELLOW,
                        Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())
                )
        };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] {
                aBaseTexture,
                TextureFactory.of(
                        PRL_HATCH_RED,
                        Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())
                )
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
    public boolean isOutputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return false;
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
            if (aPlayer.capabilities.isCreativeMode) {
                GT_Utility.sendChatToPlayer(aPlayer, "Debug recipe: " + isConnected);
            }
        }
    }

    @Override
    public void inValidate() {
        NetworkNodeLifecycle.unload(this);
        super.inValidate();
    }

    @Override
    public void onNotePadRightClick(
            byte aSide,
            EntityPlayer aPlayer,
            float aX,
            float aY,
            float aZ
    ) {
        super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
        if (getBaseMetaTileEntity().isServerSide()) {
            getBaseMetaTileEntity().setActive(false);

            ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
            if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {

                GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);

                NBTTagCompound nbt = tCurrentItem.getTagCompound();

                if (nbt == null) {
                    nbt = new NBTTagCompound();
                    tCurrentItem.setTagCompound(nbt);
                }

                NBTTagCompound posNbt = new PositionObject(getBaseMetaTileEntity()).saveToNBT();
                nbt.setTag("parallel", posNbt);

                GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.YELLOW + "Connection start..");
            }
        }
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        if (aBaseMetaTileEntity.isServerSide()) {
            if (machineName == null || machineName.isEmpty()) {
                machineName = "No target machine";
            }
            if (address == null || address.isEmpty()) {
                address = "no target address";
            }
            NetworkNodeLifecycle.load(this);
        }
    }

    private final INetworkNodeHandler networkHandler = new INetworkNodeHandler() {
        @Override
        public void onLoad() {
            findIn(pinPos);
        }

        @Override
        public void onUnload() {
            onPostDisconnect();
        }

        @Override
        public void onTick(long tick) {
            if (tick % 20 == 0) {
                if (pin != null && !pin.isValid()) {
                    onPostDisconnect();
                }
                if (pin == null) {
                    findIn(pinPos);
                }
            }
        }

        @Override
        public NetworkStatus getStatus() {
            return getNetworkStatus();
        }
    };

    @Override
    public INetworkNodeHandler getNetworkHandler() {
        return networkHandler;
    }

    public IParallelIn getReceiver() {
        return pin;
    }

    public IPosition getReceiverPosition() {
        return pinPos == null ? null : new PositionObject(pinPos);
    }

    public String getMachineName() {
        return machineName == null ? "" : machineName;
    }

    public String getMachineAddress() {
        return address == null ? "" : address;
    }

    public boolean getConnectionStatus() {
        return isConnected;
    }

    @Override
    public boolean isAvailable() {
        return NetworkNodeAccess.isLoaded(this) && !NetworkNodeLifecycle.isFaulted(this);
    }

    @Override
    public boolean isEnabled() {
        return getBaseMetaTileEntity() != null && getBaseMetaTileEntity().isAllowedToWork();
    }

    @Override
    public long getAllocationRevision() {
        return allocationRevision;
    }

    @Override
    public NetworkStatus getNetworkStatus() {
        return pin == null ? receiverStatus : networkStatus;
    }

    public boolean hasReceiver() {
        return pin != null && pin.isValid();
    }

    private void findIn(IPosition pos) {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        if (pos == null) {
            setReceiverStatus(NetworkStatus.NO_TARGET);
            return;
        }
        if (tile == null || tile.getWorld().provider.dimensionId != pos.getD()
                || !tile.getWorld().blockExists(pos.getX(), pos.getY(), pos.getZ())) {
            setReceiverStatus(NetworkStatus.UNLOADED);
            return;
        }
        IGregTechTileEntity destination = PositionObject.getIGregTechTileEntity(tile, pos);
        if (destination != null && destination.getMetaTileEntity() instanceof IParallelIn) {
            IParallelIn target = (IParallelIn) destination.getMetaTileEntity();
            if (target.getParallel() == getParallel()) {
                onConnect(target);
                return;
            }
        }
        setReceiverStatus(NetworkStatus.RECEIVER_MISSING);
    }

    private void setReceiverStatus(NetworkStatus status) {
        if (receiverStatus != status) {
            receiverStatus = status;
            allocationRevision++;
        }
    }

    @Override
    public NetworkStatus getReceiverStatus() {
        return pin == null ? receiverStatus : hasReceiver() ? NetworkStatus.CONNECTED : NetworkStatus.UNLOADED;
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("isConnected", this.isConnected);

        if (this.machineName != null && !this.machineName.isEmpty()) {
            aNBT.setString("machineName", this.machineName);
        }

        if (this.address != null && !this.address.isEmpty()) {
            aNBT.setString("address", this.address);
        }

        if (pinPos != null) {
            aNBT.setTag("parallelIn", pinPos.saveToNBT());
        }
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        onPostDisconnect();

        machineName = aNBT.getString("machineName");
        address = aNBT.getString("address");

        pinPos = aNBT.hasKey("parallelIn")
                ? PositionObject.loadFromNBT(aNBT.getCompoundTag("parallelIn")) : null;
    }

    @Override
    public void onConnect(@NotNull IParallelIn pin) {
        if (this.pin != null && this.pin != pin) {
            IParallelIn previous = this.pin;
            this.pin = null;
            previous.onDisconnect();
        }
        this.pin = pin;
        receiverStatus = NetworkStatus.CONNECTED;
        this.pinPos = new PositionObject(pin.getPosition());
        allocationRevision++;
        NetworkNodeAccess.markConfigurationDirty(this);
        pin.onConnect(this);
        publishAllocation();
    }

    @Override
    public void disconnect(IParallelIn receiver, boolean forgetPosition) {
        if (pin == receiver) {
            onPostDisconnect();
            if (forgetPosition) {
                pinPos = null;
                receiverStatus = NetworkStatus.NO_TARGET;
                NetworkNodeAccess.markConfigurationDirty(this);
            }
        }
    }

    @Override
    public void updateStatus(boolean connected) {
        allocate(connected ? getParallel() : 0,
                connected ? NetworkStatus.CONNECTED : NetworkStatus.MACHINE_OFFLINE);
    }

    @Override
    public void allocate(int points, NetworkStatus status) {
        allocatedParallel = status == NetworkStatus.CONNECTED ? Math.max(0, Math.min(getParallel(), points)) : 0;
        networkStatus = allocatedParallel == 0 && status == NetworkStatus.CONNECTED
                ? NetworkStatus.INSUFFICIENT_POINTS : status;
        isConnected = allocatedParallel > 0 && networkStatus == NetworkStatus.CONNECTED;
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        if (tile != null) {
            tile.setActive(isConnected);
        }
        publishAllocation();
    }

    private void publishAllocation() {
        if (pin != null) {
            pin.updateParallelAmount(isConnected ? allocatedParallel : 0);
            pin.updateConnectionStatus(isConnected);
            pin.updateNetworkStatus(networkStatus);
        }
    }

    @Override
    public void onPostDisconnect() {
        machineName = "No target machine";
        address = "no target address";
        IParallelIn previous = pin;
        pin = null;
        receiverStatus = pinPos == null ? NetworkStatus.NO_TARGET : NetworkStatus.UNLOADED;
        allocationRevision++;
        allocate(0, receiverStatus);
        if (previous != null) {
            previous.onDisconnect();
        }
    }

    @Override
    public int getParallel() {
        return maxParallel;
    }

    @Override
    public void changeCurrentParallel(int parallels) {
        allocate(isConnected ? parallels : 0, networkStatus);
    }

    @Override
    public void updateData(@NotNull String name, @NotNull String address) {
        this.address = address;
        this.machineName = name;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(
            ItemStack stack,
            List<String> tooltip,
            IWailaDataAccessor accessor,
            IWailaConfigHandler config
    ) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        String machineKey = tag.getString("ppOutMachineNameKey");
        String machine = machineKey.isEmpty() ? tag.getString("ppOutMachineName") : translateToLocal(machineKey);
        tooltip.add(machine.isEmpty() || machine.equals("Not target machine") || machine.equals("No target machine")
                ? translateToLocal("impact.waila.parallel.no_machine") : machine);
        String machineAddress = tag.getString("ppOutMachineAddress");
        if (machineAddress.isEmpty() || machineAddress.equals("no target address")) {
            machineAddress = translateToLocal("impact.waila.parallel.no_address");
        }
        tooltip.add(translateToLocalFormatted("impact.waila.parallel.address",
                EnumChatFormatting.YELLOW + machineAddress + EnumChatFormatting.RESET));
        NetworkDiagnostics.appendConnection(tag, tooltip, tag.getBoolean("isParallelOUT"));
        tooltip.add(translateToLocalFormatted("impact.waila.parallel.point", tag.getInteger("ppHatchOut")));
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
        final boolean isParallelOUT = isConnected;
        tag.setBoolean("isParallelOUT", isParallelOUT);
        tag.setInteger("ppHatchOut", maxParallel);
        tag.setString("ppOutMachineName", machineName == null ? "" : machineName);
        tag.setString("ppOutMachineNameKey", pin instanceof GTMTE_ParallelHatch_Input
                ? ((GTMTE_ParallelHatch_Input) pin).getMachineTranslationKey() : "");
        tag.setString("ppOutMachineAddress", address == null ? "" : address);
    }
}
