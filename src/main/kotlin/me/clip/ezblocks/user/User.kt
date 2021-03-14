package me.clip.ezblocks.user

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.UUID

data class User(val uuid: UUID, var broken: Int) {

    fun player(): OfflinePlayer {
        return Bukkit.getOfflinePlayer(uuid)
    }

    fun name(): String {
        return player().name ?: "" // probably wont be null anyway
    }

    fun plus(other: Int) {
        broken += other
    }
}
