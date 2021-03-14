package me.clip.ezblocks.commands

import me.clip.ezblocks.EZBlocks
import me.clip.ezblocks.getValue
import me.clip.ezblocks.message

import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.Optional
import org.bukkit.Bukkit

import org.bukkit.entity.Player
import org.bukkit.command.CommandSender

/**
 * /blocks command
 * supports a player arg for other people
 * @author Kaliber
 */

@Command("blocks")
class BlockCounterCommand(private val plugin: EZBlocks) : CommandBase() {

    @Default
    fun blocksCommand(sender: CommandSender, @Optional arg: String?) {
        if (sender !is Player) {
            return sender.sendMessage("Player Command Only.")
        }

        val users = plugin.usersHandler
        if (arg == null) {
            val message = getValue<String>("messages.blocks_broken").replace("%blocks%", "${users[sender].broken}")
            return sender.message(sender, message)
        }

        val playerArg = Bukkit.getOfflinePlayer(arg)
        val message = getValue<String>("messages.blocks_broken").replace("%blocks%", "${users[playerArg].broken}")

        sender.message(sender, message)
    }
}
