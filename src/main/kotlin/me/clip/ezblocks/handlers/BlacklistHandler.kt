package me.clip.ezblocks.handlers

import me.clip.ezblocks.getValue

import org.bukkit.Material
import org.bukkit.entity.Player

class BlacklistHandler {

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
}
