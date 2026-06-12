package com.impact.workspace.draft.comms.common

import kotlin.math.sqrt

data class CommsPos(
    val x: Int,
    val y: Int,
    val z: Int
) {
    fun distanceSquaredTo(other: CommsPos): Int {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return dx * dx + dy * dy + dz * dz
    }

    fun distanceTo(other: CommsPos): Double {
        return sqrt(distanceSquaredTo(other).toDouble())
    }
}
