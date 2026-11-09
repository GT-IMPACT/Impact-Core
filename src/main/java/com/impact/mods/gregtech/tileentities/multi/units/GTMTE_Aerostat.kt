package com.impact.mods.gregtech.tileentities.multi.units


import gregtech.api.logic.TimedProcessingLogic
import gregtech.api.logic.ProcessingLogic
import com.impact.addon.gt.api.aerostat.IAeroStat
import com.impact.addon.gt.api.position.IPosition
import com.cleanroommc.modularui.factory.PosGuiData
import com.cleanroommc.modularui.screen.ModularPanel
import com.cleanroommc.modularui.screen.UISettings
import com.cleanroommc.modularui.value.sync.PanelSyncManager
import com.impact.mods.gregtech.gui.aerostat.AerostatGui
import com.impact.common.managers.AeroStateNetworkManager
import com.impact.mods.gregtech.GT_ItemList
import com.impact.mods.gregtech.blocks.HatchCounts
import com.impact.mods.gregtech.blocks.ImpactCasings
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.hatches.GTMTEAerostatCargoHatch
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.util.ImpactMultiblockTooltipBuilder
import com.impact.util.PositionObject
import cpw.mods.fml.common.Optional
import gregtech.api.enums.HatchElement
import gregtech.api.enums.Materials
import gregtech.api.interfaces.ICasing
import gregtech.api.interfaces.IHatchElement
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase
import gregtech.api.render.TextureFactory
import gregtech.api.modularui2.GTGuis
import gregtech.api.util.GTStructureUtility
import gregtech.api.util.MultiblockTooltipBuilder
import gregtech.api.util.StructureRequirements
import java.util.Arrays
import java.util.UUID
import mcp.mobius.waila.api.IWailaConfigHandler
import mcp.mobius.waila.api.IWailaDataAccessor
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.StatCollector.translateToLocal
import net.minecraft.util.StatCollector.translateToLocalFormatted
import net.minecraft.world.World
import net.minecraft.util.ChatComponentTranslation
import space.impact.structures.alignment.IAlignmentLimits
import space.impact.structures.hint.HintColor
import space.impact.structures.structure.IStructureDefinition
import space.impact.structures.structure.ISurvivalBuildEnvironment
import space.impact.structures.structure.StructureDefinition
import space.impact.structures.structure.StructureUtility.withHint

class GTMTE_Aerostat : GTMTE_Impact_BlockBase<GTMTE_Aerostat>, IAeroStat {

    @Optional.Method(modid = "Waila")
    public override fun getWailaBody(
        stack: ItemStack,
        tooltip: MutableList<String>,
        accessor: IWailaDataAccessor,
        config: IWailaConfigHandler,
    ) {
        super.getWailaBody(
            stack,
            tooltip,
            accessor,
            config,
        )
        val tag = accessor.nbtData
        val stationName = tag.getString("aerostatName")
        if (stationName.isNotEmpty()) {
            tooltip.add(
                translateToLocalFormatted(
                    "impact.waila.aerostat.namestation",
                    EnumChatFormatting.GOLD.toString() + stationName + EnumChatFormatting.RESET,
                )
            )
        } else {
            tooltip.add(EnumChatFormatting.RED.toString() + translateToLocal("impact.waila.aerostat.no_valid_station"))
        }

        val stationOwner = tag.getString("aerostatOwner")
        if (stationOwner.isNotEmpty()) {
            tooltip.add(
                translateToLocalFormatted(
                    "impact.waila.aerostat.owner",
                    EnumChatFormatting.GREEN.toString() + stationOwner + EnumChatFormatting.RESET,
                )
            )
        }
    }

    @Optional.Method(modid = "Waila")
    public override fun getWailaNBTData(
        player: EntityPlayerMP,
        tile: TileEntity,
        tag: NBTTagCompound,
        world: World,
        x: Int,
        y: Int,
        z: Int,
    ) {
        super.getWailaNBTData(
            player,
            tile,
            tag,
            world,
            x,
            y,
            z,
        )
        tag.setString(
            "aerostatName",
            name,
        )
        tag.setString(
            "aerostatOwner",
            owner,
        )
    }

    private var casingCount: Int = 0

    companion object {
        const val MAX_BUFFER = 50_000
        public const val FUEL_PER_BLOCK: Int = 25
        public const val MAX_NAME_LENGTH: Int = 48

        @JvmStatic
        public fun hasAerostat(player: EntityPlayer?): Boolean {
            val held = player?.heldItem ?: return false
            return GT_ItemList.Aerostat.isStackEqual(held)
        }

        private val CASING: ICasing = ImpactCasings.AEROSTATE_PLATFORM
        private val CARGO_HATCH: IHatchElement = object : IHatchElement {
            override fun matches(tile: IMetaTileEntity?): Boolean {
                return tile is GTMTEAerostatCargoHatch
            }

            override fun count(machine: GT_MetaTileEntity_MultiBlockBase): Int {
                return if ((machine as GTMTE_Aerostat).cargoHatch == null) {
                    0
                } else {
                    1
                }
            }

            override fun add(
                machine: GT_MetaTileEntity_MultiBlockBase,
                tile: IGregTechTileEntity?,
                texture: Int,
            ): Boolean {
                return (machine as GTMTE_Aerostat).addCargoHatch(
                    te = tile,
                    aBaseCasingIndex = texture,
                )
            }

            override fun getDescription(): String {
                return translateToLocal("impact.structure.hatch.aerostat_cargo")
            }
        }
        private val SHELL: StructureRequirements = StructureRequirements.builder(
            CASING,
            4,
        ).atLeast(HatchElement.Input).build()
        private val STRUCTURE: IStructureDefinition<GTMTE_Aerostat> by lazy {
            StructureDefinition.builder<GTMTE_Aerostat>()
                .addChannel(
                    GTStructureUtility.HATCH_CHANNEL,
                    1,
                    1,
                )
                .addShape(
                    "main",
                    arrayOf(
                        arrayOf("CCC"),
                        arrayOf("C~C"),
                        arrayOf("CCC"),
                    ),
                )
                .addElement(
                    'C',
                    withHint(
                        GTStructureUtility.buildHatchAdder(GTMTE_Aerostat::class.java)
                            .allow(
                                HatchElement.Output,
                                CARGO_HATCH,
                            )
                            .autoPlace(
                                HatchElement.Output,
                                CARGO_HATCH,
                            )
                            .buildAndChain(SHELL) { machine ->
                                machine.casingCount++
                            },
                        HintColor.GREEN,
                    ),
                )
                .build()
        }
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

    private var isFluidEngine: Boolean = false
    private var automaticName: Boolean = true
    var targetPos: IPosition? = null
    private var cargoHatch: GTMTEAerostatCargoHatch? = null

    override var owner: String = ""
    override var name: String = ""
    public override var uuid: String = UUID.randomUUID().toString()

    public val travelRange: Int
        get() {
            return if (isFluidEngine) {
                1024
            } else {
                256
            }
        }

    public val defaultStationName: String
        get() {
            val tile = baseMetaTileEntity ?: return ""
            return "${tile.xCoord}, ${tile.yCoord}, ${tile.zCoord}"
        }

    override fun hasIndicator(): Boolean {
        return true
    }

    override fun getStack(): ItemStack {
        return GT_ItemList.Aerostat.get(1)
    }

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity,
        aSide: Byte,
        aFacing: Byte,
        aColorIndex: Byte,
        aActive: Boolean,
        aRedstone: Boolean,
    ): Array<ITexture> {
        return if (aSide.toInt() == 1) {
            arrayOf(
                CASING.texture,
                TextureFactory.of(Texture.Icons.PLATFORM_AEROSTATE_OVERLAY),
            )
        } else {
            arrayOf(CASING.texture)
        }
    }

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity): IMetaTileEntity {
        return GTMTE_Aerostat(this.mName)
    }

    public override fun getInitialAlignmentLimits(): IAlignmentLimits {
        return IAlignmentLimits { direction, rotation, flip ->
            (direction.ordinal > 1) && rotation.isNotRotated && flip.isNotFlipped
        }
    }

    public override fun getStructureDefinition(): IStructureDefinition<GTMTE_Aerostat> {
        return STRUCTURE
    }


    public override fun machineStructure(thisController: IGregTechTileEntity): Boolean {
        casingCount = 0
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

        val formationChecklist = checkPiece(
            1,
            0,
            1,
        ) && SHELL.isSatisfied(
            this,
            casingCount,
        )

        if (mOutputHatches.size > 1 || HatchCounts.inputBuses(this) > 1 || HatchCounts.inputHatches(this) > 1) {
            return false
        }

        val inputHatches = HatchCounts.inputHatches(this)
        val inputBuses = HatchCounts.inputBuses(this)
        if (inputHatches + inputBuses == 1) {
            isFluidEngine = inputHatches == 1
            return formationChecklist
        }

        return false
    }

    override fun setupMachine(
        aBaseMetaTileEntity: IGregTechTileEntity?,
        stack: ItemStack?,
    ) {
        super.setupMachine(
            aBaseMetaTileEntity,
            stack,
        )
        if (aBaseMetaTileEntity?.isServerSide == true) {
            initializeStation()
            AeroStateNetworkManager.updateNetwork(aBaseMetaTileEntity.world)
        }
    }

    private fun addCargoHatch(
        te: IGregTechTileEntity?,
        aBaseCasingIndex: Int,
    ): Boolean {
        if (cargoHatch != null) {
            return false
        }
        val hatch = te?.metaTileEntity as? GTMTEAerostatCargoHatch ?: return false
        hatch.updateTexture(aBaseCasingIndex)
        cargoHatch = hatch
        return true
    }

    override fun onPostTick(
        aBaseMetaTileEntity: IGregTechTileEntity,
        aTick: Long,
    ) {
        super.onPostTick(
            aBaseMetaTileEntity,
            aTick,
        )
        if (!aBaseMetaTileEntity.isServerSide) {
            return
        }
        if (aTick % 20L == 0L) {
            initializeStation()
        }
        if (mMachine && aTick % 18L == 0L) {
            refuel()
        }
    }

    public fun refuel() {
        if (!mMachine || baseMetaTileEntity?.isServerSide != true || curBuffer >= MAX_BUFFER) {
            return
        }
        val fuelBefore = curBuffer
        if (isFluidEngine) {
            if (MAX_BUFFER - curBuffer >= 1000 &&
                (depleteInput(Materials.NatruralGas.getGas(1000)) || depleteInput(Materials.Gas.getGas(1000)))
            ) {
                curBuffer += 1000
            }
        } else {
            for (input in storedInputs) {
                if (input == null || input.stackSize <= 0) {
                    continue
                }
                val fuelItem = input.copy()
                fuelItem.stackSize = 1
                val burnTime = TileEntityFurnace.getItemBurnTime(fuelItem)
                if (burnTime <= 0) {
                    continue
                }
                val count = minOf(
                    a = input.stackSize,
                    b = (MAX_BUFFER - curBuffer) / burnTime,
                )
                repeat(count) {
                    if (depleteInput(fuelItem)) {
                        curBuffer += burnTime
                    }
                }
            }
        }
        if (curBuffer != fuelBefore) {
            updateSlots()
            markDirty()
        }
    }

    public override fun createTooltip(): MultiblockTooltipBuilder {
        return ImpactMultiblockTooltipBuilder(mName)
            .useCompactStructure()
            .addMachineType("Moving Machine")
            .addInfo("Travels between platforms with the same owner in the same dimension.")
            .addInfo("Solid fuel: furnace fuels; range 16-256 blocks.")
            .addInfo("Gas fuel: Natural Gas or Refinery Gas; range 16-1024 blocks.")
            .addInfo("Requires an Aerostat controller in hand; controller height must be Y 60 or higher.")
            .beginStructureBlock(3, 1, 3, false)
            .addController("center of the platform")
            .addStructureSection("Components")
            .addStructurePartInfo(CASING, "%s+; fill unused platform positions", SHELL.minimumCasings)
            .addStructureSection("Hatches and buses")
            .addHatchInfo("Input Bus or Input Hatch", "exactly 1 total; platform casings")
            .addHatchInfo("Output Buses", "optional; platform casings")
            .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
            .addHatchChannelInfo()
            .toolTipFinisher()
    }

    override fun useMui2(): Boolean {
        return true
    }

    override fun isAccessAllowed(player: EntityPlayer?): Boolean {
        return mMachine && hasAerostat(player)
    }

    public override fun buildUI(
        data: PosGuiData,
        sync: PanelSyncManager,
        settings: UISettings,
    ): ModularPanel {
        return AerostatGui.build(
            this,
            data,
            sync,
            settings,
        )
    }

    override fun onRightclick(
        aBaseMetaTileEntity: IGregTechTileEntity,
        aPlayer: EntityPlayer,
    ): Boolean {
        if (!mMachine || !hasAerostat(aPlayer)) {
            return false
        }
        if (aBaseMetaTileEntity.isServerSide) {
            openStationGui(aPlayer)
        }
        return true
    }

    public fun openStationGui(player: EntityPlayer) {
        val tile = baseMetaTileEntity ?: return
        if (!mMachine || tile.isClientSide || !hasAerostat(player) || !tile.isUseableByPlayer(player)) {
            return
        }
        initializeStation()
        AeroStateNetworkManager.updateNetwork(tile.world)
        GTGuis.open(
            player,
            this,
        )
    }

    public fun setLocationName(name: String) {
        val sanitized = name.trim().filter {
            it >= ' ' && it != '\u007f' && it != '\u00a7'
        }.take(MAX_NAME_LENGTH)
        automaticName = sanitized.isEmpty()
        this.name = sanitized.ifEmpty {
            defaultStationName
        }
        val tile = baseMetaTileEntity ?: return
        if (tile.isServerSide) {
            AeroStateNetworkManager.register(this)
            markDirty()
        }
    }

    public fun initializeStation() {
        val tile = baseMetaTileEntity ?: return
        if (tile.isClientSide) {
            return
        }
        var changed = false
        if (owner != tile.ownerName) {
            owner = tile.ownerName
            changed = true
        }
        val registered = AeroStateNetworkManager.getStation(uuid)
        if (uuid.isEmpty() || (registered != null && registered !== this && registered.isValid() &&
                !registered.pos.isEquals(pos))) {
            uuid = UUID.randomUUID().toString()
            changed = true
        }
        if (name.isBlank() || (automaticName && name != defaultStationName)) {
            name = defaultStationName
            changed = true
        }
        AeroStateNetworkManager.register(this)
        if (changed) {
            markDirty()
        }
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        if (te.isServerSide) {
            initializeStation()
        }
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)
        aNBT.setInteger("curBuffer", curBuffer)
        aNBT.setString("aerName", name)
        aNBT.setBoolean("automaticName", automaticName)
        aNBT.setString("playerName", owner)
        aNBT.setString("uuidName", uuid)
        aNBT.setBoolean("isFluidEngine", isFluidEngine)

        targetPos?.also {
            aNBT.setTag("targetPos", it.saveToNBT())
        }
    }

    override fun inValidate() {
        cargoHatch?.changeTarget(
            pos = null,
            callDecreaseFuel = null,
        )
        AeroStateNetworkManager.unregister(this)
        super.inValidate()
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)
        curBuffer = aNBT.getInteger("curBuffer").coerceIn(
            minimumValue = 0,
            maximumValue = MAX_BUFFER,
        )
        name = aNBT.getString("aerName")
        automaticName = if (aNBT.hasKey("automaticName")) {
            aNBT.getBoolean("automaticName")
        } else {
            name.isBlank()
        }
        uuid = aNBT.getString("uuidName")
        owner = aNBT.getString("playerName")
        isFluidEngine = aNBT.getBoolean("isFluidEngine")

        targetPos = null
        if (aNBT.hasKey("targetPos")) {
            targetPos = PositionObject.loadFromNBT(aNBT.getCompoundTag("targetPos"))
        }
    }

    override fun createProcessingLogic(): ProcessingLogic {
        return TimedProcessingLogic(
            0,
            {
                0
            },
        )
    }

    private val distanceTravel: Int
        get() {
            val destination = targetPos ?: return 0
            return distanceTo(destination)
        }

    fun updateSelected(indexSelect: Int) {
        val station = currentLocationPlatforms.getOrNull(indexSelect)
        if (station == null) {
            this.indexSelect = 0
            targetPos = null
            selectedDistance = 0
            cargoHatch?.changeTarget(
                pos = null,
                callDecreaseFuel = null,
            )
            return
        }
        this.indexSelect = indexSelect
        targetPos = station.pos
        selectedDistance = distanceTravel
        cargoHatch?.changeTarget(
            pos = station.pos,
            callDecreaseFuel = {
                val cost = selectedDistance * FUEL_PER_BLOCK
                if (cost > 0 && curBuffer >= cost) {
                    curBuffer -= cost
                    markDirty()
                    true
                } else {
                    false
                }
            },
        )
    }

    public fun distanceTo(destination: IPosition): Int {
        val origin = pos
        val dx = origin.x.toDouble() - destination.x
        val dy = origin.y.toDouble() - destination.y
        val dz = origin.z.toDouble() - destination.z
        return kotlin.math.sqrt(dx * dx + dy * dy + dz * dz).toInt()
    }

    public fun getReachableStation(id: String): GTMTE_Aerostat? {
        val station = AeroStateNetworkManager.getStation(id) as? GTMTE_Aerostat ?: return null
        if (!mMachine || !isValid() || !station.mMachine || !station.isValid() || station === this) {
            return null
        }
        val originTile = baseMetaTileEntity
        val destinationTile = station.baseMetaTileEntity
        if (originTile.ownerName != destinationTile.ownerName || originTile.world !== destinationTile.world) {
            return null
        }
        val distance = distanceTo(station.pos)
        return if (distance in 16..travelRange) {
            station
        } else {
            null
        }
    }

    public fun travelTo(
        player: EntityPlayer,
        stationId: String,
    ): Boolean {
        val tile = baseMetaTileEntity ?: return false
        if (tile.isClientSide || player.worldObj !== tile.world ||
            !hasAerostat(player) || !tile.isUseableByPlayer(player)
        ) {
            return false
        }
        val destination = getReachableStation(stationId)
        val error = when {
            destination == null -> "impact.aerostat.unavailable"
            player.ridingEntity != null -> "impact.aerostat.dismount"
            curBuffer < distanceTo(destination.pos) * FUEL_PER_BLOCK -> "impact.aerostat.no_fuel"
            else -> ""
        }
        if (error.isNotEmpty()) {
            player.addChatMessage(ChatComponentTranslation(error))
            return false
        }
        val arrival = destination?.pos ?: return false
        val x = arrival.x + 0.5
        val y = arrival.y + 1.0
        val z = arrival.z + 0.5
        val arrivalBox = player.boundingBox.getOffsetBoundingBox(
            x - player.posX,
            y - player.posY,
            z - player.posZ,
        )
        if (tile.world.getCollidingBoundingBoxes(player, arrivalBox).isNotEmpty()) {
            player.addChatMessage(ChatComponentTranslation("impact.aerostat.blocked"))
            return false
        }
        curBuffer -= distanceTo(arrival) * FUEL_PER_BLOCK
        markDirty()
        player.closeScreen()
        player.fallDistance = 0f
        player.setPositionAndUpdate(
            x,
            y,
            z,
        )
        return true
    }

    override fun onScrewdriverRightClick(aSide: Byte, aPlayer: EntityPlayer, aX: Float, aY: Float, aZ: Float) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ)
    }

    public override fun construct(
        itemStack: ItemStack,
        b: Boolean,
    ) {
        buildPiece(
            itemStack,
            b,
            1,
            0,
            1,
        )
    }

    public override fun survivalConstruct(
        trigger: ItemStack,
        budget: Int,
        environment: ISurvivalBuildEnvironment,
    ): Int {
        return survivalBuildPiece(
            "main",
            trigger,
            1,
            0,
            1,
            budget,
            environment,
        )
    }


    override fun getSupportedProcessingModes(): Int {
        return 0
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
        get() {
            return PositionObject(baseMetaTileEntity)
        }

    override fun updateState(nested: List<IAeroStat>) {
        val previousTarget = targetPos
        currentLocationPlatforms.clear()
        for (station in nested) {
            if (getReachableStation(station.uuid) === station) {
                currentLocationPlatforms.add(station)
            }
        }
        currentLocationPlatforms.sortWith(
            compareBy<IAeroStat> {
                distanceTo(it.pos)
            }.thenBy {
                it.name.lowercase(java.util.Locale.ROOT)
            }.thenBy {
                it.uuid
            },
        )
        val selectedIndex = currentLocationPlatforms.indexOfFirst {
            previousTarget?.isEquals(it.pos) == true
        }
        updateSelected(selectedIndex)
    }

    override fun isValid(): Boolean {
        val tile = baseMetaTileEntity ?: return false
        return !tile.isDead && tile.metaTileEntity === this &&
            tile.world.blockExists(
                tile.xCoord,
                tile.yCoord.toInt(),
                tile.zCoord,
            ) && tile.world.getTileEntity(
                tile.xCoord,
                tile.yCoord.toInt(),
                tile.zCoord,
            ) === tile
    }
    fun selectStation(
        direction: Int,
        end: Boolean,
    ) {
        if (name.isEmpty() || currentLocationPlatforms.isEmpty()) {
            return
        }
        val last = currentLocationPlatforms.lastIndex
        val next = if (end) {
            if (direction > 0) {
                last
            } else {
                0
            }
        } else {
            (indexSelect + direction).coerceIn(0, last)
        }
        updateSelected(next)
    }

    override fun getProcessingInfo(): Array<String> {
        val lines = arrayListOf(
            translateToLocalFormatted(
                "impact.processing.station",
                name,
            ),
            translateToLocalFormatted(
                "impact.processing.owner",
                owner,
            ),
            translateToLocalFormatted(
                "impact.processing.travel_fuel",
                curBuffer,
                MAX_BUFFER,
                selectedDistance * 25,
            ),
        )
        if (currentLocationPlatforms.isEmpty()) {
            lines.add(translateToLocal("impact.processing.no_stations"))
        }
        for ((index, station) in currentLocationPlatforms.withIndex()) {
            val prefix = if (index == indexSelect && targetPos != null) {
                "§6> "
            } else {
                "§7  "
            }
            lines.add(prefix + station.name)
        }
        return lines.toTypedArray()
    }

}
