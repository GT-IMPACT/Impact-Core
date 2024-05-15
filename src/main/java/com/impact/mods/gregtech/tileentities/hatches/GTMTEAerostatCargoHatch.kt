package com.impact.mods.gregtech.tileentities.hatches

import com.impact.addon.gt.api.position.IPosition
import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import gregtech.api.enums.Textures
import gregtech.api.gui.GT_ContainerMetaTile_Machine
import gregtech.api.gui.GT_Slot_Holo
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.render.TextureFactory
import gregtech.api.util.GT_Utility
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.ICrafting
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import space.gtimpact.virtual_world.extras.send
import java.awt.Color

class GTMTEAerostatCargoHatch : GT_MetaTileEntity_Hatch {

    private var posTarget: IPosition? = null
    private var callDecreaseFuel: (() -> Boolean)? = null
    private var timerTravel: Int = 0
    private var timerCountdown: Int = 0
    var isEnabled: Boolean = false

    constructor(id: Int, name: String) : super(id, "impact.basic.aerostat.cargo", name, 1, 9, arrayOf("[WIP]")); // TODO
    constructor(name: String, desc: Array<String>, textures: Array<Array<Array<ITexture>>>) : super(name, 1, 9, desc, textures)

    override fun getTexturesActive(base: ITexture): Array<out ITexture> {
        return arrayOf(base, TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT))
    }

    override fun getTexturesInactive(base: ITexture): Array<out ITexture> {
        return arrayOf(base, TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT))
    }

    override fun newMetaEntity(te: IGregTechTileEntity): MetaTileEntity {
        return GTMTEAerostatCargoHatch(mName, mDescriptionArray, mTextures)
    }

    fun changeTarget(pos: IPosition?, callDecreaseFuel: (() -> Boolean)?) {
        this.posTarget = pos
        this.callDecreaseFuel = callDecreaseFuel
    }

    override fun isFacingValid(aFacing: Byte): Boolean {
        return true
    }

    override fun isAccessAllowed(aPlayer: EntityPlayer?): Boolean {
        return true
    }

    override fun isSimpleMachine(): Boolean {
        return true
    }

    override fun isValidSlot(aIndex: Int): Boolean {
        return true
    }

    override fun getClientGUI(aID: Int, inv: InventoryPlayer, te: IGregTechTileEntity) = Gui(te, inv)
    override fun getServerGUI(aID: Int, inv: InventoryPlayer, te: IGregTechTileEntity) = Container(te, inv)

    override fun onRightclick(te: IGregTechTileEntity, aPlayer: EntityPlayer): Boolean {
        return if (te.isClientSide) true else {
            te.openGUI(aPlayer)
            true
        }
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)

        aNBT.setInteger("timerTravel", timerTravel)
        aNBT.setInteger("timerCountdown", timerCountdown)
        aNBT.setBoolean("isEnabled", isEnabled)
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)

        timerTravel = aNBT.getInteger("timerTravel")
        timerCountdown = aNBT.getInteger("timerCountdown")
        isEnabled = aNBT.getBoolean("isEnabled")
    }

    override fun onPostTick(te: IGregTechTileEntity, aTick: Long) {
        super.onPostTick(te, aTick)

        if (te.isServerSide && te.hasInventoryBeenModified()) {
            fillStacksIntoFirstSlots()
        }

        if (te.isServerSide && timerTravel > 0) {

            if (!isEnabled) {
                timerCountdown = 0
                return
            }

            timerCountdown--

            if (timerCountdown == 0) {
                if (mInventory.any { it != null }) {
                    posTarget?.also { pos ->
                        te.getIGregTechTileEntity(pos.x, pos.y, pos.z)?.also { gte ->
                            (gte.metaTileEntity as? GTMTE_Aerostat)?.also { aerostat ->
                                if (callDecreaseFuel?.invoke() == true && mInventory?.let(aerostat::inputItems) == true) {
                                    updateSlots()
                                }
                            }
                        }
                    }
                }
            }

            if (timerCountdown <= 0)
                timerCountdown = timerTravel * 20
        }
    }

    fun updateSlots() {
        for ((index, _) in mInventory.withIndex()) {
            if (mInventory[index] != null && mInventory[index].stackSize <= 0) mInventory[index] = null
        }
        fillStacksIntoFirstSlots();
    }

    private fun fillStacksIntoFirstSlots() {
        for (i in mInventory.indices) {
            for (j in i + 1 until mInventory.size) {
                if (mInventory[j] != null && (mInventory[j]?.stackSize ?: 0) <= 0 && (mInventory[i] == null || GT_Utility.areStacksEqual(mInventory[i], mInventory[j]))) {
                    GT_Utility.moveStackFromSlotAToSlotB(
                        baseMetaTileEntity, baseMetaTileEntity, j, i, 64.toByte(), 1.toByte(), 64.toByte(), 1.toByte()
                    )
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    class Gui(te: IGregTechTileEntity?, player: InventoryPlayer?) : GT_GUIContainerMT_Machine(Container(te, player), "gregtech:textures/gui/CargoAerostat.png") {
        companion object {
            const val NAME = "Cargo Aerostat Hatch"
        }

        override fun drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
            fontRendererObj.drawString(NAME, 8, 4, 4210752)

            (mContainer as? Container)?.also {
                var time = if (it.timer > 0) "${it.timer}" else "OFF"
                var w = fontRendererObj.getStringWidth(time)
                fontRendererObj.drawString(time, 54 - w, 19, Color.WHITE.hashCode())

                if (it.timerCountdown >= 0) {
                    time = (it.timerCountdown / 20).toString()
                    w = fontRendererObj.getStringWidth(time)
                    fontRendererObj.drawString(time, 54 - w, 55, Color.WHITE.hashCode())
                }
            }
        }

        override fun drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
            super.drawGuiContainerBackgroundLayer(par1, par2, par3)
            val x = (width - xSize) / 2
            val y = (height - ySize) / 2
            drawTexturedModalRect(x, y, 0, 0, xSize, ySize)

            (mContainer as? Container)?.also {
                if (it.isEnabled)
                    drawTexturedModalRect(x + 7, y + 50, 178, 0, 16, 16)
                else
                    drawTexturedModalRect(x + 7, y + 50, 178, 18, 16, 16)
            }
        }

        override fun drawScreen(x: Int, y: Int, par3: Float) {
            super.drawScreen(x, y, par3)
            if (mContainer == null) return

            getTooltip(x, y, 7, 14, 16, 16, arrayOf("Timer transfer items, in seconds", "Decrease value"))
            getTooltip(x, y, 58, 14, 16, 16, arrayOf("Timer transfer items, in seconds", "Increase value"))
            getTooltip(x, y, 7, 50, 16, 16, arrayOf("Change Mode Active/Inactive"))
            getTooltip(x, y, 27, 16, 14, 29, arrayOf("Timer transfer items, in seconds"))
        }
    }

    class Container(te: IGregTechTileEntity?, player: InventoryPlayer?) : GT_ContainerMetaTile_Machine(player, te) {

        var timer: Int = 0
        var timerCountdown: Int = 0
        var isEnabled: Boolean = false

        override fun addSlots(aInventoryPlayer: InventoryPlayer?) {
            for ((slotIndex, row) in (0 .. 3).withIndex()) {
                val x = 59 + row * 18
                addSlotToContainer(Slot(mTileEntity, slotIndex, x, 51))
            }

            addSlotToContainer(GT_Slot_Holo(mTileEntity, 4, 7, 14, false, false, 1))
            addSlotToContainer(GT_Slot_Holo(mTileEntity, 5, 58, 14, false, false, 1))
            addSlotToContainer(GT_Slot_Holo(mTileEntity, 6, 7, 50, false, false, 1))
        }

        override fun slotClick(aSlotIndex: Int, aMouseclick: Int, aShifthold: Int, aPlayer: EntityPlayer?): ItemStack? {
            if (aSlotIndex in 4..6) {
                (mTileEntity.metaTileEntity as? GTMTEAerostatCargoHatch)?.also { te ->
                    when (aSlotIndex) {
                        4 -> {
                            if (aShifthold == 1) te.timerTravel -= 10 else te.timerTravel--
                            if (te.timerTravel <= 0) te.timerTravel = 0
                        }

                        5 -> {
                            if (aShifthold == 1) te.timerTravel += 10 else te.timerTravel++
                            if (te.timerTravel >= 60) te.timerTravel = 60
                        }

                        6 -> {
                            te.isEnabled = !te.isEnabled
                            if (mTileEntity.isServerSide)
                                aPlayer?.send("Cargo mode is ${if (te.isEnabled) "${EnumChatFormatting.GREEN}Active" else "${EnumChatFormatting.RED}Inactive"}")
                        }
                    }
                }
                return null
            }
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer)
        }

        override fun getSlotCount(): Int {
            return 4
        }

        override fun getShiftClickSlotCount(): Int {
            return 4
        }

        override fun detectAndSendChanges() {
            super.detectAndSendChanges()

            if ((mTileEntity.isClientSide) || (mTileEntity.metaTileEntity == null)) return

            (mTileEntity.metaTileEntity as? GTMTEAerostatCargoHatch)?.also { te ->
                timer = te.timerTravel
                isEnabled = te.isEnabled
                timerCountdown = te.timerCountdown + 19

                for (crafter in this.crafters) {
                    (crafter as? ICrafting)?.also {
                        it.sendProgressBarUpdate(this, 100, timer)
                        it.sendProgressBarUpdate(this, 101, timerCountdown)
                        it.sendProgressBarUpdate(this, 102, if (isEnabled) 1 else 0)
                    }
                }
            }
        }

        @SideOnly(Side.CLIENT)
        override fun updateProgressBar(key: Int, value: Int) {
            super.updateProgressBar(key, value)
            when (key) {
                100 -> timer = value
                101 -> timerCountdown = value
                102 -> isEnabled = value == 1
            }
        }
    }
}
