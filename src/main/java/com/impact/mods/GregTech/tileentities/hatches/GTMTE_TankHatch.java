package com.impact.mods.GregTech.tileentities.hatches;

import com.impact.mods.GregTech.tileentities.storage.GTMTE_MultiTank;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_SingleTank;
import com.impact.util.MultiFluidHandler;
import com.impact.util.SingleFluidHandler;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.HashMap;
import java.util.List;

import static com.impact.mods.GregTech.enums.Texture.Icons.OVERLAY_MULTIHATCH;
import static com.impact.util.Utilits.invertBoolean;

public class GTMTE_TankHatch extends GT_MetaTileEntity_Hatch {

    private static final HashMap<Integer, Integer> vals = new HashMap<>();

    static {
        vals.put(5, 200000);
    }

    private static final int INV_SLOT_COUNT = 2;

    public MultiFluidHandler mfhMulti;
    public SingleFluidHandler mfhSingle;
    public boolean modeOut = false;

    public GTMTE_TankHatch(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, INV_SLOT_COUNT, new String[] {
                "All-in-one access for the T.F.F.T",
                "Right-click with a screwdriver to toggle auto-output"}
        );
    }

    public GTMTE_TankHatch(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, INV_SLOT_COUNT, aDescription, aTextures);
    }

    public GTMTE_TankHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, INV_SLOT_COUNT, aDescription, aTextures);
    }

    public void setMultiFluidHandler(MultiFluidHandler mfhMulti) {
        this.mfhMulti = mfhMulti;
    }

    public void setSingleFluidHandler(SingleFluidHandler mfhSingle) {
        this.mfhSingle = mfhSingle;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("outputting", modeOut);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        modeOut = aNBT.getBoolean("outputting");
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(OVERLAY_MULTIHATCH)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(OVERLAY_MULTIHATCH)};
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
        return new GTMTE_TankHatch(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        modeOut = invertBoolean(modeOut);

        GT_Utility.sendChatToPlayer(aPlayer, modeOut ? "Auto-output enabled" : "Auto-output disabled");
    }

    @Override
    public int getCapacity() {
        if (getBaseMetaTileEntity() instanceof GTMTE_MultiTank)
            return (mfhMulti != null) ? mTier*20000 : 0;

        if (getBaseMetaTileEntity() instanceof GTMTE_SingleTank)
            return (mfhSingle != null) ? mTier*20000 : 0;

        return mTier*20000;
    }

    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPreTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo[] tankInfo = new FluidTankInfo[0];
        List<FluidStack> fluids;
        if(mfhMulti != null) {
            fluids = mfhMulti.getFluids();
            tankInfo = new FluidTankInfo[fluids.size()];
            for (int i = 0; i < tankInfo.length; i++) tankInfo[i] = new FluidTankInfo(fluids.get(i), mfhMulti.getCapacity());
        }
        if(mfhSingle != null) {
            fluids = mfhSingle.getFluids();
            tankInfo = new FluidTankInfo[fluids.size()];
            for (int i = 0; i < tankInfo.length; i++) tankInfo[i] = new FluidTankInfo(fluids.get(i), mfhSingle.getCapacity());
        }
        return tankInfo;
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return true;
    }

    @Override
    public boolean doesEmptyContainers() {
        return true;
    }

    @Override
    public boolean displaysItemStack() {
        return true;
    }

    @Override
    public boolean displaysStackSize() {
        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (mfhMulti != null) return mfhMulti.pushFluid(resource, doFill);
        if (mfhSingle != null) return mfhSingle.pushFluid(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (mfhMulti != null) return new FluidStack(resource.getFluid(), mfhMulti.pullFluid(resource, doDrain));
        if (mfhSingle != null) return new FluidStack(resource.getFluid(), mfhSingle.pullFluid(resource, doDrain));
        return null;
    }

    /**
     * Drains fluid out of 0th internal tank.
     * If the TFFT Controller contains an Integrated Circuit, drain fluid
     * from the slot equal to the circuit configuration.
     *
     * @param from
     *            Orientation the fluid is drained to.
     * @param maxDrain
     *            Maximum amount of fluid to drain.
     * @param doDrain
     *            If false, drain will only be simulated.
     * @return FluidStack representing the Fluid and amount that was (or would have been, if
     *         simulated) drained.
     */
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if(mfhMulti != null) {
            final FluidStack drain = mfhMulti.getFluid(0);
            if(drain != null) {
                return new FluidStack(
                        drain.getFluid(),
                        mfhMulti.pullFluid(new FluidStack(drain.getFluid(), maxDrain), 0, doDrain)
                );
            }
        }
        if(mfhSingle != null) {
            final FluidStack drain = mfhSingle.getFluid(0);
            if(drain != null) {
                return new FluidStack(
                        drain.getFluid(),
                        mfhSingle.pullFluid(new FluidStack(drain.getFluid(), maxDrain), 0, doDrain)
                );
            }
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (mfhMulti != null) return mfhMulti.couldPush(new FluidStack(fluid, 1));
        if (mfhSingle != null) return mfhSingle.couldPush(new FluidStack(fluid, 1));
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (mfhMulti != null) return mfhMulti.contains(new FluidStack(fluid, 1));
        if (mfhSingle != null) return mfhSingle.contains(new FluidStack(fluid, 1));
        return false;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return true;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return true;
    }

    @Override
    public boolean canTankBeFilled() {
        return true;
    }

    @Override
    public boolean canTankBeEmptied() {
        return true;
    }

    @Override
    public boolean doesFillContainers() {
        return true;
    }


}


