package com.impact.common.storage

import appeng.api.storage.data.IAEItemStack
import net.minecraft.item.ItemStack

fun Array<IAEItemStack?>.isPlace() = find { it == null }.isNull()

fun Array<IAEItemStack?>.indexOfEmpty() = indexOf(find { it == null })

fun IAEItemStack?.isNotNull() = this != null

fun IAEItemStack?.isNull() = this == null
