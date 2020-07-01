package me.clip.ezblocks.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.clip.ezblocks.EZBlocks
import org.jetbrains.exposed.sql.Database

class MySQL(private val plugin: EZBlocks) {

    private val dataSource = init()

    private fun init(): HikariDataSource {
        val section = plugin.config

        val config = HikariConfig().apply {

            jdbcUrl = "jdbc:mysql://${ConfigKey.URL[section]}:${ConfigKey.PORT[section]}/${ConfigKey.NAME[section]}"

            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = ConfigKey.PASS[section]
            password = ConfigKey.PASS[section]
            maximumPoolSize = 10
        }

        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")

        return HikariDataSource(config)
    }

    fun connect() {
        Database.connect(dataSource)
    }

}
