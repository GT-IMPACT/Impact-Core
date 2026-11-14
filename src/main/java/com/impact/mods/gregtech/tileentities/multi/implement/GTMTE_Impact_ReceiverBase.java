package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.INetworkMachine;
import com.impact.addon.gt.api.parallel_system.MachineNetworkHandler;
import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import cpw.mods.fml.common.Optional;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.check.CheckRecipeResult;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class GTMTE_Impact_ReceiverBase<MULTIS extends GTMTE_Impact_ReceiverBase<MULTIS>>
        extends GTMTE_Impact_BlockBase<MULTIS> implements INetworkMachine, IHandledNetworkNode {

    private final MachineNetworkHandler networkHandler = new MachineNetworkHandler(this);

    public GTMTE_Impact_ReceiverBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_Impact_ReceiverBase(int aID, String aName, String aNameRegional, int slots) {
        super(aID, aName, aNameRegional, slots);
    }

    public GTMTE_Impact_ReceiverBase(String aName, int slots) {
        super(aName, slots);
    }

    public GTMTE_Impact_ReceiverBase(String aName) {
        super(aName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("isConnected", isSatelliteConnected());
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        networkHandler.setConnected(false);
    }

    @Override
    public boolean isSatelliteConnected() {
        return networkHandler.isConnected();
    }

    @Override
    public boolean hasShowConnect() {
        return true;
    }

    @Override
    public void setSatelliteConnected(boolean connect) {
        networkHandler.setConnected(connect);
    }

    public abstract boolean hasConnectForce();

    @Override
    protected CheckRecipeResult validateProcessing() {
        if (hasConnectForce() && !isSatelliteConnected()) {
            return CheckRecipeResult.MACHINE_CONDITION;
        }
        return super.validateProcessing();
    }


    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (hasConnectForce() && !isSatelliteConnected()) {
            IGregTechTileEntity te = getBaseMetaTileEntity();
            if (te != null) {
                te.disableWorking();
            }
        }
        return super.onRunningTick(aStack);
    }

    @Override
    public MachineNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    @Override
    public void onFirstTick(IGregTechTileEntity base) {
        super.onFirstTick(base);
        if (base.isServerSide()) {
            NetworkNodeLifecycle.load(this);
        }
    }

    @Override
    public void inValidate() {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.isServerSide()) {
            NetworkNodeLifecycle.unload(this);
        }
        super.inValidate();
    }

    @Override
    public void createConnect() {
        NetworkNodeLifecycle.load(this);
    }

    @Override
    public void removeConnect() {
        NetworkNodeLifecycle.unload(this);
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NetworkDiagnostics.append(accessor.getNBTData(), tooltip);
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
        if (showsNetworkStatusInWaila()) {
            NetworkDiagnostics.write(tag, this);
        }
    }

    protected boolean showsNetworkStatusInWaila() {
        return hasShowConnect();
    }
}
