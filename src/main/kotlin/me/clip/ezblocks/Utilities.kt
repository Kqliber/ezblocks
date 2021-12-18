package me.clip.ezblocks

import me.clip.placeholderapi.PlaceholderAPI
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.OfflinePlayer
import java.util.regex.Pattern

val VERSION = Bukkit.getServer().javaClass.getPackage().name.split('.').last() // example: v1_18_R1
val USE_HEX = VERSION.split('_')[1].toInt() >= 16
private val HEX_PATTERN = Pattern.compile("(#[a-f0-9]{6})", Pattern.CASE_INSENSITIVE)

fun String.color(): String {

    //Hex Support for 1.16.1+
    var input = this
    val m = HEX_PATTERN.matcher(input)
    if (USE_HEX) {
        while (m.find()) {
            input = input.replace(m.group(), ChatColor.of(m.group(1)).toString())
        }
    } else {
        while (m.find()) {
            input = input.replace(m.group(), "")
        }
    }
    return ChatColor.translateAlternateColorCodes('&', input)
}

private fun setMessage(player: OfflinePlayer, message: String) = PlaceholderAPI.setPlaceholders(player, message.color())

fun Player.message(player: Player, msg: String) {
    val message = setMessage(player, msg)

    if (USE_HEX) {
        this.spigot().sendMessage(*TextComponent.fromLegacyText(message))
        return
    }

    this.sendMessage(message)
}
