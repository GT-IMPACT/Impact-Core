package com.impact.addon.gt.api.other

import space.gtimpact.virtual_world.api.ObjectIndicator

interface IIndicatorProvider : ObjectIndicator {
    fun hasIndicator(): Boolean
}
