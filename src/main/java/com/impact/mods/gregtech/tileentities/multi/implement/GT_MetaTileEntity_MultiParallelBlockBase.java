package com.impact.mods.gregtech.tileentities.multi.implement;

import static net.minecraft.util.StatCollector.translateToLocalFormatted;

import cpw.mods.fml.common.Optional;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import static net.minecraft.util.StatCollector.translateToLocal;

import com.impact.addon.gt.api.multis.IMachineParallelRecipe;
import com.impact.addon.gt.api.recipe.MultiBlockRecipeBuilder;
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

    public boolean isConnectParallel = false;
    public int mParallel = 0;
    public int mCheckParallelCurrent = 0;

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
        aNBT.setInteger("mCheckParallelCurrent", this.mCheckParallelCurrent);
        aNBT.setBoolean("isConnectParallel", this.isConnectParallel);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        this.mParallel = aNBT.getInteger("mParallel");
        this.mCheckParallelCurrent = aNBT.getInteger("mCheckParallelCurrent");
        this.isConnectParallel = aNBT.getBoolean("isConnectParallel");
        super.loadNBTData(aNBT);
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
            clearHatches();
        }
        return formed;
    }

    @Override
    public void inValidate() {
        clearHatches();
        super.inValidate();
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide() && aTick % 100 == 0) {
            int maxParallel = 1;
            boolean isDebug = false;

            for (GTMTE_ParallelHatch_Input ph : sParallHatchesIn) {

                isConnectParallel = ph.isConnected;
                ph.machineName = getLocalName();

                maxParallel = ph.getParallel();
                isDebug = ph.isDebug;

            }
            if (isDebug) {
                isConnectParallel = true;
                isSatelliteConnected = true;
                setParallel(maxParallel);
                return;
            }
            if (!isConnectParallel || !isSatelliteConnected) {
                maxParallel = 1;
            }
            setParallel(maxParallel);
        }
    }

    @Override
    public boolean checkRecipe(MultiBlockRecipeBuilder<?> recipeBuilder, int indexBus) {
        if (!sParallHatchesIn.isEmpty() && !isConnectParallel) {
            return false;
        }
        return recipeBuilder
                .checkSizeHatches(true, true, indexBus)
                .checkVoltage()
                .checkRecipeMap(indexBus)
                .checkInputEqualsParallel(indexBus, true)
                .checkWorldProperties(true, true)
                .checkEfficiency()
                .checkConsumptionParallel()
                .checkOutputs(false, indexBus)
                .build();
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        return super.checkRecipe(itemStack);
    }

	@Override
	public int getCurrentParallel() {
		return mCheckParallelCurrent;
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
            String str = tag.getBoolean("connectWithTower")
                    ? EnumChatFormatting.GREEN + translateToLocal("impact.waila.connect.yes")
                    : EnumChatFormatting.RED + translateToLocal("impact.waila.connect.no");
            tooltip.add(str);

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
            tag.setInteger("currentParallel", mCheckParallelCurrent);
        }
        tag.setBoolean("hasShowConnect", hasShowConnect());
        tag.setBoolean("connectWithTower", isSatelliteConnected());
    }
}
