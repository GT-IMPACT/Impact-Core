package com.impact.mods.gregtech.tileentities.basic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.impact.mods.gregtech.blocks.other.GT_Block_LongDistancePipe;
import cpw.mods.fml.common.Optional;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull_NonElectric;
import gregtech.api.util.GT_Utility;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import static net.minecraft.util.StatCollector.translateToLocal;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;

/**
 * Inspired/ported from GregTech 6 under the LGPL license
 * <p>
 * Copyright (c) 2020 GregTech-6 Team
 * <p>
 * This file is part of GregTech.
 * <p>
 * GregTech is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * <p>
 * GregTech is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with GregTech. If
 * not, see <http://www.gnu.org/licenses/>.
 */

public abstract class GTMTE_LongDistancePipelineBase extends GT_MetaTileEntity_BasicHull_NonElectric {

    protected GTMTE_LongDistancePipelineBase mTarget = null, mSender = null;
    protected ChunkCoordinates mTargetPos = null;

    protected long mDistance = 0;

    public GTMTE_LongDistancePipelineBase(int aID, String aName, String aNameRegional, int aTier, String aDescription) {
        super(aID, aName, aNameRegional, aTier, aDescription);
    }

    public GTMTE_LongDistancePipelineBase(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    public boolean isSender() {
        return mSender == null;
    }

    public int getMinDistance() {
        return 0;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        if (mTargetPos != null && mTarget != this) {
            aNBT.setBoolean("target", true);
            aNBT.setInteger("target.x", mTargetPos.posX);
            aNBT.setInteger("target.y", mTargetPos.posY);
            aNBT.setInteger("target.z", mTargetPos.posZ);
            aNBT.setLong("mDistance", mDistance);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        if (aNBT.hasKey("mDistance")) {
            mDistance = aNBT.getLong("mDistance");
        }
        if (aNBT.hasKey("target")) {
            mTargetPos = new ChunkCoordinates(aNBT.getInteger("target.x"), aNBT.getInteger("target.y"),
                    aNBT.getInteger("target.z"));
        }
    }

    public boolean isSameClass(GTMTE_LongDistancePipelineBase other) {
        return false;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) {
            return true;
        }
        ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
        if (tCurrentItem != null) {
            if (GT_Utility.isStackInList(tCurrentItem, GregTech_API.sSoftHammerList)) {
                scanPipes();
                return true;
            }
        }
        return false;
    }

    public boolean isDead() {
        return getBaseMetaTileEntity() == null || getBaseMetaTileEntity().isDead();
    }

    public boolean checkTarget() {
        final IGregTechTileEntity gt_tile = getBaseMetaTileEntity();
        if (gt_tile == null || !gt_tile.isAllowedToWork() || gt_tile.isClientSide()) {
            return false;
        }
        World world = gt_tile.getWorld();

        if (mTargetPos == null) {
            // We don't have a target position, scan the pipes
            scanPipes();
        } else if (mTarget == null || mTarget.isDead()) {
            // We don't have a target, or it's dead.  Try checking the target position
            mTarget = null;
            if (world.blockExists(mTargetPos.posX, mTargetPos.posY, mTargetPos.posZ)) {
                // Only check if the target position is loaded
                TileEntity te = world.getTileEntity(mTargetPos.posX, mTargetPos.posY, mTargetPos.posZ);
                final IMetaTileEntity tMeta;
                if (te instanceof BaseMetaTileEntity
                        && ((tMeta = ((BaseMetaTileEntity) te)
                                .getMetaTileEntity()) instanceof GTMTE_LongDistancePipelineBase)
                        && isSameClass((GTMTE_LongDistancePipelineBase) tMeta)) {
                    // It's the right type!
                    mTarget = (GTMTE_LongDistancePipelineBase) tMeta;
                } else if (te != null) {
                    // It isn't the right type, kill the target position
                    mTargetPos = null;
                }
            }
        }
        if (mTarget == null || mTarget == this) {
            return false;
        }
        if (mTarget.mSender == null || mTarget.mSender.isDead() || mTarget.mSender.mTarget == null
                || mTarget.mSender.mTarget.isDead()) {
            mTarget.mSender = this;
        }

        mTarget.mDistance = mDistance;

        return mTarget.mSender == this;
    }

    @Override
    public ArrayList<String> getSpecialDebugInfo(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer,
            int aLogLevel, ArrayList<String> aList) {
        if (mSender != null && !mSender.isDead() && mSender.mTarget == this) {
            final ChunkCoordinates coords = mSender.getCoords();
            aList.addAll(Arrays.asList("Is the Target",
                    "Sender is at: X: " + coords.posX + " Y: " + coords.posY + " Z: " + coords.posZ));
        } else {
            aList.add(checkTarget() ? "Has Target" : "Has no loaded Target");
            if (mTargetPos != null) {
                aList.add("Target should be around: X: " + mTargetPos.posX + " Y: " + mTargetPos.posY + " Z: "
                        + mTargetPos.posZ);
            }
        }

        return aList;

    }

    public long getDistance() {
        return mDistance;
    }

    // What meta should the pipes for this pipeline have
    public abstract int getPipeMeta();

    protected void scanPipes() {
        if (mSender != null && !mSender.isDead() && mSender.mTarget == this) {
            return;
        }
        final IGregTechTileEntity gtTile = getBaseMetaTileEntity();
        if (gtTile == null) {
            return;
        }

        final World world = gtTile.getWorld();
        if (world == null) {
            return;
        }

        mTargetPos = getCoords();
        mTarget = this;
        mSender = null;
        mDistance = 0;

        ChunkCoordinates start = getFacingOffset(gtTile, gtTile.getBackFacing());
        if (!world.blockExists(start.posX, start.posY, start.posZ)) {
            return;
        }
        Block pipe = world.getBlock(start.posX, start.posY, start.posZ);
        if (!(pipe instanceof GT_Block_LongDistancePipe)
                || world.getBlockMetadata(start.posX, start.posY, start.posZ) != getPipeMeta()) {
            return;
        }

        Map<ChunkCoordinates, Long> distances = new HashMap<>();
        Queue<ChunkCoordinates> queue = new ArrayDeque<>();
        distances.put(start, 1L);
        queue.add(start);
        while (!queue.isEmpty()) {
            ChunkCoordinates current = queue.remove();
            long distance = distances.get(current);
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                ChunkCoordinates next = new ChunkCoordinates(current.posX + direction.offsetX,
                        current.posY + direction.offsetY, current.posZ + direction.offsetZ);
                if (!world.blockExists(next.posX, next.posY, next.posZ)) {
                    continue;
                }
                if (world.getBlock(next.posX, next.posY, next.posZ) == pipe
                        && world.getBlockMetadata(next.posX, next.posY, next.posZ) == getPipeMeta()) {
                    if (!distances.containsKey(next)) {
                        distances.put(next, distance + 1L);
                        queue.add(next);
                    }
                    continue;
                }

                TileEntity targetTile = world.getTileEntity(next.posX, next.posY, next.posZ);
                if (targetTile == gtTile || !(targetTile instanceof BaseMetaTileEntity)) {
                    continue;
                }
                BaseMetaTileEntity targetBase = (BaseMetaTileEntity) targetTile;
                IMetaTileEntity meta = targetBase.getMetaTileEntity();
                if (!(meta instanceof GTMTE_LongDistancePipelineBase)) {
                    continue;
                }
                GTMTE_LongDistancePipelineBase target = (GTMTE_LongDistancePipelineBase) meta;
                if (isSameClass(target) && current.equals(getFacingOffset(targetBase, targetBase.getFrontFacing()))) {
                    mTarget = target;
                    mTargetPos = target.getCoords();
                    mDistance = distance;
                    return;
                }
            }
        }
    }

    public ChunkCoordinates getFacingOffset(IGregTechTileEntity gt_tile, byte aSide) {
        return new ChunkCoordinates(gt_tile.getOffsetX(aSide, 1), gt_tile.getOffsetY(aSide, 1),
                gt_tile.getOffsetZ(aSide, 1));

    }

    public ChunkCoordinates getCoords() {
        final IGregTechTileEntity gt_tile = getBaseMetaTileEntity();
        return new ChunkCoordinates(gt_tile.getXCoord(), gt_tile.getYCoord(), gt_tile.getZCoord());
    }

    @Override
    public void onMachineBlockUpdate() {
        //GT_Mod.GT_FML_LOGGER.info("You're dead to me");
        mTargetPos = null;
        mSender = null;
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return new ITexture[0][0][0];
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        final int side = accessor.getSide().ordinal();
        final int facing = tag.getInteger("facing");

        if (side == facing) {
            tooltip.add(
                    EnumChatFormatting.GOLD + translateToLocal("impact.waila.pipeline.in") + EnumChatFormatting.RESET);
        } else if (side == ForgeDirection.OPPOSITES[facing]) {
            tooltip.add(
                    EnumChatFormatting.BLUE + translateToLocal("impact.waila.pipeline.out") + EnumChatFormatting.RESET);
        } else {
            tooltip.add(translateToLocal("impact.waila.pipeline.side"));
        }

        if (this instanceof GTMTE_LongDistancePipelineEnergy) {

            tooltip.add(tag.getBoolean("pipeline.isSender")
                    ? EnumChatFormatting.GOLD + translateToLocal("impact.waila.pipeline.sender")
                            + EnumChatFormatting.RESET
                    : EnumChatFormatting.BLUE + translateToLocal("impact.waila.pipeline.recipient")
                            + EnumChatFormatting.RESET);

            long distance = tag.getLong("pipeline.distance");
            tooltip.add(translateToLocalFormatted("impact.waila.pipeline.distance",
                    distance < tag.getInteger("pipeline.minDistance")
                            ? EnumChatFormatting.RED + translateToLocal("impact.waila.pipeline.low_distance")
                            : distance));
        }
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
            int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        if (this instanceof GTMTE_LongDistancePipelineEnergy) {
            tag.setLong("pipeline.distance", getDistance());
            tag.setInteger("pipeline.minDistance", getMinDistance());
            tag.setBoolean("pipeline.isSender", isSender());
        }
    }
}
