package me.clip.ezblocks.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object EZBlocksData : Table(){

    private val uuid: Column<String> = varchar("uuid", 36)
    private val blocks: Column<Int> = integer("blocks")
    override val primaryKey = PrimaryKey(uuid)

}