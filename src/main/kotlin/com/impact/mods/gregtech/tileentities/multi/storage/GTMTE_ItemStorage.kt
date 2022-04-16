package com.impact.mods.gregtech.tileentities.multi.storage

import com.impact.common.storage.StorageHandler
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase
import gregtech.api.render.TextureFactory
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import org.lwjgl.input.Keyboard

class GTMTE_ItemStorage : GT_MetaTileEntity_MultiBlockBase {

    private var maxStorageSize = 1
    private var storage: StorageHandler? = null

    constructor(
        aID: Int, aNameRegional: String, maxStorageSize: Int
    ) : super(aID, "impact.multimachine.itemstorage", aNameRegional) {
        this.maxStorageSize = maxStorageSize
    }

    constructor(aName: String) : super(aName)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity): IMetaTileEntity {
        return GTMTE_ItemStorage(mName)
    }

    override fun getDescription(): Array<String> {
        val b = MultiBlockTooltipBuilder("storage_item")
        b.addInfo("info.0", "High-Tech fluid tank!").addTypeMachine("name", "Storage Capacity").addInfo("info.1", "Right-Click the controller with a screwdriver will turn on excess voiding.").addSeparator().addSeparator().beginStructureBlock(3, 7, 3).addController().signAndFinalize()
        return if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            b.information
        } else {
            b.structureInformation
        }
    }

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity?, aSide: Byte, aFacing: Byte, aColorIndex: Byte, aActive: Boolean, aRedstone: Boolean
    ): Array<ITexture> {
        return if (aSide == aFacing) arrayOf(
            Textures.BlockIcons.casingTexturePages[1][48], TextureFactory.of(
                if (aActive) Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR_ACTIVE
                else Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR
            )
        ) else arrayOf(Textures.BlockIcons.casingTexturePages[1][48])
    }

    override fun getClientGUI(
        aID: Int, aPlayerInventory: InventoryPlayer, aBaseMetaTileEntity: IGregTechTileEntity
    ): Any? {
        return null
    }

    override fun getServerGUI(
        aID: Int, aPlayerInventory: InventoryPlayer, aBaseMetaTileEntity: IGregTechTileEntity
    ): Any? {
        return null
    }

    override fun checkRecipe(aStack: ItemStack?): Boolean {
        return true
    }

    override fun checkMachine(
        aBaseMetaTileEntity: IGregTechTileEntity, aStack: ItemStack
    ): Boolean {
        return true
    }

    override fun saveNBTData(aNBT: NBTTagCompound?) {
        val nbt = aNBT ?: NBTTagCompound()

        storage?.apply { nbt.setTag("storage", saveNBT(NBTTagCompound())) } ?: nbt.setTag("storage", NBTTagCompound())
        super.saveNBTData(nbt)
    }

    override fun loadNBTData(aNBT: NBTTagCompound?) {
        val nbt = aNBT ?: NBTTagCompound()

        storage = StorageHandler(maxStorageSize)
        storage?.apply { loadNBT(nbt) }
        super.loadNBTData(nbt)
    }

    override fun isCorrectMachinePart(stack: ItemStack) = true
    override fun isGivingInformation() = true
    override fun getMaxEfficiency(stack: ItemStack?) = 10000
    override fun getPollutionPerTick(stack: ItemStack) = 0
    override fun getDamageToComponent(stack: ItemStack) = 0
    override fun explodesOnComponentBreak(stack: ItemStack) = false
}