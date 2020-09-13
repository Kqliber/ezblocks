package me.clip.ezblocks.listeners

import me.clip.ezblocks.handlers.BlacklistHandler
import me.clip.ezblocks.handlers.PlayerDataHandler

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent

/**
 * Listens to BlockBreakEvent and adds to block counter.
 *
 * @author Kaliber
 */

class BlockBreakListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun BlockBreakEvent.onBlockBreak() {
        val block = block.blockData.material
        if (!BlacklistHandler().isAllowedBlock(block) && !BlacklistHandler().isAllowedWorld(player)) {
            return
        }

        PlayerDataHandler().addBlocks(player.uniqueId, 1)
    }

}
