package com.impact.addon.gt.api.aerostat

import com.impact.addon.gt.api.position.IPosition

interface IAeroStat {

    val pos: IPosition
    val name: String
    val owner: String

    fun updateState(nested: List<IAeroStat>)
    fun isValid(): Boolean
}
