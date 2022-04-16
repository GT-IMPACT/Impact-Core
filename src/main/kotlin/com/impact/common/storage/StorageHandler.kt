package com.impact.common.storage

import appeng.api.storage.data.IAEItemStack
import appeng.util.item.AEItemStack
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import kotlin.math.min

class StorageHandler(val size: Int) {
    val items = arrayOfNulls<IAEItemStack>(size)
    val capacity = IntArray(size)

    fun add(stack: ItemStack, isSimulate: Boolean): Boolean {
        val item = items.find { it?.item == stack.item && it?.itemDamage == stack.itemDamage }
        if (item.isNotNull()) {
            val index = items.indexOf(item)
            capacity[index] += stack.stackSize
            return true
        }
        if (items.isPlace()) {
            val index = items.indexOfEmpty()
            items[index] = AEItemStack.create(stack)
            capacity[index] += stack.stackSize
            return true
        }
        return false
    }

    fun get(index: Int, stackSize: Int): ItemStack? {
        if (size > index) return null
        val stackSizeFix = min(stackSize, 64)
        val stack = items[index] ?: return null
        capacity[index] -= stackSizeFix
        return ItemStack(stack.item, stackSizeFix, stack.itemDamage)
    }

    fun saveNBT(nbt: NBTTagCompound): NBTTagCompound {
        repeat(size) {
            items[it]?.apply {
                val stack = NBTTagCompound()
                writeToNBT(stack)
                nbt.setTag("stack#$it", stack)
                nbt.setInteger("stackCapacity#$it", capacity[it])
            }
        }
        return nbt
    }

    fun loadNBT(nbt: NBTTagCompound) {
        val tag = nbt.getTag("storage") as NBTTagCompound
        repeat(size) {
            val stack = tag.getCompoundTag("stack#$it")
            items[it] = AEItemStack.loadItemStackFromNBT(stack)
            capacity[it] = tag.getInteger("stackCapacity#$it")
        }
    }
}