package com.impact.addon.structure_lib

import com.impact.util.multis.GT_StructureUtility
import com.impact.util.multis.IGT_HatchAdder
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.block.Block
import space.impact.api.multiblocks.structure.IStructureElement

inline fun <reified T> gtHatchAdder(
    crossinline hatchAdder: (te: T, igt: IGregTechTileEntity, index: Int) -> Boolean,
): IGT_HatchAdder<T> {
    return IGT_HatchAdder<T> { te, igt, index ->
        hatchAdder(te, igt, index.toInt())
    }
}

inline fun <reified T> ofHatchAdderOptional(
    crossinline hatchAdder: (te: T, igt: IGregTechTileEntity, index: Int) -> Boolean,
    hatchTextureIndex: Int,
    blockCasing: Block,
    metaCasing: Int,
    dots: Int,
): IStructureElement<T> {
    return GT_StructureUtility
        .ofHatchAdderOptional(gtHatchAdder(hatchAdder), hatchTextureIndex, dots, blockCasing, metaCasing)
}

inline fun <reified T> ofHatchCasing(
    crossinline hatchAdder: (te: T, igt: IGregTechTileEntity, index: Int) -> Boolean,
    hatchTextureIndex: Int,
    blockCasing: Block,
    metaCasing: Int,
): IStructureElement<T> {
    return GT_StructureUtility
        .ofHatchAdder(gtHatchAdder(hatchAdder), hatchTextureIndex, blockCasing, metaCasing)
}

inline fun <reified T> ofHatchDots(
    crossinline hatchAdder: (te: T, igt: IGregTechTileEntity, index: Int) -> Boolean,
    hatchTextureIndex: Int,
    dots: Int,
): IStructureElement<T> {
    return GT_StructureUtility
        .ofHatchAdder(gtHatchAdder(hatchAdder), hatchTextureIndex, dots)
}
