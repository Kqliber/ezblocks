package me.clip.ezblocks.listeners

import me.clip.ezblocks.getValue

import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent

/**
 * Listens to BlockBreakEvent and adds to block counter.
 *
 * @author Kaliber
 */

class BlockBreak : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun BlockBreakEvent.onBlockBreak() {
        val block = block.blockData.material
        val excludedBlocks = getValue<List<String>>("excluded_blocks").map { Material.getMaterial(it) }
        if (block in excludedBlocks) {
            return
        }
        val blockCounter = 1
        // TODO: add blockCounter to the player's blocks mined statistic in database
    }

}
