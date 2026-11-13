package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.INetworkMachine;
import com.impact.addon.gt.api.parallel_system.MachineNetworkHandler;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.check.CheckRecipeResult;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
    public void createConnect() {
        com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle.load(this);
    }

    @Override
    public void removeConnect() {
        com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle.unload(this);
    }
}
