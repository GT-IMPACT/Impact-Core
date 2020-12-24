package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.impact.mods.GTScanner.GTScanner;
import gregtech.common.GT_UndergroundOil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidStack;


public class ImpactPacketGTScanner extends ImpactPacket {

    private int playerID, dimID;

    public ImpactPacketGTScanner() {
    }

    public ImpactPacketGTScanner(int dimensionId, int aPlayerID) {
        this.dimID = dimensionId;
        this.playerID = aPlayerID;
    }

    public ImpactPacketGTScanner(EntityPlayer p) {
        this.dimID = p.worldObj.provider.dimensionId;
        this.playerID = p.getEntityId();
    }

    @Override
    public int getPacketID() {
        return 0;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeInt(dimID);
        tOut.writeInt(playerID);

        return tOut.toByteArray();
    }

    @Override
    public Object decode(ByteArrayDataInput aData) {
        return new ImpactPacketGTScanner(aData.readInt(), aData.readInt());
    }

    @Override
    public void process() {
        World w = DimensionManager.getWorld(dimID);
        if (w != null && w.getEntityByID(playerID) instanceof EntityPlayerMP) {
            checkOil((EntityPlayer) w.getEntityByID(playerID));
        }
    }

    public void checkOil(EntityPlayer mc) {
        if (!mc.worldObj.isRemote) {
            int pX = ((int) mc.posX) >> 4;
            int pZ = ((int) mc.posZ) >> 4;
            World world = mc.worldObj;
            FluidStack underOil = GT_UndergroundOil.undergroundOilReadInformation(world.getChunkFromChunkCoords(pX, pZ));
            if (underOil != null) {
                GTScanner.setFluidStack(underOil);
            }
        }
    }

}
