package com.impact.addon.gt.api.data

import com.impact.mods.gregtech.enums.Texture
import gregtech.api.enums.Dyes
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.render.TextureFactory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.fluids.FluidStack

abstract class DataHatchConnectorBase<T : ProvideData<*>> : GT_MetaTileEntity_Hatch, IConnectsToDataCable {

    var data: T? = null
    var id: Short = -1

    constructor(aID: Int, aName: String, aNameRegional: String, aTier: Int, descr: String) : super(aID, aName, aNameRegional, aTier, 0, descr)
    constructor(aName: String?, aTier: Int, aDescription: String?, aTextures: Array<Array<Array<ITexture?>?>?>?) : super(aName, aTier, 0, aDescription, aTextures)

    override fun getTexturesActive(aBaseTexture: ITexture) = arrayOf(
        aBaseTexture,
        TextureFactory.of(Texture.Icons.DATA_CONNECTOR_ACTIVE, Dyes.getModulation(baseMetaTileEntity.colorization.toInt(), Dyes.MACHINE_METAL.rgba)),
        TextureFactory.of(Texture.Icons.DATA_CONNECTOR_CONNECT),
    )

    override fun getTexturesInactive(aBaseTexture: ITexture) = arrayOf(
        aBaseTexture,
        TextureFactory.of(Texture.Icons.DATA_CONNECTOR_SIDE, Dyes.getModulation(baseMetaTileEntity.colorization.toInt(), Dyes.MACHINE_METAL.rgba)),
        TextureFactory.of(Texture.Icons.DATA_CONNECTOR_CONNECT),
    )

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)
        aNBT.setShort("dID", id)
        data?.also { aNBT.setTag("dDATA", it.toNbt()) }
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)
        id = aNBT.getShort("dID")
        aNBT.getCompoundTag("dDATA")?.also { data = loadDataFromNBT(it) }
    }

    protected abstract fun loadDataFromNBT(nbt: NBTTagCompound): T
    abstract fun moveAround(te: IGregTechTileEntity)

    override fun onPostTick(te: IGregTechTileEntity, aTick: Long) {
        if (te.isServerSide) {
            if (4L == aTick % 20) {
                if (data == null) {
                    baseMetaTileEntity.isActive = false
                } else {
                    baseMetaTileEntity.isActive = true
                    moveAround(te)
                }
            }
        }
    }

    override fun getColor() = baseMetaTileEntity.colorization
    override fun isFacingValid(aFacing: Byte) = true
    override fun isAccessAllowed(aPlayer: EntityPlayer?) = true
    override fun isLiquidInput(aSide: Byte) = false
    override fun isFluidInputAllowed(aFluid: FluidStack?) = false
    override fun allowPullStack(aBaseMetaTileEntity: IGregTechTileEntity?, aIndex: Int, aSide: Byte, aStack: ItemStack?) = false
    override fun allowPutStack(aBaseMetaTileEntity: IGregTechTileEntity?, aIndex: Int, aSide: Byte, aStack: ItemStack?) = false
    override fun isValidSlot(aIndex: Int) = false
    override fun isGivingInformation() = true

    override fun getDescription() = arrayOf(
        EnumChatFormatting.GOLD.toString() + "Must be painted to work"
    )

    override fun getInfoData() = arrayOf(
        "Content: ${EnumChatFormatting.AQUA} ${data?.getContentString() ?: 0}",
        "Data History: ${EnumChatFormatting.RED} ${data?.getTraceSize() ?: 0}",
    )
}
