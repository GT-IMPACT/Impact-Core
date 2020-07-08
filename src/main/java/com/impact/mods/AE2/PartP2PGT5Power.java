package com.impact.mods.AE2;

import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.me.GridAccessException;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.util.GT_Utility;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import javax.annotation.Nullable;
import java.util.Iterator;

public class PartP2PGT5Power extends PartDedicatedP2PTunnel<PartP2PGT5Power> implements IPartGT5Power, IGridTickable {
    private long buffer;
    private long voltage;
    private TileEntity cachedTarget;
    private boolean isCachedTargetValid;

    public PartP2PGT5Power(ItemStack is) {
        super(is);
        this.getProxy().setFlags();
    }

    private static IChatComponent chatComponent(String title, String value) {
        ChatComponentText cct = new ChatComponentText(title);
        cct.getChatStyle().setColor(EnumChatFormatting.GOLD);
        ChatComponentText ccv = new ChatComponentText(value);
        ccv.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        cct.appendSibling(ccv);
        return cct;
    }

    public void onTunnelNetworkChange() {
        this.isCachedTargetValid = false;
        this.cachedTarget = null;
    }

    public void onNeighborChanged() {
        this.isCachedTargetValid = false;
        this.cachedTarget = null;
    }

    @SideOnly(Side.CLIENT)
    protected IIcon getTypeTexture() {
        return Blocks.stone.getIcon(0, 0);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.buffer = data.getLong("e");
        this.voltage = data.getLong("v");
    }

    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setLong("e", this.buffer);
        data.setLong("v", this.voltage);
    }

    public boolean onPartActivate(EntityPlayer player, Vec3 pos) {
        if (!super.onPartActivate(player, pos) && !player.worldObj.isRemote && player.inventory.getCurrentItem() == null) {
            PartP2PGT5Power input = this.getInput();
            String inputLoc;
            if (input == null) {
                inputLoc = "no input";
            } else {
                TileEntity te = input.getHost().getTile();
                inputLoc = "[" + te.getWorldObj().provider.dimensionId + "](" + te.xCoord + ", " + te.zCoord + ", " + te.zCoord + ")";
            }

            player.addChatMessage(chatComponent("------", ""));
            player.addChatMessage(chatComponent("Input location: ", (input == this ? "this block " : "") + inputLoc));
            player.addChatMessage(chatComponent("Name: ", input != null ? input.getCustomName() : this.getCustomName()));
            player.addChatMessage(chatComponent("Freq: ", "" + this.getFrequency()));
            player.addChatMessage(chatComponent("Voltage: ", input == null ? "no input" : "" + input.voltage));
            return true;
        } else {
            return false;
        }
    }

    private long getMaxBufferSize(long voltage) {
        return voltage * 64L;
    }

    public long injectEnergyUnits(long voltage, long amperage) {
        if (!this.isOutput() && this.isActive() && voltage > 0L && amperage > 0L && this.buffer < this.getMaxBufferSize(voltage)) {
            long lastBuffer = this.buffer;
            long lastVoltage = this.voltage;
            this.voltage = voltage;
            this.buffer += amperage * voltage;
            if (lastBuffer < voltage && this.buffer >= voltage || lastVoltage <= 0L) {
                try {
                    this.getProxy().getTick().alertDevice(this.getProxy().getNode());
                } catch (GridAccessException var10) {
                }
            }

            return amperage;
        } else {
            return 0L;
        }
    }

    public boolean inputEnergy() {
        return !this.isOutput();
    }

    public boolean outputsEnergy() {
        return this.isOutput();
    }

    public TickingRequest getTickingRequest(IGridNode node) {
        return new TickingRequest(1, 20, false, true);
    }

    public TickRateModulation tickingRequest(IGridNode node, int TicksSinceLastCall) {
        long voltage = this.voltage;
        if (!this.isOutput() && voltage > 0L && this.buffer >= voltage) {
            long amperes = this.buffer / voltage;
            long amperesUsed = 0L;

            try {
                Iterator var9 = this.getOutputs().iterator();

                while (var9.hasNext()) {
                    PartP2PGT5Power t = (PartP2PGT5Power) var9.next();
                    long received = t.doOutput(voltage, amperes);
                    amperesUsed += received;
                    amperes -= received;
                    if (amperes <= 0L) {
                        break;
                    }
                }
            } catch (GridAccessException var13) {
            }

            this.buffer -= amperesUsed * voltage;
            return this.buffer < voltage ? TickRateModulation.IDLE : TickRateModulation.URGENT;
        } else {
            return TickRateModulation.IDLE;
        }
    }

    @Nullable
    private TileEntity getTarget() {
        TileEntity te;
        if (this.isCachedTargetValid) {
            te = this.cachedTarget;
            if (te == null || !te.isInvalid()) {
                return te;
            }
        }

        this.isCachedTargetValid = true;
        te = this.getTile();
        ForgeDirection side = this.getSide();
        return this.cachedTarget = te.getWorldObj().getTileEntity(te.xCoord + side.offsetX, te.yCoord + side.offsetY, te.zCoord + side.offsetZ);
    }

    private long doOutput(long aVoltage, long aAmperage) {
        if (!this.isOutput()) {
            return 0L;
        } else {
            TileEntity te = this.getTarget();
            if (te == null) {
                return 0L;
            } else {
                ForgeDirection oppositeSide = this.getSide().getOpposite();
                if (te instanceof IEnergyConnected) {
                    return ((IEnergyConnected) te).injectEnergyUnits((byte) oppositeSide.ordinal(), aVoltage, aAmperage);
                } else {
                    if (te instanceof IEnergySink) {
                        if (((IEnergySink) te).acceptsEnergyFrom(this.getTile(), oppositeSide)) {
                            long rUsedAmperes;
                            for (rUsedAmperes = 0L; aAmperage > rUsedAmperes && ((IEnergySink) te).getDemandedEnergy() > 0.0D && ((IEnergySink) te).injectEnergy(oppositeSide, (double) aVoltage, (double) aVoltage) < (double) aVoltage; ++rUsedAmperes) {
                            }

                            return rUsedAmperes;
                        }
                    } else if (GregTech_API.mOutputRF && te instanceof IEnergyReceiver) {
                        int rfOut = (int) (aVoltage * (long) GregTech_API.mEUtoRF / 100L);
                        if (((IEnergyReceiver) te).receiveEnergy(oppositeSide, rfOut, true) == rfOut) {
                            ((IEnergyReceiver) te).receiveEnergy(oppositeSide, rfOut, false);
                            return 1L;
                        }

                        if (GregTech_API.mRFExplosions && GregTech_API.sMachineExplosions && ((IEnergyReceiver) te).getMaxEnergyStored(oppositeSide) < rfOut * 600 && rfOut > 32 * GregTech_API.mEUtoRF / 100) {
                            float tStrength = (long) rfOut < GT_Values.V[0] ? 1.0F : ((long) rfOut < GT_Values.V[1] ? 2.0F : ((long) rfOut < GT_Values.V[2] ? 3.0F : ((long) rfOut < GT_Values.V[3] ? 4.0F : ((long) rfOut < GT_Values.V[4] ? 5.0F : ((long) rfOut < GT_Values.V[4] * 2L ? 6.0F : ((long) rfOut < GT_Values.V[5] ? 7.0F : ((long) rfOut < GT_Values.V[6] ? 8.0F : ((long) rfOut < GT_Values.V[7] ? 9.0F : 10.0F))))))));
                            int tX = te.xCoord;
                            int tY = te.yCoord;
                            int tZ = te.zCoord;
                            World tWorld = te.getWorldObj();
                            GT_Utility.sendSoundToPlayers(tWorld, GregTech_API.sSoundList.get(209), 1.0F, -1.0F, tX, tY, tZ);
                            tWorld.setBlock(tX, tY, tZ, Blocks.air);
                            if (GregTech_API.sMachineExplosions) {
                                tWorld.createExplosion(null, (double) tX + 0.5D, (double) tY + 0.5D, (double) tZ + 0.5D, tStrength, true);
                            }
                        }
                    }

                    return 0L;
                }
            }
        }
    }
}

