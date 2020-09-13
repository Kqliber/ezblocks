package me.clip.ezblocks.commands

import me.clip.ezblocks.getValue
import me.clip.ezblocks.sendTranslatedMessage
import me.clip.ezblocks.handlers.PlayerDataHandler

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
class BlockCounterCommand : CommandBase() {

    @Default
    fun blocksCommand(sender: CommandSender, @Optional playerArg: String?) {
        val player = sender as Player
        val data = PlayerDataHandler()

        if (playerArg == null) {
            val message = getValue<String>("messages.blocks_broken").replace("%blocks%", "${data.getBlocks(player)}")

            return player.sendTranslatedMessage(player, message)
        }

        val player2 = Bukkit.getOfflinePlayer(playerArg) as Player
        val message = getValue<String>("messages.blocks_broken").replace("%blocks%", "${data.getBlocks(player2)}")

        player.sendTranslatedMessage(player2, message)
    }
}
