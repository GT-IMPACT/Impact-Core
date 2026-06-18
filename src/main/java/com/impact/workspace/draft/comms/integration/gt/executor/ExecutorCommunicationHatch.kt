package com.impact.workspace.draft.comms.integration.gt.executor

import com.impact.addon.waila.WailaProvider
import com.impact.mods.gregtech.enums.Texture
import com.impact.util.Utilits
import com.impact.util.nbt.getUuid
import com.impact.util.nbt.setNbt
import gregtech.api.enums.Dyes
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.render.TextureFactory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import java.util.UUID

open class ExecutorCommunicationHatch : GT_MetaTileEntity_Hatch, WailaProvider {

    constructor(id: Int, nameRegional: String)
            : super(id, "impact.machine.comms.exec.hatch", nameRegional, 3, 0, desc)

    constructor(id: Int, name: String, nameRegional: String, tier: Int, description: Array<String>)
            : super(id, name, nameRegional, tier, 0, description)

    constructor(name: String, tier: Int, description: Array<String>, textures: Array<Array<Array<ITexture>>>)
            : super(name, tier, 0, description, textures)

    private var parentId: UUID? = null

    override fun newMetaEntity(te: IGregTechTileEntity): IMetaTileEntity {
        return ExecutorCommunicationHatch(mName, mTier.toInt(), mDescriptionArray, mTextures)
    }

    override fun getTexturesActive(base: ITexture): Array<ITexture?> {
        val color = Dyes.getModulation(baseMetaTileEntity.colorization.toInt(), Dyes.MACHINE_METAL.rgba)
        return arrayOf(base, TextureFactory.of(Texture.Icons.PRL_HATCH_GREEN, color))
    }

    override fun getTexturesInactive(base: ITexture): Array<ITexture?> {
        val color = Dyes.getModulation(baseMetaTileEntity.colorization.toInt(), Dyes.MACHINE_METAL.rgba)
        return arrayOf(base, TextureFactory.of(Texture.Icons.PRL_HATCH_YELLOW, color))
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

    open fun checkParent(parentId: UUID?): Boolean {
        return this.parentId == parentId || this.parentId == null
    }

    override fun writeInfoWaila(nbt: NBTTagCompound) {
        parentId?.setNbt("parentId", nbt)
    }

    override fun readInfoWaila(nbt: NBTTagCompound, tt: MutableList<String>) {
        val parent = nbt.getUuid("parentId")
        if (parent != null) {
            tt += "Parent ID: ${parent.toString().substring(0, 8)}"
        }
    }

    companion object {
        private val desc = arrayOf(
            Utilits.impactTag(),
            "Receives a signal from a Communication Tower",
            "Used in Communication Machines",
            EnumChatFormatting.YELLOW.toString() + "[]" + EnumChatFormatting.GRAY + " - Connecting",
            EnumChatFormatting.GREEN.toString() + "[]" + EnumChatFormatting.GRAY + " - Connected",
        )
    }
}
