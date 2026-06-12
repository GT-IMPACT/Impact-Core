package com.impact.workspace.draft.comms.integration.gt.tower

import com.impact.addon.waila.WailaProvider
import com.impact.client.gui.GuiRegistry
import com.impact.mods.gregtech.blocks.Casing_Helper
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_CommunicationTower_Receiver
import com.impact.util.Utilits
import com.impact.util.multis.GT_StructureUtility
import com.impact.util.multis.IGT_HatchAdder
import com.impact.util.nbt.getUuid
import com.impact.util.nbt.setNbt
import com.impact.util.string.MultiBlockTooltipBuilder
import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.adapters.GregtechMetaTileEntityCommsAccess.Companion.access
import com.impact.workspace.draft.comms.adapters.commsWorldKey
import com.impact.workspace.draft.comms.common.CommsActiveProvider
import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.parts.CommsMachinePartProvider
import com.impact.workspace.draft.comms.parts.types.TowerCommsMachinePart
import gregtech.api.enums.Materials
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.IStructureElement
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility
import java.util.function.Supplier

class TowerCommunicationMachine : GTMTE_Impact_BlockBase<TowerCommunicationMachine>, CommsMachinePartProvider, WailaProvider, CommsActiveProvider {

    val sCommunReceiver: HashSet<GTMTE_CommunicationTower_Receiver> = HashSet()
    private var isShowConnections = false
    private var lastActive = false

    override val part by lazy {
        TowerCommsMachinePart(
            access = baseMetaTileEntity.access,
            radiusProvider = 72,
            activeProvider = this,
        )
    }

    constructor(aID: Int, aNameRegional: String) : super(aID, "impact.multis.communicationtower", aNameRegional)

    constructor(aName: String) : super(aName)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity {
        return TowerCommunicationMachine(mName)
    }

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity,
        aSide: Byte,
        aFacing: Byte,
        aColorIndex: Byte,
        aActive: Boolean,
        aRedstone: Boolean
    ): Array<ITexture?> {
        return if (aSide.toInt() == 1) {
            val overlay = if (aActive) Texture.Icons.TOWER_OVERLAY_ACTIVE else Texture.Icons.TOWER_OVERLAY
            arrayOf(INDEX_CASE, TextureFactory.of(overlay))
        } else {
            arrayOf(INDEX_CASE)
        }
    }

    override fun getServerGUI(aID: Int, aPlayerInventory: InventoryPlayer?, aBaseMetaTileEntity: IGregTechTileEntity?): Any? {
        return null
    }

    override fun getClientGUI(aID: Int, aPlayerInventory: InventoryPlayer?, aBaseMetaTileEntity: IGregTechTileEntity?): Any? {
        return null
    }

    override fun checkRecipe(itemStack: ItemStack?): Boolean {
        this.mMaxProgresstime = 20
        this.mEfficiency = 10000
        this.mEfficiencyIncrease = 10000
        this.mEUt = 0
        return true
    }

    override fun clearHatches() {
        sCommunReceiver.clear()
        super.clearHatches()
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        return MultiBlockTooltipBuilder("ttc").apply {
            addTypeMachine("name", "Communication Tower")
            addInfo("info.0", "Working radius is 9x9 chunks square")
            addSeparator()
            addController()
            addOtherStructurePart("other.0", "Communication Receiver Hatch", "other.1", "Top")
            addOtherStructurePart("other.2", "Steel Frame Box", "other.3", "Frames")
            addCasingInfo("case", "Tower Casing", 41)
            signAndFinalize()
        }
    }

    override fun onNotePadRightClick(aSide: Byte, aPlayer: EntityPlayer?, aX: Float, aY: Float, aZ: Float) {
        isShowConnections = !isShowConnections

        Utilits.openGui(aPlayer, GuiRegistry.CommsTowerRadar.id, baseMetaTileEntity)
    }

    override fun getStructureDefinition(): IStructureDefinition<TowerCommunicationMachine> {
        return STRUCTURE_DEFINITION
    }

    override fun machineStructure(thisController: IGregTechTileEntity?): Boolean {
        var formationCheckList = checkPiece(3, 17, 3)
        if (sCommunReceiver.size != 4) {
            formationCheckList = false
        }

//        for (receiver in sCommunReceiver) {
//            receiver.connectToTower(this) // TODO
//        }

        return formationCheckList
    }

    override fun construct(stackSize: ItemStack?, hintsOnly: Boolean) {
        buildPiece(stackSize, hintsOnly, 3, 17, 3)
    }

    override fun getInfoData(): Array<String> {
        return emptyArray() // TODO
    }

    override fun isCommsActive(): Boolean {
        return mMachine && baseMetaTileEntity.isActive
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)
        aNBT.setBoolean("lastActive", lastActive)
        part.saveNBTData(aNBT)
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)
        lastActive = aNBT.getBoolean("lastActive")
        part.loadNBTData(aNBT)
    }

    override fun hasIndicator(): Boolean {
        return true
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

    private fun commsMarkUpdateNetwork() {
        CommsServer.getWorld(baseMetaTileEntity.commsWorldKey()).markDirty()
    }

    override fun onFirstTick(aBaseMetaTileEntity: IGregTechTileEntity?) {
        super.onFirstTick(aBaseMetaTileEntity)
        part.validate()
    }

    override fun inValidate() {
        for (receiver in sCommunReceiver) {
            receiver.connectToTower(null)
        }
        part.invalidate()
        super.inValidate()
    }

    override fun hasSeparate(): Boolean {
        return false
    }

    fun addCommunicationHatchToMachineList(te: IGregTechTileEntity?, aBaseCasingIndex: Int): Boolean {
        val mte = te?.metaTileEntity as? GTMTE_CommunicationTower_Receiver ?: return false
        mte.updateTexture(aBaseCasingIndex)
        return sCommunReceiver.add(mte)
    }

    override fun writeInfoWaila(nbt: NBTTagCompound) {
        nbt.setByte("status", part.commsNetworkState.ordinal.toByte())
        part.commsId.setNbt("commsId", nbt)
    }

    override fun readInfoWaila(nbt: NBTTagCompound, tt: MutableList<String>) {
        val commsId = nbt.getUuid("commsId")
        tt += "ID: " + EnumChatFormatting.YELLOW + commsId.toString().substring(0, 8)

        val statusOrdinal = nbt.getByte("status").toInt()
        val status = CommsNetworkState.entries.find { it.ordinal == statusOrdinal } ?: return

        val color = when (status) {
            CommsNetworkState.DISCONNECTED -> EnumChatFormatting.RED
            CommsNetworkState.CONNECTING -> EnumChatFormatting.YELLOW
            CommsNetworkState.CONNECTED -> EnumChatFormatting.GREEN
        }

        tt += "Status: $color$status"
    }

    companion object {
        var CASING: Block = Casing_Helper.sCasePage8_3
        var CASING_META: Byte = 6
        var INDEX_CASE: ITexture? = Textures.BlockIcons.casingTexturePages[8][CASING_META + 64]
        var CASING_TEXTURE_ID: Int = CASING_META + 64 + 128 * 8
        private val machineListAdder = IGT_HatchAdder<TowerCommunicationMachine> { te, igt, index ->
            te.addToMachineList(igt, index.toInt())
        }
        private val commsTowersAdder = IGT_HatchAdder<TowerCommunicationMachine> { te, igt, index ->
            te.addCommunicationHatchToMachineList(igt, index.toInt())
        }
        private val lazyFramesAdder = Supplier<IStructureElement<TowerCommunicationMachine>> {
            GT_StructureUtility.ofFrame(Materials.Steel)
        }
        val STRUCTURE_DEFINITION: IStructureDefinition<TowerCommunicationMachine> = StructureDefinition.builder<TowerCommunicationMachine>()
            .addShape(
                "main", arrayOf(
                    arrayOf("       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E"),
                    arrayOf("       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  "),
                    arrayOf("       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA "),
                    arrayOf("   E   ", "   E   ", "   E   ", "   E   ", "  DED  ", "   E   ", "       ", "       ", "       ", " E   E ", "       ", "       ", "       ", "       ", "E     E", "       ", "       ", " AA~AA ", " AAAAA "),
                    arrayOf("       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA "),
                    arrayOf("       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  "),
                    arrayOf("       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E")
                )
            )
            .addElement(
                'A', StructureUtility.ofChain(
                    GT_StructureUtility.ofHatchAdder(machineListAdder, CASING_TEXTURE_ID, CASING, CASING_META.toInt()),
                    StructureUtility.ofBlock(CASING, CASING_META.toInt())
                )
            )
            .addElement('D', GT_StructureUtility.ofHatchAdder(commsTowersAdder, CASING_TEXTURE_ID, CASING, CASING_META.toInt()))
            .addElement('E', StructureUtility.lazy(lazyFramesAdder))
            .build()
    }
}