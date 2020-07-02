package me.clip.ezblocks

import org.bukkit.ChatColor

fun String.colourise(): String = ChatColor.translateAlternateColorCodes('&', this)