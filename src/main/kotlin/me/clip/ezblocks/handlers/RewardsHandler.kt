package me.clip.ezblocks.handlers

import me.clip.ezblocks.EZBlocks
import me.clip.ezblocks.colourise
import me.clip.ezblocks.getValue
import me.gabytm.util.actions.ActionManager
import org.bukkit.entity.Player

/**
 * Handles the rewards from config
 * Only supports 1 milestone / interval for now
 * @author Kaliber
 */
class RewardsHandler(private val plugin: EZBlocks) {

    private val actionManager = ActionManager(plugin)

    private fun runGlobalRewards(player: Player) {
        val user = plugin.usersHandler[player]
        val blocksNeeded = getValue<Int>("rewards.global_rewards.default.blocks_needed")

        if (user.broken != blocksNeeded) {
            return
        }

        val commands = getValue<List<String>>("rewards.global_rewards.default.commands").map(String::colourise)
        actionManager.execute(player, commands)
    }

    private fun runIntervalRewards(player: Player) {
        val user = plugin.usersHandler[player]
        val interval = getValue<Int>("rewards.interval_rewards.default.blocks_needed")

        if (user.broken % interval != 0) {
            return
        }

        val commands = getValue<List<String>>("rewards.interval_rewards.default.commands").map(String::colourise)
        actionManager.execute(player, commands)
    }

    fun runAllRewards(player: Player) {
        runGlobalRewards(player)
        runIntervalRewards(player)
    }
}
