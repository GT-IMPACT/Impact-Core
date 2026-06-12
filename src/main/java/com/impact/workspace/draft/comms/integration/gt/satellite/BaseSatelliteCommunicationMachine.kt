package com.impact.workspace.draft.comms.integration.gt.satellite

import com.impact.client.gui.GuiRegistry
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.util.Utilits
import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.adapters.GregtechMetaTileEntityCommsAccess.Companion.access
import com.impact.workspace.draft.comms.adapters.commsWorldKey
import com.impact.workspace.draft.comms.common.CommsActiveProvider
import com.impact.workspace.draft.comms.parts.CommsMachinePartProvider
import com.impact.workspace.draft.comms.parts.types.SatelliteCommsMachinePart
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound

abstract class BaseSatelliteCommunicationMachine<T : BaseSatelliteCommunicationMachine<T>>
    : GTMTE_Impact_BlockBase<T>, CommsMachinePartProvider, CommsActiveProvider {

    private var lastActive = false

    constructor(id: Int, aName: String, aNameRegional: String) : super(id, aName, aNameRegional)
    constructor(aName: String) : super(aName)

    override val part by lazy {
        SatelliteCommsMachinePart(
            access = baseMetaTileEntity.access,
            activeProvider = this,
        )
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        part.validate()
    }

    override fun inValidate() {
        part.invalidate()
        super.inValidate()
    }

    override fun saveNBTData(nbt: NBTTagCompound) {
        super.saveNBTData(nbt)
        nbt.setBoolean("lastActive", lastActive)
        part.saveNBTData(nbt)
    }

    override fun loadNBTData(nbt: NBTTagCompound) {
        super.loadNBTData(nbt)
        lastActive = nbt.getBoolean("lastActive")
        part.loadNBTData(nbt)
    }

    protected fun commsMarkUpdateNetwork() {
        CommsServer.getWorld(baseMetaTileEntity.commsWorldKey()).markDirty()
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (!te.isServerSide) return

        if (tick % 20 * 60 == 0L) {
            noMaintenance()
        }

        if (tick % 40 == 0L && lastActive != te.isActive) {
            lastActive = te.isActive
            commsMarkUpdateNetwork()
        }
    }

    override fun isCommsActive(): Boolean {
        return mMachine && baseMetaTileEntity.isActive
    }

    override fun onNotePadRightClick(side: Byte, player: EntityPlayer, x: Float, y: Float, z: Float) {
        super.onNotePadRightClick(side, player, x, y, z)

        Utilits.openGui(player, GuiRegistry.CommsNetwork.id, baseMetaTileEntity)
    }
}
