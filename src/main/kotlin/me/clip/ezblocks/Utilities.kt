package me.clip.ezblocks

import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import me.clip.placeholderapi.PlaceholderAPI

fun String.colourise(): String = ChatColor.translateAlternateColorCodes('&', this)

fun setMessage(player: OfflinePlayer, message: String) = PlaceholderAPI.setPlaceholders(player, message.colourise())