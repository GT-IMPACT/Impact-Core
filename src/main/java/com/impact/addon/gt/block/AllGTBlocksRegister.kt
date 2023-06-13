package com.impact.addon.gt.block

object AllGTBlocksRegister {
    @JvmStatic
    fun register() {
        ScienceDataBlock.INSTANCE.registerBlock()
    }
}