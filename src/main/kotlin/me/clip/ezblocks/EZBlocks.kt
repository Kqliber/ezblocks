package me.clip.ezblocks

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.clip.ezblocks.commands.BlockCounterCommand
import me.clip.ezblocks.commands.EZBlocksCommand
import me.clip.ezblocks.database.BlockDataTable
import me.clip.ezblocks.handlers.UsersHandler
import me.clip.ezblocks.listeners.BlockBreakListener
import me.clip.ezblocks.listeners.TEListener
import me.mattstudios.mf.base.CommandManager
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * The main class for the plugin
 *
 * @author Callum Seabrook
 */
class EZBlocks : JavaPlugin() {

    private var database: Database? = null
    val usersHandler = UsersHandler()

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        val commandManager = CommandManager(this)
        commandManager.register(EZBlocksCommand(this), BlockCounterCommand(this))

        server.pluginManager.registerEvents(BlockBreakListener(this), this)
        if (server.pluginManager.getPlugin("TokenEnchant") != null) {
            server.pluginManager.registerEvents(TEListener(this), this)
        }

        val dataSource = createDataSource()

        if (dataSource == null) {
            logger.severe("${getValue<String>("sql.driver")} not supported! Valid drivers are: MySQL, MariaDB, H2 or PostgreSQL")
            server.pluginManager.disablePlugin(this)
            return
        }

        database = Database.connect(dataSource)

        transaction { SchemaUtils.createMissingTablesAndColumns(BlockDataTable) }

    }

    override fun onDisable() {
        database?.connector?.invoke()?.close()
        server.scheduler.cancelTasks(this)
    }

    /**
     * Creates a new [HikariDataSource] with the specified configuration values.
     *
     * @return The configured [HikariDataSource], or null if the driver provided
     *         is not in the list of supported drivers.
     *
     * @author Callum Seabrook
     */
    private fun createDataSource(): HikariDataSource? {
        val config = HikariConfig().apply {

            // Database configuration
            val driver = getValue<String>("sql.driver").lowercase()
            val address = getValue<String>("sql.address")
            val database = getValue<String>("sql.database").lowercase()

            driverClassName = SUPPORTED_DRIVERS[driver]

            jdbcUrl = when (driver) {
                "h2" -> "jdbc:h2:file:./plugins/EZBlocks/$database"
                in listOf("mysql", "mariadb", "postgresql") -> "jdbc:$driver://$address/$database"
                else -> return null
            }
            username = getValue("sql.username")
            password = getValue("sql.password")

            // Pool configuration
            maximumPoolSize = getValue("pool.max_connections")
            connectionTimeout = getValue<Int>("pool.timeout").toLong()
            idleTimeout = getValue<Int>("pool.idle_timeout").toLong()

            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }

        return HikariDataSource(config)
    }

    companion object {

        lateinit var instance: EZBlocks

        /**
         * A list of supported drivers, excluding H2 as this is used in setting the
         * jdbcUrl in [createDataSource]. Really hacky way of solving this problem
         * but it works because all of the drivers in this list have the same JDBC URL
         * format.
         *
         * @author Callum Seabrook
         */
        private val SUPPORTED_DRIVERS = mapOf(
                "h2" to "org.h2.Driver",
                "mysql" to "com.mysql.jdbc.Driver",
                "mariadb" to "org.mariadb.jdbc.Driver",
                "postgresql" to "org.postgresql.Driver"
        )
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
