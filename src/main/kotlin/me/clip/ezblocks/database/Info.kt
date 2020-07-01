package me.clip.ezblocks.database

import me.clip.ezblocks.EZBlocks
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

enum class ConfigKey(private val key: String) {

    URL("url"),
    PORT("port"),
    NAME("name"),
    USER("user"),
    PASS("pass");

    operator fun get(config: ConfigurationSection) = config.getString(key)
}