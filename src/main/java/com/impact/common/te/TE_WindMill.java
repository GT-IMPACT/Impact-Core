package com.impact.common.te;

import ic2.core.IC2;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
import net.minecraft.nbt.NBTTagCompound;

public class TE_WindMill extends TileEntityWindKineticGenerator {

    private int rgb = 0;
    private int speed = 0;
    private boolean isRotorHidden = true;

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        IC2.network.get().updateTileEntityField(this, "speed");
    }

    public boolean hasRotorHidden() {
        return isRotorHidden;
    }

    public void setRotorHidden(boolean rotorHidden) {
        isRotorHidden = rotorHidden;
        IC2.network.get().updateTileEntityField(this, "isRotorHidden");
    }

    public int getRGB() {
        return rgb;
    }

    public void setRGB(int rgb) {
        this.rgb = rgb;
        IC2.network.get().updateTileEntityField(this, "rgb");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        rgb = nbt.getInteger("rgb");
        isRotorHidden = nbt.getBoolean("isRotorHidden");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("rgb", rgb);
        nbt.setBoolean("isRotorHidden", isRotorHidden);
    }

    public int getGrindPower() {
        return super.getKuOutput();
    }

    public int getKuOutput() {
        return 0;
    }
}