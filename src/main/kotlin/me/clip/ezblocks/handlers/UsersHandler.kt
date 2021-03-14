package me.clip.ezblocks.handlers

import me.clip.ezblocks.user.User
import org.bukkit.OfflinePlayer
import java.util.UUID

/**
    * Handler for accessing and storing user data
    *
    * @author Kaliber
 */

class UsersHandler {

    private val users = mutableMapOf<UUID, User>()

    operator fun get(uuid: UUID): User {
        return users.getOrPut(uuid) {
            User(uuid, 0)
        }
    }

    operator fun get(player: OfflinePlayer): User {
        return get(player.uniqueId)
    }

}
