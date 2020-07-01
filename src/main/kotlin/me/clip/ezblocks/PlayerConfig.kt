package me.clip.ezblocks

import com.google.common.collect.ImmutableSet
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.*
import java.util.logging.Level

class PlayerConfig(private val plugin: Ezblocks) {

    private var dataConfig: FileConfiguration? = null
    private var dataFile: File? = null

    fun reload() {
        if (dataFile == null) dataFile = File(plugin.dataFolder, "stats.yml")
        dataConfig = YamlConfiguration.loadConfiguration(dataFile!!)
    }

    fun load(): FileConfiguration {
        if (dataConfig == null) reload()
        return dataConfig!!
    }

    fun save() {
        if (dataConfig == null || dataFile == null) return

        try {
            load().save(dataFile!!)
        } catch (ex: IOException) {
            plugin.logger.log(Level.SEVERE, "Could not save to $dataFile", ex)
        }
    }

    fun getAllEntries(): ImmutableSet<String> {
        if (dataConfig == null) reload()
        return ImmutableSet.copyOf(dataConfig!!.getKeys(false))
    }

    fun savePlayer(uuid: UUID, broken: Int) {

        if (broken < 1) return

        plugin.

    }

}