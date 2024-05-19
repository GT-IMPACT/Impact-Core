package com.impact.mods.gregtech.tileentities.multi.units

import com.google.common.io.ByteArrayDataInput
import com.impact.addon.gt.api.aerostat.IAeroStat
import com.impact.addon.gt.api.position.IPosition
import com.impact.common.managers.AeroStateNetworkManager
import com.impact.impact
import com.impact.mods.gregtech.GT_ItemList
import com.impact.mods.gregtech.blocks.Build_Casing_Helper
import com.impact.mods.gregtech.blocks.Casing_Helper
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.hatches.GTMTEAerostatCargoHatch
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.util.PositionObject
import com.impact.util.Utilits
import com.impact.util.string.MultiBlockTooltipBuilder
import com.impact.util.vector.Vector3i
import com.impact.util.vector.Vector3ic
import gregtech.api.enums.Materials
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.render.TextureFactory
import gregtech.api.util.GT_Utility
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraftforge.common.util.ForgeDirection
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility
import space.impact.packet_network.network.packets.IStreamPacketReceiver
import java.util.*

class GTMTE_Aerostat : GTMTE_Impact_BlockBase<GTMTE_Aerostat>, IStreamPacketReceiver, IAeroStat {

    companion object {
        const val MAX_BUFFER = 50_000
        val CASING: Block = Casing_Helper.sCaseCore3
        val CASING_META: Int = Build_Casing_Helper.AEROSTATE_PLATFORM.meta
        val INDEX_CASE: ITexture = Textures.BlockIcons.casingTexturePages[3][CASING_META + 32]
        val CASING_TEXTURE_ID: Int = Build_Casing_Helper.AEROSTATE_PLATFORM.idCasing
        private val STRUCTURE = StructureDefinition.builder<GTMTE_Aerostat>()
            .addShape(
                "main", arrayOf(
                    arrayOf("AAA"),
                    arrayOf("A~A"),
                    arrayOf("AAA"),
                )
            )
            .addElement('A', StructureUtility.ofBlock(CASING, CASING_META))
            .build()
    }

    constructor(aID: Int, aNameRegional: String?) : super(aID, "impact.multis.aerostat", aNameRegional)
    constructor(aName: String?) : super(aName)

    @JvmField
    var indexSelect: Int = 0

    @JvmField
    var currentLocationPlatforms: MutableList<IAeroStat> = ArrayList()

    @JvmField
    var curBuffer: Int = 0

    @JvmField
    var selectedDistance = 0

    private var firstOpen: Boolean = true
    private var isFullBuffer: Boolean = false
    private var isFluidEngine: Boolean = false
    var targetPos: IPosition? = null
    private var cargoHatch: GTMTEAerostatCargoHatch? = null

    override var owner: String = ""
    override var name: String = ""
    var uuid: String = UUID.randomUUID().toString()

    override fun hasIndicator(): Boolean {
        return true
    }

    override fun getStack(): ItemStack {
        return GT_ItemList.Aerostat.get(1)
    }

    override fun getTexture(aBaseMetaTileEntity: IGregTechTileEntity, aSide: Byte, aFacing: Byte, aColorIndex: Byte, aActive: Boolean, aRedstone: Boolean): Array<ITexture> {
        return if (aSide.toInt() == 1) arrayOf(INDEX_CASE, TextureFactory.of(Texture.Icons.PLATFORM_AEROSTATE_OVERLAY)) else arrayOf(INDEX_CASE)
    }

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity): IMetaTileEntity {
        return GTMTE_Aerostat(this.mName)
    }

    override fun isFacingValid(aFacing: Byte): Boolean {
        return aFacing > 1
    }

    override fun getStructureDefinition(): IStructureDefinition<GTMTE_Aerostat> = STRUCTURE

    override fun getClientGUI(aID: Int, aPlayerInventory: InventoryPlayer, aBaseMetaTileEntity: IGregTechTileEntity): Any? {
        return null
    }

    override fun getServerGUI(aID: Int, aPlayerInventory: InventoryPlayer, aBaseMetaTileEntity: IGregTechTileEntity): Any? {
        return null
    }

    override fun machineStructure(thisController: IGregTechTileEntity): Boolean {
        cargoHatch = null
        mCrowbar = true
        mSolderingTool = mCrowbar
        mHardHammer = mSolderingTool
        mSoftHammer = mHardHammer
        mScrewdriver = mSoftHammer
        mWrench = mScrewdriver
        if (thisController.yCoord < 60) {
            return false
        }

        val forgeDirection: Vector3ic = Vector3i(
            ForgeDirection.getOrientation(thisController.backFacing.toInt()).offsetX,
            ForgeDirection.getOrientation(thisController.backFacing.toInt()).offsetY,
            ForgeDirection.getOrientation(thisController.backFacing.toInt()).offsetZ
        )

        var formationChecklist = true

        for (X in -1..1) {
            for (Z in -1..1) {
                if (X == 0 && Z == 0) {
                    continue
                }
                val offset = rotateOffsetVector(forgeDirection, X, 0, Z)
                val currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z())

                if (!addInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                    if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) === CASING)
                        && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()).toInt() == CASING_META)
                    ) {
                    } else {
                        formationChecklist = false
                    }
                }
            }
        }

        if (mOutputHatches.size > 1) {
            formationChecklist = false
        }

        if (mInputHatches.size == 1 || mInputBusses.size == 1) {
            isFluidEngine = mInputHatches.size == 1
            return formationChecklist
        }

        return false
    }

    override fun setupMachine(aBaseMetaTileEntity: IGregTechTileEntity?, stack: ItemStack?) {
        super.setupMachine(aBaseMetaTileEntity, stack)
        if (mMachine) AeroStateNetworkManager.markUpdate()
    }

    override fun addInputToMachineList(aTileEntity: IGregTechTileEntity?, aBaseCasingIndex: Int): Boolean {
        return addCargoHatch(aTileEntity, aBaseCasingIndex)
                || super.addOutputToMachineList(aTileEntity, aBaseCasingIndex)
                || super.addInputToMachineList(aTileEntity, aBaseCasingIndex)
    }

    private fun addCargoHatch(te: IGregTechTileEntity?, aBaseCasingIndex: Int): Boolean {
        if (te == null) return false
        val mte = te.metaTileEntity
        if (mte != null) {
            if (mte is GT_MetaTileEntity_Hatch) {
                mte.updateTexture(aBaseCasingIndex)
            }
            if (mte is GTMTEAerostatCargoHatch) {
                cargoHatch = mte
                return true
            }
        }
        return false
    }

    override fun onPostTick(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
        super.onPostTick(aBaseMetaTileEntity, aTick)
        if (aBaseMetaTileEntity.isServerSide && mMachine && aTick % 18 == 0L) {
            if (isFluidEngine) {
                if (curBuffer < MAX_BUFFER - 1000) {
                    isFullBuffer = false
                }

                if (!isFullBuffer && depleteInput(Materials.NatruralGas.getGas(1000)) || depleteInput(Materials.Gas.getGas(1000))) {
                    curBuffer += 1000
                    if (curBuffer + 1000 > MAX_BUFFER) {
                        curBuffer = MAX_BUFFER
                        isFullBuffer = true
                    }
                }
            } else {
                val items: List<ItemStack> = storedInputs

                if (items.isNotEmpty()) {
                    val input = storedInputs[0]

                    if (input != null && input.item != null && input.stackSize > 0) {
                        for (i in 0 until input.stackSize) {
                            val item = ItemStack(input.item, 1, input.itemDamage)
                            val burnTime = TileEntityFurnace.getItemBurnTime(item)

                            if (curBuffer < MAX_BUFFER - burnTime) {
                                isFullBuffer = false
                            }

                            if (!isFullBuffer && burnTime > 0 && depleteInput(item)) {
                                input.stackSize--
                                curBuffer += burnTime
                                if (curBuffer + burnTime >= MAX_BUFFER) {
                                    curBuffer = MAX_BUFFER
                                    isFullBuffer = true
                                }
                            }
                        }
                    }
                    updateSlots()
                }
            }
        }
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        val b = MultiBlockTooltipBuilder("aerostate")
        b
            .addInfo("info.0", "Moving over a distance by fuel")
            .addInfo("info.3", "Natural/Refinery Gas: 64 chunks or Any Burn Time Fuel: 16 chunks")
            .addInfo("info.2", "Minimum height (Y coord) for work: 60")
            .addTypeMachine("type", "Moving Machine")
            .addController()
            .sizeStructure(3, 1, 3)
            .addInputHatch(1)
            .addCasingInfo("case.1", "Aerostate Platform Casing", 7)
            .signAndFinalize()
        return b
    }

    override fun onRightclick(aBaseMetaTileEntity: IGregTechTileEntity, aPlayer: EntityPlayer): Boolean {
        return mMachine && super.onRightclick(aBaseMetaTileEntity, aPlayer)
    }

    fun setLocationName(name: String) {
        this.name = name
        impact.proxy.addClientSideChatMessages("Set location name: $name")

        if (baseMetaTileEntity.isServerSide)
            AeroStateNetworkManager.addAeroState(owner, this, uuid)
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        if (te.isServerSide) {
            if (owner.isEmpty()) owner = te.ownerName
            if (uuid.isEmpty()) uuid = UUID.randomUUID().toString()
            if (owner.isNotEmpty() && name.isNotEmpty()) {
                AeroStateNetworkManager.addAeroState(owner, this, uuid)
            }
        }
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)
        aNBT.setInteger("curBuffer", curBuffer)
        aNBT.setString("aerName", name)
        aNBT.setString("playerName", owner)
        aNBT.setString("uuidName", uuid)
        aNBT.setBoolean("firstOpen", firstOpen)
        aNBT.setBoolean("isFluidEngine", isFluidEngine)

        targetPos?.also {
            aNBT.setTag("targetPos", it.saveToNBT())
        }
    }

    override fun inValidate() {
        cargoHatch?.changeTarget(null, null)
        AeroStateNetworkManager.onRemove(owner, this, uuid)
        super.inValidate()
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)
        curBuffer = aNBT.getInteger("curBuffer")
        firstOpen = aNBT.getBoolean("firstOpen")
        name = aNBT.getString("aerName")
        uuid = aNBT.getString("uuidName")
        owner = aNBT.getString("playerName")
        isFluidEngine = aNBT.getBoolean("isFluidEngine")

        aNBT.getCompoundTag("targetPos")?.also {
            val pos = PositionObject.loadFromNBT(it)
            if (!pos.isInvalid()) targetPos = pos
        }
    }

    override fun checkRecipe(itemStack: ItemStack?): Boolean {
        return false
    }

    private val distanceTravel: Int
        get() {
            val thisPos = pos
            val newPos = targetPos
            if (newPos != null && thisPos != newPos) {
                val distance = Utilits.distanceBetween3D(thisPos.x, newPos.x, thisPos.y, newPos.y, thisPos.z, newPos.z)
                if (distance >= 16) {
                    return distance
                }
            }
            return 0
        }

    fun updateSelected(indexSelect: Int) {
        this.indexSelect = indexSelect
        if (currentLocationPlatforms.isNotEmpty()) {
            val newPos = currentLocationPlatforms[indexSelect]
            targetPos = newPos.pos
            cargoHatch?.changeTarget(newPos.pos) {
                (curBuffer - selectedDistance * 25 >= 0).also { isOk ->
                    if (isOk) curBuffer -= selectedDistance * 25
                }
            }
            selectedDistance = distanceTravel
        }
    }

    fun toTravel(aPlayer: EntityPlayer) {

        if (currentLocationPlatforms.isEmpty()) {
            targetPos = null
        }

        val thisPos = pos
        targetPos?.also { newPos ->
            val distance = Utilits.distanceBetween3D(thisPos.x, newPos.x, thisPos.y, newPos.y, thisPos.z, newPos.z)
            if (distance >= 16 && thisPos.d == newPos.d) {
                if (curBuffer - distance * 25 > 0) {
                    curBuffer -= distance * 25
                    aPlayer.setPositionAndUpdate(newPos.x + 0.5, newPos.y + 1.0, newPos.z + 0.5)
                } else {
                    GT_Utility.sendChatToPlayer(aPlayer, "No Fuel")
                }
            } else {
                GT_Utility.sendChatToPlayer(aPlayer, "Error Distance")
            }
        }
    }

    override fun onScrewdriverRightClick(aSide: Byte, aPlayer: EntityPlayer, aX: Float, aY: Float, aZ: Float) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ)
    }

    override fun construct(itemStack: ItemStack, b: Boolean) {
        buildPiece(itemStack, b, 1, 0, 1)
    }

    override fun receive(data: ByteArrayDataInput) {
        setLocationName(data.readUTF())
    }

    override fun hasSeparate(): Boolean {
        return false
    }

    fun inputItems(inventory: Array<ItemStack?>): Boolean {
        if (mOutputBusses.isNotEmpty()) {
            val items = inventory.clone()
            Arrays.fill(inventory, null)
            for (item in items) {
                addOutput(item)
            }
            return true
        }
        return false
    }

    override val pos: IPosition
        get() = PositionObject(baseMetaTileEntity)

    override fun updateState(nested: List<IAeroStat>) {
        val te = baseMetaTileEntity ?: return
        val updateList = getRadiusAeroStates(nested, te.ownerName, te, if (isFluidEngine) 1024 else 256)
        currentLocationPlatforms.clear()
        currentLocationPlatforms.addAll(updateList)
        selectedDistance = distanceTravel

        val index = currentLocationPlatforms.indexOfFirst { targetPos == it.pos }
        if (index > 0) updateSelected(index)
    }

    private fun getRadiusAeroStates(updateList: List<IAeroStat>, owner: String, te: IGregTechTileEntity, maxRange: Int): List<IAeroStat> {
        val list: MutableList<IAeroStat> = ArrayList()
        val thisPos = PositionObject(te)

        for (aerostat in updateList) {
            val location = aerostat.pos
            val teTarget = te.getIGregTechTileEntity(location.x, location.y, location.z)
            if (teTarget == null || thisPos.isEquals(location) || thisPos.dPos != location.d) continue
            if (teTarget.metaTileEntity is GTMTE_Aerostat) {
                val gtmteAerostat = teTarget.metaTileEntity as GTMTE_Aerostat
                if (gtmteAerostat.owner == owner) {
                    val distance = Utilits.distanceBetween3D(thisPos.xPos, location.x, thisPos.yPos, location.y, thisPos.zPos, location.z)
                    if (distance in 16..maxRange) {
                        list.add(gtmteAerostat)
                    }
                }
            }
        }

        return list
    }

    override fun isValid(): Boolean {
        return baseMetaTileEntity?.metaTileEntity != null
    }
}
