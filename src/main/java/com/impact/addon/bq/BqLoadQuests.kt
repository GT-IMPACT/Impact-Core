package com.impact.addon.bq

import betterquesting.api.properties.NativeProps
import betterquesting.api.storage.BQ_Settings
import betterquesting.api.utils.JsonHelper
import betterquesting.api.utils.NBTConverter
import betterquesting.handlers.SaveLoadHandler
import betterquesting.legacy.LegacyLoaderRegistry
import betterquesting.network.handlers.NetChapterSync
import betterquesting.network.handlers.NetQuestSync
import betterquesting.network.handlers.NetSettingSync
import betterquesting.questing.QuestDatabase
import betterquesting.questing.QuestLineDatabase
import betterquesting.storage.QuestSettings
import cpw.mods.fml.common.Loader
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.util.ChatComponentTranslation
import java.io.File

object BqLoadQuests {

    @JvmStatic
    fun load(player: EntityPlayer, isEnabled: Boolean) {
        if (Loader.isModLoaded("betterquesting") && isEnabled) {
            try {
                val qFile = File(BQ_Settings.defaultDir, "DefaultQuests.json")
                if (qFile.exists()) {
                    val editMode = QuestSettings.INSTANCE.getProperty(NativeProps.EDIT_MODE)
                    val hardMode = QuestSettings.INSTANCE.getProperty(NativeProps.HARDCORE)
                    val jsonP = QuestDatabase.INSTANCE.writeProgressToNBT(NBTTagList(), null)
                    val j1 = JsonHelper.ReadFromFile(qFile)
                    val nbt1 = NBTConverter.JSONtoNBT_Object(j1, NBTTagCompound(), true)
                    val loader = LegacyLoaderRegistry.getLoader(if (nbt1.hasKey("format", 8)) nbt1.getString("format") else "0.0.0")

                    if (loader == null) {
                        QuestSettings.INSTANCE.readFromNBT(nbt1.getCompoundTag("questSettings"))
                        QuestDatabase.INSTANCE.readFromNBT(nbt1.getTagList("questDatabase", 10), false)
                        QuestLineDatabase.INSTANCE.readFromNBT(nbt1.getTagList("questLines", 10), false)
                    } else {
                        loader.readFromJson(j1)
                    }

                    QuestDatabase.INSTANCE.readProgressFromNBT(jsonP, false)
                    QuestSettings.INSTANCE.setProperty(NativeProps.EDIT_MODE, editMode)
                    QuestSettings.INSTANCE.setProperty(NativeProps.HARDCORE, hardMode)
                    player.addChatMessage(ChatComponentTranslation("betterquesting.cmd.default.load"))
                    NetSettingSync.sendSync(null)
                    NetQuestSync.quickSync(-1, true, true)
                    NetChapterSync.sendSync(null, null)
                    SaveLoadHandler.INSTANCE.markDirty()
                } else {
                    player.addChatMessage(ChatComponentTranslation("betterquesting.cmd.default.none"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
