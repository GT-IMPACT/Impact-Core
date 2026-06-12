package com.impact.util.nbt

import net.minecraft.nbt.NBTTagCompound
import java.util.UUID

fun NBTTagCompound.containsUuid(prefix: String): Boolean {
    return hasKey("$prefix.uuidMost") && hasKey("$prefix.uuidLeast")
}

fun NBTTagCompound.getUuid(prefix: String): UUID? {
    return if (containsUuid(prefix)) {
        UUID(getLong("$prefix.uuidMost"), getLong("$prefix.uuidLeast"))
    } else null
}

fun UUID?.setNbt(prefix: String, nbt: NBTTagCompound) {
    if (this == null) return
    nbt.setLong("$prefix.uuidMost", mostSignificantBits)
    nbt.setLong("$prefix.uuidLeast", leastSignificantBits)
}
