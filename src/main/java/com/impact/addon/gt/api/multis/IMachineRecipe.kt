package com.impact.addon.gt.api.multis

interface IMachineRecipe {
    var eUt: Int
    var maxProgressTime: Int
}

interface IMachineParallelRecipe : IMachineRecipe {
    val currentParallel: Int
    val maxParallel: Int
}
