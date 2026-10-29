package com.impact.common.te;

import ic2.core.IC2;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class TE_WindMill extends TileEntityWindKineticGenerator {

    private int rgb = 0;
    private int speed = 0;
    private boolean isRotorHidden = true;
    private boolean hasController;
    private int controllerX;
    private int controllerY;
    private int controllerZ;

    @Override
    public List<String> getNetworkedFields() {
        List<String> fields = new ArrayList<>(super.getNetworkedFields());
        fields.add("speed");
        fields.add("rgb");
        fields.add("isRotorHidden");
        return fields;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        if (this.speed == speed || isClientWorld()) return;
        this.speed = speed;
        updateVisualField("speed");
    }

    public boolean hasRotorHidden() {
        return isRotorHidden;
    }

    public void setRotorHidden(boolean rotorHidden) {
        if (isRotorHidden == rotorHidden || isClientWorld()) return;
        isRotorHidden = rotorHidden;
        updateVisualField("isRotorHidden");
    }

    public int getRGB() {
        return rgb;
    }

    public void setRGB(int rgb) {
        if (this.rgb == rgb || isClientWorld()) return;
        this.rgb = rgb;
        updateVisualField("rgb");
    }

    private boolean isClientWorld() {
        return worldObj != null && worldObj.isRemote;
    }

    private void updateVisualField(String field) {
        markDirty();
        if (worldObj != null) IC2.network.get().updateTileEntityField(this, field);
    }

    public boolean isControlledBy(int x, int y, int z) {
        return hasController && controllerX == x && controllerY == y && controllerZ == z;
    }

    public boolean attachController(int x, int y, int z) {
        if (isClientWorld()) return false;
        if (hasController) return isControlledBy(x, y, z);
        hasController = true;
        controllerX = x;
        controllerY = y;
        controllerZ = z;
        markDirty();
        return true;
    }

    @Override
    protected void updateEntityServer() {
        // The controller may have been removed while the rotor's chunk was unloaded.
        if (hasController && worldObj.getTotalWorldTime() % 20 == 0
                && worldObj.blockExists(controllerX, controllerY, controllerZ)
                && worldObj.getTileEntity(controllerX, controllerY, controllerZ) == null) {
            if (worldObj.getTileEntity(xCoord, yCoord, zCoord) == this) {
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            }
            return;
        }
        super.updateEntityServer();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        rgb = nbt.getInteger("rgb");
        speed = nbt.getInteger("speed");
        isRotorHidden = !nbt.hasKey("isRotorHidden") || nbt.getBoolean("isRotorHidden");
        hasController = nbt.hasKey("windController", 10);
        NBTTagCompound controller = nbt.getCompoundTag("windController");
        controllerX = controller.getInteger("x");
        controllerY = controller.getInteger("y");
        controllerZ = controller.getInteger("z");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("rgb", rgb);
        nbt.setInteger("speed", speed);
        nbt.setBoolean("isRotorHidden", isRotorHidden);
        if (hasController) {
            NBTTagCompound controller = new NBTTagCompound();
            controller.setInteger("x", controllerX);
            controller.setInteger("y", controllerY);
            controller.setInteger("z", controllerZ);
            nbt.setTag("windController", controller);
        }
    }

    public int getGrindPower() {
        return super.getKuOutput();
    }

    public int getKuOutput() {
        return 0;
    }
}
