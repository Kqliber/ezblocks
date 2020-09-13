package me.clip.ezblocks.handlers

import org.bukkit.OfflinePlayer
import java.util.UUID

/**
    * Temporary class for now until we get the plugin finished to a good extent. Will put each block break into a map
    * before saving to DB.
    *
    * @author Kaliber
 */

private val playerData = mutableMapOf<UUID, Int>()

class PlayerDataHandler {

    fun addBlocks(uuid: UUID, blocks: Int) {
        if (uuid !in playerData) {
            playerData[uuid] = 1
        }
        playerData[uuid]?.plus(blocks)?.let { playerData.put(uuid, it) }
    }
    fun getBlocks(player: OfflinePlayer): Int {
        return playerData[player.uniqueId] ?: return 0
    }
}
