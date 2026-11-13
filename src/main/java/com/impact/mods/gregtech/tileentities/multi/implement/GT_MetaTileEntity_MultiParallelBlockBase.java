package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;

import static net.minecraft.util.StatCollector.translateToLocalFormatted;

import cpw.mods.fml.common.Optional;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.impact.addon.gt.api.multis.IMachineParallelRecipe;
import com.impact.core.Config;
import gregtech.GT_Mod;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.util.GT_Recipe;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class GT_MetaTileEntity_MultiParallelBlockBase<MULTIS extends GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>>
        extends GTMTE_Impact_ReceiverBase<MULTIS>
        implements IMachineParallelRecipe {

    public final HashSet<GTMTE_ParallelHatch_Input> sParallHatchesIn = new HashSet<>();
    public final HashSet<GTMTE_ParallelHatch_Output> sParallHatchesOut = new HashSet<>();

    private boolean isConnectParallel = false;
    protected int mParallel = 0;

    public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional, int slots) {
        super(aID, aName, aNameRegional, slots);
    }

    public GT_MetaTileEntity_MultiParallelBlockBase(final String aName, int slots) {
        super(aName, slots);
    }

    public GT_MetaTileEntity_MultiParallelBlockBase(final String aName) {
        super(aName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("mParallel", this.mParallel);
        aNBT.setBoolean("isConnectParallel", this.isConnectParallel);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        this.mParallel = aNBT.getInteger("mParallel");
        this.isConnectParallel = aNBT.getBoolean("isConnectParallel");
        super.loadNBTData(aNBT);
    }

    @Override
    protected int getLegacyCycleParallel(NBTTagCompound tag) {
        return Math.max(1, tag.getInteger("mCheckParallelCurrent"));
    }

    @Override
    public String[] addInfoData() {
        final ArrayList<String> ll = new ArrayList<>();
        ll.add(mParallel > 1 ? "Parallel Point: " + mParallel : "Parallel not found");
        final String[] a = new String[ll.size()];
        return ll.toArray(a);
    }

    public boolean addParallHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_ParallelHatch_Input) {
                GTMTE_ParallelHatch_Input hatch = (GTMTE_ParallelHatch_Input) aMetaTileEntity;
                if (!hatch.claimController(this)) {
                    return false;
                }
                hatch.updateTexture(aBaseCasingIndex);
                return sParallHatchesIn.add(hatch);
            } else {
                return false;
            }
        }
    }

    public void setParallel(int setParallel) {
        mParallel = setParallel;
    }

    @Override
    public boolean hasConnectForce() {
        return false;
    }

    @Override
    public void clearHatches() {
        super.clearHatches();
        for (GTMTE_ParallelHatch_Input hatch : sParallHatchesIn) {
            hatch.releaseController(this);
        }
        sParallHatchesOut.clear();
        sParallHatchesIn.clear();
        isConnectParallel = false;
        setParallel(1);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        boolean formed = super.checkMachine(thisController, guiSlotItem);
        if (!formed) {
            // Keep the detected hatches for construction limits, but release live parallel connections.
            for (GTMTE_ParallelHatch_Input hatch : sParallHatchesIn) {
                hatch.releaseController(this);
            }
            isConnectParallel = false;
            setParallel(1);
        }
        return formed;
    }

    @Override
    public void inValidate() {
        clearHatches();
        super.inValidate();
    }

    @Override
    public void onPostTick(IGregTechTileEntity tile, long tick) {
        if (tile.isServerSide() && tick % 100 == 0) {
            refreshParallelConnection();
        }
        super.onPostTick(tile, tick);
    }

    private void refreshParallelConnection() {
        isConnectParallel = false;
        int available = 1;
        for (GTMTE_ParallelHatch_Input hatch : sParallHatchesIn) {
            if (!isValidMetaTileEntity(hatch)) {
                continue;
            }
            hatch.setMachineName(getLocalName());
            isConnectParallel = hatch.getConnectionStatus() || hatch.isDebugReceiver();
            if (hatch.isDebugReceiver() || isConnectParallel && isSatelliteConnected()) {
                available = Math.max(1, hatch.getAllocatedParallel());
            }
        }
        setParallel(available);
    }

    @Override
    public int getMaxParallelRecipes() {
        return Math.max(1, mParallel);
    }

    @Override
    protected CheckRecipeResult validateProcessing() {
        refreshParallelConnection();
        CheckRecipeResult result = super.validateProcessing();
        if (!result.wasSuccessful()) {
            return result;
        }
        return !sParallHatchesIn.isEmpty() && !isConnectParallel
                ? CheckRecipeResult.MACHINE_CONDITION : CheckRecipeResult.SUCCESS;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return super.createProcessingLogic()
                .setMaxParallelSupplier(this::getMaxParallelRecipes)
                .setDurationAfterOverclock(this::applyParallelSpeedBonus);
    }

    private int applyParallelSpeedBonus(int duration) {
        int parallel = getProcessingLogic().getAvailableParallel();
        int divisor = parallel == 16 ? 2 : parallel == 64 ? 3 : parallel == 256 ? 4 : 1;
        return Math.max(Config.MAX_TICK_RATE, Math.max(1, duration / divisor));
    }

    @Override
    protected boolean usesRecipeOutputChances() {
        return true;
    }

    @Override
    protected CheckRecipeResult validateRecipe(GT_Recipe recipe) {
        if (recipe.mSpecialValue == -200 && (mCleanroom == null || mCleanroom.mEfficiency == 0)) {
            return CheckRecipeResult.MACHINE_CONDITION;
        }
        if (GT_Mod.gregtechproxy.mLowGravProcessing && recipe.mSpecialValue == -100
                && !GT_MetaTileEntity_BasicMachine.isValidForLowGravity(recipe,
                        getBaseMetaTileEntity().getWorld().provider.dimensionId)) {
            return CheckRecipeResult.MACHINE_CONDITION;
        }
        return super.validateRecipe(recipe);
    }

	@Override
	public int getMaxParallel() {
		return mParallel;
	}

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
                             IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        if (tag.getBoolean("hasShowConnect")) {
            if (!NetworkDiagnostics.hasFailure(tag)) {
                NetworkDiagnostics.appendConnection(tag, tooltip, tag.getBoolean("connectWithTower"));
            }

            if (!(this instanceof GTMTE_ParallelComputer)) {
                tooltip.add(translateToLocalFormatted("impact.waila.parallel.progress",
                        tag.getInteger("currentParallel"), tag.getInteger("Parallel")));
            }
        }
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
                                World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        if (!(this instanceof GTMTE_ParallelComputer)) {
            final int Parallel = mParallel;
            tag.setInteger("Parallel", Parallel);
            tag.setInteger("currentParallel", getCurrentParallel());
        }
        tag.setBoolean("hasShowConnect", hasShowConnect());
        tag.setBoolean("connectWithTower", isSatelliteConnected());
    }
}
