package com.impact.addon.structure_lib

import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.blocks.Casing_Helper
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import net.minecraft.block.Block

enum class StructureBlockIdentification(
    val block: Block,
    val meta: Int,
    val texturePage: Int = 0,
    val texturePageNumber: Int = 0,
    val forceIndexTexture: Int? = null,
    val forceTexture: ITexture? = null,
) {

    ComputerOutsideCasing(
        block = Casing_Helper.sCasePage8_3,
        meta = 7,
        texturePage = 8,
        texturePageNumber = 4,
    ),

    ComputerInsideCasing(
        block = ItemRegistery.InsideBlock,
        meta = 2,
        forceIndexTexture = ComputerOutsideCasing.indexTexture,
        forceTexture = ComputerOutsideCasing.forceTexture,
    ),
    ;

    val indexTexture: Int
        get() = forceIndexTexture ?: (meta + texturePageNumber * 16 + 128 * texturePage)

    val texture: ITexture?
        get() = forceTexture ?: Textures.BlockIcons.casingTexturePages[texturePage][meta + texturePageNumber * 16]
}
