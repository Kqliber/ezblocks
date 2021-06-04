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
        return isBlacklistedBlock(material) || isBlacklistedWorld(player) || isBlacklistedHeight(player)
    }

    fun isBlacklistedBlock(material: Material): Boolean {
        val blacklistedBlocks = getValue<List<String>>("excluded_blocks").map(Material::getMaterial)
        return material in blacklistedBlocks
    }

    fun isBlacklistedWorld(player: Player): Boolean {
        val blacklistedWorlds = getValue<List<String>>("excluded_worlds")
        return player.world.name in blacklistedWorlds
    }

    fun isBlacklistedHeight(player: Player): Boolean {
        val trackY = "track_stats_below_y"
        return getValue("$trackY.enabled") || player.location.y >= getValue<Int>("$trackY.y")
    }
}
