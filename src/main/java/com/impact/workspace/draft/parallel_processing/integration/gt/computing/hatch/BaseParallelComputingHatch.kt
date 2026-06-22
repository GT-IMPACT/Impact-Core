package com.impact.workspace.draft.parallel_processing.integration.gt.computing.hatch

import com.impact.util.nbt.getUuid
import com.impact.util.nbt.setNbt
import gregtech.api.interfaces.ITexture
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import java.util.UUID

abstract class BaseParallelComputingHatch : GT_MetaTileEntity_Hatch {

    constructor(id: Int, name: String, nameRegional: String, tier: Int, slots: Int,  description: Array<String>)
            : super(id, name, nameRegional, tier, slots, description)

    constructor(name: String, tier: Int, slots: Int, description: Array<String>, textures: Array<Array<Array<ITexture>>>)
            : super(name, tier, slots, description, textures)

    private var parentId: UUID? = null

    override fun saveNBTData(nbt: NBTTagCompound) {
        super.saveNBTData(nbt)
        parentId?.setNbt("parentPpId", nbt)
    }

    override fun loadNBTData(nbt: NBTTagCompound) {
        super.loadNBTData(nbt)
        parentId = nbt.getUuid("parentPpId")
    }

    override fun isSimpleMachine(): Boolean {
        return true
    }

    override fun isFacingValid(aFacing: Byte): Boolean {
        return true
    }

    override fun isAccessAllowed(aPlayer: EntityPlayer?): Boolean {
        return true
    }

    open fun bindParent(parentId: UUID?) {
        this.parentId = parentId
    }

    fun checkParent(parentId: UUID?): Boolean {
        return this.parentId == parentId
    }

    fun getParentId(): UUID? {
        return parentId
    }
}
