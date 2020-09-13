package me.clip.ezblocks.listeners

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

import me.clip.ezblocks.handlers.BlacklistHandler
import me.clip.ezblocks.handlers.PlayerDataHandler

import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent
import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent.getBlocksPerProcess


/**
 * Hooks into TokenEnchant and counts the exploded blocks using TEBlockExplodeEvent.
 *
 * @author Kaliber
 */

class TEListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun TEBlockExplodeEvent.onBlockExplode() {
        var blockCounter = getBlocksPerProcess() // get amount of exploded blocks
        val blockList = blockList() // get materials that are exploded

        if (!BlacklistHandler().isAllowedWorld(player)) {
            return
        }

        blockList.forEach {
            if (!BlacklistHandler().isAllowedBlock(it.type)) {
                blockCounter--
            }
        }

        PlayerDataHandler().addBlocks(player.uniqueId, blockCounter)
    }
}
