package tech.connordavis.sylcore.commands

import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo

class GameModeCommand : Command(
    CommandInfo(
        "gamemode",
        "this command allows players to change their gamemode.",
        aliases = arrayOf("creative", "survival", "spectator", "adventure", "gmc", "gms", "gmspc", "gma")
    )
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (sender !is Player) sender.sendMessage("Only players can use this command.")
        else when (commandLabel) {
            "gamemode" -> {
                when (args[0]) {
                    "creative" -> sender.gameMode = GameMode.CREATIVE
                    "survival" -> sender.gameMode = GameMode.SURVIVAL
                    "spectator" -> sender.gameMode = GameMode.SPECTATOR
                    "adventure" -> sender.gameMode = GameMode.ADVENTURE
                }
            }

            "creative" -> sender.gameMode = GameMode.CREATIVE
            "gmc" -> sender.gameMode = GameMode.CREATIVE

            "survival" -> sender.gameMode = GameMode.SURVIVAL
            "gms" -> sender.gameMode = GameMode.SURVIVAL

            "spectator" -> sender.gameMode = GameMode.SPECTATOR
            "gmspc" -> sender.gameMode = GameMode.SPECTATOR

            "adventure" -> sender.gameMode = GameMode.ADVENTURE
            "gma" -> sender.gameMode = GameMode.ADVENTURE
        }
        return false
    }
}