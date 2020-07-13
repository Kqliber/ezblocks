package me.clip.ezblocks.hooks

import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent
import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent.getBlocksPerProcess

import me.clip.ezblocks.getValue

/**
 * Hooks into TokenEnchant and counts the exploded blocks using TEBlockExplodeEvent.
 * This will exclude any blocks from being counted if the user specifies materials to be
 * blacklisted.
 *
 * @author Kaliber
 */

class TokenEnchant : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockExplode(event: TEBlockExplodeEvent) {
        var blockCount = getBlocksPerProcess() // get amount of exploded blocks
        val excludedBlocks = getValue<List<String>>("excluded_blocks").map { Material.getMaterial(it) }
        val blockList = event.blockList() // get materials that are exploded

        blockList.forEach {
            if (it.blockData.material in excludedBlocks) {
                blockCount--
            }
        }

        // TODO: add blockCount to the player's blocks mined statistic in database
    }
}
