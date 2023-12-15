package com.impact.models

import com.impact.addon.gt.api.multis.IMachineParallelRecipe

data class RecipeMachineModel(
    override var eUt: Int = 0,
    override var maxProgressTime: Int = 0,
    override val currentParallel: Int = 1,
    override val maxParallel: Int = 1,
) : IMachineParallelRecipe