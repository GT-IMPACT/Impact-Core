package com.impact.addon.gt.tiles.ore_mining

import com.impact.mods.gregtech.GT_ItemList
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_OreHatch
import com.impact.util.multis.WorldProperties.chunk
import gregtech.api.enums.Casings
import gregtech.api.enums.GT_Values.RA
import gregtech.api.enums.ItemList
import gregtech.api.enums.Materials
import gregtech.api.enums.OrePrefixes
import gregtech.api.interfaces.ICasing
import gregtech.api.interfaces.IHatchElement
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase
import gregtech.api.objects.XSTR
import gregtech.api.render.TextureFactory
import gregtech.api.util.GTStructureUtility
import gregtech.api.util.GTStructureUtility.ofFrame
import gregtech.api.util.GT_OreDictUnificator
import gregtech.api.util.GT_Utility
import gregtech.api.util.MultiblockTooltipBuilder
import gregtech.api.util.StructureRequirements
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.StatCollector
import net.minecraftforge.common.util.ForgeDirection
import space.gtimpact.virtual_world.api.VirtualAPI
import space.gtimpact.virtual_world.api.core.WorldPos
import space.gtimpact.virtual_world.api.services.mining.ores.OreMiningResult
import space.gtimpact.virtual_world.api.services.scanning.ScanMode
import space.gtimpact.virtual_world.api.services.scanning.ores.OreScanReport
import space.impact.structures.alignment.IAlignmentLimits
import space.impact.structures.hint.HintColor
import space.impact.structures.structure.IStructureDefinition
import space.impact.structures.structure.ISurvivalBuildEnvironment
import space.impact.structures.structure.StructureDefinition

class GTMTEOreSamplingMachine : GT_MetaTileEntity_MultiParallelBlockBase<GTMTEOreSamplingMachine> {

    companion object {

        private val CASING: ICasing = Casings.SOLID_STEEL
        private val ORE_HATCH: IHatchElement = object : IHatchElement {

            override fun matches(tile: IMetaTileEntity?): Boolean {
                return tile is GTMTE_OreHatch
            }

            override fun count(machine: GT_MetaTileEntity_MultiBlockBase): Int {
                return (machine as? GTMTEOreSamplingMachine)?.hatch?.size ?: 0
            }

            override fun add(
                machine: GT_MetaTileEntity_MultiBlockBase,
                tile: IGregTechTileEntity,
                textureIndex: Int,
            ): Boolean {
                val controller = machine as? GTMTEOreSamplingMachine ?: return false
                val hatch = tile.metaTileEntity as? GTMTE_OreHatch ?: return false
                hatch.updateTexture(textureIndex)
                return controller.hatch.add(hatch)
            }

            override fun getDescription(): String {
                return StatCollector.translateToLocal("impact.structure.hatch.ore")
            }
        }

        private val ORE_POSITION: StructureRequirements = StructureRequirements.builder(
            CASING,
            0,
        )
            .atLeast(ORE_HATCH)
            .build()

        private val DEFINITION: IStructureDefinition<GTMTEOreSamplingMachine> by lazy {
            StructureDefinition.builder<GTMTEOreSamplingMachine>()
                .addShape(
                    "main",
                    arrayOf(
                        arrayOf("   ", " ~ ", "   "),
                        arrayOf(" A ", "ABA", " A "),
                        arrayOf(" A ", "A A", " A "),
                    ),
                )
                .addChannel(
                    GTStructureUtility.HATCH_CHANNEL,
                    1,
                    1,
                )
                .addElement(
                    'A',
                    ofFrame(Materials.Steel),
                )
                .addElement(
                    'B',
                    GTStructureUtility.buildHatchAdder(GTMTEOreSamplingMachine::class.java)
                        .hint(HintColor.RED)
                        .build(ORE_POSITION),
                )
                .build()
        }

        private const val LAYER = 1
        private const val DEFAULT_WORK = 30 * 20

        @JvmStatic
        fun addRecipe() {
            RA.addAssemblerRecipe(
                arrayOf(
                    ItemList.Hull_LV.get(1),
                    ItemList.Electric_Motor_LV.get(2),
                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2),
                    GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 4),
                    GT_Utility.getIntegratedCircuit(1)
                ),
                null, GT_ItemList.OreSamplingMachine.get(1),
                20 * 20, 16
            )
        }
    }

    constructor(aID: Int, aNameRegional: String) : super(aID, "impact.multis.miner.ore_sampling", aNameRegional)

    constructor(aName: String) : super(aName)

    init {
        enabledMaintenance = false
    }

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?) = GTMTEOreSamplingMachine(mName)

    private val hatch: HashSet<GTMTE_OreHatch> = hashSetOf()
    private var isHanded = false
    private var playerHandler: EntityPlayer? = null

    private var lastScanReport: OreScanReport? = null
    private var oreProbe: OreMiningResult? = null



    public override fun getInitialAlignmentLimits(): IAlignmentLimits {
        return IAlignmentLimits { direction, rotation, flip ->
            direction == ForgeDirection.UP && rotation.isNotRotated && flip.isNotFlipped
        }
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        te.frontFacing = 1.toByte()
        initOreProperty(te)
    }

    private fun updateScanReport() {
        val te = baseMetaTileEntity ?: return
        lastScanReport = VirtualAPI.scanning.scanOreAroundBlock(
            dimensionId = te.world.provider.dimensionId,
            layerIndex = LAYER,
            centerBlockPos = WorldPos(x = te.xCoord, z = te.zCoord),
            mode = ScanMode.WITH_AMOUNT,
        )
    }

    private fun extractOneOre() {
        val te = baseMetaTileEntity ?: return
        oreProbe = VirtualAPI.mining.mineOreAtBlock(
            dimensionId = te.world.provider.dimensionId,
            layerIndex = LAYER,
            blockPos = WorldPos(x = te.xCoord, z = te.zCoord),
            amount = 1,
        )
    }

    private fun initOreProperty(te: IGregTechTileEntity) {
        if (te.isServerSide) {
            updateScanReport()
        }
    }

    public override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity?,
        aSide: Byte,
        aFacing: Byte,
        aColorIndex: Byte,
        aActive: Boolean,
        aRedstone: Boolean,
    ): Array<ITexture> {
        return if (aSide == 1.toByte()) {
            val overlay = if (aActive) {
                Texture.Icons.ORE_SAMPLE_ACTIVE
            } else {
                Texture.Icons.ORE_SAMPLE
            }
            arrayOf(
                CASING.texture,
                TextureFactory.of(overlay),
            )
        } else {
            arrayOf(CASING.texture)
        }
    }

    override fun checkRecipe(aStack: ItemStack?): Boolean {
        val size = lastScanReport
            ?.results
            ?.sumOf { it.generatedAmount ?: 0 }
            ?: 0

        if (size <= 0) {
            stopMachine()
            return false
        }
        val oreHatch = hatch.firstOrNull() ?: return false

        return if (isHanded && oreHatch.ready) {
            mMaxProgresstime = DEFAULT_WORK
            mEUt = 0
            true
        } else false
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)
        playerHandler?.also { player ->
            aNBT.setString("playerHandler", player.gameProfile.name)
        }
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)
        aNBT.getString("playerHandler")?.also { name ->
            playerHandler = MinecraftServer.getServer().configurationManager.func_152612_a(name)
        }
    }

    override fun onRightclick(
        te: IGregTechTileEntity,
        p: EntityPlayer,
        side: Byte,
        aX: Float,
        aY: Float,
        aZ: Float
    ): Boolean {
        if (!mMachine) return true
        if (side != 1.toByte() || isHanded) return true
        playerHandler = p
        isHanded = true
        extractOneOre()
        checkRecipe(null)
        return true
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)

        if (te.isServerSide) {
            if (te.isActive) {
                val oreProbe = oreProbe
                if (tick % 20 == 2L && hatch.isNotEmpty()) {
                    val oreHatch = hatch.first()
                    oreHatch.cycleDrill(oreProbe != null && oreHatch.ready)
                }
                if (mProgresstime == DEFAULT_WORK - 1 && oreProbe != null) {
                    val multiplier = 0.05f
                    val w = te.world
                    val droppedItem = EntityItem(w, te.xCoord.toDouble(), te.yCoord.toDouble(), te.zCoord.toDouble(), oreProbe.ore.ores.random().stack)
                    droppedItem.motionX = ((-0.5f + w.rand.nextFloat()) * multiplier).toDouble()
                    droppedItem.motionY = ((4 + w.rand.nextFloat()) * multiplier).toDouble()
                    droppedItem.motionZ = ((-0.5f + w.rand.nextFloat()) * multiplier).toDouble()
                    w.spawnEntityInWorld(droppedItem)
                    playerHandler?.also { player ->
                        VirtualAPI.scannerManager.scanOres(
                            player = player,
                            mode = ScanMode.WITH_AMOUNT,
                            layer = LAYER,
                            dimensionId = te.chunk.worldObj.provider.dimensionId,
                            radiusVeins = 0,
                        )
                        playerHandler = null
                    }
                    isHanded = false
                }
            } else {
                if (tick % 20 == 2L && hatch.isNotEmpty()) {
                    hatch.firstOrNull()?.cycleDrill(false)
                }
            }
        } else {
            if (te.isActive) {
                te.world.spawnParticle(
                    "largesmoke",
                    (te.getOffsetX(0.toByte(), 1) + XSTR.XSTR_INSTANCE.nextFloat()).toDouble(),
                    te.getOffsetY(0.toByte(), 2).toDouble(),
                    (te.getOffsetZ(0.toByte(), 1) + XSTR.XSTR_INSTANCE.nextFloat()).toDouble(),
                    0.1, 0.6, 0.1
                )
            }
        }
    }

    public override fun construct(
        stack: ItemStack,
        hintOnly: Boolean,
    ) {
        buildPiece(
            stack,
            hintOnly,
            1,
            1,
            0,
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
            1,
            0,
            budget,
            environment,
        )
    }


    public override fun machineStructure(te: IGregTechTileEntity): Boolean {
        val chunk = te.world.getChunkFromBlockCoords(
            te.xCoord,
            te.zCoord,
        )
        var size = 0
        for (tile in chunk.chunkTileEntityMap.values) {
            if (tile is TileEntity && tile is IGregTechTileEntity && tile.metaTileEntity is GTMTEOreSamplingMachine) {
                size++
            }
        }
        if (size > 1) return false
        hatch.clear()
        var check = checkPiece(
            1,
            1,
            0,
        )
        if (hatch.size != 1) check = false
        return check
    }

    public override fun createTooltip(): MultiblockTooltipBuilder {
        return MultiblockTooltipBuilder(mName)
            .addInfo("WHO are you, Mr.Ore??")
            .addMachineType("Ore Sampling")
            .addInfo("Mining takes place in current Chunk by manually")
            .addSeparator()
            .addController("See the structure hologram")
            .beginStructureBlock(3, 3, 3, false)
            .addOtherStructurePart("Steel Frame Box", "At least 8")
            .addStructureHint("Miner Drill Hatch", HintColor.RED)
            .toolTipFinisher()
    }

    public override fun getStructureDefinition(): IStructureDefinition<GTMTEOreSamplingMachine> {
        return DEFINITION
    }

    override fun hasSeparate(): Boolean {
        return false
    }

    override fun hasShowConnect(): Boolean {
        return false
    }
}
