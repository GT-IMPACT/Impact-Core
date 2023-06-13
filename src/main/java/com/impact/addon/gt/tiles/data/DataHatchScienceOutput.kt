package com.impact.addon.gt.tiles.data

import com.impact.addon.gt.api.data.DataHatchConnectorBase
import com.impact.addon.gt.api.data.IConnectsToDataCable
import com.impact.addon.gt.api.data.ProvideDataScience
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.util.GT_Utility
import net.minecraft.nbt.NBTTagCompound

class DataHatchScienceOutput : DataHatchConnectorBase<ProvideDataScience> {

    companion object {
        private const val MAX_RANGE_CHECK_PATH = 256
    }

    constructor(aID: Int, aNameRegional: String, aTier: Int) : super(aID, "hatch.data_science.output.$aTier", aNameRegional, aTier, "")
    constructor(aName: String, aTier: Int, aDescription: String, aTextures: Array<Array<Array<ITexture?>?>?>?) : super(aName, aTier, aDescription, aTextures)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): MetaTileEntity {
        return DataHatchScienceOutput(mName, mTier.toInt(), mDescription, mTextures)
    }

    override fun isOutputFacing(aSide: Byte): Boolean {
        return aSide == baseMetaTileEntity.frontFacing
    }

    override fun isInputFacing(aSide: Byte): Boolean {
        return false
    }

    override fun isSimpleMachine(): Boolean {
        return true
    }

    override fun isDataInputFacing(side: Byte): Boolean {
        return isInputFacing(side)
    }

    override fun loadDataFromNBT(nbt: NBTTagCompound): ProvideDataScience {
        return ProvideDataScience(nbt)
    }

    override fun canConnectData(side: Byte): Boolean {
        return isOutputFacing(side)
    }

    override fun moveAround(te: IGregTechTileEntity) {
        var current: IConnectsToDataCable? = this
        var source: IConnectsToDataCable? = this
        var next: IConnectsToDataCable?
        var range = 0

        while (current?.getNext(source).also { next = it } != null && range++ < MAX_RANGE_CHECK_PATH) {
            if (next is DataHatchScienceInput) {
                (next as DataHatchScienceInput).setContents(data)
                break
            }
            source = current
            current = next
        }
        data = null
    }

    override fun getDescription() = arrayOf(
        "Science Data Output for Multiblocks",
        "High speed fibre optics connector",
        "Must be painted to work",
    )


    override fun getNext(source: IConnectsToDataCable?): IConnectsToDataCable? {
        val base = baseMetaTileEntity
        val color = base.colorization
        if (color < 0) return null
        val next = base.getIGregTechTileEntityAtSide(base.frontFacing) ?: return null
        val meta = next.metaTileEntity
        if (meta is CableDataScience) {
            meta.markUsed()
            return meta
        } else if (
            meta is DataHatchScienceInput
            && meta.getColor() == color
            && meta.canConnectData(GT_Utility.getOppositeSide(base.frontFacing.toInt()))
        ) {
            return meta
        }
        return null
    }
}