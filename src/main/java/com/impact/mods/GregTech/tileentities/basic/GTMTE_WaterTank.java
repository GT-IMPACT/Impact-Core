package com.impact.mods.GregTech.tileentities.basic;

import com.impact.mods.GregTech.gui.GUI_WaterTank;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.gui.GT_Container_BasicTank;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class GTMTE_WaterTank extends GT_MetaTileEntity_BasicTank {
    public GTMTE_WaterTank(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 3, "Condense " + 5 + "L per tick of water from Air.");
    }

    public GTMTE_WaterTank(String mName, byte mTier, String aDescription, ITexture[][][] mTextures) {
        super(mName, mTier, 2, aDescription, mTextures);
    }

    @SuppressWarnings("deprecation")
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_WaterTank(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return
                aSide == 1
                        ? new ITexture[]{new GT_RenderedTexture(BlockIcons.WATER_CAULDRON_OVERLAY)}
                        : new ITexture[]{new GT_RenderedTexture(BlockIcons.WATER_CAULDRON)};
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_BasicTank(aPlayerInventory, aBaseMetaTileEntity);
    }
    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_WaterTank(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }


    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (this.getBaseMetaTileEntity().isServerSide()) {
            if (this.getBaseMetaTileEntity().isAllowedToWork()) {
                if (this.getFluidAmount() + this.generateWaterAmount() <= this.getCapacity() && this.getBaseMetaTileEntity().decreaseStoredEnergyUnits(0, true)) {
                    if (this.mFluid != null && this.mFluid.getFluidID() != Materials.Water.getFluid(1L).getFluidID()) {
                        this.mFluid = null;
                    }

                    this.fill(Materials.Water.getFluid(this.generateWaterAmount()), true);
                }

                this.getBaseMetaTileEntity().setActive(true);
            } else {
                this.getBaseMetaTileEntity().setActive(false);
            }

            for(byte tSide = 0; tSide < 6; ++tSide) {
                IFluidHandler tTileEntity = aBaseMetaTileEntity.getITankContainerAtSide(tSide);
                if (tTileEntity != null) {
                    if (tTileEntity instanceof IGregTechTileEntity && aBaseMetaTileEntity.getColorization() >= 0) {
                        byte tColor = ((IGregTechTileEntity)tTileEntity).getColorization();
                        if (tColor >= 0 && (tColor & 15) != (aBaseMetaTileEntity.getColorization() & 15)) {
                            continue;
                        }
                    }

                    FluidTankInfo[] inf = tTileEntity.getTankInfo(ForgeDirection.getOrientation(tSide).getOpposite());
                    FluidTankInfo[] var7 = inf;
                    int var8 = inf.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        FluidTankInfo info = var7[var9];
                        if (info != null && (info.fluid == null || info.fluid.getFluidID() < 0 || info.fluid.getFluidID() == Materials.Water.getFluid(1L).getFluidID())) {
                            int amount = info.capacity;
                            if (info.fluid != null && info.fluid.amount != info.capacity) {
                                amount = info.capacity - info.fluid.amount;
                            }

                            if (this.getFluidAmount() >= amount && tTileEntity.fill(ForgeDirection.getOrientation(tSide).getOpposite(), this.drain(amount, false), false) > 0) {
                                tTileEntity.fill(ForgeDirection.getOrientation(tSide).getOpposite(), this.drain(amount, true), true);
                            }
                            break;
                        }
                    }
                }
            }
        }

    }

    public boolean isLiquidOutput(byte aSide) {
        return true;
    }

    private int generateWaterAmount() {
        return 5;
    }

    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) {
            return true;
        } else {
            aBaseMetaTileEntity.openGUI(aPlayer);
            return true;
        }
    }

    public boolean isSimpleMachine() {
        return false;
    }

    public boolean isElectric() {
        return false;
    }

    public boolean isEnetInput() {
        return false;
    }

    public long getMinimumStoredEU() {
        return 0;
    }

    public long maxEUStore() {
        return 1000;
    }
	
	public long maxEUInput() {
    	return 0;
    }

    public long maxSteamStore() {
        return 0;
    }

    public long maxAmperesIn() {
        return 0;
    }



    public boolean isSteampowered() {
        return false;
    }

    public int getStackDisplaySlot() {
        return 2;
    }

    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    public boolean isInputFacing(byte aSide) {
        return true;
    }

    public boolean isOutputFacing(byte aSide) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    public int getCapacity() {
        return 16000;
    }

    public int getTankPressure() {
        return 100;
    }

    public boolean isFluidChangingAllowed() {
        return false;
    }

    public boolean doesFillContainers() {
        return true;
    }

    public boolean doesEmptyContainers() {
        return true;
    }

    public boolean canTankBeFilled() {
        return true;
    }

    public boolean canTankBeEmptied() {
        return true;
    }

    public boolean displaysItemStack() {
        return true;
    }

    public boolean displaysStackSize() {
        return false;
    }

    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return null;
    }
}
