package com.impact.addon.structure_lib

import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.block.Block
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility

inline fun <reified T> StructureDefinition.Builder<T>.addElementOptionalHatch(
    char: Char,
    crossinline hatchAdder: (T, IGregTechTileEntity, Int) -> Boolean,
    hatchTextureIndex: Int,
    blockCasing: Block,
    metaCasing: Int,
    dots: Int = ImpactAPI.RED,
): StructureDefinition.Builder<T> {
    val structure = ofHatchAdderOptional(
        hatchAdder = hatchAdder,
        hatchTextureIndex = hatchTextureIndex,
        blockCasing = blockCasing,
        metaCasing = metaCasing,
        dots = dots,
    )
    addElement(char, structure)
    return this
}

inline fun <reified T> StructureDefinition.Builder<T>.addElementHatchDots(
    char: Char,
    crossinline hatchAdder: (T, IGregTechTileEntity, Int) -> Boolean,
    hatchTextureIndex: Int,
    dots: Int = ImpactAPI.RED,
): StructureDefinition.Builder<T> {
    val structure = ofHatchDots<T>(
        hatchAdder = hatchAdder,
        hatchTextureIndex = hatchTextureIndex,
        dots = dots,
    )
    addElement(char, structure)
    return this
}

inline fun <reified T> StructureDefinition.Builder<T>.addElementHatchCasing(
    char: Char,
    crossinline hatchAdder: (T, IGregTechTileEntity, Int) -> Boolean,
    hatchTextureIndex: Int,
    blockCasing: Block,
    metaCasing: Int,
): StructureDefinition.Builder<T> {
    val structure = ofHatchCasing<T>(
        hatchAdder = hatchAdder,
        hatchTextureIndex = hatchTextureIndex,
        blockCasing = blockCasing,
        metaCasing = metaCasing,
    )
    addElement(char, structure)
    return this
}

inline fun <reified T> StructureDefinition.Builder<T>.addElementHatchCasing(
    char: Char,
    crossinline hatchAdder: (T, IGregTechTileEntity, Int) -> Boolean,
    identification: StructureBlockIdentification,
): StructureDefinition.Builder<T> {
    return addElementHatchCasing(
        char = char,
        hatchAdder = hatchAdder,
        hatchTextureIndex = identification.indexTexture,
        blockCasing = identification.block,
        metaCasing = identification.meta,
    )
}

inline fun <reified T> StructureDefinition.Builder<T>.addElementChainBlockHatch(
    char: Char,
    crossinline hatchAdder: (T, IGregTechTileEntity, Int) -> Boolean,
    hatchTextureIndex: Int,
    blockCasing: Block,
    metaCasing: Int,
): StructureDefinition.Builder<T> {
    val structure = StructureUtility.ofChain(
        StructureUtility.ofBlock(
            blockCasing,
            metaCasing,
        ),
        ofHatchCasing<T>(
            hatchAdder = hatchAdder,
            hatchTextureIndex = hatchTextureIndex,
            blockCasing = blockCasing,
            metaCasing = metaCasing,
        ),
    )
    addElement(char, structure)
    return this
}

inline fun <reified T> StructureDefinition.Builder<T>.addElementChainBlockHatch(
    char: Char,
    crossinline hatchAdder: (T, IGregTechTileEntity, Int) -> Boolean,
    identification: StructureBlockIdentification,
): StructureDefinition.Builder<T> {
    return addElementChainBlockHatch(
        char = char,
        hatchAdder = hatchAdder,
        hatchTextureIndex = identification.indexTexture,
        blockCasing = identification.block,
        metaCasing = identification.meta,
    )
}
