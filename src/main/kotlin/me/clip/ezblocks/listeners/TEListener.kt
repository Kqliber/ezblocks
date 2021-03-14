package me.clip.ezblocks.listeners

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

import me.clip.ezblocks.handlers.BlacklistHandler

import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent
import me.clip.ezblocks.EZBlocks


/**
 * Hooks into TokenEnchant and counts the exploded blocks using TEBlockExplodeEvent.
 *
 * @author Kaliber
 */

class TEListener(private val plugin: EZBlocks) : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun TEBlockExplodeEvent.onBlockExplode() {
        val blockList = blockList() // get materials that are exploded
        val blacklistHandler = BlacklistHandler()
        if (!blacklistHandler.isAllowedWorld(player) || !blacklistHandler.isAllowedHeight(player)) {
            return
        }

        val counter = blockList.count { blacklistHandler.isAllowedBlock(it.type) } // amount of exploded blocks

        plugin.usersHandler[player.uniqueId].plus(counter)
    }
}
