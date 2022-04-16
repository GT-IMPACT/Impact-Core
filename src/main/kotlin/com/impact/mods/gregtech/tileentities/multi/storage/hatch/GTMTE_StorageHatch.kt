package com.impact.mods.gregtech.tileentities.multi.storage.hatch

import appeng.api.config.Actionable
import appeng.api.networking.GridFlags
import appeng.api.networking.IGridNode
import appeng.api.networking.security.BaseActionSource
import appeng.api.networking.security.IActionHost
import appeng.api.storage.IMEInventory
import appeng.api.storage.StorageChannel
import appeng.api.storage.data.IAEItemStack
import appeng.api.storage.data.IItemList
import appeng.api.util.DimensionalCoord
import appeng.me.helpers.AENetworkProxy
import appeng.me.helpers.IGridProxyable
import com.impact.common.storage.StorageHandler
import com.impact.mods.gregtech.enums.Texture
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.render.TextureFactory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

class GTMTE_StorageHatch : GT_MetaTileEntity_Hatch,
    IGridProxyable,
    IActionHost,
    IMEInventory<IAEItemStack>
//    IMEInventoryHandler<IAEItemStack>
{


    private var storage: StorageHandler? = null
    private var gridProxy: AENetworkProxy? = null

    constructor(
        aID: Int,
        aName: String,
        aNameRegional: String,
        aTier: Int
    ) : super(aID, aName, aNameRegional, aTier, 0, "")

    constructor(
        aName: String,
        aTier: Int,
        aDescription: Array<String>,
        aTextures: Array<Array<Array<ITexture?>?>?>?
    ) : super(aName, aTier, 0, aDescription, aTextures)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity {
        return GTMTE_StorageHatch(mName, mTier.toInt(), mDescriptionArray, mTextures)
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)

    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)

    }

    override fun getTexturesActive(aBaseTexture: ITexture): Array<ITexture>? {
        return arrayOf(aBaseTexture, TextureFactory.of(Texture.Icons.OVERLAY_MULTIHATCH))
    }

    override fun getTexturesInactive(aBaseTexture: ITexture): Array<ITexture>? {
        return arrayOf(aBaseTexture, TextureFactory.of(Texture.Icons.OVERLAY_MULTIHATCH))
    }

    override fun isSimpleMachine() = true
    override fun isFacingValid(aFacing: Byte) = true
    override fun isAccessAllowed(aPlayer: EntityPlayer?) = true

    override fun getGridNode(p0: ForgeDirection?): IGridNode {
        return proxy.node
    }

    override fun getProxy(): AENetworkProxy {
        if (gridProxy == null) {
            gridProxy = AENetworkProxy(this, "proxy", getStackForm(1), true)
            gridProxy?.apply {
                onReady()
                setFlags(GridFlags.REQUIRE_CHANNEL)
            }
        }
        return gridProxy as AENetworkProxy
    }

    override fun securityBreak() {}

    override fun getLocation() =
        baseMetaTileEntity.let { DimensionalCoord(it.world, it.xCoord, it.yCoord.toInt(), it.zCoord) }

    override fun getActionableNode(): IGridNode = proxy.node

    override fun injectItems(input: IAEItemStack, type: Actionable, src: BaseActionSource): IAEItemStack? {
        val stack = input.itemStack ?: return null
        storage?.apply {
            val isConfirm = add(input.itemStack, true)
            if (isConfirm) {

            }
        }
    }

    override fun extractItems(request: IAEItemStack, mode: Actionable, src: BaseActionSource): IAEItemStack? {
        val stack = request.itemStack

    }

    override fun getAvailableItems(p0: IItemList<IAEItemStack>?): IItemList<IAEItemStack> {
        TODO("Not yet implemented")
    }

    override fun getChannel(): StorageChannel {
        TODO("Not yet implemented")
    }


}