package com.impact.addon.gt.tiles.data

import com.impact.addon.gt.api.data.DataHatchConnectorBase
import com.impact.addon.gt.api.data.IConnectsToDataCable
import com.impact.addon.gt.api.data.ProvideDataScience
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import net.minecraft.nbt.NBTTagCompound

class DataHatchScienceInput : DataHatchConnectorBase<ProvideDataScience> {

    private var delDelay = true

    constructor(aID: Int, aNameRegional: String, aTier: Int) : super(aID, "hatch.data_science.input.$aTier", aNameRegional, aTier, "")
    constructor(aName: String, aTier: Int, aDescription: String, aTextures: Array<Array<Array<ITexture?>?>?>?) : super(aName, aTier, aDescription, aTextures)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): MetaTileEntity {
        return DataHatchScienceInput(mName, mTier.toInt(), mDescription, mTextures)
    }

    override fun isInputFacing(aSide: Byte): Boolean {
        return aSide == baseMetaTileEntity.frontFacing
    }

    override fun isDataInputFacing(side: Byte): Boolean {
        return isInputFacing(side)
    }

    override fun isOutputFacing(aSide: Byte): Boolean {
        return false
    }

    override fun isSimpleMachine(): Boolean {
        return true
    }

    override fun loadDataFromNBT(nbt: NBTTagCompound): ProvideDataScience {
        return ProvideDataScience(nbt)
    }

    override fun canConnectData(side: Byte): Boolean {
        return isInputFacing(side)
    }

    override fun getNext(source: IConnectsToDataCable?): IConnectsToDataCable? {
        return null
    }

    fun setContents(qIn: ProvideDataScience?) {
        if (qIn == null) {
            this.data = null
        } else {
            val content = qIn.getContent()
            if (content != null && content.content > 0L) {
                this.data = qIn
                delDelay = true
            } else {
                this.data = null
            }
        }
    }

    override fun getDescription() = arrayOf(
        "Science Data Input for Multiblocks",
        "High speed fibre optics connector.",
        "Must be painted to work",
    )

    override fun moveAround(te: IGregTechTileEntity) {
        if (delDelay) {
            delDelay = false
        } else {
            setContents(null)
        }
    }
}
