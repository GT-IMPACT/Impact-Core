package com.impact.mods.GregTech.tileentities.hatches;

import com.impact.util.MultiFluidHandler;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.HashMap;
import java.util.List;

import static com.impact.api.enums.Textures.Icons.OVERLAY_MULTIHATCH;
import static com.impact.util.Utilits.invertBoolean;

public class GTMTE_TankHatch extends GT_MetaTileEntity_Hatch {

    private static final HashMap<Integer, Integer> vals = new HashMap<>();

    static {
        vals.put(5, 200000);
    }

    private static final int INV_SLOT_COUNT = 2;

    public MultiFluidHandler mfh;
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

    public void setMultiFluidHandler(MultiFluidHandler mfh) {
        this.mfh = mfh;
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
        return (mfh != null) ? mTier*20000 : 0;
    }

    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPreTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide() && mfh != null) {
            if(modeOut && (aTick % 20 == 0)) {
                doAutoOutputPerSecond(aBaseMetaTileEntity);
            }
        }
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if(mfh == null) return null;

        final List<FluidStack> fluids = mfh.getFluids();
        final FluidTankInfo[] tankInfo = new FluidTankInfo[fluids.size()];
        for(int i = 0; i < tankInfo.length; i++)
            tankInfo[i] = new FluidTankInfo(fluids.get(i), mfh.getCapacity());

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

    /**
     * Handle the Multi Hatch's auto-output feature. Should be called once per second only.
     * @param aBaseMetaTileEntity
     *              this MetaTileEntity
     */
    public void doAutoOutputPerSecond(IGregTechTileEntity aBaseMetaTileEntity) {
        final ForgeDirection outSide = ForgeDirection.getOrientation(aBaseMetaTileEntity.getFrontFacing());
        final TileEntity adjacentTE = aBaseMetaTileEntity.getTileEntityOffset(outSide.offsetX, outSide.offsetY, outSide.offsetZ);
        if(adjacentTE instanceof IFluidHandler) {
            final IFluidHandler adjFH = (IFluidHandler) adjacentTE;
            // Cycle through fluids
            for(int i = 0; i < mfh.getDistinctFluids(); i++) {
                final FluidStack fluidCopy = mfh.getFluidCopy(i);
                // Make sure the adjacent IFluidHandler can accept this fluid
                if(adjFH.canFill(outSide.getOpposite(), fluidCopy.getFluid())) {

                    // Limit to output rate
                    fluidCopy.amount = Math.min(fluidCopy.amount, vals.get(super.mTier));

                    // Test how much can be drawn
                    fluidCopy.amount =  mfh.pullFluid(fluidCopy, false);

                    // Test how much can be filled (and fill if possible)
                    fluidCopy.amount = adjFH.fill(outSide.getOpposite(), fluidCopy, true);

                    // Actually deplete storage
                    mfh.pullFluid(fluidCopy, true);
                }
            }

        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return (mfh != null) ? mfh.pushFluid(resource, doFill) : 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return (mfh != null) ? new FluidStack(resource.getFluid(), mfh.pullFluid(resource, doDrain)) : null;
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
        if(mfh != null) {
            final FluidStack drain = mfh.getFluidCopy(0);
            if(drain != null) {
                // If there's no integrated circuit in the T.F.F.T. controller, output slot 0
                final byte selectedSlot = (mfh.getSelectedFluid() == -1) ? 0 : mfh.getSelectedFluid();

                return new FluidStack(drain.getFluid(), mfh.pullFluid(new FluidStack(drain.getFluid(), maxDrain), 0, doDrain)
                );
            }
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (mfh != null) && mfh.couldPush(new FluidStack(fluid, 1));
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return (mfh != null) && mfh.contains(new FluidStack(fluid, 1));
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


