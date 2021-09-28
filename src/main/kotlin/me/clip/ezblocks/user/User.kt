package me.clip.ezblocks.user

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.UUID

/**
 * stores user data
 * @author Kaliber
 */
data class User(val uuid: UUID, var broken: Int) {

    val player: OfflinePlayer
        get() = Bukkit.getOfflinePlayer(uuid)

    val name: String
        get() = player.name ?: ""

    fun plus(other: Int) {
        broken += other
    }
}
