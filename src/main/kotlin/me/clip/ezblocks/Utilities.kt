package me.clip.ezblocks

import me.clip.placeholderapi.PlaceholderAPI

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.OfflinePlayer

import java.util.regex.Pattern

private val HEX_PATTERN: Pattern = Pattern.compile("#<([A-Fa-f0-9]){6}>")
private val USE_HEX = Bukkit.getServer().version.contains("16")

fun String.colourise(): String {
    var translation = this

    if (USE_HEX) {
        var matcher = HEX_PATTERN.matcher(translation)

        while (matcher.find()) {
            var hexString = matcher.group()

            hexString = "#" + hexString.substring(2, hexString.length - 1)
            val hex = ChatColor.of(hexString)
            val before = translation.substring(0, matcher.start())
            val after = translation.substring(matcher.end())

            translation = before + hex + after
            matcher = HEX_PATTERN.matcher(translation)
        }
    }

    return ChatColor.translateAlternateColorCodes('&', translation)
}

fun setMessage(player: OfflinePlayer, message: String) = PlaceholderAPI.setPlaceholders(player, message.colourise())

fun Player.message(player: Player, msg: String) {
    val message = setMessage(player, msg)

    if (USE_HEX) {
        this.spigot().sendMessage(*TextComponent.fromLegacyText(message))
        return
    }

    this.sendMessage(message)
}
