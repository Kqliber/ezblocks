package me.clip.ezblocks.handlers

import me.clip.ezblocks.getValue

import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Handler for blacklists using the config.yml.
 * Includes world and block blacklists.
 * @author Kaliber
 */

class BlacklistHandler {

    fun isBlacklisted(player: Player, material: Material): Boolean {
        if (isBlacklistedBlock(material) || isBlacklistedWorld(player) || isBlacklistedHeight(player)) {
            return true
        }
        return false
    }

    fun isBlacklistedBlock(material: Material): Boolean {
        val blacklistedBlocks = getValue<List<String>>("excluded_blocks").map(Material::getMaterial)
        if (material !in blacklistedBlocks) {
            return false
        }
        return true
    }

    fun isBlacklistedWorld(player: Player): Boolean {
        val blacklistedWorlds = getValue<List<String>>("excluded_worlds")
        if (player.world.name !in blacklistedWorlds) {
            return false
        }
        return true
    }

    fun isBlacklistedHeight(player: Player): Boolean {
        if (!getValue<Boolean>("track_stats_below_y.enabled")) {
            return false
        }
        if (player.location.y <= getValue<Int>("track_stats_below_y.y")) {
            return false
        }
        return true
    }
}
