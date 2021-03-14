package me.clip.ezblocks.listeners

import me.clip.ezblocks.EZBlocks
import me.clip.ezblocks.handlers.BlacklistHandler

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent

/**
 * Listens to BlockBreakEvent and adds to block counter.
 *
 * @author Kaliber
 */

class BlockBreakListener(private val plugin: EZBlocks) : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun BlockBreakEvent.onBlockBreak() {
        val block = block.blockData.material
        if (!BlacklistHandler().isBlacklisted(player, block)) {
            return
        }

        plugin.usersHandler[player.uniqueId].broken++
    }

}
