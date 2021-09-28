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
        val blacklistHandler = BlacklistHandler()
        if (blacklistHandler.isBlacklistedWorld(player) || blacklistHandler.isBlacklistedHeight(player)) {
            return
        }

        val counter = blockList().count { !blacklistHandler.isBlacklistedBlock(it.type) } // amount of exploded blocks

        plugin.usersHandler[player].plus(counter)
    }
}
