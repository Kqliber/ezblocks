package me.clip.ezblocks.commands

import me.clip.ezblocks.EZBlocks
import me.clip.ezblocks.colourise
import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.base.CommandBase
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.CommandSender

/**
 * The main command handler for the plugin commands.
 *
 * @author Callum Seabrook
 */
@Command("ezblocks")
@Alias("ezb", "blocks")
class EZBlocksCommand(private val plugin: EZBlocks) : CommandBase() {

    @Default
    fun defaultCommand(sender: CommandSender) {
        val message = ComponentBuilder("""
            &c&lEZ&fBlocks &7version &f${plugin.description.version}
            &7Created by: &cextended_clip
            &7Maintained by 
        """.trimIndent().colourise())
                .color(ChatColor.RED)
                .append(TextComponent("Prevarinite")
                        .apply {
                            clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.prevarinite.com")
                        })
                .create()

        sender.spigot().sendMessage(*message)
    }
}