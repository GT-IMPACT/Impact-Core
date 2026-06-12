package com.impact.workspace.draft.comms.common

data class CommsConnectionPath(
    val computer: CommsPathPoint,
    val executor: CommsPathPoint,
    val computerTower: CommsPathPoint,
    val executorTower: CommsPathPoint,
    val satellite: CommsPathPoint,
) {
    fun points(): List<CommsPathPoint> {
        return listOf(
            executor,
            executorTower,
            satellite,
            computerTower,
            computer
        )
    }

    fun groundLines(): List<Pair<CommsPathPoint, CommsPathPoint>> {
        return listOf(
            executor to executorTower,
            computerTower to computer
        )
    }

    fun towerToTowerLine(): Pair<CommsPathPoint, CommsPathPoint> {
        return executorTower to computerTower
    }
}
