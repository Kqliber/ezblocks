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
        if (isAllowedBlock(material) && isAllowedWorld(player) && isAllowedHeight(player)) {
            return false
        }
        return true
    }

    fun isAllowedBlock(material: Material): Boolean {
        val blacklistedBlocks = getValue<List<String>>("excluded_blocks").map { Material.getMaterial(it) }
        if (material !in blacklistedBlocks) {
            return true
        }
        return false
    }

    fun isAllowedWorld(player: Player): Boolean {
        val blacklistedWorlds = getValue<List<String>>("excluded_worlds")
        if (player.world.name !in blacklistedWorlds) {
            return true
        }
        return false
    }

    fun isAllowedHeight(player: Player): Boolean {

        if (!getValue<Boolean>("track_stats_below_y.enabled") && player.location.blockY <= getValue<Int>("track_stats_below_y.y")) {
            return true
        }
        return false
    }
}
