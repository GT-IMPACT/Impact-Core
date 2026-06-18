package com.impact.workspace.draft.parallel_processing.integration.gt.computing

import com.impact.addon.structure_lib.StructureBlockIdentification
import com.impact.addon.structure_lib.addElementChainBlockHatch
import com.impact.mods.gregtech.tileentities.multi.structure.RequiresHatches.hasRequireHatches
import com.impact.util.string.MultiBlockTooltipBuilder
import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.integration.gt.computer.BaseComputerCommunicationMachine
import com.impact.workspace.draft.parallel_processing.integration.gt.computing.delegate.ParallelComputingMachineDelegate
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition

class ParallelComputingMachine
    : BaseComputerCommunicationMachine<ParallelComputingMachine> {

    constructor(id: Int, nameRegional: String) : super(id, "impact.multis.parallelcomputer", nameRegional)
    constructor(aName: String) : super(aName)

    internal val machineDelegate by lazy { ParallelComputingMachineDelegate() }
    private var maxComputingPpu: Int = 0
    private var currentComputingPpu: Int = 0

    override fun newMetaEntity(te: IGregTechTileEntity?): IMetaTileEntity {
        return ParallelComputingMachine(mName)
    }

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity,
        aSide: Byte, aFacing: Byte, aColorIndex: Byte,
        aActive: Boolean, aRedstone: Boolean,
    ): Array<ITexture?> {
        return if (aSide == aFacing) {
            val overlay = if (aActive) Textures.BlockIcons.MP1a else Textures.BlockIcons.MP1
            arrayOf(INDEX_CASE, TextureFactory.of(overlay))
        } else {
            arrayOf(INDEX_CASE)
        }
    }

    override fun writeInfoWaila(nbt: NBTTagCompound) {
        super.writeInfoWaila(nbt)
        nbt.setInteger("currentComputingPpu", currentComputingPpu)
        nbt.setInteger("maxComputingPpu", maxComputingPpu)
    }

    override fun readInfoWaila(nbt: NBTTagCompound, tt: MutableList<String>) {
        val currentComputingPpu = nbt.getInteger("currentComputingPpu")
        val maxComputingPpu = nbt.getInteger("maxComputingPpu")
        tt += "Capacity: ${EnumChatFormatting.GREEN}$currentComputingPpu${EnumChatFormatting.RESET} / ${EnumChatFormatting.YELLOW}$maxComputingPpu${EnumChatFormatting.RESET} PPU"
        super.readInfoWaila(nbt, tt)
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        return MultiBlockTooltipBuilder(mName).apply {
            addTypeMachine("name", "Parallel Computing")
            addSeparator()
            addController()
            addEnergyHatch(1)
            addMaintenanceHatch()
            addOtherStructurePart("psc.other.0", "Computer Rack", "psc.other.1", "Empty Rack Casing, Right Side")
            addOtherStructurePart("psc.other.2", "Parallel Hatch Out", "psc.other.3", "Empty Rack Casing, Left Side")
            addOtherStructurePart("psc.other.4", "Empty Rack Casing", "psc.other.5", "inside")
            addCasingInfo("psc.case", "Computer Casing", 13)
            signAndFinalize()
        }
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        machineDelegate.setParent(part.commsId)
    }

    override fun inValidate() {
        machineDelegate.removeBindings()
        super.inValidate()
    }

    override fun onRemoval() {
        machineDelegate.removeBindings()
        super.onRemoval()
    }

    override fun saveNBTData(nbt: NBTTagCompound) {
        super.saveNBTData(nbt)
        nbt.setInteger("maxComputingPpu", maxComputingPpu)
        nbt.setInteger("currentComputingPpu", currentComputingPpu)
    }

    override fun loadNBTData(nbt: NBTTagCompound) {
        super.loadNBTData(nbt)
        maxComputingPpu = nbt.getInteger("maxComputingPpu")
        currentComputingPpu = nbt.getInteger("currentComputingPpu")
    }

    override fun checkRecipe(itemStack: ItemStack?): Boolean {
        mMaxProgresstime = 10
        mEfficiency = (10000 - (idealStatus - repairStatus) * 1000)
        mEfficiencyIncrease = 10000
        mEUt = 8192 + maxComputingPpu
        mEUt = -mEUt
        return true
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (te.isServerSide && tick % 100 == 0L) {

            if (te.isActive) {
                maxComputingPpu = machineDelegate.calculatePpu()
                if (part.isCommsOnline() && part.commsNetworkState == CommsNetworkState.CONNECTED) {
                    currentComputingPpu = machineDelegate.transmitPpu(maxComputingPpu)
                } else {
                    machineDelegate.inactiveCommunication()
                }
            } else {
                machineDelegate.inactive()
            }
        }
    }

    override fun clearHatches() {
        machineDelegate.clearHatches()
        super.clearHatches()
    }

    override fun machineStructure(te: IGregTechTileEntity?): Boolean {
        var countMiddleCheck = 0

        var checkStructure = checkPiece("front", 0, 2, 0)

        if (checkStructure) {
            repeat(16) {
                val isOk = checkPiece("middle", 0, 2, -1 - it)
                if (isOk) countMiddleCheck++
            }
            checkStructure = countMiddleCheck in 2..16
        }

        if (checkStructure) {
            checkStructure = checkPiece("back", 0, 2, -1 - countMiddleCheck)
        }

        return checkStructure && hasRequireHatches(
            energy = 1,
            maintenance = 1,
        )
    }

    private fun addDefaultHatches(te: IGregTechTileEntity, indexTexture: Int): Boolean {
        return addMaintenanceToMachineList(te, indexTexture)
                || addEnergyInputToMachineList(te, indexTexture)
    }

    override fun construct(itemStack: ItemStack, hintsOnly: Boolean) {
        var countMiddle = 0
        buildPiece("front", itemStack, hintsOnly, 0, 2, 0)
        repeat(itemStack.stackSize.coerceIn(2, 16)) {
            countMiddle++
            buildPiece("middle", itemStack, hintsOnly, 0, 2, -1 - it)
        }
        buildPiece("back", itemStack, hintsOnly, 0, 2, -1 - countMiddle)
    }

    override fun getStructureDefinition(): IStructureDefinition<ParallelComputingMachine> {
        return STRUCTURE
    }

    companion object {
        private val INDEX_CASE = StructureBlockIdentification.ComputerOutsideCasing.texture
        private val STRUCTURE = StructureDefinition.builder<ParallelComputingMachine>()
            .addShape("front", arrayOf(arrayOf("AA", "AA", "~A", "AA")))
            .addShape("middle", arrayOf(arrayOf("AA", "BC", "BC", "AA")))
            .addShape("back", arrayOf(arrayOf("AA", "AA", "AA", "AA")))
            .addElementChainBlockHatch(
                char = 'A',
                identification = StructureBlockIdentification.ComputerOutsideCasing,
                hatchAdder = ParallelComputingMachine::addDefaultHatches,
            )
            .addElementChainBlockHatch(
                char = 'B',
                identification = StructureBlockIdentification.ComputerInsideCasing,
                hatchAdder = { te, igt, index ->
                    te.machineDelegate.addMachineCommunication(igt, index)
                },
            )
            .addElementChainBlockHatch(
                char = 'C',
                identification = StructureBlockIdentification.ComputerInsideCasing,
                hatchAdder = { te, igt, index ->
                    te.machineDelegate.addMachineRack(igt, index)
                },
            )
            .build()
    }
}
