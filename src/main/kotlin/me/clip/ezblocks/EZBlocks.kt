package me.clip.ezblocks

import me.clip.ezblocks.commands.BlockCounterCommand
import me.clip.ezblocks.commands.EZBlocksCommand
import me.clip.ezblocks.handlers.UsersHandler
import me.clip.ezblocks.listeners.BlockBreakListener
import me.clip.ezblocks.listeners.TEListener
import me.mattstudios.mf.base.CommandManager
import org.bukkit.plugin.java.JavaPlugin

/**
 * The main class for the plugin
 *
 * @author Callum Seabrook
 */
class EZBlocks : JavaPlugin() {

    val usersHandler = UsersHandler()

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        val commandManager = CommandManager(this)
        commandManager.register(EZBlocksCommand(this), BlockCounterCommand(this))

        val pluginManager = server.pluginManager

        pluginManager.registerEvents(BlockBreakListener(this), this)
        if (pluginManager.getPlugin("TokenEnchant") != null) {
            pluginManager.registerEvents(TEListener(this), this)
        }

    }

    override fun onDisable() {
        server.scheduler.cancelTasks(this)
    }

    companion object {
        lateinit var instance: EZBlocks
    }

}

/**
 * Gets the value of the specified key in config.yml. Uses reified to allow for
 * getting different types with the same method. Again, really hacky method but
 * it works.
 *
 * @param key the key to find in the configuration
 * @return the value of the specified key, or the default value of that key in
 *         the default config.yml if the value was null.
 *
 * @author Callum Seabrook
 */
inline fun <reified T> getValue(key: String) = EZBlocks.instance.config.get(key) as? T
        ?: EZBlocks.instance.config.defaults?.get(key) as T
