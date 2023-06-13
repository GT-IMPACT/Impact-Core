package com.impact.addon.gt.tiles.data

import com.impact.addon.gt.api.data.IConnectsToDataCable
import com.impact.mods.gregtech.enums.Texture
import gregtech.GT_Mod
import gregtech.api.enums.Dyes
import gregtech.api.enums.Textures.BlockIcons
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.BaseMetaPipeEntity
import gregtech.api.metatileentity.MetaPipeEntity
import gregtech.api.render.TextureFactory
import gregtech.api.util.GT_Utility
import gregtech.common.GT_Client
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection.*

class CableDataScience : MetaPipeEntity, IConnectsToDataCable {

    var connectionCount: Byte = 0
    private var isActive = false

    constructor(aID: Int) : super(aID, "cable.science_data", "Data Science Fiber Cable", 0)
    constructor(aName: String) : super(aName, 0)

    override fun newMetaEntity(iGregTechTileEntity: IGregTechTileEntity?): IMetaTileEntity {
        return CableDataScience(mName)
    }

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity?,
        aSide: Byte,
        aConnections: Byte,
        aColorIndex: Byte,
        aConnected: Boolean,
        aRedstone: Boolean
    ): Array<ITexture> {
        return arrayOf(
            TextureFactory.of(Texture.Icons.DATA_SCIENCE_CABLE),
            TextureFactory.of(Texture.Icons.DATA_SCIENCE_CABLE_OVERLAY, Dyes.getModulation(aColorIndex.toInt(), Dyes.MACHINE_METAL.rgba)),
            TextureFactory.builder().addIcon(Texture.Icons.DATA_SCIENCE_CABLE_OVERLAY).setRGBA(Dyes.getModulation(aColorIndex.toInt(), Dyes.MACHINE_METAL.rgba)).glow().build()
        )
    }

    override fun getDescription() = arrayOf<String>()
    override fun allowPutStack(iGregTechTileEntity: IGregTechTileEntity?, i: Int, b: Byte, itemStack: ItemStack?) = false
    override fun allowPullStack(iGregTechTileEntity: IGregTechTileEntity?, i: Int, b: Byte, itemStack: ItemStack?) = false
    override fun renderInside(b: Byte) = false
    override fun getTileEntityBaseType() = 4.toByte()

    override fun loadNBTData(nbtTagCompound: NBTTagCompound) {
        isActive = nbtTagCompound.getBoolean("isActive")
    }

    override fun saveNBTData(nbtTagCompound: NBTTagCompound) {
        nbtTagCompound.setBoolean("isActive", isActive)
    }

    override fun onFirstTick(aBaseMetaTileEntity: IGregTechTileEntity) {
        onPostTick(aBaseMetaTileEntity, 31)
    }

    override fun onPostTick(te: IGregTechTileEntity, aTick: Long) {
        if (te.isServerSide) {
            if (aTick and 31L == 31L) {
                if (isActive) isActive = false
                mConnections = 0
                connectionCount = 0
                val myColor = te.colorization
                if (te.colorization < 0) {
                    return
                }
                var sideStart: Byte = 0
                var sideEnd: Byte
                while (sideStart < 6) {
                    sideEnd = GT_Utility.getOppositeSide(sideStart.toInt())
                    val tTileEntity = te.getTileEntityAtSide(sideStart)
                    if (tTileEntity is IConnectsToDataCable) {
                        val tColor = tTileEntity.getColor()
                        if (tColor != myColor) {
                            sideStart++
                            continue
                        }
                        if ((tTileEntity as IConnectsToDataCable).canConnectData(sideEnd)) {
                            mConnections = (mConnections.toInt() or (1 shl sideStart.toInt())).toByte()
                            connectionCount++
                        }
                    } else if (tTileEntity is IGregTechTileEntity) {
                        val meta = (tTileEntity as IGregTechTileEntity).metaTileEntity
                        if (meta is IConnectsToDataCable) {
                            if (meta.getColor() != myColor) {
                                sideStart++; continue
                            }
                            if (meta.canConnectData(sideEnd)) {
                                mConnections = (mConnections.toInt() or (1 shl sideStart.toInt())).toByte()
                                connectionCount++
                            }
                        }
                    }
                    sideStart++
                }
            }
        } else if (te.isClientSide && GT_Client.changeDetected == 4) {
            te.issueTextureUpdate()
        }
    }

    override fun canConnectData(side: Byte) = true

    override fun getNext(source: IConnectsToDataCable?): IConnectsToDataCable? {
        if (connectionCount.toInt() != 2) return null
        for (b in 0..5) {
            if (mConnections.toInt() and (1 shl b) == 0) continue
            val next = baseMetaTileEntity.getTileEntityAtSide(b.toByte())
            if (next is IConnectsToDataCable && next != source) {
                if (next.isDataInputFacing(GT_Utility.getOppositeSide(b))) {
                    return next
                }
            } else if (next is IGregTechTileEntity) {
                val meta = (next as IGregTechTileEntity).metaTileEntity
                if (meta is IConnectsToDataCable && meta != source) {
                    if (meta is CableDataScience && meta.connectionCount.toInt() == 2) {
                        meta.markUsed()
                        return meta
                    }
                    if (meta.isDataInputFacing(GT_Utility.getOppositeSide(b))) {
                        return meta
                    }
                }
            }
        }
        return null
    }

    fun markUsed() {
        isActive = true
    }

    override fun getCollisionBoundingBoxFromPool(aWorld: World?, aX: Int, aY: Int, aZ: Int): AxisAlignedBB? {
        val tSpace = (1f - 0.375f) / 2
        var tSide0 = tSpace
        var tSide1 = 1f - tSpace
        var tSide2 = tSpace
        var tSide3 = 1f - tSpace
        var tSide4 = tSpace
        var tSide5 = 1f - tSpace
        if (baseMetaTileEntity.getCoverIDAtSide(0.toByte()) != 0) {
            tSide4 = 0f
            tSide2 = tSide4
            tSide0 = tSide2
            tSide5 = 1f
            tSide3 = tSide5
        }
        if (baseMetaTileEntity.getCoverIDAtSide(1.toByte()) != 0) {
            tSide4 = 0f
            tSide2 = tSide4
            tSide5 = 1f
            tSide3 = tSide5
            tSide1 = tSide3
        }
        if (baseMetaTileEntity.getCoverIDAtSide(2.toByte()) != 0) {
            tSide4 = 0f
            tSide2 = tSide4
            tSide0 = tSide2
            tSide5 = 1f
            tSide1 = tSide5
        }
        if (baseMetaTileEntity.getCoverIDAtSide(3.toByte()) != 0) {
            tSide4 = 0f
            tSide0 = tSide4
            tSide5 = 1f
            tSide3 = tSide5
            tSide1 = tSide3
        }
        if (baseMetaTileEntity.getCoverIDAtSide(4.toByte()) != 0) {
            tSide4 = 0f
            tSide2 = tSide4
            tSide0 = tSide2
            tSide3 = 1f
            tSide1 = tSide3
        }
        if (baseMetaTileEntity.getCoverIDAtSide(5.toByte()) != 0) {
            tSide2 = 0f
            tSide0 = tSide2
            tSide5 = 1f
            tSide3 = tSide5
            tSide1 = tSide3
        }
        val tConn = (baseMetaTileEntity as BaseMetaPipeEntity).mConnections
        if (tConn.toInt() and (1 shl DOWN.ordinal) != 0) {
            tSide0 = 0f
        }
        if (tConn.toInt() and (1 shl UP.ordinal) != 0) {
            tSide1 = 1f
        }
        if (tConn.toInt() and (1 shl NORTH.ordinal) != 0) {
            tSide2 = 0f
        }
        if (tConn.toInt() and (1 shl SOUTH.ordinal) != 0) {
            tSide3 = 1f
        }
        if (tConn.toInt() and (1 shl WEST.ordinal) != 0) {
            tSide4 = 0f
        }
        if (tConn.toInt() and (1 shl EAST.ordinal) != 0) {
            tSide5 = 1f
        }
        return AxisAlignedBB.getBoundingBox((aX + tSide4).toDouble(), (aY + tSide0).toDouble(), (aZ + tSide2).toDouble(), (aX + tSide5).toDouble(), (aY + tSide1).toDouble(), (aZ + tSide3).toDouble())
    }

    override fun getThickNess(): Float {
        return if (GT_Mod.instance.isClientSide && GT_Client.hideValue == 1) 0.0625f
        else 0.375f
    }

    override fun getColor() = baseMetaTileEntity.colorization
    override fun isDataInputFacing(side: Byte) = true
}