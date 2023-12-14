package com.impact.models

import com.impact.addon.gt.api.multis.IMachineRecipe

class RecipeMachineModel(
    override var eUt: Int = 0,
    override var maxProgressTime: Int = 0,
) : IMachineRecipe
