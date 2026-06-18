package com.impact.workspace.draft.parallel_processing.integration.gt.computing.hatch

import com.impact.addon.waila.WailaProvider
import com.impact.mods.gregtech.GT_ItemList
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.gui.parallelcomputer.GT_Container_Rack
import com.impact.mods.gregtech.gui.parallelcomputer.GT_GUIContainer_Rack
import com.impact.util.Utilits
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.nbt.NBTTagCompound
import java.util.UUID

class RackParallelComputingHatch : BaseParallelComputingHatch, WailaProvider {

    constructor(id: Int, nameRegional: String)
            : super(id, "impact.machine.rack", nameRegional, 5, 4, desc) {
        itemsPpu[GT_ItemList.UpgradeCasingT1[1].unlocalizedName] = 1
        itemsPpu[GT_ItemList.UpgradeCasingT2[1].unlocalizedName] = 2
        itemsPpu[GT_ItemList.UpgradeCasingT3[1].unlocalizedName] = 3
        itemsPpu[GT_ItemList.UpgradeCasingT4[1].unlocalizedName] = 4
    }

    constructor(name: String, description: Array<String>, textures: Array<Array<Array<ITexture>>>)
            : super(name, 5, 4, description, textures)

    private var ppuCapacity: Int = 0

    override fun newMetaEntity(te: IGregTechTileEntity): IMetaTileEntity {
        return RackParallelComputingHatch(mName, mDescriptionArray, mTextures)
    }

    override fun getTexturesActive(base: ITexture): Array<ITexture?> {
        return arrayOf(base, TextureFactory.of(Texture.Icons.RACK_OVERLAY_ACTIVE))
    }

    override fun getTexturesInactive(base: ITexture): Array<ITexture?> {
        return arrayOf(base, TextureFactory.of(Texture.Icons.RACK_OVERLAY))
    }

    override fun onRightclick(te: IGregTechTileEntity, player: EntityPlayer?): Boolean {
        if (te.isClientSide) return true
        te.openGUI(player)
        return true
    }

    override fun getServerGUI(aID: Int, inv: InventoryPlayer?, te: IGregTechTileEntity): Any {
        return GT_Container_Rack(inv, te)
    }

    override fun getClientGUI(aID: Int, inv: InventoryPlayer?, te: IGregTechTileEntity): Any {
        return GT_GUIContainer_Rack(inv, te, "Computer Rack")
    }

    override fun writeInfoWaila(nbt: NBTTagCompound) {
        nbt.setInteger("ppuCapacity", ppuCapacity)
    }

    override fun readInfoWaila(nbt: NBTTagCompound, tt: MutableList<String>) {
        val capacity = nbt.getInteger("ppuCapacity")
        tt += "Capacity: $capacity PPU"
    }

    override fun saveNBTData(nbt: NBTTagCompound) {
        super.saveNBTData(nbt)
        nbt.setInteger("ppuCapacity", ppuCapacity)
    }

    override fun loadNBTData(nbt: NBTTagCompound) {
        super.loadNBTData(nbt)
        ppuCapacity = nbt.getInteger("ppuCapacity")
    }

    override fun getInventoryStackLimit(): Int {
        return 1
    }

    override fun isOutputFacing(aSide: Byte): Boolean {
        return false
    }

    override fun isInputFacing(aSide: Byte): Boolean {
        return aSide == baseMetaTileEntity.frontFacing
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (te.isServerSide && tick % 400 == 0L) {
            updateCapacity()
        }
    }

    private fun updateCapacity() {
        var currentCapacity = 0
        mInventory.forEach { stack ->
            if (stack != null && stack.stackSize == 1) {
                val gen = itemsPpu[stack.unlocalizedName] ?: return@forEach
                currentCapacity += 1 shl (2 * gen)
            }
        }
        ppuCapacity = currentCapacity
    }

    fun getPpu(parentId: UUID): Int {
        return if (checkParent(parentId)) {
            updateCapacity()
            ppuCapacity
        } else 0
    }

    companion object {
        private val itemsPpu = hashMapOf<String, Int>()
        private val desc = arrayOf(
            Utilits.impactTag(),
            "Increase the total amount of parallel processing units (PPU)",
            "Used in Super Parallel Computer",
        )
    }
}
