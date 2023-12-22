package com.impact.recipe

import codechicken.nei.recipe.GuiCraftingRecipe
import com.gtnewhorizons.modularui.api.ModularUITextures
import com.gtnewhorizons.modularui.api.UIInfos
import com.gtnewhorizons.modularui.api.drawable.AdaptableUITexture
import com.gtnewhorizons.modularui.api.drawable.UITexture
import com.gtnewhorizons.modularui.api.forge.IItemHandlerModifiable
import com.gtnewhorizons.modularui.api.forge.ItemStackHandler
import com.gtnewhorizons.modularui.api.math.MainAxisAlignment
import com.gtnewhorizons.modularui.api.math.Size
import com.gtnewhorizons.modularui.api.screen.IItemWithModularUI
import com.gtnewhorizons.modularui.api.screen.ModularWindow
import com.gtnewhorizons.modularui.api.screen.UIBuildContext
import com.gtnewhorizons.modularui.api.widget.Widget
import com.gtnewhorizons.modularui.common.internal.wrapper.BaseSlot
import com.gtnewhorizons.modularui.common.widget.*
import com.gtnewhorizons.modularui.common.widget.FluidSlotWidget.phantom
import com.impact.network.NetworkPackets
import com.impact.recipe.recipe_builder.CraftingTableBuilder
import cpw.mods.fml.common.registry.GameRegistry
import gregtech.api.util.GT_LanguageManager
import gregtech.api.util.GT_Recipe.GT_Recipe_Map
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fluids.FluidTank
import space.impact.impact.MODID
import space.impact.packet_network.network.NetworkHandler.sendToPlayer

class RecipeToolItem : Item(), IItemWithModularUI {

    companion object {
        @JvmField
        val recipeToolItem = RecipeToolItem()
        val PROGRESSBAR_ARROW: UITexture = UITexture.fullImage(MODID, "gui/progressbar/arrow")
        private val BACKGROUND = AdaptableUITexture
            .of("modularui:gui/background/background", 176, 166, 3)
    }

    private val recipeBuilder = CraftingTableBuilder()

    private val gtRecipes = GT_Recipe_Map.sMappings.toList()
    private var currentRecipe: GT_Recipe_Map? = null

    private val inputsCraftTable = object : ItemStackHandler(9) {
        override fun getSlotLimit(slot: Int) = 1
    }
    private val resultCraftTable = object : ItemStackHandler(1) {
        override fun getSlotLimit(slot: Int) = 1
    }

    var progress = ProgressBar()
    var tab = TabContainer()

    private val inputsMachines = ItemStackHandler(16)
    private val outputMachines = ItemStackHandler(16)
    private val inputsFluidMachines = arrayListOf<FluidTank>()
    private val outputsFluidMachines = arrayListOf<FluidTank>()

    init {
        inputsFluidMachines.clear()
        outputsFluidMachines.clear()
        repeat(12) { outputsFluidMachines += FluidTank(Int.MAX_VALUE) }
        repeat(6) { inputsFluidMachines += FluidTank(Int.MAX_VALUE) }

    }

    fun registerItem() {
        super.setHasSubtypes(true)
        val unlocalizedName = "recipe_tool_item"
        super.setUnlocalizedName(unlocalizedName)
        super.setMaxStackSize(1)
        GameRegistry.registerItem(recipeToolItem, unlocalizedName)
    }

    override fun onItemUse(
        stack: ItemStack?, player: EntityPlayer, world: World?,
        x: Int, y: Int, z: Int, side: Int,
        hitX: Float, hitY: Float, hitZ: Float
    ): Boolean {
        if (player is EntityPlayerMP) {
            UIInfos.PLAYER_HELD_ITEM_UI.open(player)
            return true
        }
        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ)
    }

    override fun onItemUseFirst(
        stack: ItemStack?, player: EntityPlayer, world: World?,
        x: Int, y: Int, z: Int, side: Int,
        hitX: Float, hitY: Float, hitZ: Float
    ): Boolean {
        if (player is EntityPlayerMP) {
            UIInfos.PLAYER_HELD_ITEM_UI.open(player)
            return true
        }
        return false
    }

    override fun createWindow(ctx: UIBuildContext, heldStack: ItemStack): ModularWindow {
        val builder = ModularWindow.builder(Size(250, 272))
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND).bindPlayerInventory(ctx.player)
        ctx.addSyncedWindow(1, ::createAnotherWindow)
        tab = TabContainer()
        tab.setButtonSize(Size(28, 32))
            .addTabButton(
                TabButton(0)
                    .setBackground(false, ModularUITextures.VANILLA_TAB_TOP_START.getSubArea(0f, 0f, 1f, 0.5f))
                    .setBackground(true, ModularUITextures.VANILLA_TAB_TOP_START.getSubArea(0f, 0.5f, 1f, 1f))
                    .setPos(0, -28)
            )
            .addTabButton(
                TabButton(1)
                    .setBackground(false, ModularUITextures.VANILLA_TAB_TOP_MIDDLE.getSubArea(0f, 0f, 1f, 0.5f))
                    .setBackground(true, ModularUITextures.VANILLA_TAB_TOP_MIDDLE.getSubArea(0f, 0.5f, 1f, 1f))
                    .setPos(28, -28)
            )
            .addPage(addCraftingTableRecipe())
            .addPage(addMultiBlock())

        return builder.widget(tab).build()
    }

    private fun MultiChildWidget.addPhantomSlot(
        inv: IItemHandlerModifiable,
        index: Int,
        x: Int, y: Int,
    ): MultiChildWidget {
        return addChild(SlotWidget(BaseSlot(inv, index)).setPos(x, y))
    }

    private fun MultiChildWidget.addPhantomFluidSlot(
        inv: FluidTank,
        x: Int, y: Int,
    ): MultiChildWidget {
        return addChild(phantom(inv, true).setPos(x, y))
    }

    private fun addCraftingTableRecipe(): Widget {
        return MultiChildWidget()
            .addChild(TextWidget("Crafting Table Recipe").setPos(10, 10))
            .addPhantomSlot(inputsCraftTable, 0, 25, 20)
            .addPhantomSlot(inputsCraftTable, 1, 43, 20)
            .addPhantomSlot(inputsCraftTable, 2, 61, 20)

            .addPhantomSlot(inputsCraftTable, 3, 25, 38)
            .addPhantomSlot(inputsCraftTable, 4, 43, 38)
            .addPhantomSlot(inputsCraftTable, 5, 61, 38)

            .addPhantomSlot(inputsCraftTable, 6, 25, 56)
            .addPhantomSlot(inputsCraftTable, 7, 43, 56)
            .addPhantomSlot(inputsCraftTable, 8, 61, 56)

            .addPhantomSlot(resultCraftTable, 0, 125, 38)
            .addChild(
                ProgressBar()
                    .setProgress(0f)
                    .setTexture(PROGRESSBAR_ARROW, 20)
                    .setPos(90, 38)
                    .setSize(20, 18)
                    .setNEITransferRect("crafting", "Crafting Table")
            )
            .addChild(
                Row()
                    .setMaxWidth(230)
                    .setAlignment(MainAxisAlignment.SPACE_BETWEEN)
                    .widgets(
                        VanillaButtonWidget()
                            .setDisplayString("Create Recipe")
                            .setOnClick { data, widget ->
                                if (!widget.isClient)
                                    recipeBuilder
                                        .addItems(inputsCraftTable.stacks.map { it?.copy() })
                                        .addResult(resultCraftTable.stacks.firstOrNull()?.copy())
                                        .createRecipe()
                            }
                            .addTooltip("Create Recipe for NEI")
                            .setSize(100, 20),

                        VanillaButtonWidget()
                            .setDisplayString("Save")
                            .setOnClick { data, widget ->
                                if (!widget.isClient)
                                    recipeBuilder
                                        .addItems(inputsCraftTable.stacks.map { it?.copy() })
                                        .addResult(resultCraftTable.stacks.firstOrNull()?.copy())
                                        .saveRecipe()
                            }
                            .addTooltip("Save Recipe to JSON")
                            .setSize(50, 20)
                    ).setPos(10, 200)
            )
    }

    private fun generateItemSlots(multi: MultiChildWidget, inv: IItemHandlerModifiable, count: Int, xStart: Int, yStart: Int) {
        when (count) {
            0 -> Unit
            1 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)

            2 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)

            3 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)
                .addPhantomSlot(inv, 2, xStart + 36, yStart)

            4 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)
                .addPhantomSlot(inv, 2, xStart + 36, yStart + 18)
                .addPhantomSlot(inv, 3, xStart + 0, yStart + 18)

            5 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)
                .addPhantomSlot(inv, 2, xStart + 36, yStart)
                .addPhantomSlot(inv, 3, xStart + 0, yStart + 18)
                .addPhantomSlot(inv, 4, xStart + 18, yStart + 18)

            6 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)
                .addPhantomSlot(inv, 2, xStart + 36, yStart)
                .addPhantomSlot(inv, 3, xStart + 0, yStart + 18)
                .addPhantomSlot(inv, 4, xStart + 18, yStart + 18)
                .addPhantomSlot(inv, 5, xStart + 36, yStart + 18)

            7 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)
                .addPhantomSlot(inv, 2, xStart + 36, yStart)
                .addPhantomSlot(inv, 3, xStart + 0, yStart + 18)
                .addPhantomSlot(inv, 4, xStart + 18, yStart + 18)
                .addPhantomSlot(inv, 5, xStart + 36, yStart + 18)
                .addPhantomSlot(inv, 6, xStart + 0, yStart + 36)

            8 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)
                .addPhantomSlot(inv, 2, xStart + 36, yStart)
                .addPhantomSlot(inv, 3, xStart + 0, yStart + 18)
                .addPhantomSlot(inv, 4, xStart + 18, yStart + 18)
                .addPhantomSlot(inv, 5, xStart + 36, yStart + 18)
                .addPhantomSlot(inv, 6, xStart + 0, yStart + 36)
                .addPhantomSlot(inv, 7, xStart + 18, yStart + 36)

            9 -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)
                .addPhantomSlot(inv, 2, xStart + 36, yStart)
                .addPhantomSlot(inv, 3, xStart + 0, yStart + 18)
                .addPhantomSlot(inv, 4, xStart + 18, yStart + 18)
                .addPhantomSlot(inv, 5, xStart + 36, yStart + 18)
                .addPhantomSlot(inv, 6, xStart + 0, yStart + 36)
                .addPhantomSlot(inv, 7, xStart + 18, yStart + 36)
                .addPhantomSlot(inv, 8, xStart + 36, yStart + 36)

            else -> multi
                .addPhantomSlot(inv, 0, xStart + 0, yStart)
                .addPhantomSlot(inv, 1, xStart + 18, yStart)
                .addPhantomSlot(inv, 2, xStart + 36, yStart)
                .addPhantomSlot(inv, 3, xStart + 54, yStart)
                .addPhantomSlot(inv, 4, xStart + 0, yStart + 18)
                .addPhantomSlot(inv, 5, xStart + 18, yStart + 18)
                .addPhantomSlot(inv, 6, xStart + 36, yStart + 18)
                .addPhantomSlot(inv, 7, xStart + 54, yStart + 18)
                .addPhantomSlot(inv, 8, xStart + 0, yStart + 36)
                .addPhantomSlot(inv, 9, xStart + 18, yStart + 36)
                .addPhantomSlot(inv, 10, xStart + 36, yStart + 36)
                .addPhantomSlot(inv, 11, xStart + 54, yStart + 36)
                .addPhantomSlot(inv, 12, xStart + 0, yStart + 54)
                .addPhantomSlot(inv, 13, xStart + 18, yStart + 54)
                .addPhantomSlot(inv, 14, xStart + 36, yStart + 54)
                .addPhantomSlot(inv, 15, xStart + 54, yStart + 54)
        }
    }

    private fun generateFluidSlots(multi: MultiChildWidget, inv: List<FluidTank>, xStart: Int, yStart: Int) {
        when (inv.size) {
            0 -> Unit
            1 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)

            2 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)

            3 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)
                .addPhantomFluidSlot(inv[2], xStart + 36, yStart)

            4 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)
                .addPhantomFluidSlot(inv[2], xStart + 0, yStart + 18)
                .addPhantomFluidSlot(inv[3], xStart + 18, yStart + 18)

            5 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)
                .addPhantomFluidSlot(inv[2], xStart + 36, yStart)
                .addPhantomFluidSlot(inv[3], xStart + 0, yStart + 18)
                .addPhantomFluidSlot(inv[4], xStart + 18, yStart + 18)

            6 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)
                .addPhantomFluidSlot(inv[2], xStart + 36, yStart)
                .addPhantomFluidSlot(inv[3], xStart + 0, yStart + 18)
                .addPhantomFluidSlot(inv[4], xStart + 18, yStart + 18)
                .addPhantomFluidSlot(inv[5], xStart + 36, yStart + 18)

            7 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)
                .addPhantomFluidSlot(inv[2], xStart + 36, yStart)
                .addPhantomFluidSlot(inv[3], xStart + 0, yStart + 18)
                .addPhantomFluidSlot(inv[4], xStart + 18, yStart + 18)
                .addPhantomFluidSlot(inv[5], xStart + 36, yStart + 18)
                .addPhantomFluidSlot(inv[6], xStart + 0, yStart + 36)

            8 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)
                .addPhantomFluidSlot(inv[2], xStart + 36, yStart)
                .addPhantomFluidSlot(inv[3], xStart + 0, yStart + 18)
                .addPhantomFluidSlot(inv[4], xStart + 18, yStart + 18)
                .addPhantomFluidSlot(inv[5], xStart + 36, yStart + 18)
                .addPhantomFluidSlot(inv[6], xStart + 0, yStart + 36)
                .addPhantomFluidSlot(inv[7], xStart + 18, yStart + 36)

            9 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)
                .addPhantomFluidSlot(inv[2], xStart + 36, yStart)
                .addPhantomFluidSlot(inv[3], xStart + 0, yStart + 18)
                .addPhantomFluidSlot(inv[4], xStart + 18, yStart + 18)
                .addPhantomFluidSlot(inv[5], xStart + 36, yStart + 18)
                .addPhantomFluidSlot(inv[6], xStart + 0, yStart + 36)
                .addPhantomFluidSlot(inv[7], xStart + 18, yStart + 36)
                .addPhantomFluidSlot(inv[8], xStart + 36, yStart + 36)

            16 -> multi
                .addPhantomFluidSlot(inv[0], xStart + 0, yStart)
                .addPhantomFluidSlot(inv[1], xStart + 18, yStart)
                .addPhantomFluidSlot(inv[2], xStart + 36, yStart)
                .addPhantomFluidSlot(inv[3], xStart + 54, yStart)
                .addPhantomFluidSlot(inv[4], xStart + 0, yStart + 18)
                .addPhantomFluidSlot(inv[5], xStart + 18, yStart + 18)
                .addPhantomFluidSlot(inv[6], xStart + 36, yStart + 18)
                .addPhantomFluidSlot(inv[7], xStart + 54, yStart + 18)
                .addPhantomFluidSlot(inv[8], xStart + 0, yStart + 36)
                .addPhantomFluidSlot(inv[9], xStart + 18, yStart + 36)
                .addPhantomFluidSlot(inv[10], xStart + 36, yStart + 36)
                .addPhantomFluidSlot(inv[11], xStart + 54, yStart + 36)
                .addPhantomFluidSlot(inv[12], xStart + 0, yStart + 54)
                .addPhantomFluidSlot(inv[13], xStart + 18, yStart + 54)
                .addPhantomFluidSlot(inv[14], xStart + 36, yStart + 54)
                .addPhantomFluidSlot(inv[15], xStart + 54, yStart + 54)
        }
    }

    private fun addMultiBlock(): Widget {
        val multi = MultiChildWidget()
            .addChild(TextWidget("Multi-Blocks Recipe").setPos(10, 10))
        generateItemSlots(multi, inputsMachines, 16, 25, 50)
        generateItemSlots(multi, outputMachines, 16, 120, 50)
        generateFluidSlots(multi, inputsFluidMachines.take(16), 25, 122)
        generateFluidSlots(multi, outputsFluidMachines.take(16), 120, 122)
        return multi.createButtons().addGTRecipesDropDown()
    }

    private fun MultiChildWidget.createButtons(): MultiChildWidget {
        return addChild(
            Row()
                .setMaxWidth(230)
                .setAlignment(MainAxisAlignment.SPACE_BETWEEN)
                .widgets(
                    VanillaButtonWidget()
                        .setDisplayString("Create Recipe")
                        .setOnClick { _, widget ->
                            if (!widget.isClient) widget.context.openSyncedWindow(1)
                        }
                        .addTooltip("Create Recipe for NEI")
                        .setSize(100, 20),
                    VanillaButtonWidget()
                        .setDisplayString("Save")
                        .setOnClick { _, widget ->
                            if (!widget.isClient) Unit
                        }
                        .addTooltip("Save Recipe to JSON")
                        .setSize(50, 20)
                ).setPos(10, 160)
        )
    }

    private var dropDown = DropDownWidget()

    fun createAnotherWindow(player: EntityPlayer?): ModularWindow? {
        val row = Scrollable().setVerticalScroll()
        gtRecipes.map { GT_LanguageManager.getTranslation(it.mUnlocalizedName) }.forEachIndexed { index, name ->
            row.widget(
                MultiChildWidget()
                    .addChild(DrawableWidget().setDrawable(BACKGROUND).setSize(150, 50).setPos(10, 10 + 50 * index))
                    .addChild(TextWidget(name).setPos(25, 25 + 50 * index))
            )
        }
        return ModularWindow.builder(200, 200).setBackground(ModularUITextures.VANILLA_BACKGROUND)
            .widget(ButtonWidget.closeWindowButton(true).setPos(180, 5))
            .widget(row.setSize(160, 160).setPos(10, 10))
            .build()
    }

    private fun MultiChildWidget.addGTRecipesDropDown(): MultiChildWidget {
        val btn = VanillaButtonWidget()
            .setDisplayString("Open Nei Map")
            .setOnClick { _, widget ->
                if (widget.isClient) currentRecipe?.also { GuiCraftingRecipe.openRecipeGui(it.mNEIName) }
            }
            .setSize(60, 15)
            .setPos(180, 25)

        dropDown = DropDownWidget()
        dropDown
            .addDropDownItemsSimple(
                gtRecipes.map { GT_LanguageManager.getTranslation(it.mUnlocalizedName) },
                { buttonWidget, index, _, setSelected ->
                    buttonWidget.setOnClick { _, widget ->
                        if (!widget.isClient) {
                            widget.context.player.sendToPlayer(NetworkPackets.RecipeToolPacket.transaction(index))
                        }
                        setSelected.run()
                    }
                },
                true
            )
            .setExpandedMaxHeight(100)
            .setDirection(DropDownWidget.Direction.UP)
            .setPos(88, -20)
            .setSize(160, 15)

        return addChild(btn).addChild(dropDown)
    }

    fun updateMap(index: Int) {
        currentRecipe = gtRecipes[index]
        dropDown.setSelected(index)
    }
}
