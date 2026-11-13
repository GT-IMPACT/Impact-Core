package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.INetworkNodeHandler;
import com.impact.addon.gt.api.parallel_system.IParallelIn;
import com.impact.addon.gt.api.parallel_system.IParallelOut;
import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;
import com.impact.addon.gt.api.parallel_system.NetworkNodeAccess;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.position.IPosition;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
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
import java.util.UUID;
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

public class GTMTE_ParallelHatch_Input extends GT_MetaTileEntity_Hatch implements IParallelIn, IHandledNetworkNode {

    private final int maxParallel;
    private int allocatedParallel;

    private String machineName;
    private String address;

    private IParallelOut pout;
    private boolean isConnected;
    private NetworkStatus networkStatus = NetworkStatus.NO_TARGET;
    private GT_MetaTileEntity_MultiParallelBlockBase<?> controller;

    public boolean claimController(GT_MetaTileEntity_MultiParallelBlockBase<?> machine) {
        if (controller != null && controller != machine) {
            IGregTechTileEntity te = controller.getBaseMetaTileEntity();
            if (te != null && !te.isDead() && te.getMetaTileEntity() == controller) {
                return false;
            }
        }
        controller = machine;
        return true;
    }

    public void releaseController(GT_MetaTileEntity_MultiParallelBlockBase<?> machine) {
        if (controller == machine) {
            controller = null;
        }
    }

    public GTMTE_ParallelHatch_Input(
            int aID,
            String aName,
            String aNameRegional,
            int aTier,
            int aMaxParallel
    ) {
        super(aID, aName, aNameRegional, aTier, 0, new String[]{
                Utilits.impactTag(),
                "Parallel points receiver",
                "Used in multis machines",
                "Reduces recipe time by a factor of " + (aTier - 4)
        });
        maxParallel = aMaxParallel;
    }

    public GTMTE_ParallelHatch_Input(
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
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new GTMTE_ParallelHatch_Input(mName, mTier, mDescriptionArray, mTextures, getParallel());
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
    public void onNotePadRightClick(
            byte aSide,
            EntityPlayer aPlayer,
            float aX,
            float aY,
            float aZ
    ) {
        super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
        if (getBaseMetaTileEntity().isServerSide()) {
            ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
            if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
                GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
                NBTTagCompound nbt = tCurrentItem.getTagCompound();

                NBTTagCompound posNbt = nbt.getCompoundTag("parallel");
                if (posNbt != null) {
                    IPosition pos = PositionObject.loadFromNBT(posNbt);
                    if (findOut(pos)) {
                        nbt.removeTag("parallel");
                        GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.GREEN + "Connection confirm");
                    } else {
                        GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Connection Error");
                    }
                }
            }
        }
    }

    private boolean findOut(IPosition pos) {
        IGregTechTileEntity te = getBaseMetaTileEntity();
        if (te != null && pos != null && te.getWorld().provider.dimensionId == pos.getD()
                && te.getWorld().blockExists(pos.getX(), pos.getY(), pos.getZ())) {
            IGregTechTileEntity dest = PositionObject.getIGregTechTileEntity(te, pos);

            if (dest != null && dest.getMetaTileEntity() != null && dest.getMetaTileEntity() instanceof IParallelOut) {
                IParallelOut target = (IParallelOut) dest.getMetaTileEntity();

                if (target.getParallel() == getParallel()) {
                    target.onConnect(this);
                    onConnect(target);
                    return true;
                }
            }
        }
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
                GT_Utility.sendChatToPlayer(aPlayer, "Debug recipe: " + getConnectionStatus());
            }
        }
    }

    @Override
    public void inValidate() {
        NetworkNodeLifecycle.unload(this);
        super.inValidate();
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mCurrentParallelIn", this.allocatedParallel);
        aNBT.setBoolean("isConnected", this.isConnected);

        if (this.machineName != null && !this.machineName.isEmpty()) {
            aNBT.setString("machineName", this.machineName);
        }

        if (this.address != null && !this.address.isEmpty()) {
            aNBT.setString("address", this.address);
        }
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        onDisconnect();
        // A saved flag is not proof of a live transmitter connection.
        allocatedParallel = 0;
        isConnected = false;
        pout = null;
        machineName = aNBT.getString("machineName");
        address = aNBT.getString("address");
    }

    @Override
    public void onFirstTick(IGregTechTileEntity te) {
        super.onFirstTick(te);
        if (te.isServerSide()) {
            NetworkNodeLifecycle.load(this);
            if (machineName == null || machineName.isEmpty()) {
                machineName = "Not target machine";
            }
            if (address == null || address.isEmpty()) {
                address = UUID.randomUUID().toString().substring(0, 8);
            }
            if (pout != null) {
                pout.updateData(getMachineName(), getMachineAddress());
            }
        }
    }

    @Override
    public void updateConnectionStatus(boolean isConnected) {
        this.isConnected = isConnected;
        if (!isConnected) {
            allocatedParallel = 0;
        }
        IGregTechTileEntity te = getBaseMetaTileEntity();
        if (te != null) {
            te.setActive(isConnected);
        }
    }

    @Override
    public boolean getConnectionStatus() {
        return isConnected && allocatedParallel > 0;
    }

    @Override
    public boolean isValid() {
        return NetworkNodeAccess.isLoaded(this) && !NetworkNodeLifecycle.isFaulted(this);
    }

    @Override
    public void onDisconnect() {
        disconnect(false);
    }

    private void disconnect(boolean forgetPosition) {
        IParallelOut previous = pout;
        pout = null;
        updateConnectionStatus(false);
        networkStatus = NetworkStatus.NO_TARGET;
        if (previous != null) {
            previous.disconnect(this, forgetPosition);
        }
    }

    @Override
    public void updateParallelAmount(int parallels) {
        allocatedParallel = Math.max(0, Math.min(getParallel(), parallels));
    }

    @Override
    public int getAllocatedParallel() {
        return isDebugReceiver() ? getParallel() : getConnectionStatus() ? allocatedParallel : 0;
    }

    @Override
    public void updateNetworkStatus(NetworkStatus status) {
        networkStatus = status;
    }

    public boolean isDebugReceiver() {
        return false;
    }

    public IParallelOut getTransmitter() {
        return pout;
    }

    public void setMachineName(String name) {
        machineName = name;
    }

    public void setMachineAddress(String address) {
        this.address = address;
    }

    @Override
    public INetworkNodeHandler getNetworkHandler() {
        return networkHandler;
    }

    private final INetworkNodeHandler networkHandler = new INetworkNodeHandler() {
        @Override
        public void onLoad() {
        }

        @Override
        public void onUnload() {
            onDisconnect();
        }

        @Override
        public NetworkStatus getStatus() {
            return isDebugReceiver() || getConnectionStatus() ? NetworkStatus.CONNECTED : networkStatus;
        }
    };

    @NotNull
    @Override
    public IPosition getPosition() {
        IGregTechTileEntity te = getBaseMetaTileEntity();
        if (te != null) {
            return new PositionObject(te);
        }
        return new PositionObject(0, 0, 0);
    }

    @Override
    public int getParallel() {
        return maxParallel;
    }

    @Override
    public void onConnect(@NotNull IParallelOut pout) {
        if (this.pout != null && this.pout != pout) {
            disconnect(true);
        }
        this.pout = pout;
        this.pout.updateData(getMachineName(), getMachineAddress());
    }

    @NotNull
    @Override
    public String getMachineName() {
        return machineName == null ? "" : machineName;
    }

    @NotNull
    @Override
    public String getMachineAddress() {
        return address == null ? "" : address;
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
        String machineKey = tag.getString("ppInMachineNameKey");
        String machine = machineKey.isEmpty() ? tag.getString("ppInMachineName") : translateToLocal(machineKey);
        tooltip.add(machine.isEmpty() || machine.equals("Not target machine") || machine.equals("No target machine")
                ? translateToLocal("impact.waila.parallel.no_machine") : machine);
        String machineAddress = tag.getString("ppInMachineAddress");
        if (machineAddress.isEmpty() || machineAddress.equals("no target address")) {
            machineAddress = translateToLocal("impact.waila.parallel.no_address");
        }
        tooltip.add(translateToLocalFormatted("impact.waila.parallel.address",
                EnumChatFormatting.YELLOW + machineAddress + EnumChatFormatting.RESET));
        NetworkDiagnostics.appendConnection(tag, tooltip, tag.getBoolean("isParallelIN"));
        tooltip.add(translateToLocalFormatted("impact.waila.parallel.point", tag.getInteger("ppHatchIn")));
        tooltip.add(translateToLocalFormatted("impact.network.allocated", tag.getInteger("ppAllocated")));
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
        final boolean isParallelIN = getConnectionStatus();
        tag.setBoolean("isParallelIN", isParallelIN);
        tag.setInteger("ppHatchIn", getParallel());
        tag.setInteger("ppAllocated", getAllocatedParallel());
        tag.setString("ppInMachineName", machineName == null ? "" : machineName);
        tag.setString("ppInMachineNameKey", getMachineTranslationKey());
        tag.setString("ppInMachineAddress", address == null ? "" : address);
    }

    public String getMachineTranslationKey() {
        return controller == null ? "" : "gt.blockmachines." + controller.mName + ".name";
    }
}
