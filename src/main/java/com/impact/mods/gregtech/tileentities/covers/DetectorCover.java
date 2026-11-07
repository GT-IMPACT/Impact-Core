package com.impact.mods.gregtech.tileentities.covers;

import com.google.common.io.ByteArrayDataInput;
import com.impact.mods.gregtech.gui.cover.DetectorCoverGui;
import com.impact.util.math.PackUnpackValues;
import gregtech.api.covers.CoverContext;
import gregtech.common.covers.Cover;
import gregtech.common.gui.modularui.cover.base.CoverBaseGui;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraftforge.fluids.Fluid;

public abstract class DetectorCover extends Cover {

    // Keep the original signed threshold / packed range so existing world and cover-item NBT stays readable.
    protected int setting;

    protected DetectorCover(CoverContext context) {
        super(context, null);
    }

    public abstract boolean isRange();

    public abstract boolean isFluid();

    public long getCapacity() {
        return isFluid() ? FluidDetectorTankData.read(getTile()).capacity : EnergyDetectorData.read(getTile()).capacity;
    }

    public int getSetting() {
        return setting;
    }

    public void setSetting(int setting) {
        if (isValid()) {
            this.setting = isRange() ? setting
                    : isFluid() ? (int) Math.min(setting, Math.min(Integer.MAX_VALUE, getCapacity()))
                            : Math.max(-100, Math.min(100, setting));
            markGuiChanged();
        }
    }

    public boolean isInverted() {
        return setting < 0;
    }

    public void setInverted(boolean inverted) {
        int threshold = (int) Math.min(100L, Math.abs((long) setting));
        setSetting(inverted ? -threshold : threshold);
    }

    public int getStartPercent() {
        return (int) PackUnpackValues.unpackFloat1(setting);
    }

    public int getEndPercent() {
        return (int) PackUnpackValues.unpackFloat2(setting);
    }

    public void setStartPercent(int percent) {
        setSetting(PackUnpackValues.packFloats(Math.max(0, Math.min(100, percent)), getEndPercent()));
    }

    public void setEndPercent(int percent) {
        setSetting(PackUnpackValues.packFloats(getStartPercent(), Math.max(0, Math.min(100, percent))));
    }

    @Override
    public int getLegacyData() {
        return setting;
    }

    @Override
    public void setLegacyData(int setting) {
        this.setting = setting;
    }

    @Override
    protected NBTBase saveDataToNbt() {
        return new NBTTagInt(setting);
    }

    @Override
    protected void readDataFromNbt(NBTBase nbt) {
        setting = nbt instanceof NBTTagInt ? ((NBTTagInt) nbt).func_150287_d() : 0;
    }

    @Override
    protected void writeDataToByteBuf(ByteBuf buffer) {
        buffer.writeInt(setting);
    }

    @Override
    protected void readDataFromPacket(ByteArrayDataInput input) {
        setting = input.readInt();
    }

    @Override
    public boolean hasCoverGUI() {
        return true;
    }

    @Override
    protected CoverBaseGui<?> getCoverGui() {
        return new DetectorCoverGui(this);
    }

    @Override
    public boolean manipulatesSidedRedstoneOutput() {
        return true;
    }

    @Override
    public boolean letsEnergyIn() {
        return true;
    }

    @Override
    public boolean letsEnergyOut() {
        return true;
    }

    @Override
    public boolean letsFluidIn(Fluid fluid) {
        return true;
    }

    @Override
    public boolean letsFluidOut(Fluid fluid) {
        return true;
    }

    @Override
    public boolean letsItemsIn(int slot) {
        return true;
    }

    @Override
    public boolean letsItemsOut(int slot) {
        return true;
    }
}
