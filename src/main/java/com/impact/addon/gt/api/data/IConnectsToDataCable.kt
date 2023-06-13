package com.impact.addon.gt.api.data

interface IConnectsToDataCable {
    fun canConnectData(side: Byte): Boolean
    fun getNext(source: IConnectsToDataCable?): IConnectsToDataCable?
    fun isDataInputFacing(side: Byte): Boolean
    fun getColor(): Byte
}
