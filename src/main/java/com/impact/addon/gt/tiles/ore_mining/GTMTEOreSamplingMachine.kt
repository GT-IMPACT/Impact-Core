package com.impact.addon.gt.tiles.ore_mining

import com.impact.addon.vw.VirtualWorldScan
import com.impact.mods.gregtech.GT_ItemList
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_OreHatch
import com.impact.util.multis.GT_StructureUtility.ofFrame
import com.impact.util.multis.GT_StructureUtility.ofHatchAdder
import com.impact.util.multis.WorldProperties.chunk
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.enums.GT_Values.RA
import gregtech.api.enums.ItemList
import gregtech.api.enums.Materials
import gregtech.api.enums.OrePrefixes
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.objects.XSTR
import gregtech.api.render.TextureFactory
import gregtech.api.util.GT_OreDictUnificator
import gregtech.api.util.GT_Utility
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.tileentity.TileEntity
import space.gtimpact.virtual_world.api.VirtualAPI
import space.gtimpact.virtual_world.api.OreVeinCount
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility.*

class GTMTEOreSamplingMachine : GT_MetaTileEntity_MultiParallelBlockBase<GTMTEOreSamplingMachine> {

    companion object {
        private val INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16]
        private val DEFINITION = StructureDefinition.builder<GTMTEOreSamplingMachine>()
            .addShape(
                "main", arrayOf(
                    arrayOf("   ", " ~ ", "   "),
                    arrayOf(" A ", "ABA", " A "),
                    arrayOf(" A ", "A A", " A ")
                )
            )
            .addElement('A', lazy { _ -> ofFrame(Materials.Steel) })
            .addElement('B', ofHatchAdder(GTMTEOreSamplingMachine::checkHatch, 16, ImpactAPI.RED))
            .build()

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

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?) = GTMTEOreSamplingMachine(mName)

    private val hatch: HashSet<GTMTE_OreHatch> = hashSetOf()
    private var currentVeinCount: OreVeinCount? = null
    private var isHanded = false
    private var playerHandler: EntityPlayer? = null

    private fun checkHatch(te: IGregTechTileEntity?, index: Short): Boolean {
        val mte = te?.metaTileEntity ?: return false
        return if (mte is GTMTE_OreHatch) {
            mte.updateTexture(index.toInt())
            return hatch.add(mte)
        } else false
    }

    override fun isFacingValid(aFacing: Byte): Boolean {
        return aFacing.toInt() == 1
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        te.frontFacing = 1.toByte()
        initOreProperty(te)
    }

    private fun initOreProperty(te: IGregTechTileEntity) {
        if (te.isServerSide) {
            currentVeinCount = VirtualAPI.extractOreFromChunk(te.chunk, LAYER, 0)
        }
    }

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity?,
        aSide: Byte,
        aFacing: Byte,
        aColorIndex: Byte,
        aActive: Boolean,
        aRedstone: Boolean
    ): Array<ITexture> {
        return if (aSide == 1.toByte())
            arrayOf(
                INDEX_CASE,
                TextureFactory.of(if (aActive) Texture.Icons.ORE_SAMPLE_ACTIVE else Texture.Icons.ORE_SAMPLE)
            )
        else
            arrayOf(INDEX_CASE)
    }

    override fun checkRecipe(aStack: ItemStack?): Boolean {
        val size = currentVeinCount?.size ?: 0
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
        currentVeinCount = VirtualAPI.extractOreFromChunk(te.chunk, LAYER, 1)
        checkRecipe(null)
        return true
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (te.isServerSide) {
            if (te.isActive) {
                if (tick % 20 == 2L && hatch.isNotEmpty()) {
                    val oreHatch = hatch.first()
                    oreHatch.cycleDrill(currentVeinCount != null && oreHatch.ready)
                }
                if (mProgresstime == DEFAULT_WORK - 1 && currentVeinCount != null) {
                    val multiplier = 0.05f
                    val w = te.world
                    val droppedItem = EntityItem(w, te.xCoord.toDouble(), te.yCoord.toDouble(), te.zCoord.toDouble(), currentVeinCount?.vein?.ores.orEmpty().random().ore)
                    droppedItem.motionX = ((-0.5f + w.rand.nextFloat()) * multiplier).toDouble()
                    droppedItem.motionY = ((4 + w.rand.nextFloat()) * multiplier).toDouble()
                    droppedItem.motionZ = ((-0.5f + w.rand.nextFloat()) * multiplier).toDouble()
                    w.spawnEntityInWorld(droppedItem)
                    playerHandler?.also { player ->
                        VirtualWorldScan.scanVeinOre(te.chunk, LAYER,  player)
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

    override fun construct(stack: ItemStack, hintOnly: Boolean) {
        buildPiece(stack, hintOnly, 1, 1, 0)
    }

    override fun machineStructure(te: IGregTechTileEntity): Boolean {
        noMaintenance()
        val chunk = te.world.getChunkFromBlockCoords(te.xCoord, te.zCoord)
        var size = 0
        for (tile in chunk.chunkTileEntityMap.values) {
            if (tile is TileEntity && tile is IGregTechTileEntity && tile.metaTileEntity is GTMTEOreSamplingMachine) {
                size++
            }
        }
        if (size > 1) return false
        hatch.clear()
        var check = checkPiece(1, 1, 0)
        if (hatch.size != 1) check = false
        return check
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        val b = MultiBlockTooltipBuilder("ore_sampling_machine")
        b.addInfo("info.0", "WHO are you, Mr.Ore??")
            .addTypeMachine("name", "Ore Sampling")
            .addInfo("info.1", "Mining takes place in current Chunk by manually")
            .addSeparator()
            .addController()
            .sizeStructure(3, 3, 3)
            .addOtherStructurePartAny("case.0", "Steel Frame Box", 8)
            .addRedHint("Miner Drill Hatch")
            .signAndFinalize()
        return b
    }

    override fun getStructureDefinition(): IStructureDefinition<GTMTEOreSamplingMachine> = DEFINITION

    override fun hasSeparate(): Boolean {
        return false
    }
}
