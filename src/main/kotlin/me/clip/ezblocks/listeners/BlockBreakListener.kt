package me.clip.ezblocks.listeners

import me.clip.ezblocks.EZBlocks
import me.clip.ezblocks.handlers.BlacklistHandler
import me.clip.ezblocks.handlers.RewardsHandler

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
        if (BlacklistHandler().isBlacklisted(player, block.blockData.material)) {
            return
        }

        plugin.usersHandler[player].broken++
        RewardsHandler(plugin).runAllRewards(player)
    }
}
